package com.company.tests;

import com.company.application.UserHandler;
import com.company.domain.MySQL;

import express.Express;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.sql.Connection;


public class TestUserHandler {
    Connection con;
    Express app;
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    public TestUserHandler() {
        app = new Express();
        app.listen(5000);

        con = MySQL.INSTANCE.getConnection();

        entityManagerFactory = Persistence.createEntityManagerFactory("ClearBnB");
        entityManager = entityManagerFactory.createEntityManager();

        this.runTests();
    }

    public void runTests() {
        UserHandler userHandler = new UserHandler(app, entityManager);

        //TEST
        userHandler.register(1, "TestUser", "TestPassword", "TestEmail");
        userHandler.login();   // Logout not implemented????
        userHandler.whoami();
        userHandler.logout();
        userHandler.remove(1);
    }
}
