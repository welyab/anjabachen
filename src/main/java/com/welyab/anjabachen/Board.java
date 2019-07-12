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
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.welyab.anjabachen.PieceMovementMeta.Builder;

/**
 * @author Welyab Paula
 */
public class Board implements Copiable<Board> {

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
	private Piece[][] grid;

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

	public Board(List<LocalizedPiece> pieces, BoardConfig boardConfig) {
		this(
			localizedPieceListToGrid(pieces),
			new GameInfo(boardConfig),
			new ArrayList<>()
		);
	}

	private Board(Piece[][] grid, GameInfo gameInfo, List<MovementLog> movementHistory) {
		this.grid = grid;
		this.gameInfo = gameInfo;
		this.movementHistory = movementHistory;
		privateStream()
			.filter(LocalizedPiece::isNotEmpty)
			.filter(p -> p.getPiece().isKing())
			.forEach(p -> gameInfo.setKingPosition(p.getPiece().getColor(), p.getPosition()));
		validatePosition();
	}

	private void validatePosition() {
	}

	private static Piece[][] localizedPieceListToGrid(List<LocalizedPiece> pieces) {
		Piece[][] grid = new Piece[GameConstants.BOARD_SIZE][GameConstants.BOARD_SIZE];
		pieces.forEach(p -> grid[p.getPosition().getRow()][p.getPosition().getColumn()] = p.getPiece());
		return grid;
	}

