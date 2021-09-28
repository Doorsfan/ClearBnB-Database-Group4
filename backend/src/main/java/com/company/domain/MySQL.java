package com.company.domain;

import java.sql.*;


public enum MySQL {
    INSTANCE;
    Connection con;

    MySQL() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ClearBnB","root","root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return con;
    }
}
