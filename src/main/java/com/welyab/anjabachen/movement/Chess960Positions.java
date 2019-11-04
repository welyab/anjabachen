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
package com.welyab.anjabachen.movement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Chess960Positions {
	
	public static void main(String[] args) {
		List<byte[]> positions = new ArrayList<>();
		createPositions(positions);
		System.out.println(positions.size());
		positions.stream()
			.map(p -> toString(p))
			.sorted()
			.forEach(System.out::println);
	}
	
	private static String toString(byte[] pieceTypes) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < pieceTypes.length; i++) {
			builder.append(MovementUtil.pieceCodeToLetter(pieceTypes[i]));
		}
		return builder.toString();
	}
	
	private static void createPositions(List<byte[]> positions) {
		byte[] pieces = new byte[8];
		for (int i = 1; i <= 6; i++) {
			pieces[i] = MovementUtil.KING;
			placeRooks(pieces, i, positions);
			pieces[i] = MovementUtil.EMPTY;
		}
	}
	
	private static void placeRooks(byte[] pieces, int kingIndex, List<byte[]> positions) {
		for (int i = 0; i < kingIndex; i++) {
			if (pieces[i] == MovementUtil.EMPTY) {
				pieces[i] = MovementUtil.ROOK;
				for (int j = kingIndex + 1; j < 8; j++) {
					if (pieces[j] == MovementUtil.EMPTY) {
						pieces[j] = MovementUtil.ROOK;
						placeBishops(pieces, positions);
						pieces[j] = MovementUtil.EMPTY;
					}
				}
				pieces[i] = MovementUtil.EMPTY;
			}
		}
	}
	
	private static void placeBishops(byte[] pieces, List<byte[]> positions) {
		for (int i = 0; i < 8; i += 2) {
			if (pieces[i] == MovementUtil.EMPTY) {
				pieces[i] = MovementUtil.BISHOP;
				for (int j = 1; j < 8; j += 2) {
					if (pieces[j] == MovementUtil.EMPTY) {
						pieces[j] = MovementUtil.BISHOP;
						placeKnights(pieces, positions);
						pieces[j] = MovementUtil.EMPTY;
					}
				}
				pieces[i] = MovementUtil.EMPTY;
			}
		}
	}
	
	private static void placeKnights(byte[] pieces, List<byte[]> positions) {
		for (int i = 0; i < 8; i++) {
			if (pieces[i] == MovementUtil.EMPTY) {
				pieces[i] = MovementUtil.KNIGHT;
				for (int j = i + 1; j < 8; j++) {
					if (pieces[j] == MovementUtil.EMPTY) {
						pieces[j] = MovementUtil.KNIGHT;
						placeQueen(pieces, positions);
						pieces[j] = MovementUtil.EMPTY;
					}
				}
				pieces[i] = MovementUtil.EMPTY;
			}
		}
	}
	
	private static void placeQueen(byte[] pieces, List<byte[]> positions) {
		for (int i = 0; i < 8; i++) {
			if (pieces[i] == MovementUtil.EMPTY) {
				pieces[i] = MovementUtil.QUEEN;
				byte[] copy = Arrays.copyOf(pieces, 8);
				positions.add(copy);
				pieces[i] = MovementUtil.EMPTY;
			}
		}
	}
}
