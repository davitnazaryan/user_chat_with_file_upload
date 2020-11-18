package am.gitc.chat.exceptions;

public class FileUploadException extends Exception {

  private static final long serialVersionUID = 452786993124545454L;

  public FileUploadException() {
    super();
  }

  public FileUploadException(String message) {
    super(message);
  }

  public FileUploadException(String message, Throwable cause) {
    super(message, cause);
  }

  public FileUploadException(Throwable cause) {
    super(cause);
  }

  protected FileUploadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
