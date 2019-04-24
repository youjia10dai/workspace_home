package main.app.njry.email;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import main.helper.BaseHelper;
import main.spring.database.aspect.DataSource;
import main.spring.database.multDataSource.DBContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** 
 * @description 生成开门红邮件日报内容
 * @author 陈吕奖
 * @date 2019-01-28 
 */
@Component("startEmail")
@DataSource(name = DBContextHolder.zw6)
public class StartEmail extends BaseHelper{

    private String date;
    
    private String subject;
    
    private String context;
    
    @Autowired
    private StarEmailExcelAttach attach;
    
    private Map<String, String> titles;

    private List<String> inValidTitles = new ArrayList<String>();
    
    private String[] columns = new String[]{"C002", "C003", "C004", "C005", "C006", "C007", "C008", "C009", "C010", "C011", "C012"};

    //当前遍历的下标
    private int index;
    
    //需要排序的下标
    private List<String> sortIndex;
    
    //排序的大小
    private int sortSize = 5;
    
    //当前排名
    private int currentRank = 0;
    
    //邮件日报是否有效
    public boolean isValid;
    
    //获取开门红数据
    private String originalContextSql = "select a.kpi_type_name, a.kpi_name, a.big_order_id, a.small_order_id, b.zhibiao_name, b.order_id," +
    "                    nvl(c.C001, ' ') C001, nvl(c.C002, ' ') C002, nvl(c.C003, ' ') C003, nvl(c.C004, ' ') C004, nvl(c.C005, ' ') C005, nvl(c.C006, ' ') C006, " +
    "                    nvl(c.C007, ' ') C007, nvl(c.C008, ' ') C008, nvl(c.C009, ' ') C009, nvl(c.C010, ' ') C010," +
    "                    nvl(c.C011, ' ') C011, nvl(c.C012, ' ') C012" +
    "               from t_crm_kmh2019_ztpj_cfg a, t_crm_kmh2019_ztpj_cfg2 b, t_crm_kmh2019_ztpj_content c"+
    "              where a.kpi_type_id = b.kpi_type_id"+ 
    "                and a.kpi_id = b.kpi_id"+
    "                and a.kpi_type_id = c.kpi_type_id"+
    "                and a.kpi_id = c.kpi_id"+
    "                and b.zhibiao_id = c.zhibiao_id"+
    "                and c.day_id = ?"  + 
    "               order by big_order_id, small_order_id, order_id";
    
    private String titleSql = "select kpi_type_name name, count(1) cnt" +
    "        from t_crm_kmh2019_ztpj_cfg a, t_crm_kmh2019_ztpj_cfg2 b, t_crm_kmh2019_ztpj_content c"+
    "       where a.kpi_type_id = b.kpi_type_id"+ 
    "         and a.kpi_id = b.kpi_id"+
    "         and a.kpi_type_id = c.kpi_type_id"+
    "         and a.kpi_id = c.kpi_id"+
    "         and b.zhibiao_id = c.zhibiao_id"+
    "         and c.day_id = ?";
    //sql = sql + " group by kpi_type_name union all " + sql.replace("kpi_type_name", "kpi_name||''''") + "group by kpi_name";
    
    public StartEmail() {
    }

    /** 
     * @description 获取邮件内容
     * @author 陈吕奖 2019-01-28
     */
    public String getContext(String date, String subject) {
        init(date, subject);
        getAllTitle();
        generateContext();
        return context;
    }

    /**
     * @param subject 主题
     * @param context 内容
     * @param attachNames 附件列表
     */
    private void init(String date, String subject){
        try {
            this.date = date;
            this.subject = subject;
        }catch (EmailException e){
            e.printStackTrace();
            isValid = true;
        }
    }
    
    /** 
     * @description 获取竖列的所有标题
     * @author 陈吕奖 2019-01-28
     */ 
    private void getAllTitle() {
        //获取冲刺大类和冲刺项目
        titleSql = titleSql + " group by kpi_type_name union all " + titleSql.replace("kpi_type_name", "kpi_name||''''") + "group by kpi_name";
        titles = new HashMap<String, String>();
        List<Map<String, Object>> _titles = db.queryForList(titleSql, date, date);
        for(Map<String, Object> map : _titles) {
            String name = map.get("name").toString();
            titles.put(name, map.get("cnt").toString());
        }
    }
    
    private void generateContext(){
        initSortCloums();
        String tableBody = generateTableBody();
        context = addTableHead(tableBody);
    }
    
    private void initSortCloums() {
        String[] sortCloums = new String[]{"4", "7", "13", "18", "19", "24", "25", "30", "31", "36",
                                           "37", "43", "46", "49", "52", "55", "58", "61", "64", "67"};
        sortIndex = Arrays.asList(sortCloums);
    }

    private String generateTableBody() {
        List<Map<String, Object>> originalContext = db.queryForList(originalContextSql, date);
        StringBuffer tableBody = new StringBuffer();
        int length = originalContext.size();
        Map<String, Object> contextMap;
        for(int i = 0; i < length; i++){
            contextMap = originalContext.get(i);
            String tdContext = getTdContexts(contextMap);
            String trContext = addTrContext(tdContext);
            tableBody.append(trContext);
            index++;
        }
        return tableBody.toString();
    }

    private String getTdContexts(Map<String, Object> contextMap){
        StringBuffer tdContext = new StringBuffer();
        //生成前两个标题
        tdContext.append(generateTdTitle(contextMap));
        //生成后面的内容
        tdContext.append(generateTdContext(contextMap));
        return tdContext.toString();
    }

