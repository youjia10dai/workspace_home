/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
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
 * @description �Զ������𹤾���
 * ע����Ŀ����һ��Ҫ��ͬ
 * @author ������
 * @date 2018-10-29 
 */
public class AutomaticDeployUtils {
    
    public final static Logger log = Logger.getLogger(AutomaticDeployUtils.class);
    public static File deployDirFile;
    public static File developDirFile;
    public static long lastModifyTime;
    public static String developName;
    public static String[] developNames;
    //D:\tomcat6.0.45_x86\webapps\njcrm  JSPҳ��
    //D:\tomcat6.0.45_x86\webapps\njcrm\WEB-INF\classes   �滻��class
    /**
     * @description ��Ϊ��Ҫ�ڷ�����������
     * ���Ը��ಢ�����뵽Spring������(����ͨ��main����������)
     * �������֮��,��Ҫ�ŵ�AutomaticDeploy��Ŀ�б���
     * 
     * ��ȡ�����ļ�,������޸ĵ��ļ�������һ���ļ���(.xml�ļ���ȥ��)
     * 
     * �����ļ����Զ����ɵ�
     * 
     * @author ������ 2018-10-29
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        //��ʼ��
        init();
        //�������õ�����޸�ʱ���Զ����ɿ����ļ�Ŀ¼
        //�жϿ����ļ�Ŀ¼����û���ļ�,û���κ��ļ���,���Զ������ɿ����ļ�
        if(developDirFile.listFiles().length == 0){
            //���ɿ����ļ�,����ʾ������Ա�˶���Ϣ
            generationDevelopDir(deployDirFile);
            System.out.println("�����ļ��Ѿ��Զ�������˶�!");
        }
        ScannerHelper sc = new ScannerHelper("�Ƿ�ѹ���ļ��� Y/N");
        sc.getInputString(new GetUserInputInfo(){
            @Override
            public void input(String input) throws Exception {
                if("Y".equalsIgnoreCase(input)){
                    ZipHelper zip = new ZipHelper();
                    zip.zipFiles(developDirFile);
                    System.out.println("ѹ���ɹ�");
                }else {
                    System.out.println("ѹ��ʧ��");
                }
            }
        });
        //���ɲ�ִ�мƻ�
        //generationAndExcutedPlan();
    }
    
    /** 
     * @description ��ʼ��,��ȡ����Ŀ¼�Ϳ���Ŀ¼
     * @author ������ 2018-11-01
     * @return
     * @throws Exception
     */ 
    public static void init() throws Exception{
        File[] files = getDeployAndDevelopDirFile();
        deployDirFile = files[0];
        developDirFile = files[1];
        if(!deployDirFile.exists()){
            System.out.println("����Ŀ¼������,��˶�");
            System.exit(0);//�˳�ϵͳ
        }
        //�����ļ������ڴ���
        if(!developDirFile.exists()){
            developDirFile.mkdirs();
        }
        //����ṩ�Ŀ���Ŀ¼��������ĿĿ¼��β,��ô�ʹ���һ����ĿĿ¼
        if(!cheakProjectName(deployDirFile, developDirFile)){
            //��ȡ��Ŀ����
            File _developDirFile = new File(developDirFile.getAbsolutePath() + File.separator + getProjectName(deployDirFile));
            _developDirFile.mkdir();
            developDirFile = _developDirFile;
        }
        if(deployDirFile == null || developDirFile == null){
            System.out.println("��ʼ��ʧ��!!!");
            System.exit(0);
        }
    }
    
    /** 
     * @description ���ɲ�ִ�мƻ�
     * @author ������ 2018-11-01
     * @throws Exception 
     */ 
    public static void generationAndExcutedPlan() throws Exception{
        final List<String> commands = developFileToDeployFile(deployDirFile, developDirFile);
        System.out.println(commands);
        System.out.println("ִ����������");
        getExecuteInfo(commands);
        ScannerHelper sc = new ScannerHelper("Y/N");
        sc.getInputString(new GetUserInputInfo(){
            @Override
            public void input(String input) throws Exception {
                if("Y".equalsIgnoreCase(input)){
                    executeCommands(commands);
                    System.out.println("����ɹ�");
                }else {
                    System.out.println("����ȡ��,����ʧ��");
                }
            }
        });
    }
    
