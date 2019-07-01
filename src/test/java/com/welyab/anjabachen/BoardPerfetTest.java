package com.welyab.anjabachen;

import org.junit.Test;

public class BoardPerfetTest {
	
	@Test
	public void f() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . p . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . p . . . ." + Board.NEWLINE
				/* 3 */ + "K P . . . . . r" + Board.NEWLINE
				/* 4 */ + ". R . . . p . K" + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . P . P ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, 0);
		int deepth = 6;
		PieceMovementMeta expectedMeta = PieceMovementMeta
			.builder()
			.incrementTotalMovements(4085603)
			.incrementCaptureCount(757163)
			.incrementPromotionCount(15172)
			.incrementCastlings(128013)
			.incrementEnPassantCount(1929)
			.build();
		PieceMovementMeta meta = calc(board, deepth);
		System.out.printf("Deepth:           %10d%n", deepth);
		System.out.printf(
			"Nodes:            %10d  %d%n",
			meta.getTotalMovements(),
			meta.getTotalMovements() - expectedMeta.getTotalMovements()
		);
		System.out.printf(
			"Capture count:    %10d  %d%n",
			meta.getCaptureCount(),
			meta.getCaptureCount() - expectedMeta.getCaptureCount()
		);
		System.out.printf(
			"Promotion count:  %10d  %d%n",
			meta.getPromotionCount(),
			meta.getPromotionCount() - expectedMeta.getPromotionCount()
		);
		System.out.printf(
			"Castling count:   %10d  %d%n",
			meta.getCastlingsCount(),
			meta.getCastlingsCount() - expectedMeta.getCastlingsCount()
		);
		System.out.printf(
			"En passant count: %10d  %d%n",
			meta.getEnPassantCount(),
			meta.getEnPassantCount() - expectedMeta.getEnPassantCount()
		);
	}
	
	static int b = 0;
	
	static int k = 0;
	
	private static PieceMovementMeta calc(Board board, int depth) {
		if (depth == 1) {
			PieceMovements movements = board.getMovements();
			k += movements.getMeta().getTotalMovements();
			b += movements.getMeta().getTotalMovements();
			if (b > 5000000) {
				b = 0;
				System.out.println("Current count: " + k);
			}
			return movements.getMeta();
		}
		PieceMovementMeta.Builder pieceMovementMetaBuilder = PieceMovementMeta.builder();
		for (PieceMovement pieceMovement : board.getMovements()) {
			for (MovementTarget movementTarget : pieceMovement) {
				Board boardTemp = new Board(
					board.toString2(),
					board.getMovementCount()
				);
				boardTemp.move(
					pieceMovement.getPosition(),
					movementTarget.getPosition(),
					movementTarget.getPiece().getType()
				);
				PieceMovementMeta pieceMovementMeta = calc(boardTemp, depth - 1);
				pieceMovementMetaBuilder.add(pieceMovementMeta);
			}
		}
		return pieceMovementMetaBuilder.build();
	}
}
