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
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Unit tests for the <code>BoardUtil</code> class.
 * 
 * @author Welyab Paula
 */
public class BoardUtilTest {
	
	@Test
	@SuppressWarnings("javadoc")
	public void opositeColorShouldReturnWhiteWhenGivenColorIsBlack() {
		assertEquals(BoardUtil.WHITE_COLOR_CODE, BoardUtil.oppositeColor(BoardUtil.BLACK_COLOR_CODE));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void opositeColorShouldReturnBlackWhenGivenColorIsWhite() {
		assertEquals(BoardUtil.BLACK_COLOR_CODE, BoardUtil.oppositeColor(BoardUtil.WHITE_COLOR_CODE));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void opositeColorShouldThrowIllegalArgument() {
		assertThrows(IllegalArgumentException.class, () -> {
			BoardUtil.oppositeColor(0);
		});
	}
	
	@ParameterizedTest
	@CsvSource({
			BoardUtil.WHITE_COLOR_LETTER + "," + BoardUtil.WHITE_COLOR_CODE,
			BoardUtil.BLACK_COLOR_LETTER + "," + BoardUtil.BLACK_COLOR_CODE
	})
	@SuppressWarnings("javadoc")
	public void colorCodeToLetterShouldReturnProperlyColorLetter(char expected, byte input) {
		assertEquals(expected, BoardUtil.colorCodeToLetter(input));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void colorCodeToLetterShouldThrowIllegalArgumentexceptionWhenColorCodeInvalid() {
		assertThrows(IllegalArgumentException.class, () -> {
			BoardUtil.colorCodeToLetter(0);
		});
	}
	
	@ParameterizedTest
	@CsvSource({
			BoardUtil.WHITE_COLOR_CODE + "," + BoardUtil.WHITE_COLOR_LETTER,
			BoardUtil.BLACK_COLOR_CODE + "," + BoardUtil.BLACK_COLOR_LETTER
	})
	@SuppressWarnings("javadoc")
	public void colorLetterToCodeShouldReturnProperlyColorCode(byte expected, char input) {
		assertEquals(expected, BoardUtil.colorLetterToCode(input));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void colorLetterToCodeShouldThrowIllegalArgumentexceptionWhenColorCodeInvalid() {
		assertThrows(IllegalArgumentException.class, () -> {
			BoardUtil.colorLetterToCode('x');
		});
	}
	
	@ParameterizedTest
	@CsvSource({
			BoardUtil.BLACK_KING_LETTER + "," + BoardUtil.BLACK_KING_CODE,
			BoardUtil.BLACK_QUEEN_LETTER + "," + BoardUtil.BLACK_QUEEN_CODE,
			BoardUtil.BLACK_ROOK_LETTER + "," + BoardUtil.BLACK_ROOK_CODE,
			BoardUtil.BLACK_BISHOP_LETTER + "," + BoardUtil.BLACK_BISHOP_CODE,
			BoardUtil.BLACK_KNIGHT_LETTER + "," + BoardUtil.BLACK_KNIGHT_CODE,
			BoardUtil.BLACK_PAWN_LETTER + "," + BoardUtil.BLACK_PAWN_CODE,
			BoardUtil.WHITE_KING_LETTER + "," + BoardUtil.WHITE_KING_CODE,
			BoardUtil.WHITE_QUEEN_LETTER + "," + BoardUtil.WHITE_QUEEN_CODE,
			BoardUtil.WHITE_ROOK_LETTER + "," + BoardUtil.WHITE_ROOK_CODE,
			BoardUtil.WHITE_BISHOP_LETTER + "," + BoardUtil.WHITE_BISHOP_CODE,
			BoardUtil.WHITE_KNIGHT_LETTER + "," + BoardUtil.WHITE_KNIGHT_CODE,
			BoardUtil.WHITE_PAWN_LETTER + "," + BoardUtil.WHITE_PAWN_CODE
	})
	@SuppressWarnings("javadoc")
	public void pieceCodeToLetterShouldReturnProperlyPieceLetter(char pieceLetter, byte pieceCode) {
		assertEquals(pieceLetter, BoardUtil.pieceCodeToLetter(pieceCode));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void pieceCodeToLetterShouldThrowIllegalArgumentExceptionWhenPieceCodeIsInvalid() {
		assertThrows(IllegalArgumentException.class, () -> {
			BoardUtil.pieceCodeToLetter(0);
		});
	}
	
	@ParameterizedTest
	@CsvSource({
			BoardUtil.BLACK_KING_CODE + "," + BoardUtil.BLACK_KING_LETTER,
			BoardUtil.BLACK_QUEEN_CODE + "," + BoardUtil.BLACK_QUEEN_LETTER,
			BoardUtil.BLACK_ROOK_CODE + "," + BoardUtil.BLACK_ROOK_LETTER,
			BoardUtil.BLACK_BISHOP_CODE + "," + BoardUtil.BLACK_BISHOP_LETTER,
			BoardUtil.BLACK_KNIGHT_CODE + "," + BoardUtil.BLACK_KNIGHT_LETTER,
			BoardUtil.BLACK_PAWN_CODE + "," + BoardUtil.BLACK_PAWN_LETTER,
			BoardUtil.WHITE_KING_CODE + "," + BoardUtil.WHITE_KING_LETTER,
			BoardUtil.WHITE_QUEEN_CODE + "," + BoardUtil.WHITE_QUEEN_LETTER,
			BoardUtil.WHITE_ROOK_CODE + "," + BoardUtil.WHITE_ROOK_LETTER,
			BoardUtil.WHITE_BISHOP_CODE + "," + BoardUtil.WHITE_BISHOP_LETTER,
			BoardUtil.WHITE_KNIGHT_CODE + "," + BoardUtil.WHITE_KNIGHT_LETTER,
			BoardUtil.WHITE_PAWN_CODE + "," + BoardUtil.WHITE_PAWN_LETTER
	})
	@SuppressWarnings("javadoc")
	public void pieceCharToCodeShouldReturnProperlyPieceCode(byte pieceCode, char pieceLetter) {
		assertEquals(pieceCode, BoardUtil.pieceLetterToCode(pieceLetter));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void pieceLetterToCodeShouldThrowIllegalArgumentExceptionWhenPieceLetterIsInvalid() {
		assertThrows(IllegalArgumentException.class, () -> {
			BoardUtil.pieceLetterToCode('x');
		});
	}
	
	@ParameterizedTest
	@CsvSource({
			true + "," + 0b11111111,
			false + "," + 0b00000000,
			true + "," + BoardUtil.WHITE_KING_SIDE_CASTLING_FLAG_MASK,
			false + "," + ~BoardUtil.WHITE_KING_SIDE_CASTLING_FLAG_MASK,
			false + "," + BoardUtil.WHITE_QUEEN_SIDE_CASTLING_FLAG_MASK,
			false + "," + BoardUtil.BLACK_KING_SIDE_CASTLING_FLAG_MASK,
			false + "," + BoardUtil.BLACK_QUEEN_SIDE_CASTLING_FLAG_MASK
	})
	@SuppressWarnings("javadoc")
	public void isWhiteKingSideCasltingShouldReturnProperlyValue(boolean expected, int castlingFlags) {
		assertEquals(expected, BoardUtil.isWhiteKingSideCaslting(castlingFlags));
	}
	
	@ParameterizedTest
	@CsvSource({
			true + "," + 0b11111111,
			false + "," + 0b00000000,
			true + "," + BoardUtil.WHITE_QUEEN_SIDE_CASTLING_FLAG_MASK,
			false + "," + ~BoardUtil.WHITE_QUEEN_SIDE_CASTLING_FLAG_MASK,
			false + "," + BoardUtil.WHITE_KING_SIDE_CASTLING_FLAG_MASK,
			false + "," + BoardUtil.BLACK_KING_SIDE_CASTLING_FLAG_MASK,
			false + "," + BoardUtil.BLACK_QUEEN_SIDE_CASTLING_FLAG_MASK
	})
	@SuppressWarnings("javadoc")
	public void isWhiteQueenSideCasltingShouldReturnProperlyValue(boolean expected, int castlingFlags) {
		assertEquals(expected, BoardUtil.isWhiteQueenSideCaslting(castlingFlags));
	}
	
	@ParameterizedTest
	@CsvSource({
			true + "," + 0b11111111,
			false + "," + 0b00000000,
			true + "," + BoardUtil.BLACK_KING_SIDE_CASTLING_FLAG_MASK,
			false + "," + ~BoardUtil.BLACK_KING_SIDE_CASTLING_FLAG_MASK,
			false + "," + BoardUtil.WHITE_KING_SIDE_CASTLING_FLAG_MASK,
			false + "," + BoardUtil.WHITE_QUEEN_SIDE_CASTLING_FLAG_MASK,
			false + "," + BoardUtil.BLACK_QUEEN_SIDE_CASTLING_FLAG_MASK
	})
	@SuppressWarnings("javadoc")
	public void isBlackKingSideCasltingShouldReturnProperlyValue(boolean expected, int castlingFlags) {
		assertEquals(expected, BoardUtil.isBlackKingSideCaslting(castlingFlags));
	}
	
	@ParameterizedTest
	@CsvSource({
			true + "," + 0b11111111,
			false + "," + 0b00000000,
			true + "," + BoardUtil.BLACK_QUEEN_SIDE_CASTLING_FLAG_MASK,
			false + "," + ~BoardUtil.BLACK_QUEEN_SIDE_CASTLING_FLAG_MASK,
			false + "," + BoardUtil.WHITE_KING_SIDE_CASTLING_FLAG_MASK,
			false + "," + BoardUtil.WHITE_QUEEN_SIDE_CASTLING_FLAG_MASK,
			false + "," + BoardUtil.BLACK_KING_SIDE_CASTLING_FLAG_MASK
	})
	@SuppressWarnings("javadoc")
	public void isBlackQueenSideCasltingShouldReturnProperlyValue(boolean expected, int castlingFlags) {
		assertEquals(expected, BoardUtil.isBlackQueenSideCaslting(castlingFlags));
	}
	
	@ParameterizedTest
	@CsvSource({
			0b1111 + "," + true + "," + true + "," + true + "," + true,
			0b0000 + "," + false + "," + false + "," + false + "," + false,
			0b1000 + "," + true + "," + false + "," + false + "," + false,
			0b0100 + "," + false + "," + true + "," + false + "," + false,
			0b0010 + "," + false + "," + false + "," + true + "," + false,
			0b0001 + "," + false + "," + false + "," + false + "," + true,
			0b1001 + "," + true + "," + false + "," + false + "," + true,
	})
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
			BoardUtil.muxCastlingFlags(
				isWhiteKingSideCastling,
				isWhiteQueenSideCastling,
				isBlackKingSideCastling,
				isBlackQueenSideCastling
			)
		);
	}
	
	@ParameterizedTest
	@CsvSource({
			true + "," + BoardUtil.WHITE_KING_CODE,
			true + "," + BoardUtil.BLACK_KING_CODE,
			false + "," + BoardUtil.WHITE_QUEEN_CODE,
			false + "," + BoardUtil.BLACK_QUEEN_CODE,
			false + "," + BoardUtil.WHITE_ROOK_CODE,
			false + "," + BoardUtil.BLACK_ROOK_CODE,
			false + "," + BoardUtil.WHITE_BISHOP_CODE,
			false + "," + BoardUtil.BLACK_BISHOP_CODE,
			false + "," + BoardUtil.WHITE_KNIGHT_CODE,
			false + "," + BoardUtil.BLACK_KNIGHT_CODE,
			false + "," + BoardUtil.WHITE_PAWN_CODE,
			false + "," + BoardUtil.BLACK_PAWN_CODE,
	})
	@SuppressWarnings("javadoc")
	public void isKingShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, BoardUtil.isKing(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource({
			false + "," + BoardUtil.WHITE_KING_CODE,
			false + "," + BoardUtil.BLACK_KING_CODE,
			true + "," + BoardUtil.WHITE_QUEEN_CODE,
			true + "," + BoardUtil.BLACK_QUEEN_CODE,
			false + "," + BoardUtil.WHITE_ROOK_CODE,
			false + "," + BoardUtil.BLACK_ROOK_CODE,
			false + "," + BoardUtil.WHITE_BISHOP_CODE,
			false + "," + BoardUtil.BLACK_BISHOP_CODE,
			false + "," + BoardUtil.WHITE_KNIGHT_CODE,
			false + "," + BoardUtil.BLACK_KNIGHT_CODE,
			false + "," + BoardUtil.WHITE_PAWN_CODE,
			false + "," + BoardUtil.BLACK_PAWN_CODE,
	})
	@SuppressWarnings("javadoc")
	public void isQueenShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, BoardUtil.isQueen(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource({
			false + "," + BoardUtil.WHITE_KING_CODE,
			false + "," + BoardUtil.BLACK_KING_CODE,
			false + "," + BoardUtil.WHITE_QUEEN_CODE,
			false + "," + BoardUtil.BLACK_QUEEN_CODE,
			true + "," + BoardUtil.WHITE_ROOK_CODE,
			true + "," + BoardUtil.BLACK_ROOK_CODE,
			false + "," + BoardUtil.WHITE_BISHOP_CODE,
			false + "," + BoardUtil.BLACK_BISHOP_CODE,
			false + "," + BoardUtil.WHITE_KNIGHT_CODE,
			false + "," + BoardUtil.BLACK_KNIGHT_CODE,
			false + "," + BoardUtil.WHITE_PAWN_CODE,
			false + "," + BoardUtil.BLACK_PAWN_CODE,
	})
	@SuppressWarnings("javadoc")
	public void isRookShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, BoardUtil.isRook(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource({
			false + "," + BoardUtil.WHITE_KING_CODE,
			false + "," + BoardUtil.BLACK_KING_CODE,
			false + "," + BoardUtil.WHITE_QUEEN_CODE,
			false + "," + BoardUtil.BLACK_QUEEN_CODE,
			false + "," + BoardUtil.WHITE_ROOK_CODE,
			false + "," + BoardUtil.BLACK_ROOK_CODE,
			true + "," + BoardUtil.WHITE_BISHOP_CODE,
			true + "," + BoardUtil.BLACK_BISHOP_CODE,
			false + "," + BoardUtil.WHITE_KNIGHT_CODE,
			false + "," + BoardUtil.BLACK_KNIGHT_CODE,
			false + "," + BoardUtil.WHITE_PAWN_CODE,
			false + "," + BoardUtil.BLACK_PAWN_CODE,
	})
	@SuppressWarnings("javadoc")
	public void isTrueShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, BoardUtil.isBishop(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource({
			false + "," + BoardUtil.WHITE_KING_CODE,
			false + "," + BoardUtil.BLACK_KING_CODE,
			false + "," + BoardUtil.WHITE_QUEEN_CODE,
			false + "," + BoardUtil.BLACK_QUEEN_CODE,
			false + "," + BoardUtil.WHITE_ROOK_CODE,
			false + "," + BoardUtil.BLACK_ROOK_CODE,
			true + "," + BoardUtil.WHITE_BISHOP_CODE,
			true + "," + BoardUtil.BLACK_BISHOP_CODE,
			false + "," + BoardUtil.WHITE_KNIGHT_CODE,
			false + "," + BoardUtil.BLACK_KNIGHT_CODE,
			false + "," + BoardUtil.WHITE_PAWN_CODE,
			false + "," + BoardUtil.BLACK_PAWN_CODE,
	})
	@SuppressWarnings("javadoc")
	public void isBishopShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, BoardUtil.isBishop(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource({
			false + "," + BoardUtil.WHITE_KING_CODE,
			false + "," + BoardUtil.BLACK_KING_CODE,
			false + "," + BoardUtil.WHITE_QUEEN_CODE,
			false + "," + BoardUtil.BLACK_QUEEN_CODE,
			false + "," + BoardUtil.WHITE_ROOK_CODE,
			false + "," + BoardUtil.BLACK_ROOK_CODE,
			false + "," + BoardUtil.WHITE_BISHOP_CODE,
			false + "," + BoardUtil.BLACK_BISHOP_CODE,
			true + "," + BoardUtil.WHITE_KNIGHT_CODE,
			true + "," + BoardUtil.BLACK_KNIGHT_CODE,
			false + "," + BoardUtil.WHITE_PAWN_CODE,
			false + "," + BoardUtil.BLACK_PAWN_CODE,
	})
	@SuppressWarnings("javadoc")
	public void isKnightShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, BoardUtil.isKnight(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource({
			false + "," + BoardUtil.WHITE_KING_CODE,
			false + "," + BoardUtil.BLACK_KING_CODE,
			false + "," + BoardUtil.WHITE_QUEEN_CODE,
			false + "," + BoardUtil.BLACK_QUEEN_CODE,
			false + "," + BoardUtil.WHITE_ROOK_CODE,
			false + "," + BoardUtil.BLACK_ROOK_CODE,
			false + "," + BoardUtil.WHITE_BISHOP_CODE,
			false + "," + BoardUtil.BLACK_BISHOP_CODE,
			false + "," + BoardUtil.WHITE_KNIGHT_CODE,
			false + "," + BoardUtil.BLACK_KNIGHT_CODE,
			true + "," + BoardUtil.WHITE_PAWN_CODE,
			true + "," + BoardUtil.BLACK_PAWN_CODE,
	})
	@SuppressWarnings("javadoc")
	public void isPawnShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, BoardUtil.isPawn(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource({
			true + "," + BoardUtil.WHITE_KING_CODE,
			false + "," + BoardUtil.BLACK_KING_CODE,
			false + "," + BoardUtil.WHITE_QUEEN_CODE,
			false + "," + BoardUtil.BLACK_QUEEN_CODE,
			false + "," + BoardUtil.WHITE_ROOK_CODE,
			false + "," + BoardUtil.BLACK_ROOK_CODE,
			false + "," + BoardUtil.WHITE_BISHOP_CODE,
			false + "," + BoardUtil.BLACK_BISHOP_CODE,
			false + "," + BoardUtil.WHITE_KNIGHT_CODE,
			false + "," + BoardUtil.BLACK_KNIGHT_CODE,
			false + "," + BoardUtil.WHITE_PAWN_CODE,
			false + "," + BoardUtil.BLACK_PAWN_CODE,
	})
	@SuppressWarnings("javadoc")
	public void isWhiteKingShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, BoardUtil.isWhiteKing(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource({
			false + "," + BoardUtil.WHITE_KING_CODE,
			false + "," + BoardUtil.BLACK_KING_CODE,
			true + "," + BoardUtil.WHITE_QUEEN_CODE,
			false + "," + BoardUtil.BLACK_QUEEN_CODE,
			false + "," + BoardUtil.WHITE_ROOK_CODE,
			false + "," + BoardUtil.BLACK_ROOK_CODE,
			false + "," + BoardUtil.WHITE_BISHOP_CODE,
			false + "," + BoardUtil.BLACK_BISHOP_CODE,
			false + "," + BoardUtil.WHITE_KNIGHT_CODE,
			false + "," + BoardUtil.BLACK_KNIGHT_CODE,
			false + "," + BoardUtil.WHITE_PAWN_CODE,
			false + "," + BoardUtil.BLACK_PAWN_CODE,
	})
	@SuppressWarnings("javadoc")
	public void isWhiteQueenShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, BoardUtil.isWhiteQueen(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource({
			false + "," + BoardUtil.WHITE_KING_CODE,
			false + "," + BoardUtil.BLACK_KING_CODE,
			false + "," + BoardUtil.WHITE_QUEEN_CODE,
			false + "," + BoardUtil.BLACK_QUEEN_CODE,
			true + "," + BoardUtil.WHITE_ROOK_CODE,
			false + "," + BoardUtil.BLACK_ROOK_CODE,
			false + "," + BoardUtil.WHITE_BISHOP_CODE,
			false + "," + BoardUtil.BLACK_BISHOP_CODE,
			false + "," + BoardUtil.WHITE_KNIGHT_CODE,
			false + "," + BoardUtil.BLACK_KNIGHT_CODE,
			false + "," + BoardUtil.WHITE_PAWN_CODE,
			false + "," + BoardUtil.BLACK_PAWN_CODE,
	})
	@SuppressWarnings("javadoc")
	public void isWhiteRookShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, BoardUtil.isWhiteRook(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource({
			false + "," + BoardUtil.WHITE_KING_CODE,
			false + "," + BoardUtil.BLACK_KING_CODE,
			false + "," + BoardUtil.WHITE_QUEEN_CODE,
			false + "," + BoardUtil.BLACK_QUEEN_CODE,
			false + "," + BoardUtil.WHITE_ROOK_CODE,
			false + "," + BoardUtil.BLACK_ROOK_CODE,
			true + "," + BoardUtil.WHITE_BISHOP_CODE,
			false + "," + BoardUtil.BLACK_BISHOP_CODE,
			false + "," + BoardUtil.WHITE_KNIGHT_CODE,
			false + "," + BoardUtil.BLACK_KNIGHT_CODE,
			false + "," + BoardUtil.WHITE_PAWN_CODE,
			false + "," + BoardUtil.BLACK_PAWN_CODE,
	})
	@SuppressWarnings("javadoc")
	public void isWhiteBishopShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, BoardUtil.isWhiteBishop(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource({
			false + "," + BoardUtil.WHITE_KING_CODE,
			false + "," + BoardUtil.BLACK_KING_CODE,
			false + "," + BoardUtil.WHITE_QUEEN_CODE,
			false + "," + BoardUtil.BLACK_QUEEN_CODE,
			false + "," + BoardUtil.WHITE_ROOK_CODE,
			false + "," + BoardUtil.BLACK_ROOK_CODE,
			false + "," + BoardUtil.WHITE_BISHOP_CODE,
			false + "," + BoardUtil.BLACK_BISHOP_CODE,
			true + "," + BoardUtil.WHITE_KNIGHT_CODE,
			false + "," + BoardUtil.BLACK_KNIGHT_CODE,
			false + "," + BoardUtil.WHITE_PAWN_CODE,
			false + "," + BoardUtil.BLACK_PAWN_CODE,
	})
	@SuppressWarnings("javadoc")
	public void isWhiteKnightShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, BoardUtil.isWhiteKnight(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource({
			false + "," + BoardUtil.WHITE_KING_CODE,
			false + "," + BoardUtil.BLACK_KING_CODE,
			false + "," + BoardUtil.WHITE_QUEEN_CODE,
			false + "," + BoardUtil.BLACK_QUEEN_CODE,
			false + "," + BoardUtil.WHITE_ROOK_CODE,
			false + "," + BoardUtil.BLACK_ROOK_CODE,
			false + "," + BoardUtil.WHITE_BISHOP_CODE,
			false + "," + BoardUtil.BLACK_BISHOP_CODE,
			false + "," + BoardUtil.WHITE_KNIGHT_CODE,
			false + "," + BoardUtil.BLACK_KNIGHT_CODE,
			true + "," + BoardUtil.WHITE_PAWN_CODE,
			false + "," + BoardUtil.BLACK_PAWN_CODE,
	})
	@SuppressWarnings("javadoc")
	public void isWhitePawnShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, BoardUtil.isWhitePawn(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource({
			false + "," + BoardUtil.WHITE_KING_CODE,
			true + "," + BoardUtil.BLACK_KING_CODE,
			false + "," + BoardUtil.WHITE_QUEEN_CODE,
			false + "," + BoardUtil.BLACK_QUEEN_CODE,
			false + "," + BoardUtil.WHITE_ROOK_CODE,
			false + "," + BoardUtil.BLACK_ROOK_CODE,
			false + "," + BoardUtil.WHITE_BISHOP_CODE,
			false + "," + BoardUtil.BLACK_BISHOP_CODE,
			false + "," + BoardUtil.WHITE_KNIGHT_CODE,
			false + "," + BoardUtil.BLACK_KNIGHT_CODE,
			false + "," + BoardUtil.WHITE_PAWN_CODE,
			false + "," + BoardUtil.BLACK_PAWN_CODE,
	})
	@SuppressWarnings("javadoc")
	public void isBlackKingShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, BoardUtil.isBlackKing(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource({
			false + "," + BoardUtil.WHITE_KING_CODE,
			false + "," + BoardUtil.BLACK_KING_CODE,
			false + "," + BoardUtil.WHITE_QUEEN_CODE,
			true + "," + BoardUtil.BLACK_QUEEN_CODE,
			false + "," + BoardUtil.WHITE_ROOK_CODE,
			false + "," + BoardUtil.BLACK_ROOK_CODE,
			false + "," + BoardUtil.WHITE_BISHOP_CODE,
			false + "," + BoardUtil.BLACK_BISHOP_CODE,
			false + "," + BoardUtil.WHITE_KNIGHT_CODE,
			false + "," + BoardUtil.BLACK_KNIGHT_CODE,
			false + "," + BoardUtil.WHITE_PAWN_CODE,
			false + "," + BoardUtil.BLACK_PAWN_CODE,
	})
	@SuppressWarnings("javadoc")
	public void isBlackQueenShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, BoardUtil.isBlackQueen(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource({
			false + "," + BoardUtil.WHITE_KING_CODE,
			false + "," + BoardUtil.BLACK_KING_CODE,
			false + "," + BoardUtil.WHITE_QUEEN_CODE,
			false + "," + BoardUtil.BLACK_QUEEN_CODE,
			false + "," + BoardUtil.WHITE_ROOK_CODE,
			true + "," + BoardUtil.BLACK_ROOK_CODE,
			false + "," + BoardUtil.WHITE_BISHOP_CODE,
			false + "," + BoardUtil.BLACK_BISHOP_CODE,
			false + "," + BoardUtil.WHITE_KNIGHT_CODE,
			false + "," + BoardUtil.BLACK_KNIGHT_CODE,
			false + "," + BoardUtil.WHITE_PAWN_CODE,
			false + "," + BoardUtil.BLACK_PAWN_CODE,
	})
	@SuppressWarnings("javadoc")
	public void isBlackRookShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, BoardUtil.isBlackRook(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource({
			false + "," + BoardUtil.WHITE_KING_CODE,
			false + "," + BoardUtil.BLACK_KING_CODE,
			false + "," + BoardUtil.WHITE_QUEEN_CODE,
			false + "," + BoardUtil.BLACK_QUEEN_CODE,
			false + "," + BoardUtil.WHITE_ROOK_CODE,
			false + "," + BoardUtil.BLACK_ROOK_CODE,
			false + "," + BoardUtil.WHITE_BISHOP_CODE,
			true + "," + BoardUtil.BLACK_BISHOP_CODE,
			false + "," + BoardUtil.WHITE_KNIGHT_CODE,
			false + "," + BoardUtil.BLACK_KNIGHT_CODE,
			false + "," + BoardUtil.WHITE_PAWN_CODE,
			false + "," + BoardUtil.BLACK_PAWN_CODE,
	})
	@SuppressWarnings("javadoc")
	public void isBlackBishopShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, BoardUtil.isBlackBishop(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource({
			false + "," + BoardUtil.WHITE_KING_CODE,
			false + "," + BoardUtil.BLACK_KING_CODE,
			false + "," + BoardUtil.WHITE_QUEEN_CODE,
			false + "," + BoardUtil.BLACK_QUEEN_CODE,
			false + "," + BoardUtil.WHITE_ROOK_CODE,
			false + "," + BoardUtil.BLACK_ROOK_CODE,
			false + "," + BoardUtil.WHITE_BISHOP_CODE,
			false + "," + BoardUtil.BLACK_BISHOP_CODE,
			false + "," + BoardUtil.WHITE_KNIGHT_CODE,
			true + "," + BoardUtil.BLACK_KNIGHT_CODE,
			false + "," + BoardUtil.WHITE_PAWN_CODE,
			false + "," + BoardUtil.BLACK_PAWN_CODE,
	})
	@SuppressWarnings("javadoc")
	public void isBlackKnightShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, BoardUtil.isBlackKnight(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource({
			false + "," + BoardUtil.WHITE_KING_CODE,
			false + "," + BoardUtil.BLACK_KING_CODE,
			false + "," + BoardUtil.WHITE_QUEEN_CODE,
			false + "," + BoardUtil.BLACK_QUEEN_CODE,
			false + "," + BoardUtil.WHITE_ROOK_CODE,
			false + "," + BoardUtil.BLACK_ROOK_CODE,
			false + "," + BoardUtil.WHITE_BISHOP_CODE,
			false + "," + BoardUtil.BLACK_BISHOP_CODE,
			false + "," + BoardUtil.WHITE_KNIGHT_CODE,
			false + "," + BoardUtil.BLACK_KNIGHT_CODE,
			false + "," + BoardUtil.WHITE_PAWN_CODE,
			true + "," + BoardUtil.BLACK_PAWN_CODE,
	})
	@SuppressWarnings("javadoc")
	public void isBlackPawnShouldReturnProperlyValue(boolean isKing, byte pieceCode) {
		assertEquals(isKing, BoardUtil.isBlackPawn(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource({
			true + "," + BoardUtil.WHITE_KING_CODE,
			true + "," + BoardUtil.WHITE_QUEEN_CODE,
			true + "," + BoardUtil.WHITE_ROOK_CODE,
			true + "," + BoardUtil.WHITE_BISHOP_CODE,
			true + "," + BoardUtil.WHITE_KNIGHT_CODE,
			true + "," + BoardUtil.WHITE_PAWN_CODE,
			false + "," + BoardUtil.BLACK_KING_CODE,
			false + "," + BoardUtil.BLACK_QUEEN_CODE,
			false + "," + BoardUtil.BLACK_ROOK_CODE,
			false + "," + BoardUtil.BLACK_BISHOP_CODE,
			false + "," + BoardUtil.BLACK_KNIGHT_CODE,
			false + "," + BoardUtil.BLACK_PAWN_CODE,
	})
	@SuppressWarnings("javadoc")
	public void isWhitePieceShouldReturnProperlyValue(boolean isWhite, byte pieceCode) {
		assertEquals(isWhite, BoardUtil.isWhitePiece(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource({
			false + "," + BoardUtil.WHITE_KING_CODE,
			false + "," + BoardUtil.WHITE_QUEEN_CODE,
			false + "," + BoardUtil.WHITE_ROOK_CODE,
			false + "," + BoardUtil.WHITE_BISHOP_CODE,
			false + "," + BoardUtil.WHITE_KNIGHT_CODE,
			false + "," + BoardUtil.WHITE_PAWN_CODE,
			true + "," + BoardUtil.BLACK_KING_CODE,
			true + "," + BoardUtil.BLACK_QUEEN_CODE,
			true + "," + BoardUtil.BLACK_ROOK_CODE,
			true + "," + BoardUtil.BLACK_BISHOP_CODE,
			true + "," + BoardUtil.BLACK_KNIGHT_CODE,
			true + "," + BoardUtil.BLACK_PAWN_CODE,
	})
	@SuppressWarnings("javadoc")
	public void isBlackPieceShouldReturnProperlyValue(boolean isBlack, byte pieceCode) {
		assertEquals(isBlack, BoardUtil.isBlackPiece(pieceCode));
	}
	
	@ParameterizedTest
	@CsvSource({
			BoardUtil.WHITE_COLOR_CODE + "," + BoardUtil.WHITE_KING_CODE,
			BoardUtil.WHITE_COLOR_CODE + "," + BoardUtil.WHITE_QUEEN_CODE,
			BoardUtil.WHITE_COLOR_CODE + "," + BoardUtil.WHITE_ROOK_CODE,
			BoardUtil.WHITE_COLOR_CODE + "," + BoardUtil.WHITE_BISHOP_CODE,
			BoardUtil.WHITE_COLOR_CODE + "," + BoardUtil.WHITE_KNIGHT_CODE,
			BoardUtil.WHITE_COLOR_CODE + "," + BoardUtil.WHITE_PAWN_CODE,
			BoardUtil.BLACK_COLOR_CODE + "," + BoardUtil.BLACK_KING_CODE,
			BoardUtil.BLACK_COLOR_CODE + "," + BoardUtil.BLACK_QUEEN_CODE,
			BoardUtil.BLACK_COLOR_CODE + "," + BoardUtil.BLACK_ROOK_CODE,
			BoardUtil.BLACK_COLOR_CODE + "," + BoardUtil.BLACK_BISHOP_CODE,
			BoardUtil.BLACK_COLOR_CODE + "," + BoardUtil.BLACK_KNIGHT_CODE,
			BoardUtil.BLACK_COLOR_CODE + "," + BoardUtil.BLACK_PAWN_CODE,
	})
	@SuppressWarnings("javadoc")
	public void getColorCodeShouldReturnProperlyCode(byte expectedColorcode, byte pieceCode) {
		assertEquals(expectedColorcode, BoardUtil.getColorCode(pieceCode));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void getColorCodeShoulThrowIllegalArgumentExceptionWhenPieceCodeIsZero() {
		assertThrows(IllegalArgumentException.class, () -> BoardUtil.getColorCode(0));
	}
}
