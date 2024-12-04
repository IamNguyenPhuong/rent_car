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
import java.sql.*;
import java.util.*;

public class account_controller {
    private account_view view;
    private account_model model;
    private Connection connection;

    public account_controller(account_view view, account_model model) {
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
        this.view.addSwitchtoCarButtonListener(new SwitchToCarsButtonListener());
        this.view.addSwitchtoCustomerButtonListener(new SwitchtoCustomerButtonListener());
        this.view.addSwitchToRentButtonListener(new SwitchToRentButtonListener());
        this.view.addSwitchToReturnButtonListener(new SwitchToReturnButtonListener());



        // Lấy danh sách xe từ model và thêm vào view
        String[][] cars = getAllAccount();
        for (String[] car : cars) {
            view.addAccount(car);
        }
        
        this.view.addMouseListener(new AccountTableMouseListener());

        view.setVisible(false);
    }
    public String[][] getAllAccount() {
        String query = "SELECT * FROM login";
        List<String[]> accountList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                String[] car = {username, password};
                accountList.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accountList.toArray(new String[0][0]);
    }
    
    private boolean isValidAccountData(String[] accountData) {
        // Kiểm tra dữ liệu xe hợp lệ
        // Ở đây có thể thêm các kiểm tra khác tùy theo yêu cầu
        if (accountData[0].isEmpty() || accountData[1].isEmpty() ) {
            return false;
        }
        return true;
    }
    
    public boolean addAccount(String username, String password) {
        if (connection == null) {
            return false;
        }

        String query = "INSERT INTO login (username, password) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
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
            String[] accountData = view.getAccountData();

            if (isValidAccountData(accountData)) {
                boolean success = addAccount(accountData[0], accountData[1]);
                if (success) {
                    view.addAccount(accountData);
                    view.clearFields();
                } else {
                    view.displayErrorMessage("Failed to add car.");
                }
            } else {
                view.displayErrorMessage("Invalid car data. Please check the fields.");
            }
        }
    }
    
    public boolean editAccount(String username, String password) {
        if (connection == null) {
            return false;
        }

        String query = "UPDATE login SET username = ?, password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
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
            int selectedRow = view.accountTable.getSelectedRow();
            if (selectedRow == -1) {
                view.displayErrorMessage("Please select a car to edit.");
                return;
            }

            String[] accountData = view.getAccountData();

            if (isValidAccountData(accountData)) {
                boolean success = editAccount(accountData[0], accountData[1]);
                if (success) {
                    view.editCar(selectedRow, accountData);
                    view.clearFields();
                } else {
                    view.displayErrorMessage("Failed to edit car.");
                }
            } else {
                view.displayErrorMessage("Invalid car data. Please check the fields.");
            }
        }
    }

    
    public boolean deleteAccount(String id) {
        if (connection == null) {
            return false;
        }

        String query = "DELETE FROM login WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
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
            int selectedRow = view.accountTable.getSelectedRow();
            if (selectedRow == -1) {
                view.displayErrorMessage("Please select a car to delete.");
                return;
            }

            String registrationNumber = view.getSelectedRegistrationNumber();
            if (registrationNumber != null) {
                boolean success = deleteAccount(registrationNumber);
                if (success) {
                    view.deleteCar(selectedRow);
                } else {
                    view.displayErrorMessage("Failed to delete car.");
                }
            }
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

    
    class SwitchtoCustomerButtonListener implements ActionListener {
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
    
    class AccountTableMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int selectedRow = view.accountTable.getSelectedRow();
            if (selectedRow != -1) {
                view.usernameField.setText((String) view.tableModel.getValueAt(selectedRow, 0));
                view.passwordField.setText((String) view.tableModel.getValueAt(selectedRow, 1));
            }
        }
    }

}
