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

import java.util.Scanner;

import com.welyab.anjabachen.Board;
import com.welyab.anjabachen.Color;
import com.welyab.anjabachen.BoardUtils;
import com.welyab.anjabachen.Movement;
import com.welyab.anjabachen.MovementBag;
import com.welyab.anjabachen.MovementTarget;
import com.welyab.anjabachen.Piece;
import com.welyab.anjabachen.PieceMovement;
import com.welyab.anjabachen.PieceType;
import com.welyab.anjabachen.Position;

public class Evaluator {
	
	// @formatter:off
	private static final int pawnTable[][] = {
			{0,  0,  0,  0,  0,  0,  0,  0},
			{50, 50, 50, 50, 50, 50, 50, 50},
			{10, 10, 20, 30, 30, 20, 10, 10},
			{5,  5, 10, 25, 25, 10,  5,  5},
			{0,  0,  0, 20, 20,  0,  0,  0},
			{5, -5,-10,  0,  0,-10, -5,  5},
			{5, 10, 10,-20,-20, 10, 10,  5},
			{0,  0,  0,  0,  0,  0,  0,  0}
	};
	// @formatter:on
	
	// @formatter:off
	private static final int bishopTable[][] = {
			{-20,-10,-10,-10,-10,-10,-10,-20},
			{-10,  0,  0,  0,  0,  0,  0,-10},
			{-10,  0,  5, 10, 10,  5,  0,-10},
			{-10,  5,  5, 10, 10,  5,  5,-10},
			{-10,  0, 10, 10, 10, 10,  0,-10},
			{-10, 10, 10, 10, 10, 10, 10,-10},
			{-10,  5,  0,  0,  0,  0,  5,-10},
			{-20,-10,-10,-10,-10,-10,-10,-20}
	};
	// @formatter:on
	
	// @formatter:off
	private static final int knightTable[][] = {
			{-50,-40,-30,-30,-30,-30,-40,-50},
			{-40,-20,  0,  0,  0,  0,-20,-40},
			{-30,  0, 10, 15, 15, 10,  0,-30},
			{-30,  5, 15, 20, 20, 15,  5,-30},
			{-30,  0, 15, 20, 20, 15,  0,-30},
			{-30,  5, 10, 15, 15, 10,  5,-30},
			{-40,-20,  0,  5,  5,  0,-20,-40},
			{-50,-40,-30,-30,-30,-30,-40,-50}
	};
	// @formatter:on
	
