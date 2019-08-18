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
				"c:/users/welyab/desktop/test.pgn"
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
