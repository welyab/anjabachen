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
import java.util.stream.Collectors;

/**
 * Contains some static methods for formating a text based grid information with a head row for
 * column names.
 *
 * <p>
 * Example:
 *
 * <pre>
 * " A | B | C "
 * "---+---+---"
 * " a | b | c "
 * " d | e | f "
 * ""
 * </pre>
 *
 * or
 *
 * <pre>
 * "A B C"
 * "a b c"
 * "d e f"
 * ""
 * </pre>
 *
 * or
 *
 * <pre>
 * "A   B   C"
 * "a   b   c"
 * "d   e   f"
 * ""
 * </pre>
 *
 * @author welyab
 *
 */
public class TablePrinter {

	@SuppressWarnings("javadoc")
	private static final char SPACE = ' ';

	@SuppressWarnings("javadoc")
	private TablePrinter() {
	}

	/**
	 * Formats a text based grid information.
	 *
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * " A | B | C "
	 * "---+---+---"
	 * " a | b | c "
	 * " d | e | f "
	 * ""
	 * </pre>
	 *
	 * or
	 *
	 * <pre>
	 * "A B C"
	 * "a b c"
	 * "d e f"
	 * ""
	 * </pre>
	 *
	 * or
	 *
	 * <pre>
	 * "A   B   C"
	 * "a   b   c"
	 * "d   e   f"
	 * ""
	 * </pre>
	 *
	 * The configuration parameter may configure the size of the space between columns, or define
	 * the output for text stream.
	 *
	 * @param columns The columns names.
	 * @param data The data abstraction.
	 * @param config The configuration.
	 */
	public static void print(
			TablePrinterColumnModel columns,
			TablePrinterDataModel data,
			TablePrinterConfig config
	) {
		print(
			columns.stream().collect(Collectors.toList()),
			data,
			config
		);
	}

	/**
	 * Formats a text based grid information.
	 *
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * " A | B | C "
	 * "---+---+---"
	 * " a | b | c "
	 * " d | e | f "
	 * ""
	 * </pre>
	 *
	 * or
	 *
	 * <pre>
	 * "A B C"
	 * "a b c"
	 * "d e f"
	 * ""
	 * </pre>
	 *
	 * or
	 *
	 * <pre>
	 * "A   B   C"
	 * "a   b   c"
	 * "d   e   f"
	 * ""
	 * </pre>
	 *
	 * The configuration parameter may configure the size of the space between columns, or define
	 * the output for text stream.
	 *
	 * @param columns The columns names.
	 * @param data The data abstraction.
	 * @param config The configuration.
	 */
	public static void print(
			List<String> columns,
			TablePrinterDataModel data,
			TablePrinterConfig config
	) {
		var values = new ArrayList<List<String>>();
		for (var i = 0; i < data.rowCount(); i++) {
			var row = new ArrayList<String>();
			values.add(row);
			for (var j = 0; j < data.columnCount(); j++) {
				row.add(data.getValue(i, j));
			}
		}
		print(columns, values, config);
	}

	/**
	 * Formats a text based grid information.
	 *
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * " A | B | C "
	 * "---+---+---"
	 * " a | b | c "
	 * " d | e | f "
	 * ""
	 * </pre>
	 *
	 * or
	 *
	 * <pre>
	 * "A B C"
	 * "a b c"
	 * "d e f"
	 * ""
	 * </pre>
	 *
	 * or
	 *
	 * <pre>
	 * "A   B   C"
	 * "a   b   c"
	 * "d   e   f"
	 * ""
	 * </pre>
	 *
	 * The configuration parameter may configure the size of the space between columns, or define
	 * the output for text stream.
	 *
	 * <p>
	 * This method uses internally the default configuration
	 * ({@link TablePrinterConfig#defaultConfig()}).
	 *
	 * @param columns The columns names.
	 * @param values The data abstraction. Each entry of the outter list represents a row of data.
	 * @param config The configuration.
	 */
	public static void print(
			List<String> columns,
			List<List<String>> values,
			TablePrinterConfig config
	) {
		var writer = Optional.ofNullable(config.getPrintWriter()).orElse(new PrintWriter(System.out));
		var columnsLength = computeColumnsLength(columns, values);
		printRow(columns, columnsLength, config, writer);
		if (config.isUseColumnSeparator()) {
			printHeaderSeparator(columnsLength, writer);
		}
		values.forEach(row -> printRow(row, columnsLength, config, writer));
		writer.flush();
	}

	/**
	 * Prints the header separator.
	 *
	 * <pre>
	 *  A | B | C
	 * ---+---+--- <-- the separator
	 *  e | f | g
	 *  h | i | j
	 * </pre>
	 *
	 * @param columnsLength The length of the colunms.
	 * @param writer The destination of the string stream.
	 */
	private static void printHeaderSeparator(List<Integer> columnsLength, PrintWriter writer) {
		for (var i = 0; i < columnsLength.size(); i++) {
			if (i > 0) {
				writer.print('+');
			}
			writer.print('-');
			for (var j = 0; j < columnsLength.get(i); j++) {
				writer.print('-');
			}
			writer.print('-');
		}
		writer.println();
	}

	/**
	 * Prints the values of a specific row (this method is also used for print the header).
	 *
	 * @param values The values of a specific row.
	 * @param columnsLength The list of required length for each row.
	 * @param config The print configuration.
	 * @param writer The destination of the string stream.
	 */
	private static void printRow(
			List<String> values,
			List<Integer> columnsLength,
			TablePrinterConfig config,
			PrintWriter writer
	) {
		for (var i = 0; i < values.size(); i++) {
			if (i > 0 && !config.isUseColumnSeparator()) {
				writer.print(normalizeLength("", config.getColumnSpace()));
			} else if (config.isUseColumnSeparator()) {
				if (i > 0) {
					writer.print('|');
				}
				writer.print(' ');
			}

			var value = normalizeLength(values.get(i), columnsLength.get(i));
			writer.print(value);

			if (config.isUseColumnSeparator()) {
				writer.print(' ');
			}
		}
		writer.println();
	}

	/**
	 * Inserts spaces in the beginning of string in order to reach the required length, if
	 * necessary.
	 *
	 * @param value The string value to be normalized.
	 * @param requiredLength The required length.
	 *
	 * @return The normalized string.
	 */
	private static String normalizeLength(String value, int requiredLength) {
		var builder = new StringBuilder(max(value.length(), requiredLength));
		int neededChars = abs(value.length() - requiredLength);
		for (var i = 0; i < neededChars; i++) {
			builder.append(SPACE);
		}
		return builder.append(value).toString();
	}

	/**
	 * Computes a list where each position contains a integer representing the maximum length for
	 * each column, including the column names.
	 *
	 * @param columns The column names.
	 *
	 * @param values The column values.
	 *
	 * @return A list of column lengths.
	 */
	private static List<Integer> computeColumnsLength(
			List<String> columns,
			List<List<String>> values
	) {
		var lengths = new ArrayList<Integer>();
		for (var i = 0; i < columns.size(); i++) {
			var max = columns.get(i).length();
			for (var j = 0; j < values.size(); j++) {
				max = max(max, values.get(j).get(i).length());
			}
			lengths.add(max);
		}
		return lengths;
	}
}
