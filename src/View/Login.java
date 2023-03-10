/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Backend.Account;
import Backend.LoginDataAccess;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author jaket
 */
public class Login extends javax.swing.JDialog {
    
    Account userAccount = new Account();
    boolean loggedIn = false;
    boolean wantingToExit = true;
    
    
    
    public Login(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.setUndecorated(true);
        initComponents();
        this.setLocationRelativeTo(this);
        
        this.usernameField.setOpaque(false);
        this.passwordField.setOpaque(false);
        
       
        
        
        
        
        
        
    }
    
    public Account getAccount()
    {
        //System.out.println("I have " + this.userAccount.getName());
        return this.userAccount;
    }
    public boolean getLoginStatus()
    {
        return this.loggedIn;
    }
    public String getEnteredUsername()
    {
        return this.usernameField.getText();
    }
    
    public String getEnteredPassword()
    {
        return this.passwordField.getText();
    }
    
    public boolean getExit()
    {
        return this.wantingToExit;
    }
    
    public ArrayList<String> getDetails()
    {
        ArrayList<String> details = new ArrayList();
        details.add(userAccount.getName());
        details.add(Integer.toString((userAccount.getAccess())));
        
        for(String str : details)
        {
            System.out.println(str);
        }
        System.out.println("Stop");
        return details;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        usernameLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        loginButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        passwordField = new javax.swing.JPasswordField();
        backgroundLabel = new javax.swing.JLabel();
        jLayeredPane2 = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setMinimumSize(new java.awt.Dimension(1280, 720));
        getContentPane().setLayout(null);

        usernameLabel.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        usernameLabel.setForeground(new java.awt.Color(255, 255, 255));
        usernameLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/UI Icons/Login/usernameIcon.png"))); // NOI18N
        usernameLabel.setText("Username:");
        getContentPane().add(usernameLabel);
        usernameLabel.setBounds(450, 381, 48, 48);

        passwordLabel.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        passwordLabel.setForeground(new java.awt.Color(255, 255, 255));
        passwordLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/UI Icons/Login/passwordIcon.png"))); // NOI18N
        passwordLabel.setText("Password:");
        getContentPane().add(passwordLabel);
        passwordLabel.setBounds(450, 461, 48, 48);

        usernameField.setBackground(new java.awt.Color(255,255,255,0));
        usernameField.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        usernameField.setForeground(new java.awt.Color(255, 255, 255));
        usernameField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        usernameField.setBorder(null);
        usernameField.setOpaque(false);
        usernameField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                usernameFieldKeyReleased(evt);
            }
        });
        getContentPane().add(usernameField);
        usernameField.setBounds(510, 390, 180, 30);

        loginButton.setBackground(new java.awt.Color(255,255,255,0));
        loginButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        loginButton.setForeground(new java.awt.Color(255, 255, 255));
        loginButton.setText("Login");
        loginButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButtonMouseExited(evt);
            }
        });
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });
        getContentPane().add(loginButton);
        loginButton.setBounds(460, 550, 110, 60);

        exitButton.setBackground(new java.awt.Color(255,255,255,0));
        exitButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        exitButton.setForeground(new java.awt.Color(255, 255, 255));
        exitButton.setText("Exit");
        exitButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });
        getContentPane().add(exitButton);
        exitButton.setBounds(590, 550, 120, 60);

        passwordField.setBackground(new java.awt.Color(255,255,255,0));
        passwordField.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        passwordField.setForeground(new java.awt.Color(255, 255, 255));
        passwordField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passwordField.setBorder(null);
        passwordField.setOpaque(false);
        passwordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwordFieldKeyPressed(evt);
            }
        });
        getContentPane().add(passwordField);
        passwordField.setBounds(510, 470, 180, 30);

        backgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/Backgrounds/loginmabye.jpeg"))); // NOI18N
        backgroundLabel.setOpaque(true);
        getContentPane().add(backgroundLabel);
        backgroundLabel.setBounds(0, 0, 1280, 720);
        getContentPane().add(jLayeredPane2);
        jLayeredPane2.setBounds(0, 0, 1280, 0);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed

       
        
        
        /*
        
        try
        {
            userAccount = LoginDataAccess.login(usernameField.getText().toLowerCase(), passwordField.getText().toLowerCase());
            
            if(!userAccount.getName().equals(null))
            {
                loggedIn = true;
                wantingToExit = false;
                this.dispose();
            }
           
            else
            {
                JOptionPane.showMessageDialog(null, "Could not log into your account.");
                
            }

        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }*/
    }//GEN-LAST:event_loginButtonActionPerformed

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        System.out.println("Exit Test");
        System.exit(0);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void loginButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginButtonMouseEntered
        
        
        
        
    }//GEN-LAST:event_loginButtonMouseEntered

    private void loginButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginButtonMouseExited
        
    }//GEN-LAST:event_loginButtonMouseExited

    private void passwordFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordFieldKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) 
        {
                try
            {
                userAccount = LoginDataAccess.login(usernameField.getText().toLowerCase(), passwordField.getText().toLowerCase());

                if(!userAccount.getName().equals(null))
                {
                    loggedIn = true;
                    wantingToExit = false;
                    this.dispose();
                }

                else
                {
                    JOptionPane.showMessageDialog(null, "Could not log into your account.");

                }

            } 
                catch (FileNotFoundException ex) {
                System.out.println(ex);
            }
        }
      
   
    }//GEN-LAST:event_passwordFieldKeyPressed

    private void usernameFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usernameFieldKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) 
        {
            
            
            
            try
            {
                userAccount = LoginDataAccess.login(usernameField.getText().toLowerCase(), passwordField.getText().toLowerCase());

                if(!userAccount.getName().equals(null))
                {
                    System.out.println("Logging in");
                    loggedIn = true;
                    wantingToExit = false;
                    this.dispose();
                }

                else
                {
                    JOptionPane.showMessageDialog(null, "Could not log into your account.");

                }

            } 
                catch (FileNotFoundException ex) {
                System.out.println(ex);
            }
        }
    }//GEN-LAST:event_usernameFieldKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Login dialog = new Login(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel backgroundLabel;
    private javax.swing.JButton exitButton;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JTextField usernameField;
    private javax.swing.JLabel usernameLabel;
    // End of variables declaration//GEN-END:variables
}