	// @formatter:off
	private static final int rookTable[][] = {
			{0,  0,  0,  0,  0,  0,  0,  0},
		    {5, 10, 10, 10, 10, 10, 10,  5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
		    {0,  0,  0,  5,  5,  0,  0,  0}
	};
	// @formatter:on
	
	// @formatter:off
	private static final int queenTable[][] = {
			{-20,-10,-10, -5, -5,-10,-10,-20},
			{-10,  0,  0,  0,  0,  0,  0,-10},
			{-10,  0,  5,  5,  5,  5,  0,-10},
			{ -5,  0,  5,  5,  5,  5,  0, -5},
			{  0,  0,  5,  5,  5,  5,  0, -5},
			{-10,  5,  5,  5,  5,  5,  0,-10},
			{-10,  0,  5,  0,  0,  0,  0,-10},
			{-20,-10,-10, -5, -5,-10,-10,-20}
	};
	// @formatter:on
	
	// @formatter:off
	private static final int kingTable[][] = {
			{-50,-40,-30,-20,-20,-30,-40,-50},
			{-30,-20,-10,  0,  0,-10,-20,-30},
			{-30,-10, 20, 30, 30, 20,-10,-30},
			{-30,-10, 30, 40, 40, 30,-10,-30},
			{-30,-10, 30, 40, 40, 30,-10,-30},
			{-30,-10, 20, 30, 30, 20,-10,-30},
			{-30,-30,  0,  0,  0,  0,-30,-30},
			{-50,-30,-30,-30,-30,-30,-30,-50}
	};
	// @formatter:on
	
	private Board board;
	
	private Movement currentWhiteMovement;
	
	private Movement currentBlackMovement;
	
	public Evaluator(String fen) {
		board = new Board(fen);
	}
	
	public static void main(String[] args) {
		Evaluator evaluator = new Evaluator("rnbq1bnr/ppp1pppp/3p2k1/1B4B1/3PP1Q1/8/PPP2PPP/RN2K1NR w KQ - 7 6");
		try (Scanner s = new Scanner(System.in)) {
			while (true) {
				System.out.println("Current board: " + evaluator.board.getFen());
				System.out.println(evaluator.board.toString(true));
				System.out.println("Type your move: ");
				String myMove = s.nextLine();
				Position origin = Position.fromPgnNotation(myMove.substring(0, 2));
				Position target = Position.fromPgnNotation(myMove.substring(2, 4));
				System.out.printf("Your move %s -> %s%n", origin.getPgnPosition(), target.getPgnPosition());
				System.out.printf("Your move (internal) %s -> %s%n", origin, target);
				evaluator.board.move(origin, target);
				System.out.println("Board after your move: " + evaluator.board.getFen());
				System.out.println(evaluator.board.toString(true));
				System.out.println("The computer is thinking...");
				ScoredMovement sMovement = evaluator.getMovement();
				System.out.printf("Current score: %.04f%n", sMovement.score);
				System.out.printf(
					"The computer move is %s -> %s%n",
					sMovement.movement.getOrigin().getPosition().getPgnPosition(),
					sMovement.movement.getTarget().getPosition().getPgnPosition()
				);
				evaluator.board.move(
					sMovement.movement.getOrigin(),
					sMovement.movement.getTarget()
				);
			}
		}
	}
	
	public int getPiecePositionalValue(Color color, PieceType type, Position p) {
		int row = color.isWhite() ? 7 - p.getRow() : p.getRow();
		int sign = color.isWhite() ? 1 : -1;
		int value = switch (type) {
			case KING -> kingTable[row][p.getColumn()];
			case QUEEN -> queenTable[row][p.getColumn()];
			case ROOK -> rookTable[row][p.getColumn()];
			case BISHOP -> bishopTable[row][p.getColumn()];
			case KNIGHT -> knightTable[row][p.getColumn()];
			case PAWN -> pawnTable[row][p.getColumn()];
		};
		return value * sign;
	}
	
	private int getPieceValue(Position p) {
		Piece piece = board.getPiece(p);
		int pieceValuePosition = getPiecePositionalValue(piece.getColor(), piece.getType(), p);
		int value = switch (piece.getType()) {
			case KING -> 999 * pieceValuePosition;
			case QUEEN -> 9 * pieceValuePosition;
			case ROOK -> 5 * pieceValuePosition;
			case BISHOP -> 3 * pieceValuePosition;
			case KNIGHT -> 3 * pieceValuePosition;
			case PAWN -> 1 * pieceValuePosition;
		};
		return value;
	}
	
	private double getScore(MovementBag bag) {
		double score = 0;
		for (int row = 0; row < BoardUtils.BOARD_SIZE; row++) {
			for (int column = 0; column < BoardUtils.BOARD_SIZE; column++) {
				Position position = Position.of(row, column);
				if (!board.isEmpty(position)) {
					score += getPieceValue(position);
				}
			}
		}
		return score;
	}
	
	public ScoredMovement getMovement() {
		if (board.getActiveColor().isWhite()) {
			double score = max(4);
			return new ScoredMovement(score, currentWhiteMovement);
		} else {
			double score = min(4);
			return new ScoredMovement(score, currentBlackMovement);
		}
	}
	
	public static class ScoredMovement {
		
		double score;
		
		Movement movement;
		
		public ScoredMovement(double score, Movement movement) {
			this.score = score;
			this.movement = movement;
		}
	}
	
	private double min(int depth) {
		if (depth == 0) {
			return getScore(null);
		}
		double score = Integer.MAX_VALUE;
		for (PieceMovement pieceMovement : board.getMovements()) {
			for (MovementTarget movementTarget : pieceMovement) {
				board.move(pieceMovement.getOrigin(), movementTarget);
				double depperScore = max(depth - 1);
				if (score > depperScore) {
					score = depperScore;
					if (depth == 4) {
						currentBlackMovement = new Movement(pieceMovement.getOrigin(), movementTarget);
					}
				}
				board.undo();
			}
		}
		return score;
	}
	
	private double max(int depth) {
		if (depth == 0) {
			return getScore(null);
		}
		double score = Integer.MIN_VALUE;
		for (PieceMovement pieceMovement : board.getMovements()) {
			for (MovementTarget movementTarget : pieceMovement) {
				board.move(pieceMovement.getOrigin(), movementTarget);
				double depperScore = min(depth - 1);
				if (score < depperScore) {
					score = depperScore;
					if (depth == 4) {
						currentWhiteMovement = new Movement(pieceMovement.getOrigin(), movementTarget);
					}
				}
				board.undo();
			}
		}
		return score;
	}
}
