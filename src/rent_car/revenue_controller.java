/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rent_car;

/**
 *
 * @author Admin
 */
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.*;
import java.util.*;
import java.text.*;

public class revenue_controller {
    private revenue_view view;
    private revenue_model model;

    public revenue_controller(revenue_view view, revenue_model model) {
        this.view = view;
        this.model = model;

        // Initialize view
        DefaultTableModel tableModel = getRevenueTable();
        view.setRevenueTable(tableModel);
    }

    public DefaultTableModel getRevenueTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Date", "Revenue"});

        // Query database to get revenue statistics
        try {
            // Connect to the database
            String url = "jdbc:mysql://localhost:3306/rent_car";
            String username = "root";
            String password = "";
            Connection connection = DriverManager.getConnection(url, username, password);

            // Retrieve revenue statistics
            String query = "SELECT return_date, SUM(fine) AS total_revenue FROM manage_return GROUP BY return_date";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String date = resultSet.getString("return_date");
                double revenue = resultSet.getDouble("total_revenue");
                model.addRow(new Object[]{date, revenue});
            }

            // Close connections
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            view.displayErrorMessage("Error fetching revenue statistics.");
        }

        return model;
    }
}

