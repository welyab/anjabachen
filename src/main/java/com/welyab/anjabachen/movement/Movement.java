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

public class Movement {
	
	private final Position origin;
	
	private final MovementTarget movementTarget;
	
	public Movement(Position origin, MovementTarget movementTarget) {
		this.origin = origin;
		this.movementTarget = movementTarget;
	}
	
	public Position getOrigin() {
		return origin;
	}
	
	public MovementTarget getTarget() {
		return movementTarget;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("from ")
			.append(origin.getNotation())
			.append(" to ")
			.append(movementTarget);
		return builder.toString();
	}
}
