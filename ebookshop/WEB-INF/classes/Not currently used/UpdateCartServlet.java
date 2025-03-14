import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/updateCart")
public class UpdateCartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        int userId = Integer.parseInt(request.getParameter("userId")); // User ID
        int bookId = Integer.parseInt(request.getParameter("bookId")); // Book ID
        int newQuantity = Integer.parseInt(request.getParameter("quantity")); // New quantity

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ebookshop?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "xxxx")) {

            // Get the user's cart ID
            PreparedStatement cartStmt = conn.prepareStatement(
                    "SELECT cart_id FROM Shopping_Cart WHERE user_id = ?");
            cartStmt.setInt(1, userId);
            ResultSet cartRs = cartStmt.executeQuery();

            int cartId = -1;
            if (cartRs.next()) {
                cartId = cartRs.getInt("cart_id");
            } else {
                out.print("{\"error\": \"Cart not found for user.\"}");
                return;
            }

            if (newQuantity > 0) {
                // Update item quantity
                PreparedStatement updateStmt = conn.prepareStatement(
                        "UPDATE Cart_Item SET quantity = ? WHERE cart_id = ? AND book_id = ?");
                updateStmt.setInt(1, newQuantity);
                updateStmt.setInt(2, cartId);
                updateStmt.setInt(3, bookId);
                updateStmt.executeUpdate();

                out.print("{\"success\": \"Quantity updated.\"}");
            } else {
                // Remove item if quantity is 0
                PreparedStatement deleteStmt = conn.prepareStatement(
                        "DELETE FROM Cart_Item WHERE cart_id = ? AND book_id = ?");
                deleteStmt.setInt(1, cartId);
                deleteStmt.setInt(2, bookId);
                deleteStmt.executeUpdate();

                out.print("{\"success\": \"Item removed from cart.\"}");
            }
        } catch (SQLException e) {
            out.print("{\"error\": \"Database error: " + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }
}
