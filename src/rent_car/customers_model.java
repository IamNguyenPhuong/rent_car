/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rent_car;

import java.sql.*;
import java.util.*;

public class customers_model {
    private String id;
    private String name;
    private String address;
    private String phone;
    
    public customers_model() {
        super();
    }
    
    public customers_model(String id, String name, String address, String phone) {
        super();
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
}


