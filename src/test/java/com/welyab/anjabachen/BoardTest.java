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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

/**
 * Unit tests for the <code>Board</code> class.
 *
 * @author Welyab Paula
 */
public class BoardTest {

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateMovementsForKingOnCenter() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . . K . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(3, 3);
		addPieces(board, movements.getTargets());
		String expectedBoard = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . K K K . . ." + Board.NEWLINE
				/* 3 */ + ". . K K K . . ." + Board.NEWLINE
				/* 4 */ + ". . K K K . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		assertEquals(expectedBoard, board.toString());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsShouldNotGenerateKingMovesBeyondSquareOccupiedBySameColorPiece() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . N . . . ." + Board.NEWLINE
				/* 3 */ + ". . . K . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(3, 3);
		addPieces(board, movements.getTargets());
		String expectedBoard = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . K N K . . ." + Board.NEWLINE
				/* 3 */ + ". . K K K . . ." + Board.NEWLINE
				/* 4 */ + ". . K K K . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		assertEquals(expectedBoard, board.toString());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateMovementsForKingOnCorner() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + "K . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . . . . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(0, 0);
		addPieces(board, movements.getTargets());
		String expectedBoard = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + "K K . . . . . ." + Board.NEWLINE
				/* 1 */ + "K K . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . . . . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		assertEquals(expectedBoard, board.toString());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateMovementsForKingOnBorder() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . . . . . . ." + Board.NEWLINE
				/* 4 */ + "K . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(4, 0);
		addPieces(board, movements.getTargets());
		String expectedBoard = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + "K K . . . . . ." + Board.NEWLINE
				/* 4 */ + "K K . . . . . ." + Board.NEWLINE
				/* 5 */ + "K K . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		assertEquals(expectedBoard, board.toString());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateMovementsForQueenOnCenter() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . . Q . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(3, 3);
		addPieces(board, movements.getTargets());
		String expectedBoard = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + "Q . . Q . . Q ." + Board.NEWLINE
				/* 1 */ + ". Q . Q . Q . ." + Board.NEWLINE
				/* 2 */ + ". . Q Q Q . . ." + Board.NEWLINE
				/* 3 */ + "Q Q Q Q Q Q Q Q" + Board.NEWLINE
				/* 4 */ + ". . Q Q Q . . ." + Board.NEWLINE
				/* 5 */ + ". Q . Q . Q . ." + Board.NEWLINE
				/* 6 */ + "Q . . Q . . Q ." + Board.NEWLINE
				/* 7 */ + ". . . Q . . . Q" + Board.NEWLINE;
		assertEquals(expectedBoard, board.toString());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsShouldNotGenerateQueenMovesBeyondSquareOccupiedBySameColorPiece() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + "N . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . N . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . . Q . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . N . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(3, 3);
		addPieces(board, movements.getTargets());
		String expectedBoard = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + "N . . . . . Q ." + Board.NEWLINE
				/* 1 */ + ". Q . N . Q . ." + Board.NEWLINE
				/* 2 */ + ". . Q Q Q . . ." + Board.NEWLINE
				/* 3 */ + "Q Q Q Q Q Q Q Q" + Board.NEWLINE
				/* 4 */ + ". . Q Q N . . ." + Board.NEWLINE
				/* 5 */ + ". Q . Q . . . ." + Board.NEWLINE
				/* 6 */ + "Q . . Q . . . ." + Board.NEWLINE
				/* 7 */ + ". . . Q . . . ." + Board.NEWLINE;
		assertEquals(expectedBoard, board.toString());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateMovementsForQueenOnCorner() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . Q" + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . . . . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(0, 7);
		addPieces(board, movements.getTargets());
		String expectedBoard = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + "Q Q Q Q Q Q Q Q" + Board.NEWLINE
				/* 1 */ + ". . . . . . Q Q" + Board.NEWLINE
				/* 2 */ + ". . . . . Q . Q" + Board.NEWLINE
				/* 3 */ + ". . . . Q . . Q" + Board.NEWLINE
				/* 4 */ + ". . . Q . . . Q" + Board.NEWLINE
				/* 5 */ + ". . Q . . . . Q" + Board.NEWLINE
				/* 6 */ + ". Q . . . . . Q" + Board.NEWLINE
				/* 7 */ + "Q . . . . . . Q" + Board.NEWLINE;
		assertEquals(expectedBoard, board.toString());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateMovementsForQueenOnBorder() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . . . . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . Q" + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(4, 7);
		addPieces(board, movements.getTargets());
		String expectedBoard = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . Q . . . Q" + Board.NEWLINE
				/* 1 */ + ". . . . Q . . Q" + Board.NEWLINE
				/* 2 */ + ". . . . . Q . Q" + Board.NEWLINE
				/* 3 */ + ". . . . . . Q Q" + Board.NEWLINE
				/* 4 */ + "Q Q Q Q Q Q Q Q" + Board.NEWLINE
				/* 5 */ + ". . . . . . Q Q" + Board.NEWLINE
				/* 6 */ + ". . . . . Q . Q" + Board.NEWLINE
				/* 7 */ + ". . . . Q . . Q" + Board.NEWLINE;
		assertEquals(expectedBoard, board.toString());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateMovementsForRookOnCenter() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . . R . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(3, 3);
		addPieces(board, movements.getTargets());
		String expectedBoard = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . R . . . ." + Board.NEWLINE
				/* 1 */ + ". . . R . . . ." + Board.NEWLINE
				/* 2 */ + ". . . R . . . ." + Board.NEWLINE
				/* 3 */ + "R R R R R R R R" + Board.NEWLINE
				/* 4 */ + ". . . R . . . ." + Board.NEWLINE
				/* 5 */ + ". . . R . . . ." + Board.NEWLINE
				/* 6 */ + ". . . R . . . ." + Board.NEWLINE
				/* 7 */ + ". . . R . . . ." + Board.NEWLINE;
		assertEquals(expectedBoard, board.toString());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsShouldNotGenerateRookMovesBeyondSquareOccupiedBySameColorPiece() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . N . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . . R . . . ." + Board.NEWLINE
				/* 4 */ + ". . . N . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(3, 3);
		addPieces(board, movements.getTargets());
		String expectedBoard = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . N . . . ." + Board.NEWLINE
				/* 2 */ + ". . . R . . . ." + Board.NEWLINE
				/* 3 */ + "R R R R R R R R" + Board.NEWLINE
				/* 4 */ + ". . . N . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		assertEquals(expectedBoard, board.toString());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateMovementsForRookOnCorner() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . . . . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + "R . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(7, 0);
		addPieces(board, movements.getTargets());
		String expectedBoard = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + "R . . . . . . ." + Board.NEWLINE
				/* 1 */ + "R . . . . . . ." + Board.NEWLINE
				/* 2 */ + "R . . . . . . ." + Board.NEWLINE
				/* 3 */ + "R . . . . . . ." + Board.NEWLINE
				/* 4 */ + "R . . . . . . ." + Board.NEWLINE
				/* 5 */ + "R . . . . . . ." + Board.NEWLINE
				/* 6 */ + "R . . . . . . ." + Board.NEWLINE
				/* 7 */ + "R R R R R R R R" + Board.NEWLINE;
		assertEquals(expectedBoard, board.toString());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateMovementsForRookBorder() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + "R . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . . . . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(2, 0);
		addPieces(board, movements.getTargets());
		String expectedBoard = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + "R . . . . . . ." + Board.NEWLINE
				/* 1 */ + "R . . . . . . ." + Board.NEWLINE
				/* 2 */ + "R R R R R R R R" + Board.NEWLINE
				/* 3 */ + "R . . . . . . ." + Board.NEWLINE
				/* 4 */ + "R . . . . . . ." + Board.NEWLINE
				/* 5 */ + "R . . . . . . ." + Board.NEWLINE
				/* 6 */ + "R . . . . . . ." + Board.NEWLINE
				/* 7 */ + "R . . . . . . ." + Board.NEWLINE;
		assertEquals(expectedBoard, board.toString());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateMovementsForBishopOnCenter() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . . B . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(3, 3);
		addPieces(board, movements.getTargets());
		String expectedBoard = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + "B . . . . . B ." + Board.NEWLINE
				/* 1 */ + ". B . . . B . ." + Board.NEWLINE
				/* 2 */ + ". . B . B . . ." + Board.NEWLINE
				/* 3 */ + ". . . B . . . ." + Board.NEWLINE
				/* 4 */ + ". . B . B . . ." + Board.NEWLINE
				/* 5 */ + ". B . . . B . ." + Board.NEWLINE
				/* 6 */ + "B . . . . . B ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . B" + Board.NEWLINE;
		assertEquals(expectedBoard, board.toString());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsShouldNotGenerateBishopMovesBeyondSquareOccupiedBySameColorPiece() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". N . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . N . . ." + Board.NEWLINE
				/* 3 */ + ". . . B . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". N . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(3, 3);
		addPieces(board, movements.getTargets());
		String expectedBoard = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". N . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . B . N . . ." + Board.NEWLINE
				/* 3 */ + ". . . B . . . ." + Board.NEWLINE
				/* 4 */ + ". . B . B . . ." + Board.NEWLINE
				/* 5 */ + ". N . . . B . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . B ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . B" + Board.NEWLINE;
		assertEquals(expectedBoard, board.toString());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateMovementsForBishopOnCorner() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . . . . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + "B . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(7, 0);
		addPieces(board, movements.getTargets());
		String expectedBoard = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . B" + Board.NEWLINE
				/* 1 */ + ". . . . . . B ." + Board.NEWLINE
				/* 2 */ + ". . . . . B . ." + Board.NEWLINE
				/* 3 */ + ". . . . B . . ." + Board.NEWLINE
				/* 4 */ + ". . . B . . . ." + Board.NEWLINE
				/* 5 */ + ". . B . . . . ." + Board.NEWLINE
				/* 6 */ + ". B . . . . . ." + Board.NEWLINE
				/* 7 */ + "B . . . . . . ." + Board.NEWLINE;
		assertEquals(expectedBoard, board.toString());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateMovementsForBishopOnBorder() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . . . . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . B . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(7, 4);
		addPieces(board, movements.getTargets());
		String expectedBoard = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + "B . . . . . . ." + Board.NEWLINE
				/* 4 */ + ". B . . . . . B" + Board.NEWLINE
				/* 5 */ + ". . B . . . B ." + Board.NEWLINE
				/* 6 */ + ". . . B . B . ." + Board.NEWLINE
				/* 7 */ + ". . . . B . . ." + Board.NEWLINE;
		assertEquals(expectedBoard, board.toString());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateMovementsForKnightOnCenter() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . . N . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(3, 3);
		addPieces(board, movements.getTargets());
		String expectedBoard = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . N . N . . ." + Board.NEWLINE
				/* 2 */ + ". N . . . N . ." + Board.NEWLINE
				/* 3 */ + ". . . N . . . ." + Board.NEWLINE
				/* 4 */ + ". N . . . N . ." + Board.NEWLINE
				/* 5 */ + ". . N . N . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		assertEquals(expectedBoard, board.toString());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsShouldNotGenerateBishopMovesBeyondKnightOccupiedBySameColorPiece() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . B . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . B . ." + Board.NEWLINE
				/* 3 */ + ". . . N . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . B . B . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(3, 3);
		addPieces(board, movements.getTargets());
		String expectedBoard = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . N . B . . ." + Board.NEWLINE
				/* 2 */ + ". N . . . B . ." + Board.NEWLINE
				/* 3 */ + ". . . N . . . ." + Board.NEWLINE
				/* 4 */ + ". N . . . N . ." + Board.NEWLINE
				/* 5 */ + ". . B . B . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		assertEquals(expectedBoard, board.toString());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateMovementsForKnightOnCorner() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . . . . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . N" + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(7, 7);
		addPieces(board, movements.getTargets());
		String expectedBoard = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . . . . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . N ." + Board.NEWLINE
				/* 6 */ + ". . . . . N . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . N" + Board.NEWLINE;
		assertEquals(expectedBoard, board.toString());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateMovementsForKnightOnBorder() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . N . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . . . . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(0, 4);
		addPieces(board, movements.getTargets());
		String expectedBoard = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . N . . ." + Board.NEWLINE
				/* 1 */ + ". . N . . . N ." + Board.NEWLINE
				/* 2 */ + ". . . N . N . ." + Board.NEWLINE
				/* 3 */ + ". . . . . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		assertEquals(expectedBoard, board.toString());
	}

	@SuppressWarnings("javadoc")
	private void addPieces(Board board, List<MovementTarget> targets) {
		for (MovementTarget target : targets) {
			board.addPiece(target.getPiece(), target.getRow(), target.getColumn());
		}
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isUnderAttackeShouldReturnTrueWhenPositionIsAttackedByKing() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . K . . . ." + Board.NEWLINE
				/* 3 */ + ". . . . . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		assertTrue(board.isUnderAttack(2, 2, Color.WHITE));
		assertTrue(board.isUnderAttack(2, 4, Color.WHITE));
		assertTrue(board.isUnderAttack(3, 4, Color.WHITE));
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isUnderAttackeShouldReturnTrueWhenPositionIsAttackedByQueen() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . Q . . . ." + Board.NEWLINE
				/* 3 */ + ". . . . . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		assertTrue(board.isUnderAttack(1, 2, Color.WHITE));
		assertTrue(board.isUnderAttack(5, 6, Color.WHITE));
		assertTrue(board.isUnderAttack(0, 5, Color.WHITE));
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isUnderAttackeShouldReturnTrueWhenPositionIsAttackedByRook() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . R . . . ." + Board.NEWLINE
				/* 3 */ + ". . . . . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		assertTrue(board.isUnderAttack(2, 0, Color.WHITE));
		assertTrue(board.isUnderAttack(6, 3, Color.WHITE));
		assertTrue(board.isUnderAttack(2, 6, Color.WHITE));
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isUnderAttackeShouldReturnTrueWhenPositionIsAttackedByBishop() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . B . . . ." + Board.NEWLINE
				/* 3 */ + ". . . . . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		assertTrue(board.isUnderAttack(0, 1, Color.WHITE));
		assertTrue(board.isUnderAttack(4, 5, Color.WHITE));
		assertTrue(board.isUnderAttack(5, 0, Color.WHITE));
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isUnderAttackeShouldReturnTrueWhenPositionIsAttackedByKnight() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . N . . . ." + Board.NEWLINE
				/* 3 */ + ". . . . . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		assertTrue(board.isUnderAttack(0, 2, Color.WHITE));
		assertTrue(board.isUnderAttack(4, 4, Color.WHITE));
		assertTrue(board.isUnderAttack(3, 1, Color.WHITE));
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isUnderAttackShouldReturnFalseWhenQueenIsBlockedByPieceOfSameColor() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". Q . . B . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		assertFalse(board.isUnderAttack(3, 6, Color.WHITE));
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isUnderAttackShouldReturnFalseWhenQueenIsBlockedByPieceOfDifferentColor() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". Q . . b . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		assertFalse(board.isUnderAttack(3, 6, Color.WHITE));
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isUnderAttackShouldReturnFalseWhenRookIsBlockedByPieceOfSameColor() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . R . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . . B . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		assertFalse(board.isUnderAttack(7, 3, Color.WHITE));
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isUnderAttackShouldReturnFalseWhenRookIsBlockedByPieceOfDifferentColor() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . R . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . . b . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		assertFalse(board.isUnderAttack(7, 3, Color.WHITE));
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isUnderAttackShouldReturnFalseWhenBishopIsBlockedByPieceOfSameColor() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". B . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . . R . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		assertFalse(board.isUnderAttack(6, 6, Color.WHITE));
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isUnderAttackShouldReturnFalseWhenBishopIsBlockedByPieceOfDifferentColor() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". B . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . . n . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		assertFalse(board.isUnderAttack(6, 6, Color.WHITE));
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateMovementsForQuenInPlacesWhereTheKingIsNotInCheck() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . K . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . Q . . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . r . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(3, 2);
		addPieces(board, movements.getTargets());
		String expectedBoard = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . K . . . . ." + Board.NEWLINE
				/* 1 */ + ". . Q . . . . ." + Board.NEWLINE
				/* 2 */ + ". . Q . . . . ." + Board.NEWLINE
				/* 3 */ + ". . Q . . . . ." + Board.NEWLINE
				/* 4 */ + ". . Q . . . . ." + Board.NEWLINE
				/* 5 */ + ". . Q . . . . ." + Board.NEWLINE
				/* 6 */ + ". . Q . . . . ." + Board.NEWLINE
				/* 7 */ + ". . Q . . . . ." + Board.NEWLINE;
		assertEquals(expectedBoard, board.toString());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateMovementsForRookInPlacesWhereTheKingIsNotInCheck() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + "K . . R . . . q" + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(3, 3);
		addPieces(board, movements.getTargets());
		String expectedBoard = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + "K R R R R R R R" + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		assertEquals(expectedBoard, board.toString());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateMovementsForBishopInPlacesWhereTheKingIsNotInCheck() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + "K . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . B . . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . b ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(3, 2);
		addPieces(board, movements.getTargets());
		String expectedBoard = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + "K . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". B . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . B . . . . ." + Board.NEWLINE
				/* 4 */ + ". . . B . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . B . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . B . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . B ." + Board.NEWLINE;
		assertEquals(expectedBoard, board.toString());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateMovementsForKnightInPlacesWhereTheKingIsNotInCheck() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + "K . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . N . . ." + Board.NEWLINE
				/* 3 */ + ". . . . . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . b ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(2, 4);
		addPieces(board, movements.getTargets());
		String expectedBoard = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . . . . . ." + Board.NEWLINE
				/* 1 */ + "K . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . N . . ." + Board.NEWLINE
				/* 3 */ + ". . N . . . . ." + Board.NEWLINE
				/* 4 */ + ". . . N . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . b ." + Board.NEWLINE;
		assertEquals(expectedBoard, board.toString());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateMovementForQueenInTheOnlyPlaceWhereItReleasesKingFromCheck() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . K . . Q . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . . . . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . r . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(0, 5);
		addPieces(board, movements.getTargets());
		String expectedBoard = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . K . . Q . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . Q . . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . r . . . . ." + Board.NEWLINE;
		assertEquals(expectedBoard, board.toString());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateMovementForRookInTheOnlyPlaceWhereItReleasesKingFromCheck() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . K . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . . . . R . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . r . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(3, 5);
		addPieces(board, movements.getTargets());
		String expectedBoard = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . K . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . R . . R . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . r . . . . ." + Board.NEWLINE;
		assertEquals(expectedBoard, board.toString());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateMovementForBishopInTheOnlyPlaceWhereItReleasesKingFromCheck() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . K . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . . . . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . B . ." + Board.NEWLINE
				/* 7 */ + ". . r . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(6, 5);
		addPieces(board, movements.getTargets());
		String expectedBoard = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . K . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . B . . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . B . ." + Board.NEWLINE
				/* 7 */ + ". . r . . . . ." + Board.NEWLINE;
		assertEquals(expectedBoard, board.toString());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateMovementForKnightInTheOnlyPlaceWhereItReleasesKingFromCheck() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . K . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . . . . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . r . N . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(7, 4);
		addPieces(board, movements.getTargets());
		String expectedBoard = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . K . . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . . . . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . N . . . . ." + Board.NEWLINE
				/* 7 */ + ". . r . N . . ." + Board.NEWLINE;
		assertEquals(expectedBoard, board.toString());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsCantGenerateMovesForQueenIfItCantReleaseItsKingFromCheck() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . K . . . ." + Board.NEWLINE
				/* 1 */ + ". . . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + "b . . . . Q . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . r . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(3, 5);
		assertTrue(movements.isEmpty());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsCantGenerateMovesForRookIfItCantReleaseItsKingFromCheck() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . K . . . ." + Board.NEWLINE
				/* 1 */ + ". n . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . . . . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . R ." + Board.NEWLINE
				/* 6 */ + ". . . r . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(5, 6);
		assertTrue(movements.isEmpty());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsCantGenerateMovesForBishopIfItCantReleaseItsKingFromCheck() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . K . . . ." + Board.NEWLINE
				/* 1 */ + ". n . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . . . . . . ." + Board.NEWLINE
				/* 4 */ + ". B . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . . . . . ." + Board.NEWLINE
				/* 6 */ + ". . . q . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(4, 1);
		assertTrue(movements.isEmpty());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getMovementsCantGenerateMovesForKnightIfItCantReleaseItsKingFromCheck() {
		String boardString = ""
				// - - - - 0 1 2 3 4 5 6 7
				/* 0 */ + ". . . K . . . ." + Board.NEWLINE
				/* 1 */ + ". n . . . . . ." + Board.NEWLINE
				/* 2 */ + ". . . . . . . ." + Board.NEWLINE
				/* 3 */ + ". . N . . . . ." + Board.NEWLINE
				/* 4 */ + ". . . . . . . ." + Board.NEWLINE
				/* 5 */ + ". . . q . . . ." + Board.NEWLINE
				/* 6 */ + ". . . . . . . ." + Board.NEWLINE
				/* 7 */ + ". . . . . . . ." + Board.NEWLINE;
		Board board = new Board(boardString, "");
		Movements movements = board.getMovements(3, 2);
		assertTrue(movements.isEmpty());
	}
}
