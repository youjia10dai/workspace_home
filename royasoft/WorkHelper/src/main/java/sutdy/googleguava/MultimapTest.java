package sutdy.googleguava;

import java.util.Collection;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class MultimapTest {

	
	public static void main(String[] args) {
		Multimap<String, String> multimap = ArrayListMultimap.create();
		multimap.put("1", "1");
		multimap.put("1", "2");
		multimap.put("1", "3");
		multimap.put("1", "4");
		multimap.put("1", "5");
		multimap.put("1", "6");
		multimap.put("1", "6");
		multimap.put("1", "6");
		multimap.put("1", "6");
		multimap.put("1", "6");
		Collection<String> collection = multimap.get("1");
		for (String string : collection) {
			System.out.println(string);
		}
	}
}
