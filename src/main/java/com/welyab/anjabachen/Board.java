/*
 * Copyright (C) 2019 Welyab da Silva Paula
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.welyab.anjabachen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Collectors;

import com.welyab.anjabachen.fen.BoardConfig;
import com.welyab.anjabachen.fen.FenParser;
import com.welyab.anjabachen.movement.BoardMovements;
import com.welyab.anjabachen.movement.Movement;
import com.welyab.anjabachen.movement.MovementException;
import com.welyab.anjabachen.movement.MovementMetadata;
import com.welyab.anjabachen.movement.MovementOrigin;
import com.welyab.anjabachen.movement.MovementTarget;
import com.welyab.anjabachen.movement.PieceMovement;
import com.welyab.anjabachen.util.Copiable;

/**
 * @author Welyab Paula
 */
public class Board implements Copiable<Board> {
	
	/** FEN notation for the initial position. */
	private static final String FEN_INITIAL_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
	
	/**
	 * The king's side rook initial column number position.
	 */
	private static final int KING_SIDE_ROOK_INITIAL_COLUMN = 7;
	
	/**
	 * The queen's side rook initial column number position.
	 */
	private static final int QUEEN_SIDE_ROOK_INITIAL_COLUMN = 0;
	
	/**
	 * The black rook initial row number position.
	 */
	private static final int BLACK_ROOK_INITIAL_ROW = 0;
	
	/**
	 * The white rook initial row number position.
	 */
	private static final int WHITE_ROOK_INITIAL_ROW = 7;
	
	/**
	 * The row number where black pawns are promoted.
	 */
	private static final int BLACK_PAWN_PROMOTION_ROW = 7;
	
	/**
	 * The row number where white pawns are promoted.
	 */
	private static final int WHITE_PAWN_PROMOTION_ROW = 0;
	
	/**
	 * The initial row, in the usual game, where the black pawns are placed.
	 */
	private static final int BLACK_PAWN_INITIAL_ROW = 1;
	
	/**
	 * The initial row, in the usual game, where the white pawns are placed.
	 */
	private static final int WHITE_PAWN_INITIAL_ROW = 6;
	
	/**
	 * A random number generator used manily in the method that provides random movements.
	 *
	 * @see #getRandomMovement()
	 */
	private static final Random random = new Random();
	
	/**
	 * When a pawn is not promoting to another piece, the target piece is the same. This field
	 * exists for facilitate compatibility with {@link #pawnPromotionReplacements}.
	 */
	private static final List<PieceType> pawnNonPromotionReplacements = Collections.unmodifiableList(
		Arrays.asList(
			PieceType.PAWN
		)
	);
	
	/**
	 * The list of possible replacements for a promoting pawn.
	 */
	private static final List<PieceType> pawnPromotionReplacements = Collections.unmodifiableList(
		Arrays.asList(
			PieceType.QUEEN,
			PieceType.ROOK,
			PieceType.BISHOP,
			PieceType.KNIGHT
		)
	);
	
	/**
	 * New line.
	 */
	public static final String NEWLINE = String.format("%n");
	
	/**
	 * The movement template for the king piece.
	 */
	private static List<Direction> kingMoveTemplate = Collections.unmodifiableList(
		Arrays.asList(
			new Direction(-1, -1),
			new Direction(-1, +0),
			new Direction(-1, +1),
			new Direction(+0, -1),
			new Direction(+0, +1),
			new Direction(+1, -1),
			new Direction(+1, +0),
			new Direction(+1, +1)
		)
	);
	
	/**
	 * The movement template for the queen piece.
	 */
	private static List<Direction> queenMoveTemplate = Collections.unmodifiableList(
		Arrays.asList(
			new Direction(-1, -1),
			new Direction(-1, +0),
			new Direction(-1, +1),
			new Direction(+0, -1),
			new Direction(+0, +1),
			new Direction(+1, -1),
			new Direction(+1, +0),
			new Direction(+1, +1)
		)
	);
	
	/**
	 * The movement template for the rook piece.
	 */
	private static List<Direction> rookMoveTemplate = Collections.unmodifiableList(
		Arrays.asList(
			new Direction(-1, +0),
			new Direction(+0, -1),
			new Direction(+0, +1),
			new Direction(+1, +0)
		)
	);
	
	/**
	 * The movement template for the bishop piece.
	 */
	private static List<Direction> bishopMoveTemplate = Collections.unmodifiableList(
		Arrays.asList(
			new Direction(-1, -1),
			new Direction(-1, +1),
			new Direction(+1, -1),
			new Direction(+1, +1)
		)
	);
	
	/**
	 * The movement template for the knight piece.
	 */
	private static List<Direction> knightMoveTemplate = Collections.unmodifiableList(
		Arrays.asList(
			new Direction(-2, +1),
			new Direction(-2, -1),
			new Direction(+2, +1),
			new Direction(+2, -1),
			new Direction(+1, +2),
			new Direction(-1, +2),
			new Direction(+1, -2),
			new Direction(-1, -2)
		)
	);
	
	/**
	 * The movement template for the black pawn.
	 */
	private static List<Direction> blackPawnMoveTemplate = Collections.unmodifiableList(
		Arrays.asList(
			new Direction(+1, +0),
			new Direction(+2, +0),
			new Direction(+1, +1),
			new Direction(+1, -1)
		)
	);
	
	/**
	 * The movement template for the white pawn.
	 */
	private static List<Direction> whitePawnMoveTemplate = Collections.unmodifiableList(
		Arrays.asList(
			new Direction(-1, +0),
			new Direction(-2, +0),
			new Direction(-1, +1),
			new Direction(-1, -1)
		)
	);
	
	/**
	 * The 2-dimensional array where the pieces are placed.
	 */
	private Piece[][] grid;
	
	/**
	 * The game info associated with this board.
	 */
	private GameInfo gameInfo;
	
	/**
	 * Movement log for undo and redo.
	 */
	private final List<MovementLog> movementHistory;
	
	/**
	 * Creates a new board with the pieces in the initial position and the white pieces turned to
	 * make next movement.
	 *
	 * <p>
	 * The board is placed as the follow:
	 *
	 * <pre>
	 * ┌───┬───┬───┬───┬───┬───┬───┬───┐
	 * │ r │ n │ b │ q │ k │ b │ n │ r │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │ p │ p │ p │ p │ p │ p │ p │ p │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │   │   │   │   │   │   │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │   │   │   │   │   │   │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │   │   │   │   │   │   │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │   │   │   │   │   │   │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │ P │ P │ P │ P │ P │ P │ P │ P │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │ R │ N │ B │ Q │ K │ B │ N │ R │
	 * └───┴───┴───┴───┴───┴───┴───┴───┘
	 * </pre>
	 */
	public Board() {
		this(FEN_INITIAL_POSITION);
	}
	
	/**
	 * Creates a new board with using a FEN string.
	 *
	 * @param fen The fen.
	 */
	public Board(String fen) {
		this(FenParser.of(fen));
	}
	
	@SuppressWarnings("javadoc")
	private Board(FenParser fenParser) {
		this(fenParser.getPiecesDisposition(), fenParser.getBoardConfig());
	}
	
	/**
	 * Creates a board using a encoded string as source for the pieces disposition.
	 * 
	 * <pre>
	 * String pieces = ""
	 *   + ". k . . . . . ."
	 *   + ". . r . . . . ."
	 *   + ". . . . . . . ."
	 *   + ". . . . . . . ."
	 *   + ". . . . . . . ."
	 *   + ". . . . . . . ."
	 *   + ". . . K . . . ."
	 *   + ". . . . Q . . .";
	 *   
	 * Board board = new Board(pieces, BoardConfig.defaultBlackToMove());
	 * </pre>
	 *
	 * <p>
	 * The disposition of pieces in the being created board can't break the rules of chess:
	 * 
	 * <ul>
	 * <li>the side with turn to move may have its king in check, but the opposite side, don't
	 * <li>a king can't be attacked by three pieces or more at same time
	 * <li>pawns in last rank are not allowed
	 * </ul>
	 *
	 * @param piecesDisposition String where each character represents a board's piece. The string
	 *        should have exactly 64 non white space characters:
	 * 
	 *        <ul>
	 *        <li><code>'.'</code> (dot) - represents an empty square
	 *        <li><code>'K'</code> (upper case) - white king
	 *        <li><code>'Q'</code> (upper case) - white queen
	 *        <li><code>'R'</code> (upper case) - white rook
	 *        <li><code>'B'</code> (upper case) - white bishop
	 *        <li><code>'N'</code> (upper case) - white knight
	 *        <li><code>'P'</code> (upper case) - white pawn
	 *        <li><code>'k'</code> (lower case) - black king
	 *        <li><code>'q'</code> (lower case) - black queen
	 *        <li><code>'r'</code> (lower case) - black rook
	 *        <li><code>'b'</code> (lower case) - black bishop
	 *        <li><code>'n'</code> (lower case) - black knight
	 *        <li><code>'p'</code> (lower case) - black pawn
	 *        </ul>
	 * 
	 * @param boardConfig The board configuration. This object indicated if there is a possible
	 *        <i>en passant</i> capture for current movement, and what castling movements are
	 *        available for kings.
	 */
	public Board(String piecesDisposition, BoardConfig boardConfig) {
		this(boardStringToLocalizedPieceList(piecesDisposition), boardConfig);
	}
	
	/**
	 * Creates a board using given list of pieces and its respective locations.
	 * 
	 * <p>
	 * The disposition of pieces in the being created board can't break the rules of chess:
	 * 
	 * <ul>
	 * <li>the side with turn to move may have its king in check, but the opposite side, don't
	 * <li>a king can't be attacked by three pieces or more at same time
	 * <li>pawns in last rank are not allowed
	 * </ul>
	 * 
	 * @param pieces A list of <code>LocalizedPiece</code> (a pair of
	 *        <code>Piece</code>/<code>Position</code>) which describes each location where a piece
	 *        should be added.
	 * 
	 * @param boardConfig The board configuration. This object indicated if there is a possible
	 *        <i>en passant</i> capture for current movement, and what castling movements are
	 *        available for kings.
	 */
	public Board(List<LocalizedPiece> pieces, BoardConfig boardConfig) {
		this(
			localizedPieceListToGrid(pieces),
			new GameInfo(boardConfig),
			new ArrayList<>()
		);
	}
	
	@SuppressWarnings("javadoc")
	private Board(Piece[][] grid, GameInfo gameInfo, List<MovementLog> movementHistory) {
		this.grid = grid;
		this.gameInfo = gameInfo;
		this.movementHistory = movementHistory;
		for (int row = 0; row < BoardUtils.BOARD_SIZE; row++) {
			for (int column = 0; column < BoardUtils.BOARD_SIZE; column++) {
				Position position = Position.of(row, column);
				if (isNotEmpty(position) && getPiece(position).isKing()) {
					gameInfo.setKingPosition(
						getPiece(position).getColor(),
						Position.of(row, column)
					);
				}
			}
		}
	}
	
