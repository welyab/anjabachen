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
		{nnew(0, 0), nnew(0, 1), nnew(0, 2), nnew(0, 3), nnew(0, 4), nnew(0, 5), nnew(0, 6), nnew(0, 7)},
		{nnew(1, 0), nnew(1, 1), nnew(1, 2), nnew(1, 3), nnew(1, 4), nnew(1, 5), nnew(1, 6), nnew(1, 7)},
		{nnew(2, 0), nnew(2, 1), nnew(2, 2), nnew(2, 3), nnew(2, 4), nnew(2, 5), nnew(2, 6), nnew(2, 7)},
		{nnew(3, 0), nnew(3, 1), nnew(3, 2), nnew(3, 3), nnew(3, 4), nnew(3, 5), nnew(3, 6), nnew(3, 7)},
		{nnew(4, 0), nnew(4, 1), nnew(4, 2), nnew(4, 3), nnew(4, 4), nnew(4, 5), nnew(4, 6), nnew(4, 7)},
		{nnew(5, 0), nnew(5, 1), nnew(5, 2), nnew(5, 3), nnew(5, 4), nnew(5, 5), nnew(5, 6), nnew(5, 7)},
		{nnew(6, 0), nnew(6, 1), nnew(6, 2), nnew(6, 3), nnew(6, 4), nnew(6, 5), nnew(6, 6), nnew(6, 7)},
		{nnew(7, 0), nnew(7, 1), nnew(7, 2), nnew(7, 3), nnew(7, 4), nnew(7, 5), nnew(7, 6), nnew(7, 7)}
	};
	// @formatter:on
	
	/** The position row. */
	private byte row;
	
	/** The position column. */
	private byte column;
	
	@SuppressWarnings("javadoc")
	private Position(byte row, byte column) {
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Retrieves the position row.
	 * 
	 * @return The row number.
	 */
	public byte getRow() {
		return row;
	}
	
	/**
	 * Retrieves the position column.
	 * 
	 * @return The column number.
	 */
	public byte getColumn() {
		return column;
	}
	
	@SuppressWarnings("javadoc")
	private static Position nnew(int row, int column) {
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
	public String toString() {
		return String.format("Position[%d, %d]", row, column);
	}
}
