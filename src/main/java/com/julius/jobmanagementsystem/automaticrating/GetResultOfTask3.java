package com.julius.jobmanagementsystem.automaticrating;

import com.julius.jobmanagementsystem.automaticrating.algorithm.canopy.Canopy;
import com.julius.jobmanagementsystem.automaticrating.algorithm.kmeans.KMeans;
import com.julius.jobmanagementsystem.automaticrating.convertor.TFIDFConvertor;
import com.julius.jobmanagementsystem.automaticrating.distance.CosDistance;
import com.julius.jobmanagementsystem.automaticrating.utils.ClusterUtil;
import com.julius.jobmanagementsystem.automaticrating.utils.ExcelReader;
import com.julius.jobmanagementsystem.automaticrating.utils.VectorUtil;
import com.julius.jobmanagementsystem.automaticrating.utils.zipUtils;
import com.julius.jobmanagementsystem.utils.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 获取聚类实验学生提交的文件，
 * 计算得出学生的成绩
 * 并将成绩写入数据库
 * @author tankai
 *
 */
public class GetResultOfTask3 {
	//学生ID
	private String stuId;
	//作业ID
	private Integer taskId;
	//文件完整性基础得分
	private Integer basicScore = 60;
	//Canopy聚类分数
	private Integer canopyScore = 0;
	//KMeans聚类分数
	private Integer kmeansScore = 0;
	//学生作业存放路径
	private String basePath;
	//Canopy核心类
	private Canopy canopy;
	//KMeans核心类
	private KMeans kmeans ;
	
	//学生聚类结果的DB值
	private double stuDB = 0f;
	//助教KMeans聚类结果计算出来的参考DB值
	private double stdDB = 0f;
	
	public GetResultOfTask3(String stuId, Integer taskId){
		this.stuId = stuId;
		this.taskId = taskId;
		this.basePath = Config.task + taskId + File.separator + stuId;
	}
	public int getScore(){
		getBasicScore();
		System.out.println("最后分数："+(basicScore + canopyScore + kmeansScore));
		return basicScore + canopyScore + kmeansScore;
	}
	
