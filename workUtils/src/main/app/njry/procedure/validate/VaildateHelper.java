/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.app.njry.procedure.validate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import main.app.njry.excel.ExcelUtils;
import main.app.njry.procedure.validate.entity.VaildateColumn;
import main.app.njry.procedure.validate.entity.VaildateSql;
import main.helper.BaseHelper;
import main.helper.json.JsonArrayHelper;
import main.helper.json.JsonObjectHelper;
import main.old.helper.excel.ExcelHelper;
import main.study.spring.annotation.FileListUtils;
import main.utils.common.PatternUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/** 
 * @description 自动生成验证过程的基础验证(也可以完成一些简单得数据有效性验证)
 * 例如:
 *  1.必填
 *  2.是数字类型,日期类型,是手机号码
 * @author 陈吕奖
 * @date 2018-07-27 
 */
@Scope("prototype")//多例
@Component("vaildatePro")
public class VaildateHelper extends BaseHelper {

    public ExcelUtils excelUtils;

    /*
     * 这个bean是通过有参数的构造函数自动创建对象的
     * 参数通过名字查找
     * 步骤
     *  1.读取批量导入的模板文件
     *  2.根据模板文件,自动生成验证sql
     *  3.思考如何写,可以更加方便的添加一种数据类型的验证
     *  使用指定构造函数创建bean
     */
    @Autowired(required = true)//表示使用这个构造函数来创建Bean对象
    public VaildateHelper(@Qualifier(value="excelHelper") ExcelHelper excel, @Qualifier(value="file")File file){
        log.info("执行有参构造函数");
        //没有办法在这里对excel进行初始化(需要这个对象的属性进行初始化)
        this.excel = excel;
        /*
         * 实际开发中,file对象应该是通过一个参数传递,
         * 再调用对象的初始化方法
         */
        excel.init(file, null);
    }
    
    /**
     * @description 知识点:以后的所有对象尽量都需要一个空参的构造函数
     */
    public VaildateHelper(){
        log.info("执行无参构造函数");
    }

    /** 
     * @description 加载excel文件
     * 初始化必要的数据(将excel中的数据封装成list<map>)
     * @author 陈吕奖 2018-08-06
     * @throws Exception 
     */
    @PostConstruct//定义初始化方法
    public void loadExcel() throws Exception {
        //初始化excel文件
        System.out.println("进行初始化");
        System.out.println("列:"+excel.getCols()+" 行:"+excel.getRows());
        //list中存放的是String[]
        List<String[]> list = new ArrayList<String[]>();
        for(int i = 0; i < excel.getCols(); i++) {
            list.add(excel.getColContextByIndex(i));
        }
        //意思为: 1.先是将list<String[]> 转换成字符串  2.将字符串转换成JosnArray对象
        //3.将JosnArray 转换为 JsonArrayHelper(就是将JosnArray当成List看)
        //4.将JsonArrayHelper 转换为ListColumns
        //5.将ListColumns 转换成字符串
        getSql(getColumns(JsonUtils.getJsonArray(list)));
    }

    /** 
     * @description 根据Json对象生成Column数组
     * @author 陈吕奖 2018-08-21
     * @param jsonArrayHelper
     */ 
    private List<VaildateColumn> getColumns(JsonArrayHelper json) {
        List<VaildateColumn> list =new ArrayList<VaildateColumn>();
        for(int i = 0; i < json.size(); i++) {
            JsonObjectHelper helper = json.get(i);
            //获取所有的key值,再根据key值获取value值
            //当成Map使用太麻烦,转换成对象使用
            VaildateColumn col =  helper.toBean(VaildateColumn.class);
            col.json = helper;
            list.add(col);
        }
        return list;
    }

