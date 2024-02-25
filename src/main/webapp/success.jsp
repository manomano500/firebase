<%-- 
    Document   : success
    Created on : Feb 23, 2024, 8:31:16 PM
    Author     : mohamed
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import=" com.uniquedevelober.UserFile" %>

<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
</head>
<style>
    p {
      text-align: center;
    }
    h3 {
        text-align: center;
    }
  table {
    width: 100%;
    height: 100%;
    table-layout: fixed;
  }
  
  td {
    text-align: center;
    vertical-align: top;
  }
</style>
<body>
    <p>Welcome, <%= session.getAttribute("name") %>! to firebase <> <a href="uploadpage.jsp">upload file</a> </p>
     <h3>files here</h3>
     <table>
        <thead>
            <tr>
                <th>File Type</th>
                <th>File Name</th>
                <th>Upload Date</th>
            </tr>
        </thead>
        <tbody>
            <% 
            List<UserFile> userFiles = (List<UserFile>) request.getAttribute("userFiles");
            if (userFiles != null) {
                for (UserFile userFile : userFiles) { %>
                    <tr>
                        <td><%= userFile.getFileType() %></td>
                        <td><%= userFile.getFilename() %></td>
                        <td><%= userFile.getUploadDate() %></td>
                    </tr>
            <%  }
            } %>
        </tbody>
    </table>
</body>
</html>
