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
import java.util.Random;

/**
 * A set of utility methods and constants used in movement generation.
 * 
 * <p>
 * Piece types identifies pieces without color distinction:
 * 
 * <ul>
 * <li>{@link #KING} - {@link #isKing(int)}
 * <li>{@link #QUEEN} - {@link #isQueen(int)}
 * <li>{@link #ROOK} - {@link #isRook(int)}
 * <li>{@link #BISHOP} - {@link #isBishop(int)}
 * <li>{@link #KNIGHT} - {@link #isKnight(int)}
 * <li>{@link #PAWN} - {@link #isPawn(int)}
 * </ul>
 * 
 * <p>
 * Also, there are constants for identify pieces with its colors.
 * 
 * <p>
 * White pieces:
 * 
 * <ul>
 * <li>{@link #WHITE_KING} - {@link #isWhiteKing(int)}
 * <li>{@link #WHITE_QUEEN} - {@link #isWhiteQueen(int)}
 * <li>{@link #WHITE_ROOK} - {@link #isWhiteRook(int)}
 * <li>{@link #WHITE_BISHOP} - {@link #isWhiteBishop(int)}
 * <li>{@link #WHITE_KNIGHT} - {@link #isWhiteKnight(int)}
 * <li>{@link #WHITE_PAWN} - {@link #isWhitePawn(int)}
 * </ul>
 * 
 * <p>
 * Black pieces:
 * 
 * <ul>
 * <li>{@link #BLACK_KING} - {@link #isBlackKing(int)}
 * <li>{@link #BLACK_QUEEN} - {@link #isBlackQueen(int)}
 * <li>{@link #BLACK_ROOK} - {@link #isBlackRook(int)}
 * <li>{@link #BLACK_BISHOP} - {@link #isBlackBishop(int)}
 * <li>{@link #BLACK_KNIGHT} - {@link #isBlackKnight(int)}
 * <li>{@link #BLACK_PAWN} - {@link #isBlackPawn(int)}
 * </ul>
 * 
 * <p>
 * A set of bit masks to flag if a castling movement is available:
 * 
 * <ul>
 * <li>{@link #WHITE_KING_CASTLING_MASK}
 * <li>{@link #WHITE_QUEEN_CASTLING_MASK}
 * <li>{@link #BLACK_KING_CASTLING_MASK}
 * <li>{@link #BLACK_QUEEN_CASTLING_MASK}
 * </ul>
 * 
 * Usage:
 * 
 * <pre>
 * int flags = WHITE_KING_CASTLING_MASK | BLACK_QUEEN_CASTLING_MASK;
 * 
 * isWhiteKingCaslting(flags); // true
 * isWhiteQueenCaslting(flags); // false
 * isBlackKingCaslting(flags); // false
 * isBlackQueenCaslting(flags); // true
 * </pre>
 * 
 * <p>
 * A set of bit masks for metadata information linked to movement. For exemplo,
 * if a movement is a capture, or an <i>en passant</i>, a pawn promotion, check,
 * discovery check, etc:
 * 
 * <ul>
 * <li>{@link #CAPTURE_MASK} - {@link #isCapture(int)}
 * <li>{@link #EN_PASSANT_MASK} - {@link #isEnPassant(int)}
 * <li>{@link #CASTLING_MASK} - {@link #isCastling(int)}
 * <li>{@link #PROMOTION_MASK} - {@link #isPromotion(int)}
 * <li>{@link #CHECK_MASK} - {@link #isCheck(int)}
 * <li>{@link #DISCOVERY_CHECK_MASK} - {@link #isDiscoveryCheck(int)}
 * <li>{@link #DOUBLE_CHECK_MASK} - {@link #isDoubleCheck(int)}
 * <li>{@link #CHECKMATE_MASK} - {@link #isCheckMate(int)}
 * <li>{@link #STALEMATE_MASK} - {@link #isStalemate(int)}
 * </ul>
 * 
 * Usage:
 * 
 * <pre>
 * Board board = new Board();
 * PieceMovements m = board.getMovementRandom();
 * MovementTarget t = m.getTargetRandom();
 * 
 * isCapture(t.getMetadata().getFlags())   // indicates if movement is a capture
 * isPromotion(t.getMetadata().getFlags()) // if movement is a pawn promotion
 * isStalemate(t.getMetadata().getFlags()) // if movement finishes the game with stalemate
 * </pre>
 * 
 * There are many others constants and helper methods, for sample, constants for
 * piece representative letters, colors code and its representatives, etc.
 * 
 * @author Welyab Paula
 * 
 * @see Board
 * @see PieceMovements
 * @see Movements
 * @see Movement
 * @see MovementTarget
 */
