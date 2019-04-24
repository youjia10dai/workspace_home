package main.source.thread;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
 
public class TimeTest {
  public static void main(String[] args) {
    timer1();
    
    timer2();
    timer3();
    //timer4();
  }
 
  // ��һ�ַ������趨ָ������task��ָ��ʱ��timeִ�� schedule(TimerTask task, Date time)
  public static void timer1() {
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      public void run() {
        System.out.println("-------�趨Ҫָ������1--------");
        //System.gc(); ���������ʹ����ֹͣ
      }
    }, 10000);// �趨ָ����ʱ��time,�˴�Ϊ2000����
  }
 
  // �ڶ��ַ������趨ָ������task��ָ���ӳ�delay����й̶��ӳ�peroid��ִ��
  // schedule(TimerTask task, long delay, long period)
  public static void timer2() {
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      public void run() {
        System.out.println("-------�趨Ҫָ������2--------");
      }
    }, 1000, 5000);//
  }
 
  // �����ַ������趨ָ������task��ָ���ӳ�delay����й̶�Ƶ��peroid��ִ�С�
  // scheduleAtFixedRate(TimerTask task, long delay, long period)
  public static void timer3() {
    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      public void run() {
        System.out.println("-------�趨Ҫָ������3--------");
      }
    }, 1000, 2000);
  }
   
  // �����ַ���������ָ��������task��ָ����ʱ��firstTime��ʼ�����ظ��Ĺ̶�����periodִ�У�
  // Timer.scheduleAtFixedRate(TimerTask task,Date firstTime,long period)
  public static void timer4() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.HOUR_OF_DAY, 12); // ����ʱ
    calendar.set(Calendar.MINUTE, 0);    // ���Ʒ�
    calendar.set(Calendar.SECOND, 0);    // ������
 
    Date time = calendar.getTime();     // �ó�ִ�������ʱ��,�˴�Ϊ�����12��00��00
 
    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      public void run() {
        System.out.println("-------�趨Ҫָ������--------");
      }
    }, time, 1000 * 60 * 60 * 24);// �����趨����ʱÿ��̶�ִ��
  }
}