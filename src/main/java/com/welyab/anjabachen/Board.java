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
	
	/** FEN notation for the initial position. */
	private static final String FEN_INITIAL_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
	
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
	
	private static final int MASK_BLACK_KING_SIDE_CASTLING_AVAILABLE = 0;
	
	private static final int MASK_BLACK_QUEEN_SIDE_CASTLING_AVAILABLE = 0;
	
	private static final int MASK_WHITE_KING_SIDE_CASTLING_AVAILABLE = 0;
	
	private static final int MASK_WHITE_QUEEN_SIDE_CASTLING_AVAILABLE = 0;
	
	/**
	 * The 2-dimensional array where the pieces are placed.
	 */
	private Square[][] grid;
	
	private GameInfo gameInfo;
	
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
	
	public Board() {
		this(FEN_INITIAL_POSITION);
	}
	
	public Board(String fen) {
		this(FENParser.of(fen));
	}
	
	private Board(FENParser fenParser) {
		this(fenParser.getPiecesDisposition(), fenParser.getBoardConfig());
	}
	
	public Board(String piecesDisposition, BoardConfig boardConfig) {
		this(boardStringToPiecePositionList(piecesDisposition), boardConfig);
	}
	
	public Board(List<PiecePosition> pieces, BoardConfig boardConfig) {
		grid = createGrid();
		pieces.forEach(p -> addPiece(p.getPiece(), p.getPosition()));
		gameInfo = new GameInfo(boardConfig);
		validatePosition();
	}
	
	private void validatePosition() {
	}
	
	private static List<PiecePosition> boardStringToPiecePositionList(String boardString) {
		List<PiecePosition> dispositionList = new ArrayList<>();
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
			dispositionList.add(new PiecePosition(piece, Position.of(row, column)));
		}
		return dispositionList;
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
		square.setPiece(piece);
	}
	
	public void move(Position originPosition, Position targetPosition) {
		move(originPosition, targetPosition, PieceType.QUEEN);
	}
	
	public void move(Position originPosition, Position targetPosition, PieceType toPromotePawn) {
		toPromotePawn = toPromotePawn == null ? PieceType.QUEEN : toPromotePawn;
		PieceMovement movements = getMovements(originPosition);
		MovementTarget target = null;
		for (int i = 0; i < movements.size(); i++) {
			MovementTarget currentTarget = movements.getTarget(i);
			if (currentTarget.getPosition().equals(targetPosition)) {
				if (currentTarget.getMeta().isPromotion()
						&& currentTarget.getPiece().getType().equals(toPromotePawn)
						|| !currentTarget.getMeta().isPromotion()) {
					target = currentTarget;
					break;
				}
			}
		}
		
		if (target == null) {
			throw new MovementException(
				String.format(
					"The piece %s located in [%d, %d] can't reach the location [%d, %d]",
					getSquare(originPosition).getPiece(),
					originPosition.getRow(), originPosition.getColumn(),
					targetPosition.getRow(), targetPosition.getColumn()
				)
			);
		}
		
		move(movements, target);
	}
	
	public static void main(String[] args) {
		Board board = new Board();
		System.out.println(board.toString(true));
		System.out.println(board.getFen());
		
		board.move(Position.of(6, 4), Position.of(4, 4));
		System.out.println(board.toString(true));
		System.out.println(board.getFen());
		
		board.move(Position.of(1, 4), Position.of(2, 4));
		System.out.println(board.toString(true));
		System.out.println(board.getFen());
		
		board.move(Position.of(7, 5), Position.of(4, 2));
		System.out.println(board.toString(true));
		System.out.println(board.getFen());
		
		board.move(Position.of(0, 6), Position.of(2, 5));
		System.out.println(board.toString(true));
		System.out.println(board.getFen());
		
		board.move(Position.of(7, 6), Position.of(5, 5));
		System.out.println(board.toString(true));
		System.out.println(board.getFen());
		
		board.move(Position.of(0, 5), Position.of(4, 1));
		System.out.println(board.toString(true));
		System.out.println(board.getFen());
		
		board.move(Position.of(7, 7), Position.of(7, 6));
		System.out.println(board.toString(true));
		System.out.println(board.getFen());
		
		// Board board = new Board();
		// Random random = new Random();
		// for (int i = 0; i < 100; i++) {
		// PieceMovements movements = board.getMovements();
		// PieceMovement pieceMovement = movements.get(random.nextInt(movements.size()));
		// MovementTarget movementTarget =
		// pieceMovement.getTarget(random.nextInt(pieceMovement.size()));
		// board.move(pieceMovement, movementTarget);
		// System.out.println(board);
		// }
	}
	
	private void move(PieceMovement pieceMovement, MovementTarget movementTarget) {
		getSquare(movementTarget.getPosition()).setPiece(movementTarget.getPiece());
		getSquare(pieceMovement.getPosition()).setEmpty();
		
		if (movementTarget.getMeta().isCastling()) {
			Position rookOrigin = Position.of(
				pieceMovement.getPosition().getRow(),
				pieceMovement.getPosition().getColumn() < movementTarget.getPosition().getColumn() ? 7 : 0
			);
			int adjuster = pieceMovement.getPosition().getColumn() < movementTarget.getPosition().getColumn() ? -1 : 1;
			Position rookTarget = Position.of(
				pieceMovement.getPosition().getRow(),
				movementTarget.getPosition().getColumn() + adjuster
			);
			getSquare(rookTarget).setPiece(getSquare(rookOrigin).getPiece());
			getSquare(rookOrigin).setEmpty();
		}
		
		if (movementTarget.getMeta().isEnPassant()) {
			getSquare(
				Position.of(
					pieceMovement.getPosition().getRow(),
					movementTarget.getPosition().getColumn()
				)
			).setEmpty();
		}
		
		if (pieceMovement.getPiece().isPawn()
				&& Math.abs(pieceMovement.getPosition().getRow() - movementTarget.getPosition().getRow()) == 2) {
			int midRow = (pieceMovement.getPosition().getRow() + movementTarget.getPosition().getRow()) / 2;
			getGameInfo().setEnPassantTargetSquare(Position.of(midRow, movementTarget.getPosition().getColumn()));
		} else {
			getGameInfo().setEnPassantTargetSquare(null);
		}
		
		if (pieceMovement.getPiece().isPawn() || movementTarget.getMeta().isCapture()) {
			getGameInfo().resetHalfMoveCounter();
		} else {
			getGameInfo().incrementHalfMoveCounter();
		}
		
		if (pieceMovement.getPiece().isBlack()) {
			getGameInfo().incrementFullMoveCounter();
		}
		
		if (pieceMovement.getPiece().isKing()) {
			getGameInfo().invalidCastling(pieceMovement.getPiece().getColor());
		}
		
		if (pieceMovement.getPiece().isRook()) {
			if (getKingSideRookSquare(pieceMovement.getPiece().getColor()).equals(pieceMovement.getPosition())) {
				getGameInfo().invalidKingSideCastling(pieceMovement.getPiece().getColor());
			} else if (getQueenSideRookSquare(pieceMovement.getPiece().getColor())
				.equals(pieceMovement.getPosition())) {
				getGameInfo().invalidQueenSideCastling(pieceMovement.getPiece().getColor());
			}
		}
		
		getGameInfo().incrementMoveCounter();
	}
	
	/**
	 * The side color that has the next movement.
	 *
	 * @return The side color.
	 */
	public Color getActiveColor() {
		return getGameInfo().getMoveCounter() % 2 == 0 ? Color.WHITE : Color.BLACK;
	}
	
	public String getFen() {
		StringBuilder builder = new StringBuilder();
		for (int row = 0; row < Constants.BOARD_SIZE; row++) {
			int emptyCounter = 0;
			if (row > 0) {
				builder.append('/');
			}
			for (int column = 0; column < Constants.BOARD_SIZE; column++) {
				Square square = getSquare(Position.of(row, column));
				if (square.isEmpty()) {
					emptyCounter++;
				} else {
					if (emptyCounter > 0) {
						builder.append(emptyCounter);
					}
					emptyCounter = 0;
					builder.append(square.getPiece().getLetterSymbol());
				}
			}
			if (emptyCounter > 0) {
				builder.append(emptyCounter);
			}
		}
		builder.append(' ').append(getActiveColor().getLetterSymbol());
		builder.append(' ');
		
		StringBuilder castlingAvaiability = new StringBuilder();
		if (getGameInfo().isWhiteKingSideCastlingAvailable()) {
			castlingAvaiability.append('K');
		}
		if (getGameInfo().isWhiteQueenSideCastlingAvailable()) {
			castlingAvaiability.append('Q');
		}
		if (getGameInfo().isBlackKingSideCastlingAvailable()) {
			castlingAvaiability.append('k');
		}
		if (getGameInfo().isBlackQueenSideCastlingAvailable()) {
			castlingAvaiability.append('q');
		}
		if (castlingAvaiability.length() == 0) {
			builder.append('-');
		} else {
			builder.append(castlingAvaiability);
		}
		
		builder.append(' ');
		
		if (getGameInfo().getEnPassantTargetSquare() != null) {
			Position position = getGameInfo().getEnPassantTargetSquare();
			builder.append(position.getFile()).append(position.getRank());
		} else {
			builder.append('-');
		}
		
		builder.append(' ');
		builder.append(getGameInfo().getHalfMoveCounter());
		
		builder.append(' ');
		builder.append(getGameInfo().getFullMoveCounter());
		
		return builder.toString();
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
			if (square.isNotEmpty() && square.getPiece().getColor().equals(getActiveColor())) {
				PieceMovement pieceMovement = privateGetMovements(square.getPosition());
				if (pieceMovement.isNotEmpty()) {
					movements.add(pieceMovement);
					pieceMovementMetaBuilder.add(pieceMovement.getMeta());
				}
			}
		}
		return new PieceMovements(movements, pieceMovementMetaBuilder.build());
	}
	
	private GameInfo getGameInfo() {
		return gameInfo;
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
			
			final int instantMovementOperationCount = getGameInfo().getMoveCounter();
			
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
				if (instantMovementOperationCount != getGameInfo().getMoveCounter()) {
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
		if (square.getPiece().isPawn()) {
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
		Piece piece = square.getPiece();
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
		Color color = square.getPiece().getColor();
		if (!isKingPresent(color)) {
			return false;
		}
		Square targetSquare = getSquare(targetPosition);
		Piece temp = null;
		if (targetSquare.isNotEmpty()) {
			temp = targetSquare.getPiece();
		}
		targetSquare.setPiece(square.getPiece());
		square.setEmpty();
		Position kingPosition = getKingPosition(color);
		boolean underAttack = isUnderAttack(
			kingPosition,
			color.getOpposite()
		);
		square.setPiece(targetSquare.getPiece());
		targetSquare.setPiece(temp);
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
							Piece targetPiece = targetSquare.getPiece();
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
					Piece piece = targetSquare.getPiece();
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
						&& targetSquare.getPiece().isKnight()
						&& targetSquare.getPiece().getColor().equals(color)) {
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
						&& square.getPiece().isKing()
						&& square.getPiece().getColor().equals(color)) {
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
						&& square.getPiece().isKing()
						&& square.getPiece().getColor().equals(color)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private List<MovementTarget> getCastlingTargets(Position position) {
		Square kingSquare = getSquare(position);
		Piece king = kingSquare.getPiece();
		List<Position> rookPositions = new ArrayList<>(2);
		if (getGameInfo().isKingSideCastlingAvailable(king.getColor())) {
			rookPositions.add(getKingSideRookSquare(king.getColor()));
		}
		if (getGameInfo().isQueenSideCastlingAvailable(king.getColor())) {
			rookPositions.add(getQueenSideRookSquare(king.getColor()));
		}
		
		if (rookPositions.isEmpty()) {
			return Collections.emptyList();
		}
		List<MovementTarget> targets = new ArrayList<>();
		for (Position rookPosition : rookPositions) {
			int direction = Integer.signum(rookPosition.getColumn() - position.getColumn());
			int tLength = 0;
			boolean invalidCastling = false;
			while (!invalidCastling
					&& tLength < Math.abs(rookPosition.getColumn() - position.getColumn())) {
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
		return targets;
	}
	
	private Position getKingSideRookSquare(Color color) {
		return color.isWhite()
				? Position.of(WHITE_ROOK_INITIAL_ROW, KING_SIDE_ROOK_INITIAL_COLUMN)
				: Position.of(BLACK_ROOK_INITIAL_ROW, KING_SIDE_ROOK_INITIAL_COLUMN);
	}
	
	private Position getQueenSideRookSquare(Color color) {
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
		Piece pawn = pawnSquare.getPiece();
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
								Piece.get(replacement, pawnSquare.getPiece().getColor()),
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
		if (moveValue < 0) {
			return true;
		}
		return moveValue == 0
				&& getGameInfo().getEnPassantTargetSquare() != null
				&& getGameInfo().getEnPassantTargetSquare().equals(targetSquare.getPosition());
	}
	
	private boolean isValidPawnMovementForward(Square pawnSquare, Square targetSquare) {
		if (targetSquare.isNotEmpty()) {
			return false;
		}
		if (pawnSquare.getPosition().getColumn() != targetSquare.getPosition().getColumn()) {
			return false;
		}
		if (Math.abs(pawnSquare.getPosition().getRow() - targetSquare.getPosition().getRow()) == 2) {
			int pawnInitialRow = getPawnInitialRow(pawnSquare.getPiece().getColor());
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
		return toString(false);
	}
	
	public String toString(boolean printCoordinates) {
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
						.append(square.getPiece().getLetterSymbol())
						.append(' ');
				}
			}
			builder.append("│");
			if (printCoordinates) {
				builder.append(" ").append(row);
			}
			builder.append(NEWLINE);
			if (row == Constants.BOARD_SIZE - 1) {
				builder.append("└───┴───┴───┴───┴───┴───┴───┴───┘").append(NEWLINE);
				if (printCoordinates) {
					builder.append("  0   1   2   3   4   5   6   7  ").append(NEWLINE);
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
		for (int row = 0; row < Constants.BOARD_SIZE; row++) {
			for (int column = 0; column < Constants.BOARD_SIZE; column++) {
				Square square = getSquare(Position.of(row, column));
				if (column > 0) {
					builder.append(' ');
				}
				if (square.isEmpty()) {
					builder.append('.');
				} else {
					builder.append(square.getPiece().getLetterSymbol());
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
		
		Piece piece;
		
		/**
		 * Instantiates a square for the for the given location.
		 *
		 * @param row The square position row number.
		 * @param column The square position column number.
		 */
		Square(Position position) {
			this.position = position;
		}
		
		void setPiece(Piece piece) {
			this.piece = piece;
		}
		
		/**
		 * Adjusts this square to be interpreted as empty square.
		 */
		void setEmpty() {
			piece = null;
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
			return piece == null;
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
					: getPiece().getValue();
		}
		
		Piece getPiece() {
			if (isEmpty()) {
				throw new EmptySquareException(position.getRow(), position.getColumn());
			}
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
	
	private static class GameInfo {
		
		int moveCounter = 0;
		
		int fullMoveCounter = 1;
		
		int halfMoveCounter = 0;
		
		Position enPassantTargetSquare = null;
		
		boolean blackKingSideCastlingAvailable = true;
		
		boolean blackQueenSideCastlingAvailable = true;
		
		boolean whiteKingSideCastlingAvailable = true;
		
		boolean whiteQueenSideCastlingAvailable = true;
		
		GameInfo() {
		}
		
		GameInfo(BoardConfig config) {
			fullMoveCounter = config.getFullMoveCounter();
			halfMoveCounter = config.getHalfMoveCounter();
			enPassantTargetSquare = config.getEnPassantTargetSquare();
			blackKingSideCastlingAvailable = config.isBlackKingSideCastlingAvailable();
			blackQueenSideCastlingAvailable = config.isBlackQueenSideCastlingAvailable();
			whiteKingSideCastlingAvailable = config.isWhiteKingSideCastlingAvailable();
			whiteQueenSideCastlingAvailable = config.isWhiteQueenSideCastlingAvailable();
		}
		
		GameInfo copy() {
			GameInfo copy = new GameInfo();
			copy.moveCounter = moveCounter;
			copy.fullMoveCounter = fullMoveCounter;
			copy.halfMoveCounter = halfMoveCounter;
			copy.enPassantTargetSquare = enPassantTargetSquare;
			copy.blackKingSideCastlingAvailable = blackKingSideCastlingAvailable;
			copy.blackQueenSideCastlingAvailable = blackQueenSideCastlingAvailable;
			copy.whiteKingSideCastlingAvailable = whiteKingSideCastlingAvailable;
			copy.whiteQueenSideCastlingAvailable = whiteQueenSideCastlingAvailable;
			return copy;
		}
		
		int getHalfMoveCounter() {
			return halfMoveCounter;
		}
		
		void setHalfMoveCounter(int halfMoveCounter) {
			this.halfMoveCounter = halfMoveCounter;
		}
		
		Position getEnPassantTargetSquare() {
			return enPassantTargetSquare;
		}
		
		void setEnPassantTargetSquare(Position enPassantTargetSquare) {
			this.enPassantTargetSquare = enPassantTargetSquare;
		}
		
		boolean isBlackKingSideCastlingAvailable() {
			return blackKingSideCastlingAvailable;
		}
		
		void setBlackKingSideCastlingAvailable(boolean blackKingSideCastlingAvailable) {
			this.blackKingSideCastlingAvailable = blackKingSideCastlingAvailable;
		}
		
		boolean isBlackQueenSideCastlingAvailable() {
			return blackQueenSideCastlingAvailable;
		}
		
		void setBlackQueenSideCastlingAvailable(boolean blackQueenSideCastlingAvailable) {
			this.blackQueenSideCastlingAvailable = blackQueenSideCastlingAvailable;
		}
		
		boolean isWhiteKingSideCastlingAvailable() {
			return whiteKingSideCastlingAvailable;
		}
		
		void setWhiteKingSideCastlingAvailable(boolean whiteKingSideCastlingAvailable) {
			this.whiteKingSideCastlingAvailable = whiteKingSideCastlingAvailable;
		}
		
		boolean isWhiteQueenSideCastlingAvailable() {
			return whiteQueenSideCastlingAvailable;
		}
		
		void setWhiteQueenSideCastlingAvailable(boolean whiteQueenSideCastlingAvailable) {
			this.whiteQueenSideCastlingAvailable = whiteQueenSideCastlingAvailable;
		}
		
		int getFullMoveCounter() {
			return fullMoveCounter;
		}
		
		boolean isQueenSideCastlingAvailable(Color color) {
			return color.isWhite()
					? isWhiteQueenSideCastlingAvailable()
					: isBlackQueenSideCastlingAvailable();
		}
		
		boolean isKingSideCastlingAvailable(Color color) {
			return color.isWhite()
					? isWhiteKingSideCastlingAvailable()
					: isBlackKingSideCastlingAvailable();
		}
		
		void resetHalfMoveCounter() {
			setHalfMoveCounter(0);
		}
		
		void incrementHalfMoveCounter() {
			halfMoveCounter++;
		}
		
		void incrementFullMoveCounter() {
			fullMoveCounter++;
		}
		
		int getMoveCounter() {
			return moveCounter;
		}
		
		void incrementMoveCounter() {
			moveCounter++;
		}
		
		void invalidCastling(Color color) {
			if (color.isWhite()) {
				setWhiteKingSideCastlingAvailable(false);
				setWhiteQueenSideCastlingAvailable(false);
			} else {
				setBlackKingSideCastlingAvailable(false);
				setBlackQueenSideCastlingAvailable(false);
			}
		}
		
		void invalidQueenSideCastling(Color color) {
			if (color.isWhite()) {
				setWhiteQueenSideCastlingAvailable(false);
			} else {
				setBlackQueenSideCastlingAvailable(false);
			}
		}
		
		void invalidKingSideCastling(Color color) {
			if (color.isWhite()) {
				setWhiteKingSideCastlingAvailable(false);
			} else {
				setBlackKingSideCastlingAvailable(false);
			}
		}
	}
}
