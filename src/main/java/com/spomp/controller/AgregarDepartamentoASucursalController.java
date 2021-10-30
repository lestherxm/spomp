/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spomp.controller;

import com.spomp.Main;
import com.spomp.model.Department;
import com.spomp.model.MessagesForUser;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author 50232
 */
public class AgregarDepartamentoASucursalController implements Initializable {

    ObservableList ListaDeptosDeSucursal = FXCollections.observableArrayList();
    @FXML
    private Button BtnRegresar;
    @FXML
    private Button BtnAgregarDeptoASucursal;
    @FXML
    private ComboBox<Department> CbDeptosDisponibles;
    
    int IdBranch;
    Main main;
    
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

        public void setDeptosDisponibles(int IdBranch){
            this.IdBranch = IdBranch;
            ListaDeptosDeSucursal.clear();
            String sql = "SELECT NAME_ FROM DEPARTMENT WHERE NAME_ NOT IN(SELECT FK_DEPT FROM BRANCH_DEPTS WHERE FK_BRANCH=?);";
                try {
                    PreparedStatement pstmt = this.main.getPreparedStatementConnection(sql);
                       pstmt.setInt(1, IdBranch);
                       ResultSet rst = pstmt.executeQuery();
                           while(rst.next()){
                                 Department dpt = new Department();
                                 dpt.setName(rst.getString(1));
                                     ListaDeptosDeSucursal.add(dpt);
                           }
                    CbDeptosDisponibles.setItems(ListaDeptosDeSucursal);
                    this.main.CloseConnectionAfterPreparedStatement();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
        }

    @FXML
    private void BtnRegresarAlMantenimiento(ActionEvent event) {
        this.main.regresarAntesDeCrudMantenimiento("Sucursales.fxml");
    }

    @FXML
    private void MetodoAgregarDeptoASucursal(ActionEvent event) {
        try {
            if(CbDeptosDisponibles.getValue() != null){
                String sql = "INSERT INTO BRANCH_DEPTS (FK_BRANCH,FK_DEPT,N_PEOPLE) VALUES(?,?,0);";
                PreparedStatement stmt = this.main.getPreparedStatementConnection(sql);
                
                stmt.setInt(1, IdBranch);
                System.out.println(IdBranch);
                stmt.setString(2,CbDeptosDisponibles.getSelectionModel().getSelectedItem().getName());
                System.out.println(CbDeptosDisponibles.getSelectionModel().getSelectedItem().getName());    
                    int rst = stmt.executeUpdate();
                        if(rst>0){
                            //se ingreso el branch_dept tengo que regresar
                            this.main.regresarAntesDeCrudMantenimiento("Sucursales.fxml");
                        }else{
                            throw(new SQLException());
                        }
            }
        } catch (Exception e) {
            MessagesForUser.showAlert("OYE!", "HUBO UN ERROR AL INTENTAR REGISTRAR EL DEPARTAMENTO", Alert.AlertType.ERROR);
        }
    }
    
}
