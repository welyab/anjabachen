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
import java.util.List;
import java.util.TreeMap;

import com.welyab.anjabachen.Board;
import com.welyab.anjabachen.PieceMovement;
import com.welyab.anjabachen.PieceMovementMeta;

/**
 *
 *
 * @author Welyab Paula
 */
public class Perft {

	private Board board;

	private int depth;

	public Perft(String fen) {
		this(fen, 3);
	}

	public Perft(String fen, int depth) {
		this(new Board(fen), depth);
	}

	public Perft(Board board) {
		this(board, 3);
	}

	public Perft(Board board, int depth) {
		this.board = board;
		this.depth = depth;
	}

	public PieceMovementMeta execute() {
		TreeMap<Integer, List<PieceMovement>> map = new TreeMap<>();
		map.put(0, new ArrayList<>());
		while(!map.isEmpty()) {
			Integer currentDepth = map.firstKey();
			while(!map.get(currentDepth).isEmpty()) {
			}
		}
		return null;
	}
}