public final class MovementUtil {

	/** Initial row position for white pawns. */
	public static final byte WHITE_PAWNS_INITIAL_ROW = 6;

	/** Initial row position for black pawns. */
	public static final byte BLACK_PAWNS_INITIAL_ROW = 1;

	/** Row number where white pawn are promoted. */
	public static final byte WHITE_PAWN_PROMOTION_ROW = 7;

	/** Row number where black pawn are promoted. */
	public static final byte BLACK_PAWN_PROMOTION_ROW = 0;

	/** FEN string for initial board disposition. */
	public static final String FEN_INITIAL_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

	/** The type for identify the king piece (white or black). */
	public static final byte KING = 6;

	/** The type for identify the queen piece (white or black). */
	public static final byte QUEEN = 5;

	/** The type for identify the rook piece (white or black). */
	public static final byte ROOK = 4;

	/** The type for identify the bishop piece (white or black). */
	public static final byte BISHOP = 3;

	/** The type for identify the knight piece (white or black). */
	public static final byte KNIGHT = 2;

	/** The type for identify the pawn piece (white or black). */
	public static final byte PAWN = 1;

	/** Representative letter for the white color. */
	public static final char WHITE_LETTER = 'w';

	/** Representative letter for the black color. */
	public static final char BLACK_LETTER = 'b';

	/** The color code for white color. */
	public static final byte WHITE = 1;

	/** The color code for black color. */
	public static final byte BLACK = -1;

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
	public static final byte WHITE_KING = KING * WHITE;

	/** The letter for the white queen piece. */
	public static final byte WHITE_QUEEN = QUEEN * WHITE;

	/** The code for the white rook piece. */
	public static final byte WHITE_ROOK = ROOK * WHITE;

	/** The code for the white bishop piece. */
	public static final byte WHITE_BISHOP = BISHOP * WHITE;

	/** The code for the white knight piece. */
	public static final byte WHITE_KNIGHT = KNIGHT * WHITE;

	/** The code for the white pawn piece. */
	public static final byte WHITE_PAWN = PAWN * WHITE;

	/** The code for the black king piece. */
	public static final byte BLACK_KING = KING * BLACK;

	/** The code for the black queen piece. */
	public static final byte BLACK_QUEEN = QUEEN * BLACK;

	/** The code for the black rook piece. */
	public static final byte BLACK_ROOK = ROOK * BLACK;

	/** The code for the black bishop piece. */
	public static final byte BLACK_BISHOP = BISHOP * BLACK;

	/** The code for the black knight piece. */
	public static final byte BLACK_KNIGHT = KNIGHT * BLACK;

	/** The code for the black pawn piece. */
	public static final byte BLACK_PAWN = PAWN * BLACK;

	/** The value indicative for a non piece (a empty square, for sample). */
	public static final byte EMPTY = 0;

	// @formatter:off
	/** The bit mask for white king's side castling. */
	public static final byte WHITE_KING_CASTLING_MASK = 0b1000;
	/** The bit mask for white queen's side castling. */
	public static final byte WHITE_QUEEN_CASTLING_MASK = 0b0100;
	/** The bit mask for black king's side castling. */
	public static final byte BLACK_KING_CASTLING_MASK = 0b0010;
	/** The bit mask for black queen's side castling. */
	public static final byte BLACK_QUEEN_CASTLING_MASK = 0b0001;
	// @formatter:on

