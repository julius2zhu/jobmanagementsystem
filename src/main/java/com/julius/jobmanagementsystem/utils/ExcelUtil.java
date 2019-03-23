package com.julius.jobmanagementsystem.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 描述：Excel写操作帮助类
 *
 * 
 * */
public class ExcelUtil {
    /**
     * 功能：多行多列导入到Excel并且设置标题栏格式
     */
    public static void writeArrayToExcel(HSSFWorkbook wb,HSSFSheet sheet,int rows,int cells,Object [][]value){
 
          Row row[]=new HSSFRow[rows];
         Cell cell[]=new HSSFCell[cells];
     
         sheet.setColumnWidth(0, 20*256);
         
          HSSFCellStyle ztStyle =  (HSSFCellStyle)wb.createCellStyle();

         Font ztFont = wb.createFont();  
         ztFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
         //ztFont.setItalic(true);                     // 设置字体为斜体字  
        // ztFont.setColor(Font.COLOR_RED);            // 将字体设置为“红色”  
         ztFont.setFontHeightInPoints((short)12);    // 将字体大小设置为18px  
         ztFont.setFontName("宋体");             // 将“华文行楷”字体应用到当前单元格上  
        // ztFont.setUnderline(Font.U_DOUBLE);
         ztStyle.setFont(ztFont);
         
         for(int i=0;i<row.length;i++){
             row[i]=sheet.createRow(i);

             for(int j=0;j<cell.length;j++){
                 cell[j]=row[i].createCell(j);
                 cell[j].setCellValue(convertString(value[i][j]));
               System.out.println(cell[j]);
                 if(i==0)
                   cell[j].setCellStyle(ztStyle);
                  
             }
 
         }
         try {
			wb.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * 功能：将HSSFWorkbook写入Excel文件
     * @param     wb        HSSFWorkbook
     * @param     absPath    写入文件的相对路径
     * @param     wbName    文件名
     */
    public static void writeWorkbook(HSSFWorkbook wb,String fileName){
        FileOutputStream fos=null;
        File f=new File(fileName);
        try {
            fos=new FileOutputStream(f);
            wb.write(fos);
            wb.close();
        } catch (FileNotFoundException e) {
           System.out.println("导入数据前请关闭工作表");

         } catch ( Exception e) {
        	 System.out.println("没有进行筛选");

         } finally{
            try {
                if(fos!=null){
                    fos.close();
                }
            } catch (IOException e) {
             }
        }
    }
    
    /**
	 * 功能：多行多列导入到Excel并且设置标题栏格式
	 * @param fileName 输出的excel文件名
	 * @param rows	行数
	 * @param cells	列数
	 * @param value 要输出的数据，二维数组Object[][]
	 */
    public static void writeToExcel(String fileName,int rows,int cells,Object [][]value){
    	
    	//new 文件
        FileOutputStream fos=null;
        File f=new File(fileName);
    	
        Workbook wb = null;
        Sheet sheet = null;
        
        int lastRowNum = 0;
                
  
    	//文件不存在，新建的文件 判断文件类型
    	if (fileName.endsWith("xls")) {
			wb = new HSSFWorkbook();
			
		} else if (fileName.endsWith("xlsx")) {
			wb = new XSSFWorkbook();
		} else {
			System.out.println("文件类型不正确！");
			return;
		}
		sheet = wb.createSheet();
       
        
        
        Row row = null; 
        
         for(int i = 0 ; i < rows ; i++){
            //
        	 row = sheet.createRow(lastRowNum+i);
             for(int j= 0 ; j<cells ; j++){
                //
            	 row.createCell(j).setCellValue(convertString(value[i][j]));                 
             } 
         }
        
         //数据写入文件
         try {
             fos=new FileOutputStream(f);
             wb.write(fos);
             wb.close();
             fos.close();
         }  catch ( Exception e) {
         	 System.out.println("写入文件出错");
         	 e.printStackTrace();

          } 
    }
    
    public static String convertString(Object value) {
        if (value == null) {
            return "";
        } else {
            return value.toString();
        }
    }



 
}