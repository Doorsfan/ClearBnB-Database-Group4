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

        /*
        // create user object
        User user = new User();
        user.setUserId(1);
        user.setUsername("Matt");
        user.setPassword("password");
        user.setEmail("matt@yahoo.com");
        user.setBalance(1000.00);

        // save user to db
        userRepository.save(user);

        // create listing object
        Listing listing = new Listing();
        listing.setListingId(1);
        listing.setVersion(1);
        listing.setAuditedDatetime(LocalDateTime.now());
        listing.setOwner(userRepository.findById(1));
        listing.setTitle("TitleXXX");
        listing.setDescription("DescriptionXXX");
        listing.setImageUrl("https://www.rocketmortgage.com/resources-cmsassets/RocketMortgage.com/Article_Images/Large_Images/TypesOfHomes/types-of-homes-hero.jpg");
        listing.setNumberGuests(1);
        listing.setLocation("Malmö");
        listing.setPrice(4200.00);
        //listing.setListingStartDate(Date.now());
        //listing.setListingEndDate(Date.now().plusMonths(1));

        // save listing to db
        listingRepository.save(listing);

        // create booking object
        Booking booking = new Booking();
        booking.setBookingId(1);
        booking.setListingBooked(1);
        booking.setBookedByUser(user);
        booking.setAmountPaid(1000.00);
        booking.setBookingStartDate(LocalDate.now().plusDays(1));
        booking.setBookingEndDate(LocalDate.now().plusDays(3));
        booking.setCancelled(false);

        // save booking to db
        bookingRepository.save(booking);
        System.out.println("\n" + bookingRepository.findAll() + "\n");
        System.out.println("\n" + bookingRepository.findById(1) + "\n");
        System.out.println("\n" + bookingRepository.findForListing(listing) + "\n");
        System.out.println("\n" + bookingRepository.findForUser(user) + "\n");

        // update booking
        bookingRepository.update(booking.getBookingId(), 4000.00, null, null);
        System.out.println("\n" + bookingRepository.findById(1) + "\n");

        // remove booking (set cancelled to true)
        bookingRepository.remove(booking.getBookingId());
        System.out.println("\n" + bookingRepository.findById(1) + "\n");


        // Close everything after program completes
        entityManager.close();
        entityManager2.close();
        entityManager3.close();
        entityManagerFactory.close();
        */
        try{
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
