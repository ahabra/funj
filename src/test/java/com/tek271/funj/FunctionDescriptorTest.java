package com.tek271.funj;

import org.junit.Test;

import static com.tek271.funj.FunctionDescriptor.dynamicFunction;
import static com.tek271.funj.FunctionDescriptor.staticFunction;
import static org.junit.Assert.assertEquals;

public class FunctionDescriptorTest {

	@Test(expected = IllegalArgumentException.class)
	public void dynamicFunctionFailsIfContextIsNull() {
		dynamicFunction(null, "f");
	}

	@Test
	public void dynamicFunctionCanBeCalled() {
		FunctionDescriptor func = dynamicFunction(this, "dynaFunc");
		Integer c = func.call(1, 2);
		assertEquals(3, c.intValue());
	}

	@Test(expected = IllegalArgumentException.class)
	public void staticFunctionFailsIfPathIsNull() {
		staticFunction((String) null, "f");
	}

	@Test(expected = IllegalArgumentException.class)
	public void staticFunctionFailsIfPathIsEmpty() {
		staticFunction("", "f");
	}

	@Test(expected = IllegalArgumentException.class)
	public void staticFunctionFailsIfClassIsNull() {
		staticFunction((Class) null, "f");
	}

	@Test
	public void staticFunctionCanBeCalled() {
		FunctionDescriptor func = staticFunction(this.getClass(), "staticFunc");
		assertEquals("ab", func.call("a", "b"));
	}

	@Test
	public void staticFunctionWithFillNameCanBeCalled() {
		String fullName = this.getClass().getName() + ".staticFunc";
		FunctionDescriptor func = staticFunction(fullName);
		assertEquals("ab", func.call("a", "b"));
	}

	@Test
	public void toStringWithStaticFunction() {
		FunctionDescriptor func = staticFunction(this.getClass(), "staticFunc");
		assertEquals("static com.tek271.funj.FunctionDescriptorTest.staticFunc", func.toString());
	}

	@Test
	public void toStringWithDynamicFunction() {
		FunctionDescriptor func = dynamicFunction(this, "dynaFunc");
		assertEquals("com.tek271.funj.FunctionDescriptorTest.dynaFunc", func.toString());
	}

	@SuppressWarnings("UnusedDeclaration")
	public int dynaFunc(int a, int b) {
		return a+b;
	}

	@SuppressWarnings("UnusedDeclaration")
	public static String staticFunc(String a, String b) {
		return a + b;
	}

}
