package websocket;

public interface NotificationHandler {
    void notifyLoadGame(websocket.messages.LoadGameMessage notification);
    void notifyError(websocket.messages.ErrorMessage notification);
    void notifyNotification(websocket.messages.NotificationMessage notification);
}
