package com.tek271.funj;

import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.tek271.funj.Zoo.Cat;
import static org.junit.Assert.assertEquals;

public class TransformerTest {

	final Zoo z1 = Zoo.createZoo(1);
	final Zoo z2 = Zoo.createZoo(2);
	final Zoo z3 = Zoo.createZoo(3);
	final Zoo z4 = Zoo.createZoo(4);
	final List<Zoo> zoos = newArrayList(z1, z2, z3, z4);

	@Test
	public void simpleTransform() {
		StepFunction fun = StepFunction.create()
				.staticClass(this.getClass())
				.functionName("mapZooId");

		Transformer transformer = Transformer.create();
		transformer.addSteps(fun);
		List<Integer> transformed = transformer.apply(zoos);

		assertEquals(newArrayList(10, 20, 30, 40), transformed);
	}

	@Test
	public void testIgnoreNulls() {
		StepFunction fun = StepFunction.create()
				.dynamicContext(this)
				.functionName("mapZooIdConditionally")
				.extraArgs(3)
				.ignoreNulls();

		Transformer transformer = Transformer.create();
		transformer.addSteps(fun);
		List<Integer> transformed = transformer.apply(zoos);

		assertEquals(newArrayList(10, 20, 40), transformed);
	}

	@Test
	public void testIncludeNulls() {
		StepFunction fun = StepFunction.create()
				.dynamicContext(this)
				.functionName("mapZooIdConditionally")
				.extraArgs(3)
				.ignoreNulls(false);

		Transformer transformer = Transformer.create();
		transformer.addSteps(fun);
		List<Integer> transformed = transformer.apply(zoos);

		assertEquals(newArrayList(10, 20, null, 40), transformed);
	}

	@Test
	public void testExtraArgs() {
		StepFunction fun = StepFunction.create()
				.dynamicContext(this)
				.functionName("cityNameCallback")
				.extraArgs("city_1", 2)
				.ignoreNulls();

		Transformer transformer = Transformer.create();
		transformer.addSteps(fun);
		List<String> transformed = transformer.apply(zoos);

		assertEquals(newArrayList( "city_3", "city_4"), transformed);
	}

	@Test
	public void testChaining() {
		StepFunction catOfZoo = StepFunction.create()
				.dynamicContext(this)
				.functionName("catOfZoo");

		StepFunction colorOfCat = StepFunction.create()
				.dynamicContext(this)
				.functionName("colorOfCat");

		StepFunction colorFilter = StepFunction.create()
				.dynamicContext(this)
				.functionName("isNiceColor")
				.filterFunction(true)
				.extraArgs("color_1");

		Transformer transformer = Transformer.create();
		transformer.addSteps(catOfZoo, colorOfCat, colorFilter);

		List<String> transformed = transformer.apply(zoos);

		List<String> expected = newArrayList("color_1", "color_4");
		assertEquals(expected, transformed);
	}

	@SuppressWarnings("UnusedDeclaration")
	public static int mapZooId(Zoo zoo) {
		return zoo.id * 10;
	}


	@SuppressWarnings("UnusedDeclaration")
	public Integer mapZooIdConditionally(Zoo zoo, Integer idForNull) {
		if (zoo.id == idForNull) {
			return null;
		}
		return zoo.id * 10;
	}

	@SuppressWarnings("UnusedDeclaration")
	public String cityNameCallback(Zoo zoo, String excludedCity, int excludedId) {
		if (zoo.id==excludedId || zoo.getCity().equals(excludedCity)) {
			return null;
		}
		return zoo.getCity();
	}

	@SuppressWarnings("UnusedDeclaration")
	public Cat catOfZoo(Zoo zoo) {
		return zoo.cat;
	}

	@SuppressWarnings("UnusedDeclaration")
	public String colorOfCat(Cat cat) {
		return cat.color;
	}

	@SuppressWarnings("UnusedDeclaration")
	public boolean isNiceColor(String color, String specialColor) {
		return color.equals(specialColor) || color.endsWith("4");
	}


}
