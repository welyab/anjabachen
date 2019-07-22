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

import java.util.function.Consumer;

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
	 * @see FENParser
	 */
	public Perft(String fen) {
		this(fen, DEFAULT_DEPTH);
	}

	/**
	 * @param fen
	 * @param depth
	 */
	public Perft(String fen, int depth) {
		this.board = new Board(fen);
		this.depth = depth;
	}

	/**
	 *
	 * @return
	 */
	public PerftResult execute() {
		return null;
	}

	/**
	 * @param onResult A callback to notify the caller when the result is ready for a specific
	 *        depth. The first calling is for the movements metadata available for the first
	 *        movement possibilities, and the second is for the next depth, and so on.
	 */
	public void execute(Consumer<PerftResult> onResult) {
	}

	private void walk(int currentDepth) {
		if (currentDepth > depth) {
			return;
		}
	}

	/**
	 * If there is some movement tree walking running, this method notifies the process to stop.
	 * This method just returns when the underlying process stops.
	 */
	public void stop() {
	}
}
