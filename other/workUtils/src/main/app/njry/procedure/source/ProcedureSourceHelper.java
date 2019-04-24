/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.app.njry.procedure.source;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.app.njry.procedure.source.entity.FullTableName;
import main.app.njry.procedure.source.exception.InvalidProcedure;
import main.helper.BaseHelper;
import main.helper.database.ProcHelper;
import main.spring.database.aspect.DataSource;
import main.spring.database.multDataSource.DBContextHolder;
import main.utils.common.ListUtils;
import main.utils.common.PatternUtils;
import main.utils.common.StringUtils;
import org.springframework.stereotype.Component;

/** 
 * @description 过程信息对象
 * 目前完成3中功能
 * 1.可以获取过程的源码  封装成list(可以自动生成删表语句,和过程参数信息)
 * 1.1 获取所有的表名(自己生成的表,  依赖的表)
 * 2.可以自动的获取所有的依赖项
 *   2.1 生成一张明细文本  表名   过程项
 *   2.2 生成历史记录 下次获取时,先从改文本中获取,再从数据库中获取
 * 3.查询一个字段的来源(提供表名,还有字段名   生成查找明细)
 * 4.自动生成简单的过程
 * 5.自动生成结果表(生成结果表的插入语句)
 * 
 * @author 陈吕奖
 * @date 2018-09-03
 * 考虑到方法是否需要做成静态的方法,还是做成一个对象()
 * 做成一个对象,交给spring管理,spring可以为它添加很多的功能(延迟加载,缓存,aop)(静态方法,在程序启动时就会加载,使用对象可以做到延迟加载)
 * 静态类失去面向对象的多态的特点
 * 对象是单例还是多例的,如果对象本身有属性,最好设置成多例(多线程存在着漏洞)
 */
@Component("proInfo")
@DataSource(name = DBContextHolder.zw3)
public class ProcedureSourceHelper extends BaseHelper{
    
    public String getSourceSql = "select text from all_source a where 1 = 1 and type = 'PROCEDURE' and name like ? order by line asc";
    //使用起别名的方式来防止列明大写
    public String getProInfoByproNameSql = "select cnt_item \"cntItem\", item_desc \"proName\", item_type \"itemType\" from ngbossbk.v_proc_dict a where a.cnt_item = ?";
    public String getProInfoByCntItemSql = "select cnt_item \"cntItem\", item_desc \"proName\", item_type \"itemType\" from ngbossbk.v_proc_dict a where upper(a.item_desc) = ?";
    public String findDependItemInSysSql = "select cnt_user, cnt_item, cnt_name, item_desc from ngbossbk.v_proc_dict where (lower(key_word) like ? or lower(item_desc) like ?) and lower(cnt_user) = ? and lower(db_name) like ?";
    public String findDependItemInLocalSql = "select cnt_item from t_ryclj_3000437_dependitem where cnt_user = ? and db_name like ? and tablename = ?";
    public String insertCNtItemSql = "insert into t_ryclj_3000437_dependitem (cnt_user, cnt_item, cnt_name, item_desc, tablename, db_name, type) values(?, ?, ?, ?, ?, ?, ?)";
    
    /** 
     * @fields cntItem 过程项
     */ 
    public String cntItem;
    
    /** 
     * @fields itemName 过程名
     */ 
    public String proName;
    
    /** 
     * @fields itemType 表示这个过程项的类型   1.日      2.月
     * 1默认值表示是日报表
     */ 
    public int itemType = 1;
    
    /**
     * @fields COLUMNCOUNT sql获取一个Map,Map其中的字段数量
     */
    public static int COLUMNCOUNT = 4;
    
    /**
     * @description 根据过程项获取源码
     * @author 陈吕奖 2018-09-21
     * @param cntItem
     * @return
     * @throws Exception 
     */
    public List<String> getProSourceByCntItem(String cntItem) throws Exception{
        initByCntItem(cntItem);
        return getProSource();
    }
    
