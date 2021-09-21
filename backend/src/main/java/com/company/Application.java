package com.company;

import com.company.Entities.*;
import com.company.Repositories.*;
import express.Express;
import jakarta.persistence.*;

import java.net.UnknownServiceException;
import java.sql.*;

public class Application {
    Connection con;

    Application() {
        Express app = new Express();

        app.listen(4000);

        con = MySQL.INSTANCE.getConnection();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ClearBnB");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        ListingRepository listingRepository = new ListingRepository(entityManager);

        /*** Testing ListingRepository methods ***/

        System.out.println(listingRepository.findById(1));

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
