package com.company.application;

import com.company.domain.Listing;
import com.company.domain.Review;
import com.company.domain.User;
import com.company.infrastructure.BookingRepository;
import com.company.infrastructure.ListingRepository;
import com.company.infrastructure.ReviewRepository;
import express.Express;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListingHandler {

    private final Express app;
    private final ListingRepository theListingRepository;
    private final ReviewRepository theReviewRepository;

    public ListingHandler(Express app, ListingRepository theListingRepository, ReviewRepository theReviewRepository){
        this.app = app;
        this.theListingRepository = theListingRepository;
        this.theReviewRepository = theReviewRepository;
        initListingHandler();
    }
    private void initListingHandler() {

        app.post("/makeANewLease", (req, res) -> {
            res.append("Access-Control-Allow-Origin", "*");
            res.append("Access-Control-Allow-Credentials", "true");

            Listing myListing = req.body(Listing.class);
            List<Listing> myListings = this.theListingRepository.findAll();
            int count = 1;
            boolean changedId = false;
            for(Listing listingToCheckIdOn : myListings){
                if(listingToCheckIdOn.getListingId() != count){
                    myListing.setListingId(count);
                    changedId = true;
                    break;
                }
                count += 1;
            }
            if(!changedId){
                myListing.setListingId(count);
            }
            this.theListingRepository.save(myListing);
            res.json("hi");
        });

        app.get("/getResultsFromFiltering", (req, res) -> {
            res.append("Access-Control-Allow-Origin", "*");
            System.out.println("The location was: " + req.query("location"));
            System.out.println("The numberGuests was: " + req.query("numberGuests"));
            System.out.println("The price was: " + req.query("myPrice"));
            System.out.println("The mindate was: " + req.query("myMinDate"));
            System.out.println("The maxDate was: " + req.query("myMaxDate"));
            System.out.println(this.theListingRepository.findFilteredListings(req.query("location"),
                    Integer.parseInt(req.query("numberGuests")),
                    Double.parseDouble(req.query("myPrice")), req.query("myMinDate"), req.query("myMaxDate")));
            //List<Listing> filteredListings = this.theListingRepository.findFilteredListings(myParams.get("location"),
            //        myParams.get("myGuests"), myParams.get("myMinDate"), myParams.get("myMaxDate"),
            //        myParams.get("myPrice"));
        });

        app.post("/getAllListings", (req, res) -> {
            List<Listing> allListings = this.theListingRepository.findAll();
            res.append("Access-Control-Allow-Origin", "http://localhost:3000");
            res.append("Access-Control-Allow-Credentials", "true");
            res.json(allListings);
        });

        app.post("/getSpecificListing", (req, res) -> {
            Object mySetofQueryParams = req.body();
            String queryParamsString = mySetofQueryParams.toString().substring(1, mySetofQueryParams.toString().length() - 1);
            String[] splitString = new String[20];
            queryParamsString = queryParamsString.replaceAll("\\btitle=\\b", "SPLITHERE");
            queryParamsString = queryParamsString.replaceAll("\\bdescription=\\b", "SPLITHERE");
            queryParamsString = queryParamsString.replaceAll("\\bimage_url=\\b", "SPLITHERE");
            queryParamsString = queryParamsString.replaceAll("\\blocation=\\b", "SPLITHERE");
            queryParamsString = queryParamsString.replaceAll("\\bnumber_guests=\\b", "SPLITHERE");
            queryParamsString = queryParamsString.replaceAll("\\bprice=\\b", "SPLITHERE");
            queryParamsString = queryParamsString.replaceAll("\\blisting_start_date=\\b", "SPLITHERE");
            queryParamsString = queryParamsString.replaceAll("\\blisting_end_date=\\b", "SPLITHERE");

            splitString = queryParamsString.split("SPLITHERE");
            //Title, Description, Image URL, Location, Guests, Price, start, end
            //1    , 2          ,  3       ,    4    ,  5    ,  6   , 7    , 8

            int index = 0;

            for(String myString : splitString){
                index += 1;
            }

            String wantedTitle = splitString[1].substring(0,splitString[1].length()-2);
            String wantedDescription = splitString[2].substring(0,splitString[2].length()-2);
            String wantedImageURL = splitString[3].substring(0,splitString[3].length()-2);
            String wantedLocation = splitString[4].substring(0,splitString[4].length()-2);
            String wantedGuests = splitString[5].substring(0,splitString[5].length()-2);
            String wantedPrice = splitString[6].substring(0,splitString[6].length()-2);
            String wantedStart = splitString[7].substring(0,splitString[7].length()-2);
            String wantedEnd = splitString[8].substring(0,splitString[8].length());

            int listingId = theListingRepository.findSpecifiedListing(wantedTitle,
                    wantedDescription, wantedImageURL, wantedLocation,
                    Integer.parseInt(wantedGuests), Double.parseDouble(wantedPrice),
                    wantedStart, wantedEnd);
            System.out.println(listingId);
            List<Review> listOfRelevantReviews = theReviewRepository.findAllReviewsForListingOfId(listingId);
            res.append("Access-Control-Allow-Origin", "http://localhost:3000");
            res.append("Access-Control-Allow-Credentials", "true");
            res.json(listOfRelevantReviews);

        });
    }
}
