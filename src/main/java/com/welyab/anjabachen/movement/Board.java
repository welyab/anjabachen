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
package com.welyab.anjabachen.movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.ObjIntConsumer;

import com.welyab.anjabachen.ChessException;
import com.welyab.anjabachen.movement.MovementMetadata.Builder;
import com.welyab.anjabachen.movement.fen.FenParser;
import com.welyab.anjabachen.movement.fen.FenPositionInfo;
import com.welyab.anjabachen.movement.perft.PerftCalculator;
import com.welyab.anjabachen.movement.perft.PerftResult;

/**
 * The <code>Board</code> class is the main component for movement generation in
 * <strong>ANJABACHEN</strong>. It keeps internally a piece disposition state, also castling
 * ability flags, <i>en passant</i> captures, etc.
 * 
 * <p>
 * The <code>Board</code> class can manage movement generation also to the chess variant
 * <strong><i><a href="https://en.wikipedia.org/wiki/Chess960">Fischer Randon
 * Chess</a></i></strong>.
 * 
 * <p>
 * A simples usage of the <code>Board</code> class:
 * 
 * <pre>
 * var board = new Board();
 * var list = board.getMovements();
 * list.forEach(System.out::println);
 * </pre>
 * 
 * The code creates a board with pieces in the initial position, then retrieves the list of
 * available
 * movements for white pieces and finally print these movements.
 * 
 * <p>
 * Another exemple:
 * 
 * <pre>
 * var board = new Board();
 * var move = board.getRandomMove();
 * board.move(move);
 * var list = board.getMovements();
 * list.forEach(System.out::println);
 * </pre>
 * 
 * The above code creates a board and retrieve from it a random move for white pieces, then submits
 * that move to the board. The following call to the <code>getMovements</code> will retrieve the
 * available movements for the black pieces.
 * 
 * @author Welyab Paula
 */
@SuppressWarnings(
	{
		"squid:S1448"
	}
)
public final class Board implements Copyable<Board> {
	
	@SuppressWarnings("javadoc")
	private static final String NEWLINE = String.format("%n");
	
	@SuppressWarnings("javadoc")
	private static final List<Byte> PIECE_TYPES = List.of(
		MovementUtil.KING,
		MovementUtil.QUEEN,
		MovementUtil.ROOK,
		MovementUtil.BISHOP,
		MovementUtil.KNIGHT,
		MovementUtil.PAWN
	);
	
	@SuppressWarnings("javadoc")
	private static final List<Byte> PAWN_REPLACEMENT_PIECE_TYPES = List.of(
		MovementUtil.QUEEN,
		MovementUtil.ROOK,
		MovementUtil.BISHOP,
		MovementUtil.KNIGHT
	);
	
	@SuppressWarnings("javadoc")
	private static final List<Byte> PAWN_NO_REPLACEMENT_PIECE_TYPES = List.of(
		MovementUtil.PAWN
	);
	
	/** Directions for the king piece (white or black). */
	private static final Direction[] ALL_DIRECTIONS = {
		new Direction(-1, -1),
		new Direction(-1, +0),
		new Direction(-1, +1),
		new Direction(+1, -1),
		new Direction(+1, +0),
		new Direction(+1, +1),
		new Direction(+0, +1),
		new Direction(+0, -1)
	};
	
	/** Directions for the king piece (white or black). */
	private static final Direction[] KING_DIRECTIONS = {
		new Direction(-1, -1),
		new Direction(-1, +0),
		new Direction(-1, +1),
		new Direction(+1, -1),
		new Direction(+1, +0),
		new Direction(+1, +1),
		new Direction(+0, +1),
		new Direction(+0, -1)
	};
	
	/** Directions for the queen piece (white or black). */
	private static final Direction[] QUEEN_DIRECTIONS = {
		new Direction(-1, -1),
		new Direction(-1, +0),
		new Direction(-1, +1),
		new Direction(+1, -1),
		new Direction(+1, +0),
		new Direction(+1, +1),
		new Direction(+0, +1),
		new Direction(+0, -1)
	};
	
	/** Directions for the king rook (white or black). */
	private static final Direction[] ROOK_DIRECTIONS = {
		new Direction(-1, +0),
		new Direction(+1, +0),
		new Direction(+0, +1),
		new Direction(+0, -1)
	};
	
	/** Directions for the bishop piece (white or black). */
	private static final Direction[] BISHOP_DIRECTIONS = {
		new Direction(-1, -1),
		new Direction(+1, -1),
		new Direction(-1, +1),
		new Direction(+1, +1)
	};
	
	/** Directions for the knight piece (white or black). */
	private static final Direction[] KNIGHT_DIRECTIONS = {
		new Direction(-2, -1),
		new Direction(-2, +1),
		new Direction(+2, -1),
		new Direction(+2, +1),
		new Direction(+1, -2),
		new Direction(-1, -2),
		new Direction(+1, +2),
		new Direction(-1, +2)
	};
	
	/** Directions for white pawns' capturing movement only. */
	private static final Direction[] WHITE_PAWN_CAPTURE_DIRECTIONS = {
		new Direction(-1, -1),
		new Direction(-1, +1)
	};
	
	/** Directions for black pawns' capturing movement only. */
	private static final Direction[] BLACK_PAWN_CAPTURE_DIRECTIONS = {
		new Direction(+1, -1),
		new Direction(+1, +1)
	};
	
	/** Directions for white pawns' single square movement forward (white point of view). */
	private static final Direction[] WHITE_PAWN_SINGLE_SQUARE_MOVE = {
		new Direction(-1, +0)
	};
	
	/** Directions for black pawns' single square movement forward (black point of view). */
	private static final Direction[] BLACK_PAWN_SINGLE_SQUARE_MOVE = {
		new Direction(+1, +0)
	};
	
	/** Directions for white pawns' double square movement forward (white point of view). */
	private static final Direction[] WHITE_PAWN_DOUBLE_SQUARE_MOVE = {
		new Direction(-2, +0)
	};
	
	/** Directions for black pawns' double square movement forward (black point of view). */
	private static final Direction[] BLACK_PAWN_DOUBLE_SQUARE_MOVE = {
		new Direction(+2, +0)
	};
	
	private static final Direction[] PAWN_EN_PASSANT_SIDES = {
		new Direction(+0, +1),
		new Direction(+0, +1)
	};
	
	/** The board squares. */
	private final byte[][] grid;
	
	/** The board state. */
	private final BoardState state;
	
	/** The movement log, for undo purpose, etc. */
	private final List<MovementLogEntry> movementLog;
	
	/**
	 * Creates a board with initial piece disposition.
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
		this(MovementUtil.FEN_INITIAL_POSITION);
	}
	
	/**
	 * Creates a board with given piece disposition described in the FEN string.
	 * 
	 * @param fen The FEN string.
	 */
	public Board(String fen) {
		FenParser parser = new FenParser(fen);
		grid = createGrid(parser.getLocalizedPieces());
		state = createBoardState(grid, parser.getFenPositionInfo());
		setKingsPositions(state, parser.getLocalizedPieces());
		movementLog = new ArrayList<>();
	}
	
