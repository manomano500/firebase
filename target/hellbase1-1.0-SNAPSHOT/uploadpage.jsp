<%-- 
    Document   : success
    Created on : Feb 23, 2024, 8:31:16 PM
    Author     : mohamed
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>File Upload</title>
</head>
<body> 
    <h1>File Upload</h1>
    <form action="UploadServlet2" method="POST" enctype="multipart/form-data">
        <input type="file" name="file"><br><br>
        <button type="submit" name="submit">Upload</button>
    </form>
    <!-- Insert this line where you want to display the files -->
</body>
</html>
