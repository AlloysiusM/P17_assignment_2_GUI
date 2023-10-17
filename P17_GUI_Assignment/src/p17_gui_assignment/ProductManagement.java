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

    // Initialize CategoryDB and ItemDB
    public ProductManagement(CategoryDB categoryDB, ItemDB itemDB) {
        this.categoryDB = categoryDB;
        this.itemDB = itemDB;
    }

    // Adds a new item to the system
    public void addNewItem(Item item) {
        itemDB.insertItem(item);
    }

    // Updates an existing item in the system
    public void updateItem(Item item) {
        itemDB.updateItem(item);
    }

    // Deletes an item from the system by its ID
    public void deleteItem(String itemId) {
        itemDB.deleteItem(itemId);
    }

    // Retrieves a list of all items in the system
    public List<Item> getAllItems() {
        return itemDB.getAllItems();
    }

    // Retrieves a list of items in a specific category
    public List<Item> getItemsByCategory(Category category) {
        return itemDB.getItemsByCategory(category);
    }
}
