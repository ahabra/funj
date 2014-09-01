package com.tek271.funj;

import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

public class FinderTest {
	private static final String NOT_EXIST = "propertyNameWhichDoesNotExist";

	final Zoo z1 = Zoo.createZoo(1);
	final Zoo z2 = Zoo.createZoo(2);
	final Zoo z3 = Zoo.createZoo(3);
	final Zoo z4 = Zoo.createZoo(4);
	final List<Zoo> zoos = newArrayList(z1, z2, z3, z4);

	@Test
	public void findAllReturnsEmptyListIfNoData() {
		List<Zoo> list = newArrayList();
		List<Zoo> found = Finder.findAll(list, "id", 1, 2);
		assertEquals(true, found.isEmpty());

		found = Finder.findAll(zoos, "id");
		assertEquals(true, found.isEmpty());
	}

	@Test(expected = IllegalArgumentException.class)
	public void findAllReturnsFailsIfBadPropertyName() {
		Finder.findAll(zoos, NOT_EXIST, 1, 2);
	}

	@Test
	public void findAllReturnsFoundElements() {
		List<Zoo> found = Finder.findAll(zoos, "city", "city_1", "city_3", "city_xx");
		assertEquals(2, found.size());
		assertEquals(z1, found.get(0));
		assertEquals(z3, found.get(1));
	}

	@Test
	public void testFindFirst() {
		assertEquals(z2, Finder.findFirst(zoos, "id", 2));
		assertEquals(null, Finder.findFirst(zoos, "id", "2"));
		assertEquals(null, Finder.findFirst(zoos, "id", 100));
		assertEquals(null, Finder.findFirst(zoos, "id", "invalid filter"));
	}

	@Test
	public void rejectWillExcludeMatchingObjects() {
		List<Zoo> found = Finder.reject(zoos, "id", 1, 2);
		assertEquals(newArrayList(z3, z4), found);
	}

	@Test
	public void rejectWillReturnOriginalListIfNoMatchers() {
		List<Zoo> found = Finder.reject(zoos, "id");
		assertEquals(zoos, found);
	}

}
