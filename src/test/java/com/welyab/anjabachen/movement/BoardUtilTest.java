package com.welyab.anjabachen.movement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Unit tests for the <code>BoardUtil</code> class.
 * 
 * @author Welyab Paula
 */
public class BoardUtilTest {
	
	@Test
	@SuppressWarnings("javadoc")
	public void opositeColorShouldReturnWhiteWhenGivenColorIsBlack() {
		assertEquals(BoardUtil.WHITE_COLOR_CODE, BoardUtil.oppositeColor(BoardUtil.BLACK_COLOR_CODE));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void opositeColorShouldReturnBlackWhenGivenColorIsWhite() {
		assertEquals(BoardUtil.BLACK_COLOR_CODE, BoardUtil.oppositeColor(BoardUtil.WHITE_COLOR_CODE));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void opositeColorShouldThrowIllegalArgument() {
		assertThrows(IllegalArgumentException.class, () -> {
			BoardUtil.oppositeColor(0);
		});
	}
	
	@ParameterizedTest
	@CsvSource({
			BoardUtil.WHITE_COLOR_LETTER + "," + BoardUtil.WHITE_COLOR_CODE,
			BoardUtil.BLACK_COLOR_LETTER + "," + BoardUtil.BLACK_COLOR_CODE
	})
	@SuppressWarnings("javadoc")
	public void colorCodeToLetterShouldReturnProperlyColorLetter(char expected, byte input) {
		assertEquals(expected, BoardUtil.colorCodeToLetter(input));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void colorCodeToLetterShouldThrowIllegalArgumentexceptionWhenColorCodeInvalid() {
		assertThrows(IllegalArgumentException.class, () -> {
			BoardUtil.colorCodeToLetter(0);
		});
	}
	
	@ParameterizedTest
	@CsvSource({
			BoardUtil.WHITE_COLOR_CODE + "," + BoardUtil.WHITE_COLOR_LETTER,
			BoardUtil.BLACK_COLOR_CODE + "," + BoardUtil.BLACK_COLOR_LETTER
	})
	@SuppressWarnings("javadoc")
	public void colorLetterToCodeShouldReturnProperlyColorCode(byte expected, char input) {
		assertEquals(expected, BoardUtil.colorLetterToCode(input));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void colorLetterToCodeShouldThrowIllegalArgumentexceptionWhenColorCodeInvalid() {
		assertThrows(IllegalArgumentException.class, () -> {
			BoardUtil.colorLetterToCode('x');
		});
	}
	
	@ParameterizedTest
	@CsvSource({
			BoardUtil.BLACK_KING_LETTER + "," + BoardUtil.BLACK_KING_CODE,
			BoardUtil.BLACK_QUEEN_LETTER + "," + BoardUtil.BLACK_QUEEN_CODE,
			BoardUtil.BLACK_ROOK_LETTER + "," + BoardUtil.BLACK_ROOK_CODE,
			BoardUtil.BLACK_BISHOP_LETTER + "," + BoardUtil.BLACK_BISHOP_CODE,
			BoardUtil.BLACK_KNIGHT_LETTER + "," + BoardUtil.BLACK_KNIGHT_CODE,
			BoardUtil.BLACK_PAWN_LETTER + "," + BoardUtil.BLACK_PAWN_CODE,
			BoardUtil.WHITE_KING_LETTER + "," + BoardUtil.WHITE_KING_CODE,
			BoardUtil.WHITE_QUEEN_LETTER + "," + BoardUtil.WHITE_QUEEN_CODE,
			BoardUtil.WHITE_ROOK_LETTER + "," + BoardUtil.WHITE_ROOK_CODE,
			BoardUtil.WHITE_BISHOP_LETTER + "," + BoardUtil.WHITE_BISHOP_CODE,
			BoardUtil.WHITE_KNIGHT_LETTER + "," + BoardUtil.WHITE_KNIGHT_CODE,
			BoardUtil.WHITE_PAWN_LETTER + "," + BoardUtil.WHITE_PAWN_CODE
	})
	@SuppressWarnings("javadoc")
	public void pieceCodeToLetterShouldReturnProperlyPieceLetter(char pieceLetter, byte pieceCode) {
		assertEquals(pieceLetter, BoardUtil.pieceCodeToLetter(pieceCode));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void pieceCodeToLetterShouldThrowIllegalArgumentExceptionWhenPieceCodeIsInvalid() {
		assertThrows(IllegalArgumentException.class, () -> {
			BoardUtil.pieceCodeToLetter(0);
		});
	}
	
	@ParameterizedTest
	@CsvSource({
			BoardUtil.BLACK_KING_CODE + "," + BoardUtil.BLACK_KING_LETTER,
			BoardUtil.BLACK_QUEEN_CODE + "," + BoardUtil.BLACK_QUEEN_LETTER,
			BoardUtil.BLACK_ROOK_CODE + "," + BoardUtil.BLACK_ROOK_LETTER,
			BoardUtil.BLACK_BISHOP_CODE + "," + BoardUtil.BLACK_BISHOP_LETTER,
			BoardUtil.BLACK_KNIGHT_CODE + "," + BoardUtil.BLACK_KNIGHT_LETTER,
			BoardUtil.BLACK_PAWN_CODE + "," + BoardUtil.BLACK_PAWN_LETTER,
			BoardUtil.WHITE_KING_CODE + "," + BoardUtil.WHITE_KING_LETTER,
			BoardUtil.WHITE_QUEEN_CODE + "," + BoardUtil.WHITE_QUEEN_LETTER,
			BoardUtil.WHITE_ROOK_CODE + "," + BoardUtil.WHITE_ROOK_LETTER,
			BoardUtil.WHITE_BISHOP_CODE + "," + BoardUtil.WHITE_BISHOP_LETTER,
			BoardUtil.WHITE_KNIGHT_CODE + "," + BoardUtil.WHITE_KNIGHT_LETTER,
			BoardUtil.WHITE_PAWN_CODE + "," + BoardUtil.WHITE_PAWN_LETTER
	})
	@SuppressWarnings("javadoc")
	public void pieceCharToCodeShouldReturnProperlyPieceCode(byte pieceCode, char pieceLetter) {
		assertEquals(pieceCode, BoardUtil.pieceLetterToCode(pieceLetter));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void pieceLetterToCodeShouldThrowIllegalArgumentExceptionWhenPieceLetterIsInvalid() {
		assertThrows(IllegalArgumentException.class, () -> {
			BoardUtil.pieceLetterToCode('x');
		});
	}
}
