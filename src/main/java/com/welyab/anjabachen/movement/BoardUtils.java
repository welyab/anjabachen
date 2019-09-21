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

public class BoardUtils {
	
	/** FEN notation for the initial position. */
	public static final String FEN_INITIAL_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
	
	/**
	 * Bit mask for extract the <i>capture</i> bit flag from movement flags.
	 * 
	 * <p>
	 * The capture bit flag is the 1º least significant bit.
	 * 
	 * @see #isCapture(int)
	 */
	public static final int CAPTURE = 0b0000_0000_0000_0001;
	
	/**
	 * Bit mask for extract the <i>en passant</i> bit flag from movement flags.
	 * 
	 * <p>
	 * The capture bit flag is the 2º least significant bit.
	 * 
	 * @see #isEnPassant(int)
	 */
	public static final int EN_PASSANT = 0b0000_0000_0000_0010;
	
	/**
	 * Bit mask for extract the <i>castling</i> bit flag from movement flags.
	 * 
	 * <p>
	 * The capture bit flag is the 3º least significant bit.
	 * 
	 * @see #isCastling(int)
	 */
	public static final int CASTLING = 0b0000_0000_0000_0100;
	
	/**
	 * Bit mask for extract the <i>promotion</i> bit flag from movement flags.
	 * 
	 * <p>
	 * The capture bit flag is the 4º least significant bit.
	 * 
	 * @see #isPromotion(int)
	 */
	public static final int PROMOTION = 0b0000_0000_0000_1000;
	
	/**
	 * Bit mask for extract the <i>check</i> bit flag from movement flags.
	 * 
	 * <p>
	 * The capture bit flag is the 5º least significant bit.
	 * 
	 * @see #isCheck(int)
	 */
	public static final int CHECK = 0b0000_0000_0001_0000;
	
	/**
	 * Bit mask for extract the <i>DISCOVERY CHECK</i> bit flag from movement flags.
	 * 
	 * <p>
	 * The capture bit flag is the 6º least significant bit.
	 * 
	 * @see #isDiscoveryCheck(int)
	 */
	public static final int DISCOVERY_CHECK = 0b0000_0000_0010_0000;
	
	/**
	 * Bit mask for extract the <i>DOUBLE CHECK</i> bit flag from movement flags.
	 * 
	 * <p>
	 * The capture bit flag is the 7º least significant bit.
	 * 
	 * @see #isDoubleCheck(int)
	 */
	public static final int DOUBLE_CHECK = 0b0000_0000_0100_0000;
	
	/**
	 * Bit mask for extract the <i>CHECKMATE</i> bit flag from movement flags.
	 * 
	 * <p>
	 * The capture bit flag is the 8º least significant bit.
	 * 
	 * @see #isCheckmate(int)
	 */
	public static final int CHECKMATE = 0b0000_0000_1000_0000;
	
	/**
	 * Bit mask for extract the <i>STALEMATE</i> bit flag from movement flags.
	 * 
	 * <p>
	 * The capture bit flag is the 9º least significant bit.
	 * 
	 * @see #isStalemate(int)
	 */
	public static final int STALEMATE = 0b0000_0001_0000_0000;
	
	/**
	 * Flags castling movement is available for the black piece in the king side.
	 */
	public static final int BLACK_KING_SIDE_CASTLING = 0b0001;
	
	/**
	 * Flags castling movement is available for the black piece in the queen side.
	 */
	public static final int BLACK_QUEEN_SIDE_CASTLING = 0b0010;
	
	/**
	 * Flags castling movement is available for the white piece in the king side.
	 */
	public static final int WHITE_KING_SIDE_CASTLING = 0b0100;
	
	/**
	 * Flags castling movement is available for the white piece in the queen side.
	 */
	public static final int WHITE_QUEEN_SIDE_CASTLING = 0b1000;
	
	/** The chess board size (a chess board is a 8x8 matrix like table). */
	public static final int BOARD_SIZE = 8;
	
	/**
	 * The total amount of squares in the chess board.
	 */
	public static final int SQUARES_COUNT = BoardUtils.BOARD_SIZE * BoardUtils.BOARD_SIZE;
	
	/** The board's minimum row number. */
	public static final int MIN_ROW_NUMBER = 0;
	
	/** The board's maximum row number. */
	public static final int MAX_ROW_NUMBER = 7;
	
	/** The board's minimum column number. */
	public static final int MIN_COLUMN_NUMBER = 0;
	
	/** The board's maximum column number. */
	public static final int MAX_COLUMN_NUMBER = 7;
	
