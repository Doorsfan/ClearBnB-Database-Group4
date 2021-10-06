package com.company.application;

import com.company.domain.Listing;
import com.company.domain.Review;
import com.company.domain.User;
import com.company.infrastructure.BookingRepository;
import com.company.infrastructure.ListingRepository;
import com.company.infrastructure.ReviewRepository;
import com.company.infrastructure.UserRepository;
import express.Express;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ListingHandler {

    private final Express app;
    private final ListingRepository theListingRepository;
    private final ReviewRepository theReviewRepository;
    private final UserRepository theUserRepository;

    public ListingHandler(Express app, ListingRepository theListingRepository, ReviewRepository theReviewRepository,
                          UserRepository theUserRepository){
        this.app = app;
        this.theListingRepository = theListingRepository;
        this.theReviewRepository = theReviewRepository;
        this.theUserRepository = theUserRepository;
        initListingHandler();
    }
    private void initListingHandler() {

        app.post("/updateLease", (req, res) -> {
            List<User> ownerList = this.theUserRepository.findByUsername(req.query("username"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
            res.append("Access-Control-Allow-Origin", "*");
            res.append("Access-Control-Allow-Credentials", "true");
            Listing myListing = req.body(Listing.class);
            myListing.setAuditedDatetime(LocalDateTime.now().toString());
            myListing.setLocation(req.query("location"));
            List <Listing> foundListings =
                    this.theListingRepository.findAllForId(Integer.parseInt(
                            req.query("listingId")));
            List <Listing> allVersions =
                    this.theListingRepository.findAllBasedOnId(foundListings.get(0).getOriginalListingId());
            System.out.println("THE LISTING ID WAS: " + req.query("listingId"));
            myListing.setOwner(ownerList.get(0));
            myListing.setVersion(allVersions.get(allVersions.size()-1).getVersion() + 1);
            myListing.setNumberGuests(Integer.parseInt(req.query("numberGuests")));
            myListing.setOriginalListingId(
                    allVersions.get(0).getOriginalListingId()
            );
            System.out.println(req.query());
            this.theListingRepository.save(myListing);

            List <Listing> latestSetOfVersions =
                    this.theListingRepository.findAllBasedOnId(foundListings.get(0).getOriginalListingId());

            List<ListingDTO> myListOfDTOs = new ArrayList<ListingDTO>();
            for(Listing foundListing : foundListings){
                ListingDTO myListingDTO = new ListingDTO();
                myListingDTO.setOwner(foundListing.getOwner());
                myListingDTO.setListingId(foundListing.getListingId());
                myListingDTO.setVersion(foundListing.getVersion());
                myListingDTO.setAuditedDateTime(foundListing.getAuditedDatetime());
                myListingDTO.setOwnerId(foundListing.getOwner().getUserId());
                myListingDTO.setTitle(foundListing.getTitle());
                myListingDTO.setDescription(foundListing.getDescription());
                myListingDTO.setImageUrl(foundListing.getImageUrl());
                myListingDTO.setLocation(foundListing.getLocation());
                myListingDTO.setNumberGuests(foundListing.getNumberGuests());
                myListingDTO.setPrice(foundListing.getPrice());
                myListingDTO.setListingStartDate(foundListing.getListingStartDate());
                myListingDTO.setListingEndDate(foundListing.getListingEndDate());
                myListOfDTOs.add(myListingDTO);
            }


            res.json(latestSetOfVersions);
        });

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
                    myListing.setOriginalListingId(count);
                    changedId = true;
                    break;
                }
                count += 1;
            }
            if(!changedId){
                myListing.setListingId(count);
                myListing.setOriginalListingId(count);
            }
            this.theListingRepository.save(myListing);
            res.json("Made a new listing!");
        });

        app.get("/getResultsFromFiltering", (req, res) -> {
            res.append("Access-Control-Allow-Origin", "*");

            List<Listing> filteredListings = this.theListingRepository.findFilteredListings(req.query("location"),
                    Integer.parseInt(req.query("numberGuests").length() == 0
                            ? String.valueOf(1) : req.query("numberGuests")),
                    (req.query("myPrice").length() == 0 ? 100000.0 :
                    Double.parseDouble(req.query("myPrice"))),
                    req.query("myMinDate"), req.query("myMaxDate"));

            res.json(filteredListings);
        });

        app.post("/getAllListings", (req, res) -> {
            List<Listing> allListings = this.theListingRepository.findAll();
            ArrayList<Listing> emptyListing = new ArrayList<Listing>();
            ArrayList<Integer> alreadyCoveredIds = new ArrayList<Integer>();
            ArrayList<Listing> laterVersions = new ArrayList<Listing>();
            for(int i = 0; i < allListings.size(); i++){
                System.out.println(allListings.get(i).getOriginalListingId());
                if(!alreadyCoveredIds.contains(allListings.get(i).getOriginalListingId())){
                    alreadyCoveredIds.add(allListings.get(i).getOriginalListingId());
                    emptyListing.add(allListings.get(i));
                    System.out.println("Added id of: " + i);
                }
                else{
                    laterVersions.add(allListings.get(i));
                }
            }
            for(int i = 0; i < emptyListing.size(); i++){
                for(int e = 0; e < laterVersions.size(); e++) {
                    if(emptyListing.get(i).getOriginalListingId() == laterVersions.get(e).getOriginalListingId()
                    && emptyListing.get(i).getVersion() < laterVersions.get(e).getVersion()){
                        emptyListing.set(i, laterVersions.get(e));
                    }
                }
            }
            System.out.println("Added a total of: " + alreadyCoveredIds.size() + " items");
            List<Listing> allMyListings = this.theListingRepository.findAll();


            res.append("Access-Control-Allow-Origin", "http://localhost:3000");
            res.append("Access-Control-Allow-Credentials", "true");
            res.json(allMyListings);
        });

        app.post("/getAllVersionsOfListing", (req, res) -> {
                    List<Listing> myListingList = this.theListingRepository.findAllForId(
                            Integer.parseInt(req.query("listingId")));
                    System.out.println(myListingList.get(0).getOriginalListingId());
                    List<Listing> allRelevantListings = this.theListingRepository.findAllBasedOnId(
                            myListingList.get(0).getOriginalListingId()
                    );
                    res.append("Access-Control-Allow-Origin", "http://localhost:3000");
                    res.append("Access-Control-Allow-Credentials", "true");
                    res.json(allRelevantListings);
                });

            app.get("/getListingsByOwner", (req, res) -> {
                List<User> myWantedUsers = this.theUserRepository.findByUsername(req.query("username"));

                if (myWantedUsers.size() > 0) {
                    ArrayList<Listing> relevantListings = new ArrayList<Listing>();
                    ArrayList<Integer> alreadyCoveredIds = new ArrayList<Integer>();
                    ArrayList<Listing> laterVersions = new ArrayList<Listing>();

                    List<Listing> findAllForOwner = this.theListingRepository.findAllForOwner(myWantedUsers.get(0));
                    for(int i = 0; i < findAllForOwner.size(); i++){
                        System.out.println(findAllForOwner.get(i).getOriginalListingId());
                        if(!alreadyCoveredIds.contains(findAllForOwner.get(i).getOriginalListingId())){
                            alreadyCoveredIds.add(findAllForOwner.get(i).getOriginalListingId());
                            relevantListings.add(findAllForOwner.get(i));
                            System.out.println("Added id of: " + i);
                        }
                        else{
                            laterVersions.add(findAllForOwner.get(i));
                        }
                    }
                    for(int i = 0; i < findAllForOwner.size(); i++){
                        for(int e = 0; e < laterVersions.size(); e++) {
                            if(findAllForOwner.get(i).getOriginalListingId() == laterVersions.get(e).getOriginalListingId()
                                    && findAllForOwner.get(i).getVersion() < laterVersions.get(e).getVersion()){
                                relevantListings.set(i, laterVersions.get(e));
                            }
                        }
                    }
                    res.append("Access-Control-Allow-Origin", "http://localhost:3000");
                    res.append("Access-Control-Allow-Credentials", "true");
                    List<ListingDTO> allListingDTOs = new ArrayList<ListingDTO>();
                    for(Listing foundListing : findAllForOwner){
                        ListingDTO myListingDTO = new ListingDTO();
                        myListingDTO.setOwner(foundListing.getOwner());
                        myListingDTO.setListingId(foundListing.getListingId());
                        myListingDTO.setVersion(foundListing.getVersion());
                        myListingDTO.setAuditedDateTime(foundListing.getAuditedDatetime());
                        myListingDTO.setOwnerId(foundListing.getOwner().getUserId());
                        myListingDTO.setTitle(foundListing.getTitle());
                        myListingDTO.setDescription(foundListing.getDescription());
                        myListingDTO.setImageUrl(foundListing.getImageUrl());
                        myListingDTO.setLocation(foundListing.getLocation());
                        myListingDTO.setNumberGuests(foundListing.getNumberGuests());
                        myListingDTO.setPrice(foundListing.getPrice());
                        myListingDTO.setListingStartDate(foundListing.getListingStartDate());
                        myListingDTO.setListingEndDate(foundListing.getListingEndDate());
                        allListingDTOs.add(myListingDTO);
                    }

                    res.json(relevantListings);
                } else {
                    res.append("Access-Control-Allow-Origin", "http://localhost:3000");
                    res.append("Access-Control-Allow-Credentials", "true");
                    res.json("No user was found.");
                }
            });

    }
}
