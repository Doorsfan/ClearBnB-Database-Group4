package com.company;

import java.sql.*;

public enum MySQL {
    INSTANCE;
    Connection con;

    MySQL() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/clearBnB","root","root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return con;
    }
}
