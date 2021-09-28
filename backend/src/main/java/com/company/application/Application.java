package com.company.application;

import com.company.domain.MySQL;
import express.Express;
import io.javalin.core.JavalinConfig;
import jakarta.persistence.*;

import java.sql.*;

public class Application {
    Connection con;
    Express app = new Express(JavalinConfig::enableCorsForAllOrigins);

    public Application() {
        app.listen(4000);

        con = MySQL.INSTANCE.getConnection();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ClearBnB");

        // CONSIDER Do we need separate managers for different DOs?
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        UserHandler userHandler = new UserHandler(app, entityManager);
        try{
        System.out.println("Finished registering API");
            //entityManagerFactory.close();
            //con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