	/**
	 * Creates and returns a bi-dimensional array (<code>Piece[][]</code>) with all positions
	 * indicated by the list of <code>LocalizedPiece</code> set.
	 * 
	 * @param pieces The list of pieces and its position to be added in the being created grid.
	 * 
	 * @return The grid <code>Piece[][]</code>.
	 */
	private static Piece[][] localizedPieceListToGrid(List<LocalizedPiece> pieces) {
		Piece[][] grid = new Piece[BoardUtils.BOARD_SIZE][BoardUtils.BOARD_SIZE];
		pieces.forEach(p -> grid[p.getPosition().getRow()][p.getPosition().getColumn()] = p.getPiece());
		return grid;
	}
	
	/**
	 * Converts the encoded board into a list of <code>LocalizedPiece</code>.
	 * 
	 * @param boardString The board encoded in the string.
	 * 
	 * @return The list of <code>LocalizedPiece</code>.
	 * 
	 * @see #Board(String, BoardConfig)
	 */
	private static List<LocalizedPiece> boardStringToLocalizedPieceList(String boardString) {
		List<LocalizedPiece> dispositionList = new ArrayList<>();
		int counter = 0;
		for (int i = 0; i < boardString.length(); i++) {
			char c = boardString.charAt(i);
			if (Character.isWhitespace(c) || c == '.') {
				if (c == '.') {
					counter++;
				}
				continue;
			}
			Piece piece = null;
			try {
				piece = Piece.fromLetterSymbol(c);
			} catch (IllegalArgumentException e) {
				throw new ChessException(String.format("Invalid character at position %d: %c", i, c), e);
			}
			int row = counter / BoardUtils.BOARD_SIZE;
			int column = counter % BoardUtils.BOARD_SIZE;
			counter++;
			dispositionList.add(new LocalizedPiece(piece, Position.of(row, column)));
		}
		return dispositionList;
	}
	
	/**
	 * Adds a piece to the board.
	 * 
	 * <p>
	 * Some conditions are evaluated in order to keep the rules of chess safe:
	 * 
	 * <ul>
	 * <li>The board can't have two kings of same color
	 * <li>The being added piece can't attack the enemy king if its own king is in check, except if
	 * the adding piece is breaking that condition in some way.
	 * <li>The being added piece can't attack the enemy king if it is in double check, except if the
	 * adding piece is breaking that condition in some way.
	 * <li>It's not possible add a pawn in the last rank.
	 * </ul>
	 * 
	 * @param piece The piece to be added.
	 * @param position The location to adding the piece.
	 */
	public void addPiece(Piece piece, Position position) {
		setPiece(piece, position);
		if (piece.isKing()) {
			gameInfo.setKingPosition(piece.getColor(), position);
		}
	}
	
	/**
	 * Moves the piece located in position informed by <code>originPosition</code> parameter to the
	 * location indicated by <code>targetPosition</code> parameter. If the movement is a pawn
	 * promotion, a queen piece is used as replacement to being promoted pawn.
	 *
	 * <p>
	 * If the board has movements to redoes and if that movement is equals the being made movement,
	 * this method does not create a entry in the movement history, it just redo the movement. If
	 * the movement is different, the board will create a branch in the movement history as child of
	 * current line.
	 *
	 * @param originPosition The origin of the piece.
	 * @param targetPosition The destination of the piece.
	 *
	 * @throws EmptySquareException If the square located in the position indicated by
	 *         <code>originPosition</code> parameter is empty.
	 * @throws MovementException If the piece located in the <code>originPosition</code> parameter
	 *         can't reach the position indicated by <code>targetPosition</code> parameter.
	 */
	public void move(Position originPosition, Position targetPosition) {
		move(originPosition, targetPosition, PieceType.QUEEN);
	}
	
	/**
	 * Performs the movement indicated by given <code>movement</code> parameter.
	 * 
	 * <p>
	 * If the board has movements to redoes and if that movement is equals the being made movement,
	 * this method does not create a entry in the movement history, it just redo the movement. If
	 * the movement is different, the board will create a branch in the movement history as child of
	 * current line.
	 * 
	 * @param movement The movement to be made.
	 * 
	 * @throws EmptySquareException If the square located in the origin position is empty.
	 * @throws MovementException If the piece located in the origin square can't reach the indicated
	 *         target position.
	 */
	public void move(Movement movement) {
		move(movement.getOrigin(), movement.getTarget());
	}
	
	/**
	 * Moves the piece located in position informed by <code>originPosition</code> parameter to the
	 * location indicated by <code>targetPosition</code> parameter.
	 *
	 * <p>
	 * If the board has movement to redoes and if that movement is equals the being made movement,
	 * this method does not create a entry in the movement history, it just redo the movement. If
	 * the movement is different, the board will create a branch in the movement history as child of
	 * current line.
	 *
	 * @param originPosition The origin of the piece.
	 * @param targetPosition The destination of the piece.
	 * @param toPromotePawn The piece to be used as replacement in case of pawn promotion. This
	 *        field may be <code>null</code> and in that case a queen will be used as replacement.
	 *        If the movement is not a pawn promotion, this field is ignored.
	 *
	 * @throws EmptySquareException If the square located in the position indicated by
	 *         <code>originPosition</code> parameter is empty.
	 * @throws MovementException If the piece located in the <code>originPosition</code> parameter
	 *         can't reach the position indicated by <code>targetPosition</code> parameter.
	 * @throws MovementException If the movement is a pawn promotion and informed replacement piece
	 *         is a pawn or a king.
	 */
	public void move(Position originPosition, Position targetPosition, PieceType toPromotePawn) {
		toPromotePawn = toPromotePawn == null ? PieceType.QUEEN : toPromotePawn;
		PieceMovement pieceMovement = getMovement(originPosition);
		MovementTarget target = null;
		for (int i = 0; i < pieceMovement.size(); i++) {
			MovementTarget currentTarget = pieceMovement.getTarget(i);
			if (currentTarget.getPosition().equals(targetPosition)) {
				if (BoardUtils.isPromotion(currentTarget.getMovementFlags())) {
					if (currentTarget.getPiece().getType() == toPromotePawn) {
						target = currentTarget;
						break;
					}
				} else {
					target = currentTarget;
					break;
				}
			}
		}
		
		if (target == null) {
			throw new MovementException(
				String.format(
					"The piece %s located in [%d, %d] can't reach the location [%d, %d]",
					getPiece(originPosition),
					originPosition.getRow(), originPosition.getColumn(),
					targetPosition.getRow(), targetPosition.getColumn()
				)
			);
		}
		
		move(pieceMovement.getOrigin(), target);
	}
	
	/**
	 * Moves the piece located in the origin parameter to the target parameter. The
	 * <code>movement target</code> parameter has a set of flags that indicate if the movement is a
	 * <i>en passant</i>, capture, castling, etc...
	 *
	 * <p>
	 * If the board has movement to redoes and if that movement is equals the being made movement,
	 * this method does not create a entry in the movement history, it just redo the movement. If
	 * the movement is different, the board will create a branch in the movement history as child of
	 * current line.
	 *
	 * @param movementOrigin The movement origin.
	 * @param movementTarget The movement target.
	 */
	public void move(MovementOrigin movementOrigin, MovementTarget movementTarget) {
		GameInfo copiedGameInfo = gameInfo.copy();
		Piece capturedPiece = null;
		if (BoardUtils.isCapture(movementTarget.getMovementFlags())
				&& !BoardUtils.isEnPassant(movementTarget.getMovementFlags())) {
			capturedPiece = getPiece(movementTarget.getPosition());
		}
		
		setPiece(movementTarget.getPiece(), movementTarget.getPosition());
		setPiece(null, movementOrigin.getPosition());
		
		if (BoardUtils.isCastling(movementTarget.getMovementFlags())) {
			Position rookOrigin = Position.of(
				movementOrigin.getPosition().getRow(),
				movementOrigin.getPosition().getColumn() < movementTarget.getPosition().getColumn() ? 7 : 0
			);
			int adjuster = movementOrigin.getPosition().getColumn() < movementTarget.getPosition().getColumn() ? -1 : 1;
			Position rookTarget = Position.of(
				movementOrigin.getPosition().getRow(),
				movementTarget.getPosition().getColumn() + adjuster
			);
			setPiece(getPieceOrNull(rookOrigin), rookTarget);
			setPiece(null, rookOrigin);
		}
		
		if (BoardUtils.isEnPassant(movementTarget.getMovementFlags())) {
			Position position = Position.of(
				movementOrigin.getPosition().getRow(),
				movementTarget.getPosition().getColumn()
			);
			capturedPiece = getPiece(position);
			setPiece(null, position);
		}
		
		if (movementOrigin.getPiece().isPawn()
				&& Math.abs(movementOrigin.getPosition().getRow() - movementTarget.getPosition().getRow()) == 2) {
			int midRow = (movementOrigin.getPosition().getRow() + movementTarget.getPosition().getRow()) / 2;
			gameInfo.enPassantTargetSquare = Position.of(midRow, movementTarget.getPosition().getColumn());
		} else {
			gameInfo.enPassantTargetSquare = null;
		}
		
		if (movementOrigin.getPiece().isPawn() || BoardUtils.isCapture(movementTarget.getMovementFlags())) {
			gameInfo.halfMoveCounter = 0;
		} else {
			gameInfo.halfMoveCounter++;
		}
		
		if (movementOrigin.getPiece().isBlack()) {
			gameInfo.fullMoveCounter++;
		}
		
		if (movementOrigin.getPiece().isKing()) {
			gameInfo.setKingPosition(movementOrigin.getPiece().getColor(), movementTarget.getPosition());
			gameInfo.invalidateCastling(movementOrigin.getPiece().getColor());
		}
		
		if (movementOrigin.getPiece().isRook()) {
			if (gameInfo.isKingSideCastlingAvailable(movementOrigin.getPiece().getColor())
					&& getKingSideRookPosition(movementOrigin.getPiece().getColor()).equals(movementOrigin.getPosition())) {
				gameInfo.invalidateKingSideCastling(movementOrigin.getPiece().getColor());
			}
			if (gameInfo.isQueenSideCastlingAvailable(movementOrigin.getPiece().getColor())
					&& getQueenSideRookPosition(movementOrigin.getPiece().getColor()).equals(movementOrigin.getPosition())) {
				gameInfo.invalidateQueenSideCastling(movementOrigin.getPiece().getColor());
			}
		}
		
		if (capturedPiece != null && capturedPiece.isRook()) {
			if (gameInfo.isKingSideCastlingAvailable(capturedPiece.getColor())
					&& getKingSideRookPosition(capturedPiece.getColor()).equals(movementTarget.getPosition())) {
				gameInfo.invalidateKingSideCastling(capturedPiece.getColor());
			}
			if (gameInfo.isQueenSideCastlingAvailable(capturedPiece.getColor())
					&& getQueenSideRookPosition(capturedPiece.getColor()).equals(movementTarget.getPosition())) {
				gameInfo.invalidateQueenSideCastling(capturedPiece.getColor());
			}
		}
		
		gameInfo.moveCounter++;
		
		movementHistory.add(
			new MovementLog(
				movementOrigin,
				movementTarget,
				capturedPiece,
				copiedGameInfo
			)
		);
	}
	
