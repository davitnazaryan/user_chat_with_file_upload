package am.gitc.chat.service.csv.impl;

import am.gitc.chat.service.csv.CsvCreator;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public class CsvCreatorImpl implements CsvCreator {

  @Override
  public char[] createCsvData(List<?> data) throws IOException, NoSuchFieldException, IllegalAccessException {
    List<String> headers = createHeader(data.get(0).getClass());
    try (CharArrayWriter writer = new CharArrayWriter();
         CSVPrinter csvPrinter = CSVFormat
             .newFormat(',')
             .withDelimiter(',')
             .withQuote('"')
             .withRecordSeparator("\n")
             .withQuoteMode(QuoteMode.NON_NUMERIC)
             .withHeader(headers.toArray(new String[headers.size()]))
             .print(writer)) {
      for (Object obj : data) {
        csvPrinter.printRecord(createContent(headers, obj));
      }
      return writer.toCharArray();
    }
  }

  private static List<String> createHeader(Class<?> clazz) {
    List<String> headers = new LinkedList<>();
    Field[] fields = clazz.getDeclaredFields();
    for (Field field : fields) {
      headers.add(field.getName());
    }
    return headers;
  }

  private static List<Object> createContent(List<String> headers, Object obj) throws NoSuchFieldException, IllegalAccessException {
    List<Object> data = new LinkedList<>();
    Class<?> clazz = obj.getClass();
    for (String columnName : headers) {
      Field field = clazz.getDeclaredField(columnName);
      field.setAccessible(true);
      data.add(field.get(obj));
    }
    return data;
  }
}
