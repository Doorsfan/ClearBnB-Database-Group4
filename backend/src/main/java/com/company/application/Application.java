package com.company.application;

import com.company.MySQL;
import com.company.domain.*;
import com.company.infrastructure.*;
import express.Express;
import jakarta.persistence.*;

import java.sql.*;
import java.time.LocalDate;
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
        ListingRepository listingRepository = new ListingRepository(entityManager2);
        BookingRepository bookingRepository = new BookingRepository(entityManager3);

        ListingHandler myListingHandler = new ListingHandler(app, listingRepository);
        
        try{
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
