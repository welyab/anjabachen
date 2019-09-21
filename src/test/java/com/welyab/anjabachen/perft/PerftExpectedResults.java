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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.welyab.anjabachen.movement.MovementMetadataField;

/**
 * Auxiliary class for load perft result from CSV files to be used in the movement generation tests.
 * 
 * @author Welyab Paula
 */
public class PerftExpectedResults {
	
	@SuppressWarnings("javadoc")
	private static final String NL = String.format("%n");
	
	@SuppressWarnings("javadoc")
	private static final String DEPTH_COL = "DEPTH";
	
	@SuppressWarnings("javadoc")
	private static final Map<String, String> csvResults = Map.of(
		"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
		string(
			"DEPTH,NODES,CAPTURES,EN_PASSANTS,CASTLINGS,PROMOTIONS,CHECKS,DISCOVERY_CHECKS,DOUBLE_CHECKS,CHECKMATES",
			"1,20,0,0,0,0,0,0,0,0",
			"2,400,0,0,0,0,0,0,0,0",
			"3,8902,34,0,0,0,12,0,0,0",
			"4,197281,1576,0,0,0,469,0,0,8",
			"5,4865609,82719,258,0,0,27351,6,0,347",
			"6,119060324,2812008,5248,0,0,809099,329,46,10828",
			"7,3195901860,108329926,319617,883453,0,33103848,18026,1628,435767",
			"8,84998978956,3523740106,7187977,23605205,0,968981593,847039,147215,9852036",
			"9,2439530234167,125208536153,319496827,1784356000,17334376,36095901903,37101713,5547231,400191963"
		),
		"r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -",
		string(
			"DEPTH,NODES,CAPTURES,EN_PASSANTS,CASTLINGS,PROMOTIONS,CHECKS,DISCOVERY_CHECKS,DOUBLE_CHECKS,CHECKMATES",
			"1,48,8,0,2,0,0,0,0,0",
			"2,2039,351,1,91,0,3,0,0,0",
			"3,97862,17102,45,3162,0,993,0,0,1",
			"4,4085603,757163,1929,128013,15172,25523,42,6,43",
			"5,193690690,35043416,73365,4993637,8392,3309887,19883,2637,30171",
			"6,8031647685,1558445089,3577504,184513607,56627920,92238050,568417,54948,360003"
		),
		"8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -",
		string(
			"DEPTH,NODES,CAPTURES,EN_PASSANTS,CASTLINGS,PROMOTIONS,CHECKS,DISCOVERY_CHECKS,DOUBLE_CHECKS,CHECKMATES",
			"1,14,1,0,0,0,2,0,0,0",
			"2,191,14,0,0,0,10,0,0,0",
			"3,2812,209,2,0,0,267,3,0,0",
			"4,43238,3348,123,0,0,1680,106,0,17",
			"5,674624,52051,1165,0,0,52950,1292,3,0",
			"6,11030083,940350,33325,0,7552,452473,26067,0,2733",
			"7,178633661,14519036,294874,0,140024,12797406,370630,3612,87",
			"8,3009794393,267586558,8009239,0,6578076,135626805,7181487,1630,450410"
		),
		"r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1",
		string(
			"DEPTH,NODES,CAPTURES,EN_PASSANTS,CASTLINGS,PROMOTIONS,CHECKS,CHECKMATES",
			"1,6,0,0,0,0,0,0",
			"2,264,87,0,6,48,10,0",
			"3,9467,1021,4,0,120,38,22",
			"4,422333,131393,0,7795,60032,15492,5",
			"5,15833292,2046173,6512,0,329464,200568,50562",
			"6,706045033,210369132,212,10882006,81102984,26973664,81076"
		),
		"rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8",
		string(
			"DEPTH,NODES",
			"1,44",
			"2,1486",
			"3,62379",
			"4,2103487",
			"5,89941194"
		),
		"r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10",
		string(
			"DEPTH,NODES",
			"1,46",
			"2,2079",
			"3,89890",
			"4,3894594",
			"5,164075551",
			"6,6923051137",
			"7,287188994746",
			"8,11923589843526",
			"9,490154852788714"
		)
	);
	
	public static Set<String> getAvailableResults() {
		return csvResults
			.keySet()
			.stream()
			.sorted()
			.collect(Collectors.toSet());
	}
	
	private static String string(String... strings) {
		return Arrays
			.stream(strings)
			.map(StringBuilder::new)
			.reduce((s1, s2) -> s1.append(NL).append(s2))
			.map(StringBuilder::toString)
			.orElse("");
	}
	
	public static PerftResult loadResult(String fen) {
		String csv = csvResults.get(fen);
		try (StringReader stringReader = new StringReader(csv)) {
			BufferedReader reader = new BufferedReader(stringReader);
			String line = null;
			List<String> columnNames = splitLine(reader.readLine());
			int depthIndex = columnNames.indexOf(DEPTH_COL);
			PerftResult.Builder builder = PerftResult.builder();
			while ((line = reader.readLine()) != null) {
				List<String> columnValues = splitLine(line);
				for (int i = 0; i < columnValues.size(); i++) {
					int depth = Integer.parseInt(columnValues.get(depthIndex));
					if (depthIndex != i) {
						MovementMetadataField field = MovementMetadataField.valueOf(columnNames.get(i));
						long value = Long.parseLong(columnValues.get(i));
						builder.addValue(depth, field, value);
					}
				}
			}
			return builder.build();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	private static List<String> splitLine(String line) {
		return Arrays.asList(line.split(","));
	}
}
