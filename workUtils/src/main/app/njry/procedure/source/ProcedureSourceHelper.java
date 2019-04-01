/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
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
 * @description ������Ϣ����
 * Ŀǰ���3�й���
 * 1.���Ի�ȡ���̵�Դ��  ��װ��list(�����Զ�����ɾ�����,�͹��̲�����Ϣ)
 * 1.1 ��ȡ���еı���(�Լ����ɵı�,  �����ı�)
 * 2.�����Զ��Ļ�ȡ���е�������
 *   2.1 ����һ����ϸ�ı�  ����   ������
 *   2.2 ������ʷ��¼ �´λ�ȡʱ,�ȴӸ��ı��л�ȡ,�ٴ����ݿ��л�ȡ
 * 3.��ѯһ���ֶε���Դ(�ṩ����,�����ֶ���   ���ɲ�����ϸ)
 * 4.�Զ����ɼ򵥵Ĺ���
 * 5.�Զ����ɽ����(���ɽ����Ĳ������)
 * 
 * @author ������
 * @date 2018-09-03
 * ���ǵ������Ƿ���Ҫ���ɾ�̬�ķ���,��������һ������()
 * ����һ������,����spring����,spring����Ϊ����Ӻܶ�Ĺ���(�ӳټ���,����,aop)(��̬����,�ڳ�������ʱ�ͻ����,ʹ�ö�����������ӳټ���)
 * ��̬��ʧȥ�������Ķ�̬���ص�
 * �����ǵ������Ƕ�����,���������������,������óɶ���(���̴߳�����©��)
 */
@Component("proInfo")
@DataSource(name = DBContextHolder.zw3)
public class ProcedureSourceHelper extends BaseHelper{
    
    public String getSourceSql = "select text from all_source a where 1 = 1 and type = 'PROCEDURE' and name like ? order by line asc";
    //ʹ��������ķ�ʽ����ֹ������д
    public String getProInfoByproNameSql = "select cnt_item \"cntItem\", item_desc \"proName\", item_type \"itemType\" from ngbossbk.v_proc_dict a where a.cnt_item = ?";
    public String getProInfoByCntItemSql = "select cnt_item \"cntItem\", item_desc \"proName\", item_type \"itemType\" from ngbossbk.v_proc_dict a where upper(a.item_desc) = ?";
    public String findDependItemInSysSql = "select cnt_user, cnt_item, cnt_name, item_desc from ngbossbk.v_proc_dict where (lower(key_word) like ? or lower(item_desc) like ?) and lower(cnt_user) = ? and lower(db_name) like ?";
    public String findDependItemInLocalSql = "select cnt_item from t_ryclj_3000437_dependitem where cnt_user = ? and db_name like ? and tablename = ?";
    public String insertCNtItemSql = "insert into t_ryclj_3000437_dependitem (cnt_user, cnt_item, cnt_name, item_desc, tablename, db_name, type) values(?, ?, ?, ?, ?, ?, ?)";
    
    /** 
     * @fields cntItem ������
     */ 
    public String cntItem;
    
    /** 
     * @fields itemName ������
     */ 
    public String proName;
    
    /** 
     * @fields itemType ��ʾ��������������   1.��      2.��
     * 1Ĭ��ֵ��ʾ���ձ���
     */ 
    public int itemType = 1;
    
    /**
     * @fields COLUMNCOUNT sql��ȡһ��Map,Map���е��ֶ�����
     */
    public static int COLUMNCOUNT = 4;
    
    /**
     * @description ���ݹ������ȡԴ��
     * @author ������ 2018-09-21
     * @param cntItem
     * @return
     * @throws Exception 
     */
    public List<String> getProSourceByCntItem(String cntItem) throws Exception{
        initByCntItem(cntItem);
        return getProSource();
    }
    
    /** 
     * @description ���ݹ�������ȡԴ��
     * @author ������ 2018-09-21
     * @param proName
     * @return
     * @throws Exception 
     */
    public List<String> getProSourceByProName(String proName) throws Exception{
        initByProName(proName);
        return getProSource();
    }
    
