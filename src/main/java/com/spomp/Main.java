package com.spomp;

import com.spomp.controller.AgregarArticuloController;
import com.spomp.controller.AgregarDepartamentoASucursalController;
import com.spomp.controller.AgregarDeptoController;
import com.spomp.controller.ArticulosController;
import com.spomp.controller.CrudUsuariosController;
import com.spomp.controller.DeptosController;
import com.spomp.controller.LoginController;
import com.spomp.controller.MantenimientoAdminCpyController;
import com.spomp.controller.MenuAdminCpyController;
import com.spomp.controller.MenuAdminProvController;
import com.spomp.controller.ProveedoresController;
import com.spomp.controller.RubrosController;
import com.spomp.controller.SucursalesController;
import com.spomp.controller.UsuariosController;
import com.spomp.model.ConnectionSQLServer;
import com.spomp.model.ConnectionSQLite;
import com.spomp.model.User;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * JavaFX Main
 */

public class Main extends Application {
    
    //variables de clase
    private String $Path = "/View/Fxml/"; //todos los archivos .fxml estan ubicados alli
    private String rdbmsSelected = ""; //para guardar desde el login el Rdbms que se seleccionó
    private User usuarioActual; //para almacenar el usuario que ha iniciado sesion. 
    private ConnectionSQLServer sqlserver = null;
    private ConnectionSQLite sqlite = null;
    
    //controladores
     UsuariosController UsuariosCont;
     DeptosController DeptosCont;
     ArticulosController ArticulosCont;
     SucursalesController  SucursalesCont;
     RubrosController RubrosCont;
     ProveedoresController ProveedoresCont;
     public AgregarDepartamentoASucursalController AgregarDepartamentoASucursalCont;
             
     //escenarios de la app
     private Stage LoginStage;
     private Stage MenuStage;
     private Stage OpcionDeMenuStage;
     private Stage OpcionMantenimientoCpy;
     private Stage CrudMenuMantenimientoCpy;
     
    @Override
     public void start(Stage stage) throws IOException {
            this.LoginStage = stage; //se carga el escenario de la app
                 cargarLogin();
    }
     
    public void setRdbmsGlobal(String rdbmsSelected){
         this.rdbmsSelected = rdbmsSelected;
         System.out.println(rdbmsSelected + " FUE SELECCIONADO");
    }
    
    public void setUsuarioActual(User user){
         this.usuarioActual = user;
    }
    
    public String getRdbmsGlobal(){
         return this.rdbmsSelected;
    }

    public Statement getStatementConnection(){
        Statement stmt = null;
        if(this.sqlserver != null){ //evitar multiples conexiones
            this.sqlserver.CloseConnectionSQLServer();
            this.sqlserver = null; //tienen valores null al principio de toda llamada PreparedStatement
        }
        if(this.sqlite != null){
            this.sqlite.CloseConnectionSQLite();
            this.sqlite = null;
        }
             try {
                 if(getRdbmsGlobal().equals("SQL SERVER")){
                      System.out.println("STATEMENT SQL SERVER");
                      this.sqlserver = new ConnectionSQLServer(); //deja de ser null
                      stmt = sqlserver.getConnectionToSQLServer().createStatement(); // se prepara el statement y deja de ser null
                      //rst = stmt.executeQuery(sql);
                 }else{
                      System.out.println("STATEMENT SQL LITE");
                      this.sqlite = new ConnectionSQLite(); //deja de ser null
                      stmt = sqlite.getConnectionSQLite().createStatement(); // se prepara el statement y deja de ser nulll
                     // rst = stmt.executeQuery(sql);
                 }
             } catch (SQLException e) {
                 System.out.println(e.getMessage());
                 e.printStackTrace();
             }
        return stmt;//se retorna el statement
    }
    
