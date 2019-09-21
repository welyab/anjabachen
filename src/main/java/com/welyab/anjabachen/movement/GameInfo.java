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

import com.welyab.anjabachen.fen.BoardConfig;
import com.welyab.anjabachen.util.Copiable;

/**
 * The <code>GameInfo</code> class holds some informations during the game playing, as castling
 * flags and <i>en passant</i> availability, etc.
 *
 * @author Welyab Paula
 */
public class GameInfo implements Copiable<GameInfo> {
	
	/**
	 * The move side adjuster.
	 */
	int moveSideAdjuster;
	
	/**
	 * A half movement counter to be used internally by the game engine.
	 */
	int moveCounter = 0;
	
	/**
	 * The full movement counter. This value starts in one (initial game position) and is
	 * incremented after each black piece movement.
	 */
	int fullMoveCounter = 1;
	
	/**
	 * The half move counter is a auxiliary information to help help the engine detect a draw by
	 * the "Fifty-move Rule". This counter is set to zero during pawn movements or piece
	 * captures, otherwise it is incremented. When this counter is equals or equal to
	 * <code>100</code> a draw may be claimed by the player.
	 *
	 * <p>
	 * This counter is updated mainly in the {@link Board#move(MovementOrigin, MovementTarget)}
	 * method.
	 *
	 * @see Board#move(MovementOrigin, MovementTarget)
	 */
	int halfMoveCounter = 0;
	
	/**
	 * This field is used by movement generator in order to let pawn capture other pawns using
	 * <i>en passant</i> movement.
	 *
	 * <i>
	 * This field is updated in the {@link Board#move(MovementOrigin, MovementTarget)} method
	 * during each double square pawn movement, with the target position of capture for the next
	 * pawn movement. In other cases, this field is set to <code>null</code>.
	 *
	 * @see Board#move(MovementOrigin, MovementTarget)
	 * @see Board#getPawnMovements(Position, boolean)
	 */
	Position enPassantTargetSquare = null;
	
	/**
	 * The current position of the black king piece. This field is updated for each king
	 * movement.
	 */
	Position blackKingPosition;
	
	/**
	 * The current position of the white king piece. This field is updated for each king
	 * movement.
	 */
	Position whiteKingPosition;
	
	/**
	 * Castling flags indicate which castling movements are available for the pieces white/black
	 * for the king side or queen side.
	 */
	int castlingFlags;
	
	@SuppressWarnings("javadoc")
	GameInfo(BoardConfig config) {
		this(
			config.getSideToMove().isWhite() ? 0 : 1,
			0,
			config.getFullMoveCounter(),
			config.getHalfMoveCounter(),
			BoardUtils.toCastlingFlags(
				config.isBlackKingSideCastlingAvailable(),
				config.isBlackQueenSideCastlingAvailable(),
				config.isWhiteKingSideCastlingAvailable(),
				config.isWhiteQueenSideCastlingAvailable()
			),
			config.getEnPassantTargetSquare(),
			null,
			null
		);
	}
	
	@SuppressWarnings("javadoc")
	GameInfo(
			int moveSideAdjuster,
			int moveCounter,
			int fullMoveCounter,
			int halfMoveCounter,
			int castlingFlags,
			Position enPassantTargetSquare,
			Position blackKingPosition,
			Position whiteKingPosition
	) {
		this.moveSideAdjuster = moveSideAdjuster;
		this.moveCounter = moveCounter;
		this.fullMoveCounter = fullMoveCounter;
		this.halfMoveCounter = halfMoveCounter;
		this.castlingFlags = castlingFlags;
		this.enPassantTargetSquare = enPassantTargetSquare;
		this.blackKingPosition = blackKingPosition;
		this.whiteKingPosition = whiteKingPosition;
	}
	
	@Override
	public GameInfo copy() {
		return new GameInfo(
			moveSideAdjuster,
			moveCounter,
			fullMoveCounter,
			halfMoveCounter,
			castlingFlags,
			enPassantTargetSquare,
			blackKingPosition,
			whiteKingPosition
		);
	}
	
	/**
	 * Retrieves the color side that should make the next movement.
	 *
	 * @return The color black or white.
	 */
	Color getActiveColor() {
		return (moveCounter + moveSideAdjuster) % 2 == 0 ? Color.WHITE : Color.BLACK;
	}
	
	/**
	 * Set the position of the king piece, according to the given color.
	 *
	 * @param color The king color.
	 * @param position The position.
	 *
	 * @see #getKingPosition(Color)
	 * @see #isKingPresent(Color)
	 */
	void setKingPosition(Color color, Position position) {
		if (color.isWhite()) {
			whiteKingPosition = position;
		} else {
			blackKingPosition = position;
		}
	}
	
