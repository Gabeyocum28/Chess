package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor Team;
    private ChessBoard Board;
    private Collection<ChessMove> opponentMoves = new ArrayList<>();
    private Collection<ChessMove> allOpponentMoves = new ArrayList<>();
    private Collection<ChessMove> allMyMoves = new ArrayList<>();
    private Collection<ChessMove> possibleMoves = new ArrayList<>();
    private ChessPosition myKing;
    private ChessBoard copyBoard;
    private ChessBoard masterBoard;

    public ChessGame() {
        Board = new ChessBoard();
        Board.resetBoard();
        Team = TeamColor.WHITE;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return Team;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.Team = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        // make copy of board to save curr state of game to simulate move
        possibleMoves.clear();
        if (Board.getPiece(startPosition) != null) {
            ChessPiece checkpiece = Board.getPiece(startPosition);
            PieceMovesCalculator oppMoves = new PieceMovesCalculator();
            allMyMoves = oppMoves.PieceMovesCalculator(Board, startPosition);
        }
        masterBoard = Board.copy();

        Iterator<ChessMove> allMyMovesIterator = allMyMoves.iterator();
        while(allMyMovesIterator.hasNext()) {
            ChessMove Move = allMyMovesIterator.next();

            copyBoard = masterBoard.copy();
            ChessPosition Old = new ChessPosition(Move.getStartPosition().getRow(),Move.getStartPosition().getColumn());
            ChessPosition New = new ChessPosition(Move.getEndPosition().getRow(), Move.getEndPosition().getColumn());
            ChessPiece checkpiece = copyBoard.getPiece(Old);
            copyBoard.addPiece(New,checkpiece);
            copyBoard.addPiece(Old,null);
            setBoard(copyBoard);
            if(!isInCheck(checkpiece.getTeamColor())){
                possibleMoves.add(Move);
            }
            setBoard(masterBoard);
        }

        // if is in check returns true on the copy board the move in invalid
        //if false add to collection
        //return collection
        return possibleMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        //valid moves
        throw new InvalidMoveException();
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        // Find King
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPosition position = new ChessPosition(i, j);
                if (Board.getPiece(position) != null) {
                    ChessPiece checkpiece = Board.getPiece(position);
                    if ((checkpiece.getTeamColor() == teamColor) && checkpiece.getPieceType() == ChessPiece.PieceType.KING) {
                        myKing = new ChessPosition(i,j);
                        i = 8;
                        break;
                    }
                }
            }
        }
        //call pievemoves function on enemys
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPosition position = new ChessPosition(i, j);
                if (Board.getPiece(position) != null) {
                    ChessPiece checkpiece = Board.getPiece(position);
                    if ((checkpiece.getTeamColor() != teamColor)) {
                        PieceMovesCalculator oppMoves = new PieceMovesCalculator();
                        opponentMoves = oppMoves.PieceMovesCalculator(Board,position);
                        allOpponentMoves.addAll(opponentMoves);
                    }
                }
            }
        }
        //iterate through and find if any endposition is on my king
        Iterator<ChessMove> allOpponentMovesIterator = allOpponentMoves.iterator();
        int i = 0;
        while(allOpponentMovesIterator.hasNext()) {
            ChessMove Move = allOpponentMovesIterator.next();
            if(myKing.getRow() == Move.getEndPosition().getRow() && myKing.getColumn() == Move.getEndPosition().getColumn()){
                i++;
            }
        }
        allOpponentMoves.clear();
        if(i > 0){
            i = 0;
            return true;
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        //possibleMoves = validMoves();
        if(possibleMoves.isEmpty()) {
         return true;
        }
        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        ChessBoard board = getBoard();
        CCMCalculator checkCalculator = new CCMCalculator();
        return checkCalculator.isInStalemate(teamColor, board);
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.Board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return Board;
    }

    /*
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return Team == chessGame.Team && Objects.equals(Board, chessGame.Board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Team, Board);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "Team=" + Team +
                ", Board=" + Board +
                '}';
    }

     */
}
