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
public class rent_model {
    private String id;
    private String regisNum;
    private String customerName;
    private String rentDate;
    private String returnDate;
    private String fees;

    public rent_model() {
        super();
    }

    public rent_model(String id, String regisNum, String customerName, String rentDate, String returnDate, String fees) {
        super();
        this.id = id;
        this.regisNum = regisNum;
        this.customerName = customerName;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.fees = fees;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegisNum() {
        return regisNum;
    }

    public void setRegisNum(String regisNum) {
        this.regisNum = regisNum;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getRentDate() {
        return rentDate;
    }

    public void setRentDate(String rentDate) {
        this.rentDate = rentDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }
}