    /** 
     * @description ���ݹ������ʼ��
     * @author ������ 2018-09-21
     * @param cntItem
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private void initByCntItem(String cntItem) throws Exception{
        db.mapConvertToObject(db.queryForMap(getProInfoByproNameSql, cntItem), this);
    }
    
    /** 
     * @description ���ݹ�������ʼ��
     * @author ������ 2018-09-25
     * @param proName
     * @return
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    private void initByProName(String proName) throws Exception{
        db.mapConvertToObject(db.queryForMap(getProInfoByCntItemSql, proName.toUpperCase()), this);
    }
    
    /**
     * @description ���ݹ�����͹�������ȡ����Դ��(Դ��ȫ��ת����Сд)
     * @author ������ 2018-09-21
     * @param cntItem  ���ݹ������ȡ��ͬgetSource  sql���
     * @param proName  ��������
     * @return
     */
    public List<String> getProSource(){
        if(cntItem == null){
            throw new InvalidProcedure("��Ч�Ĺ���,������Ϊ��");
        }
        String getSourceSql = generateGetSourceSql();
        List<String> listStr = getSource();
        return listStr;
    }

    /** 
     * @description ���ɻ�ȡԴ���sql���
     * @author ������ 2018-12-04
     * @return
     */
    private String generateGetSourceSql() {
        //���ݲ�ͬ�Ĺ�����,���ɲ�ͬgetSource���
        if(cntItem.startsWith("3")){
        }else if(cntItem.startsWith("6")){
            getSourceSql = getSourceSql.replace("all_source", "all_source@tonjzw6");
        }else if(cntItem.startsWith("9")){
            getSourceSql = getSourceSql.replace("all_source", "all_source@topnj");
        }else{
            throw new InvalidProcedure("��ʱֻ֧��369������");
        }
        return getSourceSql;
    }
    
    /** 
     * @description ��ȡԴ��
     * @author ������ 2018-12-05
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
     * @description �Զ����ɹ���ɾ��������
     * @author ������ 2018-09-21
     * @param sources
     */
    public void getDeleteStatement(List<String> sources){
        ProcedureUtils.getDeleteSrateByArray(sources.toArray(new String[]{}));
    }
    
    /** 
     * @description �Զ����ɹ��̱�����Ϣ
     * @author ������ 2018-09-21
     * @param sources
     */ 
    public void getVariableInfo(List<String> sources){
        ProcedureUtils.getVariableInfoByArray(sources.toArray(new String[]{}));
    }
    
    /** 
     * @description ����Դ���ȡ������,�����еı���
     * ���е�sql������Ϸ��淶
     * @author ������ 2018-09-25
     * @param sources
     * @return ���ؼ���Ԫ��ΪString[]�ļ���  ����Ԫ��Ϊ 1.�û���  2.����  3.���ݿ���
     */
    public Set<FullTableName> getAllTableNames(List<String> sources){
        //���кͷ���ֵ��صı��������ڵ�һ��
        sources = getValidContext(sources);
        String[] _tableNamesParts = getValidTableNames(sources);
        Set<FullTableName> tableNames = getAllFullTableNames(_tableNamesParts);
        return tableNames;
    }

    /** 
     * @description ��Դ���л�ȡ��Ч����
     * @author ������ 2018-11-26
     * @param sources
     */ 
    private List<String> getValidContext(List<String> sources) {
        //ɾ��û�а���from��
        sources = deleteItemNotContainsContext(sources, "from");
        //��ȡ����
        sources = getTableNameString(sources);
        //���޳�����(����select)
        sources = deleteItemContainsContext(sources, "select");
        return sources;
    }
    
    /** 
     * @description ����Ԫ���в�����ĳ���ַ�����ɾ��
     * @author ������ 2018-11-26
     * @param iterator
     */
    private List<String> deleteItemNotContainsContext(List<String> sources, String Context) {
        Iterator<String> iterator = sources.iterator();
        while(iterator.hasNext()){
            String next = iterator.next();
            //���û�а���from��ɾ��Ԫ��
            if(!next.contains(Context)){
                iterator.remove();
            }
        }
        return sources;
    }
    
