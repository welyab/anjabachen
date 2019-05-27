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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit tests for <code>Piece</code> class.
 *
 * @author Welyab Paula
 */
public class PieceTest {

	@Test
	@SuppressWarnings("javadoc")
	public void getTypeShouldReturnKingTypeIfPieceIsBlackKing() {
		assertEquals(PieceType.KING, Piece.BLACK_KING.getType());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getTypeShouldReturnQueenTypeIfPieceIsBlackQueen() {
		assertEquals(PieceType.QUEEN, Piece.BLACK_QUEEN.getType());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getTypeShouldReturnRookTypeIfPieceIsBlackRook() {
		assertEquals(PieceType.ROOK, Piece.BLACK_ROOK.getType());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getTypeShouldReturnBishopTypeIfPieceIsBlackBishop() {
		assertEquals(PieceType.BISHOP, Piece.BLACK_BISHOP.getType());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getTypeShouldReturnKnightTypeIfPieceIsBlackKnight() {
		assertEquals(PieceType.KNIGHT, Piece.BLACK_KNIGHT.getType());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getTypeShouldReturnPawnTypeIfPieceIsBlackPawn() {
		assertEquals(PieceType.PAWN, Piece.BLACK_PAWN.getType());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getTypeShouldReturnKingTypeIfPieceIsWhiteKing() {
		assertEquals(PieceType.KING, Piece.WHITE_KING.getType());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getTypeShouldReturnQueenTypeIfPieceIsWhiteQueen() {
		assertEquals(PieceType.QUEEN, Piece.WHITE_QUEEN.getType());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getTypeShouldReturnRookTypeIfPieceIsWhiteRook() {
		assertEquals(PieceType.ROOK, Piece.WHITE_ROOK.getType());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getTypeShouldReturnBishopTypeIfPieceIsWhiteBishop() {
		assertEquals(PieceType.BISHOP, Piece.WHITE_BISHOP.getType());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getTypeShouldReturnKnightTypeIfPieceIsWhiteKnight() {
		assertEquals(PieceType.KNIGHT, Piece.WHITE_KNIGHT.getType());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getTypeShouldReturnPawnTypeIfPieceIsWhitePawn() {
		assertEquals(PieceType.PAWN, Piece.WHITE_PAWN.getType());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getColorShouldReturnBlackIfPieceIsBlackKing() {
		assertEquals(Color.BLACK, Piece.BLACK_KING.getColor());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getColorShouldReturnBlackIfPieceIsBlackQueen() {
		assertEquals(Color.BLACK, Piece.BLACK_QUEEN.getColor());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getColorShouldReturnBlackIfPieceIsBlackRook() {
		assertEquals(Color.BLACK, Piece.BLACK_ROOK.getColor());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getColorShouldReturnBlackIfPieceIsBlackBishop() {
		assertEquals(Color.BLACK, Piece.BLACK_BISHOP.getColor());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getColorShouldReturnBlackIfPieceIsBlackKnight() {
		assertEquals(Color.BLACK, Piece.BLACK_KING.getColor());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getColorShouldReturnBlackIfPieceIsBlackPawn() {
		assertEquals(Color.BLACK, Piece.BLACK_PAWN.getColor());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getColorShouldReturnWhiteIfPieceIsWhiteKing() {
		assertEquals(Color.WHITE, Piece.WHITE_KING.getColor());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getColorShouldReturnWhiteIfPieceIsWhiteQueen() {
		assertEquals(Color.WHITE, Piece.WHITE_QUEEN.getColor());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getColorShouldReturnWhiteIfPieceIsWhiteRook() {
		assertEquals(Color.WHITE, Piece.WHITE_ROOK.getColor());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getColorShouldReturnWhiteIfPieceIsWhiteBishop() {
		assertEquals(Color.WHITE, Piece.WHITE_BISHOP.getColor());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getColorShouldReturnWhiteIfPieceIsWhiteKnight() {
		assertEquals(Color.WHITE, Piece.WHITE_KING.getColor());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getColorShouldReturnWhiteIfPieceIsWhitePawn() {
		assertEquals(Color.WHITE, Piece.WHITE_PAWN.getColor());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isBlackShouldReturnTrueIfThePieceIsTheBlackKing() {
		assertTrue(Piece.BLACK_KING.isBlack());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isBlackShouldReturnTrueIfThePieceIsTheBlackQueen() {
		assertTrue(Piece.BLACK_QUEEN.isBlack());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isBlackShouldReturnTrueIfThePieceIsTheBlackRook() {
		assertTrue(Piece.BLACK_ROOK.isBlack());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isBlackShouldReturnTrueIfThePieceIsTheBlackBishop() {
		assertTrue(Piece.BLACK_BISHOP.isBlack());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isBlackShouldReturnTrueIfThePieceIsTheBlackKnight() {
		assertTrue(Piece.BLACK_KNIGHT.isBlack());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isBlackShouldReturnTrueIfThePieceIsTheBlackPawn() {
		assertTrue(Piece.BLACK_PAWN.isBlack());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isWhiteShouldReturnTrueIfThePieceIsTheWhiteKing() {
		assertTrue(Piece.WHITE_KING.isWhite());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isWhiteShouldReturnTrueIfThePieceIsTheWhiteQueen() {
		assertTrue(Piece.WHITE_QUEEN.isWhite());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isWhiteShouldReturnTrueIfThePieceIsTheWhiteRook() {
		assertTrue(Piece.WHITE_ROOK.isWhite());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isWhiteShouldReturnTrueIfThePieceIsTheWhiteBishop() {
		assertTrue(Piece.WHITE_BISHOP.isWhite());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isWhiteShouldReturnTrueIfThePieceIsTheWhiteKnight() {
		assertTrue(Piece.WHITE_KNIGHT.isWhite());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isWhiteShouldReturnTrueIfThePieceIsTheWhitePawn() {
		assertTrue(Piece.WHITE_PAWN.isWhite());
	}
}
