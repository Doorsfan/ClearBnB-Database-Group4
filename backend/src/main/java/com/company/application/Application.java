package com.company.application;

import com.company.domain.*;
import com.company.infrastructure.*;
import express.Express;
import jakarta.persistence.*;

import java.sql.*;

public class Application {
    Connection con;

    public Application() {
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
        test.setUsername("matt");
        test.setPassword("password");
        test.setEmail("matt@yahoo.com");
        test.setBalance(420.00);

        // test saving user to db
        userRepository.save(test);
        System.out.println(userRepository.findAll());

        // test updating a user
        userRepository.update(test.getUserId(), null, null, null, 400.00);
        System.out.println(userRepository.findAll());

        // test removing a user
        userRepository.remove(test.getUserId());
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
