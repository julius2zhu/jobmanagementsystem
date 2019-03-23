package com.julius.jobmanagementsystem.automaticrating.utils;

import java.util.ArrayList;
import java.util.List;

public class ListCompareUtils {
	/**
	 * 比较两个字符串集合中的字符串相同的比例，当且仅当两个集合大小相同是有正确结果，否则返回结果为0.8
	 * 
	 * @param source
	 *            源集合
	 * @param target
	 *            目标集合
	 * @return
	 */
	public static double compareList(List<String> source, List<String> target) {
		double result = -1;
		int count = 0;
		if (source.size() <= target.size()) {
			for (int i = 0; i < source.size(); i++) {
				String sr = source.get(i);
				String tr = target.get(i);
				if (sr.equals(tr)) {
					count++;
				}else{
				//	System.out.println("第"+i+"行,source:"+sr.length()+" target:"+tr.length());
					/*if(i==40){
						System.out.println("source:"+sr);
						System.out.println("target:"+tr);
					}
					if(i==80){
						System.out.println("source:"+sr);
						System.out.println("target:"+tr);
					}*/
				}
			}
			result = 1.0 * count / source.size();
		} else {
			result = 0.8;
		}
		return result;
	}

	/**
	 * 比较两个集合里面所包含的词语个数（以,或空格分割词语）
	 * 
	 * @param source
	 *            源集合
	 * @param target
	 *            目标集合
	 * @return （源词语个数-目标词语个数）/源词语个数
	 */
	public static double compareListByword(List<String[]> source, List<String[]> target) {
		double result = -1;
		List<String> srWords = new ArrayList<String>();
		List<String> trWords = new ArrayList<String>();
		int newWords = 0;
		int oldWords = 0;
		for (String[] srStr : source) {
			for (String string : srStr) {
				if (!srWords.contains(string)) {
					srWords.add(string);
				}
			}
		}
		for (String[] trStr : target) {
			for (String string2 : trStr) {		
				if (!trWords.contains(string2)) {
					trWords.add(string2);
					if(!srWords.contains(string2)){
						newWords++;
					}else{
						oldWords++;
					}
				}
				
			}
		}
		
		System.out.println("相差" + (srWords.size() - (newWords+oldWords)) + "个词语");
		result = 1.0 * (srWords.size() - oldWords) / srWords.size();
		return result;
	}

	// 将list<String[]>以,为分隔符变为List<String>
	public static List<String> changeList(List<String[]> source) {
		List<String> target = new ArrayList<String>();
		for (String[] string : source) {
			StringBuilder builder = new StringBuilder();
			for (String string2 : string) {
				builder.append(string2 + ",");
			}
			if (builder.length() != 0)
				builder = builder.deleteCharAt(builder.length() - 1);
			target.add(builder.toString());
		}
		return target;
	}
}
