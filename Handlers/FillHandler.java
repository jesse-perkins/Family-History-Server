package Handlers;

import Handlers.Result.FillResult;
import ServiceModels.FillService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;

/**
 * <h1>FillHandler</h1>
 * <p>
 * The handler for the FillService
 * </p>
 *
 * @author Jesse White
 * @version 0.1
 * @since 3/9/2017.
 */
public class FillHandler implements HttpHandler {
    /**
     * Encoder / decoder for Json objects
     */
    private Gson gson = new Gson();

    /**
     * Handles the exchange regarding the FillService
     *
     * @param exchange the HTTP Exchange handled by this function
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //Snag parameters out of the URI if the exist
        URI uri = exchange.getRequestURI();
        String path = uri.getPath();
        String[] pathSegments = path.split("/");
        //[0]="", [1]="fill", [2]="username", [3]"numGenerations"

        int numGenerations = 4;
        if(pathSegments.length > 3){
            numGenerations = Integer.parseInt(pathSegments[3]);
        }
        FillResult result;
        if(numGenerations < 1){
            result = new FillResult(("Error: Invalid Generation Parameter passed in: " + numGenerations));
        }else if(pathSegments.length > 2) {
            String username = pathSegments[2];
            result = FillService.SINGLETON.fill(numGenerations, username);
        }else{
            result = new FillResult(("Error: No username passed in."));
        }
        OutputStreamWriter output = new OutputStreamWriter(exchange.getResponseBody());
        gson.toJson(result, output);
        //0 means that there is something in the response body
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        output.close();
    }
}
