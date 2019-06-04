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
 */package com.welyab.anjabachen;

/**
 * A target represents the destination of a piece after move.
 *
 * <p>
 * If the being moved piece is a pawn during a promotion, the method {@link #getPiece()} may be used
 * in order to know which piece should be used as replacement for the pawn. In other scenarios, the
 * {@link #getPiece()} method return the same piece present in the origin square.
 *
 * @author Welyab Paula
 */
public class MovementTarget {

	/**
	 * If the being moved piece is a pawn during a promotion, this field may be
	 * used in order to know which piece should be used as replacement for the pawn. In other
	 * scenarios, this field keeps the same piece present in the origin square.
	 */
	private final Piece piece;

	/**
	 * The destination square row number.
	 */
	private final int row;

	/**
	 * The destination square column number.
	 */
	private final int column;

	/**
	 * Creates a new target by informing the piece and the <code>[row, column]</code> coordinates of
	 * the destination square.
	 *
	 * @param piece If the being moved piece is a pawn during a promotion, this field may be used in
	 *        order to know which piece should be used as replacement for the pawn. In
	 *        other scenarios, this field keeps the same piece present in the origin square.
	 *
	 * @param row The destination square row number.
	 * @param column The destination square column number.
	 */
	public MovementTarget(Piece piece, int row, int column) {
		this.piece = piece;
		this.row = row;
		this.column = column;
	}

	/**
	 * If the being moved piece is a pawn during a promotion, this method is used to let
	 * know which piece should be used as replacement for the pawn. In other scenarios,
	 * this method return the same piece present in the origin square.
	 *
	 * @return The piece.
	 */
	public Piece getPiece() {
		return piece;
	}

	/**
	 * Retrieves the destination square row number.
	 *
	 * @return he destination square row number.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Retrieves the destination square row number.
	 *
	 * @return The destination square row number.
	 */
	public int getColumn() {
		return column;
	}
}
