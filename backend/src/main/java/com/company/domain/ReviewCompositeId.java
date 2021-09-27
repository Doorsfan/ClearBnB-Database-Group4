package com.company.domain;

import java.io.Serializable;
import java.util.Objects;

public class ReviewCompositeId implements Serializable {
    private Integer ReviewId;
    private Integer version;

    public ReviewCompositeId() { }

    public ReviewCompositeId(Integer ReviewId, Integer version) {
        this.ReviewId = ReviewId;
        this.version = version;
    }

    public Integer getReviewId() {
        return ReviewId;
    }

    public void setReviewId(Integer ReviewId) {
        this.ReviewId = ReviewId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public boolean equals(ReviewCompositeId ReviewCompositeId) {
        return Objects.equals(this.getReviewId(), ReviewCompositeId.getReviewId()) &&
                Objects.equals(this.getVersion(), ReviewCompositeId.getVersion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getReviewId(), this.getVersion());
    }
}

