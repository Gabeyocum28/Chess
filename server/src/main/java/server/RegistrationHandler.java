package server;

import com.google.gson.Gson;
import exceptions.AlreadyTakenException;
import model.AuthData;
import model.UserData;
import service.RegisterService;
import spark.Request;
import spark.Response;

import java.util.Map;

public class RegistrationHandler {
    public static String register(Request request, Response response) throws Exception {
        Gson gson =  new Gson();
        UserData newUser = gson.fromJson(request.body(), UserData.class);
        if(newUser.username() == null || newUser.password() == null || newUser.email() == null){
            response.status(400);
            String json = new Gson().toJson(Map.of("message", "Error: bad request"));
            response.body(json);
            return json;
        }
        try {
            AuthData auth = RegisterService.registerUser(newUser);
            return gson.toJson(auth);
        }catch (AlreadyTakenException ex){
            return new ErrorHandler().error(403, response, ex);
        }catch (Exception ex){
            return new ErrorHandler().error(500, response, ex);
        }

    }


}
