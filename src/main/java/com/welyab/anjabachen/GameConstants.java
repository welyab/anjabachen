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

	public static final int MASK_CAPTURE = 0b0000_0000_0000_0001;

	/**
	 * This mask is used by movement generation to indicate that a specific movement is a <i>en
	 * passant</i>.
	 */
	public static final int MASK_EN_PASSANT = 0b0000_0000_0000_0010;

	/**
	 * This mask is used by movement generation to indicate that a specific movement is a castling.
	 */
	public static final int MASK_CASTLING = 0b0000_0000_0000_0100;

	/**
	 * This mask is used by movement generation to indicate that a specific movement is a pawn
	 * promotion.
	 */
	public static final int MASK_PROMOTION = 0b0000_0000_0000_1000;

	/**
	 * This mask is used by movement generation to indicate that a specific movement puts the
	 * opposite king in check.
	 *
	 * <p>
	 * When a movement is marked as <i>check</i>, it will note be marked with another kind of
	 * checks, like <i>discovery check</i>, <i>double check</i>, neither <i>checkmate</i> as well.
	 */
	public static final int MASK_CHECK = 0b0000_0000_0001_0000;

	/**
	 * This mask is used by movement generation to indicate that a specific movement puts the
	 * opposite king in check, not by movement of the piece itself, but by uncovering a same side
	 * piece that attacks the opposite king.
	 *
	 * <p>
	 * When a movement is marked as <code>discovery check</code>, it will not be marked with another
	 * kind of checks, like <i>double check</i> or <i>check</i>, neither <i>checkmate</i>.
	 * as well.
	 */
	public static final int MASK_DISCOVERY_CHECK = 0b0000_0000_0010_0000;

	/**
	 * This mask is used by movement generation to indicate that a specific movement puts the
	 * opposite king in check, and also have uncovered another piece that also attacks the opposite
	 * king.
	 *
	 * <p>
	 * When a movement is marked as <code>double check</code>, it will not be marked with another
	 * kind of checks, like <i>check</i> or <i>discovery check</i>, neither <i>checkmate</i>.
	 * as well.
	 */
	public static final int MASK_DOUBLE_CHECK = 0b0000_0000_0100_0000;

	/**
	 * This mask is used by movement generation to indicate that a specific movement puts the
	 * opposite king in checkmate, finishing the game.
	 *
	 * <p>
	 * When a movement is market <i>checkmate</i>, it will not be marked with another kind of
	 * checks, like <i>discovery check</i>.
	 */
	public static final int MASK_CHECKMATE = 0b0000_0000_1000_0000;

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
		return (movementFlags & MASK_CAPTURE) != 0;
	}

	public static boolean isEnPassant(int movementFlags) {
		return (movementFlags & MASK_EN_PASSANT) != 0;
	}

	public static boolean isCastling(int movementFlags) {
		return (movementFlags & MASK_CASTLING) != 0;
	}

	public static boolean isPromotion(int movementFlags) {
		return (movementFlags & MASK_PROMOTION) != 0;
	}

	public static boolean isCheck(int movementFlags) {
		return (MASK_CHECK & movementFlags) != 0;
	}

	public static boolean isDiscoveryCheck(int movementFlags) {
		return (movementFlags & MASK_DISCOVERY_CHECK) != 0;
	}

	public static boolean isDoubleCheck(int movementFlags) {
		return (movementFlags & MASK_DOUBLE_CHECK) != 0;
	}

	public static boolean isCheckmate(int movementFlags) {
		return (movementFlags & MASK_CHECKMATE) != 0;
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
		return (movementFlags & (MASK_CHECK | MASK_DOUBLE_CHECK | MASK_DISCOVERY_CHECK)) != 0;
	}
}
