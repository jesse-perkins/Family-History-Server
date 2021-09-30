package Handlers;

import Handlers.Request.LoadRequest;
import Handlers.Result.LoadResult;
import ServiceModels.LoadService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

/**
 * <h1>LoadHandler</h1>
 * <p>
 * The handler for the LoadService
 * </p>
 *
 * @author Jesse White
 * @version 0.1
 * @since 3/9/2017.
 */
public class LoadHandler implements HttpHandler {
    /**
     * Encoder / decoder for Json objects
     */
    private Gson gson = new Gson();

    /**
     * Handles the exchange regarding the LoadService
     *
     * @param exchange the HTTP Exchange handled by this function
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        InputStreamReader input = new InputStreamReader(exchange.getRequestBody());
        LoadRequest request = gson.fromJson(input, LoadRequest.class);
        input.close();
        exchange.getRequestBody().close();
        LoadResult result = LoadService.SINGLETON.load(request);
//        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        OutputStreamWriter output = new OutputStreamWriter(exchange.getResponseBody());
        gson.toJson(result, output);
        output.close();
    }
}
