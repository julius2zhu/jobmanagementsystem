package com.julius.jobmanagementsystem.automaticrating;

import com.julius.jobmanagementsystem.automaticrating.utils.TxtReader;
import com.julius.jobmanagementsystem.utils.Config;
import org.ansj.domain.Result;
import org.ansj.library.DicLibrary;
import org.ansj.recognition.impl.StopRecognition;
import org.ansj.splitWord.analysis.DicAnalysis;

import java.util.ArrayList;
import java.util.List;

public class AnsjSegmentation {
	// 文档集合
	private List<String> wordList;
	// 分词后的集合存放
	private List<String[]> segList;
	// 停用词表的存放路径
	private static String stopWordsPath = Config.stopWordPath;
	// 用户词表的存放路径
	private static String userWordsPath = Config.userWordsPath;
	// 用来存放未过滤停用词的结果
	private List<String[]> listWithoutFilter = new ArrayList<String[]>();
	private static StopRecognition filter;
	// 加载停用词
/*	static {
		List<String> list = new TxtReader().getDataFromTxt(stopWordsPath);
		filter = new StopRecognition();
		filter.insertStopWords(list);
//		filter.insertStopNatures("m");
//		filter.insertStopNatures("en");
		System.out.println("停用词加载成功！");
	}*/
	// 添加用户自定义词典
/*	static {
		List<String> list = new TxtReader().getDataFromTxt(userWordsPath);
		for (String str : list) {
			DicLibrary.insert(DicLibrary.DEFAULT, str);
		}

	}*/
	static {DicLibrary.insert(DicLibrary.DEFAULT, "");}
	// 初始化变量
	public AnsjSegmentation() {
		wordList = new ArrayList<String>();
		segList = new ArrayList<String[]>();
		System.out.println("stopwords:"+stopWordsPath);
		List<String> list = TxtReader.getDataFromTxt(stopWordsPath);
		filter = new StopRecognition();
		filter.insertStopWords(list);
		System.out.println("停用词加载成功！共计："+list.size());
		System.out.println(stopWordsPath);
		List<String> List = TxtReader.getDataFromTxt(userWordsPath);
		for (String str : List) {
			DicLibrary.insert(DicLibrary.DEFAULT, str);
		}
		System.out.println("用户自定义词共计(无参)："+List.size());
	}
	
	public AnsjSegmentation(String stopWordsPath, String userWordsPath) {
		wordList = new ArrayList<String>();
		segList = new ArrayList<String[]>();
		System.out.println(stopWordsPath);
		List<String> list = TxtReader.getDataFromTxt(stopWordsPath);
		filter = new StopRecognition();
		filter.insertStopWords(list);
		System.out.println("停用词加载成功！共计："+list.size());
		System.out.println(stopWordsPath);
		List<String> List = TxtReader.getDataFromTxt(userWordsPath);
		for (String str : List) {
			DicLibrary.insert(DicLibrary.DEFAULT, str);
		}
		System.out.println("用户自定义词共计："+List.size());
	}

	// 分词实现（用户自定义分词）
	public void segment() {
		// TODO Auto-generated method stub
		// 循环，处理一句一句的分词
		for (String word : wordList) {
			// 若word为空则跳过分词部分
			if (word == null) {
				segList.add(new String[] {});
				continue;
			}
			// 用来存放分词后的结果
			Result result = new Result(null);
			// 用用户自定义分词模式得到一个未过滤的集合
			result = DicAnalysis.parse(word);
			if(result.size() == 0){
				segList.add(new String[] {});
				continue;
			}
			// 将result去掉词性后（toStringWithOutNature）按（","）切分为一个一个的单词
			listWithoutFilter.add(result.toStringWithOutNature().split(","));
			// 过滤停用词
			result = result.recognition(filter);
			if(result.size() == 0){
				segList.add(new String[] {});
				continue;
			}
			segList.add(result.toStringWithOutNature().split(","));
		}
	}

	public List<String[]> getListWithoutFilter() {
		return listWithoutFilter;
	}

	// 将给定string经过过滤停用词分词为一个str数组（一个一个的词语）
	public String[] segmentToArray(String str) {
		// TODO Auto-generated method stub
		Result result = new Result(null);
		if (str == null)
			return new String[] {};
		result = DicAnalysis.parse(str).recognition(filter);
		if (result.size() == 0)
			return new String[] {};
		return result.toStringWithOutNature().split(",");
	}

	// 将给定string经过过滤停用词分词为一个str（以,分隔每个词语）
	public String segmentToString(String str) {
		// TODO Auto-generated method stub
		Result result = new Result(null);
		if (str == null)
			return "";
		result = DicAnalysis.parse(str).recognition(filter);
		if (result.size() == 0)
			return "";
		return result.toStringWithOutNature();
	}

	//获取分词后的词语集合
	public List<String[]> getSegList() {
		return segList;
	}
	//设置要分词的文档集合
	public void setWordList(List<String> wordList) {
		this.wordList = wordList;
	}

}
