package server;

import com.google.gson.Gson;
import exceptions.ResponseException;
import model.*;

import java.util.Collection;

import java.io.*;
import java.net.*;

public class ServerFacade {

    private final String serverUrl;

    public ServerFacade(String url){
        serverUrl = url;
    }

    public AuthData registerUser(UserData user) throws ResponseException {
        var path = "/user";
        return this.makeRequest("POST", path, user, AuthData.class);
    }

    public AuthData login(Login login) throws ResponseException {
        var path = "/session";
        return this.makeRequest("POST", path, login, AuthData.class);
    }

    public void logout(String authRequest) throws ResponseException {
        var path = String.format("/session/%s", authRequest);
        this.makeRequest("DELETE", path, null, null);
    }

    public Collection<GameList> listGames(String authRequest) throws ResponseException {
        var path = String.format("/game/%s", authRequest);
        record listGamesResponse(Collection<GameList> games) {
        }
        var response = this.makeRequest("GET", path, null, listGamesResponse.class);
        return response.games();
    }

    public GameData createGame(String authRequest, GameData gameName) throws ResponseException {
        var path = String.format("/game/%s", authRequest);
        return this.makeRequest("POST", path, gameName, GameData.class);
    }

    public void joinGame(String authToken, JoinRequest joinRequest) throws ResponseException {
        var path = String.format("/game/%s", authToken);
        this.makeRequest("POST", path, joinRequest, null);
    }

    public void clear() throws ResponseException {
        var path = "/db";
        this.makeRequest("DELETE", path, null, null);
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws ResponseException{
        try{
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            try (InputStream respErr = http.getErrorStream()) {
                if (respErr != null) {
                    throw ResponseException.fromJson(respErr);
                }
            }

            throw new ResponseException(status, "other failure: " + status);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }


    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}

