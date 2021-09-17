package com.company;

import express.Express;

import java.sql.*;

public class Application {
    Connection con;

    Application() {
        Express app = new Express();

        app.listen(4000);
        this.connectToDB();
        if (con == null) {
            System.out.println("Panic mode?! Handle graceful shutdown.");
        }

        doExampleQuery();
    }

    private void doExampleQuery() {
        try {
            int userID = 1;
            // Good habit: Never use non-prepared statement
            // Statement stmt = con.createStatement();
            // SQL-injection possible with the query below since we're concatenating strings
            // ResultSet rs=stmt.executeQuery("SELECT * FROM emp WHERE emp.id = " + userID);

            PreparedStatement pStatement = con.prepareStatement("SELECT id, name, date_created FROM users WHERE id = ?");
            pStatement.setInt(1, userID);
            ResultSet rs = pStatement.executeQuery();

            while(rs.next()) {
                // We must manually specify at which index and which datatypes each column in the result is.
                System.out.println(
                        rs.getInt(1)
                                + "  " +
                                rs.getString(2)
                                + "  " +
                                rs.getTimestamp(3)
                );
            }
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    private void connectToDB() {
        {
            try {
                con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/mydb","root","root");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
