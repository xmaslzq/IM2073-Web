import java.io.*;
import java.sql.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

// Define the servlet and map it to the "/api/books" endpoint
@WebServlet("/api/books")
public class BookDataServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ebookshop?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "user";
    private static final String DB_PASS = "xxxx";

    // Handles HTTP GET requests.
    // Retrieves book data based on filters like genre, author, and price order.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
        
      // Set response content type to JSON and UTF-8 encoding
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        // ----------------- QUERY PARAMETER RETRIEVING -----------------
        
        // Retrieve query parameters from the request (obtained from querybook.html)
        String genre = request.getParameter("genre");
        String priceOrder = request.getParameter("priceOrder");
        String[] authors = request.getParameterValues("author");
        
        // Construct the base SQL query => need to join 3 tables: book, author, and genre
        StringBuilder sql = new StringBuilder(
            "SELECT b.book_id, b.title, b.price, b.image_url, a.author_name, g.genre_name " +
            "FROM book b " +
            "JOIN author a ON b.author_id = a.author_id " +
            "JOIN genre g ON b.genre_id = g.genre_id "
        );
        
        // List to store SQL conditions and corresponding parameter values => I look at way Youtubers did: https://www.youtube.com/watch?v=RqiuxA_OFOk
        // for JDBC and database connectivity
        // Write query to filter books based on genre and authors
        List<String> conditions = new ArrayList<>();
        List<Object> params = new ArrayList<>(); //param to pass to query
        
        // Apply genre filter if specified
        if (genre != null && !genre.equals("all")) {
            conditions.add("g.genre_name = ?");
            params.add(genre);
        }
        
        //Author filter
        if (authors != null && authors.length > 0) {
            StringBuilder authorCond = new StringBuilder("a.author_name IN (");
            for (int i = 0; i < authors.length; i++) {
                if (i > 0) authorCond.append(",");
                authorCond.append("?");
                params.add(authors[i]);
            } // Use from the JavaServlet tutorial at class
            authorCond.append(")");
            conditions.add(authorCond.toString());
        }
        
        // Append conditions to the SQL query
        if (!conditions.isEmpty()) {
            sql.append("WHERE ").append(String.join(" AND ", conditions));
        }
        // Apply price order if specified DESC/ ASC
        if (priceOrder != null) {
            if (priceOrder.equals("asc") || priceOrder.equals("desc")) {
                sql.append(" ORDER BY b.price ").append(priceOrder.toUpperCase());
            }
        }

         // ----------------- DATABASE QUERY EXECUTION and construct JSON repsonse-----------------
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            
            // Set query parameters
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }
            
            //Execute here
            ResultSet rs = pstmt.executeQuery();
            
             // Build JSON response
            StringBuilder json = new StringBuilder("[");
            boolean first = true;
            

            // This part is to obtain all the books data from the Book table and store it in json file
            // if you go to http://localhost:8080/ebookshop/api/books, you will see all the books data
         
            while (rs.next()) {
                if (!first) json.append(",");
                first = false;
                
                json.append(String.format(
                    "{\"book_id\":%d,\"title\":\"%s\",\"price\":%f,\"image_url\":\"%s\",\"author_name\":\"%s\",\"genre_name\":\"%s\"}",
                    rs.getInt("book_id"),
                    escapeJsonString(rs.getString("title")),
                    rs.getDouble("price"),
                    escapeJsonString(rs.getString("image_url")),
                    escapeJsonString(rs.getString("author_name")),
                    escapeJsonString(rs.getString("genre_name"))
                ));
            }
            
            json.append("]");
            out.print(json.toString());
            
        } catch (SQLException ex) {
         // Handle database errors and send JSON error response
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"Database error: " + escapeJsonString(ex.getMessage()) + "\"}");
        }
    }
    // Handles HTTP POST requests.
   //Retrieves a list of available genres or authors based on the 'type' parameter.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
         // Set response content type to JSON and UTF-8 encoding
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        // Get 'type' parameter (should be either "genres" or "authors")
        String type = request.getParameter("type");
        
        if (type == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"Missing parameter: type\"}");
            return;
        }
        
        // Define SQL query based on type
        String sql;
        if (type.equals("genres")) {
            sql = "SELECT genre_id, genre_name FROM genre ORDER BY genre_name";
        } else if (type.equals("authors")) {
            sql = "SELECT author_id, author_name FROM author ORDER BY author_name";
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"Invalid type parameter. Use 'genres' or 'authors'.\"}");
            return;
        }

        // --------Execute the query and construct JSON response------
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            StringBuilder json = new StringBuilder("[");
            boolean first = true;
            
            while (rs.next()) {
                if (!first) json.append(",");
                first = false;
                
                String idField = type.equals("genres") ? "genre_id" : "author_id";
                String nameField = type.equals("genres") ? "genre_name" : "author_name";
                
                json.append(String.format(
                    "{\"id\":%d,\"name\":\"%s\"}",
                    rs.getInt(idField),
                    escapeJsonString(rs.getString(nameField))
                ));
            }
            
            json.append("]");
            out.print(json.toString());
            
        } catch (SQLException ex) {
            // Handle database errors and send JSON error response
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"Database error: " + escapeJsonString(ex.getMessage()) + "\"}");
        }
    }
    // Escapes special characters in a JSON string.
    private String escapeJsonString(String input) {
        if (input == null) return "";
        return input.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }
}