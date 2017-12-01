package pt.isel.ls.heroku;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.http.TimeServlet;

public class HttpServer {

    /* 
     * TCP port where to listen. 
     * Standard port for HTTP is 80 but might be already in use
     */
    private static final int LISTEN_PORT = 8081;

    public static void main(String[] args) throws Exception {

        System.setProperty("org.slf4j.simpleLogger.levelInBrackets", "true");

        Logger logger = LoggerFactory.getLogger(HttpServer.class);
        logger.info("Starting main...");

        String portDef = System.getenv("PORT");
        int port = portDef != null ? Integer.valueOf(portDef) : LISTEN_PORT;
        logger.info("Listening on port {}", port);

        PGSimpleDataSource ds = new PGSimpleDataSource();
        String jdbcUrl = System.getenv("JDBC_DATABASE_URL");
        if(jdbcUrl == null) {
            logger.error("JDBC_DATABASE_URL is not defined, ending");
            return;
        }
        ds.setUrl(jdbcUrl);

        Server server = new Server(port);
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(new ServletHolder(new HerokuServlet(ds)), "/*");
        server.start();
        logger.info("Server started");
        server.join();

        logger.info("main ends.");
    }
}
