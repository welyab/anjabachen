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
 */
public class Position {

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
	public Position(int row, int column) {
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
	 * Creates a new instance of <code>Position</code> by receive a <code>[row, column]</code> pair.
	 *
	 * @param row The row number.
	 *
	 * @param column The column number.
	 *
	 * @return A new <code>Position</code> instance.
	 */
	public static Position of(int row, int column) {
		return new Position(row, column);
	}

	@Override
	public String toString() {
		return String.format("[%d, %d]", row, column);
	}
}
