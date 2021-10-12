/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spomp.controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;

// my own classes
import com.spomp.model.ConnectionSQLServer;
import com.spomp.model.ConnectionSQLite;
import com.spomp.model.MessagesForUser;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author 50232
 */
public class LoginController implements Initializable {

    @FXML TextField txtEmail;
    @FXML PasswordField txtPass;
    @FXML ComboBox cbRdbms;
    @FXML Button btnLogin;
    
    //This List will save the RDBMS "available"
    List<String> rdbms = new ArrayList<>();
   
    //This method runs on @initialized method
    public void addRDBMS(){
        rdbms.add("SQL SERVER");
        rdbms.add("SQLITE");
        cbRdbms.getItems().add(rdbms.get(0));
        cbRdbms.getItems().add(rdbms.get(1));       
    }
    
    @FXML
    public void Login(){
        //getting data user
        String Email = txtEmail.getText();
        String Pass  = txtPass.getText();
        String rdbmsSelected = (String) cbRdbms.getValue();
        
        if(rdbms.get(0).equals(rdbmsSelected)){
            System.out.println("SQLSERVER CONNECTION");
            try {
                String sql = "SELECT EMAIL, CONVERT(VARCHAR(255),DECRYPTBYPASSPHRASE(?,PASS)) FROM USER_ WHERE EMAIL = ?";
                ConnectionSQLServer con = new ConnectionSQLServer();
                PreparedStatement pstmt = con.getConnectionToSQLServer().prepareStatement(sql);
                pstmt.setString(1, Email);
                pstmt.setString(2, Email);
                ResultSet result = pstmt.executeQuery();
                    if(result.next()){
                        System.out.println("SE ENCONTRÓ EL CORREO");
                        String PassGetted = result.getString(2);
                            if(PassGetted.equals(Pass)){
                                System.out.println("CORREO Y PASS COINCIDEN, ENTRA");
                            }else{
                                MessagesForUser.showAlert("¡ATENCIÓN!","EL CORREO EXISTE PERO LA CONTRASEÑA ES INCORRECTA", AlertType.INFORMATION);
                            }     
                    }else{
                        MessagesForUser.showAlert("¡ATENCIÓN!","EL CORREO ELECTRÓNICO QUE INGRESÓ NO EXISTE", AlertType.INFORMATION);
                    }
                  con.CloseConnectionSQLServer();
                } catch (SQLException sqle) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, sqle);
                }
        } else if(rdbms.get(1).equals(rdbmsSelected)){
            System.out.println("SQLITE CONNECTION");
                try {
                    String sql = "SELECT EMAIL,PASS FROM USER_ WHERE EMAIL = ?;";
                    ConnectionSQLite con = new ConnectionSQLite();
                    PreparedStatement pstmt = con.getConnectionSQLite().prepareStatement(sql);
                    pstmt.setString(1, Email);
                    ResultSet result = pstmt.executeQuery();
                        if(result.next()){
                            System.out.println("SE ENCONTRÓ EL CORREO");
                            String PassGetted = result.getString(2);
                                if(PassGetted.equals(Pass)){
                                    System.out.println("CORREO Y PASS COINCIDEN, ENTRA");
                                }else{
                                    MessagesForUser.showAlert("¡ATENCIÓN!","EL CORREO EXISTE PERO LA CONTRASEÑA ES INCORRECTA", AlertType.INFORMATION);
                                }     
                        }else{
                            MessagesForUser.showAlert("¡ATENCIÓN!","EL CORREO ELECTRÓNICO QUE INGRESÓ NO EXISTE", AlertType.INFORMATION);
                        }
                      con.CloseConnectionSQLite();
                
                } catch (SQLException e) {
                      e.printStackTrace();
                }
        }else{
            //@MessagesForUser class has been created in this project [com.spomp.model.mmessagesForUser.java] by lestherxm
            MessagesForUser.showAlert("¡ATENCIÓN!","TIENES QUÉ SELECCIONAR UN SERVIDOR", AlertType.INFORMATION);
        }
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addRDBMS();
    }    
    
}
