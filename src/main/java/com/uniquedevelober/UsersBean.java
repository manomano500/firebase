/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.uniquedevelober;

import com.uniquedevelober.entitys.Users;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;

/**
 *
 * @author mahjouba
 */

@Stateless
@LocalBean
public class UsersBean {

    @PersistenceContext // Replace with your unit name
     EntityManager em;

    public void addUser(int id,String username, String email, String password) {
        Users user = new Users();
        user.setUserid(id);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        em.persist(user);
    }
    public String tstFunction(){
        Users users =new Users(4);
    return users.getUsername();
    }

    public Users findUserById(int userId) {
        System.err.println(userId);
        return em.find(Users.class, userId);
    }
    
        public List<Users> getUsers() {

          return em.createQuery("select u from Users u",Users.class).getResultList();

    }


    // Additional methods for retrieving, updating, or deleting users as needed
}
