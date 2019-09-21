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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.welyab.anjabachen.movement.PieceType;

/**
 * Unit tests for <code>PieceType</code> enumeration.
 *
 * @author Welyab Paula
 */
public class PieceTypeTest {

	@Test
	@SuppressWarnings("javadoc")
	public void isKingShouldReturnTrueWhenPieceIsKing() {
		assertTrue(PieceType.KING.isKing());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isQueenShouldReturnTrueWhenPieceIsQueen() {
		assertTrue(PieceType.QUEEN.isQueen());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isRookShouldReturnTrueWhenPieceIsRook() {
		assertTrue(PieceType.ROOK.isRook());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isBishopShouldReturnTrueWhenPieceIsBishop() {
		assertTrue(PieceType.BISHOP.isBishop());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isKnightShouldReturnTrueWhenPieceIsKnight() {
		assertTrue(PieceType.KNIGHT.isKnight());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isPawnShouldReturnTrueWhenPieceIsPawn() {
		assertTrue(PieceType.PAWN.isPawn());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isKingShouldReturnFalseWhenPieceIsNotKing() {
		assertFalse(PieceType.QUEEN.isKing());
		assertFalse(PieceType.ROOK.isKing());
		assertFalse(PieceType.BISHOP.isKing());
		assertFalse(PieceType.KNIGHT.isKing());
		assertFalse(PieceType.PAWN.isKing());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isQueenShouldReturnFalseWhenPieceIsNotQueen() {
		assertFalse(PieceType.KING.isQueen());
		assertFalse(PieceType.ROOK.isQueen());
		assertFalse(PieceType.BISHOP.isQueen());
		assertFalse(PieceType.KNIGHT.isQueen());
		assertFalse(PieceType.PAWN.isQueen());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isRookShouldReturnFalseWhenPieceIsNotRook() {
		assertFalse(PieceType.KING.isRook());
		assertFalse(PieceType.QUEEN.isRook());
		assertFalse(PieceType.BISHOP.isRook());
		assertFalse(PieceType.KNIGHT.isRook());
		assertFalse(PieceType.PAWN.isRook());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isBishopShouldReturnFalseWhenPieceIsNotBishop() {
		assertFalse(PieceType.KING.isBishop());
		assertFalse(PieceType.QUEEN.isBishop());
		assertFalse(PieceType.ROOK.isBishop());
		assertFalse(PieceType.KNIGHT.isBishop());
		assertFalse(PieceType.PAWN.isBishop());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isKnightShouldReturnFalseWhenPieceIsNotKnight() {
		assertFalse(PieceType.KING.isKnight());
		assertFalse(PieceType.QUEEN.isKnight());
		assertFalse(PieceType.ROOK.isKnight());
		assertFalse(PieceType.BISHOP.isKnight());
		assertFalse(PieceType.PAWN.isKnight());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isPawnShouldReturnFalseWhenPieceIsNotPawn() {
		assertFalse(PieceType.KING.isPawn());
		assertFalse(PieceType.QUEEN.isPawn());
		assertFalse(PieceType.ROOK.isPawn());
		assertFalse(PieceType.BISHOP.isPawn());
		assertFalse(PieceType.KNIGHT.isPawn());
	}
}
