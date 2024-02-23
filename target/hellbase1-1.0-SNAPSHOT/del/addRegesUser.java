/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniquedevelober.regesUsersMngmnt;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jdk.jfr.Name;

/**
 *
 * @author mahjouba
 */
@WebServlet(name = "addRegesUser", urlPatterns = {"/addRegesUser"})
public class addRegesUser extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        String userEmail = req.getParameter("userEmail");
        String userPassword = req.getParameter("userPassword");
        String userName = req.getParameter("userName");
        Connection con = null;
        RequestDispatcher rd = null;


        
        
        
        try {
            // Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hellbase2", "root", "");

            // Insert user information into the database
            String insertQuery = "INSERT INTO reges_users (email,username, password) VALUES (?, ?, ?)";
            try (PreparedStatement statement = con.prepareStatement(insertQuery)) {
                statement.setString(1, userEmail);
                statement.setString(2, userName);
                statement.setString(3, userPassword);

                int rowCount = statement.executeUpdate();

                if (rowCount > 0) {
                    // Insertion successful
                    out.println("User registered successfully!");
                } else {
                    // Insertion failed
                    out.println("Failed to register user.");
                }
                


            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("An error occurred while processing your request.");
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        rd = req.getRequestDispatcher("index.jsp");
        rd.forward(req, resp);
    }
    
    
    
 
}
