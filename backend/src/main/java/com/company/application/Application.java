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
        EntityManager entityManager4 = entityManagerFactory.createEntityManager();
        EntityManager entityManager5 = entityManagerFactory.createEntityManager();

        // Repositories
        UserRepository userRepository = new UserRepository(entityManager);
        BookingRepository bookingRepository = new BookingRepository(entityManager2);
        ReviewRepository reviewRepository = new ReviewRepository(entityManager3);
        ListingRepository listingRepository = new ListingRepository(entityManager4);
        MessageRepository messageRepository = new MessageRepository(entityManager5);

        //Handlers
        BookingHandler bookingHandler = new BookingHandler(app,bookingRepository, listingRepository, userRepository);
        UserHandler userHandler = new UserHandler(app,userRepository);
        ReviewHandler reviewHandler = new ReviewHandler(app,reviewRepository, listingRepository, userRepository);
        ListingHandler listingHandler = new ListingHandler(app,listingRepository, reviewRepository);
        MessageHandler messageHandler = new MessageHandler(app, messageRepository, userRepository);

        /*
        try{
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        */

    }
}
