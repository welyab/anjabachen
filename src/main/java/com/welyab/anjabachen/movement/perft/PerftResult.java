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
package com.welyab.anjabachen.movement.perft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.welyab.anjabachen.movement.MovementMetadata;

public class PerftResult {
	
	private final Map<Integer, MovementMetadata> results;
	
	private PerftResult(Map<Integer, MovementMetadata> results) {
		this.results = results;
	}
	
	public List<Integer> getDepths() {
		return results.keySet().stream().sorted().collect(Collectors.toList());
	}
	
	public List<Integer> getAvailableFields(int depth) {
		List<Integer> list = new ArrayList<>();
		MovementMetadata metadata = getMetadata(depth);
		for (int field : MovementMetadata.getFields()) {
			if (metadata.isFieldPresent(field)) {
				list.add(field);
			}
		}
		return list;
	}
	
	public MovementMetadata getMetadata(int depth) {
		return results.get(depth);
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		
		private Map<Integer, MovementMetadata.Builder> metaBuilders = new HashMap<>();
		
		private Builder() {
		}
		
		private MovementMetadata.Builder getMetadataBuilder(int depth) {
			return metaBuilders.computeIfAbsent(depth, i -> MovementMetadata.builder());
		}
		
		public Builder addMetadata(int depth, MovementMetadata metadata) {
			getMetadataBuilder(depth).add(metadata);
			return this;
		}
		
		public Builder incrementNodes(int depth, int value) {
			getMetadataBuilder(depth).incrementNodes(value);
			return this;
		}
		
		public Builder incrementCaptures(int depth, int value) {
			getMetadataBuilder(depth).incrementCaptures(value);
			return this;
		}
		
		public Builder incrementEnPassant(int depth, int value) {
			getMetadataBuilder(depth).incrementEnPassant(value);
			return this;
		}
		
		public Builder incrementCastling(int depth, int value) {
			getMetadataBuilder(depth).incrementCastling(value);
			return this;
		}
		
		public Builder incrementPromotions(int depth, int value) {
			getMetadataBuilder(depth).incrementPromotions(value);
			return this;
		}
		
		public Builder incrementChecks(int depth, int value) {
			getMetadataBuilder(depth).incrementChecks(value);
			return this;
		}
		
		public Builder incrementDiscoveryChecks(int depth, int value) {
			getMetadataBuilder(depth).incrementDiscoveryChecks(value);
			return this;
		}
		
		public Builder incrementDoubleChecks(int depth, int value) {
			getMetadataBuilder(depth).incrementDoubleChecks(value);
			return this;
		}
		
		public Builder incrementCheckmates(int depth, int value) {
			getMetadataBuilder(depth).incrementCheckmates(value);
			return this;
		}
		
		public Builder incrementStalemate(int depth, int value) {
			getMetadataBuilder(depth).incrementStalemate(value);
			return this;
		}
		
		public PerftResult build() {
			HashMap<Integer, MovementMetadata> values = metaBuilders
				.entrySet()
				.stream()
				.reduce(
					new HashMap<Integer, MovementMetadata>(),
					(m, e) -> {
						m.put(e.getKey(), e.getValue().buid());
						return m;
					},
					(m1, m2) -> {
						m1.putAll(m2);
						return m1;
					}
				);
			
			return new PerftResult(values);
		}
	}
}
