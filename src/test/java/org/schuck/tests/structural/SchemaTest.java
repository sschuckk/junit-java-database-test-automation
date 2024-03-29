package org.schuck.tests.structural;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.schuck.database.MySQLConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SchemaTest {
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

    // Check table presence in database schema
    // SHOW TABLES;
    // Check if tables name is in the list
    @Test
    @DisplayName("TestOO1")
    void verifyTablePresence() throws SQLException {
        ResultSet resultSet = dbStatement.executeQuery("SHOW TABLES");
        List<String> actualTableNames = new ArrayList<>();

        while (resultSet.next()) {
            actualTableNames.add(resultSet.getString(1));
        }

        // Creates an immutable list containing the specified table names.
        List<String> expectedTableNames = List.of("customers", "employees", "offices", "orderdetails", "orders", "payments", "persons", "productlines", "products");

        // Verify whether the number of items returned in the query matches the schema.
        assertEquals(expectedTableNames.size(), actualTableNames.size(),"Number of tables retrieved does not match the expected number");

        // Iterate through the expected table names, and if any of them are not found in the list of actual table names,
        // fail the test with an error message indicating the missing table.
        for (String expectedTableName : expectedTableNames) {
            if (!actualTableNames.contains(expectedTableName)) {
                fail("Expected table '" + expectedTableName + "' not found");
            }
        }
    }

    // Check table name conventions

    // Check number of columns in a table

    // Check column names in a table

    // Check data type of columns in table

    // Check size of the columns in a table

    // Check nulls fields in a table

    // Check column keys in a table
}
