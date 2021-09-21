package com.company.application;

import com.company.MySQL;
import com.company.Repositories.ListingRepository;
import com.company.domain.*;
import com.company.infrastructure.*;
import express.Express;
import jakarta.persistence.*;

import java.sql.*;

public class Application {
    Connection con;

    public Application() {
        Express app = new Express();

        app.listen(4000);

        con = MySQL.INSTANCE.getConnection();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ClearBnB");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        ListingRepository listingRepository = new ListingRepository(entityManager);

        /*** Testing ListingRepository methods ***/

        System.out.println(listingRepository.findAllForId(1));


        /*** end test ***/

        // Close everything after program completes
        entityManager.close();
        entityManagerFactory.close();

        try{
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
