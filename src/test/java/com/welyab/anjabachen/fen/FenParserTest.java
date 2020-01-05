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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.welyab.anjabachen.LocalizedPiece;
import com.welyab.anjabachen.Piece;

@SuppressWarnings("all")
public class FenParserTest {
	
	@ParameterizedTest
	@CsvSource(
		{
			"9,rnb2bnr/p1pq3p/4k1p1/1p1pppB1/2BPP3/2N2N1P/PPP1KPP1/R2Q3R w - - 1 9",
			"13,rnb2bnr/p1p4p/4k1p1/3pppB1/PqpPP3/2N2N1P/2P1KPP1/R2Q3R w - - 0 13",
			"17,rnb2bnr/p1p5/4k1p1/P3p1Pp/2pPpp2/1qN2N1P/2PBKP2/R2Q3R w - h6 0 17",
			"12,r4b1r/p2nkp1p/b1p2n2/1p1p2q1/2PpP3/2N4P/PPRQNPPR/4KB2 w - - 6 12"
		}
	)
	@DisplayName("it should parse correct full move counter")
	public void testParseCorrectFullMoveCounter(int expectedFullMoveCounter, String fen) {
		FenParser parser = FenParser.createParser(fen);
		assertEquals(expectedFullMoveCounter, parser.getFullMoveCounter());
	}
	
	@Test
	@DisplayName("isParsed should return false when FenParser is brand new")
	public void isParsedShouldReturnFalseWnenFenParserIsBrandNew() {
		assertFalse(
			FenParser.createParser(
				"2r1k2r/pbqp1ppp/1p6/1BpPp3/Pn2P1n1/1PN1BN2/2P2PPP/R2Q1RK1 w k - 9 15"
			).isParsed()
		);
	}
	
	@Test
	@DisplayName("isParsed should return true when FenParser already parsed fen string")
	public void isParsedShouldReturnTrueWhenFenParserAlreadyParsedFenString() {
		FenParser parser = FenParser.createParser(
			"2r1k2r/pbqp1ppp/1p6/1BpPp3/Pn2P1n1/1PN1BN2/2P2PPP/R2Q1RK1 w k - 9 15"
		);
		parser.parse();
		assertTrue(parser.isParsed());
	}
	
	@ParameterizedTest
	@ValueSource(
		strings = {
			"2r1k2rr/pbqp1ppp/1p6/1BpPp3/Pn2P1n1/1PN1BN2/2P2PPP/R2Q1RK1 w k - 9 15",
			"r1bqk2r/pppp2ppp/2n2n2/1Bb1p3/4P3/2N2N2/PPPP1PPP/R1BQ1RK1 b kq - 7 5",
			"r2qk2r/pb1p1ppp//1BpPp3/Pn2P3/1PN1bN2/2P2PPP/R1BQ1RK1 w kq c6 0 10",
			"2r1k2r/pbqp1ppp/1p6/1BpPp3/Pn2P1n1/1PN1BN2/2P2PPP/R2Q1RK1/2P2PPP w k - 9 15",
			"2r1k2r/pbqp1ppp/1p6/1B0pPp3/Pn2P1n1/1PN1BN2/2P2PPP/R2Q1RK1 w k - 9 15",
			"2r1k2r/pbqp1ppp/1x6/1BpPp3/Pn2P1n1/1PN1BN2/2P2PPP/R2Q1RK1 w k - 9 15",
			"2r1k2r/pbqp1ppp/1p6/1BpPp3/Pn2P1n1/1PN1BN2/2P2P PP/R2Q1RK1 w k - 9 15",
			"2r1k2r/pbqp1ppp/1p6/1BpPp3p/Pn2P1n1/1PN1BN2/2P2PPP/R2Q1RK1 w k - 9 15",
			"2r1k2r/pbqp1ppp/1p6/1BpPp3/Pn2P1n1/1PN7BN2/2P2P PP/R2Q1RK1 w k - 9 15"
		}
	)
	@DisplayName("when piece disposition is invalid it should throw FenParserException")
	public void whenPieceDispositionIsInvalidItShouldThrowFenParserException(String fen) {
		assertThrows(FenParserException.class, () -> {
			FenParser.createParser(fen).parse();
		});
	}
	
