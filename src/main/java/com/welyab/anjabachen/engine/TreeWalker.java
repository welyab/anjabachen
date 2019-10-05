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
package com.welyab.anjabachen.engine;

import com.welyab.anjabachen.evaluation.Evaluator;
import com.welyab.anjabachen.evaluation.SimpleEvaluator;
import com.welyab.anjabachen.movement.Board;
import com.welyab.anjabachen.movement.BoardMovements;
import com.welyab.anjabachen.movement.PieceMovement;
import com.welyab.anjabachen.ui.concurrent.NonReturningOperation;

public class TreeWalker {
	
	private final Object lock = new Object();
	
	private boolean ponder;
	
	private int timeLimit;
	
	private int nodesLimit;
	
	private int depthLimit;
	
	private int movesToGo;
	
	private int blackIncrement;
	
	private int whiteIncrement;
	
	private int blackTime;
	
	private int whiteTime;
	
	private boolean infinite;
	
	private Board board;
	
	private boolean running;
	
	public TreeWalker() {
		running = false;
		clear();
	}
	
	public static void main(String[] args) {
		TreeWalker walker = new TreeWalker();
		Board board = new Board("1kr1q3/ppp5/8/8/8/1R6/8/1K5B w - - 0 1");
		System.out.println(board.toString(true));
		int score = walker.negamax(
			board,
			1,
			6,
			Integer.MIN_VALUE,
			Integer.MAX_VALUE
		);
		System.out.println(score / 100.0);
	}
	
	private int negamax(
			Board board,
			int depth,
			int maxDepth,
			int alpha,
			int beta
	) {
		if (depth == maxDepth) {
			return evaluate(board);
		}
		
		BoardMovements movements = board.getMovements();
		int score = alpha;
		for (int i = 0; i < movements.size(); i++) {
			PieceMovement pieceMovement = movements.get(i);
			for (int j = 0; j < pieceMovement.size(); j++) {
				board.move(pieceMovement.getOrigin(), pieceMovement.getTarget(j));
				score = board.getActiveColor().getOpposite().isWhite()
						? Integer.max(evaluate(board), score)
						: Integer.min(evaluate(board), score);
				board.undo();
			}
		}
		return score;
	}
	
	private int evaluate(Board board) {
		Evaluator eval = new SimpleEvaluator();
		eval.setBoard(board);
		return eval.getScore();
	}
	
	public boolean isRunning() {
		synchronized (lock) {
			return running;
		}
	}
	
	public final void clear() {
		synchronized (lock) {
			if (running) {
				return;
			}
			
			ponder = false;
			infinite = false;
			whiteTime = Integer.MAX_VALUE;
			blackTime = Integer.MAX_VALUE;
			whiteIncrement = 0;
			blackIncrement = 0;
			nodesLimit = Integer.MAX_VALUE;
			depthLimit = Integer.MAX_VALUE;
			movesToGo = Integer.MAX_VALUE;
			timeLimit = Integer.MAX_VALUE;
		}
	}
	
	public void stop(NonReturningOperation callback) {
	}
	
	public void start() {
	}
	
	public void setPosition(String fen) {
		board = new Board(fen);
	}
	
	public void setPonder(boolean ponder) {
		synchronized (lock) {
			this.ponder = ponder;
		}
	}
	
	public void setInfinite(boolean infinite) {
		synchronized (lock) {
			this.infinite = infinite;
		}
	}
	
	public void setWhiteTime(int whiteTime) {
		synchronized (lock) {
			this.whiteTime = whiteTime;
		}
	}
	
	public void setBlackTime(int blackTime) {
		synchronized (lock) {
			this.blackTime = blackTime;
		}
	}
	
	public void setWhiteIncrement(int whiteIncrement) {
		synchronized (lock) {
			this.whiteIncrement = whiteIncrement;
		}
	}
	
	public void setBlackIncrement(int blackIncrement) {
		synchronized (lock) {
			this.blackIncrement = blackIncrement;
		}
	}
	
	public void setMovesToGo(int movesToGo) {
		synchronized (lock) {
			this.movesToGo = movesToGo;
		}
	}
	
	public void setDepthLimit(int depthLimit) {
		synchronized (lock) {
			this.depthLimit = depthLimit;
		}
	}
	
	public void setNodesLimit(int nodesLimit) {
		synchronized (lock) {
			this.nodesLimit = nodesLimit;
		}
	}
	
	public void setTimeLimit(int timeLimit) {
		synchronized (lock) {
			this.timeLimit = timeLimit;
		}
	}
}
