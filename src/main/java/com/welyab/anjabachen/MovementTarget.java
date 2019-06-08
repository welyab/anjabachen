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
	 * Keeps the rook piece movement during a castling movement.
	 */
	private final RookMovement rooMovement;
	
	/**
	 * If the movement refers to a <i>en passant</i> pawn capture, this field keeps the position of
	 * the captured pawn.
	 */
	private final Position enPassantCapturedPawn;
	
	/**
	 * Creates a new target by informing the piece and the <code>[row, column]</code> coordinates of
	 * the destination square.
	 *
	 * <p>
	 * This constructor is the most simple one. Used when a piece simply moving from a square to
	 * another. There are constructors for castling movements and <i>en passant</i> pawn captures.
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
		rooMovement = null;
		enPassantCapturedPawn = null;
	}
	
	public MovementTarget(Piece piece, int row, int column, RookMovement rooMovement) {
		this.piece = piece;
		this.row = row;
		this.column = column;
		this.rooMovement = rooMovement;
		enPassantCapturedPawn = null;
	}
	
	public MovementTarget(Piece piece, int row, int column, Position enPassantCapturedPawn) {
		this.piece = piece;
		this.row = row;
		this.column = column;
		this.enPassantCapturedPawn = enPassantCapturedPawn;
		rooMovement = null;
	}
	
	public boolean isCastling() {
		return rooMovement != null;
	}
	
	public RookMovement getRooMovement() {
		if (!isCastling()) {
			throw new MovementException("movement target is not a castling movement");
		}
		return rooMovement;
	}
	
	/**
	 * Evaluates if this movement target refers to an <code>en passant</code>.
	 *
	 * @return A value <code>true</code> if this movement is an <i>en passant</i>, or
	 *         <code>false</code> otherwise.
	 *
	 * @see #getEnPassantCapturedPawn()
	 */
	public boolean isEnPassant() {
		return enPassantCapturedPawn != null;
	}
	
	/**
	 * Retrieves the position of the opposite captured pawn.
	 *
	 * @return The position of the pawn that should be removed.
	 *
	 * @see #isEnPassant()
	 *
	 * @throws MovementException If the movement is not a <i>en passant</i>.
	 */
	public Position getEnPassantCapturedPawn() {
		if (!isEnPassant()) {
			throw new MovementException("movement target is not an en passant movement");
		}
		return enPassantCapturedPawn;
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
	
	/**
	 * This class keeps the rook movement during a castling.
	 *
	 * @author Welyab Paula
	 */
	public static class RookMovement {
		
		/**
		 * The origin location of the rook piece.
		 */
		private final Position origin;
		
		/**
		 * The final location of the rook piece.
		 */
		private final Position destination;
		
		/**
		 * Creates a new instance using given <i>origin</i> and <i>destination</i> arguments.
		 *
		 * @param origin The origin position.
		 * @param destination The final position.
		 */
		public RookMovement(Position origin, Position destination) {
			this.origin = origin;
			this.destination = destination;
		}
		
		/**
		 * Retrieves the origin position of the rook piece during a castling movement.
		 *
		 * @return The position.
		 */
		public Position getOrigin() {
			return origin;
		}
		
		/**
		 * Retrieves the destination position of the rook piece during a castling movement.
		 *
		 * @return The position.
		 */
		public Position getDestination() {
			return destination;
		}
	}
}
