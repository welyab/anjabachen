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

	public InvalidPositionException(char file, int rank) {
		this(Position.toRow(rank), Position.toColumn(file));
	}

	public InvalidPositionException(int row, int column) {
		super(String.format("The position [%d, %d] is invalid", row, column));
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
}
