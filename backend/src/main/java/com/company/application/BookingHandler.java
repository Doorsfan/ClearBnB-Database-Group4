package com.company.application;

import com.company.domain.Booking;
import com.company.domain.User;
import com.company.infrastructure.BookingRepository;
import com.company.infrastructure.ListingRepository;
import com.company.infrastructure.UserRepository;
import express.Express;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class BookingHandler {

    private final Express app;
    private final BookingRepository theBookingRepository;
    private final ListingRepository theListingRepository;
    private final UserRepository theUserRepository;

    public BookingHandler(Express app,
                          BookingRepository theBookingRepository,
                          ListingRepository theListingRepository,
                          UserRepository theUserRepository){
        this.app = app;
        this.theBookingRepository = theBookingRepository;
        this.theListingRepository = theListingRepository;
        this.theUserRepository = theUserRepository;
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

            List<Booking> myBookings = this.theBookingRepository.findForListing(
                    this.theListingRepository.findMostRecentForId(newBooking.getListingBooked()));
            boolean taken = false;
            int startPurged = Integer.parseInt(newBooking.getBookingStartDate().replaceAll("-", ""));
            int endPurged = Integer.parseInt(newBooking.getBookingEndDate().replaceAll("-", ""));
            for(Booking aBooking : myBookings){
                int purgedEndBooking = Integer.parseInt(aBooking.getBookingEndDate().
                        replaceAll("-", ""));
                int purgedStartBooking = Integer.parseInt(aBooking.getBookingStartDate().
                        replaceAll("-", ""));
                if(purgedStartBooking <= startPurged && purgedEndBooking >= endPurged){
                    taken = true;
                }
            }

            res.append("Access-Control-Allow-Origin", "http://localhost:3000");
            res.append("Access-Control-Allow-Credentials", "true");
            if(!taken){
                personWhoBooked.setBalance(personWhoBooked.getBalance() - newBooking.getAmountPaid());
                this.theBookingRepository.save(newBooking);
                theUserRepository.update(newBooking.getBookedByUser().getUserId(),
                        null, null, null, personWhoBooked.getBalance());

                res.json(personWhoBooked);
            }
            else{
                res.json("Failed to make a booking, dates were taken!");
            }

        });
    }
}
