package com.company.application;

import com.company.domain.Listing;
import com.company.domain.Review;
import com.company.domain.User;
import com.company.infrastructure.ReviewRepository;
import com.company.infrastructure.UserRepository;
import express.Express;

public class ReviewHandler {

    private final Express app;
    private final ReviewRepository theReviewRepository;

    public ReviewHandler(Express app, ReviewRepository theReviewRepository){
        this.app = app;
        this.theReviewRepository = theReviewRepository;
        initReviewHandler();
    }

    private void initReviewHandler() {
        app.post("/review", (req, res) -> {
            Listing myListing = req.body(Listing.class);
            res.append("Access-Control-Allow-Origin", "http://localhost:3000");
            res.append("Access-Control-Allow-Credentials", "true");

            this.theReviewRepository.findAll();
        });
    }
}
