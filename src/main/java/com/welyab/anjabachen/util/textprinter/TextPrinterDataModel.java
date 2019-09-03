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

/**
 * The <code>TextPrinter</code> uses this class as source for the data to be printed.
 * 
 * @author Welyab Paula
 */
public interface TextPrinterDataModel {
	
	/**
	 * Retrieves the column name for the specific column number parameter.
	 * 
	 * @param column The column number.
	 * 
	 * @return The column name.
	 */
	String getColumnName(int column);
	
	/**
	 * Returns the text that must be printed in the specific cell identified by given
	 * <code>row/column</code>.
	 * 
	 * <p>
	 * During the print processing, the <code>TextPrinter</code> may call this method some times to
	 * the same cell.
	 * 
	 * @param row The row number.
	 * @param column The column number.
	 * 
	 * @return The value.
	 */
	String getValue(int row, int column);
}
