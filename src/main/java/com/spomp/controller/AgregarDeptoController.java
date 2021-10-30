/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spomp.controller;

import com.spomp.Main;
import com.spomp.model.MessagesForUser;
import java.net.URL;
import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author 50232
 */
public class AgregarDeptoController implements Initializable {

    @FXML 
    TextField CampoNombre;
    @FXML 
    TextArea CampoDescripcion;
    @FXML 
    Button BtnRegistrarDepto;
    
    Main main;
    @FXML
    private Button BtnRegresar;
    
    public void setMain(Main main){
        this.main = main;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void MetodoRegistrarDepto(){
        try {
            if(CampoNombre.getText().isEmpty() || CampoDescripcion.getText().isEmpty()){
                System.out.println("no se puede bro");
            }else{
                String sql = "INSERT INTO DEPARTMENT(NAME_,DESCRIPTION_) VALUES(?,?);";
                    PreparedStatement pstmt = this.main.getPreparedStatementConnection(sql);
                        pstmt.setString(1, CampoNombre.getText());
                        pstmt.setString(2, CampoDescripcion.getText());
                            int rst = pstmt.executeUpdate();
                                if(rst>0){
                                     System.out.println("REGISTRO DE DEPARTAMENTO EXITOSO");
                                         this.main.regresarAntesDeCrudMantenimiento("Deptos.fxml");
                                }else{
                                     throw(new SQLException());
                                }
            }
        } catch (SQLException e) {
            MessagesForUser.showAlert("HEY", "HUBO UN ERROR AL TRATAR DE REGISTRAR AL DEPARTAMENTO :(", Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void BtnRegresarAlMantenimiento(ActionEvent event) {
        this.main.regresarAntesDeCrudMantenimiento("Deptos.fxml");
    }
    
}
