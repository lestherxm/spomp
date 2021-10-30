/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spomp.controller;

import com.spomp.Main;
import com.spomp.model.Branch;
import com.spomp.model.Department;
import com.spomp.model.Telefonos;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
public class SucursalesController implements Initializable {

    @FXML
    private Button BtnRegresar;
    @FXML
    private TableView<Branch> TablaSucursales;
    @FXML
    private TableColumn<Branch, String> ColumnaDepartamento;
    @FXML
    private TableColumn<Branch, String> ColumnaMunicipio;
    @FXML
    private TableColumn<Branch, String> ColumnaDirection;
    @FXML
    private Button BtnCrearSucursal;
    @FXML
    private Button BtnActualizarSucursal;
    @FXML
    private Button BtnEliminarSucursal;
    @FXML
    private TableView<Department> TablaDeptoSucursales;
     @FXML
    private TableColumn<Department, String> ColumnaDeptoSucursales;
     @FXML
    private TableColumn<Department, Integer> ColumnaN_People;
    @FXML
    private Button BtnCrearDeptoSucursal;
    @FXML
    private Button BtnActualizarDeptoSucursal;
    @FXML
    private Button BtnEliminarDeptoSucursal;
    
    ObservableList ListaSucursales = FXCollections.observableArrayList();
    ObservableList ListaDeptosDeSucursal = FXCollections.observableArrayList();
    ObservableList ListaTelefonos = FXCollections.observableArrayList();
    Branch branchSelected;

    Main main;
    @FXML
    private TableView<Telefonos> TablaTelefonosSucursal;
    @FXML
    private TableColumn<Telefonos, String> ColumnaTelefonosSucursal;
    @FXML
    private Button BtnCrearTelefonoSucursal;
    @FXML
    private Button BtnActualizarDeptoSucursal1;
    @FXML
    private Button BtnEliminarTelefonoSucursal;
    public void setMain(Main main){
         this.main = main;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         relacionarTablaConClase();
            aplicarSelectionModel();
    }    

    public void obtenerDatos(){
        ListaSucursales.clear();
        try {
            String sql = "SELECT ID, FK_DEPT_GT, FK_CITY_GT, DIRECTION FROM BRANCH;";
            Statement stmt = this.main.getStatementConnection();
            ResultSet rst = stmt.executeQuery(sql);
                while(rst.next()){
                    Branch branch = new Branch();
                        branch.setId(rst.getInt(1));
                        branch.setFk_DeptGt(rst.getString(2));
                        branch.setFk_cityGt(rst.getString(3));
                        branch.setDirection(rst.getString(4));
                            System.out.println(branch);
                            ListaSucursales.add(branch);
                }
             this.main.CloseConnectionAfterPreparedStatement();
        } catch (Exception e) {
            System.out.println("errr al obtener datos");
             System.out.println(e.getMessage());
             e.printStackTrace();
        }
    }
    
    @FXML
    private void BtnRegresarAlMantenimiento(ActionEvent event) {
        this.main.regresarAlMenuMantenimientoCpy();
    }

    @FXML
    private void MetodoCrearSucursal(ActionEvent event) {
    }

    @FXML
    private void MetodoActualizarSucursal(ActionEvent event) {
    }

    @FXML
    private void MetodoEliminarSucursal(ActionEvent event) {
    }

    @FXML
    private void MetodoCrearDeptoSucursal(ActionEvent event) {
        this.main.cargarStageCrudMantenimiento("agregarDepartamentoASucursal.fxml");
        this.main.AgregarDepartamentoASucursalCont.setDeptosDisponibles(branchSelected.getId());
    }
    
    @FXML
    private void MetodoActualizarArticulo(ActionEvent event) {
    }

    @FXML
    private void MetodoEliminarArticulo(ActionEvent event) {
    }
    
    public void relacionarTablaConClase(){
        try {
             //sucursales
             ColumnaDepartamento.setCellValueFactory(new PropertyValueFactory<>("Fk_DeptGt"));
             ColumnaMunicipio.setCellValueFactory(new PropertyValueFactory<>("Fk_cityGt"));
             ColumnaDirection.setCellValueFactory(new PropertyValueFactory<>("Direction"));
                TablaSucursales.setItems(ListaSucursales);
             //departamentos de la sucursal
             ColumnaDeptoSucursales.setCellValueFactory(new PropertyValueFactory<>("Name"));
             ColumnaN_People.setCellValueFactory(new PropertyValueFactory<>("N_People"));
                    TablaDeptoSucursales.setItems(ListaDeptosDeSucursal);
             //tleefonods de la sucursal       
             ColumnaTelefonosSucursal.setCellValueFactory(new PropertyValueFactory<>("Telefono"));
                    TablaTelefonosSucursal.setItems(ListaTelefonos);
                    
        } catch (Exception e) {
             System.out.println("error al relacioanr tabla con clase");
        }
    }
    
    public void aplicarSelectionModel(){
        TablaSucursales.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                branchSelected = new Branch();
                branchSelected = (Branch) TablaSucursales.getSelectionModel().getSelectedItem();
                setearDeptosDeSucursal(branchSelected.getId());
                setearTelefonosSucursal(branchSelected.getId());    
                    BtnCrearDeptoSucursal.setDisable(false);
                    BtnCrearTelefonoSucursal.setDisable(false);
            }
        });
        
    }
    
    public void setearDeptosDeSucursal(int IdBranch){
         ListaDeptosDeSucursal.clear();
         String sql = "SELECT FK_DEPT, N_PEOPLE FROM BRANCH_DEPTS WHERE FK_BRANCH=?;";
             try {
                 PreparedStatement pstmt = this.main.getPreparedStatementConnection(sql);
                    pstmt.setInt(1, IdBranch);
                    ResultSet rst = pstmt.executeQuery();
                        while(rst.next()){
                              Department dpt = new Department();
                              dpt.setName(rst.getString(1));
                              dpt.setN_People(rst.getInt(2));
                                  ListaDeptosDeSucursal.add(dpt);
                        }
                 this.main.CloseConnectionAfterPreparedStatement();
             } catch (SQLException e) {
                 System.out.println(e.getMessage());
                 e.printStackTrace();
             }
    }

    public void setearTelefonosSucursal(int IdBranch){
        ListaTelefonos.clear();
            try {
                String sql =  "SELECT PHONE FROM PHONE WHERE FK_BRANCH=?";
                PreparedStatement stmt = this.main.getPreparedStatementConnection(sql);
                    stmt.setInt(1, IdBranch);
                        ResultSet rst = stmt.executeQuery();
                            while(rst.next()){
                                Telefonos telefono = new Telefonos();
                                telefono.setTelefono(rst.getString(1));
                                    ListaTelefonos.add(telefono);
                            }
                this.main.getPreparedStatementConnection(sql);
            } catch (Exception e) {
                 e.printStackTrace();
            }
    }
    
    @FXML
    private void MetodoCrearTelefonoSucursal(ActionEvent event) {
    }

    @FXML
    private void MetodoEliminarTelefonoSucursal(ActionEvent event) {
    }
    
}
