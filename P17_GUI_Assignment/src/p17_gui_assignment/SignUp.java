package p17_gui_assignment;

/**
 *
 * @author Albrent Manlutac
 */
import javax.swing.JOptionPane;

public class SignUp extends javax.swing.JFrame {

    private UserDB userDB;

    /**
     * Creates new form SignUp
     */
    public SignUp() {
        initComponents();
        setSize(800, 500); // Set the default size to 800x500
        setResizable(false); // Disable resizing

        // Initialize UserDB
        userDB = new UserDB();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 500));
        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(101, 157, 189));
        jPanel2.setPreferredSize(new java.awt.Dimension(400, 500));

        jLabel2.setFont(new java.awt.Font("Microsoft Himalaya", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Online Shopping System");
        jLabel2.setPreferredSize(new java.awt.Dimension(277, 37));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                .addGap(31, 31, 31))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(190, 190, 190)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(248, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 400, 500);

        jLabel1.setFont(new java.awt.Font("Microsoft Himalaya", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(101, 157, 189));
        jLabel1.setText("Sign Up");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(550, 80, 120, 50);

        jLabel3.setForeground(new java.awt.Color(101, 157, 189));
        jLabel3.setText("First Name:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(450, 140, 70, 16);
        jPanel1.add(jTextField1);
        jTextField1.setBounds(450, 160, 300, 22);

        jLabel4.setForeground(new java.awt.Color(101, 157, 189));
        jLabel4.setText("Last Name:");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(450, 200, 100, 16);
        jPanel1.add(jTextField2);
        jTextField2.setBounds(450, 220, 300, 22);

        jLabel5.setForeground(new java.awt.Color(101, 157, 189));
        jLabel5.setText("Email:");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(450, 260, 37, 16);
        jPanel1.add(jTextField3);
        jTextField3.setBounds(450, 280, 300, 22);

        jLabel6.setForeground(new java.awt.Color(101, 157, 189));
        jLabel6.setText("Password:");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(450, 320, 70, 20);
        jPanel1.add(jTextField4);
        jTextField4.setBounds(450, 340, 300, 22);

        jButton1.setBackground(new java.awt.Color(101, 157, 189));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Sign Up");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(560, 380, 90, 30);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 60, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 62, Short.MAX_VALUE))
        );

        jPanel1.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String firstName = jTextField1.getText();
        String lastName = jTextField2.getText();
        String email = jTextField3.getText();
        String password = jTextField4.getText();

        // Check if any of the fields are empty
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all the fields.");
            return; // Exit the method without attempting sign-up
        }

        // Call methods from UserDB to create table and insert user data
        userDB.createUsersTable();
        userDB.insertUserData(firstName, lastName, email, password);

        // Redirect to the login page
        Login loginPage = new Login();
        loginPage.setVisible(true);
        this.dispose(); // Close the current sign-up window

    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
