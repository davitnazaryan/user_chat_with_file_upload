package am.gitc.chat.dao.sql;

import am.gitc.chat.model.User;
import am.gitc.chat.dao.UsersDao;
import am.gitc.chat.util.DataSource;

import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class UsersDaoSql implements UsersDao {

  @Override
  public User insert(User user) throws SQLException {
    String query = "INSERT INTO users(name,surname,email,password,image_url) values(?,?,?,?,?);";
    try (Connection connection = DataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query,
             Statement.RETURN_GENERATED_KEYS)) {

      preparedStatement.setString(1, user.getName());
      preparedStatement.setString(2, user.getSurname());
      preparedStatement.setString(3, user.getEmail());
      preparedStatement.setString(4, user.getPassword());
      preparedStatement.setString(5, user.getImageUrl());
      preparedStatement.execute();
      try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
        if (resultSet.next()) {
          user.setId(resultSet.getInt(1));
        }
      }
    }
    return user;
  }

  @Override
  public Optional<User> fetch(String email, String password) throws SQLException {
    String query = "select * from users where email = ? and password = ?;";
    User user = null;
    try (Connection connection = DataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      preparedStatement.setString(1, email);
      preparedStatement.setString(2, password);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          user = createFromResultSet(resultSet);
        }
      }
    }
    return user == null ? Optional.empty() : Optional.of(user);
  }

  @Override
  public boolean userExist(String email) throws SQLException {
    String query = "select email from users where email = ?;";

    try (Connection connection = DataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      preparedStatement.setString(1, email);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public Optional<User> fetch(int id) throws SQLException {
    String query = "SELECT * FROM users where id = ?;";
    User user = null;
    try (Connection connection = DataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      preparedStatement.setInt(1, id);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          user = createFromResultSet(resultSet);
        }
      }
    }
    return user == null ? Optional.empty() : Optional.of(user);
  }

  @Override
  public List<User> fetchAll() throws SQLException {
    List<User> users = new LinkedList<>();
    String query = "SELECT * FROM users;";
    try (Connection connection = DataSource.getConnection();
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {
      while (resultSet.next()) {
        users.add(createFromResultSet(resultSet));
      }
    }
    return users;
  }

  private User createFromResultSet(ResultSet resultSet) throws SQLException {
    User user = new User();
    user.setId(resultSet.getInt("id"));
    user.setName(resultSet.getString("name"));
    user.setSurname(resultSet.getString("surname"));
    user.setEmail(resultSet.getString("email"));
    user.setPassword(resultSet.getString("password"));
    user.setImageUrl(resultSet.getString("image_url"));
    return user;
  }
}
