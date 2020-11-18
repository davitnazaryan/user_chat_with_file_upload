package am.gitc.chat.exceptions;

public class DatabaseException extends Exception {

  private static final long serialVersionUID = 3388786993124545454L;

  public DatabaseException() {
    super();
  }

  public DatabaseException(String message) {
    super(message);
  }

  public DatabaseException(String message, Throwable cause) {
    super(message, cause);
  }

  public DatabaseException(Throwable cause) {
    super(cause);
  }

  protected DatabaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
