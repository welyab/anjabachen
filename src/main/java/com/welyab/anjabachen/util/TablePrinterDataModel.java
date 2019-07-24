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

/**
 * This is a auxiliary class to serve as abstraction to the data source for the
 * <code>TablePrinter</code>.
 *
 * @author Welyab Paula
 */
public interface TablePrinterDataModel {

	/**
	 * Retrieve the total number of rows provided by this <code>TablePrinterDataModel</code>.
	 *
	 * @return The total number of rows.
	 */
	public int rowCount();

	/**
	 * Retrieves the total number of columns provider by this <code>TablePrinterDataModel</code>.
	 *
	 * @return The total number of columns.
	 */
	public int columnCount();

	/**
	 * Retrieves the text data localized in the specific <code>[row, column]</code> position. These
	 * location are zero based indexes.
	 *
	 * @param row The row number.
	 * @param column The column number.
	 *
	 * @return The text value.
	 */
	public String getValue(int row, int column);
}