	private static List<LocalizedPiece> boardStringToPiecePositionList(String boardString) {
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
			int row = counter / GameConstants.BOARD_SIZE;
			int column = counter % GameConstants.BOARD_SIZE;
			counter++;
			dispositionList.add(new LocalizedPiece(piece, Position.of(row, column)));
		}
		return dispositionList;
	}

	public void addPiece(Piece piece, Position position) {
		isInsideBoard(position.getRow(), position.getColumn());
		grid[position.getRow()][position.getColumn()] = piece;
		if (piece.isKing()) {
			gameInfo.setKingPosition(piece.getColor(), position);
		}
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
					grid[originPosition.getRow()][originPosition.getColumn()],
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
			capturedPiece = grid[movementTarget.getPosition().getRow()][movementTarget.getPosition().getColumn()];
		}
		grid[movementTarget.getPosition().getRow()][movementTarget.getPosition()
			.getColumn()] = grid[movementOrigin.getPosition().getRow()][movementOrigin.getPosition().getColumn()];
		grid[movementOrigin.getPosition().getRow()][movementOrigin.getPosition().getColumn()] = null;

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
			grid[rookTarget.getRow()][rookTarget.getColumn()] = grid[rookOrigin.getRow()][rookOrigin.getColumn()];
			grid[rookOrigin.getRow()][rookOrigin.getColumn()] = null;
		}

		if (GameConstants.isEnPassant(movementTarget.getMovementFlags())) {
			capturedPiece = grid[movementOrigin.getPosition().getRow()][movementTarget.getPosition().getColumn()];
			grid[movementOrigin.getPosition().getRow()][movementTarget.getPosition().getColumn()] = null;
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
			gameInfo.setKingPosition(movementOrigin.getPiece().getColor(), movementTarget.getPosition());
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

	private static class MovementLog implements Copiable<MovementLog> {

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

		grid[movementOrigin.getPosition().getRow()][movementOrigin.getPosition().getColumn()] = movementOrigin
			.getPiece();
		grid[movementTarget.getPosition().getRow()][movementTarget.getPosition().getColumn()] = null;

		if (GameConstants.isCapture(movementTarget.getMovementFlags())) {
			if (GameConstants.isEnPassant(movementTarget.getMovementFlags())) {
				grid[movementOrigin.getPosition().getRow()][movementTarget.getPosition().getColumn()] = movementLog
					.getCapturedPiece();
			} else {
				grid[movementTarget.getPosition().getRow()][movementTarget.getPosition().getColumn()] = movementLog
					.getCapturedPiece();
			}
		}

		if (GameConstants.isCastling(movementTarget.getMovementFlags())) {
			if (grid[movementTarget.getPosition().getRow()][movementTarget.getPosition().getColumn() + 1] != null
					&& grid[movementTarget.getPosition().getRow()][movementTarget.getPosition().getColumn() + 1]
						.isRook()) {
				grid[movementTarget.getPosition().getRow()][0] = grid[movementTarget.getPosition()
					.getRow()][movementTarget.getPosition().getColumn() + 1];
				grid[movementTarget.getPosition().getRow()][movementTarget.getPosition().getColumn() + 1] = null;
			} else {
				grid[movementTarget.getPosition().getRow()][7] = grid[movementTarget.getPosition()
					.getRow()][movementTarget.getPosition().getColumn() - 1];
				grid[movementTarget.getPosition().getRow()][movementTarget.getPosition().getColumn() - 1] = null;
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
		return calculateNextSideToMove(gameInfo.moveCounter, gameInfo.moveSideAdjuster);
	}

	@Override
	public Board copy() {
		return new Board(
			copyGrid(),
			gameInfo.copy(),
			copyMoveHistory()
		);
	}

	private List<MovementLog> copyMoveHistory() {
		return movementHistory
			.stream()
			.map(MovementLog::copy)
			.collect(Collectors.toList());
	}

	private Piece[][] copyGrid() {
		Piece[][] copy = new Piece[GameConstants.BOARD_SIZE][GameConstants.BOARD_SIZE];
		for (int row = 0; row < GameConstants.BOARD_SIZE; row++) {
			for (int column = 0; column < GameConstants.BOARD_SIZE; column++) {
				copy[row][column] = grid[row][column];
			}
		}
		return copy;
	}

	public String getFen() {
		StringBuilder builder = new StringBuilder();
		for (int row = 0; row < GameConstants.BOARD_SIZE; row++) {
			int emptyCounter = 0;
			if (row > 0) {
				builder.append('/');
			}
			for (int column = 0; column < GameConstants.BOARD_SIZE; column++) {
				Piece piece = grid[row][column];
				if (piece == null) {
					emptyCounter++;
				} else {
					if (emptyCounter > 0) {
						builder.append(emptyCounter);
					}
					emptyCounter = 0;
					builder.append(piece.getLetterSymbol());
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
		if (!gameInfo.isKingPresent(getActiveColor())) {
			return false;
		}
		Position kingPosition = gameInfo.getKingPosition(getActiveColor());
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
		return getMovements(true);
	}

	public MovementBag getMovements(boolean extractCheckFlags) {
		Iterable<LocalizedPiece> squares = this::privateIterator;
		List<PieceMovement> movements = new ArrayList<>(32);
		PieceMovementMeta.Builder pieceMovementMetaBuilder = PieceMovementMeta.builder();
		for (LocalizedPiece piecePosition : squares) {
			if (!piecePosition.isEmpty() && piecePosition.getPiece().getColor().equals(getActiveColor())) {
				PieceMovement pieceMovement = privateGetMovements(piecePosition.getPosition(), extractCheckFlags);
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
		return getMovement(position, true);
	}

	public PieceMovement getMovement(Position position, boolean extractExtraFlags) {
		if (grid[position.getRow()][position.getColumn()] == null) {
			throw new EmptySquareException(position.getRow(), position.getColumn());
		}
		return privateGetMovements(position, extractExtraFlags);
	}

	/**
	 * Creates a stream of squares. Its expected that the board stated do not change during
	 * streaming, otherwise a exception will be thrown.
	 *
	 * @return The stream of squares.
	 */
	private Stream<LocalizedPiece> privateStream() {
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
	private Iterator<LocalizedPiece> privateIterator() {
		return new Iterator<>() {

			final int instantMovementOperationCount = gameInfo.moveCounter;

			int index = 0;

			@Override
			public boolean hasNext() {
				return index < GameConstants.SQUARES_COUNT;
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
				int row = index / GameConstants.BOARD_SIZE;
				int column = index % GameConstants.BOARD_SIZE;
				index++;
				return new LocalizedPiece(grid[row][column], Position.of(row, column));
			}
		};
	}

	private PieceMovement privateGetMovements(Position position, boolean extractCheckFlags) {
		return grid[position.getRow()][position.getColumn()].isPawn()
				? getPawnMovements(position, extractCheckFlags)
				: getNonPawnMovements(position, extractCheckFlags);
	}

	private PieceMovement getNonPawnMovements(Position position, boolean extractCheckFlags) {
		int maxMoveLength = getMaxPieceMovementLength(
			grid[position.getRow()][position.getColumn()].getType()
		);
		List<DirectionAdjuster> directionAdjusters = getMovementTemplate(
			grid[position.getRow()][position.getColumn()]
		);
		boolean[] invalidDirection = new boolean[directionAdjusters.size()];
		MovementOrigin movementOrigin = new MovementOrigin(
			grid[position.getRow()][position.getColumn()],
			position
		);
		List<MovementTarget> targets = new ArrayList<>();
		Builder pieceMovementMetaBuilder = PieceMovementMeta.builder();
		for (int mLength = 1; mLength <= maxMoveLength; mLength++) {
			for (int t = 0; t < directionAdjusters.size(); t++) {
				if (!invalidDirection[t]) {
					DirectionAdjuster directionAdjuster = directionAdjusters.get(t);
					int targetRow = position.getRow() + mLength * directionAdjuster.getRowAdjuster();
					int targetColumn = position.getColumn()
							+ mLength * directionAdjuster.getColumnAdjuster();
					if (isInsideBoard(targetRow, targetColumn)) {
						Piece targetPiece = grid[targetRow][targetColumn];
						int moveValue = grid[position.getRow()][position.getColumn()].getValue()
								* (targetPiece == null ? 0 : targetPiece.getValue());
						if (moveValue <= 0) {
							if (moveValue < 0) {
								invalidDirection[t] = true;
							}
							if (!isKingInCheckWithMove(
								position, Position.of(targetRow, targetColumn), null
							)) {
								int movementFlags = 0;
								if (moveValue < 0) {
									pieceMovementMetaBuilder.incrementCaptureCount();
									movementFlags |= GameConstants.CAPTURE;
								}
								if (extractCheckFlags) {
									movementFlags |= extractExtraMovementFlags(
										movementOrigin.getPiece(),
										movementOrigin.getPosition(),
										movementOrigin.getPiece(),
										Position.of(targetRow, targetColumn),
										movementFlags
									);

									updatePieceMovementMetaWithCheckFlags(pieceMovementMetaBuilder, movementFlags);
								}
								pieceMovementMetaBuilder.incrementTotalMovements();
								targets.add(
									new MovementTarget(
										grid[position.getRow()][position.getColumn()],
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
		if (grid[position.getRow()][position.getColumn()].isKing()) {
			List<MovementTarget> castlingTargets = getCastlingTargets(position, extractCheckFlags);
			castlingTargets
				.forEach(t -> updatePieceMovementMetaWithCheckFlags(pieceMovementMetaBuilder, t.getMovementFlags()));
			pieceMovementMetaBuilder.incrementTotalMovements(castlingTargets.size());
			pieceMovementMetaBuilder.incrementCastlings(castlingTargets.size());
			targets.addAll(castlingTargets);
		}
		return new PieceMovement(
			movementOrigin,
			targets,
			pieceMovementMetaBuilder.build()
		);
	}

	/**
	 * Verify if the movement described by given parameters is capable to be marked with following
	 * flags:
	 *
	 * <ul>
	 * <li>{@linkplain GameConstants#CHECK CHECK} - if the movement puts the opposite king in check
	 * <li>{@linkplain GameConstants#DOUBLE_CHECK DOUBLE_CHECK} - if the movement puts the opposite
	 * king in check, attacked by two pieces.
	 * <li>{@linkplain GameConstants#DISCOVERY_CHECK DISCOVERY_CHECK} - if being moved piece don't
	 * checks the opposite king by itself, but uncover another piece, that attacks the king.
	 * <li>{@linkplain GameConstants#CHECKMATE CHECKMATE} - the movement puts the opposite king in
	 * checkmate.
	 * <li>{@linkplain GameConstants#STALEMATE STALEMATE} - the movement left the opposite with no
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
	 *        {@linkplain GameConstants#CAPTURE CAPTURE}, {@linkplain GameConstants#EN_PASSANT
	 *        EN_PASSANT}, {@linkplain GameConstants#PROMOTION PROMOTION}, and
	 *        {@linkplain GameConstants#CASTLING CASTLING}.
	 *
	 * @return The additional flags, if any.
	 *
	 * @see #extractExtraMovementFlags(Color, Position)
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
	 * {@linkplain GameConstants#CHECK CHECK}, {@linkplain GameConstants#DOUBLE_CHECK DOUBLE_CHECK},
	 * {@linkplain GameConstants#DISCOVERY_CHECK DISCOVERY_CHECK},
	 * {@linkplain GameConstants#CHECKMATE CHECKMATE} and {@linkplain GameConstants#STALEMATE
	 * STALEMATE}. For each one, the respective counter in the
	 * <code>PieceMovementMeta</code> is updated.
	 *
	 * @param builder The builder for <code>PieceMovementMeta</code> builder.
	 *
	 * @param movementFlags The movement flags.
	 */
	private void updatePieceMovementMetaWithCheckFlags(PieceMovementMeta.Builder builder, int movementFlags) {
		if (GameConstants.isCheck(movementFlags)) {
			builder.incrementCheckCount();
		}

		if (GameConstants.isDoubleCheck(movementFlags)) {
			builder.incrementDoubleCheckCount();
		}

		if (GameConstants.isDiscoveryCheck(movementFlags)) {
			builder.incrementDiscoveryCheckCount();
		}

		if (GameConstants.isCheckmate(movementFlags)) {
			builder.incrementCheckmateCount();
		}

		if (GameConstants.isStalemate(movementFlags)) {
			builder.incrementStalemateCount();
		}
	}

	/**
	 * This method may be called after a movement in order to extract the following movement flags:
	 * {@linkplain GameConstants#CHECK CHECK}, {@linkplain GameConstants#DOUBLE_CHECK DOUBLE_CHECK},
	 * {@linkplain GameConstants#DISCOVERY_CHECK DISCOVERY_CHECK},
	 * {@linkplain GameConstants#CHECKMATE CHECKMATE} and {@linkplain GameConstants#STALEMATE
	 * STALEMATE}.
	 *
	 * @return The movement flags.
	 */
	private int extractExtraMovementFlags() {
		int flags = extractKingQueenRookBishopExtraFlags() | extractKnight() | extractPawn();
		if (getMovements(false).isEmpty()) {
			if (GameConstants.isCheck(flags)) {
				flags |= GameConstants.CHECKMATE;
			} else {
				flags |= GameConstants.STALEMATE;
			}
		}
		return flags;
	}

	/**
	 * This method may be called after a movement in order to extract the following movement flags:
	 * {@linkplain GameConstants#CHECK CHECK}, {@linkplain GameConstants#DOUBLE_CHECK DOUBLE_CHECK},
	 * {@linkplain GameConstants#DISCOVERY_CHECK DISCOVERY_CHECK},
	 * {@linkplain GameConstants#CHECKMATE CHECKMATE} and {@linkplain GameConstants#STALEMATE
	 * STALEMATE}.
	 *
	 * <p>
	 * Only the pieces {@linkplain PieceType#KING KING}, {@linkplain PieceType#QUEEN
	 * QUEEN}, {@linkplain PieceType#ROOK ROOK} and {@linkplain PieceType#BISHOP BISHOP} are
	 * considered in this method.
	 *
	 * @return The movement flags.
	 */
	private int extractKingQueenRookBishopExtraFlags() {
		Position position = movementHistory.get(movementHistory.size() - 1).movementOrigin.getPosition();
		Color attackerColor = movementHistory.get(movementHistory.size() - 1).movementOrigin.getPiece().getColor();
		Position kingPosition = gameInfo.getKingPosition(attackerColor.getOpposite());
		int maxMoveLength = getMaxPieceMovementLength(PieceType.QUEEN);
		boolean[] invalidDirections = new boolean[queenMoveTemplate.size()];
		int flags = 0;
		for (int t = 0; t < queenMoveTemplate.size(); t++) {
			boolean discovery = false;
			for (int mLength = 1; mLength <= maxMoveLength; mLength++) {
				if (!invalidDirections[t]) {
					DirectionAdjuster directionAdjuster = queenMoveTemplate.get(t);
					int targetRow = kingPosition.getRow() + mLength * directionAdjuster.getRowAdjuster();
					int targetColumn = kingPosition.getColumn() + mLength * directionAdjuster.getColumnAdjuster();
					if (isInsideBoard(targetRow, targetColumn)) {
						if (!discovery && position.equals(targetRow, targetColumn)) {
							discovery = true;
						}
						if (grid[targetRow][targetColumn] != null) {
							Piece targetPiece = grid[targetRow][targetColumn];
							if (targetPiece.getColor().equals(attackerColor)
									&& (targetPiece.isQueen()
											|| targetPiece.isRook() && (kingPosition.getRow() == targetRow
													|| kingPosition.getColumn() == targetColumn)
											|| targetPiece.isBishop() && kingPosition.getRow() != targetRow
													&& kingPosition.getColumn() != targetColumn
											|| targetPiece.isKing() && mLength == 1)) {
								if (GameConstants.isCheck(flags)) {
									flags |= GameConstants.DOUBLE_CHECK;
								}
								flags |= GameConstants.CHECK;

								if (discovery) {
									flags |= GameConstants.DISCOVERY_CHECK;
								}
							} else {
								invalidDirections[t] = true;
							}
						}
					}
				}
			}
		}
		return flags;
	}

	private int extractKnight() {
		Color attackerColor = movementHistory.get(movementHistory.size() - 1).movementOrigin.getPiece().getColor();
		Position kingPosition = gameInfo.getKingPosition(attackerColor.getOpposite());
		int flags = 0;
		for (int t = 0; t < knightMoveTemplate.size(); t++) {
			int targetRow = kingPosition.getRow() + knightMoveTemplate.get(t).getRowAdjuster();
			int targetColumn = kingPosition.getColumn() + knightMoveTemplate.get(t).getColumnAdjuster();
			if (isInsideBoard(targetRow, targetColumn)) {
				Piece piece = grid[targetRow][targetColumn];
				if (piece != null
						&& piece.isKnight()
						&& piece.getColor().equals(attackerColor)) {
					if (GameConstants.isCheck(flags)) {
						flags |= GameConstants.DOUBLE_CHECK;
					}
					flags |= GameConstants.CHECK;
				}
			}
		}
		return flags;
	}

	private int extractPawn() {
		Color attackerColor = movementHistory.get(movementHistory.size() - 1).movementOrigin.getPiece().getColor();
		Position kingPosition = gameInfo.getKingPosition(attackerColor.getOpposite());
		int direction = attackerColor.isWhite()
				? -1
				: 1;
		int flags = 0;
		for (int i = -1; i <= 1; i += 2) {
			int targetRow = kingPosition.getRow() - direction;
			int targetColumn = kingPosition.getColumn() + i;
			if (isInsideBoard(targetRow, targetColumn)) {
				Piece piece = grid[targetRow][targetColumn];
				if (piece != null && piece.isPawn() && piece.getColor().equals(attackerColor)) {
					if (GameConstants.isCheck(flags)) {
						flags |= GameConstants.DOUBLE_CHECK;
					}
					flags |= GameConstants.CHECK;
				}
			}
		}
		return flags;
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
	private boolean isKingInCheckWithMove(
			Position originPosition,
			Position targetPosition,
			Position capturedPawnEnPassant
	) {
		if (!gameInfo.isKingPresent(grid[originPosition.getRow()][originPosition.getColumn()].getColor())) {
			return false;
		}
		Color color = grid[originPosition.getRow()][originPosition.getColumn()].getColor();
		Piece temp = grid[targetPosition.getRow()][targetPosition.getColumn()];
		grid[targetPosition.getRow()][targetPosition.getColumn()] = grid[originPosition.getRow()][originPosition
			.getColumn()];
		grid[originPosition.getRow()][originPosition.getColumn()] = null;
		Piece capturedEnPassantPawn = null;
		if (capturedPawnEnPassant != null) {
			capturedEnPassantPawn = grid[capturedPawnEnPassant.getRow()][capturedPawnEnPassant.getColumn()];
			grid[capturedPawnEnPassant.getRow()][capturedPawnEnPassant.getColumn()] = null;
		}
		Position kingPosition = grid[targetPosition.getRow()][targetPosition.getColumn()].isKing()
				? targetPosition
				: gameInfo.getKingPosition(color);
		boolean underAttack = isUnderAttack(
			kingPosition,
			grid[kingPosition.getRow()][kingPosition.getColumn()].getColor().getOpposite()
		);
		grid[originPosition.getRow()][originPosition.getColumn()] = grid[targetPosition.getRow()][targetPosition
			.getColumn()];
		grid[targetPosition.getRow()][targetPosition.getColumn()] = temp;
		if (capturedPawnEnPassant != null) {
			grid[capturedPawnEnPassant.getRow()][capturedPawnEnPassant.getColumn()] = capturedEnPassantPawn;
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
						if (grid[targetRow][targetColumn] != null) {
							Piece targetPiece = grid[targetRow][targetColumn];
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
				Piece piece = grid[targetRow][targetColumn];
				if (piece != null && piece.isPawn() && piece.getColor().equals(color)) {
					return true;
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
				Piece piece = grid[targetRow][targetColumn];
				if (piece != null
						&& piece.isKnight()
						&& piece.getColor().equals(color)) {
					return true;
				}
			}
		}
		return false;
	}

	private List<MovementTarget> getCastlingTargets(Position kingSquare, boolean extractCheckFlags) {
		List<Position> rookPositions = new ArrayList<>(2);
		Piece kingPiece = grid[kingSquare.getRow()][kingSquare.getColumn()];
		if (gameInfo.isKingSideCastlingAvailable(kingPiece.getColor())) {
			rookPositions.add(getKingSideRookSquare(kingPiece.getColor()));
		}
		if (gameInfo.isQueenSideCastlingAvailable(kingPiece.getColor())) {
			rookPositions.add(getQueenSideRookSquare(kingPiece.getColor()));
		}

		if (rookPositions.isEmpty()) {
			return Collections.emptyList();
		}
		List<MovementTarget> targets = new ArrayList<>();
		for (Position rookPosition : rookPositions) {
			int direction = Integer.signum(rookPosition.getColumn() - kingSquare.getColumn());
			int tLength = 0;
			boolean invalidCastling = false;
			int distance = Math.abs(rookPosition.getColumn() - kingSquare.getColumn());
			while (!invalidCastling && tLength < distance) {
				int targetColumn = kingSquare.getColumn() + tLength * direction;
				if (tLength <= 2 && isUnderAttack(
					Position.of(kingSquare.getRow(), targetColumn),
					kingPiece.getColor().getOpposite()
				)) {
					invalidCastling = true;
				}
				if (tLength > 0 && grid[kingSquare.getRow()][targetColumn] != null) {
					invalidCastling = true;
				}
				tLength++;
			}
			if (!invalidCastling) {
				int targetKingColumn = kingSquare.getColumn() + 2 * direction;
				Position targetPosition = Position.of(kingSquare.getRow(), targetKingColumn);
				int movementFlags = GameConstants.CASTLING;
				if (extractCheckFlags) {
					movementFlags |= extractExtraMovementFlags(
						kingPiece,
						kingSquare,
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

	private PieceMovement getPawnMovements(Position pawnSquare, boolean extractCheckFlags) {
		List<MovementTarget> targets = new ArrayList<>();
		PieceMovementMeta.Builder pieceMovementMetaBuilder = PieceMovementMeta.builder();
		MovementOrigin movementOrigin = new MovementOrigin(
			grid[pawnSquare.getRow()][pawnSquare.getColumn()],
			pawnSquare
		);
		for (DirectionAdjuster directionAjduster : getMovementTemplate(
			grid[pawnSquare.getRow()][pawnSquare.getColumn()]
		)) {
			int targetRow = pawnSquare.getRow() + directionAjduster.getRowAdjuster();
			int targetColumn = pawnSquare.getColumn() + directionAjduster.getColumnAdjuster();
			if (isInsideBoard(targetRow, targetColumn)) {
				Position targetPosition = Position.of(targetRow, targetColumn);
				boolean isMovementForward = isValidPawnMovementForward(pawnSquare, targetPosition);
				boolean isCapture = isValidPawnCaptureMovement(pawnSquare, targetPosition);
				boolean isEnPassant = isCapture && grid[targetRow][targetColumn] == null;
				if ((isMovementForward || isCapture)
						&& !isKingInCheckWithMove(
							pawnSquare,
							targetPosition,
							isEnPassant ? Position.of(pawnSquare.getRow(), targetColumn) : null
						)) {
					boolean isPawnPromotion = targetRow == getPawnPromotionRow(
						grid[pawnSquare.getRow()][pawnSquare.getColumn()].getColor()
					);
					List<PieceType> targetPieces = isPawnPromotion
							? pawnPromotionReplacements
							: pawnNonPromotionReplacements;
					for (PieceType pieceType : targetPieces) {
						int movementFlags = 0;
						pieceMovementMetaBuilder.incrementTotalMovements();
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
						Piece targetPiece = Piece.get(
							pieceType,
							grid[pawnSquare.getRow()][pawnSquare.getColumn()].getColor()
						);
						if (extractCheckFlags) {
							movementFlags |= extractExtraMovementFlags(
								movementOrigin.getPiece(),
								movementOrigin.getPosition(),
								targetPiece,
								targetPosition,
								movementFlags
							);
							updatePieceMovementMetaWithCheckFlags(
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

	private int getPawnPromotionRow(Color color) {
		return color.isWhite()
				? WHITE_PAWN_PROMOTION_ROW
				: BLACK_PAWN_PROMOTION_ROW;
	}

	private boolean isValidPawnCaptureMovement(Position pawnSquare, Position targetSquare) {
		if (pawnSquare.getColumn() == targetSquare.getColumn()) {
			return false;
		}
		Piece targetPiece = grid[targetSquare.getRow()][targetSquare.getColumn()];
		int moveValue = grid[pawnSquare.getRow()][pawnSquare.getColumn()].getValue()
				* (targetPiece == null ? 0 : targetPiece.getValue());
		if (moveValue < 0) {
			return true;
		}
		return moveValue == 0
				&& gameInfo.enPassantTargetSquare != null
				&& gameInfo.enPassantTargetSquare.equals(targetSquare);
	}

	private boolean isValidPawnMovementForward(Position pawnSquare, Position targetSquare) {
		if (grid[targetSquare.getRow()][targetSquare.getColumn()] != null) {
			return false;
		}
		if (pawnSquare.getColumn() != targetSquare.getColumn()) {
			return false;
		}
		if (Math.abs(pawnSquare.getRow() - targetSquare.getRow()) == 2) {
			int pawnInitialRow = getPawnInitialRow(grid[pawnSquare.getRow()][pawnSquare.getColumn()].getColor());
			if (pawnInitialRow != pawnSquare.getRow()) {
				return false;
			}
			int midRow = (pawnSquare.getRow() + targetSquare.getRow()) / 2;
			if (grid[midRow][pawnSquare.getColumn()] != null) {
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

	public String toString(boolean printCoordinates) {
		StringBuilder builder = new StringBuilder();
		builder.append("┌───┬───┬───┬───┬───┬───┬───┬───┐").append(NEWLINE);
		for (int row = 0; row < GameConstants.BOARD_SIZE; row++) {
			for (int column = 0; column < GameConstants.BOARD_SIZE; column++) {
				builder.append("│");
				if (grid[row][column] == null) {
					builder.append("   ");
				} else {
					builder
						.append(' ')
						.append(grid[row][column].getLetterSymbol())
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
				if (column > 0) {
					builder.append(' ');
				}
				if (grid[row][column] == null) {
					builder.append('.');
				} else {
					builder.append(grid[row][column].getLetterSymbol());
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

	private static Color calculateNextSideToMove(int movementCounter, int movementAdjuster) {
		return (movementCounter + movementAdjuster) % 2 == 0 ? Color.WHITE : Color.BLACK;
	}

	private static class GameInfo implements Copiable<GameInfo> {

		int moveSideAdjuster;

		int moveCounter = 0;

		int fullMoveCounter = 1;

		int halfMoveCounter = 0;

		Position enPassantTargetSquare = null;

		Position blackKingPosition;

		Position whiteKingPosition;

		int castlingFlags;

		GameInfo() {
		}

		void setKingPosition(Color color, Position position) {
			if (color.isWhite()) {
				whiteKingPosition = position;
			} else {
				blackKingPosition = position;
			}
		}

		Position getKingPosition(Color color) {
			return color.isWhite()
					? whiteKingPosition
					: blackKingPosition;
		}

		boolean isKingPresent(Color color) {
			return color.isWhite()
					? whiteKingPosition != null
					: blackKingPosition != null;
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
			castlingFlags |= config.isBlackQueenSideCastlingAvailable() ? GameConstants.BLACK_QUEEN_SIDE_CASTLING : 0;
			castlingFlags |= config.isWhiteKingSideCastlingAvailable() ? GameConstants.WHITE_KING_SIDE_CASTLING : 0;
			castlingFlags |= config.isWhiteQueenSideCastlingAvailable() ? GameConstants.WHITE_QUEEN_SIDE_CASTLING : 0;
		}

		@Override
		public GameInfo copy() {
			GameInfo copy = new GameInfo();
			copy.moveSideAdjuster = moveSideAdjuster;
			copy.moveCounter = moveCounter;
			copy.fullMoveCounter = fullMoveCounter;
			copy.halfMoveCounter = halfMoveCounter;
			copy.enPassantTargetSquare = enPassantTargetSquare;
			copy.castlingFlags = castlingFlags;
			copy.whiteKingPosition = whiteKingPosition;
			copy.blackKingPosition = blackKingPosition;
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
				.append(calculateNextSideToMove(moveCounter, moveSideAdjuster))
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
