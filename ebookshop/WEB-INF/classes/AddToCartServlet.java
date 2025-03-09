import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/addToCart")
public class AddToCartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        int userId = Integer.parseInt(request.getParameter("userId")); // Retrieve userId
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        int quantity = 1; // Default to 1 when first added

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ebookshop?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "user", "xxxx")) {

            // Get user's cart ID
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

            // Check if item already exists in cart
            PreparedStatement checkStmt = conn.prepareStatement(
                    "SELECT quantity FROM Cart_Item WHERE cart_id = ? AND book_id = ?");
            checkStmt.setInt(1, cartId);
            checkStmt.setInt(2, bookId);
            ResultSet checkRs = checkStmt.executeQuery();

            if (checkRs.next()) {
                // Update quantity
                int newQuantity = checkRs.getInt("quantity") + 1;
                PreparedStatement updateStmt = conn.prepareStatement(
                        "UPDATE Cart_Item SET quantity = ? WHERE cart_id = ? AND book_id = ?");
                updateStmt.setInt(1, newQuantity);
                updateStmt.setInt(2, cartId);
                updateStmt.setInt(3, bookId);
                updateStmt.executeUpdate();
            } else {
                // Insert new item into cart
                PreparedStatement insertStmt = conn.prepareStatement(
                        "INSERT INTO Cart_Item (cart_id, book_id, quantity) VALUES (?, ?, ?)");
                insertStmt.setInt(1, cartId);
                insertStmt.setInt(2, bookId);
                insertStmt.setInt(3, quantity);
                insertStmt.executeUpdate();
            }

            out.print("{\"success\": \"Book added to cart.\"}");
        } catch (SQLException e) {
            out.print("{\"error\": \"Database error: " + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }
}
// To insert items into Cart_Item table