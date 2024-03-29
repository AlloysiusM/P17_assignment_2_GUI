/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p17_gui_assignment;

/**
 *
 * @author Albrent Manlutac
 */
class Category {

    private int id;
    private String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters 
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    // Return name
    @Override
    public String toString() {
        return name;
    }
}
