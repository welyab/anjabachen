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
package com.welyab.anjabachen.movement;

import java.util.List;

import com.welyab.anjabachen.movement.fen.PositionInfo;
import com.welyab.anjabachen.movement.fen.FenParser;

/**
 * The <code>Board</code> class is the main component for movement generation in
 * <strong>ANJABACHEN</strong>. It keeps internally a piece disposition state, also castling
 * ability flags, <i>en passant</i> captures, etc.
 * 
 * <p>
 * The <code>Board</code> class can manage movement generation also to the chess variant
 * <strong><i><a href="https://en.wikipedia.org/wiki/Chess960" alt="wikipedia article about chess
 * variant created by bobby fish name chess960">Fischer Randon Chess</a></i></strong>.
 * 
 * <p>
 * A simples usage of the <code>Board</code> class:
 * 
 * <pre>
 * var board = new Board();
 * var list = board.getMovements();
 * list.forEach(System.out::println);
 * </pre>
 * 
 * The code creates a board with pieces in the initial position, then retrieves the list of
 * available
 * movements for white pieces and finally print these movements.
 * 
 * <p>
 * Another exemple:
 * 
 * <pre>
 * var board = new Board();
 * var move = board.getRandomMove();
 * board.move(move);
 * var list = board.getMovements();
 * list.forEach(System.out::println);
 * </pre>
 * 
 * The above code creates a board and retrieve from it a random move for white pieces, then submits
 * that move to the board. The following call to the <code>getMovements</code> will retrieve the
 * available movements for the black pieces.
 * 
 * @author Welyab Paula
 */
public class Board {
	
	private final byte[][] grid;
	
	private final StateInfo info;
	
	public Board() {
		this(BoardUtil.FEN_INITIAL_POSITION);
	}
	
	public Board(String fen) {
		this(new FenParser(fen));
	}
	
	private Board(FenParser fenParser) {
		this(
			fenParser.getLocalizedPieces(),
			createStateInfo(fenParser.getPositionInfo())
		);
	}
	
	private Board(List<LocalizedPiece> pieces, StateInfo info) {
		grid = createGrid();
		this.info = info.copy();
	}
	
	private byte[][] createGrid() {
		return new byte[BoardUtil.SIZE][BoardUtil.SIZE];
	}
	
	private static StateInfo createStateInfo(PositionInfo info) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
