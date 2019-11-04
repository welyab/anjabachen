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

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the class <code>LocalizedPiece</code>.
 * 
 * @author Welyab Paula
 */
public class LocalizedPieceTest {
	
	@Test
	@SuppressWarnings("javadoc")
	public void testEqualsShouldReturnFalseWhenComparedObjectIsNull() {
		assertFalse(
			new LocalizedPiece(
				Position.of(0, 0), MovementUtil.WHITE_KING
			).equals(null),
			"LocalizedPiece.equals should return false when compared object is null"
		);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testEqualsShouldReturnFalseWhenComparedObjectHasDifferentObject() {
		assertFalse(
			new LocalizedPiece(
				Position.of(0, 0), MovementUtil.WHITE_KING
			).equals(new Object()),
			"LocalizedPiece.equals should return false when compared object has a different object"
		);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testToStringShouldReturnSpecificFormat() {
		assertEquals(
			"LocalizedPiece([6,5], K)",
			new LocalizedPiece(
				Position.of(6, 5),
				MovementUtil.WHITE_KING
			).toString(),
			"LocalizedPiece.toString should return the format 'LocalizedPiece([row,column], piece letter)'"
		);
	}
}
