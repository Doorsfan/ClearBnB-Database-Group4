package com.company.application;

import com.company.domain.User;
import com.company.infrastructure.UserRepository;
import express.Express;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class UserMethods {
    private Express app;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private UserRepository userRepository;

    public UserMethods(Express app, EntityManagerFactory entityManagerFactory) {
        this.app = app;
        this.entityManagerFactory = entityManagerFactory;
        this.entityManager = entityManagerFactory.createEntityManager();
        this.userRepository = new UserRepository(entityManager);
        initMethods();
    }

    private void initMethods() {
        app.get("/rest/getAllUsers", (req, res) -> {
            List<User> users = userRepository.findAll();
            for (User u : users) {
                u.setListings(null);
                u.setBookings(null);
            }
            res.json(users);
        });
    }
}
