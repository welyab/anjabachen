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
	 * The destination square position referred by this movement target.
	 */
	private final Position position;
	
	/**
	 * The meta information about this movement.
	 */
	private final int movementFlags;
	
	/**
	 * Creates a new target by informing the piece and the <code>[row, column]</code> coordinates of
	 * the destination square.
	 *
	 * @param piece If the being moved piece is a pawn during a promotion, this field may be used in
	 *        order to know which piece should be used as replacement for the pawn. In
	 *        other scenarios, this field keeps the same piece present in the origin square.
	 * @param position The destination square position referred by this movement target.
	 * @param movementFlags The meta information about this movement.
	 */
	public MovementTarget(
			Piece piece,
			Position position,
			int movementFlags
	) {
		this.piece = piece;
		this.position = position;
		this.movementFlags = movementFlags;
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
	 * Retrieves the destination square position referred by this movement target.
	 *
	 * @return The destination square position referred by this movement target.
	 */
	public Position getPosition() {
		return position;
	}
	
	public int getMovementFlags() {
		return movementFlags;
	}
	
	@Override
	public String toString() {
		return String.format(
			"Target %s %s[%d, %d]",
			piece,
			BoardUtils.isCapture(getMovementFlags()) ? "x" : "",
			position.getRow(),
			position.getColumn()
		);
	}
}
