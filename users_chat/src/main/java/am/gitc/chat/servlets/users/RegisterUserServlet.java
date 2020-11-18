package am.gitc.chat.servlets.users;

import am.gitc.chat.model.User;
import am.gitc.chat.servlets.util.validator.RequestValidator;
import am.gitc.chat.util.DataValidator;
import am.gitc.chat.util.EncryptionUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/register")
public class RegisterUserServlet extends BaseUserServlet {

  private List<User> users;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.getRequestDispatcher("WEB-INF/pages/register.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      RequestValidator<User> validator = this.validate(req);
      if (!validator.hasError()) {
        User user = validator.getValue();
        boolean userExist = this.usersService.userExist(user.getEmail());
        if (!userExist) {
          InputStream imageStream = !DataValidator.isEmpty(validator.getFileItems()) ? validator.getFileItems().get(0).getInputStream() : null;
          this.usersService.add(user, imageStream);
          req.getSession().setAttribute("registered", "");
          resp.sendRedirect("login");
          return;
        } else {
          req.setAttribute("userExist", String.format("User with email = %s " + " already exists!", user.getEmail()));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      req.setAttribute("globalError", "");
    }
    req.getRequestDispatcher("WEB-INF/pages/register.jsp").forward(req, resp);
  }

  private RequestValidator<User> validate(HttpServletRequest req) throws FileUploadException {
    String name = null;
    String surname = null;
    String email = null;
    String password = null;
    String confirmPassword = null;
    List<FileItem> fileItems = new ArrayList<>();

    boolean isMultipart = ServletFileUpload.isMultipartContent(req);
    if (isMultipart) {
      FileItemFactory factory = new DiskFileItemFactory();
      ServletFileUpload upload = new ServletFileUpload(factory);
      List<FileItem> items = upload.parseRequest(req);
      for (FileItem item : items) {
        if (item.isFormField()) {
          switch (item.getFieldName()) {
            case "name":
              name = item.getString();
              break;
            case "surname":
              surname = item.getString();
              break;
            case "email":
              email = item.getString();
              break;
            case "password":
              password = item.getString();
              break;
            case "confirmPassword":
              confirmPassword = item.getString();
              break;
          }
        } else {
          if (item.getSize() > 0) {
            fileItems.add(item);
          }
        }
      }
    } else {
      name = req.getParameter("name");
      surname = req.getParameter("surname");
      email = req.getParameter("email");
      password = req.getParameter("password");
      confirmPassword = req.getParameter("confirmPassword");
    }

    boolean hasError = false;

    if (DataValidator.isEmpty(name)) {
      req.setAttribute("errorName", "Name is required!");
      hasError = true;
    }
    if (DataValidator.isEmpty(surname)) {
      req.setAttribute("errorSurname", "Surname is required!");
      hasError = true;
    }
    if (DataValidator.isEmpty(email)) {
      req.setAttribute("errorEmail", "Email is required!");
      hasError = true;
    } else if (!DataValidator.isValidEmail(email)) {
      req.setAttribute("errorEmail", "Wrong email format!");
      hasError = true;
    }
    if (DataValidator.isEmpty(password)) {
      req.setAttribute("errorPassword", "Password is required!");
      hasError = true;
    } else {
      if (DataValidator.isEmpty(confirmPassword) || !password.equals(confirmPassword)) {
        req.setAttribute("errorConfirmPassword", "Password doesn't match!");
        hasError = true;
      }
    }
    RequestValidator<User> requestValidator = new RequestValidator<User>();
    if (!hasError) {
      User user = new User();
      user.setName(name);
      user.setSurname(surname);
      user.setEmail(email);
      user.setPassword(EncryptionUtil.encrypt(password));
      requestValidator.setValue(user);
      requestValidator.setFileItems(fileItems);
    } else {
      requestValidator.setHasError(true);
    }
    return requestValidator;
  }
}