    /** 
     * @description �Զ����ɸտ�����Ŀ¼
     * @author ������ 2018-11-01
     * @param deployDirFile
     * @throws Exception 
     */ 
    public static void generationDevelopDir(File deployDirFile) throws Exception{
        //ֻ�����ļ��������ļ���
        File[] _deployDirFiles = deployDirFile.listFiles();
        File _deployDirFile = null;
        for(int i = 0; i < _deployDirFiles.length; i++){
            _deployDirFile = _deployDirFiles[i];
            long _lastModifyTime = _deployDirFile.lastModified();
            if(_deployDirFile.isFile()){
                //���������ſ�����(����ʱ�䲻���ж�)
                if(_lastModifyTime > lastModifyTime){
                    File _futureFile = getFutureFile(developDirFile,_deployDirFile);
                    if(!"".equals(developName)){
                        //���ݿ���������ȥ��
                        if(developNames!=null){
                            //��������������
                            for(String _developName : developNames) {
                                if(filterFile(_futureFile, _developName)){
                                    log.debug(1);
                                    FileUtils.copy(_deployDirFile, _futureFile);
                                    break;//ֻҪһ��������˳���ǰ��ѭ��
                                }
                            }
                        }else{
                            //��������������
                            log.debug(2);
                            if(filterFile(_futureFile, developName)){
                                FileUtils.copy(_deployDirFile, _futureFile);
                            }
                        }
                    }else {
                        //���û��ָ����������,�Ͱ�����޸�ʱ�����ж��ļ��Ƿ�Ҫ����
                        log.debug(3);
                        FileUtils.copy(_deployDirFile, _futureFile);
                    }
                }
            }else{
                //���ļ�,ͬ��Ҳ���ж�����޸�ʱ�����ж�����ļ����Ƿ�Ҫ
//                log.info(_lastModifyTime+ "   " + _deployDirFile.);
                generationDevelopDir(_deployDirFile);
            }
        }
    }
    
    /** 
     * @description �����ļ�
     * @author ������ 2018-11-02
     * @param file ����Ҫ���Ƶ��ļ�
     * @param developName  ��Ŀ��
     * @return
     */ 
    public static boolean filterFile(File file, String developName){
        //����.xml���ļ�������
        if(file.getName().contains(".xml"))
            return false;
        //���ڿ���Ŀ¼�µ��ļ�������
        return file.getParentFile().getAbsolutePath().contains(developName);
    }
    
    /**
     * @description ��ȡִ����Ϣ,���������������̨
     * @author ������ 2018-10-31
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
                System.out.println("Ϊ" + targetFile.getFileName() + "������Ϊ:" + copyFile.getFileName());
                System.out.println("�����ļ�" + targetFile.getCompleteFileName());
                i++;//����һ��
                coverFile++;//�����ļ���+1
            }else{
                if(targetFile.isFile()){
                    System.out.println("�����ļ�" + targetFile.getCompleteFileName());
                    cooyFile++;//�����ļ���+1
                }else{
                    System.out.println("�����ļ���" + targetFile.getCompleteFileName());
                    copyDIr++;//�����ļ�����+1
                }
            }
        }
        System.out.println("�����ļ�����Ϊ:" + copyDIr);
        System.out.println("�����ļ���Ϊ:" + cooyFile);
        System.out.println("�����ļ���Ϊ:" + coverFile);
    }
    
    /** 
     * @description ִ������
     * ���Ǹ��������ļ�,�������������ļ�
     * ���ϣ�������ļ�Ҳ�Զ������,��ִ�е�ʱ������������ж�
     * @author ������ 2018-10-31
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
     * @description ���ɿ��ļ�������Ŀ�������,��û��������ִ��
     * @author ������ 2018-10-31
     * @param deployDirFile  �����Ŀ¼�ļ�
     * @param developDirFile ������Ŀ¼�ļ�
     * @return �����
     * @throws Exception
     */ 
    public static List<String> developFileToDeployFile(File deployDirFile, File developDirFile) throws Exception{
        List<String> commands = new ArrayList<String>();
        File[] developFiles = developDirFile.listFiles();
        if(developDirFile.listFiles().length == 0){
            log.info("��û�п����ļ�");
            return null;
        }
        for(File developFile : developFiles) {
            //1.���ж��ļ��Ƿ����
            //2.������  ֱ�Ӹ���
            //3.����  �ж����ļ�����Ŀ¼  �ļ�����������,
            //4.Ŀ¼�ͽ��еݹ����
            File _futureDeployFile = getFutureFile(deployDirFile, developFile);
            log.info("����������ļ���" + _futureDeployFile.getName());
            if(_futureDeployFile.exists()){
                //����
                if(_futureDeployFile.isFile()){
                    //���ļ�
                    FileNameAndPathHelper _fileInfo = new FileNameAndPathHelper(_futureDeployFile);
                    log.info(_futureDeployFile.getAbsolutePath()+ "�������� :" + _fileInfo.getCompleteFileName());
                    _fileInfo.rename(_fileInfo.getFileNameWithoutSuffixName() + DateUtils.getToday("yyyyMMddHHmmss"));
                    commands.add("rename&" + _futureDeployFile.getAbsolutePath() + "&" + _fileInfo.getCompleteFileName());
//                    FileUtils.rename(_futureDeployFile.getAbsolutePath(), _fileInfo.getCompleteFileName());
                    commands.add("copy&" + developFile + "&" + _futureDeployFile);
//                    FileUtils.copy(source, _futureDeployFile);
                }else{
                    //��Ŀ¼
                    commands.addAll(developFileToDeployFile(deployDirFile, developFile));
                }
            }else{
                //������
                log.info(developFile);
                log.info(_futureDeployFile);
                commands.add("copy&" + developFile + "&" + _futureDeployFile);
//                FileUtils.copy(source, _futureDeployFile);
            }
        }
        //TextFileUtils.outPutFile("E:\\1.txt",commands);������ı��ļ���
        return commands;
    }
    
