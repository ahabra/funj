package com.tek271.funj;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class Zoo {
	public int id;
	private String city;
	public Cat cat;
	public List<Dog> dogs;

	public String getCity() { return city; }

	public Map<String, Object> toMap() {
		Map<String, Object> map = Maps.newHashMap();
		map.put("id", id);
		map.put("city", city);
		map.put("cat", cat.toMap());

		List<Map<String, Object>> d= Lists.newArrayList();
		for(Dog dog: dogs) {
			d.add(dog.toMap());
		}
		map.put("dogs", d);
		return map;
	}


	public static Zoo create(int id, String city, Cat cat,  List<Dog> dogs) {
		Zoo zoo = new Zoo();
		zoo.id = id;
		zoo.city = city;
		zoo.cat = cat;
		zoo.dogs = dogs;
		return zoo;
	}

	public static class Cat {
		public int id;
		public String color;

		public static Cat create(int id, String color) {
			Cat cat = new Cat();
			cat.id = id;
			cat.color = color;
			return cat;
		}

		public Map<String, Object> toMap() {
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", id);
			map.put("color", color);
			return map;
		}
	}

	public static class Dog {
		public int id;
		public String bark;

		public static Dog create(int id, String bark) {
			Dog dog = new Dog();
			dog.id = id;
			dog.bark = bark;
			return dog;
		}

		public Map<String, Object> toMap() {
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", id);
			map.put("bark", bark);
			return map;
		}
	}

	public static Cat createCat(int id) {
		return Cat.create(id, "color_" +  id);
	}

	public static Dog createDog(int id) {
		return Dog.create(id, "bark_" + id);
	}

	/** Create a zoo wit a cat and 3 dogs */
	public static Zoo createZoo(int id) {
		List<Dog> dogs = Lists.newArrayList(createDog(id), createDog(id+1), createDog(id+2));
		return Zoo.create(id, "city_" + id, createCat(id), dogs);
	}

	public static List<Map<String, Object>> toListOfMaps(Zoo... zoos) {
		List<Map<String, Object>> list = Lists.newArrayList();
		for(Zoo zoo: zoos) {
			list.add(zoo.toMap());
		}
		return list;
	}

}
