package p17_gui_assignment;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Albrent Manlutac
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginHandler {
    // JDBC URL, username, and password for Apache Derby
    private static final String URL = "jdbc:derby://localhost:1527/OSS_DB";
    private static final String USER = "admin17";
    private static final String PASSWORD = "admin";

    // JDBC variables for managing the database connection
    private Connection connection;

    public LoginHandler() {
        try {
            // Initialize the database connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any exceptions here
        }
    }

    public boolean login(String email, String password) {
        // SQL query to check the user's credentials
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            
            // Check if any rows were returned; if so, login is successful
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any exceptions here
            return false;
        }
    }

    public boolean register(String email, String password) {
        // SQL query to insert a new user into the 'users' table
        String sql = "INSERT INTO users (email, password) VALUES (?, ?)";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            int rowsInserted = preparedStatement.executeUpdate();
            
            // Check if the registration was successful
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any exceptions here
            return false;
        }
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any exceptions here
        }
    }
}
