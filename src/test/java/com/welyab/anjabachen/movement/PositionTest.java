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

/**
 * Unit tests for the <code>Position</code> class.
 * 
 * @author Welyab Paula
 */
public class PositionTest {
	
	@Test
	@SuppressWarnings("javadoc")
	public void columnToFileShouldConvertColumnsToFilesProperly() {
		assertEquals('a', Position.columnToFile(0));
		assertEquals('b', Position.columnToFile(1));
		assertEquals('c', Position.columnToFile(2));
		assertEquals('d', Position.columnToFile(3));
		assertEquals('e', Position.columnToFile(4));
		assertEquals('f', Position.columnToFile(5));
		assertEquals('g', Position.columnToFile(6));
		assertEquals('h', Position.columnToFile(7));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void fileToColumnShouldConevertFilesToColumnProperly() {
		assertEquals(0, Position.fileToColumn('a'));
		assertEquals(1, Position.fileToColumn('b'));
		assertEquals(2, Position.fileToColumn('c'));
		assertEquals(3, Position.fileToColumn('d'));
		assertEquals(4, Position.fileToColumn('e'));
		assertEquals(5, Position.fileToColumn('f'));
		assertEquals(6, Position.fileToColumn('g'));
		assertEquals(7, Position.fileToColumn('h'));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void rowToRankShouldConvertRanksToColumnsProperly() {
		assertEquals(1, Position.rowToRank(7));
		assertEquals(2, Position.rowToRank(6));
		assertEquals(3, Position.rowToRank(5));
		assertEquals(4, Position.rowToRank(4));
		assertEquals(5, Position.rowToRank(3));
		assertEquals(6, Position.rowToRank(2));
		assertEquals(7, Position.rowToRank(1));
		assertEquals(8, Position.rowToRank(0));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void rankToRowShouldConevertRanksToRowsProperlyProperly() {
		assertEquals(7, Position.rankToRow(1));
		assertEquals(6, Position.rankToRow(2));
		assertEquals(5, Position.rankToRow(3));
		assertEquals(4, Position.rankToRow(4));
		assertEquals(3, Position.rankToRow(5));
		assertEquals(2, Position.rankToRow(6));
		assertEquals(1, Position.rankToRow(7));
		assertEquals(0, Position.rankToRow(8));
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
}
