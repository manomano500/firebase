/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniquedevelober.regesUsersMngmnt;

import static com.mysql.cj.conf.PropertyKey.PASSWORD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author mahjouba
 */
public class UserDAO {
    
    final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    final String DB_URL = "jdbc:mysql://localhost:3306/hellbase2";
    final String USER_NAME = "root";
    
    private String errMsg;
    private List<RegesUser> userList;

    @PostConstruct
    public void init(){
        userList =getAllUsers();
    }


 
    
    public List<RegesUser> getAllUsers()  {
        List<RegesUser> userList = new ArrayList<>();
        
        

        try {
            Connection connection = null;
            PreparedStatement preparedStatement = null;

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER_NAME, "");
            String sql = "SELECT * FROM `reges_users` WHERE '1'";
            
            

            preparedStatement = connection.prepareStatement(sql);
        
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                RegesUser user = new RegesUser();
                user.setId(resultSet.getInt(1));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                userList.add(user);
                
            }

          

        } catch (Exception e) {
            setErrMsg(e.getMessage());

        }
        return userList;
    }
    
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getErrMsg() {
        return errMsg;
    }
    public void setUserList(List<RegesUser> userList) {
        this.userList = userList;
    }

    public List<RegesUser> getUserList() {
        return userList;
    }
}
