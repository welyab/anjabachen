package com.welyab.anjabachen.movement;

public class LocalizedPiece {
	
	private Position position;
	
	private byte pieceCode;
	
	public LocalizedPiece(Position position) {
		this(position, 0);
	}
	
	public LocalizedPiece(Position position, int pieceCode) {
		this.position = position;
		this.pieceCode = (byte) pieceCode;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public byte getPieceCode() {
		return pieceCode;
	}
}
