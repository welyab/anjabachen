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

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class PieceMovements implements Iterable<MovementTarget> {
	
	private final byte pieceCode;
	
	private final Position origin;
	
	private final List<MovementTarget> targets;
	
	private final MovementMetadata metadata;
	
	public PieceMovements(
		Position originPosition,
		byte pieceCode,
		List<MovementTarget> targets
	) {
		this(
			originPosition,
			pieceCode,
			targets,
			MovementMetadata.empty()
		);
	}
	
	public PieceMovements(
		Position originPosition,
		byte pieceCode,
		List<MovementTarget> targets,
		MovementMetadata metadata
	) {
		this.origin = originPosition;
		this.pieceCode = pieceCode;
		this.targets = targets;
		this.metadata = metadata;
	}
	
	public byte getPieceCode() {
		return pieceCode;
	}
	
	public MovementTarget getTarget(int index) {
		return targets.get(index);
	}
	
	public MovementTarget getTargetRandom() {
		return targets.get(MovementUtil.RDN.nextInt(targets.size()));
	}
	
	public Position getOrigin() {
		return origin;
	}
	
	public boolean isEmpty() {
		return targets.isEmpty();
	}
	
	public int getTargertsCount() {
		return targets.size();
	}
	
	public Stream<MovementTarget> streamTargets() {
		return targets.stream();
	}
	
	@Override
	public Iterator<MovementTarget> iterator() {
		return targets.iterator();
	}
	
	public MovementMetadata getMetadata() {
		return metadata;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(
			String.format(
				"from %c%s to [",
				MovementUtil.pieceCodeToLetter(pieceCode),
				origin.getNotation()
			)
		);
		for (int i = 0; i < targets.size(); i++) {
			if (i > 0) {
				builder.append(", ");
			}
			MovementTarget target = targets.get(i);
			builder.append(target);
		}
		return builder.append("]").toString();
	}
}
