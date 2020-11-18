package am.gitc.chat.servlets.messages;

import am.gitc.chat.service.MessagesService;
import am.gitc.chat.service.impl.MessagesServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public abstract class BaseMessagesServlet extends HttpServlet {

  protected MessagesService messagesService;

  @Override
  public void init() throws ServletException {
    super.init();
    this.messagesService = new MessagesServiceImpl();
  }
}
