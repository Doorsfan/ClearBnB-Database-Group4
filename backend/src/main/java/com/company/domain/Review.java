package com.company.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@IdClass(ReviewCompositeId.class)
@Table(name = "Review")
public class Review implements Cloneable {
    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long reviewId;
    @Id
    private Integer version;
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

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
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
                ", version=" + version +
                ", timestamp=" + timestamp +
                ", author=" + author.getUserId() +
                ", target=" + target.getUserId() +
                ", comment='" + comment + '\'' +
                ", rating=" + rating +
                '}';
    }

    @Override
    public Review clone() {
        Review review = new Review();
        review.setReviewId(this.getReviewId());
        review.setVersion(this.getVersion());
        review.setTimestamp((this.getTimestamp()));
        review.setAuthor(this.getAuthor());
        review.setTarget(this.getTarget());
        review.setComment(this.getComment());
        review.setRating(this.getRating());
        return review;
    }
}
