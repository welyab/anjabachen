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
	@CsvSource(
		value = {
			"nodes" + "," + MovementMetadata.NODES,
			"captures" + "," + MovementMetadata.CAPTURES,
			"e.p" + "," + MovementMetadata.EN_PASSANT,
			"castlings" + "," + MovementMetadata.CASTLING,
			"promotions" + "," + MovementMetadata.PROMOTIONS,
			"checks" + "," + MovementMetadata.CHECKS,
			"disc. checks" + "," + MovementMetadata.DISCOVERY_CHECKS,
			"double checks" + "," + MovementMetadata.DOUBLE_CHECKS,
			"checkmates" + "," + MovementMetadata.CHECKMATES,
			"stalemates" + "," + MovementMetadata.STALEMATE
		}
	)
	@SuppressWarnings("javadoc")
	public void testIsFieldPresentShouldReturnTrueWhenTheFieldIsPresentInTheMetadata(String fieldName, int field) {
		assertEquals(fieldName, MovementMetadata.getFieldName(field));
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
		String expected = "nodes = 0, captures = 0, e.p = 0, castlings = 0, "
				+ "promotions = 0, checks = 0, disc. checks = 0, double checks = 0, "
				+ "checkmates = 0, stalemates = 0";
		assertEquals(expected, MovementMetadata.empty().toString());
	}
}
