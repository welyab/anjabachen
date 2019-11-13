package com.welyab.anjabachen.movement.png;

import com.welyab.anjabachen.movement.Board;
import com.welyab.anjabachen.movement.LocalizedPiece;
import com.welyab.anjabachen.movement.Movement;
import com.welyab.anjabachen.movement.MovementTarget;
import com.welyab.anjabachen.movement.MovementUtil;
import com.welyab.anjabachen.movement.Position;

public class MovementParser {
	
	public static Movement parseMovement(
		String token,
		byte color,
		Board board
	) {
		byte originRow = -1;
		byte originColumn = -1;
		byte originPieceType = MovementUtil.EMPTY;
		byte targetRow = -1;
		byte targetColumn = -1;
		byte targetPieceType = MovementUtil.EMPTY;
		if ("O-O".equals(token)) {
			originRow = MovementUtil.isWhite(color) ? 7 : (byte) 0;
			originColumn = 4;
			originPieceType = MovementUtil.KING;
			targetRow = originRow;
			targetColumn = 6;
			targetPieceType = MovementUtil.KING;
		} else if ("O-O-O".equals(token)) {
			originRow = MovementUtil.isWhite(color) ? 7 : (byte) 0;
			originColumn = 4;
			originPieceType = MovementUtil.KING;
			targetRow = originRow;
			targetColumn = 2;
			targetPieceType = MovementUtil.KING;
		} else {
			int index = token.length() - 1;
			boolean checkmate = false;
			if (token.charAt(index) == '#') {
				checkmate = true;
				index--;
			}
			boolean check = false;
			if (token.charAt(index) == '+') {
				check = true;
				index--;
			}
			if (Character.isLetter(token.charAt(index)) && Character.isUpperCase(token.charAt(index))) {
				targetPieceType = MovementUtil.getPieceType(token.charAt(index));
				index -= 2; // skip "="
			}
			targetRow = Position.rankToRow(token.charAt(index) - '0');
			index--;
			targetColumn = Position.fileToColumn(token.charAt(index));
			index--;
			boolean capture = false;
			if (token.charAt(index) == 'x') {
				capture = true;
				index--;
			}
			if (index >= 0 && Character.isDigit(token.charAt(index))) {
				originRow = Position.rankToRow(token.charAt(index) - '0');
				index--;
			}
			if (index >= 0 && Character.isLowerCase(token.charAt(index))) {
				originColumn = Position.fileToColumn(token.charAt(index));
				index--;
			}
			if (index >= 0) {
				originPieceType = MovementUtil.getPieceType(token.charAt(index));
				if (targetPieceType == MovementUtil.EMPTY) {
					targetPieceType = originPieceType;
				}
			}
		}
		
		return createSimpleMovement(
			board,
			originRow,
			originColumn,
			originPieceType,
			targetRow,
			targetColumn,
			targetPieceType,
			color
		);
	}
	
	private static Movement createSimpleMovement(
		Board board,
		int originRow, int originColumn,
		byte originPieceType,
		int targetRow, int targetColumn,
		byte targetPieceType,
		byte color
	) {
		Position target = Position.of(targetRow, targetColumn);
		Position origin = board
			.getAttackers(target, color)
			.stream()
			.filter(p -> MovementUtil.getPieceType(p.getPieceCode()) == originPieceType)
			.filter(p -> originRow < 0 || p.getPosition().row == originRow)
			.filter(p -> originColumn < 0 || p.getPosition().column == originColumn)
			.findFirst()
			.map(LocalizedPiece::getPosition)
			.orElse(null);
		
		short flags = 0;
		if (board.getSquareValue(target) != MovementUtil.EMPTY) {
			flags |= MovementUtil.CAPTURE_MASK;
		}
		byte pieceCode = board.getSquareValue(origin);
		Position enpTarget = board.getState().getEnPassantTargetSquare();
		if (MovementUtil.isPawn(pieceCode) && enpTarget != null && enpTarget.equals(target)) {
			flags |= MovementUtil.EN_PASSANT_MASK;
			flags |= MovementUtil.CAPTURE_MASK;
		}
		if (MovementUtil.isKing(pieceCode) && Math.abs(origin.column - target.column) == 2) {
			flags |= MovementUtil.CASTLING_MASK;
		}
		if (MovementUtil.isPawn(pieceCode) && (target.row == 0 || target.row == 7)) {
			flags |= MovementUtil.PROMOTION_MASK;
		}
		
		return new Movement(
			origin,
			new MovementTarget(target, targetPieceType, flags)
		);
	}
}
