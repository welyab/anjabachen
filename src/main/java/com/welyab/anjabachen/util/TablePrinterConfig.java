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

import java.io.PrintWriter;

/**
 * Configuration for the <code>TablePrinter</code> print methods.
 *
 * @author Welyab Paula
 */
public class TablePrinterConfig {

	@SuppressWarnings("javadoc")
	private static final TablePrinterConfig DEFAULT_CONFIG = builder().build();

	/**
	 * The destination for the text stream. May be <code>null</code>; the printer will outputs the
	 * text to the <code>System.out</code>.
	 */
	private PrintWriter writer;

	/**
	 * The column space size to be used to separate columns; default value is <code>1</code>. If the
	 * {@link #isUseColumnSeparator()} is set to <code>true</code>, this field is ignored.
	 */
	private int columnSpace = 1;

	/**
	 * A flag to indicate if the generated grid should use a columns separator.
	 *
	 * <p>
	 * A example using column separator:
	 *
	 * <pre>
	 * " A | B | C "
	 * "---+---+---"
	 * " a | b | c "
	 * " d | e | f "
	 * ""
	 * </pre>
	 *
	 * ... and without column separator:
	 *
	 * <pre>
	 * "A B C"
	 * "a b c"
	 * "d e f"
	 * ""
	 * </pre>
	 */
	private boolean useColumnSeparator;

	@SuppressWarnings("javadoc")
	private TablePrinterConfig() {
	}

	/**
	 * Retrieves the default configuration object.
	 *
	 * <p>
	 * That configuration does not define a output destination.
	 *
	 * @return The default configuration.
	 */
	public static TablePrinterConfig defaultConfig() {
		return DEFAULT_CONFIG;
	}

	/**
	 * Retrieves the builder for construction of the configuration.
	 *
	 * @return The builder.
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Retrieves the configured output destination for the text stream.
	 *
	 * @return The output destination. May be <code>null</code>; in that case, the
	 *         <code>TablePrinter</code> class is prepared to outputs the text to the
	 *         <code>System.out</code>.
	 */
	public PrintWriter getPrintWriter() {
		return writer;
	}

	/**
	 * The column space size to be used to separate columns; default value is <code>1</code>. If the
	 * {@link #isUseColumnSeparator()} is set to <code>true</code>, this field is ignored.
	 *
	 * <p>
	 * A example using column space of <code>1</code> (default):
	 *
	 * <pre>
	 * "A B C"
	 * "a b c"
	 * "d e f"
	 * ""
	 * </pre>
	 *
	 * ... and a example using column space of <code>3</code>:
	 *
	 * <pre>
	 * "A   B   C"
	 * "a   b   c"
	 * "d   e   f"
	 * ""
	 * </pre>
	 *
	 * If the flag {@linkplain #isUseColumnSeparator() isUseColumnSeparator} is set to
	 * <code>true</code>, the columns space configuration will be ignored by the printer.
	 *
	 * @return The column space size.
	 */
	public int getColumnSpace() {
		return columnSpace;
	}

	/**
	 * A flag to indicate if the generated grid should use a columns separator.
	 *
	 * <p>
	 * A example using column separator:
	 *
	 * <pre>
	 * " A | B | C "
	 * "---+---+---"
	 * " a | b | c "
	 * " d | e | f "
	 * ""
	 * </pre>
	 *
	 * ... and without column separator:
	 *
	 * <pre>
	 * "A B C"
	 * "a b c"
	 * "d e f"
	 * ""
	 * </pre>
	 *
	 * If this flag is set to <code>true</code>, {@linkplain #getColumnSpace() columnSpace}
	 * configuration will be ignored by the printer.
	 *
	 * @return A value <code>true</code> if the print must generate a output using a columns
	 *         separator, or <code>false</code> otherwise.
	 */
	public boolean isUseColumnSeparator() {
		return useColumnSeparator;
	}

	/**
	 * A builder auxiliary class for creating instances of <code>TablePrintConfig</code>.
	 *
	 * @author Welyab Paula
	 */
	public static final class Builder {

		/**
		 * The being created configuration.
		 */
		private TablePrinterConfig config;

		@SuppressWarnings("javadoc")
		private Builder() {
			config = new TablePrinterConfig();
		}

		/**
		 * Adjusts the destination for the text stream. May be <code>null</code>; the printer will
		 * outputs the text to the <code>System.out</code>.
		 *
		 * @param writer The output text destination; may be <code>null</code>.
		 *
		 * @return This builder instance for further usage.
		 */
		public Builder writer(PrintWriter writer) {
			config.writer = writer;
			return this;
		}

		/**
		 * Adjusts the column space size to be used to separate columns; default value is
		 * <code>1</code>. If the {@link #isUseColumnSeparator()} is set to <code>true</code>, this
		 * field is ignored.
		 *
		 * <p>
		 * A example using column space of <code>1</code> (default):
		 *
		 * <pre>
		 * "A B C"
		 * "a b c"
		 * "d e f"
		 * ""
		 * </pre>
		 *
		 * ... and a example using column space of <code>3</code>:
		 *
		 * <pre>
		 * "A   B   C"
		 * "a   b   c"
		 * "d   e   f"
		 * ""
		 * </pre>
		 *
		 * If the flag {@linkplain #isUseColumnSeparator() isUseColumnSeparator} is set to
		 * <code>true</code>, the columns space configuration will be ignored by the printer.
		 *
		 * @param columnSpace The length of the space between columns.
		 *
		 * @return This builder instance for further usage.
		 */
		public Builder columnSpace(int columnSpace) {
			config.columnSpace = columnSpace;
			return this;
		}

		/**
		 * Adjusts a flag to indicate if the generated grid should use a columns separator.
		 *
		 * <p>
		 * A example using column separator:
		 *
		 * <pre>
		 * " A | B | C "
		 * "---+---+---"
		 * " a | b | c "
		 * " d | e | f "
		 * ""
		 * </pre>
		 *
		 * ... and without column separator:
		 *
		 * <pre>
		 * "A B C"
		 * "a b c"
		 * "d e f"
		 * ""
		 * </pre>
		 *
		 * If this flag is set to <code>true</code>, the {@linkplain #columnSpace(int) columnSpace}
		 * configuration will be ignored by the printer.
		 *
		 * @param separeteColumns The flag.
		 *
		 * @return This builder instance for further usage.
		 */
		public Builder separeteColumns(boolean separeteColumns) {
			config.useColumnSeparator = separeteColumns;
			return this;
		}

		/**
		 * Retrieves the configuration for <code>TablePrinter</code>.
		 *
		 * @return The brand new configuration for <code>TablePrinter</code>.
		 */
		public TablePrinterConfig build() {
			return config;
		}
	}
}