	// @formatter:off
	/** Indicates a movement with piece capture in the movement metadata. */
	public static final short CAPTURE_MASK = 0b0000_0000_0001;
	/**
	 * Indicates a movement with <i>en passant</i> capture in the movement metadata.
	 */
	public static final short EN_PASSANT_MASK = 0b0000_0000_0010;
	/** Indicates a castling movement. */
	public static final short CASTLING_MASK = 0b0000_0000_0100;
	/** Indicates a pawn promotion in the movement metadata. */
	public static final short PROMOTION_MASK = 0b0000_0000_1000;
	/** Indicates a movement that places an attack in the enemy king. */
	public static final short CHECK_MASK = 0b0000_0001_0000;
	/** Indicates a movement that reveals an attack in the enemy king. */
	public static final short DISCOVERY_CHECK_MASK = 0b0000_0010_0000;
	/**
	 * Indicates a movement that places a attack in the enemy king, while also
	 * reveals an attack of other piece in the king.
	 */
	public static final short DOUBLE_CHECK_MASK = 0b0000_0100_0000;
	/** Indicates that movement will finish the game with checkmate. */
	public static final short CHECKMATE_MASK = 0b0000_1000_0000;
	/** Indicates that movement will finish the game with a draw by stalemate. */
	public static final short STALEMATE_MASK = 0b0001_0000_0000;
	// @formatter:on

	@SuppressWarnings("javadoc")
	public static final Random RDN = new Random();

	/**
	 * Piece code cache. Each index is associated with a piece code. The indexes are
	 * piece letters minus the <code>'A'</code> letter.
	 */
	private static final byte[] PIECE_CODES = new byte[52];

	static {
		PIECE_CODES[WHITE_KING_LETTER - 'A'] = WHITE_KING;
		PIECE_CODES[WHITE_QUEEN_LETTER - 'A'] = WHITE_QUEEN;
		PIECE_CODES[WHITE_ROOK_LETTER - 'A'] = WHITE_ROOK;
		PIECE_CODES[WHITE_BISHOP_LETTER - 'A'] = WHITE_BISHOP;
		PIECE_CODES[WHITE_KNIGHT_LETTER - 'A'] = WHITE_KNIGHT;
		PIECE_CODES[WHITE_PAWN_LETTER - 'A'] = WHITE_PAWN;
		PIECE_CODES[BLACK_KING_LETTER - 'A'] = BLACK_KING;
		PIECE_CODES[BLACK_QUEEN_LETTER - 'A'] = BLACK_QUEEN;
		PIECE_CODES[BLACK_ROOK_LETTER - 'A'] = BLACK_ROOK;
		PIECE_CODES[BLACK_BISHOP_LETTER - 'A'] = BLACK_BISHOP;
		PIECE_CODES[BLACK_KNIGHT_LETTER - 'A'] = BLACK_KNIGHT;
		PIECE_CODES[BLACK_PAWN_LETTER - 'A'] = BLACK_PAWN;
	}

	/**
	 * Piece letter cache. Each index is associated with a piece letter. The indexes
	 * are the piece code plus <code>6</code>.
	 */
	private static final char[] PIECE_LETTERS = new char[13];

	static {
		PIECE_LETTERS[WHITE_KING + 6] = WHITE_KING_LETTER;
		PIECE_LETTERS[WHITE_QUEEN + 6] = WHITE_QUEEN_LETTER;
		PIECE_LETTERS[WHITE_ROOK + 6] = WHITE_ROOK_LETTER;
		PIECE_LETTERS[WHITE_BISHOP + 6] = WHITE_BISHOP_LETTER;
		PIECE_LETTERS[WHITE_KNIGHT + 6] = WHITE_KNIGHT_LETTER;
		PIECE_LETTERS[WHITE_PAWN + 6] = WHITE_PAWN_LETTER;
		PIECE_LETTERS[BLACK_KING + 6] = BLACK_KING_LETTER;
		PIECE_LETTERS[BLACK_QUEEN + 6] = BLACK_QUEEN_LETTER;
		PIECE_LETTERS[BLACK_ROOK + 6] = BLACK_ROOK_LETTER;
		PIECE_LETTERS[BLACK_BISHOP + 6] = BLACK_BISHOP_LETTER;
		PIECE_LETTERS[BLACK_KNIGHT + 6] = BLACK_KNIGHT_LETTER;
		PIECE_LETTERS[BLACK_PAWN + 6] = BLACK_PAWN_LETTER;
	}

