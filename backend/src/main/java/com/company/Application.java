package com.company;

<<<<<<< Updated upstream:backend/src/main/java/com/company/Application.java
import com.company.Entities.*;
import com.company.Repositories.*;
=======
import com.company.domain.*;
>>>>>>> Stashed changes:backend/src/main/java/com/company/application/Application.java
import express.Express;
import jakarta.persistence.*;

import java.net.UnknownServiceException;
import java.sql.*;

public class Application {
    Connection con;
    Express app = new Express();

<<<<<<< Updated upstream:backend/src/main/java/com/company/Application.java
    Application() {
        Express app = new Express();
=======
    public Application() {
>>>>>>> Stashed changes:backend/src/main/java/com/company/application/Application.java

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
