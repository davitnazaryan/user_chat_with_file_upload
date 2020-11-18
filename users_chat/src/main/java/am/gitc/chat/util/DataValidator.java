package am.gitc.chat.util;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.IntegerValidator;

import java.util.Collection;

public class DataValidator {

  private DataValidator() {
    throw new SecurityException("Can't instantiate this class.");
  }

  public static boolean isEmpty(String value) {
    return value == null || value.trim().isEmpty();
  }

  public static boolean isEmpty(Collection<?> collection) {
    return collection == null || collection.isEmpty();
  }

  public static boolean isValidEmail(String email) {
    return EmailValidator.getInstance().isValid(email);
  }

  public static boolean isNumber(String value) {
    return IntegerValidator.getInstance().isValid(value);
  }
}
