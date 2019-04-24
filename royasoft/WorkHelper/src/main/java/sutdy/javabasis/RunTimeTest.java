package sutdy.javabasis;

/**
 * �����������˼������jvm������һ���رյĹ��ӣ� ��jvm�رյ�ʱ�򣬻�ִ��ϵͳ���Ѿ����õ�����ͨ������addShutdownHook��ӵĹ��ӣ�
 * ��ϵͳִ������Щ���Ӻ�jvm�Ż�رա�������Щ���ӿ�����jvm�رյ�ʱ������ڴ����� �������ٵȲ�����
 *
 */
public class RunTimeTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// �����߳�1
		Thread thread1 = new Thread() {
			public void run() {
				System.out.println("thread1...");
			}
		};

		// �����߳�2
		Thread thread2 = new Thread() {
			public void run() {
				System.out.println("thread2...");
			}
		};

		// ����ر��߳�
		Thread shutdownThread = new Thread() {
			public void run() {
				System.out.println("shutdownThread...");
			}
		};

		// jvm�رյ�ʱ����ִ�и��̹߳���
		Runtime.getRuntime().addShutdownHook(shutdownThread);

		thread1.start();
		thread2.start();
	}
}
