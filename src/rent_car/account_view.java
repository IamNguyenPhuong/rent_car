/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rent_car;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.*;

/**
 *
 * @author ASUS
 */


public class account_view extends JFrame {
    public JLabel titleLabel;
    public JTable accountTable;
    public DefaultTableModel tableModel;

    public JTextField usernameField = new JTextField(15);
    public JTextField passwordField = new JTextField(15);

    public JButton addButton = new JButton("Add");
    public JButton editButton = new JButton("Edit");
    public JButton deleteButton = new JButton("Delete");
    public JButton switchToCarsButton = new JButton("Cars Management");
    public JButton switchToCustomersButton = new JButton("Customers Management");
    public JButton switchToRentButton = new JButton("Rent Management");
    public JButton switchToReturnButton = new JButton("Return Management");
    
    public account_view() {
        setTitle("Manage Account");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Title Label
        titleLabel = new JLabel("Account Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel 1: Management Buttons on the Left
        JPanel changePanel = new JPanel();
        changePanel.setLayout(new BoxLayout(changePanel, BoxLayout.Y_AXIS));
        changePanel.add(switchToCarsButton);
        changePanel.add(switchToCustomersButton);
        changePanel.add(switchToRentButton);
        changePanel.add(switchToReturnButton);

        // Panel 2: Table on the Right
        JPanel tablePanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Username");
        tableModel.addColumn("Passwords");
        accountTable = new JTable(tableModel);
        accountTable.setPreferredSize(new Dimension(500, 200)); // Set preferred size
        JScrollPane scrollPane = new JScrollPane(accountTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Panel 3: Form Fields and Buttons Below the Table
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        formPanel.add(new JLabel("Username:"), gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 5);

        formPanel.add(usernameField, gbc);

        gbc.gridy++;
        formPanel.add(passwordField, gbc);
        // Buttons: Add, Edit, Delete
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Add Form Panel and Button Panel to a single Panel
        JPanel combinedFormPanel = new JPanel(new BorderLayout());
        combinedFormPanel.add(formPanel, BorderLayout.CENTER);
        combinedFormPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Combining Table Panel and Form Panel
        JPanel tableFormPanel = new JPanel(new BorderLayout());
        tableFormPanel.add(tablePanel, BorderLayout.CENTER);
        tableFormPanel.add(combinedFormPanel, BorderLayout.SOUTH);

        // Adding Panels to Main Panel
        mainPanel.add(changePanel, BorderLayout.WEST);
        mainPanel.add(tableFormPanel, BorderLayout.CENTER);

        add(mainPanel);
        
        accountTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = accountTable.getSelectedRow();
                if (selectedRow != -1) {
                    usernameField.setText((String) tableModel.getValueAt(selectedRow, 0));
                    passwordField.setText((String) tableModel.getValueAt(selectedRow, 1));
                }
            }
        });
    }
    public void addAccount(String[] accountData) {
        tableModel.addRow(accountData);
    }

    public void editCar(int rowIndex, String[] accountData) {
        for (int i = 0; i < accountData.length; i++) {
            tableModel.setValueAt(accountData[i], rowIndex, i);
        }
    }

    public void deleteCar(int rowIndex) {
        tableModel.removeRow(rowIndex);
    }

    public String getSelectedRegistrationNumber() {
        int selectedRow = accountTable.getSelectedRow();
        if (selectedRow != -1) {
            return (String) tableModel.getValueAt(selectedRow, 0);
        }
        return null;
    }

    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }

    public void addAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void addEditButtonListener(ActionListener listener) {
        editButton.addActionListener(listener);
    }

    public void addDeleteButtonListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }
    
    public void addSwitchtoCarButtonListener(ActionListener listener) { 
        switchToCarsButton.addActionListener(listener);
    }
    
    public void addSwitchtoCustomerButtonListener(ActionListener listener) { 
        switchToCustomersButton.addActionListener(listener);
    }
    
    public void addSwitchToRentButtonListener(ActionListener listener) {
        switchToRentButton.addActionListener(listener);
    }

    public void addSwitchToReturnButtonListener(ActionListener listener) {
        switchToReturnButton.addActionListener(listener);
    }
    
    public String[] getAccountData() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        return new String[]{username, password};
    }
    
    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
