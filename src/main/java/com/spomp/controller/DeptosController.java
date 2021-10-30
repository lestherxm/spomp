/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spomp.controller;

import com.spomp.Main;
import com.spomp.model.Department;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author 50232
 */
public class DeptosController implements Initializable {

    @FXML
    private Button BtnRegresar;

    Main main;
    @FXML
    private TableView<Department> TablaDepartamentos;
    @FXML
    private TableColumn<Department, String> ColumnaNombre;
    @FXML
    private TableColumn<Department, String> ColumnaDescription;
    
    ObservableList ListaDeptos = FXCollections.observableArrayList();
    @FXML
    private Button BtnCrearDpto;
    @FXML
    private Button BtnActualizarDepto;
    @FXML
    private Button BtnEliminarDepto;
    
    public void setMain(Main main){
         this.main = main;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         relacionarTablaConClase();
    }    

    @FXML
    private void BtnRegresarAlMantenimiento(ActionEvent event) {
          this.main.regresarAlMenuMantenimientoCpy();
    }
    
    public void obtenerDatos(){
        ListaDeptos.clear();
            try {
                String sql = "SELECT * FROM DEPARTMENT;";
                Statement stmt = this.main.getStatementConnection();
                     ResultSet rst = stmt.executeQuery(sql);
                         while(rst.next()){
                             Department dp = new Department();
                                dp.setName(rst.getString(1));
                                dp.setDescription(rst.getString(2));
                                     ListaDeptos.add(dp);
                         }
                this.main.CloseConnectionAfterPreparedStatement();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
    }

    public void relacionarTablaConClase(){
        ColumnaNombre.setCellValueFactory(new PropertyValueFactory<>("Name"));
        ColumnaDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
            TablaDepartamentos.setItems(ListaDeptos);
    }

    @FXML
    private void MetodoCrearDepto(ActionEvent event) {
            this.main.cargarStageCrudMantenimiento("AgregarDepto.fxml");
    }

    @FXML
    private void MetodoActualizarDepto(ActionEvent event) {
    }

    @FXML
    private void MetodoEliminarDepto(ActionEvent event) {
    }
}
