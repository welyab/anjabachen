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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class joins a set of information about a movement. During generation, the engine
 * extract extra related to the movement, for example, if the movement is a capturing.
 * 
 * <ul>
 * <li>{@link #NODES}
 * <li>{@link #CAPTURES}
 * <li>{@link #EN_PASSANT}
 * <li>{@link #CASTLING}
 * <li>{@link #PROMOTIONS}
 * <li>{@link #CHECKS}
 * <li>{@link #DISCOVERY_CHECKS}
 * <li>{@link #DOUBLE_CHECKS}
 * <li>{@link #CHECKMATES}
 * <li>{@link #STALEMATE}
 * </ul>
 * 
 * @author Welyab Paula
 */
public final class MovementMetadata {
	
	@SuppressWarnings("javadoc")
	private static final MovementMetadata EMPTY = builder().buid();
	
	/** The field in metadata that indicates total number of nodes. */
	public static final int NODES = 0;
	
	/** The field in metadata that indicates total number of movement that are capture. */
	public static final int CAPTURES = 1;
	
	/** The field in metadata that indicates total number of movement that are capture. */
	public static final int EN_PASSANT = 2;
	
	/** The field in metadata that indicates total number of movement that are castling. */
	public static final int CASTLING = 3;
	
	/** The field in metadata that indicates total number of movement that are pawn promotion. */
	public static final int PROMOTIONS = 4;
	
	/** The field in metadata that indicates total number of movement that are check. */
	public static final int CHECKS = 5;
	
	/** The field in metadata that indicates total number of movement that are discovery check. */
	public static final int DISCOVERY_CHECKS = 6;
	
	/** The field in metadata that indicates total number of movement that are double check. */
	public static final int DOUBLE_CHECKS = 7;
	
	/** The field in metadata that indicates total number of movement that are check mate. */
	public static final int CHECKMATES = 8;
	
	/** The field in metadata that indicates total number of movement that are stalemate. */
	public static final int STALEMATE = 9;
	
	@SuppressWarnings("javadoc")
	private static final int[] KEYS = {
		NODES,
		CAPTURES,
		EN_PASSANT,
		CASTLING,
		PROMOTIONS,
		CHECKS,
		DISCOVERY_CHECKS,
		DOUBLE_CHECKS,
		CHECKMATES,
		STALEMATE
	};
	
	@SuppressWarnings("javadoc")
	private static final String[] KEY_NAMES = {
		"nodes",
		"captures",
		"e.p",
		"castlings",
		"promotions",
		"checks",
		"disc. checks",
		"double checks",
		"checkmates",
		"stalemates"
	};
	
	/**
	 * The values for each field. The index is the field value.
	 */
	private long[] values;
	
	@SuppressWarnings("javadoc")
	private MovementMetadata() {
		values = new long[10];
	}
	
	public static List<Integer> getFields() {
		List<Integer> list = new ArrayList<>();
		for (int key : KEYS) {
			list.add(key);
		}
		return list;
	}
	
	public static List<String> getFieldsNames() {
		return getFields()
			.stream()
			.map(i -> getFieldName(i))
			.collect(Collectors.toList());
	}
	
	/**
	 * Retrieves the value o associated with the specified field key.
	 * 
	 * <p>
	 * <strong>Important:</strong> this method may return unpredicted values if the specific field
	 * was not filled during metadata building. In order to know if a field is valid, use the method
	 * {@link #isFieldPresent(int)}.
	 * 
	 * @param key The key. A value of the list:
	 * 
	 *        <ul>
	 *        <li>{@link #NODES}
	 *        <li>{@link #CAPTURES}
	 *        <li>{@link #EN_PASSANT}
	 *        <li>{@link #CASTLING}
	 *        <li>{@link #PROMOTIONS}
	 *        <li>{@link #CHECKS}
	 *        <li>{@link #DISCOVERY_CHECKS}
	 *        <li>{@link #DOUBLE_CHECKS}
	 *        <li>{@link #CHECKMATES}
	 *        <li>{@link #STALEMATE}
	 *        </ul>
	 * 
	 * @return The value associated with given key.
	 * 
	 * @throws ArrayIndexOutOfBoundsException If the key is invalid.
	 */
	public long getValue(int key) {
		return values[key];
	}
	
	/**
	 * Retrieves the value associated with {@link #NODES} field.
	 * 
	 * @return The value.
	 */
	public long getNodes() {
		return getValue(NODES);
	}
	
	/**
	 * Retrieves the value associated with {@link #CAPTURES} field.
	 * 
	 * @return The value.
	 */
	
	public long getCaptures() {
		return getValue(CAPTURES);
	}
	
	/**
	 * Retrieves the value associated with {@link #EN_PASSANT} field.
	 * 
	 * @return The value.
	 */
	
	public long getEnPassant() {
		return getValue(EN_PASSANT);
	}
	
	/**
	 * Retrieves the value associated with {@link #CASTLING} field.
	 * 
	 * @return The value.
	 */
	
	public long getCastling() {
		return getValue(CASTLING);
	}
	
	/**
	 * Retrieves the value associated with {@link #PROMOTIONS} field.
	 * 
	 * @return The value.
	 */
	
	public long getPromotions() {
		return getValue(PROMOTIONS);
	}
	
	/**
	 * Retrieves the value associated with {@link #CHECKS} field.
	 * 
	 * @return The value.
	 */
	public long getChecks() {
		return getValue(CHECKS);
	}
	
	/**
	 * Retrieves the value associated with {@link #DISCOVERY_CHECKS} field.
	 * 
	 * @return The value.
	 */
	public long getDiscoveryChecks() {
		return getValue(DISCOVERY_CHECKS);
	}
	
	/**
	 * Retrieves the value associated with {@link #DOUBLE_CHECKS} field.
	 * 
	 * @return The value.
	 */
	public long getDoubleChecks() {
		return getValue(DOUBLE_CHECKS);
	}
	
	/**
	 * Retrieves the value associated with {@link #CHECKMATES} field.
	 * 
	 * @return The value.
	 */
	public long getCheckmates() {
		return getValue(CHECKMATES);
	}
	
	/**
	 * Retrieves the value associated with {@link #STALEMATE} field.
	 * 
	 * @return The value.
	 */
	public long getStalemate() {
		return getValue(STALEMATE);
	}
	
	/**
	 * Retrieves the key name for given code.
	 * 
	 * @param key The key code.
	 * 
	 * @return The key name.
	 */
	public static String getFieldName(int key) {
		return KEY_NAMES[key];
	}
	
	/**
	 * Retrieves an empty metadata.
	 * 
	 * @return An empty metadata.
	 */
	public static MovementMetadata empty() {
		return EMPTY;
	}
	
	/**
	 * Retrieves a builder for creating <code>MovementMetadata</code> instances.
	 * 
	 * @return The builder.
	 */
	public static Builder builder() {
		return new Builder();
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(values);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MovementMetadata)) {
			return false;
		}
		MovementMetadata other = (MovementMetadata) obj;
		return Arrays.equals(values, other.values);
	}
	
	/**
	 * A builder for create instances of <code>MovementMetadata</code>.
	 * 
	 * @author Welyab Paula
	 */
	public static final class Builder {
		
		@SuppressWarnings("javadoc")
		private MovementMetadata metadata = new MovementMetadata();
		
		@SuppressWarnings("javadoc")
		private Builder() {
		}
		
		/**
		 * Adds the value in the for field indicated by parameter.
		 * 
		 * @param field The field to be incremented.
		 * @param value The value to add.
		 * 
		 * @return This builder for further usage.
		 */
		public Builder increment(int field, long value) {
			metadata.values[field] += value;
			return this;
		}
		
		/**
		 * Adds each value present in the given metadata to its respective value in this being built
		 * metadata.
		 * 
		 * @param metadata The metadata to be added into this metadata.
		 * 
		 * @return This builder for further usage.
		 */
		public Builder add(MovementMetadata metadata) {
			incrementNodes(metadata.getNodes());
			incrementCaptures(metadata.getCaptures());
			incrementEnPassant(metadata.getEnPassant());
			incrementCastling(metadata.getCastling());
			incrementPromotions(metadata.getPromotions());
			incrementChecks(metadata.getChecks());
			incrementDiscoveryChecks(metadata.getDiscoveryChecks());
			incrementDoubleChecks(metadata.getDoubleChecks());
			incrementCheckmates(metadata.getCheckmates());
			incrementStalemate(metadata.getStalemate());
			return this;
		}
		
		/**
		 * Increments by one all fields encoded in the given movement flags. Also, this method
		 * increments the field {@link MovementMetadata#NODES} by one.
		 * 
		 * @param movementFlags The movement flags.
		 * 
		 * @return This builder for further usage.
		 */
		public Builder add(short movementFlags) {
			incrementNodes(1);
			if (MovementUtil.isCapture(movementFlags)) {
				incrementCaptures();
			}
			if (MovementUtil.isEnPassant(movementFlags)) {
				incrementEnPassant();
			}
			if (MovementUtil.isCastling(movementFlags)) {
				incrementCastling();
			}
			if (MovementUtil.isPromotion(movementFlags)) {
				incrementPromotions();
			}
			if (MovementUtil.isCheck(movementFlags)) {
				incrementChecks();
			}
			if (MovementUtil.isDiscoveryCheck(movementFlags)) {
				incrementDiscoveryChecks();
			}
			if (MovementUtil.isDoubleCheck(movementFlags)) {
				incrementDoubleChecks();
			}
			if (MovementUtil.isCheckMate(movementFlags)) {
				incrementCheckmates();
			}
			if (MovementUtil.isStalemate(movementFlags)) {
				incrementStalemate();
			}
			return this;
		}
		
		/**
		 * Increments the field {@link MovementMetadata#NODES} by one.
		 * 
		 * @return This builder for further usage.
		 */
		public Builder incrementNodes() {
			return incrementNodes(1);
		}
		
		/**
		 * Adds the value into the field {@link MovementMetadata#NODES}.
		 * 
		 * @param value The value to be added.
		 * 
		 * @return This builder for further usage.
		 */
		public Builder incrementNodes(long value) {
			metadata.values[NODES] += value;
			return this;
		}
		
		/**
		 * Increments the field {@link MovementMetadata#CAPTURES} by one.
		 * 
		 * @return This builder for further usage.
		 */
		public Builder incrementCaptures() {
			return incrementCaptures(1);
		}
		
		/**
		 * Adds the value into the field {@link MovementMetadata#CAPTURES}.
		 * 
		 * @param value The value to be added.
		 * 
		 * @return This builder for further usage.
		 */
		public Builder incrementCaptures(long value) {
			metadata.values[CAPTURES] += value;
			return this;
		}
		
		/**
		 * Increments the field {@link MovementMetadata#EN_PASSANT} by one.
		 * 
		 * @return This builder for further usage.
		 */
		public Builder incrementEnPassant() {
			return incrementEnPassant(1);
		}
		
		/**
		 * Adds the value into the field {@link MovementMetadata#EN_PASSANT}.
		 * 
		 * @param value The value to be added.
		 * 
		 * @return This builder for further usage.
		 */
		public Builder incrementEnPassant(long value) {
			metadata.values[EN_PASSANT] += value;
			return this;
		}
		
		/**
		 * Increments the field {@link MovementMetadata#CASTLING} by one.
		 * 
		 * @return This builder for further usage.
		 */
		public Builder incrementCastling() {
			return incrementCastling(1);
		}
		
		/**
		 * Adds the value into the field {@link MovementMetadata#CASTLING}.
		 * 
		 * @param value The value to be added.
		 * 
		 * @return This builder for further usage.
		 */
		public Builder incrementCastling(long value) {
			metadata.values[CASTLING] += value;
			return this;
		}
		
		/**
		 * Increments the field {@link MovementMetadata#PROMOTIONS} by one.
		 * 
		 * @return This builder for further usage.
		 */
		public Builder incrementPromotions() {
			return incrementPromotions(1);
		}
		
		/**
		 * Adds the value into the field {@link MovementMetadata#PROMOTIONS}.
		 * 
		 * @param value The value to be added.
		 * 
		 * @return This builder for further usage.
		 */
		public Builder incrementPromotions(long value) {
			metadata.values[PROMOTIONS] += value;
			return this;
		}
		
		/**
		 * Increments the field {@link MovementMetadata#CHECKS} by one.
		 * 
		 * @return This builder for further usage.
		 */
		public Builder incrementChecks() {
			return incrementChecks(1);
		}
		
		/**
		 * Adds the value into the field {@link MovementMetadata#CHECKS}.
		 * 
		 * @param value The value to be added.
		 * 
		 * @return This builder for further usage.
		 */
		public Builder incrementChecks(long value) {
			metadata.values[CHECKS] += value;
			return this;
		}
		
		/**
		 * Increments the field {@link MovementMetadata#DISCOVERY_CHECKS} by one.
		 * 
		 * @return This builder for further usage.
		 */
		public Builder incrementDiscoveryChecks() {
			return incrementDiscoveryChecks(1);
		}
		
		/**
		 * Adds the value into the field {@link MovementMetadata#DISCOVERY_CHECKS}.
		 * 
		 * @param value The value to be added.
		 * 
		 * @return This builder for further usage.
		 */
		public Builder incrementDiscoveryChecks(long value) {
			metadata.values[DISCOVERY_CHECKS] += value;
			return this;
		}
		
		/**
		 * Increments the field {@link MovementMetadata#DOUBLE_CHECKS} by one.
		 * 
		 * @return This builder for further usage.
		 */
		public Builder incrementDoubleChecks() {
			return incrementDoubleChecks(1);
		}
		
		/**
		 * Adds the value into the field {@link MovementMetadata#DOUBLE_CHECKS}.
		 * 
		 * @param value The value to be added.
		 * 
		 * @return This builder for further usage.
		 */
		public Builder incrementDoubleChecks(long value) {
			metadata.values[DOUBLE_CHECKS] += value;
			return this;
		}
		
		/**
		 * Increments the field {@link MovementMetadata#CHECKMATES} by one.
		 * 
		 * @return This builder for further usage.
		 */
		public Builder incrementCheckmates() {
			return incrementCheckmates(1);
		}
		
		/**
		 * Adds the value into the field {@link MovementMetadata#CHECKMATES}.
		 * 
		 * @param value The value to be added.
		 * 
		 * @return This builder for further usage.
		 */
		public Builder incrementCheckmates(long value) {
			metadata.values[CHECKMATES] += value;
			return this;
		}
		
		/**
		 * Increments the field {@link MovementMetadata#STALEMATE} by one.
		 * 
		 * @return This builder for further usage.
		 */
		public Builder incrementStalemate() {
			return incrementStalemate(1);
		}
		
		/**
		 * Adds the value into the field {@link MovementMetadata#STALEMATE}.
		 * 
		 * @param value The value to be added.
		 * 
		 * @return This builder for further usage.
		 */
		public Builder incrementStalemate(long value) {
			metadata.values[STALEMATE] += value;
			return this;
		}
		
		/**
		 * Returns the created metadata.
		 * 
		 * @return The created metadata.
		 */
		public MovementMetadata buid() {
			return metadata;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < KEYS.length; i++) {
			if (i > 0) {
				builder.append(", ");
			}
			builder.append(getFieldName(i)).append(" = ").append(getValue(i));
		}
		return builder.toString();
	}
}
