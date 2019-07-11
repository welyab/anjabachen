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

public class GameConstants {
	
	/**
	 * This mark is used by movement generation t indicate that a specific movement is also
	 * <i>capture</i>.
	 */
	public static final int CAPTURE = 0b0000_0000_0000_0001;
	
	/**
	 * This mark is used by movement generation to indicate that a specific movement is a <i>en
	 * passant</i>.
	 */
	public static final int EN_PASSANT = 0b0000_0000_0000_0010;
	
	/**
	 * This mark is used by movement generation to indicate that a specific movement is a castling.
	 */
	public static final int CASTLING = 0b0000_0000_0000_0100;
	
	/**
	 * This mark is used by movement generation to indicate that a specific movement is a pawn
	 * promotion.
	 */
	public static final int PROMOTION = 0b0000_0000_0000_1000;
	
	/**
	 * This mark is used by movement generation to indicate that a specific movement puts the
	 * opposite king in check.
	 *
	 * <p>
	 * When a movement is marked as <i>check</i>, it will note be marked with another kind of
	 * checks, like <i>discovery check</i>, <i>double check</i>, neither <i>checkmate</i> as well.
	 */
	public static final int CHECK = 0b0000_0000_0001_0000;
	
	/**
	 * This mark is used by movement generation to indicate that a specific movement puts the
	 * opposite king in check, not by movement of the piece itself, but by uncovering a same side
	 * piece that attacks the opposite king.
	 *
	 * <p>
	 * When a movement is marked as <code>discovery check</code>, it will not be marked with another
	 * kind of checks, like <i>double check</i> or <i>check</i>, neither <i>checkmate</i>.
	 * as well.
	 */
	public static final int DISCOVERY_CHECK = 0b0000_0000_0010_0000;
	
	/**
	 * This mark is used by movement generation to indicate that a specific movement puts the
	 * opposite king in check, and also have uncovered another piece that also attacks the opposite
	 * king.
	 *
	 * <p>
	 * When a movement is marked as <code>double check</code>, it will not be marked with another
	 * kind of checks, like <i>check</i> or <i>discovery check</i>, neither <i>checkmate</i>.
	 * as well.
	 */
	public static final int DOUBLE_CHECK = 0b0000_0000_0100_0000;
	
	/**
	 * This mark is used by movement generation to indicate that a specific movement puts the
	 * opposite king in checkmate, finishing the game.
	 *
	 * <p>
	 * When a movement is market <i>checkmate</i>, it will not be marked with another kind of
	 * checks, like <i>discovery check</i>.
	 */
	public static final int CHECKMATE = 0b0000_0000_1000_0000;
	
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
	
	public static final int BOARD_SIZE = 8;
	
	/**
	 * The total amount of squares in the chess board.
	 */
	public static final int SQUARES_COUNT = GameConstants.BOARD_SIZE * GameConstants.BOARD_SIZE;
	
	public static final int MIN_ROW_NUMBER = 0;
	
	public static final int MAX_ROW_NUMBER = BOARD_SIZE - 1;
	
	public static final int MIN_COLUMN_NUMBER = 0;
	
	public static final int MAX_COLUMN_NUMBER = BOARD_SIZE - 1;
	
	public static boolean isCapture(int movementFlags) {
		return (movementFlags & CAPTURE) != 0;
	}
	
	public static boolean isEnPassant(int movementFlags) {
		return (movementFlags & EN_PASSANT) != 0;
	}
	
	public static boolean isCastling(int movementFlags) {
		return (movementFlags & CASTLING) != 0;
	}
	
	public static boolean isPromotion(int movementFlags) {
		return (movementFlags & PROMOTION) != 0;
	}
	
	public static boolean isCheck(int movementFlags) {
		return (CHECK & movementFlags) != 0;
	}
	
	public static boolean isDiscoveryCheck(int movementFlags) {
		return (movementFlags & DISCOVERY_CHECK) != 0;
	}
	
	public static boolean isDoubleCheck(int movementFlags) {
		return (movementFlags & DOUBLE_CHECK) != 0;
	}
	
	public static boolean isCheckmate(int movementFlags) {
		return (movementFlags & CHECKMATE) != 0;
	}
	
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
}
