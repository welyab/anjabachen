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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for <code>Movements</code> calss.
 * 
 * @author Welyab Paula
 */
public class MovementsTest {
	
	@Test
	@SuppressWarnings("javadoc")
	public void testOriginCountShouldReturnTheTotalOfOriginStoredInMovementsSet() {
		assertEquals(
			1,
			new Movements(
				List.of(
					new PieceMovements(
						Position.of(1, 0),
						MovementUtil.WHITE_KING,
						List.of(
							new MovementTarget(
								Position.of(0, 0),
								MovementUtil.WHITE_KING,
								(byte) 0
							),
							new MovementTarget(
								Position.of(0, 1),
								MovementUtil.WHITE_KING,
								(byte) 0
							)
						)
					)
				)
			).getOriginCount(),
			"Movements.getOriginCount should return the toal number of unique origin in the movements set"
		);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testIsEmptyShouldReturnFalseIfThereIsSomeMovementInTheSet() {
		assertFalse(
			new Movements(
				List.of(
					new PieceMovements(
						Position.of(0, 1),
						MovementUtil.WHITE_KING,
						List.of(
							new MovementTarget(
								Position.of(0, 0),
								MovementUtil.WHITE_KING,
								(byte) 0
							)
						)
					)
				)
			).isEmpty(),
			"Movements.isEmpty should return false if there is at least one movement origin in the set"
		);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testIsEmptyShouldReturnTrueIfMovementSetIsEmpty() {
		assertTrue(
			new Movements(List.of()).isEmpty(),
			"Movements.isEmpty should return false if there is at least one movement origin in the set"
		);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testIfOriginCountOfMergedMovementsIsTheSumOfBoth() {
		Movements m1 = new Movements(
			List.of(
				new PieceMovements(
					Position.of(0, 0),
					MovementUtil.WHITE_KING,
					List.of(
						new MovementTarget(
							Position.of(1, 0),
							MovementUtil.WHITE_KING,
							(byte) 0
						)
					)
				),
				new PieceMovements(
					Position.of(3, 1),
					MovementUtil.WHITE_KING,
					List.of(
						new MovementTarget(
							Position.of(3, 2),
							MovementUtil.WHITE_KING,
							(byte) 0
						)
					)
				)
			)
		);
		Movements m2 = new Movements(
			List.of(
				new PieceMovements(
					Position.of(0, 1),
					MovementUtil.WHITE_KING,
					List.of(
						new MovementTarget(
							Position.of(0, 0),
							MovementUtil.WHITE_KING,
							(byte) 0
						)
					)
				)
			)
		);
		assertEquals(
			3,
			m1.merge(m2).getOriginCount(),
			"Movements.getOriginCount of the merged Movements should be the sum of originCount of the participatns"
		);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testToStringGeneratesExpectedString() {
		Movements movements = new Movements(
			List.of(
				new PieceMovements(
					Position.of(0, 0),
					MovementUtil.WHITE_KING,
					List.of(
						new MovementTarget(
							Position.of(1, 0),
							MovementUtil.WHITE_KING,
							(byte) 0
						),
						new MovementTarget(
							Position.of(0, 1),
							MovementUtil.WHITE_KING,
							(byte) 0
						)
					)
				),
				new PieceMovements(
					Position.of(3, 1),
					MovementUtil.WHITE_KING,
					List.of(
						new MovementTarget(
							Position.of(3, 2),
							MovementUtil.WHITE_KING,
							(byte) 0
						)
					)
				)
			)
		);
		assertEquals(
			"from Ka8 to [Ka7, Kb8], from Kb5 to [Kc5]",
			movements.toString(),
			"The toString should obeys the pattern"
		);
	}
}
