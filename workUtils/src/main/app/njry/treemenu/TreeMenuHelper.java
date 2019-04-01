package main.app.njry.treemenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import main.helper.BaseHelper;
import main.helper.database.sql.BatchSql;
import main.spring.database.aspect.DataSource;
import main.spring.database.multDataSource.DBContextHolder;
import main.utils.common.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @description �����˵��ڵ�
 *              ��Ŀ�İ�����,���һЩ�򵥵Ĳ���.(����.�ڵ�����ڵ���ƶ�)
 *              �Խڵ�Ĳ���Ĭ������Ǹ������ֲ���,��������������,���������ɹ�
 * @author ������
 * @date 2018-07-13
 */
@Component("treeMenuHelper")
@DataSource(name = DBContextHolder.zw6)
public class TreeMenuHelper extends BaseHelper {

    public static String insertNodeSql = "insert into t_tree_menu (node_id,node_name,node_url,superior_node_id,node_level,status ) values (?,?,?,23706,2,1 )";
    public static String permissionSql = "insert into t_role_tree values (1, ?)";
    
    /**
     * @description ���һ���ڵ�(��Ĭ�ϵĸ��ڵ�,Ĭ�Ϲ���Ա��Ȩ��)
     * @author ������ 2018-07-13 'report/yhgczw!Frame.jspa' �����ķ��ʵ�ַ
     * @param nodeName �ڵ������
     * @param url �ڵ�ķ���·��
     */
    public void addNode(String nodeName, String url) {
        //��ӽڵ�
        BatchSql batchSql = new BatchSql();
        //�Ȼ�ȡ����ֵ
        String seq = db.getNextSequenceValue("t_tree_menu_s_id");
        batchSql.addBatch(insertNodeSql, new Object[]{seq, nodeName, url});
        batchSql.addBatch(permissionSql, new Object[]{seq});
        db2.doInTransaction(batchSql);
    }
    
    /** 
     * @description ��ָ�����ڵ���,�½�һ���ڵ�(Ĭ�Ϲ���Ա��Ȩ��)
     * @author ������ 2018-07-16
     * @param parentNodeId
     * @param nodeName
     * @param url
     */ 
    public void addNode(String parentNodeId, String nodeName, String url) {
        String sql = "insert into t_tree_menu (node_id,node_name,node_url,superior_node_id,node_level,status ) values (?,?,?,?,2,1 )";
        //��ӽڵ�
        BatchSql batchSql = new BatchSql();
        //�Ȼ�ȡ����ֵ
        String seq = db.getNextSequenceValue("t_tree_menu_s_id");
        batchSql.addBatch(sql, new Object[]{seq, nodeName, url, parentNodeId});
        batchSql.addBatch(permissionSql, new Object[]{seq});
        db2.doInTransaction(batchSql);
    }

    /** 
     * @description ����nodeName��ȡnodeID(Ĭ�������,һ���ڵ�����ֶ�Ӧһ���ڵ�ID,�����Ӧ����ڵ�id��ô���򱨴�)
     * @author ������ 2018-07-17
     * @param nodeName
     * @return
     */ 
    public String getNodeIdByNodeName(String nodeName){
        /*
         * �����ϲ��ᱨ��,ΪʲôҪ��intֵת��ΪString
         */
        String sql = "select node_id from t_tree_menu where node_name = ? and status = 1";
        int i = db.queryForInt(sql, nodeName);
        if(i == -1){
            throw new RuntimeException("û�в�ѯ��������ѯ�����Ψһ");
        }else{
            return i + "";
        }
    }
    
