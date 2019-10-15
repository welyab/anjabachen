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
package com.welyab.anjabachen.movement.fen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.welyab.anjabachen.movement.BoardUtil;
import com.welyab.anjabachen.movement.LocalizedPiece;
import com.welyab.anjabachen.movement.Position;

/**
 * This class process
 * <a href="https://www.chessprogramming.org/Forsyth-Edwards_Notation" alt="article in the chess
 * programming wiki that describes the FEN position format">FEN</a> strings.
 * 
 * @author Welyab Paula
 */
public class FenParser {
	
	@SuppressWarnings("javadoc")
	private static final String CANT_PARSE_CASTLING_FLAGS_MSG = "Can't parse castling flags";
	
	/** The FEN strings. */
	private final String fen;
	
	/** If the FEN already been parsed. */
	private boolean parsed;
	
	/** Each piece and its position in the board, extract from FEN string. */
	private List<LocalizedPiece> localizedPieces;
	
	/** The information extract from the FEN string. */
	private PositionInfo info;
	
	/** The side to move code. */
	private byte sideToMove;
	
	/** If the castling is available for the white pieces in the king side. */
	private boolean whiteKingCastling;
	
	/** If the castling is available for the white pieces in the queen side. */
	private boolean whiteQueenCastling;
	
	/** If the castling is available for the black pieces in the king side. */
	private boolean blackKingCastling;
	
	/** If the castling is available for the black pieces in the queen side. */
	private boolean blackQueenCastling;
	
	/** If the <i>en passant</i> is available, this this keeps the target square. */
	private Position enPassantTarget;
	
	/** The half move clock. */
	private byte halfMoveClock;
	
	/** The full move counter. */
	private short fullMoveCounter;
	
	/**
	 * Creates a parser for the given FEN string.
	 * 
	 * @param fen The FEN parser.
	 */
	public FenParser(String fen) {
		this.fen = fen;
		parsed = false;
	}
	
	/**
	 * Retrieves the list of pieces and its locations.
	 * 
	 * @return The list of pieces and its locations.
	 */
	public List<LocalizedPiece> getLocalizedPieces() {
		parseIfNecessary();
		return Collections.unmodifiableList(localizedPieces);
	}
	
	/**
	 * Retrieves the position info.
	 * 
	 * @return The position info.
	 */
	public PositionInfo getPositionInfo() {
		parseIfNecessary();
		if (info == null) {
			info = new PositionInfoImpl();
		}
		return info;
	}
	
	/**
	 * Check if the FEN string already been parsed and, if not, parses the string.
	 */
	private void parseIfNecessary() {
		if (parsed) {
			return;
		}
		parse1();
		parsed = true;
	}
	
	/**
	 * Parses the FEN string. The passing occurs automatically when the method
	 * {@linkplain #getLocalizedPieces() getLocalizedPieces} or {@linkplain #getPositionInfo()
	 * getPositionInfo} is called.
	 */
	public void parse() {
		parseIfNecessary();
	}
	
	/**
	 * Parses the FEN string.
	 */
	private void parse1() {
		String[] parts = fen.split(" ");
		processPieceDisposition(extractPart(0, parts));
		processSideToMove(extractPart(1, parts));
		processCastlingFlags(extractPart(2, parts));
		processEnPassantTarget(extractPart(3, parts));
		processHalfMoveClock(extractPart(4, parts));
		processFullMoveCounter(extractPart(5, parts));
	}
	
	@SuppressWarnings("javadoc")
	private static String extractPart(int index, String[] parts) {
		if (index >= parts.length) {
			return null;
		}
		return parts[index];
	}
	
	/**
	 * Process a part of FEN string that describes the pieces disposition.
	 * 
	 * @param pieceDisposition The pieces dispositions.
	 */
	private void processPieceDisposition(String pieceDisposition) {
		if (pieceDisposition == null || pieceDisposition.isEmpty()) {
			throw new FenParserException(fen, "Inavlid pieces disposition");
		}
		
		String[] parts = pieceDisposition.split("/");
		if (parts.length != BoardUtil.SIZE) {
			throw new FenParserException(fen, "Invalid pieces disposition");
		}
		
		List<LocalizedPiece> localizedPiecesTemp = new ArrayList<>();
		for (byte row = 0; row < parts.length; row++) {
			String disposition = parts[row];
			processDispositionRow(row, disposition, localizedPiecesTemp);
		}
		
		localizedPieces = new ArrayList<>(localizedPiecesTemp);
	}
	
	/**
	 * Process a row of pieces.
	 * 
	 * @param row The current row being processed.
	 * @param disposition The disposition of pieces in the given row.
	 * @param localizedPieces The list where the being created localized pieces will be stored.
	 */
	private void processDispositionRow(
			byte row,
			String disposition,
			List<LocalizedPiece> localizedPieces
	) {
		List<Byte> squareValues = new ArrayList<>();
		byte column = 0;
		for (int j = 0; j < disposition.length(); j++) {
			char c = disposition.charAt(j);
			if (Character.isDigit(c)) {
				int spaces = c - '0';
				for (int k = 0; k < spaces; k++) {
					squareValues.add(BoardUtil.NO_PIECE_CODE);
					column++;
				}
			} else {
				try {
					squareValues.add(BoardUtil.pieceLetterToCode(c));
				} catch (IllegalArgumentException e) {
					throw new FenParserException(fen, "Can't parse piece disposition", e);
				}
				column++;
			}
		}
		if (column != BoardUtil.SIZE) {
			throw new FenParserException(
				fen,
				String.format("Inavlid pieces disposition to row %d", row)
			);
		}
		for (int col = 0; col < squareValues.size(); col++) {
			localizedPieces.add(
				new LocalizedPiece(
					Position.of(row, col),
					squareValues.get(col)
				)
			);
		}
	}
	
