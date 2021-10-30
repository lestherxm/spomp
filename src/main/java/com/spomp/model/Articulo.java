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
public class Articulo {
    SimpleIntegerProperty Id;
    SimpleStringProperty Name;
    SimpleStringProperty Description;
    
        public Articulo(){
            this.Id = new SimpleIntegerProperty();
            this.Name = new SimpleStringProperty();
            this.Description = new SimpleStringProperty();
        }
            //getters
            public int getId() {
                return Id.get();
            }

            public void setId(int Id) {
                this.Id.set(Id);
            }

            public String getName() {
                return Name.get();
            }

            public void setName(String Name) {
                this.Name.set(Name);
            }

            public String getDescription() {
                return Description.get();
            }

            public void setDescription(String Description) {
                this.Description.set(Description);
            }

                @Override
                public String toString() {
                    return "ARTICULO {}";
                }
}
