package com.company.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "Review")
public class Review implements Cloneable, Serializable {
    @Id
    @Column(name = "reviewId")
    @GenericGenerator(name="temp", strategy = "increment")
    @GeneratedValue(generator="temp")
    private Integer reviewId;
    @Id
    private Integer version;
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    @OneToOne
    @JoinColumn(name="author_id")
    private User author;
    @JoinColumn(name="reviewsUserIdOf")
    private Integer reviewsUserIdOf;
    private String comment;
    private Integer rating;
    private Integer postedToListingId;

    public Integer getPostedToListingId() {
        return postedToListingId;
    }

    public void setPostedToListingId(Integer postedToListingId) {
        this.postedToListingId = postedToListingId;
    }

    public Review() {}

    public Integer getReviewId() {
        return reviewId;
    }

    public Integer getRefersToListingId() {
        return postedToListingId;
    }

    public void setRefersToListingId(Integer postedToListingId) {
        this.postedToListingId = postedToListingId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setTimestamp(String myWantedTime) {
        Instant instant = Instant.parse(myWantedTime);
        ZoneId z = ZoneId.of("Europe/Paris");
        LocalDateTime convertedTime = LocalDateTime.ofInstant(instant,z);
        this.timestamp = convertedTime;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }



    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getReviewsUserIdOf() {
        return reviewsUserIdOf;
    }

    public void setReviewsUserIdOf(Integer reviewsUserIdOf) {
        this.reviewsUserIdOf = reviewsUserIdOf;
    }


    @Override
    public Review clone() {
        Review review = new Review();
        review.setReviewId(this.getReviewId());
        review.setVersion(this.getVersion());
        review.setTimestamp((this.getTimestamp()));
        review.setAuthor(this.getAuthor());
        review.setReviewsUserIdOf(this.getReviewsUserIdOf());
        review.setComment(this.getComment());
        review.setRating(this.getRating());
        review.setRefersToListingId(this.getRefersToListingId());
        return review;
    }
}
