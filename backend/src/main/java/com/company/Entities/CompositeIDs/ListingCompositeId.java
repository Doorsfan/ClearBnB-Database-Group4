package com.company.Entities.CompositeIDs;

import java.io.Serializable;
import java.util.Objects;

public class ListingCompositeId implements Serializable {
    private Integer listingId;
    private Integer version;

    public ListingCompositeId() { }

    public ListingCompositeId(Integer listingId, Integer version) {
        this.listingId = listingId;
        this.version = version;
    }

    public Integer getListingId() {
        return listingId;
    }

    public void setListingId(Integer listingId) {
        this.listingId = listingId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public boolean equals(ListingCompositeId listingCompositeId) {
        return Objects.equals(this.getListingId(), listingCompositeId.getListingId()) &&
                Objects.equals(this.getVersion(), listingCompositeId.getVersion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getListingId(), this.getVersion());
    }
}
