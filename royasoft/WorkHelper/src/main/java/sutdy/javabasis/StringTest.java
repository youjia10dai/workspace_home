package sutdy.javabasis;

import org.junit.Test;

public class StringTest {

	public static void main(String[] args) {
	}
	

	@Test
	public void splitTest(){
		String[] strings = "a.s".split("\\.");
		for (String string : strings) {
			System.out.println(string);
		}
		System.out.println("dd");
	}
	
	@Test
	public void stringTest() {
		String test = "http://www.taobao.com?activityName=555";
        String activityName = test.substring(test.lastIndexOf("=") + 1);
        test = test.substring(0, test.indexOf("?"));
        System.out.println(activityName);
        System.out.println(test);
	}
	
}
