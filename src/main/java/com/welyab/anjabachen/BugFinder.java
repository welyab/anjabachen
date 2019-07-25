package com.welyab.anjabachen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

public class BugFinder {

	public static void main(String[] args) throws IOException {
		// new Perft("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -", 5).execute();
		// System.out.println("FIM");
		// if (true) {
		// return;
		// }

		Set<String> expected = Files
			.lines(Paths.get("/home/welyab/Desktop/moves.txt"))
			.filter(l -> l.startsWith("6."))
			.map(l -> l.substring(3, 27))
			.collect(Collectors.toSet());

		Set<String> calculated = Files
			.lines(Paths.get("/home/welyab/Desktop/calculatedmoves.txt"))
			.filter(l -> !l.isEmpty())
			.collect(Collectors.toSet());

		System.out.println("Expected size: " + expected.size());
		System.out.println("Calculated size: " + calculated.size());

		calculated.removeAll(expected);
		calculated.forEach(System.out::println);
	}
}
