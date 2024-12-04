/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rent_car;

/**
 *
 * @author ASUS
 */
//import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.event.*;
import com.toedter.calendar.*;
import java.beans.*;

public class rent_view extends JFrame {
    
    
    
    private JLabel titleLabel;
    private JLabel availableCarsLabel;
    private JTable availableCarsTable;
    private JScrollPane availableCarsScrollPane;
    private JLabel rentedCarsLabel;
    private JTable rentedCarsTable;
    private JScrollPane rentedCarsScrollPane;
    private JLabel idLabel;
    private JTextField idField;
    private JLabel regisNumLabel;
    private JTextField regisNumField;
    private JLabel customerNameLabel;
    private JComboBox<String> customerNameComboBox;
    private JLabel rentDateLabel;
    private JDateChooser rentDateField;
    private JLabel returnDateLabel;
    private JDateChooser returnDateField;
    private JLabel feesLabel;
    private JTextField feesField;
    private JButton rentButton;
    public JButton rentExcelButton = new JButton("Export to Excel");
    public JButton switchToAccountButton = new JButton("Account Management");
    public JButton switchtoCarsButton = new JButton("Cars Management");
    public JButton switchToCustomersButton = new JButton("Customer Management");
    public JButton switchToReturnButton = new JButton("Return Management");
    private JTextField availableCarsSearchField;
    private JTextField rentedCarsSearchField;