    /** 
     * @description 根据过程名获取源码
     * @author 陈吕奖 2018-09-21
     * @param proName
     * @return
     * @throws Exception 
     */
    public List<String> getProSourceByProName(String proName) throws Exception{
        initByProName(proName);
        return getProSource();
    }
    
    /** 
     * @description 根据过程项初始化
     * @author 陈吕奖 2018-09-21
     * @param cntItem
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private void initByCntItem(String cntItem) throws Exception{
        db.mapConvertToObject(db.queryForMap(getProInfoByproNameSql, cntItem), this);
    }
    
    /** 
     * @description 根据过程名初始化
     * @author 陈吕奖 2018-09-25
     * @param proName
     * @return
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    private void initByProName(String proName) throws Exception{
        db.mapConvertToObject(db.queryForMap(getProInfoByCntItemSql, proName.toUpperCase()), this);
    }
    
    /**
     * @description 根据过程项和过程名获取过程源码(源码全部转换成小写)
     * @author 陈吕奖 2018-09-21
     * @param cntItem  根据过程项获取不同getSource  sql语句
     * @param proName  过程名称
     * @return
     */
    public List<String> getProSource(){
        if(cntItem == null){
            throw new InvalidProcedure("无效的过程,过程项为空");
        }
        String getSourceSql = generateGetSourceSql();
        List<String> listStr = getSource();
        return listStr;
    }

    /** 
     * @description 生成获取源码的sql语句
     * @author 陈吕奖 2018-12-04
     * @return
     */
    private String generateGetSourceSql() {
        //根据不同的过程项,生成不同getSource语句
        if(cntItem.startsWith("3")){
        }else if(cntItem.startsWith("6")){
            getSourceSql = getSourceSql.replace("all_source", "all_source@tonjzw6");
        }else if(cntItem.startsWith("9")){
            getSourceSql = getSourceSql.replace("all_source", "all_source@topnj");
        }else{
            throw new InvalidProcedure("暂时只支持369过程项");
        }
        return getSourceSql;
    }
    
    /** 
     * @description 获取源码
     * @author 陈吕奖 2018-12-05
     * @param getSourceSql
     * @return
     */ 
    private List<String> getSource() {
        List<String> listStr = new ArrayList<String>();
        List<Map<String,Object>> listSource = db.queryForList(getSourceSql, proName);
        for(int index = 0; index < listSource.size(); index++) {
            String text = listSource.get(index).get("text").toString();
            listStr.add(text);
        }
        return listStr;
    }
    
    /**
     * @description 自动生成过程删除表的语句
     * @author 陈吕奖 2018-09-21
     * @param sources
     */
    public void getDeleteStatement(List<String> sources){
        ProcedureUtils.getDeleteSrateByArray(sources.toArray(new String[]{}));
    }
    
    /** 
     * @description 自动生成过程变量信息
     * @author 陈吕奖 2018-09-21
     * @param sources
     */ 
    public void getVariableInfo(List<String> sources){
        ProcedureUtils.getVariableInfoByArray(sources.toArray(new String[]{}));
    }
    
    /** 
     * @description 根据源码获取过程项,和所有的表名
     * 其中的sql语句必须合符规范
     * @author 陈吕奖 2018-09-25
     * @param sources
     * @return 返回集合元素为String[]的集合  单个元素为 1.用户名  2.表名  3.数据库名
     */
    public Set<FullTableName> getAllTableNames(List<String> sources){
        //所有和返回值相关的变量定义在第一行
        sources = getValidContext(sources);
        String[] _tableNamesParts = getValidTableNames(sources);
        Set<FullTableName> tableNames = getAllFullTableNames(_tableNamesParts);
        return tableNames;
    }

