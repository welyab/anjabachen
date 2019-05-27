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
package com.welyab.anjabachen;

/**
 * Enumerates chess color sides.
 *
 * @author Welyab Paula
 */
public enum Color {

	/**
	 * The white side.
	 */
	BLACK("Black") {

		@Override
		public Color getOpposite() {
			return Color.WHITE;
		}
	},

	/**
	 * The black side.
	 */
	WHITE("White") {

		@Override
		public Color getOpposite() {
			return Color.BLACK;
		}
	};

	/**
	 * The name of this color.
	 */
	private final String name;

	@SuppressWarnings("javadoc")
	private Color(String name) {
		this.name = name;
	}

	/**
	 * Retrieves the name of this color.
	 *
	 * @return The name of this color.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Evaluates whether this object represents the black color.
	 *
	 * @return A value <code>true</code> if this object represents the black color, or
	 *         <code>false</code> otherwise.
	 */
	public boolean isBlack() {
		return this == BLACK;
	}

	/**
	 * Evaluates whether this object represents the white color.
	 *
	 * @return A value <code>true</code> if this object represents the white color, or
	 *         <code>false</code> otherwise.
	 */
	public boolean isWhite() {
		return this == WHITE;
	}

	/**
	 * Retrieves the opposite color a color.
	 *
	 * @return The opposite color. If this color is black, then return white, if white, then return
	 *         black.
	 */
	public abstract Color getOpposite();
}
