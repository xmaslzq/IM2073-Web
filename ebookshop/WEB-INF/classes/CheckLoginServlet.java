import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet to check if a user is logged in
 */
@WebServlet("/CheckLoginServlet")
public class CheckLoginServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession(false);
        boolean isLoggedIn = false;
        
        if (session != null && session.getAttribute("user_id") != null) {
            isLoggedIn = true;
        }
        
        response.getWriter().write("{\"loggedIn\": " + isLoggedIn + "}");
    }
}