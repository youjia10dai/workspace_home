/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.app.njry.procedure.validate.entity;

/** 
 * @description ���ڴ洢�̶�ģʽ��SQL�ַ���
 * @author ������
 * @date 2018-08-24 
 */
public class VaildateSql {

    /*���ü�final,�ַ����������ǲ��ɱ��*/
    
    /*
     * name �ֶ�������
     * columnName �ֶ���
     * tipRange  ��ʾ����ʾ�ķ�Χ��Ϣ
     * sqlRange  sql����еķ�Χ��Ϣ
     */
    
    /**
     * @fields require ��Ҫ
     */ 
    public static String require = "--��֤{name}����\n"+
          "v_sql := 'update '|| v_table ||' a\n"+
          "             set a.decription = ''{name}�����˶�'' , a.flag = ''ʧ��''\n"+
          "           where (a.{columnName} is null or trim(a.{columnName}) = '''')\n"+
          "             and a.flag is null';\n"+
          "execute immediate v_sql;\n"+
          "commit;\n";
    
    /** 
     * @fields range ��������һ���ķ�Χ��
     */ 
    public static String requireRange = "--��֤{name}�������ָ����Χ\n"+
          "v_sql := 'update '|| v_table ||' a\n"+
          "             set a.decription = ''{name}�����({tipRange})�����Χ�ڣ���˶�'' , a.flag = ''ʧ��''\n"+
          "           where (a.{columnName} is null or trim(a.{columnName}) not in ({sqlRange}))\n"+
          "             and a.flag is null';\n"+
          "execute immediate v_sql;\n"+
          "commit;\n";
    
    
    /** 
     * @fields requireNumber �������������
     */ 
    public static String requireNumber = "--��֤{name}����ұ�������������\n"+
          "v_sql := 'update '|| v_table ||' a\n"+
          "             set a.decription = ''{name}����������֣���˶�'' , a.flag = ''ʧ��''\n"+
          "           where (a.{columnName} is null or is_number(a.{columnName}) = 0)\n"+
          "             and a.flag is null';\n"+
          "execute immediate v_sql;\n"+
          "commit;\n";
    
    /** 
     * @fields requireDate �������������
     */ 
    public static String requireDate = "--��֤{name}����ұ�������������\n"+
          "v_sql := 'update '|| v_table ||' a\n"+
          "             set a.decription = ''{name}���������ڸ�ʽYYYYMMDD����˶�'' , a.flag = ''ʧ��''\n"+
          "           where (a.{columnName} is null or is_date_2(a.{columnName}, ''yyyymmdd'') = 0)\n"+
          "             and a.flag is null';\n"+
          "execute immediate v_sql;\n"+
          "commit;\n";
    
    /** 
     * @fields date ��������������
     */ 
    public static String date = "--��֤{name}��������������\n"+
          "v_sql := 'update '|| v_table ||' a\n"+
          "             set a.decription = ''{name}���������ڸ�ʽYYYYMMDD����˶�'' , a.flag = ''ʧ��''\n"+
          "           where a.{columnName} is not null\n"+
          "             and is_date_2(a.{columnName}, ''yyyymmdd'') = 0\n"+
          "             and a.flag is null';\n"+
          "execute immediate v_sql;\n"+
          "commit;\n";
    
    /** 
     * @fields number ��������������
     */ 
    public static String number = "--��֤{name}��������������\n"+
          "v_sql := 'update '|| v_table ||' a\n"+
          "             set a.decription = ''{name}���������֣���˶�'' , a.flag = ''ʧ��''\n"+
          "           where a.{columnName} is not null\n"+
          "             and is_number(a.{columnName}) = 0\n"+
          "             and a.flag is null';\n"+
          "execute immediate v_sql;\n"+
          "commit;\n";
    
    /** 
     * @fields range ������ʲô��Χ��
     */ 
    public static String range = "--��֤{name}������({tipRange})�ķ�Χ��\n"+
          "v_sql := 'update '|| v_table ||' a\n"+
          "             set a.decription = ''{name}������({tipRange})��Χ�ڣ���˶�'' , a.flag = ''ʧ��''\n"+
          "           where a.{columnName} is not null\n"+
          "             and trim(a.{columnName}) not in ({sqlRange})\n"+
          "             and a.flag is null';\n"+
          "execute immediate v_sql;\n"+
          "commit;\n";
    
    /** 
     * @fields repeat �����ֶβ����ظ�
     */ 
    public static String repeat = "v_table_name := 't_'||to_char(sysdate,'yyyymmddhhmiss');\n"+
        "droptable(v_table_name);\n"+
        "v_sql := ' create table '|| v_table_name ||' as\n"+
        "           select {columnName} from '|| v_table ||' group by {columnName} having count(1) > 1';\n"+
        "execute immediate v_sql;\n" +
        "v_sql := ' update '|| v_table ||' a\n"+
        "             set a.decription = ''{name}�ظ����룬��˶�'' , a.flag = ''ʧ��''\n"+
                   "where exists (select 1 from '|| v_table_name ||' b where a.{columnName}= b.{columnName})\n"+
        "             and a.flag is null';\n"+
        "execute immediate v_sql;\n";
    
    public static void main(String[] args) {
        System.out.println(range);
    }
}
