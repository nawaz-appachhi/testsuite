package com.myntra.apiTests.portalservices.lgpservices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySqlDBConnectionHelper {
	
	 
    private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    private final String DB_URL = "jdbc:mysql://localhost/database_name";
    
    private static final String USER = "username";
    private static final String PASS = "password";
    
    private Connection conn = null;
    private Statement stmt = null;
    
    public MySqlDBConnectionHelper() {
        
        try {
            Class.forName(JDBC_DRIVER);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void open() {
        try {
            
            conn = DriverManager.getConnection(DB_URL, USER,PASS);
           
            stmt = conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(MySqlDBConnectionHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void close() {
        try {
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(MySqlDBConnectionHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

}
