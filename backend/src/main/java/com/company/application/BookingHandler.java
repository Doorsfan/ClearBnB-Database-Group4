package com.company.application;

import com.company.domain.Booking;
import com.company.infrastructure.BookingRepository;
import express.Express;

public class BookingHandler {

    private final Express app;
    private final BookingRepository theBookingRepository;

    public BookingHandler(Express app, BookingRepository theBookingRepository){
        this.app = app;
        this.theBookingRepository = theBookingRepository;
        initBookingHandler();
    }

    private void initBookingHandler() {
        app.post("/booking", (req, res) -> {
            Booking newBooking = req.body(Booking.class);
            res.append("Access-Control-Allow-Origin", "http://localhost:3000");
            res.append("Access-Control-Allow-Credentials", "true");

            this.theBookingRepository.save(newBooking);
            res.json("Made a booking!");
        });
    }
}
