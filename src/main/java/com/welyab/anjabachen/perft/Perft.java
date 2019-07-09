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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import com.welyab.anjabachen.Board;
import com.welyab.anjabachen.MovementBag;
import com.welyab.anjabachen.MovementTarget;
import com.welyab.anjabachen.PieceMovement;
import com.welyab.anjabachen.PieceMovementMeta;

/**
 * Performs
 *
 * @author Welyab Paula
 */
public class Perft {

	private static final int MAX_DEPTH = 6;

	private Board board;

	private int depth;

	public Perft(Board board) {
		this(board, MAX_DEPTH);
	}

	public Perft(Board board, int depth) {
		this(board.getFen(), depth);
	}

	public Perft(String fen) {
		this(fen, MAX_DEPTH);
	}

	public Perft(String fen, int depth) {
		board = new Board(fen);
		this.depth = depth;
	}

	public static void main(String[] args) {
		Perft perft = new Perft("4k3/3pp3/8/8/8/8/8/4K3 w - - 0 1", 5);
		System.out.println(perft.board.toString(true));
		Map<Integer, PieceMovementMeta> accumulators = new HashMap<>();
		perft.walkTree(perft.board, 1, accumulators);
		accumulators
			.entrySet()
			.stream()
			.sorted((e1, e2) -> e1.getKey() - e2.getKey())
			.forEach(System.out::println);
	}

	private void walkTree(
			Board board,
			int currentDepth,
			Map<Integer, PieceMovementMeta> accumulators
	) {
		Map<Integer, List<Board>> map = new HashMap<>();
		map.put(1, new ArrayList<>());
		map.get(1).add(board);
		for (int i = 1; i <= depth; i++) {
			List<Board> boards = map.remove(i);
			for (Board b : boards) {
				MovementBag bag = b.getMovements();
				mergeAccumulators(i, bag.getMeta(), accumulators);
				map.put(i + 1, new ArrayList<>());
				for (PieceMovement pieceMovement : bag) {
					for (MovementTarget movementTarget : pieceMovement.getTargets()) {
						b.move(
							pieceMovement.getOrigin().getPosition(),
							movementTarget.getPosition(),
							movementTarget.getPiece().getType()
						);
						map.get(i + 1).add(new Board(b.getFen()));
						b.undo();
					}
				}
			}
		}
	}

	private void mergeAccumulators(
			int depth,
			PieceMovementMeta meta,
			Map<Integer, PieceMovementMeta> accumulators
	) {
		accumulators.put(
			depth,
			PieceMovementMeta
				.builder()
				.add(
					accumulators.getOrDefault(
						depth, PieceMovementMeta.empty()
					)
				)
				.add(meta)
				.build()

		);
	}

	public void onDepthResult(BiConsumer<Integer, PieceMovementMeta> metadataConsumer) {
	}
}
