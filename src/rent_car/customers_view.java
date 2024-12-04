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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class customers_view extends JFrame {
    public JLabel titleLabel;
    public JTable customersTable;
    public DefaultTableModel tableModel;// Lớp DefaultTableModel được sử dụng để tạo và quản lý mô hình dữ liệu cho các thành phần JTable trong Java Swing

    public JTextField idField = new JTextField(15);
    public JTextField nameField = new JTextField(15);
    public JTextField addressField = new JTextField(15);
    public JTextField phoneField = new JTextField(15);

    public JButton addButton = new JButton("Add");
    public JButton editButton = new JButton("Edit");
    public JButton deleteButton = new JButton("Delete");
    public JButton ExcelButton = new JButton("Export to Excel");
    public JButton switchToAccountButton = new JButton("Account Management");
    public JButton switchtoCarsButton = new JButton("Cars Management");
    public JButton switchToRentButton = new JButton("Rent Management");
    public JButton switchToReturnButton = new JButton("Return Management");
    private JTextField customersSearchField;

    public customers_view() {
        setTitle("Manage Customers");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Title Label
        titleLabel = new JLabel("Customers Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel 1: Management Buttons on the Left
        JPanel changePanel = new JPanel();
        changePanel.setLayout(new BoxLayout(changePanel, BoxLayout.Y_AXIS)); //sử dụng để sắp xếp theo chiều ngang hoặc chiều dọc
        changePanel.add(switchToAccountButton); 
        changePanel.add(switchtoCarsButton);
        changePanel.add(switchToRentButton);
        changePanel.add(switchToReturnButton);

        // Panel 2: Table on the Right
        JPanel tablePanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Address");
        tableModel.addColumn("Phone");
        customersTable = new JTable(tableModel);
        customersTable.setPreferredSize(new Dimension(500, 200)); // Set preferred size
        JScrollPane scrollPane = new JScrollPane(customersTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // Customers Search
        JPanel customersSearchPanel = new JPanel();
        customersSearchPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        //Thiết lập trình quản lý bố cục cho customersSearchPanel thành FlowLayout với căn chỉnh bên phải. FlowLayout sắp xếp các thành phần theo chiều ngang từ trái sang phải, trên xuống dưới
        JLabel customersCarsSearchLabel = new JLabel("Search:");
        customersSearchField = new JTextField(15);
        customersSearchPanel.add(customersCarsSearchLabel);
        customersSearchPanel.add(customersSearchField);
        tablePanel.add(customersSearchPanel,BorderLayout.SOUTH);

        // Panel 3: Form Fields and Buttons Below the Table
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        //tạo một bảng điều khiển chuyên dụng với bố cục dạng lưới và viền để có thể chứa các thành phần biểu mẫu khác nhau 
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;//Thiết lập cột của thành phần là 0 (cột đầu tiên)
        gbc.gridy = 0;//Thiết lập dòng tăng dần
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5); //Thiết lập khoảng cách 

        formPanel.add(new JLabel("ID:"), gbc);
        //Thêm nhãn vào formPanel với các thuộc tính được thiết lập trong gbc

        gbc.gridy++; //Thiết lập dòng tiếp theo của thành phần
        formPanel.add(new JLabel("Name:"), gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Address:"), gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Phone:"), gbc);

        gbc.gridx = 1; //Thiết lập cột thành phần là 1 (cột thứ hai)
        gbc.gridy = 0;
        gbc.weightx = 1.0; //Cho phép trường nhập liệu nới rộng ngang hết khoảng trống
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 5);

        formPanel.add(idField, gbc);

        gbc.gridy++;
        formPanel.add(nameField, gbc);

        gbc.gridy++;
        formPanel.add(addressField, gbc);

        gbc.gridy++;
        formPanel.add(phoneField, gbc);

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
        
        customersTable.addMouseListener(new MouseAdapter() {
            @Override
            //ghi đè phương thức từ lớp MouseAdapter
            
            public void mouseClicked(MouseEvent e) {
                int selectedRow = customersTable.getSelectedRow();
                if (selectedRow != -1) {
                    idField.setText((String) tableModel.getValueAt(selectedRow, 0));
                    nameField.setText((String) tableModel.getValueAt(selectedRow, 1));
                    addressField.setText((String) tableModel.getValueAt(selectedRow, 2));
                    phoneField.setText((String) tableModel.getValueAt(selectedRow, 3));
                }
            }
        });
        
       
}

    public void addCustomer(String[] customerData) {
        tableModel.addRow(customerData); //Mảng chuỗi chứa thông tin khách hàng được truyền trực tiếp làm dữ liệu cho mảng mới
    }

    public void editCustomer(int rowIndex, String[] customerData) {
        //Duyệt qua từng phần tử trong mảng customerData
        for (int i = 0; i < customerData.length; i++) {
            tableModel.setValueAt(customerData[i], rowIndex, i);
        }
    }

    public void deleteCustomer(int rowIndex) {
        tableModel.removeRow(rowIndex);
    }
    
    //Trả về bảng dữ liệu khách hàng (customersTable) dưới dạng đối tượng JTable
    public JTable getCustomersTable() {
        return customersTable;
    }
    
    //Trả về ID của khách hàng được chọn trong bảng (nếu có), nếu không trả về null
    public String getSelectedCustomerId() {
        int selectedRow = customersTable.getSelectedRow();
        if (selectedRow != -1) {
            return (String) tableModel.getValueAt(selectedRow, 0);
        }
        return null;
    }

    public void clearFields() {
        idField.setText("");
        nameField.setText("");
        addressField.setText("");
        phoneField.setText("");
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

    public void addSwitchToCarsButtonListener(ActionListener listener) {
        switchtoCarsButton.addActionListener(listener);
    }
    
    
    public void addSwitchToRentButtonListener(ActionListener listener) {
        switchToRentButton.addActionListener(listener);
    }
    
    public void addSwitchToReturnButtonListener(ActionListener listener) {
        switchToReturnButton.addActionListener(listener);
    }
    
    public void addCustomersSearchListener(DocumentListener listener) {
        customersSearchField.getDocument().addDocumentListener(listener);
    }
    
    //Trích xuất thông tin khách hàng từ các trường nhập liệu trong giao diện người dùng và tổng hợp chúng thành một mảng chuỗi
    public String[] getCustomerData() {
        String id = idField.getText();
        String name = nameField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        return new String[]{id, name, address, phone};
    }
    
    //Cập nhật toàn bộ dữ liệu hiển thị trong bảng
    public void setCustomersTable(DefaultTableModel model) {
        customersTable.setModel(model);
    }
    
    //Trả lại kết quả search
    public String getCustomersSearchText() {
        return customersSearchField.getText();
    }
    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
   
}