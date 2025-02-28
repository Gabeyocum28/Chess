package server;

import com.google.gson.Gson;
import model.AuthData;
import model.UserData;
import service.RegisterService;
import spark.Request;
import spark.Response;

public class RegistrationHandler {
    public static String register(Request request, Response response){
        Gson gson =  new Gson();
        UserData newUser = gson.fromJson(request.body(), UserData.class);
        AuthData auth = RegisterService.registerUser(newUser);
        return gson.toJson(auth);

    }
}
