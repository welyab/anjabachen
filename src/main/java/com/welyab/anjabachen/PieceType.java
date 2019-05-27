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
 * Enumerates available piece types of chess game.
 *
 * @author Welyab Paula
 */
public enum PieceType {

	/**
	 * The king piece type.
	 */
	KING,

	/**
	 * The queen piece type.
	 */
	QUEEN,

	/**
	 * The rook piece type.
	 */
	ROOK,

	/**
	 * The bishop piece type.
	 */
	BISHOP,

	/**
	 * The knight piece type.
	 */
	KNIGHT,

	/**
	 * The pawn piece type.
	 */
	PAWN;

	/**
	 * Evaluates whether this piece type is a representative for the king piece.
	 *
	 * @return A value <code>true</code> whether this type presents the king, or <code>false</code>
	 *         otherwise.
	 */
	public boolean isKing() {
		return this == KING;
	}

	/**
	 * Evaluates whether this piece type is a representative for the queen piece.
	 *
	 * @return A value <code>true</code> whether this type presents the queen, or <code>false</code>
	 *         otherwise.
	 */
	public boolean isQueen() {
		return this == QUEEN;
	}

	/**
	 * Evaluates whether this piece type is a representative for the queen rook.
	 *
	 * @return A value <code>true</code> whether this type presents the rook, or <code>false</code>
	 *         otherwise.
	 */
	public boolean isRook() {
		return this == ROOK;
	}

	/**
	 * Evaluates whether this piece type is a representative for the bishop piece.
	 *
	 * @return A value <code>true</code> whether this type presents the bishop, or
	 *         <code>false</code> otherwise.
	 */
	public boolean isBishop() {
		return this == BISHOP;
	}

	/**
	 * Evaluates whether this piece type is a representative for the knight piece.
	 *
	 * @return A value <code>true</code> whether this type presents the bishop, or
	 *         <code>false</code> otherwise.
	 */
	public boolean isKnight() {
		return this == KNIGHT;
	}

	/**
	 * Evaluates whether this piece type is a representative for the pawn piece.
	 *
	 * @return A value <code>true</code> whether this type presents the pawn, or <code>false</code>
	 *         otherwise.
	 */
	public boolean isPawn() {
		return this == PAWN;
	}
}
