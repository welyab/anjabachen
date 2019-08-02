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

import java.util.HashMap;
import java.util.Map;

import com.welyab.anjabachen.fen.FenParser;

/**
 * @author Welyab Paula
 */
public class Perft {
	
	public static final int DEFAULT_DEPTH = 3;
	
	private final Board board;
	
	private final int depth;
	
	/**
	 *
	 * @param fen The <code>FEN</code> representation for the board.
	 *
	 * @see FenParser
	 */
	public Perft(String fen) {
		this(fen, DEFAULT_DEPTH);
	}
	
	/**
	 * @param fen
	 * @param depth
	 */
	public Perft(String fen, int depth) {
		board = new Board(fen);
		this.depth = depth;
	}
	
	public void divide() {
	}
	
	public Map<Integer, PieceMovementMeta> perft() {
		return perft(true);
	}
	
	public Map<Integer, PieceMovementMeta> perft(boolean extractExtraFlags) {
		var metas = new HashMap<Integer, PieceMovementMeta>();
		Board boardCopy = board.copy();
		walk(
			boardCopy,
			1,
			metas,
			extractExtraFlags
		);
		return metas;
	}
	
	private void walk(
			Board board,
			int currentDepth,
			Map<Integer, PieceMovementMeta> metas,
			boolean extractExtraFlags
	) {
		if (currentDepth <= depth) {
			MovementBag movementBag = board.getMovements();
			mergeMetas(currentDepth, movementBag.getMeta(), metas);
			for (PieceMovement pieceMovement : movementBag) {
				for (MovementTarget movementTarget : pieceMovement) {
					board.move(pieceMovement.getOrigin(), movementTarget);
					walk(board, currentDepth + 1, metas, extractExtraFlags);
					board.undo();
				}
			}
		}
	}
	
	private void mergeMetas(
			int depth,
			PieceMovementMeta meta,
			Map<Integer, PieceMovementMeta> metas
	) {
		metas.put(
			depth,
			PieceMovementMeta
				.builder()
				.add(
					meta
				)
				.add(
					metas.getOrDefault(
						depth,
						PieceMovementMeta.empty()
					)
				)
				.build()
		);
	}
	
	/**
	 * If there is some movement tree walking running, this method notifies the
	 * process to stop.
	 * This method just returns when the underlying process stops.
	 */
	public void stop() {
	}
	
	/**
	 * Retrieves the underlying board of this perft generator. The returned
	 * board is a copy of the
	 * internal board; you may change the state of the board and that
	 * modifications will not reflect
	 * in the results of this perft generator.
	 *
	 * @return A copy of the chess board.
	 */
	public Board getBoard() {
		return board.copy();
	}
}
