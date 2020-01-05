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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class PieceTest {
	
	@ParameterizedTest
	@ValueSource(
		strings = {
			"WHITE_KING",
			"WHITE_QUEEN",
			"WHITE_ROOK",
			"WHITE_BISHOP",
			"WHITE_KNIGHT",
			"WHITE_PAWN",
			"BLACK_KING",
			"BLACK_QUEEN",
			"BLACK_ROOK",
			"BLACK_BISHOP",
			"BLACK_KNIGHT",
			"BLACK_PAWN"
		}
	)
		
	public void getValueShouldReturnAProductOfColorSignAndTypeValue(Piece piece) {
		assertEquals(piece.getColor().getValue() * piece.getType().getValue(), piece.getValue());
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			"WHITE_KING,WHITE",
			"WHITE_QUEEN,WHITE",
			"WHITE_ROOK,WHITE",
			"WHITE_BISHOP,WHITE",
			"WHITE_KNIGHT,WHITE",
			"WHITE_PAWN,WHITE",
			"BLACK_KING,BLACK",
			"BLACK_QUEEN,BLACK",
			"BLACK_ROOK,BLACK",
			"BLACK_BISHOP,BLACK",
			"BLACK_KNIGHT,BLACK",
			"BLACK_PAWN,BLACK"
		}
	)
	public void getColorShouldReturnProperlyColor(Piece piece, Color expectedColor) {
		assertEquals(expectedColor, piece.getColor());
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			"WHITE_KING,KING",
			"WHITE_QUEEN,QUEEN",
			"WHITE_ROOK,ROOK",
			"WHITE_BISHOP,BISHOP",
			"WHITE_KNIGHT,KNIGHT",
			"WHITE_PAWN,PAWN",
			"BLACK_KING,KING",
			"BLACK_QUEEN,QUEEN",
			"BLACK_ROOK,ROOK",
			"BLACK_BISHOP,BISHOP",
			"BLACK_KNIGHT,KNIGHT",
			"BLACK_PAWN,PAWN"
		}
	)
	public void getTypeShouldReturnProperlyPieceType(Piece piece, PieceType expectedPieceType) {
		assertEquals(expectedPieceType, piece.getType());
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			"WHITE_KING,true",
			"WHITE_QUEEN,false",
			"WHITE_ROOK,false",
			"WHITE_BISHOP,false",
			"WHITE_KNIGHT,false",
			"WHITE_PAWN,false",
			"BLACK_KING,false",
			"BLACK_QUEEN,false",
			"BLACK_ROOK,false",
			"BLACK_BISHOP,false",
			"BLACK_KNIGHT,false",
			"BLACK_PAWN,false"
		}
	)
	public void isWhiteKingShouldReturnProperlyValue(Piece piece, boolean expectedValue) {
		assertEquals(expectedValue, piece.isWhiteKing());
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			"WHITE_KING,false",
			"WHITE_QUEEN,true",
			"WHITE_ROOK,false",
			"WHITE_BISHOP,false",
			"WHITE_KNIGHT,false",
			"WHITE_PAWN,false",
			"BLACK_KING,false",
			"BLACK_QUEEN,false",
			"BLACK_ROOK,false",
			"BLACK_BISHOP,false",
			"BLACK_KNIGHT,false",
			"BLACK_PAWN,false"
		}
	)
	public void isWhiteQueenShouldReturnProperlyValue(Piece piece, boolean expectedValue) {
		assertEquals(expectedValue, piece.isWhiteQueen());
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			"WHITE_KING,false",
			"WHITE_QUEEN,false",
			"WHITE_ROOK,true",
			"WHITE_BISHOP,false",
			"WHITE_KNIGHT,false",
			"WHITE_PAWN,false",
			"BLACK_KING,false",
			"BLACK_QUEEN,false",
			"BLACK_ROOK,false",
			"BLACK_BISHOP,false",
			"BLACK_KNIGHT,false",
			"BLACK_PAWN,false"
		}
	)
	public void isWhiteRookShouldReturnProperlyValue(Piece piece, boolean expectedValue) {
		assertEquals(expectedValue, piece.isWhiteRook());
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			"WHITE_KING,false",
			"WHITE_QUEEN,false",
			"WHITE_ROOK,false",
			"WHITE_BISHOP,true",
			"WHITE_KNIGHT,false",
			"WHITE_PAWN,false",
			"BLACK_KING,false",
			"BLACK_QUEEN,false",
			"BLACK_ROOK,false",
			"BLACK_BISHOP,false",
			"BLACK_KNIGHT,false",
			"BLACK_PAWN,false"
		}
	)
	public void isWhiteBishopShouldReturnProperlyValue(Piece piece, boolean expectedValue) {
		assertEquals(expectedValue, piece.isWhiteBishop());
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			"WHITE_KING,false",
			"WHITE_QUEEN,false",
			"WHITE_ROOK,false",
			"WHITE_BISHOP,false",
			"WHITE_KNIGHT,true",
			"WHITE_PAWN,false",
			"BLACK_KING,false",
			"BLACK_QUEEN,false",
			"BLACK_ROOK,false",
			"BLACK_BISHOP,false",
			"BLACK_KNIGHT,false",
			"BLACK_PAWN,false"
		}
	)
	public void isWhiteKnightShouldReturnProperlyValue(Piece piece, boolean expectedValue) {
		assertEquals(expectedValue, piece.isWhiteKnight());
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			"WHITE_KING,false",
			"WHITE_QUEEN,false",
			"WHITE_ROOK,false",
			"WHITE_BISHOP,false",
			"WHITE_KNIGHT,false",
			"WHITE_PAWN,true",
			"BLACK_KING,false",
			"BLACK_QUEEN,false",
			"BLACK_ROOK,false",
			"BLACK_BISHOP,false",
			"BLACK_KNIGHT,false",
			"BLACK_PAWN,false"
		}
	)
	public void isWhitePawnShouldReturnProperlyValue(Piece piece, boolean expectedValue) {
		assertEquals(expectedValue, piece.isWhitePawn());
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			"WHITE_KING,false",
			"WHITE_QUEEN,false",
			"WHITE_ROOK,false",
			"WHITE_BISHOP,false",
			"WHITE_KNIGHT,false",
			"WHITE_PAWN,false",
			"BLACK_KING,true",
			"BLACK_QUEEN,false",
			"BLACK_ROOK,false",
			"BLACK_BISHOP,false",
			"BLACK_KNIGHT,false",
			"BLACK_PAWN,false"
		}
	)
	public void isBlackKingShouldReturnProperlyValue(Piece piece, boolean expectedValue) {
		assertEquals(expectedValue, piece.isBlackKing());
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			"WHITE_KING,false",
			"WHITE_QUEEN,false",
			"WHITE_ROOK,false",
			"WHITE_BISHOP,false",
			"WHITE_KNIGHT,false",
			"WHITE_PAWN,false",
			"BLACK_KING,false",
			"BLACK_QUEEN,true",
			"BLACK_ROOK,false",
			"BLACK_BISHOP,false",
			"BLACK_KNIGHT,false",
			"BLACK_PAWN,false"
		}
	)
	public void isBlackQueenShouldReturnProperlyValue(Piece piece, boolean expectedValue) {
		assertEquals(expectedValue, piece.isBlackQueen());
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			"WHITE_KING,false",
			"WHITE_QUEEN,false",
			"WHITE_ROOK,false",
			"WHITE_BISHOP,false",
			"WHITE_KNIGHT,false",
			"WHITE_PAWN,false",
			"BLACK_KING,false",
			"BLACK_QUEEN,false",
			"BLACK_ROOK,true",
			"BLACK_BISHOP,false",
			"BLACK_KNIGHT,false",
			"BLACK_PAWN,false"
		}
	)
	public void isBlackRookShouldReturnProperlyValue(Piece piece, boolean expectedValue) {
		assertEquals(expectedValue, piece.isBlackRook());
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			"WHITE_KING,false",
			"WHITE_QUEEN,false",
			"WHITE_ROOK,false",
			"WHITE_BISHOP,false",
			"WHITE_KNIGHT,false",
			"WHITE_PAWN,false",
			"BLACK_KING,false",
			"BLACK_QUEEN,false",
			"BLACK_ROOK,false",
			"BLACK_BISHOP,true",
			"BLACK_KNIGHT,false",
			"BLACK_PAWN,false"
		}
	)
	public void isBlackBishopShouldReturnProperlyValue(Piece piece, boolean expectedValue) {
		assertEquals(expectedValue, piece.isBlackBishop());
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			"WHITE_KING,false",
			"WHITE_QUEEN,false",
			"WHITE_ROOK,false",
			"WHITE_BISHOP,false",
			"WHITE_KNIGHT,false",
			"WHITE_PAWN,false",
			"BLACK_KING,false",
			"BLACK_QUEEN,false",
			"BLACK_ROOK,false",
			"BLACK_BISHOP,false",
			"BLACK_KNIGHT,true",
			"BLACK_PAWN,false"
		}
	)
	public void isBlackKnightShouldReturnProperlyValue(Piece piece, boolean expectedValue) {
		assertEquals(expectedValue, piece.isBlackKnight());
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			"WHITE_KING,false",
			"WHITE_QUEEN,false",
			"WHITE_ROOK,false",
			"WHITE_BISHOP,false",
			"WHITE_KNIGHT,false",
			"WHITE_PAWN,false",
			"BLACK_KING,false",
			"BLACK_QUEEN,false",
			"BLACK_ROOK,false",
			"BLACK_BISHOP,false",
			"BLACK_KNIGHT,false",
			"BLACK_PAWN,true"
		}
	)
	public void isBlackPawnShouldReturnProperlyValue(Piece piece, boolean expectedValue) {
		assertEquals(expectedValue, piece.isBlackPawn());
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			"K,WHITE_KING",
			"Q,WHITE_QUEEN",
			"R,WHITE_ROOK",
			"B,WHITE_BISHOP",
			"N,WHITE_KNIGHT",
			"P,WHITE_PAWN",
			"k,BLACK_KING",
			"q,BLACK_QUEEN",
			"r,BLACK_ROOK",
			"b,BLACK_BISHOP",
			"n,BLACK_KNIGHT",
			"p,BLACK_PAWN"
		}
	)
	public void getLetterShouldReturnProperValue(char expectedLetter, Piece piece) {
		assertEquals(expectedLetter, piece.getLetter());
	}
}
