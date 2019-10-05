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
package com.welyab.anjabachen.ui;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.welyab.anjabachen.movement.Position;

public class UiBoardTest {
	
	@Test
	public void getSquareCenterRelativeTo()
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		UiBoard uiBoard = createDefaultUiBoard();
		uiBoard.setSize(80, 80);
		Position position = null;
		
		assertEquals(
			Position.of(0, 0),
			callPrivate(
				uiBoard,
				"getSquarePositionRelativeTo",
				Arrays.asList(double.class, double.class),
				Arrays.asList(0.0, 0.0)
			)
		);
		
		assertEquals(
			Position.of(0, 0),
			callPrivate(
				uiBoard,
				"getSquarePositionRelativeTo",
				Arrays.asList(double.class, double.class),
				Arrays.asList(5.0, 5.0)
			)
		);
		
		assertEquals(
			Position.of(0, 0),
			callPrivate(
				uiBoard,
				"getSquarePositionRelativeTo",
				Arrays.asList(double.class, double.class),
				Arrays.asList(9.999, 9.999)
			)
		);
		
		assertEquals(
			Position.of(0, 0),
			callPrivate(
				uiBoard,
				"getSquarePositionRelativeTo",
				Arrays.asList(double.class, double.class),
				Arrays.asList(9.0, 9.0)
			)
		);
		
		assertEquals(
			Position.of(7, 7),
			callPrivate(
				uiBoard,
				"getSquarePositionRelativeTo",
				Arrays.asList(double.class, double.class),
				Arrays.asList(70.0, 70.0)
			)
		);
		
		assertEquals(
			Position.of(7, 7),
			callPrivate(
				uiBoard,
				"getSquarePositionRelativeTo",
				Arrays.asList(double.class, double.class),
				Arrays.asList(75.0, 75.0)
			)
		);
		
		assertEquals(
			Position.of(7, 7),
			callPrivate(
				uiBoard,
				"getSquarePositionRelativeTo",
				Arrays.asList(double.class, double.class),
				Arrays.asList(79.999, 79.999)
			)
		);
	}
	
	private UiBoard createDefaultUiBoard() {
		UiBoard uiBoard = new UiBoard();
		return uiBoard;
	}
	
	public <E> E callPrivate(
			Object obj,
			String methodName,
			List<Class<?>> parameterTypes,
			List<Object> parameters
	) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method declaredMethod = obj.getClass()
			.getDeclaredMethod(
				methodName,
				parameterTypes.toArray(new Class[] {})
			);
		declaredMethod.setAccessible(true);
		return (E) declaredMethod.invoke(obj, parameters.toArray());
	}
}
