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

@SuppressWarnings("javadoc")
public class PieceTypeTest {
	
	@ParameterizedTest
	@CsvSource(
		{
			"KING,6",
			"QUEEN,5",
			"ROOK,4",
			"BISHOP,3",
			"KNIGHT,2",
			"PAWN,1",
		}
	)
	public void isKingShouldReturnProperlyValue(PieceType type, int pieceTypeValue) {
		assertEquals(pieceTypeValue, type.getValue());
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			"KING,true",
			"QUEEN,false",
			"ROOK,false",
			"BISHOP,false",
			"KNIGHT,false",
			"PAWN,false",
		}
	)
	public void isKingShouldReturnProperlyValue(PieceType type, boolean expectedValue) {
		assertEquals(expectedValue, type.isKing());
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			"KING,false",
			"QUEEN,true",
			"ROOK,false",
			"BISHOP,false",
			"KNIGHT,false",
			"PAWN,false",
		}
	)
	public void isQueenShouldReturnProperlyValue(PieceType type, boolean expectedValue) {
		assertEquals(expectedValue, type.isQueen());
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			"KING,false",
			"QUEEN,false",
			"ROOK,true",
			"BISHOP,false",
			"KNIGHT,false",
			"PAWN,false",
		}
	)
	public void isRookShouldReturnProperlyValue(PieceType type, boolean expectedValue) {
		assertEquals(expectedValue, type.isRook());
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			"KING,false",
			"QUEEN,false",
			"ROOK,false",
			"BISHOP,true",
			"KNIGHT,false",
			"PAWN,false",
		}
	)
	public void isBishopShouldReturnProperlyValue(PieceType type, boolean expectedValue) {
		assertEquals(expectedValue, type.isBishop());
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			"KING,false",
			"QUEEN,false",
			"ROOK,false",
			"BISHOP,false",
			"KNIGHT,true",
			"PAWN,false",
		}
	)
	public void isKnigthShouldReturnProperlyValue(PieceType type, boolean expectedValue) {
		assertEquals(expectedValue, type.isKnight());
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			"KING,false",
			"QUEEN,false",
			"ROOK,false",
			"BISHOP,false",
			"KNIGHT,false",
			"PAWN,true",
		}
	)
	public void isPawnShouldReturnProperlyValue(PieceType type, boolean expectedValue) {
		assertEquals(expectedValue, type.isPawn());
	}
}
