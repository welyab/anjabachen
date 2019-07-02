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
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.welyab.anjabachen.PieceMovementMeta.Builder;

/**
 * @author Welyab Paula
 */
public class Board {

	/**
	 * The king initial column number position.
	 */
	private static final int KING_INITIAL_COLUMN = 4;

	/**
	 * The black king initial row number position.
	 */
	private static final int BLACK_KING_INITIAL_ROW = 0;

	/**
	 * The white king initial row number position.
	 */
	private static final int WHITE_KING_INITIAL_ROW = 7;

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

	private static final int BLACK_PAWN_PROMOTION_ROW = 7;

	private static final int WHITE_PAWN_PROMOTION_ROW = 0;

	private static final int BLACK_PAWN_INITIAL_ROW = 1;

	private static final int WHITE_PAWN_INITIAL_ROW = 6;

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
	 * The 2-dimensional array where the pieces are placed.
	 */
	private Square[][] grid;

	private List<MoveLog> logs;

	/**
	 * Counts how many times the pieces were moved during the game playing. Each side moved count as
	 * a movement, so the white movement followed by black movement counts as 2 movements.
	 */
	private int movementCount;

	/**
	 * The total number of movement operations performed in this board. Works just like
	 * {@link #movementCount}, but this counter do not have its value decremented.
	 */
	int movementOperationCount;

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
	 * The movement template for the black pawn.
	 */
	private static List<DirectionAdjuster> blackPawnMoveTemplate = Collections.unmodifiableList(
		Arrays.asList(
			new DirectionAdjuster(+1, +0),
			new DirectionAdjuster(+2, +0),
			new DirectionAdjuster(+1, +1),
			new DirectionAdjuster(+1, -1)
		)
	);

