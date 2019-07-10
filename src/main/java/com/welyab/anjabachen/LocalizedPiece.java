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
 * A simple pair of <code>Piece</code> and <code>Position</code> objects.
 *
 * @author Welyab Paula
 *
 * @see Board#Board(java.util.List, BoardConfig)
 */
class LocalizedPiece {

	/** The piece. */
	private final Piece piece;

	/** The position. */
	private final Position position;

	/**
	 * Creates a new instance.
	 *
	 * @param piece The piece.
	 * @param position The position.
	 */
	public LocalizedPiece(Piece piece, Position position) {
		this.piece = piece;
		this.position = position;
	}

	public boolean isEmpty() {
		return piece == null;
	}

	public boolean isNotEmpty() {
		return !isEmpty();
	}

	/**
	 * Retrieves the underlying piece.
	 *
	 * @return The piece.
	 */
	public Piece getPiece() {
		if (isEmpty()) {
			throw new EmptySquareException(
				position.getRow(),
				position.getColumn()
			);
		}
		return piece;
	}

	/**
	 * Retrieves the underlying position.
	 *
	 * @return The position.
	 */
	public Position getPosition() {
		return position;
	}

	@Override
	public String toString() {
		return "LocalizedPiece [piece=" + piece + ", position=" + position + "]";
	}
}
