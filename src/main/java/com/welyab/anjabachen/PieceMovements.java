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

import java.util.Iterator;
import java.util.List;

public class PieceMovements implements Iterable<PieceMovement> {

	private List<PieceMovement> movements;

	private PieceMovementMeta meta;

	public PieceMovements(List<PieceMovement> movements, PieceMovementMeta meta) {
		this.movements = movements;
		this.meta = meta;
	}

	public boolean isEmpty() {
		return movements.isEmpty();
	}

	public PieceMovement get(int index) {
		return movements.get(index);
	}

	public int size() {
		return movements.size();
	}
	
	public PieceMovementMeta getMeta() {
		return meta;
	}

	@Override
	public Iterator<PieceMovement> iterator() {
		return movements.iterator();
	}
}
