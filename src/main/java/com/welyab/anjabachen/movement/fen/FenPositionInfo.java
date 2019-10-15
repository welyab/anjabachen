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
package com.welyab.anjabachen.movement.fen;

import com.welyab.anjabachen.movement.Position;

/**
 * This class is a representation of the extra information extract from <code>FEN</code> string,
 * like side to move, castling availability, etc.
 * 
 * @author Welyab Paula
 */
public interface FenPositionInfo {
	
	/**
	 * The color code of the side that should perform the next movement in the game.
	 * 
	 * @return The color code.
	 */
	byte getSideToMove();
	
	/**
	 * Indicates if the castling movement is available for the white pieces in the king's side.
	 * 
	 * @return A value <code>true</code> if the movement is available, or <code>false</code> if not.
	 */
	boolean isWhiteKingCastlingAvaiable();
	
	/**
	 * Indicates if the castling movement is available for the white pieces in the queen's side.
	 * 
	 * @return A value <code>true</code> if the movement is available, or <code>false</code> if not.
	 */
	boolean isWhiteQueenCastlingAvaiable();
	
	/**
	 * Indicates if the castling movement is available for the black pieces in the king's side.
	 * 
	 * @return A value <code>true</code> if the movement is available, or <code>false</code> if not.
	 */
	boolean isBlackKingCastlingAvaiable();
	
	/**
	 * Indicates if the castling movement is available for the black pieces in the queen's side.
	 * 
	 * @return A value <code>true</code> if the movement is available, or <code>false</code> if not.
	 */
	boolean isBlackQueenCastlingAvaiable();
	
	/**
	 * If the <i>en passant</i> movement is available, this method will return the target square
	 * position piece that has been made capturing movement.
	 * 
	 * @return The target square of the <i>en passant</i> capture, or <code>null</code> if there is
	 *         no <i>en passant</i> available.
	 */
	Position getEnPassantTargetSquare();
	
	/**
	 * Retrieves the half move clock extract from <code>FEN</code> string.
	 * 
	 * @return The half move clock.
	 */
	byte getHalfMoveClock();
	
	/**
	 * Retrieves the current full move counter from <code>FEN</code> string.
	 * 
	 * @return The full move counter.
	 */
	short getFullMoveCounter();
}
