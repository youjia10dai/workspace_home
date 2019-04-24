package sutdy.javabasis;

/**
 * 这个方法的意思就是在jvm中增加一个关闭的钩子， 当jvm关闭的时候，会执行系统中已经设置的所有通过方法addShutdownHook添加的钩子，
 * 当系统执行完这些钩子后，jvm才会关闭。所以这些钩子可以在jvm关闭的时候进行内存清理、 对象销毁等操作。
 *
 */
public class RunTimeTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 定义线程1
		Thread thread1 = new Thread() {
			public void run() {
				System.out.println("thread1...");
			}
		};

		// 定义线程2
		Thread thread2 = new Thread() {
			public void run() {
				System.out.println("thread2...");
			}
		};

		// 定义关闭线程
		Thread shutdownThread = new Thread() {
			public void run() {
				System.out.println("shutdownThread...");
			}
		};

		// jvm关闭的时候先执行该线程钩子
		Runtime.getRuntime().addShutdownHook(shutdownThread);

		thread1.start();
		thread2.start();
	}
}
