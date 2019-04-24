package main.source.excel;
import java.io.File;  
import java.util.ArrayList;  
import java.util.Date;  
import java.util.List;  
  
import jxl.Workbook;  
import jxl.format.Alignment;  
import jxl.format.Border;  
import jxl.format.BorderLineStyle;  
import jxl.format.Colour;  
import jxl.format.PageOrientation;  
import jxl.format.PaperSize;  
import jxl.format.UnderlineStyle;  
import jxl.format.VerticalAlignment;  
import jxl.write.DateFormat;  
import jxl.write.DateTime;  
import jxl.write.Label;  
import jxl.write.Number;  
import jxl.write.NumberFormat;  
import jxl.write.WritableCellFeatures;  
import jxl.write.WritableCellFormat;  
import jxl.write.WritableFont;  
import jxl.write.WritableImage;  
import jxl.write.WritableSheet;  
import jxl.write.WritableWorkbook;  
  
public class JXLClass {  
    public static String savePath = "D:"+java.io.File.separator+"saveDir";//�ĵ�����Ŀ¼  
      
    /** 
     * ��ϰʹ��JXL����Excel�ļ� 
     */  
    public static void main(String[] args) {  
         String fileName = System.currentTimeMillis()+".xls";//�Ե�ǰʱ����ΪExcel�����ƣ�2003�汾��  
         String filePath = savePath+ java.io.File.separator + fileName;  
        System.out.println("�ļ�·����"+filePath);  
        File excelFile = new File(filePath);//�ļ����ھ���·��  
          
        createExcel(excelFile);  
  
    }  
      
