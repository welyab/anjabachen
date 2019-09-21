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

public class MovementOrigin {
	
	/**
	 * The piece being moved.
	 */
	private final Piece piece;
	
	/**
	 * The initial position of this piece.
	 */
	private final Position position;
	
	/**
	 * Creates a new movement origin, by specifying the position and the expected piece to be found
	 * the square.
	 *
	 * @param piece The being moved piece.
	 * @param position The origin position of this being moved piece.
	 */
	public MovementOrigin(Piece piece, Position position) {
		this.piece = piece;
		this.position = position;
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
	
	@Override
	public String toString() {
		return "Origin " + piece + ", position=" + position + "]";
	}
}
