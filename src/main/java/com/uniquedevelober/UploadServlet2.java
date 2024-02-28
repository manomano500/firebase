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
        InputStream fileContent = filePart.getInputStream();

        Connection con = null;

        try {
            // Load the JDBC driver for your database
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hellbase2", "root", "");

            // Prepare the SQL statement
            String sql = "INSERT INTO user_files (file_type, filename, file_data, upload_date) VALUES (?, ?, ?, NOW())";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, filePart.getContentType());
            statement.setString(2, fileName);
            statement.setBlob(3, fileContent);

            // Execute the SQL statement
            statement.executeUpdate();

            // Close the statement and connection
            statement.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handle any errors that occur during database operations
        } finally {
            if (fileContent != null) {
                fileContent.close();
            }
        }

        // Redirect to a success page or display a success message
        response.sendRedirect("success.jsp");
    }

    // ... (rest of the servlet code)
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use the following sample code. */
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
