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
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.swing.event.*;
import java.util.Date;

public class return_controller {
    private return_view view;
    private return_model model;
    private Connection connection;

    public return_controller(return_view view, return_model model) {
        this.view = view;
        this.model = model;
        
        try {
            // Kết nối đến cơ sở dữ liệu
            String url = "jdbc:mysql://localhost:3306/rent_car";
            String username = "root";
            String password = "";
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        updateRentTable();
        updateReturnTable();

        view.addReturnButtonListener(new ReturnButtonListener());
        view.addDeleteButtonListener(new DeleteButtonListener());
        view.addReturnExcelButtonListener(new ReturnExcelButtonListener());
        
        this.view.addSwitchtoAccountButtonListener(new addSwitchtoAccountButtonListener());
        this.view.addSwitchToCarsButtonListener(new SwitchToCarsButtonListener());
        this.view.addSwitchToCustomersButtonListener(new SwitchToCustomersButtonListener());
        this.view.addSwitchToRentButtonListener(new SwitchToRentButtonListener());
        this.view.addRentCarsSearchListener(new RentCarsSearchListener());
        this.view.addReturnCarsSearchListener(new ReturnCarsSearchListener());
        
        // Add ListSelectionListener to rentedCarsTable
        view.addRentedCarsTableListener(new RentedCarsTableListener());
        
    }
    
    public DefaultTableModel getRentCars(String search) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Registration Num", "Customer Name", "Rent Date", "Return Date", "Fees"});

        String query = "SELECT * FROM manage_rent WHERE regis_num LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + search + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String regisNum = resultSet.getString("regis_num");
                String customerName = resultSet.getString("customer_name");
                String rentDate = resultSet.getString("rent_date");
                String returnDate = resultSet.getString("return_date");
                double fees = resultSet.getDouble("fees");

                model.addRow(new Object[]{id, regisNum, customerName, rentDate, returnDate, fees});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }
    
    public DefaultTableModel getReturnCars(String search) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Registration Num", "Customer Name", "Return Date", "Delay", "Fine"});

        String query = "SELECT * FROM manage_return WHERE regis_num LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + search + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String regisNum = resultSet.getString("regis_num");
                String customerName = resultSet.getString("customer_name");
                String returnDate = resultSet.getString("return_date");
                int delay = resultSet.getInt("delay");
                double fine = resultSet.getDouble("fine");

