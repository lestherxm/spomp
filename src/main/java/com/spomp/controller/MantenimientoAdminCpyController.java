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
public class MantenimientoAdminCpyController implements Initializable {

    @FXML
    private Button BtnRegresar;
    @FXML
    private Button BtnMUsuarios;
    @FXML
    private Button BtnMDeptos;
    @FXML
    private Button BtnMSucursales;
    @FXML
    private Button BtnMArticulos;
    @FXML
    private Button BtnMRubros;
    @FXML
    private Button BtnMProveedores;

    Main main;
    String rdbms;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void BtnRegresarAlMenu(ActionEvent event) {
         this.main.cargarMenuAdminCpyDesdeOptionMenu();
    }
    
    public void setMain(Main main, String rdbms){
         this.main = main;
         this.rdbms = rdbms;
         System.out.println("SET MAIN MANTENIMIENTO ADMIN CPY CON: " + this.rdbms);
    }

    @FXML
    private void MetodoMUsuarios(ActionEvent event) {
         this.main.cargarOpcionDeMantenimientoCpy("Usuarios.fxml", this.rdbms);
    }

    @FXML
    private void MetodoMDeptos(ActionEvent event) {
         this.main.cargarOpcionDeMantenimientoCpy("Deptos.fxml", this.rdbms);
    }

    @FXML
    private void BtnMSucursales(ActionEvent event) {
         this.main.cargarOpcionDeMantenimientoCpy("Sucursales.fxml",this.rdbms);
    }

    @FXML
    private void MetodoMArticulos(ActionEvent event) {
         this.main.cargarOpcionDeMantenimientoCpy("Articulos.fxml",this.rdbms);
    }

    @FXML
    private void BtnMRubros(ActionEvent event) {
         this.main.cargarOpcionDeMantenimientoCpy("Rubros.fxml",this.rdbms);
    }

    @FXML
    private void BrnMProveedores(ActionEvent event) {
         this.main.cargarOpcionDeMantenimientoCpy("Proveedores.fxml",this.rdbms);
    }
    
}
