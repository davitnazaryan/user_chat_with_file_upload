package am.gitc.chat.servlets.users;

import am.gitc.chat.model.User;
import am.gitc.chat.servlets.util.validator.RequestValidator;
import am.gitc.chat.util.DataValidator;
import am.gitc.chat.util.EncryptionUtil;
import am.gitc.chat.util.Settings;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginUserServlet extends BaseUserServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    RequestValidator<User> validator = this.validate(req);
    System.out.println(Settings.getInstance().getString("images.path"));
    try {
      if (!validator.hasError()) {
        User user = validator.getValue();
        Optional<User> optionalUser = this.usersService.get(user.getEmail(), user.getPassword());
        if (!optionalUser.isPresent()) {
          req.setAttribute("wrongEmailPassword", "Wrong Email or Password!");
        } else {
          user = optionalUser.get();
          req.getSession().setAttribute("user", user);
          resp.sendRedirect("home");
          return;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      req.setAttribute("globalError", "");
    }
    req.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(req, resp);
  }

  private RequestValidator<User> validate(HttpServletRequest req) {
    boolean hasError = false;
    String email = req.getParameter("email");
    String password = req.getParameter("password");
    if (DataValidator.isEmpty(email)) {
      req.setAttribute("errorEmail", "Email is required!");
      hasError = true;
    }
    if (DataValidator.isEmpty(password)) {
      req.setAttribute("errorPassword", "Password is required!");
      hasError = true;
    }
    RequestValidator<User> requestValidator = new RequestValidator<>();
    if (!hasError) {
      User user = new User();
      user.setEmail(email);
      user.setPassword(EncryptionUtil.encrypt(password));
      requestValidator.setValue(user);
    } else {
      requestValidator.setHasError(true);
    }
    return requestValidator;
  }
}
