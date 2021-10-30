/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spomp.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author 50232
 */
public class Department {
    
    SimpleStringProperty Name;
    SimpleStringProperty Description;
    SimpleIntegerProperty N_People;
    
        public Department(){
             this.Name = new SimpleStringProperty();
             this.Description = new SimpleStringProperty();
             this.N_People = new SimpleIntegerProperty();
        }
        
        //getters
        public String getName(){
            return this.Name.get();
        }
        
        public String getDescription(){
            return this.Description.get();
        }
        
        public int getN_People(){
            return this.N_People.get();
        }
        
        //setters
        public void setName(String Name){
            this.Name.set(Name);
        }
        
        public void setDescription(String Description){
            this.Description.set(Description);
        }

        public void setN_People(int N_People){
            this.N_People.set(N_People);
        }
        
    @Override
    public String toString() {
        return this.getName();
    }
    
}
