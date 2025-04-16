package websocket;

import websocket.messages.ServerNotification;

public interface NotificationHandler {
    void notifyUserGame(websocket.commands.UserNotification notification);
    void notifyLoadGame(websocket.messages.ServerNotification notification);
    void notifyError(websocket.messages.ServerNotification notification);
    void notifyNotification(websocket.messages.ServerNotification notification);
}
