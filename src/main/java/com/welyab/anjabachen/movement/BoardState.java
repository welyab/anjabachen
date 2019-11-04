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
 * A <code>BoardState</code> is a set of information about game playing available for an specific
 * piece disposition. Information as castling availability, move counter, etc, are stored in this
 * class.
 * 
 * @author Welyab Paula
 */
public final class BoardState implements Copyable<BoardState> {
	
	@SuppressWarnings("javadoc")
	private short movementCounter;
	
	@SuppressWarnings("javadoc")
	private Position enPassantTargetSquare;
	
	@SuppressWarnings("javadoc")
	private short fullMoveCounter;
	
	@SuppressWarnings("javadoc")
	private byte halfMoveClock;
	
	@SuppressWarnings("javadoc")
	private byte sideToMove;
	
	@SuppressWarnings("javadoc")
	private Position whiteKingPosition;
	
	@SuppressWarnings("javadoc")
	private Position blackKingPosition;
	
	@SuppressWarnings("javadoc")
	private Position whiteQueenRookPosition;
	
	@SuppressWarnings("javadoc")
	private Position whiteKingRookPosition;
	
	@SuppressWarnings("javadoc")
	private Position blackQueenRookPosition;
	
	@SuppressWarnings("javadoc")
	private Position blackKingRookPosition;
	
	@SuppressWarnings("javadoc")
	public BoardState() {
		fullMoveCounter = 1;
		halfMoveClock = 0;
	}
	
	/**
	 * Retrieves the <i>en passant</i> target square when that movement is available.
	 * 
	 * @return enPassantTargetSquare The target square.
	 */
	public Position getEnPassantTargetSquare() {
		return enPassantTargetSquare;
	}
	
	/**
	 * Adjusts the target square after an <i>en passant</i> movement.
	 * 
	 * @param enPassantTargetSquare The target square.
	 */
	public void setEnPassantTargetSquare(Position enPassantTargetSquare) {
		this.enPassantTargetSquare = enPassantTargetSquare;
	}
	
	/**
	 * Retrieves the full move counter.
	 * 
	 * @return The full move counter value.
	 */
	public short getFullMoveCounter() {
		return fullMoveCounter;
	}
	
	/**
	 * Adjusts the full move counter.
	 * 
	 * @param fullMoveCounter The full move counter value.
	 */
	public void setFullMoveCounter(int fullMoveCounter) {
		this.fullMoveCounter = (short) fullMoveCounter;
	}
	
	/**
	 * Retrieves the half move clock.
	 * 
	 * @return The half move clock.
	 */
	public byte getHalfMoveClock() {
		return halfMoveClock;
	}
	
	/**
	 * Adjusts the half move clock.
	 * 
	 * @param halfMoveClock The half move clock.
	 */
	public void setHalfMoveClock(int halfMoveClock) {
		this.halfMoveClock = (byte) halfMoveClock;
	}
	
	/**
	 * Retrieves the side to move code.
	 * 
	 * @return The side to move.
	 */
	public byte getSideToMove() {
		int sm = (movementCounter + sideToMove) % 2;
		return sm == 0
				? MovementUtil.WHITE
				: MovementUtil.BLACK;
	}
	
	/**
	 * Adjusts the side to move.
	 * 
	 * @param color The side to move.
	 */
	public void setSideToMove(int color) {
		if (MovementUtil.isWhiteColor(color)) {
			sideToMove = 2;
			return;
		}
		if (MovementUtil.isBlackColor(color)) {
			sideToMove = 3;
			return;
		}
		throw new IllegalArgumentException(String.format("Invalid color code: %d", color));
	}
	
	/**
	 * Retrieves the castling flags.
	 * 
	 * @return The castling flags.
	 */
	public byte getCastlingFlags() {
		return MovementUtil.muxCastlingFlags(
			whiteKingRookPosition != null,
			whiteQueenRookPosition != null,
			blackKingRookPosition != null,
			blackQueenRookPosition != null
		);
	}
	
	/**
	 * Check if the king of the specific color is present in the board.
	 * 
	 * @param color The king color.
	 * 
	 * @return A value <code>true</code> if the king piece is present in the board, or
	 *         <code>false</code> if not.
	 */
	public boolean isKingPresent(int color) {
		if (color == MovementUtil.WHITE) {
			return whiteKingPosition != null;
		}
		if (color == MovementUtil.BLACK) {
			return blackKingPosition != null;
		}
		
		throw createInvalidColorException(color);
	}
	
