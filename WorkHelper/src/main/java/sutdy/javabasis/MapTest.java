package sutdy.javabasis;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class MapTest {

	public static void main(String[] args) {

	}
	
	/**
	 * ����Mapֱ�����ʲô
	 */
	@Test
	public void testMapOut(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "v");
		System.out.println(map);
	}
	
}
