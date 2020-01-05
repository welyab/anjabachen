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
import com.welyab.anjabachen.LocalizedPiece;
import com.welyab.anjabachen.Piece;
import com.welyab.anjabachen.Position;

public class FenParser {
	
	private static final char WHITE_KING_CASTLING_FLAG = 'K';
	
	private static final char WHITE_QUEEN_CASTLING_FLAG = 'Q';
	
	private static final char BLACK_KING_CASTLING_FLAG = 'k';
	
	private static final char BLACK_QUEEN_CASTLING_FLAG = 'q';
	
	private static final char SLASH = '/';
	
	private final String fen;
	
	private boolean parsed;
	
	private List<LocalizedPiece> pieceDisposition;
	
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
	
	public List<LocalizedPiece> getPiecesDisposition() {
		parse();
		return Collections.unmodifiableList(pieceDisposition);
	}
	
	public int getHalfMoveClock() {
		parse();
		return halfMoveClock;
	}
	
	public int getFullMoveCounter() {
		parse();
		return fullMoveCounter;
	}
	
	public Color getSideToMove() {
		parse();
		return sideToMove;
	}
	
	public boolean isParsed() {
		return parsed;
	}
	
	public boolean isWhiteKingCastling() {
		return whiteKingCastling;
	}
	
	public boolean isWhiteQueenCastling() {
		return whiteQueenCastling;
	}
	
	public boolean isBlackKingCastling() {
		return blackKingCastling;
	}
	
	public boolean isBlackQueenCastling() {
		return blackQueenCastling;
	}
	
	public void parse() {
		if (parsed) {
			return;
		}
		
		try {
			parse0();
			parsed = true;
		} catch (FenParserException e) {
			throw e;
		} catch (RuntimeException e) {
			throw new FenParserException(fen, "Fail to parse FEN string", e);
		}
	}
	
	private void parse0() {
		List<CharSequence> parts = splitParts();
		
		for (int i = 1; i < parts.size(); i += 2) {
			if (parts.get(i).length() > 1 || parts.get(i).charAt(0) != ' ') {
				throw new FenParserException(fen, "Invalid FEN string");
			}
		}
		if (parts.get(parts.size() - 1).charAt(0) == ' ') {
			throw new FenParserException(fen, "Invalid FEN string");
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
		this.pieceDisposition = new ArrayList<>();
		int currentRow = 0;
		int currentColumn = 0;
		for (int i = 0; i < pieceDisposition.length(); i++) {
			char c = fen.charAt(i);
			if (c == SLASH) {
				if (currentColumn != 8) {
					throw new FenParserException(fen, "Invalid piece disposition");
				}
				currentRow++;
				currentColumn = 0;
				continue;
			}
			
			if (c >= '1' && c <= '8') {
				currentColumn += c - '0';
				if (currentColumn > 8) {
					throw new FenParserException(fen, "Invalid piece disposition");
				}
				continue;
			}
			this.pieceDisposition.add(
				new LocalizedPiece(
					Piece.valueOf(c),
					Position.of(
						currentRow,
						currentColumn
					)
				)
			);
			currentColumn++;
		}
		if (currentRow != 7) {
			throw new FenParserException(fen, "Invalid piece disposition");
		}
	}
	
	private void parseSideToMove(CharSequence sideToMove) {
		if (sideToMove == null || sideToMove.length() != 1
				|| (sideToMove.charAt(0) != Color.WHITE_LETTER && sideToMove.charAt(0) != Color.BLACK_LETTER)) {
			throw new FenParserException(fen, String.format("Invalid side to move: %s", sideToMove));
		}
		this.sideToMove = Color.valueOf(sideToMove.charAt(0));
	}
	
	private void parseCastlingAvailability(CharSequence castlingAvailability) {
		if (castlingAvailability == null) {
			throw new FenParserException(fen, "Fail to parse castling flags");
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
		boolean flag = Character.isWhitespace(fen.charAt(0));
		while (endIndex < fen.length()) {
			boolean f = Character.isWhitespace(fen.charAt(endIndex));
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
		if (partIndex >= parts.size()) {
			return null;
		}
		return parts.get(partIndex);
	}
	
	public static FenParser createParser(String fen) {
		return new FenParser(fen);
	}
	
	private String encodeCastlingFlags() {
		StringBuilder builder = new StringBuilder();
		if (whiteKingCastling) {
			builder.append(WHITE_KING_CASTLING_FLAG);
		}
		if (whiteQueenCastling) {
			builder.append(WHITE_QUEEN_CASTLING_FLAG);
		}
		if (blackKingCastling) {
			builder.append(BLACK_KING_CASTLING_FLAG);
		}
		if (blackQueenCastling) {
			builder.append(BLACK_QUEEN_CASTLING_FLAG);
		}
		if (builder.length() == 0) {
			builder.append("-");
		}
		return builder.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("next = ");
		builder.append(sideToMove.getLetter()).append(", ");
		builder.append("castlings = ");
		builder.append(encodeCastlingFlags()).append(", ");
		builder.append("pieces = ");
		for (int i = 0; i < pieceDisposition.size(); i++) {
			if (i > 0) {
				builder.append(", ");
			}
			builder.append(pieceDisposition.get(i));
		}
		return builder.toString();
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
