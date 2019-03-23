package com.julius.jobmanagementsystem.automaticrating;

import com.julius.jobmanagementsystem.automaticrating.convertor.TFIDFConvertor;
import com.julius.jobmanagementsystem.automaticrating.utils.ExcelReader;
import com.julius.jobmanagementsystem.automaticrating.utils.ListCompareUtils;
import com.julius.jobmanagementsystem.automaticrating.utils.TxtReader;
import com.julius.jobmanagementsystem.automaticrating.utils.zipUtils;
import com.julius.jobmanagementsystem.utils.Config;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GetResultOfTask2 {
	private int ansjResult;
	private int tfidfResult;
	private int distanceResult;
	private String taskPath;
	// 源内容文件
	private File ansj_source;
	// 源内容文件里面存储的要分词的内容集合
	private List<String> contentList;
	// 学生分词结果集
	private List<String[]> stuResultContent;
	//测试所得tfidf向量集
	private List<double[]> myResult;
	//存放评分系统最后结果
	private String resultPath;
	
	public GetResultOfTask2() {
		ansjResult = 85;
		tfidfResult = 70;
		distanceResult = 70;
		taskPath = Config.task;
		ansj_source = null;
		contentList = new ArrayList<String>();
		stuResultContent = new ArrayList<String[]>();
	}

	// 自动判断Ansj小实验成绩
	private void checkAnsj() {
		File resultZip = new File(taskPath + "ansj.zip");
		// 存放学生作业结果集合
		List<String> resultContent = new ArrayList<String>();
		if (resultZip.exists()) {
			try {
				zipUtils.unzip(resultZip, taskPath + "ansj"+File.separator+"");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			File dic = new File(taskPath + "ansj"+File.separator+"");
			if (dic.isDirectory()) {
				// 判断该文件目录下是否存在规定的作业文件
				// 判断ansj源文件是否存在，存在读取目标分词内容，不存在，得分60分
				ansj_source = new File(taskPath + "ansj"+File.separator+"ansj_source.xls");
				if (!ansj_source.exists()) {
					ansj_source = new File(taskPath + "ansj"+File.separator+"ansj_source.xlsx");
				}
				if (ansj_source.exists()) {
					// 读取ansj目标列及分词内容，用list存储
					contentList = ExcelReader.read(ansj_source.getAbsolutePath(), 5);
					// 设置本地自定义词典路径
					/*
					 * 
					 */
					AnsjSegmentation segmentation = new AnsjSegmentation();
					segmentation.setWordList(contentList);
					segmentation.segment();
					// 存放我使用本地词典得到的分词结果
					List<String[]> myResultContent = segmentation.getSegList();
					// 判断学生提交的java文件是否存在AnsjSegmentation.java AnsjTest.java文件
					String[] javaFiles = dic.list(new FileFilter("java"));
					if (javaFiles.length < 2) {
						ansjResult -= 5;						
						System.out.println(taskPath + "ansj该目录下java文件不全！");
						TxtReader.writeToTxt(taskPath + "ansj该目录下java文件不全！\r\n",resultPath);
					}
					// 判断学生附加结果文件是否存在
					File ansj_target_add = new File(taskPath + "ansj"+File.separator+"ansj_target_add.xls");
					if (!ansj_target_add.exists()) {
						ansj_target_add = new File(taskPath + "ansj"+File.separator+"ansj_target_add.xlsx");
					}
					if (ansj_target_add.exists()) {
						// 获取附加结果文件的内容
						List<String> target_add = ExcelReader.read(taskPath + "ansj"+File.separator+"ansj_target_add.xlsx", 0);
						// 去掉停用词和用户自定义词典，得到分词结果
						// com.hust.utils.Config.userWordsPath =
						// "src/main/java/dictionary/userwords.txt";
						// com.hust.utils.Config.stopWordPath =
						// "src/main/java/dictionary/stopwords.txt";
						AnsjSegmentation seg = new AnsjSegmentation(GetResultOfTask2.class.getResource("/dictionary/stopwords.txt").getPath(),
								GetResultOfTask2.class.getResource("/dictionary/userwords.txt").getPath());
						seg.setWordList(contentList);
						seg.segment();
						List<String[]> my_target_add = seg.getSegList();
						// 对比后得到结果
						double result = ListCompareUtils.compareList(ListCompareUtils.changeList(my_target_add),
								target_add);
						System.out.println("附加结果文件相似度：" + result);
						TxtReader.writeToTxt("附加结果文件相似度：" + result+"\r\n",resultPath);
						if (result > 0.9) {
							ansjResult += 10;
						}
					}
					System.out.println("ansj初始成绩："+ansjResult);
					// 获取停用词表和用户自定义词典
					File userwords = new File(taskPath + "ansj"+File.separator+"userwords.txt");
					File stopwords = new File(taskPath + "ansj"+File.separator+"stopwords.txt");
					if (userwords.exists()) {
						// 设置自定义文件目录
						// com.hust.utils.Config.userWordsPath = taskPath +
						// "ansj\\userwords.txt";
					} else {
						// 自定义不存在扣分
						ansjResult -= 5;
						System.out.println(taskPath + "ansj该目录下自定义词典不存在！");
						TxtReader.writeToTxt(taskPath + "ansj该目录下自定义词典不存在！\r\n",resultPath);
					}
					if (stopwords.exists()) {
						// 设置停用词目录
						// com.hust.utils.Config.stopWordPath = taskPath +
						// "ansj\\stopwords.txt";
					} else {
						ansjResult -= 5;
						System.out.println(taskPath + "ansj该目录下停用词词典不存在！");
						TxtReader.writeToTxt(taskPath + "ansj该目录下停用词词典不存在！\r\n",resultPath);
					}

					// 判断学生提交的作业结果文件是否存在，存在获取结果集合用list存储
					File ansj_target = new File(taskPath + "ansj"+File.separator+"ansj_target.xls");
					if (!ansj_target.exists()) {
						ansj_target = new File(taskPath + "ansj"+File.separator+"ansj_target.xlsx");
					}
					if (ansj_target.exists()) {
						resultContent = ExcelReader.read(ansj_target.getAbsolutePath(), 0);
						// 根据学生提供的字典得到的结果集
						AnsjSegmentation seg = new AnsjSegmentation(taskPath + "ansj"+File.separator+"stopwords.txt",
								taskPath + "ansj"+File.separator+"userwords.txt");
						seg.setWordList(contentList);
						seg.segment();
						stuResultContent = seg.getSegList();
						double preResult = ListCompareUtils.compareList(ListCompareUtils.changeList(stuResultContent),
								resultContent);
						System.out.println("学生文件对比相似度：" + preResult);
						TxtReader.writeToTxt("学生文件对比相似度：" + preResult+"\r\n",resultPath);
						if (preResult < 0.9) {
							ansjResult -= 10;
							System.out.println("ansj最终成绩：" + ansjResult);
							TxtReader.writeToTxt("ansj最终成绩：" + ansjResult+"\r\n",resultPath);
							// return;
						}
						double result = ListCompareUtils.compareListByword(myResultContent, stuResultContent);
						System.out.println("词语相对多少比较:" + result);
						TxtReader.writeToTxt("词语相对多少比较:" + result+"\r\n",resultPath);
						if (result > 0.05)
							ansjResult += 5;
					} else {
						ansjResult -= 5;
						System.out.println(taskPath + "ansj该目录下没有结果文件！");
						TxtReader.writeToTxt(taskPath + "ansj该目录下没有结果文件！\r\n",resultPath);
						return;
					}
				} else {
					ansjResult = 60;
					return;
				}
			} else {
				System.out.println("解压文件失败");
			}
		} else {
			ansjResult = 60;
			System.out.println("该文件不存在");
		}
		System.out.println("ansj最终成绩：" + ansjResult);
		TxtReader.writeToTxt("ansj最终成绩：" + ansjResult+"\r\n",resultPath);
	}

	// 自动判断TfiDf小实验成绩
	private void checkTfidf() {
		File resultZip = new File(taskPath + "tfidf.zip");
		//存储学生上交的结果
		List<String> stuResult = new ArrayList<String>();
		//获取分词工具
		AnsjSegmentation seg = new AnsjSegmentation(taskPath + "ansj"+File.separator+"stopwords.txt",
				taskPath + "ansj"+File.separator+"userwords.txt");
		if (resultZip.exists()) {
			try {
				zipUtils.unzip(resultZip, taskPath + "tfidf"+File.separator+"");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			File dic = new File(taskPath + "tfidf"+File.separator+"");
			if (dic.isDirectory()) {
				// 判断结果文件存在与否
				File tfidf_target = new File(taskPath + "tfidf"+File.separator+"tfidf_target.txt");
				if (!tfidf_target.exists()) {
					tfidf_target = new File(taskPath + "tfidf"+File.separator+"tfidf_target.xls");							
					if (!tfidf_target.exists())
						tfidf_target = new File(taskPath + "tfidf"+File.separator+"tfidf_target.xlsx");
					else{
						stuResult = ExcelReader.read(taskPath + "tfidf"+File.separator+"tfidf_target.xls", 0);
					}
					if (!tfidf_target.exists())
						tfidfResult -= 5;
					else{
						stuResult = ExcelReader.read(taskPath + "tfidf"+File.separator+"tfidf_target.xlsx", 0);
					}
				} else {
					// txt
					stuResult = TxtReader.getDataFromTxt(taskPath + "tfidf"+File.separator+"tfidf_target.txt");
				}
				// 判断提交的作业里面是否包含java文件，少于2个扣5分
				String[] javaFiles = dic.list(new FileFilter("java"));
				if (javaFiles.length < 2) {
					tfidfResult -= 5;
					System.out.println(taskPath + "tfidf该目录下java文件不全！");
					TxtReader.writeToTxt(taskPath + "tfidf该目录下java文件不全！\r\n",resultPath);
				}
				
				File tfidf_source = new File(taskPath + "tfidf"+File.separator+"tfidf_source.xlsx");
				if(!tfidf_source.exists()){
					tfidf_source = new File(taskPath + "tfidf"+File.separator+"tfidf_source.xls");
				}else{
					contentList = ExcelReader.read(taskPath + "tfidf"+File.separator+"tfidf_source.xlsx", 0);
				}
				if(!tfidf_source.exists()){
					
				}else{
					contentList = ExcelReader.read(taskPath + "tfidf"+File.separator+"tfidf_source.xls", 0);
				}
				seg.setWordList(contentList);
				seg.segment();
				stuResultContent = seg.getSegList();
				TFIDFConvertor convertor = new TFIDFConvertor(stuResultContent);
				myResult = convertor.getVector();
				System.out.println("double[]:"+myResult.get(25).length);
				
				List<String> testResult = double2String(myResult, stuResultContent);
				System.out.println(stuResult.get(1).charAt(stuResult.get(1).length()-1)+"stuResult:"+stuResult.size());
				System.out.println(testResult.get(1).charAt(testResult.get(1).length()-1)+"testResult:"+testResult.size());
				double distance = ListCompareUtils.compareList(testResult, stuResult);
				System.out.println("tfidf结果文件相似度："+distance);
				TxtReader.writeToTxt("tfidf结果文件相似度："+distance+"\r\n",resultPath);
				tfidfResult += 10+10*distance;
				
			} else {
				System.out.println("解压文件失败");
			}
		} else {
			tfidfResult = 60;
			System.out.println("该文件不存在");
		}
		System.out.println("tfidf最终成绩：" + tfidfResult);
		TxtReader.writeToTxt("tfidf最终成绩：" + tfidfResult+"\r\n",resultPath);
	}

	// 自动判断相似度计算小实验成绩

	private void checkDistance() {
		File resultZip = new File(taskPath + "distance.zip");
		if (resultZip.exists()) {
			try {
				zipUtils.unzip(resultZip, taskPath + "distance"+File.separator);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			File dic = new File(taskPath + "distance"+File.separator);
			if (dic.isDirectory()) {
				distanceResult = tfidfResult;
				// 判断结果文件存在与否
				File distance_target = new File(taskPath + "distance"+File.separator+"distance_target.txt");
				if (!distance_target.exists())
					distance_target = new File(taskPath + "distance"+File.separator+"distance_target.xls");
				if (!distance_target.exists())
					distance_target = new File(taskPath + "distance"+File.separator+"distance_target.xlsx");
				if (!distance_target.exists())
					distanceResult -= 5;
				// 判断提交的作业里面是否包含java文件，少于2个扣5分
				String[] javaFiles = dic.list(new FileFilter("java"));
				if (javaFiles.length < 2) {
					distanceResult -= 5;
					System.out.println(taskPath + "distance该目录下java文件不全！");
					TxtReader.writeToTxt(taskPath + "distance该目录下java文件不全！\r\n",resultPath);
				}

			} else {
				System.out.println("解压文件失败");
			}
		} else {
			distanceResult = 60;
			System.out.println("该文件不存在");
		}
		System.out.println("distance最终成绩：" + distanceResult);
		TxtReader.writeToTxt("distance最终成绩：" + distanceResult+"\r\n",resultPath);
	}

	/**
	 * 根据作业和学生信息给学生自动评分
	 * 
	 * @param taskId
	 *            作业id
	 * @param stuId
	 *            学生id
	 */
	public int getTotalResult(Integer taskId, String stuId) {
		taskPath = taskPath + taskId + File.separator + stuId + File.separator;
		resultPath = taskPath+"result.txt";
		checkAnsj();
		checkTfidf();
		checkDistance();
		return (int) (ansjResult * 0.4 + tfidfResult * 0.3 + distanceResult * 0.3) + new Random().nextInt(4);
	}

	/*
	 * private File isExists(File[] fls,String fileName){
	 * 
	 * }
	 */

	class FileFilter implements FilenameFilter { // 实现FilenameFilter接口
		private String fileExtension;

		public FileFilter(String fileExtension) {
			this.fileExtension = fileExtension;
		}

		public boolean accept(File dir, String name) {
			return name.endsWith("." + fileExtension);
		}
	}
	
	
	private List<String> double2String(List<double[]>vector,List<String[]> list){
		List<String> result = new ArrayList<String>();
	//	TxtReader reader = new TxtReader();
		int i = 0;
		for (double[] ds : vector) {
			//		String text = "";
			//		String vectorStr = "[";
					//用StringBuilder来存储字符串，这才操作字符串拼接 效率更高
					StringBuilder vectorStr = new StringBuilder("[");
					StringBuilder text = new StringBuilder();
					for (String str : list.get(i++)) {
						text.append(str+",");
					}
					//如果分词后的文本不为空 将最后一个‘，’去掉
					if(text.length()!=0)
						text = text.deleteCharAt(text.length()-1);
					result.add(text.toString());
			//		reader.writeToTxt(text.toString()+"\r\n", "E:/cc.txt");
					for (double d : ds) {
						vectorStr.append( d+",");
					}
					vectorStr=vectorStr.deleteCharAt(vectorStr.length()-1);
					vectorStr.append("]");
					result.add(vectorStr.toString());	
			//		reader.writeToTxt(vectorStr.toString()+"\r\n", "E:/cc.txt");
				}
		return result;
	}
}
