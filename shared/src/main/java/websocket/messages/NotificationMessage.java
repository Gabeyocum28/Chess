package websocket.messages;

import chess.ChessGame;

public class NotificationMessage extends ServerMessage{

    String message;

    public NotificationMessage(ServerMessage.ServerMessageType type, String message) {
        super(ServerMessageType.NOTIFICATION);
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

}
