package com.julius.jobmanagementsystem.automaticrating;

import com.julius.jobmanagementsystem.automaticrating.utils.ExcelReader;
import com.julius.jobmanagementsystem.automaticrating.utils.zipUtils;
import com.julius.jobmanagementsystem.utils.Config;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 对实验四(分类实验)进行自动评分
 * 
 * @author Chan
 *
 */
public class GetResultOfTask4 {
	// 三部分的分数、权重分别为0.25,0.5,0.25
	double weight1 = 0.25f;
	double weight2 = 0.50f;
	double weight3 = 0.25f;

	// 大数据与招聘的先验概率
	final double priorPROfBigData = 0.5307470078415187;
	final double priorPROfRecruit = 0.4692529921584812;
	// 大数据与招聘的词数
	final int totalWordsOfBigData = 1571696;
	final int totalWordsOfRecruit = 227891;
	// 词典大小
	final int wordBook = 40002;
	// 每个单词在某类下的条件概率
	private static HashMap<String, double[]> conditionalPROfWord = new HashMap<String, double[]>();

	// 读取先验概率
	static {
		try {
			File file = new File(GetResultOfTask4.class.getResource("/bayesdata/bayesdata.txt").getPath());
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.isEmpty()) {
					continue;
				}
				String[] wordsinfo = line.split("\t");
				conditionalPROfWord.put(wordsinfo[0],
						new double[] { Double.valueOf(wordsinfo[1]), Double.valueOf(wordsinfo[2]) });
			}
			br.close();
			fr.close();
		} catch (Exception e) {
			System.out.println("读取文件出错");
		}
	}

	// 自动判分
	public int getTotalResult(Integer taskId, String stuId) {
		String taskPath = Config.task + taskId + "/" + stuId + "/";
		try {
			zipUtils.unzip(new File(taskPath + "classify.zip"), taskPath);
		} catch (Exception e) {
			System.out.println("解压异常...");
			return 0;
		}
		double part1 = 0.0, part2 = 0.0, part3 = 0.0;
		String filePath = taskPath + "classify";
		if (checkPath(filePath)) {
			part1 = judgePart1(filePath);
			part2 = judgePart2(filePath);
			part3 = judgePart3(filePath);
		}

		return (int) (part1 * weight1 + part2 * weight2 + part3 * weight3);
	}

	private boolean checkPath(String filePath) {
		File classifyDir = new File(filePath);

		File[] checkDir = new File[3];
		File[] subClassifyDir = classifyDir.listFiles();
		for (int i = 0; i < subClassifyDir.length; i++) {
			if (!subClassifyDir[i].isDirectory()) {
				return false;
			}
			if (subClassifyDir[i].getName().equals("分类结果")) {
				checkDir[0] = subClassifyDir[i];
			} else if (subClassifyDir[i].getName().equals("训练集")) {
				checkDir[1] = subClassifyDir[i];
			} else if (subClassifyDir[i].getName().equals("训练器输出")) {
				checkDir[2] = subClassifyDir[i];
			}
		}
		boolean sub1 = checkSubPath(checkDir[0], "大数据", "招聘");
		boolean sub2 = checkSubPath(checkDir[1], "大数据", "招聘");
		boolean sub3 = checkSubPath(checkDir[2], "训练集条件概率.txt", "训练集先验概率.txt");

		return sub1 && sub2 && sub3;
	}

	private boolean checkSubPath(File file, String filename1, String filename2) {
		File[] files = file.listFiles();
		if (files.length != 2) {
			return false;
		}
		int checkfile = 0;
		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().contains(filename1)) {
				checkfile++;
			} else if (files[i].getName().contains(filename2)) {
				checkfile++;
			}
		}
		if (checkfile != 2) {
			return false;
		}
		return true;
	}

	// part1判分:检查训练集爬取是否符合要求。
	private double judgePart1(String filePath) {
		double part1 = 0.0;
		File dir = new File(filePath + "/训练集");
		int[] excelRows = new int[2];
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			excelRows[i] = ExcelReader.readRows(files[i].getAbsolutePath());
		}

		for (int row : excelRows) {
			if (row < 500) {
				part1 += 30;
			} else if (row <= 850) {
				part1 += 32.5;
			} else if (row <= 1000 || row >= 2000) {
				part1 += 45;
			} else {
				part1 += 50;
			}
		}

		return part1;
	}

	// part2判分：检查训练器输出情况
	private double judgePart2(String filePath) {
		double part2 = 0;
		File dir = new File(filePath + "/训练集");
		int[] excelRows = new int[2];
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			excelRows[i] = ExcelReader.readRows(files[i].getAbsolutePath());
		}

		double[] correctPriorPR = new double[2];
		for (int i = 0; i < correctPriorPR.length; i++) {
			correctPriorPR[i] = (double) excelRows[i] / (excelRows[0] + excelRows[1]);
		}
		Arrays.sort(correctPriorPR);

		double[] stuPriorPR = new double[2];
		try {
			File filePriorPR = new File(filePath + "/训练器输出/训练集先验概率.txt");
			FileReader fr = new FileReader(filePriorPR);
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			int row = 0;
			while (row < 2 && (line = br.readLine()) != null) {
				if (line.isEmpty()) {
					continue;
				}
				String[] wordsinfo = line.split("\t");
				stuPriorPR[row++] = Double.valueOf(wordsinfo[1]);
			}
			br.close();
			fr.close();

			Arrays.sort(stuPriorPR);
			double stuSumPriorPR = stuPriorPR[0] + stuPriorPR[1];
			if (stuSumPriorPR <= 1.1 && stuSumPriorPR >= 0.9) {
				double p = Math.abs(correctPriorPR[0] - stuPriorPR[0]);
				if (p <= 1e-10) {
					part2 = 48 + Math.random() * 2.0;
				} else if (p <= 0.00005) {
					part2 = 45 + Math.random() * 5.0;
				} else if (p <= 0.0001) {
					part2 = 40 + Math.random() * 5.0;
				} else if (p <= 0.0005) {
					part2 = 35 + Math.random() * 5.0;
				} else if (p <= 0.001) {
					part2 = 30 + Math.random() * 5.0;
				} else {
					part2 = 25 + Math.random() * 10.0;
				}
			} else {
				part2 = 15 + Math.random() * 5.0;
			}
		} catch (Exception e) {
			part2 = 0;
			System.out.println("读取训练集先验概率.txt出错。");
		}

		try {
			long conditionFile = new File(filePath + "/训练器输出/训练集条件概率.txt").length();
			conditionFile /= 50;
			if (conditionFile < 10000) {
				part2 += 25 + Math.random() * 5.0;
			} else if (conditionFile < 20000) {
				part2 += 30 + Math.random() * 5.0;
			} else if (conditionFile < 25000) {
				part2 += 35 + Math.random() * 5.0;
			} else if (conditionFile < 30000) {
				part2 += 40 + Math.random() * 5.0;
			} else {
				part2 += 48 + +Math.random() * 2.0;
			}
		} catch (Exception e) {
			part2 += 0;
			System.out.println("读取训练集先验概率.txt出错。");
		}

		return part2;
	}

	// part3判分：检查分类结果正确率
	private double judgePart3(String filePath) {
		double part3 = 0.0;
		File dir = new File(filePath + "/分类结果");
		// 随机选取一个文件进行评分
		int index = (int) (Math.random() * 2);
		File file = dir.listFiles()[index];
		List<String> content = ExcelReader.read(file.getAbsolutePath(), 1);

		// 将分类结果存储于result中,只存储个数
		int[] result = new int[2];
		nativebayes(content, result);

		if (Math.abs(result[0] + result[1] - content.size()) < 10) {
			int p = (int) Math.abs(result[index] - content.size());
			if (p <= 5) {
				part3 = 95 + Math.random() * 5.0;
			} else if (p <= 25) {
				part3 = 90 + Math.random() * 5.0;
			} else if (p <= 50) {
				part3 = 85 + Math.random() * 5.0;
			} else if (p <= 100) {
				part3 = 75 + Math.random() * 5.0;
			} else {
				part3 = 60 + Math.random() * 5.0;
			}
		} else {
			part3 = 0;
		}

		return part3;
	}

	// 利用bayes处理content，将结果存储于result中
	private void nativebayes(List<String> content, int[] result) {
		// 对指定列进行分词
		AnsjSegmentation seg = new AnsjSegmentation();
		seg.setWordList(content);
		seg.segment();
		List<String[]> segList = seg.getSegList();
		for (int i = 0; i < segList.size(); i++) {
			// 对该条数据利用训练集进行分类，返回类的索引
			result[classifyOneRow(segList.get(i))]++;
		}
	}

	private int classifyOneRow(String[] cell) {
		// 文档d属于类Ci的概率
		double[] prOfDBelongsToCi = new double[2];
		// 使用分词将该cell分词
		for (String word : cell) {
			double[] values = conditionalPROfWord.get(word);
			if (values != null) {
				prOfDBelongsToCi[0] += Math.log(values[0]);
				prOfDBelongsToCi[1] += Math.log(values[1]);
			} else {
				prOfDBelongsToCi[0] += (double) 1 / (totalWordsOfBigData + wordBook);
				prOfDBelongsToCi[1] += (double) 1 / (totalWordsOfRecruit + wordBook);
			}
		}
		prOfDBelongsToCi[0] += Math.log(priorPROfBigData);
		prOfDBelongsToCi[1] += Math.log(priorPROfRecruit);

		return prOfDBelongsToCi[0] >= prOfDBelongsToCi[1] ? 0 : 1;
	}

	// public static void main(String[] args) {
	// // TODO Auto-generated method stub
	// GetResultOfTask4 g = new GetResultOfTask4();
	// System.out.println(g.judge("C:/Users/Chan/Desktop/classify"));
	// try {
	// // zipUtils.unzip(new File("C:/Users/Chan/Desktop/classify.zip"),
	// // "C:/Users/Chan/Desktop/");
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

}
