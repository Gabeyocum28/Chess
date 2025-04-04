package ui;

import com.sun.nio.sctp.NotificationHandler;
import exceptions.ResponseException;
import model.AuthData;
import model.GameData;
import model.GameList;
import model.JoinRequest;
import server.ServerFacade;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.*;

public class PostLoginClient {
    private final ServerFacade server;
    private final String serverUrl;
    private final NotificationHandler notificationHandler;
    public AuthData user;
    public JoinRequest joinRequest;
    private Map<Integer, GameList> gameMap;

    public PostLoginClient(String serverUrl, NotificationHandler notificationHandler) throws MalformedURLException, URISyntaxException {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        this.notificationHandler = notificationHandler;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "create" -> create(params);
                case "list" -> list();
                case "join" -> join(params);
                case "observe" -> observe(params);
                case "logout" -> logout();
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String create(String... params) throws ResponseException {
        if (params.length >= 1) {
            GameData game = new GameData(0, null, null, params[0], null);
            server.createGame(user.authToken(), game);
            return String.format("game created %s", params[0]);
        }
        throw new ResponseException(400, "Expected: <NAME>");
    }

    public String list() throws ResponseException {
        gameListCreation();

        for (Map.Entry<Integer, GameList> entry : gameMap.entrySet()) {
            System.out.println("ID:" + entry.getKey() + "     Name:" + entry.getValue().gameName() +
                    "    White Username:" + entry.getValue().whiteUsername() +
                    "    Black Username:" + entry.getValue().blackUsername());
        }

        return "";
    }

    public String join(String... params) throws ResponseException {
        if (params.length >= 2) {

            if(gameMap == null){
                gameListCreation();
            }
            try {
                int id = Integer.parseInt(params[0]);
                joinRequest = new JoinRequest(params[1], gameMap.get(id).gameID());
            }catch (Exception e){
                return "Game does not exist\n";
            }
            try {
                server.joinGame(user.authToken(), joinRequest);
            }catch (Exception e){
                return "Already taken\n";
            }

            return String.format("joined game as %s", params[1]);
        }
        throw new ResponseException(400, "Expected: <ID> [WHITE/BLACK]");
    }

    public String observe(String... params) throws ResponseException {
        if (params.length >= 1) {
            gameListCreation();
            try {
                int id = Integer.parseInt(params[0]);
                gameMap.get(id);
                joinRequest = new JoinRequest("white", gameMap.get(id).gameID());

            }catch (Exception e){
                return "Game does not exist\n";
            }

            return String.format("observing game %s", params[0]);
        }
        throw new ResponseException(400, "Expected: <ID>");
    }

    public String logout() throws ResponseException {
        try {
            server.logout(user.authToken());
        }catch(Exception e){
            return "Not Authorized";
        }
        return "quit";
    }

    public String help() {

        return """
                - "create" <NAME> - a game
                - "list" - games
                - "join" <ID> [WHITE/BLACK] - a game
                - "observe" <ID> - a game
                - "logout" - when you are done
                - "quit" - playing chess
                _ "help" - display possible actions
                """;


    }

    public void setUserData(AuthData authUser){
        user = authUser;
    }

    public void gameListCreation(){
        Collection<GameList> games = List.of();
        try {
            games = server.listGames(user.authToken());
        }catch(Exception e){
            System.out.println("Not Authorized");
        }

        gameMap = new HashMap<>();
        int index = 1;

        for (GameList game : games) {
            gameMap.put(index++, game);
        }
    }
}