    /** 
     * @description ���ڵ���ӽ�ɫ��Ϣ
     * @author ������ 2018-07-17
     * @param nodeName  �ڵ�����
     * @param siblingNodeName �ֵܽڵ�
     */
    public void addRoles(String nodeName, String siblingNodeName){
        BatchSql batchSql = new BatchSql();
        //�û��洢���е��ӽڵ���Ϣ
        List<Map<String, Object>> list = null;
        //list2���ڼ�list��ȡֵ
        List<String> list2 = new ArrayList<String>();
        //0.���ڵ�������Ƿ�Ψһ
        if( !cheakNodeName(nodeName) || !cheakNodeName(siblingNodeName)){
            log.debug("��ȡ�ڵ㱨��");
            return;
        }
        //1.��ȡ������Ҫ��ӽ�ɫ��Ϣ�Ľڵ�ID
        String sql = "";
        String nodeId = getNodeIdByNodeName(nodeName);
        if(isParentNode(nodeId)){
            list = getAllNode(nodeId, 0);
        }
        //2.ɾ����ǰ�ڵ�Ľ�ɫ��Ϣ(��ʹ�ñ�����������)
        sql = "delete from t_role_tree where node_id = ?";
        batchSql.addBatch(sql, new Object[]{nodeId});
        if(list != null){
            for(Map map2 : list) {
                log.debug(map2.get("node_id"));
                batchSql.addBatch(sql, new Object[]{map2.get("node_id")});
            }
        }
        //3.��ȡĿ��ڵ�Ľ�ɫ��Ϣ
        String sibNodeId = getNodeIdByNodeName(siblingNodeName);
        sql = "select role_id from t_role_tree where node_id = ?";
        List<Map<String,Object>> queryForList = db2.queryForList(sql, new Object[]{sibNodeId});
        for(Map<String, Object> map : queryForList) {
            list2.add(map.get("role_id").toString());
        }
        //4.�����еĽڵ�����Ͻ�ɫ��Ϣ
        sql = "insert into t_role_tree(role_id, node_id) values (?,?)";
        if(list != null){
            for(Map<String, Object> map : list) {
                //���е��ӽڵ�
                for(String role_id : list2){
                    //���е�Ȩ����Ϣ
                    batchSql.addBatch(sql, new Object[]{role_id, map.get("node_id")});
                }
            }
        }
        //�����˽ڵ����Ȩ����Ϣ
        for(String role_id : list2){
            //���е�Ȩ����Ϣ
            batchSql.addBatch(sql, new Object[]{role_id, nodeId});
        }
        db2.doInTransaction(batchSql);
    }
    
    /** 
     * @description �������Ľڵ������ǲ���Ψһ��
     * @author ������ 2018-07-17
     * @param nodeName �ڵ������
     * @return
     */ 
    public boolean cheakNodeName(String nodeName){
        String sql = "select count(1) from t_tree_menu where node_name = ? and status = 1";
        int count = db2.queryForInt(sql, nodeName);
        if(count != 1){
            return false;
        }
        return true;
    }
    
    /** 
     * @description ���ڵ��ƶ���ĳ���ڵ��Ա�
     * @author ������ 2018-07-13
     * @param nodeName �ڵ�����
     * @param siblingNodeName  �ֵܽڵ�
     */
    public void moveNode(String nodeName, String siblingNodeName){
        BatchSql batchSql = new BatchSql();
        //��Ϊ�Ǹ������ֲ���,���ܻ��������������,��������۸��ж�,����������ֲ��ҵ�1����¼����,ֱ���˳�
        String sql = "";
        
        if( !cheakNodeName(nodeName) || !cheakNodeName(siblingNodeName)){
            log.debug("��ȡ�ڵ㱨��");
            return;
        }
        //��ȡ�ֵܽڵ�ĸ��ڵ�ͽڵ�ĵȼ� node_level
        sql = "select superior_node_id parentNode, node_level from t_tree_menu where node_name like ? and status = 1";
        Map map = db2.queryForMap(sql, siblingNodeName);
        
        //��ȡ�ڵ�nodeId
        String nodeId = getNodeIdByNodeName(nodeName);
        
        //�жϸýڵ��ǲ��Ǹ��ڵ�
        if(isParentNode(nodeId)){
            int node_level = Integer.parseInt(map.get("node_level").toString());
            //��ʾΪ���ڵ�,Ĭ��Ϊ����㼶��,ʹ�õݹ�����ƶ�
            List<Map<String, Object>> list = getAllNode(nodeId, ++node_level);
            sql = "update t_tree_menu set node_level = ? where node_id = ?";
            for(Map map2 : list) {
                log.debug(map2.get("node_id"));
                log.debug(map2.get("nodeLevel"));
                batchSql.addBatch(sql, new Object[]{map2.get("nodeLevel"), map2.get("node_id")});
            }
        }
        //�����ǲ��Ǹ��ڵ�,��һ����Ҫ��(�޸�����Ľڵ�)
        sql = "update t_tree_menu set superior_node_id = ? , node_level = ? where node_id = ?";
        batchSql.addBatch(sql, new Object[]{map.get("parentNode"), map.get("node_level"), nodeId});
        db2.doInTransaction(batchSql);
    }
    
    /** 
     * @description ��ȡһ���ڵ������еĽڵ���Ϣ(�����ڵ�Ľڵ�)
     * �ӽڵ���ӽڵ�,ֻ��Ҫ���½ڵ�ļ���
     * @author ������ 2018-07-16
     * @param nodeList һ���ڵ�����нڵ�
     * @param nodeLevel ��ǰ�ڵ�ĵȼ�
     * @return
     */
    public List<Map<String, Object>> getAllNode(String nodeId, int nodeLevel){
        //��������
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
        //���һ������нڵ�
        list.addAll(getChildNodes(nodeId, nodeLevel));
        for(Map map : list) {
            String childNodeId = map.get("node_id").toString();
            //�жϸò�ڵ����Ƿ��и��ڵ�
            if(isParentNode(childNodeId)){
                list1.addAll(getAllNode(childNodeId, ++nodeLevel));
            }
        }
        list.addAll(list1);
        return list;
    }
    
