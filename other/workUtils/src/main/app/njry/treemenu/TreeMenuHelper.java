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
 * @description 操作菜单节点
 *              项目的帮助类,完成一些简单的操作.(例如.节点树里节点的移动)
 *              对节点的操作默认情况是根据名字操作,如果有重名的情况,将操作不成功
 * @author 陈吕奖
 * @date 2018-07-13
 */
@Component("treeMenuHelper")
@DataSource(name = DBContextHolder.zw6)
public class TreeMenuHelper extends BaseHelper {

    public static String insertNodeSql = "insert into t_tree_menu (node_id,node_name,node_url,superior_node_id,node_level,status ) values (?,?,?,23706,2,1 )";
    public static String permissionSql = "insert into t_role_tree values (1, ?)";
    
    /**
     * @description 添加一个节点(有默认的父节点,默认管理员的权限)
     * @author 陈吕奖 2018-07-13 'report/yhgczw!Frame.jspa' 报表层的访问地址
     * @param nodeName 节点的名称
     * @param url 节点的访问路径
     */
    public void addNode(String nodeName, String url) {
        //添加节点
        BatchSql batchSql = new BatchSql();
        //先获取序列值
        String seq = db.getNextSequenceValue("t_tree_menu_s_id");
        batchSql.addBatch(insertNodeSql, new Object[]{seq, nodeName, url});
        batchSql.addBatch(permissionSql, new Object[]{seq});
        db2.doInTransaction(batchSql);
    }
    
    /** 
     * @description 在指定父节点下,新建一个节点(默认管理员的权限)
     * @author 陈吕奖 2018-07-16
     * @param parentNodeId
     * @param nodeName
     * @param url
     */ 
    public void addNode(String parentNodeId, String nodeName, String url) {
        String sql = "insert into t_tree_menu (node_id,node_name,node_url,superior_node_id,node_level,status ) values (?,?,?,?,2,1 )";
        //添加节点
        BatchSql batchSql = new BatchSql();
        //先获取序列值
        String seq = db.getNextSequenceValue("t_tree_menu_s_id");
        batchSql.addBatch(sql, new Object[]{seq, nodeName, url, parentNodeId});
        batchSql.addBatch(permissionSql, new Object[]{seq});
        db2.doInTransaction(batchSql);
    }

    /** 
     * @description 根据nodeName获取nodeID(默认情况下,一个节点的名字对应一个节点ID,如果对应多个节点id那么程序报错)
     * @author 陈吕奖 2018-07-17
     * @param nodeName
     * @return
     */ 
    public String getNodeIdByNodeName(String nodeName){
        /*
         * 理论上不会报错,为什么要将int值转换为String
         */
        String sql = "select node_id from t_tree_menu where node_name = ? and status = 1";
        int i = db.queryForInt(sql, nodeName);
        if(i == -1){
            throw new RuntimeException("没有查询到结果或查询结果不唯一");
        }else{
            return i + "";
        }
    }
    
    /** 
     * @description 给节点添加角色信息
     * @author 陈吕奖 2018-07-17
     * @param nodeName  节点名称
     * @param siblingNodeName 兄弟节点
     */
    public void addRoles(String nodeName, String siblingNodeName){
        BatchSql batchSql = new BatchSql();
        //用户存储所有的子节点信息
        List<Map<String, Object>> list = null;
        //list2用于简化list的取值
        List<String> list2 = new ArrayList<String>();
        //0.检测节点的名称是否唯一
        if( !cheakNodeName(nodeName) || !cheakNodeName(siblingNodeName)){
            log.debug("获取节点报错");
            return;
        }
        //1.获取所有需要添加角色信息的节点ID
        String sql = "";
        String nodeId = getNodeIdByNodeName(nodeName);
        if(isParentNode(nodeId)){
            list = getAllNode(nodeId, 0);
        }
        //2.删除当前节点的角色信息(先使用备表来做测试)
        sql = "delete from t_role_tree where node_id = ?";
        batchSql.addBatch(sql, new Object[]{nodeId});
        if(list != null){
            for(Map map2 : list) {
                log.debug(map2.get("node_id"));
                batchSql.addBatch(sql, new Object[]{map2.get("node_id")});
            }
        }
        //3.获取目标节点的角色信息
        String sibNodeId = getNodeIdByNodeName(siblingNodeName);
        sql = "select role_id from t_role_tree where node_id = ?";
        List<Map<String,Object>> queryForList = db2.queryForList(sql, new Object[]{sibNodeId});
        for(Map<String, Object> map : queryForList) {
            list2.add(map.get("role_id").toString());
        }
        //4.给所有的节点添加上角色信息
        sql = "insert into t_role_tree(role_id, node_id) values (?,?)";
        if(list != null){
            for(Map<String, Object> map : list) {
                //所有的子节点
                for(String role_id : list2){
                    //所有的权限信息
                    batchSql.addBatch(sql, new Object[]{role_id, map.get("node_id")});
                }
            }
        }
        //给顶端节点添加权限信息
        for(String role_id : list2){
            //所有的权限信息
            batchSql.addBatch(sql, new Object[]{role_id, nodeId});
        }
        db2.doInTransaction(batchSql);
    }
    
