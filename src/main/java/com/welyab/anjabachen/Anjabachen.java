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
package com.welyab.anjabachen;

import com.welyab.anjabachen.engine.Engine;
import com.welyab.anjabachen.engine.EngineBootConfig;
import com.welyab.anjabachen.ui.AnjabachenGui;
import com.welyab.anjabachen.ui.AnjabachenGuiBootConfig;

/**
 * This is the start point to interact with AN.JA.BA.CH.EN. This class has a method <i>main</i> that
 * starts program execution in the <code>GUI</code> mode (default) or in the <code>UCI</code>
 * compatible mode.
 * 
 * <pre>
 * $ java -jar anjabachen.jar options
 * 
 * options:
 *   -gui - (optional) indicates that program should run in GUI mode.
 *   -uci - (optional) indicates that program should start UCI mode.
 *   
 * Exemples:
 * 
 * $ java -jar anjabachen.jar
 * $ java -jar anjabachen.jar -gui
 * $ java -jar anjabachen.jar -uci
 * </pre>
 * 
 * @author Welyab Paula
 */
public class Anjabachen {
	
	/** A command line option to start the program in GUI mode. */
	private static final String GUI_OPTION = "-gui";
	
	/** A command line option to start the program in UCI mode. */
	private static final String UCI_OPTION = "-uci";
	
	public static void main(String[] args) {
		Arguments arguments = parseArguments(args);
		if (!arguments.isValid()) {
			printUsage(arguments.getProblemDescription());
			return;
		}
		ExecutionMode mode = arguments.getExecutionMode();
		
		if (mode == ExecutionMode.GUI) {
			AnjabachenGui.start(createAnjabachenGuiBootConfig(arguments));
		} else if (mode == ExecutionMode.UCI) {
			Engine.start(createEngineBootConfig(arguments));
		} else {
			throw new ChessError(String.format("Unexpected program mode: %s", mode));
		}
	}
	
	private static AnjabachenGuiBootConfig createAnjabachenGuiBootConfig(Arguments arguments) {
		return null;
	}
	
	private static EngineBootConfig createEngineBootConfig(Arguments arguments) {
		return null;
	}
	
	private static void printUsage(String object) {
	}
	
	private static Arguments parseArguments(String[] args) {
		if (args.length == 0 || args[0].equalsIgnoreCase(GUI_OPTION)) {
			return mountGuiArguments(args);
		}
		if (args[0].equals(UCI_OPTION)) {
			return mountUciArguments(args);
		}
		return mountInvalidArguments(String.format("Unexpected option '%s'", args[0]));
	}
	
	private static Arguments mountInvalidArguments(String problemDescription) {
		return new Arguments() {
			
			@Override
			public boolean isValid() {
				return false;
			}
			
			@Override
			public String getProblemDescription() {
				return problemDescription;
			}
			
			@Override
			public ExecutionMode getExecutionMode() {
				throw new IllegalStateException("The arguments are invalid");
			}
		};
	}
	
	private static Arguments mountUciArguments(String[] args) {
		return new Arguments() {
			
			@Override
			public boolean isValid() {
				return true;
			}
			
			@Override
			public String getProblemDescription() {
				return "";
			}
			
			@Override
			public ExecutionMode getExecutionMode() {
				return ExecutionMode.UCI;
			}
		};
	}
	
	private static Arguments mountGuiArguments(String[] args) {
		return new Arguments() {
			
			@Override
			public boolean isValid() {
				return true;
			}
			
			@Override
			public String getProblemDescription() {
				return "";
			}
			
			@Override
			public ExecutionMode getExecutionMode() {
				return ExecutionMode.GUI;
			}
		};
	}
	
	/**
	 * Parsed arguments from program start up.
	 * 
	 * @author Welyab Paula
	 */
	private interface Arguments {
		
		boolean isValid();
		
		ExecutionMode getExecutionMode();
		
		String getProblemDescription();
	}
}
