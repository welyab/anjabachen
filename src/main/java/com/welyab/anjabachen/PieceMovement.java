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
 * @author Welyab Paula
 */
public class PieceMovement implements Iterable<MovementTarget> {
	
	/**
	 * The piece being moved.
	 */
	private final Piece piece;
	
	/**
	 * The initial position of this piece.
	 */
	private final Position position;
	
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
	 * @param piece The being moved piece.
	 * @param position The origin position of this being moved piece.
	 * @param targets The list of available targets for the underlying piece.
	 * @param meta The metadata associated with this movement set.
	 */
	public PieceMovement(
			Piece piece,
			Position position,
			List<MovementTarget> targets,
			PieceMovementMeta meta
	) {
		this.piece = piece;
		this.position = position;
		this.targets = targets;
		this.meta = meta;
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
	 * Retrieves the origin position of this piece.
	 *
	 * @return The position.
	 */
	public Position getPosition() {
		return position;
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
		StringBuilder builder = new StringBuilder();
		builder.append(piece.name())
			.append(String.format("[%d, %d]", position.getRow(), position.getColumn()))
			.append(" -> [");
		for (int i = 0; i < targets.size(); i++) {
			MovementTarget target = targets.get(i);
			if (i > 0) {
				builder.append(", ");
			}
			builder.append(String.format("[%d, %d]", target.getPosition().getRow(), target.getPosition().getColumn()));
		}
		builder.append("]");
		return builder.toString();
	}
}
