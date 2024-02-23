/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.*;

/**
 *
 * @author skorp
 */
@Path("/users")
public class UserResource {

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response registerUser(@FormParam("email") String email,
                                 @FormParam("password") String password,
                                 @FormParam("name") String name) {
        try {
            // Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hellbase2", "root", "");

            // Insert user information into the database
            String insertQuery = "INSERT INTO users (email, username, password) VALUES (?, ?, ?)";
            try (PreparedStatement statement = con.prepareStatement(insertQuery)) {
                statement.setString(1, email);
                statement.setString(2, name);
                statement.setString(3, password);

                int rowCount = statement.executeUpdate();

                if (rowCount > 0) {
                    // Registration successful
                    return Response.status(Response.Status.OK).entity("User registered successfully!").build();
                } else {
                    // Registration failed
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to register userllllll.").build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to register useroooooo.").build();
        }
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response loginUser(@FormParam("email") String email,
                              @FormParam("password") String password) {
        try {
            // Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hellbase2", "root", "");

            // Check if the user exists in the database
            String selectQuery = "SELECT * FROM users WHERE email = ? AND password = ?";
            try (PreparedStatement statement = con.prepareStatement(selectQuery)) {
                statement.setString(1, email);
                statement.setString(2, password);

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    // Login successful
                    return Response.status(Response.Status.OK).entity("User logged in successfully!").build();
                } else {
                    // Login failed
                    return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid email or password.").build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to login user.").build();
        }
    }


@GET
@Path("/")
public Response get(){
    return Response.status(Response.Status.OK).entity("User registered successfully!").build();
}
}