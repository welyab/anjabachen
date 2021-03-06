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

/**
 * The <code>Position</code> is a simple pair of <code>row/column</code> that locates a square in
 * the chess board.
 * 
 * @author Welyab Paula
 */
public final class Position {
	
	// @formatter:off
	@SuppressWarnings("javadoc")
	private static final Position[][] CACHE = {
		{n(0, 0), n(0, 1), n(0, 2), n(0, 3), n(0, 4), n(0, 5), n(0, 6), n(0, 7)},
		{n(1, 0), n(1, 1), n(1, 2), n(1, 3), n(1, 4), n(1, 5), n(1, 6), n(1, 7)},
		{n(2, 0), n(2, 1), n(2, 2), n(2, 3), n(2, 4), n(2, 5), n(2, 6), n(2, 7)},
		{n(3, 0), n(3, 1), n(3, 2), n(3, 3), n(3, 4), n(3, 5), n(3, 6), n(3, 7)},
		{n(4, 0), n(4, 1), n(4, 2), n(4, 3), n(4, 4), n(4, 5), n(4, 6), n(4, 7)},
		{n(5, 0), n(5, 1), n(5, 2), n(5, 3), n(5, 4), n(5, 5), n(5, 6), n(5, 7)},
		{n(6, 0), n(6, 1), n(6, 2), n(6, 3), n(6, 4), n(6, 5), n(6, 6), n(6, 7)},
		{n(7, 0), n(7, 1), n(7, 2), n(7, 3), n(7, 4), n(7, 5), n(7, 6), n(7, 7)}
	};
	// @formatter:on
	
	// @formatter:off
	@SuppressWarnings("javadoc")
	private static final String[][] CACHE_SAN = {
		{"a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8"},
		{"a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7"},
		{"a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6"},
		{"a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5"},
		{"a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4"},
		{"a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3"},
		{"a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2"},
		{"a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"},
	};
	// @formatter:on
	
	// @formatter:off
	@SuppressWarnings("javadoc") public static final Position A1 = of(7, 0);
	@SuppressWarnings("javadoc") public static final Position A2 = of(6, 0);
	@SuppressWarnings("javadoc") public static final Position A3 = of(5, 0);
	@SuppressWarnings("javadoc") public static final Position A4 = of(4, 0);
	@SuppressWarnings("javadoc") public static final Position A5 = of(3, 0);
	@SuppressWarnings("javadoc") public static final Position A6 = of(2, 0);
	@SuppressWarnings("javadoc") public static final Position A7 = of(1, 0);
	@SuppressWarnings("javadoc") public static final Position A8 = of(0, 0);
	
	@SuppressWarnings("javadoc") public static final Position B1 = of(7, 1);
	@SuppressWarnings("javadoc") public static final Position B2 = of(6, 1);
	@SuppressWarnings("javadoc") public static final Position B3 = of(5, 1);
	@SuppressWarnings("javadoc") public static final Position B4 = of(4, 1);
	@SuppressWarnings("javadoc") public static final Position B5 = of(3, 1);
	@SuppressWarnings("javadoc") public static final Position B6 = of(2, 1);
	@SuppressWarnings("javadoc") public static final Position B7 = of(1, 1);
	@SuppressWarnings("javadoc") public static final Position B8 = of(0, 1);
	
	@SuppressWarnings("javadoc") public static final Position C1 = of(7, 2);
	@SuppressWarnings("javadoc") public static final Position C2 = of(6, 2);
	@SuppressWarnings("javadoc") public static final Position C3 = of(5, 2);
	@SuppressWarnings("javadoc") public static final Position C4 = of(4, 2);
	@SuppressWarnings("javadoc") public static final Position C5 = of(3, 2);
	@SuppressWarnings("javadoc") public static final Position C6 = of(2, 2);
	@SuppressWarnings("javadoc") public static final Position C7 = of(1, 2);
	@SuppressWarnings("javadoc") public static final Position C8 = of(0, 2);
	
