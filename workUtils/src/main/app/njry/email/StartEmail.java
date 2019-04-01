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
 * @description ���ɿ��ź��ʼ��ձ�����
 * @author ������
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

    //��ǰ�������±�
    private int index;
    
    //��Ҫ������±�
    private List<String> sortIndex;
    
    //����Ĵ�С
    private int sortSize = 5;
    
    //��ǰ����
    private int currentRank = 0;
    
    //�ʼ��ձ��Ƿ���Ч
    public boolean isValid;
    
    //��ȡ���ź�����
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
     * @description ��ȡ�ʼ�����
     * @author ������ 2019-01-28
     */
    public String getContext(String date, String subject) {
        init(date, subject);
        getAllTitle();
        generateContext();
        return context;
    }

    /**
     * @param subject ����
     * @param context ����
     * @param attachNames �����б�
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
     * @description ��ȡ���е����б���
     * @author ������ 2019-01-28
     */ 
    private void getAllTitle() {
        //��ȡ��̴���ͳ����Ŀ
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
        //����ǰ��������
        tdContext.append(generateTdTitle(contextMap));
        //���ɺ��������
        tdContext.append(generateTdContext(contextMap));
        return tdContext.toString();
    }

    //���ɴ������Ŀ���
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
     * @description ������һ�仰�����������������
     * @author ������ 2019-01-28
     * @param contextMap
     * @return
     */ 
    private String generateTdContext(Map<String, Object> contextMap) {
        StringBuffer tdContext = new StringBuffer();
        //�����������,�ͻ���
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
                //����%,ͳһ�滻Ϊ""
                sort.add(Double.parseDouble(columnContext.replace("%", "")));
            }
        }
        Collections.sort(sort);
        tdContext.append(generateTdContextWithColor(contextMap, sort));
        return tdContext.toString();
    }

    /** 
     * @description ���ɴ�����ɫ�ĵ�td
     * @author ������ 2019-01-28
     * @param contextMap
     * @param sort
     * @return
     */ 
    private String generateTdContextWithColor(Map<String, Object> contextMap, List<Double> sort) {
        StringBuffer tdContext = new StringBuffer();
        Set<Double> start = new HashSet<Double>();
        Set<Double> end = new HashSet<Double>();
        //ȡǰ5,����һ����û��5,����Ӹ��ж�
        int _sortSize = sort.size() - sortSize >= 0 ? sortSize : sort.size();
        start.addAll(sort.subList(sort.size() - _sortSize, sort.size()));
        end.addAll(sort.subList(0, _sortSize));
        for(String column : columns){
            String columnContext = contextMap.get(column).toString();
            if(isContains(start, columnContext)){
              //ǰ����
                tdContext.append("<td style=\"background-color:#99CC00\">"+ (currentRank + 1) + "("+columnContext+ ")" +"</td>"+ "\n");
            }else if(isContains(end, columnContext)){
                //����
                tdContext.append("<td style=\"background-color:#FF8080\">"+ (sort.size() - sortSize + currentRank + 1) + "("+columnContext+ ")" +"</td>"+ "\n");
            }else{
                //û������
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
     * @description ������һ�仰�����������������
     * @author ������ 2019-01-28
     */ 
    private String addTrContext(String td) {
        return "<tr style=\"font-size: 8pt;height:30px;\">\n" + td + "</tr>";
    }

    private String addTableHead(String tableBody){
        return "<br/><div style='font-family:����ϸ��; font-size: 11pt; color: #0000FF;'>�Ͼ��ֹ�˾�ص㹤���ձ�(" + "20190124" + ")</div><br/>"
        + "<table cellspacing='0' cellpadding='0' border= '1' style='width:90%; border-collapse:collapse; text-align: center;'>" 
        + "    <tr style='font-size: 9pt; font-weight: bold; background-color:#CCFFFF;height:35px;'>" 
        + "        <td style='width:7%;'>��̴���</td><td style='width:13%;'>�����Ŀ</td><td>����</td><td>ȫ��</td><td>����</td><td>�껨</td>"
        + "        <td>�Ľ�</td><td>��ϼ</td><td>����</td><td>�ֿ�</td><td>����</td><td>��ˮ</td><td>�ߴ�</td><td>������</td><td>ʡ����</td>"
        + "    </tr>" + tableBody
        + "</table>";
    }
    
    
    
}