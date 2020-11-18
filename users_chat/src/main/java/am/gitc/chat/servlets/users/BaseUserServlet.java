package am.gitc.chat.servlets.users;

import am.gitc.chat.service.MessagesService;
import am.gitc.chat.service.UsersService;
import am.gitc.chat.service.impl.MessagesServiceImpl;
import am.gitc.chat.service.impl.UsersServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

abstract class BaseUserServlet extends HttpServlet {

  protected UsersService usersService;
  protected MessagesService messagesService;

  @Override
  public void init() throws ServletException {
    super.init();
    this.usersService = new UsersServiceImpl();
    this.messagesService = new MessagesServiceImpl();
  }
}
