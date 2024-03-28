package org.schuck.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schuck.database.MySQLConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class MySQLTest {
    private Connection connection;
    private Statement dbStatement;

    @BeforeEach
    void setUp() {
        try {
            // Create a SQL connection
            connection = MySQLConnector.connect();
            dbStatement = connection.createStatement();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    void testDatabaseConnection() throws SQLException {
        ResultSet resultSet = dbStatement.executeQuery("SELECT * FROM customers");
        while (resultSet.next()) {
            // Assuming you have three columns: col1, col2, col3
            String col1 = resultSet.getString("customerName");

            // Print the values of each column for the current row
            System.out.println("Customer Name: " + col1);
        }
    }

    @Test
    void printSome() {
        System.out.println("EEEENNNNNDDDDD");
    }
}