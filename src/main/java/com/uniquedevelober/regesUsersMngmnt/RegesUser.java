/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniquedevelober.regesUsersMngmnt;

import java.io.IOException;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author mahjouba
 */
public class RegesUser {

    private int id;
    
    @NotNull
    @Email
    @NotEmpty
    private String email;
    
    @NotNull
    @NotEmpty
    private String password;
    
    @NotNull
    @NotEmpty
    @Size(min = 4, max = 50)
    private String username;
    
    private List<RegesUser> userList;


    private String errMsg= "errMsg";

    final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    final String DB_URL = "jdbc:mysql://localhost:3306/hellbase2";
    final String USER_NAME = "root";

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getErrMsg() {
        return errMsg;
    }
    public void setUserList(List<RegesUser> userList) {
        this.userList =userList;
    }

    public List<RegesUser> getUserList() {
        return userList;
    }

       public String addRegesUser() {
        boolean isVerified = false;
        Connection con = null;

        try {
            // Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hellbase2", "root", "");

            // Insert user information into the database
            String insertQuery = "INSERT INTO reges_users (email, password, username, owner_user_id) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                // Assuming you store the current user's ID in a session variable
                int currentUserId = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");

                statement.setString(1, email);
                statement.setString(2, password);
                statement.setString(3, username);
                statement.setInt(4, currentUserId);

                int rowCount = statement.executeUpdate();

                if (rowCount > 0) {
                    // Insertion successful
                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            setId(generatedKeys.getInt(1));
                        }
                    }
                    catch(Exception e){
                        setErrMsg(e.getMessage());}
                    setErrMsg("User registered successfully!");
                } else {
                    // Insertion failed
                    setErrMsg("Failed to register user.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            setErrMsg(e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                setErrMsg(e.getMessage());
            }
        }
        return "addUser?faces-redirect=true";
    }
    
    public List<RegesUser> getAllUsers() {
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
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setUsername(resultSet.getString("username"));
                userList.add(user);

            }
            connection.close();
            preparedStatement.close();

        } catch (Exception e) {
            setErrMsg(e.getMessage());

        }
        
        return userList;
    }

    
    
        public void deleteRegesUser(int userId) {
        Connection con = null;

        try {
            // Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hellbase2", "root", "");

            // Delete user by ID
            String deleteQuery = "DELETE FROM reges_users WHERE id=?";
            try (PreparedStatement statement = con.prepareStatement(deleteQuery)) {
                statement.setInt(1, userId);

                int rowCount = statement.executeUpdate();

                if (rowCount > 0) {
                    // Deletion successful
                    setErrMsg("User deleted successfully!");
                } else {
                    // Deletion failed
                    setErrMsg("Failed to delete user.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            setErrMsg(e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
        
        
        

}
