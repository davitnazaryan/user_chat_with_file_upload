package am.gitc.chat.dao;

import am.gitc.chat.model.Message;

import java.sql.SQLException;
import java.util.List;

public interface MessagesDao {

  Message insert(Message message) throws SQLException;

  List<Message> fetchAll(int senderId, int receiverId) throws SQLException;

}