    /** 
     * @description 检测输入的节点名称是不是唯一的
     * @author 陈吕奖 2018-07-17
     * @param nodeName 节点的名称
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
     * @description 将节点移动到某个节点旁边
     * @author 陈吕奖 2018-07-13
     * @param nodeName 节点名称
     * @param siblingNodeName  兄弟节点
     */
    public void moveNode(String nodeName, String siblingNodeName){
        BatchSql batchSql = new BatchSql();
        //因为是根据名字查找,可能会存在重名的现象,所以这里价格判断,如果根据名字查找到1条记录以上,直接退出
        String sql = "";
        
        if( !cheakNodeName(nodeName) || !cheakNodeName(siblingNodeName)){
            log.debug("获取节点报错");
            return;
        }
        //获取兄弟节点的父节点和节点的等级 node_level
        sql = "select superior_node_id parentNode, node_level from t_tree_menu where node_name like ? and status = 1";
        Map map = db2.queryForMap(sql, siblingNodeName);
        
        //获取节点nodeId
        String nodeId = getNodeIdByNodeName(nodeName);
        
        //判断该节点是不是父节点
        if(isParentNode(nodeId)){
            int node_level = Integer.parseInt(map.get("node_level").toString());
            //表示为父节点,默认为多个层级的,使用递归进行移动
            List<Map<String, Object>> list = getAllNode(nodeId, ++node_level);
            sql = "update t_tree_menu set node_level = ? where node_id = ?";
            for(Map map2 : list) {
                log.debug(map2.get("node_id"));
                log.debug(map2.get("nodeLevel"));
                batchSql.addBatch(sql, new Object[]{map2.get("nodeLevel"), map2.get("node_id")});
            }
        }
        //不管是不是父节点,这一步都要做(修改最顶级的节点)
        sql = "update t_tree_menu set superior_node_id = ? , node_level = ? where node_id = ?";
        batchSql.addBatch(sql, new Object[]{map.get("parentNode"), map.get("node_level"), nodeId});
        db2.doInTransaction(batchSql);
    }
    
    /** 
     * @description 获取一个节点下所有的节点信息(包括节点的节点)
     * 子节点的子节点,只需要更新节点的级别
     * @author 陈吕奖 2018-07-16
     * @param nodeList 一个节点的所有节点
     * @param nodeLevel 当前节点的等级
     * @return
     */
    public List<Map<String, Object>> getAllNode(String nodeId, int nodeLevel){
        //创建两个
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
        //添加一层的所有节点
        list.addAll(getChildNodes(nodeId, nodeLevel));
        for(Map map : list) {
            String childNodeId = map.get("node_id").toString();
            //判断该层节点中是否有父节点
            if(isParentNode(childNodeId)){
                list1.addAll(getAllNode(childNodeId, ++nodeLevel));
            }
        }
        list.addAll(list1);
        return list;
    }
    
    /** 
     * @description 获取一个节点的所有子节点,不包含节点的节点
     * @author 陈吕奖 2018-07-16
     * @param nodeId
     * @return
     */ 
    public List<Map<String, Object>> getChildNodes(String nodeId, int nodeLevel){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        String sql = "select node_id from t_tree_menu where superior_node_id = ? and status = 1 ";
        List<Map<String,Object>> queryForList = db2.queryForList(sql, new Object[]{nodeId});
        for(Map<String, Object> map : queryForList) {
            //额外添加一个节点的等级信息
            map.put("nodeLevel", nodeLevel);
            list.add(map);
        }
        return list;
    }
    
    /** 
     * @description 判断一个节点是否是父节点
     * @author 陈吕奖 2018-07-16
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
     * @author 陈吕奖 2018-10-16
     * @param nodeId
     */
    /** 
     * @description 为一个节点添加报表配置信息
     * @author 陈吕奖 2019-01-29
     * @param nodeId   菜单节点编号
     * @param report
     */ 
    public void insertReportConfigByNodeId(String nodeId, ReportCfg report){
        String sql = "select * from t_tree_menu where node_id = ?";
        Map<String,Object> map = db.queryForMap(sql, nodeId);
        log.debug("dd");
        //获取节点的路径
        String reportPah = "";
        if(isParentNode(nodeId)){
            reportPah = getReportPathByNodeId(nodeId);
        }else {
            reportPah = getReportPathByNodeId(map.get("SUPERIOR_NODE_ID").toString());
        }
//        ReportCfg report = new ReportCfg("需求编号", "需求人姓名", "没有过程名", reportPah ,map);
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
     * @description 为一个节点添加报表配置信息 根据nodeName
     * @author 陈吕奖 2018-10-16
     * @param nodeId
     */
    public void insertReportConfigByNodeName(String nodeName, ReportCfg report){
        String nodeId = getNodeIdByNodeName(nodeName);
        insertReportConfigByNodeId(nodeId, report);
    }
    
    /** 
     * @description 根据节点获取报表访问的菜单路径
     * @author 陈吕奖 2018-10-16
     * @param nodeId
     * @return
     */ 
    @SuppressWarnings("unchecked")
    public String getReportPathByNodeId(String nodeId){
        //根据父节点获取子节点(用这个,这样出来的路径地址能直接使用)
        String sql = "select node_id ,superior_node_id," +
            "                substr(sys_connect_by_path(a.node_name, '→'), 2) path"+
            "           from (select 0 node_id, -1 superior_node_id, '客服营销管理系统' node_name"+
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