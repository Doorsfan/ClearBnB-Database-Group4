package com.company.application;

import com.company.MySQL;
import com.company.domain.*;
import com.company.infrastructure.*;
import express.Express;
import jakarta.persistence.*;
import java.sql.*;
import java.time.LocalDateTime;

public class Application {
    Connection con;

    public Application() {
        Express app = new Express();

        app.listen(4000);

        con = MySQL.INSTANCE.getConnection();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ClearBnB");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        UserRepository userRepository = new UserRepository(entityManager);
        BookingRepository bookingRepository = new BookingRepository(entityManager);
        MessageRepository messageRepository = new MessageRepository(entityManager);

        UserHandler userHandler = new UserHandler(app,userRepository);
        BookingHandler bookingHandler = new BookingHandler(app,bookingRepository);


        /*** Tests for MessageRepository ***/
        // create user object
        User user = new User();
        user.setUserId(1);
        user.setUsername("Matt");
        user.setPassword("password");
        user.setEmail("matt@yahoo.com");
        user.setBalance(1000.00);

        // save user to db
        userRepository.save(user);

        // create review object
        Message message = new Message();
        message.setMessageId(1);
        message.setWrittenByUser(user);
        message.setContent("Hello all");
        message.setTimestamp(LocalDateTime.now());

        // save review to db
        messageRepository.save(message);
        System.out.println("\n" + messageRepository.findAll() + "\n");
        System.out.println("\n" + messageRepository.findById(1) + "\n");
        System.out.println("\n" + messageRepository.findAllForUser(user) + "\n");

        /*** End of tests for ReviewRepository ***/
        /*
        try{
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        */

    }
}
