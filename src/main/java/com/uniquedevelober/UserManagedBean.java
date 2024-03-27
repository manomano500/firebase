/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.uniquedevelober;

import com.uniquedevelober.entitys.Users;
import java.io.Serializable;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author mahjouba
 */
@ManagedBean

public class UserManagedBean implements Serializable{


    @Inject
    private UsersBean usersBean;


    
        /**
     * Creates a new instance of UserManagedBean
     */
    public UserManagedBean() {
    }

private List<Users> usersList ;
    private String tstText;
    private int newId;
    private String newUsername;
    private String newEmail;
    private String newPassword;


    
    
    public List<Users> getUsersList() {
        return usersList;
    }
    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    
    

    public void setTstText(String tstText) {
        this.tstText = tstText;
    }

    public String getTstText() {
        return tstText;
    }
    public void setNewId(int newId) {
        this.newId = newId;
    }

    public int getNewId() {
        return newId;
    }
    


    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public UsersBean getUsersBean() {
        return usersBean;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public String getNewPassword() {
        return newPassword;
    }
  

    public void addUserAction() throws EJBException{
        usersBean.addUser(newId,newUsername, newEmail, newPassword);
        
        // Handle success or error messages, clear input fields if needed
    }

    
    
    public List<Users> fetchUsers(){
    return usersBean.getUsers();
    }
 
    
}
