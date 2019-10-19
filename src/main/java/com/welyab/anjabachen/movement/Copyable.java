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
 * A <i>Copyable</i> is an element that may be copied. Its state should be copied as well, i.e,
 * changes in the state of copied object should never reflect in the state of the original object.
 * 
 * @author Welyab Paula
 *
 * @param <T> The underlying type of the copyable object.
 */
public interface Copyable<T extends Copyable<T>> {
	
	/**
	 * Creates a copy of this element. Changes in the state of the copied object should never
	 * reflect
	 * in the stated of original object.
	 * 
	 * @return The copy.
	 */
	T copy();
}
