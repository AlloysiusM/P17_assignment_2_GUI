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
    private static final String DB_URL = "jdbc:derby://localhost:1527/OSS_DB";
    private static final String DB_USER = "admin17";
    private static final String DB_PASSWORD = "admin";

    // Make DB connection
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Establish the database connection with username and password
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }
        return connection;
    }

}
