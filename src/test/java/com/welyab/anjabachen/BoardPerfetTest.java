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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import org.junit.Test;

public class BoardPerfetTest {

	private final List<MetadataInfoPrinter> printers = Collections.unmodifiableList(
		Arrays.asList(
			new MetadataInfoPrinter(
				"Depth",
				(p, e) -> fixedWidth(p.columnName.length(), e.deepth)
			),
			new MetadataInfoPrinter(
				"Nodes",
				(p, e) -> fixedWidth(p.columnName.length(), e.metadata.getTotalMovements())
			),
			new MetadataInfoPrinter(
				"Captures",
				(p, e) -> fixedWidth(p.columnName.length(), e.metadata.getCaptureCount())
			),
			new MetadataInfoPrinter(
				"En passant",
				(p, e) -> fixedWidth(p.columnName.length(), e.metadata.getEnPassantCount())
			),
			new MetadataInfoPrinter(
				"Castling",
				(p, e) -> fixedWidth(p.columnName.length(), e.metadata.getCastlingsCount())
			),
			new MetadataInfoPrinter(
				"Promotions",
				(p, e) -> fixedWidth(p.columnName.length(), e.metadata.getPromotionCount())
			),
			new MetadataInfoPrinter(
				"Checks",
				(p, e) -> fixedWidth(p.columnName.length(), 0)
			),
			new MetadataInfoPrinter(
				"Discovery Checks",
				(p, e) -> fixedWidth(p.columnName.length(), 0)
			),
			new MetadataInfoPrinter(
				"Double Checks",
				(p, e) -> fixedWidth(p.columnName.length(), 0)
			),
			new MetadataInfoPrinter(
				"Checkmates",
				(p, e) -> fixedWidth(p.columnName.length(), 0)
			)
		)
	);

	private String fixedWidth(int width, int value) {
		String format = String.format("%%%dd", width);
		return String.format(format, value);
	}

	private class PieceMovementMetaEnhancer {

		final PieceMovementMeta metadata;

		final int deepth;

		public PieceMovementMetaEnhancer(PieceMovementMeta metadata, int deepth) {
			this.metadata = metadata;
			this.deepth = deepth;
		}
	}

	private class MetadataInfoPrinter {

		final String columnName;

		final BiFunction<MetadataInfoPrinter, PieceMovementMetaEnhancer, String> valueFormatter;

		public MetadataInfoPrinter(
				String columnName,
				BiFunction<MetadataInfoPrinter, PieceMovementMetaEnhancer, String> valueFormatter
		) {
			this.columnName = columnName;
			this.valueFormatter = valueFormatter;
		}

		String format(PieceMovementMetaEnhancer enhancer) {
			return valueFormatter.apply(this, enhancer);
		}
	}

	@Test
	public void position0() {
		walkTree("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", 2);
	}
	
	@Test
	public void position1() {
		walkTree("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1", 2);
	}

	private void walkTree(String fen, int maxDeepth) {
		Map<Integer, PieceMovementMeta> metas = new HashMap<>();
		calc(new Board(fen), 0, maxDeepth, metas);
		printInfo(metas);
	}

	private void printInfo(Map<Integer, PieceMovementMeta> metas) {
		String columns = printers
			.stream()
			.map(p -> p.columnName)
			.reduce((c1, c2) -> String.format("%s  %s", c1, c2))
			.get();
		System.out.println(columns);
		metas.entrySet()
			.stream()
			.sorted((e1, e2) -> e1.getKey() - e2.getKey())
			.map(e -> new PieceMovementMetaEnhancer(e.getValue(), e.getKey()))
			.forEach(enhancer -> {
				String values = printers
					.stream()
					.map(p -> p.format(enhancer))
					.reduce((s1, s2) -> String.format("%s  %s", s1, s2))
					.get();
				System.out.println(values);
			});
	}

	private static void calc(Board board, int currentDeepth, int maxDeepth, Map<Integer, PieceMovementMeta> metas) {
		if (currentDeepth <= maxDeepth) {
			PieceMovementMeta.Builder pieceMovementMetaBuilder = PieceMovementMeta.builder();
			PieceMovements movements = board.getMovements();
			PieceMovementMeta meta = movements.getMeta();
			metas.put(
				currentDeepth,
				PieceMovementMeta
					.builder()
					.add(meta)
					.add(metas.getOrDefault(currentDeepth, PieceMovementMeta.empty()))
					.build()
			);
			for (PieceMovement pieceMovement : movements) {
				for (MovementTarget movementTarget : pieceMovement) {
					Board boardTemp = new Board(board.getFen());
					boardTemp.move(
						pieceMovement.getPosition(),
						movementTarget.getPosition(),
						movementTarget.getPiece().getType()
					);
					calc(
						boardTemp,
						currentDeepth + 1,
						maxDeepth,
						metas
					);
				}
			}
		}
	}
}