	/**
	 * Process the part of the <code>FEN</code> string that specifies the side to move.
	 * 
	 * @param sideToMove The code.
	 */
	private void processSideToMove(String sideToMove) {
		if (sideToMove == null || sideToMove.length() != 1) {
			throw new FenParserException(fen, "Can't parse side to move");
		}
		try {
			this.sideToMove = BoardUtil.colorLetterToCode(sideToMove.charAt(0));
		} catch (IllegalArgumentException e) {
			throw new FenParserException(fen, "Can't parse side to move", e);
		}
	}
	
	/**
	 * Parses castling flags.
	 * 
	 * @param castlingFlags The castling flags.
	 */
	@SuppressWarnings({
			"squid:MethodCyclomaticComplexity"
	})
	private void processCastlingFlags(String castlingFlags) {
		if (castlingFlags == null || castlingFlags.isEmpty() || castlingFlags.length() > 4) {
			throw new FenParserException(fen, CANT_PARSE_CASTLING_FLAGS_MSG);
		}
		
		if (castlingFlags.contentEquals("-")) {
			return;
		}
		
		boolean whiteKingCastlingTemp = false;
		boolean whiteQueenCastlingTemp = false;
		boolean blackKingCastlingTemp = false;
		boolean blackQueenCastlingTemp = false;
		
		int index = 0;
		if (castlingFlags.charAt(index) == 'K') {
			whiteKingCastlingTemp = true;
			index++;
		}
		if (index < castlingFlags.length() && castlingFlags.charAt(index) == 'Q') {
			whiteQueenCastlingTemp = true;
			index++;
		}
		if (index < castlingFlags.length() && castlingFlags.charAt(index) == 'k') {
			blackKingCastlingTemp = true;
			index++;
		}
		if (index < castlingFlags.length() && castlingFlags.charAt(index) == 'q') {
			blackQueenCastlingTemp = true;
			index++;
		}
		
		if (castlingFlags.length() != index) {
			throw new FenParserException(fen, CANT_PARSE_CASTLING_FLAGS_MSG);
		}
		
		whiteKingCastling = whiteKingCastlingTemp;
		whiteQueenCastling = whiteQueenCastlingTemp;
		blackKingCastling = blackKingCastlingTemp;
		blackQueenCastling = blackQueenCastlingTemp;
	}
	
	/**
	 * Process the <i>en passant</i> target square part of the <code>FEN</code> string.
	 * 
	 * @param enPassantTarget The <i>en passant</i> target square part.
	 */
	private void processEnPassantTarget(String enPassantTarget) {
		if (enPassantTarget == null) {
			throw new FenParserException(fen, CANT_PARSE_CASTLING_FLAGS_MSG);
		}
		if (enPassantTarget.contentEquals("-")) {
			return;
		}
		if (enPassantTarget.length() != 2) {
			throw new FenParserException(fen, CANT_PARSE_CASTLING_FLAGS_MSG);
		}
		this.enPassantTarget = Position.of(
			enPassantTarget.charAt(0),
			(byte) (enPassantTarget.charAt(1) - '0')
		);
	}
	
	/**
	 * Process the half move clock.
	 * 
	 * @param halfMoveClock The half move clock part of the <code>FEN</code> string.
	 */
	private void processHalfMoveClock(String halfMoveClock) {
		if (halfMoveClock == null || halfMoveClock.contentEquals("-")) {
			return;
		}
		
		try {
			this.halfMoveClock = Byte.parseByte(halfMoveClock);
		} catch (NumberFormatException e) {
			throw new FenParserException(fen, "Can't parse half move clock");
		}
	}
	
	/**
	 * Process the full move counter.
	 * 
	 * @param fullMoveCounter The full move counter part of the <code>FEN</code> string.
	 */
	private void processFullMoveCounter(String fullMoveCounter) {
		if (fullMoveCounter == null || fullMoveCounter.contentEquals("-")) {
			return;
		}
		
		try {
			this.fullMoveCounter = Short.parseShort(fullMoveCounter);
		} catch (NumberFormatException e) {
			throw new FenParserException(fen, "Can't parse full move counter");
		}
	}
	
	@SuppressWarnings({
			"javadoc",
			"squid:S2972"
	})
	private class PositionInfoImpl implements PositionInfo {
		
		@Override
		public byte getSideToMove() {
			return sideToMove;
		}
		
		@Override
		public boolean isWhiteKingCastlingAvaiable() {
			return whiteKingCastling;
		}
		
		@Override
		public boolean isWhiteQueenCastlingAvaiable() {
			return whiteQueenCastling;
		}
		
		@Override
		public boolean isBlackKingCastlingAvaiable() {
			return blackKingCastling;
		}
		
		@Override
		public boolean isBlackQueenCastlingAvaiable() {
			return blackQueenCastling;
		}
		
		@Override
		public Position getEnPassantTargetSquare() {
			return enPassantTarget;
		}
		
		@Override
		public byte getHalfMoveClock() {
			return halfMoveClock;
		}
		
		@Override
		public short getFullMoveCounter() {
			return fullMoveCounter;
		}
	}
	
	/**
	 * Creates a FEN parser.
	 * 
	 * @param string The FEN or EPD string.
	 * 
	 * @return The parser.
	 */
	public static FenParser of(String string) {
		return new FenParser(string);
	}
}
