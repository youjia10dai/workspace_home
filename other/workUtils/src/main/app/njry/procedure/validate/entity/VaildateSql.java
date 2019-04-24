/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.app.njry.procedure.validate.entity;

/** 
 * @description 用于存储固定模式的SQL字符串
 * @author 陈吕奖
 * @date 2018-08-24 
 */
public class VaildateSql {

    /*不用加final,字符串本来就是不可变的*/
    
    /*
     * name 字段中文名
     * columnName 字段名
     * tipRange  提示中显示的范围信息
     * sqlRange  sql语句中的范围信息
     */
    
    /**
     * @fields require 必要
     */ 
    public static String require = "--验证{name}必填\n"+
          "v_sql := 'update '|| v_table ||' a\n"+
          "             set a.decription = ''{name}必填，请核对'' , a.flag = ''失败''\n"+
          "           where (a.{columnName} is null or trim(a.{columnName}) = '''')\n"+
          "             and a.flag is null';\n"+
          "execute immediate v_sql;\n"+
          "commit;\n";
    
    /** 
     * @fields range 必填且在一定的范围内
     */ 
    public static String requireRange = "--验证{name}必填并且在指定范围\n"+
          "v_sql := 'update '|| v_table ||' a\n"+
          "             set a.decription = ''{name}填并且在({tipRange})这个范围内，请核对'' , a.flag = ''失败''\n"+
          "           where (a.{columnName} is null or trim(a.{columnName}) not in ({sqlRange}))\n"+
          "             and a.flag is null';\n"+
          "execute immediate v_sql;\n"+
          "commit;\n";
    
    
    /** 
     * @fields requireNumber 必填并是数字类型
     */ 
    public static String requireNumber = "--验证{name}必填并且必须是数字类型\n"+
          "v_sql := 'update '|| v_table ||' a\n"+
          "             set a.decription = ''{name}必填并且是数字，请核对'' , a.flag = ''失败''\n"+
          "           where (a.{columnName} is null or is_number(a.{columnName}) = 0)\n"+
          "             and a.flag is null';\n"+
          "execute immediate v_sql;\n"+
          "commit;\n";
    
    /** 
     * @fields requireDate 必填并是日期类型
     */ 
    public static String requireDate = "--验证{name}必填并且必须是日期类型\n"+
          "v_sql := 'update '|| v_table ||' a\n"+
          "             set a.decription = ''{name}并且是日期格式YYYYMMDD，请核对'' , a.flag = ''失败''\n"+
          "           where (a.{columnName} is null or is_date_2(a.{columnName}, ''yyyymmdd'') = 0)\n"+
          "             and a.flag is null';\n"+
          "execute immediate v_sql;\n"+
          "commit;\n";
    
    /** 
     * @fields date 必须是日期类型
     */ 
    public static String date = "--验证{name}必须是日期类型\n"+
          "v_sql := 'update '|| v_table ||' a\n"+
          "             set a.decription = ''{name}必须是日期格式YYYYMMDD，请核对'' , a.flag = ''失败''\n"+
          "           where a.{columnName} is not null\n"+
          "             and is_date_2(a.{columnName}, ''yyyymmdd'') = 0\n"+
          "             and a.flag is null';\n"+
          "execute immediate v_sql;\n"+
          "commit;\n";
    
    /** 
     * @fields number 必须是数字类型
     */ 
    public static String number = "--验证{name}必须是数字类型\n"+
          "v_sql := 'update '|| v_table ||' a\n"+
          "             set a.decription = ''{name}必须是数字，请核对'' , a.flag = ''失败''\n"+
          "           where a.{columnName} is not null\n"+
          "             and is_number(a.{columnName}) = 0\n"+
          "             and a.flag is null';\n"+
          "execute immediate v_sql;\n"+
          "commit;\n";
    
    /** 
     * @fields range 必须在什么范围内
     */ 
    public static String range = "--验证{name}必须在({tipRange})的范围内\n"+
          "v_sql := 'update '|| v_table ||' a\n"+
          "             set a.decription = ''{name}必需在({tipRange})范围内，请核对'' , a.flag = ''失败''\n"+
          "           where a.{columnName} is not null\n"+
          "             and trim(a.{columnName}) not in ({sqlRange})\n"+
          "             and a.flag is null';\n"+
          "execute immediate v_sql;\n"+
          "commit;\n";
    
    /** 
     * @fields repeat 描述字段不能重复
     */ 
    public static String repeat = "v_table_name := 't_'||to_char(sysdate,'yyyymmddhhmiss');\n"+
        "droptable(v_table_name);\n"+
        "v_sql := ' create table '|| v_table_name ||' as\n"+
        "           select {columnName} from '|| v_table ||' group by {columnName} having count(1) > 1';\n"+
        "execute immediate v_sql;\n" +
        "v_sql := ' update '|| v_table ||' a\n"+
        "             set a.decription = ''{name}重复输入，请核对'' , a.flag = ''失败''\n"+
                   "where exists (select 1 from '|| v_table_name ||' b where a.{columnName}= b.{columnName})\n"+
        "             and a.flag is null';\n"+
        "execute immediate v_sql;\n";
    
    public static void main(String[] args) {
        System.out.println(range);
    }
}
