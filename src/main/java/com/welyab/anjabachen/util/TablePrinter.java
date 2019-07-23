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
package com.welyab.anjabachen.util;

import static java.lang.Integer.max;
import static java.lang.Math.abs;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TablePrinter {

	private static final char SPACE = ' ';

	private static void print(
			List<String> columns,
			List<List<String>> values
	) {
		print(
			columns,
			values,
			TablePrinterConfig.defaultConfig()
		);
	}

	private static void print(
			List<String> columns,
			List<List<String>> values,
			TablePrinterConfig config
	) {
		var writer = Optional.ofNullable(config.getPrintWriter()).orElse(new PrintWriter(System.out));
		var columnsLength = computeColumnsLength(columns, values);
		printRow(columns, columnsLength, config, writer);
	}

	private static void printRow(
			List<String> values,
			List<Integer> columnsLength,
			TablePrinterConfig config,
			PrintWriter writer
	) {
		var builder = new StringBuilder();
		for (var i = 0; i < values.size(); i++) {
			var value = normalizeLength(values.get(i), columnsLength.get(i));
			builder.append(value);
		}
		writer.println(builder.toString());
	}

	private static String normalizeLength(String value, int requiredLength) {
		var builder = new StringBuilder(max(value.length(), requiredLength));
		int neededChars = abs(value.length() - requiredLength);
		for (var i = 0; i < neededChars; i++) {
			builder.append(SPACE);
		}
		return builder.append(value).toString();
	}

	private static List<Integer> computeColumnsLength(
			List<String> columns,
			List<List<String>> values
	) {
		var lengths = new ArrayList<Integer>();
		for (var i = 0; i < columns.size(); i++) {
			lengths.add(
				max(
					columns.get(i).length(),
					values.get(i).stream().mapToInt(String::length).max().getAsInt()
				)
			);
		}
		return lengths;
	}
}
