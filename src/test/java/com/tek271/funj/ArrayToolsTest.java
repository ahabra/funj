package com.tek271.funj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArrayToolsTest {

	@Test
	public void testSize() {
		assertEquals(0, ArrayTools.size(null));
		assertEquals(0, ArrayTools.size(new Integer[] {}));
		assertEquals(2, ArrayTools.size(new Integer[] {1, 2}));
	}

}
