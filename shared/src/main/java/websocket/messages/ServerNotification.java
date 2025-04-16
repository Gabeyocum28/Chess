package websocket.messages;


public class ServerNotification {
    public ServerMessage.ServerMessageType type;
    public String message;

    public ServerNotification() {} // Required for Gson

    public ServerNotification(ServerMessage.ServerMessageType type, String message) {
        this.type = type;
        this.message = message;
    }

    @Override
    public String toString() {
        return "[" + type + "] " + message;
    }
}
