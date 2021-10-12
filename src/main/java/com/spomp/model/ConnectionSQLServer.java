/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spomp.model;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author 50232
 */
public class ConnectionSQLServer {
    
    private Properties props;
    private InputStream in;
    private String Path;
    private Connection myConnection = null; // The connection variable will have that value when a new object of this class is created.
    
    //constructor
    public ConnectionSQLServer(){
        if(myConnection == null){ //this way I make sure I don't have multiple connections
            try {
                getPropertiesSQLServer();
                String ConnectionData = LoadPropertiesSQLServer();
                myConnection = DriverManager.getConnection(ConnectionData);
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
    }
            
    //This method runs on @getConnectionToSQLServer method [Uing SQLServer]
    private void getPropertiesSQLServer(){
        try{
            props = new Properties();
            Path = "C:\\spomp\\sqlserver.properties"; 
            in = Files.newInputStream(FileSystems.getDefault().getPath(Path));
            props.load(in);
            in.close();   
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    //This method runs on @getConnectionToSQLServer method [Uing SQLServer]
    private String LoadPropertiesSQLServer(){
         try {
            String host = props.getProperty("host");
            String port = props.getProperty("port");
            String database = props.getProperty("database");
            String user = props.getProperty("user");
            String password = props.getProperty("password");
            String loginTimeOut = props.getProperty("loginTimeOut");
            String ConnectionData = "jdbc:sqlserver://"+host+":"+port+";"
                                 + "database="+database+";"
                                 + "user="+user+";"
                                 + "password="+password+";"
                                 + "loginTimeOut="+loginTimeOut+";";
            return ConnectionData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    //This method runs on all the app when the user do changes in the system (CRUD) [Uing SQLServer]
    public Connection getConnectionToSQLServer(){
        return this.myConnection;
    }
    
    //This method runs on all the app when the user do changes in the system (CRUD) [Uing SQLServer]
    public void CloseConnectionSQLServer(){
        try {
            this.myConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
