package com.company.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "User")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer userId;
    private String username;
    private String password;
    private String email;
    private Double balance;
    /*
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Listing> listings = new ArrayList<>();
    @OneToMany(mappedBy = "bookedByUser", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();
    /*@OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    List<Review> authorOfReviews = new ArrayList<>();
    @OneToMany(mappedBy = "target", cascade = CascadeType.ALL)
    List<Review> targetOfReviews = new ArrayList<>();*/

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
    /*
    public List<Listing> getListings() {
        return listings;
    }

    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
    */

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                '}';
    }
}
