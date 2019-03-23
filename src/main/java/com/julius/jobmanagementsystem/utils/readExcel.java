package com.julius.jobmanagementsystem.utils;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class readExcel {
	
	/**
	 * 读取的EXCEL的格式为
	 * 姓名     年级        性别
	 * 念念     研一        女
	 * 小虎子
	 * @param filepath
	 * @return List<List<Map<String, String>>>  第一个list存sheet,第二个list存列。按照map取关键字
	 * 如：map=姓名 ，输出为念念、小虎子
	 * @throws Exception
	 */
	
	public List<List<Map<String, String>>> readExcelWithTitle(String filepath) throws Exception
	{
		String fileType = filepath.substring(filepath.lastIndexOf(".") + 1, filepath.length());
		InputStream is = null;
		Workbook wb = null;
		try {
			is = new FileInputStream(filepath);
			
		    if (fileType.equals("xls")) {
		    	wb = new HSSFWorkbook(is);
		    } else if (fileType.equals("xlsx")) {
		    	wb = new XSSFWorkbook(is);
		    } else {
		    	throw new Exception("读取的不是excel文件");
		    }
		    
		    List<List<Map<String, String>>> result = new ArrayList<List<Map<String,String>>>();//对应excel文件
		    
		    int sheetSize = wb.getNumberOfSheets();
		    for (int i = 0; i < sheetSize; i++) {//遍历sheet页
		    	Sheet sheet = wb.getSheetAt(i);
		    	List<Map<String, String>> sheetList = new ArrayList<Map<String, String>>();//对应sheet页
		    	
		    	List<String> titles = new ArrayList<String>();//放置所有的标题
		    	
		    	int rowSize = sheet.getLastRowNum() + 1;
		    	for (int j = 0; j < rowSize; j++) {//遍历行
		    		Row row = sheet.getRow(j);
		    		if (row == null) {//略过空行
						continue;
					}
		    		int cellSize = row.getLastCellNum();//行中有多少个单元格，也就是有多少列
		    		if (j == 0) {//第一行是标题行
		    			for (int k = 0; k < cellSize; k++) {
			    			Cell cell = row.getCell(k);
			    			titles.add(cell.toString());
			    		}
		    		} else {//其他行是数据行
		    			Map<String, String> rowMap = new HashMap<String, String>();//对应一个数据行
		    			for (int k = 0; k < titles.size(); k++) {
		    				Cell cell = row.getCell(k);
		    				String key = titles.get(k);
		    				String value = null;
		    				if (cell != null) {
								value = cell.toString();
							}
		    				rowMap.put(key, value);
		    			}
		    			sheetList.add(rowMap);
		    		}
		    	}
		    	result.add(sheetList);
		    }
		    
		    return result;
		} catch (FileNotFoundException e) {
			throw e;
		} finally {
			if (wb != null) {
				wb.close();
			}
			if (is != null) {
				is.close();
			}
		}
	}
	
	/**
	 * 读取excel文件，将excel文件转换为list。限定一个excel文件只能拥有一个sheet。
	 * 
	 * @param filePath
	 *            完全限定文件名
	 * @return List<List<String>>
	 */
	
	public static List<List<String>> read(String filePath) {
		List<List<String>> content = null;
		InputStream is = null;
		Workbook wb = null;
		try {
			// 判断文件是否为excel格式的文件
			is = new FileInputStream(filePath);
			if (filePath.endsWith(".xls")) {
				wb = new HSSFWorkbook(is);
			} else if (filePath.endsWith(".xlsx")) {
				wb = new XSSFWorkbook(is);
			} else {
				throw new Exception("读取的不是excel文件");
			}

			// 遍历excel，将结果存储在content中
			content = new ArrayList<List<String>>();
			Sheet sheet = wb.getSheetAt(0);
			List<String> rowList = null; 
			for (int i = 0; i < sheet.getLastRowNum() + 1; i++) { //遍历行
				Row row = sheet.getRow(i);
				rowList = new ArrayList<String>();
				if (row != null) {
					for (int j = 0; j < row.getLastCellNum(); j++) {
						if (row.getCell(j) == null) { //第i行j列为空
							rowList.add("");  
						} else {
							try{
								rowList.add(row.getCell(j).getStringCellValue());
							}catch(Exception e){
								continue;
							}
						}
					}
				}
				content.add(rowList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (wb != null) {
					wb.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return content;
	}

	//按照华中科技大学的名单格式导入
	public List<Map<String, String>> readStudent(String filepath) throws Exception
	{
		String fileType = filepath.substring(filepath.lastIndexOf(".") + 1, filepath.length());
		InputStream is = null;
		Workbook wb = null;
		try {
			is = new FileInputStream(filepath);
			
		    if (fileType.equals("xls")) {
		    	wb = new HSSFWorkbook(is);
		    } else if (fileType.equals("xlsx")) {
		    	wb = new XSSFWorkbook(is);
		    } else {
		    	throw new Exception("读取的不是excel文件");
		    }
		    
		        List<Map<String, String>> result = new ArrayList<Map<String,String>>();//对应excel文件
		    	
		    	List<String> titles = new ArrayList<String>();//放置所有的标题
		    	Sheet sheet = wb.getSheetAt(0); //第一个sheet
		    	int rowSize = sheet.getLastRowNum() + 1;
		    	for (int j = 3; j < rowSize; j++)
		    	{//遍历行
		    		Row row = sheet.getRow(j);
		    		if (row == null || j==4 ||j==5 || j==6) 
		    		{//略过空行
						continue;
					}
		    		int cellSize = row.getLastCellNum();//行中有多少个单元格，也就是有多少列
		    		if (j == 3)
		    		{//第三行是标题行
		    			for (int k = 0; k < 4; k++) 
		    			{
			    			Cell cell = row.getCell(k);
			    			titles.add(cell.toString());
			    		}
		    		} 
		    		else 
		    		{//其他行是数据行
		    			Map<String, String> rowMap = new HashMap<String, String>();//对应一个数据行
		    			for (int k = 0; k < titles.size(); k++)
		    			{
		    				Cell cell = row.getCell(k);
		    				String key = titles.get(k);
		    				String value = null;
		    				if (cell != null) 
		    				{
								value = cell.toString();
							}
		    				rowMap.put(key, value);
		    			}
		    			result.add(rowMap);
		    			
		    		}
		    		
		    	}
		    return result;
		} catch (FileNotFoundException e) {
			throw e;
		} finally {
			if (wb != null) {
				wb.close();
			}
			if (is != null) {
				is.close();
			}
		}
	}
	
	public static void main(String args[])
	{
		readExcel test = new readExcel();
		List<Map<String, String>> result = new ArrayList<Map<String,String>>();//对应excel文件
		try {
			result=test.readStudent("C://Users//Administrator//Desktop//华中科技大学报表大数据.xls");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Map<String, String> map : result) {
			System.out.print(map.get("姓名"));
			System.out.print(map.get("学号"));
			  System.out.println("");
			
		}
	  
		
	}

}
