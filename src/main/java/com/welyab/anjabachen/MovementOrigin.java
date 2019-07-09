package com.welyab.anjabachen;

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
