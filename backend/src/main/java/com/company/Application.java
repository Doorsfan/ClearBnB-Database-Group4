package com.company;

import com.company.Entities.*;
import com.company.Repositories.*;
import express.Express;
import jakarta.persistence.*;

import java.net.UnknownServiceException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Application {
    Connection con;

    Application() {
        Express app = new Express();

        app.listen(4000);

        con = MySQL.INSTANCE.getConnection();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ClearBnB");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        UserRepository userRepository = new UserRepository(entityManager);
        ListingRepository listingRepository = new ListingRepository(entityManager2);

        /*** Testing ListingRepository methods ***/
        // create user object
        /*User user = new User();
        user.setUserId(1);
        user.setUsername("Matt");
        user.setPassword("password");
        user.setEmail("matt@yahoo.com");
        user.setBalance(1000.00);

        // save user to db
        userRepository.save(user);*/

        // create listing object
        Listing test = new Listing();
        test.setListingId(1);
        test.setVersion(1);
        test.setAuditedDatetime(LocalDateTime.now());
        test.setOwner(userRepository.findById(1));
        test.setTitle("TitleXXX");
        test.setDescription("DescriptionXXX");
        test.setImageUrl("https://www.rocketmortgage.com/resources-cmsassets/RocketMortgage.com/Article_Images/Large_Images/TypesOfHomes/types-of-homes-hero.jpg");
        test.setNumberGuests(1);
        test.setLocation("Malmö");
        test.setPrice(4200.00);
        test.setListingStartDate(LocalDate.now());
        test.setListingEndDate(LocalDate.now());

        // save listing to db
        listingRepository.save(test);
        System.out.println(listingRepository.findAllMostRecent());

        // update listing in db (creates a new version with updated info
        listingRepository.update(test.getListingId(), "UPDATED TITLE", null, null,
                null, 2, 5000.00, null, null);
        System.out.println(listingRepository.findAllForId(test.getListingId()));

        // remove listing in db (make a new version with a start and end date of null)
        listingRepository.remove(test);
        System.out.println(listingRepository.findAllForId(test.getListingId()));


        /*** end test ***/

        // Close everything after program completes
        entityManager.close();
        entityManager2.close();
        entityManagerFactory.close();

        try{
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
