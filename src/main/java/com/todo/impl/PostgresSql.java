package com.todo.impl;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class PostgresSql{
    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "postgres", "postgres");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        } finally {
            return connection;
        }

    }

}
