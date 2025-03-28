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
        return this.makeRequest("POST", "/user", user, AuthData.class, null);
    }

    public AuthData login(Login login) throws ResponseException {
        return this.makeRequest("POST", "/session", login, AuthData.class, null);
    }

    public void logout(String authToken) throws ResponseException {
        this.makeRequest("DELETE", "/session", null, null, authToken);
    }

    public Collection<GameList> listGames(String authToken) throws ResponseException {
        record ListGamesResponse(Collection<GameList> games) {}
        var response = this.makeRequest("GET", "/game", null, ListGamesResponse.class, authToken);
        return response.games();
    }

    public GameData createGame(String authToken, GameData gameName) throws ResponseException {
        return this.makeRequest("POST", "/game", gameName, GameData.class, authToken);
    }

    public void joinGame(String authToken, JoinRequest joinRequest) throws ResponseException {
        this.makeRequest("PUT", "/game", joinRequest, null, authToken);
    }

    public void clear() throws ResponseException {
        this.makeRequest("DELETE", "/db", null, null, null);
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass, String authToken) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            if (authToken != null) {
                http.setRequestProperty("Authorization", authToken);
            }

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
            throw new ResponseException(status, "Request failed with status: " + status);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        if (responseClass == null) {
            return null;
        }
        try (InputStream respBody = http.getInputStream(); InputStreamReader reader = new InputStreamReader(respBody)) {
            return new Gson().fromJson(reader, responseClass);
        }
    }

    private boolean isSuccessful(int status) {
        return status >= 200 && status < 300;
    }
}
