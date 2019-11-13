package com.welyab.anjabachen;

import com.welyab.anjabachen.movement.MovementUtil;
import com.welyab.anjabachen.movement.Position;

public class SimpleMovement {
	
	private final Position origin;
	
	private final Position target;
	
	private final byte targetPiece;
	
	public SimpleMovement(Position origin, Position target, byte targetPiece) {
		this.origin = origin;
		this.target = target;
		this.targetPiece = targetPiece;
	}
	
	public Position getOrigin() {
		return origin;
	}
	
	public Position getTarget() {
		return target;
	}
	
	public byte getTargetPiece() {
		return targetPiece;
	}
	
	@Override
	public String toString() {
		return origin + " -> " + target + "=" + MovementUtil.pieceCodeToLetter(targetPiece);
	}
}
