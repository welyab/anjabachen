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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

@SuppressWarnings("javadoc")
public class ColorTest {
	
	@Test
	public void isWhiteShouldReturnTrueWhenColorIsWhite() {
		assertTrue(Color.WHITE.isWhite());
	}
	
	@Test
	public void isWhiteShouldReturnFalseWhenColorIsNotWhite() {
		assertFalse(Color.BLACK.isWhite());
	}
	
	@Test
	public void isBlackShouldreturnTrueWhenColorIsBlack() {
		assertTrue(Color.BLACK.isBlack());
	}
	
	@Test
	public void isBlackShouldReturnFalseWhenColorIsNotBlack() {
		assertFalse(Color.WHITE.isBlack());
	}
	
	@Test
	public void getOppositeShouldReturnWhiteWhenColorIsBlack() {
		assertEquals(Color.WHITE, Color.BLACK.getOpposite());
	}
	
	@Test
	public void getOppositeShouldReturnBlackWhenColorIsWhite() {
		assertEquals(Color.BLACK, Color.WHITE.getOpposite());
	}
	
	@Test
	public void getSignShouldReturnPositiveOneWhenColorIswhite() {
		assertEquals(1, Color.WHITE.getValue());
	}
	
	@Test
	public void getSignShouldReturnNegativeOneWhenColorIsBlack() {
		assertEquals(-1, Color.BLACK.getValue());
	}
}
