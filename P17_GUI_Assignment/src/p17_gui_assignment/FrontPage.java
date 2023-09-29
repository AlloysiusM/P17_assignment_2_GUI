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
        
        // Create database tables and insert initial data
        CategoryDB categoryDB = new CategoryDB();
        ItemDB itemDB = new ItemDB();
        
        categoryDB.createCategoriesTable();
        itemDB.createItemsTable();
        
        // Create an instance of the Login class
        Login loginFrame = new Login();

        // Set it as visible
        loginFrame.setVisible(true);
    }
}
