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

import java.io.PrintStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.welyab.anjabachen.movement.MovementMetadata;

public class PerftResult {
	
	private final String fen;
	
	private final long timeSpent;
	
	private final Map<Integer, MovementMetadata> results;
	
	private PerftResult(
		String fen,
		long timeSpent,
		Map<Integer, MovementMetadata> results
	) {
		this.fen = fen;
		this.timeSpent = timeSpent;
		this.results = results;
	}
	
	public List<Integer> getDepths() {
		return results
			.keySet()
			.stream()
			.sorted()
			.collect(Collectors.toList());
	}
	
	public MovementMetadata getMetadata(int depth) {
		return results.get(depth);
	}
	
	public static Builder builder(String fen) {
		return new Builder(fen);
	}
	
	public String getFen() {
		return fen;
	}
	
	public long getTimeSpent() {
		return timeSpent;
	}
	
	public void asTextTable() {
		toTable(System.out);
	}
	
	public static void main(String[] args) {
		PerftCalculator
			.perft(
				"r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -",
				5,
				true
			)
			.asTextTable();
	}
	
	public void toTable(PrintStream out) {
		List<List<String>> values = new ArrayList<>();
		List<String> header = new ArrayList<>();
		header.add("depth");
		values.add(header);
		List<String> fieldsNames = MovementMetadata.getFieldsNames();
		for (String fieldName : fieldsNames) {
			header.add(fieldName);
		}
		for (int depth : getDepths()) {
			MovementMetadata metadata = getMetadata(depth);
			List<String> list = new ArrayList<>();
			list.add(Integer.toString(depth));
			values.add(list);
			for (Integer field : MovementMetadata.getFields()) {
				list.add(Long.toString(metadata.getValue(field)));
			}
		}
		Function<Integer, Integer> maxLengthByColumn = column -> {
			return values
				.stream()
				.map(l -> l.get(column))
				.mapToInt(String::length)
				.max()
				.orElse(0);
		};
		StringBuilder horizontalSeparator = new StringBuilder();
		int columnsCount = fieldsNames.size() + 1;
		for (int i = 0; i < columnsCount; i++) {
			horizontalSeparator.append("+");
			horizontalSeparator.append(leftPad("", '-', maxLengthByColumn.apply(i) + 2));
		}
		horizontalSeparator.append("+");
		out.println("Perft Calculation - AN.JA.BA.CH.EN");
		out.println("FEN: " + getFen());
		out.printf(Locale.US, "Total time: %s%n", getTimeString(timeSpent));
		out.println(horizontalSeparator);
		for (int i = 0; i < values.size(); i++) {
			List<String> list = values.get(i);
			for (int j = 0; j < list.size(); j++) {
				String value = list.get(j);
				out.print("| ");
				out.print(leftPad(value, ' ', maxLengthByColumn.apply(j)));
				out.print(" ");
			}
			out.println("|");
			if (i == 0) {
				out.println(horizontalSeparator);
			}
		}
		out.println(horizontalSeparator);
	}
	
	private static final String getTimeString(long millis) {
		Duration duration = Duration.ofMillis(millis);
		StringBuilder builder = new StringBuilder();
		if (duration.toHoursPart() > 0) {
			builder.append(duration.toHoursPart() + " h ");
		}
		if (duration.toMinutesPart() > 0) {
			builder.append(duration.toMinutesPart() + " m ");
		}
		builder.append(duration.toSecondsPart())
			.append(".")
			.append(duration.toMillisPart())
			.append(" s");
		return builder.toString();
	}
	
	private static String leftPad(String str, char c, int length) {
		StringBuilder stringBuilder = new StringBuilder();
		int diff = length - str.length();
		for (int i = 0; i < diff; i++) {
			stringBuilder.append(c);
		}
		stringBuilder.append(str);
		return stringBuilder.toString();
	}
	
	public static class Builder {
		
		private Map<Integer, MovementMetadata.Builder> metaBuilders = new HashMap<>();
		
		private String fen;
		
		private Builder(String fen) {
			this.fen = fen;
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
		
		public PerftResult build(long timeSpent) {
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
			
			return new PerftResult(
				fen,
				timeSpent,
				values
			);
		}
	}
}
