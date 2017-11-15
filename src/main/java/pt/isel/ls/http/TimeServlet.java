package pt.isel.ls.http;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class TimeServlet extends HttpServlet {

    private static final Logger _logger = LoggerFactory.getLogger(TimeServlet.class);


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        _logger.info("{} on '{}' with accept:'{}', instance {}",
                req.getMethod(), req.getRequestURI(), req.getHeader("Accept"),
                this.hashCode());

        Charset utf8 = Charset.forName("utf-8");
        resp.setContentType(String.format("text/html; charset=%s", utf8.name()));

        String respBody = String.format("<h1>Current date and time is %s</h1>\nVersion:0.5.0", new DateTime().toString());
        byte[] respBodyBytes = respBody.getBytes(utf8);
        resp.setStatus(200);
        resp.setContentLength(respBodyBytes.length);
        OutputStream os = resp.getOutputStream();
        os.write(respBodyBytes);
        os.close();
    }
}
