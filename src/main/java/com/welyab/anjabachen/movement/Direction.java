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

/**
 * A direction adjuster is a row adjuster and column adjuster pair that helps the piece movement
 * generation to generate the target positions.
 *
 * <p>
 * The movement generator related methods use a direction adjuster in order to transform a given
 * position into a target position. The pieces like king, queen, rook, bishop and knight have a
 * radial movement.
 *
 * <pre>
 * targetRow    = initialRow    + moveLength * rowAdjuster;
 * targetColumn = initialColumn + moveLength * columnAdjuster;
 * </pre>
 *
 * @author Welyab Paula
 */
public class Direction {
	
	/**
	 * The row adjuster value.
	 */
	final int rowAdjuster;
	
	/**
	 * The column adjuster value.
	 */
	final int columnAdjuster;
	
	/**
	 * Creates a direction adjuster instance using given values.
	 *
	 * @param rowAdjuster The row adjuster value.
	 * @param columnAdjsuter The column adjuster value.
	 */
	Direction(int rowAdjuster, int columnAdjsuter) {
		this.rowAdjuster = rowAdjuster;
		columnAdjuster = columnAdjsuter;
	}
}
