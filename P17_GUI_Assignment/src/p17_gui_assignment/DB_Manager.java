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
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Manager {

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                String dbURL = "jdbc:derby://localhost:1527/OSS_DB";
                connection = DriverManager.getConnection(dbURL);
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle any database connection errors here
            }
        }
        return connection;
    }
}
