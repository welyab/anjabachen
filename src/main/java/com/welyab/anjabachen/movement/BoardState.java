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
public class BoardState implements Copyable<BoardState> {
	
	@SuppressWarnings("javadoc")
	private Position enPassantTargetSquare;
	
	@SuppressWarnings("javadoc")
	private short fullMoveCounter;
	
	@SuppressWarnings("javadoc")
	private byte halfMoveClock;
	
	@SuppressWarnings("javadoc")
	private byte sideToMove;
	
	@SuppressWarnings("javadoc")
	private byte castlingFlags;
	
	@SuppressWarnings("javadoc")
	public BoardState() {
		fullMoveCounter = 1;
		halfMoveClock = 0;
		castlingFlags = BoardUtil.muxCastlingFlags(true, true, true, true);
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
		return sideToMove;
	}
	
	/**
	 * Adjusts the side to move.
	 * 
	 * @param sideToMove The side to move.
	 */
	public void setSideToMove(int sideToMove) {
		this.sideToMove = (byte) sideToMove;
	}
	
	/**
	 * Retrieves the castling flags.
	 * 
	 * @return The castling flags.
	 */
	public byte getCastlingFlags() {
		return castlingFlags;
	}
	
	/**
	 * Adjusts the castling flags.
	 * 
	 * @param castlingFlags The castlings flags.
	 */
	public void setCastlingFlags(int castlingFlags) {
		this.castlingFlags = (byte) castlingFlags;
	}
	
	@Override
	public BoardState copy() {
		BoardState copy = new BoardState();
		copy.setCastlingFlags(castlingFlags);
		copy.setEnPassantTargetSquare(enPassantTargetSquare);
		copy.setFullMoveCounter(fullMoveCounter);
		copy.setHalfMoveClock(halfMoveClock);
		copy.setSideToMove(sideToMove);
		return copy;
	}
}