    public static void createExcel(File file){  
        System.out.println("����Excel�ļ���ʼ�ˣ�");  
        try {  
            String[] title={"���","����","����","����","��������","���","ͷ��"};//��������  
              
            //1����������д���Excel������  
            WritableWorkbook workBook = Workbook.createWorkbook(file);  
              
            //2������������  
            //createSheet(param1,param2):param1Ϊ��ǰ���������ƣ�param2��ʾ�ǵڼ��ű���0��ʼ  
            WritableSheet sheet = workBook.createSheet("������1", 0);//0��ʾ��һ�ű�  
            WritableSheet sheet2 = workBook.createSheet("������2", 1);  
            WritableSheet sheet3 = workBook.createSheet("������3", 2);  
              
            //3�����ô�ӡ����  
            sheet.getSettings().setOrientation(PageOrientation.LANDSCAPE) ;// ����Ϊ�����ӡ  
            sheet.getSettings().setPaperSize(PaperSize.A4);//����ֽ��A4  
            sheet.getSettings().setFitHeight(297);//��ӡ���߶�  
            sheet.getSettings().setFitWidth(210) ;//��ӡ�����  
            // ���ñ߾�              
            sheet.getSettings().setTopMargin(0.5) ;  
            sheet.getSettings().setBottomMargin(0.3) ;  
            sheet.getSettings().setLeftMargin(0.1) ;  
            sheet.getSettings().setRightMargin(0.1) ;  
            //����ҳ��  
            sheet.getSettings().getFooter().getCentre().appendPageNumber() ;// Ϊҳ�����ҳ��  
            sheet.getSettings().setFooterMargin(0.07) ;// ����ҳ�ű߾ࣨ�£�  
             
            //4�����ñ������������� ������Cell�Ż�������  
            //���ñ��������������һأ����ɵ�ExcelΪֻ�����ڡ�����-����-��������������ģ���ȡ����ȡ��ʱҪ�������������õ�����  
            sheet.getSettings().setProtected(true) ;//���ñ���  
            sheet.getSettings().setPassword("123456") ;//���ñ�������  
            // ���ô�ӡ������  
            sheet.getSettings().setPrintHeaders(true) ;// ���ô�ӡͷ��Ϣ  
              
            //5���ϲ���Ԫ��  
            //mergeCells(int x,int y,int m,int n)���ϲ���Ԫ�񣬱�ʾ��  
            //�ӵ�x+1�У�y+1�е�m+1�У�n+1�кϲ� (�ĸ��㶨�����������꣬���ϽǺ����½�)  
            //����Ǻϲ���m-x+1�У�n-y+1�У����߳˻����Ǻϲ��ĵ�Ԫ��������  
            sheet.mergeCells(0,0,title.length-1,0);//������1�ĵ�һ�кϲ�����ʾ����  
            //setRowView(int i,int height); ָ����i�еĸ߶ȣ�����߶Ⱥ������Excel�е�20����Ҳ�����������ó�200��Excel�и߶Ȳ��Ϊ10��  
            //����������ʱ��������������ʾ��ǰ���Ƿ�����(������������Ϊtrue���߶ȼ����ٸ�Ҳ��ʾ����������Ĭ��Ϊtrue)������ֻд������������ǰ�ᱻ����  
            sheet.setRowView(0,500,false);//��һ��Ϊ�ϲ��Եı����У��ڶ��п�ʼ��ʾtitle�е����ݣ�������Ϊ�ı�����  
  
            //6�����ñ������ʽ  
            //WritableFont()��������Ϊ������(���塢�����)����С�����Σ����桢�Ӵֵȣ���  
            //�Ƿ���б��true��ʾ��б��false��ʾ����б�����»�����ʽ���������õ�˫�»��ߣ���������ɫ������Ϊ��ɫ��  
            WritableFont titleFont = new WritableFont(WritableFont.ARIAL,14,WritableFont.BOLD,  
                                true,UnderlineStyle.DOUBLE,Colour.RED);//������ʽ����  
            WritableCellFormat cellFormatForTitle = new WritableCellFormat(titleFont);//��Ԫ����ʽ����  
            cellFormatForTitle.setAlignment(jxl.format.Alignment.CENTRE); //����ˮƽ������뷽ʽ  
            cellFormatForTitle.setVerticalAlignment(VerticalAlignment.CENTRE);//���ô�ֱ������뷽ʽ  
            cellFormatForTitle.setWrap(true);//�Ƿ��Զ�����  
            cellFormatForTitle.setBorder(Border.ALL, BorderLineStyle.THIN);//���ñ߿���ʽ  
            cellFormatForTitle.setBackground(Colour.LIGHT_GREEN);//���ñ���ɫ  
            //cellFormat.setIndentation(2);//���������ַ�����  
               
            //7��Ϊ��һ�кϲ��ĵ�Ԫ�����ñ���  
            Label lab = new Label(0,0,"JXL��ϰ",cellFormatForTitle);  
            //Label(int x,int y,String content,WritableCellFormat wcf):ָ����Ԫ���λ�ú�����,�ĸ���������Ϊ;�ڼ��С��ڼ��С����ݡ���ʽ  
            sheet.addCell(lab);//����Ԫ����ӵ�sheet��  
              
            //8����titleд��Excel��  
            for(int i=0;i<title.length;i++){  
                //setColumnView(int i,int width):ָ����i�еĿ��  
                sheet.setColumnView(i, 20);//���õ�Ԫ��Ŀ��:��������Excel����Ŀ�Ȳ��  
                Label label = new Label(i,1,title[i],cellFormatForTitle);//�ڵڶ���������ı��൥Ԫ��  
                sheet.addCell(label);//��������ӵ�sheet��  
            }  
            sheet.setRowView(1,500,false);//�������и�25  
              
            //9���������ĵ���ʽ  
            //Arial��һ����ͬ����΢��Ӧ��������ַ����޳�����TrueType����  
            WritableFont bodyFont = new WritableFont(WritableFont.ARIAL,12,WritableFont.NO_BOLD,  
                    false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);//������ʽ����:ARIAL,12�ţ����Ӵ֣�����б��û���»��ߣ���ɫ  
            WritableCellFormat cellFormatForBody = new WritableCellFormat(bodyFont);//��Ԫ����ʽ����  
            cellFormatForBody.setAlignment(jxl.format.Alignment.CENTRE); //����ˮƽ������뷽ʽ  
            cellFormatForBody.setVerticalAlignment(VerticalAlignment.CENTRE);//���ô�ֱ������뷽ʽ  
            cellFormatForBody.setWrap(true);//�Ƿ��Զ�����  
            cellFormatForBody.setBorder(Border.ALL, BorderLineStyle.THIN);//���ñ߿���ʽ  
            cellFormatForBody.setBackground(Colour.LIGHT_GREEN);//���ñ���ɫ  
              
            //10���������������  
            //��һ�У���ţ��������ͣ�Number():��������Ϊ���ڼ��У��ڼ��У���ʾ���ݣ���ʽ  
            Number number = new Number(0,2,1,cellFormatForBody);  
            sheet.addCell(number);  
              
            //�ڶ��У�������������������  
            Label name = new Label(1,2,"����",cellFormatForBody);  
            sheet.addCell(name);  
              
            //�����У����䣬��������  
            Number age = new Number(2,2,10,cellFormatForBody);  
            sheet.addCell(age);  
              
            //�����У����ʣ����и�ʽ����������  
            NumberFormat nf = new NumberFormat("#.##");//��ʽ  
            WritableCellFormat wcfN = new WritableCellFormat(nf);  
            //������ʽ  
            wcfN.setAlignment(Alignment.CENTRE); //����ˮƽ������뷽ʽ  
            wcfN.setVerticalAlignment(VerticalAlignment.CENTRE);//���ô�ֱ������뷽ʽ  
            wcfN.setWrap(true);//�Ƿ��Զ�����  
            wcfN.setBorder(Border.ALL, BorderLineStyle.THIN);//���ñ߿���ʽ  
            wcfN.setBackground(Colour.LIGHT_GREEN);//���ñ���ɫ  
            Number salary = new Number(3,2,3000.1415926,wcfN);  
            sheet.addCell(salary);  
                  
            //�����У��������ڣ�ʱ������  
            DateFormat df = new DateFormat("yyyy-MM-dd hh:mm:ss");//����ʱ���ʽ  
            WritableCellFormat wcfDF = new WritableCellFormat(df);  
            //������ʽ  
            wcfDF.setAlignment(Alignment.CENTRE); //����ˮƽ������뷽ʽ  
            wcfDF.setVerticalAlignment(VerticalAlignment.CENTRE);//���ô�ֱ������뷽ʽ  
            wcfDF.setWrap(true);//�Ƿ��Զ�����  
            wcfDF.setBorder(Border.ALL, BorderLineStyle.THIN);//���ñ߿���ʽ  
            wcfDF.setBackground(Colour.LIGHT_GREEN);//���ñ���ɫ  
            DateTime birthday = new DateTime(4,2,new Date(),wcfDF);  
            //���ֵ  
            sheet.addCell(birthday);  
              
            //�����У����boolean����  
            //Boolean marry = new Boolean(5, 2, false, cellFormatForBody);  
            //sheet.addCell(marry);  
          //����ѡ�ĸ�ʽ��ֻ�ܴӸ�����������ѡ��  
            Label lblColumn  = new Label(5, 2, "��ѡ��",cellFormatForBody);//����һ����ѡ��ı�ǩ  
            WritableCellFeatures wcf2 = new WritableCellFeatures();//��ѡ�񼯺϶�������jxl�Ķ���  
            List<String> angerlist = new ArrayList<String>();  
            angerlist.add("�ѻ�");  
            angerlist.add("δ��");  
            wcf2.setDataValidationList(angerlist);//����jxl����Ҫѡ��ļ���  
            lblColumn.setCellFeatures(wcf2);//���õ���Ԫ������ȥ  
            sheet.addCell(lblColumn);//����sheet�����  
                  
            //�����У�ͷ��ͼƬ����:ֻ֧��png�ļ�  
            //WritableImage():��������Ϊ���ڼ��У��ڼ��У�ͼƬ��Ҫռ�ݼ��У�ͼƬ��Ҫռ�ݼ��У�ͼ���ļ�  
            System.out.print("ͼƬ·����"+(savePath+ java.io.File.separator+"1.png"));  
            WritableImage photo=new WritableImage(6,2,1,1,new File(savePath+ java.io.File.separator+"1.png"));  
            sheet.addImage(photo);  
              
            sheet.setRowView(2,400,false);//�����и�20  
              
            //11����Excel��д�����ݲ��ر��ļ�  
            workBook.write();  
            workBook.close();  
              
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}  
//˵����
//1�����������뱣�����ܣ��������ɵ�Excel���ܱ༭��Excel2007�����ڡ�����-����-��������������ģ��ȡ��������ȡ��ʱҪ�������������õ�����123456
//
//
//sheet.getSettings().setProtected(true) ;//���ñ���  
//sheet.getSettings().setPassword("123456") ;//���ñ�������
//2�������иߵ����⣺
//����ʹ��setRowView(int i,int height)�����������и�
//sheet.setRowView(0,500,false);  
// �����������������������Ҳ������������
//��һ��������Ҫ�����иߵ��У�
//�ڶ��������������иߵĸ߶ȡ�����߶ȴ�ŵ����ó�Excel�����иߵ�20�����ܴﵽ��Ӧ��Ч����
//������������boolean���ͣ��Ƿ����أ�Ĭ��Ϊ���ء�Ҫ�ǲ�����setRowView������ô��ǰ�в������أ�Ҫ�ǵ�������ֻд��������������ô�ᱻ���ء�д����������ʱ��trueΪ���أ�falseΪ�����ء������п��setColumnView�����������ͬ��
//3��ͼ�����͵����ݣ�
//WritableImage photo=new WritableImage(6,2,1,1,new File(savePath+ java.io.File.separator+"1.png"));  
//���ĸ������ֱ��ʾ���ڼ��У��ڼ��У�ͼƬ��Ҫռ�ݼ��У�ͼƬ��Ҫռ�ݼ��У�ͼ���ļ�����Ҫע����ǵ����͵����������������ǲ�����ͼƬ��������ֵ������ռ�ݼ�����Ԫ�����˼��
//����������������ʽ��
//Label lblColumn  = new Label(5, 2, "��ѡ��",cellFormatForBody);//����һ����ѡ��ı�ǩ  
//WritableCellFeatures wcf2 = new WritableCellFeatures();//��ѡ�񼯺϶���  
//List angerlist = new ArrayList();  
//angerlist.add("�ѻ�");  
//angerlist.add("δ��");  
//wcf2.setDataValidationList(angerlist);//����jxl����Ҫѡ��ļ���  
//lblColumn.setCellFeatures(wcf2);//���õ���Ԫ������ȥ  
//sheet.addCell(lblColumn);//����sheet�����