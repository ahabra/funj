package com.tek271.funj;

public class ArrayTools {

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
