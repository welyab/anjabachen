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
package com.welyab.anjabachen.util.sync;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;

/**
 * Collection of methods to execute synchronized tasks.
 * 
 * @author Welyab Paula
 */
public class SyncBlock {
	
	@SuppressWarnings("javadoc")
	private SyncBlock() {
	}
	
	/**
	 * Executes the given task inside a synchronized bloc, as follow:
	 * 
	 * <pre>
	 * try {
	 *   lock.lock();
	 *   return task.call();
	 * } finally {
	 *   lock.unlock();
	 * }
	 * </pre>
	 * 
	 * @param <E> The return type of being executing task.
	 * @param lock The lock.
	 * @param task The task to be executed.
	 * 
	 * @return The value returned by given task.
	 * 
	 * @throws Exception The exception thrown by the given task.
	 */
	public static <E> E exec(Lock lock, Callable<E> task) throws Exception {
		try {
			lock.lock();
			return task.call();
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * Executes the given task inside a synchronized bloc, as follow:
	 * 
	 * <pre>
	 * try {
	 *   lock.lock();
	 *   return task.call();
	 * } finally {
	 *   lock.unlock();
	 * }
	 * </pre>
	 * 
	 * @param lock The lock.
	 * @param task The task to be executed.
	 * 
	 * @throws Exception The exception thrown by the given task.
	 */
	public static void exec(Lock lock, Runnable2 task) throws Exception {
		try {
			lock.lock();
			task.run();
		} finally {
			lock.unlock();
		}
	}
}
