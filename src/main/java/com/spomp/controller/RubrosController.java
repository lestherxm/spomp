/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spomp.controller;

import com.spomp.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author 50232
 */
public class RubrosController implements Initializable {

    
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
    private void BtnRegresarAlMantenimiento(ActionEvent event) {
         this.main.regresarAlMenuMantenimientoCpy();
    }
    
    public void obtenerDatos(){
        
    }
}
