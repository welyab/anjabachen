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
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
	
	private static final List<PieceType> pawnNonPromotionReplacements = Collections.unmodifiableList(
		Arrays.asList(
			PieceType.PAWN
		)
	);
	
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
	 * The 2-dimensional array where the pieces are placed.
	 */
	private Square[][] grid;
	
	private GameInfo gameInfo;
	
	private final List<MovementLog> movementHistory;
	
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
		movementHistory = new ArrayList<>();
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
			int row = counter / GameConstants.BOARD_SIZE;
			int column = counter % GameConstants.BOARD_SIZE;
			counter++;
			dispositionList.add(new PiecePosition(piece, Position.of(row, column)));
		}
		return dispositionList;
	}
	
	/**
	 * Creates a 2-dimensional array with size equals to {@link GameConstants#BOARD_SIZE}.
	 *
	 * @return The array.
	 */
	private static Square[][] createGrid() {
		Square[][] g = new Square[GameConstants.BOARD_SIZE][GameConstants.BOARD_SIZE];
		for (int row = 0; row < GameConstants.BOARD_SIZE; row++) {
			for (int column = 0; column < GameConstants.BOARD_SIZE; column++) {
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
		PieceMovement pieceMovement = getMovement(originPosition);
		MovementTarget target = null;
		for (int i = 0; i < pieceMovement.size(); i++) {
			MovementTarget currentTarget = pieceMovement.getTarget(i);
			if (currentTarget.getPosition().equals(targetPosition)) {
				if (GameConstants.isPromotion(currentTarget.getMovementFlags())
						&& currentTarget.getPiece().getType().equals(toPromotePawn)
						|| !GameConstants.isPromotion(currentTarget.getMovementFlags())) {
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
		
		move(pieceMovement.getOrigin(), target);
	}
	
	void move(MovementOrigin movementOrigin, MovementTarget movementTarget) {
		GameInfo copiedGameInfo = gameInfo.copy();
		Piece capturedPiece = null;
		if (GameConstants.isCapture(movementTarget.getMovementFlags())
				&& !GameConstants.isEnPassant(movementTarget.getMovementFlags())) {
			capturedPiece = getSquare(movementTarget.getPosition()).getPiece();
		}
		getSquare(movementTarget.getPosition()).setPiece(movementTarget.getPiece());
		getSquare(movementOrigin.getPosition()).setEmpty();
		
		if (GameConstants.isCastling(movementTarget.getMovementFlags())) {
			Position rookOrigin = Position.of(
				movementOrigin.getPosition().getRow(),
				movementOrigin.getPosition().getColumn() < movementTarget.getPosition().getColumn() ? 7 : 0
			);
			int adjuster = movementOrigin.getPosition().getColumn() < movementTarget.getPosition().getColumn() ? -1 : 1;
			Position rookTarget = Position.of(
				movementOrigin.getPosition().getRow(),
				movementTarget.getPosition().getColumn() + adjuster
			);
			getSquare(rookTarget).setPiece(getSquare(rookOrigin).getPiece());
			getSquare(rookOrigin).setEmpty();
		}
		
		if (GameConstants.isEnPassant(movementTarget.getMovementFlags())) {
			Square capturedPawnSquare = getSquare(
				Position.of(
					movementOrigin.getPosition().getRow(),
					movementTarget.getPosition().getColumn()
				)
			);
			capturedPiece = capturedPawnSquare.getPiece();
			capturedPawnSquare.setEmpty();
		}
		
		if (movementOrigin.getPiece().isPawn()
				&& Math.abs(movementOrigin.getPosition().getRow() - movementTarget.getPosition().getRow()) == 2) {
			int midRow = (movementOrigin.getPosition().getRow() + movementTarget.getPosition().getRow()) / 2;
			gameInfo.enPassantTargetSquare = Position.of(midRow, movementTarget.getPosition().getColumn());
		} else {
			gameInfo.enPassantTargetSquare = null;
		}
		
		if (movementOrigin.getPiece().isPawn() || GameConstants.isCapture(movementTarget.getMovementFlags())) {
			gameInfo.halfMoveCounter = 0;
		} else {
			gameInfo.halfMoveCounter++;
		}
		
		if (movementOrigin.getPiece().isBlack()) {
			gameInfo.fullMoveCounter++;
		}
		
		if (movementOrigin.getPiece().isKing()) {
			gameInfo.invalidateCastling(movementOrigin.getPiece().getColor());
		}
		
		if (movementOrigin.getPiece().isRook()) {
			if (gameInfo.isKingSideCastlingAvailable(movementOrigin.getPiece().getColor())
					&& getKingSideRookSquare(movementOrigin.getPiece().getColor()).equals(movementOrigin.getPosition())) {
				gameInfo.invalidateKingSideCastling(movementOrigin.getPiece().getColor());
			}
			if (gameInfo.isQueenSideCastlingAvailable(movementOrigin.getPiece().getColor())
					&& getQueenSideRookSquare(movementOrigin.getPiece().getColor()).equals(movementOrigin.getPosition())) {
				gameInfo.invalidateQueenSideCastling(movementOrigin.getPiece().getColor());
			}
		}
		
		if (capturedPiece != null && capturedPiece.isRook()) {
			if (gameInfo.isKingSideCastlingAvailable(capturedPiece.getColor())
					&& getKingSideRookSquare(capturedPiece.getColor()).equals(movementTarget.getPosition())) {
				gameInfo.invalidateKingSideCastling(capturedPiece.getColor());
			}
			if (gameInfo.isQueenSideCastlingAvailable(capturedPiece.getColor())
					&& getQueenSideRookSquare(capturedPiece.getColor()).equals(movementTarget.getPosition())) {
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
	
	private static class MovementLog {
		
		final MovementOrigin movementOrigin;
		
		final MovementTarget movementTarget;
		
		final Piece capturedPiece;
		
		final GameInfo gameInfo;
		
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
		
		MovementOrigin getMovementOrigin() {
			return movementOrigin;
		}
		
		MovementTarget getMovementTarget() {
			return movementTarget;
		}
		
		Piece getCapturedPiece() {
			return capturedPiece;
		}
		
		GameInfo getGameInfo() {
			return gameInfo;
		}
	}
	
	static Map<Piece, Integer> pieceValue = new EnumMap<>(Piece.class);
	static {
		pieceValue.put(Piece.BLACK_KING, -900);
		pieceValue.put(Piece.BLACK_QUEEN, -9);
		pieceValue.put(Piece.BLACK_ROOK, -5);
		pieceValue.put(Piece.BLACK_BISHOP, -3);
		pieceValue.put(Piece.BLACK_KNIGHT, -3);
		pieceValue.put(Piece.BLACK_PAWN, -1);
		pieceValue.put(Piece.WHITE_KING, 900);
		pieceValue.put(Piece.WHITE_QUEEN, 9);
		pieceValue.put(Piece.WHITE_ROOK, 5);
		pieceValue.put(Piece.WHITE_BISHOP, 2);
		pieceValue.put(Piece.WHITE_KNIGHT, 3);
		pieceValue.put(Piece.WHITE_PAWN, 1);
	}
	
	public boolean hasPreviousMovement() {
		return !movementHistory.isEmpty();
	}
	
	public void undo() {
		if (!hasPreviousMovement()) {
			throw new MovementException("No previous movement to undo");
		}
		
		MovementLog movementLog = movementHistory.remove(movementHistory.size() - 1);
		MovementOrigin movementOrigin = movementLog.getMovementOrigin();
		MovementTarget movementTarget = movementLog.getMovementTarget();
		
		Square originSquare = getSquare(movementOrigin.getPosition());
		Square targetSquare = getSquare(movementTarget.getPosition());
		
		originSquare.setPiece(movementOrigin.getPiece());
		targetSquare.setEmpty();
		
		if (GameConstants.isCapture(movementTarget.getMovementFlags())) {
			if (GameConstants.isEnPassant(movementTarget.getMovementFlags())) {
				getSquare(
					Position.of(
						movementOrigin.getPosition().getRow(),
						movementTarget.getPosition().getColumn()
					)
				).setPiece(movementLog.getCapturedPiece());
			} else {
				targetSquare.setPiece(movementLog.getCapturedPiece());
			}
		}
		
		if (GameConstants.isCastling(movementTarget.getMovementFlags())) {
			Square possibleRookSquare = getSquare(
				Position.of(
					movementTarget.getPosition().getRow(),
					movementTarget.getPosition().getColumn() + 1
				)
			);
			if (possibleRookSquare.isNotEmpty() && possibleRookSquare.getPiece().isRook()) {
				getSquare(Position.of(movementTarget.getPosition().getRow(), 0))
					.setPiece(possibleRookSquare.getPiece());
				possibleRookSquare.setEmpty();
			} else {
				Square rookSquare = getSquare(
					Position.of(
						movementTarget.getPosition().getRow(),
						movementTarget.getPosition().getColumn() - 1
					)
				);
				getSquare(Position.of(movementTarget.getPosition().getRow(), 7))
					.setPiece(rookSquare.getPiece());
				rookSquare.setEmpty();
			}
		}
		
		setGameInfo(movementLog.getGameInfo());
	}
	
	/**
	 * The side color that has the next movement.
	 *
	 * @return The side color.
	 */
	public Color getActiveColor() {
		return (gameInfo.moveCounter + gameInfo.moveSideAdjuster) % 2 == 0 ? Color.WHITE : Color.BLACK;
	}
	
	public String getFen() {
		StringBuilder builder = new StringBuilder();
		for (int row = 0; row < GameConstants.BOARD_SIZE; row++) {
			int emptyCounter = 0;
			if (row > 0) {
				builder.append('/');
			}
			for (int column = 0; column < GameConstants.BOARD_SIZE; column++) {
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
		if (GameConstants.isWhiteKingSideCastling(gameInfo.castlingFlags)) {
			castlingAvaiability.append('K');
		}
		if (GameConstants.isWhiteQueenSideCastling(gameInfo.castlingFlags)) {
			castlingAvaiability.append('Q');
		}
		if (GameConstants.isBlackKingSideCastling(gameInfo.castlingFlags)) {
			castlingAvaiability.append('k');
		}
		if (GameConstants.isBlackQueenSideCastling(gameInfo.castlingFlags)) {
			castlingAvaiability.append('q');
		}
		if (castlingAvaiability.length() == 0) {
			builder.append('-');
		} else {
			builder.append(castlingAvaiability);
		}
		
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
	
	public MovementBag getMovements() {
		Iterable<Square> squares = this::privateIterator;
		List<PieceMovement> movements = new ArrayList<>(32);
		PieceMovementMeta.Builder pieceMovementMetaBuilder = PieceMovementMeta.builder();
		for (Square square : squares) {
			if (square.isNotEmpty() && square.getPiece().getColor().equals(getActiveColor())) {
				PieceMovement pieceMovement = privateGetMovements(square);
				if (pieceMovement.isNotEmpty()) {
					movements.add(pieceMovement);
					pieceMovementMetaBuilder.add(pieceMovement.getMeta());
				}
			}
		}
		return new MovementBag(movements, pieceMovementMetaBuilder.build());
	}
	
	private void setGameInfo(GameInfo gameInfo) {
		this.gameInfo = gameInfo;
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
	public PieceMovement getMovement(Position position) {
		Square square = getSquare(position);
		if (square.isEmpty()) {
			throw new EmptySquareException(position.getRow(), position.getColumn());
		}
		return privateGetMovements(square);
	}
	
	/**
	 * Creates a stream of squares. Its expected that the board stated do not change during
	 * streaming, otherwise a exception will be thrown.
	 *
	 * @return The stream of squares.
	 */
	private Stream<Square> privateStream() {
		return StreamSupport.stream(
			Spliterators.spliterator(privateIterator(), GameConstants.SQUARES_COUNT, 0),
			false
		);
	}
	
	/**
	 * Creates a iterator for iterate over all squares of this board. It is expected that the board
	 * state do not change during interaction, otherwise a exception will be thrown.
	 *
	 * @return The iterator object.
	 */
	private Iterator<Square> privateIterator() {
		return new Iterator<>() {
			
			final int instantMovementOperationCount = gameInfo.moveCounter;
			
			int index = 0;
			
			@Override
			public boolean hasNext() {
				return index < GameConstants.SQUARES_COUNT;
			}
			
			@Override
			public Square next() {
				if (!hasNext()) {
					throw new NoSuchElementException("No more squares");
				}
				if (instantMovementOperationCount != gameInfo.moveCounter) {
					throw new ConcurrentModificationException(
						"The board have had changed its state during this iteration"
					);
				}
				int row = index / GameConstants.BOARD_SIZE;
				int column = index % GameConstants.BOARD_SIZE;
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
	private PieceMovement privateGetMovements(Square square) {
		return square.getPiece().isPawn()
				? getPawnMovements(square)
				: getNonPawnMovements(square);
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
	private PieceMovement getNonPawnMovements(Square square) {
		int maxMoveLength = getMaxPieceMovementLength(square.getPiece().getType());
		List<DirectionAdjuster> directionAdjusters = getMovementTemplate(square.getPiece());
		boolean[] invalidDirection = new boolean[directionAdjusters.size()];
		List<MovementTarget> targets = new ArrayList<>();
		Builder pieceMovementMetaBuilder = PieceMovementMeta.builder();
		for (int mLength = 1; mLength <= maxMoveLength; mLength++) {
			for (int t = 0; t < directionAdjusters.size(); t++) {
				if (!invalidDirection[t]) {
					DirectionAdjuster directionAdjuster = directionAdjusters.get(t);
					int targetRow = square.getPosition().getRow() + mLength * directionAdjuster.getRowAdjuster();
					int targetColumn = square.getPosition().getColumn()
							+ mLength * directionAdjuster.getColumnAdjuster();
					if (isInsideBoard(targetRow, targetColumn)) {
						Square targetSquare = getSquare(Position.of(targetRow, targetColumn));
						int moveValue = square.getValue() * targetSquare.getValue();
						if (moveValue <= 0) {
							if (moveValue < 0) {
								invalidDirection[t] = true;
							}
							if (!isKingInCheckWithMove(
								square.getPosition(), Position.of(targetRow, targetColumn), null
							)) {
								int movementFlags = 0;
								if (moveValue < 0) {
									pieceMovementMetaBuilder.incrementCaptureCount();
									movementFlags |= GameConstants.CAPTURE;
								}
								pieceMovementMetaBuilder.incrementTotalMovements();
								targets.add(
									new MovementTarget(
										square.getPiece(),
										Position.of(targetRow, targetColumn),
										movementFlags
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
		if (square.getPiece().isKing()) {
			List<MovementTarget> castlingTargets = getCastlingTargets(square);
			pieceMovementMetaBuilder.incrementTotalMovements(castlingTargets.size());
			pieceMovementMetaBuilder.incrementCastlings(castlingTargets.size());
			targets.addAll(castlingTargets);
		}
		return new PieceMovement(
			new MovementOrigin(square.getPiece(), square.getPosition()),
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
	private boolean isKingInCheckWithMove(Position originPosition, Position targetPosition,
			Position capturedPawnEnPassant) {
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
		Piece capturedEnPassantPawn = null;
		if (capturedPawnEnPassant != null) {
			capturedEnPassantPawn = getSquare(capturedPawnEnPassant).getPiece();
			getSquare(capturedPawnEnPassant).setEmpty();
		}
		Position kingPosition = getKingPosition(color);
		boolean underAttack = isUnderAttack(
			kingPosition,
			color.getOpposite()
		);
		square.setPiece(targetSquare.getPiece());
		targetSquare.setPiece(temp);
		if (capturedPawnEnPassant != null) {
			getSquare(capturedPawnEnPassant).setPiece(capturedEnPassantPawn);
		}
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
		for (int row = 0; row < GameConstants.BOARD_SIZE; row++) {
			for (int column = 0; column < GameConstants.BOARD_SIZE; column++) {
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
		for (int row = 0; row < GameConstants.BOARD_SIZE; row++) {
			for (int column = 0; column < GameConstants.BOARD_SIZE; column++) {
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
	
	private List<MovementTarget> getCastlingTargets(Square kingSquare) {
		List<Position> rookPositions = new ArrayList<>(2);
		if (gameInfo.isKingSideCastlingAvailable(kingSquare.getPiece().getColor())) {
			rookPositions.add(getKingSideRookSquare(kingSquare.getPiece().getColor()));
		}
		if (gameInfo.isQueenSideCastlingAvailable(kingSquare.getPiece().getColor())) {
			rookPositions.add(getQueenSideRookSquare(kingSquare.getPiece().getColor()));
		}
		
		if (rookPositions.isEmpty()) {
			return Collections.emptyList();
		}
		List<MovementTarget> targets = new ArrayList<>();
		for (Position rookPosition : rookPositions) {
			int direction = Integer.signum(rookPosition.getColumn() - kingSquare.getPosition().getColumn());
			int tLength = 0;
			boolean invalidCastling = false;
			int distance = Math.abs(rookPosition.getColumn() - kingSquare.getPosition().getColumn());
			while (!invalidCastling && tLength < distance) {
				int targetColumn = kingSquare.getPosition().getColumn() + tLength * direction;
				if (tLength <= 2 && isUnderAttack(
					Position.of(kingSquare.getPosition().getRow(), targetColumn),
					kingSquare.getPiece().getColor().getOpposite()
				)) {
					invalidCastling = true;
				}
				if (tLength > 0
						&& getSquare(Position.of(kingSquare.getPosition().getRow(), targetColumn)).isNotEmpty()) {
					invalidCastling = true;
				}
				tLength++;
			}
			if (!invalidCastling) {
				int targetKingColumn = kingSquare.getPosition().getColumn() + 2 * direction;
				targets.add(
					new MovementTarget(
						kingSquare.getPiece(),
						Position.of(kingSquare.getPosition().getRow(), targetKingColumn),
						GameConstants.CASTLING
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
		return row >= 0 && row < GameConstants.BOARD_SIZE
				&& column >= 0 && column < GameConstants.BOARD_SIZE;
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
			return GameConstants.BOARD_SIZE - 1;
		}
		if (type == PieceType.ROOK) {
			return GameConstants.BOARD_SIZE - 1;
		}
		if (type == PieceType.BISHOP) {
			return GameConstants.BOARD_SIZE - 1;
		}
		if (type == PieceType.KNIGHT) {
			return 1;
		}
		throw new ChessError(String.format("Unexpected piece type: %s", type));
	}
	
	private PieceMovement getPawnMovements(Square pawnSquare) {
		List<MovementTarget> targets = new ArrayList<>();
		PieceMovementMeta.Builder pieceMovementMetaBuilder = PieceMovementMeta.builder();
		for (DirectionAdjuster directionAjduster : getMovementTemplate(pawnSquare.getPiece())) {
			int targetRow = pawnSquare.getPosition().getRow() + directionAjduster.getRowAdjuster();
			int targetColumn = pawnSquare.getPosition().getColumn() + directionAjduster.getColumnAdjuster();
			if (isInsideBoard(targetRow, targetColumn)) {
				Square targetSquare = getSquare(Position.of(targetRow, targetColumn));
				boolean isMovementForward = isValidPawnMovementForward(pawnSquare, targetSquare);
				boolean isCapture = isValidPawnCaptureMovement(pawnSquare, targetSquare);
				boolean isEnPassant = isCapture && targetSquare.isEmpty();
				if ((isMovementForward || isCapture)
						&& !isKingInCheckWithMove(
							pawnSquare.getPosition(),
							targetSquare.getPosition(),
							isEnPassant ? Position.of(pawnSquare.getPosition().getRow(), targetColumn) : null
						)) {
					boolean isPawnPromotion = targetRow == getPawnPromotionRow(pawnSquare.getPiece().getColor());
					List<PieceType> targetPieces = isPawnPromotion
							? pawnPromotionReplacements
							: pawnNonPromotionReplacements;
					for (PieceType pieceType : targetPieces) {
						int movementFlags = 0;
						if (isCapture) {
							movementFlags |= GameConstants.CAPTURE;
							pieceMovementMetaBuilder.incrementCaptureCount();
						}
						if (isEnPassant) {
							movementFlags |= GameConstants.EN_PASSANT;
							pieceMovementMetaBuilder.incrementEnPassantCount();
						}
						if (isPawnPromotion) {
							movementFlags |= GameConstants.PROMOTION;
							pieceMovementMetaBuilder.incrementPromotionCount();
						}
						pieceMovementMetaBuilder.incrementTotalMovements();
						targets.add(
							new MovementTarget(
								Piece.get(pieceType, pawnSquare.getPiece().getColor()),
								targetSquare.getPosition(),
								movementFlags
							)
						);
					}
				}
			}
		}
		return new PieceMovement(
			new MovementOrigin(pawnSquare.getPiece(), pawnSquare.getPosition()),
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
				&& gameInfo.enPassantTargetSquare != null
				&& gameInfo.enPassantTargetSquare.equals(targetSquare.getPosition());
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
		for (int row = 0; row < GameConstants.BOARD_SIZE; row++) {
			for (int column = 0; column < GameConstants.BOARD_SIZE; column++) {
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
				builder.append(" ").append(row).append(' ').append('[').append(Position.toRank(row)).append(']');
			}
			builder.append(NEWLINE);
			if (row == GameConstants.BOARD_SIZE - 1) {
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
		for (int row = 0; row < GameConstants.BOARD_SIZE; row++) {
			for (int column = 0; column < GameConstants.BOARD_SIZE; column++) {
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
	
	private class GameInfo implements Copiable<GameInfo> {
		
		int moveSideAdjuster;
		
		int moveCounter = 0;
		
		int fullMoveCounter = 1;
		
		int halfMoveCounter = 0;
		
		Position enPassantTargetSquare = null;
		
		/**
		 *
		 */
		int castlingFlags;
		
		GameInfo() {
		}
		
		void decrementMoveCounter() {
			moveCounter--;
		}
		
		GameInfo(BoardConfig config) {
			moveSideAdjuster = config.getSideToMove().isWhite() ? 0 : 1;
			fullMoveCounter = config.getFullMoveCounter();
			halfMoveCounter = config.getHalfMoveCounter();
			enPassantTargetSquare = config.getEnPassantTargetSquare();
			castlingFlags |= config.isBlackKingSideCastlingAvailable() ? GameConstants.BLACK_KING_SIDE_CASTLING : 0;
			castlingFlags |= config.isBlackQueenSideCastlingAvailable() ? GameConstants.BLACK_KING_SIDE_CASTLING : 0;
			castlingFlags |= config.isWhiteKingSideCastlingAvailable() ? GameConstants.BLACK_KING_SIDE_CASTLING : 0;
			castlingFlags |= config.isWhiteQueenSideCastlingAvailable() ? GameConstants.BLACK_KING_SIDE_CASTLING : 0;
		}
		
		@Override
		public GameInfo copy() {
			GameInfo copy = new GameInfo();
			copy.moveCounter = moveCounter;
			copy.fullMoveCounter = fullMoveCounter;
			copy.halfMoveCounter = halfMoveCounter;
			copy.enPassantTargetSquare = enPassantTargetSquare;
			copy.castlingFlags = castlingFlags;
			return copy;
		}
		
		int getFullMoveCounter() {
			return fullMoveCounter;
		}
		
		boolean isQueenSideCastlingAvailable(Color color) {
			return color.isWhite()
					? GameConstants.isWhiteQueenSideCastling(castlingFlags)
					: GameConstants.isBlackQueenSideCastling(castlingFlags);
		}
		
		boolean isKingSideCastlingAvailable(Color color) {
			return color.isWhite()
					? GameConstants.isWhiteKingSideCastling(castlingFlags)
					: GameConstants.isBlackKingSideCastling(castlingFlags);
		}
		
		void invalidateCastling(Color color) {
			if (color.isWhite()) {
				castlingFlags &= ~GameConstants.WHITE_KING_SIDE_CASTLING;
				castlingFlags &= ~GameConstants.WHITE_QUEEN_SIDE_CASTLING;
			} else {
				castlingFlags &= ~GameConstants.BLACK_KING_SIDE_CASTLING;
				castlingFlags &= ~GameConstants.BLACK_QUEEN_SIDE_CASTLING;
			}
		}
		
		void invalidateQueenSideCastling(Color color) {
			if (color.isWhite()) {
				castlingFlags ^= GameConstants.WHITE_QUEEN_SIDE_CASTLING;
			} else {
				castlingFlags ^= GameConstants.BLACK_QUEEN_SIDE_CASTLING;
			}
		}
		
		void invalidateKingSideCastling(Color color) {
			if (color.isWhite()) {
				castlingFlags ^= GameConstants.WHITE_KING_SIDE_CASTLING;
			} else {
				castlingFlags ^= GameConstants.BLACK_KING_SIDE_CASTLING;
			}
		}
		
		@Override
		public String toString() {
			StringBuilder castlingFlagsStr = new StringBuilder();
			if (GameConstants.isWhiteKingSideCastling(castlingFlags)) {
				castlingFlagsStr.append('K');
			}
			if (GameConstants.isWhiteQueenSideCastling(castlingFlags)) {
				castlingFlagsStr.append('Q');
			}
			if (GameConstants.isBlackKingSideCastling(castlingFlags)) {
				castlingFlagsStr.append('k');
			}
			if (GameConstants.isBlackQueenSideCastling(castlingFlags)) {
				castlingFlagsStr.append('q');
			}
			if (castlingFlagsStr.length() == 0) {
				castlingFlagsStr.append('-');
			}
			return new StringBuilder()
				.append("Next: ")
				.append(getActiveColor().getName())
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
