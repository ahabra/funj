package com.tek271.funj;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import java.util.Map;

public class CollectionTools {

	public static <OBJ, PROP> Map<PROP, OBJ> toMap(Iterable<OBJ> it, String keyPropertyName) {
		Map<PROP, OBJ> map = Maps.newHashMap();
		return fillMap(it, keyPropertyName, map);
	}

	public static <OBJ, PROP> Multimap<PROP, OBJ> toMultimap(Iterable<OBJ> it, String keyPropertyName) {
		Multimap<PROP, OBJ> map = ArrayListMultimap.create();
		return fillMap(it, keyPropertyName, map);
	}

	private static <OBJ, PROP, MAP> MAP fillMap(Iterable<OBJ> it, String keyPropertyName, MAP mapObject) {
		if (isEmpty(it)) return mapObject;
		ReflectionTools.checkPropertyExists(it, keyPropertyName);

		for (OBJ obj: it) {
			PROP key = ReflectionTools.getPropertyValue(obj, keyPropertyName);
			putValueInMap(mapObject, key, obj);
		}

		return mapObject;
	}

	@SuppressWarnings("unchecked")
	private static <OBJ, PROP, MAP> void putValueInMap(MAP mapObject, PROP key, OBJ value) {
		if (mapObject instanceof Map) {
			Map<PROP, OBJ> map = (Map<PROP, OBJ>) mapObject;
			map.put(key, value);
		} else {
			Multimap<PROP, OBJ> multimap = (Multimap<PROP, OBJ>) mapObject;
			multimap.put(key, value);
		}

	}

	public static <T> boolean isEmpty(Iterable<T> it) {
		return it==null || !it.iterator().hasNext();
	}

}
