/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbcon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author SAMSUNG
 */
public class DbConnect {
    Connection con = null;
    ResultSet rs;
    Statement st;
    
    public DbConnect() {
        getConnection();
    }
    
    public int insertDetails(String qry) {
        int ret = 0;
        try {
            ret = st.executeUpdate(qry);
        } catch (Exception e) {}
        return ret;
    }
    
    public int updateDetails(String qry) {
        int ret = 0;
        try {
            ret = st.executeUpdate(qry);
        } catch (Exception e) {}
        return ret;
    }
    
    public ResultSet selectDetails(String qry) {
        try {
            rs = st.executeQuery(qry);
        } catch (Exception e) {}
        return rs;
    }
    
    public ResultSet selectDetails1(String qry) {
//        ResultSet rs1 = null;
        try {
            Statement st1 = con.createStatement();
            rs = st1.executeQuery(qry);
        } catch (Exception e) {}
        return rs;
    }
    
    public int deleteDetails(String qry) {
        int ret = 0;
        try {
            ret = st.executeUpdate(qry);
        } catch (Exception e) {}
        return ret;
    }
    
    public void getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/project", "root", "");
            st = con.createStatement();
        } catch (Exception e) {
            System.out.println("Exception : " + e);
        }
    }
}
