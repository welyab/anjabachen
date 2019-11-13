package com.welyab.anjabachen.movement.perft;

import com.welyab.anjabachen.movement.MovementMetadata;

public class Main {
	
	public static void main(String[] args) {
		String[] fens = new String[] {
			// "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -",
			"8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -"
		};
		int iterations = 1;
		int maxDepth = 6;
		long t1 = System.currentTimeMillis();
		for (int i = 0; i < iterations; i++) {
			for (String fen : fens) {
				PerftResult perft = PerftCalculator.perft(fen, maxDepth, true);
				printResults(fen, perft.getMetadata(maxDepth));
			}
		}
		long t2 = System.currentTimeMillis();
		System.out.printf("Time spent: %.2f", (t2 - t1) / 1000.0);
	}
	
	private static void printResults(String fen, MovementMetadata metadata) {
		for (int field : MovementMetadata.getFields()) {
			System.out.printf(
				"%s\t%s\t",
				MovementMetadata.getFieldName(field),
				metadata.getValue(field)
			);
		}
		System.out.println();
	}
}
