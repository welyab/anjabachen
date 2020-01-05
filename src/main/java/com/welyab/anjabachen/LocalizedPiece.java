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

public class LocalizedPiece {
	
	private final Piece piece;
	
	private final Position position;
	
	public LocalizedPiece(Piece piece, Position position) {
		this.piece = piece;
		this.position = position;
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public Position getPosition() {
		return position;
	}
	
	@Override
	public String toString() {
		return String.format("%c%s", piece.getLetter(), position.getNotation());
	}
}
