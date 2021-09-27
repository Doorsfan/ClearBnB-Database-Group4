package com.company.application;

import com.company.infrastructure.UserRepository;
import express.Express;
import com.company.domain.User;
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
    }
}