    //生成大类和项目冲刺
    private String generateTdTitle(Map<String, Object> contextMap){
        StringBuffer titleContext = new StringBuffer();
        String titleName = contextMap.get("kpi_type_name").toString();
        String projectName = contextMap.get("kpi_name").toString();
        if(!isExistTitleName(titleName)){
            int rows = Integer.parseInt(titles.get(titleName));
            titleContext.append("<td rowspan=\""+ rows +"\">" + titleName + "</td>" + "\n");
        }
        if(!isExistTitleName(projectName + "'")){
            int rows = Integer.parseInt(titles.get(projectName + "'"));
            titleContext.append("<td rowspan=\""+ rows +"\">" + projectName + "</td>" + "\n");
        }
        return titleContext.toString();
    }
    
    private boolean isExistTitleName(String titleName){
        boolean isExist = inValidTitles.contains(titleName);
        if(!isExist){
            inValidTitles.add(titleName);
        }
        return isExist;
    }
    
    /** 
     * @description 这里用一句话描述这个方法的作用
     * @author 陈吕奖 2019-01-28
     * @param contextMap
     * @return
     */ 
    private String generateTdContext(Map<String, Object> contextMap) {
        StringBuffer tdContext = new StringBuffer();
        //正常输出周期,和汇总
        tdContext.append("<td>"+ contextMap.get("zhibiao_name") +"</td>"+ "\n");
        tdContext.append("<td>"+ contextMap.get("C001") +"</td>"+ "\n");
        if(sortIndex.contains(index+"")){
            tdContext.append(generateTdContextWithSort(contextMap));
        }else{
            tdContext.append(generateTdContextWithNotSort(contextMap));
        }
        return tdContext.toString();
    }
    
    private String generateTdContextWithSort(Map<String, Object> contextMap) {
        StringBuffer tdContext = new StringBuffer();
        List<Double> sort = new ArrayList<Double>();
        for(String column : columns){
            String columnContext = contextMap.get(column).toString().trim();
            if(!"".equals(columnContext)){
                //存在%,统一替换为""
                sort.add(Double.parseDouble(columnContext.replace("%", "")));
            }
        }
        Collections.sort(sort);
        tdContext.append(generateTdContextWithColor(contextMap, sort));
        return tdContext.toString();
    }

    /** 
     * @description 生成带有颜色的的td
     * @author 陈吕奖 2019-01-28
     * @param contextMap
     * @param sort
     * @return
     */ 
    private String generateTdContextWithColor(Map<String, Object> contextMap, List<Double> sort) {
        StringBuffer tdContext = new StringBuffer();
        Set<Double> start = new HashSet<Double>();
        Set<Double> end = new HashSet<Double>();
        //取前5,可能一共都没有5,这里加个判断
        int _sortSize = sort.size() - sortSize >= 0 ? sortSize : sort.size();
        start.addAll(sort.subList(sort.size() - _sortSize, sort.size()));
        end.addAll(sort.subList(0, _sortSize));
        for(String column : columns){
            String columnContext = contextMap.get(column).toString();
            if(isContains(start, columnContext)){
              //前几名
                tdContext.append("<td style=\"background-color:#99CC00\">"+ (currentRank + 1) + "("+columnContext+ ")" +"</td>"+ "\n");
            }else if(isContains(end, columnContext)){
                //后几名
                tdContext.append("<td style=\"background-color:#FF8080\">"+ (sort.size() - sortSize + currentRank + 1) + "("+columnContext+ ")" +"</td>"+ "\n");
            }else{
                //没有排名
                tdContext.append("<td>"+ columnContext +"</td>"+ "\n");
            }
        }
        return tdContext.toString();
    }

    private boolean isContains(Set<Double> target, String source){
        if("".equals(source.trim())){
            return false;
        }
        List<Double> _target = new ArrayList<Double>();
        double sourceContext = Double.parseDouble(source.replace("%", ""));
        double targetContext;
        _target.addAll(target);
        Collections.sort(_target);
        Collections.reverse(_target);
        log.debug(_target);
        for(int i = 0; i < target.size(); i++) {
            targetContext = _target.get(i);
            if(sourceContext == targetContext){
                currentRank = i;
                return true;
            }
        }
        return false;
    }
    
    private String generateTdContextWithNotSort(Map<String, Object> contextMap) {
        StringBuffer tdContext = new StringBuffer();
        for(String column : columns){
            tdContext.append("<td>"+ contextMap.get(column) +"</td>"+ "\n");
        }
        return tdContext.toString();
    }

    /** 
     * @description 这里用一句话描述这个方法的作用
     * @author 陈吕奖 2019-01-28
     */ 
    private String addTrContext(String td) {
        return "<tr style=\"font-size: 8pt;height:30px;\">\n" + td + "</tr>";
    }

    private String addTableHead(String tableBody){
        return "<br/><div style='font-family:华文细黑; font-size: 11pt; color: #0000FF;'>南京分公司重点工作日报(" + "20190124" + ")</div><br/>"
        + "<table cellspacing='0' cellpadding='0' border= '1' style='width:90%; border-collapse:collapse; text-align: center;'>" 
        + "    <tr style='font-size: 9pt; font-weight: bold; background-color:#CCFFFF;height:35px;'>" 
        + "        <td style='width:7%;'>冲刺大类</td><td style='width:13%;'>冲刺项目</td><td>周期</td><td>全区</td><td>玄秦</td><td>雨花</td>"
        + "        <td>鼓建</td><td>栖霞</td><td>江宁</td><td>浦口</td><td>六合</td><td>溧水</td><td>高淳</td><td>市政企</td><td>省政企</td>"
        + "    </tr>" + tableBody
        + "</table>";
    }
    
    
    
}