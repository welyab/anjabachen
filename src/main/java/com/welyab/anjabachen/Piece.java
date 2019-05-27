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
 * Enumerates available pieces of chess game. Here, the same piece type, but with different colors,
 * are considered distinguished. i.e, the black king is a piece, and the white king is another.
 *
 * @author Welyab Paula
 *
 * @see PieceType
 * @see Color
 */
public enum Piece {

	/**
	 */
	BLACK_KING(PieceType.KING, Color.BLACK),

	/**
	 */
	BLACK_QUEEN(PieceType.QUEEN, Color.BLACK),

	/**
	 */
	BLACK_ROOK(PieceType.ROOK, Color.BLACK),

	/**
	 */
	BLACK_BISHOP(PieceType.BISHOP, Color.BLACK),

	/**
	 */
	BLACK_KNIGHT(PieceType.KNIGHT, Color.BLACK),

	/**
	 */
	BLACK_PAWN(PieceType.PAWN, Color.BLACK),

	/**
	 */
	WHITE_KING(PieceType.KING, Color.WHITE),

	/**
	 */
	WHITE_QUEEN(PieceType.QUEEN, Color.WHITE),

	/**
	 */
	WHITE_ROOK(PieceType.ROOK, Color.WHITE),

	/**
	 */
	WHITE_BISHOP(PieceType.BISHOP, Color.WHITE),

	/**
	 */
	WHITE_KNIGHT(PieceType.KNIGHT, Color.WHITE),

	/**
	 */
	WHITE_PAWN(PieceType.PAWN, Color.WHITE);

	/**
	 * The piece type (king, queen, rook...).
	 */
	private final PieceType type;

	/**
	 * The piece color, if white, or black.
	 */
	private final Color color;

	@SuppressWarnings("javadoc")
	private Piece(PieceType type, Color color) {
		this.type = type;
		this.color = color;
	}

	/**
	 * Retrieves the type of this piece (king, queen, rook...).
	 *
	 * @return The type representation.
	 */
	public PieceType getType() {
		return type;
	}

	/**
	 * Retrieves the color of this piece (white or black).
	 *
	 * @return The color.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Evaluates if this piece belongs to the black side.
	 *
	 * @return A value <code>true</code> if this piece belongs to the black side, or
	 *         <code>false</code> otherwise.
	 */
	public boolean isBlack() {
		return color.isBlack();
	}

	/**
	 * Evaluates if this piece belongs to the white side.
	 *
	 * @return A value <code>true</code> if this piece belongs to the white side, or
	 *         <code>false</code> otherwise.
	 */
	public boolean isWhite() {
		return color.isWhite();
	}

	/**
	 * Evaluates if this piece is a king, regardless to its color.
	 *
	 * @return A value <code>true</code> if this piece is a king, or <code>false</code> otherwise.
	 */
	public boolean isKing() {
		return type.isKing();
	}

	/**
	 * Evaluates if this piece is a queen, regardless to its color.
	 *
	 * @return A value <code>true</code> if this piece is a queen, or <code>false</code> otherwise.
	 */
	public boolean isQueen() {
		return type.isQueen();
	}

	/**
	 * Evaluates if this piece is a rook, regardless to its color.
	 *
	 * @return A value <code>true</code> if this piece is a rook, or <code>false</code> otherwise.
	 */
	public boolean isRook() {
		return type.isRook();
	}

	/**
	 * Evaluates if this piece is a bishop, regardless to its color.
	 *
	 * @return A value <code>true</code> if this piece is a bishop, or <code>false</code> otherwise.
	 */
	public boolean isBishop() {
		return type.isBishop();
	}

	/**
	 * Evaluates if this piece is a knight, regardless to its color.
	 *
	 * @return A value <code>true</code> if this piece is a knight, or <code>false</code> otherwise.
	 */
	public boolean isKnight() {
		return type.isKnight();
	}

	/**
	 * Evaluates if this piece is a pawn, regardless to its color.
	 *
	 * @return A value <code>true</code> if this piece is a pawn, or <code>false</code> otherwise.
	 */
	public boolean isPawn() {
		return type.isPawn();
	}

	/**
	 * Evaluates if this piece is a black king.
	 *
	 * @return A value <code>true</code> if this piece is a black king, or <code>false</code>
	 *         otherwise.
	 */
	public boolean isBlackKing() {
		return this == BLACK_KING;
	}

	/**
	 * Evaluates if this piece is a black queen.
	 *
	 * @return A value <code>true</code> if this piece is a black queen, or <code>false</code>
	 *         otherwise.
	 */
	public boolean isBlackQueen() {
		return this == BLACK_QUEEN;
	}

	/**
	 * Evaluates if this piece is a black rook.
	 *
	 * @return A value <code>true</code> if this piece is a black rook, or <code>false</code>
	 *         otherwise.
	 */
	public boolean isBlackRook() {
		return this == BLACK_ROOK;
	}

	/**
	 * Evaluates if this piece is a black bishop.
	 *
	 * @return A value <code>true</code> if this piece is a black bishop, or <code>false</code>
	 *         otherwise.
	 */
	public boolean isBlackBishop() {
		return this == BLACK_BISHOP;
	}

	/**
	 * Evaluates if this piece is a black knight.
	 *
	 * @return A value <code>true</code> if this piece is a black knight, or <code>false</code>
	 *         otherwise.
	 */
	public boolean isBlackKnight() {
		return this == BLACK_KNIGHT;
	}

	/**
	 * Evaluates if this piece is a black pawn.
	 *
	 * @return A value <code>true</code> if this piece is a black pawn, or <code>false</code>
	 *         otherwise.
	 */
	public boolean isBlackPawn() {
		return this == BLACK_PAWN;
	}

	/**
	 * Evaluates if this piece is a white king.
	 *
	 * @return A value <code>true</code> if this piece is a black king, or <code>false</code>
	 *         otherwise.
	 */
	public boolean isWhiteKing() {
		return this == WHITE_KING;
	}

	/**
	 * Evaluates if this piece is a white queen.
	 *
	 * @return A value <code>true</code> if this piece is a white queen, or <code>false</code>
	 *         otherwise.
	 */
	public boolean isWhiteQueen() {
		return this == WHITE_QUEEN;
	}

	/**
	 * Evaluates if this piece is a white rook.
	 *
	 * @return A value <code>true</code> if this piece is a white rook, or <code>false</code>
	 *         otherwise.
	 */
	public boolean isWhiteRook() {
		return this == WHITE_ROOK;
	}

	/**
	 * Evaluates if this piece is a white bishop.
	 *
	 * @return A value <code>true</code> if this piece is a white bishop, or <code>false</code>
	 *         otherwise.
	 */
	public boolean isWhiteBishop() {
		return this == WHITE_BISHOP;
	}

	/**
	 * Evaluates if this piece is a white knight.
	 *
	 * @return A value <code>true</code> if this piece is a white knight, or <code>false</code>
	 *         otherwise.
	 */
	public boolean isWhiteKnight() {
		return this == Piece.WHITE_KING;
	}

	/**
	 * Evaluates if this piece is a white pawn.
	 *
	 * @return A value <code>true</code> if this piece is a white pawn, or <code>false</code>
	 *         otherwise.
	 */
	public boolean isWhitePawn() {
		return this == WHITE_PAWN;
	}
}
