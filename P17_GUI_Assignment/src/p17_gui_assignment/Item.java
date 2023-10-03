/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p17_gui_assignment;

/**
 *
 * @author Albrent Manlutac
 */
public abstract class Item {

    private String id;
    private String name;
    private double price;
    private String productInfo;
    private Category category;

    public Item(String id, String name, double price, String productInfo, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.productInfo = productInfo;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public Category getCategory() {
        return category;
    }

}