    public rent_view() {
        setTitle("Manage Rent");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        // Panel 1: Management Buttons on the Left
        JPanel changePanel = new JPanel();
        changePanel.setLayout(new BoxLayout(changePanel, BoxLayout.Y_AXIS));
        changePanel.add(switchToAccountButton); 
        changePanel.add(switchtoCarsButton);
        changePanel.add(switchToCustomersButton);
        changePanel.add(switchToReturnButton);
        mainPanel.add(changePanel, BorderLayout.WEST);

        // Panel 2: Rent Title and Tables on the Right
        JPanel rentPanel = new JPanel();
        rentPanel.setLayout(new BoxLayout(rentPanel, BoxLayout.Y_AXIS));

        // Rent Title
        titleLabel = new JLabel("Manage Rent");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rentPanel.add(titleLabel);
        rentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Available Cars
        availableCarsLabel = new JLabel("Available Cars:");
        rentPanel.add(availableCarsLabel);
        availableCarsTable = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Registration Num", "Model", "Brand", "Status", "Price"}
        ));
        availableCarsScrollPane = new JScrollPane(availableCarsTable);
        rentPanel.add(availableCarsScrollPane);
        rentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Available Cars Search
        JPanel availableCarsSearchPanel = new JPanel();
        availableCarsSearchPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JLabel availableCarsSearchLabel = new JLabel("Search:");
        availableCarsSearchField = new JTextField(15);
        availableCarsSearchPanel.add(availableCarsSearchLabel);
        availableCarsSearchPanel.add(availableCarsSearchField);
        rentPanel.add(availableCarsSearchPanel);
        rentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Rented Cars
        rentedCarsLabel = new JLabel("Rented Cars:");
        rentPanel.add(rentedCarsLabel);
        rentedCarsTable = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Registration Num", "Customer Name", "Rent Date", "Return Date", "Fees"}
        ));
        rentedCarsScrollPane = new JScrollPane(rentedCarsTable);
        rentPanel.add(rentedCarsScrollPane);
        rentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Rented Cars Search
        JPanel rentedCarsSearchPanel = new JPanel();
        rentedCarsSearchPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JLabel rentedCarsSearchLabel = new JLabel("Search:");
        rentedCarsSearchField = new JTextField(15);
        rentedCarsSearchPanel.add(rentedCarsSearchLabel);
        rentedCarsSearchPanel.add(rentedCarsSearchField);
        rentPanel.add(rentedCarsSearchPanel);
        rentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Panel 3: Form Fields and Buttons Below the Tables
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Labels và TextFields ở bên trái
        idLabel = new JLabel("ID:");
        regisNumLabel = new JLabel("Registration Num:");
        customerNameLabel = new JLabel("Customer Name:");

        idField = new JTextField(10);
        regisNumField = new JTextField(10);
        customerNameComboBox = new JComboBox<>();

        gbc.weightx = 0.5;  // Trọng số của cột là 0.5, để căn chỉnh tỷ lệ rộng của các thành phần
        formPanel.add(idLabel, gbc);  // Thêm nhãn ID vào formPanel
        gbc.gridx++;  // Di chuyển sang cột tiếp theo
        gbc.weightx = 1.0;  // Trọng số của cột là 1.0, để căn chỉnh tỷ lệ rộng của các thành phần
        formPanel.add(idField, gbc);  // Thêm trường nhập liệu ID vào formPanel

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.weightx = 0.5;
        formPanel.add(regisNumLabel, gbc);
        gbc.gridx++;
        gbc.weightx = 1.0;
        formPanel.add(regisNumField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.weightx = 0.5;
        formPanel.add(customerNameLabel, gbc);
        gbc.gridx++;
        gbc.weightx = 1.0;
        formPanel.add(customerNameComboBox, gbc);

        // Labels và TextFields ở bên phải
        rentDateLabel = new JLabel("Rent Date:");
        returnDateLabel = new JLabel("Return Date:");
        feesLabel = new JLabel("Fees:");

        // Thay đổi thành JDateChooser
        rentDateField = new JDateChooser();
        rentDateField.setDateFormatString("dd/MM/yyyy");

        returnDateField = new JDateChooser();
        returnDateField.setDateFormatString("dd/MM/yyyy");
        feesField = new JTextField(10);

        gbc.gridx = 2; // Di chuyển sang cột thứ 3
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        formPanel.add(rentDateLabel, gbc);
        gbc.gridx++;
        gbc.weightx = 1.0;
        formPanel.add(rentDateField, gbc);

        gbc.gridy++;
        gbc.gridx = 2;
        gbc.weightx = 0.5;
        formPanel.add(returnDateLabel, gbc);
        gbc.gridx++;
        gbc.weightx = 1.0;
        formPanel.add(returnDateField, gbc);

        gbc.gridy++;
        gbc.gridx = 2;
        gbc.weightx = 0.5;
        formPanel.add(feesLabel, gbc);
                gbc.gridx++;
        gbc.weightx = 1.0;
        formPanel.add(feesField, gbc);

        // Buttons: Rent, Rent Excel
        rentButton = new JButton("Rent");
        rentExcelButton = new JButton("Export to Excel");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(rentButton);
        buttonPanel.add(rentExcelButton);

        // Add Form Panel and Button Panel to a single Panel
        JPanel combinedFormPanel = new JPanel(new BorderLayout());
        combinedFormPanel.add(formPanel, BorderLayout.CENTER);
        combinedFormPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Adding Rent Panel to Main Panel
        rentPanel.add(combinedFormPanel);

        // Adding Rent Panel to Main Panel
        mainPanel.add(rentPanel, BorderLayout.CENTER);

        setVisible(true);
        
        
        //if rentdate< returndate -> returndate null
        rentDateField.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                    Date rentDate = (Date) evt.getNewValue();
                    Date returnDate = returnDateField.getDate();

                    if (returnDate != null && rentDate != null && returnDate.before(rentDate)) {
                        returnDateField.setDate(null);
                    }
                }
            }

            
        });
        
        //if rentdate< returndate -> returndate == rentdate
        returnDateField.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                    Date returnDate = (Date) evt.getNewValue();
                    Date rentDate = rentDateField.getDate();

                    if (rentDate != null && returnDate != null && returnDate.before(rentDate)) {
                        returnDateField.setDate(rentDate);
                    }
                }
            }
        });

    }

    public void clearFields() {
        idField.setText("");
        regisNumField.setText("");
        customerNameComboBox.setSelectedIndex(0);
        rentDateField.setDate(null);
        returnDateField.setDate(null);
        feesField.setText("");
    }
    
    //Sử dụng để thiết lập mô hình dữ liệu (DefaultTableModel) cho bảng hiển thị thông tin các xe có sẵn để thuê.
    public void setAvailableCarsTable(DefaultTableModel model) {
        availableCarsTable.setModel(model);
    }
    
    //Tương tự
    public void setRentedCarsTable(DefaultTableModel model) {
        rentedCarsTable.setModel(model);
    }

    public String getId() {
        return idField.getText();
    }

    public String getRegisNum() {
        return regisNumField.getText();
    }

    public String getCustomerName() {
        return (String) customerNameComboBox.getSelectedItem();
    }

    public Date getRentDate() {
        return rentDateField.getDate();
    }

    public Date getReturnDate() {
        return returnDateField.getDate();
    }

    public String getFees() {
        return feesField.getText();
    }

    public String getAvailableCarsSearchText() {
        return availableCarsSearchField.getText();
    }

    public String getRentedCarsSearchText() {
        return rentedCarsSearchField.getText();
    }

    public void setCustomerNames(String[] names) {
        if (names != null) {
            customerNameComboBox.setModel(new DefaultComboBoxModel<>(names));
        }
    }

    public void addRentButtonListener(ActionListener listener) {
        rentButton.addActionListener(listener);
    }

    public void addRentExcelButtonListener(ActionListener listener) {
        rentExcelButton.addActionListener(listener);
    }
    
    public void addSwitchtoAccountButtonListener(ActionListener listener) { 
        switchToAccountButton.addActionListener(listener);
    }

    public void addSwitchToCarsButtonListener(ActionListener listener) {
        switchtoCarsButton.addActionListener(listener);
    }

    public void addSwitchToCustomersButtonListener(ActionListener listener) {
        switchToCustomersButton.addActionListener(listener);
    }

    public void addSwitchToReturnButtonListener(ActionListener listener) {
        switchToReturnButton.addActionListener(listener);
    }

    public void addAvailableCarsTableListener(ListSelectionListener listener) {
        availableCarsTable.getSelectionModel().addListSelectionListener(listener);
    }

    public void addRentedCarsTableListener(ListSelectionListener listener) {
        rentedCarsTable.getSelectionModel().addListSelectionListener(listener);
    }

    public void addAvailableCarsSearchListener(DocumentListener listener) {
        availableCarsSearchField.getDocument().addDocumentListener(listener);
    }

    public void addRentedCarsSearchListener(DocumentListener listener) {
        rentedCarsSearchField.getDocument().addDocumentListener(listener);
    }

    public void setRegisNum(String regisNum) {
        regisNumField.setText(regisNum);
    }

    public void setFees(String price) {
        feesField.setText(price);
    }

    public JTable getRentedCarsTable() {
        return rentedCarsTable;
    }

    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public JTable getAvailableCarsTable() {
        return availableCarsTable;
    }
}
