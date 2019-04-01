/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.app.njry.automaticdeploy;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import main.helper.file.FileNameAndPathHelper;
import main.helper.zip.ZipHelper;
import main.utils.common.DateUtils;
import main.utils.common.GetUserInputInfo;
import main.utils.common.ScannerHelper;
import main.utils.file.FileUtils;

import org.apache.log4j.Logger;
/** 
 * @description 自动化部署工具类
 * 注意项目名称一定要相同
 * @author 陈吕奖
 * @date 2018-10-29 
 */
public class AutomaticDeployUtils {
    
    public final static Logger log = Logger.getLogger(AutomaticDeployUtils.class);
    public static File deployDirFile;
    public static File developDirFile;
    public static long lastModifyTime;
    public static String developName;
    public static String[] developNames;
    //D:\tomcat6.0.45_x86\webapps\njcrm  JSP页面
    //D:\tomcat6.0.45_x86\webapps\njcrm\WEB-INF\classes   替换的class
    /**
     * @description 因为需要在服务器端运行
     * 所以该类并不放入到Spring容器中(而是通过main方法来启动)
     * 功能完成之后,需要放到AutomaticDeploy项目中编译
     * 
     * 读取部署文件,将最近修改的文件创建成一个文件夹(.xml文件先去除)
     * 
     * 开发文件是自动生成的
     * 
     * @author 陈吕奖 2018-10-29
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        //初始化
        init();
        //根据配置的最大修改时间自动生成开发文件目录
        //判断开发文件目录下有没有文件,没有任何文件夹,就自动的生成开发文件
        if(developDirFile.listFiles().length == 0){
            //生成开发文件,并提示开发人员核对信息
            generationDevelopDir(deployDirFile);
            System.out.println("开发文件已经自动生成请核对!");
        }
        ScannerHelper sc = new ScannerHelper("是否压缩文件夹 Y/N");
        sc.getInputString(new GetUserInputInfo(){
            @Override
            public void input(String input) throws Exception {
                if("Y".equalsIgnoreCase(input)){
                    ZipHelper zip = new ZipHelper();
                    zip.zipFiles(developDirFile);
                    System.out.println("压缩成功");
                }else {
                    System.out.println("压缩失败");
                }
            }
        });
        //生成并执行计划
        //generationAndExcutedPlan();
    }
    
    /** 
     * @description 初始化,获取部署目录和开发目录
     * @author 陈吕奖 2018-11-01
     * @return
     * @throws Exception
     */ 
    public static void init() throws Exception{
        File[] files = getDeployAndDevelopDirFile();
        deployDirFile = files[0];
        developDirFile = files[1];
        if(!deployDirFile.exists()){
            System.out.println("部署目录不存在,请核对");
            System.exit(0);//退出系统
        }
        //开发文件不存在创建
        if(!developDirFile.exists()){
            developDirFile.mkdirs();
        }
        //如果提供的开发目录不是以项目目录结尾,那么就创建一级项目目录
        if(!cheakProjectName(deployDirFile, developDirFile)){
            //获取项目名称
            File _developDirFile = new File(developDirFile.getAbsolutePath() + File.separator + getProjectName(deployDirFile));
            _developDirFile.mkdir();
            developDirFile = _developDirFile;
        }
        if(deployDirFile == null || developDirFile == null){
            System.out.println("初始化失败!!!");
            System.exit(0);
        }
    }
    
    /** 
     * @description 生成并执行计划
     * @author 陈吕奖 2018-11-01
     * @throws Exception 
     */ 
    public static void generationAndExcutedPlan() throws Exception{
        final List<String> commands = developFileToDeployFile(deployDirFile, developDirFile);
        System.out.println(commands);
        System.out.println("执行以下命令");
        getExecuteInfo(commands);
        ScannerHelper sc = new ScannerHelper("Y/N");
        sc.getInputString(new GetUserInputInfo(){
            @Override
            public void input(String input) throws Exception {
                if("Y".equalsIgnoreCase(input)){
                    executeCommands(commands);
                    System.out.println("部署成功");
                }else {
                    System.out.println("命令取消,部署失败");
                }
            }
        });
    }
    
