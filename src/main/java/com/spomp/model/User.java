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
public class User {
     SimpleIntegerProperty Id;//
     SimpleStringProperty Name;//
     SimpleStringProperty Surname;//
     SimpleIntegerProperty FK_TypeUser; //
     SimpleIntegerProperty FK_Branch;
     SimpleStringProperty TypeUser;//
     SimpleStringProperty Email;
     SimpleStringProperty Branch;
     SimpleStringProperty Depto; 
     SimpleStringProperty Pass;
     
         public User(){
             this.Id = new SimpleIntegerProperty();
             this.Name = new SimpleStringProperty();
             this.Surname = new SimpleStringProperty();
             this.FK_TypeUser = new SimpleIntegerProperty();
             this.TypeUser = new SimpleStringProperty();
             this.Email = new SimpleStringProperty();
             this.Branch = new SimpleStringProperty();
             this.Depto = new SimpleStringProperty();
             this.Pass = new SimpleStringProperty();
             this.FK_Branch = new SimpleIntegerProperty();
         }
        
           /* public User(int Id, String Name, String Surname, int FK_TypeUser, String TypeUser,String Email,String Branch, String Depto){
                this.Id = new SimpleIntegerProperty(Id);
                this.Name = new SimpleStringProperty(Name);
                this.Surname = new SimpleStringProperty(Surname);
                this.FK_TypeUser = new SimpleIntegerProperty(FK_TypeUser);
                this.TypeUser = new SimpleStringProperty(TypeUser);
                this.Email = new SimpleStringProperty(Email);
                this.Branch = new SimpleStringProperty(Branch);
                this.Depto = new SimpleStringProperty(Depto);
            }*/
                //getters
                public int getId(){
                     return this.Id.get();
                } 
    
                public String getName(){
                     return this.Name.get();
                }
                
                public String getSurname(){
                     return this.Surname.get();
                }
                
                public int getFK_TypeUser(){
                     return this.FK_TypeUser.get();
                }
                
                public String getTypeUser(){
                     return this.TypeUser.get();
                }
                
                public String getEmail(){
                     return this.Email.get();
                }
                
                public String getBranch(){
                     return this.Branch.get();
                }
                
                public String getDepto(){
                     return this.Depto.get();
                }
                
                public String getPass(){
                     return this.Pass.get();
                }
                
                public int getFK_Branch(){
                      return this.FK_Branch.get();
                }
                
                //Setters
                public void setPass(String pass){
                     this.Pass.set(pass);
                }
                
                public void setId(int Id){
                     this.Id.set(Id);
                }
                
                public void setName(String Name){
                     this.Name.set(Name);
                }

                public void setSurname(String Surname){
                     this.Surname.set(Surname);
                }
                
                public void setFK_TypeUser(int FK_TypeUser){
                     this.FK_TypeUser.set(FK_TypeUser);
                }
                
                public void setTypeUser(String TypeUser){
                     this.TypeUser.set(TypeUser);
                }
                
                public void setEmail(String Email){
                     this.Email.set(Email);
                }

                public void setBranch(String Branch){
                     this.Branch.set(Branch);
                }
                
                public void setDepto(String Depto){
                     this.Depto.set(Depto);
                }
                
                public void setFK_Branch(int FK_Branch){
                     this.FK_Branch.set(FK_Branch);
                }
                
                    @Override
                    public String toString() {
                         return "USER{"+this.Id+", " + this.Name + ", "+this.Surname+", " + this.FK_TypeUser + "} ";
                    }
           
}
