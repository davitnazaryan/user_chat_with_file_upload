package am.gitc.chat.util;

import org.apache.commons.codec.digest.DigestUtils;

public class EncryptionUtil {

  private EncryptionUtil() {
    throw new SecurityException("Can't instantiate this class.");
  }

  public static String encrypt(String text) {
    return DigestUtils.sha256Hex(text);
  }
}
