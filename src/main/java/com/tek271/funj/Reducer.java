package com.tek271.funj;

import static com.tek271.funj.CollectionTools.isEmpty;

public class Reducer {
	public static interface ReduceFunction<T> {
		T reduceValue(T buffer, T value);
	}

	public static <T> T reduce(Iterable<T> iterable,
														 ReduceFunction<T> reduceFunction,
														 T initialValue) {
		if (isEmpty(iterable)) return initialValue;

		T r = initialValue;
		for (T i: iterable) {
			r = reduceFunction.reduceValue(r, i);
		}
		return r;
	}

}
