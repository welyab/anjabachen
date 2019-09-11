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

import java.awt.Point;
import java.awt.geom.Point2D;

public class MouseState extends Point2D {
	
	private double x;
	
	private double y;
	
	private boolean overComponent;
	
	private boolean pressed;
	
	private boolean released;
	
	private Point pressedLocation;
	
	private Point releasedLocation;
	
	public MouseState() {
		x = java.lang.Double.MAX_VALUE;
		y = java.lang.Double.MAX_VALUE;
		overComponent = false;
		pressed = false;
		released = true;
		pressedLocation = null;
		releasedLocation = null;
	}
	
	@Override
	public double getX() {
		return x;
	}
	
	@Override
	public double getY() {
		return y;
	}
	
	@Override
	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean isOverComponent() {
		return overComponent;
	}
	
	public void setOverComponent(boolean overComponent) {
		this.overComponent = overComponent;
	}
	
	public boolean isPressed() {
		return pressed;
	}
	
	public void setPressed(boolean mousePressed) {
		this.pressed = mousePressed;
	}
	
	public boolean isReleased() {
		return released;
	}
	
	public void setReleased(boolean mouseReleased) {
		this.released = mouseReleased;
	}
	
	public Point getPressedLocation() {
		return pressedLocation;
	}
	
	public void setPressedLocation(Point pressedLocation) {
		this.pressedLocation = pressedLocation;
	}
	
	public Point getReleasedLocation() {
		return releasedLocation;
	}
	
	public void setReleasedLocation(Point releasedLocation) {
		this.releasedLocation = releasedLocation;
	}
}
