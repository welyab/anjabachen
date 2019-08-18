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

import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;

import com.welyab.anjabachen.Board;
import com.welyab.anjabachen.Movement;
import com.welyab.anjabachen.MovementBag;
import com.welyab.anjabachen.MovementTarget;
import com.welyab.anjabachen.PieceMovement;
import com.welyab.anjabachen.PieceMovementMeta;

public class PerftCalculator {
	
	/** Symbolic value for the max depth supported by the perft calculator. */
	private static final int MAX_DEPTH_DEFAULT = Integer.MAX_VALUE;
	
	/** The tread pool where movement tree walkers will run. */
	private final ExecutorService executor = Executors.newFixedThreadPool(14);
	
	private final ConcurrentHashMap<Integer, BreadthWalker> walkers = new ConcurrentHashMap<>();
	
	private final ConcurrentHashMap<Integer, Future<?>> futures = new ConcurrentHashMap<>();
	
	/** The board where the movement generation will act. */
	private final Board board;
	
	/** The maximum depth reached by movement generation. */
	private final int maxDepth;
	
	/**
	 * Creates a perft calculator to act over the given board.
	 * 
	 * <p>
	 * The board will be copied internally and the referenced board will not be modified.
	 * 
	 * @param board The board.
	 */
	public PerftCalculator(Board board) {
		this(board.getFen());
	}
	
	/**
	 * Creates a perft calculator to act over the board represented by given FEN string.
	 * 
	 * @param fen The FEN string.
	 */
	public PerftCalculator(String fen) {
		this(fen, MAX_DEPTH_DEFAULT);
	}
	
	/**
	 * Creates a perft calculator to act over the given board. During movement generation, the perft
	 * calculator will reach the maximum depth indicated by <code>maxDepth</code> parameter.
	 * 
	 * <p>
	 * The board will be copied internally and the referenced board will not be modified.
	 * 
	 * @param board The board.
	 * @param maxDepth The maximum depth.
	 */
	public PerftCalculator(Board board, int maxDepth) {
		this(board.getFen(), maxDepth);
	}
	
	/**
	 * Creates a perft calculator to act over the board represented by given FEN string. During
	 * movement generation, the perft calculator will reach the maximum depth indicated by
	 * <code>maxDepth</code> parameter.
	 * 
	 * @param fen The FEN string.
	 * @param maxDepth The maximum depth.
	 */
	public PerftCalculator(String fen, int maxDepth) {
		board = new Board(fen);
		this.maxDepth = maxDepth;
	}
	
	/**
	 * Returns a copy of the underlying board used by this perft calculator in its initial
	 * disposition.
	 * 
	 * @return The board.
	 */
	public Board getBoard() {
		return board.copy();
	}
	
	public static void main(String[] args) {
		new PerftCalculator("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -", 6)
			.execute();
	}
	
	public Map<Integer, PieceMovementMeta> execute() {
		MovementBag bag = board.getMovements();
		System.out.println(1 + " - " + bag.getMeta());
		BreadthWalker walker = getWalker(2, true);
		Board boardCopy = board.copy();
		for (PieceMovement pieceMovement : bag) {
			for (MovementTarget target : pieceMovement) {
				walker.add(
					new BoardMovement(
						boardCopy,
						new Movement(
							pieceMovement.getOrigin(),
							target
						)
					)
				);
			}
		}
		walker.finish();
		return null;
	}
	
	/**
	 * A simple class to join <code>board</code> and <code>movement</code> instances.
	 * 
	 * @author Welyab Paula
	 */
	private static class BoardMovement {
		
		@SuppressWarnings("javadoc")
		final Board board;
		
		@SuppressWarnings("javadoc")
		final Movement movement;
		
		@SuppressWarnings("javadoc")
		BoardMovement(Board board, Movement movement) {
			this.board = board;
			this.movement = movement;
		}
		
		@SuppressWarnings("javadoc")
		void move() {
			board.move(movement);
		}
		
		@SuppressWarnings("javadoc")
		void undo() {
			board.undo();
		}
	}
	
	private BreadthWalker getWalker(int depth, boolean extractExtraFlags) {
		return walkers.computeIfAbsent(
			depth,
			iDepth -> {
				BreadthWalker breadthWalker = new BreadthWalker(
					iDepth,
					extractExtraFlags
				);
				Future<?> future = executor.submit(breadthWalker);
				futures.put(iDepth, future);
				return breadthWalker;
			}
		);
	}
	
	private void finishDepth(int depth) {
	}
	
	private class BreadthWalker implements Runnable {
		
		final Object lock = new Object();
		
		PieceMovementMeta meta = PieceMovementMeta.empty();
		
		final BlockingDeque<BoardMovement> queue = new LinkedBlockingDeque<>();
		
		boolean finished = false;
		
		final int depth;
		
		final boolean extractExtraFlags;
		
		@SuppressWarnings("javadoc")
		BreadthWalker(int depth, boolean extractExtraFlags) {
			this.depth = depth;
			this.extractExtraFlags = extractExtraFlags;
		}
		
		@Override
		public void run() {
			try {
				while (!finished || !queue.isEmpty()) {
					BoardMovement boardMovement = queue.takeFirst();
					boardMovement.move();
					Board boardCopy = boardMovement.board.copy();
					MovementBag bag = boardMovement.board.getMovements(extractExtraFlags);
					meta = PieceMovementMeta.builder().add(meta).add(bag.getMeta()).build();
					if (depth + 1 <= maxDepth) {
						BreadthWalker walker = getWalker(depth + 1, extractExtraFlags);
						for (PieceMovement pieceMovement : bag) {
							for (MovementTarget target : pieceMovement) {
								walker.add(
									new BoardMovement(
										boardCopy,
										new Movement(
											pieceMovement.getOrigin(),
											target
										)
									)
								);
							}
						}
					}
					boardMovement.undo();
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			System.out.println(depth + " - " + meta);
			getWalker(depth + 1, extractExtraFlags).finish();
		}
		
		void add(BoardMovement boardMovement) {
			queue.add(boardMovement);
		}
		
		void finish() {
			synchronized (lock) {
				finished = true;
				futures.get(depth).cancel(true);
			}
		}
	}
}
