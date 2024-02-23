/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.uniquedevelober.regestratoin;

import com.mysql.cj.jdbc.Driver;
import com.sun.tools.javac.resources.compiler;
import java.io.IOException;
import java.io.PrintWriter;
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

/**
 *
 * @author mahjouba
 */
@WebServlet("/regester")
public class RegestrationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out =resp.getWriter();
        RequestDispatcher rd=null;
        Connection con=null;
        
//  get user info       
       String name =req.getParameter("name");
       String email =req.getParameter("email");
       String pass =req.getParameter("pass");
       
 //  get user info       


//     database connection

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con =DriverManager.getConnection("jdbc:mysql://localhost:3306/hellbase2","root","");
            PreparedStatement statement = con.prepareStatement("INSERT INTO `users` (`username`, `email`, `password`) VALUES (?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, pass);
            
            
            int rowCoutn =statement.executeUpdate();
            rd= req.getRequestDispatcher("registration.jsp");
            
            
            if (rowCoutn>0) {
                req.setAttribute("status", "success");
               

                
                
            }
            else{
                req.setAttribute("status", "fail");
              
            
            }
            rd.forward(req, resp);
           
            
        } catch (Exception e) {
            
            e.printStackTrace();
        }
        finally{
            try {
                con.close();
            } catch (SQLException e) {
            }
        }
        
       
       
       
    }
    

  
}
