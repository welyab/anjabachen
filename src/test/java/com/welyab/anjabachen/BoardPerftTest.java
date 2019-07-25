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
package com.welyab.anjabachen;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.junit.Test;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Logger;

import com.welyab.anjabachen.util.TablePrinter;
import com.welyab.anjabachen.util.TablePrinterColumnModel;
import com.welyab.anjabachen.util.TablePrinterConfig;
import com.welyab.anjabachen.util.TablePrinterDataModel;

/**
 * Tests the movement generation logic inside <cod</code>
 *
 * @author Welyab Paula
 */
public class BoardPerftTest {

	static {
		Configurator
			.defaultConfig()
			.formatPattern("{message}")
			.activate();
	}

	private static final List<T> columnNames = Arrays.asList(
		new T(
			"Depth",
			(depth, expected, calculated) -> Integer.toString(depth)
		),
		new T(
			"Nodes",
			(depth, expected, calculated) -> formatValue(
				extractIntValue(expected, PieceMovementMeta::getTotalMovements),
				extractIntValue(calculated, PieceMovementMeta::getTotalMovements)
			)
		),
		new T(
			"Captures",
			(depth, expected, calculated) -> formatValue(
				extractIntValue(expected, PieceMovementMeta::getCaptureCount),
				extractIntValue(calculated, PieceMovementMeta::getCaptureCount)
			)
		),
		new T(
			"En passats",
			(depth, expected, calculated) -> formatValue(
				extractIntValue(expected, PieceMovementMeta::getEnPassantCount),
				extractIntValue(calculated, PieceMovementMeta::getEnPassantCount)
			)
		),
		new T(
			"Castles",
			(depth, expected, calculated) -> formatValue(
				extractIntValue(expected, PieceMovementMeta::getCastlingsCount),
				extractIntValue(calculated, PieceMovementMeta::getCastlingsCount)
			)
		),
		new T(
			"Promotions",
			(depth, expected, calculated) -> formatValue(
				extractIntValue(expected, PieceMovementMeta::getPromotionCount),
				extractIntValue(calculated, PieceMovementMeta::getPromotionCount)
			)
		),
		new T(
			"Checks",
			(depth, expected, calculated) -> formatValue(
				extractIntValue(expected, PieceMovementMeta::getCheckCount),
				extractIntValue(calculated, PieceMovementMeta::getCheckCount)
			)
		),
		new T(
			"Dis_checks",
			(depth, expected, calculated) -> formatValue(
				extractIntValue(expected, PieceMovementMeta::getDiscoveryCheckCount),
				extractIntValue(calculated, PieceMovementMeta::getDiscoveryCheckCount)
			)
		),
		new T(
			"Dou_checks",
			(depth, expected, calculated) -> formatValue(
				extractIntValue(expected, PieceMovementMeta::getDoubleCheckCount),
				extractIntValue(calculated, PieceMovementMeta::getDoubleCheckCount)
			)
		),
		new T(
			"Checkmates",
			(depth, expected, calculated) -> formatValue(
				extractIntValue(expected, PieceMovementMeta::getCheckmateCount),
				extractIntValue(calculated, PieceMovementMeta::getCheckmateCount)
			)
		),
		new T(
			"Stalemates",
			(depth, expected, calculated) -> formatValue(
				extractIntValue(expected, PieceMovementMeta::getStalemateCount),
				extractIntValue(calculated, PieceMovementMeta::getStalemateCount)
			)
		)
	);

	private static Optional<Integer> extractIntValue(
			PieceMovementMeta meta,
			Function<PieceMovementMeta, Integer> extractor
	) {
		return Optional.ofNullable(meta).map(extractor::apply);
	}

	private static String formatValue(
			Optional<Integer> expectedValue,
			Optional<Integer> calculatedValue
	) {
		return new StringBuilder()
			.append(expectedValue.equals(calculatedValue) ? "" : "* ")
			.append(expectedValue.map(String::valueOf).orElse("<no value>"))
			.append(" ")
			.append(calculatedValue.map(String::valueOf).orElse("<no value>"))
			.append(" ")
			.append(calculatedValue.orElse(0) - expectedValue.orElse(0))
			.toString();
	}

	@FunctionalInterface
	private interface ValueFormatter {

		String format(
				int depth,
				PieceMovementMeta expectedMeta,
				PieceMovementMeta calculatedMeta
		);
	}

	private static class ColumnModel implements TablePrinterColumnModel {

		@Override
		public int columnCount() {
			return columnNames.size();
		}

		@Override
		public String getColumnName(int columnNumber) {
			return columnNames.get(columnNumber).columnName;
		}

	}

	private static class T {

		final String columnName;

		final ValueFormatter valueFormatter;