	/**
	 * Retrieves the position of the king piece, according to the given color.
	 *
	 * @param color The color of the required king.
	 *
	 * @return The position of the king piece, or <code>null</code> if there is no king of the
	 *         specific color in the board.
	 *
	 * @see #setKingPosition(Color, Position)
	 * @see #isKingPresent(Color)
	 */
	Position getKingPosition(Color color) {
		if (!isKingPresent(color)) {
			throw new KingNotPresentException(color);
		}
		return color.isWhite()
				? whiteKingPosition
				: blackKingPosition;
	}
	
	/**
	 * Evaluates if the king piece of the specific color is present in the board.
	 *
	 * @param color The color of the required king piece.
	 *
	 * @return A value <code>true</code> if the king of the specific color is present, or
	 *         <code>false</code> otherwise.
	 *
	 * @see #setKingPosition(Color, Position)
	 * @see #isKingPresent(Color)
	 */
	boolean isKingPresent(Color color) {
		return color.isWhite()
				? whiteKingPosition != null
				: blackKingPosition != null;
	}
	
	/**
	 * Retrieves the full movement counter.
	 *
	 * @return The full movement counter.
	 */
	int getFullMoveCounter() {
		return fullMoveCounter;
	}
	
	/**
	 * Evaluates if the castling movement for the queen side is available for the pieces of the
	 * specific color.
	 *
	 * @param color The color.
	 *
	 * @return A value <code>true</code> if the castling is available, or <code>false</code>
	 *         otherwise.
	 */
	boolean isQueenSideCastlingAvailable(Color color) {
		return color.isWhite()
				? BoardUtils.isWhiteQueenSideCastling(castlingFlags)
				: BoardUtils.isBlackQueenSideCastling(castlingFlags);
	}
	
	/**
	 * Evaluates if the castling movement for the king side is available for the pieces of the
	 * specific color.
	 *
	 * @param color The color.
	 *
	 * @return A value <code>true</code> if the castling is available, or <code>false</code>
	 *         otherwise.
	 */
	boolean isKingSideCastlingAvailable(Color color) {
		return color.isWhite()
				? BoardUtils.isWhiteKingSideCastling(castlingFlags)
				: BoardUtils.isBlackKingSideCastling(castlingFlags);
	}
	
	/**
	 * Invalidate the castling movement for the pieces of the specific color.
	 *
	 * @param color The color of pieces that will have the castling movement invalidated.
	 */
	void invalidateCastling(Color color) {
		invalidateKingSideCastling(color);
		invalidateQueenSideCastling(color);
	}
	
	/**
	 * Invalidate the castling movement for the queen side for the specific color.
	 *
	 * @param color The color of pieces that will have the castling movement invalidated.
	 */
	void invalidateQueenSideCastling(Color color) {
		if (color.isWhite()) {
			castlingFlags &= ~BoardUtils.WHITE_QUEEN_SIDE_CASTLING;
		} else {
			castlingFlags &= ~BoardUtils.BLACK_QUEEN_SIDE_CASTLING;
		}
	}
	
	/**
	 * Invalidate the castling movement for the king side for the specific color.
	 *
	 * @param color The color of pieces that will have the castling movement invalidated.
	 */
	void invalidateKingSideCastling(Color color) {
		if (color.isWhite()) {
			castlingFlags &= ~BoardUtils.WHITE_KING_SIDE_CASTLING;
		} else {
			castlingFlags &= ~BoardUtils.BLACK_KING_SIDE_CASTLING;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder castlingFlagsStr = new StringBuilder();
		if (BoardUtils.isWhiteKingSideCastling(castlingFlags)) {
			castlingFlagsStr.append(Piece.WHITE_KING.getLetterSymbol());
		}
		if (BoardUtils.isWhiteQueenSideCastling(castlingFlags)) {
			castlingFlagsStr.append(Piece.WHITE_QUEEN.getLetterSymbol());
		}
		if (BoardUtils.isBlackKingSideCastling(castlingFlags)) {
			castlingFlagsStr.append(Piece.BLACK_KING.getLetterSymbol());
		}
		if (BoardUtils.isBlackQueenSideCastling(castlingFlags)) {
			castlingFlagsStr.append(Piece.BLACK_QUEEN.getLetterSymbol());
		}
		if (castlingFlagsStr.length() == 0) {
			castlingFlagsStr.append('-');
		}
		return new StringBuilder()
			.append("Next: ")
			.append(getActiveColor())
			.append(", ")
			.append("Full moves: ")
			.append(fullMoveCounter)
			.append(", ")
			.append("Half moves: ")
			.append(halfMoveCounter)
			.append(", ")
			.append("En passant: ")
			.append(enPassantTargetSquare != null)
			.append(", ")
			.append("Castling: ")
			.append(castlingFlagsStr)
			.toString();
	}
}
