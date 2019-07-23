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

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import com.welyab.anjabachen.Board;
import com.welyab.anjabachen.Color;
import com.welyab.anjabachen.LocalizedPiece;
import com.welyab.anjabachen.Piece;
import com.welyab.anjabachen.Position;
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

public class FenParser {
	
	private boolean parsed;
	
	String fen;
	
	private FenContext fenContext;
	
	private Board board;
	
	List<LocalizedPiece> pieces = new ArrayList<>();
	
	GameConfig boardConfig;
	
	public FenParser(String fen) {
		this.fen = fen;
		FENLexer lexer = new FENLexer(CharStreams.fromString(fen));
		CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		com.welyab.anjabachen.grammar.FENParser parser = new com.welyab.anjabachen.grammar.FENParser(tokenStream);
		fenContext = parser.fen();
	}
	
	public static FenParser of(String fen) {
		return new FenParser(fen);
	}
	
	public GameConfig getBoardConfig() {
		parse();
		return boardConfig;
	}
	
	public List<LocalizedPiece> getPiecesDisposition() {
		parse();
		return Collections.unmodifiableList(pieces);
	}
	
	public Board getBoard() {
		parse();
		return new Board(
			pieces,
			boardConfig
		);
	}
	
	/**
	 * Parses the underlying FEN string.
	 *
	 * @see #getBoard()
	 * @see #getPiecesDisposition()
	 */
	private void parse() {
		if (parsed) {
			return;
		}
		ParseTreeWalker walker = new ParseTreeWalker();
		FenWalker boardFenConfig = new FenWalker();
		walker.walk(boardFenConfig, fenContext);
		parsed = true;
	}
	
	/**
	 *
	 * @author Welyab Paula
	 */
	private class FenWalker extends FENBaseListener {
		
		/**
		 * The builder for game configuration.
		 */
		GameConfig.Builder configBuilder = GameConfig.builder();
		
		/**
		 * The current rank number (in the FEN string, the first line of represented pieces is the
		 * 8th rank).
		 */
		int currentRank = 8;
		
		@Override
		public void enterEnPassantTargetSquare(EnPassantTargetSquareContext ctx) {
			if (!ctx.getText().equals("-")) {
				char file = ctx.getChild(0).getText().charAt(0);
				int rank = Integer.parseInt(ctx.getChild(1).getText());
				configBuilder = configBuilder.enPassantTargetSquare(Position.of(file, rank));
			}
		}
		
		@Override
		public void enterHalfMoveClock(HalfMoveClockContext ctx) {
			configBuilder = configBuilder.halfMoveCounter(Integer.parseInt(ctx.getText()));
		}
		
		@Override
		public void enterWhiteKingSideCastling(WhiteKingSideCastlingContext ctx) {
			configBuilder = configBuilder.whiteKingSideCastlingAvailable(true);
		}
		
		@Override
		public void enterWhiteQueenSideCastling(WhiteQueenSideCastlingContext ctx) {
			configBuilder = configBuilder.whiteQueenSideCastlingAvailable(true);
		}
		
		@Override
		public void enterBlackKingSideCastling(BlackKingSideCastlingContext ctx) {
			configBuilder = configBuilder.blackKingSideCastlingAvailable(true);
		}
		
		@Override
		public void enterBlackQueenSideCastling(BlackQueenSideCastlingContext ctx) {
			configBuilder = configBuilder.blackQueenSideCastlingAvailable(true);
		}
		
		@Override
		public void enterSideToMove(SideToMoveContext ctx) {
			configBuilder = configBuilder.sideToMove(Color.fromColor(ctx.getText().charAt(0)));
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
						new LocalizedPiece(
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
		public void exitFen(FenContext ctx) {
			boardConfig = configBuilder.build();
		}
	}
}
