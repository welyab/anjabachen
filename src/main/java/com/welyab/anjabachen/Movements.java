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

import java.util.List;

/**
 * This class is a container for the set of available movements for a piece in an specific board
 * state.
 *
 * @author Welyab Paula
 */
public class Movements {

	/**
	 * The piece being moved.
	 */
	private final Piece piece;

	/**
	 * The origin square row number.
	 */
	private final int row;

	/**
	 * The origin square column number.
	 */
	private final int column;

	/**
	 * The list of available targets for the underlying piece.
	 */
	private final List<MovementTarget> targets;

	/**
	 * Creates a new <code>Movements</code> set for piece.
	 *
	 * @param piece The being moved piece.
	 * @param row The origin square row number.
	 * @param column The origin square column number.
	 * @param targets The list of available targets for the underlying piece.
	 */
	public Movements(Piece piece, int row, int column, List<MovementTarget> targets) {
		this.piece = piece;
		this.row = row;
		this.column = column;
		this.targets = targets;
	}

	/**
	 * Retrieves the being moved piece.
	 *
	 * @return The piece.
	 */
	public Piece getPiece() {
		return piece;
	}

	/**
	 * Retrieves the origin square column number.
	 *
	 * @return The origin square column number.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Retrieves the origin square column number.
	 *
	 * @return The origin square column number.
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Retrieves the list of movement targets.
	 *
	 * @return The list of targets for the underlying piece.
	 */
	public List<MovementTarget> getTargets() {
		return targets;
	}

	/**
	 * The amount of available movements in this set.
	 *
	 * @return The amount of available movements in this set.
	 */
	public int size() {
		return targets.size();
	}

	/**
	 * Indicates if exists any movement in this set.
	 *
	 * @return If exists any movement in this set.
	 */
	public boolean isEmpty() {
		return targets.isEmpty();
	}

	/**
	 * Retrieves the target in the specific index.
	 *
	 * @param index The target index.
	 *
	 * @return The target in the specific index.
	 */
	public MovementTarget getTarget(int index) {
		return targets.get(index);
	}
}
