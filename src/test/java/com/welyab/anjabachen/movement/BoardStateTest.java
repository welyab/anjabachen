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
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
		state.setCastlingFlags(1);
		state.setEnPassantTargetSquare(Position.of(5, 6));
		state.setFullMoveCounter(12);
		state.setHalfMoveClock(23);
		state.setSideToMove(BoardUtil.WHITE_COLOR_CODE);
		
		BoardState copy = state.copy();
		
		for (Field field : BoardState.class.getDeclaredFields()) {
			field.setAccessible(true);
			Object original = field.get(state);
			assertNotNull(original, "Test can't be performed 'cause  value is null");
			if (original instanceof Number && ((Number) original).longValue() == 0) {
				fail("Test can't be performed 'cause the value is a number and it is zero");
			}
			Object copied = field.get(copy);
			assertEquals(original, copied);
		}
	}
}
