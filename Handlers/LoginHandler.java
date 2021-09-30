package Handlers;

import Handlers.Request.LoginRequest;
import Handlers.Result.LoginResult;
import ServiceModels.LoginService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

/**
 * <h1>LoginHandler</h1>
 * <p>
 * The handler for the LoginService
 * </p>
 *
 * @author Jesse White
 * @version 0.1
 * @since 3/9/2017.
 */
public class LoginHandler implements HttpHandler {
    /**
     * Encoder / decoder for Json objects
     */
    private Gson gson = new Gson();

    /**
     * Handles the exchange regarding the LoginService
     *
     * @param exchange the HTTP Exchange handled by this function
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException{
        InputStreamReader input = new InputStreamReader(exchange.getRequestBody());
        LoginRequest request = gson.fromJson(input, LoginRequest.class);
        input.close();
        exchange.getRequestBody().close();
        LoginResult result = LoginService.SINGLETON.login(request);
        OutputStreamWriter output = new OutputStreamWriter(exchange.getResponseBody());
        gson.toJson(result, output);
        //0 means that there is something in the response body
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        output.close();
    }
}
