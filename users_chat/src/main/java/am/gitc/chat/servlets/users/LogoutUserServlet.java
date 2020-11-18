package am.gitc.chat.servlets.users;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutUserServlet extends BaseUserServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    req.getSession().invalidate();
    resp.sendRedirect("login");
  }
}
