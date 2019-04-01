package main.source.excel;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * POI�¼�������ȡExcel�ļ��ĳ����ࡣ
 * 
 * @author elon
 * @version 2018��7��7��
 */
public abstract class ExcelAbstract extends DefaultHandler {
    private SharedStringsTable sst;
    private String lastContents;
    private boolean nextIsString;

    private int curRow = 0;
    private String curCellName = "";

    /**
     * ��ȡ��ǰ�е����ݡ�key�ǵ�Ԫ��������A1,value�ǵ�Ԫ���е�ֵ�������Ԫ��ʽ��,��û�����ݡ�
     */
    private Map<String, String> rowValueMap = new HashMap<String, String>();

    /**
     * ���������ݵĻص�������
     * 
     * @param curRow ��ǰ�к�
     * @param rowValueMap ��ǰ�е�ֵ
     * @throws SQLException
     */
    public abstract void optRows(int curRow, Map<String, String> rowValueMap);

    /**
     * ��ȡExcelָ��sheetҳ�����ݡ�
     * 
     * @param filePath �ļ�·��
     * @param sheetNum sheetҳ���.��1��ʼ��
     * @throws Exception
     */
    public void readOneSheet(String filePath, int sheetNum) throws Exception {
        OPCPackage pkg = OPCPackage.open(filePath);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();

        XMLReader parser = getSheetParser(sst);

        // ���� rId# �� rSheet# ����sheet
        InputStream sheet2 = r.getSheet("rId" + sheetNum);
        InputSource sheetSource = new InputSource(sheet2);
        parser.parse(sheetSource);
        sheet2.close();
        pkg.close();
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        // c => ��Ԫ��
        if (name.equals("c")) {
            
            // �����һ��Ԫ���� SST ����������nextIsString���Ϊtrue
            String cellType = attributes.getValue("t");
            if (cellType != null && cellType.equals("s")) {
                nextIsString = true;
            } else {
                nextIsString = false;
            }
        }
        
        // �ÿ�
        lastContents = "";
        
        /**
         * ��¼��ǰ��ȡ��Ԫ�������
         */
        String cellName = attributes.getValue("r");
        if (cellName != null && !cellName.isEmpty()) {
            curCellName = cellName;
        }
    }

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {
        // ����SST������ֵ�ĵ���Ԫ�������Ҫ�洢���ַ���
        // ��ʱcharacters()�������ܻᱻ���ö��
        if (nextIsString) {
            try {
                int idx = Integer.parseInt(lastContents);
                lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
            } catch (Exception e) {

            }
        }

        // v => ��Ԫ���ֵ�������Ԫ�����ַ�����v��ǩ��ֵΪ���ַ�����SST�е�����
        // ����Ԫ�����ݼ���rowlist�У�����֮ǰ��ȥ���ַ���ǰ��Ŀհ׷�
        if (name.equals("v")) {
            String value = lastContents.trim();
            value = value.equals("") ? " " : value;
            rowValueMap.put(curCellName, value);
        } else {
            // �����ǩ����Ϊ row ����˵���ѵ���β������ optRows() ����
            if (name.equals("row")) {
                optRows(curRow, rowValueMap);
                rowValueMap.clear();
                curRow++;
            }
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        // �õ���Ԫ�����ݵ�ֵ
        lastContents += new String(ch, start, length);
    }
    
    /**
     * ��ȡ����sheetҳ��xml��������
     * @param sst
     * @return
     * @throws SAXException
     */
    private XMLReader getSheetParser(SharedStringsTable sst) throws SAXException {
        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
        this.sst = sst;
        parser.setContentHandler(this);
        return parser;
    }
}