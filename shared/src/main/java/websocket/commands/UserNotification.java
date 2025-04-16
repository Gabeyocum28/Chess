package websocket.commands;


public class UserNotification {
    public UserGameCommand.CommandType type;
    public String message;

    public UserNotification() {} // Required for Gson

    public UserNotification(UserGameCommand.CommandType type, String message) {
        this.type = type;
        this.message = message;
    }

    @Override
    public String toString() {
        return "[" + type + "] " + message;
    }
}