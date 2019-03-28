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

	
	//返回成绩集合
	public List<Result> getResults(){
		return results;
	}
}
