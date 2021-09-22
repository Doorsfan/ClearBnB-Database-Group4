package com.company.application;

import com.company.domain.Listing;
import com.company.domain.User;
import com.company.infrastructure.ListingRepository;
import express.Express;

public class ListingHandler {

    private final Express app;
    private final ListingRepository theListingRepository;

    public ListingHandler(Express app, ListingRepository theListingRepository){
        this.app = app;
        this.theListingRepository = theListingRepository;
        initListingHandler();
    }


    private void initListingHandler() {
        app.post("/listing", (req, res) -> {
            Listing newListing = req.body(Listing.class);
            res.append("Access-Control-Allow-Origin", "http://localhost:3000/leaseAHouse");
            res.append("Access-Control-Allow-Credentials", "true");
            try{
                theListingRepository.save(newListing);
                res.json("Posted a new listing!");
            }
            catch(Exception e){
                res.json(e);
            }
        });
    }
}
