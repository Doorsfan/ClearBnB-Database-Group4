package com.company.infrastructure;

import com.company.domain.Listing;
import com.company.domain.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public List<Listing> findFilteredListings(String location, int numberGuests, double price,
                                              String listingStartDate, String listingEndDate){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tenYearsAhead = now.plusYears(10);

        return entityManager.createQuery("SELECT l FROM Listing l WHERE l.location " +
                "LIKE :location AND :numberGuests <= l.numberGuests" +
                " AND :price >= l.price" +
                " AND :listingStartDate = l.listingStartDate" +
                " AND :listingEndDate = l.listingEndDate")
                .setParameter("location", "%" + location + "%")
                .setParameter("numberGuests", (numberGuests < 1 ? 1 : numberGuests))
                .setParameter("price", (price == 0 ? 100000.0 : price))
                .setParameter("listingStartDate", (listingStartDate.length() == 0 ? dtf.format(now): listingStartDate))
                .setParameter("listingEndDate", (listingEndDate.length() == 0 ? dtf.format(tenYearsAhead) : listingEndDate))
                .getResultList();
        //" AND l.numberGuests >= :numberGuests " +
        //                "AND l.price <= :price AND l.listingStartDate >= :listingStartDate " +
        //                "AND l.listingEndDate <= :listingEndDate"
        //.setParameter("numberGuests", numberGuests)
        //                .setParameter("price", price)
        //                .setParameter("listingStartDate", ((listingStartDate.length() == 0) ? LocalDate.now().toString() : listingStartDate))
        //                .setParameter("listingEndDate", ((listingEndDate.length() == 0) ? LocalDate.now().toString() : listingEndDate))
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

    public Integer findSpecifiedListing(String title, String description, String imageUrl, String location,
                                        Integer numberGuests, Double price, String listingStartDate,
                                        String listingEndDate){
        List<Listing> result = entityManager.createQuery("SELECT l FROM Listing l WHERE l.title = :title AND " +
                "l.description = :description AND l.imageUrl = :imageUrl AND l.location = :location AND " +
                "l.numberGuests = :numberGuests AND l.price = :price AND l.listingStartDate = :listingStartDate AND " +
                "l.listingEndDate = :listingEndDate")
                .setParameter("title", title)
                .setParameter("description", description)
                .setParameter("imageUrl", imageUrl)
                .setParameter("location", location)
                .setParameter("numberGuests", numberGuests)
                .setParameter("price", price)
                .setParameter("listingStartDate", listingStartDate)
                .setParameter("listingEndDate", listingEndDate)
                .getResultList();
        if(result.size() > 0){
            return result.get(0).getListingId();
        }
        return -1;
    }


    public Listing update(Integer id, String title, String description, String imageUrl, String location,
                          Integer numberGuests, Double price, LocalDate listingStartDate,
                          LocalDate listingEndDate) {
        Listing listing = this.findMostRecentForId(id).clone();
        listing.setVersion(listing.getVersion() + 1);
        listing.setAuditedDatetime(LocalDateTime.now().toString());

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
            listing.setListingStartDate(listingStartDate.toString());
        }
        if (listingEndDate != null) {
            listing.setListingEndDate(listingEndDate.toString());
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
