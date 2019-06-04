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
import java.util.List;

/**
 * @author Welyab Paula
 */
public class Board {

	/**
	 * Default chess board size.
	 */
	private static final int BOARD_SIZE = 8;

	/**
	 * New line.
	 */
	public static final String NEWLINE = String.format("%n");

	/**
	 * Cache for all 64 position available in a chess board.
	 */
	// @formatter:off
	private static final Position[][] POSITIONS = {
		{Position.of(0, 0),Position.of(0, 1),Position.of(0, 2),Position.of(0, 3),Position.of(0, 4),Position.of(0, 5),Position.of(0, 6),Position.of(0, 7)},
		{Position.of(1, 0),Position.of(1, 1),Position.of(1, 2),Position.of(1, 3),Position.of(1, 4),Position.of(1, 5),Position.of(1, 6),Position.of(1, 7)},
		{Position.of(2, 0),Position.of(2, 1),Position.of(2, 2),Position.of(2, 3),Position.of(2, 4),Position.of(2, 5),Position.of(2, 6),Position.of(2, 7)},
		{Position.of(3, 0),Position.of(3, 1),Position.of(3, 2),Position.of(3, 3),Position.of(3, 4),Position.of(3, 5),Position.of(3, 6),Position.of(3, 7)},
		{Position.of(4, 0),Position.of(4, 1),Position.of(4, 2),Position.of(4, 3),Position.of(4, 4),Position.of(4, 5),Position.of(4, 6),Position.of(4, 7)},
		{Position.of(5, 0),Position.of(5, 1),Position.of(5, 2),Position.of(5, 3),Position.of(5, 4),Position.of(5, 5),Position.of(5, 6),Position.of(5, 7)},
		{Position.of(6, 0),Position.of(6, 1),Position.of(6, 2),Position.of(6, 3),Position.of(6, 4),Position.of(6, 5),Position.of(6, 6),Position.of(6, 7)},
		{Position.of(7, 0),Position.of(7, 1),Position.of(7, 2),Position.of(7, 3),Position.of(7, 4),Position.of(7, 5),Position.of(7, 6),Position.of(7, 7)},
	};
	//@formatter:on

	/**
	 * The 2-dimensional array where the pieces are placed.
	 */
	private Square[][] grid;

	/**
	 * Counts how many times the pieces were moved during the game playing. Each side moved count as
	 * a movement, so the white movement followed by black movement counts as 2 movements.
	 */
	private int movementCount;

	/**
	 * The movement template for the king piece.
	 */
	private static List<DirectionAdjuster> kingMoveTemplate = Collections.unmodifiableList(
		Arrays.asList(
			new DirectionAdjuster(-1, -1),
			new DirectionAdjuster(-1, +0),
			new DirectionAdjuster(-1, +1),
			new DirectionAdjuster(+0, -1),
			new DirectionAdjuster(+0, +1),
			new DirectionAdjuster(+1, -1),
			new DirectionAdjuster(+1, +0),
			new DirectionAdjuster(+1, +1)
		)
	);

	/**
	 * The movement template for the queen piece.
	 */
	private static List<DirectionAdjuster> queenMoveTemplate = Collections.unmodifiableList(
		Arrays.asList(
			new DirectionAdjuster(-1, -1),
			new DirectionAdjuster(-1, +0),
			new DirectionAdjuster(-1, +1),
			new DirectionAdjuster(+0, -1),
			new DirectionAdjuster(+0, +1),
			new DirectionAdjuster(+1, -1),
			new DirectionAdjuster(+1, +0),
			new DirectionAdjuster(+1, +1)
		)
	);

	/**
	 * The movement template for the rook piece.
	 */
	private static List<DirectionAdjuster> rookMoveTemplate = Collections.unmodifiableList(
		Arrays.asList(
			new DirectionAdjuster(-1, +0),
			new DirectionAdjuster(+0, -1),
			new DirectionAdjuster(+0, +1),
			new DirectionAdjuster(+1, +0)
		)
	);

	/**
	 * The movement template for the bishop piece.
	 */
	private static List<DirectionAdjuster> bishopMoveTemplate = Collections.unmodifiableList(
		Arrays.asList(
			new DirectionAdjuster(-1, -1),
			new DirectionAdjuster(-1, +1),
			new DirectionAdjuster(+1, -1),
			new DirectionAdjuster(+1, +1)
		)
	);

