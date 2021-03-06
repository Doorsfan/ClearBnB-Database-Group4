package com.company.application;

import com.company.infrastructure.UserCacheRepository;
import com.company.infrastructure.UserRepository;
import com.company.utilities.HashPassword;
import express.Express;
import com.company.domain.User;
import org.springframework.cache.annotation.Cacheable;

import java.util.Map;

import static nosqlite.Database.collection;

public class UserHandler {

    private final Express app;
    private final UserRepository userRepository;
    private final UserCacheRepository userCacheRepository;

    public UserHandler(Express app, UserRepository userRepository, UserCacheRepository userCacheRepository){
        this.app = app;
        this.userRepository = userRepository;
        this.userCacheRepository = userCacheRepository;
        initUserHandler();
    }


    private void initUserHandler() {

        app.get("/api/updateUserBalance", (req, res) -> {
            res.append("Access-Control-Allow-Origin", "http://localhost:3000/ListingPage");
            res.append("Access-Control-Allow-Credentials", "true");
            userRepository.update(Integer.parseInt(req.query("userId")), null,
                   null, null, Double.parseDouble(req.query("balance")));
            // invalidate user in cache
            userCacheRepository.remove("username-" + req.query("username"));
            User myUser = userRepository.findById(Integer.parseInt(req.query("userId")));
            UserDTO myUserDTO = new UserDTO();
            myUserDTO.setUsername(myUser.getUsername());
            myUserDTO.setEmail(myUser.getEmail());
            myUserDTO.setBalance(myUser.getBalance());
            res.json(myUserDTO);
        });
        
        app.post("/api/register", (req, res) -> {
            User user = req.body(User.class);

            // check if user exists
            try {
                if (userCacheRepository.find("username-" + user.getUsername()) != null
                    || userRepository.findByUsername(user.getUsername()).get(0) != null) {
                    res.json(Map.of("error", "User already exists"));
                    return;
                }
            } catch (Exception e) {
            }

            // hash password (encrypt password)
            String hashedPassword = HashPassword.hash(user.getPassword());
            user.setPassword(hashedPassword);

            // save new user
            userRepository.save(user);
            userCacheRepository.add("username-" + user.getUsername(), user);

            // log user in
            req.session("current-user", user);

            UserDTO myUserDTO = new UserDTO();
            myUserDTO.setUserId(user.getUserId());
            myUserDTO.setUsername(user.getUsername());
            myUserDTO.setBalance(user.getBalance());
            myUserDTO.setEmail(user.getEmail());
            res.json(myUserDTO);
        });

        // login user
        app.post("/api/login", (req, res) -> {
            User user = req.body(User.class);
            User userInDatabase;
            try {
                userInDatabase = userCacheRepository.find("username-" + user.getUsername()); // check cache first
                if (userInDatabase == null) {
                    userInDatabase = userRepository.findByUsername(user.getUsername()).get(0);
                    if (userInDatabase != null) {
                        userCacheRepository.add("username-" + userInDatabase.getUsername(), userInDatabase);
                    }
                }
            } catch (Exception e) {
                userInDatabase = null;
            }

            if(userInDatabase == null) {
                res.json(Map.of("error", "Bad credentials"));
                return;
            }

            // validate password
            if(HashPassword.match(req.query("password"), userInDatabase.getPassword())) {
                // save user in session, to remember logged in state
                req.session("current-user", userInDatabase);
                UserDTO myUserDTO = new UserDTO();
                myUserDTO.setUserId(userInDatabase.getUserId());
                myUserDTO.setUsername(userInDatabase.getUsername());
                myUserDTO.setBalance(userInDatabase.getBalance());
                myUserDTO.setEmail(userInDatabase.getEmail());
                res.json(myUserDTO);
            } else {
                res.json(Map.of("error", "Bad credentials"));
            }
        });

        app.get("/api/whoami", (req, res) -> {
            // return user saved in session
            res.append("Access-Control-Allow-Origin", "http://localhost:3000");
            res.append("Access-Control-Allow-Credentials", "true");
            UserDTO myUserDTO = new UserDTO();
            if(req.session("current-user") != null){
                User myUser = req.session("current-user");
                myUserDTO.setUserId(myUser.getUserId());
                myUserDTO.setUsername(myUser.getUsername());
                myUserDTO.setBalance(myUser.getBalance());
                myUserDTO.setEmail(myUser.getEmail());
                res.json(myUserDTO);
            }
            else{
                res.json("You are not logged in.");
            }
        });

        app.get("/api/logout", (req, res) -> {
            // remove user from session
            req.session("current-user", null);
        });

        app.get("/api/getUserByUsername/:username", (req, res) -> {
            User user = userCacheRepository.find("username-" + req.params("username"));
            if (user == null) {
                user = userRepository.findByUsername(req.params("username")).get(0);
                userCacheRepository.add("username-" + req.params("username"), user);
            }
            UserDTO myUserDTO = new UserDTO();
            myUserDTO.setUserId(user.getUserId());
            myUserDTO.setUsername(user.getUsername());
            myUserDTO.setBalance(user.getBalance());
            myUserDTO.setEmail(user.getEmail());
            res.json(myUserDTO);
        });
    }
}
