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

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.welyab.anjabachen.Perft;

/**
 * A <i>Perft Result</i> is a set of values about movement generation. For example, the amout of
 * position where a king is in check is a information possible these results.
 * 
 * @author Welyab Paula
 * 
 * @see Perft
 * @see PerftResultField
 */
public class PerftResult {
	
	/** The values of this perft result. */
	private Map<PerftResultField, Map<Long, Long>> values;
	
	@SuppressWarnings("javadoc")
	private PerftResult() {
		values = new EnumMap<>(PerftResultField.class);
	}
	
	/**
	 * Retrieves the value associated with the given field in the specific depth.
	 * 
	 * @param field The required field.
	 * @param depth The required depth.
	 * 
	 * @return The value associated with given field in the in a specific depth.
	 * 
	 * @see #isValuePresent(PerftResultField, long)
	 */
	public Long getValue(PerftResultField field, long depth) {
		if (!isValuePresent(field, depth)) {
			throw new PerftException(String.format("Field %s not present for the depth %d", field, depth));
		}
		
		return values.get(field).get(depth);
	}
	
	/**
	 * Evaluates if this perft result has a value for the specific field in the given depth.
	 * 
	 * @param field The field query.
	 * @param depth The depth query.
	 * 
	 * @return A value <code>true</code> if this perft has a value for the specific field in the
	 *         given depth, or <code>false</code> otherwise.
	 */
	public boolean isValuePresent(PerftResultField field, long depth) {
		return values.containsKey(field) && values.get(field).containsKey(depth);
	}
	
	/**
	 * Creates a builder for create instances of <code>PerftResult</code>.
	 * 
	 * @return The builder.
	 */
	public static Builder builder() {
		return new Builder();
	}
	
	/**
	 * A builder construct <code>PerftResult</code> instances.
	 * 
	 * @author Welyab Paula
	 */
	public static final class Builder {
		
		/** The being constructing perft result instance. */
		private PerftResult result = new PerftResult();
		
		/** Indicates whether this builder was finished. */
		private boolean finished = false;
		
		@SuppressWarnings("javadoc")
		private Builder() {
		}
		
		/**
		 * Assigns a value to the field in the specific depth.
		 * 
		 * @param field The field.
		 * @param depth The depth.
		 * @param value The value to be assigned.
		 * 
		 * @return This builder instance for further usage.
		 * 
		 * @throws IllegalStateException If the builder is finished. A builder is considered
		 *         <i>finished</i> after the method {@linkplain #build() build} is returns.
		 */
		public Builder addValue(PerftResultField field, long depth, long value) {
			checkNotFinished();
			if (!result.values.containsKey(field)) {
				result.values.put(field, new HashMap<>());
			}
			result.values.get(field).put(depth, value);
			return this;
		}
		
		/**
		 * Retrieves the created <code>PerftResult</code> instance.
		 * 
		 * <p>
		 * This method cannot be used twice. After its returning, the builder is considered
		 * <i>finished</i>.
		 * 
		 * @return The creates the instance.
		 * 
		 * @throws IllegalStateException If this method was called before.
		 */
		public PerftResult build() {
			checkNotFinished();
			finished = true;
			return result;
		}
		
		@SuppressWarnings("javadoc")
		private void checkNotFinished() {
			Preconditions.checkState(!finished, "This builder is finished.");
		}
	}
}