	@ParameterizedTest
	@ValueSource(
		strings = {
			"rnb2bnr/p1p5/4k1p1/P3p1Pp/2pPpp2/1qN2N1P/2PBKP2/R2Q3R w",
			"rnb2bnr/p1p5/4k1p1/P3p1Pp/2pPpp2/1qN2N1P/2PBKP2/R2Q3R x",
			"rnb2bnr/p1p5/4k1p1/P3p1Pp/2pPpp2/1qN2N1P/2PBKP2/R2Q3R",
			"rnb2bnr/p1p5/4k1p1/P3p1Pp/2pPpp2/1qN2N1P/2PBKP2/R2Q3R wb"
		}
	)
	@DisplayName("when side to move is not informed or invalid, the parser should thrown FenParserException")
	public void whenSideToMoveIsNotInformedOrInvalidTheParserShouldThrownFenParserException(String fen) {
		assertThrows(FenParserException.class, () -> {
			FenParser.createParser(fen).parse();
		});
	}
	
	@ParameterizedTest
	@ValueSource(
		strings = {
			"rnb2bnr/p1pq3p/4k1p1/1p1pppB1/2BPP3/2N2N1P/PPP1KPP1/R2Q3R w",
			"rnb2bnr/p1p4p/4k1p1/3pppB1/PqpPP3/2N2N1P/2P1KPP1/R2Q3R w --",
			"rnb2bnr/p1p5/4k1p1/P3p1Pp/2pPpp2/1qN2N1P/2PBKP2/R2Q3R w xyz h6 0 17",
			"r4b1r/p2nkp1p/b1p2n2/1p1p2q1/2PpP3/2N4P/PPRQNPPR/4KB2 w KQkqq - 6 12"
		}
	)
	@DisplayName("when castling flags are not informed or invalid should thrown FenParserException")
	public void whenCastlingFlagsAreNotInformedOrInvalidShouldThrownFenParserException(String fen) {
		assertThrows(FenParserException.class, () -> {
			FenParser.createParser(fen).parse();
		});
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			"true,true,true,true,r2qk2r/ppp2ppp/2np1n2/1Bb1p1B1/4P1b1/2NP1N2/PPP2PPP/R2QK2R w KQkq - 2 7",
			"true,false,true,true,r2qk2r/ppp2ppp/2np1n2/1Bb1p1B1/4P1b1/2NP1N2/PPP2PPP/1R1QK2R b Kkq - 3 7",
			"true,true,true,false,1r1qk2r/ppp2ppp/2np1n2/1Bb1p1B1/4P1b1/P1NP1N2/1PP2PPP/R2QK2R w KQk - 1 8",
			"true,true,false,true,r2qk1r1/ppp2ppp/2np1n2/1Bb1p1B1/4P1b1/P1NP1N2/1PP2PPP/R2QK2R w KQq - 1 8",
			"false,true,true,true,r2qk2r/ppp2ppp/2np1n2/1Bb1p1B1/4P1b1/2NP1N2/PPP2PPP/R2QK1R1 b Qkq - 3 7",
			"false,false,true,true,r2qk2r/ppp2ppp/2np1n2/1Bb1p1B1/4P1b1/2NP1N2/PPP2PPP/R2Q1RK1 b kq - 3 7",
			"true,false,false,true,r2qk1r1/ppp2ppp/2np1n2/1Bb1p1B1/4P1b1/2NP1N2/PPP2PPP/1R1QK2R w Kq - 4 8",
			"false,false,false,false,r2q1rk1/ppp2ppp/2np1n2/1Bb1p1B1/4P1b1/2NP1N2/PPP2PPP/R2Q1RK1 w - - 4 8"
		}
	)
	@DisplayName("castling flags should be parsed correctly")
	public void castlingFlagsShouldBeParsedCorrectly(
		boolean whiteKingCastling,
		boolean whiteQueenCastling,
		boolean blackKingCastling,
		boolean blackQueenCastling,
		String fen
	) {
		FenParser parser = FenParser.createParser(fen);
		parser.parse();
		assertEquals(whiteKingCastling, parser.isWhiteKingCastling());
		assertEquals(whiteQueenCastling, parser.isWhiteQueenCastling());
		assertEquals(blackKingCastling, parser.isBlackKingCastling());
		assertEquals(blackQueenCastling, parser.isBlackQueenCastling());
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			"1,rnb2bnr/p1pq3p/4k1p1/1p1pppB1/2BPP3/2N2N1P/PPP1KPP1/R2Q3R w - - 1 9",
			"0,rnb2bnr/p1p4p/4k1p1/3pppB1/PqpPP3/2N2N1P/2P1KPP1/R2Q3R w - - 0 13",
			"0,rnb2bnr/p1p5/4k1p1/P3p1Pp/2pPpp2/1qN2N1P/2PBKP2/R2Q3R w - h6 0 17",
			"6,r4b1r/p2nkp1p/b1p2n2/1p1p2q1/2PpP3/2N4P/PPRQNPPR/4KB2 w - - 6 12"
		}
	)
	@DisplayName("it should parse correct half clock counter")
	public void testItShouldParseCorrectHalfClockCounter(int expectedHalfMoveClock, String fen) {
		FenParser parser = FenParser.createParser(fen);
		assertEquals(expectedHalfMoveClock, parser.getHalfMoveClock());
	}
	
	@ParameterizedTest
	@ValueSource(
		strings = {
			"2r1k2r/pbqp1ppp/1p6/1BpPp3/Pn2P1n1/1PN1BN2/2P2PPP/R2Q1RK1 w k - 9x 15",
			"2r1k2r/pbqp1ppp/1p6/1BpPp3/Pn2P1n1/1PN1BN2/2P2PPP/R2Q1RK1 w k - asdf 15",
			"2r1k2r/pbqp1ppp/1p6/1BpPp3/Pn2P1n1/1PN1BN2/2P2PPP/R2Q1RK1 w k - .d 15",
			"2r1k2r/pbqp1ppp/1p6/1BpPp3/Pn2P1n1/1PN1BN2/2P2PPP/R2Q1RK1 w k - x 15"
		}
	)
	@DisplayName("it should thrown FenParserException when half move clock is invalid")
	public void itShouldThrownFenParserExceptionWhenHalfMoveClockIsInvalid(String fen) {
		assertThrows(
			FenParserException.class,
			() -> {
				FenParser.createParser(fen).parse();
			}
		);
	}
	
	@ParameterizedTest
	@ValueSource(
		strings = {
			"2r1k2r/pbqp1ppp/1p6/1BpPp3/Pn2P1n1/1PN1BN2/2P2PPP/R2Q1RK1 w k - 9 1x5",
			"2r1k2r/pbqp1ppp/1p6/1BpPp3/Pn2P1n1/1PN1BN2/2P2PPP/R2Q1RK1 w k - 9 15d",
			"2r1k2r/pbqp1ppp/1p6/1BpPp3/Pn2P1n1/1PN1BN2/2P2PPP/R2Q1RK1 w k - 9 1d5",
			"2r1k2r/pbqp1ppp/1p6/1BpPp3/Pn2P1n1/1PN1BN2/2P2PPP/R2Q1RK1 w k - 9 1_5"
		}
	)
	@DisplayName("it should thrown FenParserException when full move counter is invalid")
	public void itShouldThrownFenParserExceptionWhenFullMoveCounterIsInvalid(String fen) {
		assertThrows(
			FenParserException.class,
			() -> {
				FenParser.createParser(fen).parse();
			}
		);
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			"b,rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 1",
			"w,r1bqkbnr/pppp1ppp/2n5/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq - 2 3",
			"b,r1bqk1nr/pppp1ppp/2n5/1B2p3/1b2P3/2P2N2/PP1P1PPP/RNBQK2R b KQkq - 0 4",
			"w,r1bqk1nr/pppp2pp/2n2p2/1B2p3/1b2P3/2P2N2/PP1P1PPP/RNBQK2R w KQkq - 0 5",
			"b,r1bqk1nr/pppp2pp/2n2p2/1B2p3/1b2P3/2P2N2/PP1P1PPP/RNBQ1RK1 b kq - 1 5",
			"w,r1bqk2r/pppp2pp/2n2p1n/1B2p3/1b2P3/2P2N2/PP1P1PPP/RNBQ1RK1 w kq - 2 6",
			"b,r1bq1rk1/pppp2pp/2n2p1n/1B2p3/1b1PP3/P1P2N2/1P3PPP/RNBQ1RK1 b - - 0 7"
		}
	)
	@DisplayName("should parse side to move properly")
	public void testShouldParseSideToMov(char sideToMove, String fen) {
		FenParser parser = FenParser.createParser(fen);
		assertEquals(sideToMove, parser.getSideToMove().getLetter());
	}
	
	@Test
	@DisplayName("when FEN is empty it should thrown FenParserException")
	public void testShouldThrownFenParserExceptionWhenFenIsEmpty() {
		assertThrows(
			FenParserException.class,
			() -> {
				FenParser.createParser("").parse();
			}
		);
	}
	
	@ParameterizedTest
	@ValueSource(
		strings = {
			"r1bqkb1r/p1p2ppp/2n2n2/1pP5/4p3/2N5/PP1PBPpP/R1BQ1RK1  w kq b6 0 10",
			"r1bqkb1r/p1p2ppp/2n2n2/1pP5/4p3/2N5/PP1PBPpP/R1BQ1RK1 w  kq b6 0 10",
			"r1bqkb1r/p1p2ppp/2n2n2/1pP5/4p3/2N5/PP1PBPpP/R1BQ1RK1 w kq b6  0 10",
			"r1bqkb1r/p1p2ppp/2n2n2/1pP5/4p3/2N5/PP1PBPpP/R1BQ1RK1 w kq b6 0  10",
			"r1bqkb1r/p1p2ppp/2n2n2/1pP5/4p3/2N5/PP1PBPpP/R1BQ1RK1\tw kq b6 0  10",
			"r1bqkb1r/p1p2ppp/2n2n2/1pP5/4p3/2N5/PP1PBPpP/R1BQ1RK1\nw kq b6 0  10",
			"r1bqkb1r/p1p2ppp/2n2n2/1pP5/4p3/2N5/PP1PBPpP/R1BQ1RK1\rw kq b6 0  10"
		}
	)
	@DisplayName("when FEN has two consecutive white spaces between its parts a FenParserException should be thrown")
	public void testShouldThrownFenParserExceptionWhenTwoConsecutiveWhiteSpaces(String fen) {
		assertThrows(
			FenParserException.class,
			() -> {
				FenParser.createParser(fen).parse();
			}
		);
	}
	
	@ParameterizedTest
	@ValueSource(
		strings = {
			"r1bqkb1r/p1p2ppp/2n2n2/1pP5/4p3/2N5/PP1PBPpP/R1BQ1RK1 w kq",
			"r1bqkb1r/p1p2ppp/2n2n2/1pP5/4p3/2N5/PP1PBPpP/R1BQ1RK1 w kq b6",
		}
	)
	@DisplayName("when FEN is not complete it should thrown FenParserException")
	public void testShouldThrownFenParserExceptionWhenFenIsNotComplete(String fen) {
		assertThrows(
			FenParserException.class,
			() -> {
				FenParser.createParser(fen).parse();
			}
		);
	}
	
	@ParameterizedTest
	@ValueSource(
		strings = {
			" r1bqkb1r/p1p2ppp/2n2n2/1pP5/4p3/2N5/PP1PBPpP/R1BQ1RK1 w kq b6 0 10",
			"   r1bqkb1r/p1p2ppp/2n2n2/1pP5/4p3/2N5/PP1PBPpP/R1BQ1RK1 w kq b6 0 10",
			"r1bqkb1r/p1p2ppp/2n2n2/1pP5/4p3/2N5/PP1PBPpP/R1BQ1RK1 w kq b6 0 10 ",
			"r1bqkb1r/p1p2ppp/2n2n2/1pP5/4p3/2N5/PP1PBPpP/R1BQ1RK1 w kq b6 0 10   "
		}
	)
	@DisplayName("when FEN has starts or ends with white spaces it should thrown FenParserException")
	public void testThrownWhenFenStartsWithWhiteSpaces(String fen) {
		assertThrows(
			FenParserException.class,
			() -> {
				FenParser.createParser(fen).parse();
			}
		);
	}
	
	@Test
	public void getPiecesDispositionShouldReturnCorrectValues1() {
		List<LocalizedPiece> disposition = FenParser
			.createParser("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")
			.getPiecesDisposition();
		assertEquals(Piece.BLACK_ROOK, disposition.get(0).getPiece());
		assertEquals(Piece.BLACK_KNIGHT, disposition.get(1).getPiece());
		assertEquals(Piece.BLACK_BISHOP, disposition.get(2).getPiece());
		assertEquals(Piece.BLACK_QUEEN, disposition.get(3).getPiece());
		assertEquals(Piece.BLACK_KING, disposition.get(4).getPiece());
		assertEquals(Piece.BLACK_BISHOP, disposition.get(5).getPiece());
		assertEquals(Piece.BLACK_KNIGHT, disposition.get(6).getPiece());
		assertEquals(Piece.BLACK_ROOK, disposition.get(7).getPiece());
		assertEquals(Piece.BLACK_PAWN, disposition.get(8).getPiece());
		assertEquals(Piece.BLACK_PAWN, disposition.get(9).getPiece());
		assertEquals(Piece.BLACK_PAWN, disposition.get(10).getPiece());
		assertEquals(Piece.BLACK_PAWN, disposition.get(11).getPiece());
		assertEquals(Piece.BLACK_PAWN, disposition.get(12).getPiece());
		assertEquals(Piece.BLACK_PAWN, disposition.get(13).getPiece());
		assertEquals(Piece.BLACK_PAWN, disposition.get(14).getPiece());
		assertEquals(Piece.BLACK_PAWN, disposition.get(15).getPiece());
		
		assertEquals(Piece.WHITE_PAWN, disposition.get(16).getPiece());
		assertEquals(Piece.WHITE_PAWN, disposition.get(17).getPiece());
		assertEquals(Piece.WHITE_PAWN, disposition.get(18).getPiece());
		assertEquals(Piece.WHITE_PAWN, disposition.get(19).getPiece());
		assertEquals(Piece.WHITE_PAWN, disposition.get(20).getPiece());
		assertEquals(Piece.WHITE_PAWN, disposition.get(21).getPiece());
		assertEquals(Piece.WHITE_PAWN, disposition.get(22).getPiece());
		assertEquals(Piece.WHITE_PAWN, disposition.get(23).getPiece());
		assertEquals(Piece.WHITE_ROOK, disposition.get(24).getPiece());
		assertEquals(Piece.WHITE_KNIGHT, disposition.get(25).getPiece());
		assertEquals(Piece.WHITE_BISHOP, disposition.get(26).getPiece());
		assertEquals(Piece.WHITE_QUEEN, disposition.get(27).getPiece());
		assertEquals(Piece.WHITE_KING, disposition.get(28).getPiece());
		assertEquals(Piece.WHITE_BISHOP, disposition.get(29).getPiece());
		assertEquals(Piece.WHITE_KNIGHT, disposition.get(30).getPiece());
		assertEquals(Piece.WHITE_ROOK, disposition.get(31).getPiece());
	}
	
	@Test
	public void getPiecesDispositionShouldReturnCorrectValues2() {
		List<LocalizedPiece> disposition = FenParser
			.createParser("1kr5/1p3p2/bQB5/p3P3/3P1Pp1/6P1/8/2R3K1 b - - 0 38")
			.getPiecesDisposition();
		assertEquals(Piece.BLACK_KING, disposition.get(0).getPiece());
		assertEquals(Piece.BLACK_ROOK, disposition.get(1).getPiece());
		assertEquals(Piece.BLACK_PAWN, disposition.get(2).getPiece());
		assertEquals(Piece.BLACK_PAWN, disposition.get(3).getPiece());
		assertEquals(Piece.BLACK_BISHOP, disposition.get(4).getPiece());
		assertEquals(Piece.WHITE_QUEEN, disposition.get(5).getPiece());
		assertEquals(Piece.WHITE_BISHOP, disposition.get(6).getPiece());
		assertEquals(Piece.BLACK_PAWN, disposition.get(7).getPiece());
		assertEquals(Piece.WHITE_PAWN, disposition.get(8).getPiece());
		assertEquals(Piece.WHITE_PAWN, disposition.get(9).getPiece());
		assertEquals(Piece.WHITE_PAWN, disposition.get(10).getPiece());
		assertEquals(Piece.BLACK_PAWN, disposition.get(11).getPiece());
		assertEquals(Piece.WHITE_PAWN, disposition.get(12).getPiece());
		assertEquals(Piece.WHITE_ROOK, disposition.get(13).getPiece());
		assertEquals(Piece.WHITE_KING, disposition.get(14).getPiece());
	}
}
