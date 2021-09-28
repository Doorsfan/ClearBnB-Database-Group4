package com.company.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Booking")
public class Booking {
    @Id
    @Column(name = "booking_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long bookingId;
    @Column(name = "listing_booked")
    private Long listingBooked;
    @ManyToOne
    @JoinColumn(name="booked_by_user")
    private User bookedByUser;
    @Column(name = "amount_paid")
    private Double amountPaid;
    @Column(name = "booking_start_date")
    private LocalDate bookingStartDate;
    @Column(name = "booking_end_date")
    private LocalDate bookingEndDate;
    private Boolean cancelled;

    public Booking() { }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getListingBooked() {
        return listingBooked;
    }

    public void setListingBooked(Long listingBooked) {
        this.listingBooked = listingBooked;
    }

    public User getBookedByUser() {
        return bookedByUser;
    }

    public void setBookedByUser(User bookedByUser) {
        this.bookedByUser = bookedByUser;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public LocalDate getBookingStartDate() {
        return bookingStartDate;
    }

    public void setBookingStartDate(LocalDate bookingStartDate) {
        this.bookingStartDate = bookingStartDate;
    }

    public LocalDate getBookingEndDate() {
        return bookingEndDate;
    }

    public void setBookingEndDate(LocalDate bookingEndDate) {
        this.bookingEndDate = bookingEndDate;
    }

    public Boolean getCancelled() {
        return cancelled;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", listingBooked=" + listingBooked +
                ", bookedByUser=" + bookedByUser.getUserId() +
                ", amountPaid=" + amountPaid +
                ", bookingStartDate=" + bookingStartDate +
                ", bookingEndDate=" + bookingEndDate +
                ", cancelled=" + cancelled +
                '}';
    }
}