	@SuppressWarnings("javadoc")
	private Board(List<LocalizedPiece> pieces, BoardState state) {
		grid = createGrid(pieces);
		addPiecesToGrid(grid, pieces);
		this.state = state;
		movementLog = new ArrayList<>();
	}
	
	@SuppressWarnings("javadoc")
	private static void setKingsPositions(BoardState state, List<LocalizedPiece> pieces) {
		for (int i = 0; i < pieces.size(); i++) {
			LocalizedPiece localizedPiece = pieces.get(i);
			if (MovementUtil.isKing(localizedPiece.getPieceCode())) {
				byte color = MovementUtil.getPieceColor(localizedPiece.getPieceCode());
				if (state.isKingPresent(color)) {
					throw new ChessException(
						String.format(
							"Can't create boar with two kings of same color: %c",
							MovementUtil.colorCodeToLetter(color)
						)
					);
				}
				
				state.setKingPosition(localizedPiece.getPosition(), color);
			}
		}
	}
	
	@SuppressWarnings("javadoc")
	private static void addPiecesToGrid(byte[][] grid, List<LocalizedPiece> pieces) {
		pieces.forEach((LocalizedPiece lp) -> {
			Position position = lp.getPosition();
			grid[position.getRow()][position.getColumn()] = lp.getPieceCode();
		});
	}
	
	@SuppressWarnings("javadoc")
	private static byte[][] createGrid() {
		return new byte[8][8];
	}
	
	@SuppressWarnings("javadoc")
	private static byte[][] createGrid(List<LocalizedPiece> pieces) {
		byte[][] grid = createGrid();
		addPiecesToGrid(grid, pieces);
		return grid;
	}
	
	/**
	 * Walk all squares of the board, calling the given <code>visitor</code> when the square is
	 * not empty.
	 * 
	 * @param visitor The visitor.
	 */
	private void simplePiecesWalking(ObjIntConsumer<Position> visitor) {
		for (int row = 0; row < 8; row++) {
			for (int column = 0; column < 8; column++) {
				if (grid[row][column] != 0) {
					visitor.accept(Position.of(row, column), grid[row][column]);
				}
			}
		}
	}
	
	/**
	 * Evaluate if the square indicated by given position is empty.
	 * 
	 * @param position The position.
	 * 
	 * @return A value <code>true</code> if the square is empty, or <code>false</code> otherwise.
	 */
	public boolean isEmpty(Position position) {
		return grid[position.getRow()][position.getColumn()] == MovementUtil.EMPTY;
	}
	
	/**
	 * Retrieves the piece located in the given position.
	 * 
	 * @param position The square position.
	 * 
	 * @return The piece code.
	 * 
	 * @throws EmptySquareException If the specific square is empty.
	 */
	public byte getPiece(Position position) {
		if (isEmpty(position)) {
			throw new EmptySquareException(position);
		}
		return grid[position.getRow()][position.getColumn()];
	}
	
	/**
	 * Retrieves all pieces present in the board and its respective locations.
	 * 
	 * @return The list of localized pieces.
	 */
	public List<LocalizedPiece> getLocalizedPieces() {
		List<LocalizedPiece> list = new ArrayList<>(32);
		simplePiecesWalking(
			(position, pieceCode) -> list.add(
				new LocalizedPiece(position, pieceCode)
			)
		);
		return list;
	}
	
	/**
	 * Retrieves a copy of the current board state. Changes in the returned object will not reflect
	 * in the internal board state.
	 * 
	 * @return The board state.
	 */
	public BoardState getState() {
		return state.copy();
	}
	
	public void moveRandom() {
		Movement movementRandom = getMovementRandom();
		move(movementRandom.getOrigin(), movementRandom.getTarget());
	}
	
	public void move(Position origin, Position target) {
		move(origin, target, MovementUtil.QUEEN);
	}
	
	public void move(Position origin, Position target, byte toPromotePawn) {
		Movements movements = getMovements(origin);
		MovementTarget movementTarget = null;
		for (int i = 0; i < movements.getOriginCount(); i++) {
			PieceMovements pieceMovements = movements.getPieceMovements(i);
			for (int j = 0; j < pieceMovements.getTargertsCount(); j++) {
				MovementTarget currentTarget = pieceMovements.getTarget(j);
				if (currentTarget.getPosition().equals(target)) {
					if (MovementUtil.isPromotion(currentTarget.getFlags())) {
						if (MovementUtil.getPieceType(currentTarget.getPieceCode()) == toPromotePawn) {
							movementTarget = currentTarget;
							break;
						}
					} else {
						movementTarget = currentTarget;
						break;
					}
				}
			}
			if (movementTarget != null) {
				break;
			}
		}
		
		if (movementTarget == null) {
			throw new ChessException(
				String.format(
					"The piece located in %s cant reach %s",
					origin.getNotation(),
					target.getNotation()
				)
			);
		}
		
		move(origin, movementTarget);
	}
	
	public void move(Movement movement) {
		move(movement.getOrigin(), movement.getTarget());
	}
	
	public void move(Position origin, MovementTarget movementTarget) {
		Position target = movementTarget.getPosition();
		byte originPiece = grid[origin.getRow()][origin.getColumn()];
		byte color = MovementUtil.getPieceColor(originPiece);
		byte capturedPiece = grid[target.getRow()][target.getColumn()];
		BoardState stateCopy = state.copy();
		
		// moves the piece from origin to target
		grid[target.getRow()][target.getColumn()] = movementTarget.getPieceCode();
		grid[origin.getRow()][origin.getColumn()] = MovementUtil.EMPTY;
		
		// remove the captured pawn if the movement is a en passant
		Position epTarget = state.getEnPassantTargetSquare();
		if (MovementUtil.isEnPassant(movementTarget.getFlags())) {
			capturedPiece = grid[origin.getRow()][target.getColumn()];
			grid[origin.getRow()][epTarget.getColumn()] = MovementUtil.EMPTY;
		}
		
		// update the king position cache
		if (MovementUtil.isKing(originPiece)) {
			state.setKingPosition(target, color);
		}
		
		// moves the rook if the movement is a castling
		if (MovementUtil.isCastling(movementTarget.getFlags())) {
			int rookOriginCol = MovementUtil.getCastlingRookOriginColumn(target.getColumn());
			int rookTargetCol = MovementUtil.getCastlingRookTargetColumn(target.getColumn());
			grid[origin.getRow()][rookTargetCol] = grid[origin.getRow()][rookOriginCol];
			grid[origin.getRow()][rookOriginCol] = MovementUtil.EMPTY;
		}
		
		// update castling flags
		if (MovementUtil.isKing(originPiece)) {
			state.invalidateCastlingFlags(color);
		}
		if (MovementUtil.isRook(originPiece)) {
			Position kingRookPosition = state.getKingRookPosition(color);
			if (kingRookPosition != null && kingRookPosition.equals(origin)) {
				state.invalidateKingSideCastlingFlags(color);
			}
			
			Position queeRookPosition = state.getQueenRookPosition(color);
			if (queeRookPosition != null && queeRookPosition.equals(origin)) {
				state.invalidateQueenSideCastlingFlags(color);
			}
		}
		if (MovementUtil.isRook(capturedPiece)) {
			byte capturedColor = MovementUtil.getPieceColor(capturedPiece);
			Position kingRookPosition = state.getKingRookPosition(capturedColor);
			if (kingRookPosition != null && kingRookPosition.equals(target)) {
				state.invalidateKingSideCastlingFlags(capturedColor);
			}
			
			Position queeRookPosition = state.getQueenRookPosition(capturedColor);
			if (queeRookPosition != null && queeRookPosition.equals(target)) {
				state.invalidateQueenSideCastlingFlags(capturedColor);
			}
		}
		
		// update en passant target square
		state.setEnPassantTargetSquare(null);
		if (MovementUtil.isPawn(originPiece) && Math.abs(origin.getRow() - target.getRow()) == 2) {
			for (int i = -1; i <= 1; i += 2) {
				int sideColumn = target.getColumn() + i;
				if (sideColumn >= 0 && sideColumn < 8) {
					byte squareValue = grid[target.getRow()][sideColumn];
					if (MovementUtil.isPawn(squareValue)) {
						byte sidePieceColor = MovementUtil.getPieceColor(squareValue);
						if (MovementUtil.getOppositeColor(sidePieceColor) == MovementUtil.getPieceColor(originPiece)) {
							state.setEnPassantTargetSquare(
								Position.of(
									(target.getRow() + origin.getRow()) / 2,
									target.getColumn()
								)
							);
						}
					}
				}
			}
		}
		
		// update half move clock
		if (MovementUtil.isCapture(movementTarget.getFlags()) || MovementUtil.isPawn(originPiece)) {
			state.resetHalMoveClock();
		} else {
			state.incrementHalfMoveClock();
		}
		
		// update full move counter
		if (MovementUtil.isBlackColor(getSideToMove())) {
			state.incrementFullMoveClock();
		}
		
		// increment game movement counter
		state.incrementMovementCounter();
		
		movementLog.add(
			new MovementLogEntry(
				stateCopy,
				originPiece,
				origin,
				target,
				capturedPiece
			)
		);
	}
	
