package com.welyab.anjabachen.movement.fen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.welyab.anjabachen.movement.BoardUtil;
import com.welyab.anjabachen.movement.LocalizedPiece;
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
		
		for (int i = 16; i < 48; i++) {
			assertEquals(BoardUtil.NO_PIECE_CODE, lpieces.get(i).getPieceCode());
		}
		
		assertEquals(BoardUtil.WHITE_PAWN_CODE, lpieces.get(48).getPieceCode());
		assertEquals(BoardUtil.WHITE_PAWN_CODE, lpieces.get(49).getPieceCode());
		assertEquals(BoardUtil.WHITE_PAWN_CODE, lpieces.get(50).getPieceCode());
		assertEquals(BoardUtil.WHITE_PAWN_CODE, lpieces.get(51).getPieceCode());
		assertEquals(BoardUtil.WHITE_PAWN_CODE, lpieces.get(52).getPieceCode());
		assertEquals(BoardUtil.WHITE_PAWN_CODE, lpieces.get(53).getPieceCode());
		assertEquals(BoardUtil.WHITE_PAWN_CODE, lpieces.get(54).getPieceCode());
		assertEquals(BoardUtil.WHITE_PAWN_CODE, lpieces.get(55).getPieceCode());
		assertEquals(BoardUtil.WHITE_ROOK_CODE, lpieces.get(56).getPieceCode());
		assertEquals(BoardUtil.WHITE_KNIGHT_CODE, lpieces.get(57).getPieceCode());
		assertEquals(BoardUtil.WHITE_BISHOP_CODE, lpieces.get(58).getPieceCode());
		assertEquals(BoardUtil.WHITE_QUEEN_CODE, lpieces.get(59).getPieceCode());
		assertEquals(BoardUtil.WHITE_KING_CODE, lpieces.get(60).getPieceCode());
		assertEquals(BoardUtil.WHITE_BISHOP_CODE, lpieces.get(61).getPieceCode());
		assertEquals(BoardUtil.WHITE_KNIGHT_CODE, lpieces.get(62).getPieceCode());
		assertEquals(BoardUtil.WHITE_ROOK_CODE, lpieces.get(63).getPieceCode());
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
		assertEquals(1, parser.getPositionInfo().getHalfMoveClock());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void fullMoveCounterShouldBeParsedToTwo() {
		FenParser parser = new FenParser("rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2");
		assertEquals(2, parser.getPositionInfo().getFullMoveCounter());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void fullMoveCounterShouldBeParsedToZero() {
		FenParser parser = new FenParser("rn1qkb1r/pp3ppp/3p1n2/2pPp3/2B1P3/2N5/PPP2PPP/R1BbK1NR w KQkq c6 -");
		assertEquals(0, parser.getPositionInfo().getFullMoveCounter());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void halMoveClockShouldBeParsedToZero() {
		FenParser parser = new FenParser("rn1qkb1r/pp3ppp/3p1n2/2pPp3/2B1P3/2N5/PPP2PPP/R1BbK1NR w KQkq c6 -");
		assertEquals(0, parser.getPositionInfo().getHalfMoveClock());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void allCastlingFlagsShouldBeParsedToTrue() {
		FenParser parser = new FenParser("rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2");
		assertTrue(parser.getPositionInfo().isWhiteKingCastlingAvaiable());
		assertTrue(parser.getPositionInfo().isWhiteQueenCastlingAvaiable());
		assertTrue(parser.getPositionInfo().isBlackKingCastlingAvaiable());
		assertTrue(parser.getPositionInfo().isBlackQueenCastlingAvaiable());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void onlyWhiteKingCastlingShouldBeTrue() {
		FenParser parser = new FenParser("rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b K - 1 2");
		assertTrue(parser.getPositionInfo().isWhiteKingCastlingAvaiable());
		assertFalse(parser.getPositionInfo().isWhiteQueenCastlingAvaiable());
		assertFalse(parser.getPositionInfo().isBlackKingCastlingAvaiable());
		assertFalse(parser.getPositionInfo().isBlackQueenCastlingAvaiable());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void allCastlingFlagsShouldBeParsedToFalse() {
		FenParser parser = new FenParser("rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b - - 1 2");
		assertFalse(parser.getPositionInfo().isWhiteKingCastlingAvaiable());
		assertFalse(parser.getPositionInfo().isWhiteQueenCastlingAvaiable());
		assertFalse(parser.getPositionInfo().isBlackKingCastlingAvaiable());
		assertFalse(parser.getPositionInfo().isBlackQueenCastlingAvaiable());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void sideToMoveShouldBeParsedToWhite() {
		FenParser parser = new FenParser("rn1qkb1r/ppp2ppp/3p1n2/4p3/2B1P1b1/2NP4/PPP2PPP/R1BQK1NR w KQkq - 1 5");
		assertEquals(BoardUtil.WHITE_COLOR_CODE, parser.getPositionInfo().getSideToMove());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void sideToMoveShouldBeParsedToBlack() {
		FenParser parser = new FenParser("rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b - - 1 2");
		assertEquals(BoardUtil.BLACK_COLOR_CODE, parser.getPositionInfo().getSideToMove());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void enPassantTargetSquareShouldBeParsedToc6() {
		FenParser parser = new FenParser("rn1qkb1r/pp3ppp/3p1n2/2pPp3/2B1P3/2N5/PPP2PPP/R1BbK1NR w KQkq c6 0 7");
		assertEquals(Position.of('c', (byte) 6), parser.getPositionInfo().getEnPassantTargetSquare());
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void enPassantTargetSquareShouldBeParsedToNull() {
		FenParser parser = new FenParser("rn1qkb1r/pp3ppp/3p1n2/2pPp3/2B1P3/2N5/PPP2PPP/R1BbK1NR w KQkq - 0 7");
		assertNull(parser.getPositionInfo().getEnPassantTargetSquare());
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
}
