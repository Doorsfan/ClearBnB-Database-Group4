package com.company.application;

import com.company.MySQL;
import com.company.domain.*;
import com.company.infrastructure.*;
import express.Express;
import jakarta.persistence.*;

import java.sql.*;

public class Application {
    Connection con;
    Express app = new Express();

    public Application() {

        app.listen(4000);

        con = MySQL.INSTANCE.getConnection();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ClearBnB");

        // CONSIDER Do we need separate managers for different DOs?
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        UserHandler userHandler = new UserHandler(app, entityManager);
        try{
            entityManager.close();
            entityManagerFactory.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