                model.addRow(new Object[]{id, regisNum, customerName, returnDate, delay, fine});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }
    
    private void updateRentCarsTable() {
        DefaultTableModel model = this.getRentCars(view.getRentCarsSearchText());
        view.setRentedCarsTable(model);
    }

    private void updateReturnCarsTable() {
        DefaultTableModel model = this.getReturnCars(view.getReturnCarsSearchText());
        view.setReturnTableModel(model);
    }

    
    public DefaultTableModel getRentTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Registration Num", "Customer Name", "Rent Date", "Return Date", "Fees"});

        String query = "SELECT * FROM manage_rent";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String regisNum = resultSet.getString("regis_num");
                String customerName = resultSet.getString("customer_name");
                String rentDate = resultSet.getString("rent_date");
                String returnDate = resultSet.getString("return_date");
                double fees = resultSet.getDouble("fees");

                model.addRow(new Object[]{id, regisNum, customerName, rentDate, returnDate, fees});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    private void updateRentTable() {
        DefaultTableModel model = this.getRentTable();
        view.setRentTableModel(model);
    }
    
    public DefaultTableModel getReturnTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Registration Num", "Customer Name", "Return Date", "Delay", "Fine"});

        String query = "SELECT * FROM manage_return";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String regisNum = resultSet.getString("regis_num");
                String customerName = resultSet.getString("customer_name");
                String returnDate = resultSet.getString("return_date");
                int delay = resultSet.getInt("delay");
                double fine = resultSet.getDouble("fine");

                model.addRow(new Object[]{id, regisNum, customerName, returnDate, delay, fine});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    private void updateReturnTable() {
        DefaultTableModel model = this.getReturnTable();
        view.setReturnTableModel(model);
    }
    
    public void returnCar(String id, String regisNum, String customerName, Date returnDate, int delay, double fine) {
        String query = "INSERT INTO manage_return (id, regis_num, customer_name, return_date, delay, fine) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.setString(2, regisNum);
            statement.setString(3, customerName);
            statement.setDate(4, new java.sql.Date(returnDate.getTime()));
            statement.setInt(5, delay);
            statement.setDouble(6, fine);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateCarStatusToAvailable(String regisNum) {
        String query = "UPDATE manage_cars SET status = 'Available' WHERE regis_num = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, regisNum);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteRentedCar(String id) {
        String query = "DELETE FROM manage_rent WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    class ReturnButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String id = view.getID();
            String regisNum = view.getRegisNum();
            String customerName = view.getCustomerName();
            Date returnDate = view.getReturnDate();
            int delay = view.getDelay();
            double fine = view.getFine();

            returnCar(id, regisNum, customerName, returnDate, delay, fine);
            
            deleteRentedCar(id);

            updateRentTable();
            updateReturnTable();
            view.clearFields();
            
            updateCarStatusToAvailable(regisNum);
            
            
        }
    }
    
    class ReturnExcelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel returnedCarsModel = (DefaultTableModel) view.getReturnedCarsTable().getModel();
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showSaveDialog(view);

            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String filePath = file.getAbsolutePath();

                try {
                    FileWriter fw = new FileWriter(filePath + ".xls");

                    for (int i = 0; i < returnedCarsModel.getColumnCount(); i++) {
                        fw.write(returnedCarsModel.getColumnName(i) + "\t");
                    }
                    fw.write("\n");

                    for (int i = 0; i < returnedCarsModel.getRowCount(); i++) {
                        for (int j = 0; j < returnedCarsModel.getColumnCount(); j++) {
                            fw.write(returnedCarsModel.getValueAt(i, j).toString() + "\t");
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
    
    // Phương thức để xoá dữ liệu
    class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = view.getReturnTable().getSelectedRow();
            if (selectedRow != -1) {
                String id = view.getReturnTable().getValueAt(selectedRow, 0).toString();
                deleteReturnData(id);
            }
        }
    }

    public void deleteReturnData(String id) {
        String query = "DELETE FROM manage_return WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.executeUpdate();
            updateReturnTable(); // Cập nhật bảng sau khi xoá dữ liệu
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    class addSwitchtoAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setVisible(false);
            // Chuyển sang cửa sổ quản lý khách hàng
            account_model accountModel = new account_model();
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
    
    class SwitchToCustomersButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setVisible(false);  // Ẩn giao diện quản lý khách hàng
            // Tạo và hiển thị form quản lý xe
            customers_model customersModel = new customers_model();
            customers_view customersView = new customers_view();
            customers_controller customersController = new customers_controller(customersView, customersModel);
            customersView.setVisible(true);
        }
    }
    
    class SwitchToRentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setVisible(false);  // Ẩn giao diện quản lý khách hàng
            
            rent_model rentModel = new rent_model();
            rent_view rentView = new rent_view();
            rent_controller rentController = new rent_controller(rentView, rentModel);
            rentView.setVisible(true);
        }
    }
    
    class RentedCarsTableListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.getRentedCarsTable().getSelectedRow();
                if (selectedRow != -1) {
                    String regisNum = view.getRentedCarsTable().getValueAt(selectedRow, 1).toString();
                    String customerName = view.getRentedCarsTable().getValueAt(selectedRow, 2).toString();

                    // Set regisNum to regisNumField
                    view.setRegisNum(regisNum);

                    // Set customerName to customerNameComboBox
                    view.setCustomerName(customerName);
                }
            }

        }
    }
    
    class RentCarsSearchListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            searchAvailableCars();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            searchAvailableCars();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            searchAvailableCars();
        }

        private void searchAvailableCars() {
            String search = view.getRentCarsSearchText();
            DefaultTableModel model = getRentCars(search);
            view.setRentedCarsTable(model);
        }
    }

    class ReturnCarsSearchListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            searchRentedCars();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            searchRentedCars();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            searchRentedCars();
        }

        private void searchRentedCars() {
            String search = view.getReturnCarsSearchText();
            DefaultTableModel model = getReturnCars(search);
            view.setReturnTableModel(model);
        }
    }
    
    public Date getExpectedReturnDate() {
        int selectedRow = view.getRentedCarsTable().getSelectedRow();
        if (selectedRow != -1) {
            String rentDateString = view.getRentedCarsTable().getValueAt(selectedRow, 4).toString();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            try {
                return formatter.parse(rentDateString);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return null;
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