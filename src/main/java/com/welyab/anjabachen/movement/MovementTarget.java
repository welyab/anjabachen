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

public class MovementTarget {
	
	private Position position;
	
	private byte pieceCode;
	
	private short flags;
	
	public MovementTarget(Position position, byte pieceCode, short flags) {
		this.position = position;
		this.pieceCode = pieceCode;
		this.flags = flags;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public byte getPieceCode() {
		return pieceCode;
	}
	
	public short getFlags() {
		return flags;
	}
	
	@Override
	public String toString() {
		return String.format(
			"%c%s",
			MovementUtil.pieceCodeToLetter(pieceCode),
			position.getNotation()
		);
	}
}
