import chess.*;
import com.sun.nio.sctp.HandlerResult;
import com.sun.nio.sctp.Notification;
import ui.Repl;

public class ClientMain {
    public static void main(String[] args) {
        var serverUrl = "http://localhost:8080";
        if (args.length == 1) {
            serverUrl = args[0];
        }

        new Repl(serverUrl) {
            @Override
            public HandlerResult handleNotification(Notification notification, Object attachment) {
                return null;
            }
        }.runPre();

        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
    }
}