/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spomp.controller;

import com.spomp.Main;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;

// my own classes
import com.spomp.model.MessagesForUser;
import com.spomp.model.User;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author 50232
 */
public class LoginController implements Initializable {

    @FXML 
    private TextField txtEmail;
    @FXML 
    private PasswordField txtPass;
    @FXML
    private ComboBox cbRdbms;
    @FXML
    private Button BtnLogin;
  
    //This List will save the RDBMS "available"
    List<String> rdbms = new ArrayList<>();

    //Variables de clase
    Main main;
    String rdbmsSelected;
    public void setMain(Main main){
        this.main = main;
    }
   
    //This method runs on @initialized method
    public void addRDBMS(){
        rdbms.add("SQL SERVER");
        rdbms.add("SQLITE");
        cbRdbms.getItems().add(rdbms.get(0));
        cbRdbms.getItems().add(rdbms.get(1));       
    }
    
    @FXML
    public void Login(){
        //getting data user
        String Email = txtEmail.getText();
        String Pass  = txtPass.getText();
        rdbmsSelected = (String) cbRdbms.getValue();
     
        if(rdbms.get(0).equals(rdbmsSelected)){
            this.main.setRdbmsGlobal("SQL SERVER");
            System.out.println("SQLSERVER CONNECTION");
            try {
                
                String sql =    "SELECT ID, NAME_ , SURNAME , FK_TYPE_U ," +
                                "CONVERT(VARCHAR(255),DECRYPTBYPASSPHRASE(?,PASS)) " +
                                "FROM USER_ WHERE EMAIL = ?;";
                
                //ConnectionSQLServer con = new ConnectionSQLServer();
                //PreparedStatement pstmt = con.getConnectionToSQLServer().prepareStatement(sql);
                PreparedStatement pstmt = this.main.getPreparedStatementConnection(sql);
                pstmt.setString(1, Email);
                pstmt.setString(2, Email);
                ResultSet result = pstmt.executeQuery();
                    if(result.next()){
                        System.out.println("SE ENCONTRÓ EL CORREO");
                        User user = new User();
                        user.setId(result.getInt("ID"));
                        user.setName(result.getString("NAME_"));
                        user.setSurname(result.getString("SURNAME"));
                        user.setFK_TypeUser(result.getInt("FK_TYPE_U"));
                        String PassGetted = result.getString(5); //password
                            if(PassGetted.equals(Pass)){
                                System.out.println("CORREO Y PASS COINCIDEN, ENTRA");
                                     ValidarTipoUsuario(user); //valido el tipo de usuario con base en los valores recogidos
                            }else{
                                MessagesForUser.showAlert("¡ATENCIÓN!","EL CORREO EXISTE PERO LA CONTRASEÑA ES INCORRECTA", AlertType.INFORMATION);
                            }     
                    }else{
                        MessagesForUser.showAlert("¡ATENCIÓN!","EL CORREO ELECTRÓNICO QUE INGRESÓ NO EXISTE", AlertType.INFORMATION);
                    }
                  //System.out.println("CONEXION SQL SERVER CERRADA POST LOGIN");
                  //con.CloseConnectionSQLServer();
                  this.main.CloseConnectionAfterPreparedStatement();
                  
                } catch (SQLException sqle) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, sqle);
                }
        } else if(rdbms.get(1).equals(rdbmsSelected)){
            this.main.setRdbmsGlobal("SQLITE");
            System.out.println("SQLITE CONNECTION");
                try {
                    String sql = "SELECT ID, NAME_, SURNAME, FK_TYPE_U,PASS FROM USER_ WHERE EMAIL =?;";
                    //ConnectionSQLite con = new ConnectionSQLite();
                    //PreparedStatement pstmt = con.getConnectionSQLite().prepareStatement(sql);
                    PreparedStatement pstmt = this.main.getPreparedStatementConnection(sql);
                    pstmt.setString(1, Email);
                    ResultSet result = pstmt.executeQuery();
                        if(result.next()){
                            System.out.println("SE ENCONTRÓ EL CORREO");
                            User user = new User();
                            user.setId(result.getInt("ID"));
                            user.setName(result.getString("NAME_"));
                            user.setSurname(result.getString("SURNAME"));
                            user.setFK_TypeUser(result.getInt("FK_TYPE_U"));
                            String PassGetted = result.getString("PASS"); 
                                if(PassGetted.equals(Pass)){
                                    System.out.println("CORREO Y PASS COINCIDEN, ENTRA");
                                         ValidarTipoUsuario(user);
                                }else{
                                    MessagesForUser.showAlert("¡ATENCIÓN!","EL CORREO EXISTE PERO LA CONTRASEÑA ES INCORRECTA", AlertType.INFORMATION);
                                }     
                        }else{
                            MessagesForUser.showAlert("¡ATENCIÓN!","EL CORREO ELECTRÓNICO QUE INGRESÓ NO EXISTE", AlertType.INFORMATION);
                        }
                      //System.out.println("CONEXION SQLITE CERRADA POST LOGIN");
                      //con.CloseConnectionSQLite();
                      this.main.CloseConnectionAfterPreparedStatement();
                } catch (SQLException e) {
                      e.printStackTrace();
                }
        }else{
            //@MessagesForUser class has been created in this project [com.spomp.model.mmessagesForUser.java] by lestherxm
            MessagesForUser.showAlert("¡ATENCIÓN!","TIENES QUÉ SELECCIONAR UN SERVIDOR", AlertType.INFORMATION);
        }
    }
    //Este metodo valida que tipo de usuario es para mostrarle la vista respectiva.
    public void ValidarTipoUsuario(User user) throws SQLException{
        try {
        String sql = "SELECT t.NAME_ FROM TYPE_U t INNER JOIN USER_ u ON t.ID = ? AND u.ID = ?;";
            PreparedStatement pstmt = this.main.getPreparedStatementConnection(sql);//se abre la conexion a nivel global
                pstmt.setInt(1, user.getFK_TypeUser());
                pstmt.setInt(2, user.getId());
                    ResultSet result = pstmt.executeQuery();
                        if(result.next()){
                            user.setTypeUser(result.getString("NAME_"));
                            this.main.setUsuarioActual(user);
                            if("EMPRESA - ADMINISTRADOR".equals(user.getTypeUser())){
                                   System.out.println("VALIDADO, ERES: EMPRESA - ADMINISTRADOR");
                                   this.main.cargarMenuAdminCpyDesdeLogin(rdbmsSelected);
                            }else{
                                   System.out.println("VALIDADO, ERES: PROVEEDOR - ADMINISTRADOR");
                                   this.main.cargarMenuAdminProvDesdeLogin(rdbmsSelected);
                            }
                        }
             this.main.CloseConnectionAfterPreparedStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addRDBMS();
    }    
    
}