	/**
	 * Retrieves the position of king piece of the specific color.
	 * 
	 * @param color The king color.
	 * 
	 * @return The king position, or <code>null</code> if the king piece is not present.
	 * 
	 * @throws IllegalArgumentException If the given color is invalid.
	 */
	public Position getKingPosition(int color) {
		if (color == MovementUtil.WHITE) {
			return whiteKingPosition;
		}
		if (color == MovementUtil.BLACK) {
			return blackKingPosition;
		}
		
		throw createInvalidColorException(color);
	}
	
	/**
	 * Adjusts the king position of the specific color.
	 * 
	 * @param position The new position to be set.
	 * 
	 * @param color The color of the king.
	 */
	public void setKingPosition(Position position, int color) {
		if (color == MovementUtil.WHITE) {
			whiteKingPosition = position;
		} else if (color == MovementUtil.BLACK) {
			blackKingPosition = position;
		} else {
			throw createInvalidColorException(color);
		}
	}
	
	public void setKingRookPosition(Position position, int color) {
		if (color == MovementUtil.WHITE) {
			whiteKingRookPosition = position;
			return;
		}
		
		if (color == MovementUtil.BLACK) {
			blackKingRookPosition = position;
			return;
		}
		
		throw createInvalidColorException(color);
	}
	
	public void setQueenRookPosition(Position position, int color) {
		if (color == MovementUtil.WHITE) {
			whiteQueenRookPosition = position;
			return;
		}
		
		if (color == MovementUtil.BLACK) {
			blackQueenRookPosition = position;
			return;
		}
		
		throw createInvalidColorException(color);
	}
	
	public Position getKingRookPosition(int color) {
		if (color == MovementUtil.WHITE) {
			return whiteKingRookPosition;
		}
		
		if (color == MovementUtil.BLACK) {
			return blackKingRookPosition;
		}
		
		throw createInvalidColorException(color);
	}
	
	public Position getQueenRookPosition(int color) {
		if (color == MovementUtil.WHITE) {
			return whiteQueenRookPosition;
		}
		
		if (color == MovementUtil.BLACK) {
			return blackQueenRookPosition;
		}
		
		throw createInvalidColorException(color);
	}
	
	public boolean isKingSideCastlingAvaiable(int color) {
		if (color == MovementUtil.WHITE) {
			return whiteKingRookPosition != null;
		} else if (color == MovementUtil.BLACK) {
			return blackKingRookPosition != null;
		} else {
			throw createInvalidColorException(color);
		}
	}
	
	public boolean isQueenSideCastlingAvaiable(int color) {
		if (color == MovementUtil.WHITE) {
			return whiteQueenRookPosition != null;
		} else if (color == MovementUtil.BLACK) {
			return blackQueenRookPosition != null;
		} else {
			throw createInvalidColorException(color);
		}
	}
	
	@SuppressWarnings("javadoc")
	private static IllegalArgumentException createInvalidColorException(int color) {
		return new IllegalArgumentException(String.format("Invalid color code: %d", color));
	}
	
	@Override
	public BoardState copy() {
		BoardState copy = new BoardState();
		copy.setMovementCounter(movementCounter);
		copy.whiteQueenRookPosition = whiteQueenRookPosition;
		copy.whiteKingRookPosition = whiteKingRookPosition;
		copy.blackQueenRookPosition = blackQueenRookPosition;
		copy.blackKingRookPosition = blackKingRookPosition;
		copy.enPassantTargetSquare = enPassantTargetSquare;
		copy.fullMoveCounter = fullMoveCounter;
		copy.halfMoveClock = halfMoveClock;
		copy.sideToMove = sideToMove;
		copy.whiteKingPosition = whiteKingPosition;
		copy.blackKingPosition = blackKingPosition;
		return copy;
	}
	
	public void resetHalMoveClock() {
		halfMoveClock = 0;
	}
	
	public void incrementHalfMoveClock() {
		halfMoveClock++;
	}
	
	public void incrementFullMoveClock() {
		fullMoveCounter++;
	}
	
	public void setMovementCounter(short movementCounter) {
		this.movementCounter = movementCounter;
	}
	
	public short getMovementCounter() {
		return movementCounter;
	}
	
