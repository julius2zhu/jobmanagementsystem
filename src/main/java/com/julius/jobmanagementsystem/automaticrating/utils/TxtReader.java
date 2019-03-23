package com.julius.jobmanagementsystem.automaticrating.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class TxtReader {
	// 从txt文本中获取语句集合
	/**
	 * 从txt中读取内容得到list集合
	 * @param name 文件路径
	 * @return 
	 */
	public static List<String> getDataFromTxt(String name) {
		List<String> list = new ArrayList<String>();
		//获取指定路径的文件
		File file = new File(name);
		System.out.println(name);
		try {
			if (file.isFile() && file.exists()) {
				//以UTF-8编码格式读取文件流
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF8");
				BufferedReader buff = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = buff.readLine()) != null) {
					if (lineTxt.isEmpty())
						continue;
					//将读取的每行内容放入文本集合中（实际每行都是一个词语）
					list.add(lineTxt);
				}
				buff.close();
				read.close();

			} else {
				System.out.println("文件不存在！请确认输入的文件路径是否正确！");
				System.out.println(file.getAbsolutePath());	
				Log log = LogFactory.getLog(TxtReader.class);
				log.info("文件不存在！请确认输入的文件路径是否正确！");
				log.info(file.getAbsolutePath());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("文件读取出错！");
		}
		return list;
	}

	// 将List<String>写入txt文档不覆盖
	/**
	 * 以追加的形式 写入txt文档中
	 * @param words 要写入文本的内容
	 * @param filePath 文件路径
	 * @return 写入成功返回1，写入失败返回0
	 */
	public static int writeToTxt(String words, String filePath) {
		File file = new File(filePath);
		try {
			if (file.exists()) {
	//			System.out.println("文件已存在，内容将追加到已存在的文件中");
			} else {
				System.out.println("文件不存在，将创建文件");
				file.createNewFile();
			}
			
			 FileOutputStream output = new FileOutputStream(filePath,true);
			output.write(words.getBytes());
			output.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			return 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			return 0;
		}
		return 1;
	}
	//写文件，覆盖
	/**
	 * 覆盖的形式写入txt
	 * @param words 要写入的内容
	 * @param filePath 文件路径
	 * @return 写入成功返回1，失败返回0
	 */
	public static int write2Txt(String words, String filePath) {
		File file = new File(filePath);
		try {
			if (file.exists()) {
				System.out.println("文件已存在，将覆盖文件");
			} else {
				System.out.println("文件不存在，将创建文件");
				file.createNewFile();
			}			
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write(words);
			output.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			return 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			return 0;
		}
		return 1;
	}
}
