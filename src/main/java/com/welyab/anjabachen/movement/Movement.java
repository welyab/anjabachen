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

/**
 * The <code>Movement</code> is a helper class that aggregates a pair of
 * <code>MovementOrigin/MovementTarget</code> in order to describe the movement of a piece.
 * 
 * @author Welyab Paula
 */
public class Movement {
	
	/** The movement origin. */
	private final MovementOrigin origin;
	
	/** The movement target. */
	private final MovementTarget target;
	
	/**
	 * Creates a new <code>Movement</code> by specifying the origin and the target square of the
	 * movement.
	 * 
	 * @param origin The movement origin.
	 * @param target The movement target.
	 */
	public Movement(MovementOrigin origin, MovementTarget target) {
		this.origin = origin;
		this.target = target;
	}
	
	/**
	 * Retrieves the origin of this movement.
	 * 
	 * @return The movement origin.
	 */
	public MovementOrigin getOrigin() {
		return origin;
	}
	
	/**
	 * Retrieves the destination of this movement.
	 * 
	 * @return The movement target.
	 */
	public MovementTarget getTarget() {
		return target;
	}
	
	@Override
	public String toString() {
		return origin + " -> " + target;
	}
}