    /** 
     * @description 从源码中获取有效内容
     * @author 陈吕奖 2018-11-26
     * @param sources
     */ 
    private List<String> getValidContext(List<String> sources) {
        //删除没有包含from的
        sources = deleteItemNotContainsContext(sources, "from");
        //获取表名
        sources = getTableNameString(sources);
        //再剔除数据(包含select)
        sources = deleteItemContainsContext(sources, "select");
        return sources;
    }
    
    /** 
     * @description 集合元素中不包含某个字符串就删除
     * @author 陈吕奖 2018-11-26
     * @param iterator
     */
    private List<String> deleteItemNotContainsContext(List<String> sources, String Context) {
        Iterator<String> iterator = sources.iterator();
        while(iterator.hasNext()){
            String next = iterator.next();
            //如果没有包含from就删除元素
            if(!next.contains(Context)){
                iterator.remove();
            }
        }
        return sources;
    }
    
    /** 
     * @description 获取表名字符串,"from 和where之前的内容"
     * @author 陈吕奖 2018-11-26
     * @param sources
     */ 
    private List<String> getTableNameString(List<String> sources) {
        //将from替换为空(相当于截取出from和where之间的内容,如果表明是跨行的将无法提取出来)
        for(int i = 0; i < sources.size(); i++) {
            String string = sources.get(i);
            string = string.trim();
            string = string.substring(string.indexOf("from") + "from".length()).replaceAll("from", "");
            if(string.contains("where"))
                string = string.substring(0, string.indexOf("where"));
            if(string.contains("order"))
                string = string.substring(0, string.indexOf("order"));
            if(string.contains("union"))
                string = string.substring(0, string.indexOf("union"));
            sources.set(i, string);
        }
        return sources;
    }
    
    /** 
     * @description 集合元素中包含某个字符串就删除
     * @author 陈吕奖 2018-11-26
     * @param iterator
     */ 
    private List<String> deleteItemContainsContext(List<String> sources, String Context) {
        Iterator<String> iterator = sources.iterator();
        while(iterator.hasNext()){
            String next = iterator.next();
            //如果没有包含from就删除元素 "(".equals(next.trim()) 这个是特殊情况
            if(next.contains(Context) || "(".equals(next.trim())){
                iterator.remove();
            }
        }
        return sources;
    }
    
    /**
     * @description 获取有效的表名(表名的一部分)
     * @author 陈吕奖 2018-11-26
     * @param sources
     * @return
     */
    private String[] getValidTableNames(List<String> sources) {
        String[] tableNames = PatternUtils.getMatcherStr("\\s*([\\s|\\d|\\w|\\.|@|\\||']+\\s*\\w?,?)", sources, 1,0);
        for(String string : tableNames) {
            log.debug(string);
        }
        for(int i = 0; i < tableNames.length; i++) {
            //知识点 1. .在正则中表示任意除\r\n(在Java中只能在()中使用)  2. [\\s|\\S] 可以表示任意字符  空格和非空格
            //1.将1.' 2.' ' 3.b 将别名和空格去除,获取表名(单纯的表名)   第一个正则
            //2.将参数名'|| v_mon ||'替换为空    第二个正则
            tableNames[i] = tableNames[i].replaceAll("([\\s][a-z])?[,|'|\\s]*$", "").replaceAll("'[\\s|\\S]+'", "").trim();
        }
        for(String string : tableNames) {
            log.debug(string);
        }
        return tableNames;
    }
    
    /** 
     * @description 获取所有的完整表名
     * @author 陈吕奖 2018-11-26
     * @param tableNames
     * @param _tableNamesParts 部分表名
     */ 
    public Set<FullTableName> getAllFullTableNames(String[] _tableNamesParts) {
        Set<FullTableName> tableNames = new HashSet<FullTableName>();
        /*
         * 生成一个String[]存储到TableName对象中(3个名称全部转换成小写)
         * 1.截取出用户名
         * 2.截取数据库名
         * 3.截取出表名
         */
        for(int i = 0; i < _tableNamesParts.length; i++) 
        {
            FullTableName table = getFullTableName(_tableNamesParts[i]);
            tableNames.add(table);
        }
        return tableNames;
    }