	public boolean hasPreviousMovement() {
		return !movementLog.isEmpty();
	}
	
	public void undo() {
		if (!hasPreviousMovement()) {
			throw new ChessException("No previous movement to undo");
		}
		
		MovementLogEntry log = movementLog.remove(movementLog.size() - 1);
		BoardState logState = log.getBoardState();
		Position enPassTarget = logState.getEnPassantTargetSquare();
		Position origin = log.getOrigin();
		Position target = log.getTarget();
		
		grid[origin.getRow()][origin.getColumn()] = log.getOriginPiece();
		if (MovementUtil.isPawn(log.getOriginPiece()) && enPassTarget != null && enPassTarget.equals(target)) {
			grid[origin.getRow()][target.getColumn()] = log.getCapturedPiece();
			grid[target.getRow()][target.getColumn()] = MovementUtil.EMPTY;
		} else {
			grid[target.getRow()][target.getColumn()] = log.getCapturedPiece();
		}
		
		if (MovementUtil.isKing(log.getOriginPiece()) && Math.abs(origin.getColumn() - target.getColumn()) == 2) {
			byte rookOriginCol = MovementUtil.getCastlingRookOriginColumn(target.getColumn());
			byte rookTargetCol = MovementUtil.getCastlingRookTargetColumn(target.getColumn());
			grid[target.getRow()][rookOriginCol] = grid[target.getRow()][rookTargetCol];
			grid[target.getRow()][rookTargetCol] = MovementUtil.EMPTY;
		}
		
		this.state.set(logState);
	}
	
	/**
	 * Retrieves the side color that has the turn to make next piece movement.
	 * 
	 * @return The piece color code.
	 * 
	 * @see MovementUtil#WHITE
	 * @see MovementUtil#BLACK
	 */
	public byte getSideToMove() {
		return state.getSideToMove();
	}
	
	/**
	 * Retrieves the movements for the piece localized in the given position.
	 * 
	 * <p>
	 * This method extract all movement meta information available.
	 * 
	 * @param position The piece position.
	 * 
	 * @return The container with all movements available for the piece.
	 * 
	 * @throws EmptySquareException If the square in the given position is empty.
	 * 
	 * @see #getMovements()
	 * @see #getMovements(boolean)
	 * @see #getMovements(byte)
	 * @see #getMovements(byte, boolean)
	 * @see #getMovements(Position, boolean)
	 * @see #getMovementsWB()
	 * @see #getMovementsWB(boolean)
	 */
	public Movements getMovements(Position position) {
		return getMovements(position, true);
	}
	
	/**
	 * Retrieves the movements for the piece localized in the given position.
	 * 
	 * @param position The piece position.
	 * @param extractAllMoveFlags If extra flags show be extract.
	 * 
	 * @return The container with all movements available for the piece.
	 * 
	 * @see #getMovements()
	 * @see #getMovements(byte)
	 * @see #getMovements(Position)
	 * @see #getMovements(byte, boolean)
	 * @see #getMovements(Position, boolean)
	 * @see #getMovementsWB()
	 * @see #getMovementsWB(boolean)
	 */
	public Movements getMovements(Position position, boolean extractAllMoveFlags) {
		if (isEmpty(position)) {
			throw new EmptySquareException(position);
		}
		PieceMovements pieceMovements = privateGetMovements(position, extractAllMoveFlags);
		return new Movements(List.of(pieceMovements), pieceMovements.getMetadata());
	}
	
	/**
	 * Retrieves all movements of all pieces of the side that has the turn to move.
	 * 
	 * <p>
	 * This method extract all movement meta information available.
	 * 
	 * @return The container with all movements.
	 * 
	 * @see #getMovements(boolean)
	 * @see #getMovements(byte)
	 * @see #getMovements(Position)
	 * @see #getMovements(byte, boolean)
	 * @see #getMovements(Position, boolean)
	 * @see #getMovementsWB()
	 * @see #getMovementsWB(boolean)
	 */
	public Movements getMovements() {
		return getMovements(getSideToMove());
	}
	
	/**
	 * Retrieves all movements of all pieces of the side that has the turn to move.
	 * 
	 * @param extractAllMoveFlags If extra flags show be extract.
	 * 
	 * @return The container with all movements.
	 * 
	 * @see #getMovements()
	 * @see #getMovements(byte)
	 * @see #getMovements(Position)
	 * @see #getMovements(byte, boolean)
	 * @see #getMovements(Position, boolean)
	 * @see #getMovementsWB()
	 * @see #getMovementsWB(boolean)
	 */
	public Movements getMovements(boolean extractAllMoveFlags) {
		return getMovements(getSideToMove(), extractAllMoveFlags);
	}
	
	/**
	 * Retrieves all movements of all pieces of the specific color.
	 * 
	 * <p>
	 * This method extract all movement meta information available.
	 * 
	 * @param colorCode The pieces color.
	 * 
	 * @return The container with all movements.
	 * 
	 * @see #getMovements()
	 * @see #getMovements(boolean)
	 * @see #getMovements(byte)
	 * @see #getMovements(Position)
	 * @see #getMovements(Position, boolean)
	 * @see #getMovementsWB()
	 * @see #getMovementsWB(boolean)
	 */
	public Movements getMovements(byte colorCode) {
		return getMovements(colorCode, true);
	}
	
