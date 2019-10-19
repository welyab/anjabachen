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

/**
 * A <i>localized piece</i> is a simple pair of a <code>Position</code> object and a piece code.
 * 
 * @author Welyab Paula
 */
public class LocalizedPiece {
	
	@SuppressWarnings("javadoc")
	private Position position;
	
	@SuppressWarnings("javadoc")
	private byte pieceCode;
	
	@SuppressWarnings("javadoc")
	public LocalizedPiece(Position position, int pieceCode) {
		this.position = position;
		this.pieceCode = (byte) pieceCode;
	}
	
	/**
	 * Retrieves the position of the underlying piece.
	 * 
	 * @return The position.
	 */
	public Position getPosition() {
		return position;
	}
	
	/**
	 * Retrieves the piece code.
	 * 
	 * @return The piece code.
	 */
	public byte getPieceCode() {
		return pieceCode;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + pieceCode;
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		LocalizedPiece other = (LocalizedPiece) obj;
		if (pieceCode != other.pieceCode) {
			return false;
		}
		if (position == null) {
			if (other.position != null) {
				return false;
			}
		} else if (!position.equals(other.position)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return String.format(
			"LocalizedPiece([%d,%d], %c)",
			position.getRow(),
			position.getColumn(),
			BoardUtil.pieceCodeToLetter(pieceCode)
		);
	}
}
