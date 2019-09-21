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

import org.junit.Assert;
import org.junit.Test;

import com.welyab.anjabachen.movement.Color;

/**
 * Unit tests for <code>Color</code> class.
 *
 * @author Welyab Paula
 */
public class ColorTest {

	@Test
	@SuppressWarnings("javadoc")
	public void isBlackShouldReturnTrueIfColorIsBlack() {
		Assert.assertTrue(Color.BLACK.isBlack());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isBlackShouldReturnFalseIfColorIsNotBlack() {
		Assert.assertFalse(Color.WHITE.isBlack());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isWhiteShouldReturnTrueIfColorIsWhite() {
		Assert.assertTrue(Color.WHITE.isWhite());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void isWhiteShouldReturnFalseIfColorIsNotWhite() {
		Assert.assertFalse(Color.BLACK.isWhite());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getOppositeShouldReturnWhiteWhenColorIsBlack() {
		assertEquals(Color.WHITE, Color.BLACK.getOpposite());
	}

	@Test
	@SuppressWarnings("javadoc")
	public void getOppositeShouldReturnBlackWhenColorIsWhite() {
		assertEquals(Color.BLACK, Color.WHITE.getOpposite());
	}
}
