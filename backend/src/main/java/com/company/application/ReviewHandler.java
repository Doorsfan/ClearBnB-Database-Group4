package com.company.application;

import com.company.domain.Listing;
import com.company.domain.Review;
import com.company.domain.User;
import com.company.infrastructure.ListingRepository;
import com.company.infrastructure.ReviewRepository;
import com.company.infrastructure.UserRepository;
import express.Express;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

        app.post("/postReviewAboutOtherUser", (req, res) -> {
            Review myReview = req.body(Review.class);
            myReview.setAuthor(this.theUserRepository.findById(
                    Integer.parseInt(req.query("authorId"))));
            List<User> refersTo = this.theUserRepository.findByUsername(req.query("postedAbout"));
            myReview.setReviewsUserIdOf(refersTo.get(0).getUserId());
            myReview.setTimestamp(LocalDateTime.now());
            this.theReviewRepository.save(myReview);
            res.append("Access-Control-Allow-Origin", "http://localhost:3000");
            res.append("Access-Control-Allow-Credentials", "true");

            List<Review> myListOfReviews = this.theReviewRepository.findAllForTarget(
                    this.theUserRepository.findByUsername(req.query("postedAbout")).get(0).getUserId());

            res.json(myListOfReviews);

        });

        app.get("/getReviewsForUser", (req, res) -> {
            res.append("Access-Control-Allow-Origin", "http://localhost:3000");
            res.append("Access-Control-Allow-Credentials", "true");
            try{
                List<Review> myReviews = this.theReviewRepository.findAllForTarget(this.theUserRepository.findByUsername(
                        req.query("wantedUsername")).get(0).getUserId());
                res.json(myReviews);
            }
            catch(Exception e){
                res.json("");
            }
        });

        app.post("/getReviewsForListing", (req, res) -> {
            Object mySetofQueryParams = req.body();
            String queryParamsString = mySetofQueryParams.toString().substring(1, mySetofQueryParams.toString().length() - 1);
            String[] splitString = new String[20];
            System.out.print(queryParamsString);
            queryParamsString = queryParamsString.replaceAll("\\btitle=\\b", "SPLITHERE");
            queryParamsString = queryParamsString.replaceAll("\\bdescription=\\b", "SPLITHERE");
            queryParamsString = queryParamsString.replaceAll("\\bimageUrl=\\b", "SPLITHERE");
            queryParamsString = queryParamsString.replaceAll("\\blocation=\\b", "SPLITHERE");
            queryParamsString = queryParamsString.replaceAll("\\bnumberGuests=\\b", "SPLITHERE");
            queryParamsString = queryParamsString.replaceAll("\\bprice=\\b", "SPLITHERE");
            queryParamsString = queryParamsString.replaceAll("\\blistingStartDate=\\b", "SPLITHERE");
            queryParamsString = queryParamsString.replaceAll("\\blistingEndDate=\\b", "SPLITHERE");

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
            for(String test : splitArray){
                System.out.println("IN SPLITARRAY: " + test);
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
            wantedReview.setPostedToListingId(theIdOfTheListing);
            this.theReviewRepository.save(wantedReview);

            List<Review> updatedListOfReviews = this.theReviewRepository.findAllReviewsForListingOfId(theIdOfTheListing);

            /*
                Title, Description, imageUrl, location, number of guests, price, startDate, endDate
             */
            res.append("Access-Control-Allow-Origin", "http://localhost:3000");
            res.append("Access-Control-Allow-Credentials", "true");
            res.json(updatedListOfReviews);

        });

        app.post("/updateReview", (req, res) -> {
            Review updatedReview = this.theReviewRepository.update(Integer.parseInt(req.query("id")),
                    req.query("comment"), Integer.parseInt(req.query("rating")));
            res.append("Access-Control-Allow-Origin", "http://localhost:3000");
            res.append("Access-Control-Allow-Credentials", "true");
            res.json(updatedReview);
        });

        app.post("/removeReview", (req, res) -> {
            Review removedReview = this.theReviewRepository
                    .remove(this.theReviewRepository.findMostRecentForId(Integer.parseInt(req.query("id"))));
            res.append("Access-Control-Allow-Origin", "http://localhost:3000");
            res.append("Access-Control-Allow-Credentials", "true");
            res.json(removedReview);
        });
    }
}
