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
package com.welyab.anjabachen.engine;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.welyab.anjabachen.fen.FenParser;
import com.welyab.anjabachen.movement.Board;
import com.welyab.anjabachen.movement.BoardUtils;
import com.welyab.anjabachen.movement.SimpleMovement;
import com.welyab.anjabachen.uci.cmd.CommandResolver;
import com.welyab.anjabachen.uci.cmd.GoCommandParameters;
import com.welyab.anjabachen.uci.cmd.IdCommandParameter;
import com.welyab.anjabachen.uci.cmd.IdCommandParameterType;
import com.welyab.anjabachen.uci.cmd.NullValue;
import com.welyab.anjabachen.ui.concurrent.NonReturningOperation;
import com.welyab.anjabachen.ui.concurrent.SyncOp;

public class Engine {
	
	/** Default value for the <code>debug</code> mode. */
	private static final boolean DEFAULT_DEBUG_ENABLED = false;
	
	/** This executor service is used for execute outcoming UCI commands. */
	private ExecutorService outcomingCommandExecutor = Executors.newFixedThreadPool(1);
	
	/** This executor service is used for execute incoming UCI commands. */
	private ExecutorService incomingCommandExecutor = Executors.newFixedThreadPool(1);
	
	private final EngineConfig engineConfig;
	
	private Board board;
	
	/**
	 * A counter to count the incoming commands in the execution queue, including the command
	 * currently in execution.
	 * 
	 * <p>
	 * This field is incremented and decremented in the method
	 * {@link #executingIncomingCommandsCounter}.
	 * 
	 * @see #runIncomingCommandAsync(NonReturningOperation)
	 */
	private int executingIncomingCommandsCounter;
	
	private List<NonReturningOperation> allCommandsExecutedListeners;
	
	/** The lock. */
	private final ReadWriteLock lock = new ReentrantReadWriteLock(true);
	
	/** The options used by the engine. */
	private final EnumMap<EngineOption, Object> options;
	
	/** Indicates if the debug mode is enabled, or not. */
	private boolean debugEnabled;
	
	private final TreeWalker walker;
	
	public Engine(EngineConfig engineConfig) {
		this.engineConfig = engineConfig;
		debugEnabled = DEFAULT_DEBUG_ENABLED;
		options = new EnumMap<>(EngineOption.class);
		allCommandsExecutedListeners = new ArrayList<>();
		walker = new TreeWalker();
	}
	
	public static void main(String[] args) {
		EngineConfig engineConfig = new EngineConfig(new StdOutChannel());
		Engine engine = new Engine(engineConfig);
		engine.uciCommand();
	}
	
	// --------------------------------------------------------------------------------
	// methods to receive commands from GUI
	// --------------------------------------------------------------------------------
	
	/**
	 * Submits the UCI <code>"uci"</code> command to the engine.
	 */
	public void uciCommand() {
		runIncomingCommandAsync(
			() -> SyncOp.write(
				lock, () -> {
					sendIdCommand();
					sendAvailableOptions();
					sendUciOkCommand();
				}
			)
		);
	}
	
	public void uciNewGameCommand() {
		runIncomingCommandAsync(() -> {
			boolean walkerRunning = SyncOp.read(lock, walker::isRunning);
			if (walkerRunning) {
				return;
			}
			SyncOp.write(lock, walker::clear);
		});
	}
	
	public void stopCommand() {
		runIncomingCommandAsync(() -> SyncOp.write(lock, () -> {
			if (!walker.isRunning()) {
				return;
			}
			walker.stop(() -> {
			});
		}));
	}
	
	/**
	 * Submits the UCI <code>"debug"</code> command to the engine.
	 * 
	 * @param flag If the <i>debug</i> option should be enabled or not.
	 */
	public void debugCommand(boolean flag) {
		runIncomingCommandAsync(
			() -> SyncOp.write(
				lock, () -> debugEnabled = flag
			)
		);
	}
	
	/**
	 * Submits the UCI <code>"isready"</code> command to the engine.
	 */
	public void isReadyCommand() {
		runIncomingCommandAsync(
			() -> SyncOp.write(
				lock, () -> allCommandsExecutedListeners.add(
					Engine.this::sendReadyOkCommand
				)
			)
		);
	}
	
	/**
	 * Submits the UCI <code>"setoption"</code> command to the engine.
	 * 
	 * @param optionValue
	 */
	public void setOptionCommand(EngineOptionValue optionValue) {
		runIncomingCommandAsync(
			() -> SyncOp.write(
				lock, () -> options.put(
					optionValue.getEngineOption(),
					optionValue.getValue()
				)
			)
		);
	}
	
