package com.welyab.anjabachen;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import com.welyab.anjabachen.movement.BoardMovements;
import com.welyab.anjabachen.movement.MovementTarget;
import com.welyab.anjabachen.movement.PieceMovement;
import com.welyab.anjabachen.util.BoardImageExporter;

public class Testes {
	
	public static void main(String[] args) {
		Board board = new Board("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 b - -");
		BoardMovements movements = board.getMovements();
		for (PieceMovement pieceMovement : movements) {
			for (MovementTarget movementTarget : pieceMovement) {
				board.move(pieceMovement.getOrigin(), movementTarget);
				BoardImageExporter boardImageExporter = new BoardImageExporter(board);
				Path path = Paths.get(
					"C:/Users/welyab/Desktop/images/" + UUID.randomUUID().toString() + ".png"
				);
				System.out.println(path);
				boardImageExporter.export(path);
				board.undo();
			}
		}
	}
}