	@SuppressWarnings("javadoc")
	private MovementUtil() {
	}

	/**
	 * Retrieves the piece code associated with a specific piece letter.
	 * 
	 * @param pieceLetter The piece letter (<code>'K'</code>, <code>'Q'</code>,
	 *                    <code>'R'</code>, <code>'B'</code>, <code>'N'</code>,
	 *                    <code>'P'</code>, <code>'k'</code>, <code>'q'</code>,
	 *                    <code>'r'</code>, <code>'b'</code>, <code>'n'</code>,
	 *                    <code>'p'</code>).
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
	 * @param pieceCode The piece code (<code>-6</code>, <code>-5</code>,
	 *                  <code>-4</code>, <code>-3</code>, <code>-2</code>,
	 *                  <code>-1</code>, <code>1</code>, <code>2</code>,
	 *                  <code>3</code>, <code>4</code>, <code>5</code>,
	 *                  <code>6</code>).
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
		case WHITE_LETTER -> WHITE;
		case BLACK_LETTER -> BLACK;
		default -> throw new IllegalArgumentException(String.format("Invalid color letter: %c", colorLetter));
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
		case WHITE -> WHITE_LETTER;
		case BLACK -> BLACK_LETTER;
		default -> throw new IllegalArgumentException(String.format("Invalid color code: %d", colorCode));
		};
	}

	/**
	 * Retrieves the opposite color for the given color; if color code is white,
	 * then returns the color code for the black piece, and vice versa.
	 * 
	 * @param colorCode The color code.
	 * 
	 * @return The opposite color code.
	 * 
	 * @throws IllegalArgumentException If the given color code is invalid.
	 */
	public static byte getOppositeColor(int colorCode) {
		return switch (colorCode) {
		case WHITE -> BLACK;
		case BLACK -> WHITE;
		default -> throw new IllegalArgumentException(String.format("Invalid color code: %d", colorCode));
		};
	}

	public static boolean isWhite(int pieceCode) {
		return pieceCode > 0;
	}

	public static boolean isBlack(int pieceCode) {
		return pieceCode < 0;
	}

	/**
	 * Evaluates if given castling flags is marked with flag for castling movement
	 * for white pieces on king side.
	 * 
	 * @param castlingFlags The castling flags.
	 * 
	 * @return A value <code>true</code> if the movement is available, or
	 *         <code>false</code>.
	 */
	public static boolean isWhiteKingCaslting(int castlingFlags) {
		return (castlingFlags & WHITE_KING_CASTLING_MASK) != 0;
	}

	/**
	 * Evaluates if given castling flags is marked with flag for castling movement
	 * for white pieces on queen side.
	 * 
	 * @param castlingFlags The castling flags.
	 * 
	 * @return A value <code>true</code> if the movement is available, or
	 *         <code>false</code>.
	 */
	public static boolean isWhiteQueenCaslting(int castlingFlags) {
		return (castlingFlags & WHITE_QUEEN_CASTLING_MASK) != 0;
	}

	/**
	 * Evaluates if given castling flags is marked with flag for castling movement
	 * for black pieces on king side.
	 * 
	 * @param castlingFlags The castling flags.
	 * 
	 * @return A value <code>true</code> if the movement is available, or
	 *         <code>false</code>.
	 */
	public static boolean isBlackKingCaslting(int castlingFlags) {
		return (castlingFlags & BLACK_KING_CASTLING_MASK) != 0;
	}

	/**
	 * Evaluates if given castling flags is marked with flag for castling movement
	 * for black pieces on queen side.
	 * 
	 * @param castlingFlags The castling flags.
	 * 
	 * @return A value <code>true</code> if the movement is available, or
	 *         <code>false</code>.
	 */
	public static boolean isBlackQueenCaslting(int castlingFlags) {
		return (castlingFlags & BLACK_QUEEN_CASTLING_MASK) != 0;
	}

