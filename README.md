funj
====

Collections and functional tools to complete the missing parts of Java/Guava.

To use with Maven:

	<dependency>
		<groupId>com.tek271</groupId>
		<artifactId>funj</artifactId>
		<version>1.0.0</version>
	</dependency>


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

**Note:** This library can access either properties with getters/setters or fields
by name directly.

CollectionTools
---------------
To convert a collection to a map using one of collection's items properties
as a key:

	List<Cat> cats = getCats();
	Map<Long, Cat> map = toMap(cats, "id");

Now the key in map is the id of Cat, while the value is the Cat object.

But suppose you want to group cats by something which is not unique:

	com.google.common.collect.Multimap<String, Cat> multimap = toMultimap(cats, "color");

To check if a collection is null or empty:

	isEmpty(cats)


Finder
------
To find a list of objects with a matching property value:

	List<Cat> foundList = findAll(cats, "id", 1, 2);

Will find cats whose id is either 1 or 2.

	Cat found = findFirst(cats, "name", "tom");

Will find the cat whose name property equals "tom"


Mapper
------
To extract/pluck a property from a list of cats:

	List<Long> ids = pluck(cats, "id");

To extract/pluck a key and value from a list of cats and create a map:

	Map<Long, String> idsAndColors = pluckKeyAndValue(cats, "id", "color");

To map members of a list using a callback function:

Suppose that we have 3 cats with prices 10, 11, and 12. Also suppose that we
have this *callback* function:

	long doubleThePrice(Cat cat) {
		return cat.price * 2;
	}

If you call `map()` as this:

	List<Integer> prices = map(cats, this, "doubleThePrice");

The prices list will have: [20, 22, 24].

**Note** The `map()` method is overloaded to support both static and dynamic callback
methods.

