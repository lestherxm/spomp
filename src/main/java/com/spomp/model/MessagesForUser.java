/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spomp.model;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 *
 * @author 50232
 */
public class MessagesForUser {
    static Alert alert;
   
    public static void showAlert(String Tittle_, String ContentText_, AlertType AlertType_){
         alert = new Alert(AlertType_);
         alert.setHeaderText(null);
         alert.setTitle(Tittle_);
         alert.setContentText(ContentText_);
         //alert.getDialogPane().setMinSize(400,250);
         alert.showAndWait();
    }
    
    public static ButtonType showConfirmation(String Tittle_, String ContentText_, AlertType AlertType_){
        alert = new Alert(AlertType_);
        alert.setHeaderText(null);
        alert.setTitle(Tittle_);
        alert.setContentText(ContentText_);
        //alert.getDialogPane().setMinSize(400,250);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get();
    }
    
}
