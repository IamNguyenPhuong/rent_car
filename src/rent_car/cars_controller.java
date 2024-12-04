/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rent_car;

import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

public class cars_controller {
    private cars_view view;
    private cars_model model;
    private Connection connection;
    private boolean isLoggedIn = false;


    public cars_controller(cars_view view, cars_model model) {
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

        this.view.addAddButtonListener(new AddButtonListener());
        this.view.addEditButtonListener(new EditButtonListener());
        this.view.addDeleteButtonListener(new DeleteButtonListener());
        this.view.addExcelButtonListener(new ExcelButtonListener());
        this.view.addSwitchtoAccountButtonListener(new addSwitchtoAccountButtonListener());
        this.view.addSwitchtoCustomerButtonListener(new addSwitchtoCustomerButtonListener());
        this.view.addSwitchToRentButtonListener(new SwitchToRentButtonListener());
        this.view.addSwitchToReturnButtonListener(new SwitchToReturnButtonListener());
        this.view.addCustomersSearchListener(new CarsSearchListener());

        String[][] cars = getAllCars();
        for (String[] car : cars) {
            view.addCar(car);
        }
        
        this.view.addMouseListener(new CarsTableMouseListener());

        view.setVisible(false);
    }
    
    //Sử dụng để truy vấn tất cả các thông tin về xe từ cơ sở dữ liệu và trả về một mảng hai chiều chứa thông tin này
    public String[][] getAllCars() {
        String query = "SELECT * FROM manage_cars";
        List<String[]> carsList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String registrationNum = resultSet.getString("regis_num");
                String model = resultSet.getString("model");
                String brand = resultSet.getString("brand");
                String status = resultSet.getString("status");
                String price = resultSet.getString("price");

                String[] car = {registrationNum, model, brand, status, price};
                carsList.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carsList.toArray(new String[0][0]);
    }
    
    
    private boolean isValidCarData(String[] carData) {
        // Kiểm tra dữ liệu xe hợp lệ
        // Ở đây có thể thêm các kiểm tra khác tùy theo yêu cầu
        if (carData[0].isEmpty() || carData[1].isEmpty() || carData[2].isEmpty() || carData[4].isEmpty()) {
            return false;
        }
        if (!carData[3].equals("Booked") && !carData[3].equals("Available")) {
            return false;
        }
        try {
            Double.parseDouble(carData[4]);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    
    public DefaultTableModel getCars(String search) {
        DefaultTableModel tablemodel = new DefaultTableModel();
        tablemodel.setColumnIdentifiers(new String[]{"Registration Number", "Brand", "Model", "Status", "Price"});

        String query = "SELECT * FROM manage_cars WHERE (model LIKE ? OR brand LIKE ? )";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + search + "%");
            statement.setString(2, "%" + search + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String registrationNum = resultSet.getString("regis_num");
                String model = resultSet.getString("model");
                String brand = resultSet.getString("brand");
                String status = resultSet.getString("status");
                String price = resultSet.getString("price");

                tablemodel.addRow(new Object[]{registrationNum, model, brand, status, price});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tablemodel;
    }
    
    private void updateCarsTable() {
        DefaultTableModel model = this.getCars(view.getCarsSearchText());
        view.setCarsTable(model);
    }
    
    class CarsSearchListener implements DocumentListener {
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
            String search = view.getCarsSearchText();
            DefaultTableModel model = getCars(search);
            view.setCarsTable(model);
        }
    }

    public boolean addCar(String regis_num, String brand, String model, String status, String price) {
        if (connection == null) {
            return false;
        }

        String query = "INSERT INTO manage_cars (regis_num, brand, model, status, price) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, regis_num);
            statement.setString(2, brand);
            statement.setString(3, model);
            statement.setString(4, status);
            statement.setString(5, price);
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] carData = view.getCarData();

            if (isValidCarData(carData)) {
                boolean success = addCar(carData[0], carData[1], carData[2], carData[3], carData[4]);
                if (success) {
                    view.addCar(carData);
                    view.clearFields();
                } else {
                    view.displayErrorMessage("Failed to add car.");
                }
            } else {
                view.displayErrorMessage("Invalid car data. Please check the fields.");
            }
        }
    }
    
    public boolean editCar(String regis_num, String brand, String model, String status, String price) {
        if (connection == null) {
            return false;
        }

        String query = "UPDATE manage_cars SET brand = ?, model = ?, status = ?, price = ? WHERE regis_num = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, brand);
            statement.setString(2, model);
            statement.setString(3, status);
            statement.setString(4, price);
            statement.setString(5, regis_num);
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
            int selectedRow = view.carsTable.getSelectedRow();
            if (selectedRow == -1) {
                view.displayErrorMessage("Please select a car to edit.");
                return;
            }

            String[] carData = view.getCarData();

            if (isValidCarData(carData)) {
                boolean success = editCar(carData[0], carData[1], carData[2], carData[3], carData[4]);
                if (success) {
                    view.editCar(selectedRow, carData);
                    view.clearFields();
                } else {
                    view.displayErrorMessage("Failed to edit car.");
                }
            } else {
                view.displayErrorMessage("Invalid car data. Please check the fields.");
            }
        }
    }
    
    public boolean deleteCar(String regis_num) {
        if (connection == null) {
            return false;
        }

        String query = "DELETE FROM manage_cars WHERE regis_num = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, regis_num);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = view.carsTable.getSelectedRow();
            if (selectedRow == -1) {
                view.displayErrorMessage("Please select a car to delete.");
                return;
            }

            String registrationNumber = view.getSelectedRegistrationNumber();
            if (registrationNumber != null) {
                boolean success = deleteCar(registrationNumber);
                if (success) {
                    view.deleteCar(selectedRow);
                } else {
                    view.displayErrorMessage("Failed to delete car.");
                }
            }
        }
    }
    
    class ExcelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel rentedCarsModel = (DefaultTableModel) view.getCarsTable().getModel();
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
            view.setVisible(false);
            // Chuyển sang cửa sổ quản lý khách hàng
            account_model accountModel = new account_model();
            account_view accountView = new account_view();
            account_controller accountController = new account_controller(accountView, accountModel);
            accountView.setVisible(true);
        }
    }
    
    class addSwitchtoCustomerButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setVisible(false);
            // Chuyển sang cửa sổ quản lý khách hàng
            customers_model customersModel = new customers_model();
            customers_view customersView = new customers_view();
            customers_controller customersController = new customers_controller(customersView, customersModel);
            customersView.setVisible(true);
        }
    }
    
    class SwitchToRentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setVisible(false);

            // Chuyển sang cửa sổ quản lý thuê xe
            rent_model rentModel = new rent_model();
            rent_view rentView = new rent_view();
            rent_controller rentController = new rent_controller(rentView, rentModel);
            rentView.setVisible(true);
        }
    }

    class SwitchToReturnButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setVisible(false);

            // Chuyển sang cửa sổ quản lý trả xe
            return_model returnModel = new return_model();
            return_view returnView = new return_view();
            return_controller returnController = new return_controller(returnView, returnModel);
            returnView.setVisible(true);
        }
    }
    
    class CarsTableMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int selectedRow = view.carsTable.getSelectedRow();
            if (selectedRow != -1) {
                view.regNumField.setText((String) view.tableModel.getValueAt(selectedRow, 0));
                view.modelField.setText((String) view.tableModel.getValueAt(selectedRow, 1));
                view.brandField.setText((String) view.tableModel.getValueAt(selectedRow, 2));
                view.statusCombo.setSelectedItem((String) view.tableModel.getValueAt(selectedRow, 3));
                view.priceField.setText((String) view.tableModel.getValueAt(selectedRow, 4));
            }
        }
    }
    
    public void handleLogin() {
        // Tạo và hiển thị form quản lý xe khi đăng nhập thành công
        cars_model model = new cars_model();
        cars_view view = new cars_view();
        cars_controller controller = new cars_controller(view, model);
        view.setVisible(true);
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
