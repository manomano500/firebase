package com.uniquedevelober;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;


/**
 *
 * @author mohamed
 */

public class SubmitFormServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
     protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Database configuration
        // Retrieve form data
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        Connection con = null;
      
        
        try {
            // Create a new JDBC connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hellbase2", "root", "");


            // Prepare and execute SQL statement to insert the form data into the database
            String query = "INSERT INTO users (username, email, password) VALUES (?, ?,?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, "defult pass");
            statement.executeUpdate();
           
            
           
            // Close the JDBC resources
            statement.close();
            con.close();
           
            HttpSession session = request.getSession();
            session.setAttribute("name", name);
            
            //request.setAttribute("name", name);
            //request.setAttribute("email", email);
            //request.getRequestDispatcher("success.jsp").forward(request, response);
            response.sendRedirect(request.getContextPath() + "/UserFilesServlet");
            
        } catch (SQLException e) {
            e.printStackTrace();
        
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().println("An error occurred while processing your request. Please try again later." + e.getMessage());
        } catch (ClassNotFoundException ex) {
             Logger.getLogger(SubmitFormServlet.class.getName()).log(Level.SEVERE, null, ex);
         }
        
       
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SubmitFormServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>Welcome, " + name + "!</h2>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
