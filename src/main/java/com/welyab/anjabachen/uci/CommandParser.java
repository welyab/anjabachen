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
package com.welyab.anjabachen.uci;

import java.util.AbstractList;
import java.util.List;

import com.welyab.anjabachen.uci.cmd.Command;
import com.welyab.anjabachen.uci.cmd.CommandResolver;

public class CommandParser {
	
	public Command parseCommand(String raw) {
		raw = CommandParserUtil.normalize(raw);
		List<String> tokens = CommandParserUtil.getTokens(raw);
		String commandName = tokens.get(0);
		CommandResolver commandResolver = CommandResolver.findResolver(commandName);
		List<String> parameters = new SubList(tokens, 1, tokens.size());
		return commandResolver.resolve(parameters);
	}
	
	private static class SubList extends AbstractList<String> {
		
		List<String> originalList;
		
		int startIndex;
		
		int finalIndexExclusive;
		
		SubList(List<String> originalList, int startIndex, int finalIndexExclusive) {
			this.originalList = originalList;
			this.startIndex = startIndex;
			this.finalIndexExclusive = finalIndexExclusive;
			
		}
		
		@Override
		public String get(int index) {
			if (index < 0 || index >= size()) {
				throw new IndexOutOfBoundsException(index);
			}
			return originalList.get(startIndex + index);
		}
		
		@Override
		public int size() {
			return finalIndexExclusive - startIndex;
		}
	}
}
