package websocket.messages;

import chess.ChessGame;

public class LoadGameMessage extends ServerMessage{

    private ChessGame game;

    public LoadGameMessage(ServerMessageType loadGame, ChessGame game) {
        super(ServerMessageType.LOAD_GAME);
        this.game = game;
    }

    public ChessGame getGame(){
        return game;
    }
}
