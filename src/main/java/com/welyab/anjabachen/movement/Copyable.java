package com.welyab.anjabachen.movement;

public interface Copyable<T extends Copyable<T>> {
	
	T copy();
}
