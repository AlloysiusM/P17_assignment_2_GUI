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

    public double getTotal() {
        return total;
    }

    public int getCartSize() {
        return cartListModel.size();
    }

    public UsersCart() {
        cartListModel = new DefaultListModel<>();
        total = 0;
    }

    public void addToCart(int productId, String productName, double productPrice, String productInfo) {
        // Add the selected product details to the cartListModel
        String item = productId + ": " + productName + " - $" + productPrice;
        cartListModel.addElement(item);

        // Update the total price when adding a new item
        total += productPrice;
    }

    public DefaultListModel<String> getCartListModel() {
        return cartListModel;
    }

    public void removeFromCart(int index) {
        if (index >= 0 && index < cartListModel.size()) {
            // Get the item from the cartListModel
            String item = cartListModel.getElementAt(index);

            // Extract the price of the item being removed and subtract it from the total
            double price = Double.parseDouble(item.split(" - \\$")[1]);
            total -= price;

            // Remove the item from the cartListModel
            cartListModel.removeElementAt(index);
        }
    }

    public void clearCart() {
        cartListModel.clear();
        total = 0;
    }
}
