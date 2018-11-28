package com.nyu.Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataPersistence {
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    private static final String DB_URL = "type url here";
    
    public static boolean loginCheck(String username, String password) {
        if (username == null || password == null) {
            throw new IllegalArgumentException("Username and Password can not be null!");
        }
        Connection conn = null;
        
        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, "username", "password");
            
            System.out.println("Creating statement...");  
            PreparedStatement selectPassword = conn.prepareStatement("Select password from [yourtablename] where name = ?");
            selectPassword.setString(1, username);
            ResultSet rs = selectPassword.executeQuery(); 
            while (rs.next()) {
                String passwordInDatabase = rs.getString("password");
                if (password.equals(passwordInDatabase)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }   
}
