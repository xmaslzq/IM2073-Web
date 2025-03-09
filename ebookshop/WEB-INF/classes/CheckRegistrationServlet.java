import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            // Tomcat 10 (Jakarta EE 9)
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/register")   // Configure the request URL for this servlet
public class CheckRegistrationServlet extends HttpServlet {

   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      // Set the MIME type for the response message
      response.setContentType("text/html");

      // Retrieve user input from the request
      String username = request.getParameter("username");
      String email = request.getParameter("email");
      String password = request.getParameter("password");

      if (username == null || email == null || password == null || 
          username.isEmpty() || email.isEmpty() || password.isEmpty()) {
         response.sendRedirect("register.html?error=All fields are required");
         return;
      }

      try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/ebookshop?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "user", "xxxx");   // Replace with your actual database credentials

         // Step 2: Allocate a 'PreparedStatement' object
         PreparedStatement checkStmt = conn.prepareStatement(
               "SELECT COUNT(*) FROM user WHERE user_name = ? OR email = ?");
         PreparedStatement insertStmt = conn.prepareStatement(
               "INSERT INTO user (user_name, email, password) VALUES (?, ?, ?)");
      ) {
         // Check if the username or email already exists
         checkStmt.setString(1, username);
         checkStmt.setString(2, email);
         ResultSet rs = checkStmt.executeQuery();
         rs.next();
         int count = rs.getInt(1);

         if (count > 0) {
            // Redirect back to register.html with an error message
            response.sendRedirect("register.html?error=Username or email is already in use");
            return;
         } else {
            // Insert new user
            insertStmt.setString(1, username);
            insertStmt.setString(2, email);
            insertStmt.setString(3, password);
            int rowsAffected = insertStmt.executeUpdate();

            if (rowsAffected > 0) {
               // Get the new user's ID
               Statement userStmt = conn.createStatement();
               ResultSet userRs = userStmt.executeQuery("SELECT user_id FROM User WHERE user_name='" + username + "'");
               int userId = -1;
               if (userRs.next()) {
                   userId = userRs.getInt("user_id");
               }
           
               // Insert a new cart for this user
               PreparedStatement cartStmt = conn.prepareStatement("INSERT INTO Shopping_Cart (cart_id, user_id) VALUES (?, ?)");
               int cartId = (int) (Math.random() * 1000000); // Generate a random cart_id
               cartStmt.setInt(1, cartId);
               cartStmt.setInt(2, userId);
               cartStmt.executeUpdate();
           
               // Redirect to home page after successful registration
               response.sendRedirect("Home.html");
           }
         }
      } catch(SQLException ex) {
         response.sendRedirect("register.html?error=Database error: " + ex.getMessage());
         ex.printStackTrace();
      }
   }
}
