package am.gitc.chat.servlets.users;

import am.gitc.chat.model.Message;
import am.gitc.chat.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet(urlPatterns = {"/home", ""})
public class HomeServlet extends BaseUserServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    try {
      User user = (User) req.getSession().getAttribute("user");
      List<User> users = this.usersService.getAll();
      List<Message> messages = this.messagesService.getAllMessages(user.getId(), user.getId());
      req.setAttribute("users", users);
      req.setAttribute("messages", messages);
      req.getRequestDispatcher("WEB-INF/pages/home.jsp").forward(req, resp);
    } catch (Exception e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
