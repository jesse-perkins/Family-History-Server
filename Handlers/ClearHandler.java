package Handlers;

import Handlers.Result.ClearResult;
import ServiceModels.ClearService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

/**
 * <h1>ClearHandler</h1>
 * <p>
 * The handler for the ClearService
 * </p>
 *
 * @author Jesse White
 * @version 0.1
 * @since 3/9/2017.
 */
public class ClearHandler implements HttpHandler{
    /**
     * Encoder / decoder for Json objects
     */
    private Gson gson = new Gson();

    /**
     * Handles the exchange regarding the ClearService
     *
     * @param exchange the HTTP Exchange handled by this function
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ClearResult result = ClearService.SINGLETON.clear();
        OutputStreamWriter output = new OutputStreamWriter(exchange.getResponseBody());
        gson.toJson(result, output);
        //0 means that there is something in the response body
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        output.close();
    }
}
