/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.app.njry.treemenu;

import java.util.Map;

/** 
 * @description 实例化报表描述对象
 * @author 陈吕奖
 * @date 2018-10-16 
 */
public class ReportCfg {
    
    /** 
     * @fields report_name 报表节点名称
     */ 
    public String report_name;
    
    /** 
     * @fields report_type 有默认值
     * 1是日报    2是月报   3功能
     */ 
    public String report_type = "1";

    /** 
     * @fields dmid 需求编号
     */ 
    public String dmid;

    /** 
     * @fields dmname 需求人姓名
     */ 
    public String dmname;

    /** 
     * @fields proc_name 过程名
     */ 
    public String proc_name;

    /** 
     * @fields proc_author 过程作者,默认自己
     */ 
    public String proc_author = "陈吕奖";

    public String database = "njzw3";

    public String db_username = "jtb";

    /** 
     * @fields report_path 报表路径
     */ 
    public String report_path;
    
    /** 
     * @fields remark 报表提示信息,默认的值为空
     */ 
    public String remark;

    public String node_id;

    public String superior_node_id;

    public String url;

    public String node_level;

    public String node_order = "";

    /**
     * @description 根据Map对象初始化ReportCfg信息
     * @param dmid   需求编号
     * @param dmname 需求人姓名
     * @param proc_name  过程名
     */
    public ReportCfg(String dmid, String dmname, String proc_name){
        this.dmid = dmid;
        this.dmname = dmname;
        this.proc_name = proc_name;
    }

    public void setMap(Map<String, Object> map){
        this.node_id = map.get("NODE_ID").toString();
        this.superior_node_id = map.get("SUPERIOR_NODE_ID").toString();
        this.url = map.get("NODE_URL").toString();
        this.node_level = map.get("NODE_LEVEL").toString();
        //this.node_order = map.get("NODE_ORDER").toString();
        this.report_name = map.get("NODE_NAME").toString();
    }

}
