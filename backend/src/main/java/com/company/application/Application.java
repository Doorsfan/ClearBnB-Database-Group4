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
        ReviewRepository reviewRepository = new ReviewRepository(entityManager2);

        UserHandler userHandler = new UserHandler(app,userRepository);
        BookingHandler bookingHandler = new BookingHandler(app,bookingRepository);


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
        Review review = new Review();
        review.setReviewId(1);
        review.setVersion(1);
        review.setTimestamp(LocalDateTime.now());
        review.setAuthor(user);
        review.setTarget(user);
        review.setComment("A good landlord");
        review.setRating(5);

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
