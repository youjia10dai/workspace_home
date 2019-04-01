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
    public static String savePath = "D:"+java.io.File.separator+"saveDir";//文档所在目录  
      
    /** 
     * 练习使用JXL生成Excel文件 
     */  
    public static void main(String[] args) {  
         String fileName = System.currentTimeMillis()+".xls";//以当前时间作为Excel的名称：2003版本的  
         String filePath = savePath+ java.io.File.separator + fileName;  
        System.out.println("文件路径："+filePath);  
        File excelFile = new File(filePath);//文件所在具体路径  
          
        createExcel(excelFile);  
  
    }  
      
    public static void createExcel(File file){  
        System.out.println("生成Excel文件开始了！");  
        try {  
            String[] title={"序号","姓名","年龄","工资","出生日期","婚否","头像"};//标题数组  
              
            //1、创建可以写入的Excel工作簿  
            WritableWorkbook workBook = Workbook.createWorkbook(file);  
              
            //2、创建工作表  
            //createSheet(param1,param2):param1为当前工作表名称，param2表示是第几张表，从0开始  
            WritableSheet sheet = workBook.createSheet("工作表1", 0);//0表示第一张表  
            WritableSheet sheet2 = workBook.createSheet("工作表2", 1);  
            WritableSheet sheet3 = workBook.createSheet("工作表3", 2);  
              
            //3、设置打印属性  
            sheet.getSettings().setOrientation(PageOrientation.LANDSCAPE) ;// 设置为横向打印  
            sheet.getSettings().setPaperSize(PaperSize.A4);//设置纸张A4  
            sheet.getSettings().setFitHeight(297);//打印区高度  
            sheet.getSettings().setFitWidth(210) ;//打印区宽度  
            // 设置边距              
            sheet.getSettings().setTopMargin(0.5) ;  
            sheet.getSettings().setBottomMargin(0.3) ;  
            sheet.getSettings().setLeftMargin(0.1) ;  
            sheet.getSettings().setRightMargin(0.1) ;  
            //设置页脚  
            sheet.getSettings().getFooter().getCentre().appendPageNumber() ;// 为页脚添加页数  
            sheet.getSettings().setFooterMargin(0.07) ;// 设置页脚边距（下）  
             
            //4、设置保护，并加密码 锁定的Cell才会起作用  
            //启用保护并设置密码找回，生成的Excel为只读，在”审阅-更改-撤销工作表保护“模块可取消，取消时要求输入下面设置的密码  
            sheet.getSettings().setProtected(true) ;//启用保护  
            sheet.getSettings().setPassword("123456") ;//设置保护密码  
            // 设置打印标题行  
            sheet.getSettings().setPrintHeaders(true) ;// 启用打印头信息  
              
            //5、合并单元格  
            //mergeCells(int x,int y,int m,int n)：合并单元格，表示将  
            //从第x+1列，y+1行到m+1列，n+1行合并 (四个点定义了两个坐标，左上角和右下角)  
            //结果是合并了m-x+1行，n-y+1列，两者乘积就是合并的单元格数量。  
            sheet.mergeCells(0,0,title.length-1,0);//工作表1的第一行合并，显示标题  
            //setRowView(int i,int height); 指定第i行的高度（这个高度好像得是Excel中的20倍，也就是这里设置成200，Excel中高度差不多为10）  
            //有三个参数时，第三个参数表示当前行是否隐藏(当第三个参数为true，高度即便再高也显示不出来。且默认为true)。若是只写两个参数，当前会被隐藏  
            sheet.setRowView(0,500,false);//第一行为合并性的标题行，第二行开始显示title中的内容，第三行为文本内容  
  
            //6、设置标题的样式  
            //WritableFont()参数依次为：字体(宋体、楷体等)，大小，字形（常规、加粗等），  
            //是否倾斜（true表示倾斜，false表示不倾斜），下划线样式（这里设置的双下划线），字体颜色（这里为红色）  
            WritableFont titleFont = new WritableFont(WritableFont.ARIAL,14,WritableFont.BOLD,  
                                true,UnderlineStyle.DOUBLE,Colour.RED);//字体样式定义  
            WritableCellFormat cellFormatForTitle = new WritableCellFormat(titleFont);//单元格样式定义  
            cellFormatForTitle.setAlignment(jxl.format.Alignment.CENTRE); //设置水平方向对齐方式  
            cellFormatForTitle.setVerticalAlignment(VerticalAlignment.CENTRE);//设置垂直方向对齐方式  
            cellFormatForTitle.setWrap(true);//是否自动换行  
            cellFormatForTitle.setBorder(Border.ALL, BorderLineStyle.THIN);//设置边框样式  
            cellFormatForTitle.setBackground(Colour.LIGHT_GREEN);//设置背景色  
            //cellFormat.setIndentation(2);//设置缩进字符个数  
               
            //7、为第一行合并的单元格设置标题  
            Label lab = new Label(0,0,"JXL练习",cellFormatForTitle);  
            //Label(int x,int y,String content,WritableCellFormat wcf):指明单元格的位置和内容,四个参数依次为;第几列、第几行、内容、样式  
            sheet.addCell(lab);//将单元格添加到sheet中  
              
            //8、将title写到Excel中  
            for(int i=0;i<title.length;i++){  
                //setColumnView(int i,int width):指定第i列的宽度  
                sheet.setColumnView(i, 20);//设置单元格的宽度:这个宽度与Excel里面的宽度差不多  
                Label label = new Label(i,1,title[i],cellFormatForTitle);//在第二行中添加文本类单元格  
                sheet.addCell(label);//将标题添加到sheet中  
            }  
            sheet.setRowView(1,500,false);//标题行行高25  
              
            //9、设置正文的样式  
            //Arial是一套随同多套微软应用软件所分发的无衬线体TrueType字型  
            WritableFont bodyFont = new WritableFont(WritableFont.ARIAL,12,WritableFont.NO_BOLD,  
                    false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);//字体样式定义:ARIAL,12号，不加粗，不倾斜，没有下划线，黑色  
            WritableCellFormat cellFormatForBody = new WritableCellFormat(bodyFont);//单元格样式定义  
            cellFormatForBody.setAlignment(jxl.format.Alignment.CENTRE); //设置水平方向对齐方式  
            cellFormatForBody.setVerticalAlignment(VerticalAlignment.CENTRE);//设置垂直方向对齐方式  
            cellFormatForBody.setWrap(true);//是否自动换行  
            cellFormatForBody.setBorder(Border.ALL, BorderLineStyle.THIN);//设置边框样式  
            cellFormatForBody.setBackground(Colour.LIGHT_GREEN);//设置背景色  
              
            //10、给正文添加内容  
            //第一列，序号，数字类型，Number():参数依次为：第几列，第几行，显示内容，样式  
            Number number = new Number(0,2,1,cellFormatForBody);  
            sheet.addCell(number);  
              
            //第二列，姓名，基本文字类型  
            Label name = new Label(1,2,"张三",cellFormatForBody);  
            sheet.addCell(name);  
              
            //第三列，年龄，数字类型  
            Number age = new Number(2,2,10,cellFormatForBody);  
            sheet.addCell(age);  
              
            //第四列，工资，带有格式的数字类型  
            NumberFormat nf = new NumberFormat("#.##");//格式  
            WritableCellFormat wcfN = new WritableCellFormat(nf);  
            //设置样式  
            wcfN.setAlignment(Alignment.CENTRE); //设置水平方向对齐方式  
            wcfN.setVerticalAlignment(VerticalAlignment.CENTRE);//设置垂直方向对齐方式  
            wcfN.setWrap(true);//是否自动换行  
            wcfN.setBorder(Border.ALL, BorderLineStyle.THIN);//设置边框样式  
            wcfN.setBackground(Colour.LIGHT_GREEN);//设置背景色  
            Number salary = new Number(3,2,3000.1415926,wcfN);  
            sheet.addCell(salary);  
                  
            //第五列，出生日期，时间类型  
            DateFormat df = new DateFormat("yyyy-MM-dd hh:mm:ss");//定义时间格式  
            WritableCellFormat wcfDF = new WritableCellFormat(df);  
            //设置样式  
            wcfDF.setAlignment(Alignment.CENTRE); //设置水平方向对齐方式  
            wcfDF.setVerticalAlignment(VerticalAlignment.CENTRE);//设置垂直方向对齐方式  
            wcfDF.setWrap(true);//是否自动换行  
            wcfDF.setBorder(Border.ALL, BorderLineStyle.THIN);//设置边框样式  
            wcfDF.setBackground(Colour.LIGHT_GREEN);//设置背景色  
            DateTime birthday = new DateTime(4,2,new Date(),wcfDF);  
            //添加值  
            sheet.addCell(birthday);  
              
            //第六列，婚否，boolean类型  
            //Boolean marry = new Boolean(5, 2, false, cellFormatForBody);  
            //sheet.addCell(marry);  
          //下拉选的格式，只能从给定的数据中选择  
            Label lblColumn  = new Label(5, 2, "请选择",cellFormatForBody);//生成一个待选择的标签  
            WritableCellFeatures wcf2 = new WritableCellFeatures();//待选择集合对象，这是jxl的对象  
            List<String> angerlist = new ArrayList<String>();  
            angerlist.add("已婚");  
            angerlist.add("未婚");  
            wcf2.setDataValidationList(angerlist);//设置jxl对象要选择的集合  
            lblColumn.setCellFeatures(wcf2);//设置到单元格里面去  
            sheet.addCell(lblColumn);//加入sheet表格中  
                  
            //第七列，头像，图片类型:只支持png文件  
            //WritableImage():参数依次为：第几列，第几行，图片需要占据几列，图片需要占据几行，图形文件  
            System.out.print("图片路径："+(savePath+ java.io.File.separator+"1.png"));  
            WritableImage photo=new WritableImage(6,2,1,1,new File(savePath+ java.io.File.separator+"1.png"));  
            sheet.addImage(photo);  
              
            sheet.setRowView(2,400,false);//正文行高20  
              
            //11、在Excel中写入数据并关闭文件  
            workBook.write();  
            workBook.close();  
              
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}  
//说明：
//1、启用了密码保护功能，所以生成的Excel不能编辑，Excel2007可以在”审阅-更改-撤销工作表保护“模块取消保护，取消时要求输入下面设置的密码123456
//
//
//sheet.getSettings().setProtected(true) ;//启用保护  
//sheet.getSettings().setPassword("123456") ;//设置保护密码
//2、设置行高的问题：
//可以使用setRowView(int i,int height)方法来设置行高
//sheet.setRowView(0,500,false);  
// 这个方法可以有两个参数，也可以有三个。
//第一个参数是要设置行高的行，
//第二个参数是设置行高的高度。这个高度大概得设置成Excel里面行高的20倍才能达到相应的效果。
//第三个参数是boolean类型，是否隐藏，默认为隐藏。要是不调用setRowView方法那么当前行不会隐藏，要是调用了且只写了两个参数，那么会被隐藏。写了三个参数时，true为隐藏，false为不隐藏。设置列宽的setColumnView方法跟这个相同。
//3、图像类型的数据：
//WritableImage photo=new WritableImage(6,2,1,1,new File(savePath+ java.io.File.separator+"1.png"));  
//这四个参数分别表示：第几列，第几行，图片需要占据几列，图片需要占据几行，图形文件。需要注意的是第三和第四两个参数，它们并不是图片的像数数值，而是占据几个单元格的意思。
//标题带有下拉框的形式：
//Label lblColumn  = new Label(5, 2, "请选择",cellFormatForBody);//生成一个待选择的标签  
//WritableCellFeatures wcf2 = new WritableCellFeatures();//待选择集合对象  
//List angerlist = new ArrayList();  
//angerlist.add("已婚");  
//angerlist.add("未婚");  
//wcf2.setDataValidationList(angerlist);//设置jxl对象要选择的集合  
//lblColumn.setCellFeatures(wcf2);//设置到单元格里面去  
//sheet.addCell(lblColumn);//加入sheet表格中