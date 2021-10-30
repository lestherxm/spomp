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
public class Branch {
    SimpleIntegerProperty Id;
    SimpleStringProperty Fk_DeptGt;
    SimpleStringProperty Fk_cityGt;
    SimpleStringProperty Direction;
        
        public Branch(){
            this.Id = new SimpleIntegerProperty();
            this.Fk_cityGt = new SimpleStringProperty();
            this.Fk_DeptGt = new SimpleStringProperty();
            this.Direction = new SimpleStringProperty();
        }
    
            //getters
            public int getId(){
                return this.Id.get();
            }
            public String getFk_cityGt(){
                return this.Fk_cityGt.get();
            }
            public String getFk_DeptGt(){
                return this.Fk_DeptGt.get();
            }
            public String getDirection(){
                return this.Direction.get();
            }
            //setters
            public void setId(int Id){
                this.Id.set(Id);
            }
            public void setFk_cityGt(String Fk_cityGt){
                this.Fk_cityGt.set(Fk_cityGt);
            }
            public void setFk_DeptGt(String Fk_DeptGT){
                this.Fk_DeptGt.set(Fk_DeptGT);
            }
            public void setDirection(String Direction){
                this.Direction.set(Direction);
            }

    @Override
    public String toString() {
        return this.getFk_DeptGt() + ", " + this.getFk_cityGt() + ", " + this.getDirection() + "";
    }
                
}