	/**
	 * Encode the given flags into the bits of a byte number. The meaning of each
	 * bit is described as follow:
	 * 
	 * <pre>
	 *     +-----&gt; 4º least significant bit
	 *     |+----&gt; 3º least significant bit
	 *     ||+---&gt; 2º least significant bit
	 *     |||+--&gt; 1º least significant bit  
	 *     ||||
	 *     vvvv
	 * 0b000000
	 *     ^^^^
	 *     ||||
	 *     |||+--&gt; if castling is available for white pieces on the king side
	 *     ||+---&gt; if castling is available for white pieces on the queen side
	 *     |+----&gt; if castling is available for black pieces on the king side
	 *     +-----&gt; if castling is available for black pieces on the queen side
	 * </pre>
	 * 
	 * @param isWhiteKingSideCastling  If the castling is available for white pieces
	 *                                 on the king side.
	 * @param isWhiteQueenSideCastling If the castling is available for white pieces
	 *                                 on the queen side.
	 * @param isBlackKingSideCastling  If the castling is available for black pieces
	 *                                 on the king side.
	 * @param isBlackQueenSideCastling If the castling is available for black pieces
	 *                                 on the queen side.
	 * 
	 * @return The castling encoded into bits.
	 */
	public static byte muxCastlingFlags(boolean isWhiteKingSideCastling, boolean isWhiteQueenSideCastling,
			boolean isBlackKingSideCastling, boolean isBlackQueenSideCastling) {
		byte flags = 0;
		flags |= (isWhiteKingSideCastling ? WHITE_KING_CASTLING_MASK : 0);
		flags |= (isWhiteQueenSideCastling ? WHITE_QUEEN_CASTLING_MASK : 0);
		flags |= (isBlackKingSideCastling ? BLACK_KING_CASTLING_MASK : 0);
		flags |= (isBlackQueenSideCastling ? BLACK_QUEEN_CASTLING_MASK : 0);
		return flags;
	}

	/**
	 * Evaluates if the given movement flags indicates a movement of capture. The
	 * evaluation is made by reading correspondent bit.
	 * 
	 * @param flags The movement flags.
	 * 
	 * @return A value <code>true</code> if the movement flags has a capture
	 *         indicative, or <code>false</code> otherwise.
	 */
	public static boolean isCapture(int flags) {
		return (flags & CAPTURE_MASK) != 0;
	}

	/**
	 * Evaluates if the given movement flags indicates a movement of <i>en
	 * passant</i>. The evaluation is made by reading correspondent bit.
	 * 
	 * @param flags The movement flags.
	 * 
	 * @return A value <code>true</code> if the movement flags has a <i>en
	 *         passant</i> indicative, or <code>false</code> otherwise.
	 */
	public static boolean isEnPassant(int flags) {
		return (flags & EN_PASSANT_MASK) != 0;
	}

	/**
	 * Evaluates if the given movement flags indicates a movement of castling. The
	 * evaluation is made by reading correspondent bit.
	 * 
	 * @param flags The movement flags.
	 * 
	 * @return A value <code>true</code> if the movement flags has a castling
	 *         indicative, or <code>false</code> otherwise.
	 */
	public static boolean isCastling(int flags) {
		return (flags & CASTLING_MASK) != 0;
	}

	/**
	 * Evaluates if the given movement flags indicates a movement of pawn promotion.
	 * The evaluation is made by reading correspondent bit.
	 * 
	 * @param flags The movement flags.
	 * 
	 * @return A value <code>true</code> if the movement flags has a pawn promotion
	 *         indication, or <code>false</code> otherwise.
	 */
	public static boolean isPromotion(int flags) {
		return (flags & PROMOTION_MASK) != 0;
	}

	/**
	 * Evaluates if the given movement flags indicates a movement of check. The
	 * evaluation is made by reading correspondent bit.
	 * 
	 * @param flags The movement flags.
	 * 
	 * @return A value <code>true</code> if the movement flags has a check
	 *         indication, or <code>false</code> otherwise.
	 */
	public static boolean isCheck(int flags) {
		return (flags & CHECK_MASK) != 0;
	}

