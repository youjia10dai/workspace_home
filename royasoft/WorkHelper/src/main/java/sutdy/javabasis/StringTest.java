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
	
}
