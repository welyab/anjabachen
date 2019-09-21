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
package com.welyab.anjabachen.uci.cmd;

import java.util.List;

public enum CommandResolver {
	
	UCI("uci", Origin.GUI) {
		
		@Override
		public Command resolve(List<String> parameters) {
			return new UciCommand(parameters);
		}
	},
	DEBUG("debug", Origin.GUI) {
		
		@Override
		public Command resolve(List<String> parameters) {
			return new DebugCommand(parameters);
		}
	},
	IS_READY("isready", Origin.GUI) {
		
		@Override
		public Command resolve(List<String> parameters) {
			return new IsReadyCommand(parameters);
		}
	},
	SET_OPTION("setoption", Origin.GUI) {
		
		@Override
		public Command resolve(List<String> parameters) {
			return new SetOptionCommand(parameters);
		}
	},
	REGISTER("register", Origin.GUI) {
		
		@Override
		public Command resolve(List<String> parameters) {
			return new RegisterCommand(parameters);
		}
	},
	UCI_NEW_GAME("ucinewgame", Origin.GUI) {
		
		@Override
		public Command resolve(List<String> parameters) {
			return new UciNewGameCommand(parameters);
		}
	},
	POSITION("position", Origin.GUI) {
		
		@Override
		public Command resolve(List<String> parameters) {
			return new PositionCommand(parameters);
		}
	},
	GO("go", Origin.GUI) {
		
		@Override
		public Command resolve(List<String> parameters) {
			return new GoCommand(parameters);
		}
	},
	STOP("go", Origin.GUI) {
		
		@Override
		public Command resolve(List<String> parameters) {
			return new StopCommand(parameters);
		}
	},
	PONDER_HIT("go", Origin.GUI) {
		
		@Override
		public Command resolve(List<String> parameters) {
			return new PonderHitCommand(parameters);
		}
	},
	QUIT("go", Origin.GUI) {
		
		@Override
		public Command resolve(List<String> parameters) {
			return new QuitCommand(parameters);
		}
	},
	ID("", Origin.ENGINE) {
		
		@Override
		public Command resolve(List<String> parameters) {
			return new IdCommand(parameters);
		}
	},
	UCI_OK("", Origin.ENGINE) {
		
		@Override
		public Command resolve(List<String> parameters) {
			return new UciOkCommand(parameters);
		}
	},
	READY_OK("", Origin.ENGINE) {
		
		@Override
		public Command resolve(List<String> parameters) {
			return new ReadyOkCommand(parameters);
		}
	},
	BEST_MOVE("", Origin.ENGINE) {
		
		@Override
		public Command resolve(List<String> parameters) {
			return new BestMoveCommand(parameters);
		}
	},
	COPY_PROTECTION("", Origin.ENGINE) {
		
		@Override
		public Command resolve(List<String> parameters) {
			return new CopyProtectionCommand(parameters);
		}
	},
	REGISTRATION("", Origin.ENGINE) {
		
		@Override
		public Command resolve(List<String> parameters) {
			return new RegistrationCommand(parameters);
		}
	},
	INFO("", Origin.ENGINE) {
		
		@Override
		public Command resolve(List<String> parameters) {
			return new InfoCommand(parameters);
		}
	},
	OPTION("", Origin.ENGINE) {
		
		@Override
		public Command resolve(List<String> parameters) {
			return new OptionCommand(parameters);
		}
	};
	
	private final String commandName;
	
	private final Origin origin;
	
	private CommandResolver(String commandName, Origin origin) {
		this.commandName = commandName;
		this.origin = origin;
	}
	
	public String getCommandName() {
		return commandName;
	}
	
	public static CommandResolver findResolver(String uciCommandName) {
		for (CommandResolver commandResolver : values()) {
			if (commandResolver.getCommandName().contentEquals(uciCommandName)) {
				return commandResolver;
			}
		}
		throw new IllegalArgumentException(
			String.format(
				"Unexpected command: %s",
				uciCommandName
			)
		);
	}
	
	public abstract Command resolve(List<String> parameters);
}
