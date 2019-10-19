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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Unit tests for the <code>Board</code> class.
 * 
 * @author Welyab Paula
 */
public class BoardTest {
	
	@Test
	@SuppressWarnings("javadoc")
	public void piecePositionsAndTypeShouldHaveCorrectInformation() {
		List<LocalizedPiece> lpieces = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")
			.getLocalizedPieces();
		
		assertEquals(BoardUtil.BLACK_ROOK_CODE, lpieces.get(0).getPieceCode());
		assertEquals(BoardUtil.BLACK_KNIGHT_CODE, lpieces.get(1).getPieceCode());
		assertEquals(BoardUtil.BLACK_BISHOP_CODE, lpieces.get(2).getPieceCode());
		assertEquals(BoardUtil.BLACK_QUEEN_CODE, lpieces.get(3).getPieceCode());
		assertEquals(BoardUtil.BLACK_KING_CODE, lpieces.get(4).getPieceCode());
		assertEquals(BoardUtil.BLACK_BISHOP_CODE, lpieces.get(5).getPieceCode());
		assertEquals(BoardUtil.BLACK_KNIGHT_CODE, lpieces.get(6).getPieceCode());
		assertEquals(BoardUtil.BLACK_ROOK_CODE, lpieces.get(7).getPieceCode());
		assertEquals(BoardUtil.BLACK_PAWN_CODE, lpieces.get(8).getPieceCode());
		assertEquals(BoardUtil.BLACK_PAWN_CODE, lpieces.get(9).getPieceCode());
		assertEquals(BoardUtil.BLACK_PAWN_CODE, lpieces.get(10).getPieceCode());
		assertEquals(BoardUtil.BLACK_PAWN_CODE, lpieces.get(11).getPieceCode());
		assertEquals(BoardUtil.BLACK_PAWN_CODE, lpieces.get(12).getPieceCode());
		assertEquals(BoardUtil.BLACK_PAWN_CODE, lpieces.get(13).getPieceCode());
		assertEquals(BoardUtil.BLACK_PAWN_CODE, lpieces.get(14).getPieceCode());
		assertEquals(BoardUtil.BLACK_PAWN_CODE, lpieces.get(15).getPieceCode());
		
		assertEquals(BoardUtil.WHITE_PAWN_CODE, lpieces.get(16).getPieceCode());
		assertEquals(BoardUtil.WHITE_PAWN_CODE, lpieces.get(17).getPieceCode());
		assertEquals(BoardUtil.WHITE_PAWN_CODE, lpieces.get(18).getPieceCode());
		assertEquals(BoardUtil.WHITE_PAWN_CODE, lpieces.get(19).getPieceCode());
		assertEquals(BoardUtil.WHITE_PAWN_CODE, lpieces.get(20).getPieceCode());
		assertEquals(BoardUtil.WHITE_PAWN_CODE, lpieces.get(21).getPieceCode());
		assertEquals(BoardUtil.WHITE_PAWN_CODE, lpieces.get(22).getPieceCode());
		assertEquals(BoardUtil.WHITE_PAWN_CODE, lpieces.get(23).getPieceCode());
		assertEquals(BoardUtil.WHITE_ROOK_CODE, lpieces.get(24).getPieceCode());
		assertEquals(BoardUtil.WHITE_KNIGHT_CODE, lpieces.get(25).getPieceCode());
		assertEquals(BoardUtil.WHITE_BISHOP_CODE, lpieces.get(26).getPieceCode());
		assertEquals(BoardUtil.WHITE_QUEEN_CODE, lpieces.get(27).getPieceCode());
		assertEquals(BoardUtil.WHITE_KING_CODE, lpieces.get(28).getPieceCode());
		assertEquals(BoardUtil.WHITE_BISHOP_CODE, lpieces.get(29).getPieceCode());
		assertEquals(BoardUtil.WHITE_KNIGHT_CODE, lpieces.get(30).getPieceCode());
		assertEquals(BoardUtil.WHITE_ROOK_CODE, lpieces.get(31).getPieceCode());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void isEmptyShouldReturnTrueWhenSquareIsEmpty() {
		assertTrue(new Board().isEmpty(Position.of(4, 4)));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void isEmptyShouldReturnFalseWhenSquareIsNotEmpty() {
		assertFalse(new Board().isEmpty(Position.of(1, 3)));
	}
	
	@ParameterizedTest
	@CsvSource({
			BoardUtil.BLACK_ROOK_CODE + "," + 0 + "," + 0,
			BoardUtil.BLACK_QUEEN_CODE + "," + 0 + "," + 3,
			BoardUtil.BLACK_BISHOP_CODE + "," + 0 + "," + 2,
			BoardUtil.WHITE_KING_CODE + "," + 7 + "," + 4,
			BoardUtil.WHITE_PAWN_CODE + "," + 6 + "," + 2,
			BoardUtil.WHITE_KNIGHT_CODE + "," + 7 + "," + 6,
			BoardUtil.WHITE_ROOK_CODE + "," + 7 + "," + 0,
	})
	@SuppressWarnings("javadoc")
	public void getPieceShouldReturnProperlyPiece(byte pieceCode, byte row, byte column) {
		assertEquals(pieceCode, new Board().getPiece(Position.of(row, column)));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void getPieceShouldThronEmptySquareExceptionWhenSquareIsEmpty() {
		assertThrows(EmptySquareException.class, () -> {
			new Board().getPiece(Position.of(4, 4));
		});
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
			"rnbqkb1r/pppp1ppp/5n2/4p3/3P4/2N5/PPP1PPPP/R1BQKBNR w KQkq - 2 3",
			"rnbqk2r/p3bpp1/1p1p1n1p/2pN4/3Pp3/P3P2P/1PPBBPP1/R2QK1NR w KQkq - 2 9",
			"r3k2r/pb1qbp2/np1p1n1p/3N1PpP/2pPp1P1/P3P3/1PPBB3/R2QK1NR w KQkq g6 0 14",
			"r3k2r/pb1qbp2/np1p1n1p/3N1PpP/2pPp1PR/P3P3/1PPBB3/R2QK1N1 b Qkq - 1 14",
			"3rk2r/pb1qbp2/np1p1n1p/3N1PpP/2pPp1PR/P3P3/1PPBB3/R2QK1N1 w Qk - 2 15",
			"3rk2r/pb1qbp2/n2p1n1p/1p1N1PpP/2pPp1PR/PQP1P3/1P1BB3/R3K1N1 b Qk - 1 16",
			"3rk2r/pb1qbp2/n2p3p/1p1n1PpP/2pPp1PR/PQP1P3/1P1BB3/2KR2N1 b k - 1 17",
			"3r1rk1/pb1qbp2/n2p3p/1p1n1PpP/2pPp1PR/PQP1P3/1P1BB3/2KR2N1 w - - 2 18",
			"3r1rk1/pb2bp2/2qp3p/2B2P1P/1PpPp1Pp/4P3/2Q1B3/2KR2N1 b - b3 0 23",
			"3r1rk1/pb2bp2/2qp3p/2B2P1P/3Pp1Pp/1p2P3/2Q1B3/2KR2N1 w - - 0 24",
			"2r3k1/p4p2/7p/4bP1P/3Pp1Pp/8/8/2KR2N1 w - - 1 32",
			"8/p4p2/7k/3P1Pb1/4p3/2K3N1/7p/8 b - - 0 40",
			"8/p4p2/7k/3PKP2/4p3/4b3/4N2p/8 w - - 19 50",
			"8/8/5K1k/3P1P2/p7/8/8/7N w - - 0 57",
			"5q2/8/4K3/3P4/6k1/8/8/8 w - - 0 64",
			"8/8/3K4/8/6k1/8/8/8 b - - 0 65"
	})
	@SuppressWarnings("javadoc")
	public void aBoardBuiltFromFenShouldProduceSameFenString(String fen) {
		assertEquals(fen, new Board(fen).getFen());
	}
	
	@ParameterizedTest
	@CsvSource({
			"1,true,true,true,true,,0,1,rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
			"1,true,true,true,true,,2,3,rnbqkb1r/pppp1ppp/5n2/4p3/3P4/2N5/PPP1PPPP/R1BQKBNR w KQkq - 2 3",
			"1,true,true,true,true,,2,9,rnbqk2r/p3bpp1/1p1p1n1p/2pN4/3Pp3/P3P2P/1PPBBPP1/R2QK1NR w KQkq - 2 9",
			"1,true,true,true,true,g6,0,14,r3k2r/pb1qbp2/np1p1n1p/3N1PpP/2pPp1P1/P3P3/1PPBB3/R2QK1NR w KQkq g6 0 14",
			"-1,false,true,true,true,,1,14,r3k2r/pb1qbp2/np1p1n1p/3N1PpP/2pPp1PR/P3P3/1PPBB3/R2QK1N1 b Qkq - 1 14",
			"1,false,true,true,false,,2,15,3rk2r/pb1qbp2/np1p1n1p/3N1PpP/2pPp1PR/P3P3/1PPBB3/R2QK1N1 w Qk - 2 15",
			"-1,false,true,true,false,,1,16,3rk2r/pb1qbp2/n2p1n1p/1p1N1PpP/2pPp1PR/PQP1P3/1P1BB3/R3K1N1 b Qk - 1 16",
			"-1,false,false,true,false,,1,17,3rk2r/pb1qbp2/n2p3p/1p1n1PpP/2pPp1PR/PQP1P3/1P1BB3/2KR2N1 b k - 1 17",
			"1,false,false,false,false,,2,18,3r1rk1/pb1qbp2/n2p3p/1p1n1PpP/2pPp1PR/PQP1P3/1P1BB3/2KR2N1 w - - 2 18",
			"-1,false,false,false,false,b3,0,23,3r1rk1/pb2bp2/2qp3p/2B2P1P/1PpPp1Pp/4P3/2Q1B3/2KR2N1 b - b3 0 23",
			"1,false,false,false,false,,0,24,3r1rk1/pb2bp2/2qp3p/2B2P1P/3Pp1Pp/1p2P3/2Q1B3/2KR2N1 w - - 0 24",
			"1,false,false,false,false,,1,32,2r3k1/p4p2/7p/4bP1P/3Pp1Pp/8/8/2KR2N1 w - - 1 32",
			"-1,false,false,false,false,,0,40,8/p4p2/7k/3P1Pb1/4p3/2K3N1/7p/8 b - - 0 40",
			"1,false,false,false,false,,19,50,8/p4p2/7k/3PKP2/4p3/4b3/4N2p/8 w - - 19 50",
			"1,false,false,false,false,,0,57,8/8/5K1k/3P1P2/p7/8/8/7N w - - 0 57",
			"1,false,false,false,false,,0,64,5q2/8/4K3/3P4/6k1/8/8/8 w - - 0 64",
			"-1,false,false,false,false,,0,65,8/8/3K4/8/6k1/8/8/8 b - - 0 65"
	})
	@SuppressWarnings("javadoc")
	public void stateOfBoardCreateFromFenShouldReflectCorrectValues(
			byte expectedSideToMove,
			boolean expectedWhiteKingCastling,
			boolean expectedWhiteQueenCastling,
			boolean expectedBlackeKingCastling,
			boolean expectedBlackQueenCastling,
			String expectedEnPassantTarget,
			byte expectedHalfMoveCounter,
			short expectedFullMoveCounter,
			String fen
	) {
		BoardState state = new Board(fen).getBoardState();
		assertEquals(expectedSideToMove, state.getSideToMove());
		assertEquals(expectedWhiteKingCastling, BoardUtil.isWhiteKingSideCaslting(state.getCastlingFlags()));
		assertEquals(expectedWhiteQueenCastling, BoardUtil.isWhiteQueenSideCaslting(state.getCastlingFlags()));
		assertEquals(expectedBlackeKingCastling, BoardUtil.isBlackKingSideCaslting(state.getCastlingFlags()));
		assertEquals(expectedBlackQueenCastling, BoardUtil.isBlackQueenSideCaslting(state.getCastlingFlags()));
		assertEquals(
			expectedEnPassantTarget,
			Optional.ofNullable(state.getEnPassantTargetSquare())
				.map(Position::getAlgebraicNotation)
				.orElse(null)
		);
		assertEquals(expectedHalfMoveCounter, state.getHalfMoveClock());
		assertEquals(expectedFullMoveCounter, state.getFullMoveCounter());
	}
	
	@ParameterizedTest
	@CsvSource({
			"a7" + "," + 1 + "," + "K7/8/8/8/8/8/8/8 w - -",
			"c4" + "," + 1 + "," + "8/8/8/3K4/8/8/8/8 w - -",
			"e1" + "," + 1 + "," + "8/8/8/8/8/8/8/5K2 w - -",
			"a7" + "," + -1 + "," + "k7/8/8/8/8/8/8/8 b - -",
			"c4" + "," + -1 + "," + "8/8/8/3k4/8/8/8/8 b - -",
			"e1" + "," + -1 + "," + "8/8/8/8/8/8/8/5k2 b - -"
	})
	@SuppressWarnings("javadoc")
	public void isUnderAttackShouldReturnTrueWhenPositionIsAttackedByTheKing(
			String attackedPositionAn,
			byte attackerColor,
			String fen
	) {
		Board board = new Board(fen);
		assertTrue(board.isUnderAttack(Position.of(attackedPositionAn), attackerColor));
	}
	
	@ParameterizedTest
	@CsvSource({
			"h1" + "," + 1 + "," + "K7/8/8/8/8/8/8/8 w - -",
			"c3" + "," + 1 + "," + "8/8/8/3K4/8/8/8/8 w - -",
			"f3" + "," + 1 + "," + "8/8/8/8/8/8/8/5K2 w - -",
			"h1" + "," + -1 + "," + "k7/8/8/8/8/8/8/8 b - -",
			"c3" + "," + -1 + "," + "8/8/8/3k4/8/8/8/8 b - -",
			"f3" + "," + -1 + "," + "8/8/8/8/8/8/8/5k2 b - -"
	})
	@SuppressWarnings("javadoc")
	public void isUnderAttackShouldReturnFalseWhenPositionIsNotAttackedByTheKing(
			String attackedPositionAn,
			byte attackerColor,
			String fen
	) {
		Board board = new Board(fen);
		assertFalse(board.isUnderAttack(Position.of(attackedPositionAn), attackerColor));
	}
	
	@ParameterizedTest
	@CsvSource({
			"c8" + "," + 1 + "," + "8/8/8/8/8/2Q5/8/8 w - -",
			"b3" + "," + 1 + "," + "8/8/8/8/8/2Q5/8/8 w - -",
			"f6" + "," + 1 + "," + "8/8/8/8/8/2Q5/8/8 w - -",
			"e1" + "," + 1 + "," + "8/8/8/8/8/2Q5/8/8 w - -",
			"a4" + "," + 1 + "," + "8/8/8/8/7Q/8/8/8 w - -",
			"d8" + "," + 1 + "," + "8/8/8/8/7Q/8/8/8 w - -",
			"h1" + "," + 1 + "," + "8/8/8/8/7Q/8/8/8 w - -",
			"f6" + "," + 1 + "," + "8/8/8/8/7Q/8/8/8 w - -",
			"a2" + "," + 1 + "," + "8/8/8/8/8/8/8/Q7 w - -",
			"g1" + "," + 1 + "," + "8/8/8/8/8/8/8/Q7 w - -",
			"h8" + "," + 1 + "," + "8/8/8/8/8/8/8/Q7 w - -",
			"c1" + "," + 1 + "," + "8/8/8/8/8/8/8/Q7 w - -",
			"f5" + "," + 1 + "," + "8/8/8/2Q2B2/8/4R3/8/8 w - -",
			"e3" + "," + 1 + "," + "8/8/8/2Q2B2/8/4R3/8/8 w - -",
			"c8" + "," + -1 + "," + "8/8/8/8/8/2q5/8/8 b - -",
			"b3" + "," + -1 + "," + "8/8/8/8/8/2q5/8/8 b - -",
			"f6" + "," + -1 + "," + "8/8/8/8/8/2q5/8/8 b - -",
			"e1" + "," + -1 + "," + "8/8/8/8/8/2q5/8/8 b - -",
			"a4" + "," + -1 + "," + "8/8/8/8/7q/8/8/8 b - -",
			"d8" + "," + -1 + "," + "8/8/8/8/7q/8/8/8 b - -",
			"h1" + "," + -1 + "," + "8/8/8/8/7q/8/8/8 b - -",
			"f6" + "," + -1 + "," + "8/8/8/8/7q/8/8/8 b - -",
			"a2" + "," + -1 + "," + "8/8/8/8/8/8/8/q7 b - -",
			"g1" + "," + -1 + "," + "8/8/8/8/8/8/8/q7 b - -",
			"h8" + "," + -1 + "," + "8/8/8/8/8/8/8/q7 b - -",
			"c1" + "," + -1 + "," + "8/8/8/8/8/8/8/q7 b - -",
			"f5" + "," + -1 + "," + "8/8/8/2q2b2/8/4r3/8/8 b - -",
			"e3" + "," + -1 + "," + "8/8/8/2q2b2/8/4r3/8/8 b - -"
	})
	@SuppressWarnings("javadoc")
	public void isUnderAttackShouldReturnTrueWhenPositionIsAttackedByTheQueen(
			String attackedPositionAn,
			byte attackerColor,
			String fen
	) {
		Board board = new Board(fen);
		assertTrue(board.isUnderAttack(Position.of(attackedPositionAn), attackerColor));
	}
	
	@ParameterizedTest
	@CsvSource({
			"d5" + "," + 1 + "," + "8/8/8/8/8/8/8/Q7 w - -",
			"g2" + "," + 1 + "," + "8/8/8/8/8/8/8/Q7 w - -",
			"h8" + "," + 1 + "," + "8/8/8/2Q5/8/8/8/8 w - -",
			"a1" + "," + 1 + "," + "8/8/8/2Q5/8/8/8/8 w - -",
			"f2" + "," + 1 + "," + "8/8/8/2Q2B2/8/4R3/8/8 w - -",
			"h5" + "," + 1 + "," + "8/8/8/2Q2B2/8/4R3/8/8 w - -",
			"f8" + "," + 1 + "," + "8/8/3b4/2Q2B2/8/2r1R3/8/8 w - -",
			"c1" + "," + 1 + "," + "8/8/3b4/2Q2B2/8/2r1R3/8/8 w - -",
			"d5" + "," + -1 + "," + "8/8/8/8/8/8/8/q7 b - -",
			"g2" + "," + -1 + "," + "8/8/8/8/8/8/8/q7 b - -",
			"h8" + "," + -1 + "," + "8/8/8/2q5/8/8/8/8 b - -",
			"a1" + "," + -1 + "," + "8/8/8/2q5/8/8/8/8 b - -",
			"f2" + "," + -1 + "," + "8/8/8/2q2b2/8/4r3/8/8 b - -",
			"h5" + "," + -1 + "," + "8/8/8/2q2b2/8/4r3/8/8 b - -",
			"f8" + "," + -1 + "," + "8/8/3B4/2q2b2/8/2R1r3/8/8 b - -",
			"c1" + "," + -1 + "," + "8/8/3B4/2q2b2/8/2R1r3/8/8 b - -"
	})
	@SuppressWarnings("javadoc")
	public void isUnderAttackShouldReturnFalseWhenPositionIsNotAttackedByTheQueen(
			String attackedPositionAn,
			byte attackerColor,
			String fen
	) {
		Board board = new Board(fen);
		assertFalse(board.isUnderAttack(Position.of(attackedPositionAn), attackerColor));
	}
	
	@ParameterizedTest
	@CsvSource({
			"e3" + "," + 1 + "," + "8/8/8/8/8/3R4/8/8 w - -",
			"d1" + "," + 1 + "," + "8/8/8/8/8/3R4/8/8 w - -",
			"d8" + "," + 1 + "," + "8/8/8/8/8/3R4/8/8 w - -",
			"a1" + "," + 1 + "," + "8/8/8/R7/8/8/8/8 w - -",
			"a8" + "," + 1 + "," + "8/8/8/R7/8/8/8/8 w - -",
			"b5" + "," + 1 + "," + "8/8/8/R7/8/8/8/8 w - -",
			"c1" + "," + 1 + "," + "8/8/8/8/8/8/8/R7 w - -",
			"a3" + "," + 1 + "," + "8/8/8/8/8/8/8/R7 w - -",
			"e3" + "," + -1 + "," + "8/8/8/8/8/3r4/8/8 b - -",
			"d1" + "," + -1 + "," + "8/8/8/8/8/3r4/8/8 b - -",
			"d8" + "," + -1 + "," + "8/8/8/8/8/3r4/8/8 b - -",
			"a1" + "," + -1 + "," + "8/8/8/r7/8/8/8/8 b - -",
			"a8" + "," + -1 + "," + "8/8/8/r7/8/8/8/8 b - -",
			"b5" + "," + -1 + "," + "8/8/8/r7/8/8/8/8 b - -",
			"c1" + "," + -1 + "," + "8/8/8/8/8/8/8/r7 b - -",
			"a3" + "," + -1 + "," + "8/8/8/8/8/8/8/r7 b - -"
	})
	@SuppressWarnings("javadoc")
	public void isUnderAttackShouldReturnTrueWhenPositionIsAttackedByTheRook(
			String attackedPositionAn,
			byte attackerColor,
			String fen
	) {
		Board board = new Board(fen);
		assertTrue(board.isUnderAttack(Position.of(attackedPositionAn), attackerColor));
	}
	
	@ParameterizedTest
	@CsvSource({
			"d7" + "," + 1 + "," + "8/8/3B4/8/3R4/3N4/8/8 w - -",
			"d1" + "," + 1 + "," + "8/8/3B4/8/3R4/3N4/8/8 w - -",
			"f6" + "," + 1 + "," + "8/8/3B4/8/3R4/3N4/8/8 w - -",
			"g2" + "," + 1 + "," + "8/8/3B4/8/3R4/3N4/8/8 w - -",
			"h1" + "," + -1 + "," + "8/8/7b/8/7r/7n/8/8 w - -",
			"h7" + "," + -1 + "," + "8/8/7b/8/7r/7n/8/8 w - -",
			"g3" + "," + -1 + "," + "8/8/7b/8/7r/7n/8/8 w - -",
			"e1" + "," + -1 + "," + "8/8/7b/8/7r/7n/8/8 w - -"
	})
	@SuppressWarnings("javadoc")
	public void isUnderAttackShouldReturnFalseWhenPositionIsNotAttackedByTheRook(
			String attackedPositionAn,
			byte attackerColor,
			String fen
	) {
		Board board = new Board(fen);
		assertFalse(board.isUnderAttack(Position.of(attackedPositionAn), attackerColor));
	}
	
	@ParameterizedTest
	@CsvSource({
			"b7" + "," + 1 + "," + "B7/8/8/8/8/8/8/8 w - -",
			"e4" + "," + 1 + "," + "B7/8/8/8/8/8/8/8 w - -",
			"h1" + "," + 1 + "," + "B7/8/8/8/8/8/8/8 w - -",
			"b4" + "," + 1 + "," + "8/8/8/2B5/8/8/8/8 w - -",
			"a7" + "," + 1 + "," + "8/8/8/2B5/8/8/8/8 w - -",
			"f2" + "," + 1 + "," + "8/8/8/2B5/8/8/8/8 w - -",
			"b7" + "," + -1 + "," + "b7/8/8/8/8/8/8/8 b - -",
			"e4" + "," + -1 + "," + "b7/8/8/8/8/8/8/8 b - -",
			"h1" + "," + -1 + "," + "b7/8/8/8/8/8/8/8 b - -",
			"b4" + "," + -1 + "," + "8/8/8/2b5/8/8/8/8 b - -",
			"a7" + "," + -1 + "," + "8/8/8/2b5/8/8/8/8 b - -",
			"f2" + "," + -1 + "," + "8/8/8/2b5/8/8/8/8 b - -"
	})
	@SuppressWarnings("javadoc")
	public void isUnderAttackShouldReturnTrueWhenPositionIsAttackedByTheBishop(
			String attackedPositionAn,
			byte attackerColor,
			String fen
	) {
		Board board = new Board(fen);
		assertTrue(board.isUnderAttack(Position.of(attackedPositionAn), attackerColor));
	}
	
	@ParameterizedTest
	@CsvSource({
			"f8" + "," + 1 + "," + "8/8/3N4/2B5/8/4R3/8/8 w - -",
			"c1" + "," + 1 + "," + "8/8/3N4/2B5/8/4R3/8/8 w - -",
			"g2" + "," + 1 + "," + "8/8/3N4/2B5/8/4R3/8/8 w - -",
			"f3" + "," + 1 + "," + "8/8/8/8/8/8/6R1/7B w - -",
			"f8" + "," + -1 + "," + "8/8/3n4/2b5/8/4r3/8/8 b - -",
			"c1" + "," + -1 + "," + "8/8/3n4/2b5/8/4r3/8/8 b - -",
			"g2" + "," + -1 + "," + "8/8/3n4/2b5/8/4r3/8/8 b - -",
			"f3" + "," + -1 + "," + "8/8/8/8/8/8/6r1/7b b - -",
	
	})
	@SuppressWarnings("javadoc")
	public void isUnderAttackShouldReturnFalseWhenPositionIsNotAttackedByTheBishop(
			String attackedPositionAn,
			byte attackerColor,
			String fen
	) {
		Board board = new Board(fen);
		assertFalse(board.isUnderAttack(Position.of(attackedPositionAn), attackerColor));
	}
	
	@ParameterizedTest
	@CsvSource({
			"b5" + "," + 1 + "," + "8/8/8/8/3N4/8/8/8 w - -",
			"c6" + "," + 1 + "," + "8/8/8/8/3N4/8/8/8 w - -",
			"e6" + "," + 1 + "," + "8/8/8/8/3N4/8/8/8 w - -",
			"b2" + "," + 1 + "," + "8/8/8/8/N7/8/8/8 w - -",
			"b6" + "," + 1 + "," + "8/8/8/8/N7/8/8/8 w - -",
			"b2" + "," + 1 + "," + "8/8/8/8/N7/8/8/8 w - -",
			"f7" + "," + 1 + "," + "7N/8/8/8/8/8/8/8 w - -",
			"g6" + "," + 1 + "," + "7N/8/8/8/8/8/8/8 w - -",
			"b5" + "," + -1 + "," + "8/8/8/8/3n4/8/8/8 b - -",
			"c6" + "," + -1 + "," + "8/8/8/8/3n4/8/8/8 b - -",
			"e6" + "," + -1 + "," + "8/8/8/8/3n4/8/8/8 b - -",
			"b2" + "," + -1 + "," + "8/8/8/8/n7/8/8/8 b - -",
			"b6" + "," + -1 + "," + "8/8/8/8/n7/8/8/8 b - -",
			"b2" + "," + -1 + "," + "8/8/8/8/n7/8/8/8 b - -",
			"f7" + "," + -1 + "," + "7n/8/8/8/8/8/8/8 b - -",
			"g6" + "," + -1 + "," + "7n/8/8/8/8/8/8/8 b - -",
	})
	@SuppressWarnings("javadoc")
	public void isUnderAttackShouldReturnTrueWhenPositionIsAttackedByTheKnight(
			String attackedPositionAn,
			byte attackerColor,
			String fen
	) {
		Board board = new Board(fen);
		assertTrue(board.isUnderAttack(Position.of(attackedPositionAn), attackerColor));
	}
	
	@ParameterizedTest
	@CsvSource({
			"b6" + "," + 1 + "," + "8/8/8/8/3N4/8/8/8 w - -",
			"c7" + "," + 1 + "," + "8/8/8/8/3N4/8/8/8 w - -",
			"a1" + "," + 1 + "," + "8/8/8/8/3N4/8/8/8 w - -",
			"b3" + "," + 1 + "," + "8/8/8/8/N7/8/8/8 w - -",
			"c4" + "," + 1 + "," + "8/8/8/8/N7/8/8/8 w - -",
			"b3" + "," + 1 + "," + "8/8/8/8/N7/8/8/8 w - -",
			"f6" + "," + 1 + "," + "7N/8/8/8/8/8/8/8 w - -",
			"g7" + "," + 1 + "," + "7N/8/8/8/8/8/8/8 w - -",
			"b6" + "," + -1 + "," + "8/8/8/8/3n4/8/8/8 b - -",
			"c5" + "," + -1 + "," + "8/8/8/8/3n4/8/8/8 b - -",
			"e5" + "," + -1 + "," + "8/8/8/8/3n4/8/8/8 b - -",
			"b3" + "," + -1 + "," + "8/8/8/8/n7/8/8/8 b - -",
			"b7" + "," + -1 + "," + "8/8/8/8/n7/8/8/8 b - -",
			"b3" + "," + -1 + "," + "8/8/8/8/n7/8/8/8 b - -",
			"f8" + "," + -1 + "," + "7n/8/8/8/8/8/8/8 b - -",
			"g1" + "," + -1 + "," + "7n/8/8/8/8/8/8/8 b - -",
	})
	@SuppressWarnings("javadoc")
	public void isUnderAttackShouldReturnFalseWhenPositionIsNotAttackedByTheKnight(
			String attackedPositionAn,
			byte attackerColor,
			String fen
	) {
		Board board = new Board(fen);
		assertFalse(board.isUnderAttack(Position.of(attackedPositionAn), attackerColor));
	}
	
	@ParameterizedTest
	@CsvSource({
			"d4" + "," + 1 + "," + "8/8/8/8/8/4P3/8/8 w - -",
			"f4" + "," + 1 + "," + "8/8/8/8/8/4P3/8/8 w - -",
			"f4" + "," + 1 + "," + "8/8/8/8/8/4P3/8/8 w - -",
			"c5" + "," + -1 + "," + "8/8/3p4/8/8/8/8/8 b - -",
			"e5" + "," + -1 + "," + "8/8/3p4/8/8/8/8/8 b - -",
			"g2" + "," + -1 + "," + "8/8/8/8/8/7p/8/8 b - -"
	})
	@SuppressWarnings("javadoc")
	public void isUnderAttackShoulReturnTrueWhenPositionIsAttackedByThePawn(
			String attackedPositionAn,
			byte attackerColor,
			String fen
	) {
		Board board = new Board(fen);
		assertTrue(board.isUnderAttack(Position.of(attackedPositionAn), attackerColor));
	}
	
	@ParameterizedTest
	@CsvSource({
			"f1,R,d4,Q,g6,N,w,f4,8/8/6N1/8/3Q4/8/8/5R2 w - -",
			"c6,k,a2,b,f4,n,b,d5,8/8/2k5/8/5n2/8/b7/8 b - -",
			"c6,k,a2,b,f4,n,b,d5,8/8/2k5/8/5n2/3R4/b7/3q4 w - -",
			"c6,k,a2,b,f4,n,b,d5,8/1q6/2k5/8/5n2/3R4/b7/3q4 w - -",
			"a8,q,f1,r,h5,b,b,f3,q7/8/8/7b/8/7k/2n5/5r2 b - -",
			"e4,b,f1,r,h5,b,b,f3,q7/8/8/7b/4b3/7k/2n5/5r2 b - -",
			"d6,q,f6,q,f4,q,b,e5,1q5q/2q3q1/3q1q2/7b/3r1q2/2q4k/2nb3q/5r2 b - -",
			"c4,p,e4,p,d8,q,b,d3,3q4/8/8/8/2p1p3/8/8/8 w - -",
			"c4,p,e4,p,h3,r,b,d3,3q4/8/8/8/2ppp3/7r/8/8 w - -",
			"d4,p,h3,r,g5,q,b,e3,8/8/8/6q1/2ppp3/7r/8/8 w - -",
			"a8,Q,g6,Q,h1,Q,w,e4,Q7/8/6Q1/8/8/8/2P5/1Q5Q w - -"
	})
	@SuppressWarnings("javadoc")
	public void getAttackersShoulReturnAllThreeAttackersOfASpecificPosition(
			String attacker1,
			char attackerPiece1,
			String attacker2,
			char attackerPiece2,
			String attacker3,
			char attackerPiece3,
			char attackerColor,
			String attackedPosition,
			String fen
	) {
		Board board = new Board(fen);
		List<LocalizedPiece> attackers = board.getAttackers(
			Position.of(attackedPosition),
			BoardUtil.colorLetterToCode(attackerColor)
		);
		List<LocalizedPiece> expecteds = Arrays.asList(
			new LocalizedPiece(Position.of(attacker1), BoardUtil.pieceLetterToCode(attackerPiece1)),
			new LocalizedPiece(Position.of(attacker2), BoardUtil.pieceLetterToCode(attackerPiece2)),
			new LocalizedPiece(Position.of(attacker3), BoardUtil.pieceLetterToCode(attackerPiece3))
		);
		attackers.forEach(lp -> {
			assertTrue(expecteds.contains(lp));
		});
	}
}
