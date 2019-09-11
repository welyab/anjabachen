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

import java.util.Collections;
import java.util.EnumMap;
import java.util.Optional;
import java.util.Set;

import com.welyab.anjabachen.BoardUtils;
import com.welyab.anjabachen.perft.PerftCalculator;

/**
 * The <code>MovementMetadata</code> is a set of information summary about a set a movements.
 * Instances of this class may be provided by the <code>BoardMovements</code> in order to let you
 * know about how many movement are there available, or the amount of movement that are a capture,
 * promotion, etc.
 *
 * <p>
 * Imagine follow board position, with white to perform next movement. There are 14 possible
 * movements.
 * 
 * <ul>
 * <li>Possible movements: 14
 * <li>Captures: 1
 * <li><i>En passants</i>: 0
 * <li>Castling: 0
 * <li>Promotions: 0
 * <li>Checks: 2
 * <li>Discovery checks: 0
 * <li>Double checks: 0
 * <li>Checkmates: 0
 * <li>Stalemates: 0
 * </ul>
 * 
 * <pre>
 * ┌───┬───┬───┬───┬───┬───┬───┬───┐
 * │   │   │   │   │   │   │   │   │
 * ├───┼───┼───┼───┼───┼───┼───┼───┤
 * │   │   │ p │   │   │   │   │   │
 * ├───┼───┼───┼───┼───┼───┼───┼───┤
 * │   │   │   │ p │   │   │   │   │
 * ├───┼───┼───┼───┼───┼───┼───┼───┤
 * │ K │ P │   │   │   │   │   │ r │
 * ├───┼───┼───┼───┼───┼───┼───┼───┤
 * │   │ R │   │   │   │ p │   │ k │
 * ├───┼───┼───┼───┼───┼───┼───┼───┤
 * │   │   │   │   │   │   │   │   │
 * ├───┼───┼───┼───┼───┼───┼───┼───┤
 * │   │   │   │   │ P │   │ P │   │
 * ├───┼───┼───┼───┼───┼───┼───┼───┤
 * │   │   │   │   │   │   │   │   │
 * └───┴───┴───┴───┴───┴───┴───┴───┘
 * </pre>
 * 
 * <p>
 * By continuing, after the white movement, there are 191 possible movement responses from black
 * piece:
 * 
 * <ul>
 * <li>Possible movements: 191
 * <li>Captures: 14
 * <li><i>En passants</i>: 0
 * <li>Castling: 0
 * <li>Promotions: 0
 * <li>Checks: 10
 * <li>Discovery checks: 0
 * <li>Double checks: 0
 * <li>Checkmates: 0
 * <li>Stalemates: 0
 * </ul>
 * 
 * So, a movement metadata servers for aggregate information for a immediate movement, or a movement
 * information in a deeper level.
 * 
 * <p>
 * Duing movement generation, it is possible that not all field be available.
 * 
 * <p>
 * This is a unmodifiable class. You may create instances by using the inclosing
 * {@linkplain #builder() builder}.
 * 
 * @author Welyab Paula
 * 
 * @see MovementMetadata
 * @see PerftCalculator
 */
public class MovementMetadata {
	
	@SuppressWarnings("javadoc")
	private static final MovementMetadata EMPTY = builder().build();
	
	/** Available values in this metada, identified by their fields. */
	private EnumMap<MovementMetadataField, Long> values;
	
	@SuppressWarnings("javadoc")
	private MovementMetadata() {
		values = new EnumMap<>(MovementMetadataField.class);
	}
	
	public Set<MovementMetadataField> getFields() {
		return Collections.unmodifiableSet(values.keySet());
	}
	
	public boolean isFieldPresent(MovementMetadataField field) {
		return values.containsKey(field);
	}
	
	public Long getValue(MovementMetadataField field) {
		return getValueOptional(field)
			.orElseThrow(() -> new MovementException(String.format("Field not present in the metadata: %s", field)));
	}
	
