package com.julius.jobmanagementsystem.automaticrating;

import com.julius.jobmanagementsystem.domain.entity.Result;
import com.julius.jobmanagementsystem.service.ResultService;
import com.julius.jobmanagementsystem.service.TaskService;
import com.julius.jobmanagementsystem.utils.Config;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用方法--用作业名去new对象，然后获取该作业的deadline,超过deadline的时间后，手动调用startRating开始评分
 * @author Jack
 *
 */
public class Rating {
	//存放作业名
	private String taskName;
	//存放作业id
	private Integer taskId;
	//存放学生学号
	private List<String> fileList;
	
	private List<Result> results;
	//TaskService
	@Autowired
	private TaskService taskService;
	//ResultService
	@Autowired
	private ResultService resultService;
	
	public Rating(String taskName,Integer taskId){
		this.taskName = taskName;		
		this.taskId = taskId;
		fileList = new ArrayList<String>();
		results = new ArrayList<Result>();
	}
	
	private void init(){
		String path = Config.task+taskId;
		File taskFiles = new File(path);
		if(taskFiles.isDirectory()){
			File[] files = taskFiles.listFiles();
			System.out.println("files长度:"+files.length);
			for (File file : files) {
				System.out.println(file.getName());
				if(fileList==null)
					System.out.println("1111");;
				fileList.add(file.getName());
			}
		}else{
			System.out.println(path+"目录下没有作业存在！");
		}
	}
	
//	public Date getDeadline(){
//		Task task = new Task();
//		try {
//			System.out.println("11111"+taskService);
//			task = taskService.findTaskByTaskName(taskName);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
//		return task.getTaskExpiry();
//	}
	/*
	 * 已知作业id--taskId,
	 * 学生学号集合--fileList(存放着该作业下所有以提交作业的学生的学号)
	 */
	public void startRating(){
		init();
		
		switch (taskName) {
		case "爬虫":	
		{
			GetResultOfAllStudent task1 = new GetResultOfAllStudent();
			String filepath = Config.task+taskId;
			task1.getFinalResult(filepath);
			results = task1.getResults();
		}
			break;
		case "数据预处理":	
		{
			for (String string : fileList) {
				try{
				GetResultOfTask2 task2 = new GetResultOfTask2();
				int result = task2.getTotalResult(taskId, string);
				Result rs = new Result();
				rs.setTaskId(taskId);
				rs.setStuId(string);
				rs.setScore(result);
				results.add(rs);
				/*try {
					resultService.updateResult(rs);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("评分失败！");
					e.printStackTrace();
				}*/}catch( Exception e){
					System.out.println(e);continue;
				}
			}
		}
			break;
		case "聚类":	
		{
			//实验三自动评分
			for (String string : fileList) {
				try{
				GetResultOfTask3 task3 = new GetResultOfTask3(string,taskId);
				int result = task3.getScore();
				Result rs = new Result();
				rs.setTaskId(taskId);
				rs.setStuId(string);
				rs.setScore(result);
				results.add(rs);
				/*try {
					resultService.updateResult(rs);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("评分失败！");
					e.printStackTrace();
				}*/}catch( Exception e){
					System.out.println(e);continue;
				}
			}
		}
			break;
		case "朴素贝叶斯分类":	
		{
			//实验四自动评分
			for (String string : fileList) {
				try{
				GetResultOfTask4 task4 = new GetResultOfTask4();
				int result = task4.getTotalResult(taskId,string);
				Result rs = new Result();
				rs.setTaskId(taskId);
				rs.setStuId(string);
				rs.setScore(result);
				results.add(rs);
				/*try {
					resultService.updateResult(rs);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("评分失败！");
					e.printStackTrace();
				}*/}catch( Exception e){
					System.out.println(e);continue;
				}
			}
		}
			break;
		default:
			System.out.println("自动评分失败");
			break;
		}
		
	}
	
	//返回成绩集合
	public List<Result> getResults(){
		return results;
	}
}
