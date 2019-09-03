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
package com.welyab.anjabachen.util.textprinter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.LineReader;

/**
 * This class is used to format text based tabular information. Some features including vertical and
 * horizontal alignment and multi line cell content.
 * 
 * <pre>
 *      +----------+----------+---------------------+
 *      | Column name group 1 | Column name group 2 |
 *      +----------+----------+---------------------+
 *      | Column 1 | Column 2 |            Column 3 |
 * +----+----------+----------+---------------------+
 * | #1 |  Value 1 |  Value 2 |    Value 3 center   |
 * +----+----------+----------+---------------------+
 * | #2 |  Value 4 |  Value 5 |             Value 6 |
 * |    |          |          |          Multi line |
 * +----+----------+----------+---------------------+
 * | #3 |  Value 7 |          |             Value 8 |
 * | #3 |          | Center 8 |                     |
 * | #3 |          |          |                     |
 * +----+----------+----------+---------------------+
 * </pre>
 * 
 * @author Welyab Paula
 */
public class TextPrinter {
	
	/** The total rows in the final table. */
	private final int rowCount;
	
	/** The total columns in the final table. */
	private final int columnCount;
	
	/** Where the information to be printed come from. */
	private TextPrinterDataModel model;
	
	/** The style to be used during text printing. */
	private TextPrinterStyle style;
	
	/**
	 * Creates a new text printer with the specific dimension.
	 * 
	 * @param rowCount The number of rows.
	 * @param columnCount The number of columns.
	 */
	public TextPrinter(int rowCount, int columnCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
	}
	
	public void print(OutputStream outputStream) {
		PrintWriter writer = new PrintWriter(outputStream);
		List<List<CellValue>> cellValues = getCellValues();
	}
	
	private List<List<CellValue>> getCellValues() {
		List<List<CellValue>> cellValues = new ArrayList<>();
		for (int row = 0; row < getRowCount(); row++) {
			cellValues.add(new ArrayList<>());
			for (int column = 0; column < getColumnCount(); column++) {
				CellValue cellValue = toCellValue(model.getValue(row, column));
				cellValues.get(row).add(cellValue);
			}
		}
		return cellValues;
	}
	
	@SuppressWarnings("javadoc")
	private CellValue toCellValue(String text) {
		StringReader stringReader = new StringReader(text);
		LineReader lineReader = new LineReader(stringReader);
		CellValue cellValue = new CellValue();
		try {
			String line = null;
			while ((line = lineReader.readLine()) != null) {
				cellValue.addLine(line);
			}
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		return cellValue;
	}
	
	/**
	 * Retrieves the row size of this text printer.
	 * 
	 * @return The row count.
	 */
	public int getRowCount() {
		return rowCount;
	}
	
	/**
	 * Retrieves the column size of this text printer.
	 * 
	 * @return The column count.
	 */
	public int getColumnCount() {
		return columnCount;
	}
	
	/**
	 * Adjusts the data model to be used as source for column names and cell values.
	 * 
	 * @param model The model.
	 */
	public void setModel(TextPrinterDataModel model) {
		this.model = model;
	}
	
	/**
	 * Retrieves the data model used by this text printer as source for data being printed.
	 * 
	 * @return The model
	 */
	public TextPrinterDataModel getModel() {
		return model == null
				? TextPrinterDataModelEmpty.INSTANCE
				: model;
	}
	
	/**
	 * Retrieves the column size of this text printer.
	 * 
	 * @param style The style.
	 */
	public void setStyle(TextPrinterStyle style) {
		this.style = style;
	}
	
	/**
	 * Retrieves style used in the text being printed.
	 * 
	 * @return The style.
	 */
	public TextPrinterStyle getStyle() {
		return style == null
				? TextPrinterStyleImpl.INSTANCE
				: style;
	}
	
	/**
	 * Default implementation that gives all values as empty string.
	 * 
	 * @author Welyab Paula
	 */
	private static class TextPrinterDataModelEmpty implements TextPrinterDataModel {
		
		@SuppressWarnings("javadoc")
		static final TextPrinterDataModelEmpty INSTANCE = new TextPrinterDataModelEmpty();
		
		@Override
		public String getColumnName(int column) {
			return String.format("Column %d", column);
		}
		
		@Override
		public String getValue(int row, int column) {
			return "";
		}
	}
	
	/**
	 * Default style for text printing. The text will be placed in left/top of each cell.
	 * 
	 * @author Welyab Paula
	 */
	private static class TextPrinterStyleImpl implements TextPrinterStyle {
		
		@SuppressWarnings("javadoc")
		static final TextPrinterStyleImpl INSTANCE = new TextPrinterStyleImpl();
		
		@Override
		public VertialAlign getVerticalAlign(int row, int column) {
			return VertialAlign.TOP;
		}
		
		@Override
		public HorizontalAlign getHorizontalAlign(int row, int column) {
			return HorizontalAlign.LEFT;
		}
	}
	
	private static class CellValue {
		
		final List<String> lines;
		
		CellValue() {
			lines = new ArrayList<>();
		}
		
		void addLine(String line) {
			lines.add(line);
		}
		
		int getRowsCount() {
			return lines.size();
		}
		
		int getMaxRowLength() {
			return lines.stream().mapToInt(String::length).max().orElse(0);
		}
	}
}
