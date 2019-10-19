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
	
	/** The bit mask for white king's side castling. */
	public static final byte WHITE_KING_SIDE_CASTLING_FLAG_MASK = 0b1000;
	
	/** The bit mask for white queen's side castling. */
	public static final byte WHITE_QUEEN_SIDE_CASTLING_FLAG_MASK = 0b0100;
	
	/** The bit mask for black king's side castling. */
	public static final byte BLACK_KING_SIDE_CASTLING_FLAG_MASK = 0b0010;
	
	/** The bit mask for black queen's side castling. */
	public static final byte BLACK_QUEEN_SIDE_CASTLING_FLAG_MASK = 0b0001;
	
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
				String.format("Invalid color letter: %c", colorLetter)
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
	
	/**
	 * Evaluates if given castling flags is marked with flag for castling movement for white pieces
	 * on king side.
	 * 
	 * @param castlingFlags The castling flags.
	 * 
	 * @return A value <code>true</code> if the movement is available, or <code>false</code>.
	 */
	public static boolean isWhiteKingSideCaslting(int castlingFlags) {
		return (castlingFlags & WHITE_KING_SIDE_CASTLING_FLAG_MASK) != 0;
	}
	
	/**
	 * Evaluates if given castling flags is marked with flag for castling movement for white pieces
	 * on queen side.
	 * 
	 * @param castlingFlags The castling flags.
	 * 
	 * @return A value <code>true</code> if the movement is available, or <code>false</code>.
	 */
	public static boolean isWhiteQueenSideCaslting(int castlingFlags) {
		return (castlingFlags & WHITE_QUEEN_SIDE_CASTLING_FLAG_MASK) != 0;
	}
	
	/**
	 * Evaluates if given castling flags is marked with flag for castling movement for black pieces
	 * on king side.
	 * 
	 * @param castlingFlags The castling flags.
	 * 
	 * @return A value <code>true</code> if the movement is available, or <code>false</code>.
	 */
	public static boolean isBlackKingSideCaslting(int castlingFlags) {
		return (castlingFlags & BLACK_KING_SIDE_CASTLING_FLAG_MASK) != 0;
	}
	
	/**
	 * Evaluates if given castling flags is marked with flag for castling movement for black pieces
	 * on queen side.
	 * 
	 * @param castlingFlags The castling flags.
	 * 
	 * @return A value <code>true</code> if the movement is available, or <code>false</code>.
	 */
	public static boolean isBlackQueenSideCaslting(int castlingFlags) {
		return (castlingFlags & BLACK_QUEEN_SIDE_CASTLING_FLAG_MASK) != 0;
	}
	
	/**
	 * Encode the given flags into the bits of a byte number. The meaning of each bit is described
	 * as follow:
	 * 
	 * <pre>
	 *     +-----> 4º least significant bit
	 *     |+----> 3º least significant bit
	 *     ||+---> 2º least significant bit
	 *     |||+--> 1º least significant bit  
	 *     ||||
	 *     vvvv
	 * 0b000000
	 *     ^^^^
	 *     ||||
	 *     |||+--> if castling is available for white pieces on the king side
	 *     ||+---> if castling is available for white pieces on the queen side
	 *     |+----> if castling is available for black pieces on the king side
	 *     +-----> if castling is available for black pieces on the queen side
	 * </pre>
	 * 
	 * @param isWhiteKingSideCastling If the castling is available for white pieces on the king
	 *        side.
	 * @param isWhiteQueenSideCastling If the castling is available for white pieces on the queen
	 *        side.
	 * @param isBlackKingSideCastling If the castling is available for black pieces on the king
	 *        side.
	 * @param isBlackQueenSideCastling If the castling is available for black pieces on the queen
	 *        side.
	 * 
	 * @return The castling encoded into bits.
	 */
	public static byte muxCastlingFlags(
			boolean isWhiteKingSideCastling,
			boolean isWhiteQueenSideCastling,
			boolean isBlackKingSideCastling,
			boolean isBlackQueenSideCastling
	) {
		byte flags = 0;
		flags |= (isWhiteKingSideCastling ? WHITE_KING_SIDE_CASTLING_FLAG_MASK : 0);
		flags |= (isWhiteQueenSideCastling ? WHITE_QUEEN_SIDE_CASTLING_FLAG_MASK : 0);
		flags |= (isBlackKingSideCastling ? BLACK_KING_SIDE_CASTLING_FLAG_MASK : 0);
		flags |= (isBlackQueenSideCastling ? BLACK_QUEEN_SIDE_CASTLING_FLAG_MASK : 0);
		return flags;
	}
	
	/**
	 * Evaluates if the given piece code is the identifier for a king piece (white or black).
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for the king piece
	 *         (white or black), or <code>false</code> if not.
	 */
	public static boolean isKing(int pieceCode) {
		return pieceCode == WHITE_KING_CODE || pieceCode == BLACK_KING_CODE;
	}
	
	/**
	 * Evaluates if the given piece code is the identifier for a queen piece (white or black).
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for the queen piece
	 *         (white or black), or <code>false</code> if not.
	 */
	public static boolean isQueen(int pieceCode) {
		return pieceCode == WHITE_QUEEN_CODE || pieceCode == BLACK_QUEEN_CODE;
	}
	
	/**
	 * Evaluates if the given piece code is the identifier for a rook piece (white or black).
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for the rook piece
	 *         (white or black), or <code>false</code> if not.
	 */
	public static boolean isRook(int pieceCode) {
		return pieceCode == WHITE_ROOK_CODE || pieceCode == BLACK_ROOK_CODE;
	}
	
	/**
	 * Evaluates if the given piece code is the identifier for a bishop piece (white or black).
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for the bishop
	 *         piece (white or black), or <code>false</code> if not.
	 */
	public static boolean isBishop(int pieceCode) {
		return pieceCode == WHITE_BISHOP_CODE || pieceCode == BLACK_BISHOP_CODE;
	}
	
	/**
	 * Evaluates if the given piece code is the identifier for a knight piece (white or black).
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for the knight
	 *         piece (white or black), or <code>false</code> if not.
	 */
	public static boolean isKnight(int pieceCode) {
		return pieceCode == WHITE_KNIGHT_CODE || pieceCode == BLACK_KNIGHT_CODE;
	}
	
	/**
	 * Evaluates if the given piece code is the identifier for a pawn piece (white or black).
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for the pawn piece
	 *         (white or black), or <code>false</code> if not.
	 */
	public static boolean isPawn(int pieceCode) {
		return pieceCode == WHITE_PAWN_CODE || pieceCode == BLACK_PAWN_CODE;
	}
	
	/**
	 * Evaluates if the given piece code is the identifier for the white king piece.
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for the white king
	 *         piece, or <code>false</code> if not.
	 */
	public static boolean isWhiteKing(int pieceCode) {
		return pieceCode == WHITE_KING_CODE;
	}
	
	/**
	 * Evaluates if the given piece code is the identifier for the white queen piece.
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for the white
	 *         queen piece, or <code>false</code> if not.
	 */
	public static boolean isWhiteQueen(int pieceCode) {
		return pieceCode == WHITE_QUEEN_CODE;
	}
	
	/**
	 * Evaluates if the given piece code is the identifier for the white rook piece.
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for the white rook
	 *         piece, or <code>false</code> if not.
	 */
	public static boolean isWhiteRook(int pieceCode) {
		return pieceCode == WHITE_ROOK_CODE;
	}
	
	/**
	 * Evaluates if the given piece code is the identifier for the white bishop piece.
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for the white
	 *         bishop piece, or <code>false</code> if not.
	 */
	public static boolean isWhiteBishop(int pieceCode) {
		return pieceCode == WHITE_BISHOP_CODE;
	}
	
	/**
	 * Evaluates if the given piece code is the identifier for the white knight piece.
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for the white
	 *         knight piece, or <code>false</code> if not.
	 */
	public static boolean isWhiteKnight(int pieceCode) {
		return pieceCode == WHITE_KNIGHT_CODE;
	}
	
	/**
	 * Evaluates if the given piece code is the identifier for the white pawn piece piece.
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for the white pawn
	 *         piece, or <code>false</code> if not.
	 */
	public static boolean isWhitePawn(int pieceCode) {
		return pieceCode == WHITE_PAWN_CODE;
	}
	
	/**
	 * Evaluates if the given piece code is the identifier for the black king piece.
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for the black king
	 *         piece, or <code>false</code> if not.
	 */
	public static boolean isBlackKing(int pieceCode) {
		return pieceCode == BLACK_KING_CODE;
	}
	
	/**
	 * Evaluates if the given piece code is the identifier for the black queen piece.
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for the black
	 *         queen piece, or <code>false</code> if not.
	 */
	public static boolean isBlackQueen(int pieceCode) {
		return pieceCode == BLACK_QUEEN_CODE;
	}
	
	/**
	 * Evaluates if the given piece code is the identifier for the black black rook piece.
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for the black rook
	 *         piece, or <code>false</code> if not.
	 */
	public static boolean isBlackRook(int pieceCode) {
		return pieceCode == BLACK_ROOK_CODE;
	}
	
	/**
	 * Evaluates if the given piece code is the identifier for the black black bishop.
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for the black
	 *         bishop, or <code>false</code> if not.
	 */
	public static boolean isBlackBishop(int pieceCode) {
		return pieceCode == BLACK_BISHOP_CODE;
	}
	
	/**
	 * Evaluates if the given piece code is the identifier for the black knight piece.
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for the black
	 *         knight piece, or <code>false</code> if not.
	 */
	public static boolean isBlackKnight(int pieceCode) {
		return pieceCode == BLACK_KNIGHT_CODE;
	}
	
	/**
	 * Evaluates if the given piece code is the identifier for the black pawn piece.
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for the black pawn
	 *         piece, or <code>false</code> if not.
	 */
	public static boolean isBlackPawn(int pieceCode) {
		return pieceCode == BLACK_PAWN_CODE;
	}
	
	/**
	 * Evaluates if the given piece code is a representative code for a white piece.
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is a code for white piece, of
	 *         <code>false</code> if not.
	 */
	public static boolean isWhitePiece(int pieceCode) {
		return pieceCode > 0;
	}
	
	/**
	 * Evaluates if the given piece code is a representative code for a black piece.
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is a code for black piece, or
	 *         <code>false</code> if not.
	 */
	public static boolean isBlackPiece(int pieceCode) {
		return pieceCode < 0;
	}
	
	/**
	 * Retrieves the color code for the given piece code.
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return The color code ({@linkplain #WHITE_COLOR_CODE white} or {@linkplain #BLACK_COLOR_CODE
	 *         code}).
	 */
	public static byte getColorCode(int pieceCode) {
		if (pieceCode < 0) {
			return BLACK_COLOR_CODE;
		}
		if (pieceCode > 0) {
			return WHITE_COLOR_CODE;
		}
		throw new IllegalArgumentException("Invalid piece code: 0");
	}
	
	/**
	 * Evaluates if the given color code is the representative code for white color.
	 * 
	 * @param pieceColor The color code.
	 * 
	 * @return A value <code>true</code> if the code is the identifier for the white color, or
	 *         <code>false</code> if not.
	 */
	public static boolean isWhiteColor(int pieceColor) {
		return pieceColor == WHITE_COLOR_CODE;
	}
	
	/**
	 * Evaluates if the given color code is the representative code for black color.
	 * 
	 * @param pieceColor The color code.
	 * 
	 * @return A value <code>true</code> if the code is the identifier for the black color, or
	 *         <code>false</code> if not.
	 */
	public static boolean isBlaclColor(int pieceColor) {
		return pieceColor == BLACK_COLOR_CODE;
	}
}
