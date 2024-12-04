/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rent_car;

import java.awt.event.*;
import java.sql.*;
/**
 *
 * @author ASUS
 */
public class login_controller {
    private login_view view;
    private login_model model;
    private Connection connection;
    
    public login_controller(login_view view, login_model model) {
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

        this.view.addLoginListener(new LoginListener());
    }
    
    public login_controller() {
        this(null, null);
    }
    
    public boolean authenticate(String username, String password) {
        String query = "SELECT * FROM login WHERE username = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Nếu có dòng dữ liệu tức là đăng nhập thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = view.getUsername();
            String password = view.getPassword();

            if (authenticate(username, password)) {
                view.displayMessage("Login successful!");
                view.hideLoginView(); // Ẩn cửa sổ đăng nhập

                // Hiển thị cửa sổ quản lý xe
                cars_model carsModel = new cars_model();
                cars_view carsView = new cars_view();
                cars_controller carsController = new cars_controller(carsView, carsModel);
                carsView.setVisible(true);
            } else {
                view.displayMessage("Login failed. Please check your username and password.");
            }
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