    /** 
     * @description 根据表名获取完整的表名
     * @author 陈吕奖 2018-11-26
     * @param tableNamePart
     * @param table
     */ 
    private FullTableName getFullTableName(String tableNamePart) {
        FullTableName table = new FullTableName();
        String[] split;
        String dataBaseName = "";
        //设置用户名
        if(tableNamePart.contains("."))
        {
            split = tableNamePart.split("\\.");//split可以根据正则表达式切取字符串, .在正则中表示任意字符,这里需要进行转义
            table.userName = split[0].toLowerCase();
            tableNamePart = split[1];
        }else{
            table.userName = "jtb";
        }
        //设置数据库名
        if(tableNamePart.contains("@"))
        {
            split = tableNamePart.split("@");
            table.dataBaseName = split[1].substring(2).toLowerCase();
            table.setTableName(split[0].toLowerCase());
        }else{
            //初始化数据库名cntItem
            if(cntItem.startsWith("3"))
                dataBaseName = "njzw3"; 
            else if(cntItem.startsWith("6"))
                dataBaseName = "njzw6";
            else if(cntItem.startsWith("9"))
                dataBaseName = "pnj";
            table.dataBaseName = dataBaseName;
            table.setTableName(tableNamePart.toLowerCase());
        }
        return table;
    }
    
    /** 
     * @description 获取所有表名的依赖项(在账务3创建一张配置表)
     * @author 陈吕奖 2018-10-08
     * @param tabNames  所有的表名
     * @param cntItem  过程项
     * @return
     */
    @SuppressWarnings("unchecked")
    public Set<FullTableName> getDependItemCntByTabName(Set<FullTableName> tabNames){
        //1.在自己的配置表中找
        tabNames = deleteMyItemCntTable(tabNames);
        //2.到系统的配置表中找
        findDependItemCntByConfigTable(tabNames);
        //3.在系统中查找依赖项,并将找到的信息添加到自己配置表中
        findDependItemCntInSystemTable(tabNames);
        return tabNames;
    }

    /** 
     * @description 剔除过程中自己创建的表
     * @author 陈吕奖 2018-11-26
     * @param tabNames
     */ 
    private Set<FullTableName> deleteMyItemCntTable(Set<FullTableName> tabNames) {
        Iterator<FullTableName> iterator = tabNames.iterator();
        while(iterator.hasNext()){
            String tableName = iterator.next().getTableName();
            if(tableName.contains(cntItem))
                iterator.remove();
        }
        return tabNames;
    }
    
    /** 
     * @description 从自己的配置文件中查找依赖项
     * @author 陈吕奖 2018-11-26
     * @param tabNames
     * @return
     */ 
    private void findDependItemCntByConfigTable(Set<FullTableName> tabNames) {
        Map<?,?> queryForMap;
        for(FullTableName tableName : tabNames) {
            //如果表名中已经存在过程项,就直接使用过程项
            if(tableName.getTableName().matches(".+(\\d{7}).+")){
                tableName.cntItem = PatternUtils.getMatcherStr(".+(\\d{7}).+", tableName.getTableName());
                continue;
            }
            queryForMap = db.queryForMap(findDependItemInLocalSql, tableName.userName, "%"+tableName.dataBaseName+"%", tableName.getTableName());
            if(queryForMap.size() == 1) {
                tableName.cntItem = queryForMap.get("cnt_item").toString();
            }
        }
    }
    
