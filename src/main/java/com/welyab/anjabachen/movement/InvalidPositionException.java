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
 * Indicates an attempt to perform an operation with a square out of board.
 *
 * @author Welyab Paula
 */
public class InvalidPositionException extends ChessException {

	@SuppressWarnings("javadoc")
	private static final long serialVersionUID = 1L;

	/** The invalid position row number. */
	private final int row;

	/** The invalid position column number. */
	private final int column;

	/**
	 * Creates a instance by informing the invalid position <code>[row, column]</code>.
	 *
	 * <p>
	 * This constructor does not receive a <code>Position</code> instance because that type of
	 * objects are not possible to be created with invalid positions.
	 *
	 * @param row The invalid position row number.
	 * @param column The invalid position column number.
	 */
	public InvalidPositionException(int row, int column) {
		super(String.format("The position [%d, %d] is invalid", row, column));
		this.row = row;
		this.column = column;
	}

	/**
	 * Retrieves the invalid position row number.
	 *
	 * @return The invalid row number.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Retrieves the invalid position column number.
	 *
	 * @return The column number.
	 */
	public int getColumn() {
		return column;
	}
}
