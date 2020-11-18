package am.gitc.chat.service.impl;

import am.gitc.chat.dao.MessagesDao;
import am.gitc.chat.dao.sql.MessagesDaoSql;
import am.gitc.chat.exceptions.DatabaseException;
import am.gitc.chat.model.Message;
import am.gitc.chat.service.MessagesService;

import java.sql.SQLException;
import java.util.List;

public class MessagesServiceImpl implements MessagesService {

  private MessagesDao messagesDao;

  public MessagesServiceImpl() {
    this.messagesDao = new MessagesDaoSql();
  }

  @Override
  public Message add(Message message) throws DatabaseException {
    try {
      return this.messagesDao.insert(message);
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
  }

  @Override
  public List<Message> getAllMessages(int senderId, int receiverId) throws DatabaseException {
    try {
      return this.messagesDao.fetchAll(senderId, receiverId);
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
  }
}
