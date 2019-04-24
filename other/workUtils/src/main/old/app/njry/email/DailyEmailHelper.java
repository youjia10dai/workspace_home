/*
 * Copyright (c) 2019  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.old.app.njry.email;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import main.helper.BaseHelper;
import main.helper.email.EmailHelper;
import main.spring.database.aspect.DataSource;
import main.spring.database.multDataSource.DBContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/** 
 * @description 邮件日报工具类  未封装
 * @author 陈吕奖
 * @date 2019-01-26 
 */
@Component("dailyEmailHelper")
@DataSource(name = DBContextHolder.zw6)//在账务6进行操作
public class DailyEmailHelper extends BaseHelper{

    @Autowired
    private EmailHelper emailHelper;
    
    /** 
     * @description 发送指定日期的邮件日报
     * @author 陈吕奖 2019-01-28
     * @param date   格式20190124
     */
    public void send(String date){
        //获取邮件日报内容
        String sql = "select a.kpi_type_name, a.kpi_name, a.big_order_id, a.small_order_id, b.zhibiao_name, b.order_id," +
        "                    nvl(c.C001, ' ') C001, nvl(c.C002, ' ') C002, nvl(c.C003, ' ') C003, nvl(c.C004, ' ') C004, nvl(c.C005, ' ') C005, nvl(c.C006, ' ') C006, " +
        "                    nvl(c.C007, ' ') C007, nvl(c.C008, ' ') C008, nvl(c.C009, ' ') C009, nvl(c.C010, ' ') C010," +
        "                    nvl(c.C011, ' ') C011, nvl(c.C012, ' ') C012" +
        "               from t_crm_kmh2019_ztpj_cfg a, t_crm_kmh2019_ztpj_cfg2 b, t_crm_kmh2019_ztpj_content c"+
        "              where a.kpi_type_id = b.kpi_type_id"+ 
        "                and a.kpi_id = b.kpi_id"+
        "                and a.kpi_type_id = c.kpi_type_id"+
        "                and a.kpi_id = c.kpi_id"+
        "                and b.zhibiao_id = c.zhibiao_id"+
        "                and c.day_id = " + date + 
        "               order by big_order_id, small_order_id, order_id";
        List<Map<String, Object>> list = db.queryForList(sql);
        log.debug(sql);
        
        sql = "select kpi_type_name name, count(1) cnt" +
        "        from t_crm_kmh2019_ztpj_cfg a, t_crm_kmh2019_ztpj_cfg2 b, t_crm_kmh2019_ztpj_content c"+
        "       where a.kpi_type_id = b.kpi_type_id"+ 
        "         and a.kpi_id = b.kpi_id"+
        "         and a.kpi_type_id = c.kpi_type_id"+
        "         and a.kpi_id = c.kpi_id"+
        "         and b.zhibiao_id = c.zhibiao_id"+
        "         and c.day_id = " + date;
        sql = sql + " group by kpi_type_name union all " + sql.replace("kpi_type_name", "kpi_name||''''") + "group by kpi_name";
        List<Map<String, Object>> list1 = db.queryForList(sql);
        //大类和项目只能出现一次
        List<String> title = new ArrayList<String>();
        log.debug(list1.size());
        String htmlContextString = "";
        for(Map<String, Object> contextMap : list){
            htmlContextString +="<tr style=\"font-size: 8pt;height:30px;\"> " + "\n";
            //是否包含大类
            if(!title.contains(contextMap.get("kpi_type_name"))){
                //获取大类需要占的行数
                title.add(contextMap.get("kpi_type_name").toString());
                int rows = getRows(list1, contextMap.get("kpi_type_name").toString());
                if(rows > 0){
                    htmlContextString +="<td rowspan=\""+ rows +"\">" + contextMap.get("kpi_type_name").toString() + "</td>" + "\n";
                }
            }
            //是否包含项目
            if(!title.contains(contextMap.get("kpi_name")+ "'")){
                //获取大类需要占的行数
                title.add(contextMap.get("kpi_name") + "'");
                int rows = getRows(list1, contextMap.get("kpi_name")+ "'");
                if(rows > 0){
                    htmlContextString +="<td rowspan=\""+ rows +"\"> "+ contextMap.get("kpi_name").toString() +" </td>"+ "\n";
                }
            }
            //正常输出周期
            htmlContextString += "<td>"+ contextMap.get("zhibiao_name") +"</td>"+ "\n";
            htmlContextString += "<td>"+ contextMap.get("C001") +"</td>"+ "\n";
            
            if(true){
                //进行排序
                List<Double> sort = new ArrayList<Double>();
                if(!"".equals(contextMap.get("C002").toString().trim())){
                    sort.add(Double.parseDouble(contextMap.get("C002").toString().replace("%", "")));
                }
                if(!"".equals(contextMap.get("C003").toString().trim())){
                    sort.add(Double.parseDouble(contextMap.get("C003").toString().replace("%", "")));
                }
                if(!"".equals(contextMap.get("C004").toString().trim())){
                    sort.add(Double.parseDouble(contextMap.get("C004").toString().replace("%", "")));
                }
                if(!"".equals(contextMap.get("C005").toString().trim())){
                    sort.add(Double.parseDouble(contextMap.get("C005").toString().replace("%", "")));
                }
                if(!"".equals(contextMap.get("C006").toString().trim())){
                    sort.add(Double.parseDouble(contextMap.get("C006").toString().replace("%", "")));
                }
                if(!"".equals(contextMap.get("C007").toString().trim())){
                    sort.add(Double.parseDouble(contextMap.get("C007").toString().replace("%", "")));
                }
                if(!"".equals(contextMap.get("C008").toString().trim())){
                    sort.add(Double.parseDouble(contextMap.get("C008").toString().replace("%", "")));
                }
                if(!"".equals(contextMap.get("C009").toString().trim())){
                    sort.add(Double.parseDouble(contextMap.get("C009").toString().replace("%", "")));
                }
                if(!"".equals(contextMap.get("C010").toString().trim())){
                    sort.add(Double.parseDouble(contextMap.get("C010").toString().replace("%", "")));
                }
                if(!"".equals(contextMap.get("C011").toString().trim())){
                    sort.add(Double.parseDouble(contextMap.get("C011").toString().replace("%", "")));
                }
                if(!"".equals(contextMap.get("C012").toString().trim())){
                    sort.add(Double.parseDouble(contextMap.get("C012").toString().replace("%", "")));
                }
                Collections.sort(sort);
                System.out.println(sort);
                
                //以下字段需要进行排序
                if((contextMap.get("C002")+".0").contains(sort.get(sort.size() - 1)+ "")){
                    htmlContextString += "<td style=\"background-color:#99CC00\">"+ contextMap.get("C002") +"</td>"+ "\n";
                }else{
                    htmlContextString += "<td>"+ contextMap.get("C002") +"</td>"+ "\n";
                }
                if((contextMap.get("C003")+".0").contains(sort.get(sort.size() - 1)+ "")){
                    htmlContextString += "<td style=\"background-color:#99CC00\">"+ contextMap.get("C003") +"</td>"+ "\n";
                }else{
                    htmlContextString += "<td>"+ contextMap.get("C003") +"</td>"+ "\n";
                }
                if((contextMap.get("C004")+".0").contains(sort.get(sort.size() - 1)+ "")){
                    htmlContextString += "<td style=\"background-color:#99CC00\">"+ contextMap.get("C004") +"</td>"+ "\n";
                }else{
                    htmlContextString += "<td>"+ contextMap.get("C004") +"</td>"+ "\n";
                }
                if((contextMap.get("C005")+".0").contains(sort.get(sort.size() - 1)+ "")){
                    htmlContextString += "<td style=\"background-color:#99CC00\">"+ contextMap.get("C005") +"</td>"+ "\n";
                }else{
                    htmlContextString += "<td>"+ contextMap.get("C005") +"</td>"+ "\n";
                }
                if((contextMap.get("C006")+".0").contains(sort.get(sort.size() - 1)+ "")){
                    htmlContextString += "<td style=\"background-color:#99CC00\">"+ contextMap.get("C006") +"</td>"+ "\n";
                }else{
                    htmlContextString += "<td>"+ contextMap.get("C006") +"</td>"+ "\n";
                }
                if((contextMap.get("C007")+".0").contains(sort.get(sort.size() - 1)+ "")){
                    htmlContextString += "<td style=\"background-color:#99CC00\">"+ contextMap.get("C007") +"</td>"+ "\n";
                }else{
                    htmlContextString += "<td>"+ contextMap.get("C007") +"</td>"+ "\n";
                }
                if((contextMap.get("C008")+".0").contains(sort.get(sort.size() - 1)+ "")){
                    htmlContextString += "<td style=\"background-color:#99CC00\">"+ contextMap.get("C008") +"</td>"+ "\n";
                }else{
                    htmlContextString += "<td>"+ contextMap.get("C008") +"</td>"+ "\n";
                }
                if((contextMap.get("C009")+".0").contains(sort.get(sort.size() - 1)+ "")){
                    htmlContextString += "<td style=\"background-color:#99CC00\">"+ contextMap.get("C009") +"</td>"+ "\n";
                }else{
                    htmlContextString += "<td>"+ contextMap.get("C009") +"</td>"+ "\n";
                }
                if((contextMap.get("C010")+".0").contains(sort.get(sort.size() - 1)+ "")){
                    htmlContextString += "<td style=\"background-color:#99CC00\">"+ contextMap.get("C010") +"</td>"+ "\n";
                }else{
                    htmlContextString += "<td>"+ contextMap.get("C010") +"</td>"+ "\n";
                }
                if((contextMap.get("C011")+".0").contains(sort.get(sort.size() - 1)+ "")){
                    htmlContextString += "<td style=\"background-color:#99CC00\">"+ contextMap.get("C011") +"</td>"+ "\n";
                }else{
                    htmlContextString += "<td>"+ contextMap.get("C011") +"</td>"+ "\n";
                }
                if((contextMap.get("C012")+".0").contains(sort.get(sort.size() - 1)+ "")){
                    htmlContextString += "<td style=\"background-color:#99CC00\">"+ contextMap.get("C012") +"</td>"+ "\n";
                }else{
                    htmlContextString += "<td>"+ contextMap.get("C012") +"</td>"+ "\n";
                }
            }else{
                //以下字段需要进行排序
                htmlContextString += "<td>"+ contextMap.get("C002") +"</td>"+ "\n";
                htmlContextString += "<td>"+ contextMap.get("C003") +"</td>"+ "\n";
                htmlContextString += "<td>"+ contextMap.get("C004") +"</td>"+ "\n";
                htmlContextString += "<td>"+ contextMap.get("C005") +"</td>"+ "\n";
                htmlContextString += "<td>"+ contextMap.get("C006") +"</td>"+ "\n";
                htmlContextString += "<td>"+ contextMap.get("C007") +"</td>"+ "\n";
                htmlContextString += "<td>"+ contextMap.get("C008") +"</td>"+ "\n";
                htmlContextString += "<td>"+ contextMap.get("C009") +"</td>"+ "\n";
                htmlContextString += "<td>"+ contextMap.get("C010") +"</td>"+ "\n";
                htmlContextString += "<td>"+ contextMap.get("C011") +"</td>"+ "\n";
                htmlContextString += "<td>"+ contextMap.get("C012") +"</td>"+ "\n";
            }
            htmlContextString += "</tr>";
        }
        String string = "<br/><div style='font-family:华文细黑; font-size: 11pt; color: #0000FF;'>南京分公司重点工作日报(" + "20190124" + ")</div><br/>"
        + "<table cellspacing='0' cellpadding='0' border= '1' style='width:90%; border-collapse:collapse; text-align: center;'>" 
        + "    <tr style='font-size: 9pt; font-weight: bold; background-color:#CCFFFF;height:35px;'>" 
        + "        <td style='width:7%;'>冲刺大类</td><td style='width:13%;'>冲刺项目</td><td>周期</td><td>全区</td><td>玄秦</td><td>雨花</td>"
        + "        <td>鼓建</td><td>栖霞</td><td>江宁</td><td>浦口</td><td>六合</td><td>溧水</td><td>高淳</td><td>市政企</td><td>省政企</td>"
        + "    </tr>" + htmlContextString
        + "</table>";
        System.out.println(string);
    }
    
    private int getRows(List<Map<String, Object>> list1, String name){
        for(Map<String, Object> map : list1) {
            String _name = map.get("name").toString();
            if(name.equalsIgnoreCase(_name)){
                return Integer.parseInt(map.get("cnt").toString());
            }
        }
        return 0;
    }
}
