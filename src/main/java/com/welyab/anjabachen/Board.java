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
 * @author Welyab Paula
 */
public class Board {

	/**
	 * Default chess board size.
	 */
	private static final int BOARD_SIZE = 8;

	/**
	 * The 2-dimensional array where the pieces are placed.
	 */
	private InnerSquare grid[][];

	/**
	 * Counts how many times the pieces were moved during the game playing. Each side moved count as
	 * a movement, so the white movement followed by black movement counts as 2 movements.
	 */
	private int movementCount;

	/**
	 * Creates a new chess board.
	 *
	 * @param initialPosition If <code>true</code>, the board will have the pieces placed in its
	 *        initial positions.
	 */
	public Board(boolean initialPosition) {
		grid = createGrid();
		if (initialPosition) {
		}
	}

	/**
	 * Creates a 2-dimensional array with size equals to {@link #BOARD_SIZE}.
	 *
	 * @return The array.
	 */
	private static InnerSquare[][] createGrid() {
		InnerSquare g[][] = new InnerSquare[BOARD_SIZE][BOARD_SIZE];
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {
				g[row][column] = new InnerSquare();
			}
		}
		return g;
	}

	/**
	 * The side color that has the next movement.
	 *
	 * @return The side color.
	 */
	public Color getActiveColor() {
		return movementCount % 2 == 0 ? Color.WHITE : Color.BLACK;
	}

	/**
	 * The side color that is waiting for the opponent movement.
	 *
	 * @return The side color.
	 */
	public Color getWaitingColor() {
		return getActiveColor().getOpposite();
	}

	/**
	 * This class helps to track piece movement and other information during game playing.
	 *
	 * @author Welyab Paula
	 */
	private static class InnerSquare {

		private Piece piece;
	}
}
