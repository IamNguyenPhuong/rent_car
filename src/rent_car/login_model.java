/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rent_car;

import java.sql.*;

/**
 *
 * @author ASUS
 */
public class login_model {
    private String username;
    private String password;
    
    public login_model(){
        super();
    }
    
    public login_model(String username, String password){
        super();
        this.username=username;
        this.password=password;
    }
    
    public String getUsername(){
        return username;
    }
    
    public void setUsername(String username){
        this.username=username;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){
        this.password=password;
    }
}