	/**
	 * Redoes a previously undone movement. If there are branch variants, this method always uses
	 * its main line.
	 */
	public void redo() {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	/**
	 * Evaluates if there is a previous movement in the movement history of this game.
	 *
	 * @return A value <code>true</code> if there is a previous movement, or <code>false</code>
	 *         otherwise.
	 */
	public boolean hasPreviousMovement() {
		return !movementHistory.isEmpty();
	}
	
	/**
	 * Undo all movements made with this board. If the board state is in a branch variant, the
	 * undoing continues going to the parent branch until reach the last initial board state.
	 */
	public void undoAll() {
		while (hasPreviousMovement()) {
			undo();
		}
	}
	
	/**
	 * Undoes the last movement made on the board. The movement is not removed from game history and
	 * can be redone.
	 *
	 * <p>
	 * However, if a different movement is done, a new entry entry is registered in the game history
	 * as game variant branch of its original line. There is no limit for game variants
	 * registered in the game history. The {@link #redo()} operation is made over current game
	 * line.
	 *
	 * @throws MovementException If there is not movement to undo.
	 */
	public void undo() {
		if (!hasPreviousMovement()) {
			throw new MovementException("No previous movement to undo");
		}
		
		MovementLog movementLog = movementHistory.remove(movementHistory.size() - 1);
		MovementOrigin movementOrigin = movementLog.movementOrigin;
		MovementTarget movementTarget = movementLog.movementTarget;
		
		setPiece(movementOrigin.getPiece(), movementOrigin.getPosition());
		setPiece(null, movementTarget.getPosition());
		
		if (BoardUtils.isCapture(movementTarget.getMovementFlags())) {
			if (BoardUtils.isEnPassant(movementTarget.getMovementFlags())) {
				setPiece(
					movementLog.capturedPiece,
					Position.of(
						movementOrigin.getPosition().getRow(),
						movementTarget.getPosition().getColumn()
					)
				);
			} else {
				setPiece(movementLog.capturedPiece, movementTarget.getPosition());
			}
		}
		
		if (BoardUtils.isCastling(movementTarget.getMovementFlags())) {
			Position leftPosition = movementTarget.getPosition().left();
			if (isNotEmpty(leftPosition) && getPiece(leftPosition).isRook()) {
				setPiece(
					getPiece(leftPosition),
					Position.of(movementTarget.getPosition().getRow(), BoardUtils.MAX_COLUMN_NUMBER)
				);
				setPiece(null, leftPosition);
			} else {
				Position rightPosition = movementTarget.getPosition().right();
				setPiece(
					getPiece(rightPosition),
					Position.of(movementTarget.getPosition().getRow(), BoardUtils.MIN_COLUMN_NUMBER)
				);
				setPiece(null, rightPosition);
			}
		}
		
		gameInfo = movementLog.gameInfo;
	}
	
	/**
	 * Evaluates if the given position locates a empty square in the board.
	 *
	 * @param position The position.
	 *
	 * @return A value <code>true</code> if the square indicated by given position is empty, or
	 *         <code>false</code> otherwise.
	 */
	public boolean isEmpty(Position position) {
		return grid[position.getRow()][position.getColumn()] == null;
	}
	
	/**
	 * Evaluates if the given position locates a non empty square in the board.
	 * 
	 * @param position The position.
	 * 
	 * @return A value <code>true</code> if the square indicated by given position is non empty, or
	 *         <code>false</code> otherwise.
	 */
	public boolean isNotEmpty(Position position) {
		return !isEmpty(position);
	}
	
	/**
	 * Retrieves the piece located in the given position.
	 *
	 * @param position The position.
	 *
	 * @return The piece located in the given position.
	 */
	public Piece getPiece(Position position) {
		if (isEmpty(position)) {
			throw new EmptySquareException(position.getRow(), position.getColumn());
		}
		return grid[position.getRow()][position.getColumn()];
	}
	
	/**
	 * Retrieves the piece located in the given position, or <code>null</code>, if the location is
	 * empty.
	 * 
	 * @param position The piece position.
	 * 
	 * @return The piece located in the given position, or <code>null</code>, if the location is
	 *         empty.
	 */
	private Piece getPieceOrNull(Position position) {
		return grid[position.getRow()][position.getColumn()];
	}
	
	/**
	 * Simply set the square located by given position with specified piece without any
	 * verification.
	 * 
	 * @param piece The piece to be placed in the given position.
	 * @param position The location where the piece should be placed.
	 */
	private void setPiece(Piece piece, Position position) {
		grid[position.getRow()][position.getColumn()] = piece;
	}
	
	/**
	 * Retrieves a valid movement from the list of possible movement available in the board.
	 *
	 * @return The movement.
	 *
	 * @throws MovementException If there is not valid movement available in the board. The board
	 *         may be in a checkmate or draw state...
	 *
	 * @see #moveRandom()
	 */
	public Movement getRandomMovement() {
		BoardMovements bag = getMovements();
		if (bag.isEmpty()) {
			throw new MovementException("No valid movement available. Checkmate?");
		}
		PieceMovement pieceMovement = bag.get(random.nextInt(bag.size()));
		MovementTarget target = pieceMovement.getTarget(random.nextInt(pieceMovement.size()));
		return new Movement(pieceMovement.getOrigin(), target);
	}
	
	/**
	 * The total movements made since board creation. The movement starts in zero is incremented
	 * after each white or black movement.
	 *
	 * @return The movement count.
	 */
	public int getMovementCount() {
		return gameInfo.moveCounter;
	}
	
	/**
	 * Performs
	 *
	 * @throws MovementException If there is not valid movement available in the board. The board
	 *         may be in a checkmate or draw state...
	 *
	 * @see #getRandomMovement()
	 */
	public void moveRandom() {
		Movement movement = getRandomMovement();
		move(movement.getOrigin(), movement.getTarget());
	}
	
	/**
	 * The side color that has the next movement.
	 *
	 * @return The side color.
	 */
	public Color getActiveColor() {
		return gameInfo.getActiveColor();
	}
	
	@Override
	public Board copy() {
		return new Board(
			copyGrid(),
			gameInfo.copy(),
			copyMoveHistory()
		);
	}
	
	/**
	 * Retrieves the position of the king piece.
	 *
	 * @param color The color of the piece.
	 *
	 * @return The position.
	 *
	 * @throws KingNotPresentException If the king of the specific color is not present in the
	 *         board.
	 */
	public Position getKingPosition(Color color) {
		if (!gameInfo.isKingPresent(color)) {
			throw new KingNotPresentException(color);
		}
		return gameInfo.getKingPosition(color);
	}
	
	/**
	 * Evaluates if the given position is attacked by a piece of the specific color.
	 *
	 * @param position The attacked position.
	 * @param attackerColor The attacker color.
	 *
	 * @return A value <code>true</code> if the position is attacked, or <code>false</code>
	 *         otherwise.
	 */
	public boolean isUnderAttack(Position position, Color attackerColor) {
		return !getAttackers(position, attackerColor).isEmpty();
	}
	
	/**
	 * Given a position, this method returns the list of positions where there are a piece of the
	 * specific color attacking the informed location.
	 *
	 * @param squarePosition The attacked position.
	 * @param attackerColor The attacker color
	 *
	 * @return The list of attackers.
	 */
	public List<Position> getAttackers(Position squarePosition, Color attackerColor) {
		var attackers = new ArrayList<Position>();
		attackers.addAll(getAttackersFromKing(squarePosition, attackerColor));
		attackers.addAll(getAttackersFromQueen(squarePosition, attackerColor));
		attackers.addAll(getAttackersFromRook(squarePosition, attackerColor));
		attackers.addAll(getAttackersFromBishop(squarePosition, attackerColor));
		attackers.addAll(getAttackersFromKnight(squarePosition, attackerColor));
		attackers.addAll(getAttackersFromPawn(squarePosition, attackerColor));
		return attackers;
	}
	
	private List<Position> getAttackersFromQueen(Position squarePosition, Color attackerColor) {
		int maxMoveLength = getMaxPieceMovementLength(PieceType.QUEEN);
		List<Position> attackers = new ArrayList<>();
		for (int t = 0; t < queenMoveTemplate.size(); t++) {
			for (int mLength = 1; mLength <= maxMoveLength; mLength++) {
				Direction directionAdjuster = queenMoveTemplate.get(t);
				int targetRow = squarePosition.getRow() + mLength * directionAdjuster.rowAdjuster;
				int targetColumn = squarePosition.getColumn() + mLength * directionAdjuster.columnAdjuster;
				if (isInsideBoard(targetRow, targetColumn)) {
					Position targetPosition = Position.of(targetRow, targetColumn);
					if (isNotEmpty(targetPosition)) {
						Piece targetPiece = getPiece(targetPosition);
						if (targetPiece.isQueen() && targetPiece.getColor().equals(attackerColor)) {
							attackers.add(Position.of(targetRow, targetColumn));
						}
						break;
					}
				}
			}
		}
		return attackers;
	}
	
	private List<Position> getAttackersFromRook(Position squarePosition, Color attackerColor) {
		int maxMoveLength = getMaxPieceMovementLength(PieceType.ROOK);
		List<Position> attackers = new ArrayList<>();
		for (int t = 0; t < rookMoveTemplate.size(); t++) {
			for (int mLength = 1; mLength <= maxMoveLength; mLength++) {
				Direction directionAdjuster = rookMoveTemplate.get(t);
				int targetRow = squarePosition.getRow() + mLength * directionAdjuster.rowAdjuster;
				int targetColumn = squarePosition.getColumn() + mLength * directionAdjuster.columnAdjuster;
				if (isInsideBoard(targetRow, targetColumn)) {
					Position targetPosition = Position.of(targetRow, targetColumn);
					if (isNotEmpty(targetPosition)) {
						Piece targetPiece = getPiece(targetPosition);
						if (targetPiece.isRook() && targetPiece.getColor().equals(attackerColor)) {
							attackers.add(Position.of(targetRow, targetColumn));
						}
						break;
					}
				}
			}
		}
		return attackers;
	}
	
	private List<Position> getAttackersFromBishop(Position squarePosition, Color attackerColor) {
		int maxMoveLength = getMaxPieceMovementLength(PieceType.BISHOP);
		List<Position> attackers = new ArrayList<>();
		for (int t = 0; t < bishopMoveTemplate.size(); t++) {
			for (int mLength = 1; mLength <= maxMoveLength; mLength++) {
				Direction directionAdjuster = bishopMoveTemplate.get(t);
				int targetRow = squarePosition.getRow() + mLength * directionAdjuster.rowAdjuster;
				int targetColumn = squarePosition.getColumn() + mLength * directionAdjuster.columnAdjuster;
				if (isInsideBoard(targetRow, targetColumn)) {
					Position targetPosition = Position.of(targetRow, targetColumn);
					if (isNotEmpty(targetPosition)) {
						Piece targetPiece = getPiece(targetPosition);
						if (targetPiece.isBishop() && targetPiece.getColor().equals(attackerColor)) {
							attackers.add(Position.of(targetRow, targetColumn));
						}
						break;
					}
				}
			}
		}
		return attackers;
	}
	
	/**
	 * Calculates the movement value of the pieces located in the given position. The value
	 * considered for empty square is zero.
	 * 
	 * @param originPosition The position of origin of the piece being moved.
	 * @param targetPosition The destiny position of the piece being moved.
	 * 
	 * @return Three values are possible:
	 * 
	 *         <ul>
	 *         <li>A value less than zero if the piece can move from origin to target with a capture
	 *         of the piece located in the target square.
	 *         <li>A value zero if the piece can move from origin to target with a movement without
	 *         capturing (the target square is empty).
	 *         <li>A value great than zero if the piece located in the origin square can't move to
	 *         the target square cause' it is occupied by a piece of the same color.
	 *         </ul>
	 */
	private int getMoveValue(Position originPosition, Position targetPosition) {
		int originValue = isEmpty(originPosition) ? 0 : getPiece(originPosition).getId();
		int targetValue = isEmpty(targetPosition) ? 0 : getPiece(targetPosition).getId();
		
		return originValue * targetValue;
	}
	
	/**
	 * Given a position, this method returns the list of positions where there is a king of the
	 * specific color attacking the informed location.
	 *
	 * @param squarePosition The attacked position.
	 * @param attackerColor The attacker color
	 *
	 * @return The list of attackers.
	 */
	private List<Position> getAttackersFromKing(Position squarePosition, Color attackerColor) {
		List<Position> attackers = new ArrayList<>();
		for (int t = 0; t < kingMoveTemplate.size(); t++) {
			int targetRow = squarePosition.getRow() + knightMoveTemplate.get(t).rowAdjuster;
			int targetColumn = squarePosition.getColumn() + knightMoveTemplate.get(t).columnAdjuster;
			if (isInsideBoard(targetRow, targetColumn)) {
				Position targetPosition = Position.of(targetRow, targetColumn);
				if (isNotEmpty(targetPosition)) {
					Piece piece = getPiece(targetPosition);
					if (piece.isKing() && piece.getColor().equals(attackerColor)) {
						attackers.add(Position.of(targetRow, targetColumn));
					}
				}
			}
		}
		return attackers;
	}
	
	/**
	 * Given a position, this method returns the list of positions where there are a knight of the
	 * specific color attacking the informed location.
	 *
	 * @param squarePosition The attacked position.
	 * @param attackerColor The attacker color
	 *
	 * @return The list of attackers.
	 */
	private List<Position> getAttackersFromKnight(Position squarePosition, Color attackerColor) {
		List<Position> attackers = new ArrayList<>();
		for (int t = 0; t < knightMoveTemplate.size(); t++) {
			int targetRow = squarePosition.getRow() + knightMoveTemplate.get(t).rowAdjuster;
			int targetColumn = squarePosition.getColumn() + knightMoveTemplate.get(t).columnAdjuster;
			if (isInsideBoard(targetRow, targetColumn)) {
				Position targetPosition = Position.of(targetRow, targetColumn);
				if (isNotEmpty(targetPosition)) {
					Piece piece = getPiece(targetPosition);
					if (piece.isKnight() && piece.getColor().equals(attackerColor)) {
						attackers.add(Position.of(targetRow, targetColumn));
					}
				}
			}
		}
		return attackers;
	}
	
	/**
	 * Given a position, this method returns the list of positions where there are a pawn of the
	 * specific color attacking the informed location.
	 *
	 * @param squarePosition The attacked position.
	 * @param attackerColor The attacker color.
	 *
	 * @return The list of attackers.
	 */
	private List<Position> getAttackersFromPawn(Position squarePosition, Color attackerColor) {
		List<Position> attackers = new ArrayList<>();
		int direction = attackerColor.isWhite()
				? -1
				: 1;
		for (int i = -1; i <= 1; i += 2) {
			int targetRow = squarePosition.getRow() - direction;
			int targetColumn = squarePosition.getColumn() + i;
			if (isInsideBoard(targetRow, targetColumn)) {
				Position targetPosition = Position.of(targetRow, targetColumn);
				if (isNotEmpty(targetPosition)) {
					Piece piece = getPiece(targetPosition);
					if (piece.isPawn() && piece.getColor().equals(attackerColor)) {
						attackers.add(Position.of(targetRow, targetColumn));
					}
				}
			}
		}
		return attackers;
	}
	
	/**
	 * Creates a copy of the movement history object.
	 *
	 * @return The copy.
	 */
	private List<MovementLog> copyMoveHistory() {
		return movementHistory
			.stream()
			.map(MovementLog::copy)
			.collect(Collectors.toList());
	}
	
	/**
	 * Creates a new grid (<code>Piece[][]</code>) copy the current grid to the new grid.
	 *
	 * @return The copy of the grid.
	 */
	private Piece[][] copyGrid() {
		Piece[][] copy = new Piece[BoardUtils.BOARD_SIZE][BoardUtils.BOARD_SIZE];
		for (int row = 0; row < BoardUtils.BOARD_SIZE; row++) {
			for (int column = 0; column < BoardUtils.BOARD_SIZE; column++) {
				copy[row][column] = grid[row][column];
			}
		}
		return copy;
	}
	
	/**
	 * Generates a FEN string for the current board string.
	 *
	 * @return The FEN string.
	 *
	 * @see FenParser
	 */
	public String getFen() {
		StringBuilder builder = new StringBuilder();
		encodeFenPieceDisposition(builder);
		
		builder
			.append(' ')
			.append(getActiveColor().getLetterSymbol())
			.append(' ');
		
		encodeFenCastlingFlags(builder);
		
		builder.append(' ');
		
		if (gameInfo.enPassantTargetSquare != null) {
			builder.append(gameInfo.enPassantTargetSquare.getPgnPosition());
		} else {
			builder.append('-');
		}
		
		builder.append(' ');
		builder.append(gameInfo.halfMoveCounter);
		
		builder.append(' ');
		builder.append(gameInfo.getFullMoveCounter());
		
		return builder.toString();
	}
	
	/**
	 * Encode the piece disposition for FEN string.
	 * 
	 * @param builder The string builder where the encoded string will be placed.
	 */
	private void encodeFenPieceDisposition(StringBuilder builder) {
		for (int row = 0; row < BoardUtils.BOARD_SIZE; row++) {
			int emptyCounter = 0;
			if (row > 0) {
				builder.append('/');
			}
			for (int column = 0; column < BoardUtils.BOARD_SIZE; column++) {
				Position position = Position.of(row, column);
				if (isEmpty(position)) {
					emptyCounter++;
				} else {
					if (emptyCounter > 0) {
						builder.append(emptyCounter);
					}
					emptyCounter = 0;
					builder.append(getPiece(position).getLetterSymbol());
				}
			}
			if (emptyCounter > 0) {
				builder.append(emptyCounter);
			}
		}
	}
	
	/**
	 * Encode the castling flags for the FEN string.
	 * 
	 * @param builder The string builder where the encoded string will be placed.
	 */
	private void encodeFenCastlingFlags(StringBuilder builder) {
		StringBuilder castlingAvaiability = new StringBuilder();
		if (BoardUtils.isWhiteKingSideCastling(gameInfo.castlingFlags)) {
			castlingAvaiability.append(Piece.WHITE_KING.getLetterSymbol());
		}
		if (BoardUtils.isWhiteQueenSideCastling(gameInfo.castlingFlags)) {
			castlingAvaiability.append(Piece.WHITE_QUEEN.getLetterSymbol());
		}
		if (BoardUtils.isBlackKingSideCastling(gameInfo.castlingFlags)) {
			castlingAvaiability.append(Piece.BLACK_KING.getLetterSymbol());
		}
		if (BoardUtils.isBlackQueenSideCastling(gameInfo.castlingFlags)) {
			castlingAvaiability.append(Piece.BLACK_QUEEN.getLetterSymbol());
		}
		if (castlingAvaiability.length() == 0) {
			builder.append('-');
		} else {
			builder.append(castlingAvaiability);
		}
	}
	
	/**
	 * Indicates if the current game state is a position where the king of active color is in check,
	 * i.e, is under attacking of a enemy piece.
	 *
	 * @return A value <code>true</code> if the king of active color is under attack, or
	 *         <code>false</code> otherwise.
	 *
	 * @see #isCheckmate()
	 * @see #isDraw()
	 * @see #isStalemate()
	 */
	public boolean isCheck() {
		if (!gameInfo.isKingPresent(getActiveColor())) {
			return false;
		}
		Position kingPosition = gameInfo.getKingPosition(getActiveColor());
		return !getAttackers(
			kingPosition,
			getWaitingColor()
		).isEmpty();
	}
	
	/**
	 * Evaluates if the game finishing with checkmate.
	 *
	 * @return A value <code>true</code> if game is finished with checkmate, or <code>false</code>
	 *         otherwise.
	 *
	 * @see #isCheck()
	 * @see #isDraw()
	 * @see #isStalemate()
	 */
	public boolean isCheckmate() {
		return isCheck()
				&& getMovements().isEmpty();
	}
	
	/**
	 * Evaluates if the game finishing with stale mate.
	 *
	 * @return A value <code>true</code> if game is finished with stalemate, or <code>false</code>
	 *         otherwise.
	 *
	 * @see #isCheck()
	 * @see #isCheckmate()
	 * @see #isDraw()
	 */
	public boolean isStalemate() {
		return !isCheck()
				&& getMovements().isEmpty();
	}
	
	/**
	 * Evaluates if the game if finishing with a draw. Not that situations like stalemate
	 * ({@linkplain #isStalemate()} isStalemate) are also considered a draw.
	 *
	 * @return A value <code>true</code> if the game is finished with draw, or <code>false</code>
	 *         otherwise.
	 */
	public boolean isDraw() {
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	/**
	 * The side color that is waiting for the opponent movement.
	 *
	 * @return The side color.
	 */
	public Color getWaitingColor() {
		return getActiveColor().getOpposite();
	}
	
	/**
	 * Generates all valid movements available for the active color considering the current board
	 * state.
	 *
	 * <p>
	 * This method extract the extra movement flags during movement generation.
	 *
	 * @return The all movement object representation.
	 */
	public BoardMovements getMovements() {
		return getMovements(true);
	}
	
	/**
	 * Generates all valid movements available for the active color considering the current board
	 * state.
	 *
	 * @param extractExtraMovementFlags Indicates if this method should extract extra movement
	 *        flags.
	 *
	 * @return The all movements object representation.
	 */
	public BoardMovements getMovements(boolean extractExtraMovementFlags) {
		Iterable<LocalizedPiece> squares = this::privateIterator;
		List<PieceMovement> movements = new ArrayList<>(32);
		MovementMetadata.Builder pieceMovementMetaBuilder = MovementMetadata.builder();
		for (LocalizedPiece localizedPiece : squares) {
			if (!localizedPiece.isEmpty() && localizedPiece.getPiece().getColor().equals(getActiveColor())) {
				PieceMovement pieceMovement = privateGetMovements(
					localizedPiece.getPosition(), extractExtraMovementFlags
				);
				if (pieceMovement.isNotEmpty()) {
					movements.add(pieceMovement);
					pieceMovementMetaBuilder.add(pieceMovement.getMeta());
				}
			}
		}
		return new BoardMovements(movements, pieceMovementMetaBuilder.build());
	}
	
	/**
	 * Retrieves the available movements for the piece located at given position.
	 *
	 * <p>
	 * The generated movement comes with extra movement flags.
	 *
	 * @param position The location of the piece to generate its movements.
	 *
	 * @return The movements container.
	 *
	 * @throws EmptySquareException If the square at location <code>[row, column]</code> is empty.
	 */
	public PieceMovement getMovement(Position position) {
		return getMovement(position, true);
	}
	
	/**
	 * Retrieves the available movements for the piece located at given position.
	 *
	 * @param position The location of the piece to generate its movements
	 * @param extractExtraFlags A flag to indicate if the extra movement flags should be extract.
	 *
	 * @return @return The movements container.
	 */
	public PieceMovement getMovement(Position position, boolean extractExtraFlags) {
		if (isEmpty(position)) {
			throw new EmptySquareException(position.getRow(), position.getColumn());
		}
		return privateGetMovements(position, extractExtraFlags);
	}
	
	/**
	 * Creates a iterator for iterate over all squares of this board. It is expected that the board
	 * state do not change during interaction, otherwise a exception will be thrown.
	 *
	 * @return The iterator object.
	 */
	private Iterator<LocalizedPiece> privateIterator() {
		return new BoardPieceIterator();
	}
	
	@SuppressWarnings("javadoc")
	private class BoardPieceIterator implements Iterator<LocalizedPiece> {
		
		final int instantMovementOperationCount;
		
		BoardPieceIterator() {
			instantMovementOperationCount = gameInfo.moveCounter;
		}
		
		int index = 0;
		
		@Override
		public boolean hasNext() {
			return index < BoardUtils.SQUARES_COUNT;
		}
		
		@Override
		public LocalizedPiece next() {
			if (!hasNext()) {
				throw new NoSuchElementException("No more squares");
			}
			if (instantMovementOperationCount != gameInfo.moveCounter) {
				throw new ConcurrentModificationException(
					"The board have had changed its state during this iteration"
				);
			}
			int row = index / BoardUtils.BOARD_SIZE;
			int column = index % BoardUtils.BOARD_SIZE;
			index++;
			Position position = Position.of(row, column);
			return new LocalizedPiece(getPieceOrNull(position), position);
		}
	}
	
	@SuppressWarnings("javadoc")
	private PieceMovement privateGetMovements(Position position, boolean extractCheckFlags) {
		return getPiece(position).isPawn()
				? getPawnMovements(position, extractCheckFlags)
				: getKingQueenRookBishopKnightMovements(position, extractCheckFlags);
	}
	
	/**
	 * Generate movements for the piece located in the given position. This method expect that the
	 * piece square is not empty, and piece should any of king, queen, rook bishop or knight.
	 *
	 * @param originPosition The location
	 *
	 * @param extractExtraMovementFlags A flag to indicate if this method must extract extra
	 *        movement flags.
	 *
	 * @return The generated movements.
	 */
	private PieceMovement getKingQueenRookBishopKnightMovements(
			Position originPosition,
			boolean extractExtraMovementFlags
	) {
		Piece originPiece = getPiece(originPosition);
		int maxMoveLength = getMaxPieceMovementLength(originPiece.getType());
		List<Direction> directionAdjusters = switch (originPiece.getType()) {
			case KING -> kingMoveTemplate;
			case QUEEN -> queenMoveTemplate;
			case ROOK -> rookMoveTemplate;
			case BISHOP -> bishopMoveTemplate;
			case KNIGHT -> knightMoveTemplate;
			default -> throw new ChessError(
				String.format(
					"Unexpected piece type: %s",
					originPiece.getType()
				)
			);
		};
		List<MovementTarget> targets = new ArrayList<>();
		for (int t = 0; t < directionAdjusters.size(); t++) {
			for (int mLength = 1; mLength <= maxMoveLength; mLength++) {
				Direction directionAdjuster = directionAdjusters.get(t);
				int targetRow = originPosition.getRow() + mLength * directionAdjuster.rowAdjuster;
				int targetColumn = originPosition.getColumn() + mLength * directionAdjuster.columnAdjuster;
				if (!isInsideBoard(targetRow, targetColumn)) {
					break;
				}
				Position targetPosition = Position.of(targetRow, targetColumn);
				int moveValue = getMoveValue(originPosition, targetPosition);
				if (moveValue > 0) {
					break;
				}
				if (!isKingInCheckWithMove(
					originPosition,
					targetPosition,
					null
				)) {
					int movementFlags = 0;
					if (moveValue < 0) {
						movementFlags |= BoardUtils.CAPTURE;
					}
					if (extractExtraMovementFlags) {
						movementFlags |= extractExtraMovementFlags(
							originPiece,
							originPosition,
							originPiece,
							targetPosition,
							movementFlags
						);
					}
					targets.add(
						new MovementTarget(
							getPiece(originPosition),
							Position.of(targetRow, targetColumn),
							movementFlags
						)
					);
				}
				
				if (moveValue < 0) {
					break;
				}
			}
		}
		if (originPiece.isKing()) {
			targets.addAll(
				getCastlingTargets(
					originPosition,
					extractExtraMovementFlags
				)
			);
		}
		MovementMetadata.Builder builder = MovementMetadata.builder();
		targets.forEach(t -> builder.addMovement(t.getMovementFlags()));
		return new PieceMovement(
			new MovementOrigin(
				originPiece,
				originPosition
			),
			targets,
			builder.build()
		);
	}
	
	/**
	 * Verify if the movement described by given parameters is capable to be marked with following
	 * flags:
	 *
	 * <ul>
	 * <li>{@linkplain BoardUtils#CHECK CHECK} - if the movement puts the opposite king in check
	 * <li>{@linkplain BoardUtils#DOUBLE_CHECK DOUBLE_CHECK} - if the movement puts the opposite
	 * king in check, attacked by two pieces.
	 * <li>{@linkplain BoardUtils#DISCOVERY_CHECK DISCOVERY_CHECK} - if being moved piece don't
	 * checks the opposite king by itself, but uncover another piece, that attacks the king.
	 * <li>{@linkplain BoardUtils#CHECKMATE CHECKMATE} - the movement puts the opposite king in
	 * checkmate.
	 * <li>{@linkplain BoardUtils#STALEMATE STALEMATE} - the movement left the opposite with no
	 * valid movements, but not in check (it is a draw)
	 * </ul>
	 *
	 * @param originPiece The piece as it is in the origin square.
	 * @param originPosition The position of the origin square of the being moved piece.
	 * @param targetPiece The piece as it should be in the target square. This is used for pawn
	 *        promotions; in other cases, this parameters receive the same value of
	 *        <code>originPiece</code>.
	 * @param targetPosition The position of the target square where the piece will stay after
	 *        movement.
	 * @param currentMovementFlags The already verified flags for the movement.
	 *        <p>
	 *        At this point, this parameter may contain following flags:
	 *        {@linkplain BoardUtils#CAPTURE CAPTURE}, {@linkplain BoardUtils#EN_PASSANT
	 *        EN_PASSANT}, {@linkplain BoardUtils#PROMOTION PROMOTION}, and
	 *        {@linkplain BoardUtils#CASTLING CASTLING}.
	 *
	 * @return The additional flags, if any.
	 */
	private int extractExtraMovementFlags(
			Piece originPiece,
			Position originPosition,
			Piece targetPiece,
			Position targetPosition,
			int currentMovementFlags
	) {
		MovementOrigin movementOrigin = new MovementOrigin(originPiece, originPosition);
		MovementTarget movementTarget = new MovementTarget(targetPiece, targetPosition, currentMovementFlags);
		move(movementOrigin, movementTarget);
		int flags = extractExtraMovementFlags();
		undo();
		return flags;
	}
	
	/**
	 * This method verifies if the given <code>movementFlags</code> parameter has the markers for
	 * {@linkplain BoardUtils#CHECK CHECK}, {@linkplain BoardUtils#DOUBLE_CHECK DOUBLE_CHECK},
	 * {@linkplain BoardUtils#DISCOVERY_CHECK DISCOVERY_CHECK},
	 * {@linkplain BoardUtils#CHECKMATE CHECKMATE} and {@linkplain BoardUtils#STALEMATE
	 * STALEMATE}. For each one, the respective counter in the
	 * <code>PieceMovementMeta</code> is updated.
	 *
	 * @param builder The builder for <code>PieceMovementMeta</code> builder.
	 *
	 * @param movementFlags The movement flags.
	 */
	private void updatePieceMovementMetaWithExtraFlags(MovementMetadata.Builder builder, int movementFlags) {
		if (BoardUtils.isCheck(movementFlags)) {
			builder.incrementCheckCount();
		}
		
		if (BoardUtils.isDoubleCheck(movementFlags)) {
			builder.incrementDoubleCheckCount();
		}
		
		if (BoardUtils.isDiscoveryCheck(movementFlags)) {
			builder.incrementDiscoveryCheckCount();
		}
		
		if (BoardUtils.isCheckmate(movementFlags)) {
			builder.incrementCheckmateCount();
		}
		
		if (BoardUtils.isStalemate(movementFlags)) {
			builder.incrementStalemateCount();
		}
	}
	
	/**
	 * This method may be called after a movement in order to extract the following movement flags:
	 * {@linkplain BoardUtils#CHECK CHECK}, {@linkplain BoardUtils#DOUBLE_CHECK DOUBLE_CHECK},
	 * {@linkplain BoardUtils#DISCOVERY_CHECK DISCOVERY_CHECK},
	 * {@linkplain BoardUtils#CHECKMATE CHECKMATE} and {@linkplain BoardUtils#STALEMATE
	 * STALEMATE}.
	 *
	 * @return The movement flags.
	 */
	private int extractExtraMovementFlags() {
		int flags = 0;
		flags |= extractKingQueenRookBishopExtraFlags(flags);
		flags |= extractKnightExtraFlags(flags);
		flags |= extractPawnExtraFlags(flags);
		if (BoardUtils.isDoubleCheck(flags)) {
			flags &= ~BoardUtils.DISCOVERY_CHECK;
		}
		if (getMovements(false).isEmpty()) {
			if (BoardUtils.isCheck(flags)) {
				flags |= BoardUtils.CHECKMATE;
			} else {
				flags |= BoardUtils.STALEMATE;
			}
		}
		return flags;
	}
	
	/**
	 * This method may be called after a movement in order to extract the following movement flags:
	 * {@linkplain BoardUtils#CHECK CHECK}, {@linkplain BoardUtils#DOUBLE_CHECK DOUBLE_CHECK},
	 * {@linkplain BoardUtils#DISCOVERY_CHECK DISCOVERY_CHECK},
	 * {@linkplain BoardUtils#CHECKMATE CHECKMATE} and {@linkplain BoardUtils#STALEMATE
	 * STALEMATE}.
	 *
	 * <p>
	 * Only the pieces {@linkplain PieceType#KING KING}, {@linkplain PieceType#QUEEN
	 * QUEEN}, {@linkplain PieceType#ROOK ROOK} and {@linkplain PieceType#BISHOP BISHOP} are
	 * considered in this method.
	 *
	 * @param flags The current movement flags.
	 *
	 * @return The same <code>flags</code> passed as parameter updated with extracted movement
	 *         flags.
	 */
	private int extractKingQueenRookBishopExtraFlags(int flags) {
		Position position = movementHistory.get(movementHistory.size() - 1).movementOrigin.getPosition();
		Color attackerColor = movementHistory.get(movementHistory.size() - 1).movementOrigin.getPiece().getColor();
		Position kingPosition = gameInfo.getKingPosition(attackerColor.getOpposite());
		int maxMoveLength = getMaxPieceMovementLength(PieceType.QUEEN);
		for (int t = 0; t < queenMoveTemplate.size(); t++) {
			boolean discovery = false;
			for (int mLength = 1; mLength <= maxMoveLength; mLength++) {
				Direction directionAdjuster = queenMoveTemplate.get(t);
				int targetRow = kingPosition.getRow() + mLength * directionAdjuster.rowAdjuster;
				int targetColumn = kingPosition.getColumn() + mLength * directionAdjuster.columnAdjuster;
				if (isInsideBoard(targetRow, targetColumn)) {
					Position targetPosition = Position.of(targetRow, targetColumn);
					if (!discovery && position.equals(targetPosition)) {
						discovery = true;
					}
					if (isNotEmpty(targetPosition)) {
						Piece targetPiece = getPiece(targetPosition);
						if (targetPiece.getColor().equals(attackerColor)
								&& (targetPiece.isQueen()
										|| targetPiece.isRook() && (kingPosition.getRow() == targetRow
												|| kingPosition.getColumn() == targetColumn)
										|| targetPiece.isBishop() && kingPosition.getRow() != targetRow
												&& kingPosition.getColumn() != targetColumn)) {
							if (discovery) {
								flags |= BoardUtils.DISCOVERY_CHECK;
							}
							if (BoardUtils.isCheck(flags)) {
								flags |= BoardUtils.DOUBLE_CHECK;
							}
							flags |= BoardUtils.CHECK;
						}
						break;
					}
				}
			}
		}
		return flags;
	}
	
	/**
	 * Extract extra movement flags from the current board state considering attacks of a knight
	 * piece.
	 *
	 * @param flags The current movement flags.
	 *
	 * @return The same <code>flags</code> passed as parameter updated with extracted movement
	 *         flags.
	 */
	private int extractKnightExtraFlags(int flags) {
		Position position = movementHistory.get(movementHistory.size() - 1).movementOrigin.getPosition();
		Color attackerColor = movementHistory.get(movementHistory.size() - 1).movementOrigin.getPiece().getColor();
		Position kingPosition = gameInfo.getKingPosition(attackerColor.getOpposite());
		boolean discovery = false;
		for (int t = 0; t < knightMoveTemplate.size(); t++) {
			int targetRow = kingPosition.getRow() + knightMoveTemplate.get(t).rowAdjuster;
			int targetColumn = kingPosition.getColumn() + knightMoveTemplate.get(t).columnAdjuster;
			if (isInsideBoard(targetRow, targetColumn)) {
				Position targetPosition = Position.of(targetRow, targetColumn);
				if (!discovery && position.equals(targetPosition)) {
					discovery = true;
				}
				if (isNotEmpty(targetPosition)) {
					Piece piece = getPiece(kingPosition);
					if (piece.isKnight() && piece.getColor().equals(attackerColor)) {
						if (discovery) {
							flags |= BoardUtils.DISCOVERY_CHECK;
						}
						if (BoardUtils.isCheck(flags)) {
							flags |= BoardUtils.DOUBLE_CHECK;
							flags &= ~BoardUtils.DISCOVERY_CHECK;
						}
						flags |= BoardUtils.CHECK;
					}
				}
			}
		}
		return flags;
	}
	
	/**
	 * Extract extra movement flags from the current board state considering attacks of pawn piece.
	 *
	 * @param flags The current movement flags.
	 *
	 * @return The same <code>flags</code> passed as parameter updated with extracted movement
	 *         flags.
	 */
	private int extractPawnExtraFlags(int flags) {
		Color attackerColor = movementHistory.get(movementHistory.size() - 1).movementOrigin.getPiece().getColor();
		Position kingPosition = gameInfo.getKingPosition(attackerColor.getOpposite());
		int direction = attackerColor.isWhite()
				? -1
				: 1;
		for (int i = -1; i <= 1; i += 2) {
			int targetRow = kingPosition.getRow() - direction;
			int targetColumn = kingPosition.getColumn() + i;
			if (isInsideBoard(targetRow, targetColumn)) {
				Position targetPosition = Position.of(targetRow, targetColumn);
				if (isNotEmpty(targetPosition)) {
					Piece piece = getPiece(targetPosition);
					if (piece.isPawn() && piece.getColor() == attackerColor) {
						if (BoardUtils.isCheck(flags)) {
							flags |= BoardUtils.DOUBLE_CHECK;
						}
						flags |= BoardUtils.CHECK;
					}
				}
			}
		}
		return flags;
	}
	
	/**
	 * Evaluates if, by moving the a piece from given origin position to the informed target
	 * position, the king of same color will be in check.
	 *
	 * <p>
	 * This method is not prepared for test castling movements. The verification if king is in check
	 * during castling is made in the proper method {@link #getCastlingTargets(Position, boolean)}.
	 *
	 * @param originPosition The being moved piece's origin position.
	 * @param targetPosition The being moved piece's target position.
	 * @param capturedPawnEnPassantPosition If the movement is a capture <i>en passant</i>, this
	 *        position indiactes the location of the captuerd pawn.
	 *
	 * @return A value <code>true</code> if the king piece will be in check if the movement is
	 *         really made, or <code>false</code> otherwise.
	 *
	 * @see #getCastlingTargets(Position, boolean)
	 */
	private boolean isKingInCheckWithMove(
			Position originPosition,
			Position targetPosition,
			Position capturedPawnEnPassantPosition
	) {
		Piece originPiece = getPiece(originPosition);
		if (!gameInfo.isKingPresent(originPiece.getColor())) {
			return false;
		}
		Piece temp = getPieceOrNull(targetPosition);
		setPiece(getPiece(originPosition), targetPosition);
		setPiece(null, originPosition);
		Piece capturedEnPassantPawn = null;
		if (capturedPawnEnPassantPosition != null) {
			capturedEnPassantPawn = getPiece(capturedPawnEnPassantPosition);
			setPiece(null, capturedPawnEnPassantPosition);
		}
		Position kingPosition = getPiece(targetPosition).isKing()
				? targetPosition
				: gameInfo.getKingPosition(originPiece.getColor());
		boolean underAttack = isUnderAttack(kingPosition, originPiece.getColor().getOpposite());
		setPiece(getPiece(targetPosition), originPosition);
		setPiece(temp, targetPosition);
		if (capturedPawnEnPassantPosition != null) {
			setPiece(capturedEnPassantPawn, capturedPawnEnPassantPosition);
		}
		return underAttack;
	}
	
	/**
	 * Generated the castling movement targets for the king piece located in the given position.
	 *
	 * @param kingPosition The king position.
	 * @param extractExtraMovementFlags A flag to indicate if extra movement flags must be extract
	 *        for found castling movements.
	 *
	 * @return The list of movement targets for the castlings.
	 */
	private List<MovementTarget> getCastlingTargets(Position kingPosition, boolean extractExtraMovementFlags) {
		List<Position> rookPositions = new ArrayList<>(2);
		Piece kingPiece = grid[kingPosition.getRow()][kingPosition.getColumn()];
		if (gameInfo.isKingSideCastlingAvailable(kingPiece.getColor())) {
			rookPositions.add(getKingSideRookPosition(kingPiece.getColor()));
		}
		if (gameInfo.isQueenSideCastlingAvailable(kingPiece.getColor())) {
			rookPositions.add(getQueenSideRookPosition(kingPiece.getColor()));
		}
		
		if (rookPositions.isEmpty()) {
			return Collections.emptyList();
		}
		List<MovementTarget> targets = new ArrayList<>();
		for (Position rookPosition : rookPositions) {
			int direction = Integer.signum(rookPosition.getColumn() - kingPosition.getColumn());
			int tLength = 0;
			boolean invalidCastling = false;
			int distance = Math.abs(rookPosition.getColumn() - kingPosition.getColumn());
			while (!invalidCastling && tLength < distance) {
				int targetColumn = kingPosition.getColumn() + tLength * direction;
				if (tLength <= 2 && !getAttackers(
					Position.of(kingPosition.getRow(), targetColumn),
					kingPiece.getColor().getOpposite()
				).isEmpty()) {
					invalidCastling = true;
				}
				if (tLength > 0 && isNotEmpty(Position.of(kingPosition.getRow(), targetColumn))) {
					invalidCastling = true;
				}
				tLength++;
			}
			if (!invalidCastling) {
				int targetKingColumn = kingPosition.getColumn() + 2 * direction;
				Position targetPosition = Position.of(kingPosition.getRow(), targetKingColumn);
				int movementFlags = BoardUtils.CASTLING;
				if (extractExtraMovementFlags) {
					movementFlags |= extractExtraMovementFlags(
						kingPiece,
						kingPosition,
						kingPiece,
						targetPosition,
						movementFlags
					);
				}
				targets.add(
					new MovementTarget(
						kingPiece,
						targetPosition,
						movementFlags
					)
				);
			}
		}
		return targets;
	}
	
	/**
	 * Retrieves the position king's side rook piece. This method does not check if the rook piece
	 * is placed in the square.
	 *
	 * @param color The color of rook.
	 *
	 * @return The position.
	 */
	private Position getKingSideRookPosition(Color color) {
		return color.isWhite()
				? Position.of(WHITE_ROOK_INITIAL_ROW, KING_SIDE_ROOK_INITIAL_COLUMN)
				: Position.of(BLACK_ROOK_INITIAL_ROW, KING_SIDE_ROOK_INITIAL_COLUMN);
	}
	
	/**
	 * Retrieves the position queen's side rook piece. This method does not check if the rook piece
	 * is placed in the square.
	 *
	 * @param color The color of rook.
	 *
	 * @return The position.
	 */
	private Position getQueenSideRookPosition(Color color) {
		return color.isWhite()
				? Position.of(WHITE_ROOK_INITIAL_ROW, QUEEN_SIDE_ROOK_INITIAL_COLUMN)
				: Position.of(BLACK_ROOK_INITIAL_ROW, QUEEN_SIDE_ROOK_INITIAL_COLUMN);
	}
	
	/**
	 * Evaluates if the given position is inside the board bounds.
	 *
	 * @param row The position row number.
	 * @param column The position column number.
	 *
	 * @return A value <code>true</code> if position is inside the board bounds, or
	 *         <code>false</code> otherwise.
	 */
	private static boolean isInsideBoard(int row, int column) {
		return row >= 0 && row < BoardUtils.BOARD_SIZE
				&& column >= 0 && column < BoardUtils.BOARD_SIZE;
	}
	
	/**
	 * Retrieves the maximum movement length for a piece movement.
	 *
	 * @param type The piece type.
	 *
	 * @return The maximum movement length.
	 */
	private static int getMaxPieceMovementLength(PieceType type) {
		if (type == PieceType.KING) {
			return 1;
		}
		if (type == PieceType.QUEEN) {
			return BoardUtils.BOARD_SIZE - 1;
		}
		if (type == PieceType.ROOK) {
			return BoardUtils.BOARD_SIZE - 1;
		}
		if (type == PieceType.BISHOP) {
			return BoardUtils.BOARD_SIZE - 1;
		}
		if (type == PieceType.KNIGHT) {
			return 1;
		}
		throw new ChessError(String.format("Unexpected piece type: %s", type));
	}
	
	/**
	 * Generates the available movements for the pawn piece located in the given position.
	 *
	 * @param pawnPosition The position of the pawn.
	 * @param extractExtraMovementFlags A flag to indicate the extra movement flags should be
	 *        calculated for the generated pawn movements.
	 *
	 * @return The movement object.
	 */
	private PieceMovement getPawnMovements(Position pawnPosition, boolean extractExtraMovementFlags) {
		List<MovementTarget> targets = new ArrayList<>();
		MovementMetadata.Builder pieceMovementMetaBuilder = MovementMetadata.builder();
		Piece piece = getPiece(pawnPosition);
		MovementOrigin movementOrigin = new MovementOrigin(
			getPiece(pawnPosition),
			pawnPosition
		);
		List<Direction> movementTemplate = piece.isWhite()
				? whitePawnMoveTemplate
				: blackPawnMoveTemplate;
		for (Direction directionAjduster : movementTemplate) {
			int targetRow = pawnPosition.getRow() + directionAjduster.rowAdjuster;
			int targetColumn = pawnPosition.getColumn() + directionAjduster.columnAdjuster;
			if (isInsideBoard(targetRow, targetColumn)) {
				Position targetPosition = Position.of(targetRow, targetColumn);
				boolean isMovementForward = isValidPawnMovementForward(pawnPosition, targetPosition);
				boolean isCapture = isValidPawnCaptureMovement(pawnPosition, targetPosition);
				boolean isEnPassant = isCapture && isEmpty(Position.of(targetRow, targetColumn));
				if ((isMovementForward || isCapture)
						&& !isKingInCheckWithMove(
							pawnPosition,
							targetPosition,
							isEnPassant ? Position.of(pawnPosition.getRow(), targetColumn) : null
						)) {
					boolean isPawnPromotion = targetRow == getPawnPromotionRow(
						movementOrigin.getPiece().getColor()
					);
					List<PieceType> targetPieces = isPawnPromotion
							? pawnPromotionReplacements
							: pawnNonPromotionReplacements;
					for (PieceType pieceType : targetPieces) {
						int movementFlags = 0;
						pieceMovementMetaBuilder.incrementTotalMovements();
						if (isCapture) {
							movementFlags |= BoardUtils.CAPTURE;
							pieceMovementMetaBuilder.incrementCaptureCount();
						}
						if (isEnPassant) {
							movementFlags |= BoardUtils.EN_PASSANT;
							pieceMovementMetaBuilder.incrementEnPassantCount();
						}
						if (isPawnPromotion) {
							movementFlags |= BoardUtils.PROMOTION;
							pieceMovementMetaBuilder.incrementPromotionCount();
						}
						Piece targetPiece = Piece.get(
							pieceType,
							movementOrigin.getPiece().getColor()
						);
						if (extractExtraMovementFlags) {
							movementFlags |= extractExtraMovementFlags(
								movementOrigin.getPiece(),
								movementOrigin.getPosition(),
								targetPiece,
								targetPosition,
								movementFlags
							);
							updatePieceMovementMetaWithExtraFlags(
								pieceMovementMetaBuilder,
								movementFlags
							);
						}
						targets.add(
							new MovementTarget(
								targetPiece,
								targetPosition,
								movementFlags
							)
						);
					}
				}
			}
		}
		return new PieceMovement(
			movementOrigin,
			targets,
			pieceMovementMetaBuilder.build()
		);
	}
	
	/**
	 * Retrieves the row number for promotion of pawn pieces, for a specific color.
	 *
	 * @param color The color of the piece.
	 *
	 * @return The row number where the pawn is promoted.
	 */
	private int getPawnPromotionRow(Color color) {
		return color.isWhite()
				? WHITE_PAWN_PROMOTION_ROW
				: BLACK_PAWN_PROMOTION_ROW;
	}
	
	/**
	 * Evaluates if the movement of pawn is valid whether interpreted as a capturing movement (not a
	 * forwarding movement). This method make use of the {@link #gameInfo} in order to know if a
	 * <i>en passant</i> movement is possible.
	 *
	 * @param originPosition The origin position of the pawn.
	 * @param targetPosition The target position of the pawn.
	 *
	 * @return A value <code>true</code> if the movement is valid, as interpreted as capturing
	 *         movement. Return <code>false</code> if not.
	 */
	private boolean isValidPawnCaptureMovement(Position originPosition, Position targetPosition) {
		if (originPosition.getColumn() == targetPosition.getColumn()) {
			return false;
		}
		
		int moveValue = getMoveValue(originPosition, targetPosition);
		if (moveValue < 0) {
			return true;
		}
		return moveValue == 0
				&& gameInfo.enPassantTargetSquare != null
				&& gameInfo.enPassantTargetSquare.equals(targetPosition);
	}
	
	/**
	 * Evaluates if the movement of pawn is valid whether interpreted as a forwarding movement (not
	 * capture).
	 *
	 * @param originPosition The origin position of the pawn.
	 * @param targetPosition The target position of the pawn.
	 *
	 * @return A value <code>true</code> if the movement is valid, as interpreted as pawn forwarding
	 *         movement. Return <code>false</code> if not.
	 */
	private boolean isValidPawnMovementForward(Position originPosition, Position targetPosition) {
		if (isNotEmpty(targetPosition)) {
			return false;
		}
		if (originPosition.getColumn() != targetPosition.getColumn()) {
			return false;
		}
		if (Math.abs(originPosition.getRow() - targetPosition.getRow()) == 2) {
			int pawnInitialRow = getPawnInitialRow(
				getPiece(originPosition).getColor()
			);
			if (pawnInitialRow != originPosition.getRow()) {
				return false;
			}
			int midRow = (originPosition.getRow() + targetPosition.getRow()) / 2;
			if (isNotEmpty(Position.of(midRow, originPosition.getColumn()))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Retrieves the initial row number of a pawn piece.
	 *
	 * @param color The color of the pawn piece.
	 *
	 * @return The initial row number of pawn piece.
	 */
	private int getPawnInitialRow(Color color) {
		return color.isWhite()
				? WHITE_PAWN_INITIAL_ROW
				: BLACK_PAWN_INITIAL_ROW;
	}
	
	/**
	 * Outputs a text based board drawing like as follow:
	 *
	 * <code>
	 * <pre>
	 * ┌───┬───┬───┬───┬───┬───┬───┬───┐
	 * │ r │ n │ b │ q │ k │ b │ n │ r │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │ p │ p │ p │ p │ p │ p │ p │ p │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │   │   │   │   │   │   │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │   │   │   │   │   │   │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │   │   │   │   │   │   │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │   │   │   │   │   │   │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │ P │ P │ P │ P │ P │ P │ P │ P │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │ R │ N │ B │ Q │ K │ B │ N │ R │
	 * └───┴───┴───┴───┴───┴───┴───┴───┘
	 * </pre>
	 * </code>
	 *
	 * @return The board drawing.
	 */
	@Override
	public String toString() {
		return toString(false);
	}
	
	/**
	 * Outputs a text based board drawing like as follow, given the possibility for drawing the
	 * board coordinates:
	 *
	 * <p>
	 * With coordinates...
	 *
	 * <code>
	 * <pre>
	 * ┌───┬───┬───┬───┬───┬───┬───┬───┐
	 * │ r │ n │ b │ q │ k │ b │ n │ r │ 0 [8]
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │ p │ p │ p │ p │ p │ p │ p │ p │ 1 [7]
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │   │   │   │   │   │   │ 2 [6]
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │   │   │   │   │   │   │ 3 [5]
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │   │   │   │   │   │   │ 4 [4]
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │   │   │   │   │   │   │ 5 [3]
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │ P │ P │ P │ P │ P │ P │ P │ P │ 6 [2]
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │ R │ N │ B │ Q │ K │ B │ N │ R │ 7 [1]
	 * └───┴───┴───┴───┴───┴───┴───┴───┘
	 * 0   1   2   3   4   5   6   7
	 * [A] [B] [C] [D] [E] [F] [G] [H]
	 * </pre>
	 * </code>
	 *
	 * ... and without board coordinates...
	 *
	 * <code>
	 * <pre>
	 * ┌───┬───┬───┬───┬───┬───┬───┬───┐
	 * │ r │ n │ b │ q │ k │ b │ n │ r │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │ p │ p │ p │ p │ p │ p │ p │ p │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │   │   │   │   │   │   │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │   │   │   │   │   │   │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │   │   │   │   │   │   │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │   │   │   │   │   │   │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │ P │ P │ P │ P │ P │ P │ P │ P │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │ R │ N │ B │ Q │ K │ B │ N │ R │
	 * └───┴───┴───┴───┴───┴───┴───┴───┘
	 * </pre>
	 * </code>
	 *
	 * @param printCoordinates If the board coordinates should be printed.
	 *
	 * @return The board drawing.
	 */
	public String toString(boolean printCoordinates) {
		StringBuilder builder = new StringBuilder();
		builder.append("┌───┬───┬───┬───┬───┬───┬───┬───┐").append(NEWLINE);
		for (int row = 0; row < BoardUtils.BOARD_SIZE; row++) {
			for (int column = 0; column < BoardUtils.BOARD_SIZE; column++) {
				Position position = Position.of(row, column);
				builder.append("│");
				if (isEmpty(position)) {
					builder.append("   ");
				} else {
					builder
						.append(' ')
						.append(getPiece(position).getLetterSymbol())
						.append(' ');
				}
			}
			builder.append("│");
			if (printCoordinates) {
				builder.append(" ").append(row).append(' ').append('[').append(Position.toRank(row)).append(']');
			}
			builder.append(NEWLINE);
			if (row == BoardUtils.BOARD_SIZE - 1) {
				builder.append("└───┴───┴───┴───┴───┴───┴───┴───┘").append(NEWLINE);
				if (printCoordinates) {
					builder.append("  0   1   2   3   4   5   6   7  ").append(NEWLINE);
					builder.append(" [A] [B] [C] [D] [E] [F] [G] [H]  ").append(NEWLINE);
				}
			} else {
				builder.append("├───┼───┼───┼───┼───┼───┼───┼───┤").append(NEWLINE);
			}
		}
		return builder.toString();
	}
	
	/**
	 * Outputs a text based board drawing like as follow:
	 *
	 * <code>
	 * <pre>
	 * r n b q k b n r
	 * p p p p p p p p
	 * . . . . . . . .
	 * . . . . . . . .
	 * . . . . . . . .
	 * . . . . . . . .
	 * P P P P P P P P
	 * R N B Q K B N R
	 * </pre>
	 * </code>
	 *
	 * @return The board drawing.
	 */
	public String toString2() {
		StringBuilder builder = new StringBuilder();
		for (int row = 0; row < BoardUtils.BOARD_SIZE; row++) {
			for (int column = 0; column < BoardUtils.BOARD_SIZE; column++) {
				Position position = Position.of(row, column);
				if (column > 0) {
					builder.append(' ');
				}
				if (isEmpty(position)) {
					builder.append('.');
				} else {
					builder.append(getPiece(position).getLetterSymbol());
				}
			}
			builder.append(NEWLINE);
		}
		return builder.toString();
	}
	
	/**
	 * A direction adjuster is a row adjuster and column adjuster pair that helps the piece movement
	 * generation to generate the target positions.
	 *
	 * <p>
	 * The movement generator related methods use a direction adjuster in order to transform a given
	 * position into a target position. The pieces like king, queen, rook, bishop and knight have a
	 * radial movement.
	 *
	 * <pre>
	 * targetRow    = initialRow    + moveLength * rowAdjuster;
	 * targetColumn = initialColumn + moveLength * columnAdjuster;
	 * </pre>
	 *
	 * @author Welyab Paula
	 */
	private static class Direction {
		
		/**
		 * The row adjuster value.
		 */
		final int rowAdjuster;
		
		/**
		 * The column adjuster value.
		 */
		final int columnAdjuster;
		
		/**
		 * Creates a direction adjuster instance using given values.
		 *
		 * @param rowAdjuster The row adjuster value.
		 * @param columnAdjsuter The column adjuster value.
		 */
		Direction(int rowAdjuster, int columnAdjsuter) {
			this.rowAdjuster = rowAdjuster;
			columnAdjuster = columnAdjsuter;
		}
	}
	
	/**
	 * This class keeps information about a movement in order to allow board to undo that movement.
	 *
	 * @author Welyab Paula
	 */
	private static class MovementLog implements Copiable<MovementLog> {
		
		/**
		 * The movement origin.
		 */
		final MovementOrigin movementOrigin;
		
		/**
		 * The movement target.
		 */
		final MovementTarget movementTarget;
		
		/**
		 * The captured piece, if any. This field may be <code>null</code> if the movement does not
		 * represent a capture.
		 */
		final Piece capturedPiece;
		
		/**
		 * The game information before the movement be done.
		 */
		final GameInfo gameInfo;
		
		@SuppressWarnings("javadoc")
		MovementLog(
				MovementOrigin movementOrigin,
				MovementTarget movementTarget,
				Piece capturedPiece,
				GameInfo gameInfo
		) {
			this.movementOrigin = movementOrigin;
			this.movementTarget = movementTarget;
			this.capturedPiece = capturedPiece;
			this.gameInfo = gameInfo;
		}
		
		@Override
		public MovementLog copy() {
			return new MovementLog(
				movementOrigin,
				movementTarget,
				capturedPiece,
				gameInfo.copy()
			);
		}
	}
	
	/**
	 * The <code>GameInfo</code> class holds some informations during the game playing, as castling
	 * flags and <i>en passant</i> availability, etc.
	 *
	 * @author Welyab Paula
	 */
	private static class GameInfo implements Copiable<GameInfo> {
		
		/**
		 * The move side adjuster.
		 */
		int moveSideAdjuster;
		
		/**
		 * A half movement counter to be used internally by the game engine.
		 */
		int moveCounter = 0;
		
		/**
		 * The full movement counter. This value starts in one (initial game position) and is
		 * incremented after each black piece movement.
		 */
		int fullMoveCounter = 1;
		
		/**
		 * The half move counter is a auxiliary information to help help the engine detect a draw by
		 * the "Fifty-move Rule". This counter is set to zero during pawn movements or piece
		 * captures, otherwise it is incremented. When this counter is equals or equal to
		 * <code>100</code> a draw may be claimed by the player.
		 *
		 * <p>
		 * This counter is updated mainly in the {@link Board#move(MovementOrigin, MovementTarget)}
		 * method.
		 *
		 * @see Board#move(MovementOrigin, MovementTarget)
		 */
		int halfMoveCounter = 0;
		
		/**
		 * This field is used by movement generator in order to let pawn capture other pawns using
		 * <i>en passant</i> movement.
		 *
		 * <i>
		 * This field is updated in the {@link Board#move(MovementOrigin, MovementTarget)} method
		 * during each double square pawn movement, with the target position of capture for the next
		 * pawn movement. In other cases, this field is set to <code>null</code>.
		 *
		 * @see Board#move(MovementOrigin, MovementTarget)
		 * @see Board#getPawnMovements(Position, boolean)
		 */
		Position enPassantTargetSquare = null;
		
		/**
		 * The current position of the black king piece. This field is updated for each king
		 * movement.
		 */
		Position blackKingPosition;
		
		/**
		 * The current position of the white king piece. This field is updated for each king
		 * movement.
		 */
		Position whiteKingPosition;
		
		/**
		 * Castling flags indicate which castling movements are available for the pieces white/black
		 * for the king side or queen side.
		 */
		int castlingFlags;
		
		@SuppressWarnings("javadoc")
		GameInfo(BoardConfig config) {
			this(
				config.getSideToMove().isWhite() ? 0 : 1,
				0,
				config.getFullMoveCounter(),
				config.getHalfMoveCounter(),
				BoardUtils.toCastlingFlags(
					config.isBlackKingSideCastlingAvailable(),
					config.isBlackQueenSideCastlingAvailable(),
					config.isWhiteKingSideCastlingAvailable(),
					config.isWhiteQueenSideCastlingAvailable()
				),
				config.getEnPassantTargetSquare(),
				null,
				null
			);
		}
		
		@SuppressWarnings("javadoc")
		GameInfo(
				int moveSideAdjuster,
				int moveCounter,
				int fullMoveCounter,
				int halfMoveCounter,
				int castlingFlags,
				Position enPassantTargetSquare,
				Position blackKingPosition,
				Position whiteKingPosition
		) {
			this.moveSideAdjuster = moveSideAdjuster;
			this.moveCounter = moveCounter;
			this.fullMoveCounter = fullMoveCounter;
			this.halfMoveCounter = halfMoveCounter;
			this.castlingFlags = castlingFlags;
			this.enPassantTargetSquare = enPassantTargetSquare;
			this.blackKingPosition = blackKingPosition;
			this.whiteKingPosition = whiteKingPosition;
		}
		
		@Override
		public GameInfo copy() {
			return new GameInfo(
				moveSideAdjuster,
				moveCounter,
				fullMoveCounter,
				halfMoveCounter,
				castlingFlags,
				enPassantTargetSquare,
				blackKingPosition,
				whiteKingPosition
			);
		}
		
		/**
		 * Retrieves the color side that should make the next movement.
		 *
		 * @return The color black or white.
		 */
		Color getActiveColor() {
			return (moveCounter + moveSideAdjuster) % 2 == 0 ? Color.WHITE : Color.BLACK;
		}
		
		/**
		 * Set the position of the king piece, according to the given color.
		 *
		 * @param color The king color.
		 * @param position The position.
		 *
		 * @see #getKingPosition(Color)
		 * @see #isKingPresent(Color)
		 */
		void setKingPosition(Color color, Position position) {
			if (color.isWhite()) {
				whiteKingPosition = position;
			} else {
				blackKingPosition = position;
			}
		}
		
		/**
		 * Retrieves the position of the king piece, according to the given color.
		 *
		 * @param color The color of the required king.
		 *
		 * @return The position of the king piece, or <code>null</code> if there is no king of the
		 *         specific color in the board.
		 *
		 * @see #setKingPosition(Color, Position)
		 * @see #isKingPresent(Color)
		 */
		Position getKingPosition(Color color) {
			if (!isKingPresent(color)) {
				throw new KingNotPresentException(color);
			}
			return color.isWhite()
					? whiteKingPosition
					: blackKingPosition;
		}
		
		/**
		 * Evaluates if the king piece of the specific color is present in the board.
		 *
		 * @param color The color of the required king piece.
		 *
		 * @return A value <code>true</code> if the king of the specific color is present, or
		 *         <code>false</code> otherwise.
		 *
		 * @see #setKingPosition(Color, Position)
		 * @see #isKingPresent(Color)
		 */
		boolean isKingPresent(Color color) {
			return color.isWhite()
					? whiteKingPosition != null
					: blackKingPosition != null;
		}
		
		/**
		 * Retrieves the full movement counter.
		 *
		 * @return The full movement counter.
		 */
		int getFullMoveCounter() {
			return fullMoveCounter;
		}
		
		/**
		 * Evaluates if the castling movement for the queen side is available for the pieces of the
		 * specific color.
		 *
		 * @param color The color.
		 *
		 * @return A value <code>true</code> if the castling is available, or <code>false</code>
		 *         otherwise.
		 */
		boolean isQueenSideCastlingAvailable(Color color) {
			return color.isWhite()
					? BoardUtils.isWhiteQueenSideCastling(castlingFlags)
					: BoardUtils.isBlackQueenSideCastling(castlingFlags);
		}
		
		/**
		 * Evaluates if the castling movement for the king side is available for the pieces of the
		 * specific color.
		 *
		 * @param color The color.
		 *
		 * @return A value <code>true</code> if the castling is available, or <code>false</code>
		 *         otherwise.
		 */
		boolean isKingSideCastlingAvailable(Color color) {
			return color.isWhite()
					? BoardUtils.isWhiteKingSideCastling(castlingFlags)
					: BoardUtils.isBlackKingSideCastling(castlingFlags);
		}
		
		/**
		 * Invalidate the castling movement for the pieces of the specific color.
		 *
		 * @param color The color of pieces that will have the castling movement invalidated.
		 */
		void invalidateCastling(Color color) {
			invalidateKingSideCastling(color);
			invalidateQueenSideCastling(color);
		}
		
		/**
		 * Invalidate the castling movement for the queen side for the specific color.
		 *
		 * @param color The color of pieces that will have the castling movement invalidated.
		 */
		void invalidateQueenSideCastling(Color color) {
			if (color.isWhite()) {
				castlingFlags &= ~BoardUtils.WHITE_QUEEN_SIDE_CASTLING;
			} else {
				castlingFlags &= ~BoardUtils.BLACK_QUEEN_SIDE_CASTLING;
			}
		}
		
		/**
		 * Invalidate the castling movement for the king side for the specific color.
		 *
		 * @param color The color of pieces that will have the castling movement invalidated.
		 */
		void invalidateKingSideCastling(Color color) {
			if (color.isWhite()) {
				castlingFlags &= ~BoardUtils.WHITE_KING_SIDE_CASTLING;
			} else {
				castlingFlags &= ~BoardUtils.BLACK_KING_SIDE_CASTLING;
			}
		}
		
		@Override
		public String toString() {
			StringBuilder castlingFlagsStr = new StringBuilder();
			if (BoardUtils.isWhiteKingSideCastling(castlingFlags)) {
				castlingFlagsStr.append(Piece.WHITE_KING.getLetterSymbol());
			}
			if (BoardUtils.isWhiteQueenSideCastling(castlingFlags)) {
				castlingFlagsStr.append(Piece.WHITE_QUEEN.getLetterSymbol());
			}
			if (BoardUtils.isBlackKingSideCastling(castlingFlags)) {
				castlingFlagsStr.append(Piece.BLACK_KING.getLetterSymbol());
			}
			if (BoardUtils.isBlackQueenSideCastling(castlingFlags)) {
				castlingFlagsStr.append(Piece.BLACK_QUEEN.getLetterSymbol());
			}
			if (castlingFlagsStr.length() == 0) {
				castlingFlagsStr.append('-');
			}
			return new StringBuilder()
				.append("Next: ")
				.append(getActiveColor())
				.append(", ")
				.append("Full moves: ")
				.append(fullMoveCounter)
				.append(", ")
				.append("Half moves: ")
				.append(halfMoveCounter)
				.append(", ")
				.append("En passant: ")
				.append(enPassantTargetSquare != null)
				.append(", ")
				.append("Castling: ")
				.append(castlingFlagsStr)
				.toString();
		}
	}
}
