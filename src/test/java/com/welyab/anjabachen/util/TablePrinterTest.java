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

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for <code>TablePrinter</code> class.
 *
 * @author Welyab Paula
 */
public class TablePrinterTest {
	
	@SuppressWarnings("javadoc")
	private static final String NEWLINE = String.format("%n");
	
	@Test
	@SuppressWarnings("javadoc")
	public void pritShouldFormatTableWithDefaultConfiguration() {
		String expectedTable = ""
				+ "A B C" + NEWLINE
				+ "a b c" + NEWLINE
				+ "d e f" + NEWLINE;
		var arrayOut = new ByteArrayOutputStream();
		TablePrinter.print(
			List.of("A", "B", "C"),
			List.of(
				List.of("a", "b", "c"),
				List.of("d", "e", "f")
			),
			TablePrinterConfig
				.builder()
				.writer(new PrintWriter(arrayOut))
				.build()
		);
		Assert.assertEquals(expectedTable, arrayOut.toString());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void pritShouldFormatTableWithConfiguredColumnSpaceConfiguration() {
		String expectedTable = ""
				+ "A   B   C" + NEWLINE
				+ "a   b   c" + NEWLINE
				+ "d   e   f" + NEWLINE;
		var arrayOut = new ByteArrayOutputStream();
		TablePrinter.print(
			List.of("A", "B", "C"),
			List.of(
				List.of("a", "b", "c"),
				List.of("d", "e", "f")
			),
			TablePrinterConfig
				.builder()
				.writer(new PrintWriter(arrayOut))
				.columnSpace(3)
				.build()
		);
		Assert.assertEquals(expectedTable, arrayOut.toString());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void pritShouldFormatTableWithColumnSeparatorConfiguration() {
		String expectedTable = ""
				+ " A | B | C " + NEWLINE
				+ "---+---+---" + NEWLINE
				+ " a | b | c " + NEWLINE
				+ " d | e | f " + NEWLINE;
		var arrayOut = new ByteArrayOutputStream();
		TablePrinter.print(
			List.of("A", "B", "C"),
			List.of(
				List.of("a", "b", "c"),
				List.of("d", "e", "f")
			),
			TablePrinterConfig
				.builder()
				.writer(new PrintWriter(arrayOut))
				.separeteColumns(true)
				.build()
		);
		Assert.assertEquals(expectedTable, arrayOut.toString());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void pritShouldFormatTableWithColumnWithDifferentLengtsUsingColumnSeparatorConfiguration() {
		String expectedTable = ""
				+ "   Col1 | Column2 | Column    3 " + NEWLINE
				+ "--------+---------+-------------" + NEWLINE
				+ "      a |   house |       green " + NEWLINE
				+ " yellow |     red |           f " + NEWLINE;
		var arrayOut = new ByteArrayOutputStream();
		TablePrinter.print(
			List.of("Col1", "Column2", "Column    3"),
			List.of(
				List.of("a", "house", "green"),
				List.of("yellow", "red", "f")
			),
			TablePrinterConfig
				.builder()
				.writer(new PrintWriter(arrayOut))
				.separeteColumns(true)
				.build()
		);
		Assert.assertEquals(expectedTable, arrayOut.toString());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void pritShouldFormatTableWithColumnWithDifferentLengtsUsingColumnSeparatorAndTableDataConfiguration() {
		String expectedTable = ""
				+ "   Col1 | Column2 | Column    3 " + NEWLINE
				+ "--------+---------+-------------" + NEWLINE
				+ "      a |   house |       green " + NEWLINE
				+ " yellow |     red |           f " + NEWLINE;
		var arrayOut = new ByteArrayOutputStream();
		TablePrinter.print(
			List.of("Col1", "Column2", "Column    3"),
			new TablePrinterDataModel() {
			
			// @formatter:off
				String data[][] = {
					{"a", "house", "green"},
					{"yellow", "red", "f"}
				};
			// @formatter:on
				
				@Override
				public int rowCount() {
					return 2;
				}
				
				@Override
				public int columnCount() {
					return 3;
				}
				
				@Override
				public String getValue(int row, int column) {
					return data[row][column];
				}
			},
			TablePrinterConfig
				.builder()
				.writer(new PrintWriter(arrayOut))
				.separeteColumns(true)
				.build()
		);
		Assert.assertEquals(expectedTable, arrayOut.toString());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void pritShouldFormatTableWithColumnWithDifferentLengtsUsingDefaultConfiguration() {
		String expectedTable = ""
				+ "  Col1 Column2 Column    3" + NEWLINE
				+ "     a   house       green" + NEWLINE
				+ "yellow     red           f" + NEWLINE;
		var arrayOut = new ByteArrayOutputStream();
		TablePrinter.print(
			List.of("Col1", "Column2", "Column    3"),
			List.of(
				List.of("a", "house", "green"),
				List.of("yellow", "red", "f")
			),
			TablePrinterConfig
				.builder()
				.writer(new PrintWriter(arrayOut))
				.build()
		);
		Assert.assertEquals(expectedTable, arrayOut.toString());
	}
}
