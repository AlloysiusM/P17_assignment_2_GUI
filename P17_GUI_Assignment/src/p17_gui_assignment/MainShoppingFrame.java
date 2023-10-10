/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package p17_gui_assignment;

import java.awt.CardLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Albrent Manlutac
 */
public class MainShoppingFrame extends javax.swing.JFrame {

    private CardLayout cardLayout;
    private ItemDB itemDB;
    private UsersCart usersCart;
    private String userEmail;

    /**
     * Creates new form MainShoppingFrame
     */
    public MainShoppingFrame(String userEmail) {
        initComponents();
        initializeFrame(userEmail);
    }

    private void initializeFrame(String userEmail) {
        setSize(900, 500);
        setResizable(false);

        this.userEmail = userEmail;
        userLabel.setText("User: " + userEmail);
        cardLayout = (CardLayout) layoutCard.getLayout();
        cardLayout.show(layoutCard, "ProfilePanel");

        String firstName = getUserFirstNameFromDatabase(userEmail);
        String lastName = getUserLastNameFromDatabase(userEmail);
        firstNameLabel.setText("First Name: " + firstName);
        lastNameLabel.setText("Last Name: " + lastName);
        emailLabel.setText("Email: " + userEmail);

        loadCategoriesIntoComboBox();

        CategoryDB categoryDB = new CategoryDB();
        itemDB = new ItemDB(categoryDB);
        usersCart = new UsersCart();

        String[] columnNames = {"ID", "Name", "Price", "Info", "Category"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        allProductsTable.setModel(model);
        populateItemsTable();

        DefaultListModel<String> listModel = new DefaultListModel<>();
        ShoppingList.setModel(listModel);
    }

    private void loadCategoriesIntoComboBox() {
        CategoryDB categoryDB = new CategoryDB();
        List<Category> categories = categoryDB.getAllCategories();

        // Create a list of category names
        List<String> categoryNames = new ArrayList<>();
        for (Category category : categories) {
            categoryNames.add(category.getName());
        }

        // Create a DefaultComboBoxModel with category names
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(categoryNames.toArray(String[]::new));

        // Set the ComboBoxModel to the JComboBox
        jComboBox1.setModel(comboBoxModel);

        // Check if categoryNames is not empty before setting the selected index
        if (!categoryNames.isEmpty()) {
            // Set the default selected index to 0
            jComboBox1.setSelectedIndex(0);

            // Trigger the action performed event to populate the items table for the default category
            jComboBox1ActionPerformed(null);
        } else {
            // Handle the case where categoryNames is empty, e.g., show a message or disable controls.
            // You can add your error-handling logic here.
        }
    }

    private void populateItemsTable() {
        DefaultTableModel model = (DefaultTableModel) allProductsTable.getModel();
        model.setRowCount(0);

        try (Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/OSS_DB", "admin17", "admin"); PreparedStatement statement = connection.prepareStatement("SELECT * FROM ITEMS"); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                double price = resultSet.getDouble("PRICE");
                String productInfo = resultSet.getString("PRODUCTINFO");
                String categoryName = resultSet.getString("CATEGORY_ID");

                Object[] rowData = {id, name, price, productInfo, categoryName};
                model.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any database errors here
        }
    }

    private void populateItemsTableByCategory(int categoryId) {
        DefaultTableModel model = (DefaultTableModel) categoryTable.getModel();
        model.setRowCount(0);

        try (Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/OSS_DB", "admin17", "admin"); PreparedStatement statement = connection.prepareStatement("SELECT * FROM ITEMS WHERE CATEGORY_ID = ?")) {
            if (categoryId != -1) {
                statement.setInt(1, categoryId);
            }
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                double price = resultSet.getDouble("PRICE");
                String productInfo = resultSet.getString("PRODUCTINFO");
                int category = resultSet.getInt("CATEGORY_ID");

                Object[] rowData = {id, name, price, productInfo, category};
                model.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any database errors here
        }
    }

    private String getUserFirstNameFromDatabase(String email) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish a database connection (you should have your database URL, user, and password here)
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/OSS_DB", "admin17", "admin");

            // Define the SQL query to retrieve the user's first name based on email
            String query = "SELECT FIRST_NAME FROM ADMIN17.USERS WHERE email = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, email);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("first_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any database errors here
        } finally {
            // Close database resources properly in a finally block
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Return a default value or handle the case where the user's first name is not found
        return "Unknown";
    }

