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
package com.welyab.anjabachen.evaluation;

import java.util.Map;

import com.welyab.anjabachen.movement.Board;
import com.welyab.anjabachen.movement.BoardUtils;
import com.welyab.anjabachen.movement.Piece;
import com.welyab.anjabachen.movement.Position;

public class SimpleEvaluator implements Evaluator {
	
	private static final Map<Piece, Integer> pieceStrength = Map.ofEntries(
		Map.entry(Piece.WHITE_KING, 100),
		Map.entry(Piece.WHITE_QUEEN, 8),
		Map.entry(Piece.WHITE_ROOK, 5),
		Map.entry(Piece.WHITE_BISHOP, 3),
		Map.entry(Piece.WHITE_KNIGHT, 3),
		Map.entry(Piece.WHITE_PAWN, 1),
		Map.entry(Piece.BLACK_KING, -100),
		Map.entry(Piece.BLACK_QUEEN, -8),
		Map.entry(Piece.BLACK_ROOK, -5),
		Map.entry(Piece.BLACK_BISHOP, -3),
		Map.entry(Piece.BLACK_KNIGHT, -3),
		Map.entry(Piece.BLACK_PAWN, -1)
	);
	
	private Board board;
	
	public static void main(String[] args) {
		Board board = new Board("1kr1q3/ppp5/8/8/8/1R6/8/1K5B w - - 0 1");
		SimpleEvaluator evaluator = new SimpleEvaluator();
		evaluator.setBoard(board);
		System.out.println(evaluator.getScore());
	}
	
	private int getPieceStrength(Piece piece) {
		return pieceStrength.get(piece);
	}
	
	@Override
	public int getScore() {
		if (board == null) {
			throw new IllegalStateException("No board set");
		}
		int score = 0;
		for (int row = 0; row < BoardUtils.BOARD_SIZE; row++) {
			for (int column = 0; column < BoardUtils.BOARD_SIZE; column++) {
				Position position = Position.of(row, column);
				if (board.isNotEmpty(position)) {
					score += getPieceStrength(board.getPiece(position));
				}
			}
		}
		return score;
	}
	
	@Override
	public void setBoard(Board board) {
		this.board = board;
	}
}
