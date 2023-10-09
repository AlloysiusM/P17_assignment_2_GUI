package p17_gui_assignment;

import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Albrent Manlutac
 */
public class FrontPage {

    public static void main(String[] args) throws SQLException {
        // Create an instance of the Login class
        Login loginFrame = new Login();

        // Set it as visible
        loginFrame.setVisible(true);

        // Create database tables and insert initial data
        CategoryDB categoryDB = new CategoryDB();
        ItemDB itemDB = new ItemDB(categoryDB); // Pass the categoryDB instance to the ItemDB constructor

        categoryDB.createCategoriesTable();
        itemDB.createItemsTable();

        // Create categories
        Category electronics = new Category(1, "Electronics");
        Category clothing = new Category(2, "Clothing");

        // Insert categories into the database
        categoryDB.insertCategory(electronics);
        categoryDB.insertCategory(clothing);

        // Create items
        Item laptop = new Item("12345", "Laptop", 799.99, "High-performance laptop", electronics) {
        };
        Item smartphone = new Item("67890", "Smartphone", 499.99, "Flagship smartphone", electronics) {
        };
        Item shirt = new Item("54321", "T-Shirt", 19.99, "Cotton T-Shirt", clothing) {
        };
        Item jeans = new Item("98765", "Jeans", 39.99, "Blue Jeans", clothing) {
        };

        // Insert items into the database
        itemDB.insertItem(laptop);
        itemDB.insertItem(smartphone);
        itemDB.insertItem(shirt);
        itemDB.insertItem(jeans);
    }
}