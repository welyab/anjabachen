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

import java.util.List;

/**
 * This class is a container for the set of available movements for a piece in an specific board
 * state.
 *
 * @author Welyab Paula
 */
public class PieceMovement {

	/**
	 * The piece being moved.
	 */
	private final Piece piece;

	/**
	 * The origin square row number.
	 */
	private final int row;

	/**
	 * The origin square column number.
	 */
	private final int column;

	/**
	 * The list of available targets for the underlying piece.
	 */
	private final List<MovementTarget> targets;

	/**
	 * Indicates if this movements refers to a pawn promotion.
	 */
	private final boolean pawnPromotion;

	/**
	 * Creates a new <code>Movements</code> set for piece.
	 *
	 * @param piece The being moved piece.
	 * @param row The origin square row number.
	 * @param column The origin square column number.
	 * @param targets The list of available targets for the underlying piece.
	 */
	public PieceMovement(Piece piece, int row, int column, List<MovementTarget> targets) {
		this.piece = piece;
		this.row = row;
		this.column = column;
		this.targets = targets;
		this.pawnPromotion = false;
	}

	/**
	 * Creates a new <code>Movements</code> set for piece.
	 *
	 * @param piece The being moved piece.
	 * @param row The origin square row number.
	 * @param column The origin square column number.
	 * @param pawnPromotion Indicates if this movement refers to a pawn promotion.
	 * @param targets The list of available targets for the underlying piece.
	 */
	public PieceMovement(Piece piece, int row, int column, boolean pawnPromotion, List<MovementTarget> targets) {
		this.piece = piece;
		this.row = row;
		this.column = column;
		this.targets = targets;
		this.pawnPromotion = pawnPromotion;
	}

	/**
	 * Retrieves the being moved piece.
	 *
	 * @return The piece.
	 */
	public Piece getPiece() {
		return piece;
	}

	/**
	 * Retrieves the origin square column number.
	 *
	 * @return The origin square column number.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Retrieves the origin square column number.
	 *
	 * @return The origin square column number.
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Retrieves the list of movement targets.
	 *
	 * @return The list of targets for the underlying piece.
	 */
	public List<MovementTarget> getTargets() {
		return targets;
	}

	/**
	 * The amount of available movements in this set.
	 *
	 * @return The amount of available movements in this set.
	 */
	public int size() {
		return targets.size();
	}

	/**
	 * Indicates if exists any movement in this set.
	 *
	 * @return If exists any movement in this set.
	 */
	public boolean isEmpty() {
		return targets.isEmpty();
	}
	
	/**
	 * Indicates if not exists any movement in this set.
	 *
	 * @return If not exists any movement in this set.
	 */
	public boolean isNotEmpty() {
		return !isEmpty();
	}

	/**
	 * Retrieves the target in the specific index.
	 *
	 * @param index The target index.
	 *
	 * @return The target in the specific index.
	 */
	public MovementTarget getTarget(int index) {
		return targets.get(index);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(piece.name()).append(String.format("[%d, %d]", row, column)).append(" -> [");
		for (int i = 0; i < targets.size(); i++) {
			MovementTarget target = targets.get(i);
			if (i > 0) {
				builder.append(", ");
			}
			if (target.isCastling()) {
				builder.append("castling");
			}
			builder.append(String.format("[%d, %d]", target.getRow(), target.getColumn()));
		}
		builder.append("]");
		return builder.toString();
	}

	/**
	 * Evaluates if this movements set refers to a pawn promotion.
	 *
	 * @return A value <code>true</code> if this movements set is a pawn promotion, or
	 *         <code>false</code> otherwise.
	 */
	public boolean isPawnPromotion() {
		return pawnPromotion;
	}
}
