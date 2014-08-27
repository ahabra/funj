package com.tek271.funj;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.mvel2.MVEL;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

public class ReflectionTools {

	/**
	 * get the value of a property
	 * @param obj container object
	 * @param propertyName name of property
	 * @param <OBJ> generic type of container object
	 * @param <PROP> generic type of property
	 * @return the value of the property
	 * @throws IllegalArgumentException if obj is null, or if propertyName is null,
	 * empty, blank, or does not exist.
	 */
	@SuppressWarnings("unchecked")
	public static <OBJ, PROP> PROP getPropertyValue(OBJ obj, String propertyName) {
		if (obj==null) {
			throw new IllegalArgumentException("The container object cannot be null");
		}
		if (StringUtils.isBlank(propertyName)) {
			throw new IllegalArgumentException("propertyName is required");
		}

		try {
			return (PROP) MVEL.eval(propertyName, obj);
		} catch(Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Check if the property exists on the given object
	 * @param obj container object
	 * @param propertyName name of property
	 * @param <OBJ> generic type of container object
	 * @throws IllegalArgumentException if propertyName does not exist
	 */
	public static <OBJ> void checkPropertyExists(OBJ obj, String propertyName) {
		getPropertyValue(obj, propertyName);
	}

	public static <OBJ> void checkPropertiesExist(OBJ obj, String... propertyNames) {
		for (String propertyName: propertyNames) {
			getPropertyValue(obj, propertyName);
		}
	}

	/**
	 * Check if the 1st item in the iterable has the given propertyName
	 * @param iterable some objects
	 * @param propertyName name of property
	 * @param <OBJ> generic type of the iterable element
	 * @throws IllegalArgumentException if propertyName does not exist
	 */
	public static <OBJ> void checkPropertyExists(Iterable<OBJ> iterable, String propertyName) {
		getPropertyValue(iterable.iterator().next(), propertyName);
	}

	public static <OBJ> void checkPropertiesExist(Iterable<OBJ> iterable, String... propertyNames) {
		checkPropertiesExist(iterable.iterator().next(), propertyNames);
	}

	@SuppressWarnings("unchecked")
	public static <OUT> OUT callStatic(String fullFunctionName, Object... args) {
		Pair<String, Map<String, Object>> pair = buildArgs(args);
		String func = createFunctionCall(fullFunctionName, pair.getLeft());

		return (OUT) MVEL.eval(func, pair.getRight());
	}

	public static <OUT> OUT callStatic(Class cls, String functionName, Object... args) {
		String fullFunctionName = cls.getName() + "." + functionName;
		return callStatic(fullFunctionName, args);
	}

	@SuppressWarnings("unchecked")
	public static <OUT> OUT callMethod(Object context, String functionName, Object... args) {
		Pair<String, Map<String, Object>> pair = buildArgs(args);
		String func = createFunctionCall(functionName, pair.getLeft());

		return (OUT) MVEL.eval(func, context, pair.getRight());
	}

	private static String createFunctionCall(String functionName, String args) {
		functionName = removeEndParens(functionName);
		return functionName + "(" + args + ")";
	}

	private static String removeEndParens(String funcName) {
		if (StringUtils.endsWith(funcName, "()")) {
			return StringUtils.left(funcName, funcName.length()-2);
		}
		return funcName;
	}

	private static Pair<String, Map<String, Object>> buildArgs(Object... args) {
		Map<String, Object> map = newHashMap();
		List<String> varNames = newArrayList();

		int i=0;
		for (Object arg: args) {
			String varName = "arg" + i;
			varNames.add(varName);
			map.put(varName, arg);
			i++;
		}
		return Pair.of(Joiner.on(',').join(varNames), map);
	}

}
