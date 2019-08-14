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
package com.welyab.anjabachen.perft;

import com.welyab.anjabachen.Perft;

/**
 * Enumerates the fields available in ANJABACHEN for perft calculations.
 * 
 * @author Welyab Paula
 * 
 * @see Perft
 */
public enum PerftResultField {
	
	NODES("Nodes"),
	
	CAPTURES("Captures"),
	
	EN_PASSANTS("En passants"),
	
	CASTLINGS("Castlings"),
	
	PROMOTIONS("Promotions"),
	
	CHECKS("Checks"),
	
	DISCOVERY_CHECKS("Discovery Checks"),
	
	DOUBLE_CHECKS("Double Checks"),
	
	CHECKMATES("Checkmates"),
	
	STALEMATE("Stalemate");
	
	private String fieldName;
	
	@SuppressWarnings("javadoc")
	private PerftResultField(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String getFieldName() {
		return fieldName;
	}
}