    /** 
     * @description 根据Column集合对象生成简单的验证SQL
     * @author 陈吕奖 2018-08-22
     * @param list
     * @throws Exception 
     */ 
    public void getSql(List<VaildateColumn> list) throws Exception{
        List<String> title = new ArrayList<String>();
        List<String> right = new ArrayList<String>();
        for(VaildateColumn column : list) {
            title.add(column.getName());
            right.add(column.getRight());
            //通column判断字段是否能重复,如果不能重复,生成对应的sql语句
            if(!column.repeat){
                System.out.println(PatternUtils.replaceAll(VaildateSql.repeat, PatternUtils.reg, addKey(column.json)));
            }
            if(column.isRequire()){
                //必填
                if(column.range.size() != 0){
                    //必须在指定的访问内
                    //System.out.println(SQLString.range);
                    //将range转换成tipRange和sqlRange  tipRange显示在提示语句中,sqlRange 显示在sql语句中
                    System.out.println(PatternUtils.replaceAll(VaildateSql.requireRange, PatternUtils.reg, addKey(column.json)));
                    continue;
                }else if("number".equals(column.columnType)){
                    //必须是数字类型
                    System.out.println(PatternUtils.replaceAll(VaildateSql.requireNumber, PatternUtils.reg, column.json));
                    continue;
                }else if("date".equals(column.columnType)){
                    //必须是日期类型
                    System.out.println(PatternUtils.replaceAll(VaildateSql.requireDate, PatternUtils.reg, column.json));
                    continue;
                }else{
                    //只是必填
                    System.out.println(PatternUtils.replaceAll(VaildateSql.require, PatternUtils.reg, column.json));
                    continue;
                }
            }else if("number".equals(column.columnType)){
                //必须是数字类型
                System.out.println(PatternUtils.replaceAll(VaildateSql.number, PatternUtils.reg, column.json));
                continue;
            }else if("date".equals(column.columnType)){
                //必须是日期类型
                System.out.println(PatternUtils.replaceAll(VaildateSql.date, PatternUtils.reg, column.json));
                continue;
            }else if(column.range.size() != 0){
                //必须在什么范围内
                System.out.println(PatternUtils.replaceAll(VaildateSql.range, PatternUtils.reg, addKey(column.json)));
                continue;
            }
        }
        //并且自动生成验证的数据
        //2.创建excel文件
        excelUtils.createExcel(FileListUtils.file1, getVaildateContext(right.toArray(new String[]{})), title.toArray(new String[]{}));
    }
    
    /** 
     * @description 生成所有字段的验证数据
     * @author 陈吕奖 2018-08-28
     * @param right 所有正确数据
     * @return
     */ 
    private List<String[]> getVaildateContext(String[] right){
        List<String[]> contexts = new ArrayList<String[]>();
        //第一条正确记录
        contexts.add(right);
        /* 其中的一个字段数据生成错误的,其他的是正确的
         * 错误有两种 一种是为空  一种为 字符串"test"
         * 创建的String[]的个数为字段数量的两倍
         */
        int length = right.length*2;
        for(int i = 0; i < length; i++) {
            String[] rightCoty = java.util.Arrays.copyOf(right, right.length);
            try {
                if(i%2 == 0){
                    rightCoty[i/2] = "";
                }else{
                    rightCoty[i/2] = "a";
                }
                System.out.println(i);
                contexts.add(rightCoty);
            }
            catch (Exception e) {
                log.debug("数组拷贝失败");
            }
        }
        return contexts;
    }
    
    /** 
     * 这个方法是这个对象特有的,所以定义在这里并且私有(并没有定义在一个工具类中)
     * @description 为map额外添加两个键值对
     * @author 陈吕奖 2018-08-28
     * @param map
     */ 
    private JsonObjectHelper addKey(JsonObjectHelper map){
        String range = map.get("range");
        map.put("tipRange", range.substring(1, range.length()-1).replaceAll("\"", ""));
        map.put("sqlRange", range.substring(1, range.length()-1).replaceAll("\"", "''"));
        return map;
    }
    
    /** 
     * @description 释放资源()
     * @author 陈吕奖 2018-08-21
     * 销毁的注解只在单例情况下有效
     */ 
    public void destroy(){
        System.out.println("释放资源");
        //释放excel占用的资源
        excel.close();
    }
    
    //注入excel的帮助类(构造方法注入)
    public ExcelHelper excel;
    
    //注入
    @Autowired
    public void setExcelUtils(ExcelUtils excelUtils) {
        this.excelUtils = excelUtils;
    }
}