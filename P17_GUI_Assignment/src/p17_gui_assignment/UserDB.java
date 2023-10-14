/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p17_gui_assignment;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Albrent Manlutac
 */
public class UserDB {

    public void createUsersTable() {
        try {
            Connection connection = DB_Manager.getConnection();

            // Check if the table already exists
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "USERS", null);

            if (!tables.next()) {
                // Table does not exist, so create it
                String createTableSQL = "CREATE TABLE users ("
                        + "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                        + "first_name VARCHAR(255),"
                        + "last_name VARCHAR(255),"
                        + "email VARCHAR(255),"
                        + "password VARCHAR(255))";

                // Execute the SQL statement
                connection.createStatement().execute(createTableSQL);
            }

            // Close the result set
            tables.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL errors here
        }
    }

    public void insertUserData(String firstName, String lastName, String email, String password) {
        try {
            Connection connection = DB_Manager.getConnection();
            // Define the SQL statement for inserting user data
            String insertDataSQL = "INSERT INTO users (first_name, last_name, email, password) "
                    + "VALUES (?, ?, ?, ?)";

            // Create a prepared statement and set the parameters
            PreparedStatement preparedStatement = connection.prepareStatement(insertDataSQL);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);

            // Execute the SQL statement
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL errors here
        }
    }
}
