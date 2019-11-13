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
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.welyab.anjabachen.ChessException;

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
	public void isEmptyShouldReturnTrueWhenSquareIsEmpty() {
		assertTrue(new Board().isEmpty(Position.of(4, 4)));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void isEmptyShouldReturnFalseWhenSquareIsNotEmpty() {
		assertFalse(new Board().isEmpty(Position.of(1, 3)));
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			MovementUtil.BLACK_ROOK + "," + 0 + "," + 0,
			MovementUtil.BLACK_QUEEN + "," + 0 + "," + 3,
			MovementUtil.BLACK_BISHOP + "," + 0 + "," + 2,
			MovementUtil.WHITE_KING + "," + 7 + "," + 4,
			MovementUtil.WHITE_PAWN + "," + 6 + "," + 2,
			MovementUtil.WHITE_KNIGHT + "," + 7 + "," + 6,
			MovementUtil.WHITE_ROOK + "," + 7 + "," + 0,
		}
	)
	@SuppressWarnings("javadoc")
	public void getPieceShouldReturnProperlyPiece(byte pieceCode, byte row, byte column) {
		assertEquals(pieceCode, new Board().getSquareValue(Position.of(row, column)));
	}
	
	@ParameterizedTest
	@ValueSource(
		strings = {
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
		}
	)
	@SuppressWarnings("javadoc")
	public void aBoardBuiltFromFenShouldProduceSameFenString(String fen) {
		assertEquals(fen, new Board(fen).getFen());
	}
	
	@ParameterizedTest
	@CsvSource(
		{
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
		}
	)
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
		BoardState state = new Board(fen).getState();
		assertEquals(expectedSideToMove, state.getSideToMove());
		assertEquals(expectedWhiteKingCastling, MovementUtil.isWhiteKingCaslting(state.getCastlingFlags()));
		assertEquals(expectedWhiteQueenCastling, MovementUtil.isWhiteQueenCaslting(state.getCastlingFlags()));
		assertEquals(expectedBlackeKingCastling, MovementUtil.isBlackKingCaslting(state.getCastlingFlags()));
		assertEquals(expectedBlackQueenCastling, MovementUtil.isBlackQueenCaslting(state.getCastlingFlags()));
		assertEquals(
			expectedEnPassantTarget,
			Optional.ofNullable(state.getEnPassantTargetSquare())
				.map(Position::getNotation)
				.orElse(null)
		);
		assertEquals(expectedHalfMoveCounter, state.getHalfMoveClock());
		assertEquals(expectedFullMoveCounter, state.getFullMoveCounter());
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			"a7" + "," + 1 + "," + "K7/8/8/8/8/8/8/8 w - -",
			"c4" + "," + 1 + "," + "8/8/8/3K4/8/8/8/8 w - -",
			"e1" + "," + 1 + "," + "8/8/8/8/8/8/8/5K2 w - -",
			"a7" + "," + -1 + "," + "k7/8/8/8/8/8/8/8 b - -",
			"c4" + "," + -1 + "," + "8/8/8/3k4/8/8/8/8 b - -",
			"e1" + "," + -1 + "," + "8/8/8/8/8/8/8/5k2 b - -"
		}
	)
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
	@CsvSource(
		{
			"h1" + "," + 1 + "," + "K7/8/8/8/8/8/8/8 w - -",
			"c3" + "," + 1 + "," + "8/8/8/3K4/8/8/8/8 w - -",
			"f3" + "," + 1 + "," + "8/8/8/8/8/8/8/5K2 w - -",
			"h1" + "," + -1 + "," + "k7/8/8/8/8/8/8/8 b - -",
			"c3" + "," + -1 + "," + "8/8/8/3k4/8/8/8/8 b - -",
			"f3" + "," + -1 + "," + "8/8/8/8/8/8/8/5k2 b - -"
		}
	)
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
	@CsvSource(
		{
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
		}
	)
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
	@CsvSource(
		{
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
		}
	)
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
	@CsvSource(
		{
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
		}
	)
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
	@CsvSource(
		{
			"d7" + "," + 1 + "," + "8/8/3B4/8/3R4/3N4/8/8 w - -",
			"d1" + "," + 1 + "," + "8/8/3B4/8/3R4/3N4/8/8 w - -",
			"f6" + "," + 1 + "," + "8/8/3B4/8/3R4/3N4/8/8 w - -",
			"g2" + "," + 1 + "," + "8/8/3B4/8/3R4/3N4/8/8 w - -",
			"h1" + "," + -1 + "," + "8/8/7b/8/7r/7n/8/8 w - -",
			"h7" + "," + -1 + "," + "8/8/7b/8/7r/7n/8/8 w - -",
			"g3" + "," + -1 + "," + "8/8/7b/8/7r/7n/8/8 w - -",
			"e1" + "," + -1 + "," + "8/8/7b/8/7r/7n/8/8 w - -"
		}
	)
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
	@CsvSource(
		{
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
		}
	)
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
	@CsvSource(
		{
			"f8" + "," + 1 + "," + "8/8/3N4/2B5/8/4R3/8/8 w - -",
			"c1" + "," + 1 + "," + "8/8/3N4/2B5/8/4R3/8/8 w - -",
			"g2" + "," + 1 + "," + "8/8/3N4/2B5/8/4R3/8/8 w - -",
			"f3" + "," + 1 + "," + "8/8/8/8/8/8/6R1/7B w - -",
			"f8" + "," + -1 + "," + "8/8/3n4/2b5/8/4r3/8/8 b - -",
			"c1" + "," + -1 + "," + "8/8/3n4/2b5/8/4r3/8/8 b - -",
			"g2" + "," + -1 + "," + "8/8/3n4/2b5/8/4r3/8/8 b - -",
			"f3" + "," + -1 + "," + "8/8/8/8/8/8/6r1/7b b - -",
		
		}
	)
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
	@CsvSource(
		{
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
		}
	)
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
	@CsvSource(
		{
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
		}
	)
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
	@CsvSource(
		{
			"d4" + "," + 1 + "," + "8/8/8/8/8/4P3/8/8 w - -",
			"f4" + "," + 1 + "," + "8/8/8/8/8/4P3/8/8 w - -",
			"f4" + "," + 1 + "," + "8/8/8/8/8/4P3/8/8 w - -",
			"c5" + "," + -1 + "," + "8/8/3p4/8/8/8/8/8 b - -",
			"e5" + "," + -1 + "," + "8/8/3p4/8/8/8/8/8 b - -",
			"g2" + "," + -1 + "," + "8/8/8/8/8/7p/8/8 b - -"
		}
	)
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
	@CsvSource(
		{
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
		}
	)
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
			MovementUtil.colorLetterToCode(attackerColor)
		);
		List<LocalizedPiece> expecteds = Arrays.asList(
			new LocalizedPiece(Position.of(attacker1), MovementUtil.pieceLetterToCode(attackerPiece1)),
			new LocalizedPiece(Position.of(attacker2), MovementUtil.pieceLetterToCode(attackerPiece2)),
			new LocalizedPiece(Position.of(attacker3), MovementUtil.pieceLetterToCode(attackerPiece3))
		);
		attackers.forEach(lp -> {
			assertTrue(expecteds.contains(lp));
		});
	}
	
	@ParameterizedTest
	@MethodSource("kingMovementTestCaseProvider")
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateProperlyMovementsForKing(MovementTestCase testCase) {
		movementTest(testCase);
	}
	
	@ParameterizedTest
	@MethodSource("castlingMovementTestCaseProvider")
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateProperlyMovementsForKingCastling(CastlingMovementTestCase testCase) {
		String expectedTargets = testCase.expectedTargets
			.stream()
			.map(
				m -> String.format(
					"[king=%s, rook=%s]",
					m.kingTarget,
					m.rookTarget
				)
			)
			.sorted()
			.reduce((s1, s2) -> String.format("%s, %s", s1, s2))
			.orElse("");
		String foundTargets = new Board(testCase.fen)
			.getMovements(Position.of(testCase.kingPosition))
			.streamTargets()
			.filter(m -> MovementUtil.isCastling(m.getFlags()))
			.map(m -> {
				return String.format(
					"[king=%s, rook=%s]",
					m.getPosition().getNotation(),
					Position.of(
						m.getPosition().row,
						MovementUtil.getCastlingRookTargetColumn(m.getPosition().column)
					).getNotation()
				);
			})
			.sorted()
			.reduce((s1, s2) -> String.format("%s, %s", s1, s2))
			.orElse("");
		assertEquals(expectedTargets, foundTargets);
	}
	
	@ParameterizedTest
	@MethodSource("queenMovementTestCaseProvider")
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateProperlyMovementsForQueen(MovementTestCase testCase) {
		movementTest(testCase);
	}
	
	@ParameterizedTest
	@MethodSource("rookMovementTestCaseProvider")
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateProperlyMovementsForRook(MovementTestCase testCase) {
		movementTest(testCase);
	}
	
	@ParameterizedTest
	@MethodSource("bishopMovementTestCaseProvider")
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateProperlyMovementsForBishop(MovementTestCase testCase) {
		movementTest(testCase);
	}
	
	@ParameterizedTest
	@MethodSource("knightMovementTestCaseProvider")
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateProperlyMovementsForKnight(MovementTestCase testCase) {
		movementTest(testCase);
	}
	
	@ParameterizedTest
	@MethodSource("pawnMovementTestCaseProvider")
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateProperlyMovementsForPawn(MovementTestCase testCase) {
		movementTest(testCase);
	}
	
	@ParameterizedTest
	@MethodSource("pawnPromotionMovementTestCaseProvider")
	@SuppressWarnings("javadoc")
	public void getMovementsShouldGenerateProperlyMovementsForPromotingPawn(PawnPromotionMovementTestCase testCase) {
		String expectedTarges = testCase.expectedTargets
			.stream()
			.map(l -> l.getPosition().getNotation() + "=" + MovementUtil.pieceCodeToLetter(l.getPieceCode()))
			.sorted()
			.reduce((l1, l2) -> l1 + ", " + l2)
			.orElse("");
		String foundTargets = new Board(testCase.fen)
			.getMovements(Position.of(testCase.pawnPosition))
			.streamTargets()
			.map(l -> l.getPosition().getNotation() + "=" + MovementUtil.pieceCodeToLetter(l.getPieceCode()))
			.sorted()
			.reduce((l1, l2) -> l1 + ", " + l2)
			.orElse("");
		assertEquals(
			expectedTarges,
			foundTargets
		);
	}
	
	@SuppressWarnings("javadoc")
	private void movementTest(MovementTestCase testCase) {
		String foundTargets = new Board(testCase.fen)
			.getMovements(Position.of(testCase.pawnPosition))
			.streamTargets()
			.map(MovementTarget::getPosition)
			.map(Position::getNotation)
			.sorted()
			.reduce((r1, r2) -> String.format("%s, %s", r1, r2))
			.orElse("");
		String expected = testCase.expectedTargets.stream()
			.sorted()
			.reduce((r1, r2) -> String.format("%s, %s", r1, r2))
			.orElse("");
		assertEquals(
			expected,
			foundTargets
		);
	}
	
	@ParameterizedTest
	@MethodSource("movementsWhiteBlackTestCaseProvider")
	@SuppressWarnings("javadoc")
	public void testGetMovementsWBReturnsMovementsForBothColors(MovementWBTestCase testCase) {
		List<String> foundTargets = new Board(testCase.fen)
			.getMovementsWB()
			.streamTargets()
			.map(t -> t.getPosition().getNotation())
			.sorted()
			.collect(Collectors.toList());
		List<String> expected = testCase.targetPositions
			.stream()
			.sorted()
			.collect(Collectors.toList());
		assertEquals(expected, foundTargets);
	}
	
	@SuppressWarnings("javadoc")
	public static class MovementWBTestCase {
		
		private final String fen;
		
		private final List<String> targetPositions;
		
		public MovementWBTestCase(String fen, List<String> targetPositions) {
			this.fen = fen;
			this.targetPositions = targetPositions;
		}
	}
	
	@SuppressWarnings("javadoc")
	public static List<MovementWBTestCase> movementsWhiteBlackTestCaseProvider() {
		return List.of(
			new MovementWBTestCase(
				"k7/8/8/8/8/8/8/7K w - -",
				List.of("a7", "b7", "b8", "g1", "g2", "h2")
			),
			new MovementWBTestCase(
				"k7/8/3N4/8/2K5/8/5n2/8 w - -",
				List.of(
					"a7", "b8", // black king
					"b5", "c5", "d5", "b3", "c3", "b4", "d4", // white king
					"e4", "g4", "d3", "h3", "d1", "h1", // black horse
					"c8", "e8", "b7", "f7", "b5", "f5", "e4" // white horse
				)
			)
		);
	}
	
	@SuppressWarnings("javadoc")
	public static List<MovementTestCase> kingMovementTestCaseProvider() {
		return Arrays.asList(
			new MovementTestCase(
				"8/8/4k3/8/8/8/8/8 b - -",
				"e6",
				Set.of("d7", "e7", "f7", "d6", "f6", "d5", "e5", "f5")
			),
			new MovementTestCase(
				"K7/8/8/8/8/8/8/8 b - -",
				"a8",
				Set.of("a7", "b7", "b8")
			),
			new MovementTestCase(
				"2R5/8/8/8/3k4/8/8/4R3 b - -",
				"d4",
				Set.of("d5", "d3")
			),
			new MovementTestCase(
				"2R5/8/8/8/3k4/R7/8/4R3 b - -",
				"d4",
				Set.of("d5")
			),
			new MovementTestCase(
				"8/2NN4/8/8/3k4/R7/8/4R3 b - -",
				"d4",
				Set.of("c4")
			),
			new MovementTestCase(
				"8/4k3/8/2N5/B7/8/5r2/3R1R2 w - -",
				"e7",
				Set.of("f6", "f7", "f8")
			)
		);
	}
	
	@SuppressWarnings("javadoc")
	public static List<MovementTestCase> queenMovementTestCaseProvider() {
		return List.of(
			new MovementTestCase(
				"8/8/8/8/8/2Q5/8/8 w - -",
				"c3",
				Set.of(
					"a1", "a3", "a5", "b2", "b3", "b4", "c1", "c2", "c4", "c5", "c6", "c7", "c8",
					"d2", "d3", "d4", "e1", "e3", "e5", "f3", "f6", "g3", "g7", "h3", "h8"
				)
			),
			new MovementTestCase(
				"Q7/8/8/8/8/8/8/8 w - -",
				"a8",
				Set.of(
					"a1", "a2", "a3", "a4", "a5", "a6", "a7", "b8", "c8", "d8", "e8",
					"f8", "g8", "h8", "b7", "c6", "d5", "e4", "f3", "g2", "h1"
				)
			),
			new MovementTestCase(
				"N1b5/8/2Q1r3/1n6/4P3/8/8/8 w - -",
				"c6",
				Set.of("a6", "b5", "b6", "b7", "c1", "c2", "c3", "c4", "c5", "c7", "c8", "d5", "d6", "d7", "e6", "e8")
			),
			new MovementTestCase(
				"8/8/k2q3R/8/8/8/8/8 w - -",
				"d6",
				Set.of("b6", "c6", "e6", "f6", "g6", "h6")
			),
			new MovementTestCase(
				"8/2k5/8/2Q5/8/8/2q5/8 w - -",
				"c2",
				Set.of("c5")
			),
			new MovementTestCase(
				"4q3/8/k7/8/8/8/8/5B2 w - -",
				"e8",
				Set.of("e2", "b5")
			),
			new MovementTestCase(
				"4q3/1k6/8/8/4B3/8/8/8 w - -",
				"e8",
				Set.of("c6", "e4")
			),
			new MovementTestCase(
				"5q2/1k6/8/2N5/8/8/8/8 w - -",
				"f8",
				Set.of("c5")
			)
		);
	}
	
	@SuppressWarnings("javadoc")
	public static List<MovementTestCase> rookMovementTestCaseProvider() {
		return List.of(
			new MovementTestCase(
				"8/8/8/8/8/5r2/8/8 w - -",
				"f3",
				Set.of("a3", "b3", "c3", "d3", "e3", "g3", "h3", "f1", "f2", "f4", "f5", "f6", "f7", "f8")
			),
			new MovementTestCase(
				"7r/8/8/8/8/8/8/8 w - -",
				"h8",
				Set.of("a8", "b8", "c8", "d8", "e8", "f8", "g8", "h1", "h2", "h3", "h4", "h5", "h6", "h7")
			),
			new MovementTestCase(
				"8/8/4B3/2N1r2b/8/8/8/8 w - -",
				"e5",
				Set.of("c5", "d5", "f5", "g5", "e6", "e4", "e3", "e2", "e1")
			),
			new MovementTestCase(
				"8/2k5/8/8/8/8/2r4B/8 w - -",
				"c2",
				Set.of("h2")
			),
			new MovementTestCase(
				"8/2k5/8/2r5/8/8/8/2Q5 w - -",
				"c5",
				Set.of("c6", "c4", "c3", "c2", "c1")
			)
		);
	}
	
	@SuppressWarnings("javadoc")
	public static List<MovementTestCase> bishopMovementTestCaseProvider() {
		return List.of(
			new MovementTestCase(
				"8/8/8/2b5/8/8/8/8 w - -",
				"c5",
				Set.of("a7", "b6", "d4", "e3", "f2", "g1", "a3", "b4", "d6", "e7", "f8")
			),
			new MovementTestCase(
				"8/8/8/8/8/8/8/b7 w - -",
				"a1",
				Set.of("b2", "c3", "d4", "e5", "f6", "g7", "h8")
			),
			new MovementTestCase(
				"8/8/1b3k2/8/8/8/5R2/8 w - -",
				"b6",
				Set.of("f2")
			),
			new MovementTestCase(
				"8/8/2b2k2/8/8/8/8/5R2 w - -",
				"c6",
				Set.of("f3")
			),
			new MovementTestCase(
				"8/8/6k1/8/8/3b4/8/1B6 w - -",
				"d3",
				Set.of("b1", "c2", "e4", "f5")
			),
			new MovementTestCase(
				"8/8/6k1/8/4B3/8/2b5/8 w - -",
				"c2",
				Set.of("e4")
			)
		);
	}
	
	@SuppressWarnings("javadoc")
	public static List<MovementTestCase> knightMovementTestCaseProvider() {
		return List.of(
			new MovementTestCase(
				"8/8/8/4N3/8/8/8/8 w - -",
				"e5",
				Set.of("d3", "f3", "d7", "f7", "c6", "c4", "g6", "g4")
			),
			new MovementTestCase(
				"N7/8/8/8/8/8/8/8 w - -",
				"a8",
				Set.of("b6", "c7")
			),
			new MovementTestCase(
				"3K4/8/8/8/5N2/8/8/3r4 w - -",
				"f4",
				Set.of("d5", "d3")
			),
			new MovementTestCase(
				"3K4/8/8/8/3r1N2/8/8/8 w - -",
				"f4",
				Set.of("d5")
			)
		);
	}
	
	@SuppressWarnings("javadoc")
	public static List<MovementTestCase> pawnMovementTestCaseProvider() {
		return List.of(
			new MovementTestCase(
				"8/8/8/8/4P3/8/8/8 w - -",
				"e4",
				Set.of("e5")
			),
			new MovementTestCase(
				"8/8/8/4Q3/4P3/8/8/8 w - -",
				"e4",
				Set.of("")
			),
			new MovementTestCase(
				"8/8/8/8/8/8/2P5/8 w - -",
				"c2",
				Set.of("c3", "c4")
			),
			new MovementTestCase(
				"8/8/8/8/2N5/8/2P5/8 w - -",
				"c2",
				Set.of("c3")
			),
			new MovementTestCase(
				"8/8/8/8/8/1N1n4/2P5/8 w - -",
				"c2",
				Set.of("c3", "c4", "d3")
			),
			new MovementTestCase(
				"8/8/8/8/3pP3/1N1n4/8/8 w - e3",
				"d4",
				Set.of("e3")
			),
			new MovementTestCase(
				"8/8/8/8/3pP3/1N1n4/8/8 w - -",
				"d4",
				Set.of("")
			),
			new MovementTestCase(
				"8/6k1/8/8/3pP3/1N1n4/1B6/8 w - -",
				"d4",
				Set.of("")
			),
			new MovementTestCase(
				"8/3p4/2Q1P3/8/8/8/8/8 w - -",
				"d7",
				Set.of("c6", "e6", "d6", "d5")
			),
			new MovementTestCase(
				"8/8/8/1K2pP1r/8/8/8/8 w - e6",
				"f5",
				Set.of("f6")
			)
		);
	}
	
	@SuppressWarnings("javadoc")
	public static List<PawnPromotionMovementTestCase> pawnPromotionMovementTestCaseProvider() {
		return List.of(
			new PawnPromotionMovementTestCase(
				"8/4P3/8/8/8/8/8/8 b - -",
				"e7",
				Set.of(
					LP.of("e8", MovementUtil.WHITE_QUEEN),
					LP.of("e8", MovementUtil.WHITE_ROOK),
					LP.of("e8", MovementUtil.WHITE_BISHOP),
					LP.of("e8", MovementUtil.WHITE_KNIGHT)
				)
			),
			new PawnPromotionMovementTestCase(
				"3r4/4P3/8/8/8/8/8/8 w - -",
				"e7",
				Set.of(
					LP.of("e8", MovementUtil.WHITE_QUEEN),
					LP.of("e8", MovementUtil.WHITE_ROOK),
					LP.of("e8", MovementUtil.WHITE_BISHOP),
					LP.of("e8", MovementUtil.WHITE_KNIGHT),
					LP.of("d8", MovementUtil.WHITE_QUEEN),
					LP.of("d8", MovementUtil.WHITE_ROOK),
					LP.of("d8", MovementUtil.WHITE_BISHOP),
					LP.of("d8", MovementUtil.WHITE_KNIGHT)
				)
			),
			new PawnPromotionMovementTestCase(
				"3r1n2/4P3/8/8/8/8/8/8 w - -",
				"e7",
				Set.of(
					LP.of("e8", MovementUtil.WHITE_QUEEN),
					LP.of("e8", MovementUtil.WHITE_ROOK),
					LP.of("e8", MovementUtil.WHITE_BISHOP),
					LP.of("e8", MovementUtil.WHITE_KNIGHT),
					LP.of("d8", MovementUtil.WHITE_QUEEN),
					LP.of("d8", MovementUtil.WHITE_ROOK),
					LP.of("d8", MovementUtil.WHITE_BISHOP),
					LP.of("d8", MovementUtil.WHITE_KNIGHT),
					LP.of("f8", MovementUtil.WHITE_QUEEN),
					LP.of("f8", MovementUtil.WHITE_ROOK),
					LP.of("f8", MovementUtil.WHITE_BISHOP),
					LP.of("f8", MovementUtil.WHITE_KNIGHT)
				)
			),
			new PawnPromotionMovementTestCase(
				"3rkn2/4P3/8/8/8/8/8/8 w - -",
				"e7",
				Set.of(
					LP.of("d8", MovementUtil.WHITE_QUEEN),
					LP.of("d8", MovementUtil.WHITE_ROOK),
					LP.of("d8", MovementUtil.WHITE_BISHOP),
					LP.of("d8", MovementUtil.WHITE_KNIGHT),
					LP.of("f8", MovementUtil.WHITE_QUEEN),
					LP.of("f8", MovementUtil.WHITE_ROOK),
					LP.of("f8", MovementUtil.WHITE_BISHOP),
					LP.of("f8", MovementUtil.WHITE_KNIGHT)
				)
			),
			new PawnPromotionMovementTestCase(
				"3rkn2/r3P1K1/8/8/8/8/8/8 w - -",
				"e7",
				Set.of()
			)
		);
	}
	
	@SuppressWarnings("javadoc")
	public static List<CastlingMovementTestCase> castlingMovementTestCaseProvider() {
		return Arrays.asList(
			new CastlingMovementTestCase(
				"r3k2r/pbppqppp/1pn2n2/1Bb1p3/4P3/1PN2N2/PBPPQPPP/R3K2R w KQkq -",
				"e1",
				Set.of(
					CM.of("c1", "d1"),
					CM.of("g1", "f1")
				)
			),
			new CastlingMovementTestCase(
				"r3k2r/pbppqppp/1pn2n2/1Bb1p3/4P3/1PN2N2/PBPPQPPP/R3K2R w Q -",
				"e1",
				Set.of(
					CM.of("c1", "d1")
				)
			),
			new CastlingMovementTestCase(
				"r3k2r/pbppqppp/1pn2n2/1Bb1p3/4P3/1PN2N2/PBPPQPPP/R3K2R w Kkq -",
				"e1",
				Set.of(
					CM.of("g1", "f1")
				)
			),
			new CastlingMovementTestCase(
				"r3k2r/pbppqppp/1pn2n2/1Bb1p3/4P3/1PN2N2/PBPPQPPP/R3K2R w - -",
				"e1",
				Set.of()
			),
			new CastlingMovementTestCase(
				"r3k2r/pbppqppp/1pn2n2/1Bb1p3/4P3/1PN2N2/PBPPQPPP/R3K2R b KQkq -",
				"e8",
				Set.of(
					CM.of("c8", "d8"),
					CM.of("g8", "f8")
				)
			),
			new CastlingMovementTestCase(
				"r3k2r/pbppqppp/1pn2n2/1Bb1p3/4P3/1PN2N2/PBPPQPPP/R3K2R b q -",
				"e8",
				Set.of(
					CM.of("c8", "d8")
				)
			),
			new CastlingMovementTestCase(
				"r3k2r/pbppqppp/1pn2nN1/1Bb1p3/4P3/1PN5/PBPPQPPP/R3K2R b KQkq -",
				"e8",
				Set.of(
					CM.of("c8", "d8")
				)
			),
			new CastlingMovementTestCase(
				"1r6/8/8/8/8/8/8/R3K2R w KQkq -",
				"e1",
				Set.of(
					CM.of("c1", "d1"),
					CM.of("g1", "f1")
				)
			),
			new CastlingMovementTestCase(
				"8/8/1q6/8/8/8/8/R3K2R w KQkq -",
				"e1",
				Set.of(
					CM.of("c1", "d1")
				)
			),
			new CastlingMovementTestCase(
				"8/8/1q6/8/8/8/8/R3K2R w KQkq -",
				"e1",
				Set.of(
					CM.of("c1", "d1")
				)
			),
			new CastlingMovementTestCase(
				"4q3/8/8/8/8/8/8/R3K2R w KQkq -",
				"e1",
				Set.of()
			),
			new CastlingMovementTestCase(
				"8/8/b7/8/8/8/8/R3K2R w KQkq -",
				"e1",
				Set.of(CM.of("c1", "d1"))
			),
			new CastlingMovementTestCase(
				"rn2k2r/8/8/8/8/8/8/8 w KQkq -",
				"e8",
				Set.of(CM.of("g8", "f8"))
			)
		);
	}
	
	@SuppressWarnings("javadoc")
	public static final class LP extends LocalizedPiece {
		
		public LP(String position, int pieceCode) {
			super(Position.of(position), pieceCode);
		}
		
		public static LP of(String position, int pieceCode) {
			return new LP(position, pieceCode);
		}
	}
	
	@SuppressWarnings("javadoc")
	public static class CM /* castling movement */ {
		
		private final String kingTarget;
		
		private final String rookTarget;
		
		private CM(String kingTarget, String rookTarget) {
			super();
			this.kingTarget = kingTarget;
			this.rookTarget = rookTarget;
		}
		
		private static CM of(String kingTarget, String rookTarget) {
			return new CM(kingTarget, rookTarget);
		}
	}
	
	@SuppressWarnings("javadoc")
	public static final class CastlingMovementTestCase {
		
		private final String fen;
		
		private final String kingPosition;
		
		private final Set<CM> expectedTargets;
		
		private CastlingMovementTestCase(String fen, String kingPosition, Set<CM> expectedTargets) {
			this.fen = fen;
			this.kingPosition = kingPosition;
			this.expectedTargets = expectedTargets;
		}
	}
	
	@SuppressWarnings("javadoc")
	public static final class PawnPromotionMovementTestCase {
		
		private final String fen;
		
		private final String pawnPosition;
		
		private final Set<LP> expectedTargets;
		
		private PawnPromotionMovementTestCase(
			String fen,
			String pawnPosition,
			Set<LP> expectedTargets
		) {
			this.fen = fen;
			this.pawnPosition = pawnPosition;
			this.expectedTargets = expectedTargets;
		}
	}
	
	@SuppressWarnings("javadoc")
	public static final class MovementTestCase {
		
		private final String fen;
		
		private final String pawnPosition;
		
		private final Set<String> expectedTargets;
		
		private MovementTestCase(
			String fen,
			String pawnPosition,
			Set<String> expectedTargets
		) {
			this.fen = fen;
			this.pawnPosition = pawnPosition;
			this.expectedTargets = expectedTargets;
		}
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testIfCreateBoardWithTwoKingsOfSameColorThrowsChessException() {
		assertThrows(
			ChessException.class,
			() -> {
				new Board("rnbqkbnr/pppppppp/8/8/2K5/8/PPPPPPPP/RNBQKBNR w KQkq -");
			},
			"Create a new board with FEN referencing two kings should throw ChessException"
		);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testGetMovementsForEmptySquareShouldThrownEmptySquareException() {
		assertThrows(
			EmptySquareException.class,
			() -> {
				new Board().getMovements(Position.of(4, 4));
			},
			"getMovements should throw EmptySquareException during attempt to generated a non piece (empty square)"
		);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testEqualsShouldReturnTrueIfTwoBoardsAreEquals() {
		Board board1 = new Board("r3k2r/pp3p2/2pQ1Pnp/2P1nqp1/8/1B6/PP3PPP/3RR1K1 w - - 1 0");
		Board board2 = new Board("r3k2r/pp3p2/2pQ1Pnp/2P1nqp1/8/1B6/PP3PPP/3RR1K1 w - - 1 0");
		assertTrue(board1.equals(board2));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testEqualsShouldReturnFalseIfPieceDispositionIsEqualsButStateIsDifferent() {
		Board board1 = new Board("r3k2r/pp3p2/2pQ1Pnp/2P1nqp1/8/1B6/PP3PPP/3RR1K1 w - - 1 0");
		Board board2 = new Board("r3k2r/pp3p2/2pQ1Pnp/2P1nqp1/8/1B6/PP3PPP/3RR1K1 b - - 1 0");
		assertFalse(board1.equals(board2));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testEqualsShouldReturnFalseIfTwoBoardsAreDifferent() {
		Board board1 = new Board("r3k2r/pp3p2/2pQ1Pnp/2P1nqp1/8/1B6/PP3PPP/3RR1K1 w - - 1 0");
		Board board2 = new Board("R7/8/2p4k/3pqr1p/6pK/3P4/P5B1/6Q1 b - - 0 1");
		assertFalse(board1.equals(board2));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testEqualsShouldReturnFalseIfComparedBoardIsNull() {
		assertFalse(new Board().equals(null));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testCopyShouldCreateExactlyEqualsBoard() {
		Board board = new Board("r3k2r/pp3p2/2pQ1Pnp/2P1nqp1/8/1B6/PP3PPP/3RR1K1 w - - 1 0");
		Board copy = board.copy();
		assertEquals(board.toString(), copy.toString());
		assertEquals(board.getState(), copy.getState());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testHashCodeForTwoIdenticalBoardsShouldBeTheSame() {
		Board board1 = new Board("r3k2r/pp3p2/2pQ1Pnp/2P1nqp1/8/1B6/PP3PPP/3RR1K1 w - - 1 0");
		Board board2 = new Board("r3k2r/pp3p2/2pQ1Pnp/2P1nqp1/8/1B6/PP3PPP/3RR1K1 w - - 1 0");
		assertEquals(board1.hashCode(), board2.hashCode());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testToStringGeneratesProperBoardStringRepresentation() {
		String expected = """
					┌───┬───┬───┬───┬───┬───┬───┬───┐
					│   │   │   │   │ r │   │ k │   │
					├───┼───┼───┼───┼───┼───┼───┼───┤
					│ p │ r │   │   │   │ b │ p │   │
					├───┼───┼───┼───┼───┼───┼───┼───┤
					│   │   │   │ B │   │   │   │ p │
					├───┼───┼───┼───┼───┼───┼───┼───┤
					│   │   │ p │ N │   │   │   │   │
					├───┼───┼───┼───┼───┼───┼───┼───┤
					│   │   │   │   │   │   │ p │   │
					├───┼───┼───┼───┼───┼───┼───┼───┤
					│   │   │ N │   │   │   │   │   │
					├───┼───┼───┼───┼───┼───┼───┼───┤
					│ P │   │ P │   │   │ P │ P │ P │
					├───┼───┼───┼───┼───┼───┼───┼───┤
					│   │   │   │   │   │ R │ K │   │
					└───┴───┴───┴───┴───┴───┴───┴───┘""";
		assertEquals(
			expected,
			new Board("4r1k1/pr3bp1/3B3p/2pN4/6p1/2N5/P1P2PPP/5RK1 w - - 0 25").toString()
				.replaceAll("\r\n", "\n")
		);
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			"f6,8/4N3/5k2/8/8/8/8/8 w - -",
			"b7,8/1Q6/8/8/4n3/8/8/8 w - -",
			"d7,8/3r4/8/8/3Q4/8/8/8 w - -",
			"f2,8/q7/8/8/8/8/5B2/8 w - -",
			"e4,8/8/8/8/4n3/8/5B2/8 w - -",
			"d3,8/8/8/8/4n3/3P4/8/8 w - -"
		}
	)
	@SuppressWarnings("javadoc")
	public void testCaptureFlagIsExtracted(String piecePosition, String fen) {
		Board board = new Board(fen);
		assertTrue(
			board.getMovements(Position.of(piecePosition))
				.streamTargets()
				.anyMatch(t -> MovementUtil.isCapture(t.getFlags()))
		);
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			"g5,8/8/8/5pP1/8/8/8/8 w - f6",
			"c4,8/8/8/8/2pP4/8/8/8 w - d3"
		}
	)
	@SuppressWarnings("javadoc")
	public void testEnPassantFlagIsExtracted(String piecePosition, String fen) {
		Board board = new Board(fen);
		assertTrue(
			board.getMovements(Position.of(piecePosition))
				.streamTargets()
				.anyMatch(t -> MovementUtil.isEnPassant(t.getFlags()))
		);
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			"e1,r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - ",
			"e8,r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R b KQkq - "
		}
	)
	@SuppressWarnings("javadoc")
	public void testCastlingFlagIsExtracted(String piecePosition, String fen) {
		Board board = new Board(fen);
		assertTrue(
			board.getMovements(Position.of(piecePosition))
				.streamTargets()
				.anyMatch(t -> MovementUtil.isCastling(t.getFlags()))
		);
	}
	
	@ParameterizedTest
	@CsvSource(
		{
			"e7,8/4P3/8/8/8/8/8/8 w - -",
			"b2,8/8/8/8/8/8/1p6/8 w - -",
			"b2,8/8/8/8/8/8/1p6/1rQ5 w - -",
			"e7,4kn2/4P3/8/8/8/8/8/8 w - -"
		}
	)
	@SuppressWarnings("javadoc")
	public void testPromotionFlagIsExtracted(String piecePosition, String fen) {
		Board board = new Board(fen);
		assertTrue(
			board.getMovements(Position.of(piecePosition))
				.streamTargets()
				.anyMatch(t -> MovementUtil.isPromotion(t.getFlags()))
		);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testSideToMoveChangeToBlackAfterWhiteMovement() {
		Board board = new Board("4r3/p1r2p1k/1p2pPpp/2qpP3/3R2P1/1PPQ3R/1P5P/7K w - - 1 0");
		board.moveRandom();
		byte sideToMove = board.getSideToMove();
		assertEquals(MovementUtil.BLACK, sideToMove);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testSideToMoveChangeToWhiteAfterBlackMovement() {
		Board board = new Board("2q2rk1/4r1bp/bpQp2p1/p2Pp3/P3P2P/1NP1B1K1/1P6/R2R4 b - - 0 1");
		board.moveRandom();
		byte sideToMove = board.getSideToMove();
		assertEquals(MovementUtil.WHITE, sideToMove);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testWhiteKingCastlingFlagIsSetIfAvalable() {
		Board board = new Board("rnbqkbnr/pppppppp/8/8/8/3B1N2/PPPPPPPP/RNBQK2R w K -");
		BoardState state = board.getState();
		
		assertTrue(state.isKingSideCastlingAvaiable(MovementUtil.WHITE));
		assertFalse(state.isQueenSideCastlingAvaiable(MovementUtil.WHITE));
		assertFalse(state.isKingSideCastlingAvaiable(MovementUtil.BLACK));
		assertFalse(state.isQueenSideCastlingAvaiable(MovementUtil.BLACK));
		
		assertEquals(
			MovementUtil.WHITE_ROOK,
			board.getSquareValue(state.getKingRookPosition(MovementUtil.WHITE))
		);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testWhiteQueenCastlingFlagIsSetIfAvalable() {
		Board board = new Board("rnbqkbnr/pppppppp/8/8/8/3B1N2/PPPPPPPP/RNBQK2R w Q -");
		BoardState state = board.getState();
		
		assertFalse(state.isKingSideCastlingAvaiable(MovementUtil.WHITE));
		assertTrue(state.isQueenSideCastlingAvaiable(MovementUtil.WHITE));
		assertFalse(state.isKingSideCastlingAvaiable(MovementUtil.BLACK));
		assertFalse(state.isQueenSideCastlingAvaiable(MovementUtil.BLACK));
		
		assertEquals(
			MovementUtil.WHITE_ROOK,
			board.getSquareValue(state.getQueenRookPosition(MovementUtil.WHITE))
		);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testBlackKingCastlingFlagIsSetIfAvalable() {
		Board board = new Board("rnbqkbnr/pppppppp/8/8/8/3B1N2/PPPPPPPP/RNBQK2R b k -");
		BoardState state = board.getState();
		
		assertFalse(state.isKingSideCastlingAvaiable(MovementUtil.WHITE));
		assertFalse(state.isQueenSideCastlingAvaiable(MovementUtil.WHITE));
		assertTrue(state.isKingSideCastlingAvaiable(MovementUtil.BLACK));
		assertFalse(state.isQueenSideCastlingAvaiable(MovementUtil.BLACK));
		
		assertEquals(
			MovementUtil.BLACK_ROOK,
			board.getSquareValue(state.getKingRookPosition(MovementUtil.BLACK))
		);
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void testBlackQueeCastlingFlagIsSetIfAvalable() {
		Board board = new Board("rnbqkbnr/pppppppp/8/8/8/3B1N2/PPPPPPPP/RNBQK2R b q -");
		BoardState state = board.getState();
		
		assertFalse(state.isKingSideCastlingAvaiable(MovementUtil.WHITE));
		assertFalse(state.isQueenSideCastlingAvaiable(MovementUtil.WHITE));
		assertFalse(state.isKingSideCastlingAvaiable(MovementUtil.BLACK));
		assertTrue(state.isQueenSideCastlingAvaiable(MovementUtil.BLACK));
		
		assertEquals(
			MovementUtil.BLACK_ROOK,
			board.getSquareValue(state.getQueenRookPosition(MovementUtil.BLACK))
		);
	}
	
	@ParameterizedTest
	@ValueSource(
		strings = {
			"r3k2r/p1ppq1bp/bpn2np1/4p3/B3PpP1/1PNP1N2/PBP1QP1P/R3K2R b KQkq g3 0 11",
			"r2q1rk1/1P1bn1p1/2pp4/pPnNp1B1/2BbPpPp/2QP4/2PN1P1P/R3K2R w KQ a6 0 19",
			"rnbq1bnr/pp1p1p1p/3pk3/3NP1p1/5p2/5N2/PPP1Q1PP/R1B1KB1R w - - 1 0",
			"4r3/6kp/ppr3P1/3p4/3Pq3/3nBR2/PP4QP/5R1K w - - 1 0",
			"8/2Q2pk1/3Pp1p1/1b5p/1p3P1P/1P2PK2/6RP/7q b - - 0 1",
			"rnbq1bkr/pp3p1p/2p3pQ/3N2N1/2B2p2/8/PPPP2PP/R1B1R1K1 w - - 1 0",
			"r2q2rk/ppp2p1p/3b1pn1/5R1Q/3P4/2P4N/PP4PP/R1B3K1 w - - 1 0",
			"r4b1r/pppq2pp/2n1b1k1/3n4/2Bp4/5Q2/PPP2PPP/RNB1R1K1 w - - 1 0",
			"rnbq1b1r/ppp1pQ1p/1n1p2p1/4P2k/3P4/8/PPP2PPP/RNB1K2R w - - 1 0",
			"r3rk2/5pR1/pp1q1P1p/8/3p3P/P2B4/1P1Q2b1/1K6 w - - 1 0",
			"rk6/N4ppp/Qp2q3/3p4/8/8/5PPP/2R3K1 w - - 1 0",
			"4q1rk/pb2bpnp/2r4Q/1p1p1pP1/4NP2/1P3R2/PBn4P/RB4K1 w - - 1 0",
			"r2Nqb1r/pQ1bp1pp/1pn1p3/1k1p4/2p2B2/2P5/PPP2PPP/R3KB1R w - - 1 0",
			"2r3k1/pp3ppp/1qr2n2/3p1Q2/1P6/P2BP2P/5PP1/2R2RK1 w - - 1 0",
			"r1b1rk2/ppq3p1/2nbpp2/3pN1BQ/2PP4/7R/PP3PPP/R5K1 w - - 1 0",
			"3r1bN1/3p1p1p/pp6/5k2/5P2/P7/1P2PPBq/R2R1K2 w - - 1 0",
			"5rk1/p3R2p/3p2p1/1p1P1P2/2p4q/P1Pn1P2/6P1/R2Q1K1N b - - 0 1",
			"2bk4/6b1/2pNp3/r1PpP1P1/P1pP1Q2/2rq4/7R/6RK w - - 1 0",
			"4r2k/pp2q2b/2p2p1Q/4rP2/P7/1B5P/1P2R1R1/7K w - - 1 0",
			"7k/6p1/2p3pp/1p4qn/4r3/2Pr2P1/PP2BP1K/1Q3R2 b - - 0 1",
			"2r2rk1/1b3pp1/4p3/p3P1Q1/1pqP1R2/2P5/PP1B1K1P/R7 w - - 1 0",
			"2q2rk1/4r1bp/bpQp2p1/p2Pp3/P3P2P/1NP1B1K1/1P6/R2R4 b - - 0 1",
			"3kn3/p1p2Rp1/1p2q3/7p/7P/5QP1/P6K/8 w - - 1 0",
			"4r3/p1r2p1k/1p2pPpp/2qpP3/3R2P1/1PPQ3R/1P5P/7K w - - 1 0",
			"6rk/Q2n2rp/5p2/3P4/4P3/2q4P/P5P1/5RRK b - - 0 1",
			"8/Q7/5pkp/2n1q3/8/1B5P/5PP1/6K1 w - - 1 0",
			"rq3kB1/pp1b1p2/4pB1p/4P3/3P3Q/P1n5/5PPP/R5K1 w - - 1 0",
			"3r1k1r/p1q2p2/1pp2N1p/n3RQ2/3P4/2p1PR2/PP4PP/6K1 w - - 1 0",
			"r3r1k1/ppp2p1p/1b3Bp1/4P3/3P1n2/2PB1N2/PP6/2KR3R w - - 1 0",
			"7R/r1p1q1pp/3k4/1p1n1Q2/3N4/8/1PP2PPP/2B3K1 w - - 1 0",
			"3rb1k1/ppq3p1/2p1p1p1/6P1/2Pr3R/1P1Q4/P1B4P/5RK1 w - - 1 0",
			"3r4/pk3pq1/Nb2p2p/3n4/2QP4/6P1/1P3PBP/5RK1 w - - 1 0",
			"4r2r/5k2/2p2P1p/p2pP1p1/3P2Q1/6PB/1n5P/6K1 w - - 1 0",
			"4b3/k1r1q2p/p3p3/3pQ3/2pN4/1R6/P4PPP/1R4K1 w - - 1 0",
			"r7/p1n2p1R/qp1p1k2/3Pp3/bPp1P3/2P1BBN1/3Q2K1/8 w - - 1 0",
			"r3QnR1/1bk5/pp5q/2b5/2p1P3/P7/1BB4P/3R3K w - - 1 0",
			"r5k1/1p4pp/2p1p2r/p2nRp2/P2P1P1P/1P3qP1/1BQ2P2/R5K1 b - - 0 1",
			"5k2/p2Q1pp1/1b5p/1p2PB1P/2p2P2/8/PP3qPK/8 w - - 1 0",
			"2br3k/pp3Pp1/1n2p3/1P2N1pr/2P2qP1/8/1BQ2P1P/4R1K1 w - - 1 0",
			"3r4/6kp/1p1r1pN1/5Qq1/6p1/PB4P1/1P3P2/6KR w - - 1 0",
			"5r1k/7p/p2b4/1pNp1p1q/3Pr3/2P2bP1/PP1B3Q/R3R1K1 b - - 0 1",
			"5rk1/pR6/3Q4/2pPnp1p/P3p2q/4P1N1/5Pp1/6K1 b - - 0 1",
			"r4r1k/pp4R1/3pN1p1/3P2Qp/1q2Ppn1/8/6PP/5RK1 w - - 1 0",
			"r7/1p3Q2/2kpr2p/p1p2Rp1/P3Pp2/1P3P2/1B2q1PP/3R3K w - - 1 0",
			"4r1k1/5pbp/3p2p1/1ppP4/pqP5/R4B2/1PQ3PP/1K6 b - - 0 1",
			"4k3/R3n2p/4N3/3p1p2/2b2P2/5BP1/4P1K1/2r5 w - - 1 0",
			"1R4Q1/3nr1pp/3p1k2/5Bb1/4P3/2q1B1P1/5P1P/6K1 w - - 1 0"
		}
	)
	@SuppressWarnings(
		{
			"javadoc"
		}
	)
	public void test(String fen) {
		Board board = new Board(fen);
		Board copy = board.copy();
		List<Movement> movements = board.getMovements().streamMovements().collect(Collectors.toList());
		for (Movement movement : movements) {
			board.move(movement);
			board.undo();
			Supplier<String> message = () -> String
				.format("Movement %s let the board in a invaild state after undo", movement);
			assertEquals(
				copy.toString(),
				board.toString(),
				message
			);
			assertEquals(
				copy.getState(),
				board.getState(),
				message
			);
		}
	}
}
