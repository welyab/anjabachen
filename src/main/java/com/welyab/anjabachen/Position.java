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
 * <p>
 * All operation in the the <code>Board</code> class are made in terms of <code>rows</code> and
 * <code>columns</code>. The board representation is a 2-dimensional array where the top left most
 * position is in the position <code>[0, 0]</code>, and bottom right most is in the position
 * <code>[7, 7]</code>. The piece disposition in the board places the blacks in the top of the
 * 2-dimensional array, and the whites goes to bottom. So, the <code>Board</code> class rarely use
 * the terms <i>ranks</i> and <i>files</i> to indicate an specific position. But this class contains
 * some utility method to translate a <code>[file, rank]</code> based position into a
 * <code>[row, column]</code> position, and vice versa.
 *
 * <p>
 * <pre>
 *       ┌───┬───┬───┬───┬───┬───┬───┬───┐
 * [8] 0 │ r │ n │ b │   │ k │ b │ n │ r │
 *       ├───┼───┼───┼───┼───┼───┼───┼───┤
 * [7] 1 │   │   │ p │ p │   │ p │ p │ p │
 *       ├───┼───┼───┼───┼───┼───┼───┼───┤
 * [6] 2 │ p │ p │   │   │   │   │   │   │
 *       ├───┼───┼───┼───┼───┼───┼───┼───┤
 * [5] 3 │   │   │   │   │ p │ Q │ q │   │
 *       ├───┼───┼───┼───┼───┼───┼───┼───┤
 * [4] 4 │   │   │ B │   │ P │   │   │   │
 *       ├───┼───┼───┼───┼───┼───┼───┼───┤
 * [3] 5 │   │   │   │   │   │   │   │   │
 *       ├───┼───┼───┼───┼───┼───┼───┼───┤
 * [2] 6 │ P │ P │ P │ P │   │ P │ P │ P │
 *       ├───┼───┼───┼───┼───┼───┼───┼───┤
 * [1] 7 │ R │ N │ B │   │ K │   │ N │ R │
 *  |  | └───┴───┴───┴───┴───┴───┴───┴───┘
 *  |  |   0   1   2   3   4   5   6   7 --------+
 *  |  |  [A] [B] [C] [D] [E] [F] [G] [H]----+   |
 *  |  |                                     |   |
 *  |  |                     files <---------+   |
 *  |  |                   columns <-------------+
 *  |  +-----> rows
 *  +--------> ranks
 * </pre>
 *
 * @author Welyab Paula
 *
 * @see Board
 * @see #of(int, int)
 * @see #of(char, int)
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
	 * Cache all board position based on files and ranks.
	 */
	// @formatter:off
	private static final String[][] POSITIONS_NOTATIONS = {
		{"a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8"},
		{"a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7"},
		{"a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6"},
		{"a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5"},
		{"a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4"},
		{"a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3"},
		{"a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2"},
		{"a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"}
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
	@Override
	public boolean equals(Object position) {
		if (!(position instanceof Position)) {
			return false;
		}
		Position p = (Position) position;
		return row == p.row && column == p.column;
	}

	@Override
	public int hashCode() {
		return row * 8 + column;
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

	/**
	 * Retrieves the file letter for the column of this position.
	 *
	 * @return The file letter symbol.
	 */
	public char getFile() {
		return (char) ('a' + column);
	}

	/**
	 * Retrieves the rank number of the row of this position.
	 *
	 * @return The the rank number.
	 */
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
	 *
	 * @throws InvalidPositionException If the given position <code>[row, column]</code> is out of
	 *         the board bounds.
	 */
	public static Position of(int row, int column) {
		if (row < GameConstants.MIN_ROW_NUMBER || row > GameConstants.MAX_ROW_NUMBER
				|| column < GameConstants.MIN_COLUMN_NUMBER || column > GameConstants.MAX_COLUMN_NUMBER) {
			throw new InvalidPositionException(row, column);
		}
		return POSITIONS[row][column];
	}

	/**
	 * Takes a <code>[file, rank]</code> based position and returns the equivalent
	 * <code>Position</code> object to it.
	 *
	 * @param file The file letter.
	 * @param rank The rank number.
	 *
	 * @return The associated position.
	 *
	 * @throws InvalidPositionException If the given position denotes a location out from board
	 *         bounds. File and rank based position must stay in the in interval of
	 *         <code>['a' to 'h']</code> and <code>[1 to 9]</code>.
	 */
	public static Position of(char file, int rank) {
		file = Character.toLowerCase(file);
		if (file < 'a' || file > 'h' || rank < 1 || rank > 8) {
			throw new InvalidPositionException(toRow(rank), toColumn(file));
		}
		return of(toRow(rank), toColumn(file));
	}

	/**
	 * Converts a rank number into a row number based on the ANJABACHEN grid system.
	 *
	 * <p>
	 * ANJABACHEN uses a position grid based in rows and columns, not in files (a to b) and ranks (1
	 * to 8). To know more read the documentation of the class {@link Position}.
	 *
	 * @param rank The rank number.
	 *
	 * @return The row number.
	 */
	public static int toRow(int rank) {
		return 8 - rank;
	}

	/**
	 * Converts a file number into a column number based on the ANJABACHEN grid system.
	 *
	 * <p>
	 * ANJABACHEN uses a position grid based in rows and columns, not in files (a to b) and ranks (1
	 * to 8). To know more read the documentation of the class {@link Position}.
	 *
	 * @param file The file number.
	 *
	 * @return The column number.
	 */
	public static int toColumn(int file) {
		return file - 'a';
	}

	/**
	 * Converts a column number to the file number.
	 *
	 * <p>
	 * ANJABACHEN uses a position grid based in rows and columns, not in files (a to b) and ranks (1
	 * to 8). To know more read the documentation of the class {@link Position}.
	 *
	 * @param column The column number to be converted.
	 *
	 * @return The file number of the position.
	 */
	public static char toFile(int column) {
		return (char) ('a' + column);
	}

	/**
	 * Converts a row number to the rank number.
	 *
	 * <p>
	 * ANJABACHEN uses a position grid based in rows and columns, not in files (a to b) and ranks (1
	 * to 8). To know more read the documentation of the class {@link Position}.
	 *
	 * @param row The row number to be converted.
	 *
	 * @return The rank number of the position.
	 */
	public static int toRank(int row) {
		return 8 - row;
	}

	/**
	 * Retrieves a representation of this position object as it appears in a PGN file. For the
	 * position <code>[row = 0, column = 0]</code> will return the notation <code>"a8"</code>.
	 *
	 * @return The PGN position notation.
	 */
	public String getPgnPosition() {
		return POSITIONS_NOTATIONS[row][column];
	}

	@Override
	public String toString() {
		return String.format("[%d, %d]", row, column);
	}
}