    /** 
     * @description 自动生成刚开发的目录
     * @author 陈吕奖 2018-11-01
     * @param deployDirFile
     * @throws Exception 
     */ 
    public static void generationDevelopDir(File deployDirFile) throws Exception{
        //只复制文件不复制文件夹
        File[] _deployDirFiles = deployDirFile.listFiles();
        File _deployDirFile = null;
        for(int i = 0; i < _deployDirFiles.length; i++){
            _deployDirFile = _deployDirFiles[i];
            long _lastModifyTime = _deployDirFile.lastModified();
            if(_deployDirFile.isFile()){
                //如果是最近才开发的(根据时间不好判断)
                if(_lastModifyTime > lastModifyTime){
                    File _futureFile = getFutureFile(developDirFile,_deployDirFile);
                    if(!"".equals(developName)){
                        //根据开发的名字去找
                        if(developNames!=null){
                            //处理多个发开名称
                            for(String _developName : developNames) {
                                if(filterFile(_futureFile, _developName)){
                                    log.debug(1);
                                    FileUtils.copy(_deployDirFile, _futureFile);
                                    break;//只要一个满足就退出当前的循环
                                }
                            }
                        }else{
                            //处理单个开发名称
                            log.debug(2);
                            if(filterFile(_futureFile, developName)){
                                FileUtils.copy(_deployDirFile, _futureFile);
                            }
                        }
                    }else {
                        //如果没有指定开发名称,就按最后修改时间来判断文件是否要复制
                        log.debug(3);
                        FileUtils.copy(_deployDirFile, _futureFile);
                    }
                }
            }else{
                //是文件,同样也是判断最后修改时间来判断这个文件夹是否要
//                log.info(_lastModifyTime+ "   " + _deployDirFile.);
                generationDevelopDir(_deployDirFile);
            }
        }
    }
    
    /** 
     * @description 过滤文件
     * @author 陈吕奖 2018-11-02
     * @param file 即将要复制的文件
     * @param developName  项目名
     * @return
     */ 
    public static boolean filterFile(File file, String developName){
        //包含.xml的文件不复制
        if(file.getName().contains(".xml"))
            return false;
        //不在开发目录下的文件不复制
        return file.getParentFile().getAbsolutePath().contains(developName);
    }
    
    /**
     * @description 获取执行信息,这里是输出到控制台
     * @author 陈吕奖 2018-10-31
     * @param commands
     */
    public static void getExecuteInfo(List<String> commands){
        if(commands == null){
            return;
        }
        String command = "";
        int copyDIr = 0;
        int cooyFile = 0;
        int coverFile = 0;
        for(int i = 0; i < commands.size(); i++){
            command = commands.get(i);
            if("".equals(command.trim()))
                return;
            String[] paths = command.split("&");
            FileNameAndPathHelper targetFile = new FileNameAndPathHelper(paths[1]);
            if(command.contains("rename")){
                FileNameAndPathHelper copyFile = new FileNameAndPathHelper(paths[2]);
                System.out.println("为" + targetFile.getFileName() + "重命名为:" + copyFile.getFileName());
                System.out.println("复制文件" + targetFile.getCompleteFileName());
                i++;//跳过一行
                coverFile++;//覆盖文件数+1
            }else{
                if(targetFile.isFile()){
                    System.out.println("复制文件" + targetFile.getCompleteFileName());
                    cooyFile++;//复制文件数+1
                }else{
                    System.out.println("复制文件夹" + targetFile.getCompleteFileName());
                    copyDIr++;//复制文件夹数+1
                }
            }
        }
        System.out.println("复制文件夹数为:" + copyDIr);
        System.out.println("复制文件数为:" + cooyFile);
        System.out.println("覆盖文件数为:" + coverFile);
    }
    
    /** 
     * @description 执行命令
     * 不是复制配置文件,而是配置配置文件
     * 如果希望配置文件也自动的添加,在执行的时候再做特殊的判断
     * @author 陈吕奖 2018-10-31
     * @param commands
     * @throws Exception 
     */
    public static void executeCommands(List<String> commands) throws Exception{
        if(commands == null){
            return;
        }
        for(String command : commands) {
            if("".equals(command.trim()))
                return;
            String[] paths = command.split("&");
            if(command.contains("rename")){
                FileUtils.rename(paths[1], paths[2]);
            }else if(command.contains("copy")){
                FileUtils.copy(paths[1], paths[2]);
            }
        }
    }
    
    /** 
     * @description 生成开文件部署到项目的命令集合,并没有真正的执行
     * @author 陈吕奖 2018-10-31
     * @param deployDirFile  部署的目录文件
     * @param developDirFile 开发的目录文件
     * @return 命令集合
     * @throws Exception
     */ 
    public static List<String> developFileToDeployFile(File deployDirFile, File developDirFile) throws Exception{
        List<String> commands = new ArrayList<String>();
        File[] developFiles = developDirFile.listFiles();
        if(developDirFile.listFiles().length == 0){
            log.info("并没有开发文件");
            return null;
        }
        for(File developFile : developFiles) {
            //1.先判断文件是否存在
            //2.不存在  直接复制
            //3.存在  判断是文件还是目录  文件重名命后复制,
            //4.目录就进行递归操作
            File _futureDeployFile = getFutureFile(deployDirFile, developFile);
            log.info("将来部署的文件名" + _futureDeployFile.getName());
            if(_futureDeployFile.exists()){
                //存在
                if(_futureDeployFile.isFile()){
                    //是文件
                    FileNameAndPathHelper _fileInfo = new FileNameAndPathHelper(_futureDeployFile);
                    log.info(_futureDeployFile.getAbsolutePath()+ "重命名成 :" + _fileInfo.getCompleteFileName());
                    _fileInfo.rename(_fileInfo.getFileNameWithoutSuffixName() + DateUtils.getToday("yyyyMMddHHmmss"));
                    commands.add("rename&" + _futureDeployFile.getAbsolutePath() + "&" + _fileInfo.getCompleteFileName());
//                    FileUtils.rename(_futureDeployFile.getAbsolutePath(), _fileInfo.getCompleteFileName());
                    commands.add("copy&" + developFile + "&" + _futureDeployFile);
//                    FileUtils.copy(source, _futureDeployFile);
                }else{
                    //是目录
                    commands.addAll(developFileToDeployFile(deployDirFile, developFile));
                }
            }else{
                //不存在
                log.info(developFile);
                log.info(_futureDeployFile);
                commands.add("copy&" + developFile + "&" + _futureDeployFile);
//                FileUtils.copy(source, _futureDeployFile);
            }
        }
        //TextFileUtils.outPutFile("E:\\1.txt",commands);输出到文本文件中
        return commands;
    }
    
