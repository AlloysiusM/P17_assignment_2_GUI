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

public class ItemDB {

    // Method to create the items table if it does not exist
    public void createItemsTable() {
        try {
            Connection connection = DB_Manager.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "ITEMS", null);

            if (!resultSet.next()) {
                String createTableSQL = "CREATE TABLE items ("
                        + "id VARCHAR(255) PRIMARY KEY, "
                        + "name VARCHAR(255), "
                        + "price DOUBLE, "
                        + "productInfo VARCHAR(255), "
                        + "category_id INT)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) {
                    preparedStatement.executeUpdate();
                }
            }

            resultSet.close();
        } catch (SQLException e) {
            System.err.println("Error creating items table: " + e.getMessage());
        }
    }

    // Method to insert an item into the items table
    public void insertItem(Item item) {
        createItemsTable(); // Ensure the table exists before inserting
        String insertItemSQL = "INSERT INTO items (id, name, price, productInfo, category_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DB_Manager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertItemSQL)) {
            preparedStatement.setString(1, item.getId());
            preparedStatement.setString(2, item.getName());
            preparedStatement.setDouble(3, item.getPrice());
            preparedStatement.setString(4, item.getProductInfo());
            preparedStatement.setInt(5, item.getCategory().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting item: " + e.getMessage());
        }
    }
}
