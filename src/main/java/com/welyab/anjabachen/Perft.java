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
package com.welyab.anjabachen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

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
		Perft perft = new Perft("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1", 4);
		System.out.println(perft.board.toString(true));
		System.out.println(perft.board.getFen());
		Map<Integer, PieceMovementMeta> accumulators = new HashMap<>();
		long t1 = System.currentTimeMillis();
		perft.walk2(perft.board, 1, accumulators);
		long t2 = System.currentTimeMillis();
		PerftPrinter perftPrinter = new PerftPrinter();
		perftPrinter.print(
			accumulators
				.entrySet()
				.stream()
				.sorted((e1, e2) -> e1.getKey() - e2.getKey())
				.map(e -> e.getValue())
				.collect(Collectors.toList())
		);
		System.out.println("Time: " + (t2 - t1));
	}
	
	public void walk2(
			Board board,
			int currentDepth,
			Map<Integer, PieceMovementMeta> accumulators
	) {
		if (currentDepth <= depth) {
			MovementBag movementBag = board.getMovements();
			mergeAccumulators(currentDepth, movementBag.getMeta(), accumulators);
			for (PieceMovement pieceMovement : board.getMovements()) {
				for (MovementTarget movementTarget : pieceMovement) {
					board.move(pieceMovement.getOrigin(), movementTarget);
					walk2(board, currentDepth + 1, accumulators);
					board.undo();
				}
			}
		}
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
				if (!map.containsKey(i + 1)) {
					map.put(i + 1, new ArrayList<>());
				}
				for (PieceMovement pieceMovement : bag) {
					for (MovementTarget movementTarget : pieceMovement.getTargets()) {
						b.move(
							pieceMovement.getOrigin(),
							movementTarget
						);
						map.get(i + 1).add(new Board(board.getFen()));
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
