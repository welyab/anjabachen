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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the class <code>BoardState</code>.
 * 
 * @author Welyab Paula
 */
public class BoardStateTest {
	
	@Test
	@SuppressWarnings("javadoc")
	public void copyShoulCopAllFields() throws IllegalArgumentException, IllegalAccessException {
		BoardState state = new BoardState();
		state.setMovementCounter((short) 12);
		state.setSideToMove(MovementUtil.WHITE);
		state.setEnPassantTargetSquare(Position.of(5, 6));
		state.setFullMoveCounter(12);
		state.setHalfMoveClock(23);
		state.setSideToMove(MovementUtil.WHITE);
		state.setKingPosition(Position.of(0, 0), MovementUtil.WHITE);
		state.setKingPosition(Position.of(7, 7), MovementUtil.BLACK);
		state.setKingRookPosition(Position.of(5, 6), MovementUtil.WHITE);
		state.setKingRookPosition(Position.of(6, 5), MovementUtil.BLACK);
		state.setQueenRookPosition(Position.of(3, 4), MovementUtil.WHITE);
		state.setQueenRookPosition(Position.of(4, 3), MovementUtil.BLACK);
		
		BoardState copy = state.copy();
		
		for (Field field : BoardState.class.getDeclaredFields()) {
			field.setAccessible(true);
			Object original = field.get(state);
			assertNotNull(
				original,
				String.format(
					"Test can't be performed 'cause  value of field %s is null",
					field.getName()
				)
			);
			if (original instanceof Number && ((Number) original).longValue() == 0) {
				fail(
					String.format(
						"Test can't be performed 'cause the value of field %s is a number and it is zero",
						field.getName()
					)
				);
			}
			Object copied = field.get(copy);
			assertEquals(original, copied);
		}
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void isKingPresentShouldThrowIllegalArgumentExceptionIfColorIsInvalid() {
		BoardState boardState = new BoardState();
		assertThrows(IllegalArgumentException.class, () -> {
			boardState.isKingPresent(0);
		});
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void getKingPositionShouldThrowIllegalArgumentExceptionIfColorIsInvalid() {
		BoardState boardState = new BoardState();
		assertThrows(IllegalArgumentException.class, () -> {
			boardState.getKingPosition(0);
		});
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void getKingPositionShouldReturnProperPositionForWhiteKing() {
		Position whitePosition = Position.of(4, 6);
		Position blackPosition = Position.of(3, 7);
		BoardState boardState = new BoardState();
		boardState.setKingPosition(whitePosition, MovementUtil.WHITE);
		boardState.setKingPosition(blackPosition, MovementUtil.BLACK);
		assertEquals(whitePosition, boardState.getKingPosition(MovementUtil.WHITE));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void getKingPositionShouldReturnProperPositionForBlackKing() {
		Position whitePosition = Position.of(4, 6);
		Position blackPosition = Position.of(3, 7);
		BoardState boardState = new BoardState();
		boardState.setKingPosition(whitePosition, MovementUtil.WHITE);
		boardState.setKingPosition(blackPosition, MovementUtil.BLACK);
		assertEquals(blackPosition, boardState.getKingPosition(MovementUtil.BLACK));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testSetKingPositionShouldThrowIllegalArgumentExceptionWhenColorIsInvalid() {
		BoardState boardState = new BoardState();
		assertThrows(
			IllegalArgumentException.class,
			() -> boardState.setKingPosition(Position.of(0, 0), 0),
			"BoardState.setKingPosition should throw IllegaArgumentException when color is invalid"
		);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testIsKingSideCastlingAvaiableShouldThrowIllegalArgumentExceptionWhenColorIsInvalid() {
		BoardState boardState = new BoardState();
		assertThrows(
			IllegalArgumentException.class,
			() -> boardState.isKingSideCastlingAvaiable(0),
			"BoardState.isKingSideCastlingAvaiable should throw IllegaArgumentException when color is invalid"
		);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testIsQueenSideCastlingAvaiableShouldThrowIllegalArgumentExceptionWhenColorIsInvalid() {
		BoardState boardState = new BoardState();
		assertThrows(
			IllegalArgumentException.class,
			() -> boardState.isQueenSideCastlingAvaiable(0),
			"BoardState.isQueenSideCastlingAvaiable should throw IllegaArgumentException when color is invalid"
		);
	}
	
	@Test
	@SuppressWarnings(
		{
			"javadoc",
			"deprecation"
		}
	)
	public void testHashCodeForEqualsObjectsShouldBeTheSame() {
		BoardState bs1 = new BoardState();
		bs1.setEnPassantTargetSquare(Position.of(1, 2));
		bs1.setFullMoveCounter(new Integer(91));
		bs1.setHalfMoveClock(new Integer(3));
		bs1.setKingPosition(Position.of(5, 2), MovementUtil.WHITE);
		bs1.setSideToMove(MovementUtil.BLACK);
		BoardState bs2 = new BoardState();
		bs2.setEnPassantTargetSquare(Position.of(1, 2));
		bs2.setFullMoveCounter(new Integer(91));
		bs2.setHalfMoveClock(new Integer(3));
		bs2.setKingPosition(Position.of(5, 2), MovementUtil.WHITE);
		bs2.setSideToMove(MovementUtil.BLACK);
		assertEquals(bs1.hashCode(), bs2.hashCode());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testEqualsShouldReturnFalseWhenComparedObjectIsNull() {
		assertFalse(new BoardState().equals(null));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testInvalidKingSideCastlingFlagsShouldThrowIllegalArgumentExceptionWhenColorIsInvalid() {
		assertThrows(IllegalArgumentException.class, () -> new BoardState().invalidateKingSideCastlingFlags((byte) 0));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testInvalidQueenSideCastlingFlagsShouldThrowIllegalArgumentExceptionWhenColorIsInvalid() {
		assertThrows(IllegalArgumentException.class, () -> new BoardState().invalidateQueenSideCastlingFlags((byte) 0));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testIncrementFullMoveClockShouldIncrementCounterByOne() {
		BoardState boardState = new BoardState();
		short expected = (short) (boardState.getFullMoveCounter() + 1);
		boardState.incrementFullMoveClock();
		assertEquals(expected, boardState.getFullMoveCounter());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testIncrementHalMoveClockShouldIncrementTheClockByOne() {
		BoardState boardState = new BoardState();
		byte expected = (byte) (boardState.getHalfMoveClock() + 1);
		boardState.incrementHalfMoveClock();
		assertEquals(expected, boardState.getHalfMoveClock());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testResetHalMoveClockShouldPutTheClockInZero() {
		BoardState boardState = new BoardState();
		boardState.incrementFullMoveClock();
		boardState.resetHalMoveClock();
		assertEquals(0, boardState.getHalfMoveClock());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testSetSideToMoveShouldThrowIllegalArgumentExceptionWhenColorIsInvalid() {
		assertThrows(IllegalArgumentException.class, () -> new BoardState().setSideToMove(0));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testIncrementMovementCounterShouldIncrementByOne() {
		BoardState boardState = new BoardState();
		int expected = boardState.getMovementCounter() + 1;
		boardState.incrementMovementCounter();
		assertEquals(expected, boardState.getMovementCounter());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testSetKingRookPositionThrowsIllegalArgumentExceptionWhenColorIsInvalid() {
		assertThrows(IllegalArgumentException.class, () -> new BoardState().setKingRookPosition(Position.of(0, 0), 0));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testSetCopyAllStateFromGivenObject() {
		BoardState expected = new BoardState();
		expected.setMovementCounter((short) 12);
		expected.setSideToMove(MovementUtil.WHITE);
		expected.setEnPassantTargetSquare(Position.of(5, 6));
		expected.setFullMoveCounter(12);
		expected.setHalfMoveClock(23);
		expected.setSideToMove(MovementUtil.WHITE);
		expected.setKingPosition(Position.of(0, 0), MovementUtil.WHITE);
		expected.setKingPosition(Position.of(7, 7), MovementUtil.BLACK);
		expected.setKingRookPosition(Position.of(5, 6), MovementUtil.WHITE);
		expected.setKingRookPosition(Position.of(6, 5), MovementUtil.BLACK);
		expected.setQueenRookPosition(Position.of(3, 4), MovementUtil.WHITE);
		expected.setQueenRookPosition(Position.of(4, 3), MovementUtil.BLACK);
		
		BoardState boardState = new BoardState();
		boardState.set(expected);
		
		assertEquals(expected, boardState);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testGetKingRookPositionShouldReturnRookPositionForWhiteColor() {
		BoardState state = new BoardState();
		state.setKingRookPosition(Position.of(0, 0), MovementUtil.WHITE);
		state.setKingRookPosition(Position.of(7, 7), MovementUtil.BLACK);
		assertEquals(Position.of(0, 0), state.getKingRookPosition(MovementUtil.WHITE));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testGetKingRookPositionShouldReturnRookPositionForBlackColor() {
		BoardState state = new BoardState();
		state.setKingRookPosition(Position.of(0, 0), MovementUtil.WHITE);
		state.setKingRookPosition(Position.of(7, 7), MovementUtil.BLACK);
		assertEquals(Position.of(7, 7), state.getKingRookPosition(MovementUtil.BLACK));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testGetKingRookPositionThrowsIllegalArgumentExceptionWhenColorIsInvalid() {
		assertThrows(IllegalArgumentException.class, () -> new BoardState().getKingRookPosition(0));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testSetQueenRookPositionThrowsIllegalArgumentExceptionWhenColorIsInvalid() {
		assertThrows(IllegalArgumentException.class, () -> new BoardState().setQueenRookPosition(Position.of(0, 0), 0));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testInvalidateQueenSideCastlingFlags() {
		assertThrows(
			IllegalArgumentException.class,
			() -> new BoardState().invalidateQueenSideCastlingFlags((byte) 0)
		);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testInvalidateKingSideCastlingFlags() {
		assertThrows(
			IllegalArgumentException.class,
			() -> new BoardState().invalidateKingSideCastlingFlags((byte) 0)
		);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testInvalidKingSideCastlingFlagsInvalidatesWhiteKingSideCastling() {
		BoardState boardState = new BoardState();
		boardState.setKingRookPosition(Position.of(0, 0), MovementUtil.WHITE);
		boardState.setKingRookPosition(Position.of(7, 7), MovementUtil.BLACK);
		assertNotNull(boardState.getKingRookPosition(MovementUtil.WHITE));
		boardState.invalidateKingSideCastlingFlags(MovementUtil.WHITE);
		assertNull(boardState.getKingRookPosition(MovementUtil.WHITE));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testInvalidKingSideCastlingFlagsInvalidatesBlackKingSideCastling() {
		BoardState boardState = new BoardState();
		boardState.setKingRookPosition(Position.of(0, 0), MovementUtil.WHITE);
		boardState.setKingRookPosition(Position.of(7, 7), MovementUtil.BLACK);
		assertNotNull(boardState.getKingRookPosition(MovementUtil.BLACK));
		boardState.invalidateKingSideCastlingFlags(MovementUtil.BLACK);
		assertNull(boardState.getKingRookPosition(MovementUtil.BLACK));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testInvalidQueenSideCastlingFlagsInvalidatesWhiteQueenSideCastling() {
		BoardState boardState = new BoardState();
		boardState.setQueenRookPosition(Position.of(0, 0), MovementUtil.WHITE);
		boardState.setQueenRookPosition(Position.of(7, 7), MovementUtil.BLACK);
		assertNotNull(boardState.getQueenRookPosition(MovementUtil.WHITE));
		boardState.invalidateQueenSideCastlingFlags(MovementUtil.WHITE);
		assertNull(boardState.getQueenRookPosition(MovementUtil.WHITE));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testInvalidQueenSideCastlingFlagsInvalidatesBlackQueenSideCastling() {
		BoardState boardState = new BoardState();
		boardState.setQueenRookPosition(Position.of(0, 0), MovementUtil.BLACK);
		boardState.setQueenRookPosition(Position.of(7, 7), MovementUtil.BLACK);
		assertNotNull(boardState.getQueenRookPosition(MovementUtil.BLACK));
		boardState.invalidateQueenSideCastlingFlags(MovementUtil.BLACK);
		assertNull(boardState.getQueenRookPosition(MovementUtil.BLACK));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testGetQueenRookPositionThrowsIllegalArgumentExceptionWhenColorIsInvalid() {
		assertThrows(IllegalArgumentException.class, () -> new BoardState().getQueenRookPosition(0));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testInvalidateCastlingFlagsInvalidatesAllRookPositions() {
		BoardState boardState = new BoardState();
		boardState.setKingRookPosition(Position.of(1, 0), MovementUtil.WHITE);
		boardState.setKingRookPosition(Position.of(2, 0), MovementUtil.BLACK);
		boardState.setQueenRookPosition(Position.of(3, 0), MovementUtil.WHITE);
		boardState.setQueenRookPosition(Position.of(4, 0), MovementUtil.BLACK);
		assertNotNull(boardState.getKingRookPosition(MovementUtil.WHITE));
		assertNotNull(boardState.getKingRookPosition(MovementUtil.BLACK));
		assertNotNull(boardState.getQueenRookPosition(MovementUtil.WHITE));
		assertNotNull(boardState.getQueenRookPosition(MovementUtil.BLACK));
		boardState.invalidateCastlingFlags(MovementUtil.WHITE);
		boardState.invalidateCastlingFlags(MovementUtil.BLACK);
		assertNull(boardState.getKingRookPosition(MovementUtil.WHITE));
		assertNull(boardState.getKingRookPosition(MovementUtil.BLACK));
		assertNull(boardState.getQueenRookPosition(MovementUtil.WHITE));
		assertNull(boardState.getQueenRookPosition(MovementUtil.BLACK));
	}
}
