package com.company.Repositories;

import com.company.Entities.Listing;
import jakarta.persistence.*;
import java.util.*;

public class ListingRepository {
    private EntityManager entityManager;

    public ListingRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Listing findById(Integer id) {
        Listing listing = entityManager.find(Listing.class, id);
        return listing;
    }
}
