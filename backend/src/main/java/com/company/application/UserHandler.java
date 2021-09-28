package com.company.application;

import com.company.domain.User;
import com.company.infrastructure.UserRepository;

import express.Express;
import java.util.Map;
import jakarta.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

public class UserHandler {
    private Express app; //DEBUGABLE, made final
    private EntityManager entityManager;
    private UserRepository userRepository;

    public UserHandler(Express app, EntityManager entityManager) {
        this.app = app;
        this.entityManager = entityManager;
        this.userRepository = new UserRepository(entityManager);
    }

    public void register() {
        // Register user
        app.post("/api/register", (req, res) -> {
            User user = req.body(User.class);
            res.append("Access-Control-Allow-Origin", "http://localhost:3000");
            res.append("Access-Control-Allow-Credentials", "true");
            try{
                User seeIfTheUserExists = userRepository.findByUsername(user.getUsername());
                if(seeIfTheUserExists.getClass().equals(User.class)){

                    res.json("That username is already taken.");
                }
            }
            catch(Exception e){
                if(e.getMessage() == "No entity found for query"){
                    res.json("Made a new user!");
                    double defaultBalance = 40000.00;

                    // Check if email is not taken
                    User exists = this.userRepository.findByEmail(user.getEmail());

                    // Email is taken
                    if (exists != null) {
                        res.json(Map.of("error", "User already exists"));
                        return;
                    }

                    // Hash password (encrypt password)

                    String hashedPassword = HashPassword.hash(user.getPassword());
                    //String hashedPassword = HashPassword.hash(user.getPassword());

                    // Populate user
                    user.setUserId(user.getUserId());
                    user.setUsername(user.getUsername());
                    user.setPassword(hashedPassword);
                    user.setEmail(user.getEmail());
                    user.setBalance(user.getBalance());

                    // Save user to db
                    userRepository.save(user);
                    System.out.println(userRepository.findAll());
                    res.json("User created");
                }
                else{
                    System.out.println(e.getMessage());
                    res.json(e.getMessage());
                }
            }
         });
    }

    public void update(@NotNull User user, String username, String password, String email, double balance) {

        // Update a user
        userRepository.update(user.getUserId(), username, password, email, balance);
        System.out.println(userRepository.findAll());
        entityManager.close();
    }

    public void remove(@NotNull User user) {
        // Remove a user
        userRepository.remove(user.getUserId());
        System.out.println(userRepository.findAll());
        entityManager.close();
    }

    public void remove(int userId) {
        // Remove a user
        userRepository.remove(userId);
        System.out.println(userRepository.findAll());
        entityManager.close();
    }

    public void login() {
        // login user
        app.post("/api/login", (req, res) -> {
            User user = req.body(User.class);
            res.append("Access-Control-Allow-Origin", "http://localhost:3000");
            res.append("Access-Control-Allow-Credentials", "true");

            User exists = this.userRepository.findByEmail(user.getEmail());

            if (exists == null) {
                res.json(Map.of("error", "Bad credentials"));
                return;
            }

            // validate password
            if (HashPassword.match(user.getPassword(), exists.getPassword())) {
                // save user in session, to remember logged in state
                req.session("current-user", exists);
                res.json(exists);

            } else {
                res.json(Map.of("error", "Bad credentials"));
            }
            entityManager.close();
        });
    }

    public void whoami() {
        // who am i? get logged in user
        app.get("/api/whoami", (req, res) -> {
            // return user saved in session
            res.append("Access-Control-Allow-Origin", "http://localhost:3000");
            res.append("Access-Control-Allow-Credentials", "true");
            res.json(req.session("current-user"));
            entityManager.close();
        });
    }

    public void logout(){
        // Logout user
        app.get("/api/logout", (req, res) -> {
            // Remove user from session
            res.append("Access-Control-Allow-Origin", "http://localhost:3000");
            res.append("Access-Control-Allow-Credentials", "true");
            req.session("current-user", null);

            res.json(Map.of("ok", "logged out"));
            entityManager.close();
        });
    }
}