	/**
	 * Evaluates if the given movement flags indicates a movement of check. The
	 * evaluation is made by reading correspondent bit.
	 * 
	 * @param flags The movement flags.
	 * 
	 * @return A value <code>true</code> if the movement flags has a check
	 *         indication, or <code>false</code> otherwise.
	 */
	public static boolean isDiscoveryCheck(int flags) {
		return (flags & DISCOVERY_CHECK_MASK) != 0;
	}

	/**
	 * Evaluates if the given movement flags indicates a movement of discovery
	 * check. The evaluation is made by reading correspondent bit.
	 * 
	 * @param flags The movement flags.
	 * 
	 * @return A value <code>true</code> if the movement flags has a discovery check
	 *         indication, or <code>false</code> otherwise.
	 */
	public static boolean isDoubleCheck(int flags) {
		return (flags & DOUBLE_CHECK_MASK) != 0;
	}

	/**
	 * Evaluates if the given movement flags indicates a movement of checkmate. The
	 * evaluation is made by reading correspondent bit.
	 * 
	 * @param flags The movement flags.
	 * 
	 * @return A value <code>true</code> if the movement flags has a checkmate
	 *         indication, or <code>false</code> otherwise.
	 */
	public static boolean isCheckMate(int flags) {
		return (flags & CHECKMATE_MASK) != 0;
	}

	/**
	 * Evaluates if the given movement flags indicates a movement of stalemate. The
	 * evaluation is made by reading correspondent bit.
	 * 
	 * @param flags The movement flags.
	 * 
	 * @return A value <code>true</code> if the movement flags has a stalemate
	 *         indication, or <code>false</code> otherwise.
	 */
	public static boolean isStalemate(int flags) {
		return (flags & STALEMATE_MASK) != 0;
	}

	/**
	 * Evaluates if the given piece code is the identifier for a king piece (white
	 * or black).
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for
	 *         the king piece (white or black), or <code>false</code> if not.
	 */
	public static boolean isKing(int pieceCode) {
		return pieceCode == WHITE_KING || pieceCode == BLACK_KING;
	}

	/**
	 * Evaluates if the given piece code is the identifier for a queen piece (white
	 * or black).
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for
	 *         the queen piece (white or black), or <code>false</code> if not.
	 */
	public static boolean isQueen(int pieceCode) {
		return pieceCode == WHITE_QUEEN || pieceCode == BLACK_QUEEN;
	}

	/**
	 * Evaluates if the given piece code is the identifier for a rook piece (white
	 * or black).
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for
	 *         the rook piece (white or black), or <code>false</code> if not.
	 */
	public static boolean isRook(int pieceCode) {
		return pieceCode == WHITE_ROOK || pieceCode == BLACK_ROOK;
	}

	/**
	 * Evaluates if the given piece code is the identifier for a bishop piece (white
	 * or black).
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for
	 *         the bishop piece (white or black), or <code>false</code> if not.
	 */
	public static boolean isBishop(int pieceCode) {
		return pieceCode == WHITE_BISHOP || pieceCode == BLACK_BISHOP;
	}

	/**
	 * Evaluates if the given piece code is the identifier for a knight piece (white
	 * or black).
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for
	 *         the knight piece (white or black), or <code>false</code> if not.
	 */
	public static boolean isKnight(int pieceCode) {
		return pieceCode == WHITE_KNIGHT || pieceCode == BLACK_KNIGHT;
	}

	/**
	 * Evaluates if the given piece code is the identifier for a pawn piece (white
	 * or black).
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for
	 *         the pawn piece (white or black), or <code>false</code> if not.
	 */
	public static boolean isPawn(int pieceCode) {
		return pieceCode == WHITE_PAWN || pieceCode == BLACK_PAWN;
	}

