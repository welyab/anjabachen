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
class PiecePosition {
	
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
	public PiecePosition(Piece piece, Position position) {
		this.piece = piece;
		this.position = position;
	}
	
	/**
	 * Retrieves the underlying piece.
	 *
	 * @return The piece.
	 */
	public Piece getPiece() {
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
		return "PiecePosition [piece=" + piece + ", position=" + position + "]";
	}
}