    public PreparedStatement getPreparedStatementConnection(String sql){
        PreparedStatement pstmt = null;
        if(this.sqlserver != null){ //evitar multiples conexiones
            this.sqlserver.CloseConnectionSQLServer();
            this.sqlserver = null; //tienen valores null al principio de toda llamada PreparedStatement
        }
        if(this.sqlite != null){
            this.sqlite.CloseConnectionSQLite();
            this.sqlite = null;
        }
        try {
            if(getRdbmsGlobal().equals("SQL SERVER")){
                 System.out.println("PREPARED STATEMENT SQL SERVER");
                 this.sqlserver = new ConnectionSQLServer(); //deja de ser null
                 pstmt = this.sqlserver.getConnectionToSQLServer().prepareStatement(sql); // deja de ser null
            }else{
                System.out.println("PREPARED STATEMENT SQL LITE");
                 this.sqlite = new ConnectionSQLite(); //deja de ser null
                 pstmt = this.sqlite.getConnectionSQLite().prepareStatement(sql); //deja de ser null
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return pstmt;    
    }
    
    public void CloseConnectionAfterPreparedStatement(){
        if(getRdbmsGlobal().equals("SQL SERVER")){
             System.out.println("SE CERRO SQL SERVER CONNECTION");
             this.sqlserver.CloseConnectionSQLServer(); //se cierra la conexion
        }else{
             System.out.println("SE CERRO SQL LITE CONNECTION");
             this.sqlite.CloseConnectionSQLite(); //se cierra la conexion
        }
    }
    
    public void cargarLogin(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource($Path+"Login.fxml")); //carga de archivo
                Parent root = loader.load(); //carga de archivo
                    Scene scene = new Scene(root); //se setea el archivo cargado a una escena
                    this.LoginStage.setScene(scene); //se setea la escena al escenario
                        LoginController LoginCont = loader.getController(); //se obtiene el controlador del archivo .fxml
                        LoginCont.setMain(this); //se ejecuta el metodo setMain del controlador del Login
                            this.LoginStage.setResizable(false);
                            this.LoginStage.show(); //se muestra el secenario/ventana del login
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void cargarMenu(String type, String rdbms){ //type puede ser AdminCpy.fxml o AdminProv.fxml
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource($Path+"Menu"+type));
                Parent root = loader.load();
                    Scene scene = new Scene(root);
                        this.MenuStage = new Stage();
                        this.MenuStage.setScene(scene);
                            if(type.equals("AdminCpy.fxml")){
                                MenuAdminCpyController MenuAdminCpyCont = loader.getController();
                                MenuAdminCpyCont.setMain(this,rdbms);
                                MenuAdminCpyCont.setDataUSerInPanel(this.usuarioActual);
                            }else{ //AdminProv.fxml
                                MenuAdminProvController MenuAdminProvCont = loader.getController();
                                MenuAdminProvCont.setMain(this,rdbms);
                                MenuAdminProvCont.setDataUSerInPanel(this.usuarioActual);
                            }
                                 this.MenuStage.setResizable(false);
                                 this.MenuStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void cargarOptionDelMenu(String FileName, String dbms){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource($Path+FileName));
                Parent root = loader.load();
                    Scene scene = new Scene(root);
                        this.OpcionDeMenuStage = new Stage();
                        this.OpcionDeMenuStage.setScene(scene);
                        this.OpcionDeMenuStage.initOwner(this.MenuStage); //Se le indica el escenario de donde "viene"
                        this.OpcionDeMenuStage.initModality(Modality.WINDOW_MODAL); //se le indica el comportamiento de la ventana
                            //Enlazar El controlador con base en el archivo .fxml cargado
                             switch(FileName){
                                 case "MantenimientoAdminCpy.fxml":
                                     MantenimientoAdminCpyController MantenimientoAdminCpyCont = loader.getController();
                                     MantenimientoAdminCpyCont.setMain(this,dbms);
                                 break;

                             }
                                 this.OpcionDeMenuStage.setResizable(false); // para que el usuario no pueda modificar a pantalla completa ya que no es responsive la App
                                 this.OpcionDeMenuStage.show(); //se muestra el escenario/vetana como tal
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public void cargarOpcionDeMantenimientoCpy(String FileName, String rdbms){
         try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource($Path+FileName));
                Parent root = loader.load();
                    Scene scene = new Scene(root);
                        this.OpcionMantenimientoCpy = new Stage();
                        this.OpcionMantenimientoCpy.setScene(scene);
                        this.OpcionMantenimientoCpy.initOwner(this.OpcionDeMenuStage); //Se le indica el escenario de donde "viene"
                        this.OpcionMantenimientoCpy.initModality(Modality.WINDOW_MODAL); //se le indica el comportamiento de la ventana
                            //Enlazar El controlador con base en el archivo .fxml cargado
                             switch(FileName){
                                 case "Usuarios.fxml":
                                     UsuariosCont = loader.getController();
                                     UsuariosCont.setMain(this, rdbms);
                                     UsuariosCont.obtenerUsuarios();
                                 break;
                                 case "Deptos.fxml":
                                     DeptosCont = loader.getController();
                                     DeptosCont.setMain(this);
                                     DeptosCont.obtenerDatos();
                                 break;
                                 case "Articulos.fxml":
                                     ArticulosCont = loader.getController();
                                     ArticulosCont.setMain(this);
                                     ArticulosCont.obtenerDatos();
                                 break;
                                 case "Sucursales.fxml":
                                     SucursalesCont = loader.getController();
                                     SucursalesCont.setMain(this);
                                     SucursalesCont.obtenerDatos();
                                 break;
                                 case "Rubros.fxml":
                                     RubrosCont = loader.getController();
                                     RubrosCont.setMain(this);
                                     RubrosCont.obtenerDatos();
                                 break;
                                 case "Proveedores.fxml":
                                     ProveedoresCont = loader.getController();
                                     ProveedoresCont.setMain(this);
                                     ProveedoresCont.obtenerDatos();
                                 break;

                             }
                                 this.OpcionMantenimientoCpy.setResizable(false); // para que el usuario no pueda modificar a pantalla completa ya que no es responsive la App
                                 this.OpcionMantenimientoCpy.show(); //se muestra el escenario/vetana como tal
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public void cargarStageCrudMantenimiento(String FileName){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource($Path+FileName));
                Parent root = loader.load();
                    Scene scene = new Scene(root);
                        this.CrudMenuMantenimientoCpy = new Stage();
                        this.CrudMenuMantenimientoCpy.setScene(scene);
                        this.CrudMenuMantenimientoCpy.initOwner(this.OpcionMantenimientoCpy); //Se le indica el escenario de donde "viene"
                        this.CrudMenuMantenimientoCpy.initModality(Modality.WINDOW_MODAL); //se le indica el comportamiento de la ventana
                            //Enlazar El controlador con base en el archivo .fxml cargado
                             switch(FileName){
                                 case "CrudUsuarios.fxml":
                                     CrudUsuariosController CrudUsuariosCont = loader.getController();
                                     CrudUsuariosCont.setMain(this);
                                        CrudUsuariosCont.obtenerDatosDeRegistro();
                                     break;
                                 case "AgregarDepto.fxml":
                                      AgregarDeptoController AgregarDeptoCont = loader.getController();
                                      AgregarDeptoCont.setMain(this);
                                     break;
                                 case "AgregarArticulo.fxml":
                                      AgregarArticuloController AgregarArticuloCont = loader.getController();
                                      AgregarArticuloCont.setMain(this);
                                     break;
                                 case "agregarDepartamentoASucursal.fxml":
                                     AgregarDepartamentoASucursalCont = loader.getController();
                                     AgregarDepartamentoASucursalCont.setMain(this);
                                     break;

                             }
                                 this.CrudMenuMantenimientoCpy.setResizable(false); // para que el usuario no pueda modificar a pantalla completa ya que no es responsive la App
                                 this.CrudMenuMantenimientoCpy.show(); //se muestra el escenario/vetana como tal
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }        
    }
    