	/**
	 * Evaluates if the given piece code is the identifier for the white king piece.
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for
	 *         the white king piece, or <code>false</code> if not.
	 */
	public static boolean isWhiteKing(int pieceCode) {
		return pieceCode == WHITE_KING;
	}

	/**
	 * Evaluates if the given piece code is the identifier for the white queen
	 * piece.
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for
	 *         the white queen piece, or <code>false</code> if not.
	 */
	public static boolean isWhiteQueen(int pieceCode) {
		return pieceCode == WHITE_QUEEN;
	}

	/**
	 * Evaluates if the given piece code is the identifier for the white rook piece.
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for
	 *         the white rook piece, or <code>false</code> if not.
	 */
	public static boolean isWhiteRook(int pieceCode) {
		return pieceCode == WHITE_ROOK;
	}

	/**
	 * Evaluates if the given piece code is the identifier for the white bishop
	 * piece.
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for
	 *         the white bishop piece, or <code>false</code> if not.
	 */
	public static boolean isWhiteBishop(int pieceCode) {
		return pieceCode == WHITE_BISHOP;
	}

	/**
	 * Evaluates if the given piece code is the identifier for the white knight
	 * piece.
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for
	 *         the white knight piece, or <code>false</code> if not.
	 */
	public static boolean isWhiteKnight(int pieceCode) {
		return pieceCode == WHITE_KNIGHT;
	}

	/**
	 * Evaluates if the given piece code is the identifier for the white pawn piece
	 * piece.
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for
	 *         the white pawn piece, or <code>false</code> if not.
	 */
	public static boolean isWhitePawn(int pieceCode) {
		return pieceCode == WHITE_PAWN;
	}

	/**
	 * Evaluates if the given piece code is the identifier for the black king piece.
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for
	 *         the black king piece, or <code>false</code> if not.
	 */
	public static boolean isBlackKing(int pieceCode) {
		return pieceCode == BLACK_KING;
	}

	/**
	 * Evaluates if the given piece code is the identifier for the black queen
	 * piece.
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for
	 *         the black queen piece, or <code>false</code> if not.
	 */
	public static boolean isBlackQueen(int pieceCode) {
		return pieceCode == BLACK_QUEEN;
	}

	/**
	 * Evaluates if the given piece code is the identifier for the black black rook
	 * piece.
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for
	 *         the black rook piece, or <code>false</code> if not.
	 */
	public static boolean isBlackRook(int pieceCode) {
		return pieceCode == BLACK_ROOK;
	}

	/**
	 * Evaluates if the given piece code is the identifier for the black black
	 * bishop.
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for
	 *         the black bishop, or <code>false</code> if not.
	 */
	public static boolean isBlackBishop(int pieceCode) {
		return pieceCode == BLACK_BISHOP;
	}

	/**
	 * Evaluates if the given piece code is the identifier for the black knight
	 * piece.
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for
	 *         the black knight piece, or <code>false</code> if not.
	 */
	public static boolean isBlackKnight(int pieceCode) {
		return pieceCode == BLACK_KNIGHT;
	}

	/**
	 * Evaluates if the given piece code is the identifier for the black pawn piece.
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is the representative for
	 *         the black pawn piece, or <code>false</code> if not.
	 */
	public static boolean isBlackPawn(int pieceCode) {
		return pieceCode == BLACK_PAWN;
	}

	/**
	 * Evaluates if the given piece code is a representative code for a white piece.
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is a code for white
	 *         piece, of <code>false</code> if not.
	 */
	public static boolean isWhitePiece(int pieceCode) {
		return pieceCode > 0;
	}

	/**
	 * Evaluates if the given piece code is a representative code for a black piece.
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return A value <code>true</code> if the piece code is a code for black
	 *         piece, or <code>false</code> if not.
	 */
	public static boolean isBlackPiece(int pieceCode) {
		return pieceCode < 0;
	}

	/**
	 * Retrieves the color code for the given piece code.
	 * 
	 * @param pieceCode The piece code.
	 * 
	 * @return The color code ({@linkplain #WHITE white} or {@linkplain #BLACK
	 *         code}).
	 */
	public static byte getPieceColor(int pieceCode) {
		if (pieceCode < 0) {
			return BLACK;
		}
		if (pieceCode > 0) {
			return WHITE;
		}
		throw new IllegalArgumentException("Invalid piece code: 0");
	}

