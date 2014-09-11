package com.tek271.funj;

import com.google.common.collect.Maps;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.tek271.funj.CollectionTools.isEmpty;
import static com.tek271.funj.ReflectionTools.*;

public class Mapper {

	/**
	 * Extract a list of property values from a list of objects
	 * @param iterable list of objects
	 * @param propertyName name of property to extract
	 * @param <OBJ> Generic type of the objects in the iterable
	 * @param <PROP> Generic type of property
	 * @return List of type PROP of the extracted property values
	 */
	public static <OBJ, PROP> List<PROP> pluck(Iterable<OBJ> iterable, String propertyName) {
		List<PROP> plucked = newArrayList();
		if (isEmpty(iterable)) return plucked;
		checkPropertyExists(iterable, propertyName);

		for (OBJ obj: iterable) {
			plucked.add((PROP) getPropertyValue(obj, propertyName));
		}

		return plucked;
	}

	/**
	 * Extract a Set of property values from a list of objects
	 * @param iterable list of objects
	 * @param propertyName name of property to extract
	 * @param <OBJ> Generic type of the objects in the iterable
	 * @param <PROP> Generic type of property
	 * @return Set of type PROP of the extracted property values
	 */
	public static <OBJ, PROP> Set<PROP> pluckToSet(Iterable<OBJ> iterable, String propertyName) {
		List<PROP> list = pluck(iterable, propertyName);
		return Sets.newHashSet(list);
	}

	public static <OBJ, K, V> Map<K, V> pluckKeyAndValue(Iterable<OBJ> iterable,
																											 String keyPropertyName,
																											 String valuePropertyName) {
		Map<K, V> map = Maps.newHashMap();
		if (isEmpty(iterable)) {
			return map;
		}
		checkPropertiesExist(iterable, keyPropertyName, valuePropertyName);

		for (OBJ obj: iterable) {
			K k = getPropertyValue(obj, keyPropertyName);
			V v = getPropertyValue(obj, valuePropertyName);
			map.put(k, v);
		}

		return map;
	}

	/**
	 * Map the objects in iterable into a list of objects by applying the callback on each object
	 * in the iterable
	 * @param iterable list of objects
	 * @param staticCallbackFullName full name of a function, including package and class. The function
	 *                               must have a single argument of type IN and return a value of type OUT
	 * @param <IN> Generic type of objects to be mapped
	 * @param <OUT> Generic type of the mapped values
	 * @return List of mapped values.
	 */
	public static <IN, OUT> List<OUT> map(Iterable<IN> iterable, String staticCallbackFullName,
																				Object... extraArgs) {
		FunctionDescriptor func = FunctionDescriptor.staticFunction(staticCallbackFullName);
		return map(iterable, func, extraArgs);
	}  // TODO test this

	/**
	 * Map the objects in iterable into a list of objects by applying the callback on each object
	 * in the iterable
	 * @param iterable list of objects
	 * @param cls The class which contains the static callback
	 * @param staticCallback name of a static callback function. The function must have a single
	 *                       argument of type IN and return a value of type OUT
	 * @param <IN> Generic type of objects to be mapped
	 * @param <OUT> Generic type of the mapped values
	 * @return List of mapped values.
	 */
	public static <IN, OUT> List<OUT> map(Iterable<IN> iterable, Class cls, String staticCallback,
																				Object... extraArgs) {
		FunctionDescriptor func = FunctionDescriptor.staticFunction(cls, staticCallback);
		return map(iterable, func, extraArgs);
	}

	/**
	 * Map the objects in iterable into a list of objects by applying the callback on each object
	 * in the iterable
	 * @param iterable list of objects
	 * @param context The context (object) which contains the callback
	 * @param callback name of a function which is a member of context.
	 *        The function must have at least single argument of type IN and return a value of type OUT.
	 *        The function may have other arguments which will use the extraArgs param.
	 * @param extraArgs extra arguments to be passed to the callback function
	 * @param <IN> Generic type of objects to be mapped
	 * @param <OUT> Generic type of the mapped values
	 * @return List of mapped values.
	 */
	public static <IN, OUT> List<OUT> map(Iterable<IN> iterable, Object context, String callback,
																				Object... extraArgs) {
		FunctionDescriptor func = FunctionDescriptor.dynamicFunction(context, callback);
		return map(iterable, func, extraArgs);
	}

	public static <IN, OUT> List<OUT> map(Iterable<IN> iterable, FunctionDescriptor functionDescriptor,
																				Object... extraArgs) {
		List<OUT> list = newArrayList();

		for (IN i: iterable) {
			Object[] args = ObjectArrays.concat(i, extraArgs);
			OUT mapped = functionDescriptor.call(args);
			list.add(mapped);
		}
		return list;
	}

}
