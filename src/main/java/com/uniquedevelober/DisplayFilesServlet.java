package com.uniquedevelober;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayFilesServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            Connection con = null; 
        try {
            // Create a new JDBC connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hellbase2", "root", "");

            // Fetch files from the database
            String query = "SELECT * FROM user_files";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Display the files
            while (resultSet.next()) {
                String fileName = resultSet.getString("file_type");
                String fileType = resultSet.getString("filename");
                String fileData = resultSet.getString("upload_date");

                // Display the file based on its type
                if (fileType != null && fileType.contains("image")) {
                    // Image file
                    response.getWriter().println("<img src='data:" + fileType + ";base64," + fileData + "' alt='" + fileName + "'><br>");
                }
            }

            // Close the JDBC resources
            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DisplayFilesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}