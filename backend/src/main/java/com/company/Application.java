package com.company;

import com.company.Entities.*;
import com.company.Repositories.*;
import express.Express;
import jakarta.persistence.*;

import java.net.UnknownServiceException;
import java.sql.*;

public class Application {
    Connection con;

    Application() {
        Express app = new Express();

        app.listen(4000);

        con = MySQL.INSTANCE.getConnection();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ClearBnB");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        UserRepository userRepository = new UserRepository(entityManager);

        /*** Testing UserRepository methods ***/
        // create user class
        User test = new User();
        test.setUserId(1);
        test.setUsername("Matt");
        test.setPassword("password");
        test.setEmail("matt@yahoo.com");
        test.setBalance(420.00);

        // save new user to db
        userRepository.save(test);

        // print out all users in db
        System.out.println(userRepository.findAll());
        /*** end test ***/

        // Close everything after program completes
        entityManager.close();
        entityManagerFactory.close();

        try{
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
