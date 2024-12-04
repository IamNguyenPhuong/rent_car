/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rent_car;

import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class customers_controller {
    private customers_view view; //Biến private kiểu customers_view tham chiếu đến view (giao diện) của khách hàng
    private customers_model model;
    private Connection connection;

    //Hàm khởi tạo của customers_controller nhận hai tham số là các đối tượng của lớp customers_view và customers_model
    public customers_controller(customers_view view, customers_model model) {
        this.view = view;
        this.model = model;
        
        try {
            // Kết nối đến cơ sở dữ liệu
            String url = "jdbc:mysql://localhost:3306/rent_car";
            String username = "root";
            String password = "";
            connection = DriverManager.getConnection(url, username, password);
            
            //Xảy ra trường hợp ngoại lệ lỗi SQL
        } catch (SQLException e) {
            e.printStackTrace(); //In ra lỗi kết nối
        }


        this.view.addAddButtonListener(new AddButtonListener());
        this.view.addEditButtonListener(new EditButtonListener());
        this.view.addDeleteButtonListener(new DeleteButtonListener());
        this.view.addExcelButtonListener(new ExcelButtonListener());
        this.view.addSwitchtoAccountButtonListener(new addSwitchtoAccountButtonListener());
        this.view.addSwitchToCarsButtonListener(new SwitchToCarsButtonListener());
        this.view.addSwitchToRentButtonListener(new SwitchToRentButtonListener());
        this.view.addSwitchToReturnButtonListener(new SwitchToReturnButtonListener());
        this.view.addCustomersSearchListener(new CustomersSearchListener());
        this.view.addMouseListener(new CustomersTableMouseListener());

        // Lấy danh sách khách hàng từ model và thêm vào view
        String[][] customers = getAllCustomers();//một hàm getAllCustomers() để lấy dữ liệu danh sách khách hàng
        for (String[] customer : customers) {
            view.addCustomer(customer); //Phương thức addCustomer sử dụng thông tin trong mảng customer để tạo ra các thành phần giao diện tương ứng (lên giao diện người dùng)
        }
       // view.setVisible(true); //Hiển thị giao diện người dùng
          
    }
    //lay csdl bo vao bang hien thi
    public String[][] getAllCustomers() {
        String query = "SELECT * FROM manage_customers ";
        List<String[]> customersList = new ArrayList<>();
        
        //PreparedStatement giúp ngăn ngừa SQL Injection attacks 
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            //lưu trữ kết quả trong biến resultSet, Biến resultSet sẽ chứa các dòng dữ liệu trả về bởi truy vấn
            ResultSet resultSet = statement.executeQuery();
            
            //Sử dụng vòng lặp while để duyệt qua từng dòng kết quả và di chuyển tới kq kế tiếp
            while (resultSet.next()) {
                String id = resultSet.getString("id"); //Lấy giá trị của cột id trong dòng hiện tại và chuyển đổi sang kiểu String
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");

                String[] customer = {id, name, address, phone}; //Lưu trữ các giá trị lấy được vào mảng
                customersList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customersList.toArray(new String[0][0]);// trả về kết qủa mảng 2 chiều String
    }
   
// tac dong vao csdl
    public boolean addCustomer(String id, String name, String address, String phone) {
        if (connection == null) {
            return false;
        }

        String query = "INSERT INTO manage_customers (id, name, address, phone) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            //Sử dụng phương thức setString của đối tượng statement để thiết lập giá trị cho các dấu hỏi (?) trong câu lệnh SQL
            statement.setString(1, id);
            statement.setString(2, name);
            statement.setString(3, address);
            statement.setString(4, phone);
            int rowsInserted = statement.executeUpdate(); //Phương thức executeUpdate() để thực thi câu lệnh SQL và thêm khách hàng mới vào database
            return rowsInserted > 0; //Phương thức này trả về số dòng được chèn vào database
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //gan sk cho cac nut
    class AddButtonListener implements ActionListener {
        @Override
        // Phương thức được gọi tự động khi nút "Thêm" được click
        public void actionPerformed(ActionEvent e) {
            String[] customerData = view.getCustomerData();
            
            //Kiểm tra dữ liệu có hợp lệ hay không
            if (isValidCustomerData(customerData)) {
                boolean success = addCustomer(customerData[0], customerData[1], customerData[2], customerData[3]); //Thêm khách hàng vào database
//                double phone1;
//                phone1=Double.parseDouble(customerData[3]);
                
              if (success) {
                    view.addCustomer(customerData); //Đưa thông tin khách hàng mới lên giao diện
                    view.clearFields(); //Xóa dữ liệu trong các trường nhập liệu để chuẩn bị cho lần nhập tiếp theo
                } else {
                    view.displayErrorMessage("Failed to add customer.");
                }
            } else {
                view.displayErrorMessage("Invalid customer data. Please check the fields.");
            }
        }
    }
    
    public boolean editCustomer(String id, String name, String address, String phone) {
        if (connection == null) {
            return false;
        }
        
        String query = "UPDATE manage_customers SET name = ?, address = ?, phone = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, address);
            statement.setString(3, phone);
            statement.setString(4, id);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    class EditButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = view.customersTable.getSelectedRow();
            if (selectedRow == -1) {
                view.displayErrorMessage("Please select a customer to edit.");
                return;
            }

            String[] customerData = view.getCustomerData(); //Lấy thông tin khách hàng mới được nhập vào các trường trên giao diện và lưu vào mảng 

            if (isValidCustomerData(customerData)) {
                boolean success = editCustomer(customerData[0], customerData[1], customerData[2], customerData[3]);
                if (success) {
                    view.editCustomer(selectedRow, customerData);
                    view.clearFields();
                } else {
                    view.displayErrorMessage("Failed to edit customer.");
                }
            } else {
                view.displayErrorMessage("Invalid customer data. Please check the fields.");
            }
        }
    }
    
    public boolean deleteCustomer(String id) {
        if (connection == null) {
            return false;
        }

        String query = "DELETE FROM manage_customers WHERE id = ?"; //Tạo câu lệnh SQL
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id); //Thiết lập tham số
            int rowsDeleted = statement.executeUpdate(); //Thực thi câu lệnh SQL
            return rowsDeleted > 0; // Trả về kết quả
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = view.customersTable.getSelectedRow();
            if (selectedRow == -1) {
                view.displayErrorMessage("Please select a customer to delete.");
                return;
            }

            String customerId = view.getSelectedCustomerId();
            if (customerId != null) {
                boolean success = deleteCustomer(customerId);
                if (success) {
                    view.deleteCustomer(selectedRow);
                } else {
                    view.displayErrorMessage("Failed to delete customer.");
                }
            }
        }
    }
    
    class ExcelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel rentedCarsModel = (DefaultTableModel) view.getCustomersTable().getModel();
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showSaveDialog(view);

            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String filePath = file.getAbsolutePath();

                try {
                    FileWriter fw = new FileWriter(filePath + ".xls");

                    for (int i = 0; i < rentedCarsModel.getColumnCount(); i++) {
                        fw.write(rentedCarsModel.getColumnName(i) + "\t");
                    }
                    fw.write("\n");
                    for (int i = 0; i < rentedCarsModel.getRowCount(); i++) {
                        for (int j = 0; j < rentedCarsModel.getColumnCount(); j++) {
                            fw.write(rentedCarsModel.getValueAt(i, j).toString() + "\t");
                        }
                        fw.write("\n");
                    }

                    fw.close();
                    JOptionPane.showMessageDialog(view, "Data exported to Excel successfully.", "Export Successful",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(view, "Error exporting data to Excel.", "Export Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    
    class addSwitchtoAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setVisible(false); //ẩn cửa sổ hiện tại
            // Chuyển sang cửa sổ quản lý khách hàng
            account_model accountModel = new account_model(); //Tạo đối tượng từ lớp account_model
            account_view accountView = new account_view();
            account_controller accountController = new account_controller(accountView, accountModel);
            accountView.setVisible(true);
        }
    }

    class SwitchToCarsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setVisible(false);  // Ẩn giao diện quản lý khách hàng
            // Tạo và hiển thị form quản lý xe
            cars_model carsModel = new cars_model();
            cars_view carsView = new cars_view();
            cars_controller carsController = new cars_controller(carsView, carsModel);
            carsView.setVisible(true);
            
        }
    }
    
    class SwitchToRentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setVisible(false);  // Ẩn giao diện quản lý khách hàng
            //
            rent_model rentModel = new rent_model();
            rent_view rentView = new rent_view();
            rent_controller rentController = new rent_controller(rentView, rentModel);
            rentView.setVisible(true);
        }
    }
    
    class SwitchToReturnButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setVisible(false);  // Ẩn giao diện quản lý khách hàng
            
            return_model returnModel = new return_model();
            return_view returnView = new return_view();
            return_controller returnController = new return_controller(returnView, returnModel);
            returnView.setVisible(true);
        }
    }
    
    //Gán sự kiện cho bảng khách hàng
    class CustomersTableMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int selectedRow = view.customersTable.getSelectedRow();
            if (selectedRow != -1) {
                view.idField.setText((String) view.tableModel.getValueAt(selectedRow, 0));
                view.nameField.setText((String) view.tableModel.getValueAt(selectedRow, 1));
                view.addressField.setText((String) view.tableModel.getValueAt(selectedRow, 2));
                view.phoneField.setText((String) view.tableModel.getValueAt(selectedRow, 3));
            }
        }
    }
    
    //Lấy danh sách khách hàng từ database dựa trên từ khóa tìm kiếm và trả về kết quả
    public DefaultTableModel getCustomers(String search) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Customer Name", "Address", "Phone"});

        String query = "SELECT * FROM manage_customers WHERE name LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + search + "%"); //Kí tự % giúp tìm kiếm theo kiểu chứa từ khóa ở bất kỳ vị trí nào trong tên khách hàng
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");

                model.addRow(new Object[]{id, name, address, phone}); //Thêm một dòng dữ liệu vào DefaultTableModel với các giá trị vừa lấy được
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    //Cập nhật dữ liệu bảng khách hàng hiển thị trên giao diện khi trả về kết quả sau khi tra cứu
    private void updateCustomersTable() {
        DefaultTableModel model = this.getCustomers(view.getCustomersSearchText());
        view.setCustomersTable(model);//Cập nhật dữ liệu bảng trên giao diện
    }
    //Các sự kiện insertUpdate, removeUpdate, changedUpdate sẽ được kích hoạt, gọi đến phương thức searchCustomers
    class CustomersSearchListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            searchCustomers();
        }
        @Override
        public void removeUpdate(DocumentEvent e) {
            searchCustomers();
        }
        @Override
        public void changedUpdate(DocumentEvent e) {
            searchCustomers();
        }
        private void searchCustomers() {
            String search = view.getCustomersSearchText();
            DefaultTableModel model = getCustomers(search);
            view.setCustomersTable(model);
        }
    }

    private boolean isValidCustomerData(String[] customerData) {
        // Kiểm tra dữ liệu khách hàng hợp lệ
         if (customerData.length < 1) {
            return false;
        }
        // Kiểm tra dữ liệu không được rỗng
        for (String data : customerData) {
            if (data.isEmpty()) {
                return false;
            }
        }
        //Kiểm tra điều kiện ở trường nhập dữ liệu  sđt xem cso 11 số ko
      
        return true;
    }
    
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}