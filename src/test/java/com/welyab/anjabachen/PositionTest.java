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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Unit tests for the <code>Position</code> class.
 * 
 * @author Welyab Paula
 */
public class PositionTest {
	
	@ParameterizedTest
	@CsvSource(
		{
			'a' + "," + 0,
			'b' + "," + 1,
			'c' + "," + 2,
			'd' + "," + 3,
			'e' + "," + 4,
			'f' + "," + 5,
			'g' + "," + 6,
			'h' + "," + 7
		}
	)
	@SuppressWarnings("javadoc")
	public void columnToFileShouldConvertColumnsToFilesProperly(char file, byte column) {
		assertEquals(file, Position.columnToFile(column));
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			0 + "," + 'a',
			1 + "," + 'b',
			2 + "," + 'c',
			3 + "," + 'd',
			4 + "," + 'e',
			5 + "," + 'f',
			6 + "," + 'g',
			7 + "," + 'h'
		}
	)
	@SuppressWarnings("javadoc")
	public void fileToColumnShouldConevertFilesToColumnProperly(byte column, char file) {
		assertEquals(column, Position.fileToColumn(file));
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			1 + "," + 7,
			2 + "," + 6,
			3 + "," + 5,
			4 + "," + 4,
			5 + "," + 3,
			6 + "," + 2,
			7 + "," + 1,
			8 + "," + 0
		}
	)
	@SuppressWarnings("javadoc")
	public void rowToRankShouldConvertRanksToColumnsProperly(byte rank, byte row) {
		assertEquals(rank, Position.rowToRank(row));
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			7 + "," + 1,
			6 + "," + 2,
			5 + "," + 3,
			4 + "," + 4,
			3 + "," + 5,
			2 + "," + 6,
			1 + "," + 7,
			0 + "," + 8
		}
	)
	@SuppressWarnings("javadoc")
	public void rankToRowShouldConevertRanksToRowsProperlyProperly(byte row, byte rank) {
		assertEquals(row, Position.rankToRow(rank));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void ofFileRankShouldThrowArrayIndexOutOfBoundIfTheFileLetterIsLessThanA() {
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			Position.of((char) ('a' - 1), 1);
		});
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void ofFileRankShouldThrowArrayIndexOutOfBoundIfTheFileLetterIsGreaterThanH() {
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			Position.of((char) ('h' + 1), 1);
		});
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void ofRowColumnShouldThrownIndexOutOfBoundIfTheRowIsLessThanZero() {
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			Position.of(-1, 1);
		});
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void ofRowColumnShouldThrownIndexOutOfBoundIfTheColumnIsLessThanZero() {
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			Position.of(2, -4);
		});
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			'a' + "," + 7 + "," + 0,
			'b' + "," + 6 + "," + 1,
			'c' + "," + 5 + "," + 2,
			'd' + "," + 4 + "," + 3,
			'e' + "," + 3 + "," + 4,
			'f' + "," + 2 + "," + 5,
			'g' + "," + 1 + "," + 6,
			'h' + "," + 0 + "," + 7
		}
	)
	@SuppressWarnings("javadoc")
	public void getFileShouldReturnProperlyValue(char file, int row, int column) {
		assertEquals(file, Position.of(row, column).getFile());
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			1 + "," + 7 + "," + 0,
			2 + "," + 6 + "," + 1,
			3 + "," + 5 + "," + 2,
			4 + "," + 4 + "," + 3,
			5 + "," + 3 + "," + 4,
			6 + "," + 2 + "," + 5,
			7 + "," + 1 + "," + 6,
			8 + "," + 0 + "," + 7
		}
	)
	@SuppressWarnings("javadoc")
	public void getRankShouldReturnProperlyValue(byte rank, byte row, byte column) {
		assertEquals(rank, Position.of(row, column).getRank());
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			"e5" + "," + 3 + "," + 4,
			"a8" + "," + 0 + "," + 0,
			"a1" + "," + 7 + "," + 0,
			"h8" + "," + 0 + "," + 7,
			"h1" + "," + 7 + "," + 7,
			"e4" + "," + 4 + "," + 4
		}
	)
	@SuppressWarnings("javadoc")
	public void getAlgebraicNotationShouldReturnProperlyValue(String algebraicNotation, byte row, byte column) {
		assertEquals(algebraicNotation, Position.of(row, column).getNotation());
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			3 + "," + 4 + "," + "e5",
			0 + "," + 0 + "," + "a8",
			7 + "," + 0 + "," + "a1",
			0 + "," + 7 + "," + "h8",
			7 + "," + 7 + "," + "h1",
			4 + "," + 4 + "," + "e4"
		}
	)
	@SuppressWarnings("javadoc")
	public void ofAlgebraicNotationShouldReturnProperlyValue(
		byte expectedRow,
		byte expectedColumn,
		String algebraicNotation
	) {
		assertEquals(Position.of(expectedRow, expectedColumn), Position.of(algebraicNotation));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testEqualsShouldReturnFalseWhenGivenObjectIsNotInstanceOfPosition() {
		assertFalse(
			Position.of(0, 0).equals(""),
			"Position.equals should return false when compared object is not a Position"
		);
	}
}
