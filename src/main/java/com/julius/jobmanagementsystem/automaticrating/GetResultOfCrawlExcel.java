package com.julius.jobmanagementsystem.automaticrating;

import com.julius.jobmanagementsystem.utils.readExcel;

import java.util.List;

public class GetResultOfCrawlExcel {
	
	private double scaleOfRow=0.25;
	private double scaleOfCell=0.25;
	private double scaleOfValide=0.5;
	
	public int getResult(String filepath)
	{
		List<List<String>> excel = readExcel.read(filepath); //读取excel
		int row ,cell=0;
		row=excel.size();  //获取行数
	/*	for (List<String> list : excel) {
			cell = list.size() + cell; //计算每行的列数之和
		}
		cell = cell/row; */
		List<String> list = excel.get(0);
		cell = list.size(); //获取列数
//		System.out.println("lieshu:"+cell);
//		System.out.println("hangshu:"+row);
		int result,type;
		type = getExcelType(filepath);
//		System.out.println(type);
		result=(int) (setResultOfCell(cell, type)*scaleOfCell+setResultOfRow(row)*scaleOfRow+setResultOfValide(excel)*scaleOfValide);
		
		return result;
		
	}
	
	
	//判断Excel是招聘网站还是大数据类型的网站,是大数据网站为0、是招聘网站是1
	private int getExcelType(String filepath)
	{
		int result = 0;
		String[] sourceStrArray = filepath.split("[\\\\|/]");
		String type = sourceStrArray[sourceStrArray.length-1];
		if(type.indexOf("51job")>=0|type.indexOf("lagou")>=0|type.indexOf("zhaopin")>=0|type.indexOf("liepin")>=0|type.indexOf("ganji")>=0)
		{
			result = 1;
		}
		return result;
		
	}
	
	//通过列数判断分数
	private int setResultOfCell(int cell, int type) 
	{
		int result=0;
		if (type==1)//招聘类网站，要求10列
		{
			if(cell > 10)
			{
				int r = cell-10;
				result = 100-r*5;
			}
			else
			{	
				result = 60+(cell/10)*40;
			}
			
		} 
		else 
		{
			if(cell > 3)
			{
				int r = cell-3;
				result = 100-r*5;
			}
			else
			{	
				result = 60+(cell/3)*40;
			}
		}
//		System.out.println("通过列数的分数是"+result);
		return result;
		
	}
	//根据行数得出分数
	private int setResultOfRow(int row) 
	{
		int result=0;
		if (row>1000)
		{
			result = 100;
		}
		else {
			result=row/10;
		}
	//	System.out.println("通过行数的分数是"+result);
		return result;
		
	}

	//如果每条数据有一个字段为空则为无效数据，每10个无效数据扣一分
	private int setResultOfValide(List<List<String>> excel)
	{
		int invalide = 0;
		for (List<String> list : excel) 
		{
			for (String string : list) {
				if (string=="") 
				{
					invalide++;
					break;
				}
				
			}
		}
//		System.out.println("无效条数"+invalide);
		int result;
		double r,t;
		r = 1-(double)invalide/excel.size();//存储
		t= r*40;
		result = (int)t+60;
//		System.out.println("通过有效的分数是"+result);
		return result;
		
	}
	
}
