package ui;

import chess.ChessGame;
import exceptions.ResponseException;
import model.AuthData;
import model.GameData;
import model.JoinRequest;
import server.ServerFacade;
import websocket.NotificationHandler;
import websocket.WebSocketFacade;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.*;

import static ui.EscapeSequences.SET_TEXT_COLOR_BLUE;

public class PostLoginClient {
    private final ServerFacade server;
    private final String serverUrl;
    private final NotificationHandler notificationHandler;
    private WebSocketFacade ws;
    public AuthData user;
    public JoinRequest joinRequest;
    private Map<Integer, GameData> gameMap;
    public ChessGame game;

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
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String create(String... params) throws ResponseException {
        if (params.length == 1) {
            GameData game = new GameData(0, null, null, params[0], null);
            server.createGame(user.authToken(), game);
            return String.format("game created %s", params[0]);
        }
        throw new ResponseException(400, "Expected 1 input\nEx:create <NAME(no spaces)>");
    }

    public String list() throws ResponseException {
        gameListCreation();

        for (Map.Entry<Integer, GameData> entry : gameMap.entrySet()) {
            System.out.println("ID:" + entry.getKey() + "     Name:" + entry.getValue().gameName() +
                    "    White Username:" + entry.getValue().whiteUsername() +
                    "    Black Username:" + entry.getValue().blackUsername());
        }

        return "";
    }

    public String join(String... params) throws ResponseException {
        if (params.length == 2) {

            if(gameMap == null){
                gameListCreation();
            }
            int id;
            try {
                id = Integer.parseInt(params[0]);
            } catch (NumberFormatException e) {
                return "Game id must be a number";
            }

            try {
                joinRequest = new JoinRequest(params[1], gameMap.get(id).gameID());
                if(!Objects.equals(joinRequest.playerColor(), "white") && !Objects.equals(joinRequest.playerColor(), "black")){
                    return "The only possible colors are white or black\n";
                }
                new ChessGame().setBoard(gameMap.get(id).game().getBoard());
            }catch (Exception e){
                return "Game does not exist\n";
            }
            try {
                server.joinGame(user.authToken(), joinRequest);
            }catch (Exception e){
                return "Already taken\n";
            }

            ws = new WebSocketFacade(serverUrl, notificationHandler);
            ws.enterGame(user, joinRequest);

            return String.format("joined game as %s", params[1]);
        }
        throw new ResponseException(400, "Expected 2 inputs\nEx:join <ID> [white/black]");
    }

    public String observe(String... params) throws ResponseException {
        if (params.length == 1) {
            gameListCreation();
            try {
                int id = Integer.parseInt(params[0]);
                gameMap.get(id);
                joinRequest = new JoinRequest("white", gameMap.get(id).gameID());
                new ChessGame().setBoard(gameMap.get(id).game().getBoard());
            }catch (Exception e){
                return "Game does not exist\n";
            }

            ws = new WebSocketFacade(serverUrl, notificationHandler);
            ws.enterGame(user, joinRequest);

            return String.format("observing game %s", params[0]);
        }
        throw new ResponseException(400, "Expected 1 input\nEx:observe <ID>");
    }

    public String logout() throws ResponseException {
        try {
            server.logout(user.authToken());
        }catch(Exception e){
            return "Not Authorized";
        }
        return "You have logged out";
    }

    public String help() {

        return SET_TEXT_COLOR_BLUE +
                """
                - "create" <NAME> - creates a game
                - "list" - lists all games
                - "join" <ID> [white/black] - joins a game
                - "observe" <ID> - observes a game
                - "logout" - logs out of the application
                _ "help" - display possible actions
                """;


    }

    public void setUserData(AuthData authUser){
        user = authUser;
    }

    public void gameListCreation(){
        Collection<GameData> games = List.of();
        try {
            games = server.listGames(user.authToken());
        }catch(Exception e){
            System.out.println("Not Authorized");
        }

        gameMap = new HashMap<>();
        int index = 1;

        for (GameData game : games) {
            gameMap.put(index++, game);
        }
    }
}
