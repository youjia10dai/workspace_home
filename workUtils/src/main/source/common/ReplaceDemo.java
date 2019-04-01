package main.source.common;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Frank
 * 替换匹配的文本
 */
public class ReplaceDemo {
    public static void main(String[] args) {
        // 创建一个正则表达式模式，用以匹配一个单词（\b=单词边界）
        String patt = "\\bfavor\\b";

        // 用于测试的输入字符串
        String input = "Do me a favor? Fetch my favorites.AAA favor BBB";
        System.out.println("Input:" + input);

        // 从正则表达式实例中运行方法并查看其如何运行
        Pattern r = Pattern.compile(patt);
        Matcher m = r.matcher(input);
        System.out.println("ReplaceAll:" + m.replaceAll("favour"));

        //appendReplacement方法
        //reset把matcher的状态清空了
        m.reset();
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            //将匹配之前的字符串复制到sb,再将匹配结果替换为："favour"，并追加到sb
            m.appendReplacement(sb, "favour");
        }
        System.out.println(sb.toString());
        m.appendTail(sb);
        System.out.println(sb.toString());
    }
}