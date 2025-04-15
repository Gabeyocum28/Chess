package websocket.messages;

import websocket.commands.UserGameCommand;

public class Notification {
    public UserGameCommand.CommandType type;
    public String message;

    public Notification() {} // Required for Gson

    public Notification(UserGameCommand.CommandType type, String message) {
        this.type = type;
        this.message = message;
    }

    @Override
    public String toString() {
        return "[" + type + "] " + message;
    }
}
