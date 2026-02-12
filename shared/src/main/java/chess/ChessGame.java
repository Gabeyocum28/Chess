package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor team;
    private ChessBoard board;
    private Collection<ChessMove> opponentMoves = new ArrayList<>();
    private Collection<ChessMove> allOpponentMoves = new ArrayList<>();
    private Collection<ChessMove> allMyMoves = new ArrayList<>();
    private Collection<ChessMove> possibleMoves = new ArrayList<>();
    private ChessPosition myKing;
    private ChessBoard copyBoard;
    private ChessBoard masterBoard;
    private Boolean done = false;
    private ChessMove lastMove = null;
    private final HashSet<String> movedPositions = new HashSet<>();

    public ChessGame() {
        board = new ChessBoard();
        board.resetBoard();
        team = TeamColor.WHITE;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return team;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.team = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    private String posKey(int row, int col) {
        return row + "," + col;
    }

    private boolean hasMoved(int row, int col) {
        return movedPositions.contains(posKey(row, col));
    }

    private boolean isSquareAttacked(ChessBoard testBoard, ChessPosition square, TeamColor byTeam) {
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPosition position = new ChessPosition(i, j);
                ChessPiece piece = testBoard.getPiece(position);
                if (piece != null && piece.getTeamColor() == byTeam) {
                    PieceMovesCalculator calc = new PieceMovesCalculator();
                    Collection<ChessMove> moves = calc.pieceMovesCalculator(testBoard, position);
                    for (ChessMove move : moves) {
                        if (move.getEndPosition().equals(square)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
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
        allMyMoves.clear();
        if (board.getPiece(startPosition) != null) {
            ChessPiece checkpiece = board.getPiece(startPosition);
            PieceMovesCalculator oppMoves = new PieceMovesCalculator();
            allMyMoves = oppMoves.pieceMovesCalculator(board, startPosition);

            // Add castling candidate moves for king
            if (checkpiece.getPieceType() == ChessPiece.PieceType.KING) {
                addCastlingMoves(startPosition, checkpiece);
            }

            // Add en passant candidate moves for pawn
            if (checkpiece.getPieceType() == ChessPiece.PieceType.PAWN) {
                addEnPassantMoves(startPosition, checkpiece);
            }
        }
        masterBoard = board.copy();

        Iterator<ChessMove> allMyMovesIterator = allMyMoves.iterator();
        while(allMyMovesIterator.hasNext()) {
            ChessMove move = allMyMovesIterator.next();

            copyBoard = masterBoard.copy();
            ChessPosition oldPosition = new ChessPosition(move.getStartPosition().getRow(), move.getStartPosition().getColumn());
            ChessPosition newPosition = new ChessPosition(move.getEndPosition().getRow(), move.getEndPosition().getColumn());
            ChessPiece checkpiece = copyBoard.getPiece(oldPosition);
            copyBoard.addPiece(newPosition,checkpiece);
            copyBoard.addPiece(oldPosition,null);

            // Simulate special moves on the copy board
            simulateSpecialMove(move, copyBoard, checkpiece);

            this.board = copyBoard;
            if(!isInCheck(checkpiece.getTeamColor())){
                // For castling, also verify the intermediate square is safe
                if (isCastlingMove(move, checkpiece)) {
                    if (isCastlingPathSafe(move, checkpiece, masterBoard)) {
                        possibleMoves.add(move);
                    }
                } else {
                    possibleMoves.add(move);
                }
            }
            this.board = masterBoard;
        }

        // if is in check returns true on the copy board the move in invalid
        //if false add to collection
        //return collection
        return possibleMoves;
    }

    private void addCastlingMoves(ChessPosition kingPos, ChessPiece king) {
        int row = kingPos.getRow();
        int col = kingPos.getColumn();
        TeamColor color = king.getTeamColor();

        int expectedRow = (color == TeamColor.WHITE) ? 1 : 8;
        if (row != expectedRow || col != 5) {
            return;
        }
        if (hasMoved(row, col)) {
            return;
        }
        if (isInCheck(color)) {
            return;
        }

        // Kingside castle (toward column 8)
        if (!hasMoved(row, 8)) {
            ChessPiece rookPiece = board.getPiece(new ChessPosition(row, 8));
            if (rookPiece != null && rookPiece.getPieceType() == ChessPiece.PieceType.ROOK
                    && rookPiece.getTeamColor() == color) {
                // Check no pieces between king(col 5) and rook(col 8)
                if (board.getPiece(new ChessPosition(row, 6)) == null
                        && board.getPiece(new ChessPosition(row, 7)) == null) {
                    allMyMoves.add(new ChessMove(kingPos, new ChessPosition(row, 7), null));
                }
            }
        }

        // Queenside castle (toward column 1)
        if (!hasMoved(row, 1)) {
            ChessPiece rookPiece = board.getPiece(new ChessPosition(row, 1));
            if (rookPiece != null && rookPiece.getPieceType() == ChessPiece.PieceType.ROOK
                    && rookPiece.getTeamColor() == color) {
                // Check no pieces between king(col 5) and rook(col 1)
                if (board.getPiece(new ChessPosition(row, 4)) == null
                        && board.getPiece(new ChessPosition(row, 3)) == null
                        && board.getPiece(new ChessPosition(row, 2)) == null) {
                    allMyMoves.add(new ChessMove(kingPos, new ChessPosition(row, 3), null));
                }
            }
        }
    }

    private void addEnPassantMoves(ChessPosition pawnPos, ChessPiece pawn) {
        if (lastMove == null) {
            return;
        }
        TeamColor color = pawn.getTeamColor();
        int row = pawnPos.getRow();
        int col = pawnPos.getColumn();

        // Check if last move was a double pawn move
        ChessPosition lastStart = lastMove.getStartPosition();
        ChessPosition lastEnd = lastMove.getEndPosition();
        ChessPiece lastPiece = board.getPiece(lastEnd);

        if (lastPiece == null || lastPiece.getPieceType() != ChessPiece.PieceType.PAWN) {
            return;
        }
        if (lastPiece.getTeamColor() == color) {
            return;
        }
        if (Math.abs(lastStart.getRow() - lastEnd.getRow()) != 2) {
            return;
        }

        // Check if our pawn is adjacent to the landed pawn
        if (lastEnd.getRow() != row || Math.abs(lastEnd.getColumn() - col) != 1) {
            return;
        }

        int direction = (color == TeamColor.WHITE) ? 1 : -1;
        int captureRow = row + direction;
        int captureCol = lastEnd.getColumn();

        allMyMoves.add(new ChessMove(pawnPos, new ChessPosition(captureRow, captureCol), null));
    }

    private boolean isCastlingMove(ChessMove move, ChessPiece piece) {
        if (piece.getPieceType() != ChessPiece.PieceType.KING) {
            return false;
        }
        return Math.abs(move.getStartPosition().getColumn() - move.getEndPosition().getColumn()) == 2;
    }

    private boolean isCastlingPathSafe(ChessMove move, ChessPiece king, ChessBoard originalBoard) {
        int row = move.getStartPosition().getRow();
        int startCol = move.getStartPosition().getColumn();
        int endCol = move.getEndPosition().getColumn();
        int direction = (endCol > startCol) ? 1 : -1;
        TeamColor enemyColor = (king.getTeamColor() == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;

        // Check intermediate square (the square the king passes through)
        ChessPosition intermediatePos = new ChessPosition(row, startCol + direction);
        if (isSquareAttacked(originalBoard, intermediatePos, enemyColor)) {
            return false;
        }
        return true;
    }

    private void simulateSpecialMove(ChessMove move, ChessBoard simBoard, ChessPiece piece) {
        // Castling: also move the rook
        if (isCastlingMove(move, piece)) {
            int row = move.getStartPosition().getRow();
            int endCol = move.getEndPosition().getColumn();
            if (endCol == 7) {
                // Kingside
                ChessPiece rook = simBoard.getPiece(new ChessPosition(row, 8));
                simBoard.addPiece(new ChessPosition(row, 6), rook);
                simBoard.addPiece(new ChessPosition(row, 8), null);
            } else if (endCol == 3) {
                // Queenside
                ChessPiece rook = simBoard.getPiece(new ChessPosition(row, 1));
                simBoard.addPiece(new ChessPosition(row, 4), rook);
                simBoard.addPiece(new ChessPosition(row, 1), null);
            }
        }

        // En passant: remove captured pawn
        if (piece.getPieceType() == ChessPiece.PieceType.PAWN && isEnPassantMove(move)) {
            int capturedRow = move.getStartPosition().getRow();
            int capturedCol = move.getEndPosition().getColumn();
            simBoard.addPiece(new ChessPosition(capturedRow, capturedCol), null);
        }
    }

    private boolean isEnPassantMove(ChessMove move) {
        if (lastMove == null) {
            return false;
        }
        int startCol = move.getStartPosition().getColumn();
        int endCol = move.getEndPosition().getColumn();
        // Pawn moving diagonally
        if (Math.abs(startCol - endCol) != 1) {
            return false;
        }
        // The destination should be the square the opponent's pawn passed through
        ChessPosition lastEnd = lastMove.getEndPosition();
        if (lastEnd.getRow() != move.getStartPosition().getRow()) {
            return false;
        }
        if (lastEnd.getColumn() != endCol) {
            return false;
        }
        return true;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {

        //valid moves
        if(validMoves(move.getStartPosition()).isEmpty()) {
            throw new InvalidMoveException();
        }else {
            int i = 0;
            Iterator<ChessMove> possibleMovesIterator = possibleMoves.iterator();
            while(possibleMovesIterator.hasNext()) {
                ChessMove possibleMove = possibleMovesIterator.next();
                if(move.equals(possibleMove)){
                    if(board.getPiece(move.getStartPosition()).getTeamColor() != team){
                        throw new InvalidMoveException();
                    }
                    i++;
                    ChessPiece movingPiece = board.getPiece(move.getStartPosition());

                    if(move.getPromotionPiece() == null) {
                        board.addPiece(new ChessPosition(move.getEndPosition().getRow(), move.getEndPosition().getColumn()),
                                new ChessPiece(movingPiece.getTeamColor(), movingPiece.getPieceType()));
                    }else {
                        board.addPiece(new ChessPosition(move.getEndPosition().getRow(), move.getEndPosition().getColumn()),
                                new ChessPiece(movingPiece.getTeamColor(), move.getPromotionPiece()));
                    }
                    board.addPiece(new ChessPosition(move.getStartPosition().getRow(), move.getStartPosition().getColumn()), null);

                    // Handle castling: move the rook
                    if (isCastlingMove(move, movingPiece)) {
                        int row = move.getStartPosition().getRow();
                        int endCol = move.getEndPosition().getColumn();
                        if (endCol == 7) {
                            // Kingside
                            ChessPiece rook = board.getPiece(new ChessPosition(row, 8));
                            board.addPiece(new ChessPosition(row, 6), rook);
                            board.addPiece(new ChessPosition(row, 8), null);
                        } else if (endCol == 3) {
                            // Queenside
                            ChessPiece rook = board.getPiece(new ChessPosition(row, 1));
                            board.addPiece(new ChessPosition(row, 4), rook);
                            board.addPiece(new ChessPosition(row, 1), null);
                        }
                    }

                    // Handle en passant: remove captured pawn
                    if (movingPiece.getPieceType() == ChessPiece.PieceType.PAWN && isEnPassantMove(move)) {
                        int capturedRow = move.getStartPosition().getRow();
                        int capturedCol = move.getEndPosition().getColumn();
                        board.addPiece(new ChessPosition(capturedRow, capturedCol), null);
                    }

                    // Track moved positions and last move
                    movedPositions.add(posKey(move.getStartPosition().getRow(), move.getStartPosition().getColumn()));
                    lastMove = move;

                    if(team == TeamColor.WHITE){
                        setTeamTurn(TeamColor.BLACK);
                    }else {
                        setTeamTurn(TeamColor.WHITE);
                    }

                }
            }
            if(i == 0){
                throw new InvalidMoveException();
            }
        }

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
                if (board.getPiece(position) != null) {
                    ChessPiece checkpiece = board.getPiece(position);
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
                if (board.getPiece(position) != null) {
                    ChessPiece checkpiece = board.getPiece(position);
                    if ((checkpiece.getTeamColor() != teamColor)) {
                        PieceMovesCalculator oppMoves = new PieceMovesCalculator();
                        opponentMoves = oppMoves.pieceMovesCalculator(board,position);
                        allOpponentMoves.addAll(opponentMoves);
                    }
                }
            }
        }
        //iterate through and find if any endposition is on my king
        Iterator<ChessMove> allOpponentMovesIterator = allOpponentMoves.iterator();
        int i = 0;
        while(allOpponentMovesIterator.hasNext()) {
            ChessMove move = allOpponentMovesIterator.next();
            if(myKing.getRow() == move.getEndPosition().getRow() && myKing.getColumn() == move.getEndPosition().getColumn()){
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
        if(isInCheck(teamColor)) {
            Collection<ChessMove> validMoves = validMovesCheck(teamColor);
            if (validMoves.isEmpty()) {
                done();
                return true;
            }
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
        if(!isInCheck(teamColor)){
            Collection<ChessMove> validMoves = validMovesCheck(teamColor);
            if (validMoves.isEmpty()) {
                done();
                return true;
            }
        }
        return false;
    }

    public Collection<ChessMove> validMovesCheck(TeamColor teamColor) {
        Collection<ChessMove> validMoves = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPosition position = new ChessPosition(i, j);
                if (board.getPiece(position) != null) {
                    ChessPiece checkpiece = board.getPiece(position);
                    if ((checkpiece.getTeamColor() == teamColor)) {
                        Collection<ChessMove> moves;
                        moves = validMoves(position);
                        validMoves.addAll(moves);
                    }
                }
            }
        }

        return validMoves;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
        this.lastMove = null;
        this.movedPositions.clear();
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    public void done(){
        done = true;
    }

    public boolean getStatus(){
        return done;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return team == chessGame.team && Objects.equals(board, chessGame.board) && Objects.equals(opponentMoves,
                chessGame.opponentMoves) && Objects.equals(allOpponentMoves, chessGame.allOpponentMoves) &&
                Objects.equals(allMyMoves, chessGame.allMyMoves) && Objects.equals(possibleMoves, chessGame.possibleMoves)
                && Objects.equals(myKing, chessGame.myKing) && Objects.equals(copyBoard, chessGame.copyBoard) &&
                Objects.equals(masterBoard, chessGame.masterBoard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, board, opponentMoves, allOpponentMoves, allMyMoves, possibleMoves, myKing, copyBoard, masterBoard);
    }
}