	private void getBasicScore(){
		File dataExcel = new File(basePath + File.separator + "原始数据.xls");
		File dataExcel1 = new File(basePath + File.separator + "原始数据.xlsx");
		File demoZip = new File(basePath + File.separator + "bigdata.zip");
		File canopyresultZip = new File(basePath + File.separator + "canopyresult.zip");
		File kmeansresultZip = new File(basePath + File.separator + "kmeansresult.zip");
		//原始数据文件是否上传
		if(dataExcel.exists()){
			List<String> dataList = ExcelReader.read(basePath + File.separator + "原始数据.xls",0);
			System.out.println(dataList.size());
//			ClusterUtil.showDatalist(dataList);
			//分词
			AnsjSegmentation ansj = new AnsjSegmentation();
			ansj.setWordList(dataList);
			ansj.segment();
			
			//得到分词后的List集合
			List<String[]> seglist = ansj.getSegList(); 
//			ClusterUtil.showSeglist(seglist);
			
			//向量转换
			TFIDFConvertor convertor = new TFIDFConvertor(seglist);
			List<double[]> vectors = convertor.getVector();
			
			canopy = new Canopy();
			canopy.setVectors(vectors);
			//进行Canopy聚类
			canopy.cluster();
			
			//初始化KMeans聚类参数 （K值--Canopy聚类的个数，向量集合，迭代次数）
			kmeans = new KMeans(10, vectors, 20);
			//进行KMeans聚类
			kmeans.cluster();
			//计算参考DB值
			stdDB = calculateDB(ClusterUtil.getClusters(kmeans.getResultIndex(), dataList));
			System.out.println("stdDB: "+stdDB);
		}else if(dataExcel1.exists()){
			List<String> dataList = ExcelReader.read(basePath + File.separator + "原始数据.xlsx",0);
			System.out.println(dataList.size());
//			ClusterUtil.showDatalist(dataList);
			//分词
			AnsjSegmentation ansj = new AnsjSegmentation();
			ansj.setWordList(dataList);
			ansj.segment();
			
			//得到分词后的List集合
			List<String[]> seglist = ansj.getSegList(); 
//			ClusterUtil.showSeglist(seglist);
			
			//向量转换
			TFIDFConvertor convertor = new TFIDFConvertor(seglist);
			List<double[]> vectors = convertor.getVector();
			
			canopy = new Canopy();
			canopy.setVectors(vectors);
			//进行Canopy聚类
			canopy.cluster();
			
			//初始化KMeans聚类参数 （K值--Canopy聚类的个数，向量集合，迭代次数）
			kmeans = new KMeans(10, vectors, 20);
			//进行KMeans聚类
			kmeans.cluster();
			//计算参考DB值
			stdDB = calculateDB(ClusterUtil.getClusters(kmeans.getResultIndex(), dataList));
			System.out.println("stdDB: "+stdDB);
		}else{
			basicScore--;
			System.out.println("缺少原始数据文件，扣1分");
		}
		if(!demoZip.exists()){
			basicScore--;
			System.out.println("缺少源代码文件，扣1分");
		}
		if(canopyresultZip.exists()){
			try {
				zipUtils.unzip(canopyresultZip,basePath+ File.separator+ "canopyresult");// + "\\" + "canopyresult"
				getCanopyScore(basePath + File.separator+ "canopyresult" );//
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			basicScore--;
			System.out.println("缺少Canopy结果文件，扣1分");
		}	
		if(kmeansresultZip.exists()){
			try {
				zipUtils.unzip(kmeansresultZip,basePath+ File.separator+ "kmeansresult");//+ "\\"+ "kmeansresult"
				getKmeansScore(basePath + File.separator + "kmeansresult");//
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			basicScore--;
			System.out.println("缺少kmeans结果文件，扣1分");
		}	
		
	}
	/**
	 * 计算KMeans聚类效果得分
	 * @param path
	 */
	private void getKmeansScore(String path) {
		// 获取结果文件夹路径
		File kDir = new File(path);
		if(kDir != null && kDir.exists() && kDir.isDirectory()){
			File[] fList = kDir.listFiles();
			List<List<String>> clusterList = new ArrayList<>();
			for(File file : fList){
				System.out.println(file.getAbsolutePath());
				List<String> l = ExcelReader.read(file.getAbsolutePath(),0);
				clusterList.add(l);
			}
			//计算学生聚类结果的DB值
			stuDB = calculateDB(clusterList);
			System.out.println("stuDB: "+stuDB);
			Random ran = new Random();
			int v = ran.nextInt(5);
			if(stuDB >= stdDB/1.5){
				kmeansScore += 15 + v;
				System.out.println("KMeans聚类得分，15-20分"+kmeansScore);
			}else if(stuDB >= stdDB/2.5){
				kmeansScore += 10 + v;
				System.out.println("KMeans聚类得分，10-15分"+kmeansScore);
			}else if(stuDB >= stdDB/10){
				kmeansScore += 5 + v;
				System.out.println("KMeans聚类得分，5-10分"+kmeansScore);
			}else{
				kmeansScore += v;
				System.out.println("KMeans聚类得分，0-5分"+kmeansScore);
			}
		}
		
	}
	/**
	 * 计算Canopy聚类文件数得分
	 * @param path
	 */
	private void getCanopyScore(String path) {
		// 获取结果文件夹路径
		File cDir = new File(path);
		if(cDir != null && cDir.exists() && cDir.isDirectory()){
			System.out.println(cDir.getAbsolutePath());
			int x = cDir.listFiles().length;
			Random ran = new Random();
			int v = ran.nextInt(5);
			if(Math.abs(x - canopy.getCanopy()) <= canopy.getCanopy() / 10){
				canopyScore += 15 + v;
				System.out.println("Canopy聚类得分，15-20分"+canopyScore);
			}else if(Math.abs(x - canopy.getCanopy()) <= canopy.getCanopy() / 5){
				canopyScore += 10 + v;
				System.out.println("Canopy聚类得分，10-15分"+canopyScore);
			}else if(Math.abs(x - canopy.getCanopy()) <= canopy.getCanopy() / 2){
				canopyScore += 5 + v;
				System.out.println("Canopy聚类得分，5-10分"+canopyScore);
			}else{
				canopyScore += v;
				System.out.println("Canopy聚类得分，0-5分"+canopyScore);
			}
			System.out.println("x: "+x);
		}
		
		System.out.println("canopy: "+canopy.getCanopy());
	}
	
	/**
	 * 计算聚类结果的DB值
	 * @param dataList 聚类后的文本集合
	 * @return
	 */
	private double calculateDB(List<List<String>> dataList){
		double db = 0f;
		//
		List<List<double[]>> vectors = new ArrayList<>();
		for(List<String> c : dataList){
			AnsjSegmentation ansj = new AnsjSegmentation();
			ansj.setWordList(c);
			ansj.segment();			
			//得到分词后的List集合
			List<String[]> seglist = ansj.getSegList(); 			
			//向量转换
			TFIDFConvertor convertor = new TFIDFConvertor(seglist);
			List<double[]> cvectors = convertor.getVector();
			vectors.add(cvectors);
		}
		
		for(int i = 0 ; i < vectors.size() ; i++){
			double maxdb = 0f;
			CosDistance Ci = new CosDistance(vectors.get(i));
			for(int j = i + 1 ; j < vectors.size() ; j++){
				CosDistance Cj = new CosDistance(vectors.get(j));
				double t = (Ci.getThreshold() + Cj.getThreshold()) / clusterDis(vectors.get(i),vectors.get(j));
				if(t > maxdb){
					maxdb = t;
				}
			}
			db += maxdb;
		}
		if(vectors == null || vectors.size() == 0){
			return 0f;
		}
		db /= vectors.size();
		return db;
	}
	
	private double clusterDis(List<double[]> a , List<double[]> b){
		double[] ai={0f},bi={0f};
		for(double[] d : a){
			ai = VectorUtil.add(ai, d);
		}
		for(double[] d : b){
			bi = VectorUtil.add(bi, d);
		}
		for(double d : ai){
			d /= ai.length;
		}
		for(double d : bi){
			d /= bi.length;
		}
		CosDistance cd = new CosDistance(a);
		return cd.caculate(ai, bi);
	}	
	
}