	@SuppressWarnings("javadoc") public static final Position D1 = of(7, 3);
	@SuppressWarnings("javadoc") public static final Position D2 = of(6, 3);
	@SuppressWarnings("javadoc") public static final Position D3 = of(5, 3);
	@SuppressWarnings("javadoc") public static final Position D4 = of(4, 3);
	@SuppressWarnings("javadoc") public static final Position D5 = of(3, 3);
	@SuppressWarnings("javadoc") public static final Position D6 = of(2, 3);
	@SuppressWarnings("javadoc") public static final Position D7 = of(1, 3);
	@SuppressWarnings("javadoc") public static final Position D8 = of(0, 3);
	
	@SuppressWarnings("javadoc") public static final Position E1 = of(7, 4);
	@SuppressWarnings("javadoc") public static final Position E2 = of(6, 4);
	@SuppressWarnings("javadoc") public static final Position E3 = of(5, 4);
	@SuppressWarnings("javadoc") public static final Position E4 = of(4, 4);
	@SuppressWarnings("javadoc") public static final Position E5 = of(3, 4);
	@SuppressWarnings("javadoc") public static final Position E6 = of(2, 4);
	@SuppressWarnings("javadoc") public static final Position E7 = of(1, 4);
	@SuppressWarnings("javadoc") public static final Position E8 = of(0, 4);
	
	@SuppressWarnings("javadoc") public static final Position F1 = of(7, 5);
	@SuppressWarnings("javadoc") public static final Position F2 = of(6, 5);
	@SuppressWarnings("javadoc") public static final Position F3 = of(5, 5);
	@SuppressWarnings("javadoc") public static final Position F4 = of(4, 5);
	@SuppressWarnings("javadoc") public static final Position F5 = of(3, 5);
	@SuppressWarnings("javadoc") public static final Position F6 = of(2, 5);
	@SuppressWarnings("javadoc") public static final Position F7 = of(1, 5);
	@SuppressWarnings("javadoc") public static final Position F8 = of(0, 5);
	
	@SuppressWarnings("javadoc") public static final Position G1 = of(7, 6);
	@SuppressWarnings("javadoc") public static final Position G2 = of(6, 6);
	@SuppressWarnings("javadoc") public static final Position G3 = of(5, 6);
	@SuppressWarnings("javadoc") public static final Position G4 = of(4, 6);
	@SuppressWarnings("javadoc") public static final Position G5 = of(3, 6);
	@SuppressWarnings("javadoc") public static final Position G6 = of(2, 6);
	@SuppressWarnings("javadoc") public static final Position G7 = of(1, 6);
	@SuppressWarnings("javadoc") public static final Position G8 = of(0, 6);
	
	@SuppressWarnings("javadoc") public static final Position H1 = of(7, 7);
	@SuppressWarnings("javadoc") public static final Position H2 = of(6, 7);
	@SuppressWarnings("javadoc") public static final Position H3 = of(5, 7);
	@SuppressWarnings("javadoc") public static final Position H4 = of(4, 7);
	@SuppressWarnings("javadoc") public static final Position H5 = of(3, 7);
	@SuppressWarnings("javadoc") public static final Position H6 = of(2, 7);
	@SuppressWarnings("javadoc") public static final Position H7 = of(1, 7);
	@SuppressWarnings("javadoc") public static final Position H8 = of(0, 7);
	// @formatter:on
	
	/** The position row. */
	public final byte row;
	
	/** The position column. */
	public final byte column;
	
