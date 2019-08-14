package com.welyab.anjabachen;

public class A {
	
	private Position origin;
	
	private Position target;
	
	public A(Position origin, Position target) {
		this.origin = origin;
		this.target = target;
	}
	
	public Position getOrigin() {
		return origin;
	}
	
	public void setOrigin(Position origin) {
		this.origin = origin;
	}
	
	public Position getTarget() {
		return target;
	}
	
	public void setTarget(Position target) {
		this.target = target;
	}
}
