/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.app.njry.treemenu;

import java.util.Map;

/** 
 * @description ʵ����������������
 * @author ������
 * @date 2018-10-16 
 */
public class ReportCfg {
    
    /** 
     * @fields report_name ����ڵ�����
     */ 
    public String report_name;
    
    /** 
     * @fields report_type ��Ĭ��ֵ
     * 1���ձ�    2���±�   3����
     */ 
    public String report_type = "1";

    /** 
     * @fields dmid ������
     */ 
    public String dmid;

    /** 
     * @fields dmname ����������
     */ 
    public String dmname;

    /** 
     * @fields proc_name ������
     */ 
    public String proc_name;

    /** 
     * @fields proc_author ��������,Ĭ���Լ�
     */ 
    public String proc_author = "������";

    public String database = "njzw3";

    public String db_username = "jtb";

    /** 
     * @fields report_path ����·��
     */ 
    public String report_path;
    
    /** 
     * @fields remark ������ʾ��Ϣ,Ĭ�ϵ�ֵΪ��
     */ 
    public String remark;

    public String node_id;

    public String superior_node_id;

    public String url;

    public String node_level;

    public String node_order = "";

    /**
     * @description ����Map�����ʼ��ReportCfg��Ϣ
     * @param dmid   ������
     * @param dmname ����������
     * @param proc_name  ������
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
