/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spomp.controller;

import com.spomp.Main;
import com.spomp.model.User;
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
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author 50232
 */
public class UsuariosController implements Initializable {

    @FXML
    private Button BtnRegresar;
    @FXML
    private TableView<User> TablaUsuarios;
    @FXML
    private TableColumn<User, String> ColumnaNombre;
    @FXML
    private TableColumn<User, String> ColumnaApellido;
    @FXML
    private TableColumn<User, String> ColumnaEmail;
    @FXML
    private TableColumn<User, String>ColumnaTipoPerfil;
    @FXML
    private TableColumn<User, String> ColumnaSucursal;
    @FXML
    private Button BtnCrearUsuario;
    @FXML
    private Button BtnActualizarUsuario;
    @FXML
    private Button BtnEliminarUsuario;

    Main main;
    String rdbms;
    ObservableList ListaUsuarios = FXCollections.observableArrayList();
    
    public void setMain(Main main, String rdbms){
        this.main = main;
        this.rdbms = rdbms;
        System.out.println("SET MAIN USUARIOS CONTROLLER CON: " + this.rdbms);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          relacionarTablaConClaseUsuario();
    }    

    public void obtenerUsuarios(){
        ListaUsuarios.clear();
         try {
            String sql = "SELECT DISTINCT u.NAME_, u.SURNAME, u.EMAIL, t.NAME_ , b.FK_DEPT_GT, b.FK_CITY_GT, b.DIRECTION FROM USER_ u INNER JOIN TYPE_U t ON u.FK_TYPE_U = t.ID INNER JOIN BRANCH b ON u.FK_BRANCH = b.ID;";
            String test = this.main.getRdbmsGlobal();
            System.out.println(test);
            Statement stmt = this.main.getStatementConnection();
            ResultSet rst = stmt.executeQuery(sql);
                 while(rst.next()){
                     User user = new User();
                         user.setName(rst.getString(1));
                         user.setSurname(rst.getString(2));
                         user.setEmail(rst.getString(3));
                         user.setTypeUser(rst.getString(4));
                         String depto = rst.getString(5);
                         String munic = rst.getString(6);
                         String direc = rst.getString(7);
                         String branc = depto + ", " + munic + ", " + direc;
                         user.setBranch(branc);
                            ListaUsuarios.add(user);
                 }
             this.main.CloseConnectionAfterPreparedStatement();
        } catch (Exception e) {
             System.out.println("ERROR METODO OBTENER USUARIOS" + e.getMessage());
             System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    private void BtnRegresarAlMantenimiento(ActionEvent event) {
         this.main.regresarAlMenuMantenimientoCpy();
    }

    @FXML
    private void MetodoCrearUsuario(ActionEvent event) {
         this.main.cargarStageCrudMantenimiento("CrudUsuarios.fxml");
    }

    @FXML
    private void MetodoActualizarUsuario(ActionEvent event) {
    }

    @FXML
    private void MetodoEliminarUsuario(ActionEvent event) {
    }
    
    public void relacionarTablaConClaseUsuario(){
       ColumnaNombre.setCellValueFactory(new PropertyValueFactory<>("Name"));
       ColumnaApellido.setCellValueFactory(new PropertyValueFactory<>("Surname"));
       ColumnaEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
       ColumnaTipoPerfil.setCellValueFactory(new PropertyValueFactory<>("TypeUser"));
       ColumnaSucursal.setCellValueFactory(new PropertyValueFactory<>("Branch"));
               TablaUsuarios.setItems(ListaUsuarios);
    }
    
}
