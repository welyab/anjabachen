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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class Movements implements Iterable<PieceMovements> {
	
	private final List<PieceMovements> pieceMovements;
	
	private final MovementMetadata metadata;
	
	public Movements(List<PieceMovements> pieceMovements) {
		this(pieceMovements, MovementMetadata.empty());
	}
	
	public Movements(List<PieceMovements> pieceMovements, MovementMetadata metadata) {
		this.pieceMovements = pieceMovements;
		this.metadata = metadata;
	}
	
	public PieceMovements getPieceMovements(int index) {
		return pieceMovements.get(index);
	}
	
	public MovementMetadata getMetadata() {
		return metadata;
	}
	
	public int getOriginCount() {
		return pieceMovements.size();
	}
	
	public boolean isEmpty() {
		return pieceMovements.isEmpty();
	}
	
	public Stream<PieceMovements> streamOrigins() {
		return pieceMovements.stream();
	}
	
	public Stream<Movement> streamMovements() {
		return streamOrigins()
			.flatMap(o -> o.streamTargets().map(t -> new Movement(o.getOrigin(), t)));
	}
	
	public Stream<MovementTarget> streamTargets() {
		return pieceMovements
			.stream()
			.flatMap(PieceMovements::streamTargets);
	}
	
	@Override
	public Iterator<PieceMovements> iterator() {
		return pieceMovements.iterator();
	}
	
	public Movements merge(Movements movements) {
		List<PieceMovements> list = new ArrayList<>(
			pieceMovements.size() + movements.pieceMovements.size()
		);
		list.addAll(pieceMovements);
		list.addAll(movements.pieceMovements);
		return new Movements(
			list,
			MovementMetadata
				.builder()
				.add(getMetadata())
				.add(movements.getMetadata())
				.buid()
		);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < pieceMovements.size(); i++) {
			PieceMovements pm = pieceMovements.get(i);
			if (i > 0) {
				builder.append(", ");
			}
			builder.append(pm.toString());
		}
		return builder.toString();
	}
}
