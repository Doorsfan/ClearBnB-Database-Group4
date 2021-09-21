/*package com.company.Entities;

import jakarta.persistence.*;
import java.time.*;

@Entity
@Table(name = "Review")
public class Review {
    @Id
    @Column(name = "review_id")
    private Integer reviewId;
    private LocalDateTime timestamp;
    @ManyToOne
    @JoinColumn(name="author_id")
    private User author;
    @ManyToOne
    @JoinColumn(name="target_id")
    private User target;
    private String comment;
    private Integer rating;

    public Review() {}

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
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

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", timestamp=" + timestamp +
                ", author=" + author +
                ", target=" + target +
                ", comment='" + comment + '\'' +
                ", rating=" + rating +
                '}';
    }
}*/
