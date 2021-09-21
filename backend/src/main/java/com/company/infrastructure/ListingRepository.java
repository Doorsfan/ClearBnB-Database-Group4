package com.company.infrastructure;

import com.company.domain.Listing;
import com.company.domain.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class ListingRepository {
    private EntityManager entityManager;

    public ListingRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Listing findMostRecentForId(Integer listingId) {
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

    public List<Listing> findAllMostRecentForOwner(User owner) {
        return entityManager.createQuery("SELECT l FROM Listing l WHERE l.owner = :owner", Listing.class)
                .setParameter("owner", owner)
                .getResultList();
    }

    public List<Listing> findAllMostRecent() {
        return entityManager.createQuery("from Listing").getResultList();
    }

    public Listing save(Listing listing) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(listing);
            entityManager.getTransaction().commit();
            return listing;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Listing update(Integer id, String title, String description, String imageUrl, String location,
                          Integer numberGuests, Double price, LocalDate listingStartDate,
                          LocalDate listingEndDate) {
        Listing listing = this.findMostRecentForId(id).clone();
        listing.setVersion(listing.getVersion() + 1);
        listing.setAuditedDatetime(LocalDateTime.now());

        if (title != null) {
            listing.setTitle(title);
        }
        if (description != null) {
            listing.setDescription(description);
        }
        if (imageUrl != null) {
            listing.setImageUrl(imageUrl);
        }
        if (location != null) {
            listing.setLocation(location);
        }
        if (numberGuests != null) {
            listing.setNumberGuests(numberGuests);
        }
        if (price != null) {
            listing.setPrice(price);
        }
        if (listingStartDate != null) {
            listing.setListingStartDate(listingStartDate);
        }
        if (listingEndDate != null) {
            listing.setListingEndDate(listingEndDate);
        }

        return this.save(listing);
    }

    public Listing remove(Listing listing) {
        Listing nullListing = this.update(listing.getListingId(), null, null, null, null,
                null, null, null, null);
        nullListing.setListingStartDate(null);
        nullListing.setListingEndDate(null);
        return this.save(nullListing);
    }
}