	/**
	 * Evaluate if the bit corresponding to capture flag is market, or not.
	 * 
	 * @param movementFlags The movement flags.
	 * 
	 * @return A value <code>true</code> if the corresponding bit is marked, or <code>false</code>
	 *         otherwise.
	 * 
	 * @see #CAPTURE
	 */
	public static boolean isCapture(int movementFlags) {
		return (movementFlags & CAPTURE) != 0;
	}
	
	/**
	 * Evaluate if the bit corresponding to <i>en passant</i> flag is marked, or not.
	 * 
	 * @param movementFlags The movement flags.
	 * 
	 * @return A value <code>true</code> if the corresponding bit is marked, or <code>false</code>
	 *         otherwise.
	 * 
	 * @see #EN_PASSANT
	 */
	public static boolean isEnPassant(int movementFlags) {
		return (movementFlags & EN_PASSANT) != 0;
	}
	
	/**
	 * Evaluate if the bit corresponding to castling flag is marked, or not.
	 * 
	 * @param movementFlags The movement flags.
	 * 
	 * @return A value <code>true</code> if the corresponding bit is marked, or <code>false</code>
	 *         otherwise.
	 * 
	 * @see #EN_PASSANT
	 */
	public static boolean isCastling(int movementFlags) {
		return (movementFlags & CASTLING) != 0;
	}
	
	/**
	 * Evaluate if the bit corresponding to promotion flag is marked, or not.
	 * 
	 * @param movementFlags The movement flags.
	 * 
	 * @return A value <code>true</code> if the corresponding bit is marked, or <code>false</code>
	 *         otherwise.
	 * 
	 * @see #EN_PASSANT
	 */
	public static boolean isPromotion(int movementFlags) {
		return (movementFlags & PROMOTION) != 0;
	}
	
	/**
	 * Evaluate if the bit corresponding to check flag is marked, or not.
	 * 
	 * @param movementFlags The movement flags.
	 * 
	 * @return A value <code>true</code> if the corresponding bit is marked, or <code>false</code>
	 *         otherwise.
	 * 
	 * @see #EN_PASSANT
	 */
	public static boolean isCheck(int movementFlags) {
		return (CHECK & movementFlags) != 0;
	}
	
	/**
	 * Evaluate if the bit corresponding to discovery check flag is marked, or not.
	 * 
	 * @param movementFlags The movement flags.
	 * 
	 * @return A value <code>true</code> if the corresponding bit is marked, or <code>false</code>
	 *         otherwise.
	 * 
	 * @see #EN_PASSANT
	 */
	public static boolean isDiscoveryCheck(int movementFlags) {
		return (movementFlags & DISCOVERY_CHECK) != 0;
	}
	
	/**
	 * Evaluate if the bit corresponding to double check flag is marked, or not.
	 * 
	 * @param movementFlags The movement flags.
	 * 
	 * @return A value <code>true</code> if the corresponding bit is marked, or <code>false</code>
	 *         otherwise.
	 * 
	 * @see #EN_PASSANT
	 */
	public static boolean isDoubleCheck(int movementFlags) {
		return (movementFlags & DOUBLE_CHECK) != 0;
	}
	
	/**
	 * Evaluate if the bit corresponding to checkmate flag is marked, or not.
	 * 
	 * @param movementFlags The movement flags.
	 * 
	 * @return A value <code>true</code> if the corresponding bit is marked, or <code>false</code>
	 *         otherwise.
	 * 
	 * @see #EN_PASSANT
	 */
	public static boolean isCheckmate(int movementFlags) {
		return (movementFlags & CHECKMATE) != 0;
	}
	
	/**
	 * Evaluate if the bit corresponding to stalemate flag is marked, or not.
	 * 
	 * @param movementFlags The movement flags.
	 * 
	 * @return A value <code>true</code> if the corresponding bit is marked, or <code>false</code>
	 *         otherwise.
	 * 
	 * @see #EN_PASSANT
	 */
	public static boolean isStalemate(int movementFlags) {
		return (movementFlags & STALEMATE) != 0;
	}
	
	/**
	 * Evaluates if the movement flags is related with some kind of check (simple check, discovery
	 * check, double check).
	 *
	 * <p>
	 * Note that this method does not consider <i>checkmate</i> as general check.
	 *
	 * @param movementFlags The flags associated with movement.
	 *
	 * @return A value <code>true</code> if the movements has some kind of geral check, or
	 *         <code>false</code> otherwise.
	 *
	 * @see #isCheckmate(int)
	 * @see #isCheck(int)
	 * @see #isDoubleCheck(int)
	 * @see #isDiscoveryCheck(int)
	 */
	public static boolean isGeneralCheck(int movementFlags) {
		return (movementFlags & (CHECK | DOUBLE_CHECK | DISCOVERY_CHECK)) != 0;
	}
	