	/**
	 * The movement template for the knight piece.
	 */
	private static List<DirectionAdjuster> knightMoveTemplate = Collections.unmodifiableList(
		Arrays.asList(
			new DirectionAdjuster(-2, +1),
			new DirectionAdjuster(-2, -1),
			new DirectionAdjuster(+2, +1),
			new DirectionAdjuster(+2, -1),
			new DirectionAdjuster(+1, +2),
			new DirectionAdjuster(-1, +2),
			new DirectionAdjuster(+1, -2),
			new DirectionAdjuster(-1, -2)
		)
	);

	/**
	 * Creates a new board with the initial peace disposition.
	 */
	public Board() {
		this(true);
	}

	/**
	 * Creates a new chess board.
	 *
	 * @param initialPosition If <code>true</code>, the board will have the pieces placed in its
	 *        initial positions.
	 */
	public Board(boolean initialPosition) {
		grid = createGrid();
		if (initialPosition) {
			addPiece(Piece.BLACK_PAWN, 1, 0);
			addPiece(Piece.BLACK_PAWN, 1, 1);
			addPiece(Piece.BLACK_PAWN, 1, 2);
			addPiece(Piece.BLACK_PAWN, 1, 3);
			addPiece(Piece.BLACK_PAWN, 1, 4);
			addPiece(Piece.BLACK_PAWN, 1, 5);
			addPiece(Piece.BLACK_PAWN, 1, 6);
			addPiece(Piece.BLACK_PAWN, 1, 7);
			addPiece(Piece.BLACK_ROOK, 0, 0);
			addPiece(Piece.BLACK_KNIGHT, 0, 1);
			addPiece(Piece.BLACK_BISHOP, 0, 2);
			addPiece(Piece.BLACK_QUEEN, 0, 3);
			addPiece(Piece.BLACK_KING, 0, 4);
			addPiece(Piece.BLACK_BISHOP, 0, 5);
			addPiece(Piece.BLACK_KNIGHT, 0, 6);
			addPiece(Piece.BLACK_ROOK, 0, 7);

			addPiece(Piece.WHITE_PAWN, 6, 0);
			addPiece(Piece.WHITE_PAWN, 6, 1);
			addPiece(Piece.WHITE_PAWN, 6, 2);
			addPiece(Piece.WHITE_PAWN, 6, 3);
			addPiece(Piece.WHITE_PAWN, 6, 4);
			addPiece(Piece.WHITE_PAWN, 6, 5);
			addPiece(Piece.WHITE_PAWN, 6, 6);
			addPiece(Piece.WHITE_PAWN, 6, 7);
			addPiece(Piece.WHITE_ROOK, 7, 0);
			addPiece(Piece.WHITE_KNIGHT, 7, 1);
			addPiece(Piece.WHITE_BISHOP, 7, 2);
			addPiece(Piece.WHITE_QUEEN, 7, 3);
			addPiece(Piece.WHITE_KING, 7, 4);
			addPiece(Piece.WHITE_BISHOP, 7, 5);
			addPiece(Piece.WHITE_KNIGHT, 7, 6);
			addPiece(Piece.WHITE_ROOK, 7, 7);
		}
	}

