/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spomp.controller;

import com.spomp.Main;
import com.spomp.model.Branch;
import com.spomp.model.Department;
import com.spomp.model.MessagesForUser;
import com.spomp.model.User;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author 50232
 */
public class CrudUsuariosController implements Initializable {

    @FXML
    private Button BtnRegresar;
    @FXML
    private TextField CampoNombre;
    @FXML
    private TextField CampoApellido;
    @FXML
    private TextField CampoEmail;
    @FXML
    private PasswordField CampoPass;
    @FXML
    private ComboBox<?> CbTipoUsuario;
    @FXML
    private ComboBox<?> CbSucursal;
    @FXML
    private ComboBox<?> CbDepto;
    
    //variables de clase
    Main main;
    //ObservableList ol = FXCollections.observableArrayList();
    ObservableList ListaTipo_Perfil = FXCollections.observableArrayList();
    ObservableList ListaSucursales = FXCollections.observableArrayList();
    ObservableList ListaDeptosDeSucursal = FXCollections.observableArrayList();
    @FXML
    private Button BtnRegistrar;
    Branch branchSelected;
    
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

    public void obtenerDatosDeRegistro(){
        try {
            setearTipoUsuario();
                setearSucursalesDisponibles();
                    aplicarSelectionModelASucursales();
        } catch (Exception e) {
        }
    }
    
    public void setearTipoUsuario(){
        ListaTipo_Perfil.clear();
        try {
             String sql = "SELECT NAME_ FROM TYPE_U WHERE NAME_ LIKE 'EMPRESA - %';";
             Statement stmt = this.main.getStatementConnection();
                ResultSet rst = stmt.executeQuery(sql);
                    while(rst.next()){
                         ListaTipo_Perfil.add(rst.getString(1));
                    }
             CbTipoUsuario.setItems(ListaTipo_Perfil);
             this.main.CloseConnectionAfterPreparedStatement();
        } catch (Exception e) {
             System.out.println(e.getMessage());
             e.printStackTrace();
        }   
    }
    
