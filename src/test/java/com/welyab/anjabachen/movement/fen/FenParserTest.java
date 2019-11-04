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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.welyab.anjabachen.movement.LocalizedPiece;
import com.welyab.anjabachen.movement.MovementUtil;
import com.welyab.anjabachen.movement.Position;

/**
 * Unit tests for <code>FenParser</code> class.
 * 
 * @author Welyab Paula
 */
public class FenParserTest {
	
	@Test
	@SuppressWarnings("javadoc")
	public void piecePositionsAndTypeShouldHaveCorrectInformation() {
		FenParser parser = new FenParser("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		List<LocalizedPiece> lpieces = parser.getLocalizedPieces();
		
		assertEquals(MovementUtil.BLACK_ROOK, lpieces.get(0).getPieceCode());
		assertEquals(MovementUtil.BLACK_KNIGHT, lpieces.get(1).getPieceCode());
		assertEquals(MovementUtil.BLACK_BISHOP, lpieces.get(2).getPieceCode());
		assertEquals(MovementUtil.BLACK_QUEEN, lpieces.get(3).getPieceCode());
		assertEquals(MovementUtil.BLACK_KING, lpieces.get(4).getPieceCode());
		assertEquals(MovementUtil.BLACK_BISHOP, lpieces.get(5).getPieceCode());
		assertEquals(MovementUtil.BLACK_KNIGHT, lpieces.get(6).getPieceCode());
		assertEquals(MovementUtil.BLACK_ROOK, lpieces.get(7).getPieceCode());
		assertEquals(MovementUtil.BLACK_PAWN, lpieces.get(8).getPieceCode());
		assertEquals(MovementUtil.BLACK_PAWN, lpieces.get(9).getPieceCode());
		assertEquals(MovementUtil.BLACK_PAWN, lpieces.get(10).getPieceCode());
		assertEquals(MovementUtil.BLACK_PAWN, lpieces.get(11).getPieceCode());
		assertEquals(MovementUtil.BLACK_PAWN, lpieces.get(12).getPieceCode());
		assertEquals(MovementUtil.BLACK_PAWN, lpieces.get(13).getPieceCode());
		assertEquals(MovementUtil.BLACK_PAWN, lpieces.get(14).getPieceCode());
		assertEquals(MovementUtil.BLACK_PAWN, lpieces.get(15).getPieceCode());
		
		assertEquals(MovementUtil.WHITE_PAWN, lpieces.get(16).getPieceCode());
		assertEquals(MovementUtil.WHITE_PAWN, lpieces.get(17).getPieceCode());
		assertEquals(MovementUtil.WHITE_PAWN, lpieces.get(18).getPieceCode());
		assertEquals(MovementUtil.WHITE_PAWN, lpieces.get(19).getPieceCode());
		assertEquals(MovementUtil.WHITE_PAWN, lpieces.get(20).getPieceCode());
		assertEquals(MovementUtil.WHITE_PAWN, lpieces.get(21).getPieceCode());
		assertEquals(MovementUtil.WHITE_PAWN, lpieces.get(22).getPieceCode());
		assertEquals(MovementUtil.WHITE_PAWN, lpieces.get(23).getPieceCode());
		assertEquals(MovementUtil.WHITE_ROOK, lpieces.get(24).getPieceCode());
		assertEquals(MovementUtil.WHITE_KNIGHT, lpieces.get(25).getPieceCode());
		assertEquals(MovementUtil.WHITE_BISHOP, lpieces.get(26).getPieceCode());
		assertEquals(MovementUtil.WHITE_QUEEN, lpieces.get(27).getPieceCode());
		assertEquals(MovementUtil.WHITE_KING, lpieces.get(28).getPieceCode());
		assertEquals(MovementUtil.WHITE_BISHOP, lpieces.get(29).getPieceCode());
		assertEquals(MovementUtil.WHITE_KNIGHT, lpieces.get(30).getPieceCode());
		assertEquals(MovementUtil.WHITE_ROOK, lpieces.get(31).getPieceCode());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void shouldThrowFenParserExceptionIfCantParsePieceDisposition() {
		FenParser parser = new FenParser("rnbqkbnr/pp1pppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2");
		assertThrows(FenParserException.class, () -> {
			parser.parse();
		});
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void shouldThrowFenParserExceptionIfPieceDispositionHasInvalidPieceLetter() {
		FenParser parser = new FenParser("rnbqkbnr/pp1pppXp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2");
		assertThrows(FenParserException.class, () -> {
			parser.parse();
		});
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void halfMoveClockShouldBeParsedToOne() {
		FenParser parser = new FenParser("rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2");
		assertEquals(1, parser.getFenPositionInfo().getHalfMoveClock());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void fullMoveCounterShouldBeParsedToTwo() {
		FenParser parser = new FenParser("rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2");
		assertEquals(2, parser.getFenPositionInfo().getFullMoveCounter());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void halMoveClockShouldBeParsedToZero() {
		FenParser parser = new FenParser("rn1qkb1r/pp3ppp/3p1n2/2pPp3/2B1P3/2N5/PPP2PPP/R1BbK1NR w KQkq c6 -");
		assertEquals(0, parser.getFenPositionInfo().getHalfMoveClock());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void allCastlingFlagsShouldBeParsedToTrue() {
		FenParser parser = new FenParser("rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2");
		assertTrue(parser.getFenPositionInfo().isWhiteKingCastlingAvaiable());
		assertTrue(parser.getFenPositionInfo().isWhiteQueenCastlingAvaiable());
		assertTrue(parser.getFenPositionInfo().isBlackKingCastlingAvaiable());
		assertTrue(parser.getFenPositionInfo().isBlackQueenCastlingAvaiable());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void onlyWhiteKingCastlingShouldBeTrue() {
		FenParser parser = new FenParser("rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b K - 1 2");
		assertTrue(parser.getFenPositionInfo().isWhiteKingCastlingAvaiable());
		assertFalse(parser.getFenPositionInfo().isWhiteQueenCastlingAvaiable());
		assertFalse(parser.getFenPositionInfo().isBlackKingCastlingAvaiable());
		assertFalse(parser.getFenPositionInfo().isBlackQueenCastlingAvaiable());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void allCastlingFlagsShouldBeParsedToFalse() {
		FenParser parser = new FenParser("rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b - - 1 2");
		assertFalse(parser.getFenPositionInfo().isWhiteKingCastlingAvaiable());
		assertFalse(parser.getFenPositionInfo().isWhiteQueenCastlingAvaiable());
		assertFalse(parser.getFenPositionInfo().isBlackKingCastlingAvaiable());
		assertFalse(parser.getFenPositionInfo().isBlackQueenCastlingAvaiable());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void sideToMoveShouldBeParsedToWhite() {
		FenParser parser = new FenParser("rn1qkb1r/ppp2ppp/3p1n2/4p3/2B1P1b1/2NP4/PPP2PPP/R1BQK1NR w KQkq - 1 5");
		assertEquals(MovementUtil.WHITE, parser.getFenPositionInfo().getSideToMove());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void sideToMoveShouldBeParsedToBlack() {
		FenParser parser = new FenParser("rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b - - 1 2");
		assertEquals(MovementUtil.BLACK, parser.getFenPositionInfo().getSideToMove());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void enPassantTargetSquareShouldBeParsedToc6() {
		FenParser parser = new FenParser("rn1qkb1r/pp3ppp/3p1n2/2pPp3/2B1P3/2N5/PPP2PPP/R1BbK1NR w KQkq c6 0 7");
		assertEquals(Position.of('c', (byte) 6), parser.getFenPositionInfo().getEnPassantTargetSquare());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void enPassantTargetSquareShouldBeParsedToNull() {
		FenParser parser = new FenParser("rn1qkb1r/pp3ppp/3p1n2/2pPp3/2B1P3/2N5/PPP2PPP/R1BbK1NR w KQkq - 0 7");
		assertNull(parser.getFenPositionInfo().getEnPassantTargetSquare());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void shoudThrowFenParserExceptionWhenSideToMoveIsInvalid() {
		FenParser parser = new FenParser("rn1qkb1r/pp3ppp/3p1n2/2pPp3/2B1P3/2N5/PPP2PPP/R1BbK1NR x KQkq - 0 7");
		assertThrows(FenParserException.class, () -> {
			parser.parse();
		});
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void shoudThrowFenParserExceptionWhenCantParsesCastlingFlags() {
		FenParser parser = new FenParser("rn1qkb1r/pp3ppp/3p1n2/2pPp3/2B1P3/2N5/PPP2PPP/R1BbK1NR w KNkq - 0 7");
		assertThrows(FenParserException.class, () -> {
			parser.parse();
		});
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void whenFullMoveCounterIsNotInformedParserShouldReturn1() {
		FenParser parser = new FenParser("rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b K -");
		assertEquals(1, parser.getFenPositionInfo().getFullMoveCounter());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testWhenCastlingFlagsHaveMoreThanFourCharacterShouldThrowFenParserException() {
		FenParser parser = new FenParser("rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkqb -");
		assertThrows(
			FenParserException.class,
			() -> parser.getFenPositionInfo().getFullMoveCounter(),
			"FenParser.parse should throw FenParserException when castling flags have more than 4 characters"
		);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testWhenSideToMoveIsMissingShouldThrowFenParserException() {
		FenParser parser = new FenParser("rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R");
		assertThrows(
			FenParserException.class,
			() -> parser.getFenPositionInfo().getFullMoveCounter(),
			"FenParser.parse should throw FenParserException when side to move is missing"
		);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testWhenPieceDispositionIsEmptyShouldThrowFenParserException() {
		FenParser parser = FenParser.of(" ");
		assertThrows(
			FenParserException.class,
			() -> parser.parse(),
			"FenParser.parse should throw FenParserException when piece disposition is not present"
		);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testWhenPieceDispositionIsNotPresentShouldThrowFenParserException() {
		FenParser parser = new FenParser("");
		assertThrows(
			FenParserException.class,
			() -> parser.parse(),
			"FenParser.parse should throw FenParserException when piece disposition is not present"
		);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testWhenPieceDispositionIsNotWellFormedShouldThrowFenParseException() {
		FenParser parser = new FenParser("rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/RNBQKB1R b");
		assertThrows(
			FenParserException.class,
			() -> parser.parse(),
			"FenParser.parse should throw FenParserException when piece disposition is bad formed"
		);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testWhenCastlingFlagsHaveIsMissingShouldThrowFenParserException() {
		FenParser parser = new FenParser("rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b");
		assertThrows(
			FenParserException.class,
			() -> parser.getFenPositionInfo().getFullMoveCounter(),
			"FenParser.parse should throw FenParserException when castling is missing"
		);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testWhenEnPassantHasMoreIsNotInformedShouldThrowFenParserException() {
		FenParser parser = new FenParser("rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b K");
		assertThrows(
			FenParserException.class,
			() -> parser.getFenPositionInfo().getFullMoveCounter(),
			"FenParser.parse throw FenParserException when en passant target square is not informed"
		);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testWhenEnPassantHasMoreThanTwoCharactersShouldThrowFenParserException() {
		FenParser parser = new FenParser("rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b K xyz - -");
		assertThrows(
			FenParserException.class,
			() -> parser.getFenPositionInfo().getFullMoveCounter(),
			"FenParser.parse throw FenParserException when en passant target square has invalid format"
		);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testWhenFullMoveCounterIsInformedAsHyphenParserShouldReturn1() {
		FenParser parser = new FenParser("rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b K - - -");
		assertEquals(
			1,
			parser.getFenPositionInfo().getFullMoveCounter(),
			"FenParser.parse should parse full move counter to 1 when it is informed as hyphen character"
		);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testParseThrowFenParserExceptionWhenUnableToParseHalfMoveClock() {
		FenParser parser = new FenParser("rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b K - x");
		assertThrows(
			FenParserException.class,
			() -> parser.parse(),
			"FenParser.parse should throw FenParserException when it can't parse helf move clock"
		);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testParseThrowFenParserExceptionWhenUnableToParseFullMoveCounter() {
		FenParser parser = new FenParser("rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b K - 0 x");
		assertThrows(
			FenParserException.class,
			() -> parser.parse(),
			"FenParser.parse should throw FenParserException when it can't parse full move counter"
		);
	}
}
