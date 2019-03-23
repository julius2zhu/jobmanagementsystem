package com.julius.jobmanagementsystem.automaticrating.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 控制台打印List<String>等信息、
 * 及其他工具类
 * @author tankai
 *
 */
public class ClusterUtil {

	
	public static void showDatalist(List<String> list){
		for(String s : list){
			System.out.println(s);
		}
	}
	/**
	 * 显示分词后的文本结果
	 */
	public static void showSeglist(List<String[]> seglist){
		for(String[] s:seglist){
			for(String str:s){
				System.out.print(str+" ");
			}
			System.out.println();
		}
	}
	
	/**
	 * 显示文本向量空间
	 */
	public static void showVectors(List<double[]> vectors){
		for(double[] v : vectors ){
			for(double d :v){
				System.out.print(String.format("%4.2f", d)+" ");
			}
			System.out.println();
		}
	}
	/**
	 * 显示聚类结果
	 */
	public static void showResult(List<List<Integer>> resultIndex, List<String> datalist){
		for(int i = 0 ; i < resultIndex.size() ; i++){
			List<Integer> tmpIndex = resultIndex.get(i);
			if(tmpIndex == null && tmpIndex.size() == 0){
				continue;	
			}
			System.out.println("类别"+i+":"+datalist.get(tmpIndex.get(0)));
			for(int j = 0 ; j < tmpIndex.size() ; j++){
				System.out.println(datalist.get(tmpIndex.get(j)));
			}
			System.out.println();		
		}
	}
	
	/**
	 * 通过聚类的下标集合和数据集合得到List<List<String>>类型的数据
	 * @param resultIndex
	 * @param datalist
	 * @return
	 */
	public static List<List<String>> getClusters(List<List<Integer>> resultIndex, List<String> datalist){
		List<List<String>> list = new ArrayList<>();
		for(int i = 0 ; i < resultIndex.size() ; i++){
			List<Integer> tmpIndex = resultIndex.get(i);
			List<String> tmpList = new ArrayList<>();
			for(int j = 0 ; j < tmpIndex.size() ; j++){
				tmpList.add(datalist.get(tmpIndex.get(j)));
			}
			list.add(tmpList);
		}
		return list;
	}
	
	/**
	 * 删除空类
	 * @param resultIndex
	 * @return
	 */
	public static List<List<Integer>> delNullCluster(List<List<Integer>> resultIndex){
		for(int i = 0 ; i < resultIndex.size() ; i++){
			if(resultIndex.get(i) == null || resultIndex.get(i).size() == 0){
				resultIndex.remove(i);System.out.println(i+"为空");
			}
		}
		
		return resultIndex;
	}
	
	/**
	 * 删除文件夹夹下所有文件
	 * @param dirPath
	 */
	public static void delFolder(String dirPath){
		File dir = new File(dirPath);
		if(!dir.exists()){
			return;
		}
		
		if(dir.isDirectory()){
			File[] files = dir.listFiles();
			for(File file : files){
				if(file.getName().endsWith("xls")){
					file.delete();
				}
			}
		}
	}
	
	/**
	 * 清除特殊字符
	 * @param s
	 * @return
	 */
	public static String stringFilter(String s){
		String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？0123456789]"; 
		Pattern p = Pattern.compile(regEx); 
		Matcher m = p.matcher(s);
		return m.replaceAll("").trim();
	}
}
