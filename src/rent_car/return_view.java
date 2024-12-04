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
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.event.*;
import com.toedter.calendar.*;
import java.beans.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class return_view extends JFrame {
    private JLabel titleLabel;
    private JLabel rentLabel;
    private JTable rentTable;
    private JScrollPane rentScrollPane;
    private JLabel returnLabel;
    private JTable returnTable;
    private JScrollPane returnScrollPane;
    private JLabel idLabel;
    private JTextField idField;
    private JLabel regisNumLabel;
    private JTextField regisNumField;
    private JLabel customerNameLabel;
    private JTextField customerNameField;
    private JLabel returnDateLabel;
    private JDateChooser returnDateField;
    private JLabel delayLabel;
    private JTextField delayField;
    private JLabel fineLabel;
    private JTextField fineField;
    private JButton returnButton;
    private JButton deleteButton;
    public JButton returnExcelButton = new JButton("Export to Excel");
    public JButton switchToAccountButton = new JButton("Account Management");
    public JButton switchtoCarsButton = new JButton("Cars Management");
    public JButton switchToCustomersButton = new JButton("Customer Management");
    public JButton switchToRentButton = new JButton("Rent Management");
    private JTextField rentCarsSearchField;
    private JTextField returnCarsSearchField;

    public return_view() {
        setTitle("Return Car");
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
        changePanel.add(switchToRentButton);
        mainPanel.add(changePanel, BorderLayout.WEST);

        // Panel 2: Return Title and Tables on the Right
        JPanel returnPanel = new JPanel();
        returnPanel.setLayout(new BoxLayout(returnPanel, BoxLayout.Y_AXIS));

        // Return Title
        titleLabel = new JLabel("Return Car");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnPanel.add(titleLabel);
        returnPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Rent Table
        rentLabel = new JLabel("Rent Information:");
        returnPanel.add(rentLabel);
        rentTable = new JTable();
        rentScrollPane = new JScrollPane(rentTable);
        returnPanel.add(rentScrollPane);
        returnPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Available Cars Search
        JPanel rentCarsSearchPanel = new JPanel();
        rentCarsSearchPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JLabel rentCarsSearchLabel = new JLabel("Search:");
        rentCarsSearchField = new JTextField(15);
        rentCarsSearchPanel.add(rentCarsSearchLabel);
        rentCarsSearchPanel.add(rentCarsSearchField);
        returnPanel.add(rentCarsSearchPanel);
        returnPanel.add(Box.createRigidArea(new Dimension(0, 10)));


        // Return Table
        returnLabel = new JLabel("Return Information:");
        returnPanel.add(returnLabel);
        returnTable = new JTable();
        returnScrollPane = new JScrollPane(returnTable);
        returnPanel.add(returnScrollPane);
        returnPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Return Cars Search
        JPanel returnCarsSearchPanel = new JPanel();
        returnCarsSearchPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JLabel rentedCarsSearchLabel = new JLabel("Search:");
        returnCarsSearchField = new JTextField(15);
        returnCarsSearchPanel.add(rentedCarsSearchLabel);
        returnCarsSearchPanel.add(returnCarsSearchField);
        returnPanel.add(returnCarsSearchPanel);
        returnPanel.add(Box.createRigidArea(new Dimension(0, 10)));

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
        customerNameField = new JTextField(10);

        gbc.weightx = 0.5;
        formPanel.add(idLabel, gbc);
        gbc.gridx++;
        gbc.weightx = 1.0;
        formPanel.add(idField, gbc);

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
        formPanel.add(customerNameField, gbc);

        // Labels và TextFields ở bên phải
        returnDateLabel = new JLabel("Return Date:");
        delayLabel = new JLabel("Delay (days):");
        fineLabel = new JLabel("Fine:");

        returnDateField = new JDateChooser();
        returnDateField.setDateFormatString("dd/MM/yyyy");
        delayField = new JTextField(10);
        fineField = new JTextField(10);

        gbc.gridx = 2; // Di chuyển sang cột thứ 3
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        formPanel.add(returnDateLabel, gbc);
        gbc.gridx++;
        gbc.weightx = 1.0;
        formPanel.add(returnDateField, gbc);

        gbc.gridy++;
        gbc.gridx = 2;
        gbc.weightx = 0.5;
        formPanel.add(delayLabel, gbc);
        gbc.gridx++;
        gbc.weightx = 1.0;
        formPanel.add(delayField, gbc);

        gbc.gridy++;
        gbc.gridx = 2;
        gbc.weightx = 0.5;
        formPanel.add(fineLabel, gbc);
        gbc.gridx++;
        gbc.weightx = 1.0;
        formPanel.add(fineField, gbc);

        // Buttons: Return
        returnButton = new JButton("Return");
        deleteButton = new JButton("Delete");
        returnExcelButton = new JButton("Rent Excel");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(returnButton);
        buttonPanel.add(returnExcelButton);
        buttonPanel.add(deleteButton);

        // Adding Form Panel and Button Panel to a single Panel
        JPanel combinedFormPanel = new JPanel(new BorderLayout());
        combinedFormPanel.add(formPanel, BorderLayout.CENTER);
        combinedFormPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Adding Return Panel to Main Panel
        returnPanel.add(combinedFormPanel);

        // Adding Return Panel to Main Panel
        mainPanel.add(returnPanel, BorderLayout.CENTER);

        setVisible(true);
        
        returnDateField.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                    calculateDelay(); // Tính delay khi ngày trả thực tế thay đổi
                    double fine = calculateFine(); // Tính phí phạt khi ngày trả thực tế thay đổi
                    fineField.setText(String.valueOf(fine)); // Hiển thị phí phạt
                }
            }
        });
    }
    
    // tính số ngày trễ
    
    private Date getRentDateFromTable() {
        int selectedRow = rentTable.getSelectedRow();
        if (selectedRow == -1) {
            // Nếu không có hàng nào được chọn, trả về null hoặc xử lý tùy ý của bạn
            return null;
        }

        DefaultTableModel model = (DefaultTableModel) rentTable.getModel();
        String rentDateString = (String) model.getValueAt(selectedRow, 4); // Cột thứ 5 là return date

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date rentDate = formatter.parse(rentDateString);
            return rentDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void calculateDelay() {
        Date rentDate = getRentDateFromTable(); // Lấy ngày trả dự kiến từ bảng
        Date returnDate = returnDateField.getDate(); // Lấy ngày trả thực tế từ JDateChooser

        if (rentDate != null && returnDate != null) {
            long difference = returnDate.getTime() - rentDate.getTime();
            int delayDays = (int) (difference / (1000 * 60 * 60 * 24));

            if (delayDays > 0) {
                delayField.setText(String.valueOf(delayDays));
            } else {
                delayField.setText("0");
            }
        }
    }
    
    //tính phí phạt
    private int getDelayFromTextField() {
        int delay = 0;
        try {
            delay = Integer.parseInt(delayField.getText());
        } catch (NumberFormatException e) {
            // Xử lý nếu người dùng nhập không phải số
            e.printStackTrace();
        }
        return delay;
    }
    
    private double getFeesFromTable() {
        int selectedRow = rentTable.getSelectedRow();
        if (selectedRow == -1) {
            // Nếu không có hàng nào được chọn, trả về 0 hoặc xử lý tùy ý của bạn
            return 0;
        }

        
        DefaultTableModel model = (DefaultTableModel) rentTable.getModel();
        double fees = Double.parseDouble(model.getValueAt(selectedRow, 5).toString()); // Giả sử cột 5 là cột "fees"

        return fees;
    }
    
    private double calculateFine() {
        double fine = 0;

        int delayDays = getDelayFromTextField(); // Lấy số ngày trễ từ delayField

        if (delayDays > 0) {
            double fees = getFeesFromTable(); // Lấy giá trị fees từ bảng
            fine = delayDays * (0.1 * fees); // Tính phí phạt (fine) = số ngày trễ * 10% của giá trị fees
        }

        return fine;
    }
    
    public String getID() {
        String id = idField.getText();
        if (id.isEmpty()) {
            return "0"; // hoặc giá trị mặc định khác tùy theo yêu cầu
        }
        return id;
    }

    public String getRegisNum() {
        return regisNumField.getText();
    }

    public String getCustomerName() {
        return customerNameField.getText();
    }

    public Date getReturnDate() {
        return returnDateField.getDate();
    }

    public int getDelay() {
        return Integer.parseInt(delayField.getText());
    }

    public double getFine() {
        return Double.parseDouble(fineField.getText());
    }
    
    public void clearFields() {
        idField.setText("");
        regisNumField.setText("");
        customerNameField.setText("");
        returnDateField.setDate(null);
        delayField.setText("");
        fineField.setText("");
    }

    public void setRentTableModel(DefaultTableModel model) {
        rentTable.setModel(model);
    }

    public void setReturnTableModel(DefaultTableModel model) {
        returnTable.setModel(model);
    }
    
    public String getRentCarsSearchText() {
        return rentCarsSearchField.getText();
    }

    public String getReturnCarsSearchText() {
        return returnCarsSearchField.getText();
    }
    
    public void addReturnButtonListener(ActionListener listener) {
        returnButton.addActionListener(listener);
    }
    
    public void addDeleteButtonListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }
    
    public void addReturnExcelButtonListener(ActionListener listener) { 
        returnExcelButton.addActionListener(listener);
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
    
    public void addSwitchToRentButtonListener(ActionListener listener) {
        switchToRentButton.addActionListener(listener);
    }
    
    public void addRentedCarsTableListener(ListSelectionListener listener) {
        rentTable.getSelectionModel().addListSelectionListener(listener);
    }
    
    public void addRentCarsSearchListener(DocumentListener listener) {
        rentCarsSearchField.getDocument().addDocumentListener(listener);
    }

    public void addReturnCarsSearchListener(DocumentListener listener) {
        returnCarsSearchField.getDocument().addDocumentListener(listener);
    }
    
    public int getSelectedRow() {
        return rentTable.getSelectedRow();
    }

    public void setRentedCarsTable(DefaultTableModel model) {
        rentTable.setModel(model);
    }

    public void setRegisNum(String regisNum) {
        regisNumField.setText(regisNum);
    }

    public void setCustomerName(String customerName) {
        customerNameField.setText(customerName);
    }
    
    public JTable getReturnedCarsTable() {
        return returnTable;
    }
    
    public JTable getRentedCarsTable() {
        return rentTable;
    }

    public String getSelectedId() {
        int selectedRow = returnTable.getSelectedRow();
        if (selectedRow != -1) {
            return (String) returnTable.getValueAt(selectedRow, 0);
        }
        return null;
    }
    
    public JTable getReturnTable() {
        return returnTable;
    }

}