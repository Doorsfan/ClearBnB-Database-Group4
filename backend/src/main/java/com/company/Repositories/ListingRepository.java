package com.company.Repositories;

import com.company.Entities.Listing;
import jakarta.persistence.*;
import java.util.*;

public class ListingRepository {
    private EntityManager entityManager;

    public ListingRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Listing findMostRecentById(Integer listingId) {
        List<Listing> listings = entityManager.createQuery("SELECT l FROM Listing l WHERE l.listingId = :listingId", Listing.class)
                .setParameter("listingId", listingId)
                .getResultList();

        if (listings.size() == 0) {
            return null;
        }

        Listing mostRecentListing = listings.get(0);

        for (int i = 1; i < listings.size(); i++) {
            if (listings.get(i).getVersion() > mostRecentListing.getVersion()) {
                mostRecentListing = listings.get(i);
            }
        }

        return mostRecentListing;
    }

    public List<Listing> findAllForId(Integer listingId) {
        return entityManager.createQuery("SELECT l FROM Listing l WHERE l.listingId = :listingId", Listing.class)
                .setParameter("listingId", listingId)
                .getResultList();
    }
}
