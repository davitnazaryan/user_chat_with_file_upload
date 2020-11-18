package am.gitc.chat.service.csv;

import java.io.IOException;
import java.util.List;

public interface CsvCreator {

  char[] createCsvData(List<?> data) throws Exception;
}
