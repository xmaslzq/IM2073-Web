import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet for handling reviews (fetching and inserting).
 */
@WebServlet("/ReviewServlet")
public class ReviewServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ebookshop";
    private static final String DB_USER = "root";  // Replace with your DB username
    private static final String DB_PASSWORD = "xxxx";  // Replace with your DB password

    /**
     * Handles GET requests: Fetch all reviews from the database.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StringBuilder jsonOutput = new StringBuilder();
        jsonOutput.append("[");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT user_id, rating, comment, created_at FROM Review ORDER BY created_at DESC")) {

            boolean first = true;
            while (rs.next()) {
                if (!first) {
                    jsonOutput.append(",");
                }
                
                // Escape special characters in the comment
                String comment = rs.getString("comment");
                comment = comment.replace("\\", "\\\\").replace("\"", "\\\"");
                
                jsonOutput.append("{")
                        .append("\"user_id\":").append(rs.getInt("user_id")).append(",")
                        .append("\"rating\":").append(rs.getInt("rating")).append(",")
                        .append("\"comment\":\"").append(comment).append("\",")
                        .append("\"created_at\":\"").append(rs.getTimestamp("created_at").toString()).append("\"")
                        .append("}");

                first = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Failed to fetch reviews\"}");
            return;
        }

        jsonOutput.append("]");
        response.getWriter().write(jsonOutput.toString());
    }

    /**
     * Handles POST requests: Insert a new review into the database.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Get user_id from the session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_id") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"message\": \"User is not logged in.\"}");
            return;
        }

        Integer userId = (Integer) session.getAttribute("user_id");
        if (userId == null || userId <= 0) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"message\": \"Invalid user session. Please log in again.\"}");
            return;
        }

        try {
            // Parse JSON manually with StringBuilder
            String jsonString = readJsonString(request);
            
            // Extract values from JSON
            int rating = extractInt(jsonString, "rating");
            if (rating < 1 || rating > 5) {
                response.getWriter().write("{\"message\": \"Rating must be between 1 and 5.\"}");
                return;
            }
            
            String comment = extractString(jsonString, "comment");
            if (comment == null || comment.trim().isEmpty()) {
                response.getWriter().write("{\"message\": \"Comment cannot be empty.\"}");
                return;
            }
            
            // Insert into database
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO Review (user_id, rating, comment) VALUES (?, ?, ?)")) {
                
                stmt.setInt(1, userId);
                stmt.setInt(2, rating);
                stmt.setString(3, comment.trim());
                
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    response.getWriter().write("{\"message\": \"Review submitted successfully!\"}");
                } else {
                    response.getWriter().write("{\"message\": \"Failed to submit review.\"}");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"message\": \"Database error while submitting review.\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Invalid input format: " + e.getMessage() + "\"}");
        }
    }

    /**
     * Reads the raw JSON request body into a string.
     */
    private String readJsonString(HttpServletRequest request) throws IOException {
        StringBuilder jsonInput = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonInput.append(line);
            }
        }
        return jsonInput.toString();
    }

    /**
     * Extracts an integer value from a JSON string.
     */
    private int extractInt(String json, String key) throws Exception {
        String keyPattern = "\"" + key + "\"";
        int keyIndex = json.indexOf(keyPattern);
        if (keyIndex == -1) {
            throw new Exception("Key '" + key + "' not found in JSON");
        }
        
        // Find the colon after the key
        int colonIndex = json.indexOf(':', keyIndex + keyPattern.length());
        if (colonIndex == -1) {
            throw new Exception("Invalid JSON format for key '" + key + "'");
        }
        
        // Find the start of the value (skipping whitespace)
        int valueStartIndex = colonIndex + 1;
        while (valueStartIndex < json.length() && Character.isWhitespace(json.charAt(valueStartIndex))) {
            valueStartIndex++;
        }
        
        // Find the end of the value (a comma or closing brace/bracket)
        int valueEndIndex = valueStartIndex;
        while (valueEndIndex < json.length()) {
            char c = json.charAt(valueEndIndex);
            if (c == ',' || c == '}' || c == ']') {
                break;
            }
            valueEndIndex++;
        }
        
        // Extract and parse the value
        String valueStr = json.substring(valueStartIndex, valueEndIndex).trim();
        try {
            return Integer.parseInt(valueStr);
        } catch (NumberFormatException e) {
            throw new Exception("Value for key '" + key + "' is not a valid integer: " + valueStr);
        }
    }

    /**
     * Extracts a string value from a JSON string.
     */
    private String extractString(String json, String key) throws Exception {
        String keyPattern = "\"" + key + "\"";
        int keyIndex = json.indexOf(keyPattern);
        if (keyIndex == -1) {
            throw new Exception("Key '" + key + "' not found in JSON");
        }
        
        // Find the colon after the key
        int colonIndex = json.indexOf(':', keyIndex + keyPattern.length());
        if (colonIndex == -1) {
            throw new Exception("Invalid JSON format for key '" + key + "'");
        }
        
        // Find the start of the value (skipping whitespace)
        int valueStartIndex = colonIndex + 1;
        while (valueStartIndex < json.length() && Character.isWhitespace(json.charAt(valueStartIndex))) {
            valueStartIndex++;
        }
        
        // Check if value is a string (starts with a quote)
        if (valueStartIndex >= json.length() || json.charAt(valueStartIndex) != '"') {
            throw new Exception("Value for key '" + key + "' is not a valid string");
        }
        
        // Find the end of the string (closing quote)
        valueStartIndex++; // Move past the opening quote
        StringBuilder value = new StringBuilder();
        boolean escaped = false;
        
        for (int i = valueStartIndex; i < json.length(); i++) {
            char c = json.charAt(i);
            
            if (escaped) {
                value.append(c);
                escaped = false;
            } else if (c == '\\') {
                escaped = true;
            } else if (c == '"') {
                // Found the closing quote
                return value.toString();
            } else {
                value.append(c);
            }
        }
        
        throw new Exception("Unterminated string value for key '" + key + "'");
    }
}