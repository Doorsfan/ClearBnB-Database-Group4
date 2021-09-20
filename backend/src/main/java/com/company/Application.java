package com.company;

import express.Express;

import java.sql.*;

public class Application {
    Connection con;

    Application() {
        Express app = new Express();

        app.listen(4000);

        con = MySQL.INSTANCE.getConnection();

        // Insert code here
        this.doExampleQuery();

        try{
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // This should be deleted when we actually add code
    private void doExampleQuery() {
        try {
            int userID = 1;
            // Good habit: Never use non-prepared statement
            // Statement stmt = con.createStatement();
            // SQL-injection possible with the query below since we're concatenating strings
            // ResultSet rs=stmt.executeQuery("SELECT * FROM emp WHERE emp.id = " + userID);

            PreparedStatement pStatement = con.prepareStatement("SELECT * FROM User");
            ResultSet rs = pStatement.executeQuery();

            while(rs.next()) {
                // We must manually specify at which index and which datatypes each column in the result is.
                System.out.println(
                        rs.getInt(1)
                                + "  " +
                                rs.getString(2)
                );
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
