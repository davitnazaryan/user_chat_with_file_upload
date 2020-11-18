package am.gitc.chat.servlets.users;

import am.gitc.chat.exceptions.DatabaseException;
import am.gitc.chat.model.User;
import am.gitc.chat.service.csv.CsvCreator;
import am.gitc.chat.service.csv.impl.CsvCreatorImpl;
import am.gitc.chat.service.excel.ExcelCreator;
import am.gitc.chat.service.excel.impl.ExcelCreatorImpl;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;

@WebServlet("/users/download")
public class UsersDownloadServlet extends BaseUserServlet {

  private final ExcelCreator excelCreator;
  private final CsvCreator csvCreator;

  public UsersDownloadServlet() {
    this.excelCreator = new ExcelCreatorImpl();
    this.csvCreator = new CsvCreatorImpl();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String type = req.getParameter("type");

    try {
      List<User> users = this.usersService.getAll();
      if ("xlsx".equalsIgnoreCase(type)) {
        resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        resp.setHeader("Content-Disposition", "attachment; filename=users.xlsx");

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             OutputStream clientOut = resp.getOutputStream();
             Workbook workbook = this.excelCreator.createWorkbook(users)) {
          workbook.write(out);
          byte[] data = out.toByteArray();
          for (int i = 0; i < data.length; i++) {
            clientOut.write(data[i]);
            Thread.sleep(30);
            clientOut.flush();
          }
        }
      } else {
        char[] data = csvCreator.createCsvData(users);
        resp.setContentType("text/csv");
        resp.setHeader("Content-Disposition", "attachment; filename=users.csv");
        resp.setHeader("Content-length", data.length + "");

        try (Writer out = resp.getWriter()) {
          for (int i = 0; i < data.length; i++) {
            out.write(data[i]);
            out.flush();
            Thread.sleep(30);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
