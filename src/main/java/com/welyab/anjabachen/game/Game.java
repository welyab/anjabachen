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
package com.welyab.anjabachen.game;

import com.welyab.anjabachen.movement.Board;

public class Game {
	
	private Board board;
	
	private Player white;
	
	private Player black;
	
	public Game(Board board, Player white, Player black) {
		this(board.getFen(), white, black);
	}
	
	public Game(String fen, Player white, Player black) {
		this.board = new Board(fen);
		this.white = white;
		this.black = black;
	}
}