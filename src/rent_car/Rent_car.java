/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rent_car;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author ASUS
 */
public class Rent_car {

    /**
     * @param args the comman9d line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
      login_model loginModel = new login_model();
       login_view loginView = new login_view();
      login_controller loginController = new login_controller(loginView, loginModel);
     loginView.setVisible(true);

//        revenue_model revenueModel = new revenue_model();
//        revenue_view revenueView = new revenue_view();
//        revenue_controller revenueController = new revenue_controller(revenueView, revenueModel);
//        revenueView.setVisible(true);
//        
//        cars_model carsModel = new cars_model();
//        cars_view carsView = new cars_view();
//        cars_controller carsController = new cars_controller(carsView, carsModel);
//        carsView.setVisible(true);
        
       // customers_model customersModel = new customers_model();
        //customers_view customersView = new customers_view();
       // customers_controller customersController = new customers_controller(customersView, customersModel);
      //  customersView.setVisible(true);

//        rent_model rentModel = new rent_model();
//        rent_view rentView = new rent_view();
//        rent_controller rentController = new rent_controller(rentView, rentModel);
//        rentView.setVisible(true); 

//        return_view returnView = new return_view();
//        return_model returnModel = new return_model();
//        return_controller returnController = new return_controller(returnView, returnModel);
//        returnView.setVisible(true);
    }
}
