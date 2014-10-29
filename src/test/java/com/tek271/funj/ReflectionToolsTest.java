package com.tek271.funj;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class ReflectionToolsTest {
	private static final String NOT_EXIST = "propertyNameWhichDoesNotExist";
	private final Zoo zoo = Zoo.createZoo(1);

	@Test(expected = IllegalArgumentException.class)
	public void getPropertyValueFailsWhenNullObject() {
		ReflectionTools.getPropertyValue(null, "a");
	}

	@Test(expected = IllegalArgumentException.class)
	public void getPropertyValueFailsWhenPropertyNameIsNull() {
		ReflectionTools.getPropertyValue(this, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getPropertyValueFailsWhenPropertyNameIsEmpty() {
		ReflectionTools.getPropertyValue(this, "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void getPropertyValueFailsWhenPropertyNameIsBlank() {
		ReflectionTools.getPropertyValue(this, "  ");
	}

	@Test(expected = IllegalArgumentException.class)
	public void getPropertyValueFailsWhenPropertyNameIsNotValid() {
		ReflectionTools.getPropertyValue(this, NOT_EXIST);
	}

	@Test
	public void getPropertyCanAccessFields() {
		Integer id = ReflectionTools.getPropertyValue(zoo, "id");
		assertEquals(1, id.intValue());
	}

	@Test
	public void getPropertyCanAccessGetter() {
		String city = ReflectionTools.getPropertyValue(zoo, "city");
		assertEquals("city_1", city);
	}

	@Test
	public void getPropertyCanAccessNestedObjects() {
		String color = ReflectionTools.getPropertyValue(zoo, "cat.color");
		assertEquals("color_1", color);
	}

	@Test
	public void getPropertyCanAccessNestedIndexedObjects() {
		Zoo.Dog dog = ReflectionTools.getPropertyValue(zoo, "dogs[0]");
		assertEquals(zoo.dogs.get(0), dog);
	}

	@Test
	public void getPropertyCanAccessNestedIndexedObjectsFields() {
		String bark = ReflectionTools.getPropertyValue(zoo, "dogs[1].bark");
		assertEquals("bark_2", bark);
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkPropertyExistsFailsIfPropertyDoesNotExist() {
		ReflectionTools.checkPropertyExists(zoo, NOT_EXIST);
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkPropertyExists_IterableFailsIfPropertyDoesNotExist() {
		List<Zoo> list = Lists.newArrayList(zoo);
		ReflectionTools.checkPropertyExists(list, NOT_EXIST);
	}

	@Test
	public void checkPropertiesExistChecksMultipleProperties() {
		ReflectionTools.checkPropertiesExist(zoo, "id", "city");
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkPropertiesFailsIfAnyPropertyNameDoesNotExist() {
		ReflectionTools.checkPropertiesExist(zoo, "id", "cityXX");
	}

	@Test
	public void callStaticCallsSimpleMethodWithNoArgs() {
		Properties actual = ReflectionTools.callStatic("System.getProperties");
		assertEquals(System.getProperties(), actual);
	}

	@Test
	public void callStaticHandlesOptionalParenthesis() {
		Properties actual = ReflectionTools.callStatic("System.getProperties()");
		assertEquals(System.getProperties(), actual);
	}

	@Test
	public void callStaticWillCallMethodWithArgs() {
		String fullFunctionName = this.getClass().getName() + ".add";
		Integer actual = ReflectionTools.callStatic(fullFunctionName, 10, 20);
		assertEquals(30, actual.intValue());
	}

	@SuppressWarnings("UnusedDeclaration")
	public static int add(int a, int b) {
		return a+b;
	}

	@Test
	public void callStaticWithClassWillCallMethodWithArgs() {
		Integer actual = ReflectionTools.callStatic(this.getClass(), "add", 10, 20);
		assertEquals(30, actual.intValue());
	}

	@Test
	public void callMethod() {
		Integer actual = ReflectionTools.callMethod(this, "subt", 10, 4);
		assertEquals(6, actual.intValue());
	}

	@SuppressWarnings("UnusedDeclaration")
	public int subt(int a, int b) {
		return a-b;
	}

}
