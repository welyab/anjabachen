package com.welyab.anjabachen;

public class InvalidPosition extends ChessException {

	private final int row;

	private final int column;

	public InvalidPosition(int row, int column) {
		super(String.format("The position [%d, %d] is invalid", row, column));
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
}
