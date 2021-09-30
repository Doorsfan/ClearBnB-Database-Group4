package com.company.application;

import com.company.domain.Booking;
import com.company.infrastructure.BookingRepository;
import com.company.infrastructure.ListingRepository;
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

    public BookingHandler(Express app, BookingRepository theBookingRepository, ListingRepository theListingRepository){
        this.app = app;
        this.theBookingRepository = theBookingRepository;
        this.theListingRepository = theListingRepository;
        initBookingHandler();
    }

    boolean isWithinRange(Date dateToCheck, Date startBoundary, Date endBoundary) {
        return startBoundary.compareTo(dateToCheck) * dateToCheck.compareTo(endBoundary) > 0;
    }

    private void initBookingHandler() {
        app.post("/booking", (req, res) -> {
            Booking newBooking = req.body(Booking.class);
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
                this.theBookingRepository.save(newBooking);
                res.json(newBooking.getBookedByUser());
            }
            else{
                res.json("Failed to make a booking, dates were taken!");
            }

        });
    }
}
