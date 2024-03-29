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

public class ItemDB {

    private CategoryDB categoryDB;

    // Initialize CategoryDB
    public ItemDB(CategoryDB categoryDB) {
        this.categoryDB = categoryDB;
    }

    // Method to create the items table if it does not exist
    public void createItemsTable() {
        try {
            Connection connection = DB_Manager.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "ITEMS", null);

            // Check if the table already exists
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
                System.out.println("Items table created successfully.");
            } else {
                System.out.println("Items table already exists.");
            }

            resultSet.close();
        } catch (SQLException e) {
            System.err.println("Error creating or checking items table: " + e.getMessage());
        }
    }

    // Method to insert an item into the items table
    public void insertItem(Item item) {
        createItemsTable(); // Ensure the table exists before inserting

        // Check if the category with the given ID already exists
        Category category = item.getCategory();
        // Check if the item with the same ID already exists
        if (getItemById(item.getId()) != null) {
            System.out.println("Item with ID " + item.getId() + " already exists. Ignoring insertion.");
            return;
        }

        String insertItemSQL = "INSERT INTO items (id, name, price, productInfo, category_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DB_Manager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertItemSQL)) {
            preparedStatement.setString(1, item.getId());
            preparedStatement.setString(2, item.getName());
            preparedStatement.setDouble(3, item.getPrice());
            preparedStatement.setString(4, item.getProductInfo());
            preparedStatement.setInt(5, category.getId()); // Assign correct category ID

            preparedStatement.executeUpdate();
            System.out.println("Item inserted successfully.");
        } catch (SQLException e) {
            System.err.println("Error inserting item: " + e.getMessage());
        }
    }

    // Method to get an item by its ID
    public Item getItemById(String itemId) {
        String getItemSQL = "SELECT * FROM items WHERE id = ?";
        try (Connection connection = DB_Manager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(getItemSQL)) {
            preparedStatement.setString(1, itemId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("name");
                    double price = resultSet.getDouble("price");
                    String productInfo = resultSet.getString("productInfo");
                    int categoryId = resultSet.getInt("category_id");

                    // Retrieve the category from the CategoryDB object
                    Category category = categoryDB.getCategoryById(categoryId);

                    // Create and return the item with the retrieved data
                    return new Item(id, name, price, productInfo, category) {
                    };
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting item by ID: " + e.getMessage());
        }
        return null; // Item not found
    }

    // Method to update an item in the items table
    public void updateItem(Item item) {
        String updateItemSQL = "UPDATE items SET name=?, price=?, productInfo=?, category_id=? WHERE id=?";
        try (Connection connection = DB_Manager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(updateItemSQL)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setDouble(2, item.getPrice());
            preparedStatement.setString(3, item.getProductInfo());
            preparedStatement.setInt(4, item.getCategory().getId());
            preparedStatement.setString(5, item.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating item: " + e.getMessage());
        }
    }

    // Method to delete an item from the items table by its ID
    public void deleteItem(String itemId) {
        String deleteItemSQL = "DELETE FROM items WHERE id=?";
        try (Connection connection = DB_Manager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(deleteItemSQL)) {
            preparedStatement.setString(1, itemId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting item: " + e.getMessage());
        }
    }

    // Method to retrieve a list of all items from the items table
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String selectAllItemsSQL = "SELECT * FROM items";
        try (Connection connection = DB_Manager.getConnection()) {
            connection.setAutoCommit(false); // Set autocommit to false

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectAllItemsSQL); ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    // Retrieve data from the ResultSet
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("name");
                    double price = resultSet.getDouble("price");
                    String productInfo = resultSet.getString("productInfo");
                    int categoryId = resultSet.getInt("category_id");

                    // Retrieve the category from the CategoryDB object using categoryId
                    Category category = categoryDB.getCategoryById(categoryId);

                    // Create the item with the retrieved data and category
                    Item item = new Item(id, name, price, productInfo, category) {
                    };
                    items.add(item);
                }

                connection.commit(); // Commit the transaction after retrieving data
            } catch (SQLException e) {
                connection.rollback(); // Rollback the transaction in case of error
                System.err.println("Error retrieving items: " + e.getMessage());
                e.printStackTrace();
            } finally {
                connection.setAutoCommit(true); // Set autocommit back to true after transaction is completed
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving items: " + e.getMessage());
            e.printStackTrace();
        }
        return items;
    }

    // Method to retrieve a list of items belonging to a specific category
    public List<Item> getItemsByCategory(Category category) {
        List<Item> items = new ArrayList<>();
        String selectItemsByCategorySQL = "SELECT * FROM items WHERE category_id = ?";
        try (Connection connection = DB_Manager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectItemsByCategorySQL)) {
            preparedStatement.setInt(1, category.getId()); // Pass the category ID to the SQL query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("name");
                    double price = resultSet.getDouble("price");
                    String productInfo = resultSet.getString("productInfo");

                    // Create the item with the retrieved data
                    Item item = new Item(id, name, price, productInfo, category) {
                    };
                    items.add(item);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving items by category: " + e.getMessage());
        }
        return items;
    }
}
