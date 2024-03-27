/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniquedevelober.regs;
import com.uniquedevelober.regesUsersMngmnt.RegesUser;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author mahjouba
 */
@Named
@RequestScoped
public class User {
    
    @NotEmpty
    @NotEmpty
    @Size(min = 4, max = 50)
    private String username;

 

    @NotNull
    @Email
    @NotEmpty
    private String email;

    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    private int id;
    
    
    
    private boolean isValidReg=false;
    private boolean isValidLogIn = false;
    private String errMsg="errMsg";
    
    
    
    
    
    
    
    public String logout() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        // Invalidate the current session
        externalContext.invalidateSession();

        try {
            // Redirect to the login page after logout
            externalContext.redirect(externalContext.getRequestContextPath() + "/login.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;  // Navigation case, if needed
    }
    
    
    public String register() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hellbase2", "root", "");

            PreparedStatement statement = con.prepareStatement("INSERT INTO `users` (`username`, `email`, `password`) VALUES (?, ?, ?)");
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, password);

            int rowCount = statement.executeUpdate();

            if (rowCount > 0) {
                setIsValidReg(true) ;
                setErrMsg("rowcount: " + rowCount);
            } else {
                setIsValidReg(false);
                setErrMsg("rowcount: fail");

            }

        } catch (Exception e) {
            e.printStackTrace();
            
            setErrMsg(e.getMessage());
        }
        if (isValidReg ) {
            return "login.xhtml?faces-redirect=true";
        } else {
            return "signup.xhtml?faces-redirect=true";
        }

    }
    
    
      public String login() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hellbase2", "root", "");

            PreparedStatement statement = con.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                isValidLogIn = true;

                // Store user ID in the session
                externalContext.getSessionMap().put("userId", rs.getInt("userid"));

                externalContext.getSessionMap().put("username", rs.getString("username"));
            } else {
                FacesContext.getCurrentInstance().addMessage("ErrLogin",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid credentials", null));
                setErrMsg("Invalid Login");
            }
        } catch (Exception e) {
            e.printStackTrace();
            setErrMsg( e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                setErrMsg(e.getMessage());
                e.printStackTrace();
            }
        }

        if (isValidLogIn) {
            return "index.xhtml?faces-redirect=true";
        } else {
            return "login.xhtml?faces-redirect=true";
        }
    }

    
       
    public List<RegesUser> getAssociatedUsers() {
        List<RegesUser> associatedUsers = new ArrayList<>();
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hellbase2", "root", "");

            // Assuming you have a column named owner_id in the reges_users table
            String sql = "SELECT * FROM reges_users WHERE owner_user_id = ?";

            try (PreparedStatement statement = con.prepareStatement(sql)) {
                // Assuming you store the current user's ID in a session variable
                int currentUserId = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
                statement.setInt(1, currentUserId);

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    RegesUser user = new RegesUser();
                    user.setId(resultSet.getInt("id"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setUsername(resultSet.getString("username"));
                    associatedUsers.add(user);
                }
            }
        } catch (Exception e) {
            setErrMsg(e.getMessage());
            e.printStackTrace();
        
            
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                setErrMsg(e.getMessage());

                e.printStackTrace();
            }
        }
        return associatedUsers;
    }


    
    
    
    
    
    public void setIsValidLogIn(boolean isValidLogIn) {
        this.isValidLogIn = isValidLogIn;
    }

    public boolean isIsValidLogIn() {
        return isValidLogIn;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getErrMsg() {
        return errMsg;
    }
    public void setIsValidReg(boolean isValidReg) {
        this.isValidReg = isValidReg;
    }

    public boolean isIsValidReg() {
        return isValidReg;
    }
}
