package com.tek271.funj;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class ArrayToolsTest {

	@Test
	public void concat_joinsAnElementWithAnArray() {
		Integer[] array = {2, 3};
		Object[] joined = ArrayTools.concat(1, array);
		Integer[] expected = {1, 2, 3};
		assertArrayEquals(expected, joined);
	}

	@Test
	public void concat_joinsDifferentTypes() {
		Integer[] array = {2, 3};
		Object[] joined = ArrayTools.concat("a", array);
		Object[] expected = {"a", 2, 3};
		assertArrayEquals(expected, joined);
	}

	@Test
	public void concat_handlesEmptyArray() {
		Integer[] array = {};
		Object[] joined = ArrayTools.concat("a", array);
		Object[] expected = {"a"};
		assertArrayEquals(expected, joined);
	}

	@Test
	public void concat_handlesNullArray() {
		Integer[] array = null;
		Object[] joined = ArrayTools.concat("a", array);
		Object[] expected = {"a"};
		assertArrayEquals(expected, joined);
	}

}
