package server;

import com.google.gson.Gson;
import spark.Response;

import java.util.Map;

public class ErrorHandler {
    public String error(int errorType, Response response, Exception ex) {
        response.status(errorType);
        String json = new Gson().toJson(Map.of("message", ex.getMessage()));
        response.body(json);
        return json;
    }
}
