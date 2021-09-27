package com.company.application;

import com.company.infrastructure.UserRepository;
import com.company.utilities.HashPassword;
import express.Express;
import com.company.domain.User;

import java.util.Map;

import static nosqlite.Database.collection;

public class UserHandler {

    private final Express app;
    private final UserRepository theUserRepository;

    public UserHandler(Express app, UserRepository theUserRepository){
        this.app = app;
        this.theUserRepository = theUserRepository;
        initUserHandler();
    }


    private void initUserHandler() {
        app.post("/user", (req, res) -> {
            User newUser = req.body(User.class);

            // hash password (encrypt password)
            String hashedPassword = HashPassword.hash(newUser.getPassword());
            newUser.setPassword(hashedPassword);

            res.append("Access-Control-Allow-Origin", "http://localhost:3000");
            res.append("Access-Control-Allow-Credentials", "true");
            try{
                User seeIfTheUserExists = theUserRepository.findByUsername(newUser.getUsername());
                if(seeIfTheUserExists.getClass().equals(User.class)){

                    res.json("That username is already taken.");
                    return;
                }
            }
            catch(Exception e){
                if(e.getMessage() == "No entity found for query"){
                    theUserRepository.save(newUser);
                    res.json("Made a new user!");
                }
                else{
                    res.json(e);
                }
            }
        });

        // login user
        app.post("/api/login", (req, res) -> {
            User user = req.body(User.class);

            User userInDatabase = theUserRepository.findByUsername(user.getUsername());

            if(userInDatabase == null) {
                res.json(Map.of("error", "Bad credentials"));
                return;
            }

            // validate password
            if(HashPassword.match(user.getPassword(), userInDatabase.getPassword())) {
                // save user in session, to remember logged in state
                req.session("current-user", userInDatabase);

                res.json(userInDatabase);
            } else {
                res.json(Map.of("error", "Bad credentials"));
            }
        });

        app.get("/api/whoami", (req, res) -> {
            // return user saved in session
            res.json(req.session("current-user"));
        });

        app.get("/api/logout", (req, res) -> {
            // remove user from session
            req.session("current-user", null);
        });
    }
}
