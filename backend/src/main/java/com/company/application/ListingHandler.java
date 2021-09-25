package com.company.application;

import com.company.domain.Listing;
import com.company.domain.User;
import com.company.infrastructure.ListingRepository;
import express.Express;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class ListingHandler {

    private final Express app;
    private final ListingRepository theListingRepository;

    public ListingHandler(Express app, ListingRepository theListingRepository){
        this.app = app;
        this.theListingRepository = theListingRepository;
        initListingHandler();
    }


    private void initListingHandler() {
        app.get("/listing", (req, res) -> {
            res.append("Access-Control-Allow-Origin", "http://localhost:3000");
            res.append("Access-Control-Allow-Credentials", "true");
            ArrayList<Listing> listOfAllListings = (ArrayList<Listing>) theListingRepository.findAll();
            res.json(listOfAllListings);
        });

        app.post("/listing", (req, res) -> {
            Listing newListing = req.body(Listing.class);
            Date today = new Date();
            LocalDateTime ldt = LocalDateTime.ofInstant(today.toInstant(), ZoneId.systemDefault());
            User myUser = new User();
            myUser.setUserId(1);
            newListing.setAuditedDatetime(ldt);
            newListing.setOwner(myUser);
            try{
                Listing earlierVersionOfListing = this.theListingRepository.findMostRecentForId(5);
                newListing.setVersion((earlierVersionOfListing.getVersion() + 1));
            } catch(Exception e){
                newListing.setVersion(1);
            }


            res.append("Access-Control-Allow-Origin", "http://localhost:3000/leaseAHouse");
            res.append("Access-Control-Allow-Credentials", "true");
            try{
                theListingRepository.save(newListing);
                System.out.println("Hello");
                res.json("Posted a new listing!");
            }
            catch(Exception e){
                res.json(e);
            }
        });
    }
}
