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

import com.welyab.anjabachen.movement.Board;
import com.welyab.anjabachen.movement.BoardMovements;
import com.welyab.anjabachen.movement.Movement;

public class PerftCalculator {
	
	private int depth;
	
	private String fen;
	
	public PerftCalculator(String fen, int depth) {
		this.fen = fen;
		this.depth = depth;
	}
	
	public Board getBoard() {
		return new Board(fen);
	}
	
	public PerftResult calculate() {
		Board board = new Board(fen);
		PerftResult.Builder builder = PerftResult.builder();
		walkMovementTree(builder, 1, depth, board);
		return builder.build();
	}
	
	private static void walkMovementTree(
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
				walkMovementTree(resultBuilder, depth + 1, maxDepth, board);
				board.undo();
			}
		}
	}
	
	public static void start(PerftCalculatorBootConfig createPerftCalculatorBootConfig) {
	}
}
