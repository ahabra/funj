funj
====

Collections and functional tools to complete the missing parts of Java/Guava.

To use with Maven:

```XML
	<dependency>
		<groupId>com.tek271</groupId>
		<artifactId>funj</artifactId>
		<version>1.0.0</version>
	</dependency>
```

The maven central repo http://search.maven.org has the latest production version, while the latest code is
in https://github.com/ahabra/funj

For the examples given below, suppose we have a class *Cat*

```Java
	class Cat {
		public long id;
		public String name;
		public int price;
		private String color;
		public String getColor() { return color; }
		public void setColor(String color) { this.color = color; }
	}
```

Additionally, suppose that we have a method `getCats()` which returns a list
of `Cat`s.

Notes
-----

1. You can access either getters/setters properties or fields by name directly.
2. You can access **nested** properties and fields.


CollectionTools
---------------
**toMap**: Convert a collection to a map using one of collection's items properties
as a key:

```Java
	List<Cat> cats = getCats();
	Map<Long, Cat> map = toMap(cats, "id");
```

Now the key in map is the id of Cat, while the value is the Cat object.

**toMultimap**: Group a collection by a property which is not unique:

```Java
	com.google.common.collect.Multimap<String, Cat> multimap = toMultimap(cats, "color");
```

**isEmpty**: Check if a collection is null or empty:

```Java
	isEmpty(cats)
```

Finder
------
**findAll**: Find a list of objects with a matching property value:

```Java
	List<Cat> foundList = findAll(cats, "id", 1, 2);
```

will find cats whose id is either 1 or 2.


**findFirst**: Find the first object with a matching value of a property:

```Java
	Cat found = findFirst(cats, "name", "tom");
```

will find the cat whose name property equals "tom"

**reject**: Return a list of objects whose value for propertyName is NOT
any of the filter values

```Java
	List<Cat> foundList = reject(cats, "name", "tom", "gar");
```

will find cats whose names is not "tom" nor "gar".


Mapper
------
**pluck**: Extract/pluck a property from a list of cats:

```Java
	List<Long> ids = pluck(cats, "id");
```

**pluckToSet**: Extract/pluck a property from a list of cats and return a Set:

```Java
	Set<String> colors = pluckToSet(cats, "color");
```

**pluckKeyAndValue**: Extract/pluck a key and value from a list of cats and create a map:

```Java
	Map<Long, String> idsAndColors = pluckKeyAndValue(cats, "id", "color");
```

**map**: Map members of a list using a callback function:

Suppose that we have 3 cats with prices 10, 12, and 15. Also suppose that we
have this *callback* function:

```Java
	int doubleThePrice(Cat cat, int maxPrice) {
		return Math.min(cat.price * 2, maxPrice) ;
	}
```

If you call `map()` like this:

```Java
	List<Integer> prices = map(cats, this, "doubleThePrice", 25);
```

The prices list will be: [20, 24, 25].

*Note The `map()` method is overloaded to support both static and dynamic callback
methods.*