	public Optional<Long> getValueOptional(MovementMetadataField field) {
		return Optional.ofNullable(values.get(field));
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static MovementMetadata empty() {
		return EMPTY;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (MovementMetadataField field : MovementMetadataField.values()) {
			if (isFieldPresent(field)) {
				if (builder.length() > 0) {
					builder.append(", ");
				}
				builder.append(field).append(" = ").append(getValue(field));
			}
		}
		return builder.toString();
	}
	
	public static final class Builder {
		
		private final MovementMetadata meta;
		
		private Builder() {
			meta = new MovementMetadata();
		}
		
		public Builder add(MovementMetadata meta) {
			for (MovementMetadataField field : meta.getFields()) {
				addValue(field, meta.getValue(field));
			}
			return this;
		}
		
		public Builder addValue(MovementMetadataField field, long value) {
			meta.values.put(
				field,
				meta.values.getOrDefault(field, 0L) + value
			);
			return this;
		}
		
		public Builder addMovement(int movementFlags) {
			incrementTotalMovements();
			if (BoardUtils.isCapture(movementFlags)) {
				incrementCaptureCount();
			}
			if (BoardUtils.isEnPassant(movementFlags)) {
				incrementEnPassantCount();
			}
			if (BoardUtils.isCastling(movementFlags)) {
				incrementCastlings();
			}
			if (BoardUtils.isPromotion(movementFlags)) {
				incrementPromotionCount();
			}
			if (BoardUtils.isCheck(movementFlags)) {
				incrementCheckCount();
			}
			if (BoardUtils.isDiscoveryCheck(movementFlags)) {
				incrementDiscoveryCheckCount();
			}
			if (BoardUtils.isDoubleCheck(movementFlags)) {
				incrementDoubleCheckCount();
			}
			if (BoardUtils.isCheckmate(movementFlags)) {
				incrementCheckmateCount();
			}
			if (BoardUtils.isStalemate(movementFlags)) {
				incrementStalemateCount();
			}
			return this;
		}
		
		public Builder incrementCaptureCount() {
			return incrementCaptureCount(1);
		}
		
		public Builder incrementCaptureCount(long captureCount) {
			addValue(MovementMetadataField.CAPTURES, captureCount);
			return this;
		}
		
		public Builder incrementPromotionCount() {
			return incrementPromotionCount(1);
		}
		
		public Builder incrementPromotionCount(long promotionCount) {
			addValue(MovementMetadataField.PROMOTIONS, promotionCount);
			return this;
		}
		
		public Builder incrementTotalMovements() {
			return incrementTotalMovements(1);
		}
		
		public Builder incrementCastlings() {
			return incrementCastlings(1);
		}
		
		public Builder incrementCastlings(long castlingsCount) {
			addValue(MovementMetadataField.CASTLINGS, castlingsCount);
			return this;
		}
		
		public Builder incrementEnPassantCount() {
			return incrementEnPassantCount(1);
		}
		
		public Builder incrementEnPassantCount(long enPassantCount) {
			addValue(MovementMetadataField.EN_PASSANTS, enPassantCount);
			return this;
		}
		
		public Builder incrementTotalMovements(long totalMovements) {
			addValue(MovementMetadataField.NODES, totalMovements);
			return this;
		}
		
		public Builder incrementCheckCount() {
			return incrementCheckCount(1);
		}
		
		public Builder incrementCheckCount(int checkCount) {
			addValue(MovementMetadataField.CHECKS, checkCount);
			return this;
		}
		
		public Builder incrementDiscoveryCheckCount() {
			return incrementDiscoveryCheckCount(1);
		}
		
		public Builder incrementDiscoveryCheckCount(long discoveryCheckCount) {
			addValue(MovementMetadataField.DISCOVERY_CHECKS, discoveryCheckCount);
			return this;
		}
		
		public Builder incrementDoubleCheckCount() {
			return incrementDoubleCheckCount(1);
		}
		
		public Builder incrementDoubleCheckCount(long doubleCheckCount) {
			addValue(MovementMetadataField.DOUBLE_CHECKS, doubleCheckCount);
			return this;
		}
		
		public Builder incrementCheckmateCount() {
			return incrementCheckmateCount(1);
		}
		
		public Builder incrementCheckmateCount(long checkmateCount) {
			addValue(MovementMetadataField.CHECKMATES, checkmateCount);
			return this;
		}
		
		public Builder incrementStalemateCount() {
			return incrementStalemateCount(1);
		}
		
		public Builder incrementStalemateCount(int stalemateCount) {
			addValue(MovementMetadataField.STALEMATES, stalemateCount);
			return this;
		}
		
		public MovementMetadata build() {
			return meta;
		}
	}
}
