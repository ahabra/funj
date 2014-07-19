package com.tek271.funj;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

public class MapperTest {
	private static final String NOT_EXIST = "propertyNameWhichDoesNotExist";

	final Zoo z1 = Zoo.createZoo(1);
	final Zoo z2 = Zoo.createZoo(2);
	final Zoo z3 = Zoo.createZoo(3);
	final Zoo z4 = Zoo.createZoo(4);
	final List<Zoo> zoos = newArrayList(z1, z2, z3, z4);


	@Test
	public void pluckReturnsEmptyListIfNoData() {
		List<Zoo> list = newArrayList();
		assertEquals(true, Mapper.pluck(list, "id").isEmpty());
		assertEquals(true, Mapper.pluck(null, "id").isEmpty());
	}

	@Test(expected = IllegalArgumentException.class)
	public void pluckFailsWithBadPropertyName() {
		Mapper.pluck(zoos, NOT_EXIST);
	}

	@Test
	public void pluckExtractsGivenProperty() {
		List<Integer> list = Mapper.pluck(zoos, "id");
		assertEquals(Lists.newArrayList(1, 2, 3, 4), list);
	}

	@Test
	public void mapWithStaticCallbackFullNameReturnsListOfMappedValues() {
		List<Integer> mapped = Mapper.map(zoos, this.getClass().getName() + ".mapZooId");
		assertEquals(newArrayList(10, 20, 30, 40), mapped);
	}

	@SuppressWarnings("UnusedDeclaration")
	public static int mapZooId(Zoo zoo) {
		return zoo.id * 10;
	}

	@Test
	public void mapWithStaticCallbackReturnsListOfMappedValues() {
		List<Integer> mapped = Mapper.map(zoos, this.getClass(), "mapZooId");
		assertEquals(newArrayList(10, 20, 30, 40), mapped);
	}

	@Test
	public void mapWillReturnsListOfMappedValues() {
		List<String> mapped = Mapper.map(zoos, this, "catColor");
		assertEquals(newArrayList("color_1", "color_2","color_3","color_4"), mapped);
	}

	@SuppressWarnings("UnusedDeclaration")
	public String catColor(Zoo zoo) {
		return zoo.cat.color;
	}

}