	public void goCommand(GoCommandParameters parameters) {
		runIncomingCommandAsync(
			() -> SyncOp.write(
				lock, () -> {
					walker.setPonder(parameters.isPonder());
					walker.setInfinite(parameters.isInfinite());
					walker.setWhiteTime(parameters.getWhiteTime());
					walker.setBlackTime(parameters.getBlackTime());
					walker.setWhiteIncrement(parameters.getWhiteIncrement());
					walker.setBlackIncrement(parameters.getBlackIncrement());
					walker.setMovesToGo(parameters.getMovesToGo());
					walker.setDepthLimit(parameters.getDepthLimit());
					walker.setNodesLimit(parameters.getNodesLimit());
					walker.setTimeLimit(parameters.getTimeLimit());
				}
			)
		);
	}
	
	/**
	 * Submits the UCI <code>"position"</code> command to the engine. This method set the pieces to
	 * the start disposition.
	 * 
	 * @param movements The list of movement to be played after the board's start position have been
	 *        set. May be an empty list.
	 * 
	 * @see #positionCommand(String, List)
	 * 
	 * @throws NullPointerException If the given list is <code>null</code>.
	 */
	public void positionCommand(List<SimpleMovement> movements) {
		positionCommand(BoardUtils.FEN_INITIAL_POSITION, movements);
	}
	
	/**
	 * Submits the UCI <code>"position"</code> command to the engine. This method takes the given
	 * FEN string initial as board pieces disposition.
	 * 
	 * @param fen The FEN string.
	 * @param movements The list of movement to be played after the board's start position have been
	 *        set. May be an empty list.
	 * 
	 * @see FenParser
	 * @see #positionCommand(List)
	 * 
	 * @throws NullPointerException If the given list is <code>null</code>.
	 */
	public void positionCommand(String fen, List<SimpleMovement> movements) {
		runIncomingCommandAsync(
			() -> SyncOp.write(
				lock, () -> {
					if (walker.isRunning()) {
						return;
					}
					
					Board board = new Board(fen);
					for (SimpleMovement movement : movements) {
						board.move(
							movement.getOrigin(),
							movement.getTarget(),
							movement.getToPromote()
						);
					}
					walker.setPosition(fen);
				}
			)
		);
	}
	
	// --------------------------------------------------------------------------------
	// finish methods to receive commands from GUI
	// --------------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------------
	// methods to send commands from engine to GUI
	// --------------------------------------------------------------------------------
	
	private void sendAvailableOptions() {
	}
	
	/**
	 * Sends the UCI <code>"readyok"</code> command to the GUI.
	 */
	private void sendReadyOkCommand() {
		runOutcomingCommandAsync(
			() -> CommandResolver.READY_OK
				.resolve(engineConfig.getOutputChannel())
				.call(NullValue.INSTANCE)
		);
	}
	
	/**
	 * Sends the UCI <code>"id"</code> command to the GUI.
	 */
	private void sendIdCommand() {
		runOutcomingCommandAsync(
			() -> {
				CommandResolver.ID
					.resolve(engineConfig.getOutputChannel())
					.call(
						new IdCommandParameter(
							IdCommandParameterType.NAME, "AN.JA.BA.CH.EN (Another Java Based Chess Engine)"
						)
					);
				CommandResolver.ID
					.resolve(engineConfig.getOutputChannel())
					.call(
						new IdCommandParameter(
							IdCommandParameterType.AUTHOR,
							"Welyab da Silva Paula"
						)
					);
			}
		);
	}
	
	/**
	 * Sends the UCI <code>"uciok"</code> command to the GUI.
	 */
	private void sendUciOkCommand() {
		runOutcomingCommandAsync(
			() -> {
				CommandResolver.UCI_OK
					.resolve(engineConfig.getOutputChannel())
					.call(NullValue.INSTANCE);
			}
		);
	}
	
	// --------------------------------------------------------------------------------
	// finish method to send commands from engine to GUI
	// --------------------------------------------------------------------------------
	
	private void runOutcomingCommandAsync(NonReturningOperation operation) {
		outcomingCommandExecutor.execute(() -> {
			try {
				operation.call();
			} catch (Exception e) {
				dispatchException(e);
			}
		});
	}
	
	private void runIncomingCommandAsync(NonReturningOperation operation) {
		incomingCommandExecutor.submit(() -> {
			try {
				SyncOp.write(lock, () -> executingIncomingCommandsCounter++);
				operation.call();
			} catch (Exception e) {
				dispatchException(e);
			} finally {
				SyncOp.write(lock, () -> {
					executingIncomingCommandsCounter--;
					if (executingIncomingCommandsCounter == 0 && !allCommandsExecutedListeners.isEmpty()) {
						fireAllCommandsExecuted(new ArrayList<>(allCommandsExecutedListeners));
						allCommandsExecutedListeners.clear();
					}
				});
			}
		});
	}
	
	private void fireAllCommandsExecuted(List<NonReturningOperation> listeners) {
		for (NonReturningOperation listener : listeners) {
			try {
				listener.call();
			} catch (Exception e) {
				dispatchException(new EngineException(e));
			}
		}
	}
	
	private void dispatchException(Exception exception) {
	}
	
	public static void start(EngineConfig engineBootConfig) {
	}
}
