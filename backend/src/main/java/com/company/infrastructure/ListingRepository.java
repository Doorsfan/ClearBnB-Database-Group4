package com.company.infrastructure;

import com.company.domain.Listing;
import com.company.domain.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class ListingRepository {
    private EntityManager entityManager;

    public ListingRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Listing findMostRecentForId(Integer listingId) {
        return entityManager.createQuery("SELECT l FROM Listing l WHERE l.listingId = :listingId " +
                "ORDER BY l.version DESC", Listing.class)
                .setParameter("listingId", listingId)
                .setMaxResults(1)
                .getSingleResult();
    }

    public List<Listing> findAllForId(Integer listingId) {
        return entityManager.createQuery("SELECT l FROM Listing l WHERE l.listingId = :listingId", Listing.class)
                .setParameter("listingId", listingId)
                .getResultList();
    }

    public List<Listing> findAllForOwner(User owner) {
        return entityManager.createQuery("SELECT l FROM Listing l WHERE l.owner = :owner", Listing.class)
                .setParameter("owner", owner)
                .getResultList();
    }

    public List<Listing> findAll() {
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
                          Integer numberGuests, Double price, Date listingStartDate,
                          Date listingEndDate) {
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
