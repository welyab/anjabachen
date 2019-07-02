package com.welyab.anjabachen;

public class BoardConfig {

	/** Default side to play next move is white. */
	public static final Color DEFAULT_SIDE_TO_MOVE = Color.WHITE;

	/** Game starts at full move counter equals 1, and otherwise, when not informed, also is 1 */
	public static final int DEFAULT_FULL_MOVE_COUNTER = 1;

	public static final int DEFAULT_HALF_MOVE_COUNTER = 0;

	/**
	 * Default value flag for indicate if the castling is available for king side of black pieces.
	 */
	public static final boolean BLACK_KING_SIDE_CASTLING_AVAILABLE = false;

	/**
	 * Default value flag for indicate if the castling is available for queen side of black pieces.
	 */
	public static final boolean BLACK_QUEEN_SIDE_CASTLING_AVAILABLE = false;

	/**
	 * Default value flag for indicate if the castling is available for king side of white pieces.
	 */
	public static final boolean WHITE_KING_SIDE_CASTLING_AVAILABLE = false;

	/**
	 * Default value flag for indicate if the castling is available for queen side of white pieces.
	 */
	public static final boolean WHITE_QUEEN_SIDE_CASTLING_AVAILABLE = false;

	/** Specifies the side to play the next movement. */
	private Color sideToMove = DEFAULT_SIDE_TO_MOVE;

	/** Holds the movement counter. */
	private int fullMoveCounter = DEFAULT_FULL_MOVE_COUNTER;

	private int halfMoveCounter = DEFAULT_HALF_MOVE_COUNTER;

	/** Indicates if the castling is available for the king side of black pieces. */
	private boolean blackKingSideCastlingAvailable = BLACK_KING_SIDE_CASTLING_AVAILABLE;

	/** Indicates if the castling is available for the king side of black pieces. */
	private boolean blackQueenSideCastlingAvailable = BLACK_QUEEN_SIDE_CASTLING_AVAILABLE;

	/** Indicates if the castling is available for the king side of black pieces. */
	private boolean whiteKingSideCastlingAvailable = WHITE_KING_SIDE_CASTLING_AVAILABLE;

	/** Indicates if the castling is available for the king side of black pieces. */
	private boolean whiteQueenSideCastlingAvailable = WHITE_QUEEN_SIDE_CASTLING_AVAILABLE;

	/**
	 * When <i>en passant</i> is available, this field holds the target square of being moved pawn.
	 */
	private Position enPassantTargetSquare;

	@SuppressWarnings("javadoc")
	private BoardConfig() {
	}

	public Color getSideToMove() {
		return sideToMove;
	}

	public int getFullMoveCounter() {
		return fullMoveCounter;
	}

	public boolean isBlackKingSideCastlingAvailable() {
		return blackKingSideCastlingAvailable;
	}

	public boolean isBlackQueenSideCastlingAvailable() {
		return blackQueenSideCastlingAvailable;
	}

	public boolean isWhiteKingSideCastlingAvailable() {
		return whiteKingSideCastlingAvailable;
	}

	public boolean isWhiteQueenSideCastlingAvailable() {
		return whiteQueenSideCastlingAvailable;
	}

	public Position getEnPassantTargetSquare() {
		return enPassantTargetSquare;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static BoardConfig defaultBlackToMove() {
		return builder().sideToMove(Color.BLACK).build();
	}

	public static BoardConfig defaultWhiteToMove() {
		return builder().sideToMove(Color.WHITE).build();
	}

	public static final class Builder {

		BoardConfig boardConfig = new BoardConfig();

		private Builder() {
		}

		public Builder sideToMove(Color color) {
			boardConfig.sideToMove = color;
			return this;
		}

		public Builder fullMoveCounter(int fullMoveCounter) {
			boardConfig.fullMoveCounter = fullMoveCounter;
			return this;
		}

		public Builder halfMoveCounter(int halfMoveCounter) {
			boardConfig.halfMoveCounter = halfMoveCounter;
			return this;
		}

		public Builder blackKingSideCastlingAvailable(boolean blackKingSideCastlingAvailable) {
			boardConfig.blackKingSideCastlingAvailable = blackKingSideCastlingAvailable;
			return this;
		}

		public Builder blackQueenSideCastlingAvailable(boolean blackQueenSideCastlingAvailable) {
			boardConfig.blackQueenSideCastlingAvailable = blackQueenSideCastlingAvailable;
			return this;
		}

		public Builder whiteKingSideCastlingAvailable(boolean whiteKingSideCastlingAvailable) {
			boardConfig.whiteKingSideCastlingAvailable = whiteKingSideCastlingAvailable;
			return this;
		}

		public Builder whiteQueenSideCastlingAvailable(boolean whiteQueenSideCastlingAvailable) {
			boardConfig.whiteQueenSideCastlingAvailable = whiteQueenSideCastlingAvailable;
			return this;
		}

		public Builder enPassantTargetSquare(Position enPassantTargetSquare) {
			boardConfig.enPassantTargetSquare = enPassantTargetSquare;
			return this;
		}

		public BoardConfig build() {
			return boardConfig;
		}
	}
}
