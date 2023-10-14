/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p17_gui_assignment;

/**
 *
 * @author Albrent Manlutac
 */
import java.util.List;

public class ProductManagement {

    private CategoryDB categoryDB;
    private ItemDB itemDB;

    // Constructor to initialize CategoryDB and ItemDB
    public ProductManagement(CategoryDB categoryDB, ItemDB itemDB) {
        this.categoryDB = categoryDB;
        this.itemDB = itemDB;
    }

    public void addNewItem(Item item) {
        itemDB.insertItem(item);
    }

    public void updateItem(Item item) {
        // Assuming you have a method in ItemDB to update an item
        itemDB.updateItem(item);
    }

    public void deleteItem(String itemId) {
        // Assuming you have a method in ItemDB to delete an item by ID
        itemDB.deleteItem(itemId);
    }

    public List<Item> getAllItems() {
        // Assuming you have a method in ItemDB to retrieve all items\
        return itemDB.getAllItems();
    }

    public List<Item> getItemsByCategory(Category category) {
        // Assuming you have a method in ItemDB to retrieve items by category
        return itemDB.getItemsByCategory(category);
    }
}
