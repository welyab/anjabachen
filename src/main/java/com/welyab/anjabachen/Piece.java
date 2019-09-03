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
	 * Represents the black king piece.
	 */
	BLACK_KING(-6, PieceType.KING, 'k', Color.BLACK),
	
	/**
	 * Represents the black queen piece.
	 */
	BLACK_QUEEN(-5, PieceType.QUEEN, 'q', Color.BLACK),
	
	/**
	 * Represents the black rook piece.
	 */
	BLACK_ROOK(-4, PieceType.ROOK, 'r', Color.BLACK),
	
	/**
	 * Represents the black bishop piece.
	 */
	BLACK_BISHOP(-3, PieceType.BISHOP, 'b', Color.BLACK),
	
	/**
	 * Represents the black knight piece.
	 */
	BLACK_KNIGHT(-2, PieceType.KNIGHT, 'n', Color.BLACK),
	
	/**
	 * Represents the black pawn piece.
	 */
	BLACK_PAWN(-1, PieceType.PAWN, 'p', Color.BLACK),
	
	/**
	 * Represents the white king piece.
	 */
	WHITE_KING(6, PieceType.KING, 'K', Color.WHITE),
	
	/**
	 * Represents the white queen piece.
	 */
	WHITE_QUEEN(5, PieceType.QUEEN, 'Q', Color.WHITE),
	
	/**
	 * Represents the white rook piece.
	 */
	WHITE_ROOK(4, PieceType.ROOK, 'R', Color.WHITE),
	
	/**
	 * Represents the white bishop piece.
	 */
	WHITE_BISHOP(3, PieceType.BISHOP, 'B', Color.WHITE),
	
	/**
	 * Represents the white knight piece.
	 */
	WHITE_KNIGHT(2, PieceType.KNIGHT, 'N', Color.WHITE),
	
	/**
	 * Represents the white king piece.
	 */
	WHITE_PAWN(1, PieceType.PAWN, 'P', Color.WHITE);
	
	/** The id of this piece. */
	private final int id;
	
	/**
	 * The piece type (king, queen, rook...).
	 */
	private final PieceType type;
	
	/**
	 * The piece color, if white, or black.
	 */
	private final Color color;
	
	/**
	 * The letter symbol for this piece.
	 */
	private final char letterSymbol;
	
	@SuppressWarnings("javadoc")
	private Piece(int id, PieceType type, char letterSymbol, Color color) {
		this.type = type;
		this.letterSymbol = letterSymbol;
		this.color = color;
		this.id = id;
	}
	
	/**
	 * Retrieves the id of this piece.
	 * 
	 * @return The id.
	 */
	public int getId() {
		return id;
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
	
	/**
	 * Retrieves the letter symbols for this piece.
	 *
	 * @return The letter piece.
	 */
	public char getLetterSymbol() {
		return letterSymbol;
	}
	
	/**
	 * Retrieves the piece instance associated with given letter symbol.
	 *
	 * @param letterSymbol The letter symbol associated requested piece.
	 *
	 * @return The piece.
	 *
	 * @throws IllegalArgumentException If the given letter symbols does not have a piece associated
	 *         with it.
	 */
	public static Piece fromLetterSymbol(char letterSymbol) {
		for (Piece piece : values()) {
			if (piece.getLetterSymbol() == letterSymbol) {
				return piece;
			}
		}
		throw new IllegalArgumentException(
			String.format(
				"Invalid letter symbol: %c",
				letterSymbol
			)
		);
	}
	
	/**
	 * Given a type and a color, this method returns the associated piece.
	 *
	 * @param type The piece type (king, queen, rook...).
	 * @param color The piece color (black or white).
	 *
	 * @return The instance of piece.
	 */
	public static Piece get(PieceType type, Color color) {
		return switch (color) {
			case WHITE -> switch (type) {
					case KING -> Piece.WHITE_KING;
					case QUEEN -> Piece.WHITE_QUEEN;
					case ROOK -> Piece.WHITE_ROOK;
					case BISHOP -> Piece.WHITE_BISHOP;
					case KNIGHT -> Piece.WHITE_KNIGHT;
					case PAWN -> Piece.WHITE_PAWN;
				};
			case BLACK -> switch (type) {
					case KING -> Piece.BLACK_KING;
					case QUEEN -> Piece.BLACK_QUEEN;
					case ROOK -> Piece.BLACK_ROOK;
					case BISHOP -> Piece.BLACK_BISHOP;
					case KNIGHT -> Piece.BLACK_KNIGHT;
					case PAWN -> Piece.BLACK_PAWN;
				};
		};
	}
}
