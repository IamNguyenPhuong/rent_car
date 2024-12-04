/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rent_car;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 *
 * @author ASUS
 */
public class login_view extends JFrame {
    public JLabel usernameLabel = new JLabel("Username:");
    public JLabel passwordLabel = new JLabel("Password:");
    public JTextField usernameField = new JTextField(20);
    public JPasswordField passwordField = new JPasswordField(20);
    public JButton loginButton = new JButton("Login");

    public login_view() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);

        add(panel);
    }
    
    public void hideLoginView() {
    setVisible(false);
    }
    
    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

}
