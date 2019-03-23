package com.julius.jobmanagementsystem.automaticrating;

import com.julius.jobmanagementsystem.domain.entity.Result;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GetResultOfAllStudent {

	private List<Result> results;

	public GetResultOfAllStudent() {
		results = new ArrayList<Result>();
	}

	// 获得filepath这个文件的所有子文件夹
	private List<String> getALLFile(String filePath) {
		List<String> list = new ArrayList<>();
		File f = new File(filePath);
		if (f.isDirectory()) // 如果filePath是路径
		{
			File[] fList = f.listFiles();
			for (File file : fList) {
				list.add(file.getPath());
			}

		}
		return list;

	}

	public void getFinalResult(String filepath) {
		GetResultOfCrawlExcel getResultOfCrawlExcel = new GetResultOfCrawlExcel();
		List<String> filelist = new ArrayList<>();
		filelist = getALLFile(filepath); // 获取作业文件夹下所有的子文件夹
		for (String string : filelist) { // 遍历子文件夹下所有的excel文件
			try {
				List<String> excellist = new ArrayList<>();
				excellist = getALLFile(string); // 存放的是所有待打分的文件的路径
				int result = 0; // 用于记录分数
				for (String excelfilepath : excellist) { // 统计多个文件的总成绩
					result += getResultOfCrawlExcel.getResult(excelfilepath);
				}
				result = (int) (result / excellist.size()); // 取平均成绩
				String[] strings = string.split("[\\\\|/]"); // 以 /划分路径
				String studentid = strings[strings.length - 1]; // 学号
				strings = filepath.split("[\\\\|/]"); // 以 /划分路径
				String taskid = strings[strings.length - 1]; // 作业id
				int idOfTask = Integer.parseInt(taskid); // 把String转成int
				Result result2 = new Result();
				// 构造result对象
				result2.setScore(result);
				result2.setStuId(studentid);
				result2.setTaskId(idOfTask);
				results.add(result2);
				/*
				 * try { resultService.updateResult(result2); //更新数据库 } catch
				 * (Exception e) { // TODO Auto-generated catch block
				 * e.printStackTrace(); }
				 */
				System.out.println(studentid + "的分数是" + result);
				System.out.println("********");
			} catch (Exception e) {
				System.out.println(e);
				continue;
			}
		}
	}

	public List<Result> getResults() {
		return results;
	}

}
