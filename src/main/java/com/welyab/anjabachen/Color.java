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

@SuppressWarnings("javadoc")
public enum Color {
	
	WHITE {
		
		@Override
		public boolean isWhite() {
			return true;
		}
		
		@Override
		public boolean isBlack() {
			return false;
		}
		
		@Override
		public Color getOpposite() {
			return BLACK;
		}
		
		@Override
		public int getValue() {
			return Color.WHITE_VALUE;
		}
		
		@Override
		public char getLetter() {
			return Color.WHITE_LETTER;
		}
	},
	
	BLACK {
		
		@Override
		public boolean isWhite() {
			return false;
		}
		
		@Override
		public boolean isBlack() {
			return true;
		}
		
		@Override
		public Color getOpposite() {
			return WHITE;
		}
		
		@Override
		public int getValue() {
			return Color.BLACK_VALUE;
		}
		
		@Override
		public char getLetter() {
			return Color.BLACK_LETTER;
		}
	};
	
	public static final int WHITE_VALUE = 1;
	
	public static final int BLACK_VALUE = -1;
	
	public static final char WHITE_LETTER = 'w';
	
	public static final char BLACK_LETTER = 'b';
	
	public abstract boolean isWhite();
	
	public abstract boolean isBlack();
	
	public abstract Color getOpposite();
	
	public abstract int getValue();
	
	public abstract char getLetter();
	
	public static Color valueOf(char letter) {
		return switch (letter) {
			case WHITE_LETTER -> Color.WHITE;
			case BLACK_LETTER -> Color.BLACK;
			default -> throw new IllegalArgumentException("Unexpected piece color: " + letter);
		};
	}
}