    public void setearSucursalesDisponibles(){
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
                            ListaSucursales.add(branch);
                }
             CbSucursal.setItems(ListaSucursales);
             this.main.CloseConnectionAfterPreparedStatement();
        } catch (Exception e) {
             System.out.println(e.getMessage());
             e.printStackTrace();
        }
        
    }
    
    public void aplicarSelectionModelASucursales(){
        CbSucursal.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                branchSelected = new Branch();
                branchSelected = (Branch) CbSucursal.getSelectionModel().getSelectedItem();
                setearDeptosDeSucursal(branchSelected.getId());
                    CbDepto.setDisable(false); // se deshabilita el ComboBox para elegir departamento
            }
        });
    }
    
    public void setearDeptosDeSucursal(int Id){
         ListaDeptosDeSucursal.clear();
         String sql = "SELECT FK_DEPT, N_PEOPLE FROM BRANCH_DEPTS WHERE FK_BRANCH=?;";
             try {
                 PreparedStatement pstmt = this.main.getPreparedStatementConnection(sql);
                    pstmt.setInt(1, Id);
                    ResultSet rst = pstmt.executeQuery();
                        while(rst.next()){
                              Department dpt = new Department();
                              dpt.setName(rst.getString(1));
                              dpt.setN_People(rst.getInt(2));
                                  ListaDeptosDeSucursal.add(dpt);
                        }
                 CbDepto.setItems(ListaDeptosDeSucursal);
                 this.main.CloseConnectionAfterPreparedStatement();
             } catch (SQLException e) {
                 System.out.println(e.getMessage());
                 e.printStackTrace();
             }
    }
    
    @FXML
    private void MetodoRegresarAntesDeCrudMantenimiento(ActionEvent event) {
        this.main.regresarAntesDeCrudMantenimiento("Usuarios.fxml");
    }

    @FXML
    private void MetodoRegistrarUsuario(ActionEvent event) {
        try {
            validarCamposVacios();
            String sql = "";
                if(this.main.getRdbmsGlobal().equals("SQL SERVER")){
                     sql ="INSERT INTO USER_(NAME_, SURNAME, EMAIL, PASS, FK_TYPE_U,FK_BRANCH,FK_DEPT)" +
                     "VALUES (?,?,?,ENCRYPTBYPASSPHRASE(?,?),?,?,?);";
                     PreparedStatement pstmt = this.main.getPreparedStatementConnection(sql);
                     User user = getDataUser();
                         pstmt.setString(1, user.getName());
                         pstmt.setString(2, user.getSurname());
                         pstmt.setString(3, user.getEmail());
                         pstmt.setString(4, user.getEmail());
                         pstmt.setString(5, user.getPass());
                         pstmt.setInt(6, user.getFK_TypeUser());
                         pstmt.setInt(7, user.getFK_Branch());
                         pstmt.setString(8,user.getDepto());
                            int rst = pstmt.executeUpdate();
                                if(rst>0){
                                     System.out.println("REGISTRO DE USUARIO EXITOSO");
                                         this.main.regresarAntesDeCrudMantenimiento("Usuarios.fxml");
                                }else{
                                    throw(new SQLException());
                                }
                }else{
                     sql ="INSERT INTO USER_(NAME_, SURNAME, EMAIL, PASS, FK_TYPE_U,FK_BRANCH,FK_DEPT)" +
                     "VALUES (?,?,?,?,?,?,?);";
                     PreparedStatement pstmt = this.main.getPreparedStatementConnection(sql);
                     User user = getDataUser();
                         pstmt.setString(1, user.getName());
                         pstmt.setString(2, user.getSurname());
                         pstmt.setString(3, user.getEmail());
                         pstmt.setString(4, user.getPass());
                         pstmt.setInt(5, user.getFK_TypeUser());
                         pstmt.setInt(6, user.getFK_Branch());
                         pstmt.setString(7,user.getDepto());
                            int rst = pstmt.executeUpdate();
                                if(rst>0){
                                     System.out.println("REGISTRO DE USUARIO EXITOSO");
                                         this.main.regresarAntesDeCrudMantenimiento("Usuarios.fxml");
                                }else{
                                    throw(new SQLException());
                                }
                }
             this.main.CloseConnectionAfterPreparedStatement();
        } catch (Exception e) {
            MessagesForUser.showAlert("HEY", "HUBO UN ERROR AL TRATAR DE REGISTRAR AL USUARIO :(", Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
                  
    }
    
    public User getDataUser(){
        User user = new User();
             user.setName(CampoNombre.getText());
             user.setSurname(CampoApellido.getText());
             user.setEmail(CampoEmail.getText());
             user.setPass(CampoPass.getText());
             user.setFK_TypeUser(1); // EMPRESA: ADMINISTRRADOR POR EL MONENTO SOLO ES ACPETABLE ESE POR ESO ESTÁ QUEMADO
             user.setFK_Branch(branchSelected.getId());
        return user;
    }
    
    public void validarCamposVacios(){
         Boolean Nombre = CampoNombre.getText().isEmpty();
         Boolean Apellido = CampoApellido.getText().isEmpty();
         Boolean Email = CampoEmail.getText().isEmpty();
         Boolean Pass = CampoPass.getText().isEmpty();
         System.out.println(CbTipoUsuario.getValue());
         System.out.println(CbSucursal.getValue());
         System.out.println(CbDepto.getValue());
         Boolean TipoUsuario = CbTipoUsuario.getValue() == null;
         Boolean Sucursal = CbSucursal.getValue() == null;
         Boolean DeptoSucursal = CbDepto.getValue() == null;
         
         if(Nombre || Apellido || Email || Pass || TipoUsuario || Sucursal || DeptoSucursal){
              MessagesForUser.showAlert("OYE", "ASEGÚRATE DE INGRESAR TODOS LOS CAMPOS", Alert.AlertType.WARNING);
         }else{
              validarCorreoElectronico(CampoEmail.getText());
         }
         
    }
    
         public void validarCorreoElectronico(String Email){
             System.out.println(Email); //email a validar
             // Patrón para validar el email
                Pattern pattern = Pattern
                        .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
 
                    Matcher mather = pattern.matcher(Email);

                    if (mather.find() == true) {
                        System.out.println("El email ingresado es válido.");
                    } else {
                        MessagesForUser.showAlert("OYE", "EL EMAIL QUE HAS INGRESADO ES INVÁLIDO", Alert.AlertType.ERROR);
                    }
         }
    
}
