/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.uniquedevelober.regestratoin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mahjouba
 */
@WebServlet(name = "login", urlPatterns = {"/login"})
public class login extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        RequestDispatcher rd = null;
        Connection con = null;
        HttpSession session =req.getSession();
        //  get user info       
        String email = req.getParameter("email");
        String pass = req.getParameter("pass");
       
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hellbase2", "root", "");
            
            PreparedStatement statement = con.prepareStatement("select * from users where Email = ? and Password = ?");
            statement.setString(1, email);
            statement.setString(2, pass);
            
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                session.setAttribute("email", rs.getString("email"));
                rd =req.getRequestDispatcher("index.xhtml");
                
            }else{
            req.setAttribute("status", "fail");
                rd = req.getRequestDispatcher("login.jsp");
            }
            rd.forward(req, resp);




        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
            }
        
        }

        //  get user info       

    }

    
}
