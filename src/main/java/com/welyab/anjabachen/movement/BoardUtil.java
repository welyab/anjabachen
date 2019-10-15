package com.welyab.anjabachen.movement;

/**
 * A set of utility methods and constants used in movement generation.
 * 
 * @author Welyab Paula
 */
public class BoardUtil {
	
	/** The board size in terms of squares width and height. */
	public static final int SIZE = 8;
	
	/** FEN string for initial board disposition. */
	public static final String FEN_INITIAL_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
	
	/** Representative letter for the white color. */
	public static final char WHITE_COLOR_LETTER = 'w';
	
	/** Representative letter for the black color. */
	public static final char BLACK_COLOR_LETTER = 'b';
	
	/** The color code for white color. */
	public static final byte WHITE_COLOR_CODE = 1;
	
	/** The color code for black color. */
	public static final byte BLACK_COLOR_CODE = -1;
	
	/** The letter representative for the white king piece. */
	public static final char WHITE_KING_LETTER = 'K';
	
	/** The letter representative for the white queen piece. */
	public static final char WHITE_QUEEN_LETTER = 'Q';
	
	/** The letter representative for the white rook piece. */
	public static final char WHITE_ROOK_LETTER = 'R';
	
	/** The letter representative for the white bishop piece. */
	public static final char WHITE_BISHOP_LETTER = 'B';
	
	/** The letter representative for the white knight piece. */
	public static final char WHITE_KNIGHT_LETTER = 'N';
	
	/** The letter representative for the white pawn piece. */
	public static final char WHITE_PAWN_LETTER = 'P';
	
	/** The letter representative for the black king piece. */
	public static final char BLACK_KING_LETTER = 'k';
	
	/** The letter representative for the black queen piece. */
	public static final char BLACK_QUEEN_LETTER = 'q';
	
	/** The letter representative for the black rook piece. */
	public static final char BLACK_ROOK_LETTER = 'r';
	
	/** The letter representative for the black bishop piece. */
	public static final char BLACK_BISHOP_LETTER = 'b';
	
	/** The letter representative for the black knight piece. */
	public static final char BLACK_KNIGHT_LETTER = 'n';
	
	/** The letter representative for the black pawn piece. */
	public static final char BLACK_PAWN_LETTER = 'p';
	
	/** The letter for the white king piece. */
	public static final byte WHITE_KING_CODE = 6;
	
	/** The letter for the white queen piece. */
	public static final byte WHITE_QUEEN_CODE = 5;
	
	/** The code for the white rook piece. */
	public static final byte WHITE_ROOK_CODE = 4;
	
	/** The code for the white bishop piece. */
	public static final byte WHITE_BISHOP_CODE = 3;
	
	/** The code for the white knight piece. */
	public static final byte WHITE_KNIGHT_CODE = 2;
	
	/** The code for the white pawn piece. */
	public static final byte WHITE_PAWN_CODE = 1;
	
	/** The code for the black king piece. */
	public static final byte BLACK_KING_CODE = -6;
	
	/** The code for the black queen piece. */
	public static final byte BLACK_QUEEN_CODE = -5;
	
	/** The code for the black rook piece. */
	public static final byte BLACK_ROOK_CODE = -4;
	
	/** The code for the black bishop piece. */
	public static final byte BLACK_BISHOP_CODE = -3;
	
	/** The code for the black knight piece. */
	public static final byte BLACK_KNIGHT_CODE = -2;
	
	/** The code for the black pawn piece. */
	public static final byte BLACK_PAWN_CODE = -1;
	
	/** The value indicative for a non piece (a empty square, for sample). */
	public static final byte NO_PIECE_CODE = 0;
	
	/**
	 * Piece code cache. Each index is associated with a piece code. The indexes are piece letters
	 * minus the <code>'A'</code> letter.
	 */
	private static final byte[] PIECE_CODES = new byte[52];
	
	static {
		PIECE_CODES[WHITE_KING_LETTER - 'A'] = WHITE_KING_CODE;
		PIECE_CODES[WHITE_QUEEN_LETTER - 'A'] = WHITE_QUEEN_CODE;
		PIECE_CODES[WHITE_ROOK_LETTER - 'A'] = WHITE_ROOK_CODE;
		PIECE_CODES[WHITE_BISHOP_LETTER - 'A'] = WHITE_BISHOP_CODE;
		PIECE_CODES[WHITE_KNIGHT_LETTER - 'A'] = WHITE_KNIGHT_CODE;
		PIECE_CODES[WHITE_PAWN_LETTER - 'A'] = WHITE_PAWN_CODE;
		PIECE_CODES[BLACK_KING_LETTER - 'A'] = BLACK_KING_CODE;
		PIECE_CODES[BLACK_QUEEN_LETTER - 'A'] = BLACK_QUEEN_CODE;
		PIECE_CODES[BLACK_ROOK_LETTER - 'A'] = BLACK_ROOK_CODE;
		PIECE_CODES[BLACK_BISHOP_LETTER - 'A'] = BLACK_BISHOP_CODE;
		PIECE_CODES[BLACK_KNIGHT_LETTER - 'A'] = BLACK_KNIGHT_CODE;
		PIECE_CODES[BLACK_PAWN_LETTER - 'A'] = BLACK_PAWN_CODE;
	}
	
