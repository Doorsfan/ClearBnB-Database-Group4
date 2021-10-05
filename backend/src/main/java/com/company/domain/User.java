package com.company.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "User")
public class User {
    @Id
    @Column(name = "userId")
    @GenericGenerator(name="temp", strategy = "increment")
    @GeneratedValue(generator="temp")
    @Expose
    private Integer userId;
    @Expose
    private String username;
    @Expose
    @JsonIgnore
    private String password;
    @Expose
    private String email;
    @Expose
    private Double balance;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Listing> listings = new ArrayList<>();
    @OneToMany(mappedBy = "bookedByUser", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Review> authorOfReviews = new ArrayList<>();
    @OneToMany(mappedBy = "reviewsUserIdOf", cascade = CascadeType.ALL)
    private List<Review> targetOfReviews = new ArrayList<>();
    @OneToMany(mappedBy = "writtenByUser", cascade = CascadeType.ALL)
    private List<Message> writtenMessages = new ArrayList<>();
    @OneToMany(mappedBy = "recipientUser", cascade = CascadeType.ALL)
    private List<Message> receivedMessages = new ArrayList<>();

    public User() {}

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @JsonIgnore
    public List<Listing> getListings() {
        return listings;
    }

    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }

    @JsonIgnore
    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @JsonIgnore
    public List<Review> getAuthorOfReviews() {
        return authorOfReviews;
    }

    public void setAuthorOfReviews(List<Review> authorOfReviews) {
        this.authorOfReviews = authorOfReviews;
    }

    @JsonIgnore
    public List<Review> getTargetOfReviews() {
        return targetOfReviews;
    }

    public void setTargetOfReviews(List<Review> targetOfReviews) {
        this.targetOfReviews = targetOfReviews;
    }

    @JsonIgnore
    public List<Message> getWrittenMessages() {
        return writtenMessages;
    }

    public void setWrittenMessages(List<Message> writtenMessages) {
        this.writtenMessages = writtenMessages;
    }

    @JsonIgnore
    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                ", listings=" + listings +
                ", bookings=" + bookings +
                ", authorOfReviews=" + authorOfReviews +
                ", targetOfReviews=" + targetOfReviews +
                ", writtenMessages=" + writtenMessages +
                ", receivedMessages=" + receivedMessages +
                '}';
    }
}