	/**
	 * Evaluates if the given color code is the representative code for white color.
	 * 
	 * @param pieceColor The color code.
	 * 
	 * @return A value <code>true</code> if the code is the identifier for the white
	 *         color, or <code>false</code> if not.
	 */
	public static boolean isWhiteColor(int pieceColor) {
		return pieceColor == WHITE;
	}

	/**
	 * Evaluates if the given color code is the representative code for black color.
	 * 
	 * @param pieceColor The color code.
	 * 
	 * @return A value <code>true</code> if the code is the identifier for the black
	 *         color, or <code>false</code> if not.
	 */
	public static boolean isBlackColor(int pieceColor) {
		return pieceColor == BLACK;
	}

	/**
	 * Retrieves the piece code for given piece type and piece color. The piece type
	 * is a code for identifying one of six piece kind (king, queen, rook, bishop,
	 * knight and pawn) without color distinction.
	 * 
	 * @param pieceType  The piece type.
	 * 
	 *                   One of follow values:
	 * 
	 *                   <ul>
	 *                   <li>{@link #KING}
	 *                   <li>{@link #QUEEN}
	 *                   <li>{@link #ROOK}
	 *                   <li>{@link #BISHOP}
	 *                   <li>{@link #KNIGHT}
	 *                   <li>{@link #PAWN}
	 *                   </ul>
	 * 
	 * @param pieceColor The color of desirated piece.
	 * 
	 *                   One of follow values:
	 * 
	 *                   <ul>
	 *                   <li>{@link #WHITE}
	 *                   <li>{@link #BLACK}
	 *                   </ul>
	 * 
	 * @return
	 * 
	 *         One of follow values:
	 * 
	 *         <ul>
	 *         <li>{@link #WHITE_KING}
	 *         <li>{@link #WHITE_QUEEN}
	 *         <li>{@link #WHITE_ROOK}
	 *         <li>{@link #WHITE_BISHOP}
	 *         <li>{@link #WHITE_KNIGHT}
	 *         <li>{@link #WHITE_PAWN}
	 *         <li>{@link #BLACK_KING}
	 *         <li>{@link #BLACK_QUEEN}
	 *         <li>{@link #BLACK_ROOK}
	 *         <li>{@link #BLACK_BISHOP}
	 *         <li>{@link #BLACK_KNIGHT}
	 *         <li>{@link #BLACK_PAWN}
	 *         </ul>
	 */
	public static byte getPiece(byte pieceType, byte pieceColor) {
		return (byte) (pieceType * pieceColor);
	}

	public static byte getCastlingRookTargetColumn(byte kingTargetColumn) {
		return kingTargetColumn == 6 ? 5 : (byte) 3;
	}

	public static byte getCastlingRookOriginColumn(byte kingTargetColumn) {
		return kingTargetColumn == 6 ? 7 : (byte) 0;
	}

	public static byte getPieceType(byte pieceCode) {
		return (byte) Math.abs(pieceCode);
	}

	public static String movementFlagsToString(int flags) {
		List<String> flagNames = new ArrayList<>();
		if (isCapture(flags)) {
			flagNames.add("capture");
		}
		if (isEnPassant(flags)) {
			flagNames.add("en passant");
		}
		if (isCastling(flags)) {
			flagNames.add("castling");
		}
		if (isPromotion(flags)) {
			flagNames.add("promotion");
		}
		if (isCheck(flags)) {
			flagNames.add("check");
		}
		if (isDiscoveryCheck(flags)) {
			flagNames.add("disc. check");
		}
		if (isDoubleCheck(flags)) {
			flagNames.add("double check");
		}
		if (isCheckMate(flags)) {
			flagNames.add("checkmate");
		}
		if (isStalemate(flags)) {
			flagNames.add("stalemate");
		}
		return flagNames.stream().reduce((s1, s2) -> s1 + ", " + s2).orElse("");
	}
}
