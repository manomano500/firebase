/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.uniquedevelober.files;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

 import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import javax.faces.context.FacesContext;
 
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import javax.faces.context.FacesContext;

/**
 *
 * @author mahjouba
 */
public class Files {
    

    private String errMsg = "errMsg";

    private int id;
    private String fileName;
    private Date upload_data;

  
    private String fileContentString;  // Additional attribute to store file content
    private Map<String, String> fileContents = new HashMap<>();

   

    
    
    private String selectedFileContent;

    // ... (existing code)
    // Action method to show file content
    public void showFileContent(Files file) {
        selectedFileContent = file.getFileContents().get(file.getFileName());
    }

    // Getter and setter for selectedFileContent
    public String getSelectedFileContent() {
        return selectedFileContent;
    }

    public void setSelectedFileContent(String selectedFileContent) {
        this.selectedFileContent = selectedFileContent;
    }
    
  

    private Part fileContent;  // Assuming you use JSF for file uploads
    int currentUserId = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");

    // Other attributes, getters, setters...
    public String addFile() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hellbase2", "root", "");
        fileName = getFileName(fileContent);

            // Insert file information into the database
            String insertQuery = "INSERT INTO files (file_name, file_content, owner_user_id) VALUES (?, ?,  ?)";
            try (InputStream contentStream = fileContent.getInputStream(); PreparedStatement statement = con.prepareStatement(insertQuery)) {

                statement.setString(1, fileName);
                statement.setBlob(2, contentStream);
                statement.setInt(3, currentUserId);

                int rowCount = statement.executeUpdate();

                if (rowCount > 0) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("File added successfully!"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Failed to add file."));
                }
            }
        } catch (Exception e) {
            setErrMsg(e.getMessage());

            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("An error occurred while processing your request."));
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
        return "addFiles.xhtml?faces-redirect=true";
    }
    
    
    public void deleteFile(int fileId) {
        Connection con = null;

        try {
            // Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hellbase2", "root", "");

            // Delete user by ID
            String deleteQuery = "DELETE FROM files WHERE file_id=?";
            try (PreparedStatement statement = con.prepareStatement(deleteQuery)) {
                statement.setInt(1, fileId);

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
    
    
    
    public List<Files> getFilesForCurrentUser() {
        List<Files> files = new ArrayList<>();
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hellbase2", "root", "");

            // Assuming your files table has columns: file_name, file_content, owner_user_id
            String sql = "SELECT * FROM files WHERE owner_user_id = ?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setInt(1, currentUserId);

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                   Files file =new Files();
                    file.setId(resultSet.getInt("file_id"));
                    file.setFileName(resultSet.getString("file_name"));
                    file.setUpload_data(resultSet.getDate("upload_data"));
                    // Retrieve file content and add to the fileContents map
                    Blob fileContentBlob = resultSet.getBlob("file_content");
                    if (fileContentBlob != null) {
                        byte[] bytes = fileContentBlob.getBytes(1, (int) fileContentBlob.length());
                        String content = new String(bytes);
                        file.getFileContents().put(file.getFileName(), content);
                    }
//                    file.setFileContent((Part) resultSet.getBlob("file_content"));
                    // You can add more file attributes if needed
                    files.add(file);
                }
            }
        } catch (Exception e) {
            setErrMsg(e.getMessage());
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("An error occurred while processing your request."));
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

        return files;
    }
    
    
    
    
    
    public Map<String, String> getFileContents() {
        return fileContents;
    }

    public void setFileContents(Map<String, String> fileContents) {
        this.fileContents = fileContents;
    }
    
    
    
    public String getFileContentString() {
        return fileContentString;
    }

    public void setFileContentString(String fileContentString) {
        this.fileContentString = fileContentString;
    }
    
    
    
    
    
    
    
    
    
    public void setUpload_data(Date upload_data) {
        this.upload_data = upload_data;
    }

    public Date getUpload_data() {
        return upload_data;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileContent(Part fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileName() {
        return fileName;
    }

    public Part getFileContent() {
        return fileContent;
    }

    public String getErrMsg() {
        return errMsg;
    }
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
    
        private String getFileName(Part part) {
        String contentDispositionHeader = part.getHeader("content-disposition");
        String[] elements = contentDispositionHeader.split(";");
        for (String element : elements) {
            if (element.trim().startsWith("filename")) {
                return element.substring(element.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return "";
    }
        
   

        

}