    private String getUserLastNameFromDatabase(String email) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish a database connection (you should have your database URL, user, and password here)
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/OSS_DB", "admin17", "admin");

            // Define the SQL query to retrieve the user's last name based on email
            String query = "SELECT LAST_NAME FROM ADMIN17.USERS WHERE email = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, email);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("LAST_NAME");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any database errors here
        } finally {
            // Close database resources properly in a finally block
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Return a default value or handle the case where the user's last name is not found
        return "Unknown";
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        profileBtn = new javax.swing.JButton();
        allProductsBtn = new javax.swing.JButton();
        categoryBtn = new javax.swing.JButton();
        cartBtn = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        userLabel = new javax.swing.JLabel();
        logOutBtn = new javax.swing.JButton();
        layoutCard = new javax.swing.JPanel();
        allProductsPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        allProductsTable = new javax.swing.JTable();
        productAddBtn = new javax.swing.JButton();
        profilePanel = new javax.swing.JPanel();
        firstNameLabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        lastNameLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JTextField();
        detailsField = new javax.swing.JTextField();
        changePasswordBtn = new java.awt.Button();
        Catgory = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        categoryTable = new javax.swing.JTable();
        categoryAddBtn = new javax.swing.JButton();
        shoppingCart = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        ShoppingList = new javax.swing.JList<>();
        removeItemBtn = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        totalCartText = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 500));

        jPanel2.setBackground(new java.awt.Color(101, 157, 189));
        jPanel2.setPreferredSize(new java.awt.Dimension(200, 500));

        jPanel3.setForeground(new java.awt.Color(60, 63, 65));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        profileBtn.setBackground(new java.awt.Color(255, 255, 255));
        profileBtn.setForeground(new java.awt.Color(60, 63, 65));
        profileBtn.setText("Profile");
        profileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profileBtnActionPerformed(evt);
            }
        });

        allProductsBtn.setBackground(new java.awt.Color(255, 255, 255));
        allProductsBtn.setForeground(new java.awt.Color(60, 63, 65));
        allProductsBtn.setText("All products");
        allProductsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allProductsBtnActionPerformed(evt);
            }
        });

        categoryBtn.setBackground(new java.awt.Color(255, 255, 255));
        categoryBtn.setForeground(new java.awt.Color(60, 63, 65));
        categoryBtn.setText("Catgories");
        categoryBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryBtnActionPerformed(evt);
            }
        });

        cartBtn.setBackground(new java.awt.Color(255, 255, 255));
        cartBtn.setForeground(new java.awt.Color(60, 63, 65));
        cartBtn.setText("Shopping Cart");
        cartBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cartBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(allProductsBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(categoryBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cartBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE))
                    .addComponent(profileBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(profileBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(allProductsBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(categoryBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(cartBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(175, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(101, 157, 189));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(60, 63, 65), 3));

        userLabel.setForeground(new java.awt.Color(60, 63, 65));
        userLabel.setText("User");

        logOutBtn.setBackground(new java.awt.Color(255, 255, 255));
        logOutBtn.setForeground(new java.awt.Color(255, 153, 153));
        logOutBtn.setText("Logout");
        logOutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(logOutBtn)
                .addGap(18, 18, 18)
                .addComponent(userLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(357, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userLabel)
                    .addComponent(logOutBtn))
                .addContainerGap())
        );

        layoutCard.setLayout(new java.awt.CardLayout());

        allProductsPanel.setBackground(new java.awt.Color(101, 157, 189));
        allProductsPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jScrollPane1.setBorder(null);
        jScrollPane1.setForeground(new java.awt.Color(255, 255, 255));

        allProductsTable.setBackground(new java.awt.Color(255, 255, 255));
        allProductsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Price", "Info", "Category"
            }
        ));
        allProductsTable.setGridColor(new java.awt.Color(255, 255, 255));
        allProductsTable.setShowGrid(false);
        allProductsTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(allProductsTable);

        productAddBtn.setBackground(new java.awt.Color(255, 255, 255));
        productAddBtn.setForeground(new java.awt.Color(60, 63, 65));
        productAddBtn.setText("Add to Cart");
        productAddBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productAddBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout allProductsPanelLayout = new javax.swing.GroupLayout(allProductsPanel);
        allProductsPanel.setLayout(allProductsPanelLayout);
        allProductsPanelLayout.setHorizontalGroup(
            allProductsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(allProductsPanelLayout.createSequentialGroup()
                .addGroup(allProductsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(allProductsPanelLayout.createSequentialGroup()
                        .addGap(524, 524, 524)
                        .addComponent(productAddBtn))
                    .addGroup(allProductsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 609, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        allProductsPanelLayout.setVerticalGroup(
            allProductsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(allProductsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(productAddBtn)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        layoutCard.add(allProductsPanel, "card3");

        profilePanel.setBackground(new java.awt.Color(101, 157, 189));
        profilePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        firstNameLabel.setFont(new java.awt.Font("Microsoft Himalaya", 0, 24)); // NOI18N
        firstNameLabel.setForeground(new java.awt.Color(60, 63, 65));
        firstNameLabel.setText("FirstName");

        emailLabel.setFont(new java.awt.Font("Microsoft Himalaya", 0, 24)); // NOI18N
        emailLabel.setForeground(new java.awt.Color(60, 63, 65));
        emailLabel.setText("Email");

        lastNameLabel.setFont(new java.awt.Font("Microsoft Himalaya", 0, 24)); // NOI18N
        lastNameLabel.setForeground(new java.awt.Color(60, 63, 65));
        lastNameLabel.setText("LastName");

        passwordField.setEditable(false);
        passwordField.setBackground(new java.awt.Color(101, 157, 189));
        passwordField.setFont(new java.awt.Font("Microsoft Himalaya", 0, 24)); // NOI18N
        passwordField.setForeground(new java.awt.Color(60, 63, 65));
        passwordField.setText("Password:");
        passwordField.setBorder(null);
        passwordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordFieldActionPerformed(evt);
            }
        });

        detailsField.setBackground(new java.awt.Color(101, 157, 189));
        detailsField.setFont(new java.awt.Font("Microsoft Himalaya", 0, 24)); // NOI18N
        detailsField.setForeground(new java.awt.Color(60, 63, 65));
        detailsField.setText("My Details");
        detailsField.setBorder(null);
        detailsField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detailsFieldActionPerformed(evt);
            }
        });

        changePasswordBtn.setActionCommand("Change Password");
        changePasswordBtn.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        changePasswordBtn.setLabel("Change Password");

        javax.swing.GroupLayout profilePanelLayout = new javax.swing.GroupLayout(profilePanel);
        profilePanel.setLayout(profilePanelLayout);
        profilePanelLayout.setHorizontalGroup(
            profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profilePanelLayout.createSequentialGroup()
                .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(profilePanelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(detailsField, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(firstNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lastNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(emailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(profilePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(changePasswordBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(90, Short.MAX_VALUE))
        );
        profilePanelLayout.setVerticalGroup(
            profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profilePanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(detailsField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(firstNameLabel)
                .addGap(18, 18, 18)
                .addComponent(lastNameLabel)
                .addGap(18, 18, 18)
                .addComponent(emailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(changePasswordBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(94, Short.MAX_VALUE))
        );

        layoutCard.add(profilePanel, "card2");
        profilePanel.getAccessibleContext().setAccessibleName("purple");

        Catgory.setBackground(new java.awt.Color(101, 157, 189));
        Catgory.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jComboBox1.setBackground(new java.awt.Color(255, 255, 255));
        jComboBox1.setForeground(new java.awt.Color(60, 63, 65));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        categoryTable.setAutoCreateRowSorter(true);
        categoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Price", "Info", "Category"
            }
        ));
        categoryTable.setGridColor(new java.awt.Color(255, 255, 255));
        categoryTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(categoryTable);

        categoryAddBtn.setBackground(new java.awt.Color(255, 255, 255));
        categoryAddBtn.setForeground(new java.awt.Color(60, 63, 65));
        categoryAddBtn.setText("Add to Cart");
        categoryAddBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryAddBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CatgoryLayout = new javax.swing.GroupLayout(Catgory);
        Catgory.setLayout(CatgoryLayout);
        CatgoryLayout.setHorizontalGroup(
            CatgoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CatgoryLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CatgoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CatgoryLayout.createSequentialGroup()
                        .addGroup(CatgoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(categoryAddBtn))
                        .addGap(89, 89, 89))
                    .addGroup(CatgoryLayout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(622, Short.MAX_VALUE))))
        );
        CatgoryLayout.setVerticalGroup(
            CatgoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CatgoryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(categoryAddBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layoutCard.add(Catgory, "card4");

        shoppingCart.setBackground(new java.awt.Color(101, 157, 189));
        shoppingCart.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ShoppingList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(ShoppingList);

        removeItemBtn.setBackground(new java.awt.Color(255, 255, 255));
        removeItemBtn.setForeground(new java.awt.Color(60, 63, 65));
        removeItemBtn.setText("Remove");
        removeItemBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeItemBtnActionPerformed(evt);
            }
        });

        totalCartText.setEditable(false);
        jScrollPane5.setViewportView(totalCartText);

        jLabel1.setFont(new java.awt.Font("Microsoft Himalaya", 0, 24)); // NOI18N
        jLabel1.setText("Total:");

        javax.swing.GroupLayout shoppingCartLayout = new javax.swing.GroupLayout(shoppingCart);
        shoppingCart.setLayout(shoppingCartLayout);
        shoppingCartLayout.setHorizontalGroup(
            shoppingCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(shoppingCartLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(shoppingCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(removeItemBtn)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(271, Short.MAX_VALUE))
        );
        shoppingCartLayout.setVerticalGroup(
            shoppingCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(shoppingCartLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(shoppingCartLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(removeItemBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        layoutCard.add(shoppingCart, "card5");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(layoutCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(layoutCard, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 924, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void profileBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profileBtnActionPerformed
        cardLayout.show(layoutCard, "card2");
    }//GEN-LAST:event_profileBtnActionPerformed

    private void logOutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOutBtnActionPerformed
        // Dispose of the current MainShoppingFrame
        this.dispose();

        // Create and display a new Login frame
        Login loginFrame = new Login();
        loginFrame.setVisible(true);
    }//GEN-LAST:event_logOutBtnActionPerformed

    private void allProductsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allProductsBtnActionPerformed
        cardLayout.show(layoutCard, "card3");
    }//GEN-LAST:event_allProductsBtnActionPerformed

    private void categoryBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryBtnActionPerformed
        cardLayout.show(layoutCard, "card4");
    }//GEN-LAST:event_categoryBtnActionPerformed

    private void cartBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cartBtnActionPerformed
        cardLayout.show(layoutCard, "card5");
    }//GEN-LAST:event_cartBtnActionPerformed

    private void addProductToCart(int productId, String productName, double productPrice, String productInfo) {
        // Assuming usersCart is an instance of your UsersCart class
        usersCart.addToCart(productId, productName, productPrice, productInfo);

        // Update the JList model with the new cart items
        ShoppingList.setModel(usersCart.getCartListModel());

        // Show a message to inform the user that the product has been added to the cart
        JOptionPane.showMessageDialog(this, "Product added to cart: " + productName);

        // Update the total label with the new total value
        double total = usersCart.getTotal();
        totalCartText.setText("Total: $" + String.format("%.2f", total));
    }

    private void removeItemBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeItemBtnActionPerformed
        int selectedIndex = ShoppingList.getSelectedIndex();
        if (selectedIndex != -1) {
            // Assuming usersCart is an instance of UsersCart class
            usersCart.removeFromCart(selectedIndex);

            // Update the JList model with the updated cart items
            ShoppingList.setModel(usersCart.getCartListModel());

            // Show a message to inform the user that the product has been removed from the cart
            JOptionPane.showMessageDialog(this, "Product removed from cart.");

            // Update the total label with the new total value
            double total = usersCart.getTotal();
            totalCartText.setText("Total: $" + String.format("%.2f", total));
        } else {
            // Show a message to inform the user to select a product to remove
            JOptionPane.showMessageDialog(this, "Please select a product to remove from the cart.");
        }
    }//GEN-LAST:event_removeItemBtnActionPerformed

    private void detailsFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detailsFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_detailsFieldActionPerformed

    private void productAddBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productAddBtnActionPerformed
        int selectedRow = allProductsTable.getSelectedRow();
        if (selectedRow != -1) {
            int productId = (int) allProductsTable.getValueAt(selectedRow, 0);
            String productName = (String) allProductsTable.getValueAt(selectedRow, 1);
            double productPrice = (double) allProductsTable.getValueAt(selectedRow, 2);
            String productInfo = (String) allProductsTable.getValueAt(selectedRow, 3);

            // Assuming usersCart is an instance of your UsersCart class
            usersCart.addToCart(productId, productName, productPrice, productInfo);

            // Update the JList model with the new cart items
            ShoppingList.setModel(usersCart.getCartListModel());

            // Show a message to inform the user that the product has been added to the cart
            JOptionPane.showMessageDialog(this, "Product added to cart: " + productName);
        } else {
            // Show a message to inform the user to select a product
            JOptionPane.showMessageDialog(this, "Please select a product to add to the cart.");
        }

        double total = usersCart.getTotal();
        totalCartText.setText("Total: $" + total);
    }//GEN-LAST:event_productAddBtnActionPerformed

    private void categoryAddBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryAddBtnActionPerformed
        int selectedRow = categoryTable.getSelectedRow();
        if (selectedRow != -1) {
            int productId = (int) categoryTable.getValueAt(selectedRow, 0);
            String productName = (String) categoryTable.getValueAt(selectedRow, 1);
            double productPrice = (double) categoryTable.getValueAt(selectedRow, 2);
            String productInfo = (String) categoryTable.getValueAt(selectedRow, 3);

            // Call the new method for adding products to the cart
            addProductToCart(productId, productName, productPrice, productInfo);
        } else {
            // Show a message to inform the user to select a product
            JOptionPane.showMessageDialog(this, "Please select a product to add to the cart.");
        }
    }//GEN-LAST:event_categoryAddBtnActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // Get the selected category name from the JComboBox
        String selectedCategoryName = (String) jComboBox1.getSelectedItem();

        // If selectedCategoryName is null or empty, return
        if (selectedCategoryName == null || selectedCategoryName.isEmpty()) {
            return;
        }

        // Get the Category object based on the selected category name
        CategoryDB categoryDB = new CategoryDB();
        Category selectedCategory = categoryDB.getCategoryByName(selectedCategoryName);

        // If selectedCategory is null, return
        if (selectedCategory == null) {
            return;
        }

        // Get the selected category's ID
        int categoryId = selectedCategory.getId();

        // Populate the items table based on the selected category
        populateItemsTableByCategory(categoryId);
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void passwordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordFieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Catgory;
    private javax.swing.JList<String> ShoppingList;
    private javax.swing.JButton allProductsBtn;
    private javax.swing.JPanel allProductsPanel;
    private javax.swing.JTable allProductsTable;
    private javax.swing.JButton cartBtn;
    private javax.swing.JButton categoryAddBtn;
    private javax.swing.JButton categoryBtn;
    private javax.swing.JTable categoryTable;
    private java.awt.Button changePasswordBtn;
    private javax.swing.JTextField detailsField;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JLabel firstNameLabel;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lastNameLabel;
    private javax.swing.JPanel layoutCard;
    private javax.swing.JButton logOutBtn;
    private javax.swing.JTextField passwordField;
    private javax.swing.JButton productAddBtn;
    private javax.swing.JButton profileBtn;
    private javax.swing.JPanel profilePanel;
    private javax.swing.JButton removeItemBtn;
    private javax.swing.JPanel shoppingCart;
    private javax.swing.JTextPane totalCartText;
    private javax.swing.JLabel userLabel;
    // End of variables declaration//GEN-END:variables
}
