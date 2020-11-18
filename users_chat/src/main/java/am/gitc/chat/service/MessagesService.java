package am.gitc.chat.service;

import am.gitc.chat.exceptions.DatabaseException;
import am.gitc.chat.model.Message;

import java.util.List;

public interface MessagesService {

  Message add(Message message) throws DatabaseException;

  List<Message> getAllMessages(int senderId, int receiverId) throws DatabaseException;
}
