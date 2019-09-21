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
