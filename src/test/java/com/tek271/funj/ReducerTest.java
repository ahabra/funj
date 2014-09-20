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

	@Test
	public void reduceRightAppliesFunctionAtEndOfList() {
		List<Integer> list = Lists.newArrayList(10, 3, 1);
		Integer sub = Reducer.reduceRight(list, new Substractor(), 16);
		assertEquals(2, sub.intValue());
	}

	@Test
	public void reduceRightReturnsInitialValueIfListIsEmpty() {
		List<Integer> list = Lists.newArrayList();
		Integer sub = Reducer.reduceRight(list, new Substractor(), 16);
		assertEquals(16, sub.intValue());
	}

	@Test
	public void reduceRightReturnsInitialValueIfListIsNull() {
		List<Integer> list = Lists.newArrayList();
		Integer sub = Reducer.reduceRight(list, new Substractor(), 16);
		assertEquals(16, sub.intValue());
	}

	public class Adder implements ReduceFunction<Integer> {
		@Override
		public Integer reduceValue(Integer buffer, Integer value) {
			return buffer + value;
		}
	}

	public class Substractor implements ReduceFunction<Integer> {
		@Override
		public Integer reduceValue(Integer buffer, Integer value) {
			return buffer - value;
		}
	}


}
