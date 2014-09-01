package com.tek271.funj;

import java.util.Arrays;

public class ArrayTools {

	/**
	 * Concat the given first param with array
	 * @param first the 1st element of returned array
	 * @param array remained of the returned array
	 * @return an array containing first followed by elements of array
	 */
	public static Object[] concat(Object first, Object[] array) {
		final int size = size(array);
		if (size==0) {
			return new Object[] {first};
		}

		array = Arrays.copyOf(array, size + 1, Object[].class);
		System.arraycopy(array, 0, array, 1, size);
		array[0] = first;
		return array;
	}

	/**
	 * Return the size of the given array, returns zero for null array
	 */
	public static <T> int size(T[] array) {
		if (array==null) {
			return 0;
		}
		return array.length;
	}

}
