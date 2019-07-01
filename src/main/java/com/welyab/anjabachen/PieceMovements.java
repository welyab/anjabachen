package com.welyab.anjabachen;

import java.util.Iterator;
import java.util.List;

public class PieceMovements implements Iterable<PieceMovement> {
	
	private List<PieceMovement> movements;
	
	private PieceMovementMeta meta;
	
	public PieceMovements(List<PieceMovement> movements, PieceMovementMeta meta) {
		this.movements = movements;
		this.meta = meta;
	}
	
	public boolean isEmpty() {
		return movements.isEmpty();
	}
	
	public PieceMovementMeta getMeta() {
		return meta;
	}
	
	@Override
	public Iterator<PieceMovement> iterator() {
		return movements.iterator();
	}
}
