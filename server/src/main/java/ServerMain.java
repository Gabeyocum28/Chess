import chess.*;
import server.Server;

public class ServerMain {
    public static void main(String[] args) {
        var portNum = 0;
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Server: " + piece);
        int server = new Server().run(8080);
    }
}