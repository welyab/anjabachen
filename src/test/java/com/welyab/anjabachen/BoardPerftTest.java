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
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Logger;

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

	@Test
	@SuppressWarnings("javadoc")
	public void perftTest1() throws IOException {
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
					.incrementCaptureCount(0)
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
	public void perft_r3k2r_p1ppqpb1_bn2pnp1_3PN3_1p2P3_2N2Q1p_PPPBBPPP_R3K2R_w_KQkq_x() throws IOException {
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
					.build(),
				4,
				PieceMovementMeta
					.builder()
					.incrementTotalMovements(4085603)
					.incrementCaptureCount(757163)
					.incrementEnPassantCount(1929)
					.incrementCastlings(128013)
					.incrementPromotionCount(15172)
					.incrementCheckCount(25523)
					.incrementDiscoveryCheckCount(42)
					.incrementDoubleCheckCount(6)
					.incrementCheckmateCount(43)
					.build()
			)
		);
	}

	private void test(
			String fen,
			Map<Integer, PieceMovementMeta> expectedPerftResults
	) throws IOException {
		Logger.info("Executing perft testing");
		Logger.info("FEN: {}", fen);
		Perft perft = new Perft(fen, 3);
		long t1 = System.currentTimeMillis();
		Map<Integer, PieceMovementMeta> metas = perft.execute();
		long t2 = System.currentTimeMillis();
		PerftPrinter perftPrinter = new PerftPrinter();
		ByteArrayOutputStream outArray = new ByteArrayOutputStream();
		PrintWriter printWriter = new PrintWriter(outArray);
		perftPrinter.print(
			metas.entrySet()
				.stream()
				.sorted((e1, e2) -> e1.getKey() - e2.getKey())
				.map(e -> e.getValue())
				.collect(Collectors.toList()),
			printWriter
		);
		BufferedReader input = new BufferedReader(
			new InputStreamReader(new ByteArrayInputStream(outArray.toByteArray()))
		);
		String line = null;
		while ((line = input.readLine()) != null) {
			Logger.info(line);
		}
		Logger.info("Total time: {} ms", t2 - t1);
		Logger.info("");
	}
}
