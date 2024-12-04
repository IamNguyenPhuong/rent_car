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
import java.sql.*;

public class revenue_model {
    private Connection connection;

    public revenue_model() {
        try {
            // Connect to the database
            String url = "jdbc:mysql://localhost:3306/rent_car";
            String username = "root";
            String password = "";
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getTotalRevenueForDate(String date) {
        double totalRevenue = 0.0;

        String query = "SELECT SUM(fine) AS total_revenue FROM manage_return WHERE return_date = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, date);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                totalRevenue = resultSet.getDouble("total_revenue");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalRevenue;
    }
}

