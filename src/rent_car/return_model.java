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
public class return_model {
    private String id;
    private String regisNum;
    private String customerName;
    private String returnDate;
    private int delay;
    private double fine;

    public return_model() {
        super();
    }

    public return_model(String id, String regisNum, String customerName, String returnDate, int delay, double fine) {
        super();
        this.id = id;
        this.regisNum = regisNum;
        this.customerName = customerName;
        this.returnDate = returnDate;
        this.delay = delay;
        this.fine = fine;
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

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }
}
