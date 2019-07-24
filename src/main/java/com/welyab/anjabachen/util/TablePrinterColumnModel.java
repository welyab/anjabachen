package com.welyab.anjabachen.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * This is a auxiliary class to serve as abstraction to the data source for the column names.
 *
 * @author Welyab Paula
 */
public interface TablePrinterColumnModel extends Iterable<String> {

	/**
	 * Retrieve the total number of columns provided by this <code>TablePrinterColumnModel</code>.
	 *
	 * @return The total number of columns.
	 */
	public int columnCount();

	/**
	 * Retrieves the column name for the specific column number.
	 *
	 * @param columnNumber The zero based index of column.
	 *
	 * @return The column name.
	 */
	public String getColumnName(int columnNumber);

	/**
	 * Creates a stream of column names, starting in the column at index <code>0</code>.
	 *
	 * @return The stream of column names.
	 */
	public default Stream<String> stream() {
		return StreamSupport.stream(
			Spliterators.spliterator(iterator(), columnCount(), Spliterator.ORDERED),
			false
		);
	}

	@Override
	public default Iterator<String> iterator() {
		return new Iterator<>() {

			int currentIndex = 0;

			@Override
			public String next() {
				if (!hasNext()) {
					throw new NoSuchElementException("No more columns");
				}
				return getColumnName(currentIndex++);
			}

			@Override
			public boolean hasNext() {
				return currentIndex < columnCount();
			}
		};
	}
}
