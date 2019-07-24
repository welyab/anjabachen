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

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.pmw.tinylog.Configurator;

import com.welyab.anjabachen.util.TableData;
import com.welyab.anjabachen.util.TablePrinter;
import com.welyab.anjabachen.util.TablePrinterConfig;

/**
 * Tests the movement generation logic inside <cod</code>
 *
 * @author Welyab Paula
 */
public class BoardPerftTest {

    private static final List<String> COLUMN_NAMES = Arrays.asList(
	    "Depth",
	    "Nodes",
	    "Captures",
	    "En passats",
	    "Castles",
	    "Promotions",
	    "Checks",
	    "Dis_checks",
	    "Dou_checks",
	    "Checkmates",
	    "Stalemates"
    );

    static {
	Configurator
		.defaultConfig()
		.formatPattern("{message}")
		.activate();
    }

    private void test(
	    String fen,
	    Map<Integer, PieceMovementMeta> expectedPerftResults
    ) throws IOException {
	Perft perft = new Perft(fen, expectedPerftResults.size());
	System.out.println(perft.getBoard().toString(true));
	Map<Integer, PieceMovementMeta> metas = perft.execute();
	TablePrinter.print(
		COLUMN_NAMES,
		new TableData() {

		    @Override
		    public int rowCount() {
			return metas.size();
		    }

		    @Override
		    public int columnCount() {
			return COLUMN_NAMES.size();
		    }

		    @Override
		    public String getValue(int row, int column) {
			PieceMovementMeta meta = metas.get(row + 1);
			PieceMovementMeta meta2 = expectedPerftResults.get(row + 1);
			// 0 "Depth",
			// 1 "Nodes",
			// 2 "Captures",
			// 3 "En passats",
			// 4 "Castles",
			// 5 "Promotions",
			// 6 "Checks",
			// 7 "Dis_checks",
			// 8 "Dou_checks",
			// 9 "Checkmates",
			// 10 "Stalemates"
			return switch (column) {
			    case 0 -> Integer.toString(row + 1);
			    case 1 -> s(meta.getCaptureCount(), meta2.getCaptureCount());
			    case 2 -> s(meta.getCaptureCount(), meta2.getCaptureCount());
			    case 3 -> s(meta.getEnPassantCount(), meta2.getEnPassantCount());
			    case 4 -> s(meta.getCastlingsCount(), meta2.getCastlingsCount());
			    case 5 -> s(meta.getPromotionCount(), meta2.getPromotionCount());
			    case 6 -> s(meta.getCheckCount(), meta2.getCheckCount());
			    case 7 -> s(meta.getDiscoveryCheckCount(), meta2.getDiscoveryCheckCount());
			    case 8 -> s(meta.getDoubleCheckCount(), meta2.getDoubleCheckCount());
			    case 9 -> s(meta.getCheckmateCount(), meta2.getCheckmateCount());
			    case 10 -> s(meta.getStalemateCount(), meta2.getStalemateCount());
			    default -> throw new ChessError("Unexpected column");
			};
		    }
		},
		TablePrinterConfig
			.builder()
			.separeteColumns(
				true
			)
			.build()
	);
	System.out.println();
    }

    private String s(int expected, int actual) {
	return String.format(
		"%s c=%d e=%d d=%d",
		(expected - actual) == 0 ? "" : "*",
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
				.build()
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
				.incrementCheckmateCount()
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
