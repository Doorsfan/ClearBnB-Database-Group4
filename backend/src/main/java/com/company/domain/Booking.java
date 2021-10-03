package com.company.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Entity
@Table(name = "Booking")
public class Booking {
    @Id
    @Column(name = "bookingId")
    @GenericGenerator(name="temp", strategy = "increment")
    @GeneratedValue(generator="temp")
    private Integer bookingId;
    @Column(name = "listingBooked")
    private Integer listingBooked;
    @ManyToOne
    @JoinColumn(name="bookedByUser")
    private User bookedByUser;
    @Column(name = "amountPaid")
    private Double amountPaid;
    @Column(name = "bookingStartDate")
    private String bookingStartDate;
    @Column(name = "bookingEndDate")
    private String bookingEndDate;
    private Boolean cancelled;

    public Booking() { }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getListingBooked() {
        return listingBooked;
    }

    public void setListingBooked(Integer listingBooked) {
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

    public String getBookingStartDate() {
        return bookingStartDate;
    }

    public void setBookingStartDate(String bookingStartDate) {
        this.bookingStartDate = bookingStartDate;
    }

    public String getBookingEndDate() {
        return bookingEndDate;
    }

    public void setBookingEndDate(String bookingEndDate) {
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
