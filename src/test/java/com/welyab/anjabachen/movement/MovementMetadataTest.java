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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Unit tests for the class <code>MovementMetadata</code>.
 * 
 * @author Welyab Paula
 */
public class MovementMetadataTest {
	
	@ParameterizedTest
	@CsvSource(
		value = {
			MovementUtil.CAPTURE_MASK + "," + MovementMetadata.CAPTURES,
			MovementUtil.EN_PASSANT_MASK + "," + MovementMetadata.EN_PASSANT,
			MovementUtil.CASTLING_MASK + "," + MovementMetadata.CASTLING,
			MovementUtil.PROMOTION_MASK + "," + MovementMetadata.PROMOTIONS,
			MovementUtil.CHECK_MASK + "," + MovementMetadata.CHECKS,
			MovementUtil.DISCOVERY_CHECK_MASK + "," + MovementMetadata.DISCOVERY_CHECKS,
			MovementUtil.DOUBLE_CHECK_MASK + "," + MovementMetadata.DOUBLE_CHECKS,
			MovementUtil.CHECKMATE_MASK + "," + MovementMetadata.CHECKMATES,
			MovementUtil.STALEMATE_MASK + "," + MovementMetadata.STALEMATE
		}
	)
	@SuppressWarnings("javadoc")
	public void testAddFlagIncrementTheCorrespondentField(short flags, int field) {
		MovementMetadata movementMetadata = MovementMetadata.builder().add(flags).buid();
		assertEquals(1, movementMetadata.getValue(field));
	}
	
	@ParameterizedTest
	@CsvSource(
		value = {
			MovementUtil.CAPTURE_MASK + "," + MovementMetadata.CAPTURES,
			MovementUtil.EN_PASSANT_MASK + "," + MovementMetadata.EN_PASSANT,
			MovementUtil.CASTLING_MASK + "," + MovementMetadata.CASTLING,
			MovementUtil.PROMOTION_MASK + "," + MovementMetadata.PROMOTIONS,
			MovementUtil.CHECK_MASK + "," + MovementMetadata.CHECKS,
			MovementUtil.DISCOVERY_CHECK_MASK + "," + MovementMetadata.DISCOVERY_CHECKS,
			MovementUtil.DOUBLE_CHECK_MASK + "," + MovementMetadata.DOUBLE_CHECKS,
			MovementUtil.CHECKMATE_MASK + "," + MovementMetadata.CHECKMATES,
			MovementUtil.STALEMATE_MASK + "," + MovementMetadata.STALEMATE
		}
	)
	@SuppressWarnings("javadoc")
	public void testIsFieldPresentShouldReturnTrueWhenTheFieldIsPresentInTheMetadata(short flags, int field) {
		MovementMetadata movementMetadata = MovementMetadata.builder().add(flags).buid();
		assertTrue(movementMetadata.isFieldPresent(field));
	}
	
	@ParameterizedTest
	@ValueSource(
		ints = {
			MovementMetadata.NODES,
			MovementMetadata.CAPTURES,
			MovementMetadata.EN_PASSANT,
			MovementMetadata.CASTLING,
			MovementMetadata.PROMOTIONS,
			MovementMetadata.CHECKS,
			MovementMetadata.DISCOVERY_CHECKS,
			MovementMetadata.DOUBLE_CHECKS,
			MovementMetadata.CHECKMATES,
			MovementMetadata.STALEMATE
		}
	)
	@SuppressWarnings("javadoc")
	public void testIsFieldPresentShouldReturnTrueWhenTheFieldIsPresentInThesMetadata(int field) {
		int value = new Random().nextInt(1000) + 1;
		MovementMetadata movementMetadata = MovementMetadata.builder().increment(field, value).buid();
		assertEquals(value, movementMetadata.getValue(field));
	}
	
	@ParameterizedTest
	@ValueSource(
		ints = {
			MovementMetadata.NODES,
			MovementMetadata.CAPTURES,
			MovementMetadata.EN_PASSANT,
			MovementMetadata.CASTLING,
			MovementMetadata.PROMOTIONS,
			MovementMetadata.CHECKS,
			MovementMetadata.DISCOVERY_CHECKS,
			MovementMetadata.DOUBLE_CHECKS,
			MovementMetadata.CHECKMATES,
			MovementMetadata.STALEMATE
		}
	)
	@SuppressWarnings("javadoc")
	public void testIsFieldPresentShouldReturnTrueWhenTheFieldIsPresentInTheMetadata(int field) {
		assertFalse(MovementMetadata.empty().isFieldPresent(field));
	}
	
	@ParameterizedTest
	@CsvSource(
		value = {
			"NODES" + "," + MovementMetadata.NODES,
			"CAPTURES" + "," + MovementMetadata.CAPTURES,
			"EN_PASSANT" + "," + MovementMetadata.EN_PASSANT,
			"CASTLING" + "," + MovementMetadata.CASTLING,
			"PROMOTIONS" + "," + MovementMetadata.PROMOTIONS,
			"CHECKS" + "," + MovementMetadata.CHECKS,
			"DISCOVERY_CHECKS" + "," + MovementMetadata.DISCOVERY_CHECKS,
			"DOUBLE_CHECKS" + "," + MovementMetadata.DOUBLE_CHECKS,
			"CHECKMATES" + "," + MovementMetadata.CHECKMATES,
			"STALEMATE" + "," + MovementMetadata.STALEMATE
		}
	)
	@SuppressWarnings("javadoc")
	public void testIsFieldPresentShouldReturnTrueWhenTheFieldIsPresentInTheMetadata(String fieldName, int field) {
		assertEquals(fieldName, MovementMetadata.getKeyname(field));
	}
	
	@SuppressWarnings("javadoc")
	@Test
	public void testAddMovementMetadataShouldAddEachField() {
		MovementMetadata expected = MovementMetadata
			.builder()
			.incrementNodes(17)
			.incrementCaptures(321)
			.incrementEnPassant(23)
			.incrementCastling(9)
			.incrementPromotions(78)
			.incrementChecks(564)
			.incrementDiscoveryChecks(1)
			.incrementDoubleChecks(66)
			.incrementCheckmates(987)
			.incrementStalemate(21)
			.buid();
		
		MovementMetadata movementMetadata = MovementMetadata
			.builder()
			.add(expected)
			.buid();
		
		assertEquals(expected, movementMetadata);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testEqualsShouldReturnFalseWhenComparedObjectIsNull() {
		assertFalse(MovementMetadata.empty().equals(null));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testEqualsShouldReturnFalseWhenComparedObjectIsDifferentType() {
		assertFalse(MovementMetadata.empty().equals(""));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testHashCode() {
		assertNotEquals(0.5, MovementMetadata.empty().hashCode());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testIncrementNodesShouldIncrementNodesCounterByOne() {
		assertEquals(1, MovementMetadata.builder().incrementNodes().buid().getNodes());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testToStringShouldCreateStringInTheCorrectFormat() {
		String expected = "NODES = 0, CAPTURES = 0, EN_PASSANT = 0, CASTLING = 0, "
				+ "PROMOTIONS = 0, CHECKS = 0, DISCOVERY_CHECKS = 0, DOUBLE_CHECKS = 0, "
				+ "CHECKMATES = 0, STALEMATE = 0";
		assertEquals(expected, MovementMetadata.empty().toString());
	}
}
