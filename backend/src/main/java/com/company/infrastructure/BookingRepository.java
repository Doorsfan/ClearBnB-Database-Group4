package com.company.infrastructure;

import com.company.domain.Booking;
import com.company.domain.Listing;
import com.company.domain.User;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

public class BookingRepository {
    private EntityManager entityManager;

    public BookingRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Booking findById(Integer id) {
        return entityManager.find(Booking.class, id);
    }

    public List<Booking> findForUser(User user) {
        return entityManager.createQuery("SELECT b FROM Booking b WHERE b.bookedByUser = :user", Booking.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<Booking> findForListing(Listing listing) {
        return entityManager.createQuery("SELECT b FROM Booking b WHERE b.listingBooked = :listing", Booking.class)
                .setParameter("listing", listing.getListingId())
                .getResultList();
    }

    public List<Booking> findAll() {
        return entityManager.createQuery("from Booking").getResultList();
    }

    public Booking save(Booking booking) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(booking);
            entityManager.getTransaction().commit();
            return booking;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Booking update(Integer id, Double amountPaid, LocalDate bookingStartDate, LocalDate bookingEndDate) {
        Booking booking = this.findById(id);
        try {
            entityManager.getTransaction().begin();
            if (amountPaid != null) {
                booking.setAmountPaid(amountPaid);
            }
            if (bookingStartDate != null) {
                booking.setBookingStartDate(bookingStartDate.toString());
            }
            if (bookingEndDate != null) {
                booking.setBookingEndDate(bookingEndDate.toString());
            }
            entityManager.getTransaction().commit();
            return booking;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Booking remove(Integer id) {
        Booking booking = this.findById(id);
        try {
            entityManager.getTransaction().begin();
            booking.setCancelled(true);
            entityManager.getTransaction().commit();
            return booking;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
