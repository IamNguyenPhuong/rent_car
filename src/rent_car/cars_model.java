package rent_car;

import java.sql.*;
import java.util.*;

public class cars_model {
    private String regisNum;
    private String brand;
    private String model;
    private String status;
    private String price;
    
    public cars_model() {
        super();
    }
    
    public cars_model(String regisNum, String brand, String model, String status, String price) {
        super();
        this.regisNum = regisNum;
        this.brand = brand;
        this.model = model;
        this.status = status;
        this.price = price;
    }
    
    public String getRegisNum() {
        return regisNum;
    }
    
    public void setRegisNum(String regisNum) {
        this.regisNum = regisNum;
    }
    
    public String getBrand() {
        return brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getPrice() {
        return price;
    }
    
    public void setPrice(String price) {
        this.price = price;
    }
}

