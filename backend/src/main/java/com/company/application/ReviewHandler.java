package com.company.application;

import com.company.domain.Listing;
import com.company.domain.Review;
import com.company.domain.User;
import com.company.infrastructure.ListingRepository;
import com.company.infrastructure.ReviewRepository;
import com.company.infrastructure.UserRepository;
import express.Express;

import java.util.ArrayList;

public class ReviewHandler {

    private final Express app;
    private final ReviewRepository theReviewRepository;
    private final ListingRepository theListingRepository;

    public ReviewHandler(Express app, ReviewRepository theReviewRepository, ListingRepository theListingRepository){
        this.app = app;
        this.theReviewRepository = theReviewRepository;
        this.theListingRepository = theListingRepository;
        initReviewHandler();
    }

    private void initReviewHandler() {
        app.post("/review", (req, res) -> {
            Listing listingToCheck = req.body(Listing.class);
            ArrayList<Listing> myListOfListings = (ArrayList<Listing>) this.theListingRepository.findAll();
            Listing relevantListing = null;
            for(Listing listingInList : myListOfListings){
                if(listingToCheck.getTitle() == listingInList.getTitle()
                        && listingToCheck.getDescription() == listingInList.getDescription()
                        && listingToCheck.getImageUrl() == listingInList.getImageUrl()
                && listingToCheck.getLocation() == listingInList.getLocation()
                && listingToCheck.getNumberGuests() == listingInList.getNumberGuests()
                && listingToCheck.getPrice() == listingInList.getPrice()
                && listingToCheck.getListingStartDate() == listingInList.getListingStartDate()
                && listingToCheck.getListingEndDate() == listingInList.getListingEndDate()){
                    relevantListing = listingInList;
                    System.out.println("found");
                    break;
                }
            }
            System.out.println("THE RELEVANT LISTING WAS: " + relevantListing);
            res.append("Access-Control-Allow-Origin", "http://localhost:3000");
            res.append("Access-Control-Allow-Credentials", "true");

            //this.theReviewRepository.findAll();
        });
    }
}
