package com.tek271.funj;

import com.google.common.collect.Multimap;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

public class CollectionToolsTest {
	private static final String NOT_EXIST = "propertyNameWhichDoesNotExist";

	final Zoo z1 = Zoo.createZoo(1);
	final Zoo z2 = Zoo.createZoo(2);
	final Zoo z3 = Zoo.createZoo(3);
	final Zoo z4 = Zoo.createZoo(4);
	final List<Zoo> zoos = newArrayList(z1, z2, z3, z4);

	@Test
	public void toMapReturnsEmptyMapIfIterableIsEmpty() {
		assertEquals(true, CollectionTools.toMap(null, "id").isEmpty());
		assertEquals(true, CollectionTools.toMap(newArrayList(), "id").isEmpty());
	}

	@Test(expected = IllegalArgumentException.class)
	public void toMapFailsIfBadKeyPropertyName() {
		CollectionTools.toMap(zoos, NOT_EXIST);
	}

	@Test
	public void toMapReturnsMapKeyedByProperty() {
		Map<Integer,Zoo> map = CollectionTools.toMap(zoos, "id");
		assertEquals(zoos.size(), map.size());
		assertEquals(z1, map.get(1));
		assertEquals(z2, map.get(2));
		assertEquals(z3, map.get(3));
		assertEquals(z4, map.get(4));
	}

	@Test
	public void toMapReturnsMapKeyedByNestedProperty() {
		Map<Integer,Zoo> map = CollectionTools.toMap(zoos, "cat.color");
		assertEquals(zoos.size(), map.size());
	}

	@Test
	public void toMapCanUseMapObjects() {
		List<Map<String, Object>> list = Zoo.toListOfMaps(z1, z2);
		Map<Integer, Map<String, Object>> map = CollectionTools.toMap(list, "id");
		assertEquals(2, map.size());
		assertEquals(list.get(0), map.get(1));
		assertEquals(list.get(1), map.get(2));
	}

	@Test
	public void toMultimapReturnsEmptyMapIfIterableIsEmpty() {
		assertEquals(true, CollectionTools.toMultimap(null, "id").isEmpty());
		assertEquals(true, CollectionTools.toMultimap(newArrayList(), "id").isEmpty());
	}

	@Test(expected = IllegalArgumentException.class)
	public void toMultimapFailsIfBadKeyPropertyName() {
		CollectionTools.toMultimap(zoos, NOT_EXIST);
	}

	@Test
	public void toMultimapReturnsMultimapKeyedByProperty() {
		List<Zoo> z = newArrayList(z1, z2);
		Multimap<Integer,Zoo> multimap = CollectionTools.toMultimap(z, "id");
		assertEquals(2, multimap.size());
		assertEquals(newArrayList(z1), multimap.get(1));
		assertEquals(newArrayList(z2), multimap.get(2));
	}


}
