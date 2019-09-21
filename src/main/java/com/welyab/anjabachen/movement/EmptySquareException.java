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

import com.welyab.anjabachen.ChessException;

/**
 * This exception indicates problems during some processing over a specific square a piece was
 * expected to be found.
 *
 * @author Welyab Paula
 */
public class EmptySquareException extends ChessException {

	/**
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The row number of the square.
	 */
	private final int row;

	/**
	 * The column number of the square.
	 */
	private final int column;

	/**
	 * Creating a new exception by informing the specific <code>[row, column]</code> of the empty
	 * square.
	 *
	 * @param row The row number of the square.
	 * @param column The column numbr of the square.
	 */
	public EmptySquareException(int row, int column) {
		super(String.format("The square at location [%d, %d] is empty", row, column));
		this.row = row;
		this.column = column;
	}

	/**
	 * Retrieves the row number of the square that was indicated as empty.
	 *
	 * @return The row number.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Retrieves the column number of the square that was indicated as empty.
	 *
	 * @return The column number.
	 */
	public int getColumn() {
		return column;
	}
}
