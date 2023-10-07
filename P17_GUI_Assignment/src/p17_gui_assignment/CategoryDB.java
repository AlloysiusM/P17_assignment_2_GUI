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
import java.util.ArrayList;
import java.util.List;

public class CategoryDB {

    // Method to create the categories table if it does not exist
    public void createCategoriesTable() {
        try (Connection connection = DB_Manager.getConnection()) {
            System.out.println("Connection established successfully."); // Add this line for debugging
            // existing code for table creation
        } catch (SQLException e) {
            System.err.println("Error creating categories table: " + e.getMessage());
            e.printStackTrace(); // Print the full stack trace for debugging
        }
    }

    // Method to insert a category into the categories table
    public void insertCategory(Category category) {
        // Check if the category with the given ID already exists
        if (getCategoryById(category.getId()) != null) {
            System.out.println("Category with ID " + category.getId() + " already exists. Skipping insertion.");
            return;
        }

        String insertCategorySQL = "INSERT INTO categories (id, name) VALUES (?, ?)";
        try (Connection connection = DB_Manager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertCategorySQL)) {
            preparedStatement.setInt(1, category.getId());
            preparedStatement.setString(2, category.getName());
            preparedStatement.executeUpdate();
            System.out.println("Category with ID " + category.getId() + " inserted successfully.");
        } catch (SQLException e) {
            System.err.println("Error inserting category: " + e.getMessage());
        }
    }

    public Category getCategoryById(int categoryId) {
        String getCategorySQL = "SELECT * FROM categories WHERE id = ?";
        try (Connection connection = DB_Manager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(getCategorySQL)) {
            preparedStatement.setInt(1, categoryId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String categoryName = resultSet.getString("name");
                    return new Category(categoryId, categoryName);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting category by ID: " + e.getMessage());
        }
        return null; // Category not found
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String getAllCategoriesSQL = "SELECT * FROM categories";

        try (Connection connection = DB_Manager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(getAllCategoriesSQL); ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Category category = new Category(id, name);
                categories.add(category);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all categories: " + e.getMessage());
        }
        return categories;
    }

    public Category getCategoryByName(String categoryName) {
        String getCategorySQL = "SELECT * FROM categories WHERE name = ?";
        try (Connection connection = DB_Manager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(getCategorySQL)) {
            preparedStatement.setString(1, categoryName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int categoryId = resultSet.getInt("id");
                    return new Category(categoryId, categoryName);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting category by name: " + e.getMessage());
        }
        return null; // Category not found
    }
}
