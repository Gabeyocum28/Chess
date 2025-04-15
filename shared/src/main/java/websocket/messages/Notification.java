package websocket.messages;

public class Notification {
    public String type;
    public String message;

    public Notification() {} // Required for Gson

    public Notification(String type, String message) {
        this.type = type;
        this.message = message;
    }

    @Override
    public String toString() {
        return "[" + type + "] " + message;
    }
}
