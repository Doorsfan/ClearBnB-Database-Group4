package com.company.application;

import com.company.infrastructure.UserRepository;
import com.company.utilities.HashPassword;
import express.Express;
import com.company.domain.User;

import java.util.Map;

import static nosqlite.Database.collection;

public class UserHandler {

    private final Express app;
    private final UserRepository userRepository;

    public UserHandler(Express app, UserRepository userRepository){
        this.app = app;
        this.userRepository = userRepository;
        initUserHandler();
    }


    private void initUserHandler() {
        app.post("/api/register", (req, res) -> {
            User user = req.body(User.class);

            // check if user exists
            if (userRepository.findByUsername(user.getUsername()) != null) {
                res.json(Map.of("error", "User already exists"));
                return;
            }

            // hash password (encrypt password)
            String hashedPassword = HashPassword.hash(user.getPassword());
            user.setPassword(hashedPassword);

            // save new user
            userRepository.save(user);

            // log user in
            req.session("current-user", user);

            res.json(user);
        });

        // login user
        app.post("/api/login", (req, res) -> {
            User user = req.body(User.class);

            User userInDatabase = userRepository.findByUsername(user.getUsername());

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
