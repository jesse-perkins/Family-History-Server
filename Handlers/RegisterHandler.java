package Handlers;

import Handlers.Request.RegisterRequest;
import Handlers.Result.RegisterResult;
import ServiceModels.RegisterService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

/**
 * <h1>RegisterHandler</h1>
 * <p>
 * The handler for the RegisterService
 * </p>
 *
 * @author Jesse White
 * @version 0.1
 * @since 3/9/2017.
 */
public class RegisterHandler implements HttpHandler {
    /**
     * Encoder / decoder for Json objects
     */
    private Gson gson = new Gson();

    /**
     * Handles the exchange regarding the RegisterService
     *
     * @param exchange the HTTP Exchange handled by this function
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        InputStreamReader input = new InputStreamReader(exchange.getRequestBody());
        RegisterRequest request = gson.fromJson(input, RegisterRequest.class);
        input.close();
        exchange.getRequestBody().close();
        RegisterResult result = RegisterService.SINGLETON.register(request);
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        OutputStreamWriter output = new OutputStreamWriter(exchange.getResponseBody());
        gson.toJson(result, output);
        output.close();
    }
}
