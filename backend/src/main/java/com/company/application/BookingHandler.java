package com.company.application;

import com.company.domain.Booking;
import com.company.domain.Listing;
import com.company.domain.User;
import com.company.infrastructure.BookingRepository;
import com.company.infrastructure.ListingRepository;
import com.company.infrastructure.UserCacheRepository;
import com.company.infrastructure.UserRepository;
import express.Express;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingHandler {

    private final Express app;
    private final BookingRepository theBookingRepository;
    private final ListingRepository theListingRepository;
    private final UserRepository theUserRepository;
    private final UserCacheRepository theUserCacheRepository;

    public BookingHandler(Express app,
                          BookingRepository theBookingRepository,
                          ListingRepository theListingRepository,
                          UserRepository theUserRepository,
                          UserCacheRepository theUserCacheRepository){
        this.app = app;
        this.theBookingRepository = theBookingRepository;
        this.theListingRepository = theListingRepository;
        this.theUserRepository = theUserRepository;
        this.theUserCacheRepository = theUserCacheRepository;
        initBookingHandler();
    }

    boolean isWithinRange(Date dateToCheck, Date startBoundary, Date endBoundary) {
        return startBoundary.compareTo(dateToCheck) * dateToCheck.compareTo(endBoundary) > 0;
    }

    private void initBookingHandler() {
        app.post("/booking", (req, res) -> {
            Booking newBooking = req.body(Booking.class);
            System.out.println(newBooking);
            User personWhoBooked = this.theUserRepository.findById(newBooking.getBookedByUser().getUserId());
            boolean insufficientFunds = false;
            boolean taken = false;
            boolean before = false;
            boolean after = false;
            System.out.println(personWhoBooked.getBalance());
            System.out.println(newBooking.getAmountPaid());
            res.append("Access-Control-Allow-Origin", "http://localhost:3000");
            res.append("Access-Control-Allow-Credentials", "true");
            if(personWhoBooked.getBalance() < newBooking.getAmountPaid()){
                insufficientFunds = true;
            }
            if(insufficientFunds){
                res.json("Due to insufficient funds, the booking failed.");
                return;
            }

            List<Listing> myListing = this.theListingRepository
                    .findAllForId(newBooking.getListingBooked());
            List<Listing> allVersionsBooked = this.theListingRepository.findAllBasedOnId(myListing.get(0).getOriginalListingId());
            System.out.println(allVersionsBooked.size());
            System.out.println("STEP 1");
            List<Booking> theRelevantBookings = new ArrayList<Booking>();

            for(int z = allVersionsBooked.size()-1; z > -1; z--){
                List<Booking> currentBookingList =
                        this.theBookingRepository.findForListing(allVersionsBooked.get(z));
                for(int a = currentBookingList.size()-1; a > -1; a--){
                    theRelevantBookings.add(currentBookingList.get(a));
                }
            }
            ArrayList<Integer> takenDays = new ArrayList<Integer>();
            for(int i = theRelevantBookings.size()-1; i > -1; i--){

                int startPurged = Integer.parseInt(newBooking.
                        getBookingStartDate().replaceAll("-", ""));
                int endPurged = Integer.parseInt(newBooking
                        .getBookingEndDate().replaceAll("-", ""));

                int purgedEndBooking = Integer.parseInt(theRelevantBookings.get(i).getBookingEndDate().
                        replaceAll("-", ""));
                int purgedStartBooking = Integer.parseInt(theRelevantBookings.get(i).
                        getBookingStartDate().
                        replaceAll("-", ""));
                for(int e = purgedEndBooking; e > purgedStartBooking; e--){
                    if(!takenDays.contains(e)){
                        takenDays.add(e);
                    }
                }
                for(int z = endPurged; z > startPurged; z--){
                    if(takenDays.contains(z)){
                        taken = true;
                    }
                }

            }

            if(!insufficientFunds){
                if(!taken){
                    personWhoBooked.setBalance(personWhoBooked.getBalance() - newBooking.getAmountPaid());
                    this.theBookingRepository.save(newBooking);
                    theUserRepository.update(newBooking.getBookedByUser().getUserId(),
                            null, null, null, personWhoBooked.getBalance());
                    theUserCacheRepository.remove("username-" + personWhoBooked.getUsername());
                    UserDTO myUserDTO = new UserDTO();
                    myUserDTO.setUserId(personWhoBooked.getUserId());
                    myUserDTO.setUsername(personWhoBooked.getUsername());
                    myUserDTO.setBalance(personWhoBooked.getBalance());
                    myUserDTO.setEmail(personWhoBooked.getEmail());
                    res.json(myUserDTO);
                }
                else{
                    res.json("Failed to make a booking, dates were taken!");
                }
            }
            else{
                res.json("Due to insufficient funds, the booking failed.");
            }
        });
    }
}