    /**
     * @description 在系统中获取依赖项
     * @author 陈吕奖 2018-11-26
     * @param tabNames
     */
    private void findDependItemCntInSystemTable(Set<FullTableName> tabNames) {
        Map<?,?> queryForMap;
        //在系统配置文件中查找
        for(FullTableName tableName : tabNames) {
            //如果已经获取过程项就不在系统配置表中查找
            if(tableName.cntItem != null)
                continue;
            queryForMap = db.queryForMap(findDependItemInSysSql, "%"+tableName.getTableName()+"%", "%"+tableName.getTableName()+"%", tableName.userName, "%"+tableName.dataBaseName+"%");
            log.debug(StringUtils.getSql(findDependItemInSysSql, "%"+tableName.getTableName()+"%", "%"+tableName.getTableName()+"%", tableName.userName, "%"+tableName.dataBaseName+"%"));
            log.info(queryForMap.size());
            //如果有找到就是Map数量就等于COLUMNCOUNT
            if(queryForMap.size() == COLUMNCOUNT) {
                log.info(queryForMap.get("cnt_item").toString());
                tableName.cntItem = queryForMap.get("cnt_item").toString();
                //将刚找到的依赖项添加到自己的配置表中
                db.update(insertCNtItemSql, queryForMap.get("cnt_user").toString().toLowerCase(), queryForMap.get("cnt_item").toString().toLowerCase(),
                        queryForMap.get("cnt_name").toString().toLowerCase(), queryForMap.get("item_desc").toString().toLowerCase(),
                        tableName.getTableName(), tableName.dataBaseName, 0);
            }
        }
    }
    
    /** 
     * @description 获取指定依赖项过程的执行语句
     * @author 陈吕奖 2018-12-05
     * @param cntItem
     * @throws Exception 
     */ 
    public void getProcedureExecStatement(String cntItem) throws Exception{
        List<String> sources = getProSourceByCntItem(cntItem);
        List<String> generatedSources = generateProcedureSourceBySources(sources);
        createAndCallProceduce(ListUtils.getString(generatedSources));
        printStaticSql(cntItem);
    }
    
    /**
     * @description 根据过程的源码,生成clj_test_pro过程源码
     * 存放在两张表里面 1.t_ryclj_9999999_sql   2.t_ryclj_9999999_parameter
     * @author 陈吕奖 2018-10-18
     * @param sources  源码List(在原有的List上修改)
     * @return
     */ 
    private List<String> generateProcedureSourceBySources(List<String> sources) {
        //获取过程名称
        final String proName = PatternUtils.getMatcherStr("procedure\\s+(\\S+)\\s*\\(", sources.get(0)).toLowerCase();
        //更换过程名称(开始的过程名)
        sources.set(0, "create or replace " + sources.get(0).toLowerCase().replace(proName, "clj_test_pro"));
        //删除所有的注释(单行注释和多行注释和提交)
        deleteCommentAndCommit(sources);
        //v_column_area1 这个变量是个坑(变量赋值涉及到了多行)
        //这里的\r\n注释了, 因为加上这个Oracle无法正常的解析过程,导致过程无效
        //查看生成的字符串可以是因为\r\n已经被解析成换行了(导致,查看源码时,可以手动的编译不报错)
        ListUtils.forEach(sources, new ForEach<String>(){
            //代表执行的顺序
            int no = 0;
            //记录最后一次替换的位置
            int lastIndex = 0;
            @Override
            //理论上使用for循环的效率更高,单这里主要是测试 使用接口来模拟回调函数
            public void forEach(List<String> list, int index, String element) {
                //
                element = element.toLowerCase();
                if(element.contains(proName)){
                    list.set(index, element.replaceAll(proName, "clj_test_pro"));
                }else if(element.trim().equalsIgnoreCase("begin")){
                    //添加两张配置表的删除语句,并且将v_step 值设置为0,不然执行不了
                    list.set(index,element + "  delete from t_ryclj_9999999_sql where cnt_item = " + cntItem + ";" /*+ "\r\n" */
                            + "  delete from t_ryclj_9999999_parameter where cnt_item = " + cntItem + ";" /*+ "\r\n" */
                            + "  v_step         := 0;" /*+ "\r\n" */
                            + "  v_flag         := 0;");
                }else if(element.contains(":=") && !element.contains("sql") && element.contains(";")){//;表示字段赋值在一行内完成
                    //增加变量插入语句
                    //获取变量的名称
                    //v_column_area1 这个变量是个坑,可以把它做成一个参数传递进来(处理)
                    //insert into t_ryclj_9999999_parameter values (3000468,'v_step', v_step);
                    //  v_step         := 0;
                    String paramterName = PatternUtils.getMatcherStr("\\s*(\\S+)\\s*:=", element);
                    list.set(index, element + 
                             "  insert into t_ryclj_9999999_parameter values ("+ cntItem +",'"+ paramterName +"', "+ paramterName +");" /*+ "\r\n"*/);
                }
                if(element.matches("\\s*execute\\s+immediate\\s+v_sql\\s*(into)?\\s*\\S*\\s*;[\\s*|\t|\r|\n]")){
                    //替换执行语句
                    lastIndex = index;
                    list.set(index, element.substring(0,element.indexOf("e")) 
                            +"insert into t_ryclj_9999999_sql values ("+ ++no +", "+ cntItem +", v_sql);");
                }
                if(index == list.size() - 1){
                    //添加提交语句
                    list.set(lastIndex, list.get(lastIndex)
                            /*+ "\r\n"*/ + list.get(lastIndex).substring(0,list.get(lastIndex).indexOf("i")) + "commit;");
                }
            }
        });
        return sources;
    }
    
