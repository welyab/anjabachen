/*
 * Copyright (C) 2019 Welyab da Silva Paula
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.welyab.anjabachen.movement.perft;

import java.io.PrintStream;

import com.welyab.anjabachen.movement.Board;
import com.welyab.anjabachen.movement.MovementTarget;
import com.welyab.anjabachen.movement.Movements;
import com.welyab.anjabachen.movement.PieceMovements;

/**
 * This class performs PERFT calculations and movement path enumerations.
 * 
 * @author Welyab Paula
 */
public final class PerftCalculator {
	
	@SuppressWarnings("javadoc")
	private PerftCalculator() {
	}
	
	/**
	 * Executes a PERFT calculation over given position (the <code>fen</code> parameter).
	 * 
	 * @param fen The initial position.
	 * @param depth How deep the calculation
	 * @param extractAllMetadata If all metadata must be extracted. The movement generator already
	 *        extracts some information about movements:
	 * 
	 *        <ul>
	 *        <li>capture
	 *        <li><i>en passant</i>
	 *        <li>castling
	 *        <li>promotion
	 *        </ul>
	 * 
	 *        If <code>extractAllMetadata</code> parameter is informed <code>true</code>, the follow
	 *        information will also extracted:
	 * 
	 *        <ul>
	 *        <li>check
	 *        <li>discovery check
	 *        <li>double check
	 *        <li>checkmate
	 *        <li>stalemate
	 *        </ul>
	 * 
	 * @return The PERFT results.
	 */
	public static PerftResult perft(
		String fen,
		int depth,
		boolean extractAllMetadata
	) {
		PerftResult.Builder resultBuilder = PerftResult.builder();
		Board board = new Board(fen);
		perftWalker(
			board,
			1,
			depth,
			extractAllMetadata,
			resultBuilder
		);
		return resultBuilder.build();
	}
	
	@SuppressWarnings("javadoc")
	private static void perftWalker(
		Board board,
		int currentDepth,
		int maxDepth,
		boolean extractAllMetadata,
		PerftResult.Builder perftResBuilder
	) {
		Movements movements = board.getMovements(extractAllMetadata);
		perftResBuilder.addMetadata(currentDepth, movements.getMetadata());
		if (currentDepth + 1 <= maxDepth) {
			for (int i = 0; i < movements.getOriginCount(); i++) {
				PieceMovements pieceMovements = movements.getPieceMovements(i);
				for (int j = 0; j < pieceMovements.getTargertsCount(); j++) {
					MovementTarget movementTarget = pieceMovements.getTarget(j);
					board.move(pieceMovements.getOrigin(), movementTarget);
					perftWalker(
						board,
						currentDepth + 1,
						maxDepth,
						extractAllMetadata,
						perftResBuilder
					);
					board.undo();
				}
			}
		}
	}
	
	/**
	 * Generate all moves until reach the <i>divide depth</i>.
	 * 
	 * <p>
	 * Usage: lets take the follow position:
	 * 
	 * <pre>
	 * "8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -"
	 * 
	 * ┌───┬───┬───┬───┬───┬───┬───┬───┐
	 * │   │   │   │   │   │   │   │   │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │ p │   │   │   │   │   │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │   │ p │   │   │   │   │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │ K │ P │   │   │   │   │   │ r │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │ R │   │   │   │ p │   │ k │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │   │   │   │   │   │   │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │   │   │ P │   │ P │   │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │   │   │   │   │   │   │
	 * └───┴───┴───┴───┴───┴───┴───┴───┘
	 * white to play
	 * </pre>
	 * 
	 * There are 14 possible movement for white. Calling the divide function for depth 4 and divide
	 * point equals 1 will generate the output:
	 * 
	 * <pre>
	 * a5a6 -> 3653
	 * a5a4 -> 3394
	 * b4b3 -> 3658
	 * b4b2 -> 3328
	 * b4b1 -> 4199
	 * b4c4 -> 3797
	 * b4d4 -> 3622
	 * b4e4 -> 3391
	 * b4f4 -> 606
	 * b4a4 -> 3019
	 * e2e3 -> 3107
	 * e2e4 -> 2748
	 * g2g3 -> 1014
	 * g2g4 -> 3702
	 * </pre>
	 * 
	 * The divide function shown 14 movements and total number of nodes until the depth 4, starting
	 * in the specific movement. Calling divide for the same position, but with divide point equals
	 * 2, will generate an output like that:
	 * 
	 * <pre>
	 * a5a6 c7c6 -> 250
	 * a5a6 c7c5 -> 213
	 * a5a6 d6d5 -> 182
	 * ...
	 * ... 185 other paths
	 * ...
	 * g2g4 h4g3 -> 289
	 * g2g4 h4h3 -> 237
	 * g2g4 h4g4 -> 238
	 * </pre>
	 * 
	 * The above enumeration shows all 14 movements for white, and for each, a response movement for
	 * black pieces.
	 * 
	 * @param fen The initial board position.
	 * @param depth The total depth; must be <code>&gt;= 2</code>.
	 * @param divideDepth The depth where movement enumeration should stop; must be in the interval
	 *        <code>1 &lt;= divideDepth &lt; depth</code>.
	 */
	public static void divide(String fen, int depth, int divideDepth) {
		divide(fen, depth, divideDepth, System.out);
	}
	