	/**
	 * Instantiates a board using a piece disposition informed in the parameter
	 * <code>boardString</code>. This string a sequence of 64 characters, where each character
	 * represents a piece, or a empty square.
	 *
	 * <ul>
	 * <li>'k': the black king
	 * <li>'q': the black queen
	 * <li>'r': the black rook
	 * <li>'b': the black bishop
	 * <li>'n': the black knight
	 * <li>'p': the black pawn
	 * <li>'K': the white king
	 * <li>'Q': the white queen
	 * <li>'R': the white rook
	 * <li>'B': the white bishop
	 * <li>'N': the white knight
	 * <li>'P': the white pawn
	 * <li>'.': a empty square
	 * </ul>
	 *
	 * Only these characters are valid and, optionally, spaces and new line characters.
	 *
	 * @param boardString The board disposition.
	 * @param config
	 */
	public Board(String boardString, String config) {
		grid = createGrid();
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
			int row = counter / BOARD_SIZE;
			int column = counter % BOARD_SIZE;
			counter++;
			addPiece(piece, row, column);
		}
	}

	public static void main(String[] args) {
		System.out.println(
			new Board()
		);
	}

	/**
	 * Creates a 2-dimensional array with size equals to {@link #BOARD_SIZE}.
	 *
	 * @return The array.
	 */
	private static Square[][] createGrid() {
		Square[][] g = new Square[BOARD_SIZE][BOARD_SIZE];
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {
				g[row][column] = new Square(row, column);
			}
		}
		return g;
	}

	public void addPiece(Piece piece, int row, int column) {
		Square square = getSquare(row, column);
		PieceInfo pieceInfo = new PieceInfo(piece);
		square.setPieceInfo(pieceInfo);
	}

	/**
	 * The side color that has the next movement.
	 *
	 * @return The side color.
	 */
	public Color getActiveColor() {
		return movementCount % 2 == 0 ? Color.WHITE : Color.BLACK;
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
	 * Retrieves the available movements for the piece located at given <code>[row/column]</code>
	 * position.
	 *
	 * @param row The piece position row.
	 * @param column The piece column row.
	 *
	 * @return The movements container.
	 *
	 * @throws EmptySquareException If the square at location <code>[row, column]</code> is empty.
	 */
	public Movements getMovements(int row, int column) {
		Square square = getSquare(row, column);
		if (square.isEmpty()) {
			throw new EmptySquareException(row, column);
		}

		return privateGetMovements(row, column);
	}

	/**
	 * The same method for retrieve piece available movements as {@link #getMovements(int, int)},
	 * but without verification indicated in the documentation.
	 *
	 * @param row The piece position row number.
	 * @param column The piece position column number.
	 *
	 * @return The movements object.
	 */
	private Movements privateGetMovements(int row, int column) {
		Square square = getSquare(row, column);
		if (square.getPieceInfo().getPiece().isPawn()) {
			return getPawnMovements(row, column);
		}
		return getNonPawnMovements(row, column);
	}

	/**
	 * Generated the movements for piece located in the given position indicated by
	 * <code>[row, column]</code> pair.
	 *
	 * @param row The position row number.
	 * @param column The position column number.
	 *
	 * @return The movements container.
	 */
	private Movements getNonPawnMovements(int row, int column) {
		Square square = getSquare(row, column);
		PieceInfo pieceInfo = square.getPieceInfo();
		Piece piece = pieceInfo.getPiece();
		int maxMoveLength = getMaxRadialMoveLength(piece.getType());
		List<DirectionAdjuster> directionAdjusters = getMovementTemplate(piece);
		boolean[] invalidDirection = new boolean[directionAdjusters.size()];
		List<MovementTarget> targets = new ArrayList<>();
		for (int mLength = 1; mLength <= maxMoveLength; mLength++) {
			for (int t = 0; t < directionAdjusters.size(); t++) {
				if (!invalidDirection[t]) {
					DirectionAdjuster directionAdjuster = directionAdjusters.get(t);
					int targetRow = row + mLength * directionAdjuster.getRowAdjuster();
					int targetColumn = column + mLength * directionAdjuster.getColumnAdjuster();
					if (isInsideBoard(targetRow, targetColumn)) {
						Square targetSquare = getSquare(targetRow, targetColumn);
						int moveValue = square.getValue() * targetSquare.getValue();
						if (moveValue <= 0) {
							if (moveValue < 0) {
								invalidDirection[t] = true;
							}
							if (!isKingInCheckWithMove(row, column, targetRow, targetColumn)) {
								targets.add(new MovementTarget(piece, targetRow, targetColumn));
							}
						} else {
							invalidDirection[t] = true;
						}
					} else {
						invalidDirection[t] = true;
					}
				}
			}
		}
		if (piece.isKing()) {
			targets.addAll(getCastlingTargets(row, column));
		}
		return new Movements(
			piece,
			row, column,
			targets
		);
	}

	/**
	 * Evaluates if the movement of the piece located in the square indicated by
	 * <code>[row, column]</code> to the square target square indicated by
	 * <code>[targetRow, targetColumn]</code> will provoke that the king of same color enter in
	 * check state.
	 *
	 * @param row The initial row position number of the piece.
	 * @param column The initial column position number of the piece.
	 * @param targetRow The target row position number of the piece after the movement.
	 * @param targetColumn The target column position number of the piece after the movement.
	 *
	 * @return A value <code>true</code> if the king will enter in a check state, or
	 *         <code>false</code> otherwise.
	 */
	private boolean isKingInCheckWithMove(int row, int column, int targetRow, int targetColumn) {
		Square square = getSquare(row, column);
		Color color = square.getPieceInfo().getPiece().getColor();
		if (!isKingPresent(color)) {
			return false;
		}
		Square targetSquare = getSquare(targetRow, targetColumn);
		PieceInfo temp = null;
		if (targetSquare.isNotEmpty()) {
			temp = targetSquare.getPieceInfo();
		}
		targetSquare.setPieceInfo(square.getPieceInfo());
		square.setEmpty();
		Position kingPosition = getKingPosition(color);
		boolean underAttack = isUnderAttack(
			kingPosition.getRow(),
			kingPosition.getColumn(),
			color.getOpposite()
		);
		square.setPieceInfo(targetSquare.getPieceInfo());
		targetSquare.setPieceInfo(temp);
		return underAttack;
	}

	public boolean isUnderAttack(int row, int column, Color color) {
		for (int t = 0; t < knightMoveTemplate.size(); t++) {
			int targetRow = row + knightMoveTemplate.get(t).getRowAdjuster();
			int targetColumn = column + knightMoveTemplate.get(t).getColumnAdjuster();
			if (isInsideBoard(targetRow, targetColumn)) {
				Square targetSquare = getSquare(targetRow, targetColumn);
				if (targetSquare.isNotEmpty()
						&& targetSquare.getPieceInfo().getPiece().isKnight()
						&& targetSquare.getPieceInfo().getPiece().getColor().equals(color)) {
					return true;
				}
			}
		}
		int maxMoveLength = getMaxRadialMoveLength(PieceType.QUEEN);
		boolean[] invalidDirections = new boolean[queenMoveTemplate.size()];
		for (int mLength = 1; mLength <= maxMoveLength; mLength++) {
			for (int t = 0; t < queenMoveTemplate.size(); t++) {
				if (!invalidDirections[t]) {
					DirectionAdjuster directionAdjuster = queenMoveTemplate.get(t);
					int targetRow = row + mLength * directionAdjuster.getRowAdjuster();
					int targetColumn = column + mLength * directionAdjuster.getColumnAdjuster();
					if (isInsideBoard(targetRow, targetColumn)) {
						Square targetSquare = getSquare(targetRow, targetColumn);
						if (targetSquare.isNotEmpty()) {
							PieceInfo pieceInfo = targetSquare.getPieceInfo();
							Piece targetPiece = pieceInfo.getPiece();
							if (targetPiece.getColor().equals(color)) {
								if (targetPiece.isQueen()) {
									return true;
								}
								if (targetPiece.isRook() && (row == targetRow || column == targetColumn)) {
									return true;
								} else {
									invalidDirections[t] = true;
								}
								if (targetPiece.isBishop() && row != targetRow && column != targetColumn) {
									return true;
								} else {
									invalidDirections[t] = true;
								}
								if (targetPiece.isKing() && mLength == 1) {
									return true;
								}
							} else {
								invalidDirections[t] = true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	public Position getKingPosition(Color color) {
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {
				Square square = getSquare(row, column);
				if (square.isNotEmpty()
						&& square.getPieceInfo().getPiece().isKing()
						&& square.getPieceInfo().getPiece().getColor().equals(color)) {
					return POSITIONS[row][column];
				}
			}
		}
		throw new KingNotFound(color);
	}

	public boolean isKingPresent(Color color) {
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {
				Square square = getSquare(row, column);
				if (square.isNotEmpty()
						&& square.getPieceInfo().getPiece().isKing()
						&& square.getPieceInfo().getPiece().getColor().equals(color)) {
					return true;
				}
			}
		}
		return false;
	}

	private List<MovementTarget> getCastlingTargets(int row, int column) {
		return Collections.emptyList();
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
		return row >= 0 && row < BOARD_SIZE
				&& column >= 0 && column < BOARD_SIZE;
	}

	/**
	 * Retrieves the movement template related to the given piece.
	 *
	 * @param piece The piece.
	 *
	 * @return The list of direction adjusters of the given piece.
	 */
	private List<DirectionAdjuster> getMovementTemplate(Piece piece) {
		if (piece.isKing()) {
			return kingMoveTemplate;
		}
		if (piece.isQueen()) {
			return queenMoveTemplate;
		}
		if (piece.isRook()) {
			return rookMoveTemplate;
		}
		if (piece.isBishop()) {
			return bishopMoveTemplate;
		}
		if (piece.isKnight()) {
			return knightMoveTemplate;
		}
		throw new ChessError(String.format("Invalid piece: %s", piece));
	}

	/**
	 * Retrieves the maximum movement length for an radial movement piece (all pieces, except the
	 * pawn).
	 *
	 * @param type The piece type.
	 *
	 * @return The maximum movement length.
	 *
	 * @throws ChessError If the piece has no radial movement.
	 */
	private static int getMaxRadialMoveLength(PieceType type) {
		if (type == PieceType.KING) {
			return 1;
		}
		if (type == PieceType.QUEEN) {
			return BOARD_SIZE - 1;
		}
		if (type == PieceType.ROOK) {
			return BOARD_SIZE - 1;
		}
		if (type == PieceType.BISHOP) {
			return BOARD_SIZE - 1;
		}
		if (type == PieceType.KNIGHT) {
			return 1;
		}
		throw new ChessError(String.format("Unexpected piece type: %s", type));
	}

	private Movements getPawnMovements(int row, int column) {
		return null;
	}

	/**
	 * Retrieves the square object located at the specific position indicated by
	 * <code>[row, column]</code> pair.
	 *
	 * @param row The position row number.
	 * @param column The position column number.
	 *
	 * @return The square object.
	 */
	private Square getSquare(int row, int column) {
		return grid[row][column];
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
	public String toString2() {
		StringBuilder builder = new StringBuilder();
		builder.append("┌───┬───┬───┬───┬───┬───┬───┬───┐").append(NEWLINE);
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {
				builder.append("│");
				Square square = getSquare(row, column);
				if (square.isEmpty()) {
					builder.append("   ");
				} else {
					builder
						.append(' ')
						.append(square.getPieceInfo().getPiece().getLetterSymbol())
						.append(' ');
				}
			}
			builder.append("│").append(NEWLINE);
			if (row == BOARD_SIZE - 1) {
				builder.append("└───┴───┴───┴───┴───┴───┴───┴───┘").append(NEWLINE);
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
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {
				Square square = getSquare(row, column);
				if (column > 0) {
					builder.append(' ');
				}
				if (square.isEmpty()) {
					builder.append('.');
				} else {
					builder.append(square.getPieceInfo().getPiece().getLetterSymbol());
				}
			}
			builder.append(NEWLINE);
		}
		return builder.toString();
	}

	/**
	 * @author Welyab Paula
	 */
	private static class Square {

		final int row;

		final int column;

		PieceInfo pieceInfo;

		Square(int row, int column) {
			this.row = row;
			this.column = column;
		}

		void setPieceInfo(PieceInfo piece) {
			pieceInfo = piece;
		}

		void setEmpty() {
			pieceInfo = null;
		}

		boolean isNotEmpty() {
			return !isEmpty();
		}

		boolean isEmpty() {
			return pieceInfo == null;
		}

		int getValue() {
			return isEmpty()
					? SquareContent.EMPTY
					: getPieceInfo().getPiece().getValue();
		}

		PieceInfo getPieceInfo() {
			if (isEmpty()) {
				throw new EmptySquareException(row, column);
			}
			return pieceInfo;
		}
	}

	/**
	 * This class helps to track piece movement and other information during game playing.
	 *
	 * @author Welyab Paula
	 */
	private static class PieceInfo {

		/**
		 * The underlying piece associated with this informational set.
		 */
		Piece piece;

		/**
		 * Creates a new <code>PieceInfo</code> instance.
		 *
		 * @param piece The piece.
		 */
		PieceInfo(Piece piece) {
			this.piece = piece;
		}

		/**
		 * Retrieves the piece associated with this informational set.
		 *
		 * @return The piece.
		 */
		Piece getPiece() {
			return piece;
		}
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
	private static class DirectionAdjuster {

		/**
		 * The row adjuster value.
		 */
		final int rowAdjuster;

		/**
		 * The column adjuster value.
		 */
		final int columnAdjsuter;

		/**
		 * Creates a direction adjuster instance using given values.
		 *
		 * @param rowAdjuster The row adjuster value.
		 * @param columnAdjsuter The column adjuster value.
		 */
		DirectionAdjuster(int rowAdjuster, int columnAdjsuter) {
			this.rowAdjuster = rowAdjuster;
			this.columnAdjsuter = columnAdjsuter;
		}

		/**
		 * Retrieves the row adjuster value.
		 *
		 * @return The row adjsuter value.
		 */
		int getRowAdjuster() {
			return rowAdjuster;
		}

		/**
		 * Retrieves the column adjuster value.
		 *
		 * @return The column adjuster value.
		 */
		int getColumnAdjuster() {
			return columnAdjsuter;
		}
	}
}
