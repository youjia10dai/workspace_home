package sutdy.javabasis;

public class volatile¹Ø¼ü×ÖÊ¹ÓÃ {

	
	public static void main(String[] args) throws InterruptedException {
		
		final Person person = new Person();
		
		new Thread() {
 
			@Override
			public void run() {
				for (;;) {
					if (person.bChanged == !person.bChanged) {
						System.out.println(person.bChanged+ "    " + !person.bChanged);
						System.out.println("!=");
						System.exit(0);
					}
				}
			}
		}.start();
		Thread.sleep(1);
		new Thread() {

			@Override
			public void run() {
				for (;;) {
					person.bChanged = !person.bChanged;
					System.out.println(person.bChanged);
				}
			}
		}.start();
	}
	
	static class Person{
		public volatile boolean bChanged;
	}
	
}