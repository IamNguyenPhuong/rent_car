/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rent_car;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.DocumentListener;

public class cars_view extends JFrame {
    public JLabel titleLabel;
    public JTable carsTable;
    public DefaultTableModel tableModel;

    public JTextField regNumField = new JTextField(15);
    public JTextField modelField = new JTextField(15);
    public JTextField brandField = new JTextField(15);
    public JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Available", "Booked"});
    public JTextField priceField = new JTextField(15);

    public JButton addButton = new JButton("Add");
    public JButton editButton = new JButton("Edit");
    public JButton deleteButton = new JButton("Delete");
    public JButton ExcelButton = new JButton("Export to Excel");
    public JButton switchToAccountButton = new JButton("Account Management");
    public JButton switchToCustomersButton = new JButton("Customers Management");
    public JButton switchToRentButton = new JButton("Rent Management");
    public JButton switchToReturnButton = new JButton("Return Management");
    private JTextField carsSearchField;

    public cars_view() {
        setTitle("Manage Cars");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Title Label
        titleLabel = new JLabel("Cars Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel 1: Management Buttons on the Left
        JPanel changePanel = new JPanel();
        changePanel.setLayout(new BoxLayout(changePanel, BoxLayout.Y_AXIS));
        changePanel.add(switchToAccountButton); 
        changePanel.add(switchToCustomersButton);
        changePanel.add(switchToRentButton);
        changePanel.add(switchToReturnButton);

        // Panel 2: Table on the Right
        JPanel tablePanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Registration Number");
        tableModel.addColumn("Model");
        tableModel.addColumn("Brand");
        tableModel.addColumn("Status");
        tableModel.addColumn("Price");
        carsTable = new JTable(tableModel);
        carsTable.setPreferredSize(new Dimension(500, 200)); // Set preferred size
        JScrollPane scrollPane = new JScrollPane(carsTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // Customers Search
        JPanel carsSearchPanel = new JPanel();
        carsSearchPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JLabel carsCarsSearchLabel = new JLabel("Search:");
        carsSearchField = new JTextField(15);
        carsSearchPanel.add(carsCarsSearchLabel);
        carsSearchPanel.add(carsSearchField);
        tablePanel.add(carsSearchPanel,BorderLayout.SOUTH);
        
        // Panel 3: Form Fields and Buttons Below the Table
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        formPanel.add(new JLabel("Registration Number:"), gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Model:"), gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Brand:"), gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Status:"), gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Price:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 5);

        formPanel.add(regNumField, gbc);

        gbc.gridy++;
        formPanel.add(modelField, gbc);

        gbc.gridy++;
        formPanel.add(brandField, gbc);

        gbc.gridy++;
        formPanel.add(statusCombo, gbc);

        gbc.gridy++;
        formPanel.add(priceField, gbc);
        
        // Buttons: Add, Edit, Delete
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(ExcelButton);

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
        
        carsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = carsTable.getSelectedRow();
                if (selectedRow != -1) {
                    regNumField.setText((String) tableModel.getValueAt(selectedRow, 0));
                    modelField.setText((String) tableModel.getValueAt(selectedRow, 1));
                    brandField.setText((String) tableModel.getValueAt(selectedRow, 2));
                    statusCombo.setSelectedItem((String) tableModel.getValueAt(selectedRow, 3));
                    priceField.setText((String) tableModel.getValueAt(selectedRow, 4));
                }
            }
        });

    }
    
    public void setCarsTable(DefaultTableModel model) {
        carsTable.setModel(model);
    }
    
    public JTable getCarsTable() {
        return carsTable;
    }

    public void addCar(String[] carData) {
        tableModel.addRow(carData);
    }

    public void editCar(int rowIndex, String[] carData) {
        for (int i = 0; i < carData.length; i++) {
            tableModel.setValueAt(carData[i], rowIndex, i);
        }
    }

    public void deleteCar(int rowIndex) {
        tableModel.removeRow(rowIndex);
    }

    public String getSelectedRegistrationNumber() {
        int selectedRow = carsTable.getSelectedRow();
        if (selectedRow != -1) {
            return (String) tableModel.getValueAt(selectedRow, 0);
        }
        return null;
    }
    
    public void addCustomersSearchListener(DocumentListener listener) {
        carsSearchField.getDocument().addDocumentListener(listener);
    }

    public void clearFields() {
        regNumField.setText("");
        modelField.setText("");
        brandField.setText("");
        statusCombo.setSelectedIndex(0);
        priceField.setText("");
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
    
    public void addExcelButtonListener(ActionListener listener) {
        ExcelButton.addActionListener(listener);
    }
    
    public void addSwitchtoAccountButtonListener(ActionListener listener) { 
        switchToAccountButton.addActionListener(listener);
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
    
    public String getCarsSearchText() {
        return carsSearchField.getText();
    }
    
    public String[] getCarData() {
        String regNum = regNumField.getText();
        String model = modelField.getText();
        String brand = brandField.getText();
        String status = (String) statusCombo.getSelectedItem();
        String price = priceField.getText();

        return new String[]{regNum, model, brand, status, price};
    }

    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
