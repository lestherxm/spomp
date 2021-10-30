/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spomp.controller;

import com.spomp.Main;
import com.spomp.model.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author 50232
 */
public class MenuAdminProvController implements Initializable {

    Main main;
    String rdbms;
    @FXML
    private Label LabelNameUser;
    @FXML
    private Label LabelTypeUser;
    @FXML
    private Label LabelCurrentDbms;
    @FXML
    private Button BtnSalirOption;
    
    public void setMain(Main main,String rdbms){
        this.main = main;
        this.rdbms = rdbms;
        LabelCurrentDbms.setText(rdbms);
    }
    
    public void setDataUSerInPanel(User user){
        LabelNameUser.setText(user.getName() + " " + user.getSurname());
        LabelTypeUser.setText(user.getTypeUser());
        //LabelCurrentDbms.setText(this.main.getRdbmsGlobal());
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void MetodoSalirOption(ActionEvent event) {
        this.main.cargarLoginDesdeMenu();
    }
    
}
