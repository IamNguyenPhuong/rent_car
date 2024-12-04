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
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import java.util.*;

public class revenue_view extends JFrame {
    private JTable revenueTable;
    private DefaultTableModel tableModel;

    public revenue_view() {
        setTitle("Revenue Statistics");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Date", "Revenue"});
        revenueTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(revenueTable);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    public void setRevenueTable(DefaultTableModel model) {
        revenueTable.setModel(model);
    }

    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
