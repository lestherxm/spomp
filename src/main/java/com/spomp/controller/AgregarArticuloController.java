/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spomp.controller;

import com.spomp.Main;
import com.spomp.model.MessagesForUser;
import java.net.URL;
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
public class AgregarArticuloController implements Initializable {

    @FXML
    private Button BtnRegresar;

    Main main;
    @FXML
    private TextArea CampoDescripcion;
    @FXML
    private TextField CampoNombre;
    @FXML
    private Button BtnRegistrarArticulo;
    
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
    private void BtnRegresarAlMantenimiento(ActionEvent event) {
         this.main.regresarAntesDeCrudMantenimiento("Articulos.fxml");
    }

    @FXML
    private void MetodoRegistrarArticulo(ActionEvent event) {
        try {
           if(CampoNombre.getText().isEmpty()||CampoDescripcion.getText().isEmpty()){
             MessagesForUser.showAlert("OYE", "TIENES QUE LLENAR TODOS LOS CAMPOS", Alert.AlertType.WARNING);
            }else{
                String sql = "INSERT INTO ARTICLE(NAME_,DESCRIPTION_) VALUES" +
                             "(?,?);";
                PreparedStatement pstmt = this.main.getPreparedStatementConnection(sql);
                    pstmt.setString(1, CampoNombre.getText());
                    pstmt.setString(2, CampoDescripcion.getText());
                        int rst = pstmt.executeUpdate();
                            if(rst>0){
                                //se registro el usuario, por favor take me home.
                                 this.main.regresarAntesDeCrudMantenimiento("Articulos.fxml");
                            }else{
                                 MessagesForUser.showAlert("OYE", "HUBO UN ERRRO AL HACER EL REGISTRO", Alert.AlertType.ERROR);
                            }
            } 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
}
