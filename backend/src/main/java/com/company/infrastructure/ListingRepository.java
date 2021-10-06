package com.company.infrastructure;

import com.company.domain.Listing;
import com.company.domain.User;
import jakarta.persistence.*;
import net.bytebuddy.asm.Advice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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


        List<Listing> baseList = entityManager.createQuery("SELECT l FROM Listing l WHERE l.location " +
                "LIKE :location AND :numberGuests <= l.numberGuests" +
                " AND l.price <= :price ORDER BY l.version DESC LIMIT 1", Listing.class)
                .setParameter("location", "%" + location + "%")
                .setParameter("numberGuests", (numberGuests < 1 ? 1 : numberGuests))
                .setParameter("price", (price == 0 ? 100000.0 : price))
                .getResultList();
        if(listingStartDate == "" || listingEndDate == ""){
            return baseList;
        }
        for(int i = baseList.size()-1; i > -1; i--){
            if (Integer.parseInt(baseList.get(i).getListingStartDate().replaceAll("-", "")) <
                    Integer.parseInt(listingStartDate.replaceAll("-", ""))
                    &&
                    Integer.parseInt(baseList.get(i).getListingEndDate().replaceAll("-", "")) <
                            Integer.parseInt(listingEndDate.replaceAll("-", ""))
            ) {
                baseList.remove(baseList.get(i));
            }
        }
        return baseList;
        /*
        ArrayList<Listing> emptyList = new ArrayList<Listing>();
        if(listingStartDate == ""){
            listingStartDate = String.valueOf(LocalDateTime.now().getYear()) + '-' +
                    (LocalDateTime.now().getMonthValue() < 10 ? "0" + LocalDateTime.now().getMonthValue()
                            : LocalDateTime.now().getMonthValue()) + '-' +
                    (LocalDateTime.now().getDayOfMonth() < 10 ? "0" + LocalDateTime.now().getDayOfMonth()
                            : LocalDateTime.now().getDayOfMonth());
        }
        if(listingEndDate == ""){
            listingEndDate = String.valueOf(LocalDateTime.now().getYear()) + '-' +
                    (LocalDateTime.now().plusYears(10).getMonthValue()
                            < 10 ? "0" + LocalDateTime.now().plusYears(10).getMonthValue()
                            : LocalDateTime.now().plusYears(10).getMonthValue()) + '-' +
                    (LocalDateTime.now().plusYears(10).getDayOfMonth() < 10 ?
                            "0" + LocalDateTime.now().getDayOfMonth()
                            : LocalDateTime.now().getDayOfMonth());
        }
        System.out.println("IN THE BACKEND, THE EMPTYLIST WAS: ");

        ArrayList<Integer> alreadyCheckedIds = new ArrayList<Integer>();
        for(int i = baseList.size()-1; i > 0; i--){

            System.out.println(alreadyCheckedIds.toString());
            if(price >= (baseList.get(i).getPrice() * 1.15) &&
            (Integer.parseInt(
                    baseList.get(i).getListingStartDate()
                            .replaceAll("-", ""))
                    <= Integer.parseInt(listingStartDate.replaceAll("-", "")))
                    && (Integer.parseInt(baseList.get(i).getListingEndDate()
                    .replaceAll("-", "")) >=
                    Integer.parseInt(listingEndDate.replaceAll("-", "")))
            && !(alreadyCheckedIds.contains(baseList.get(i).getListingId()))
            && !(alreadyCheckedIds.contains(baseList.get(i).getOriginalListingId())))
            {
                emptyList.add(baseList.get(i));
                for(int e = 0; e < emptyList.size(); e++){
                    if(emptyList.get(e).getOriginalListingId() == baseList.get(i).getOriginalListingId()
                            && emptyList.get(e).getVersion() < baseList.get(i).getVersion()){
                        emptyList.set(e, baseList.get(i));
                    }
                }
            }
            alreadyCheckedIds.add(baseList.get(i).getListingId());
            alreadyCheckedIds.add(baseList.get(i).getOriginalListingId());

        }

        /*
        int lastIdChecked = 0;
        for(int e = emptyList.size()-1; e > 0; e--){
            if(lastIdChecked != 0){
                if(emptyList.get(e).getListingId() == lastIdChecked){
                    emptyList.remove(e);
                }
            }
            lastIdChecked = emptyList.get(e).getListingId();
        }
        return emptyList; */
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

    public List<Listing> findUniqueVersionOfAll(){
        List<Listing> result = entityManager
                .createQuery("SELECT l FROM Listing l ORDER BY l.version")
                .getResultList();
        return result;
    }

    public List<Listing> findAllBasedOnId(Integer id){
        List<Listing> result = entityManager
                .createQuery("SELECT l FROM Listing l WHERE l.originalListingId = :wantedListingId")
                .setParameter("wantedListingId", id)
                .getResultList();
        return result;
    }
    public Listing update(Integer id, String title, String description, String imageUrl, String location,
                          Integer numberGuests, Double price, LocalDate listingStartDate,
                          LocalDate listingEndDate) {
        Listing listing = this.findMostRecentForId(id).clone();
        listing.setVersion(listing.getVersion() + 1);
        listing.setAuditedDatetime(LocalDateTime.now().toString());
        listing.setOriginalListingId(id);

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
