import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;

public class CartServlet extends HttpServlet {

    // A simple CartItem class for the demo
    static class CartItem {
        String title;
        double price;
        int quantity;
        CartItem(String title, double price, int quantity) {
            this.title = title;
            this.price = price;
            this.quantity = quantity;
        }
    }

    private List<CartItem> getCart(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<CartItem> cart = getCart(session);

        // Setting response content type to JSON
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // Prepare the cart response
        StringBuilder cartJson = new StringBuilder();
        cartJson.append("{\"cart\":[");

        double total = 0.0;
        for (CartItem item : cart) {
            total += item.price * item.quantity;
            cartJson.append("{\"title\":\"")
                    .append(item.title)
                    .append("\",\"price\":")
                    .append(item.price)
                    .append(",\"quantity\":")
                    .append(item.quantity)
                    .append("},");
        }

        if (cart.size() > 0) {
            cartJson.deleteCharAt(cartJson.length() - 1); // Remove last comma
        }

        cartJson.append("],\"totalPrice\":")
                .append(total)
                .append("}");

        out.print(cartJson.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<CartItem> cart = getCart(session);

        String title = request.getParameter("title");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        // Add the new item to the cart
        cart.add(new CartItem(title, price, quantity));

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("Item added to cart");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<CartItem> cart = getCart(session);

        String title = request.getParameter("title");

        // Find and remove the item by title
        cart.removeIf(item -> item.title.equals(title));

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("Item removed from cart");
    }
}
