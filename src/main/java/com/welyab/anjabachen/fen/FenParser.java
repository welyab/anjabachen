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
package com.welyab.anjabachen.fen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.welyab.anjabachen.Color;

public class FenParser {
	
	private static final String INVALID_FEN_PARSER = "Invalid FEN string";
	
	private static final char WHITE_KING_CASTLING_FLAG = 'K';
	
	private static final char WHITE_QUEEN_CASTLING_FLAG = 'Q';
	
	private static final char BLACK_KING_CASTLING_FLAG = 'k';
	
	private static final char BLACK_QUEEN_CASTLING_FLAG = 'q';
	
	private final String fen;
	
	private boolean parsed;
	
	private Color sideToMove;
	
	private int halfMoveClock;
	
	private int fullMoveCounter;
	
	private boolean whiteKingCastling;
	
	private boolean whiteQueenCastling;
	
	private boolean blackKingCastling;
	
	private boolean blackQueenCastling;
	
	public FenParser(String fen) {
		this.fen = fen;
	}
	
	public static void main(String[] args) {
		FenParser fenParser = new FenParser("r1bqkb1r/p1p2ppp/2n2n2/1pP5/4p3/2N5/PP1PBPpP/R1BQ1RK1 w kq b6 0 10");
		fenParser
			.splitParts()
			.forEach(s -> {
				CharSequenceImpl m = (CharSequenceImpl) s;
				System.out.printf("%02d %02d - '%s'%n", m.startIndex, m.endIndex, m);
			});
	}
	
	public void parse() {
		if (parsed) {
			return;
		}
		
		List<CharSequence> parts = splitParts();
		
		for (int i = 1; i < parts.size(); i += 2) {
			if (parts.size() > 1 || parts.get(i).charAt(i) != ' ') {
				throw new FenParserException(fen, INVALID_FEN_PARSER);
			}
		}
		if (parts.get(parts.size() - 1).charAt(0) == ' ') {
			throw new FenParserException(fen, INVALID_FEN_PARSER);
		}
		
		CharSequence pieceDisposition = extractPart(parts, 0);
		CharSequence sideToMove = extractPart(parts, 2);
		CharSequence castlingAvailability = extractPart(parts, 4);
		CharSequence enPassantTarget = extractPart(parts, 6);
		CharSequence halfMoveCounter = extractPart(parts, 8);
		CharSequence fullMoveCounter = extractPart(parts, 10);
		
		parsePieceDisposition(pieceDisposition);
		parseSideToMove(sideToMove);
		parseCastlingAvailability(castlingAvailability);
		parseEnPassantTarget(enPassantTarget);
		parseHalfMoveClock(halfMoveCounter);
		parseFullMoveCounter(fullMoveCounter);
		
		parsed = true;
	}
	
	private void parsePieceDisposition(CharSequence pieceDisposition) {
	}
	
	private void parseSideToMove(CharSequence sideToMove) {
		if (sideToMove == null || sideToMove.length() != 1
				|| (sideToMove.charAt(0) != 'w' && sideToMove.charAt(0) != 'b')) {
			throw new FenParserException(fen, String.format("Invalid side to move: %s", sideToMove));
		}
		this.sideToMove = Color.valueOf(fen.charAt(0));
	}
	
	private void parseCastlingAvailability(CharSequence castlingAvailability) {
		if (castlingAvailability == null) {
			return;
		}
		
		if (castlingAvailability.length() == 1 && castlingAvailability.charAt(0) == '-') {
			return;
		}
		
		int index = 0;
		
		if (castlingAvailability.charAt(index) == WHITE_KING_CASTLING_FLAG) {
			whiteKingCastling = true;
			index++;
		}
		
		if (castlingAvailability.charAt(index) == WHITE_QUEEN_CASTLING_FLAG) {
			whiteQueenCastling = true;
			index++;
		}
		
		if (castlingAvailability.charAt(index) == BLACK_KING_CASTLING_FLAG) {
			blackKingCastling = true;
			index++;
		}
		
		if (castlingAvailability.charAt(index) == BLACK_QUEEN_CASTLING_FLAG) {
			blackQueenCastling = true;
			index++;
		}
		
		if (index != castlingAvailability.length()) {
			throw new FenParserException(
				fen,
				String.format("Invalid castling flags: %s", castlingAvailability)
			);
		}
	}
	
	private void parseEnPassantTarget(CharSequence enPassantTarget) {
	}
	
	private void parseHalfMoveClock(CharSequence halfMoveClock) {
		try {
			this.halfMoveClock = Integer.parseInt(halfMoveClock.toString());
		} catch (NumberFormatException e) {
			throw new FenParserException(fen, String.format("Invalid half move clock: %s", halfMoveClock), e);
		}
	}
	
	private void parseFullMoveCounter(CharSequence fullMoveCounter) {
		try {
			this.fullMoveCounter = Integer.parseInt(fullMoveCounter.toString());
		} catch (NumberFormatException e) {
			throw new FenParserException(fen, String.format("Invalid full move counter: %s", halfMoveClock), e);
		}
	}
	
	private List<CharSequence> splitParts() {
		if (fen.isEmpty()) {
			return Collections.emptyList();
		}
		List<CharSequence> parts = new ArrayList<>(6);
		int startIndex = 0;
		int endIndex = 0;
		boolean flag = fen.charAt(0) == ' ';
		while (startIndex < fen.length() && endIndex < fen.length()) {
			boolean f = fen.charAt(endIndex) == ' ';
			if (flag != f) {
				parts.add(new CharSequenceImpl(startIndex, endIndex));
				startIndex = endIndex;
				flag = f;
			} else {
				endIndex++;
			}
		}
		parts.add(new CharSequenceImpl(startIndex, endIndex));
		return parts;
	}
	
	private CharSequence extractPart(List<CharSequence> parts, int partIndex) {
		if (partIndex < parts.size()) {
			return null;
		}
		return parts.get(partIndex);
	}
	
	public static FenParser parse(String fen) {
		return new FenParser(fen);
	}
	
	private final class CharSequenceImpl implements CharSequence {
		
		private final int startIndex;
		
		private final int endIndex;
		
		private CharSequenceImpl(int startIndex, int endIndex) {
			this.startIndex = startIndex;
			this.endIndex = endIndex;
			
		}
		
		@Override
		public int length() {
			return endIndex - startIndex;
		}
		
		@Override
		public char charAt(int index) {
			if (startIndex + index >= endIndex) {
				throw new IndexOutOfBoundsException(index);
			}
			return fen.charAt(startIndex + index);
		}
		
		@Override
		public CharSequence subSequence(int start, int end) {
			return fen.subSequence(startIndex + start, end);
		}
		
		@Override
		public String toString() {
			return fen.substring(startIndex, endIndex);
		}
	}
}
