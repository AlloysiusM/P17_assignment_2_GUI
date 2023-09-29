/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p17_gui_assignment;

/**
 *
 * @author Albrent Manlutac
 */
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CategoryDB {

    // Method to create the categories table if it does not exist
    public void createCategoriesTable() {
        try {
            Connection connection = DB_Manager.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "CATEGORIES", null);

            if (!resultSet.next()) {
                String createTableSQL = "CREATE TABLE categories (id INT PRIMARY KEY, name VARCHAR(255))";
                try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) {
                    preparedStatement.executeUpdate();
                }
            }

            resultSet.close();
        } catch (SQLException e) {
            System.err.println("Error creating categories table: " + e.getMessage());
        }
    }

    // Method to insert a category into the categories table
    public void insertCategory(Category category) {
        createCategoriesTable(); // Ensure the table exists before inserting
        String insertCategorySQL = "INSERT INTO categories (id, name) VALUES (?, ?)";
        try (Connection connection = DB_Manager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertCategorySQL)) {
            preparedStatement.setInt(1, category.getId());
            preparedStatement.setString(2, category.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting category: " + e.getMessage());
        }
    }
}
