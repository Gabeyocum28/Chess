package server;

import com.google.gson.Gson;
import exceptions.UnauthorizedException;
import service.LogoutService;
import spark.Request;
import spark.Response;

public class LogoutHandler {
    public static String logout(Request request, Response response) throws Exception {
        Gson gson =  new Gson();
        String authToken = request.headers("authorization");
        try {
            new LogoutService().logout(authToken);
            return "{}";
        }catch (UnauthorizedException ex){
            return new ErrorHandler().error(401, response, ex);
        }catch (Exception ex){
            return new ErrorHandler().error(500, response, ex);
        }

    }
}
