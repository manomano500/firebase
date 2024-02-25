package com.uniquedevelober;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


/**
 *
 * @author mohamed
 */

@WebServlet("/UploadServlet2")
@MultipartConfig
public class UploadServlet2 extends HttpServlet {
     protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         
     
        // Get the file part from the request
        Part filePart = request.getPart("file");

        // Get the filename and extract its name
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        // Get the input stream of the file
        // Define the directory to which the file will be uploaded
        String uploadDirectory = "C:\\Users\\mahjouba";

        // Create the directory if it does not exist
        Path directoryPath = Paths.get(uploadDirectory);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

         // Save the file to the specified directory
        Path filePath = directoryPath.resolve(fileName);
        filePart.write(filePath.toString());
        
        Connection con = null;
        
        // Insert the file details into the database
        try {
            // Load the JDBC driver for your database
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hellbase2", "root", "");

            // Prepare the SQL statement
            String sql = "INSERT INTO user_files (file_type, filename, upload_date) VALUES (?, ?, NOW())";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, filePart.getContentType());
            statement.setString(2, fileName);

            // Execute the SQL statement
            statement.executeUpdate();

            // Close the statement and connection
            statement.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handle any errors that occur during database operations
        }

        // Redirect to a success page or display a success message
        response.sendRedirect("success.jsp");
    }
    
    

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UploadServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UploadServlet at " + request.getContextPath() + "</h1>");
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

}
