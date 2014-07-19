package com.tek271.funj;

import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;

public class Finder {

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

}
