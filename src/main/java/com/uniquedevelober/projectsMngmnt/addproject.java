/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.uniquedevelober.projectsMngmnt;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
@WebServlet(name = "addproject", urlPatterns = {"/addproject"})
public class addproject extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        RequestDispatcher rd = null;
        Connection con = null;

//  get user info       
        String projectName = req.getParameter("projectName");
        String userEmail = (String) req.getSession().getAttribute("email");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hellbase2", "root", "");

            // Retrieve user ID based on the email
            PreparedStatement getUserIdStatement = con.prepareStatement("SELECT userid FROM users WHERE email = ?");
            getUserIdStatement.setString(1, userEmail);
            ResultSet resultSet = getUserIdStatement.executeQuery();

            int userId = -1;  // Initialize to a default value
            if (resultSet.next()) {
                userId = resultSet.getInt("userid");
            }

            // Update the user's table with the project name
            PreparedStatement updateStatement = con.prepareStatement("UPDATE users SET project = ? WHERE userid = ?");
            updateStatement.setString(1, projectName);
            updateStatement.setInt(2, userId);

            int rowCount = updateStatement.executeUpdate();

            if (rowCount > 0) {
                req.setAttribute("projectAdded", "success");

            } else {
                req.setAttribute("projectAdded", "fail");
            }
            rd = req.getRequestDispatcher("index.jsp");
            rd.forward(req, resp);

        } catch (ClassNotFoundException | SQLException e) {
            // Log the exception or provide user-friendly error message
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                // Log the exception or handle as needed
                e.printStackTrace();
            }
        }
    }

    //  get user info       // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
}
