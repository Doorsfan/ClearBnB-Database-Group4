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
        EntityManager entityManager3 = entityManagerFactory.createEntityManager();

        UserRepository userRepository = new UserRepository(entityManager);
        BookingRepository bookingRepository = new BookingRepository(entityManager);
        MessageRepository messageRepository = new MessageRepository(entityManager);

        BookingHandler bookingHandler = new BookingHandler(app,bookingRepository);
        ReviewRepository reviewRepository = new ReviewRepository(entityManager2);
        ListingRepository listingRepository = new ListingRepository(entityManager3);


        /*** Tests for MessageRepository ***/
        UserHandler userHandler = new UserHandler(app,userRepository);
        ReviewHandler reviewHandler = new ReviewHandler(app,reviewRepository, listingRepository);

        /*** Tests for ReviewRepository ***/
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
        reviewRepository.save(review);
        System.out.println("\n" + reviewRepository.findAll() + "\n");
        System.out.println("\n" + reviewRepository.findMostRecentForId(1) + "\n");
        System.out.println("\n" + reviewRepository.findAllForAuthor(user) + "\n");
        System.out.println("\n" + reviewRepository.findAllForTarget(user) + "\n");

        // update review
        reviewRepository.update(review.getReviewId(), "Actually not great", 3);
        System.out.println("\n" + reviewRepository.findMostRecentForId(1) + "\n");

        reviewRepository.update(review.getReviewId(), "Changed My mind", 5);
        // remove booking (set cancelled to true)
        reviewRepository.remove(review);
        System.out.println("\n" + reviewRepository.findMostRecentForId(1) + "\n");


        // Close everything after program completes
        //entityManager.close();
        //entityManager2.close();
        //entityManagerFactory.close();

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