    /** 
     * @description ��ȡһ���ڵ�������ӽڵ�,�������ڵ�Ľڵ�
     * @author ������ 2018-07-16
     * @param nodeId
     * @return
     */ 
    public List<Map<String, Object>> getChildNodes(String nodeId, int nodeLevel){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        String sql = "select node_id from t_tree_menu where superior_node_id = ? and status = 1 ";
        List<Map<String,Object>> queryForList = db2.queryForList(sql, new Object[]{nodeId});
        for(Map<String, Object> map : queryForList) {
            //�������һ���ڵ�ĵȼ���Ϣ
            map.put("nodeLevel", nodeLevel);
            list.add(map);
        }
        return list;
    }
    
    /** 
     * @description �ж�һ���ڵ��Ƿ��Ǹ��ڵ�
     * @author ������ 2018-07-16
     * @param nodeId
     * @return
     */ 
    public boolean isParentNode(String nodeId){
        String sql = "select count(1) from t_tree_menu where superior_node_id = ? and status = 1";
        if(db2.queryForInt(sql, nodeId) >= 1){
            return true;
        }
        return false;
    }
    
    /**
     * @description 
     * @author ������ 2018-10-16
     * @param nodeId
     */
    /** 
     * @description Ϊһ���ڵ���ӱ���������Ϣ
     * @author ������ 2019-01-29
     * @param nodeId   �˵��ڵ���
     * @param report
     */ 
    public void insertReportConfigByNodeId(String nodeId, ReportCfg report){
        String sql = "select * from t_tree_menu where node_id = ?";
        Map<String,Object> map = db.queryForMap(sql, nodeId);
        log.debug("dd");
        //��ȡ�ڵ��·��
        String reportPah = "";
        if(isParentNode(nodeId)){
            reportPah = getReportPathByNodeId(nodeId);
        }else {
            reportPah = getReportPathByNodeId(map.get("SUPERIOR_NODE_ID").toString());
        }
//        ReportCfg report = new ReportCfg("������", "����������", "û�й�����", reportPah ,map);
        report.report_path = reportPah;
        report.setMap(map);
        sql = " insert into t_crm_report_cfg"
            + "        (id, report_name, report_type, dmid, dmname, proc_name, proc_author, database, db_username, report_path,"
            + "        remark, node_id, superior_node_id, url, node_level, node_order, status, create_id, create_time)"
            + " values (seq_t_crm_report_cfg_id.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
            + "        ?, ?, ?, ?, ?, ?, 0, ?, sysdate)";
        log.debug(StringUtils.getSql(sql, report.report_name, report.report_type, report.dmid, report.dmname,
                report.proc_name, report.proc_author, report.database, report.db_username, report.report_path,"", report.node_id, report.superior_node_id,
                report.url, report.node_level, report.node_order, 14999));
        db.update(sql, report.report_name, report.report_type, report.dmid, report.dmname,
                  report.proc_name, report.proc_author, report.database, report.db_username, report.report_path,"", report.node_id, report.superior_node_id,
                  report.url, report.node_level, report.node_order, 14999);
        log.debug(map);
        log.debug(report);
        log.debug(reportPah);
    }
    
    /**
     * @description Ϊһ���ڵ���ӱ���������Ϣ ����nodeName
     * @author ������ 2018-10-16
     * @param nodeId
     */
    public void insertReportConfigByNodeName(String nodeName, ReportCfg report){
        String nodeId = getNodeIdByNodeName(nodeName);
        insertReportConfigByNodeId(nodeId, report);
    }
    
    /** 
     * @description ���ݽڵ��ȡ������ʵĲ˵�·��
     * @author ������ 2018-10-16
     * @param nodeId
     * @return
     */ 
    @SuppressWarnings("unchecked")
    public String getReportPathByNodeId(String nodeId){
        //���ݸ��ڵ��ȡ�ӽڵ�(�����,����������·����ַ��ֱ��ʹ��)
        String sql = "select node_id ,superior_node_id," +
            "                substr(sys_connect_by_path(a.node_name, '��'), 2) path"+
            "           from (select 0 node_id, -1 superior_node_id, '�ͷ�Ӫ������ϵͳ' node_name"+
            "                   from dual"+
            "                  union"+
            "                 select node_id, superior_node_id, node_name"+
            "                   from t_tree_menu"+
            "                  where status = 1) a"+
            "          where node_id = ?"+
            "          start with a.superior_node_id = -1"+
            "        connect by prior a.node_id = a.superior_node_id";
        Map<String,Object> map = db.queryForMap(sql, nodeId);
        return map.get("path").toString();
    }
    
}