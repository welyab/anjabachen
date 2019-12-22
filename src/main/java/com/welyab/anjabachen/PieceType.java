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

@SuppressWarnings("javadoc")
public enum PieceType {
	
	KING(PieceType.KING_VALUE),
	QUEEN(PieceType.QUEEN_VALUE),
	ROOK(PieceType.ROOK_VALUE),
	BISHOP(PieceType.BISHOP_VALUE),
	KNIGHT(PieceType.KNIGHT_VALUE),
	PAWN(PieceType.PAWN_VALUE);
	
	public static final int KING_VALUE = 6;
	
	public static final int QUEEN_VALUE = 5;
	
	public static final int ROOK_VALUE = 4;
	
	public static final int BISHOP_VALUE = 3;
	
	public static final int KNIGHT_VALUE = 2;
	
	public static final int PAWN_VALUE = 1;
	
	private final int value;
	
	PieceType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public boolean isKing() {
		return this == KING;
	}
	
	public boolean isQueen() {
		return this == QUEEN;
	}
	
	public boolean isRook() {
		return this == ROOK;
	}
	
	public boolean isBishop() {
		return this == BISHOP;
	}
	
	public boolean isKnight() {
		return this == KNIGHT;
	}
	
	public boolean isPawn() {
		return this == PAWN;
	}
}
