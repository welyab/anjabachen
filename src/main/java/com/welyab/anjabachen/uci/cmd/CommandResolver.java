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

import com.welyab.anjabachen.uci.Channel;

/**
 * Enumerates the resolvers for UCI commands.
 * 
 * @author Welyab Paula
 */
public abstract class CommandResolver<T> {
	
	/** The UCI <code>"uci"</code> command. */
	public static final CommandResolver<UciCommand> UCI = new CommandResolver<>(UciCommands.UCI) {
		
		@Override
		public UciCommand resolve(Channel channel) {
			return new UciCommand(channel);
		}
	};
	
	/** The UCI <code>"debug"</code> command. */
	public static final CommandResolver<DebugCommand> DEBUG = new CommandResolver<>(UciCommands.DEBUG) {
		
		@Override
		public DebugCommand resolve(Channel channel) {
			return new DebugCommand(channel);
		}
	};
	
	/** The UCI <code>"isready"</code> command. */
	public static final CommandResolver<IsReadyCommand> IS_READY = new CommandResolver<>(UciCommands.IS_READY) {
		
		@Override
		public IsReadyCommand resolve(Channel channel) {
			return new IsReadyCommand(channel);
		}
	};
	
	/** The UCI <code>"setoption"</code> command. */
	public static final CommandResolver<SetOptionCommand> SET_OPTION = new CommandResolver<>(UciCommands.SET_OPTION) {
		
		@Override
		public SetOptionCommand resolve(Channel channel) {
			return new SetOptionCommand(channel);
		}
	};
	
	/** The UCI <code>"register"</code> command. */
	public static final CommandResolver<RegisterCommand> REGISTER = new CommandResolver<>(UciCommands.REGISTER) {
		
		@Override
		public RegisterCommand resolve(Channel channel) {
			return new RegisterCommand(channel);
		}
	};
	
	/** The UCI <code>"ucinewgame"</code> command. */
	public static final CommandResolver<UciNewGameCommand> UCI_NEW_GAME = new CommandResolver<>(
		UciCommands.UCI_NEW_GAME
	) {
		
		@Override
		public UciNewGameCommand resolve(Channel channel) {
			return new UciNewGameCommand(channel);
		}
	};
	
	/** The UCI <code>"position"</code> command. */
	public static final CommandResolver<PositionCommand> POSITION = new CommandResolver<>(UciCommands.POSITION) {
		
		@Override
		public PositionCommand resolve(Channel channel) {
			return new PositionCommand(channel);
		}
	};
	
	/** The UCI <code>"go"</code> command. */
	public static final CommandResolver<GoCommand> GO = new CommandResolver<>(UciCommands.GO) {
		
		@Override
		public GoCommand resolve(Channel channel) {
			return new GoCommand(channel);
		}
	};
	
	/** The UCI <code>"stop"</code> command. */
	public static final CommandResolver<StopCommand> STOP = new CommandResolver<>(UciCommands.STOP) {
		
		@Override
		public StopCommand resolve(Channel channel) {
			return new StopCommand(channel);
		}
	};
	
	/** The UCI <code>"ponderhint"</code> command. */
	public static final CommandResolver<PonderHitCommand> PONDER_HIT = new CommandResolver<>(UciCommands.PONDER_HIT) {
		
		@Override
		public PonderHitCommand resolve(Channel channel) {
			return new PonderHitCommand(channel);
		}
	};
	
	/** The UCI <code>"quit"</code> command. */
	public static final CommandResolver<QuitCommand> QUIT = new CommandResolver<>(UciCommands.QUIT) {
		
		@Override
		public QuitCommand resolve(Channel channel) {
			return new QuitCommand(channel);
		}
	};
	
	/** The UCI <code>"id"</code> command. */
	public static final CommandResolver<IdCommand> ID = new CommandResolver<>(UciCommands.ID) {
		
		@Override
		public IdCommand resolve(Channel channel) {
			return new IdCommand(channel);
		}
	};
	
	/** The UCI <code>"uciok"</code> command. */
	public static final CommandResolver<UciOkCommand> UCI_OK = new CommandResolver<>(UciCommands.UCI_OK) {
		
		@Override
		public UciOkCommand resolve(Channel channel) {
			return new UciOkCommand(channel);
		}
	};
	
	/** The UCI <code>"readyok"</code> command. */
	public static final CommandResolver<ReadyOkCommand> READY_OK = new CommandResolver<>(UciCommands.READY_OK) {
		
		@Override
		public ReadyOkCommand resolve(Channel channel) {
			return new ReadyOkCommand(channel);
		}
	};
	
	/** The UCI <code>"bestmove"</code> command. */
	public static final CommandResolver<BestMoveCommand> BEST_MOVE = new CommandResolver<>(UciCommands.BEST_MOVE) {
		
		@Override
		public BestMoveCommand resolve(Channel channel) {
			return new BestMoveCommand(channel);
		}
	};
	
	/** The UCI <code>"copyprotection"</code> command. */
	public static final CommandResolver<CopyProtectionCommand> COPY_PROTECTION = new CommandResolver<>(
		UciCommands.COPY_PROTECTION
	) {
		
		@Override
		public CopyProtectionCommand resolve(Channel channel) {
			return new CopyProtectionCommand(channel);
		}
	};
	
	/** The UCI <code>"registration"</code> command. */
	public static final CommandResolver<RegistrationCommand> REGISTRATION = new CommandResolver<>(
		UciCommands.REGISTRATION
	) {
		
		@Override
		public RegistrationCommand resolve(Channel channel) {
			return new RegistrationCommand(channel);
		}
	};
	
	/** The UCI <code>"info"</code> command. */
	public static final CommandResolver<InfoCommand> INFO = new CommandResolver<>(UciCommands.INFO) {
		
		@Override
		public InfoCommand resolve(Channel channel) {
			return new InfoCommand(channel);
		}
	};
	
	/** The UCI <code>"option"</code> command. */
	public static final CommandResolver<OptionCommand> OPTION = new CommandResolver<>(UciCommands.OPTION) {
		
		@Override
		public OptionCommand resolve(Channel channel) {
			return new OptionCommand(channel);
		}
	};
	
	/** The command name. */
	private final String commandName;
	
	@SuppressWarnings("javadoc")
	protected CommandResolver(String commandName) {
		this.commandName = commandName;
	}
	
	/**
	 * Retrieves the UCI command name as described in the UCI specification.
	 * 
	 * @return The command name.
	 */
	public String getCommandName() {
		return commandName;
	}
	
	public abstract T resolve(Channel channel);
	
	/**
	 * Retrieves the command resolver based in the command name.
	 * 
	 * @param uciCommandName The command name.
	 * 
	 * @return The command resolver.
	 * 
	 * @throws IllegalArgumentException If the given name is invalid or not recognized as a UCI
	 *         command.
	 */
	public static CommandResolver findResolver(String uciCommandName) {
		return null;
	}
}
