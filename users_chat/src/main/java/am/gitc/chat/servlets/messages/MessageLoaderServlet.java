package am.gitc.chat.servlets.messages;

import am.gitc.chat.model.Message;
import am.gitc.chat.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

@WebServlet("/messages")
public class MessageLoaderServlet extends BaseMessagesServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      int senderId = Integer.parseInt(req.getParameter("senderId"));
      int receiverId = Integer.parseInt(req.getParameter("receiverId"));

      List<Message> messages = super.messagesService.getAllMessages(senderId, receiverId);
      String responseContent = JsonUtil.serialize(messages);
      resp.setContentType("application/json");
      try (Writer writer = resp.getWriter()) {
        writer.write(responseContent);
      }
    } catch (Exception e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
