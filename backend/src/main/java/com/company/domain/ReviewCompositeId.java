package com.company.domain;

import java.io.Serializable;
import java.util.Objects;

public class ReviewCompositeId implements Serializable {
    private Integer reviewId;
    private Integer version;

    public ReviewCompositeId() { }

    public ReviewCompositeId(Integer listingId, Integer version) {
        this.reviewId = listingId;
        this.version = version;
    }

    public Integer getReviewId() {
        return reviewId;
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

    public boolean equals(ReviewCompositeId reviewCompositeId) {
        return Objects.equals(this.getReviewId(), reviewCompositeId.getReviewId()) &&
                Objects.equals(this.getVersion(), reviewCompositeId.getVersion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getReviewId(), this.getVersion());
    }
}