    /** 
     * @description 删除注释和提交语句
     * @author 陈吕奖 2018-11-26
     * @param sources
     */ 
    private void deleteCommentAndCommit(List<String> sources) {
        boolean isRemove = false;
        for(Iterator<String> iterator = sources.iterator(); iterator.hasNext();) {
            //删除多行注释
            String context = iterator.next().toLowerCase().trim();
            if(context.contains("/*") && context.contains("*/") && (!context.endsWith("/") || !context.startsWith("/"))){
                //语句中的注释
                continue;
            }
            if(context.contains("/*")){
                isRemove = true;
            }
            if(context.contains("*/")){
                isRemove = false;
                iterator.remove();
                continue;
            }
            if(isRemove){
                iterator.remove();
                continue;
            }
            //删除单行注释
            if(context.trim().startsWith("--"))
                iterator.remove();
            //删除所有的commit;  删除过程体系代码 ngbossbk.p_ctrl_step_get  ngbossbk.p_ctrl_step_set
            if(context.contains("commit") || context.contains("ngbossbk.p_ctrl_step_get") || context.contains("ngbossbk.p_ctrl_step_set") || context.contains("droptable"))
                iterator.remove();
        }
    }

    /**
     * @description 根据Sql语句动态创建过程,并调用创建的存储过程
     * @author 陈吕奖 2018-10-19
     * @param sql
     * @throws Exception
     */
    private void createAndCallProceduce(String sql) throws Exception{
        db.update(sql);
        //调用存储过程
        ProcHelper helper = db.getProcHelper("clj_test_pro");
        if(itemType == 1){
            helper.setIntegerParam("v_day");
            helper.setValue("v_day", "20190113");
        }else {
            helper.setIntegerParam("v_mon");
            helper.setValue("v_mon", "201901");
        }
        helper.execute();
    }

    /**
     * @description 根据过程项输出静态的SQL语句
     * @author 陈吕奖 2018-10-19
     * @param cnt_item
     */
    public void printStaticSql(String cnt_item){
       List<Map<String,Object>> list = db.queryForList("select * from t_ryclj_9999999_sql where cnt_item = ? order by no",  cnt_item);
       for(Map<String, Object> map : list) {
           System.out.println("--" + map.get("NO"));
           System.out.print(map.get("TEXT"));
           System.out.println();
       }
    }
}