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
 * A list of representative values for piece when occupying a square.
 *
 * @author Welyab Paula
 */
public class SquareContent {

	/**
	 * The representative value for black king piece occupying a square.
	 */
	public static final int BLACK_KING = -6;

	/**
	 * The representative value for black queen piece occupying a square.
	 */
	public static final int BLACK_QUEEN = -5;

	/**
	 * The representative value for black rook piece occupying a square.
	 */
	public static final int BLACK_ROOK = -4;

	/**
	 * The representative value for black bishop piece occupying a square.
	 */
	public static final int BLACK_BISHOP = -3;

	/**
	 * The representative value for black knight piece occupying a square.
	 */
	public static final int BLACK_KNIGHT = -2;

	/**
	 * The representative value for black pawn piece occupying a square.
	 */
	public static final int BLACK_PAWN = -1;

	/**
	 * The representative value for an empty square.
	 */
	public static final int EMPTY = 0;

	/**
	 * The representative value for white king piece occupying a square.
	 */
	public static final int WHITE_KING = 6;

	/**
	 * The representative value for white queen piece occupying a square.
	 */
	public static final int WHITE_QUEEN = 5;

	/**
	 * The representative value for white rook piece occupying a square.
	 */
	public static final int WHITE_ROOK = 4;

	/**
	 * The representative value for white bishop piece occupying a square.
	 */
	public static final int WHITE_BISHOP = 3;

	/**
	 * The representative value for white knight piece occupying a square.
	 */
	public static final int WHITE_KNIGHT = 2;

	/**
	 * The representative value for white pawn piece occupying a square.
	 */
	public static final int WHITE_PAWN = 1;

	@SuppressWarnings("javadoc")
	private SquareContent() {
	}
}
