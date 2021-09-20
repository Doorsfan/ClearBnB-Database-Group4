package com.company;

import express.Express;
import jakarta.persistence.*;

import java.sql.*;

public class Application {
    Connection con;

    Application() {
        Express app = new Express();

        app.listen(4000);

        con = MySQL.INSTANCE.getConnection();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ClearBnB");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        System.out.println(entityManager.createQuery("from User").getResultList());

        entityManager.close();
        entityManagerFactory.close();

        try{
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
