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
 * This class is a container for the set of available movements for a piece in an specific board
 * state.
 *
 * <p>
 * This class implements <code>Iterable</code> in order to deliver each of its
 * <code>MovementTarget</code> instances.
 *
 * @author Welyab Paula
 *
 * @see MovementBag
 * @see MovementOrigin
 * @see MovementTarget
 */
public class PieceMovement implements Iterable<MovementTarget> {

	/** Origin of the movement. */
	private final MovementOrigin origin;

	/**
	 * The list of available targets for the underlying piece.
	 */
	private final List<MovementTarget> targets;

	/**
	 * The metadata associated with this movement set.
	 */
	private final PieceMovementMeta meta;

	/**
	 * Creates a new <code>Movements</code> set for piece.
	 *
	 * @param movementOrigin The origin of the movement.
	 * @param targets The list of available targets for the underlying piece.
	 * @param meta The metadata associated with this movement set.
	 */
	public PieceMovement(
			MovementOrigin movementOrigin,
			List<MovementTarget> targets,
			PieceMovementMeta meta
	) {
		origin = movementOrigin;
		this.targets = targets;
		this.meta = meta;
	}

	/**
	 * Retrieves the origin of this movement.
	 *
	 * @return The origin.
	 */
	public MovementOrigin getOrigin() {
		return origin;
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
	 * Indicates if not exists any movement in this set.
	 *
	 * @return If not exists any movement in this set.
	 */
	public boolean isNotEmpty() {
		return !isEmpty();
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

	@Override
	public Iterator<MovementTarget> iterator() {
		return targets.iterator();
	}

	/**
	 * Retrieves the metadata associated with this piece movement.
	 *
	 * @return The metadata.
	 */
	public PieceMovementMeta getMeta() {
		return meta;
	}

	@Override
	public String toString() {
		return "PieceMovement [origin=" + origin + ", targets=" + targets + ", meta=" + meta + "]";
	}

}