	/**
	 * Evaluates if the given <code>castlingFlags</code> parameter is marked with the flag
	 * indicating that castling movement is available for black pieces in the king side.
	 *
	 * @param castlingFlags The castling movement flags.
	 *
	 * @return A value <code>true</code> if the movement is available, or <code>false</code>
	 *         otherwise.
	 */
	public static boolean isBlackKingSideCastling(int castlingFlags) {
		return (castlingFlags & BLACK_KING_SIDE_CASTLING) != 0;
	}
	
	/**
	 * Evaluates if the given <code>castlingFlags</code> parameter is marked with the flag
	 * indicating that castling movement is available for black pieces in the queen side.
	 *
	 * @param castlingFlags The castling movement flags.
	 *
	 * @return A value <code>true</code> if the movement is available, or <code>false</code>
	 *         otherwise.
	 */
	public static boolean isBlackQueenSideCastling(int castlingFlags) {
		return (castlingFlags & BLACK_QUEEN_SIDE_CASTLING) != 0;
	}
	
	/**
	 * Evaluates if the given <code>castlingFlags</code> parameter is marked with the flag
	 * indicating that castling movement is available for white pieces in the king side.
	 *
	 * @param castlingFlags The castling movement flags.
	 *
	 * @return A value <code>true</code> if the movement is available, or <code>false</code>
	 *         otherwise.
	 */
	public static boolean isWhiteKingSideCastling(int castlingFlags) {
		return (castlingFlags & WHITE_KING_SIDE_CASTLING) != 0;
	}
	
	/**
	 * Evaluates if the given <code>castlingFlags</code> parameter is marked with the flag
	 * indicating that castling movement is available for white pieces in the queen side.
	 *
	 * @param castlingFlags The castling movement flags.
	 *
	 * @return A value <code>true</code> if the movement is available, or <code>false</code>
	 *         otherwise.
	 */
	public static boolean isWhiteQueenSideCastling(int castlingFlags) {
		return (castlingFlags & WHITE_QUEEN_SIDE_CASTLING) != 0;
	}
	
	/**
	 * Castling flags are encoded into bits of a integer number. The four least significant bits are
	 * used to store castling flags availability during a game playing.
	 *
	 * <pre>
	 * int flags = 0b000...0000000000000000
	 *                                 ^^^^
	 *                                 ||||
	 * black castling for king side  --+|||
	 * black castling for queen side ---+||
	 * white castling for king side  ----+|
	 * white castling for queen side -----+
	 * </pre>
	 *
	 * @param blackKingSideCastlingAvailable A flag to indicate if the castling movement is
	 *        available for the black pieces in the king side. If <code>true</code> is informed, the
	 *        bit associated with this movement is set to <code>1</code>, or <code>0</code>
	 *        otherwise.
	 * @param blackQueenSideCastlingAvailable A flag to indicate if the castling movement is
	 *        available for the black pieces in the queen side. If <code>true</code> is informed,
	 *        the bit associated with this movement is set to <code>1</code>, or <code>0</code>
	 *        otherwise.
	 * @param whiteKingSideCastlingAvailable A flag to indicate if the castling movement is
	 *        available for the white pieces in the king side. If <code>true</code> is informed,
	 *        the bit associated with this movement is set to <code>1</code>, or <code>0</code>
	 *        otherwise.
	 * @param whiteQueenSideCastlingAvailable A flag to indicate if the castling movement is
	 *        available for the white pieces in the queen side. If <code>true</code> is informed,
	 *        the bit associated with this movement is set to <code>1</code>, or <code>0</code>
	 *        otherwise.
	 *
	 * @return The castling flags encoded into the bits of the integer number.
	 */
	public static int toCastlingFlags(
			boolean blackKingSideCastlingAvailable,
			boolean blackQueenSideCastlingAvailable,
			boolean whiteKingSideCastlingAvailable,
			boolean whiteQueenSideCastlingAvailable
	) {
		return (blackKingSideCastlingAvailable ? BLACK_KING_SIDE_CASTLING : 0)
				| (blackQueenSideCastlingAvailable ? BLACK_QUEEN_SIDE_CASTLING : 0)
				| (whiteKingSideCastlingAvailable ? WHITE_KING_SIDE_CASTLING : 0)
				| (whiteQueenSideCastlingAvailable ? WHITE_QUEEN_SIDE_CASTLING : 0);
	}
}
