package rent_car;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import javax.swing.event.*;
import java.util.Date;

public class rent_controller {
    private rent_view view;
    private rent_model model;
    private Connection connection;

    public rent_controller(rent_view view, rent_model model) {
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

        if (this.view != null) {
            this.view.addSwitchtoAccountButtonListener(new addSwitchtoAccountButtonListener());
            this.view.addSwitchToCarsButtonListener(new SwitchToCarsButtonListener());
            this.view.addSwitchToCustomersButtonListener(new SwitchToCustomersButtonListener());
            this.view.addSwitchToReturnButtonListener(new SwitchToReturnButtonListener());

            // Load initial data
            updateAvailableCarsTable();
            updateRentedCarsTable();
            updateCustomerNames();

            // Add listeners
            this.view.addRentButtonListener(new RentButtonListener());
            this.view.addRentExcelButtonListener(new RentExcelButtonListener());
            this.view.addAvailableCarsTableListener(new AvailableCarsTableListener());
            this.view.addRentedCarsTableListener(new RentedCarsTableListener());
            this.view.addAvailableCarsSearchListener(new AvailableCarsSearchListener());
            this.view.addRentedCarsSearchListener(new RentedCarsSearchListener());
        } else {
            System.err.println("View is null in rent_controller constructor");
        }
    }

    public String[] getCustomerNames() {
        List<String> names = new ArrayList<>();
        String query = "SELECT name FROM manage_customers";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                names.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return names.toArray(new String[0]);
    }

    public void updateCustomerNames() {
        String[] names = getCustomerNames();
        if (names != null && this.view != null) {
            this.view.setCustomerNames(names);
        }
    }

    private void rentCar(String id, String regisNum, String customerName, Date rentDate, Date returnDate, String fees) {
        String query = "INSERT INTO manage_rent (id, regis_num, customer_name, rent_date, return_date, fees) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.setString(2, regisNum);
            statement.setString(3, customerName);
            statement.setDate(4, new java.sql.Date(rentDate.getTime())); // Convert java.util.Date to java.sql.Date
            statement.setDate(5, new java.sql.Date(returnDate.getTime())); // Convert java.util.Date to java.sql.Date
            statement.setString(6, fees);

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public void updateCarStatusToBooked(String regisNum) {
        String query = "UPDATE manage_cars SET status = 'Booked' WHERE regis_num = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, regisNum);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    class RentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String id = view.getId();
            String regisNum = view.getRegisNum();
            String customerName = view.getCustomerName();
            Date rentDate = view.getRentDate();
            Date returnDate = view.getReturnDate();

            String fees = view.getFees();

            if (isValidRentData(id, regisNum, customerName, rentDate, returnDate, fees)) {
                rentCar(id, regisNum, customerName, rentDate, returnDate, fees);
                updateAvailableCarsTable();
                updateRentedCarsTable();
                updateCustomerNames();
                view.clearFields();

                updateCarStatusToBooked(regisNum);
                updateAvailableCarsTable();
            } else {
                view.displayErrorMessage("Invalid rent data. Please check the fields.");
            }
        }

        private boolean isValidRentData(String id, String regisNum, String customerName, Date rentDate, Date returnDate, String fees) {
            if (id.isEmpty() || regisNum.isEmpty() || customerName.isEmpty() ||
                    rentDate == null || returnDate == null || fees.isEmpty()) {
                return false;
            }
            // Validate rent and return date here if needed

            return true;
        }
    }

    class RentExcelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel rentedCarsModel = (DefaultTableModel) view.getRentedCarsTable().getModel();
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

    class AvailableCarsTableListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.getAvailableCarsTable().getSelectedRow();
                if (selectedRow != -1) {
                    String regisNum = (String) view.getAvailableCarsTable().getValueAt(selectedRow, 0);
                    Double price = (Double) view.getAvailableCarsTable().getValueAt(selectedRow, 4);
                    view.setRegisNum(regisNum);
                    view.setFees(String.valueOf(price));
                }
            }
        }
    }

    class RentedCarsTableListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                int  selectedRow = view.getRentedCarsTable().getSelectedRow();
                if (selectedRow != -1) {
                    String regisNum = (String) view.getRentedCarsTable().getValueAt(selectedRow, 1);
                    Double fees = (Double) view.getRentedCarsTable().getValueAt(selectedRow, 5);
                    view.setRegisNum(regisNum);
                    view.setFees(String.valueOf(fees));
                }
            }
        }
    }
    
    private void updateAvailableCarsTable() {
        DefaultTableModel model = this.getAvailableCars(view.getAvailableCarsSearchText());
        view.setAvailableCarsTable(model);
    }
    
    public DefaultTableModel getAvailableCars(String search) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Registration Num", "Model", "Brand", "Status", "Price"});

        String query = "SELECT regis_num, model, brand, status, price FROM manage_cars " +
                       "WHERE (model LIKE ? OR brand LIKE ?) AND status = 'Available'";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + search + "%");
            statement.setString(2, "%" + search + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String regisNum = resultSet.getString("regis_num");
                String modelStr = resultSet.getString("model");
                String brand = resultSet.getString("brand");
                String status = resultSet.getString("status");
                double price = resultSet.getDouble("price");

                model.addRow(new Object[]{regisNum, modelStr, brand, status, price});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }


    class AvailableCarsSearchListener implements DocumentListener {
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
            String search = view.getAvailableCarsSearchText();
            DefaultTableModel model = getAvailableCars(search);
            view.setAvailableCarsTable(model);
        }
    }
    
    private void updateRentedCarsTable() {
        DefaultTableModel model = this.getRentedCars(view.getRentedCarsSearchText());
        view.setRentedCarsTable(model);
    }
    
    public DefaultTableModel getRentedCars(String search) {
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

    class RentedCarsSearchListener implements DocumentListener {
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
            String search = view.getRentedCarsSearchText();
            DefaultTableModel model = getRentedCars(search);
            view.setRentedCarsTable(model);
        }
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


