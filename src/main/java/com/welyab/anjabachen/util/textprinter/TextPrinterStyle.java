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
 * This class describes the simple text formating for each cell during text printing.
 * 
 * @author Welyab Paula
 */
public interface TextPrinterStyle {
	
	/**
	 * The text printer calls this method in order to know the vertical alignment of the text.
	 * 
	 * @param row The cell row number.
	 * @param column The cell column number.
	 * 
	 * @return The vertical alignment configuration.
	 */
	VertialAlign getVerticalAlign(int row, int column);
	
	/**
	 * The text printer calls this method in order to know the horizontal alignment of the text.
	 * 
	 * @param row The cell row number.
	 * @param column The cell column number.
	 * 
	 * @return The horizontal alignment configuration.
	 */
	HorizontalAlign getHorizontalAlign(int row, int column);
}