	/**
	 * Generate all moves until reach the <i>divide depth</i>.
	 * 
	 * <p>
	 * Usage: lets take the follow position:
	 * 
	 * <pre>
	 * "8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -"
	 * 
	 * ┌───┬───┬───┬───┬───┬───┬───┬───┐
	 * │   │   │   │   │   │   │   │   │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │ p │   │   │   │   │   │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │   │ p │   │   │   │   │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │ K │ P │   │   │   │   │   │ r │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │ R │   │   │   │ p │   │ k │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │   │   │   │   │   │   │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │   │   │ P │   │ P │   │
	 * ├───┼───┼───┼───┼───┼───┼───┼───┤
	 * │   │   │   │   │   │   │   │   │
	 * └───┴───┴───┴───┴───┴───┴───┴───┘
	 * white to play
	 * </pre>
	 * 
	 * There are 14 possible movement for white. Calling the divide function for depth 4 and divide
	 * point equals 1 will generate the output:
	 * 
	 * <pre>
	 * a5a6 -> 3653
	 * a5a4 -> 3394
	 * b4b3 -> 3658
	 * b4b2 -> 3328
	 * b4b1 -> 4199
	 * b4c4 -> 3797
	 * b4d4 -> 3622
	 * b4e4 -> 3391
	 * b4f4 -> 606
	 * b4a4 -> 3019
	 * e2e3 -> 3107
	 * e2e4 -> 2748
	 * g2g3 -> 1014
	 * g2g4 -> 3702
	 * </pre>
	 * 
	 * The divide function shown 14 movements and total number of nodes until the depth 4, starting
	 * in the specific movement. Calling divide for the same position, but with divide point equals
	 * 2, will generate an output like that:
	 * 
	 * <pre>
	 * a5a6 c7c6 -> 250
	 * a5a6 c7c5 -> 213
	 * a5a6 d6d5 -> 182
	 * ...
	 * ... 185 other paths
	 * ...
	 * g2g4 h4g3 -> 289
	 * g2g4 h4h3 -> 237
	 * g2g4 h4g4 -> 238
	 * </pre>
	 * 
	 * The above enumeration shows all 14 movements for white, and for each, a response movement for
	 * black pieces.
	 * 
	 * @param fen The initial board position.
	 * @param depth The total depth; must be <code>&gt;= 2</code>.
	 * @param divideDepth The depth where movement enumeration should stop; must be in the interval
	 *        <code>1 &lt;= divideDepth &lt; depth</code>.
	 * @param printStream The output stream.
	 */
	public static void divide(String fen, int depth, int divideDepth, PrintStream printStream) {
		if (depth < 2) {
			throw new IllegalArgumentException("The 'depth' parameter must be >= 2");
		}
		if (divideDepth < 1 || divideDepth > depth) {
			throw new IllegalArgumentException("The 'divideDepth' parameter must be >= 1 and <= 'depth'");
		}
		Board board = new Board(fen);
		printStream.println("Divide Function - AN.JA.BA.CH.EN");
		printStream.println("FEN: " + fen);
		printStream.println("Depth: " + depth);
		printStream.println("Divide point: " + divideDepth);
		printStream.println(board);
		String[][] path = new String[depth][2];
		divideWalker(
			board,
			1,
			path,
			divideDepth,
			depth,
			printStream
		);
		printStream.flush();
	}
	
	@SuppressWarnings(
		{
			"javadoc",
			"squid:S106"
		}
	)
	private static void divideWalker(
		Board board,
		int currentDepth,
		String[][] path,
		int divideDepth,
		int maxDepth,
		PrintStream printStream
	) {
		if (currentDepth == divideDepth) {
			for (int i = 0; i < currentDepth - 1; i++) {
				printStream.print(path[i][0]);
				printStream.print(path[i][1]);
				printStream.print(' ');
			}
			printStream.print("-> ");
			printStream.println(countNodes(board, currentDepth, maxDepth));
			return;
		}
		
		if (currentDepth + 1 <= maxDepth) {
			Movements movements = board.getMovements(false);
			for (int i = 0; i < movements.getOriginCount(); i++) {
				PieceMovements pieceMovements = movements.getPieceMovements(i);
				for (int j = 0; j < pieceMovements.getTargertsCount(); j++) {
					MovementTarget movementTarget = pieceMovements.getTarget(j);
					path[currentDepth - 1][0] = pieceMovements.getOrigin().getNotation();
					path[currentDepth - 1][1] = movementTarget.getPosition().getNotation();
					board.move(pieceMovements.getOrigin(), movementTarget);
					divideWalker(board, currentDepth + 1, path, divideDepth, maxDepth, printStream);
					board.undo();
				}
			}
		}
	}
	
	@SuppressWarnings("javadoc")
	private static long countNodes(Board board, int currentDepth, int maxDepth) {
		Movements movements = board.getMovements(false);
		if (currentDepth == maxDepth) {
			return movements.getMetadata().getNodes();
		}
		long counter = 0;
		for (int i = 0; i < movements.getOriginCount(); i++) {
			PieceMovements pieceMovements = movements.getPieceMovements(i);
			for (int j = 0; j < pieceMovements.getTargertsCount(); j++) {
				MovementTarget movementTarget = pieceMovements.getTarget(j);
				board.move(pieceMovements.getOrigin(), movementTarget);
				counter += countNodes(board, currentDepth + 1, maxDepth);
				board.undo();
			}
		}
		return counter;
	}
}