    /** 
     * @description ��ȡ�����ַ���,"from ��where֮ǰ������"
     * @author ������ 2018-11-26
     * @param sources
     */ 
    private List<String> getTableNameString(List<String> sources) {
        //��from�滻Ϊ��(�൱�ڽ�ȡ��from��where֮�������,��������ǿ��еĽ��޷���ȡ����)
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
     * @description ����Ԫ���а���ĳ���ַ�����ɾ��
     * @author ������ 2018-11-26
     * @param iterator
     */ 
    private List<String> deleteItemContainsContext(List<String> sources, String Context) {
        Iterator<String> iterator = sources.iterator();
        while(iterator.hasNext()){
            String next = iterator.next();
            //���û�а���from��ɾ��Ԫ�� "(".equals(next.trim()) ������������
            if(next.contains(Context) || "(".equals(next.trim())){
                iterator.remove();
            }
        }
        return sources;
    }
    
    /**
     * @description ��ȡ��Ч�ı���(������һ����)
     * @author ������ 2018-11-26
     * @param sources
     * @return
     */
    private String[] getValidTableNames(List<String> sources) {
        String[] tableNames = PatternUtils.getMatcherStr("\\s*([\\s|\\d|\\w|\\.|@|\\||']+\\s*\\w?,?)", sources, 1,0);
        for(String string : tableNames) {
            log.debug(string);
        }
        for(int i = 0; i < tableNames.length; i++) {
            //֪ʶ�� 1. .�������б�ʾ�����\r\n(��Java��ֻ����()��ʹ��)  2. [\\s|\\S] ���Ա�ʾ�����ַ�  �ո�ͷǿո�
            //1.��1.' 2.' ' 3.b �������Ϳո�ȥ��,��ȡ����(�����ı���)   ��һ������
            //2.��������'|| v_mon ||'�滻Ϊ��    �ڶ�������
            tableNames[i] = tableNames[i].replaceAll("([\\s][a-z])?[,|'|\\s]*$", "").replaceAll("'[\\s|\\S]+'", "").trim();
        }
        for(String string : tableNames) {
            log.debug(string);
        }
        return tableNames;
    }
    
    /** 
     * @description ��ȡ���е���������
     * @author ������ 2018-11-26
     * @param tableNames
     * @param _tableNamesParts ���ֱ���
     */ 
    public Set<FullTableName> getAllFullTableNames(String[] _tableNamesParts) {
        Set<FullTableName> tableNames = new HashSet<FullTableName>();
        /*
         * ����һ��String[]�洢��TableName������(3������ȫ��ת����Сд)
         * 1.��ȡ���û���
         * 2.��ȡ���ݿ���
         * 3.��ȡ������
         */
        for(int i = 0; i < _tableNamesParts.length; i++) 
        {
            FullTableName table = getFullTableName(_tableNamesParts[i]);
            tableNames.add(table);
        }
        return tableNames;
    }

    /** 
     * @description ���ݱ�����ȡ�����ı���
     * @author ������ 2018-11-26
     * @param tableNamePart
     * @param table
     */ 
    private FullTableName getFullTableName(String tableNamePart) {
        FullTableName table = new FullTableName();
        String[] split;
        String dataBaseName = "";
        //�����û���
        if(tableNamePart.contains("."))
        {
            split = tableNamePart.split("\\.");//split���Ը���������ʽ��ȡ�ַ���, .�������б�ʾ�����ַ�,������Ҫ����ת��
            table.userName = split[0].toLowerCase();
            tableNamePart = split[1];
        }else{
            table.userName = "jtb";
        }
        //�������ݿ���
        if(tableNamePart.contains("@"))
        {
            split = tableNamePart.split("@");
            table.dataBaseName = split[1].substring(2).toLowerCase();
            table.setTableName(split[0].toLowerCase());
        }else{
            //��ʼ�����ݿ���cntItem
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
     * @description ��ȡ���б�����������(������3����һ�����ñ�)
     * @author ������ 2018-10-08
     * @param tabNames  ���еı���
     * @param cntItem  ������
     * @return
     */
    @SuppressWarnings("unchecked")
    public Set<FullTableName> getDependItemCntByTabName(Set<FullTableName> tabNames){
        //1.���Լ������ñ�����
        tabNames = deleteMyItemCntTable(tabNames);
        //2.��ϵͳ�����ñ�����
        findDependItemCntByConfigTable(tabNames);
        //3.��ϵͳ�в���������,�����ҵ�����Ϣ��ӵ��Լ����ñ���
        findDependItemCntInSystemTable(tabNames);
        return tabNames;
    }

    /** 
     * @description �޳��������Լ������ı�
     * @author ������ 2018-11-26
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
     * @description ���Լ��������ļ��в���������
     * @author ������ 2018-11-26
     * @param tabNames
     * @return
     */ 
    private void findDependItemCntByConfigTable(Set<FullTableName> tabNames) {
        Map<?,?> queryForMap;
        for(FullTableName tableName : tabNames) {
            //����������Ѿ����ڹ�����,��ֱ��ʹ�ù�����
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
     * @description ��ϵͳ�л�ȡ������
     * @author ������ 2018-11-26
     * @param tabNames
     */
    private void findDependItemCntInSystemTable(Set<FullTableName> tabNames) {
        Map<?,?> queryForMap;
        //��ϵͳ�����ļ��в���
        for(FullTableName tableName : tabNames) {
            //����Ѿ���ȡ������Ͳ���ϵͳ���ñ��в���
            if(tableName.cntItem != null)
                continue;
            queryForMap = db.queryForMap(findDependItemInSysSql, "%"+tableName.getTableName()+"%", "%"+tableName.getTableName()+"%", tableName.userName, "%"+tableName.dataBaseName+"%");
            log.debug(StringUtils.getSql(findDependItemInSysSql, "%"+tableName.getTableName()+"%", "%"+tableName.getTableName()+"%", tableName.userName, "%"+tableName.dataBaseName+"%"));
            log.info(queryForMap.size());
            //������ҵ�����Map�����͵���COLUMNCOUNT
            if(queryForMap.size() == COLUMNCOUNT) {
                log.info(queryForMap.get("cnt_item").toString());
                tableName.cntItem = queryForMap.get("cnt_item").toString();
                //�����ҵ�����������ӵ��Լ������ñ���
                db.update(insertCNtItemSql, queryForMap.get("cnt_user").toString().toLowerCase(), queryForMap.get("cnt_item").toString().toLowerCase(),
                        queryForMap.get("cnt_name").toString().toLowerCase(), queryForMap.get("item_desc").toString().toLowerCase(),
                        tableName.getTableName(), tableName.dataBaseName, 0);
            }
        }
    }
    
    /** 
     * @description ��ȡָ����������̵�ִ�����
     * @author ������ 2018-12-05
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
     * @description ���ݹ��̵�Դ��,����clj_test_pro����Դ��
     * ��������ű����� 1.t_ryclj_9999999_sql   2.t_ryclj_9999999_parameter
     * @author ������ 2018-10-18
     * @param sources  Դ��List(��ԭ�е�List���޸�)
     * @return
     */ 
    private List<String> generateProcedureSourceBySources(List<String> sources) {
        //��ȡ��������
        final String proName = PatternUtils.getMatcherStr("procedure\\s+(\\S+)\\s*\\(", sources.get(0)).toLowerCase();
        //������������(��ʼ�Ĺ�����)
        sources.set(0, "create or replace " + sources.get(0).toLowerCase().replace(proName, "clj_test_pro"));
        //ɾ�����е�ע��(����ע�ͺͶ���ע�ͺ��ύ)
        deleteCommentAndCommit(sources);
        //v_column_area1 ��������Ǹ���(������ֵ�漰���˶���)
        //�����\r\nע����, ��Ϊ�������Oracle�޷������Ľ�������,���¹�����Ч
        //�鿴���ɵ��ַ�����������Ϊ\r\n�Ѿ��������ɻ�����(����,�鿴Դ��ʱ,�����ֶ��ı��벻����)
        ListUtils.forEach(sources, new ForEach<String>(){
            //����ִ�е�˳��
            int no = 0;
            //��¼���һ���滻��λ��
            int lastIndex = 0;
            @Override
            //������ʹ��forѭ����Ч�ʸ���,��������Ҫ�ǲ��� ʹ�ýӿ���ģ��ص�����
            public void forEach(List<String> list, int index, String element) {
                //
                element = element.toLowerCase();
                if(element.contains(proName)){
                    list.set(index, element.replaceAll(proName, "clj_test_pro"));
                }else if(element.trim().equalsIgnoreCase("begin")){
                    //����������ñ��ɾ�����,���ҽ�v_step ֵ����Ϊ0,��Ȼִ�в���
                    list.set(index,element + "  delete from t_ryclj_9999999_sql where cnt_item = " + cntItem + ";" /*+ "\r\n" */
                            + "  delete from t_ryclj_9999999_parameter where cnt_item = " + cntItem + ";" /*+ "\r\n" */
                            + "  v_step         := 0;" /*+ "\r\n" */
                            + "  v_flag         := 0;");
                }else if(element.contains(":=") && !element.contains("sql") && element.contains(";")){//;��ʾ�ֶθ�ֵ��һ�������
                    //���ӱ����������
                    //��ȡ����������
                    //v_column_area1 ��������Ǹ���,���԰�������һ���������ݽ���(����)
                    //insert into t_ryclj_9999999_parameter values (3000468,'v_step', v_step);
                    //  v_step         := 0;
                    String paramterName = PatternUtils.getMatcherStr("\\s*(\\S+)\\s*:=", element);
                    list.set(index, element + 
                             "  insert into t_ryclj_9999999_parameter values ("+ cntItem +",'"+ paramterName +"', "+ paramterName +");" /*+ "\r\n"*/);
                }
                if(element.matches("\\s*execute\\s+immediate\\s+v_sql\\s*(into)?\\s*\\S*\\s*;[\\s*|\t|\r|\n]")){
                    //�滻ִ�����
                    lastIndex = index;
                    list.set(index, element.substring(0,element.indexOf("e")) 
                            +"insert into t_ryclj_9999999_sql values ("+ ++no +", "+ cntItem +", v_sql);");
                }
                if(index == list.size() - 1){
                    //����ύ���
                    list.set(lastIndex, list.get(lastIndex)
                            /*+ "\r\n"*/ + list.get(lastIndex).substring(0,list.get(lastIndex).indexOf("i")) + "commit;");
                }
            }
        });
        return sources;
    }
    
    /** 
     * @description ɾ��ע�ͺ��ύ���
     * @author ������ 2018-11-26
     * @param sources
     */ 
    private void deleteCommentAndCommit(List<String> sources) {
        boolean isRemove = false;
        for(Iterator<String> iterator = sources.iterator(); iterator.hasNext();) {
            //ɾ������ע��
            String context = iterator.next().toLowerCase().trim();
            if(context.contains("/*") && context.contains("*/") && (!context.endsWith("/") || !context.startsWith("/"))){
                //����е�ע��
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
            //ɾ������ע��
            if(context.trim().startsWith("--"))
                iterator.remove();
            //ɾ�����е�commit;  ɾ��������ϵ���� ngbossbk.p_ctrl_step_get  ngbossbk.p_ctrl_step_set
            if(context.contains("commit") || context.contains("ngbossbk.p_ctrl_step_get") || context.contains("ngbossbk.p_ctrl_step_set") || context.contains("droptable"))
                iterator.remove();
        }
    }

    /**
     * @description ����Sql��䶯̬��������,�����ô����Ĵ洢����
     * @author ������ 2018-10-19
     * @param sql
     * @throws Exception
     */
    private void createAndCallProceduce(String sql) throws Exception{
        db.update(sql);
        //���ô洢����
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
     * @description ���ݹ����������̬��SQL���
     * @author ������ 2018-10-19
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