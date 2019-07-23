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
 */package com.welyab.anjabachen;

/**
 * Indicates the king piece is not present in the board during execution of routine that was
 * expecting a king to be present in the board.
 *
 * @author Welyab Paula
 */
public class KingNotPresentException extends ChessException {

	@SuppressWarnings("javadoc")
	private static final long serialVersionUID = 1L;

	/**
	 * The color side of the required king piece.
	 */
	private final Color color;

	/**
	 * Creates a new instance by informing the color side of the required king piece.
	 *
	 * @param color The color side of the required king piece.
	 */
	public KingNotPresentException(Color color) {
		super(String.format("The king of color %s is not present in the board", color.getName()));
		this.color = color;
	}

	/**
	 * Retrieves the color of the required king.
	 *
	 * @return The color of the required king.
	 */
	public Color getColor() {
		return color;
	}
}
