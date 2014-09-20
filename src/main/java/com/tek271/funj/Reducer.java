package com.tek271.funj;

import com.google.common.collect.Lists;

import java.util.List;

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

	public static <T> T reduceRight(Iterable<T> iterable,
														 ReduceFunction<T> reduceFunction,
														 T initialValue) {
		if (isEmpty(iterable)) return initialValue;

		List<T> list = Lists.reverse(Lists.newLinkedList(iterable));

		T r = initialValue;
		for (T i:list) {
			r = reduceFunction.reduceValue(r, i);
		}
		return r;
	}


}