	/**
	 * The movement template for the white pawn.
	 */
	private static List<DirectionAdjuster> whitePawnMoveTemplate = Collections.unmodifiableList(
		Arrays.asList(
			new DirectionAdjuster(-1, +0),
			new DirectionAdjuster(-2, +0),
			new DirectionAdjuster(-1, +1),
			new DirectionAdjuster(-1, -1)
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
			addPiece(Piece.BLACK_PAWN, Position.of(1, 0));
			addPiece(Piece.BLACK_PAWN, Position.of(1, 1));
			addPiece(Piece.BLACK_PAWN, Position.of(1, 2));
			addPiece(Piece.BLACK_PAWN, Position.of(1, 3));
			addPiece(Piece.BLACK_PAWN, Position.of(1, 4));
			addPiece(Piece.BLACK_PAWN, Position.of(1, 5));
			addPiece(Piece.BLACK_PAWN, Position.of(1, 6));
			addPiece(Piece.BLACK_PAWN, Position.of(1, 7));
			addPiece(Piece.BLACK_ROOK, Position.of(0, 0));
			addPiece(Piece.BLACK_KNIGHT, Position.of(0, 1));
			addPiece(Piece.BLACK_BISHOP, Position.of(0, 2));
			addPiece(Piece.BLACK_QUEEN, Position.of(0, 3));
			addPiece(Piece.BLACK_KING, Position.of(0, 4));
			addPiece(Piece.BLACK_BISHOP, Position.of(0, 5));
			addPiece(Piece.BLACK_KNIGHT, Position.of(0, 6));
			addPiece(Piece.BLACK_ROOK, Position.of(0, 7));

			addPiece(Piece.WHITE_PAWN, Position.of(6, 0));
			addPiece(Piece.WHITE_PAWN, Position.of(6, 1));
			addPiece(Piece.WHITE_PAWN, Position.of(6, 2));
			addPiece(Piece.WHITE_PAWN, Position.of(6, 3));
			addPiece(Piece.WHITE_PAWN, Position.of(6, 4));
			addPiece(Piece.WHITE_PAWN, Position.of(6, 5));
			addPiece(Piece.WHITE_PAWN, Position.of(6, 6));
			addPiece(Piece.WHITE_PAWN, Position.of(6, 7));
			addPiece(Piece.WHITE_ROOK, Position.of(7, 0));
			addPiece(Piece.WHITE_KNIGHT, Position.of(7, 1));
			addPiece(Piece.WHITE_BISHOP, Position.of(7, 2));
			addPiece(Piece.WHITE_QUEEN, Position.of(7, 3));
			addPiece(Piece.WHITE_KING, Position.of(7, 4));
			addPiece(Piece.WHITE_BISHOP, Position.of(7, 5));
			addPiece(Piece.WHITE_KNIGHT, Position.of(7, 6));
			addPiece(Piece.WHITE_ROOK, Position.of(7, 7));
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
	public Board(String boardString, BoardConfig boardConfig) {
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
			int row = counter / Constants.BOARD_SIZE;
			int column = counter % Constants.BOARD_SIZE;
			counter++;
			addPiece(piece, Position.of(row, column));
			if (piece.isPawn() && row != getPawnInitialRow(piece.getColor())) {
				getSquare(Position.of(row, column)).getPieceInfo().incrementMovementCount();
			}
		}
		movementCount = movementCount;
	}

	public static void main(String[] args) {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + "r . . . k . . r" + Board.NEWLINE
				/* 1 */ + "p . p p q p b ." + Board.NEWLINE
				/* 2 */ + "b n . . p n p ." + Board.NEWLINE
				/* 3 */ + ". . . P N . . ." + Board.NEWLINE
				/* 4 */ + ". p . . P . . ." + Board.NEWLINE
				/* 5 */ + ". . N . . Q . p" + Board.NEWLINE
				/* 6 */ + "P P P B B P P P" + Board.NEWLINE
				/* 7 */ + "R . . . K . . R" + Board.NEWLINE;
		Board board = new Board(boardString, 0);
		int deepth = 4;
		PieceMovementMeta expectedMeta = PieceMovementMeta
			.builder()
			.incrementTotalMovements(4085603)
			.incrementCaptureCount(757163)
			.incrementPromotionCount(15172)
			.incrementCastlings(128013)
			.incrementEnPassantCount(1929)
			.build();
		PieceMovementMeta meta = calc(board, deepth);
		System.out.printf("Deepth:           %10d%n", deepth);
		System.out.printf(
			"Nodes:            %10d  %d%n",
			meta.getTotalMovements(),
			meta.getTotalMovements() - expectedMeta.getTotalMovements()
		);
		System.out.printf(
			"Capture count:    %10d  %d%n",
			meta.getCaptureCount(),
			meta.getCaptureCount() - expectedMeta.getCaptureCount()
		);
		System.out.printf(
			"Promotion count:  %10d  %d%n",
			meta.getPromotionCount(),
			meta.getPromotionCount() - expectedMeta.getPromotionCount()
		);
		System.out.printf(
			"Castling count:   %10d  %d%n",
			meta.getCastlingsCount(),
			meta.getCastlingsCount() - expectedMeta.getCastlingsCount()
		);
		System.out.printf(
			"En passant count: %10d  %d%n",
			meta.getEnPassantCount(),
			meta.getEnPassantCount() - expectedMeta.getEnPassantCount()
		);
	}

	static int b = 0;

	static int k = 0;

	private static PieceMovementMeta calc(Board board, int depth) {
		if (depth == 1) {
			PieceMovements movements = board.getMovements();
			k += movements.getMeta().getTotalMovements();
			b += movements.getMeta().getTotalMovements();
			if (b > 5000000) {
				b = 0;
				System.out.println("Current count: " + k);
			}
			return movements.getMeta();
		}
		PieceMovementMeta.Builder pieceMovementMetaBuilder = PieceMovementMeta.builder();
		for (PieceMovement pieceMovement : board.getMovements()) {
			for (MovementTarget movementTarget : pieceMovement) {
				Board boardTemp = new Board(
					board.toString2(),
					board.getMovementCount()
				);
				boardTemp.move(pieceMovement, movementTarget);
				PieceMovementMeta pieceMovementMeta = calc(boardTemp, depth - 1);
				pieceMovementMetaBuilder.add(pieceMovementMeta);
			}
		}
		return pieceMovementMetaBuilder.build();
	}

	/**
	 * Creates a 2-dimensional array with size equals to {@link Constants#BOARD_SIZE}.
	 *
	 * @return The array.
	 */
	private static Square[][] createGrid() {
		Square[][] g = new Square[Constants.BOARD_SIZE][Constants.BOARD_SIZE];
		for (int row = 0; row < Constants.BOARD_SIZE; row++) {
			for (int column = 0; column < Constants.BOARD_SIZE; column++) {
				g[row][column] = new Square(Position.of(row, column));
			}
		}
		return g;
	}

	public void addPiece(Piece piece, Position position) {
		Square square = getSquare(position);
		PieceInfo pieceInfo = new PieceInfo(piece);
		square.setPieceInfo(pieceInfo);
	}

	public void move(Position originPosition, Position targetPosition, int targetColumn) {
		move(originPosition, targetPosition, PieceType.QUEEN);
	}

	public void move(Position originPosition, Position targetPosition, PieceType toPromotePawn) {
		toPromotePawn = toPromotePawn == null ? PieceType.QUEEN : toPromotePawn;
		PieceMovement movements = getMovements(originPosition);
		MovementTarget target = null;
		for (int i = 0; i < movements.size(); i++) {
			MovementTarget currentTarget = movements.getTarget(i);
			if (currentTarget.getPosition().getRow() == targetPosition.getRow()
					&& currentTarget.getPosition().getColumn() == targetPosition.getColumn()
					&& currentTarget.getMeta().isPromotion()
					&& currentTarget.getPiece().getType().equals(toPromotePawn)
					|| !currentTarget.getMeta().isPromotion()) {
				target = currentTarget;
				break;
			}
		}

		if (target == null) {
			throw new MovementException(
				String.format(
					"The piece %s located in [%d, %d] can't reach the location [%d, %d]",
					getSquare(originPosition).getPieceInfo().getPiece(),
					originPosition.getRow(), originPosition.getColumn(),
					targetPosition.getRow(), targetPosition.getRow()
				)
			);
		}

		move(movements, target);
	}

	private void move(PieceMovement pieceMovement, MovementTarget movementTarget) {
		Square originSquare = getSquare(pieceMovement.getPosition());
		PieceInfo originPieceInfo = originSquare.getPieceInfo();
		originSquare.setEmpty();

		Square targetSquare = getSquare(movementTarget.getPosition());

		targetSquare.setPieceInfo(originPieceInfo);
		targetSquare.getPieceInfo().setPiece(movementTarget.getPiece());

		if (movementTarget.getMeta().isCastling()) {
			Square targetRookSquare = null;
			Square originRookSquare = null;
			if (pieceMovement.getPosition().getColumn() < movementTarget.getPosition().getColumn()) {
				targetRookSquare = getSquare(
					Position.of(
						pieceMovement.getPosition().getRow(),
						pieceMovement.getPosition().getColumn() + 1
					)
				);
				originRookSquare = getSquare(
					Position.of(
						pieceMovement.getPosition().getRow(),
						7
					)
				);
			} else {
				targetRookSquare = getSquare(
					Position.of(
						pieceMovement.getPosition().getRow(),
						pieceMovement.getPosition().getColumn() - 1
					)
				);
				originRookSquare = getSquare(
					Position.of(
						pieceMovement.getPosition().getRow(),
						0
					)
				);
			}

			// try {
			targetRookSquare.setPieceInfo(originRookSquare.getPieceInfo());
			// } catch (EmptySquareException e) {
			// System.out.println(e.getMessage());
			// System.out.println(pieceMovement);
			// System.out.println(movementTarget);
			// System.out.println(this);
			// System.exit(0);
			// return;
			// }
			originRookSquare.setEmpty();

			targetRookSquare.getPieceInfo().incrementMovementCount();
		}

		if (movementTarget.getMeta().isEnPassant()) {
			getSquare(
				Position.of(
					originSquare.getPosition().getRow(),
					targetSquare.getPosition().getColumn()
				)
			).setEmpty();
		}

		incrementMovementCount();
		targetSquare.getPieceInfo().incrementMovementCount();
		targetSquare.getPieceInfo().setLastMovementCount(getMovementCount());
	}

	private void incrementMovementCount() {
		movementCount++;
	}

	public int getMovementCount() {
		return movementCount;
	}

	private void incrementMovementOperationCount() {
		movementOperationCount++;
	}

	/**
	 * The side color that has the next movement.
	 *
	 * @return The side color.
	 */
	public Color getActiveColor() {
		return movementCount % 2 == 0 ? Color.WHITE : Color.BLACK;
	}

	public boolean isCheck() {
		if (!isKingPresent(getActiveColor())) {
			return false;
		}
		Position kingPosition = getKingPosition(getActiveColor());
		return isUnderAttack(
			kingPosition,
			getWaitingColor()
		);
	}

	public boolean isCheckmate() {
		return isCheck()
				&& getMovements().isEmpty();
	}

	public boolean isStalemate() {
		return !isCheck()
				&& getMovements().isEmpty();
	}

	/**
	 * The side color that is waiting for the opponent movement.
	 *
	 * @return The side color.
	 */
	public Color getWaitingColor() {
		return getActiveColor().getOpposite();
	}

	public PieceMovements getMovements() {
		Iterable<Square> squares = this::privateIterator;
		List<PieceMovement> movements = new ArrayList<>(32);
		PieceMovementMeta.Builder pieceMovementMetaBuilder = PieceMovementMeta.builder();
		for (Square square : squares) {
			if (square.isNotEmpty() && square.getPieceInfo().getPiece().getColor().equals(getActiveColor())) {
				PieceMovement pieceMovement = privateGetMovements(square.getPosition());
				if (pieceMovement.isNotEmpty()) {
					movements.add(pieceMovement);
					pieceMovementMetaBuilder.add(pieceMovement.getMeta());
				}
			}
		}
		return new PieceMovements(movements, pieceMovementMetaBuilder.build());
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
	public PieceMovement getMovements(Position position) {
		Square square = getSquare(position);
		if (square.isEmpty()) {
			throw new EmptySquareException(position.getRow(), position.getColumn());
		}
		return privateGetMovements(position);
	}

	private Stream<Square> privateStream() {
		return StreamSupport.stream(
			Spliterators.spliterator(privateIterator(), Constants.SQUARES_COUNT, 0),
			false
		);
	}

	private Iterator<Square> privateIterator() {
		return new Iterator<>() {

			final int instantMovementOperationCount = getMovementOperationCount();

			int index = 0;

			@Override
			public boolean hasNext() {
				return index < Constants.SQUARES_COUNT;
			}

			@Override
			public Square next() {
				if (!hasNext()) {
					throw new NoSuchElementException("No more squares");
				}
				if (instantMovementOperationCount != getMovementOperationCount()) {
					throw new ConcurrentModificationException(
						"The board have had changed its state during this iteration"
					);
				}
				int row = index / Constants.BOARD_SIZE;
				int column = index % Constants.BOARD_SIZE;
				index++;
				return getSquare(Position.of(row, column));
			}
		};
	}

	private int getMovementOperationCount() {
		return movementOperationCount;
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
	private PieceMovement privateGetMovements(Position position) {
		Square square = getSquare(position);
		if (square.getPieceInfo().getPiece().isPawn()) {
			return getPawnMovements(position);
		}
		return getNonPawnMovements(position);
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
	private PieceMovement getNonPawnMovements(Position position) {
		Square square = getSquare(position);
		PieceInfo pieceInfo = square.getPieceInfo();
		Piece piece = pieceInfo.getPiece();
		int maxMoveLength = getMaxPieceMovementLength(piece.getType());
		List<DirectionAdjuster> directionAdjusters = getMovementTemplate(piece);
		boolean[] invalidDirection = new boolean[directionAdjusters.size()];
		List<MovementTarget> targets = new ArrayList<>();
		Builder pieceMovementMetaBuilder = PieceMovementMeta.builder();
		for (int mLength = 1; mLength <= maxMoveLength; mLength++) {
			for (int t = 0; t < directionAdjusters.size(); t++) {
				if (!invalidDirection[t]) {
					DirectionAdjuster directionAdjuster = directionAdjusters.get(t);
					int targetRow = position.getRow() + mLength * directionAdjuster.getRowAdjuster();
					int targetColumn = position.getColumn() + mLength * directionAdjuster.getColumnAdjuster();
					if (isInsideBoard(targetRow, targetColumn)) {
						Square targetSquare = getSquare(Position.of(targetRow, targetColumn));
						int moveValue = square.getValue() * targetSquare.getValue();
						if (moveValue <= 0) {
							if (moveValue < 0) {
								invalidDirection[t] = true;
							}
							if (!isKingInCheckWithMove(position, Position.of(targetRow, targetColumn))) {
								MovementTargetMeta.Builder movementTargetMetaBuilder = MovementTargetMeta.builder();
								if (moveValue < 0) {
									pieceMovementMetaBuilder.incrementCaptureCount();
									movementTargetMetaBuilder.setCapture(true);
								}
								pieceMovementMetaBuilder.incrementTotalMovements();
								targets.add(
									new MovementTarget(
										piece,
										Position.of(targetRow, targetColumn),
										movementTargetMetaBuilder.build()
									)
								);
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
			List<MovementTarget> castlingTargets = getCastlingTargets(position);
			pieceMovementMetaBuilder.incrementTotalMovements(castlingTargets.size());
			pieceMovementMetaBuilder.incrementCastlings(castlingTargets.size());
			targets.addAll(castlingTargets);
		}
		return new PieceMovement(
			piece,
			position,
			targets,
			pieceMovementMetaBuilder.build()
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
	private boolean isKingInCheckWithMove(Position originPosition, Position targetPosition) {
		Square square = getSquare(originPosition);
		Color color = square.getPieceInfo().getPiece().getColor();
		if (!isKingPresent(color)) {
			return false;
		}
		Square targetSquare = getSquare(targetPosition);
		PieceInfo temp = null;
		if (targetSquare.isNotEmpty()) {
			temp = targetSquare.getPieceInfo();
		}
		targetSquare.setPieceInfo(square.getPieceInfo());
		square.setEmpty();
		Position kingPosition = getKingPosition(color);
		boolean underAttack = isUnderAttack(
			kingPosition,
			color.getOpposite()
		);
		square.setPieceInfo(targetSquare.getPieceInfo());
		targetSquare.setPieceInfo(temp);
		return underAttack;
	}

	public boolean isUnderAttack(Position position, Color color) {
		if (isUnderAttackByKnight(position, color)) {
			return true;
		}
		if (isUnderAttackByPawn(position, color)) {
			return true;
		}
		int maxMoveLength = getMaxPieceMovementLength(PieceType.QUEEN);
		boolean[] invalidDirections = new boolean[queenMoveTemplate.size()];
		for (int mLength = 1; mLength <= maxMoveLength; mLength++) {
			for (int t = 0; t < queenMoveTemplate.size(); t++) {
				if (!invalidDirections[t]) {
					DirectionAdjuster directionAdjuster = queenMoveTemplate.get(t);
					int targetRow = position.getRow() + mLength * directionAdjuster.getRowAdjuster();
					int targetColumn = position.getColumn() + mLength * directionAdjuster.getColumnAdjuster();
					if (isInsideBoard(targetRow, targetColumn)) {
						Square targetSquare = getSquare(Position.of(targetRow, targetColumn));
						if (targetSquare.isNotEmpty()) {
							PieceInfo pieceInfo = targetSquare.getPieceInfo();
							Piece targetPiece = pieceInfo.getPiece();
							if (targetPiece.getColor().equals(color)
									&& (targetPiece.isQueen()
											|| targetPiece.isRook() && (position.getRow() == targetRow
													|| position.getColumn() == targetColumn)
											|| targetPiece.isBishop() && position.getRow() != targetRow
													&& position.getColumn() != targetColumn
											|| targetPiece.isKing() && mLength == 1)) {
								return true;
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

	private boolean isUnderAttackByPawn(Position position, Color color) {
		int direction = color.isWhite()
				? -1
				: 1;
		for (int i = -1; i <= 1; i += 2) {
			int targetRow = position.getRow() - direction;
			int targetColumn = position.getColumn() + i;
			if (isInsideBoard(targetRow, targetColumn)) {
				Square targetSquare = getSquare(Position.of(targetRow, targetColumn));
				if (targetSquare.isNotEmpty()) {
					Piece piece = targetSquare.getPieceInfo().getPiece();
					if (piece.isPawn() && piece.getColor().equals(color)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean isUnderAttackByKnight(Position position, Color color) {
		for (int t = 0; t < knightMoveTemplate.size(); t++) {
			int targetRow = position.getRow() + knightMoveTemplate.get(t).getRowAdjuster();
			int targetColumn = position.getColumn() + knightMoveTemplate.get(t).getColumnAdjuster();
			if (isInsideBoard(targetRow, targetColumn)) {
				Square targetSquare = getSquare(Position.of(targetRow, targetColumn));
				if (targetSquare.isNotEmpty()
						&& targetSquare.getPieceInfo().getPiece().isKnight()
						&& targetSquare.getPieceInfo().getPiece().getColor().equals(color)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Retrieves the position of the king of the specific color.
	 *
	 * @param color The color of the required king piece.
	 *
	 * @return The position of the king.
	 *
	 * @throws KingNotFound If the king piece is not in the board.
	 *
	 * @see #isKingPresent(Color)
	 */
	public Position getKingPosition(Color color) {
		for (int row = 0; row < Constants.BOARD_SIZE; row++) {
			for (int column = 0; column < Constants.BOARD_SIZE; column++) {
				Square square = getSquare(Position.of(row, column));
				if (square.isNotEmpty()
						&& square.getPieceInfo().getPiece().isKing()
						&& square.getPieceInfo().getPiece().getColor().equals(color)) {
					return Position.of(row, column);
				}
			}
		}
		throw new KingNotFound(color);
	}

	/**
	 * Evaluates if the king piece of the specific color is present in the board. This board
	 * implementation allow some operation to be done without king piece.
	 *
	 * @param color The color of required king.
	 *
	 * @return A value <code>true</code> if the king is present, or <code>false</code> otherwise.
	 */
	public boolean isKingPresent(Color color) {
		for (int row = 0; row < Constants.BOARD_SIZE; row++) {
			for (int column = 0; column < Constants.BOARD_SIZE; column++) {
				Square square = getSquare(Position.of(row, column));
				if (square.isNotEmpty()
						&& square.getPieceInfo().getPiece().isKing()
						&& square.getPieceInfo().getPiece().getColor().equals(color)) {
					return true;
				}
			}
		}
		return false;
	}

	private List<MovementTarget> getCastlingTargets(Position position) {
		Square kingSquare = getSquare(position);
		PieceInfo kingInfo = kingSquare.getPieceInfo();
		if (kingInfo.getMovementCount() > 0) {
			return Collections.emptyList();
		}
		Piece king = kingInfo.getPiece();
		if (!getKingInitialPosition(king.getColor()).equals(position)) {
			return Collections.emptyList();
		}
		Square[] rookSquares = new Square[] {
				getKingSideRookSquare(king.getColor()),
				getQueenSideRookSquare(king.getColor())
		};
		List<MovementTarget> targets = new ArrayList<>();
		for (Square rookSquare : rookSquares) {
			if (rookSquare.isNotEmpty()) {
				PieceInfo rookInfo = rookSquare.getPieceInfo();
				if (rookInfo.getMovementCount() == 0) {
					Piece rook = rookInfo.getPiece();
					if (rook.isRook() && rook.getColor().equals(king.getColor())) {
						int direction = Integer.signum(rookSquare.getPosition().getColumn() - position.getColumn());
						int tLength = 0;
						boolean invalidCastling = false;
						while (!invalidCastling
								&& tLength < Math.abs(rookSquare.getPosition().getColumn() - position.getColumn())) {
							int targetColumn = position.getColumn() + tLength * direction;
							if (isUnderAttack(
								Position.of(position.getRow(), targetColumn), king.getColor().getOpposite()
							)) {
								invalidCastling = true;
							}
							if (tLength > 0 && getSquare(Position.of(position.getRow(), targetColumn)).isNotEmpty()) {
								invalidCastling = true;
							}
							tLength++;
						}
						if (!invalidCastling) {
							int targetKingColumn = position.getColumn() + 2 * direction;
							targets.add(
								new MovementTarget(
									king,
									Position.of(position.getRow(), targetKingColumn),
									MovementTargetMeta
										.builder()
										.setCastling(true)
										.build()
								)
							);
						}
					}
				}
			}
		}
		return targets;
	}

	private Square getKingSideRookSquare(Color color) {
		return color.isWhite()
				? getSquare(Position.of(WHITE_ROOK_INITIAL_ROW, KING_SIDE_ROOK_INITIAL_COLUMN))
				: getSquare(Position.of(BLACK_ROOK_INITIAL_ROW, KING_SIDE_ROOK_INITIAL_COLUMN));
	}

	private Square getQueenSideRookSquare(Color color) {
		return color.isWhite()
				? getSquare(Position.of(WHITE_ROOK_INITIAL_ROW, QUEEN_SIDE_ROOK_INITIAL_COLUMN))
				: getSquare(Position.of(BLACK_ROOK_INITIAL_ROW, QUEEN_SIDE_ROOK_INITIAL_COLUMN));
	}

	/**
	 * Retrieves the king piece initial position for the specific color side.
	 *
	 * @param color The color of the king.
	 *
	 * @return The initial position of the king piece.
	 */
	private Position getKingInitialPosition(Color color) {
		return color.isWhite()
				? Position.of(WHITE_KING_INITIAL_ROW, KING_INITIAL_COLUMN)
				: Position.of(BLACK_KING_INITIAL_ROW, KING_INITIAL_COLUMN);
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
		return row >= 0 && row < Constants.BOARD_SIZE
				&& column >= 0 && column < Constants.BOARD_SIZE;
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
		if (piece.isBlackPawn()) {
			return blackPawnMoveTemplate;
		}
		if (piece.isWhitePawn()) {
			return whitePawnMoveTemplate;
		}
		throw new ChessError(String.format("Invalid piece: %s", piece));
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
			return Constants.BOARD_SIZE - 1;
		}
		if (type == PieceType.ROOK) {
			return Constants.BOARD_SIZE - 1;
		}
		if (type == PieceType.BISHOP) {
			return Constants.BOARD_SIZE - 1;
		}
		if (type == PieceType.KNIGHT) {
			return 1;
		}
		throw new ChessError(String.format("Unexpected piece type: %s", type));
	}

	private PieceMovement getPawnMovements(Position position) {
		Square pawnSquare = getSquare(position);
		PieceInfo pawnInfo = pawnSquare.getPieceInfo();
		Piece pawn = pawnInfo.getPiece();
		List<DirectionAdjuster> directionAdjusters = getMovementTemplate(pawn);
		List<MovementTarget> targets = new ArrayList<>();
		PieceMovementMeta.Builder pieceMovementMetaBuilder = PieceMovementMeta.builder();
		for (DirectionAdjuster directionAjduster : directionAdjusters) {
			int targetRow = position.getRow() + directionAjduster.getRowAdjuster();
			int targetColumn = position.getColumn() + directionAjduster.getColumnAdjuster();
			if (isInsideBoard(targetRow, targetColumn)) {
				Square targetSquare = getSquare(Position.of(targetRow, targetColumn));
				boolean isMovementForward = isValidPawnMovementForward(pawnSquare, targetSquare);
				boolean isCapture = isValidPawnCaptureMovement(pawnSquare, targetSquare);
				if ((isMovementForward || isCapture)
						&& !isKingInCheckWithMove(position, targetSquare.getPosition())) {
					boolean isPawnPromotion = targetRow == getPawnPromotionRow(pawn.getColor());
					List<PieceType> targetPieces = isPawnPromotion
							? pawnPromotionReplacements
							: Arrays.asList(PieceType.PAWN);
					for (PieceType replacement : targetPieces) {
						MovementTargetMeta.Builder movementTargetMetaBuilder = MovementTargetMeta.builder();
						if (isCapture) {
							movementTargetMetaBuilder.setCapture(isCapture);
							pieceMovementMetaBuilder.incrementCaptureCount();
							if (targetSquare.isEmpty()) {
								movementTargetMetaBuilder.setEnPassant(true);
								pieceMovementMetaBuilder.incrementEnPassantCount();
							}
						}
						if (isPawnPromotion) {
							movementTargetMetaBuilder.setPromotion(true);
							pieceMovementMetaBuilder.incrementPromotionCount();
						}
						pieceMovementMetaBuilder.incrementTotalMovements();
						targets.add(
							new MovementTarget(
								Piece.get(replacement, pawnSquare.getPieceInfo().getPiece().getColor()),
								targetSquare.getPosition(),
								movementTargetMetaBuilder.build()
							)
						);
					}
				}
			}
		}
		return new PieceMovement(
			pawn,
			position,
			targets,
			pieceMovementMetaBuilder.build()
		);
	}

	private int getPawnPromotionRow(Color color) {
		return color.isWhite()
				? WHITE_PAWN_PROMOTION_ROW
				: BLACK_PAWN_PROMOTION_ROW;
	}

	private boolean isValidPawnCaptureMovement(Square pawnSquare, Square targetSquare) {
		if (pawnSquare.getPosition().getColumn() == targetSquare.getPosition().getColumn()) {
			return false;
		}
		int moveValue = pawnSquare.getValue() * targetSquare.getValue();
		if (moveValue > 0) {
			return false;
		}
		if (moveValue == 0) {
			Square square = getSquare(
				Position.of(
					pawnSquare.getPosition().getRow(),
					targetSquare.getPosition().getColumn()
				)
			);
			int enPassatMoveValue = pawnSquare.getValue() * square.getValue();
			if (enPassatMoveValue >= 0) {
				return false;
			}
			PieceInfo pieceInfo = square.getPieceInfo();
			if (!pieceInfo.getPiece().isPawn()
					|| pawnSquare.getPosition()
						.getRow() != getEnPassantRow(pawnSquare.getPieceInfo().getPiece().getColor())
					|| pieceInfo.getMovementCount() != 1
					|| pieceInfo.getLastMovementCount() != getMovementCount()) {
				return false;
			}
		}
		return true;
	}

	private int getEnPassantRow(Color color) {
		return color.isBlack()
				? 4
				: 3;
	}

	private boolean isValidPawnMovementForward(Square pawnSquare, Square targetSquare) {
		if (targetSquare.isNotEmpty()) {
			return false;
		}
		if (pawnSquare.getPosition().getColumn() != targetSquare.getPosition().getColumn()) {
			return false;
		}
		if (Math.abs(pawnSquare.getPosition().getRow() - targetSquare.getPosition().getRow()) == 2) {
			int pawnInitialRow = getPawnInitialRow(pawnSquare.getPieceInfo().getPiece().getColor());
			if (pawnInitialRow != pawnSquare.getPosition().getRow()) {
				return false;
			}
			int midRow = (pawnSquare.getPosition().getRow() + targetSquare.getPosition().getRow()) / 2;
			Square midSquare = getSquare(Position.of(midRow, pawnSquare.getPosition().getColumn()));
			if (midSquare.isNotEmpty()) {
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
	 * Retrieves the square object located at the specific position indicated by
	 * <code>[row, column]</code> pair.
	 *
	 * @param row The position row number.
	 * @param column The position column number.
	 *
	 * @return The square object.
	 */
	private Square getSquare(Position position) {
		return grid[position.getRow()][position.getColumn()];
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
		StringBuilder builder = new StringBuilder();
		builder.append("┌───┬───┬───┬───┬───┬───┬───┬───┐").append(NEWLINE);
		for (int row = 0; row < Constants.BOARD_SIZE; row++) {
			for (int column = 0; column < Constants.BOARD_SIZE; column++) {
				builder.append("│");
				Square square = getSquare(Position.of(row, column));
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
			if (row == Constants.BOARD_SIZE - 1) {
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
	 *
	 * @return The board drawing.
	 */
	public String toString2() {
		StringBuilder builder = new StringBuilder();
		for (int row = 0; row < Constants.BOARD_SIZE; row++) {
			for (int column = 0; column < Constants.BOARD_SIZE; column++) {
				Square square = getSquare(Position.of(row, column));
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
	 * An auxiliary class to represent a board square. This class has some method to determine if
	 * the square is empty, etc.
	 *
	 * @author Welyab Paula
	 */
	private static class Square {

		final Position position;

		/**
		 * The current piece located in this square. An <code>null</code> value indicates a empty
		 * square.
		 */
		PieceInfo pieceInfo;

		/**
		 * Instantiates a square for the for the given location.
		 *
		 * @param row The square position row number.
		 * @param column The square position column number.
		 */
		Square(Position position) {
			this.position = position;
		}

		/**
		 * Adjusts the piece that is currently occupying this square. A <code>null</code> indicates
		 * that the square is empty.
		 *
		 * @param piece The piece. Or <code>null</code> if the square is empty.
		 */
		void setPieceInfo(PieceInfo piece) {
			pieceInfo = piece;
		}

		/**
		 * Adjusts this square to be interpreted as empty square.
		 */
		void setEmpty() {
			pieceInfo = null;
		}

		/**
		 * Evaluates if this square is not empty.
		 *
		 * @return A value <code>true</code> if the square is not empty, or <code>false</code>
		 *         otherwise.
		 */
		boolean isNotEmpty() {
			return !isEmpty();
		}

		/**
		 * Evaluates if this square is empty.
		 *
		 * @return A value <code>true</code> if the square is empty, or <code>false</code>
		 *         otherwise.
		 */
		boolean isEmpty() {
			return pieceInfo == null;
		}

		Position getPosition() {
			return position;
		}

		/**
		 * Retrieves the square content code for its current state.
		 *
		 * @return The square content value.
		 *
		 * @see SquareContent
		 */
		int getValue() {
			return isEmpty()
					? SquareContent.EMPTY
					: getPieceInfo().getPiece().getValue();
		}

		/**
		 * Retrieves the piece information for this piece.
		 *
		 * @return The piece info.
		 *
		 * @throws EmptySquareException If the square is empty.
		 *
		 * @see #isEmpty()
		 * @see #isNotEmpty()
		 */
		PieceInfo getPieceInfo() {
			if (isEmpty()) {
				throw new EmptySquareException(position.getRow(), position.getColumn());
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
		 * The total number of times that the underlying was moved.
		 */
		int movementCount;

		private int lastMovementCount;

		/**
		 * Creates a new <code>PieceInfo</code> instance.
		 *
		 * @param piece The piece.
		 */
		PieceInfo(Piece piece) {
			this.piece = piece;
		}

		void setLastMovementCount(int lastMovementCount) {
			this.lastMovementCount = lastMovementCount;
		}

		int getLastMovementCount() {
			return lastMovementCount;
		}

		/**
		 * Increments by one the movement counter of this piece.
		 */
		void incrementMovementCount() {
			movementCount++;
		}

		/**
		 * Adjusts the underlying piece of this informational object. This method is generally used
		 * during a pawn promotion movement, when the pawn piece turns into another piece.
		 *
		 * @param piece The replacement piece.
		 */
		void setPiece(Piece piece) {
			this.piece = piece;
		}

		/**
		 * Retrieves the total amount of times that this piece was moved in the board
		 *
		 * @return The total.
		 */
		int getMovementCount() {
			return movementCount;
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

	private static class MoveLog {

		MovementTarget target;

		PieceMovement movement;

		PieceInfo capturedPieceInfo;

		MovementTarget getTarget() {
			return target;
		}

		void setTarget(MovementTarget target) {
			this.target = target;
		}

		PieceMovement getMovement() {
			return movement;
		}

		void setMovement(PieceMovement movement) {
			this.movement = movement;
		}

		PieceInfo getCapturedPieceInfo() {
			return capturedPieceInfo;
		}

		void setCapturedPieceInfo(PieceInfo capturedPieceInfo) {
			this.capturedPieceInfo = capturedPieceInfo;
		}
	}
}