		public T(
				String columnName,
				ValueFormatter valueFormatter
		) {
			this.columnName = columnName;
			this.valueFormatter = valueFormatter;
		}

		public ValueFormatter getValueFormatter() {
			return valueFormatter;
		}
	}

	private static class DataModel implements TablePrinterDataModel {

		private Map<Integer, PieceMovementMeta> expectedMetadata;

		private Map<Integer, PieceMovementMeta> calculatedMetadata;

		public DataModel(
				Map<Integer, PieceMovementMeta> expectedMetadata,
				Map<Integer, PieceMovementMeta> calculatedMetadata
		) {
			this.expectedMetadata = expectedMetadata;
			this.calculatedMetadata = calculatedMetadata;
		}

		@Override
		public int rowCount() {
			return expectedMetadata.size();
		}

		@Override
		public int columnCount() {
			return columnNames.size();
		}

		@Override
		public String getValue(int row, int column) {
			return columnNames.get(column)
				.getValueFormatter()
				.format(
					row + 1,
					expectedMetadata.get(row + 1),
					calculatedMetadata.get(row + 1)
				);
		}
	}

	private void test(
			String fen,
			Map<Integer, PieceMovementMeta> expectedPerftResults
	) throws IOException {
		Logger.info("=========================================================");
		Logger.info("Executing perft test");
		Logger.info("FEN: {}", fen);
		Perft perft = new Perft(fen, expectedPerftResults.size());
		Board board = perft.getBoard();
		// printBoard(board);
		Map<Integer, PieceMovementMeta> calculatedMetadatas = perft.execute();

		var byteOut = new ByteArrayOutputStream();
		var writer = new PrintWriter(byteOut);
		TablePrinter.print(
			new ColumnModel(),
			new DataModel(
				expectedPerftResults,
				calculatedMetadatas
			),
			TablePrinterConfig
				.builder()
				.separeteColumns(true)
				.writer(writer)
				.build()
		);
		var reader = new BufferedReader(
			new InputStreamReader(new ByteArrayInputStream(byteOut.toByteArray()))
		);
		Logger.info("Perft results by depth:");
		var line = "";
		while ((line = reader.readLine()) != null) {
			Logger.info(line);
		}

		Logger.info(">> Legend: ");
		Logger.info(">> * expected calcualted difference");
		Logger.info(">> * - indicates a difference between expected and calculated value");
		Logger.info(">> expecetd - indicates the expected value for perft");
		Logger.info(">> calculated - the perft result calculated by the program");
		Logger.info(">> difference - the difference between expected and calculated values");

		Logger.info("");
	}

	private static void printBoard(Board board) throws IOException {
		var reader = new BufferedReader(new StringReader(board.toString()));
		var line = "";
		var firstLine = true;
		while ((line = reader.readLine()) != null) {
			if (firstLine) {
				Logger.info("Board: " + line);
			} else {
				Logger.info("       " + line);
			}
			firstLine = false;
		}
	}

	private String s(int expected, int actual) {
		return String.format(
			"%s c=%d e=%d d=%d",
			expected - actual == 0 ? "" : "*",
			expected,
			actual,
			expected - actual
		);
	}

	@Test
	@SuppressWarnings("javadoc")
	public void perft_8_2p5_3p4_KP5r_1R3p1k_8_4P1P1_8_w_x_x_() throws IOException {
		test(
			"8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - - 0 1",
			Map.of(
				1,
				PieceMovementMeta
					.builder()
					.incrementTotalMovements(14)
					.incrementCaptureCount(1)
					.incrementEnPassantCount(0)
					.incrementCastlings(0)
					.incrementPromotionCount(0)
					.incrementCheckCount(2)
					.incrementDiscoveryCheckCount(0)
					.incrementDoubleCheckCount(0)
					.incrementCheckmateCount(0)
					.build(),
				2,
				PieceMovementMeta
					.builder()
					.incrementTotalMovements(191)
					.incrementCaptureCount(14)
					.incrementEnPassantCount(0)
					.incrementCastlings(0)
					.incrementPromotionCount(0)
					.incrementCheckCount(10)
					.incrementDiscoveryCheckCount(0)
					.incrementDoubleCheckCount(0)
					.incrementCheckmateCount(0)
					.build(),
				3,
				PieceMovementMeta
					.builder()
					.incrementTotalMovements(2812)
					.incrementCaptureCount(209)
					.incrementEnPassantCount(2)
					.incrementCastlings(0)
					.incrementPromotionCount(0)
					.incrementCheckCount(267)
					.incrementDiscoveryCheckCount(3)
					.incrementDoubleCheckCount(0)
					.incrementCheckmateCount(0)
					.build(),
				4,
				PieceMovementMeta
					.builder()
					.incrementTotalMovements(43238)
					.incrementCaptureCount(3348)
					.incrementEnPassantCount(123)
					.incrementCastlings(0)
					.incrementPromotionCount(0)
					.incrementCheckCount(1680)
					.incrementDiscoveryCheckCount(106)
					.incrementDoubleCheckCount(0)
					.incrementCheckmateCount(17)
					.build(),
				5,
				PieceMovementMeta
					.builder()
					.incrementTotalMovements(674624)
					.incrementCaptureCount(52051)
					.incrementEnPassantCount(1165)
					.incrementCastlings(0)
					.incrementPromotionCount(0)
					.incrementCheckCount(52950)
					.incrementDiscoveryCheckCount(1292)
					.incrementDoubleCheckCount(3)
					.incrementCheckmateCount(0)
					.build()//,
//				6,
//				PieceMovementMeta
//					.builder()
//					.incrementTotalMovements(11030083)
//					.incrementCaptureCount(940350)
//					.incrementEnPassantCount(33325)
//					.incrementCastlings(0)
//					.incrementPromotionCount(7552)
//					.incrementCheckCount(452473)
//					.incrementDiscoveryCheckCount(26067)
//					.incrementDoubleCheckCount(0)
//					.incrementCheckmateCount(2733)
//					.build()
			)
		);
	}