	public void incrementMovementCounter() {
		movementCounter++;
	}
	
	public void invalidateKingSideCastlingFlags(byte color) {
		if (MovementUtil.isWhiteColor(color)) {
			whiteKingRookPosition = null;
			return;
		}
		
		if (MovementUtil.isBlackColor(color)) {
			blackKingRookPosition = null;
			return;
		}
		
		throw createInvalidColorException(color);
	}
	
	public void invalidateQueenSideCastlingFlags(byte color) {
		if (MovementUtil.isWhiteColor(color)) {
			whiteQueenRookPosition = null;
			return;
		}
		
		if (MovementUtil.isBlackColor(color)) {
			blackQueenRookPosition = null;
			return;
		}
		
		throw createInvalidColorException(color);
	}
	
	public void invalidateCastlingFlags(byte color) {
		invalidateKingSideCastlingFlags(color);
		invalidateQueenSideCastlingFlags(color);
	}
	
	public void set(BoardState boardState) {
		movementCounter = boardState.movementCounter;
		enPassantTargetSquare = boardState.enPassantTargetSquare;
		fullMoveCounter = boardState.fullMoveCounter;
		halfMoveClock = boardState.halfMoveClock;
		sideToMove = boardState.sideToMove;
		whiteKingPosition = boardState.whiteKingPosition;
		blackKingPosition = boardState.blackKingPosition;
		whiteQueenRookPosition = boardState.whiteQueenRookPosition;
		whiteKingRookPosition = boardState.whiteKingRookPosition;
		blackQueenRookPosition = boardState.blackQueenRookPosition;
		blackKingRookPosition = boardState.blackKingRookPosition;
	}
	
	@Override
	public int hashCode() {
		int hash = 31;
		hash *= hashCode(enPassantTargetSquare);
		hash *= hashCode(fullMoveCounter);
		hash *= hashCode(halfMoveClock);
		hash *= hashCode(sideToMove);
		hash *= hashCode(whiteQueenRookPosition);
		hash *= hashCode(whiteKingRookPosition);
		hash *= hashCode(blackQueenRookPosition);
		hash *= hashCode(blackKingRookPosition);
		hash *= hashCode(whiteKingPosition);
		hash *= hashCode(blackKingPosition);
		return hash;
	}
	
	private int hashCode(Object obj) {
		if (obj == null) {
			return 1;
		}
		return obj.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BoardState)) {
			return false;
		}
		BoardState otherState = (BoardState) obj;
		return equals(this.enPassantTargetSquare, otherState.enPassantTargetSquare)
				&& equals(this.fullMoveCounter, otherState.fullMoveCounter)
				&& equals(this.halfMoveClock, otherState.halfMoveClock)
				&& equals(this.sideToMove, otherState.sideToMove)
				&& equals(this.whiteQueenRookPosition, otherState.whiteQueenRookPosition)
				&& equals(this.whiteKingRookPosition, otherState.whiteKingRookPosition)
				&& equals(this.blackQueenRookPosition, otherState.blackQueenRookPosition)
				&& equals(this.blackKingRookPosition, otherState.blackKingRookPosition)
				&& equals(this.whiteKingPosition, otherState.whiteKingPosition)
				&& equals(this.blackKingPosition, otherState.blackKingPosition);
	}
	
	@SuppressWarnings(
		{
			"javadoc",
			"squid:S1142"
		}
	)
	private boolean equals(Object obj1, Object obj2) {
		if (obj1 == null && obj2 == null) {
			return true;
		}
		if (obj1 == obj2) {
			return true;
		}
		if (obj1 != null) {
			return obj1.equals(obj2);
		}
		return obj2.equals(obj1);
	}
	
	@Override
	public String toString() {
		return "BoardState [enPassantTargetSquare=" + enPassantTargetSquare + ", fullMoveCounter=" + fullMoveCounter
				+ ", halfMoveClock=" + halfMoveClock + ", sideToMove=" + sideToMove + ", whiteKingPosition="
				+ whiteKingPosition + ", blackKingPosition=" + blackKingPosition + ", whiteQueenRookPosition="
				+ whiteQueenRookPosition + ", whiteKingRookPosition=" + whiteKingRookPosition
				+ ", blackQueenRookPosition=" + blackQueenRookPosition + ", blackKingRookPosition="
				+ blackKingRookPosition + "]";
	}
}
