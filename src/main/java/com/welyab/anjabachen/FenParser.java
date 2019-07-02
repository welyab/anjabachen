package com.welyab.anjabachen;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import com.welyab.anjabachen.grammar.fenBaseListener;
import com.welyab.anjabachen.grammar.fenLexer;
import com.welyab.anjabachen.grammar.fenParser;
import com.welyab.anjabachen.grammar.fenParser.FenContext;

public class MainAntlr {

	public static void main(String[] args) {
		fenLexer lexer = new fenLexer(
			CharStreams.fromString("rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w K c6 50 2")
		);
		CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		fenParser parser = new fenParser(tokenStream);
		FenContext fen = parser.fen();
		ParseTreeWalker walker = new ParseTreeWalker();
		walker.walk(new MyListener(), fen);
	}

	static class MyListener extends fenBaseListener {
	}
}
