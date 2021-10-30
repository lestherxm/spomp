/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spomp.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author 50232
 */
public class Telefonos {
    //telefonos sucursales
    SimpleIntegerProperty IdBranch;
    SimpleStringProperty Telefono;
    
        public Telefonos(){
            this.IdBranch = new SimpleIntegerProperty();
            this.Telefono = new SimpleStringProperty();
        }

    public int getIdBranch() {
        return IdBranch.get();
    }

    public void setIdBranch(int IdBranch) {
        this.IdBranch.set(IdBranch);
    }

    public String getTelefono() {
        return Telefono.get();
    }

    public void setTelefono(String Telefono) {
        this.Telefono.set(Telefono);
    }
       
}
