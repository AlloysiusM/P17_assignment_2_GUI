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

// Handles database operations related to categories in the online shopping system.
public class CategoryDB {

    // Method to create the categories table if it does not exist
    public void createCategoriesTable() {
        try (Connection connection = DB_Manager.getConnection()) {
            System.out.println("Connection established successfully."); // Debug line
        } catch (SQLException e) {
            System.err.println("Error creating categories table: " + e.getMessage());
            e.printStackTrace(); // Print the full stack trace for debugging
        }
    }

    // Method to insert a category into the categories table
    public void insertCategory(Category category) {
        // Check if the category with the given ID already exists
        Category existingCategory = getCategoryById(category.getId());
        if (existingCategory != null) {
            // Update the existing category with the new name
            existingCategory.setName(category.getName());
            updateCategory(existingCategory);
            System.out.println("Category with ID " + category.getId() + " updated successfully.");
            return; // Return after updating the existing category
        }

        // Insert the new category
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

    // Method to update an existing category
    public void updateCategory(Category category) {
        String updateCategorySQL = "UPDATE categories SET name = ? WHERE id = ?";
        try (Connection connection = DB_Manager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(updateCategorySQL)) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.setInt(2, category.getId());
            preparedStatement.executeUpdate(); // Execute the update query to update the category in the database
            System.out.println("Category with ID " + category.getId() + " updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating category: " + e.getMessage());
        }
    }

    // Method to clear all categories from the database
    public void clearCategories() {
        String clearCategoriesSQL = "DELETE FROM categories";
        try (Connection connection = DB_Manager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(clearCategoriesSQL)) {
            preparedStatement.executeUpdate();
            System.out.println("All categories cleared from the database.");
        } catch (SQLException e) {
            System.err.println("Error clearing categories: " + e.getMessage());
        }
    }

    // Method to get a category by its ID
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

    // Method to get a list of all categories
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

    // Method to get a category by its name
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