	@SuppressWarnings("javadoc")
	private Position(byte row, byte column) {
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Retrieves the rank number of this position.
	 * 
	 * @return The rank number.
	 */
	public byte getRank() {
		return rowToRank(row);
	}
	
	/**
	 * Retrieves the file number of this position.
	 * 
	 * @return The file number.
	 */
	public char getFile() {
		return columnToFile(column);
	}
	
	/**
	 * Retrieves the algebraic notation of this column, like <code>"g6"</code>, <code>"d4"</code>.
	 * 
	 * @return The algebraic notation.
	 */
	public String getNotation() {
		return CACHE_SAN[row][column];
	}
	
	@SuppressWarnings("javadoc")
	// new
	private static Position n(int row, int column) {
		return new Position((byte) row, (byte) column);
	}
	
	/**
	 * Retrieves the position of the given <code>row/column</code> pair.
	 * 
	 * @param row The row number in the interval <code>[0 - 7]</code>.
	 * @param column The column number in the interval <code>[0 - 7]</code>.
	 * 
	 * @return The position for given <code>row</code> and <code>column</code>.
	 * 
	 * @throws ArrayIndexOutOfBoundsException If given <code>row</code> or <code>column</code> are
	 *         invalid.
	 */
	public static Position of(int row, int column) {
		return CACHE[row][column];
	}
	
	/**
	 * Retrieves the position of the given <code>file/rank</code> pair.
	 * 
	 * @param file The file letter in the interval <code>[a - h]</code>.
	 * @param rank The rank number in the interval <code>[1 - 8]</code>.
	 * 
	 * @return The position for given <code>file</code> and <code>rank</code>.
	 * 
	 * @throws ArrayIndexOutOfBoundsException If given <code>file</code> letter or <code>rank</code>
	 *         are invalid.
	 */
	public static Position of(char file, int rank) {
		return of(rankToRow(rank), fileToColumn(file));
	}
	
	/**
	 * Retrieves the position of the given algebraic notation of the position.
	 * 
	 * @param algebraicNotation The position in algebraic notation.
	 * 
	 * @return The position.
	 */
	public static Position of(String algebraicNotation) {
		return of(
			algebraicNotation.charAt(0),
			algebraicNotation.charAt(1) - '0'
		);
	}
	
	/**
	 * Converts the given file letter into a column number.
	 * 
	 * <p>
	 * The file letter should be in lower case and must be in the interval <code>[a - h]</code>,
	 * otherwise this method may return unexpected values.
	 * 
	 * @param file The file letter in the interval <code>[a - h]</code>.
	 * 
	 * @return The column number.
	 */
	public static byte fileToColumn(char file) {
		return (byte) (file - 'a');
	}
	
	/**
	 * Converts the given column number into a file letter.
	 * 
	 * <p>
	 * The column number should be in the interval <code>[0 - 7]</code>, otherwise this method may
	 * return unexpected values.
	 * 
	 * @param column The column number in the interval <code>[0 - 7]</code>.
	 * 
	 * @return The file letter.
	 */
	public static char columnToFile(int column) {
		return (char) ('a' + column);
	}
	
	/**
	 * Converts the given rank number into a row number.
	 * 
	 * <p>
	 * The rank number should be in the interval <code>[1 - 8]</code>, otherwise this method may
	 * return unexpected values.
	 * 
	 * @param rank The rank number in the interval <code>[1 - 8]</code>.
	 * 
	 * @return The row number.
	 */
	public static byte rankToRow(int rank) {
		return (byte) (8 - rank);
	}
	
	/**
	 * Converts the given row number into a rank number.
	 * 
	 * <p>
	 * The given row number should be in the interval <code>[0 - 7]</code>. This method will return
	 * an unexpected value if the row does not follow this rule.
	 * 
	 * @param row The row number in the interval <code>[0 - 7]</code>.
	 * 
	 * @return The rank number.
	 */
	public static byte rowToRank(int row) {
		return (byte) (8 - row);
	}
	
	@Override
	public int hashCode() {
		return column * 8 + row;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Position)) {
			return false;
		}
		Position pos = (Position) obj;
		return pos.row == row && pos.column == column;
	}
	
	@SuppressWarnings("javadoc")
	public boolean equals(int row, int column) {
		return this.row == row && this.column == column;
	}
	
	@Override
	public String toString() {
		return getNotation();
	}
}