    /**
     * @description �ж�Ŀ���ļ��Ƿ���ԴĿ¼�д���
     * ��ȡ��target
     * @author ������ 2018-10-29
     * @param source Դ�ļ�
     * @param target Ŀ���ļ�
     * @return
     */
    public static boolean exsit(File source, File target){
        return getFutureFile(source, target).exists();
    }
    
    /** 
     * @description ��ȡ���������ɵ��ļ�(target�ļ�������source��)
     * @author ������ 2018-10-30
     * @param source  ������Ŀ�ĸ�Ŀ¼
     * @param target  �����ļ�
     * @return
     */ 
    public static File getFutureFile(File source, File target){
        return new File(getFutureDirPathAndName(source, target));
    }
    
    /** 
     * @description ��ȡ���������ļ�������
     * @author ������ 2018-10-29
     * @param deployFile
     * @param developFile
     * @return
     */ 
    public static String getFutureDirPathAndName(File deployFile, File developFile){
        String projectName = getProjectName(deployFile);
        log.info("��Ŀ��" + projectName);
        String developPath = developFile.getAbsolutePath();
        log.info("����Ŀ¼ " + developPath);
        String developFilePath = developPath.substring(developPath.indexOf(projectName) + projectName.length() + 1);
        log.info("����Ŀ¼��ȡ" + developPath);
        //��ȡ�����ļ��ľ���·��Ŀ¼��ַ
//        File developFilePath = developFile.getAbsoluteFile();
        //��ȡ����Ŀ�����Ŀ¼
        log.info(deployFile.getAbsolutePath() + File.separator + developFilePath);
        return deployFile.getAbsolutePath() + File.separator + developFilePath;
    }
    
    /** 
     * @description ��ȡ����Ŀ¼����Ŀ����
     * @author ������ 2018-10-30
     * @param deployFile
     * @return
     */ 
    public static String getProjectName(File deployFile){
        String deployPath = deployFile.getAbsolutePath();
        String projectName = deployPath.substring(deployPath.lastIndexOf(File.separator) + 1);
        return projectName;
    }
    
    /** 
     * @description ��ⲿ����Ŀ�Ϳ�����Ŀ����Ŀ���Ƿ���ͬ
     * @author ������ 2018-10-31
     * @param deployDirFile
     * @param developDirFile
     * @return
     */ 
    public static boolean cheakProjectName(File deployDirFile, File developDirFile){
        return getProjectName(deployDirFile).equals(getProjectName(developDirFile));
    }
    
    /**
     * @description ��ȡ����Ŀ¼�ļ�����
     * һ����ĿĿ¼
     * һ������Ŀ¼
     * ����Ŀ¼�ļ�����Ŀ������Ҫ��ͬ,��Ȼ�ᱨ��
     * 86400000Ϊһ��ĺ�����
     * @author ������ 2018-10-29
     * @return
     * @throws Exception
     */
    public static File[] getDeployAndDevelopDirFile()throws Exception{
        //1.�������⼯�϶���
        Properties properties = new Properties();
        //2.ͨ�������������������������ļ�
        InputStream in = AutomaticDeployUtils.class.getResourceAsStream("dir.properties");
        //3.ͨ�����϶�������ļ���ͬ
        properties.load(in);
        //4.ͨ��key��ȡָ��������
        log.info("��ȡ���Ĳ���Ŀ¼" + properties.getProperty("deployDir"));
        log.info("��ȡ���Ŀ���Ŀ¼" + properties.getProperty("developDir"));
        long nowTime = new Date().getTime();
        //��ʼ�����һ���޸��ļ���ʱ��
        try {
            //��L����1
            lastModifyTime = nowTime - Long.parseLong(properties.getProperty("modifyDay")) * 86400000l;
            developName = properties.getProperty("developName");
            if(developName.contains("|")){
                developNames = developName.split("\\|");
                log.debug(developName);
                log.debug(developNames[0]+ "  " +developNames[1]);
            }
        }
        catch (NumberFormatException e) {
            lastModifyTime = nowTime - 7 * 86400000l;//Ĭ�ϵ��޸�ʱ����7��
        }catch(Exception e){
            developName = "";
        }
        return new File[]{new File(properties.getProperty("deployDir")), new File(properties.getProperty("developDir"))};
    }
}