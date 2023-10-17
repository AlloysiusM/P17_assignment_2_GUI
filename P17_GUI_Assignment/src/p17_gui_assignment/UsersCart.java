/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p17_gui_assignment;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Albrent Manlutac
 */
public class UsersCart {

    private DefaultListModel<String> cartListModel;
    private double total;

    public UsersCart() {
        cartListModel = new DefaultListModel<>();
        total = 0;
    }

    // Adds a product to the cart and updates the total price
    public void addToCart(int productId, String productName, double productPrice, String productInfo) {
        // Format the product details and add them to the cartListModel
        String item = productId + ": " + productName + " - $" + productPrice;
        cartListModel.addElement(item);

        // Update the total price by adding the price of the added item
        total += productPrice;
    }

    // Returns the total price of items in the cart
    public double getTotal() {
        return total;
    }

    // Returns the number of items in the cart
    public int getCartSize() {
        return cartListModel.size();
    }

    // Returns the cartListModel containing cart items
    public DefaultListModel<String> getCartListModel() {
        return cartListModel;
    }

    // Removes an item from the cart by its index and updates the total price
    public void removeFromCart(int index) {
        if (index >= 0 && index < cartListModel.size()) {
            // Extract the price of the item being removed and subtract it from the total
            double price = Double.parseDouble(cartListModel.getElementAt(index).split(" - \\$")[1]);
            total -= price;

            // Remove the item from the cartListModel
            cartListModel.removeElementAt(index);
        }
    }

    // Clears all items from the cart and resets the total price to 0
    public void clearCart() {
        cartListModel.clear();
        total = 0;
    }
}
