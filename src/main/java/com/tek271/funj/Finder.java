package com.tek271.funj;

import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;

public class Finder {

	/**
	 * Find all objects whose value for propertyName is one of the filter values
	 * @param it collection of objects
	 * @param propertyName name of property to inspect
	 * @param filter varargs array of possible values
	 * @param <OBJ> Generic type of the objects in the iterable
	 * @param <PROP> Generic type of property
	 * @return List of matching objects
	 */
	public static <OBJ, PROP> List<OBJ> findAll(Iterable<OBJ> it, String propertyName, PROP... filter) {
		List<OBJ> found = newArrayList();
		if (CollectionTools.isEmpty(it) || filter.length==0) {
			return found;
		}
		ReflectionTools.checkPropertyExists(it, propertyName);

		Set<PROP> filterSet = Sets.newHashSet(filter);
		for (OBJ obj: it) {
			PROP v= ReflectionTools.getPropertyValue(obj, propertyName);
			if (filterSet.contains(v)) {
				found.add(obj);
			}
		}
		return found;
	}

	@SuppressWarnings("unchecked")
	public static <OBJ, PROP> OBJ findFirst(Iterable<OBJ> it, String propertyName, PROP filter) {
		List<OBJ> found = findAll(it, propertyName, filter);
		if (found.isEmpty()) return null;
		return found.get(0);
	}

	/**
	 * Reject all objects whose value for propertyName is one of the filter values
	 * @param it collection of objects
	 * @param propertyName name of property to inspect
	 * @param filter varargs array of possible values
	 * @param <OBJ> Generic type of the objects in the iterable
	 * @param <PROP> Generic type of property
	 * @return List of objects which fo not match the filter
	 */
	public static <OBJ, PROP> List<OBJ> reject(Iterable<OBJ> it, String propertyName, PROP... filter) {
		if (CollectionTools.isEmpty(it)) {
			return newArrayList();
		}
		if (filter.length==0) {
			return newArrayList(it);
		}
		List<OBJ> found = newArrayList();

		ReflectionTools.checkPropertyExists(it, propertyName);

		Set<PROP> filterSet = Sets.newHashSet(filter);
		for (OBJ obj: it) {
			PROP v= ReflectionTools.getPropertyValue(obj, propertyName);
			if (!filterSet.contains(v)) {
				found.add(obj);
			}
		}
		return found;
	}

}
