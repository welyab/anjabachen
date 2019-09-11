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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;
import com.welyab.anjabachen.movement.MovementMetadata;
import com.welyab.anjabachen.movement.MovementMetadataField;

/**
 * A <i>Perft Result</i> is a set of values about movement generation. For example, the amout of
 * position where a king is in check is a information possible these results.
 * 
 * @author Welyab Paula
 * 
 * @see PerftCalculator
 * @see MovementMetadataField
 */
public class PerftResult {
	
	/** The values of this perft result. */
	private Map<MovementMetadataField, Map<Long, Long>> values;
	
	@SuppressWarnings("javadoc")
	private PerftResult() {
		values = new EnumMap<>(MovementMetadataField.class);
	}
	
	/**
	 * Retrieves fields available for the given depth.
	 * 
	 * @param depth The depth query.
	 * 
	 * @return The list fields.
	 */
	public Set<MovementMetadataField> getFields(long depth) {
		return values
			.entrySet()
			.stream()
			.filter(e -> e.getValue().containsKey(depth))
			.map(Map.Entry::getKey)
			.collect(Collectors.toSet());
	}
	
	/**
	 * Retrieves the value associated with the given field in the specific depth.
	 * 
	 * @param depth The required depth.
	 * @param field The required field.
	 * 
	 * @return The value associated with given field in the in a specific depth.
	 * 
	 * @see #isValuePresent(MovementMetadataField, long)
	 */
	public Long getValue(long depth, MovementMetadataField field) {
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
	public boolean isValuePresent(MovementMetadataField field, long depth) {
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
		 * Assigns all values available in the given <code>perftResult</code> paramter.
		 * 
		 * @param depth The depth where the values will be added, and where they will be extract
		 *        from the <code>perftResult</code> parameter.
		 * @param perftResult The perft result to be added to this being created perft result.
		 * 
		 * @return This builder instance for further usage.
		 * 
		 * @throws IllegalStateException If the builder is finished. A builder is considered
		 *         <i>finished</i> after the method {@linkplain #build() build} is returns.
		 */
		public Builder addValue(long depth, PerftResult perftResult) {
			checkNotFinished();
			for (MovementMetadataField field : perftResult.getFields(depth)) {
				addValue(depth, field, perftResult.getValue(depth, field));
			}
			return this;
		}
		
		/**
		 * Assigns a value to the field in the specific depth. If the field in the specific depth
		 * already have a value, the new value is added to present value.
		 * 
		 * @param depth The depth.
		 * @param field The field.
		 * @param value The value to be assigned.
		 * 
		 * @return This builder instance for further usage.
		 * 
		 * @throws IllegalStateException If the builder is finished. A builder is considered
		 *         <i>finished</i> after the method {@linkplain #build() build} is returns.
		 */
		public Builder addValue(long depth, MovementMetadataField field, long value) {
			checkNotFinished();
			if (!result.values.containsKey(field)) {
				result.values.put(field, new HashMap<>());
			}
			Map<Long, Long> map = result.values.get(field);
			map.put(depth, map.getOrDefault(depth, 0L) + value);
			return this;
		}
		
		/**
		 * Assigns all values from given movement metadata into this pert result. If the field in
		 * the specific depth already have a value, the new value is added to present value.
		 * 
		 * <p>
		 * The values are:
		 * 
		 * <ul>
		 * <li>total nodes
		 * <li>captures
		 * <li><i>en passants</i>
		 * <li>castling
		 * <li>promotions
		 * <li>checks
		 * <li>discovery checks
		 * <li>double checks
		 * <li>checkmates
		 * <li>stalemates
		 * </ul>
		 * 
		 * @param depth The depth where values will be added.
		 * @param metadata The movement metadata.
		 * 
		 * @return This builder instance for further usage.
		 */
		public Builder addValues(long depth, MovementMetadata metadata) {
			checkNotFinished();
			addValue(depth, MovementMetadataField.NODES, metadata);
			addValue(depth, MovementMetadataField.CAPTURES, metadata);
			addValue(depth, MovementMetadataField.EN_PASSANTS, metadata);
			addValue(depth, MovementMetadataField.CASTLINGS, metadata);
			addValue(depth, MovementMetadataField.PROMOTIONS, metadata);
			addValue(depth, MovementMetadataField.CHECKS, metadata);
			addValue(depth, MovementMetadataField.DISCOVERY_CHECKS, metadata);
			addValue(depth, MovementMetadataField.DOUBLE_CHECKS, metadata);
			addValue(depth, MovementMetadataField.CHECKMATES, metadata);
			addValue(depth, MovementMetadataField.STALEMATES, metadata);
			return this;
		}
		
		private void addValue(long depth, MovementMetadataField field, MovementMetadata metadata) {
			addValue(depth, field, metadata.getValueOptional(field).orElse(0L));
		}
		
		/**
		 * Assigns all values from given movement flags into this perft result. If the field in the
		 * specific depth already have a value, the new value is added to present value.
		 * 
		 * <p>
		 * The values are:
		 * 
		 * <ul>
		 * <li>total nodes
		 * <li>captures
		 * <li><i>en passants</i>
		 * <li>castling
		 * <li>promotions
		 * <li>checks
		 * <li>discovery checks
		 * <li>double checks
		 * <li>checkmates
		 * <li>stalemates
		 * </ul>
		 * 
		 * @param depth The depth where values will be added.
		 * @param movementFlags Movement information encoded in a <code>integer</code> value.
		 * 
		 * @return This builder instance for further usage.
		 */
		public Builder addValues(int depth, int movementFlags) {
			addValues(depth, MovementMetadata.builder().addMovement(movementFlags).build());
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
	
	@Override
	public String toString() {
		List<Long> depths = values
			.values()
			.stream()
			.flatMap(m -> m.keySet().stream())
			.distinct()
			.sorted()
			.collect(Collectors.toList());
		Map<Long, StringBuilder> builders = new HashMap<>();
		for (long depth : depths) {
			for (MovementMetadataField field : MovementMetadataField.values()) {
				if (isValuePresent(field, depth)) {
					builders
						.computeIfAbsent(
							depth,
							xDepth -> new StringBuilder(Long.toString(depth))
						)
						.append(" - ")
						.append(field.getFieldName())
						.append(": ")
						.append(getValue(depth, field));
				}
			}
		}
		return builders
			.entrySet()
			.stream()
			.sorted((e1, e2) -> (int) (e1.getKey() + e2.getKey()))
			.map(Map.Entry::getValue)
			.reduce((v1, v2) -> v1.append(String.format("%n")).append(v2))
			.map(StringBuilder::toString)
			.orElse("");
	}
}
