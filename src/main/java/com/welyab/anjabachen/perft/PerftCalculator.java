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
package com.welyab.anjabachen.perft;

import java.util.Iterator;

import com.welyab.anjabachen.Board;
import com.welyab.anjabachen.movement.BoardMovements;
import com.welyab.anjabachen.movement.Movement;

public class PerftCalculator {
	
	public static void main(String[] args) {
		Board board = new Board("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -");
		System.out.println(board.toString(true));
		PerftResult.Builder resultBuilder = PerftResult.builder();
		long t1 = System.currentTimeMillis();
		execute(resultBuilder, 1, 6, board);
		long t2 = System.currentTimeMillis();
		PerftResult perftResult = resultBuilder.build();
		System.out.println(perftResult);
		System.out.println("Time: " + (t2 - t1));
	}
	
	private static void execute(
			PerftResult.Builder resultBuilder,
			int depth,
			int maxDepth,
			Board board
	) {
		BoardMovements bag = board.getMovements();
		resultBuilder.addValues(depth, bag.getMetadata());
		if (depth + 1 <= maxDepth) {
			for (Iterator<Movement> it = bag.movementIterator(); it.hasNext();) {
				Movement movement = it.next();
				board.move(movement);
				execute(resultBuilder, depth + 1, maxDepth, board);
				board.undo();
			}
		}
	}
}
