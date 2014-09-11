package com.tek271.funj;

import com.google.common.collect.Lists;

import static com.tek271.funj.Reducer.ReduceFunction;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.List;

public class ReducerTest {

	@Test
	public void reduceAppliesFunctionOnList() {
		List<Integer> list = Lists.newArrayList(1, 2, 3);
		Integer sum = Reducer.reduce(list, new Adder(), 0);
		assertEquals(6, sum.intValue());
	}

	@Test
	public void reduceReturnsInitialValueIfListIsEmpty() {
		List<Integer> list = Lists.newArrayList();
		Integer sum = Reducer.reduce(list, new Adder(), 0);
		assertEquals(0, sum.intValue());
	}

	@Test
	public void reduceReturnsInitialValueIfListIsNull() {
		List<Integer> list = null;
		Integer sum = Reducer.reduce(list, new Adder(), 0);
		assertEquals(0, sum.intValue());
	}

	public class Adder implements ReduceFunction<Integer> {
		@Override
		public Integer reduceValue(Integer buffer, Integer value) {
			return buffer + value;
		}
	}

}
