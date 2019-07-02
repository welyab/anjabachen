package com.welyab.anjabachen;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import com.welyab.anjabachen.grammar.FENBaseListener;
import com.welyab.anjabachen.grammar.FENLexer;
import com.welyab.anjabachen.grammar.FENParser.BlackKingSideCastlingContext;
import com.welyab.anjabachen.grammar.FENParser.BlackQueenSideCastlingContext;
import com.welyab.anjabachen.grammar.FENParser.EnPassantTargetSquareContext;
import com.welyab.anjabachen.grammar.FENParser.FenContext;
import com.welyab.anjabachen.grammar.FENParser.HalfMoveClockContext;
import com.welyab.anjabachen.grammar.FENParser.PieceDispositionContext;
import com.welyab.anjabachen.grammar.FENParser.SideToMoveContext;
import com.welyab.anjabachen.grammar.FENParser.WhiteKingSideCastlingContext;
import com.welyab.anjabachen.grammar.FENParser.WhiteQueenSideCastlingContext;

public class FENParser {

	private FenContext fen;

	public FENParser(String fen) {
		FENLexer lexer = new FENLexer(CharStreams.fromString(fen));
		CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		com.welyab.anjabachen.grammar.FENParser parser = new com.welyab.anjabachen.grammar.FENParser(tokenStream);
		this.fen = parser.fen();
	}

	public static void main(String[] args) {
		FENParser fenParser = new FENParser("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1");
		Board board = fenParser.getBoard();
		System.out.println(board);
	}

	public Board getBoard() {
		ParseTreeWalker walker = new ParseTreeWalker();
		BoardFenConfig boardFenConfig = new BoardFenConfig();
		walker.walk(boardFenConfig, fen);
		Board board = new Board(false);
		for (PiecePosition pp : boardFenConfig.pieces) {
			board.addPiece(pp.piece, pp.position);
		}
		return board;
	}

	private class BoardFenConfig extends FENBaseListener {

		Color sideToMove;

		boolean whiteKingSideCastling;

		boolean whiteQueenSideCastling;

		boolean blackKingSideCastling;

		boolean blackQueenSideCastling;

		int halfMoveClock;

		int fullMoveCounter;

		Position enPassantTarget;

		List<PiecePosition> pieces = new ArrayList<>();

		int currentRank = 8;

		@Override
		public void enterEnPassantTargetSquare(EnPassantTargetSquareContext ctx) {
			if (!ctx.getText().equals("-")) {
				char file = ctx.getChild(0).getText().charAt(0);
				int rank = Integer.parseInt(ctx.getChild(1).getText());
				enPassantTarget = Position.of(file, rank);
			}
		}

		@Override
		public void enterHalfMoveClock(HalfMoveClockContext ctx) {
			halfMoveClock = Integer.parseInt(ctx.getText());
		}

		@Override
		public void enterWhiteKingSideCastling(WhiteKingSideCastlingContext ctx) {
			whiteKingSideCastling = true;
		}

		@Override
		public void enterWhiteQueenSideCastling(WhiteQueenSideCastlingContext ctx) {
			whiteQueenSideCastling = true;
		}

		@Override
		public void enterBlackKingSideCastling(BlackKingSideCastlingContext ctx) {
			blackKingSideCastling = true;
		}

		@Override
		public void enterBlackQueenSideCastling(BlackQueenSideCastlingContext ctx) {
			blackQueenSideCastling = true;
		}

		@Override
		public void enterSideToMove(SideToMoveContext ctx) {
			sideToMove = Color.fromColor(ctx.getText().charAt(0));
		}

		@Override
		public void enterPieceDisposition(PieceDispositionContext ctx) {
			char currentFile = 'a';
			for (int i = 0; i < ctx.getChildCount(); i++) {
				ParseTree child = ctx.getChild(i);
				char c = child.getText().charAt(0);
				if (Character.isDigit(c)) {
					currentFile += c - '0';
				} else {
					pieces.add(
						new PiecePosition(
							Piece.fromLetterSymbol(c),
							Position.of(currentFile, currentRank)
						)
					);
					currentFile++;
				}
			}
			currentRank--;
		}

		@Override
		public String toString() {
			return "BoardFenConfig [sideToMove=" + sideToMove + ", whiteKingSideCastling=" + whiteKingSideCastling
					+ ", whiteQueenSideCastling=" + whiteQueenSideCastling + ", blackKingSideCastling="
					+ blackKingSideCastling + ", blackQueenSideCastling=" + blackQueenSideCastling + ", halfMoveClock="
					+ halfMoveClock + ", fullMoveCounter=" + fullMoveCounter + ", enPassantTarget=" + enPassantTarget
					+ ", currentRank=" + currentRank + ", pieces=" + pieces + "]";
		}
	}

	private static class PiecePosition {

		final Piece piece;

		final Position position;

		PiecePosition(Piece piece, Position position) {
			this.piece = piece;
			this.position = position;
		}

		Piece getPiece() {
			return piece;
		}

		Position getPosition() {
			return position;
		}

		@Override
		public String toString() {
			return "PiecePosition [piece=" + piece + ", position=" + position + "]";
		}
	}
}
