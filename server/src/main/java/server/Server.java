package server;

import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import server.websocket.WebSocketHandler;
import spark.Spark;
@WebSocket
public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");
        Spark.webSocket("/ws", WebSocketHandler.class);

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", RegistrationHandler::register);
        Spark.post("/session", LoginHandler::login);
        Spark.delete("/session", LogoutHandler::logout);
        Spark.get("/game", ListGamesHandler::listGames);
        Spark.post("/game", CreateGameHandler::createGame);
        Spark.put("/game", JoinGameHandler::joinGame);
        Spark.delete("/db", ClearHandler::clear);
        //This line initializes the server and can be removed once you have a functioning endpoint 
        //Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

}
