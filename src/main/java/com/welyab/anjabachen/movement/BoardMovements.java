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

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A <code>BoardMovements</code> is a set of all movements available in the chess board at a
 * specific turn. A bag may contains the all movements for black pieces, or all movements for white
 * pieces, but not both at same time, according to side has the turn to perform the next movement in
 * the board.
 *
 * <p>
 * <code>BoardMovements</code> implements <code>Iterable</code> in order to deliver its
 * <code>PieceMovement</code> instances. Each <code>PieceMovement</code> describe the available
 * movement for an specific piece.
 *
 * @author Welyab Paula
 *
 * @see PieceMovement
 * @see MovementOrigin
 * @see MovementTarget
 */
public class BoardMovements implements Iterable<PieceMovement> {
	
	/** The list of piece movements. */
	private List<PieceMovement> movements;
	
	/** The consolidated information about movements. */
	private MovementMetadata meta;
	
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
	public BoardMovements(
			List<PieceMovement> movements,
			MovementMetadata meta
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
	public MovementMetadata getMetadata() {
		return meta;
	}
	
	@SuppressWarnings("javadoc")
	private class MovementIterator implements Iterator<Movement> {
		
		int deliveredMovements = 0;
		
		int pieceMovementIndex = 0;
		
		int movementTargetIndex = 0;
		
		@Override
		public boolean hasNext() {
			return deliveredMovements < getMetadata()
				.getValueOptional(MovementMetadataField.NODES)
				.orElse(0L);
		}
		
		@Override
		public Movement next() {
			if (!hasNext()) {
				throw new NoSuchElementException("No more elements");
			}
			if (movementTargetIndex >= get(pieceMovementIndex).size()) {
				pieceMovementIndex++;
				movementTargetIndex = 0;
			}
			MovementTarget movementTarget = get(pieceMovementIndex).getTarget(movementTargetIndex);
			movementTargetIndex++;
			deliveredMovements++;
			return new Movement(
				get(pieceMovementIndex).getOrigin(),
				movementTarget
			);
		}
	}
	
	/**
	 * Creates a iterator that delivers all movements in form a <code>Movement</code> object.
	 * 
	 * @return Welyab Paula
	 */
	public Iterator<Movement> movementIterator() {
		return new MovementIterator();
	}
	
	@Override
	public Iterator<PieceMovement> iterator() {
		return movements.iterator();
	}
	
	/**
	 * Creates a stream for all <code>PieceMovement</code> objects available.
	 * 
	 * @return The stream.
	 */
	public Stream<PieceMovement> stream() {
		return StreamSupport.stream(spliterator(), false);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (PieceMovement pieceMovement : this) {
			if (builder.length() != 0) {
				builder.append(String.format("%n"));
			}
			builder.append(pieceMovement);
		}
		return builder.toString();
	}
}
