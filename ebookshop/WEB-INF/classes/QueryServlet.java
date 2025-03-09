// To save as "ebookshop\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;           
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


@WebServlet("/query")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class QueryServlet extends HttpServlet {

   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      // Set the MIME type for the response message
      response.setContentType("text/html");
      // Get a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();
      // Print an HTML page as the output of the query
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Query Response</title>");
      
      // Add CSS styling here
      out.println("<style>");
      out.println("body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }");
      out.println("h3 { color:rgb(25, 27, 25); }");
      out.println("p { font-size: 16px; color: #555; line-height: 1.6; }");
      out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
      out.println("th, td { padding: 8px; text-align: left; border: 1px solid #ddd; }");
      out.println("th { background-color:rgb(36, 39, 36); color: white; }");
      out.println("tr:nth-child(even) { background-color: #f2f2f2; }");
      out.println("tr:hover { background-color: #ddd; }");
      out.println(".button { background-color:rgb(22, 24, 22); color: white; padding: 10px 20px; border: none; cursor: pointer; text-align: center; font-size: 16px; margin-top: 20px; }");
      out.println(".button:hover { background-color:rgb(12, 14, 12); }");
      out.println("</style>");
      
      out.println("</head>");
      out.println("<body>");

      try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/ebookshop?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "user", "xxxx");   // For MySQL
               // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ) {
         // Step 3: Execute a SQL SELECT query
         // === Form the SQL command - BEGIN ===
        String[] authors = request.getParameterValues("author");  // Returns an array of Strings
         String sqlStr = "SELECT * FROM book WHERE author IN (";
         for (int i = 0; i < authors.length; ++i) {
            if (i < authors.length - 1) {
               sqlStr += "'" + authors[i] + "', ";  // need a commas
            } else {
               sqlStr += "'" + authors[i] + "'";    // no commas
            }
         }
         sqlStr += ") AND qty > 0 ORDER BY author ASC, title ASC";
         // === Form the SQL command - END ===

         out.println("<h3>Thank you for your query.</h3>");
         out.println("<p>Your SQL statement is: " + sqlStr + "</p>"); // Echo for debugging
         ResultSet rset = stmt.executeQuery(sqlStr);  // Send the query to the server

         // Step 4: Process the query result set
         int count = 0;
         out.println("<table>");
         out.println("<tr><th>Author</th><th>Title</th><th>Price</th></tr>");
         while(rset.next()) {
            // Print a table row for each record
            out.println("<tr>");
            out.println("<td>" + rset.getString("author") + "</td>");
            out.println("<td>" + rset.getString("title") + "</td>");
            out.println("<td>$" + rset.getDouble("price") + "</td>");
            out.println("</tr>");
            count++;
         }
         out.println("</table>");
         out.println("<p>==== " + count + " records found =====</p>");
         // === Step 4 ends HERE - Do NOT delete the following codes ===
      } catch(SQLException ex) {
         out.println("<p>Error: " + ex.getMessage() + "</p>");
         out.println("<p>Check Tomcat console for details.</p>");
         ex.printStackTrace();
      }  // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)

      // Add a "Back to Book List" button
      out.println("<button class=\"button\" onclick=\"window.location.href='querybook.html'\">Back to Book List</button>");

      out.println("</body></html>");
      out.close();
   }
}
