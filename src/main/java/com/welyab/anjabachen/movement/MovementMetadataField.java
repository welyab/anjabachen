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

import com.welyab.anjabachen.perft.PerftCalculator;

/**
 * Enumerates the fields available in ANJABACHEN for perft calculations.
 * 
 * @author Welyab Paula
 * 
 * @see PerftCalculator
 */
public enum MovementMetadataField {
	
	/**
	 * Metadata information for the total number of available movements.
	 */
	NODES("Nodes"),
	
	/**
	 * Metadata information for the total number of movements that are also a movement of capture.
	 */
	CAPTURES("Captures"),
	
	/**
	 * Metadata information for the total number of movements that are also a <i>en passant</i>
	 * capture. These movement are also counted in the {@link #CAPTURES} field.
	 */
	EN_PASSANTS("En passants"),
	
	/**
	 * Metadata information for the total number of movement that are also a castling movement.
	 */
	CASTLINGS("Castlings"),
	
	/**
	 * Metadata in
	 */
	PROMOTIONS("Promotions"),
	
	CHECKS("Checks"),
	
	/**
	 * Metadata information for the total number of movements that applies a discovery check to the
	 * enemy king. These movements are not counted as {@link #DOUBLE_CHECKS}, when applicable.
	 */
	DISCOVERY_CHECKS("Discovery Checks"),
	
	/**
	 * Metadata information for the total number of movements that also applies a double check in
	 * the enemy king. These movements are not counted as {@link #DISCOVERY_CHECKS}, but they are
	 * counted as {@link #CHECKS}.
	 */
	DOUBLE_CHECKS("Double Checks"),
	
	/**
	 * Metadata information for the total number of movements that terminates the game with
	 * checkmate.
	 */
	CHECKMATES("Checkmates"),
	
	/**
	 * Metadata information for the total number of movements that terminates the game with
	 * stalemate.
	 */
	STALEMATES("Stalemate");
	
	private String fieldName;
	
	@SuppressWarnings("javadoc")
	private MovementMetadataField(String fieldName) {
		this.fieldName = fieldName;
	}
	
	/**
	 * Retrieves the field name.
	 * 
	 * @return The field name.
	 */
	public String getFieldName() {
		return fieldName;
	}
}
