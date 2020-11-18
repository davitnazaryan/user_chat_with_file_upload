package am.gitc.chat.dao.sql;

import am.gitc.chat.model.Message;
import am.gitc.chat.dao.MessagesDao;
import am.gitc.chat.util.DataSource;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class MessagesDaoSql implements MessagesDao {

  @Override
  public Message insert(Message message) throws SQLException {
    String query = "INSERT INTO messages(sender_id, receiver_id, message) values(?, ?, ?);";
    try (Connection connection = DataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

      preparedStatement.setInt(1, message.getSenderId());
      preparedStatement.setInt(2, message.getReceiverId());
      preparedStatement.setString(3, message.getMessage());
      preparedStatement.execute();

      try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
        if (resultSet.next()) {
          message.setId(resultSet.getInt(1));
        }
      }
    }
    return message;
  }

  @Override
  public List<Message> fetchAll(int senderId, int receiverId) throws SQLException {
    String query = "select * from messages where (sender_id = ? and receiver_id = ?)" +
        " or (sender_id = ? and receiver_id = ?) order by created_at;";
    List<Message> messages = new LinkedList<>();
    try (Connection connection = DataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      preparedStatement.setInt(1, senderId);
      preparedStatement.setInt(2, receiverId);
      preparedStatement.setInt(3, receiverId);
      preparedStatement.setInt(4, senderId);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          messages.add(createFromResultSet(resultSet));
        }
      }
    }
    return messages;
  }

  private Message createFromResultSet(ResultSet resultSet) throws SQLException {
    Message message = new Message();
    message.setId(resultSet.getInt("id"));
    message.setSenderId(resultSet.getInt("sender_id"));
    message.setReceiverId(resultSet.getInt("receiver_id"));
    message.setMessage(resultSet.getString("message"));
    message.setCreatedAt(resultSet.getTimestamp("created_at"));
    return message;
  }
}
