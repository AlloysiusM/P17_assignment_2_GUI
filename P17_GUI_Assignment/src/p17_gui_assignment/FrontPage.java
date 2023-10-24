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

        // Electronics items
        Item laptop = new Item("12345", "Laptop", 799.99, "High-performance laptop", electronics) {
        };
        Item smartphone = new Item("67890", "Smartphone", 499.99, "Flagship smartphone", electronics) {
        };
        Item tablet = new Item("13579", "Tablet", 349.99, "Lightweight tablet", electronics) {
        };
        Item camera = new Item("24680", "Digital Camera", 279.99, "20MP digital camera", electronics) {
        };
        Item wirelessEarbuds = new Item("24681", "Wireless Earbuds", 89.99, "With noise cancellation", electronics) {
        };
        Item gamingConsole = new Item("24682", "Gaming Console", 299.99, "4K support", electronics) {
        };

        // Clothing items
        Item shirt = new Item("54321", "T-Shirt", 19.99, "Cotton T-Shirt", clothing) {
        };
        Item jeans = new Item("98765", "Jeans", 39.99, "Blue Jeans", clothing) {
        };
        Item dressShoes = new Item("11223", "Dress Shoes", 79.99, "For formal occasions", clothing) {
        };
        Item hoodie = new Item("998877", "Hoodie", 29.99, "Casual wear", clothing) {
        };
        Item winterCoat = new Item("998878", "Winter Coat", 129.99, "Warm winter coat with faux fur lining", clothing) {
        };
        Item formalSuit = new Item("998879", "Formal Suit", 199.99, "Tailored formal suit for special events", clothing) {
        };

        // Insert items into the database
        // Electronics
        itemDB.insertItem(laptop);
        itemDB.insertItem(smartphone);
        itemDB.insertItem(tablet);
        itemDB.insertItem(camera);
        itemDB.insertItem(wirelessEarbuds);
        itemDB.insertItem(gamingConsole);

        // Clothing
        itemDB.insertItem(shirt);
        itemDB.insertItem(jeans);
        itemDB.insertItem(dressShoes);
        itemDB.insertItem(hoodie);
        itemDB.insertItem(winterCoat);
        itemDB.insertItem(formalSuit);
    }
}
