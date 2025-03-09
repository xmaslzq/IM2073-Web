import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/getCart")
public class GetCartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        int userId = Integer.parseInt(request.getParameter("userId"));

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ebookshop?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "user", "xxxx")) {

            // Get cart ID for user
            PreparedStatement cartStmt = conn.prepareStatement(
                    "SELECT cart_id FROM Shopping_Cart WHERE user_id = ?");
            cartStmt.setInt(1, userId);
            ResultSet cartRs = cartStmt.executeQuery();

            int cartId = -1;
            if (cartRs.next()) {
                cartId = cartRs.getInt("cart_id");
            } else {
                out.print("{\"error\": \"Cart not found.\"}");
                return;
            }

            // Get cart items with book details
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT c.book_id, b.title, b.price, c.quantity FROM Cart_Item c " +
                    "JOIN Book b ON c.book_id = b.book_id WHERE c.cart_id = ?");
            stmt.setInt(1, cartId);
            ResultSet rs = stmt.executeQuery();

            // Construct JSON manually
            StringBuilder jsonOutput = new StringBuilder();
            jsonOutput.append("{\"cart\": [");

            boolean firstItem = true;
            while (rs.next()) {
                if (!firstItem) {
                    jsonOutput.append(",");
                }
                jsonOutput.append("{")
                        .append("\"bookId\":").append(rs.getInt("book_id")).append(",")
                        .append("\"title\":\"").append(rs.getString("title").replace("\"", "\\\"")).append("\",")
                        .append("\"price\":").append(rs.getDouble("price")).append(",")
                        .append("\"quantity\":").append(rs.getInt("quantity"))
                        .append("}");
                firstItem = false;
            }

            jsonOutput.append("]}");
            out.print(jsonOutput.toString());

        } catch (SQLException e) {
            out.print("{\"error\": \"Database error: " + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }
}
