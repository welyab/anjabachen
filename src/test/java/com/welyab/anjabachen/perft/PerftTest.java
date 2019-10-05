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

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import com.welyab.anjabachen.movement.MovementMetadataField;

public class PerftTest {
	
	@Test
	public void perft() {
		String fen = "5rk1/3rqbpp/8/8/8/2B3R1/PP6/2K5 w - -";
		PerftExpectedResults.getAvailableResults();
		PerftResult perftResultExpected = PerftExpectedResults.loadResult(fen);
		long depth = 3;
		PerftCalculator perftCalculator = new PerftCalculator(fen, (int) depth);
		System.out.println("=====================================================================");
		System.out.println(perftCalculator.getBoard().toString(true));
		System.out.printf("FEN: %s%n", fen);
		long t1 = System.currentTimeMillis();
		PerftResult perftResultCalculated = perftCalculator.calculate();
		long t2 = System.currentTimeMillis();
		System.out.printf("Elapsed time: %s %n", formatTime(t2 - t1));
		printPerftResultsComparison(perftResultExpected, perftResultCalculated);
	}
	
	public static void main(String[] args) {
		PerftCalculator perftCalculator = new PerftCalculator("1kr1q3/ppp5/8/8/8/1R6/8/1K5B w - - 0 1", 6);
		PerftResult perft = perftCalculator.calculate();
		printPerftResultsComparison(perft, perft);
	}
	
	@Test
	public void perft_1() {
		for (String fen : PerftExpectedResults.getAvailableResults()) {
			PerftResult perftResultExpected = PerftExpectedResults.loadResult(fen);
			long depth = getMaxDepth(perftResultExpected);
			PerftCalculator perftCalculator = new PerftCalculator(fen, (int) depth);
			System.out.println("=====================================================================");
			System.out.println(perftCalculator.getBoard().toString(true));
			System.out.printf("FEN: %s%n", fen);
			long t1 = System.currentTimeMillis();
			PerftResult perftResultCalculated = perftCalculator.calculate();
			long t2 = System.currentTimeMillis();
			System.out.printf("Elapsed time: %s %n", formatTime(t2 - t1));
			printPerftResultsComparison(perftResultExpected, perftResultCalculated);
		}
	}
	
	private String formatTime(long time) {
		Duration duration = Duration.ofMillis(time);
		StringBuilder builder = new StringBuilder();
		if (duration.toHoursPart() > 0) {
			builder.append(String.format("%d hrs", duration.toHoursPart()));
		}
		if (duration.toMinutesPart() > 0) {
			if (builder.length() > 0) {
				builder.append(" ");
			}
			builder.append(String.format("%d min", duration.toMinutesPart()));
		}
		if (duration.toSeconds() > 0) {
			if (builder.length() > 0) {
				builder.append(" ");
			}
			builder.append(String.format("%d sec", duration.toSecondsPart()));
		}
		if (duration.toMillisPart() > 0) {
			if (builder.length() > 0) {
				builder.append(" ");
			}
			builder.append(String.format("%d mil", duration.toMillisPart()));
		}
		return builder.toString();
	}
	
	private Long getMaxDepth(PerftResult result) {
		return result
			.getAvailableDepths()
			.stream()
			.filter(l -> result.getValue(l, MovementMetadataField.NODES) <= 999000000)
			.mapToLong(l -> l)
			.max()
			.orElseThrow();
	}
	
	private static void printPerftResultsComparison(PerftResult expected, PerftResult calculated) {
		List<List<String>> lines = new ArrayList<>();
		for (Long depth : calculated.getAvailableDepths()) {
			List<String> lineValues = new ArrayList<>();
			lineValues.add(String.format("%d", depth));
			lineValues.add("-");
			List<MovementMetadataField> fields = calculated.getFields(depth);
			for (MovementMetadataField field : fields) {
				Optional<Long> expectedValue = getValue(expected, depth, field);
				Optional<Long> calculatedValue = getValue(calculated, depth, field);
				lineValues.add(compareValues(field, expectedValue, calculatedValue));
			}
			lines.add(lineValues);
		}
		for (List<String> line : lines) {
			for (int i = 0; i < line.size(); i++) {
				String column = line.get(i);
				System.out.printf("%s ", padSpace(column, getColumnLength(lines, i)));
			}
			System.out.println();
		}
	}
	
	private static String padSpace(String value, int length) {
		StringBuilder stringBuilder = new StringBuilder(value);
		int toPad = Math.abs(value.length() - length);
		for (int i = 0; i < toPad; i++) {
			stringBuilder.append(' ');
		}
		return stringBuilder.toString();
	}
	
	private static int getColumnLength(List<List<String>> lines, int column) {
		return lines
			.stream()
			.mapToInt(l -> l.get(column).length())
			.max()
			.orElse(0);
	}
	
	private static String compareValues(MovementMetadataField field, Optional<Long> expected,
			Optional<Long> calculated) {
		StringBuilder builder = new StringBuilder();
		builder.append(field).append(": ");
		if (expected.equals(calculated)) {
			builder.append(expected.get());
		} else {
			builder.append("expected=").append(expected.orElse(0L)).append(", ");
			builder.append("calculated=").append(calculated.orElse(0L)).append(", ");
			builder.append("diff=").append(calculated.orElse(0L) - expected.orElse(0L));
		}
		return builder.toString();
	}
	
	private static Optional<Long> getValue(PerftResult perftResult, long depth, MovementMetadataField field) {
		if (!perftResult.isValuePresent(field, depth)) {
			return Optional.empty();
		}
		return Optional.of(perftResult.getValue(depth, field));
	}
}
