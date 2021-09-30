package Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <h1>DefaultHandler</h1>
 * <p>
 * The handler for the DefaultService
 * </p>
 *
 * @author Jesse White
 * @version 0.1
 * @since 3/9/2017.
 */
public class DefaultHandler implements HttpHandler
{
    /**
     * Handles the exchange regarding the DefaultService
     *
     * @param exchange the HTTP Exchange handled by this function
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        String pathString = uri.getPath();

        if(pathString.equals("/")) {
            pathString = DEFAULT_FILE_NAME;
        }

        int responseCode = 0;
        int bodyIsEmptyCode = 0;
        Path path = Paths.get(HTTP_ROOT + pathString);
        byte[] result = new byte[0];
        try {
            result = Files.readAllBytes(path);
            responseCode = HttpURLConnection.HTTP_OK;
            bodyIsEmptyCode = 0;
        } catch (IOException error404) {
            try {
                path = Paths.get(HTTP_ROOT + LOC_404);
                result = Files.readAllBytes(path);
                responseCode = HttpURLConnection.HTTP_NOT_FOUND;
                bodyIsEmptyCode = 0;
            } catch (IOException cantReadLoc_404File) {
                bodyIsEmptyCode = -1;
                responseCode = HttpURLConnection.HTTP_NOT_FOUND;
            }
        }

        //bodyIsEmptyCode =  0 means something is coming back
        //bodyIsEmptyCode = -1 means nothing is coming back
        exchange.sendResponseHeaders(responseCode, bodyIsEmptyCode);

        OutputStream os = exchange.getResponseBody();
        os.write(result);
        os.close();
    }

    //CONSTANTS
    private static final String DEFAULT_FILE_NAME = "/index.html";
    private static final String LOC_404 = "/404.html";
    private static final String HTTP_ROOT = "HTML"; //What goes here?
}