	/**
	 * Piece letter cache. Each index is associated with a piece letter. The indexes are the piece
	 * code plus <code>6</code>.
	 */
	private static final char[] PIECE_LETTERS = new char[13];
	
	static {
		PIECE_LETTERS[WHITE_KING_CODE + 6] = WHITE_KING_LETTER;
		PIECE_LETTERS[WHITE_QUEEN_CODE + 6] = WHITE_QUEEN_LETTER;
		PIECE_LETTERS[WHITE_ROOK_CODE + 6] = WHITE_ROOK_LETTER;
		PIECE_LETTERS[WHITE_BISHOP_CODE + 6] = WHITE_BISHOP_LETTER;
		PIECE_LETTERS[WHITE_KNIGHT_CODE + 6] = WHITE_KNIGHT_LETTER;
		PIECE_LETTERS[WHITE_PAWN_CODE + 6] = WHITE_PAWN_LETTER;
		PIECE_LETTERS[BLACK_KING_CODE + 6] = BLACK_KING_LETTER;
		PIECE_LETTERS[BLACK_QUEEN_CODE + 6] = BLACK_QUEEN_LETTER;
		PIECE_LETTERS[BLACK_ROOK_CODE + 6] = BLACK_ROOK_LETTER;
		PIECE_LETTERS[BLACK_BISHOP_CODE + 6] = BLACK_BISHOP_LETTER;
		PIECE_LETTERS[BLACK_KNIGHT_CODE + 6] = BLACK_KNIGHT_LETTER;
		PIECE_LETTERS[BLACK_PAWN_CODE + 6] = BLACK_PAWN_LETTER;
	}
	
	/**
	 * Retrieves the piece code associated with a specific piece letter.
	 * 
	 * @param pieceLetter The piece letter (<code>'K'</code>, <code>'Q'</code>,
	 *        <code>'R'</code>, <code>'B'</code>, <code>'N'</code>, <code>'P'</code>,
	 *        <code>'k'</code>, <code>'q'</code>, <code>'r'</code>, <code>'b'</code>,
	 *        <code>'n'</code>, <code>'p'</code>).
	 * 
	 * @return The piece code associated with given code.
	 * 
	 * @throws IllegalArgumentException If the given piece letter is invalid.
	 */
	public static byte pieceLetterToCode(char pieceLetter) {
		int index = pieceLetter - 'A';
		if (index < 0 || index >= PIECE_CODES.length || PIECE_CODES[index] == 0) {
			throw new IllegalArgumentException(String.format("Invaild piece letter: %c", pieceLetter));
		}
		return PIECE_CODES[index];
	}
	
	/**
	 * Retrieve the piece letter associated with a specific piece code.
	 * 
	 * @param pieceCode The piece code (<code>-6</code>, <code>-5</code>, <code>-4</code>,
	 *        <code>-3</code>, <code>-2</code>, <code>-1</code>, <code>1</code>, <code>2</code>,
	 *        <code>3</code>, <code>4</code>, <code>5</code>, <code>6</code>).
	 * 
	 * @return The piece letter.
	 * 
	 * @throws IllegalArgumentException If the given piece code is invalid.
	 */
	public static char pieceCodeToLetter(int pieceCode) {
		int index = pieceCode + 6;
		if (index < 0 || index >= PIECE_LETTERS.length || PIECE_LETTERS[index] == 0) {
			throw new IllegalArgumentException(String.format("Invaild piece code: %d", pieceCode));
		}
		return PIECE_LETTERS[index];
	}
	
	/**
	 * Retrieves the color code associated with given color letter.
	 * 
	 * @param colorLetter The color letter.
	 * 
	 * @return The associated color code.
	 * 
	 * @throws IllegalArgumentException If the given color letter is invalid.
	 */
	public static byte colorLetterToCode(char colorLetter) {
		return switch (colorLetter) {
			case WHITE_COLOR_LETTER -> WHITE_COLOR_CODE;
			case BLACK_COLOR_LETTER -> BLACK_COLOR_CODE;
			default -> throw new IllegalArgumentException(
				String.format("Invalid color char: %c", colorLetter)
			);
		};
	}
	
	/**
	 * Retrieves the color letter associated with given color code.
	 * 
	 * @param colorCode The color code.
	 * 
	 * @return The associated color letter.
	 * 
	 * @throws IllegalArgumentException If the given color code is invalid.
	 */
	public static char colorCodeToLetter(int colorCode) {
		return switch (colorCode) {
			case WHITE_COLOR_CODE -> WHITE_COLOR_LETTER;
			case BLACK_COLOR_CODE -> BLACK_COLOR_LETTER;
			default -> throw new IllegalArgumentException(
				String.format("Invalid color code: %d", colorCode)
			);
		};
	}
	
	/**
	 * Retrieves the opposite color for the given color; if color code is white, then returns the
	 * color code for the black piece, and vice versa.
	 * 
	 * @param colorCode The color code.
	 * 
	 * @return The opposite color code.
	 * 
	 * @throws IllegalArgumentException If the given color code is invalid.
	 */
	public static byte oppositeColor(int colorCode) {
		return switch (colorCode) {
			case WHITE_COLOR_CODE -> BLACK_COLOR_CODE;
			case BLACK_COLOR_CODE -> WHITE_COLOR_CODE;
			default -> throw new IllegalArgumentException(
				String.format("Invalid color code: %d", colorCode)
			);
		};
	}
}
