package sutdy.stringutils;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class StringUtilsTest {

	@Test
	public void splitTest(){
		String[] keys = StringUtils.split("s", ".");
		for (String string : keys) {
			System.out.println(string);
		}
	}
	
}
