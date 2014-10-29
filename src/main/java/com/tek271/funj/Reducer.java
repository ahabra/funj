package com.tek271.funj;

import com.google.common.annotations.Beta;
import com.google.common.collect.Lists;

import java.util.List;

import static com.tek271.funj.CollectionTools.isEmpty;

@Beta
public class Reducer {

	/**
	 * interface to be implemented by reduce functions
	 * @param <T> type of value to reduce.
	 */
	public static interface ReduceFunction<T> {
		T reduceValue(T buffer, T value);
	}

	/**
	 * Left reduce the given iterable into a single value using the given reduceFunction
	 * @param iterable values to reduce starting at the first item
	 * @param reduceFunction the function to apply on each item
	 * @param initialValue the start value for the reduction
	 * @param <T> Type of values in iterable
	 * @return the reduced value
	 */
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

	/**
	 * Right reduce the given iterable into a single value using the given reduceFunction
	 * @param iterable values to reduce starting at the last item
	 * @param reduceFunction the function to apply on each item
	 * @param initialValue the start value for the reduction
	 * @param <T> Type of values in iterable
	 * @return the reduced value
	 */
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
