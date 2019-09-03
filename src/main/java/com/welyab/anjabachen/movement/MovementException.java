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
package com.welyab.anjabachen.movement;

import com.welyab.anjabachen.ChessException;

/**
 * Indicates problems during piece movement process.
 * 
 * @author Welyab Paula
 */
public class MovementException extends ChessException {
	
	@SuppressWarnings("javadoc")
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("javadoc")
	public MovementException() {
	}
	
	@SuppressWarnings("javadoc")
	public MovementException(String message) {
		super(message);
	}
	
	@SuppressWarnings("javadoc")
	public MovementException(Throwable cause) {
		super(cause);
	}
	
	@SuppressWarnings("javadoc")
	public MovementException(String message, Throwable cause) {
		super(message, cause);
	}
}
