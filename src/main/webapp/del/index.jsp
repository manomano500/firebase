
<%@page import="java.util.List"%>
<%@page import="com.uniquedevelober.regesUsersMngmnt.RegesUser"%>
<%@page import="com.uniquedevelober.regesUsersMngmnt.UserDAO"%>
<%
if (session.getAttribute("email")==null )  {
    response.sendRedirect("login.jsp");
        
    }
    
%>
<%@ page import="javax.servlet.http.HttpSession" %>


<%--<jsp:include page="ProjectsSevlet"/>--%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Dashboard</title>
        <style>
       .sidebar {
           height: 100vh;
           background-color: #f8f9fa;
       }

       .content {
           padding: 20px;
       }
        </style>

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>

    <body>

        
        <jsp:include page="parts/nav.jsp"/>
        

        <%    UserDAO userDAO = new UserDAO();
            List<RegesUser> userList = userDAO.getAllUsers();
        %>
     

        <div class="container p-5 my-5 bg-dark text-white">
            <p class="h2 ">Your Firebase projects</p>
            <div class="row">
                <div class=" col-2 card m-2" style="width:250px; height: 180px">
                    <div class="card-body">
                        <button type="button" class=" btn btn-link text-danger" data-toggle="modal" data-target="#popupForm">
                            Add Project
                        </button>
                    </div>


                </div>
                

            </div>



        </div>
        

        

        <div class="modal" id="popupForm">
            <div class="modal-dialog">
                <div class="modal-content">

                    <!-- Modal Header -->
                    <div class="modal-header">
                        <h4 class="modal-title">Project Name</h4>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>

                    <!-- Modal Body -->
                    <div class="modal-body">
                        <form action="addproject" method="post">
                            
                            <!-- Your form fields go here -->
                            <div class="form-group">
                                <label for="projectName">Project Name:</label>
                                <input type="text" class="form-control" id="projectName" name="projectName" required>
                            </div>
                            <button type="submit" class="btn btn-primary">Submit</button>
                        </form>
                    </div>

                    <!-- Modal Footer -->
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>

                </div>
            </div>
        </div>
        
        
        <div id="users-page" class="m-5" >
            <h2>Manage Users</h2>
            <button type="button" class="btn btn-primary mb-3 mt-4" data-toggle="modal" data-target="#userFormModal">
                Add User            </button>
            <!--<table id="users-table" class="table">-->
            <table border="1" id="users-table" class="table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>Email</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (RegesUser user : userList) {%>
                    <tr>
                        <td><%= user.getId()%></td>
                        <td><%= user.getUsername()%></td>
                        <td><%= user.getEmail()%></td>
                    </tr>
                    <% }%>
                </tbody>
            </table>
        </div>
        
        

        <!-- Button to trigger the modal -->
       

        <!-- Modal -->
        <div class="modal fade" id="userFormModal" tabindex="-1" role="dialog" aria-labelledby="userFormModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="userFormModalLabel">Add New User</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <!-- User form -->
                        <form method="post" action="addRegesUser">
                           
                            <div class="form-group">
                                <label for="userEmail">Email</label>
                                <input type="email" class="form-control" id="userEmail" name="userEmail" placeholder="Enter Email">
                            </div>
                            <div class="form-group">
                                <label for="userPassword">Password</label>
                                <input type="password" class="form-control" id="userPassword" name="userPassword" placeholder="Enter Password">
                            </div>
                            <div class="form-group">
                                <label for="userName">Username</label>
                                <input type="text" class="form-control" id="userName" name="userName" placeholder="Enter Username">
                            </div>
                            <button type="submit" class="btn btn-primary">Submit</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.6.0/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>

</html>
