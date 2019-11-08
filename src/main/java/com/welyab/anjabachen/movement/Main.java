package com.welyab.anjabachen.movement;

import java.util.List;

public class Main {
	
	public static void main(String[] args) {
		Board board = new Board("8/2p5/1P1p1k2/K6r/5p2/1R6/4P1P1/8 w - - 1 3");
		List<LocalizedPiece> attackers = board.getAttackers(Position.of("a5"), MovementUtil.BLACK);
		System.out.println(attackers);
	}
}
