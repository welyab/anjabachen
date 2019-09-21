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
		String fen = "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -";
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
	
	private void printPerftResultsComparison(PerftResult expected, PerftResult calculated) {
		List<List<String>> lines = new ArrayList<>();
		for (Long depth : calculated.getAvailableDepths()) {
			List<String> lineValues = new ArrayList<>();
			lineValues.add(String.format("%d", depth));
			lineValues.add("-");
			List<MovementMetadataField> fields = expected.getFields(depth);
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
	
	private String padSpace(String value, int length) {
		StringBuilder stringBuilder = new StringBuilder(value);
		int toPad = Math.abs(value.length() - length);
		for (int i = 0; i < toPad; i++) {
			stringBuilder.append(' ');
		}
		return stringBuilder.toString();
	}
	
	private int getColumnLength(List<List<String>> lines, int column) {
		return lines
			.stream()
			.mapToInt(l -> l.get(column).length())
			.max()
			.orElse(0);
	}
	
	private String compareValues(MovementMetadataField field, Optional<Long> expected, Optional<Long> calculated) {
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
	
	private Optional<Long> getValue(PerftResult perftResult, long depth, MovementMetadataField field) {
		if (!perftResult.isValuePresent(field, depth)) {
			return Optional.empty();
		}
		return Optional.of(perftResult.getValue(depth, field));
	}
}
