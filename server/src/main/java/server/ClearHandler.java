package server;

import com.google.gson.Gson;
import service.ClearService;
import spark.Request;
import spark.Response;

public class ClearHandler {
    public static String clear(Request request, Response response) throws Exception {
        Gson gson =  new Gson();
        new ClearService().clear();
        return "{}";
    }
}
