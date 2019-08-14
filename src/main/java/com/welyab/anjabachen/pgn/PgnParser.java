package com.welyab.anjabachen.pgn;

import java.io.IOException;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import com.welyab.anjabachen.grammar.PGNBaseListener;
import com.welyab.anjabachen.grammar.PGNLexer;
import com.welyab.anjabachen.grammar.PGNParser.PgnContext;
import com.welyab.anjabachen.grammar.PGNParser.TagNameContext;
import com.welyab.anjabachen.grammar.PGNParser.TagValueContext;

public class PgnParser {

	public static void main(String[] args) throws IOException {
		PGNLexer lexer = new PGNLexer(
			CharStreams.fromFileName(
				"/home/welyab/Downloads/Telegram Desktop/test.pgn"
			)
		);
		CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		com.welyab.anjabachen.grammar.PGNParser parser = new com.welyab.anjabachen.grammar.PGNParser(tokenStream);
		PgnContext pgnContext = parser.pgn();
		ParseTreeWalker walker = new ParseTreeWalker();
		PgnWalker boardFenConfig = new PgnWalker();
		walker.walk(boardFenConfig, pgnContext);
	}

	private static class PgnWalker extends PGNBaseListener {
		
		@Override
		public void enterTagName(TagNameContext ctx) {
			System.out.println("Tag name: " + ctx.getText());
		}
		
		@Override
		public void enterTagValue(TagValueContext ctx) {
			System.out.println("Tag value: " + ctx.getText());
		}
	}
}
