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

import java.util.Iterator;
import java.util.List;

/**
 * A <code>MovementBag</code> is a set of all movements available in the chess board at a specific
 * movement. A bag may contains the all movements for black pieces, or white pieces, but not both at
 * same time, according to side has the turn to peform the nexet movement in the board.
 *
 * <p>
 * <code>MovementBag</code> implements <code>Iterable</code> in order to deliver its
 * <code>PieceMovement</code> instances.
 *
 * @author Welyab Paula
 *
 * @see PieceMovement
 * @see MovementOrigin
 * @see MovementTarget
 */
public class MovementBag implements Iterable<PieceMovement> {

	/** The list of piece movements. */
	private List<PieceMovement> movements;

	/** The consolidated information about movements. */
	private PieceMovementMeta meta;

	/**
	 * Creates a new <code>MovementBag</code>.
	 *
	 * @param movements This is the list of available pieces to move. The <code>MovementBag</code>
	 *        class does not apply any change to given list and further modifications made on the
	 *        list by external resources will be reflected in the internals of the bag. For example,
	 *        the method {@linkplain #size() size} of this bag just delegates to the size of the
	 *        given list.
	 *
	 * @param meta The consolidated information about movements.
	 */
	public MovementBag(
			List<PieceMovement> movements,
			PieceMovementMeta meta
	) {
		this.movements = movements;
		this.meta = meta;
	}

	/**
	 * Evaluates if this bag is empty.
	 *
	 * @return A value <code>true</code> if there is at least one movement origin in this bag, or
	 *         <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return movements.isEmpty();
	}

	/**
	 * Retrieves the movement origin at the specific zero based index.
	 *
	 * @param index The index of the movement position inside this bag.
	 *
	 * @return The movement origin.
	 */
	public PieceMovement get(int index) {
		return movements.get(index);
	}

	/**
	 * Retrieves how many piece movements there are in this bag. Note that this method returns only
	 * the number of origins (a {@link PieceMovement} object).
	 *
	 * @return The total amount of movement origin.
	 */
	public int size() {
		return movements.size();
	}

	/**
	 * Retrieves the metadata associated with this movement bag.
	 *
	 * @return The metadata object.
	 */
	public PieceMovementMeta getMeta() {
		return meta;
	}

	@Override
	public Iterator<PieceMovement> iterator() {
		return movements.iterator();
	}
}
