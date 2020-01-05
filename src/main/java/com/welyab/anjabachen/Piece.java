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

public enum Piece {
	
	WHITE_KING(PieceType.KING, Color.WHITE, Piece.WHITE_KING_LETTER),
	WHITE_QUEEN(PieceType.QUEEN, Color.WHITE, Piece.WHITE_QUEEN_LETTER),
	WHITE_ROOK(PieceType.ROOK, Color.WHITE, Piece.WHITE_ROOK_LETTER),
	WHITE_BISHOP(PieceType.BISHOP, Color.WHITE, Piece.WHITE_BISHOP_LETTER),
	WHITE_KNIGHT(PieceType.KNIGHT, Color.WHITE, Piece.WHITE_KNIGHT_LETTER),
	WHITE_PAWN(PieceType.PAWN, Color.WHITE, Piece.WHITE_PAWN_LETTER),
	BLACK_KING(PieceType.KING, Color.BLACK, Piece.BLACK_KING_LETTER),
	BLACK_QUEEN(PieceType.QUEEN, Color.BLACK, Piece.BLACK_QUEEN_LETTER),
	BLACK_ROOK(PieceType.ROOK, Color.BLACK, Piece.BLACK_ROOK_LETTER),
	BLACK_BISHOP(PieceType.BISHOP, Color.BLACK, Piece.BLACK_BISHOP_LETTER),
	BLACK_KNIGHT(PieceType.KNIGHT, Color.BLACK, Piece.BLACK_KNIGHT_LETTER),
	BLACK_PAWN(PieceType.PAWN, Color.BLACK, Piece.BLACK_PAWN_LETTER);
	
	public static final char WHITE_KING_LETTER = 'K';
	
	public static final char WHITE_QUEEN_LETTER = 'Q';
	
	public static final char WHITE_ROOK_LETTER = 'R';
	
	public static final char WHITE_BISHOP_LETTER = 'B';
	
	public static final char WHITE_KNIGHT_LETTER = 'N';
	
	public static final char WHITE_PAWN_LETTER = 'P';
	
	public static final char BLACK_KING_LETTER = 'k';
	
	public static final char BLACK_QUEEN_LETTER = 'q';
	
	public static final char BLACK_ROOK_LETTER = 'r';
	
	public static final char BLACK_BISHOP_LETTER = 'b';
	
	public static final char BLACK_KNIGHT_LETTER = 'n';
	
	public static final char BLACK_PAWN_LETTER = 'p';
	
	private final PieceType type;
	
	private final Color color;
	
	private final char letter;
	
	Piece(PieceType type, Color color, char letter) {
		this.type = type;
		this.color = color;
		this.letter = letter;
	}
	
	public PieceType getType() {
		return type;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getValue() {
		return type.getValue() * color.getValue();
	}
	
	public char getLetter() {
		return letter;
	}
	
	public boolean isWhiteKing() {
		return this == WHITE_KING;
	}
	
	public boolean isWhiteQueen() {
		return this == WHITE_QUEEN;
	}
	
	public boolean isWhiteRook() {
		return this == WHITE_ROOK;
	}
	
	public boolean isWhiteBishop() {
		return this == WHITE_BISHOP;
	}
	
	public boolean isWhiteKnight() {
		return this == WHITE_KNIGHT;
	}
	
	public boolean isWhitePawn() {
		return this == WHITE_PAWN;
	}
	
	public boolean isBlackKing() {
		return this == BLACK_KING;
	}
	
	public boolean isBlackQueen() {
		return this == BLACK_QUEEN;
	}
	
	public boolean isBlackRook() {
		return this == BLACK_ROOK;
	}
	
	public boolean isBlackBishop() {
		return this == BLACK_BISHOP;
	}
	
	public boolean isBlackKnight() {
		return this == BLACK_KNIGHT;
	}
	
	public boolean isBlackPawn() {
		return this == BLACK_PAWN;
	}
	
	public static Piece valueOf(char pieceLetter) {
		return switch (pieceLetter) {
			case WHITE_KING_LETTER -> Piece.WHITE_KING;
			case WHITE_QUEEN_LETTER -> Piece.WHITE_QUEEN;
			case WHITE_ROOK_LETTER -> Piece.WHITE_ROOK;
			case WHITE_BISHOP_LETTER -> Piece.WHITE_BISHOP;
			case WHITE_KNIGHT_LETTER -> Piece.WHITE_KNIGHT;
			case WHITE_PAWN_LETTER -> Piece.WHITE_PAWN;
			case BLACK_KING_LETTER -> Piece.BLACK_KING;
			case BLACK_QUEEN_LETTER -> Piece.BLACK_QUEEN;
			case BLACK_ROOK_LETTER -> Piece.BLACK_ROOK;
			case BLACK_BISHOP_LETTER -> Piece.BLACK_BISHOP;
			case BLACK_KNIGHT_LETTER -> Piece.BLACK_KNIGHT;
			case BLACK_PAWN_LETTER -> Piece.BLACK_PAWN;
			default -> throw new IllegalArgumentException(
				String.format(
					"Invalid piece letter: '%c'",
					pieceLetter
				)
			);
		};
	}
}
