package com.company.application;

import com.company.MySQL;
import com.company.domain.*;
import com.company.infrastructure.*;
import express.Express;
import jakarta.persistence.*;
import java.sql.*;
import java.time.LocalDateTime;

import redis.clients.jedis.Jedis;

public class Application {
    Connection con;

    public Application() {
        Express app = new Express();

        app.listen(4000);

        con = MySQL.INSTANCE.getConnection();

        //Connecting to Redis server on localhost
        Jedis jedis = new Jedis();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ClearBnB");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        EntityManager entityManager3 = entityManagerFactory.createEntityManager();
        EntityManager entityManager4 = entityManagerFactory.createEntityManager();
        EntityManager entityManager5 = entityManagerFactory.createEntityManager();
        EntityManager entityManager6 = entityManagerFactory.createEntityManager();

        // Repositories
        UserRepository userRepository = new UserRepository(entityManager);
        UserRepository secondUserRepository = new UserRepository((entityManager6));

        BookingRepository bookingRepository = new BookingRepository(entityManager2);
        ReviewRepository reviewRepository = new ReviewRepository(entityManager3);
        ListingRepository listingRepository = new ListingRepository(entityManager4);
        MessageRepository messageRepository = new MessageRepository(entityManager5);

        // Cache Repositories
        UserCacheRepository userCacheRepository = new UserCacheRepository(jedis);

        //Handlers
        BookingHandler bookingHandler = new BookingHandler(app,bookingRepository, listingRepository,
                userRepository, userCacheRepository);
        UserHandler userHandler = new UserHandler(app, userRepository, userCacheRepository);
        ReviewHandler reviewHandler = new ReviewHandler(app,reviewRepository, listingRepository, userRepository);
        ListingHandler listingHandler = new ListingHandler(app,listingRepository, reviewRepository, secondUserRepository);
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
