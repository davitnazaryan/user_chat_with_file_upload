package am.gitc.chat.servlets.images;

import am.gitc.chat.util.Settings;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet("/images/*")
public class ImagesServlet extends HttpServlet {


  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String uri = req.getRequestURI();
    String imageName = uri.substring("/images/".length());
    String imagePath = Settings.getInstance().getString("images.path") + imageName;
    resp.setContentType("image/*");
    try (InputStream in = new FileInputStream(imagePath);
         OutputStream out = resp.getOutputStream()) {
      byte[] buffer = new byte[1024];
      int readCount;
      while ((readCount = in.read(buffer)) > -1) {
        out.write(buffer, 0, readCount);
      }
    }
  }
}
