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
package com.welyab.anjabachen.movement.png;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.welyab.anjabachen.movement.Board;
import com.welyab.anjabachen.movement.Movement;
import com.welyab.anjabachen.movement.MovementUtil;

/**
 * A parser for PGN style movement list.
 * 
 * @author Welyab Paula
 */
public class MovementsParser {
	
	private final Board board;
	
	private final String movementsSan;
	
	private boolean parsed = false;
	
	private StringTokenizer tokenizer;
	
	private List<Movement> movements;
	
	private State state = State.MOVEMENT_NUMBER;
	
	public MovementsParser(String fen, String movementsSan) {
		this.board = new Board(fen);
		this.movementsSan = movementsSan;
	}
	
	public void parse() {
		if (parsed) {
			return;
		}
		
		movements = new ArrayList<>();
		tokenizer = new StringTokenizer(movementsSan, " ");
		
		while (tokenizer.hasMoreElements()) {
			state = switch (state) {
				case MOVEMENT_NUMBER -> parseMovementNumber(tokenizer.nextToken());
				case WHITE_MOVEMENT -> parseMovement(tokenizer.nextToken(), MovementUtil.WHITE);
				case BLACK_MOVEMENT -> parseMovement(tokenizer.nextToken(), MovementUtil.BLACK);
			};
		}
		
		parsed = true;
	}
	
	private State parseMovementNumber(String token) {
		if (token.contains("...")) {
			return State.BLACK_MOVEMENT;
		}
		return State.WHITE_MOVEMENT;
	}
	
	private State parseMovement(String token, byte color) {
		movements.add(MovementParser.parseMovement(token, color, board));
		return MovementUtil.isWhite(color)
				? State.BLACK_MOVEMENT
				: State.MOVEMENT_NUMBER;
	}
	
	private enum State {
		MOVEMENT_NUMBER,
		BLACK_MOVEMENT,
		WHITE_MOVEMENT
	}
}
