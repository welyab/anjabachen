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
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.google.common.collect.ImmutableList;

/**
 * Auxiliary class for load perft result from CSV files to be used in the movement generation tests.
 * 
 * @author Welyab Paula
 */
public class TestPerftResults {
	
	@SuppressWarnings("javadoc")
	private static final String PERFT_RESULTS_TEST_FILES_LOCATION = "com/welyab/anjabachen/perft/";
	
	/** Value separator used in the CSV files. */
	private static final String VALUE_SEPARATOR = ",";
	
	/** Perft results loaded from CSV files. */
	public static final ImmutableList<FenPerftResult> results;
	
	static {
		try {
			results = ImmutableList
				.of(
					new FenPerftResult(
						"8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -",
						loadPerftResult("perft_results_8_2p5_3p4_KP5r_1R3p1k_8_4P1P1_8_w.csv")
					)
				);
		} catch (IOException error) {
			throw new IOError(error);
		}
	}
	
	@SuppressWarnings("javadoc")
	private static PerftResult loadPerftResult(String csvFileName) throws IOException {
		String path = PERFT_RESULTS_TEST_FILES_LOCATION + csvFileName;
		PerftResult.Builder builder = PerftResult.builder();
		try (InputStream input = TestPerftResults.class.getResourceAsStream(path)) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			List<String> header = separeteValues(reader.readLine());
			int depthFieldIndex = getDepthFieldIndex(header);
			String line = null;
			while ((line = reader.readLine()) != null) {
				List<String> values = separeteValues(line);
				for (int i = 0; i < header.size(); i++) {
					PerftResultField field = PerftResultField.valueOf(header.get(i));
					Long depth = Long.parseLong(values.get(depthFieldIndex));
					Long fieldValue = Long.parseLong(values.get(i));
					builder.addValue(
						field,
						depth,
						fieldValue
					);
				}
			}
		}
		return builder.build();
	}
	
	@SuppressWarnings("javadoc")
	private static int getDepthFieldIndex(List<String> header) {
		for (int i = 0; i < header.size(); i++) {
			String fieldName = header.get(i);
			if (PerftResultField.valueOf(fieldName) == PerftResultField.DEPTH) {
				return i;
			}
		}
		
		throw new IllegalArgumentException(
			String.format(
				"The header list %s does not apper to have the %s field",
				header,
				PerftResultField.DEPTH
			)
		);
	}
	
	@SuppressWarnings("javadoc")
	private static List<String> separeteValues(String line) {
		StringTokenizer tokenizer = new StringTokenizer(line, VALUE_SEPARATOR);
		List<String> values = new ArrayList<>();
		while (tokenizer.hasMoreElements()) {
			values.add(tokenizer.nextToken());
		}
		return values;
	}
}
