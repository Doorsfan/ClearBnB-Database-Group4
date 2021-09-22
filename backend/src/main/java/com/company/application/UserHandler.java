package com.company.application;

import com.company.infrastructure.UserRepository;
import express.Express;
import java.util.Map;

import com.company.domain.User;
import jakarta.persistence.EntityManager;

public class UserHandler {
    private final Express app; //DEBUGABLE, made final
    private final EntityManager entityManager;
    private final UserRepository userRepository;

    public UserHandler(Express app, EntityManager entityManager) {
        this.app = app;
        this.entityManager = entityManager;
        this.userRepository = new UserRepository(entityManager);
    }

    public void register(int userId, String username, String password, String email) {
        // Register user
        app.post("/api/register", (req, res) -> {
                    User user = req.body(User.class);
                    double defaultBalance = 40000.00;

                    // Check if email is not taken
                    User exists = this.userRepository.findByEmail(email);

                    // Email is taken
                    if (exists != null) {
                        res.json(Map.of("error", "User already exists"));
                        return;
                    }

                    // Hash password (encrypt password)
                    String hashedPassword = HashPassword.hash(user.getPassword());

                    // Populate user
                    //User test = new User();
                    user.setUserId(userId);
                    user.setUsername(username);
                    user.setPassword(hashedPassword);
                    user.setEmail(email);
                    user.setBalance(defaultBalance);

                    // Save user to db
                    userRepository.save(user);
                    System.out.println(userRepository.findAll());

        });
    }
    public void update(User user, String username, String password, String email, double balance) {

        // Update a user
        userRepository.update(user.getUserId(), username, password, email, balance);
        System.out.println(userRepository.findAll());
        entityManager.close();
    }

    public void remove(User user) {
            // Remove a user
            userRepository.remove(user.getUserId());
            System.out.println(userRepository.findAll());
    }

    public void remove(int userId) {
        // Remove a user
        userRepository.remove(userId);
        System.out.println(userRepository.findAll());
    }

    public void login() {
        // login user
        app.post("/api/login", (req, res) -> {
            User user = req.body(User.class);

            // CONVERT TO SQL
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


        });
    }

    public void whoami() {
        // who am i? get logged in user
        app.get("/api/whoami", (req, res) -> {
            // return user saved in session
            res.json(req.session("current-user"));
        });
    }

    public void logout(){
        // Logout user
        app.get("/api/logout", (req, res) -> {
            // Remove user from session
            req.session("current-user", null);

            res.json(Map.of("ok", "logged out"));
        });
    }
}
