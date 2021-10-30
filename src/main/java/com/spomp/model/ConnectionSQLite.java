/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spomp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author 50232
 */
public class ConnectionSQLite {
    
         private Connection connection = null;
         private String pathDB = "src\\main\\resources\\config\\spomp.db";
         private String url = "jdbc:sqlite:"+pathDB;
         
         
         public ConnectionSQLite(){
             if(connection == null){
                 try {
                     Class.forName("org.sqlite.JDBC");
                     connection = DriverManager.getConnection(url);
                 } catch (ClassNotFoundException | SQLException e) {
                     e.printStackTrace();
                 }
             } // de lo contario no hacer nada ya que ya existe.
         }
         
         public Connection getConnectionSQLite(){
             
             return this.connection;
         }
         
         public void CloseConnectionSQLite(){
             try {
                 this.connection.close();
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         }
    
}
