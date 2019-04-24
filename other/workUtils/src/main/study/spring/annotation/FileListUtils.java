/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.study.spring.annotation;

import java.io.File;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/** 
 * @description 生成File对象(所有的File对象全部定义在这里)
 * @author 陈吕奖
 * @date 2018-08-15 
 */
@Component("fileListUtils")
public class FileListUtils {
    
    //自动生成验证代码的配置文件
    public static File file = new File("D:\\我的坚果云\\同步文件\\workspace\\excelInfo\\验证测试\\生成基础验证模板 7中类型的验证.xls");

    //自动生成的验证文件
    public static File file1 = new File("D:\\我的坚果云\\同步文件\\workspace\\excelInfo\\验证测试\\数据验证模板.xls");
    
    //根据Excel文件创建配置表
    public static File file2 = new File("D:\\我的坚果云\\同步文件\\workspace\\excelInfo\\政协指数专网\\政协转网配置表.xls");
    
    
    public static File file3 = new File("D:\\我的坚果云\\同步文件\\workspace\\excelInfo\\小区用户改签\\光改V2.xls");
    
    /** 
     * @description 把file定义在方法中,通过这个方法进行注入
     * 缺点:只能注入对象
     * 
     * public VaildateProUtils(@Qualifier(value="excelHelper") ExcelHelper excel, @Qualifier(value="file")File file){
     * @author 陈吕奖 2018-08-28
     * @return
     */ 
    @Bean(name="file")
    public File getFile(){
        return file;
    }
    
}