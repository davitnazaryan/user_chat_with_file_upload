package am.gitc.chat.dao;

import am.gitc.chat.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UsersDao {

  User insert(User user) throws SQLException;

  Optional<User> fetch(int id) throws SQLException;

  Optional<User> fetch(String email, String password) throws SQLException;

  boolean userExist(String email) throws SQLException;

  List<User> fetchAll() throws SQLException;

}
