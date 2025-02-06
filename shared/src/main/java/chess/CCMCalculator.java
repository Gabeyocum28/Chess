package chess;

import java.util.ArrayList;
import java.util.Collection;

public class CCMCalculator {
    public CCMCalculator() {
    }

    public boolean isInCheck(ChessGame.TeamColor teamColor, ChessBoard Board) {
        ChessBoard board = Board;
        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                ChessPosition position = new ChessPosition(i,j);
                if(board.getPiece(position) != null) {
                    ChessPiece checkpiece = board.getPiece(position);
                    if((checkpiece.getPieceType() == ChessPiece.PieceType.KING) && (checkpiece.getTeamColor() == teamColor)){
                        ChessPiece king = board.getPiece(position);
                        Collection<ChessPosition> check = new ArrayList<>();
                        int row = position.getRow();
                        int column = position.getColumn();
                        int start = 0;
                        int m = 0;
                        int f = 0;

                        while(true){
                            if(start == 0){
                                row = position.getRow();
                                column = position.getColumn();
                                start = 1;
                            }

                            if(m == 0){
                                row++;
                                column++;

                                if (row <= 0 || row > 8 || column <= 0 || column > 8) {
                                    start = 0;
                                    m++;
                                    continue;
                                }
                                ChessPosition checkPosition = new ChessPosition(row, column);
                                if (board.getPiece(checkPosition) != null) {
                                    if (king.getTeamColor() != board.getPiece(checkPosition).getTeamColor() && (board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.QUEEN || board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.BISHOP)) {
                                        ChessPosition attacker = new ChessPosition(row, column);
                                        check.add(attacker);
                                        start = 0;
                                        m++;
                                        continue;

                                    } else {
                                        start = 0;
                                        m++;
                                        continue;
                                    }
                                }
                            }
                            else if(m == 1){
                                row++;
                                column--;

                                if (row <= 0 || row > 8 || column <= 0 || column > 8) {
                                    start = 0;
                                    m++;
                                    continue;
                                }
                                ChessPosition checkPosition = new ChessPosition(row, column);
                                if (board.getPiece(checkPosition) != null) {
                                    if (king.getTeamColor() != board.getPiece(checkPosition).getTeamColor() && (board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.QUEEN || board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.BISHOP)) {
                                        ChessPosition move = new ChessPosition(row, column);
                                        check.add(move);
                                        start = 0;
                                        m++;
                                        continue;

                                    } else {
                                        start = 0;
                                        m++;
                                        continue;
                                    }
                                }
                            }
                            else if(m == 2){
                                row--;
                                column++;

                                if (row <= 0 || row > 8 || column <= 0 || column > 8) {
                                    start = 0;
                                    m++;
                                    continue;
                                }
                                ChessPosition checkPosition = new ChessPosition(row, column);
                                if (board.getPiece(checkPosition) != null) {
                                    if (king.getTeamColor() != board.getPiece(checkPosition).getTeamColor() && (board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.QUEEN || board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.BISHOP)) {
                                        ChessPosition move = new ChessPosition(row, column);
                                        check.add(move);
                                        start = 0;
                                        m++;
                                        continue;

                                    } else {
                                        start = 0;
                                        m++;
                                        continue;
                                    }
                                }
                            }
                            else if (m == 3){
                                row--;
                                column--;

                                if (row <= 0 || row > 8 || column <= 0 || column > 8) {
                                    start = 0;
                                    m++;
                                    continue;
                                }
                                ChessPosition checkPosition = new ChessPosition(row, column);
                                if (board.getPiece(checkPosition) != null) {
                                    if (king.getTeamColor() != board.getPiece(checkPosition).getTeamColor() && (board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.QUEEN || board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.BISHOP)) {
                                        ChessPosition move = new ChessPosition(row, column);
                                        check.add(move);
                                        start = 0;
                                        m++;
                                        continue;

                                    } else {
                                        start = 0;
                                        m++;
                                        continue;
                                    }
                                }
                            }
                            else if(m == 4){
                                row++;

                                if (row <= 0 || row > 8 || column <= 0 || column > 8) {
                                    start = 0;
                                    m++;
                                    continue;
                                }
                                ChessPosition checkPosition = new ChessPosition(row, column);
                                if (board.getPiece(checkPosition) != null) {
                                    if (king.getTeamColor() != board.getPiece(checkPosition).getTeamColor() && (board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.QUEEN || board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.ROOK)) {
                                        ChessPosition move = new ChessPosition(row, column);
                                        check.add(move);
                                        start = 0;
                                        m++;
                                        continue;

                                    } else {
                                        start = 0;
                                        m++;
                                        continue;
                                    }
                                }
                            }
                            else if(m == 5){
                                row--;

                                if (row <= 0 || row > 8 || column <= 0 || column > 8) {
                                    start = 0;
                                    m++;
                                    continue;
                                }
                                ChessPosition checkPosition = new ChessPosition(row, column);
                                if (board.getPiece(checkPosition) != null) {
                                    if (king.getTeamColor() != board.getPiece(checkPosition).getTeamColor() && (board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.QUEEN || board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.ROOK)) {
                                        ChessPosition move = new ChessPosition(row, column);
                                        check.add(move);
                                        start = 0;
                                        m++;
                                        continue;

                                    } else {
                                        start = 0;
                                        m++;
                                        continue;
                                    }
                                }
                            }
                            else if(m == 6){
                                column++;

                                if (row <= 0 || row > 8 || column <= 0 || column > 8) {
                                    start = 0;
                                    m++;
                                    continue;
                                }
                                ChessPosition checkPosition = new ChessPosition(row, column);
                                if (board.getPiece(checkPosition) != null) {
                                    if (king.getTeamColor() != board.getPiece(checkPosition).getTeamColor() && (board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.QUEEN || board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.ROOK)) {
                                        ChessPosition move = new ChessPosition(row, column);
                                        check.add(move);
                                        start = 0;
                                        m++;
                                        continue;

                                    } else {
                                        start = 0;
                                        m++;
                                        continue;
                                    }
                                }
                            }
                            else if(m == 7){
                                column--;

                                if (row <= 0 || row > 8 || column <= 0 || column > 8) {
                                    start = 0;
                                    m++;
                                    continue;
                                }
                                ChessPosition checkPosition = new ChessPosition(row, column);
                                if (board.getPiece(checkPosition) != null) {
                                    if (king.getTeamColor() != board.getPiece(checkPosition).getTeamColor() && (board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.QUEEN || board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.ROOK)) {
                                        ChessPosition move = new ChessPosition(row, column);
                                        check.add(move);
                                        start = 0;
                                        m++;
                                        continue;

                                    } else {
                                        start = 0;
                                        m++;
                                        continue;
                                    }
                                }
                            }
                            else if(m == 8){
                                row++;
                                row++;
                                column++;

                                if (row <= 0 || row > 8 || column <= 0 || column > 8) {
                                    start = 0;
                                    m++;
                                    continue;
                                }
                                ChessPosition checkPosition = new ChessPosition(row, column);
                                if (board.getPiece(checkPosition) != null) {
                                    if (king.getTeamColor() != board.getPiece(checkPosition).getTeamColor() && board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.KNIGHT) {
                                        ChessPosition move = new ChessPosition(row, column);
                                        check.add(move);
                                        start = 0;
                                        m++;
                                        continue;

                                    } else {
                                        start = 0;
                                        m++;
                                        continue;
                                    }
                                }
                                start = 0;
                                m++;
                                continue;
                            }
                            else if(m == 9){
                                row++;
                                row++;
                                column--;

                                if (row <= 0 || row > 8 || column <= 0 || column > 8) {
                                    start = 0;
                                    m++;
                                    continue;
                                }
                                ChessPosition checkPosition = new ChessPosition(row, column);
                                if (board.getPiece(checkPosition) != null) {
                                    if (king.getTeamColor() != board.getPiece(checkPosition).getTeamColor() && board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.KNIGHT) {
                                        ChessPosition move = new ChessPosition(row, column);
                                        check.add(move);
                                        start = 0;
                                        m++;
                                        continue;

                                    } else {
                                        start = 0;
                                        m++;
                                        continue;
                                    }
                                }
                                start = 0;
                                m++;
                                continue;
                            }
                            else if(m == 10){
                                row--;
                                row--;
                                column++;

                                if (row <= 0 || row > 8 || column <= 0 || column > 8) {
                                    start = 0;
                                    m++;
                                    continue;
                                }
                                ChessPosition checkPosition = new ChessPosition(row, column);
                                if (board.getPiece(checkPosition) != null) {
                                    if (king.getTeamColor() != board.getPiece(checkPosition).getTeamColor() && board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.KNIGHT) {
                                        ChessPosition move = new ChessPosition(row, column);
                                        check.add(move);
                                        start = 0;
                                        m++;
                                        continue;

                                    } else {
                                        start = 0;
                                        m++;
                                        continue;
                                    }
                                }
                                start = 0;
                                m++;
                                continue;
                            }
                            else if(m == 11){
                                row--;
                                row--;
                                column--;

                                if (row <= 0 || row > 8 || column <= 0 || column > 8) {
                                    start = 0;
                                    m++;
                                    continue;
                                }
                                ChessPosition checkPosition = new ChessPosition(row, column);
                                if (board.getPiece(checkPosition) != null) {
                                    if (king.getTeamColor() != board.getPiece(checkPosition).getTeamColor() && board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.KNIGHT) {
                                        ChessPosition move = new ChessPosition(row, column);
                                        check.add(move);
                                        start = 0;
                                        m++;
                                        continue;

                                    } else {
                                        start = 0;
                                        m++;
                                        continue;
                                    }
                                }
                                start = 0;
                                m++;
                                continue;
                            }
                            else if(m == 12){
                                row++;
                                column++;
                                column++;

                                if (row <= 0 || row > 8 || column <= 0 || column > 8) {
                                    start = 0;
                                    m++;
                                    continue;
                                }
                                ChessPosition checkPosition = new ChessPosition(row, column);
                                if (board.getPiece(checkPosition) != null) {
                                    if (king.getTeamColor() != board.getPiece(checkPosition).getTeamColor() && board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.KNIGHT) {
                                        ChessPosition move = new ChessPosition(row, column);
                                        check.add(move);
                                        start = 0;
                                        m++;
                                        continue;

                                    } else {
                                        start = 0;
                                        m++;
                                        continue;
                                    }
                                }
                                start = 0;
                                m++;
                                continue;
                            }
                            else if(m == 13){
                                row--;
                                column++;
                                column++;

                                if (row <= 0 || row > 8 || column <= 0 || column > 8) {
                                    start = 0;
                                    m++;
                                    continue;
                                }
                                ChessPosition checkPosition = new ChessPosition(row, column);
                                if (board.getPiece(checkPosition) != null) {
                                    if (king.getTeamColor() != board.getPiece(checkPosition).getTeamColor() && board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.KNIGHT) {
                                        ChessPosition move = new ChessPosition(row, column);
                                        check.add(move);
                                        start = 0;
                                        m++;
                                        continue;

                                    } else {
                                        start = 0;
                                        m++;
                                        continue;
                                    }
                                }
                                start = 0;
                                m++;
                                continue;
                            }else if(m == 14){
                                row++;
                                column--;
                                column--;

                                if (row <= 0 || row > 8 || column <= 0 || column > 8) {
                                    start = 0;
                                    m++;
                                    continue;
                                }
                                ChessPosition checkPosition = new ChessPosition(row, column);
                                if (board.getPiece(checkPosition) != null) {
                                    if (king.getTeamColor() != board.getPiece(checkPosition).getTeamColor() && board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.KNIGHT) {
                                        ChessPosition move = new ChessPosition(row, column);
                                        check.add(move);
                                        start = 0;
                                        m++;
                                        continue;

                                    } else {
                                        start = 0;
                                        m++;
                                        continue;
                                    }
                                }
                                start = 0;
                                m++;
                                continue;
                            }
                            else if(m == 17){
                                row--;
                                column--;
                                column--;

                                if (row <= 0 || row > 8 || column <= 0 || column > 8) {
                                    start = 0;
                                    m++;
                                    continue;
                                }
                                ChessPosition checkPosition = new ChessPosition(row, column);
                                if (board.getPiece(checkPosition) != null) {
                                    if (king.getTeamColor() != board.getPiece(checkPosition).getTeamColor() && board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.KNIGHT) {
                                        ChessPosition move = new ChessPosition(row, column);
                                        check.add(move);
                                        start = 0;
                                        m++;
                                        continue;

                                    } else {
                                        start = 0;
                                        m++;
                                        continue;
                                    }
                                }
                                start = 0;
                                m++;
                                continue;
                            }
                            else if(m == 18 && king.getTeamColor() == ChessGame.TeamColor.BLACK){
                                row--;
                                column--;

                                if (row <= 0 || row > 8 || column <= 0 || column > 8) {
                                    start = 0;
                                    m++;
                                    continue;
                                }
                                ChessPosition checkPosition = new ChessPosition(row, column);
                                if (board.getPiece(checkPosition) != null) {
                                    if (king.getTeamColor() != board.getPiece(checkPosition).getTeamColor() && board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.PAWN) {
                                        ChessPosition move = new ChessPosition(row, column);
                                        check.add(move);
                                        start = 0;
                                        m++;
                                        continue;

                                    } else {
                                        start = 0;
                                        m++;
                                        continue;
                                    }
                                }
                                start = 0;
                                m++;
                                continue;
                            }
                            else if(m == 19 && king.getTeamColor() == ChessGame.TeamColor.BLACK){
                                row--;
                                column++;

                                if (row <= 0 || row > 8 || column <= 0 || column > 8) {
                                    start = 0;
                                    m++;
                                    continue;
                                }
                                ChessPosition checkPosition = new ChessPosition(row, column);
                                if (board.getPiece(checkPosition) != null) {
                                    if (king.getTeamColor() != board.getPiece(checkPosition).getTeamColor() && board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.PAWN) {
                                        ChessPosition move = new ChessPosition(row, column);
                                        check.add(move);
                                        start = 0;
                                        m++;
                                        continue;

                                    } else {
                                        start = 0;
                                        m++;
                                        continue;
                                    }
                                }
                                start = 0;
                                m++;
                                continue;
                            }
                            else if(m == 18 && king.getTeamColor() == ChessGame.TeamColor.WHITE){
                                row++;
                                column--;

                                if (row <= 0 || row > 8 || column <= 0 || column > 8) {
                                    start = 0;
                                    m++;
                                    continue;
                                }
                                ChessPosition checkPosition = new ChessPosition(row, column);
                                if (board.getPiece(checkPosition) != null) {
                                    if (king.getTeamColor() != board.getPiece(checkPosition).getTeamColor() && board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.PAWN) {
                                        ChessPosition move = new ChessPosition(row, column);
                                        check.add(move);
                                        start = 0;
                                        m++;
                                        continue;

                                    } else {
                                        start = 0;
                                        m++;
                                        continue;
                                    }
                                }
                                start = 0;
                                m++;
                                continue;
                            }
                            else if(m == 18 && king.getTeamColor() == ChessGame.TeamColor.WHITE){
                                row++;
                                column++;

                                if (row <= 0 || row > 8 || column <= 0 || column > 8) {
                                    start = 0;
                                    m++;
                                    continue;
                                }
                                ChessPosition checkPosition = new ChessPosition(row, column);
                                if (board.getPiece(checkPosition) != null) {
                                    if (king.getTeamColor() != board.getPiece(checkPosition).getTeamColor() && board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.PAWN) {
                                        ChessPosition move = new ChessPosition(row, column);
                                        check.add(move);
                                        start = 0;
                                        m++;
                                        continue;

                                    } else {
                                        start = 0;
                                        m++;
                                        continue;
                                    }
                                }
                                start = 0;
                                m++;
                                continue;
                            }
                            else{
                                if(check.isEmpty() == true){
                                    f = 1;
                                    break;
                                }
                                else{
                                    f = 2;
                                    break;
                                }

                            }
                        }
                        if(f == 1){
                            return false;
                        }
                        else if(f == 2){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isInCheckmate(ChessGame.TeamColor teamColor, ChessBoard Board) {
        return false;
    }

    public boolean isInStalemate(ChessGame.TeamColor teamColor, ChessBoard Board) {
        return false;
    }
}