	@Test
	@SuppressWarnings("javadoc")
	public void perft_rnbqkbnr_pppppppp_8_8_8_8_PPPPPPPP_RNBQKBNR_w_KQkq_x_0_1() throws IOException {
		test(
			"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
			Map.of(
				1,
				PieceMovementMeta
					.builder()
					.incrementTotalMovements(20)
					.incrementCaptureCount(0)
					.incrementEnPassantCount(0)
					.incrementCastlings(0)
					.incrementPromotionCount(0)
					.incrementCheckCount(0)
					.incrementDiscoveryCheckCount(0)
					.incrementDoubleCheckCount(0)
					.incrementCheckmateCount(0)
					.build(),
				2,
				PieceMovementMeta
					.builder()
					.incrementTotalMovements(400)
					.incrementCaptureCount(0)
					.incrementEnPassantCount(0)
					.incrementCastlings(0)
					.incrementPromotionCount(0)
					.incrementCheckCount(0)
					.incrementDiscoveryCheckCount(0)
					.incrementDoubleCheckCount(0)
					.incrementCheckmateCount(0)
					.build(),
				3,
				PieceMovementMeta
					.builder()
					.incrementTotalMovements(8902)
					.incrementCaptureCount(34)
					.incrementEnPassantCount(0)
					.incrementCastlings(0)
					.incrementPromotionCount(0)
					.incrementCheckCount(12)
					.incrementDiscoveryCheckCount(0)
					.incrementDoubleCheckCount(0)
					.incrementCheckmateCount(0)
					.build(),
				4,
				PieceMovementMeta
					.builder()
					.incrementTotalMovements(197281)
					.incrementCaptureCount(1576)
					.incrementEnPassantCount(0)
					.incrementCastlings(0)
					.incrementPromotionCount(0)
					.incrementCheckCount(469)
					.incrementDiscoveryCheckCount(0)
					.incrementDoubleCheckCount(0)
					.incrementCheckmateCount(8)
					.build()
			)
		);
	}

	@Test
	@SuppressWarnings("javadoc")
	public void perft_r3k2r_p1ppqpb1_bn2pnp1_3PN3_1p2P3_2N2Q1p_PPPBBPPP_R3K2R_w_KQkq_x()
			throws IOException {
		test(
			"r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1",
			Map.of(
				1,
				PieceMovementMeta
					.builder()
					.incrementTotalMovements(48)
					.incrementCaptureCount(8)
					.incrementEnPassantCount(0)
					.incrementCastlings(2)
					.incrementPromotionCount(0)
					.incrementCheckCount(0)
					.incrementDiscoveryCheckCount(0)
					.incrementDoubleCheckCount(0)
					.incrementCheckmateCount(0)
					.build(),
				2,
				PieceMovementMeta
					.builder()
					.incrementTotalMovements(2039)
					.incrementCaptureCount(351)
					.incrementEnPassantCount(1)
					.incrementCastlings(91)
					.incrementPromotionCount(0)
					.incrementCheckCount(3)
					.incrementDiscoveryCheckCount(0)
					.incrementDoubleCheckCount(0)
					.incrementCheckmateCount(0)
					.build(),
				3,
				PieceMovementMeta
					.builder()
					.incrementTotalMovements(97862)
					.incrementCaptureCount(17102)
					.incrementEnPassantCount(45)
					.incrementCastlings(3162)
					.incrementPromotionCount(0)
					.incrementCheckCount(993)
					.incrementDiscoveryCheckCount(0)
					.incrementDoubleCheckCount(0)
					.incrementCheckmateCount(1)
					.build()
			)
		);
	}
}
