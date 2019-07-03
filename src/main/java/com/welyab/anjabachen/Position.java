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
 * A <code>Position</code> is a <code>[row, column]</code> pair that locate a specific square in the
 * game board. A instance of <code>Position</code> is immutable class.
 *
 * @author Welyab Paula
 *
 * @see #of(int, int)
 */
public class Position {

	/**
	 * Cache for all 64 position available in a chess board.
	 */
	// @formatter:off
	private static final Position[][] POSITIONS = {
		{new Position(0, 0),new Position(0, 1),new Position(0, 2),new Position(0, 3),new Position(0, 4),new Position(0, 5),new Position(0, 6),new Position(0, 7)},
		{new Position(1, 0),new Position(1, 1),new Position(1, 2),new Position(1, 3),new Position(1, 4),new Position(1, 5),new Position(1, 6),new Position(1, 7)},
		{new Position(2, 0),new Position(2, 1),new Position(2, 2),new Position(2, 3),new Position(2, 4),new Position(2, 5),new Position(2, 6),new Position(2, 7)},
		{new Position(3, 0),new Position(3, 1),new Position(3, 2),new Position(3, 3),new Position(3, 4),new Position(3, 5),new Position(3, 6),new Position(3, 7)},
		{new Position(4, 0),new Position(4, 1),new Position(4, 2),new Position(4, 3),new Position(4, 4),new Position(4, 5),new Position(4, 6),new Position(4, 7)},
		{new Position(5, 0),new Position(5, 1),new Position(5, 2),new Position(5, 3),new Position(5, 4),new Position(5, 5),new Position(5, 6),new Position(5, 7)},
		{new Position(6, 0),new Position(6, 1),new Position(6, 2),new Position(6, 3),new Position(6, 4),new Position(6, 5),new Position(6, 6),new Position(6, 7)},
		{new Position(7, 0),new Position(7, 1),new Position(7, 2),new Position(7, 3),new Position(7, 4),new Position(7, 5),new Position(7, 6),new Position(7, 7)},
	};
	//@formatter:on

	/**
	 * The row number.
	 */
	private final int row;

	/**
	 * The column number.
	 */
	private final int column;

	/**
	 * Creates a new instance of <code>Position</code> by receive a <code>[row, column]</code> pair.
	 *
	 * @param row The row number.
	 *
	 * @param column The column number.
	 */
	private Position(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Retrieves the row number of this position.
	 *
	 * @return The row number.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Retrieves the column number of this position.
	 *
	 * @return The column number.
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Evaluates if this position object has the same values for given <code>position</code>.
	 *
	 * @param position The position to compare.
	 *
	 * @return A value <code>true</code> this object has the same values, or <code>false</code>
	 *         otherwise.
	 */
	public boolean equals(Position position) {
		return row == position.row && column == position.column;
	}

	/**
	 * Evaluates if this position object has the same values for <code>row</code> and
	 * <code>column</code>.
	 *
	 * @param row The row.
	 * @param column The column.
	 *
	 * @return A value <code>true</code> this object has the same values, or <code>false</code>
	 *         otherwise.
	 */
	public boolean equals(int row, int column) {
		return this.row == row && this.column == column;
	}

	public char getFile() {
		return (char) ('a' + column);
	}

	public int getRank() {
		return 8 - row;
	}

	/**
	 * Creates a new instance of <code>Position</code> by receive a <code>[row, column]</code> pair.
	 *
	 * @param row The row number.
	 *
	 * @param column The column number.
	 *
	 * @return A new <code>Position</code> instance.
	 */
	public static Position of(int row, int column) {
		if (row < Constants.MIN_ROW_NUMBER || row > Constants.MAX_ROW_NUMBER
				|| column < Constants.MIN_COLUMN_NUMBER || column > Constants.MAX_COLUMN_NUMBER) {
			throw new InvalidPosition(row, column);
		}
		return POSITIONS[row][column];
	}

	public static Position of(char file, int rank) {
		file = Character.toLowerCase(file);
		if (file < 'a' || file > 'h' || rank < 1 || rank > 8) {
		}
		// 1 2 3 4 5 6 7 8 rank
		// 7 6 5 4 3 2 1 0 row
		return of(8 - rank, file - 'a');
	}

	@Override
	public String toString() {
		return String.format("[%d, %d]", row, column);
	}
}
