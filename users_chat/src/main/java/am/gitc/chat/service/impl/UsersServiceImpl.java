package am.gitc.chat.service.impl;

import am.gitc.chat.dao.UsersDao;
import am.gitc.chat.dao.sql.UsersDaoSql;
import am.gitc.chat.exceptions.DatabaseException;
import am.gitc.chat.exceptions.FileUploadException;
import am.gitc.chat.model.User;
import am.gitc.chat.service.UsersService;
import am.gitc.chat.util.Settings;

import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UsersServiceImpl implements UsersService {

  private UsersDao usersDao;

  public UsersServiceImpl() {
    this.usersDao = new UsersDaoSql();
  }

  @Override
  public User add(User user, InputStream imageContent) throws DatabaseException, FileUploadException {
    String imageName = UUID.nameUUIDFromBytes(user.getEmail().getBytes()).toString();
    String path = Settings.getInstance().getString("images.path") + imageName;
    try {
      if (imageContent != null) {
        try (OutputStream out = new FileOutputStream(path)) {
          byte[] buffer = new byte[2048];
          int readCount;
          while ((readCount = imageContent.read(buffer)) > -1) {
            out.write(buffer, 0, readCount);
          }
          user.setImageUrl("/images/" + imageName);
        } catch (IOException e) {
          throw new FileUploadException(e);
        }
      } else {
        user.setImageUrl("/images/incognito.png");
      }
      user = this.usersDao.insert(user);
      return user;
    } catch (SQLException e) {
      if (user.getId() > 0) {
        new File(path).delete();
      }
      throw new DatabaseException(e);
    }
  }

  @Override
  public Optional<User> get(int id) throws DatabaseException {
    try {
      return this.usersDao.fetch(id);
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
  }

  @Override
  public Optional<User> get(String email, String password) throws DatabaseException {
    try {
      return this.usersDao.fetch(email, password);
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
  }

  @Override
  public boolean userExist(String email) throws DatabaseException {
    try {
      return this.usersDao.userExist(email);
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
  }

  @Override
  public List<User> getAll() throws DatabaseException {
    try {
      return this.usersDao.fetchAll();
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
  }
}