    /**
     * @description 判断目标文件是否在源目录中存在
     * 截取出target
     * @author 陈吕奖 2018-10-29
     * @param source 源文件
     * @param target 目标文件
     * @return
     */
    public static boolean exsit(File source, File target){
        return getFutureFile(source, target).exists();
    }
    
    /** 
     * @description 获取将来的生成的文件(target文件出现在source中)
     * @author 陈吕奖 2018-10-30
     * @param source  部署项目的根目录
     * @param target  开发文件
     * @return
     */ 
    public static File getFutureFile(File source, File target){
        return new File(getFutureDirPathAndName(source, target));
    }
    
    /** 
     * @description 获取将来生成文件的名称
     * @author 陈吕奖 2018-10-29
     * @param deployFile
     * @param developFile
     * @return
     */ 
    public static String getFutureDirPathAndName(File deployFile, File developFile){
        String projectName = getProjectName(deployFile);
        log.info("项目名" + projectName);
        String developPath = developFile.getAbsolutePath();
        log.info("开发目录 " + developPath);
        String developFilePath = developPath.substring(developPath.indexOf(projectName) + projectName.length() + 1);
        log.info("开发目录截取" + developPath);
        //获取开发文件的绝对路径目录地址
//        File developFilePath = developFile.getAbsoluteFile();
        //截取出项目后面的目录
        log.info(deployFile.getAbsolutePath() + File.separator + developFilePath);
        return deployFile.getAbsolutePath() + File.separator + developFilePath;
    }
    
    /** 
     * @description 获取部署目录的项目名称
     * @author 陈吕奖 2018-10-30
     * @param deployFile
     * @return
     */ 
    public static String getProjectName(File deployFile){
        String deployPath = deployFile.getAbsolutePath();
        String projectName = deployPath.substring(deployPath.lastIndexOf(File.separator) + 1);
        return projectName;
    }
    
    /** 
     * @description 检测部署项目和开发项目的项目名是否相同
     * @author 陈吕奖 2018-10-31
     * @param deployDirFile
     * @param developDirFile
     * @return
     */ 
    public static boolean cheakProjectName(File deployDirFile, File developDirFile){
        return getProjectName(deployDirFile).equals(getProjectName(developDirFile));
    }
    
    /**
     * @description 获取两个目录文件对象
     * 一个项目目录
     * 一个开发目录
     * 两个目录文件的项目名必须要相同,不然会报错
     * 86400000为一天的毫秒数
     * @author 陈吕奖 2018-10-29
     * @return
     * @throws Exception
     */
    public static File[] getDeployAndDevelopDirFile()throws Exception{
        //1.创建特殊集合对象
        Properties properties = new Properties();
        //2.通过加载类的类加载器加载配置文件
        InputStream in = AutomaticDeployUtils.class.getResourceAsStream("dir.properties");
        //3.通过集合对象加载文件内同
        properties.load(in);
        //4.通过key获取指定的内容
        log.info("获取到的部署目录" + properties.getProperty("deployDir"));
        log.info("获取到的开发目录" + properties.getProperty("developDir"));
        long nowTime = new Date().getTime();
        //初始化最后一次修改文件的时间
        try {
            //是L不是1
            lastModifyTime = nowTime - Long.parseLong(properties.getProperty("modifyDay")) * 86400000l;
            developName = properties.getProperty("developName");
            if(developName.contains("|")){
                developNames = developName.split("\\|");
                log.debug(developName);
                log.debug(developNames[0]+ "  " +developNames[1]);
            }
        }
        catch (NumberFormatException e) {
            lastModifyTime = nowTime - 7 * 86400000l;//默认的修改时间是7天
        }catch(Exception e){
            developName = "";
        }
        return new File[]{new File(properties.getProperty("deployDir")), new File(properties.getProperty("developDir"))};
    }
}