	/**
	 * Retrieves all movements of all pieces of the specific color.
	 * 
	 * @param colorCode The pieces color.
	 * @param extractAllMoveFlags If extra flags show be extract.
	 * 
	 * @return The container with all movements.
	 * 
	 * @see #getMovements()
	 * @see #getMovements(boolean)
	 * @see #getMovements(byte)
	 * @see #getMovements(Position)
	 * @see #getMovements(Position, boolean)
	 * @see #getMovementsWB()
	 * @see #getMovementsWB(boolean)
	 */
	public Movements getMovements(byte colorCode, boolean extractAllMoveFlags) {
		List<PieceMovements> list = new ArrayList<>();
		MovementMetadata.Builder metadataBuilder = MovementMetadata.builder();
		simplePiecesWalking((Position position, int pieceCode) -> {
			if (MovementUtil.getPieceColor(pieceCode) != colorCode) {
				return;
			}
			PieceMovements pieceMovements = privateGetMovements(position, extractAllMoveFlags);
			if (!pieceMovements.isEmpty()) {
				list.add(pieceMovements);
				metadataBuilder.add(pieceMovements.getMetadata());
			}
		});
		return new Movements(
			list,
			metadataBuilder.buid()
		);
	}
	
	/**
	 * Retrieves all movements of all pieces (white and black).
	 * 
	 * <p>
	 * This method extract all movement meta information available.
	 * 
	 * @return The container with all movements.
	 * 
	 * @see #getMovements()
	 * @see #getMovements(boolean)
	 * @see #getMovements(byte)
	 * @see #getMovements(Position)
	 * @see #getMovements(byte, boolean)
	 * @see #getMovements(Position, boolean)
	 * @see #getMovementsWB(boolean)
	 */
	public Movements getMovementsWB() {
		return getMovementsWB(true);
	}
	
	/**
	 * Retrieves all movements of all pieces (white and black).
	 * 
	 * @param extractAllMoveFlags If extra flags show be extract.
	 * 
	 * @return The container with all movements.
	 * 
	 * @see #getMovements()
	 * @see #getMovements(boolean)
	 * @see #getMovements(byte)
	 * @see #getMovements(Position)
	 * @see #getMovements(byte, boolean)
	 * @see #getMovements(Position, boolean)
	 * @see #getMovementsWB()
	 */
	public Movements getMovementsWB(boolean extractAllMoveFlags) {
		Movements movements1 = getMovements(getSideToMove(), extractAllMoveFlags);
		Movements movements2 = getMovements(MovementUtil.getOppositeColor(getSideToMove()), extractAllMoveFlags);
		return movements1.merge(movements2);
	}
	
	public Movement getMovementRandom() {
		Movements movements = getMovements();
		PieceMovements pieceMovements = movements.getPieceMovements(
			MovementUtil.RDN.nextInt(movements.getOriginCount())
		);
		MovementTarget movementTarget = pieceMovements.getTarget(
			MovementUtil.RDN.nextInt(pieceMovements.getTargertsCount())
		);
		return new Movement(
			pieceMovements.getOrigin(),
			movementTarget
		);
	}
	
	@SuppressWarnings("javadoc")
	private PieceMovements privateGetMovements(Position position, boolean extractAllMoveFlags) {
		byte pieceCode = getPiece(position);
		PieceMovements pieceMovements = null;
		if (MovementUtil.isKing(pieceCode)) {
			pieceMovements = getMovementsFromKing(position, extractAllMoveFlags);
		}
		if (MovementUtil.isQueen(pieceCode)) {
			pieceMovements = getMovementsFromQueen(position, extractAllMoveFlags);
		}
		if (MovementUtil.isRook(pieceCode)) {
			pieceMovements = getMovementsFromRook(position, extractAllMoveFlags);
		}
		if (MovementUtil.isBishop(pieceCode)) {
			pieceMovements = getMovementsFromBishop(position, extractAllMoveFlags);
		}
		if (MovementUtil.isKnight(pieceCode)) {
			pieceMovements = getMovementsFromKnight(position, extractAllMoveFlags);
		}
		if (MovementUtil.isPawn(pieceCode)) {
			pieceMovements = getMovementsFromPawn(position, extractAllMoveFlags);
		}
		
		if (pieceMovements == null) {
			throw new ChessException(
				String.format(
					"Unexpected piece type in the position [%d,%d]: %d",
					position.getRow(),
					position.getColumn(),
					pieceCode
				)
			);
		}
		
		return pieceMovements;
	}
	
