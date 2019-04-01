package main.source.common;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Frank
 * �滻ƥ����ı�
 */
public class ReplaceDemo {
    public static void main(String[] args) {
        // ����һ��������ʽģʽ������ƥ��һ�����ʣ�\b=���ʱ߽磩
        String patt = "\\bfavor\\b";

        // ���ڲ��Ե������ַ���
        String input = "Do me a favor? Fetch my favorites.AAA favor BBB";
        System.out.println("Input:" + input);

        // ��������ʽʵ�������з������鿴���������
        Pattern r = Pattern.compile(patt);
        Matcher m = r.matcher(input);
        System.out.println("ReplaceAll:" + m.replaceAll("favour"));

        //appendReplacement����
        //reset��matcher��״̬�����
        m.reset();
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            //��ƥ��֮ǰ���ַ������Ƶ�sb,�ٽ�ƥ�����滻Ϊ��"favour"����׷�ӵ�sb
            m.appendReplacement(sb, "favour");
        }
        System.out.println(sb.toString());
        m.appendTail(sb);
        System.out.println(sb.toString());
    }
}