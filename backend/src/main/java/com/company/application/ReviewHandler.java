package com.company.application;

import com.company.domain.Listing;
import com.company.domain.Review;
import com.company.domain.User;
import com.company.infrastructure.ListingRepository;
import com.company.infrastructure.ReviewRepository;
import com.company.infrastructure.UserRepository;
import express.Express;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReviewHandler {

    private final Express app;
    private final ReviewRepository theReviewRepository;
    private final ListingRepository theListingRepository;
    private final UserRepository theUserRepository;

    public ReviewHandler(Express app, ReviewRepository theReviewRepository,
                         ListingRepository theListingRepository
                        ,UserRepository theUserRepository){
        this.app = app;
        this.theReviewRepository = theReviewRepository;
        this.theListingRepository = theListingRepository;
        this.theUserRepository = theUserRepository;
        initReviewHandler();
    }

    private void initReviewHandler() {
        app.post("/review", (req, res) -> {
            Object myReview = req.body();
            // { rating=3, comment=i wrote a comment, myQueryParameters={ username=mikael, password=temp, balance=0 }
            //
            String[] myStringArray = myReview.toString().split("myQueryParameters");

            String baseForLeft = myStringArray[0];
            String[] splitArrayLeft = new String[20];
            // Userid: 1, Comment: 5, Rating: 7, timestamp: 13, version: 15
            baseForLeft = baseForLeft.replaceAll("\\buserId=\\b", "SPLITHERE");
            baseForLeft = baseForLeft.replaceAll("\\bcomment=\\b", "SPLITHERE");
            baseForLeft = baseForLeft.replaceAll("\\brating=\\b", "SPLITHERE");
            baseForLeft = baseForLeft.replaceAll("\\btimestamp=\\b", "SPLITHERE");
            baseForLeft = baseForLeft.replaceAll("\\bversion=\\b", "SPLITHERE");
            baseForLeft = baseForLeft.replaceAll("\\b, \\b", "SPLITHERE");
            splitArrayLeft = baseForLeft.split("SPLITHERE");

            Integer wantedUserId = Integer.parseInt(splitArrayLeft[1]);
            String wantedComment = splitArrayLeft[5];
            Integer wantedRating = Integer.parseInt(splitArrayLeft[7]);
            String wantedTimeStamp = splitArrayLeft[13];
            Integer wantedVersion = Integer.parseInt(splitArrayLeft[15].substring(0,splitArrayLeft[15].length() - 2));


            String[] splitArray = new String[20];

            for(String myString : myStringArray){
                myString = myString.replaceAll("\\btitle=\\b", "SPLITHERE");
                myString = myString.replaceAll("\\bdescription=\\b", "SPLITHERE");
                myString = myString.replaceAll("\\bimageUrl=\\b", "SPLITHERE");
                myString = myString.replaceAll("\\blocation=\\b", "SPLITHERE");
                myString = myString.replaceAll("\\bnumberGuests=\\b", "SPLITHERE");
                myString = myString.replaceAll("\\bprice=\\b", "SPLITHERE");
                myString = myString.replaceAll("\\blistingStartDate=\\b", "SPLITHERE");
                myString = myString.replaceAll("\\blistingEndDate=\\b", "SPLITHERE");
                splitArray = myString.split("SPLITHERE");
            }

            String wantedTitle = splitArray[1].substring(0, (splitArray[1].length() - 2));
            String wantedDescription = splitArray[2].substring(0, (splitArray[2].length() - 2));;
            String wantedImageURL = splitArray[3].substring(0, (splitArray[3].length() - 2));
            String wantedLocation = splitArray[4].substring(0, (splitArray[4].length() - 2));
            Integer wantedGuests = Integer.parseInt(splitArray[5].substring(0, (splitArray[5].length() - 2)));
            Double wantedPrice = Double.parseDouble(splitArray[6].substring(0, (splitArray[6].length() - 2)));
            String wantedStartDate = splitArray[7].substring(0, (splitArray[7].length() - 2));
            String wantedEndDate = splitArray[8].substring(0,(splitArray[8].length() - 2));

            int theIdOfTheListing = theListingRepository.findSpecifiedListing(wantedTitle, wantedDescription,
                    wantedImageURL, wantedLocation, wantedGuests, wantedPrice, wantedStartDate, wantedEndDate);

            Review wantedReview = new Review();

            User wantedUser = this.theUserRepository.findById(wantedUserId);

            wantedReview.setAuthor(wantedUser);
            wantedReview.setComment(wantedComment);
            wantedReview.setTimestamp(wantedTimeStamp);
            wantedReview.setRating(wantedRating);
            wantedReview.setVersion(wantedVersion);
            wantedReview.setRefersToListingId(theIdOfTheListing);
            this.theReviewRepository.save(wantedReview);

            List<Review> updatedListOfReviews = this.theReviewRepository.findAllReviewsForListingOfId(theIdOfTheListing);

            /*
                Title, Description, imageUrl, location, number of guests, price, startDate, endDate
             */
            res.append("Access-Control-Allow-Origin", "http://localhost:3000");
            res.append("Access-Control-Allow-Credentials", "true");
            res.json(updatedListOfReviews);
            /*
            myReview.setVersion(1);
            myReview.setRefers_to_listingId(1);
            this.theReviewRepository.save(myReview);
            res.json("Yes");
            //this.theReviewRepository.findAll();

             */
        });

        app.post("/updateReviewId", (req, res) -> {
           Object myObj = req.body();
        });
    }
}