    public void cargarMenuAdminCpyDesdeLogin(String rdbms){
        this.LoginStage.close();
             cargarMenu("AdminCpy.fxml",rdbms);
    }
    
    public void cargarMenuAdminProvDesdeLogin(String rdbms){
        this.LoginStage.close();
             cargarMenu("AdminProv.fxml",rdbms);
    }

    public void cargarLoginDesdeMenu(){
            this.MenuStage.close();
                 cargarLogin();
    }
    
    public void cargarMenuAdminCpyDesdeOptionMenu(){
        this.OpcionDeMenuStage.close();
                 //cargarManu("AdminCpy.fxml");
    }
    
    public void regresarAlMenuMantenimientoCpy(){
        this.OpcionMantenimientoCpy.close();
    }
    
    public void regresarAntesDeCrudMantenimiento(String FileName){
        this.CrudMenuMantenimientoCpy.close();
            switch(FileName){
                case "Usuarios.fxml":
                     UsuariosCont.obtenerUsuarios();
                break;
                case "Deptos.fxml":
                     DeptosCont.obtenerDatos();
                break;
                case "Articulos.fxml":
                     ArticulosCont.obtenerDatos();
                break;
                case "Sucursales.fxml":
                     SucursalesCont.obtenerDatos();
                break;
            }
    }
    
    public static void main(String[] args) {
        launch();
    }

}