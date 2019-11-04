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
package com.welyab.anjabachen.movement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Unit tests for the <code>BoardUtil</code> class.
 * 
 * @author Welyab Paula
 */
public class MovementUtilTest {
	
	@Test
	@SuppressWarnings("javadoc")
	public void opositeColorShouldReturnWhiteWhenGivenColorIsBlack() {
		assertEquals(MovementUtil.WHITE, MovementUtil.getOppositeColor(MovementUtil.BLACK));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void opositeColorShouldReturnBlackWhenGivenColorIsWhite() {
		assertEquals(MovementUtil.BLACK, MovementUtil.getOppositeColor(MovementUtil.WHITE));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void opositeColorShouldThrowIllegalArgument() {
		assertThrows(IllegalArgumentException.class, () -> {
			MovementUtil.getOppositeColor(0);
		});
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			MovementUtil.WHITE_LETTER + "," + MovementUtil.WHITE,
			MovementUtil.BLACK_LETTER + "," + MovementUtil.BLACK
		}
	)
	@SuppressWarnings("javadoc")
	public void colorCodeToLetterShouldReturnProperlyColorLetter(char expected, byte input) {
		assertEquals(expected, MovementUtil.colorCodeToLetter(input));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void colorCodeToLetterShouldThrowIllegalArgumentexceptionWhenColorCodeInvalid() {
		assertThrows(IllegalArgumentException.class, () -> {
			MovementUtil.colorCodeToLetter(0);
		});
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			MovementUtil.WHITE + "," + MovementUtil.WHITE_LETTER,
			MovementUtil.BLACK + "," + MovementUtil.BLACK_LETTER
		}
	)
	@SuppressWarnings("javadoc")
	public void colorLetterToCodeShouldReturnProperlyColorCode(byte expected, char input) {
		assertEquals(expected, MovementUtil.colorLetterToCode(input));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void colorLetterToCodeShouldThrowIllegalArgumentexceptionWhenColorCodeInvalid() {
		assertThrows(IllegalArgumentException.class, () -> {
			MovementUtil.colorLetterToCode('x');
		});
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			MovementUtil.BLACK_KING_LETTER + "," + MovementUtil.BLACK_KING,
			MovementUtil.BLACK_QUEEN_LETTER + "," + MovementUtil.BLACK_QUEEN,
			MovementUtil.BLACK_ROOK_LETTER + "," + MovementUtil.BLACK_ROOK,
			MovementUtil.BLACK_BISHOP_LETTER + "," + MovementUtil.BLACK_BISHOP,
			MovementUtil.BLACK_KNIGHT_LETTER + "," + MovementUtil.BLACK_KNIGHT,
			MovementUtil.BLACK_PAWN_LETTER + "," + MovementUtil.BLACK_PAWN,
			MovementUtil.WHITE_KING_LETTER + "," + MovementUtil.WHITE_KING,
			MovementUtil.WHITE_QUEEN_LETTER + "," + MovementUtil.WHITE_QUEEN,
			MovementUtil.WHITE_ROOK_LETTER + "," + MovementUtil.WHITE_ROOK,
			MovementUtil.WHITE_BISHOP_LETTER + "," + MovementUtil.WHITE_BISHOP,
			MovementUtil.WHITE_KNIGHT_LETTER + "," + MovementUtil.WHITE_KNIGHT,
			MovementUtil.WHITE_PAWN_LETTER + "," + MovementUtil.WHITE_PAWN
		}
	)
	@SuppressWarnings("javadoc")
	public void pieceCodeToLetterShouldReturnProperlyPieceLetter(char pieceLetter, byte pieceCode) {
		assertEquals(pieceLetter, MovementUtil.pieceCodeToLetter(pieceCode));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void pieceCodeToLetterShouldThrowIllegalArgumentExceptionWhenPieceCodeIsInvalid() {
		assertThrows(IllegalArgumentException.class, () -> {
			MovementUtil.pieceCodeToLetter(0);
		});
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			MovementUtil.BLACK_KING + "," + MovementUtil.BLACK_KING_LETTER,
			MovementUtil.BLACK_QUEEN + "," + MovementUtil.BLACK_QUEEN_LETTER,
			MovementUtil.BLACK_ROOK + "," + MovementUtil.BLACK_ROOK_LETTER,
			MovementUtil.BLACK_BISHOP + "," + MovementUtil.BLACK_BISHOP_LETTER,
			MovementUtil.BLACK_KNIGHT + "," + MovementUtil.BLACK_KNIGHT_LETTER,
			MovementUtil.BLACK_PAWN + "," + MovementUtil.BLACK_PAWN_LETTER,
			MovementUtil.WHITE_KING + "," + MovementUtil.WHITE_KING_LETTER,
			MovementUtil.WHITE_QUEEN + "," + MovementUtil.WHITE_QUEEN_LETTER,
			MovementUtil.WHITE_ROOK + "," + MovementUtil.WHITE_ROOK_LETTER,
			MovementUtil.WHITE_BISHOP + "," + MovementUtil.WHITE_BISHOP_LETTER,
			MovementUtil.WHITE_KNIGHT + "," + MovementUtil.WHITE_KNIGHT_LETTER,
			MovementUtil.WHITE_PAWN + "," + MovementUtil.WHITE_PAWN_LETTER
		}
	)
	@SuppressWarnings("javadoc")
	public void pieceCharToCodeShouldReturnProperlyPieceCode(byte pieceCode, char pieceLetter) {
		assertEquals(pieceCode, MovementUtil.pieceLetterToCode(pieceLetter));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void pieceLetterToCodeShouldThrowIllegalArgumentExceptionWhenPieceLetterIsInvalid() {
		assertThrows(IllegalArgumentException.class, () -> {
			MovementUtil.pieceLetterToCode('x');
		});
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			true + "," + 0b11111111,
			false + "," + 0b00000000,
			true + "," + MovementUtil.WHITE_KING_CASTLING_MASK,
			false + "," + ~MovementUtil.WHITE_KING_CASTLING_MASK,
			false + "," + MovementUtil.WHITE_QUEEN_CASTLING_MASK,
			false + "," + MovementUtil.BLACK_KING_CASTLING_MASK,
			false + "," + MovementUtil.BLACK_QUEEN_CASTLING_MASK
		}
	)
	@SuppressWarnings("javadoc")
	public void isWhiteKingSideCasltingShouldReturnProperlyValue(boolean expected, int castlingFlags) {
		assertEquals(expected, MovementUtil.isWhiteKingCaslting(castlingFlags));
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			true + "," + 0b11111111,
			false + "," + 0b00000000,
			true + "," + MovementUtil.WHITE_QUEEN_CASTLING_MASK,
			false + "," + ~MovementUtil.WHITE_QUEEN_CASTLING_MASK,
			false + "," + MovementUtil.WHITE_KING_CASTLING_MASK,
			false + "," + MovementUtil.BLACK_KING_CASTLING_MASK,
			false + "," + MovementUtil.BLACK_QUEEN_CASTLING_MASK
		}
	)
	@SuppressWarnings("javadoc")
	public void isWhiteQueenSideCasltingShouldReturnProperlyValue(boolean expected, int castlingFlags) {
		assertEquals(expected, MovementUtil.isWhiteQueenCaslting(castlingFlags));
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			true + "," + 0b11111111,
			false + "," + 0b00000000,
			true + "," + MovementUtil.BLACK_KING_CASTLING_MASK,
			false + "," + ~MovementUtil.BLACK_KING_CASTLING_MASK,
			false + "," + MovementUtil.WHITE_KING_CASTLING_MASK,
			false + "," + MovementUtil.WHITE_QUEEN_CASTLING_MASK,
			false + "," + MovementUtil.BLACK_QUEEN_CASTLING_MASK
		}
	)
	@SuppressWarnings("javadoc")
	public void isBlackKingSideCasltingShouldReturnProperlyValue(boolean expected, int castlingFlags) {
		assertEquals(expected, MovementUtil.isBlackKingCaslting(castlingFlags));
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			true + "," + 0b11111111,
			false + "," + 0b00000000,
			true + "," + MovementUtil.BLACK_QUEEN_CASTLING_MASK,
			false + "," + ~MovementUtil.BLACK_QUEEN_CASTLING_MASK,
			false + "," + MovementUtil.WHITE_KING_CASTLING_MASK,
			false + "," + MovementUtil.WHITE_QUEEN_CASTLING_MASK,
			false + "," + MovementUtil.BLACK_KING_CASTLING_MASK
		}
	)
	@SuppressWarnings("javadoc")
	public void isBlackQueenSideCasltingShouldReturnProperlyValue(boolean expected, int castlingFlags) {
		assertEquals(expected, MovementUtil.isBlackQueenCaslting(castlingFlags));
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			0b1111 + "," + true + "," + true + "," + true + "," + true,
			0b0000 + "," + false + "," + false + "," + false + "," + false,
			0b1000 + "," + true + "," + false + "," + false + "," + false,
			0b0100 + "," + false + "," + true + "," + false + "," + false,
			0b0010 + "," + false + "," + false + "," + true + "," + false,
			0b0001 + "," + false + "," + false + "," + false + "," + true,
			0b1001 + "," + true + "," + false + "," + false + "," + true,
		}
	)
	@SuppressWarnings("javadoc")
	public void muxCastlingFlagsShouldReturnProperlyValue(
		byte expected,
		boolean isWhiteKingSideCastling,
		boolean isWhiteQueenSideCastling,
		boolean isBlackKingSideCastling,
		boolean isBlackQueenSideCastling
	) {
		assertEquals(
			expected,
			MovementUtil.muxCastlingFlags(
				isWhiteKingSideCastling,
				isWhiteQueenSideCastling,
				isBlackKingSideCastling,
				isBlackQueenSideCastling
			)
		);
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			true + "," + MovementUtil.WHITE_KING,
			true + "," + MovementUtil.BLACK_KING,
			false + "," + MovementUtil.WHITE_QUEEN,
			false + "," + MovementUtil.BLACK_QUEEN,
			false + "," + MovementUtil.WHITE_ROOK,
			false + "," + MovementUtil.BLACK_ROOK,
			false + "," + MovementUtil.WHITE_BISHOP,
			false + "," + MovementUtil.BLACK_BISHOP,
			false + "," + MovementUtil.WHITE_KNIGHT,
			false + "," + MovementUtil.BLACK_KNIGHT,
			false + "," + MovementUtil.WHITE_PAWN,
			false + "," + MovementUtil.BLACK_PAWN,
		}
	)
	@SuppressWarnings("javadoc")
	public void isKingShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, MovementUtil.isKing(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			false + "," + MovementUtil.WHITE_KING,
			false + "," + MovementUtil.BLACK_KING,
			true + "," + MovementUtil.WHITE_QUEEN,
			true + "," + MovementUtil.BLACK_QUEEN,
			false + "," + MovementUtil.WHITE_ROOK,
			false + "," + MovementUtil.BLACK_ROOK,
			false + "," + MovementUtil.WHITE_BISHOP,
			false + "," + MovementUtil.BLACK_BISHOP,
			false + "," + MovementUtil.WHITE_KNIGHT,
			false + "," + MovementUtil.BLACK_KNIGHT,
			false + "," + MovementUtil.WHITE_PAWN,
			false + "," + MovementUtil.BLACK_PAWN,
		}
	)
	@SuppressWarnings("javadoc")
	public void isQueenShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, MovementUtil.isQueen(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			false + "," + MovementUtil.WHITE_KING,
			false + "," + MovementUtil.BLACK_KING,
			false + "," + MovementUtil.WHITE_QUEEN,
			false + "," + MovementUtil.BLACK_QUEEN,
			true + "," + MovementUtil.WHITE_ROOK,
			true + "," + MovementUtil.BLACK_ROOK,
			false + "," + MovementUtil.WHITE_BISHOP,
			false + "," + MovementUtil.BLACK_BISHOP,
			false + "," + MovementUtil.WHITE_KNIGHT,
			false + "," + MovementUtil.BLACK_KNIGHT,
			false + "," + MovementUtil.WHITE_PAWN,
			false + "," + MovementUtil.BLACK_PAWN,
		}
	)
	@SuppressWarnings("javadoc")
	public void isRookShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, MovementUtil.isRook(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			false + "," + MovementUtil.WHITE_KING,
			false + "," + MovementUtil.BLACK_KING,
			false + "," + MovementUtil.WHITE_QUEEN,
			false + "," + MovementUtil.BLACK_QUEEN,
			false + "," + MovementUtil.WHITE_ROOK,
			false + "," + MovementUtil.BLACK_ROOK,
			true + "," + MovementUtil.WHITE_BISHOP,
			true + "," + MovementUtil.BLACK_BISHOP,
			false + "," + MovementUtil.WHITE_KNIGHT,
			false + "," + MovementUtil.BLACK_KNIGHT,
			false + "," + MovementUtil.WHITE_PAWN,
			false + "," + MovementUtil.BLACK_PAWN,
		}
	)
	@SuppressWarnings("javadoc")
	public void isTrueShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, MovementUtil.isBishop(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			false + "," + MovementUtil.WHITE_KING,
			false + "," + MovementUtil.BLACK_KING,
			false + "," + MovementUtil.WHITE_QUEEN,
			false + "," + MovementUtil.BLACK_QUEEN,
			false + "," + MovementUtil.WHITE_ROOK,
			false + "," + MovementUtil.BLACK_ROOK,
			true + "," + MovementUtil.WHITE_BISHOP,
			true + "," + MovementUtil.BLACK_BISHOP,
			false + "," + MovementUtil.WHITE_KNIGHT,
			false + "," + MovementUtil.BLACK_KNIGHT,
			false + "," + MovementUtil.WHITE_PAWN,
			false + "," + MovementUtil.BLACK_PAWN,
		}
	)
	@SuppressWarnings("javadoc")
	public void isBishopShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, MovementUtil.isBishop(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			false + "," + MovementUtil.WHITE_KING,
			false + "," + MovementUtil.BLACK_KING,
			false + "," + MovementUtil.WHITE_QUEEN,
			false + "," + MovementUtil.BLACK_QUEEN,
			false + "," + MovementUtil.WHITE_ROOK,
			false + "," + MovementUtil.BLACK_ROOK,
			false + "," + MovementUtil.WHITE_BISHOP,
			false + "," + MovementUtil.BLACK_BISHOP,
			true + "," + MovementUtil.WHITE_KNIGHT,
			true + "," + MovementUtil.BLACK_KNIGHT,
			false + "," + MovementUtil.WHITE_PAWN,
			false + "," + MovementUtil.BLACK_PAWN,
		}
	)
	@SuppressWarnings("javadoc")
	public void isKnightShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, MovementUtil.isKnight(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			false + "," + MovementUtil.WHITE_KING,
			false + "," + MovementUtil.BLACK_KING,
			false + "," + MovementUtil.WHITE_QUEEN,
			false + "," + MovementUtil.BLACK_QUEEN,
			false + "," + MovementUtil.WHITE_ROOK,
			false + "," + MovementUtil.BLACK_ROOK,
			false + "," + MovementUtil.WHITE_BISHOP,
			false + "," + MovementUtil.BLACK_BISHOP,
			false + "," + MovementUtil.WHITE_KNIGHT,
			false + "," + MovementUtil.BLACK_KNIGHT,
			true + "," + MovementUtil.WHITE_PAWN,
			true + "," + MovementUtil.BLACK_PAWN,
		}
	)
	@SuppressWarnings("javadoc")
	public void isPawnShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, MovementUtil.isPawn(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			true + "," + MovementUtil.WHITE_KING,
			false + "," + MovementUtil.BLACK_KING,
			false + "," + MovementUtil.WHITE_QUEEN,
			false + "," + MovementUtil.BLACK_QUEEN,
			false + "," + MovementUtil.WHITE_ROOK,
			false + "," + MovementUtil.BLACK_ROOK,
			false + "," + MovementUtil.WHITE_BISHOP,
			false + "," + MovementUtil.BLACK_BISHOP,
			false + "," + MovementUtil.WHITE_KNIGHT,
			false + "," + MovementUtil.BLACK_KNIGHT,
			false + "," + MovementUtil.WHITE_PAWN,
			false + "," + MovementUtil.BLACK_PAWN,
		}
	)
	@SuppressWarnings("javadoc")
	public void isWhiteKingShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, MovementUtil.isWhiteKing(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			false + "," + MovementUtil.WHITE_KING,
			false + "," + MovementUtil.BLACK_KING,
			true + "," + MovementUtil.WHITE_QUEEN,
			false + "," + MovementUtil.BLACK_QUEEN,
			false + "," + MovementUtil.WHITE_ROOK,
			false + "," + MovementUtil.BLACK_ROOK,
			false + "," + MovementUtil.WHITE_BISHOP,
			false + "," + MovementUtil.BLACK_BISHOP,
			false + "," + MovementUtil.WHITE_KNIGHT,
			false + "," + MovementUtil.BLACK_KNIGHT,
			false + "," + MovementUtil.WHITE_PAWN,
			false + "," + MovementUtil.BLACK_PAWN,
		}
	)
	@SuppressWarnings("javadoc")
	public void isWhiteQueenShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, MovementUtil.isWhiteQueen(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			false + "," + MovementUtil.WHITE_KING,
			false + "," + MovementUtil.BLACK_KING,
			false + "," + MovementUtil.WHITE_QUEEN,
			false + "," + MovementUtil.BLACK_QUEEN,
			true + "," + MovementUtil.WHITE_ROOK,
			false + "," + MovementUtil.BLACK_ROOK,
			false + "," + MovementUtil.WHITE_BISHOP,
			false + "," + MovementUtil.BLACK_BISHOP,
			false + "," + MovementUtil.WHITE_KNIGHT,
			false + "," + MovementUtil.BLACK_KNIGHT,
			false + "," + MovementUtil.WHITE_PAWN,
			false + "," + MovementUtil.BLACK_PAWN,
		}
	)
	@SuppressWarnings("javadoc")
	public void isWhiteRookShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, MovementUtil.isWhiteRook(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			false + "," + MovementUtil.WHITE_KING,
			false + "," + MovementUtil.BLACK_KING,
			false + "," + MovementUtil.WHITE_QUEEN,
			false + "," + MovementUtil.BLACK_QUEEN,
			false + "," + MovementUtil.WHITE_ROOK,
			false + "," + MovementUtil.BLACK_ROOK,
			true + "," + MovementUtil.WHITE_BISHOP,
			false + "," + MovementUtil.BLACK_BISHOP,
			false + "," + MovementUtil.WHITE_KNIGHT,
			false + "," + MovementUtil.BLACK_KNIGHT,
			false + "," + MovementUtil.WHITE_PAWN,
			false + "," + MovementUtil.BLACK_PAWN,
		}
	)
	@SuppressWarnings("javadoc")
	public void isWhiteBishopShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, MovementUtil.isWhiteBishop(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			false + "," + MovementUtil.WHITE_KING,
			false + "," + MovementUtil.BLACK_KING,
			false + "," + MovementUtil.WHITE_QUEEN,
			false + "," + MovementUtil.BLACK_QUEEN,
			false + "," + MovementUtil.WHITE_ROOK,
			false + "," + MovementUtil.BLACK_ROOK,
			false + "," + MovementUtil.WHITE_BISHOP,
			false + "," + MovementUtil.BLACK_BISHOP,
			true + "," + MovementUtil.WHITE_KNIGHT,
			false + "," + MovementUtil.BLACK_KNIGHT,
			false + "," + MovementUtil.WHITE_PAWN,
			false + "," + MovementUtil.BLACK_PAWN,
		}
	)
	@SuppressWarnings("javadoc")
	public void isWhiteKnightShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, MovementUtil.isWhiteKnight(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			false + "," + MovementUtil.WHITE_KING,
			false + "," + MovementUtil.BLACK_KING,
			false + "," + MovementUtil.WHITE_QUEEN,
			false + "," + MovementUtil.BLACK_QUEEN,
			false + "," + MovementUtil.WHITE_ROOK,
			false + "," + MovementUtil.BLACK_ROOK,
			false + "," + MovementUtil.WHITE_BISHOP,
			false + "," + MovementUtil.BLACK_BISHOP,
			false + "," + MovementUtil.WHITE_KNIGHT,
			false + "," + MovementUtil.BLACK_KNIGHT,
			true + "," + MovementUtil.WHITE_PAWN,
			false + "," + MovementUtil.BLACK_PAWN,
		}
	)
	@SuppressWarnings("javadoc")
	public void isWhitePawnShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, MovementUtil.isWhitePawn(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			false + "," + MovementUtil.WHITE_KING,
			true + "," + MovementUtil.BLACK_KING,
			false + "," + MovementUtil.WHITE_QUEEN,
			false + "," + MovementUtil.BLACK_QUEEN,
			false + "," + MovementUtil.WHITE_ROOK,
			false + "," + MovementUtil.BLACK_ROOK,
			false + "," + MovementUtil.WHITE_BISHOP,
			false + "," + MovementUtil.BLACK_BISHOP,
			false + "," + MovementUtil.WHITE_KNIGHT,
			false + "," + MovementUtil.BLACK_KNIGHT,
			false + "," + MovementUtil.WHITE_PAWN,
			false + "," + MovementUtil.BLACK_PAWN,
		}
	)
	@SuppressWarnings("javadoc")
	public void isBlackKingShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, MovementUtil.isBlackKing(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			false + "," + MovementUtil.WHITE_KING,
			false + "," + MovementUtil.BLACK_KING,
			false + "," + MovementUtil.WHITE_QUEEN,
			true + "," + MovementUtil.BLACK_QUEEN,
			false + "," + MovementUtil.WHITE_ROOK,
			false + "," + MovementUtil.BLACK_ROOK,
			false + "," + MovementUtil.WHITE_BISHOP,
			false + "," + MovementUtil.BLACK_BISHOP,
			false + "," + MovementUtil.WHITE_KNIGHT,
			false + "," + MovementUtil.BLACK_KNIGHT,
			false + "," + MovementUtil.WHITE_PAWN,
			false + "," + MovementUtil.BLACK_PAWN,
		}
	)
	@SuppressWarnings("javadoc")
	public void isBlackQueenShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, MovementUtil.isBlackQueen(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			false + "," + MovementUtil.WHITE_KING,
			false + "," + MovementUtil.BLACK_KING,
			false + "," + MovementUtil.WHITE_QUEEN,
			false + "," + MovementUtil.BLACK_QUEEN,
			false + "," + MovementUtil.WHITE_ROOK,
			true + "," + MovementUtil.BLACK_ROOK,
			false + "," + MovementUtil.WHITE_BISHOP,
			false + "," + MovementUtil.BLACK_BISHOP,
			false + "," + MovementUtil.WHITE_KNIGHT,
			false + "," + MovementUtil.BLACK_KNIGHT,
			false + "," + MovementUtil.WHITE_PAWN,
			false + "," + MovementUtil.BLACK_PAWN,
		}
	)
	@SuppressWarnings("javadoc")
	public void isBlackRookShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, MovementUtil.isBlackRook(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			false + "," + MovementUtil.WHITE_KING,
			false + "," + MovementUtil.BLACK_KING,
			false + "," + MovementUtil.WHITE_QUEEN,
			false + "," + MovementUtil.BLACK_QUEEN,
			false + "," + MovementUtil.WHITE_ROOK,
			false + "," + MovementUtil.BLACK_ROOK,
			false + "," + MovementUtil.WHITE_BISHOP,
			true + "," + MovementUtil.BLACK_BISHOP,
			false + "," + MovementUtil.WHITE_KNIGHT,
			false + "," + MovementUtil.BLACK_KNIGHT,
			false + "," + MovementUtil.WHITE_PAWN,
			false + "," + MovementUtil.BLACK_PAWN,
		}
	)
	@SuppressWarnings("javadoc")
	public void isBlackBishopShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, MovementUtil.isBlackBishop(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			false + "," + MovementUtil.WHITE_KING,
			false + "," + MovementUtil.BLACK_KING,
			false + "," + MovementUtil.WHITE_QUEEN,
			false + "," + MovementUtil.BLACK_QUEEN,
			false + "," + MovementUtil.WHITE_ROOK,
			false + "," + MovementUtil.BLACK_ROOK,
			false + "," + MovementUtil.WHITE_BISHOP,
			false + "," + MovementUtil.BLACK_BISHOP,
			false + "," + MovementUtil.WHITE_KNIGHT,
			true + "," + MovementUtil.BLACK_KNIGHT,
			false + "," + MovementUtil.WHITE_PAWN,
			false + "," + MovementUtil.BLACK_PAWN,
		}
	)
	@SuppressWarnings("javadoc")
	public void isBlackKnightShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, MovementUtil.isBlackKnight(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			false + "," + MovementUtil.WHITE_KING,
			false + "," + MovementUtil.BLACK_KING,
			false + "," + MovementUtil.WHITE_QUEEN,
			false + "," + MovementUtil.BLACK_QUEEN,
			false + "," + MovementUtil.WHITE_ROOK,
			false + "," + MovementUtil.BLACK_ROOK,
			false + "," + MovementUtil.WHITE_BISHOP,
			false + "," + MovementUtil.BLACK_BISHOP,
			false + "," + MovementUtil.WHITE_KNIGHT,
			false + "," + MovementUtil.BLACK_KNIGHT,
			false + "," + MovementUtil.WHITE_PAWN,
			true + "," + MovementUtil.BLACK_PAWN,
		}
	)
	@SuppressWarnings("javadoc")
	public void isBlackPawnShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, MovementUtil.isBlackPawn(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			true + "," + MovementUtil.WHITE_KING,
			true + "," + MovementUtil.WHITE_QUEEN,
			true + "," + MovementUtil.WHITE_ROOK,
			true + "," + MovementUtil.WHITE_BISHOP,
			true + "," + MovementUtil.WHITE_KNIGHT,
			true + "," + MovementUtil.WHITE_PAWN,
			false + "," + MovementUtil.BLACK_KING,
			false + "," + MovementUtil.BLACK_QUEEN,
			false + "," + MovementUtil.BLACK_ROOK,
			false + "," + MovementUtil.BLACK_BISHOP,
			false + "," + MovementUtil.BLACK_KNIGHT,
			false + "," + MovementUtil.BLACK_PAWN,
		}
	)
	@SuppressWarnings("javadoc")
	public void isWhitePieceShouldReturnProperlyValue(boolean isWhite, byte pieceCode) {
		assertEquals(isWhite, MovementUtil.isWhitePiece(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			false + "," + MovementUtil.WHITE_KING,
			false + "," + MovementUtil.WHITE_QUEEN,
			false + "," + MovementUtil.WHITE_ROOK,
			false + "," + MovementUtil.WHITE_BISHOP,
			false + "," + MovementUtil.WHITE_KNIGHT,
			false + "," + MovementUtil.WHITE_PAWN,
			true + "," + MovementUtil.BLACK_KING,
			true + "," + MovementUtil.BLACK_QUEEN,
			true + "," + MovementUtil.BLACK_ROOK,
			true + "," + MovementUtil.BLACK_BISHOP,
			true + "," + MovementUtil.BLACK_KNIGHT,
			true + "," + MovementUtil.BLACK_PAWN,
		}
	)
	@SuppressWarnings("javadoc")
	public void isBlackPieceShouldReturnProperlyValue(boolean isBlack, byte pieceCode) {
		assertEquals(isBlack, MovementUtil.isBlackPiece(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			MovementUtil.WHITE + "," + MovementUtil.WHITE_KING,
			MovementUtil.WHITE + "," + MovementUtil.WHITE_QUEEN,
			MovementUtil.WHITE + "," + MovementUtil.WHITE_ROOK,
			MovementUtil.WHITE + "," + MovementUtil.WHITE_BISHOP,
			MovementUtil.WHITE + "," + MovementUtil.WHITE_KNIGHT,
			MovementUtil.WHITE + "," + MovementUtil.WHITE_PAWN,
			MovementUtil.BLACK + "," + MovementUtil.BLACK_KING,
			MovementUtil.BLACK + "," + MovementUtil.BLACK_QUEEN,
			MovementUtil.BLACK + "," + MovementUtil.BLACK_ROOK,
			MovementUtil.BLACK + "," + MovementUtil.BLACK_BISHOP,
			MovementUtil.BLACK + "," + MovementUtil.BLACK_KNIGHT,
			MovementUtil.BLACK + "," + MovementUtil.BLACK_PAWN,
		}
	)
	@SuppressWarnings("javadoc")
	public void getColorCodeShouldReturnProperlyCode(byte expectedColorcode, byte pieceCode) {
		assertEquals(expectedColorcode, MovementUtil.getPieceColor(pieceCode));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void getColorCodeShoulThrowIllegalArgumentExceptionWhenPieceCodeIsZero() {
		assertThrows(IllegalArgumentException.class, () -> MovementUtil.getPieceColor(0));
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			MovementUtil.WHITE_KING + "," + MovementUtil.KING + "," + MovementUtil.WHITE,
			MovementUtil.WHITE_QUEEN + "," + MovementUtil.QUEEN + "," + MovementUtil.WHITE,
			MovementUtil.WHITE_ROOK + "," + MovementUtil.ROOK + "," + MovementUtil.WHITE,
			MovementUtil.WHITE_BISHOP + "," + MovementUtil.BISHOP + "," + MovementUtil.WHITE,
			MovementUtil.WHITE_KNIGHT + "," + MovementUtil.KNIGHT + "," + MovementUtil.WHITE,
			MovementUtil.WHITE_PAWN + "," + MovementUtil.PAWN + "," + MovementUtil.WHITE,
			MovementUtil.BLACK_KING + "," + MovementUtil.KING + "," + MovementUtil.BLACK,
			MovementUtil.BLACK_QUEEN + "," + MovementUtil.QUEEN + "," + MovementUtil.BLACK,
			MovementUtil.BLACK_ROOK + "," + MovementUtil.ROOK + "," + MovementUtil.BLACK,
			MovementUtil.BLACK_BISHOP + "," + MovementUtil.BISHOP + "," + MovementUtil.BLACK,
			MovementUtil.BLACK_KNIGHT + "," + MovementUtil.KNIGHT + "," + MovementUtil.BLACK,
			MovementUtil.BLACK_PAWN + "," + MovementUtil.PAWN + "," + MovementUtil.BLACK
		}
	)
	@SuppressWarnings("javadoc")
	public void getPieceShouldReturnProperValue(byte expectedPieceCode, byte pieceType, byte color) {
		assertEquals(expectedPieceCode, MovementUtil.getPiece(pieceType, color));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testIsBlackColorShouldReturnTrueIfColorIsBlack() {
		assertTrue(
			MovementUtil.isBlackColor(MovementUtil.BLACK),
			"MovementUtil.isBlackColor should return true when the color is black"
		);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testIsBlackColorShouldReturnFalseIfColorIsNotBlack() {
		assertFalse(
			MovementUtil.isBlackColor(MovementUtil.WHITE),
			"MovementUtil.isBlackColor should return false when the color is not black"
		);
	}
}
