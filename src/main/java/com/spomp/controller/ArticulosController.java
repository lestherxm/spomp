/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spomp.controller;

import com.spomp.Main;
import com.spomp.model.Articulo;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author 50232
 */
public class ArticulosController implements Initializable {

    Main main; 
    @FXML
    private TableView<Articulo> TablaArticulos;
    @FXML
    private TableColumn<Articulo, Integer> ColumnaID;
    @FXML
    private TableColumn<Articulo, String> ColumnaNombre;
    @FXML
    private TableColumn<Articulo, String> ColumnaDescripcion;
    @FXML
    private Button BtnCrearArticulo;
    @FXML
    private Button BtnActualizarArticulo;
    @FXML
    private Button BtnEliminarArticulo;
    ObservableList ListaArticulos = FXCollections.observableArrayList();
    public void setMain(Main main){
         this.main = main;
    }
    
    @FXML
    private Button BtnRegresar;

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
        ListaArticulos.clear();
         try {
            String sql = "SELECT * FROM ARTICLE;";
            Statement stmt = this.main.getStatementConnection();
                ResultSet rst = stmt.executeQuery(sql);
                    while(rst.next()){
                        Articulo articulo = new Articulo();
                             articulo.setId(rst.getInt(1));
                             articulo.setName(rst.getString(2));
                             articulo.setDescription(rst.getString(3));
                             ListaArticulos.add(articulo);
                    }
             this.main.CloseConnectionAfterPreparedStatement();
        } catch (SQLException e) {
             System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void MetodoCrearArticulo(ActionEvent event) {
        this.main.cargarStageCrudMantenimiento("AgregarArticulo.fxml");
    }

    @FXML
    private void MetodoActualizarArticulo(ActionEvent event) {
    }

    @FXML
    private void MetodoEliminarArticulo(ActionEvent event) {
        
    }
    
    public void relacionarTablaConClase(){
         ColumnaID.setCellValueFactory(new PropertyValueFactory<>("Id"));
         ColumnaNombre.setCellValueFactory(new PropertyValueFactory<>("Name"));
         ColumnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("Description"));
            TablaArticulos.setItems(ListaArticulos);
    }
    
}