	private short extractMovementFlags(
		Position origin,
		Position target,
		byte pieceTarget,
		boolean extractAllMoveFlags
	) {
		short flags = 0;
		if (grid[target.getRow()][target.getColumn()] != MovementUtil.EMPTY) {
			flags |= MovementUtil.CAPTURE_MASK;
		}
		byte pieceCode = grid[origin.getRow()][origin.getColumn()];
		Position enpTarget = state.getEnPassantTargetSquare();
		if (MovementUtil.isPawn(pieceCode) && enpTarget != null && enpTarget.equals(target)) {
			flags |= MovementUtil.EN_PASSANT_MASK;
			flags |= MovementUtil.CAPTURE_MASK;
		}
		if (MovementUtil.isKing(pieceCode) && Math.abs(origin.getColumn() - target.getColumn()) == 2) {
			flags |= MovementUtil.CASTLING_MASK;
		}
		if (MovementUtil.isPawn(pieceCode) && (target.getRow() == 0 || target.getRow() == 7)) {
			flags |= MovementUtil.PROMOTION_MASK;
		}
		
		Position kingPosition = state.getKingPosition(state.getSideToMove());
		if (kingPosition != null && extractAllMoveFlags) {
			move(origin, new MovementTarget(target, pieceTarget, flags));
			byte king = grid[kingPosition.getRow()][kingPosition.getColumn()];
			for (int d = 0; d < ALL_DIRECTIONS.length; d++) {
				boolean possibleDiscoveryCheck = false;
				for (int i = 1; i <= 7; i++) {
					int targetRow = kingPosition.getRow() + i * ALL_DIRECTIONS[d].rowDirection;
					int targetColumn = kingPosition.getColumn() + i * ALL_DIRECTIONS[d].columnDirection;
					if (isInsideBoardBound(targetRow, targetColumn)) {
						byte targetSquareValue = grid[targetRow][targetColumn];
						int moveValue = targetSquareValue * king;
						if (moveValue > 0) {
							break;
						}
						if (origin.equals(targetRow, targetColumn)) {
							possibleDiscoveryCheck = true;
						}
						if (moveValue == 0) {
							continue;
						}
						if (MovementUtil.isQueen(targetSquareValue)
								|| (MovementUtil.isRook(targetSquareValue) && (kingPosition.getRow() == targetRow || kingPosition.getColumn() == targetColumn))
								|| (MovementUtil.isBishop(targetSquareValue) && kingPosition.getRow() - targetRow == kingPosition.getColumn() - targetColumn)) {
							if (MovementUtil.isCheck(flags)) {
								flags |= MovementUtil.DOUBLE_CHECK_MASK;
							}
							flags |= MovementUtil.CHECK_MASK;
							if (possibleDiscoveryCheck) {
								flags |= MovementUtil.DISCOVERY_CHECK_MASK;
							}
						}
						break;
					}
				}
			}
			boolean possibleDiscoveryCheck = false;
			for (int d = 0; d < KNIGHT_DIRECTIONS.length; d++) {
				int targetRow = kingPosition.getRow() + KNIGHT_DIRECTIONS[d].rowDirection;
				int targetColumn = kingPosition.getColumn() + KNIGHT_DIRECTIONS[d].columnDirection;
				if (isInsideBoardBound(targetRow, targetColumn)) {
					byte targetSquareValue = grid[targetRow][targetColumn];
					int moveValue = targetSquareValue * king;
					if (origin.equals(targetRow, targetColumn)) {
						possibleDiscoveryCheck = true;
					}
					if (moveValue < 0 && MovementUtil.isKnight(targetSquareValue)) {
						if (MovementUtil.isCheck(flags)) {
							flags |= MovementUtil.DOUBLE_CHECK_MASK;
						}
						flags |= MovementUtil.CHECK_MASK;
						if (possibleDiscoveryCheck) {
							flags |= MovementUtil.DISCOVERY_CHECK_MASK;
						}
					}
				}
			}
			Direction[] pawnDirections = MovementUtil.isWhiteColor(state.getSideToMove())
					? WHITE_PAWN_CAPTURE_DIRECTIONS
					: BLACK_PAWN_CAPTURE_DIRECTIONS;
			possibleDiscoveryCheck = false;
			for (int d = 0; d < pawnDirections.length; d++) {
				int targetRow = kingPosition.getRow() + pawnDirections[d].rowDirection;
				int targetColumn = kingPosition.getColumn() + pawnDirections[d].columnDirection;
				if (isInsideBoardBound(targetRow, targetColumn)) {
					byte targetSquareValue = grid[targetRow][targetColumn];
					int moveValue = targetSquareValue * king;
					if (origin.equals(targetRow, targetColumn)) {
						possibleDiscoveryCheck = true;
					}
					if (moveValue < 0 && MovementUtil.isPawn(targetSquareValue)) {
						if (MovementUtil.isCheck(flags)) {
							flags |= MovementUtil.DOUBLE_CHECK_MASK;
						}
						flags |= MovementUtil.CHECK_MASK;
						if (possibleDiscoveryCheck) {
							flags |= MovementUtil.DISCOVERY_CHECK_MASK;
						}
					}
				}
			}
			
			if (MovementUtil.isDoubleCheck(flags)) {
				flags &= ~MovementUtil.DISCOVERY_CHECK_MASK;
			}
			
			// Movements movements = getMovements(false);
			// if (movements.isEmpty() && MovementUtil.isCheck(flags)) {
			// flags |= MovementUtil.CHECKMATE_MASK;
			// }
			// if (movements.isEmpty() && !MovementUtil.isCheck(flags)) {
			// flags |= MovementUtil.STALEMATE_MASK;
			// }
			
			undo();
		}
		
		return flags;
	}
	
	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		long t1 = System.currentTimeMillis();
		PerftResult perft = PerftCalculator.perft(
			"r2qk2r/pb4pp/1n2Pb2/2B2Q2/p1p5/2P5/2B2PPP/RN2R1K1 w - - 1 0",
			4,
			true
		);
		long t2 = System.currentTimeMillis();
		perft.getDepths().forEach(d -> System.out.println(perft.getMetadata(d)));
		System.out.printf("%.2f%n", (t2 - t1) / 1000.0);
	}
	
	@SuppressWarnings("javadoc")
	private PieceMovements getMovementsFromKing(Position originPosition, boolean extractAllMoveFlags) {
		byte originSquareValue = getPiece(originPosition);
		List<MovementTarget> targets = new ArrayList<>(10);
		direcionalPiecesWalker(
			originPosition,
			KING_DIRECTIONS,
			1,
			(Position targetPosition, byte targetSquareValue) -> {
				byte moveValue = (byte) (originSquareValue * targetSquareValue);
				byte kingBackup = grid[originPosition.getRow()][originPosition.getColumn()];
				grid[originPosition.getRow()][originPosition.getColumn()] = MovementUtil.EMPTY;
				boolean underAttack = isUnderAttack(
					targetPosition,
					MovementUtil.getOppositeColor(MovementUtil.getPieceColor(originSquareValue))
				);
				grid[originPosition.getRow()][originPosition.getColumn()] = kingBackup;
				if (moveValue <= 0 && !underAttack) {
					targets.add(
						new MovementTarget(
							targetPosition,
							originSquareValue,
							extractMovementFlags(
								originPosition,
								targetPosition,
								originSquareValue,
								extractAllMoveFlags
							)
						)
					);
				}
				if (moveValue != 0) {
					return SquareVisitor.STOP_DIRECTION;
				}
				return SquareVisitor.CONTINUE;
			}
		);
		byte color = MovementUtil.getPieceColor(originSquareValue);
		byte[] castlinsDirecions = {
			-1, 1
		};
		for (byte castlingDirection : castlinsDirecions) {
			int limitColumn = castlingDirection < 0 ? 3 : 2;
			if (castlingDirection < 0 && !state.isQueenSideCastlingAvaiable(color)) {
				continue;
			}
			if (castlingDirection > 0 && !state.isKingSideCastlingAvaiable(color)) {
				continue;
			}
			
			boolean isValidCastling = true;
			for (int i = 0; i <= limitColumn; i++) {
				int currentRow = originPosition.getColumn() + castlingDirection * i;
				if (i <= 2 && isUnderAttack(
					Position.of(originPosition.getRow(), currentRow),
					MovementUtil.getOppositeColor(color)
				)) {
					isValidCastling = false;
					break;
				}
				if (i > 0 && grid[originPosition.getRow()][currentRow] != MovementUtil.EMPTY) {
					isValidCastling = false;
					break;
				}
			}
			if (isValidCastling) {
				Position targetPosition = Position.of(
					originPosition.getRow(),
					originPosition.getColumn() + 2 * castlingDirection
				);
				targets.add(
					new MovementTarget(
						Position.of(
							originPosition.getRow(),
							originPosition.getColumn() + 2 * castlingDirection
						),
						originSquareValue,
						extractMovementFlags(
							originPosition,
							targetPosition,
							originSquareValue,
							extractAllMoveFlags
						)
					)
				);
			}
		}
		return new PieceMovements(
			originPosition,
			originSquareValue,
			targets,
			mergeFlags(targets)
		);
	}
	
	@SuppressWarnings("javadoc")
	private PieceMovements getMovementsFromQueen(Position originPosition, boolean extractAllMoveFlags) {
		return getMovements(originPosition, extractAllMoveFlags, QUEEN_DIRECTIONS, 8 - 1);
	}
	
	@SuppressWarnings("javadoc")
	private PieceMovements getMovementsFromRook(Position originPosition, boolean extractAllMoveFlags) {
		return getMovements(originPosition, extractAllMoveFlags, ROOK_DIRECTIONS, 8 - 1);
	}
	
	@SuppressWarnings("javadoc")
	private PieceMovements getMovementsFromBishop(Position originPosition, boolean extractAllMoveFlags) {
		return getMovements(originPosition, extractAllMoveFlags, BISHOP_DIRECTIONS, 8 - 1);
	}
	
	@SuppressWarnings("javadoc")
	private PieceMovements getMovementsFromKnight(Position originPosition, boolean extractAllMoveFlags) {
		return getMovements(originPosition, extractAllMoveFlags, KNIGHT_DIRECTIONS, 1);
	}
	
	@SuppressWarnings("javadoc")
	private PieceMovements getMovements(
		Position originPosition,
		boolean extractAllMoveFlags,
		Direction[] directions,
		int walkLength
	) {
		byte originSquareValue = getPiece(originPosition);
		List<MovementTarget> targets = new ArrayList<>(30);
		direcionalPiecesWalker(
			originPosition,
			directions,
			walkLength,
			(Position targetPosition, byte targetSquareValue) -> {
				byte moveValue = (byte) (originSquareValue * targetSquareValue);
				if (moveValue <= 0 && !isKingInCheckWithMovement(originPosition, targetPosition, originSquareValue)) {
					targets.add(
						new MovementTarget(
							targetPosition,
							originSquareValue,
							extractMovementFlags(
								originPosition,
								targetPosition,
								originSquareValue,
								extractAllMoveFlags
							)
						)
					);
				}
				if (moveValue != 0) {
					return SquareVisitor.STOP_DIRECTION;
				}
				return SquareVisitor.CONTINUE;
			}
		);
		return new PieceMovements(
			originPosition,
			originSquareValue,
			targets,
			mergeFlags(targets)
		);
	}
	
	@SuppressWarnings("javadoc")
	private static Direction[] getPawnCaptureDirections(byte color) {
		return switch (color) {
			case MovementUtil.WHITE -> WHITE_PAWN_CAPTURE_DIRECTIONS;
			case MovementUtil.BLACK -> BLACK_PAWN_CAPTURE_DIRECTIONS;
			default -> throw new ChessException(String.format("Invalid color code: %d", color));
		};
	}
	
	@SuppressWarnings("javadoc")
	private static Direction[] getPawnSingleSquareMovementDirections(byte color) {
		return switch (color) {
			case MovementUtil.WHITE -> WHITE_PAWN_SINGLE_SQUARE_MOVE;
			case MovementUtil.BLACK -> BLACK_PAWN_SINGLE_SQUARE_MOVE;
			default -> throw new ChessException(String.format("Invalid color code: %d", color));
		};
	}
	
	@SuppressWarnings("javadoc")
	private static Direction[] getPawnDoubleSquareMovementDirections(byte color) {
		return switch (color) {
			case MovementUtil.WHITE -> WHITE_PAWN_DOUBLE_SQUARE_MOVE;
			case MovementUtil.BLACK -> BLACK_PAWN_DOUBLE_SQUARE_MOVE;
			default -> throw new ChessException(String.format("Invalid color code: %d", color));
		};
	}
	
	@SuppressWarnings("javadoc")
	private static byte getInitialPawnRow(byte color) {
		return switch (color) {
			case MovementUtil.WHITE -> MovementUtil.WHITE_PAWNS_INITIAL_ROW;
			case MovementUtil.BLACK -> MovementUtil.BLACK_PAWNS_INITIAL_ROW;
			default -> throw new ChessException(String.format("Invalid color code: %d", color));
		};
	}
	
	@SuppressWarnings("javadoc")
	private static List<Byte> getPawnTargetPieceTypes(byte targetRow) {
		if (targetRow == MovementUtil.WHITE_PAWN_PROMOTION_ROW || targetRow == MovementUtil.BLACK_PAWN_PROMOTION_ROW) {
			return PAWN_REPLACEMENT_PIECE_TYPES;
		} else {
			return PAWN_NO_REPLACEMENT_PIECE_TYPES;
		}
	}
	
	@SuppressWarnings("javadoc")
	private PieceMovements getMovementsFromPawn(Position originPosition, boolean extractAllMoveFlags) {
		byte originSquareValue = grid[originPosition.getRow()][originPosition.getColumn()];
		byte color = MovementUtil.getPieceColor(originSquareValue);
		List<MovementTarget> targets = new ArrayList<>(30);
		direcionalPiecesWalker(
			originPosition,
			getPawnCaptureDirections(color),
			1,
			(Position targetPosition, byte targetSquareValue) -> {
				byte moveValue = (byte) (originSquareValue * targetSquareValue);
				if ((moveValue < 0
						|| (moveValue == 0
								&& state.getEnPassantTargetSquare() != null
								&& state.getEnPassantTargetSquare().equals(targetPosition)))
						&& !isKingInCheckWithMovement(originPosition, targetPosition, originSquareValue)) {
					for (byte pieceType : getPawnTargetPieceTypes(targetPosition.getRow())) {
						byte targetPiece = MovementUtil.getPiece(pieceType, color);
						targets.add(
							new MovementTarget(
								targetPosition,
								targetPiece,
								extractMovementFlags(
									originPosition,
									targetPosition,
									targetPiece,
									extractAllMoveFlags
								)
							)
						);
					}
				}
				return SquareVisitor.CONTINUE;
			}
		);
		direcionalPiecesWalker(
			originPosition,
			getPawnSingleSquareMovementDirections(color),
			1,
			(Position targetPosition, byte targetSquareValue) -> {
				byte moveValue = (byte) (originSquareValue * targetSquareValue);
				if (moveValue == 0 && !isKingInCheckWithMovement(originPosition, targetPosition, originSquareValue)) {
					for (byte pieceType : getPawnTargetPieceTypes(targetPosition.getRow())) {
						byte targetPiece = MovementUtil.getPiece(pieceType, color);
						targets.add(
							new MovementTarget(
								targetPosition,
								targetPiece,
								extractMovementFlags(
									originPosition,
									targetPosition,
									targetPiece,
									extractAllMoveFlags
								)
							)
						);
					}
				}
				return SquareVisitor.CONTINUE;
			}
		);
		if (getInitialPawnRow(color) == originPosition.getRow()) {
			direcionalPiecesWalker(
				originPosition,
				getPawnDoubleSquareMovementDirections(color),
				1,
				(Position targetPosition, byte targetSquareValue) -> {
					byte moveValue = (byte) (originSquareValue * targetSquareValue);
					if (moveValue == 0) {
						byte midRow = (byte) ((originPosition.getRow() + targetPosition.getRow()) / 2);
						if (grid[midRow][originPosition.getColumn()] == MovementUtil.EMPTY
								&& !isKingInCheckWithMovement(originPosition, targetPosition, originSquareValue)) {
							targets.add(
								new MovementTarget(
									targetPosition,
									originSquareValue,
									extractMovementFlags(
										originPosition,
										targetPosition,
										originSquareValue,
										extractAllMoveFlags
									)
								)
							);
						}
					}
					return SquareVisitor.CONTINUE;
				}
			);
		}
		return new PieceMovements(
			originPosition,
			originSquareValue,
			targets,
			mergeFlags(targets)
		);
	}
	
	private static MovementMetadata mergeFlags(List<MovementTarget> targets) {
		Builder builder = MovementMetadata.builder();
		targets.forEach(t -> builder.add(t.getFlags()));
		return builder.buid();
	}
	
	@SuppressWarnings("javadoc")
	private boolean isKingInCheckWithMovement(Position origin, Position target, byte targetPiece) {
		byte originPiece = grid[origin.getRow()][origin.getColumn()];
		byte color = MovementUtil.getPieceColor(originPiece);
		if (!state.isKingPresent(color)) {
			return false;
		}
		byte capturedPawn = MovementUtil.EMPTY;
		if (state.getEnPassantTargetSquare() != null && state.getEnPassantTargetSquare().equals(target)) {
			capturedPawn = grid[origin.getRow()][state.getEnPassantTargetSquare().getColumn()];
			grid[origin.getRow()][state.getEnPassantTargetSquare().getColumn()] = MovementUtil.EMPTY;
		}
		byte targetBackup = grid[target.getRow()][target.getColumn()];
		grid[origin.getRow()][origin.getColumn()] = MovementUtil.EMPTY;
		grid[target.getRow()][target.getColumn()] = targetPiece;
		boolean isKingInCheck = isUnderAttack(state.getKingPosition(color), MovementUtil.getOppositeColor(color));
		grid[origin.getRow()][origin.getColumn()] = originPiece;
		grid[target.getRow()][target.getColumn()] = targetBackup;
		if (state.getEnPassantTargetSquare() != null && state.getEnPassantTargetSquare().equals(target)) {
			grid[origin.getRow()][state.getEnPassantTargetSquare().getColumn()] = capturedPawn;
		}
		return isKingInCheck;
	}
	
	/**
	 * Evaluates if given position is attacked by any piece of the specified color.
	 * 
	 * @param position The attacked position.
	 * @param attackerColor The attacker color.
	 * 
	 * @return A value <code>true</code> if the position is attacked, or <code>false</code> if not.
	 */
	public boolean isUnderAttack(Position position, byte attackerColor) {
		return !getAttackers(position, attackerColor, true).isEmpty();
	}
	
	/**
	 * The maximum movement length that a piece may perform during a single movement. For example,
	 * the queen may move at least 7 square in a single movement when available.
	 * 
	 * @param pieceType The piece type.
	 * 
	 * @return The movement length.
	 */
	private static int getMaxMoveLength(byte pieceType) {
		return switch (pieceType) {
			case MovementUtil.KING -> 1;
			case MovementUtil.QUEEN -> 8 - 1;
			case MovementUtil.ROOK -> 8 - 1;
			case MovementUtil.BISHOP -> 8 - 1;
			case MovementUtil.KNIGHT -> 1;
			case MovementUtil.PAWN -> 1;
			default -> throw new IllegalArgumentException(String.format("Invalid piece type: %d", pieceType));
		};
	}
	
	/**
	 * Retrieves a list of pieces that are attacking the given position. This method return all
	 * pieces that can reach an specific position by made a movement. A pinned piece will be
	 * considered in this method.
	 *
	 * <pre>
	 * ┌───┬───┬───┬───┬───┬───┬───┬───┐
	 * │   │   │   │   │   │   │   │   │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │ k │   │ r │   │   │   │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │ r │   │   │   │ P │   │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │   │   │   │   │   │ P │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │   │   │   │   │   │   │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │   │   │   │   │   │   │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │ R │   │   │   │   │   │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │ K │ Q │   │   │   │   │   │
	 * └───┴───┴───┴───┴───┴───┴───┴───┘
	 * - white to play
	 * </pre>
	 * 
	 * The above diagram shows that black rook in c6 is pinned, but a call for this method querying
	 * the attackers of the white pawn in g6 will return that black pinned rook.
	 * 
	 * @param position The attacked position.
	 * @param attackerColor The attacker color.
	 * 
	 * @return The list of attackers.
	 */
	public List<LocalizedPiece> getAttackers(Position position, byte attackerColor) {
		return getAttackers(position, attackerColor, false);
	}
	
	@SuppressWarnings(
		{
			"javadoc",
			"squid:S1142"
		}
	)
	private List<LocalizedPiece> getAttackers(Position position, byte attackerColor, boolean stopOnFirstAttackant) {
		BiFunction<Byte, Byte, Direction[]> directionResolver = (pieceType, color) -> {
			return switch (pieceType) {
				case MovementUtil.KING -> KING_DIRECTIONS;
				case MovementUtil.QUEEN -> QUEEN_DIRECTIONS;
				case MovementUtil.ROOK -> ROOK_DIRECTIONS;
				case MovementUtil.BISHOP -> BISHOP_DIRECTIONS;
				case MovementUtil.KNIGHT -> KNIGHT_DIRECTIONS;
				case MovementUtil.PAWN -> MovementUtil.isWhiteColor(color)
						? BLACK_PAWN_CAPTURE_DIRECTIONS
						: WHITE_PAWN_CAPTURE_DIRECTIONS;
				default -> throw new IllegalArgumentException(String.format("Invalid piece type: %d", pieceType));
			};
		};
		List<LocalizedPiece> attackers = new ArrayList<>(30);
		for (byte pieceType : PIECE_TYPES) {
			getAttackers(
				position,
				stopOnFirstAttackant,
				attackers,
				directionResolver.apply(pieceType, attackerColor),
				getMaxMoveLength(pieceType),
				MovementUtil.getPiece(pieceType, attackerColor)
			);
			if (stopOnFirstAttackant && !attackers.isEmpty()) {
				return attackers;
			}
		}
		return attackers;
	}
	
	@SuppressWarnings("javadoc")
	private void getAttackers(
		Position attackedPosition,
		boolean stopOnFirstAttackant,
		List<LocalizedPiece> attackers,
		Direction[] directions,
		int moveLenth,
		byte expectedAttackerPiece
	) {
		direcionalPiecesWalker(
			attackedPosition,
			directions,
			moveLenth,
			(Position attackerPosition, byte attackerPiece) -> {
				if (expectedAttackerPiece == attackerPiece) {
					attackers.add(new LocalizedPiece(attackerPosition, attackerPiece));
					if (stopOnFirstAttackant) {
						return SquareVisitor.STOP_WALKING;
					}
				}
				if (attackerPiece != MovementUtil.EMPTY) {
					return SquareVisitor.STOP_DIRECTION;
				}
				return SquareVisitor.CONTINUE;
			}
		);
	}
	
	@SuppressWarnings(
		{
			"squid:S135",
			"javadoc"
		}
	)
	private void direcionalPiecesWalker(
		Position origin,
		Direction[] directions,
		int moveLength,
		SquareVisitor visitor
	) {
		for (int d = 0; d < directions.length; d++) {
			for (int length = 1; length <= moveLength; length++) {
				int targetRow = origin.getRow() + length * directions[d].rowDirection;
				int targetColumn = origin.getColumn() + length * directions[d].columnDirection;
				if (!isInsideBoardBound(targetRow, targetColumn)) {
					break;
				}
				Position targetPosition = Position.of(targetRow, targetColumn);
				byte visitResult = visitor.visit(targetPosition, grid[targetRow][targetColumn]);
				if (visitResult == SquareVisitor.STOP_WALKING) {
					return;
				}
				if (visitResult == SquareVisitor.STOP_DIRECTION) {
					break;
				}
			}
		}
	}
	
	@FunctionalInterface
	private interface SquareVisitor {
		
		/**
		 * Indicates that current direction should be stopped.
		 */
		byte STOP_DIRECTION = 1;
		
		/**
		 * Indicates that whole walking should be immediately stopped.
		 */
		byte STOP_WALKING = 2;
		
		/**
		 * Indicates that walking should continue.
		 */
		byte CONTINUE = 3;
		
		/**
		 * Called by
		 * {@linkplain Board#direcionalPiecesWalker(Position, Direction[], int, SquareVisitor)
		 * direcionalPiecesWalker}. for each reached square.
		 * 
		 * @param position The visited square position.
		 * @param pieceCode The piece code of the piece positioned in the informed square, or the
		 *        code representative for empty square ({@link MovementUtil#EMPTY}).
		 * 
		 * @return Should return an information to the walker if the walking may continue, stop an
		 *         direction of stop entire walking. There three constants for indicating that
		 *         behavior:
		 * 
		 *         <ul>
		 *         <li>{@link #CONTINUE}
		 *         <li>{@link #STOP_DIRECTION}
		 *         <li>{@link #STOP_DIRECTION}
		 *         </ul>
		 */
		byte visit(Position position, byte pieceCode);
	}
	
	/**
	 * Retrieves the FEN string representative for this board in its current state.
	 * 
	 * @return The FEN string.
	 */
	@SuppressWarnings(
		{
			"squid:S134",
			"squid:S3776"
		}
	)
	public String getFen() {
		StringBuilder builder = new StringBuilder();
		for (int row = 0; row < 8; row++) {
			if (row > 0) {
				builder.append('/');
			}
			int emptyCount = 0;
			for (int column = 0; column < 8; column++) {
				if (grid[row][column] == MovementUtil.EMPTY) {
					emptyCount++;
				} else {
					if (emptyCount > 0) {
						builder.append(emptyCount);
					}
					emptyCount = 0;
					builder.append(MovementUtil.pieceCodeToLetter(grid[row][column]));
				}
			}
			if (emptyCount > 0) {
				builder.append(emptyCount);
			}
		}
		
		builder.append(' ');
		
		builder.append(MovementUtil.colorCodeToLetter(state.getSideToMove())).append(' ');
		builder.append(encodeFenCastlingFlags()).append(' ');
		if (state.getEnPassantTargetSquare() != null) {
			builder.append(state.getEnPassantTargetSquare().getNotation());
		} else {
			builder.append('-');
		}
		builder.append(' ');
		builder.append(state.getHalfMoveClock()).append(' ');
		builder.append(state.getFullMoveCounter());
		
		return builder.toString();
	}
	
	@SuppressWarnings("javadoc")
	private Object encodeFenCastlingFlags() {
		StringBuilder builder = new StringBuilder();
		if (MovementUtil.isWhiteKingCaslting(state.getCastlingFlags())) {
			builder.append(MovementUtil.WHITE_KING_LETTER);
		}
		if (MovementUtil.isWhiteQueenCaslting(state.getCastlingFlags())) {
			builder.append(MovementUtil.WHITE_QUEEN_LETTER);
		}
		if (MovementUtil.isBlackKingCaslting(state.getCastlingFlags())) {
			builder.append(MovementUtil.BLACK_KING_LETTER);
		}
		if (MovementUtil.isBlackQueenCaslting(state.getCastlingFlags())) {
			builder.append(MovementUtil.BLACK_QUEEN_LETTER);
		}
		return builder.length() == 0 ? "-" : builder.toString();
	}
	
	@SuppressWarnings("javadoc")
	private static BoardState createBoardState(byte[][] grid, FenPositionInfo fenInfo) {
		BoardState state = new BoardState();
		state.setEnPassantTargetSquare(fenInfo.getEnPassantTargetSquare());
		state.setFullMoveCounter(fenInfo.getFullMoveCounter());
		state.setHalfMoveClock(fenInfo.getHalfMoveClock());
		state.setSideToMove(fenInfo.getSideToMove());
		if (fenInfo.isWhiteQueenCastlingAvaiable() && MovementUtil.isWhiteRook(grid[7][0])) {
			state.setQueenRookPosition(Position.of(7, 0), MovementUtil.WHITE);
		}
		if (fenInfo.isWhiteKingCastlingAvaiable() && MovementUtil.isWhiteRook(grid[7][7])) {
			state.setKingRookPosition(Position.of(7, 7), MovementUtil.WHITE);
		}
		if (fenInfo.isBlackQueenCastlingAvaiable() && MovementUtil.isBlackRook(grid[0][0])) {
			state.setQueenRookPosition(Position.of(0, 0), MovementUtil.BLACK);
		}
		if (fenInfo.isBlackKingCastlingAvaiable() && MovementUtil.isBlackRook(grid[0][7])) {
			state.setKingRookPosition(Position.of(0, 7), MovementUtil.BLACK);
		}
		return state;
	}
	
	@SuppressWarnings("javadoc")
	private static boolean isInsideBoardBound(int row, int column) {
		return row >= 0 && row < 8
				&& column >= 0 && column < 8;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("┌───┬───┬───┬───┬───┬───┬───┬───┐").append(NEWLINE);
		for (int row = 0; row < 8; row++) {
			for (int column = 0; column < 8; column++) {
				Position position = Position.of(row, column);
				char letter = isEmpty(position)
						? ' '
						: MovementUtil.pieceCodeToLetter(grid[position.getRow()][position.getColumn()]);
				builder.append("│ ").append(letter).append(" ");
			}
			builder.append("│").append(NEWLINE);
			if (row == 8 - 1) {
				builder.append("└───┴───┴───┴───┴───┴───┴───┴───┘");
			} else {
				builder.append("├───┼───┼───┼───┼───┼───┼───┼───┤").append(NEWLINE);
			}
		}
		return builder.toString();
	}
	
	@Override
	public Board copy() {
		return new Board(
			getLocalizedPieces(),
			state.copy()
		);
	}
	
	@Override
	public int hashCode() {
		int hash = 1;
		for (int row = 0; row < 8; row++) {
			for (int column = 0; column < 8; column++) {
				int v = grid[row][column];
				v = v == 0 ? 11 : v;
				hash *= v;
			}
		}
		hash *= state.hashCode();
		return hash;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Board)) {
			return false;
		}
		Board b = (Board) obj;
		return gridEquals(b.grid) && state.equals(b.state);
	}
	
	@SuppressWarnings("javadoc")
	private boolean gridEquals(byte[][] otherGrid) {
		for (int row = 0; row < 8; row++) {
			for (int column = 0; column < 8; column++) {
				if (this.grid[row][column] != otherGrid[row][column]) {
					return false;
				}
			}
		}
		return true;
	}
	
	@SuppressWarnings("javadoc")
	private static class Direction {
		
		private final byte rowDirection;
		
		private final byte columnDirection;
		
		Direction(int rowDirection, int columnDirection) {
			this.rowDirection = (byte) rowDirection;
			this.columnDirection = (byte) columnDirection;
		}
	}
}
