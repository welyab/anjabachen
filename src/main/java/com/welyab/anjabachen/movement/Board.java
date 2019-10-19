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
import java.util.function.ObjIntConsumer;

import com.welyab.anjabachen.movement.fen.FenParser;
import com.welyab.anjabachen.movement.fen.FenPositionInfo;

/**
 * The <code>Board</code> class is the main component for movement generation in
 * <strong>ANJABACHEN</strong>. It keeps internally a piece disposition state, also castling
 * ability flags, <i>en passant</i> captures, etc.
 * 
 * <p>
 * The <code>Board</code> class can manage movement generation also to the chess variant
 * <strong><i><a href="https://en.wikipedia.org/wiki/Chess960" alt="wikipedia article about chess
 * variant created by bobby fish name chess960">Fischer Randon Chess</a></i></strong>.
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
public class Board implements Copyable<Board> {
	
	@SuppressWarnings("javadoc")
	private static final String NEWLINE = String.format("%n");
	
	/** Directions for the king piece (white or black). */
	private static final Direction[] KING_DIRECTIONS = {
			new Direction(-1, -1),
			new Direction(-1, 0),
			new Direction(-1, +1),
			new Direction(+1, -1),
			new Direction(+1, 0),
			new Direction(+1, +1),
			new Direction(0, +1),
			new Direction(0, -1)
	};
	
	/** Directions for the queen piece (white or black). */
	private static final Direction[] QUEEN_DIRECTIONS = {
			new Direction(-1, -1),
			new Direction(-1, 0),
			new Direction(-1, +1),
			new Direction(+1, -1),
			new Direction(+1, 0),
			new Direction(+1, +1),
			new Direction(0, +1),
			new Direction(0, -1)
	};
	
	/** Directions for the king rook (white or black). */
	private static final Direction[] ROOK_DIRECTIONS = {
			new Direction(-1, 0),
			new Direction(+1, 0),
			new Direction(0, +1),
			new Direction(0, -1)
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
	
	private static final Direction[] WHITE_PAWN_CAPTURE_DIRECTIONS = {
			new Direction(-1, -1),
			new Direction(-1, +1)
	};
	
	private static final Direction[] BLACK_PAWN_CAPTURE_DIRECTIONS = {
			new Direction(+1, -1),
			new Direction(+1, +1)
	};
	
	/** The board squares. */
	private final byte[][] grid;
	
	/** The board state. */
	private final BoardState state;
	
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
		this(BoardUtil.FEN_INITIAL_POSITION);
	}
	
	/**
	 * Creates a board with given piece disposition described in the FEN string.
	 * 
	 * @param fen The FEN string.
	 */
	public Board(String fen) {
		this(new FenParser(fen));
	}
	
	@SuppressWarnings("javadoc")
	private Board(FenParser fenParser) {
		this(
			fenParser.getLocalizedPieces(),
			createStateInfo(fenParser.getFenPositionInfo())
		);
	}
	
	@SuppressWarnings("javadoc")
	private Board(List<LocalizedPiece> pieces, BoardState state) {
		grid = createGrid();
		addPiecesToGrid(pieces);
		this.state = state.copy();
	}
	
	@SuppressWarnings("javadoc")
	private void addPiecesToGrid(List<LocalizedPiece> pieces) {
		pieces.forEach((LocalizedPiece lp) -> {
			Position position = lp.getPosition();
			grid[position.getRow()][position.getColumn()] = lp.getPieceCode();
		});
	}
	
	@SuppressWarnings("javadoc")
	private static byte[][] createGrid() {
		return new byte[BoardUtil.SIZE][BoardUtil.SIZE];
	}
	
	/**
	 * Walk all squares of the board, calling the given <code>visitor</code> when the square is
	 * not empty.
	 * 
	 * @param visitor The visitor.
	 */
	private void simplePiecesWalking(ObjIntConsumer<Position> visitor) {
		for (int row = 0; row < BoardUtil.SIZE; row++) {
			for (int column = 0; column < BoardUtil.SIZE; column++) {
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
		return grid[position.getRow()][position.getColumn()] == BoardUtil.NO_PIECE_CODE;
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
		return privateGetPiece(position);
	}
	
	/**
	 * A version of {@link #getPiece(Position)} without empty square verification.
	 * 
	 * @param position The position.
	 * 
	 * @return The piece.
	 */
	private byte privateGetPiece(Position position) {
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
	public BoardState getBoardState() {
		return state.copy();
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
	
	@SuppressWarnings({
			"javadoc",
			"squid:S1142"
	})
	private List<LocalizedPiece> getAttackers(Position position, byte attackerColor, boolean stopOnFirstAttackant) {
		List<LocalizedPiece> attackers = new ArrayList<>();
		getAttackersFromKing(position, attackerColor, stopOnFirstAttackant, attackers);
		if (!attackers.isEmpty()) {
			return attackers;
		}
		getAttackersFromQueen(position, attackerColor, stopOnFirstAttackant, attackers);
		if (!attackers.isEmpty()) {
			return attackers;
		}
		getAttackersFromRook(position, attackerColor, stopOnFirstAttackant, attackers);
		if (!attackers.isEmpty()) {
			return attackers;
		}
		getAttackersFromBishop(position, attackerColor, stopOnFirstAttackant, attackers);
		if (!attackers.isEmpty()) {
			return attackers;
		}
		getAttackersFromKnight(position, attackerColor, stopOnFirstAttackant, attackers);
		if (!attackers.isEmpty()) {
			return attackers;
		}
		getAttackersFromPawn(position, attackerColor, stopOnFirstAttackant, attackers);
		return attackers;
	}
	
	@SuppressWarnings("javadoc")
	private void getAttackersFromKing(
			Position attackedPosition,
			byte attackerColor,
			boolean stopOnFirstAttackant,
			List<LocalizedPiece> attackers
	) {
		direcionalPiecesWalker(
			attackedPosition,
			KING_DIRECTIONS,
			1,
			(Position attackerPosition, int attackerPiece) -> {
				if (BoardUtil.isKing(attackerPiece) && BoardUtil.getColorCode(attackerPiece) == attackerColor) {
					attackers.add(new LocalizedPiece(attackerPosition, attackerPiece));
					if (stopOnFirstAttackant) {
						return SquareVisitor.STOP_WALKING;
					}
				}
				return SquareVisitor.CONTINUE;
			}
		);
	}
	
	@SuppressWarnings("javadoc")
	private void getAttackersFromQueen(
			Position attackedPosition,
			byte attackerColor,
			boolean stopOnFirstAttackant,
			List<LocalizedPiece> attackers
	) {
		direcionalPiecesWalker(
			attackedPosition,
			QUEEN_DIRECTIONS,
			BoardUtil.SIZE - 1,
			(Position attackerPosition, int attackerPiece) -> {
				if (BoardUtil.isQueen(attackerPiece) && BoardUtil.getColorCode(attackerPiece) == attackerColor) {
					attackers.add(new LocalizedPiece(attackerPosition, attackerPiece));
					if (stopOnFirstAttackant) {
						return SquareVisitor.STOP_WALKING;
					}
				}
				if (attackerPiece != BoardUtil.NO_PIECE_CODE) {
					return SquareVisitor.STOP_DIRECTION;
				}
				return SquareVisitor.CONTINUE;
			}
		);
	}
	
	@SuppressWarnings("javadoc")
	private void getAttackersFromRook(
			Position attackedPosition,
			byte attackerColor,
			boolean stopOnFirstAttackant,
			List<LocalizedPiece> attackers
	) {
		direcionalPiecesWalker(
			attackedPosition,
			ROOK_DIRECTIONS,
			BoardUtil.SIZE - 1,
			(Position attackerPosition, int attackerPiece) -> {
				if (BoardUtil.isRook(attackerPiece) && BoardUtil.getColorCode(attackerPiece) == attackerColor) {
					attackers.add(new LocalizedPiece(attackerPosition, attackerPiece));
					if (stopOnFirstAttackant) {
						return SquareVisitor.STOP_WALKING;
					}
				}
				if (attackerPiece != BoardUtil.NO_PIECE_CODE) {
					return SquareVisitor.STOP_DIRECTION;
				}
				return SquareVisitor.CONTINUE;
			}
		);
	}
	
	@SuppressWarnings("javadoc")
	private void getAttackersFromBishop(
			Position attackedPosition,
			byte attackerColor,
			boolean stopOnFirstAttackant,
			List<LocalizedPiece> attackers
	) {
		direcionalPiecesWalker(
			attackedPosition,
			BISHOP_DIRECTIONS,
			BoardUtil.SIZE - 1,
			(Position attackerPosition, int attackerPiece) -> {
				if (BoardUtil.isBishop(attackerPiece) && BoardUtil.getColorCode(attackerPiece) == attackerColor) {
					attackers.add(new LocalizedPiece(attackerPosition, attackerPiece));
					if (stopOnFirstAttackant) {
						return SquareVisitor.STOP_WALKING;
					}
				}
				if (attackerPiece != BoardUtil.NO_PIECE_CODE) {
					return SquareVisitor.STOP_DIRECTION;
				}
				return SquareVisitor.CONTINUE;
			}
		);
	}
	
	@SuppressWarnings("javadoc")
	private void getAttackersFromKnight(
			Position attackedPosition,
			byte attackerColor,
			boolean stopOnFirstAttackant,
			List<LocalizedPiece> attackers
	) {
		direcionalPiecesWalker(
			attackedPosition,
			KNIGHT_DIRECTIONS,
			1,
			(Position attackerPosition, int attackerPiece) -> {
				if (BoardUtil.isKnight(attackerPiece) && BoardUtil.getColorCode(attackerPiece) == attackerColor) {
					attackers.add(new LocalizedPiece(attackerPosition, attackerPiece));
					if (stopOnFirstAttackant) {
						return SquareVisitor.STOP_WALKING;
					}
				}
				return SquareVisitor.CONTINUE;
			}
		);
	}
	
	@SuppressWarnings("javadoc")
	private void getAttackersFromPawn(
			Position attackedPosition,
			byte attackerColor,
			boolean stopOnFirstAttackant,
			List<LocalizedPiece> attackers
	) {
		Direction[] pawnCaptureDirection = BoardUtil.isWhiteColor(attackerColor)
				? BLACK_PAWN_CAPTURE_DIRECTIONS
				: WHITE_PAWN_CAPTURE_DIRECTIONS;
		direcionalPiecesWalker(
			attackedPosition,
			pawnCaptureDirection,
			1,
			(Position attackerPosition, int attackerPiece) -> {
				if (BoardUtil.isPawn(attackerPiece) && BoardUtil.getColorCode(attackerPiece) == attackerColor) {
					attackers.add(new LocalizedPiece(attackerPosition, attackerPiece));
					if (stopOnFirstAttackant) {
						return SquareVisitor.STOP_WALKING;
					}
				}
				return SquareVisitor.CONTINUE;
			}
		);
	}
	
	@SuppressWarnings({
			"squid:S135",
			"javadoc"
	})
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
		 *        code representative for empty square ({@link BoardUtil#NO_PIECE_CODE}).
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
		byte visit(Position position, int pieceCode);
	}
	
	/**
	 * Retrieves the FEN string representative for this board in its current state.
	 * 
	 * @return The FEN string.
	 */
	@SuppressWarnings({
			"squid:S134",
			"squid:S3776"
	})
	public String getFen() {
		StringBuilder builder = new StringBuilder();
		for (int row = 0; row < BoardUtil.SIZE; row++) {
			if (row > 0) {
				builder.append('/');
			}
			int emptyCount = 0;
			for (int column = 0; column < BoardUtil.SIZE; column++) {
				Position position = Position.of(row, column);
				if (isEmpty(position)) {
					emptyCount++;
				} else {
					if (emptyCount > 0) {
						builder.append(emptyCount);
					}
					emptyCount = 0;
					builder.append(BoardUtil.pieceCodeToLetter(privateGetPiece(position)));
				}
			}
			if (emptyCount > 0) {
				builder.append(emptyCount);
			}
		}
		
		builder.append(' ');
		
		builder.append(BoardUtil.colorCodeToLetter(state.getSideToMove())).append(' ');
		builder.append(encodeFenCastlingFlags()).append(' ');
		if (state.getEnPassantTargetSquare() != null) {
			builder.append(state.getEnPassantTargetSquare().getAlgebraicNotation());
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
		if (BoardUtil.isWhiteKingSideCaslting(state.getCastlingFlags())) {
			builder.append(BoardUtil.WHITE_KING_LETTER);
		}
		if (BoardUtil.isWhiteQueenSideCaslting(state.getCastlingFlags())) {
			builder.append(BoardUtil.WHITE_QUEEN_LETTER);
		}
		if (BoardUtil.isBlackKingSideCaslting(state.getCastlingFlags())) {
			builder.append(BoardUtil.BLACK_KING_LETTER);
		}
		if (BoardUtil.isBlackQueenSideCaslting(state.getCastlingFlags())) {
			builder.append(BoardUtil.BLACK_QUEEN_LETTER);
		}
		return builder.length() == 0 ? "-" : builder.toString();
	}
	
	@SuppressWarnings("javadoc")
	private static BoardState createStateInfo(FenPositionInfo fenInfo) {
		BoardState state = new BoardState();
		state.setEnPassantTargetSquare(fenInfo.getEnPassantTargetSquare());
		state.setFullMoveCounter(fenInfo.getFullMoveCounter());
		state.setHalfMoveClock(fenInfo.getHalfMoveClock());
		state.setSideToMove(fenInfo.getSideToMove());
		state.setCastlingFlags(
			BoardUtil.muxCastlingFlags(
				fenInfo.isWhiteKingCastlingAvaiable(),
				fenInfo.isWhiteQueenCastlingAvaiable(),
				fenInfo.isBlackKingCastlingAvaiable(),
				fenInfo.isBlackQueenCastlingAvaiable()
			)
		);
		return state;
	}
	
	@SuppressWarnings("javadoc")
	private static boolean isInsideBoardBound(int row, int column) {
		return row >= 0 && row < BoardUtil.SIZE
				&& column >= 0 && column < BoardUtil.SIZE;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("┌───┬───┬───┬───┬───┬───┬───┬───┐").append(NEWLINE);
		for (int row = 0; row < BoardUtil.SIZE; row++) {
			for (int column = 0; column < BoardUtil.SIZE; column++) {
				Position position = Position.of(row, column);
				char letter = isEmpty(position)
						? ' '
						: BoardUtil.pieceCodeToLetter(grid[position.getRow()][position.getColumn()]);
				builder.append("│ ").append(letter).append(" ");
			}
			builder.append("│").append(NEWLINE);
			if (row == BoardUtil.SIZE - 1) {
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
