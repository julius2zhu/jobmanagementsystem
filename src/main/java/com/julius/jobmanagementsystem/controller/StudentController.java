package com.julius.jobmanagementsystem.controller;

import com.alibaba.fastjson.JSONObject;
import com.julius.jobmanagementsystem.domain.entity.Result;
import com.julius.jobmanagementsystem.service.ResultService;
import com.julius.jobmanagementsystem.service.TaskService;
import com.julius.jobmanagementsystem.utils.Config;
import com.julius.jobmanagementsystem.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Controller
public class StudentController {
	
	@Autowired
	private ResultService resultService;
	@Autowired
	private TaskService taskService;
	
	//学生上交作业
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(HttpServletRequest request,
			@RequestParam(value="taskid") int taskid,
			@RequestParam(value = "uploadfile", required = false) MultipartFile[] uploadfile)
	{
		String currentID=(String) request.getSession().getAttribute("id");
		String road= Config.task+taskid+"/"+currentID;
		UploadUtils up = new UploadUtils();
		if(up.uploadUtils(uploadfile, road))
		{
			//根据作业ID，和用户ID。修改result表中的submit状态
			Result result = new Result();
			result.setStuId(currentID);
			result.setTaskId(taskid);
			result.setSubmit(true);
			try {
				resultService.updateResult(result);
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		return"redirect:/joblist";  //返回作业列表
		
	}
	@RequestMapping(value = "/checkTime",method = RequestMethod.POST)
	public void checkTime(@RequestBody JSONObject json,HttpServletResponse response){
		int flag = -1;
		Long curTime = System.currentTimeMillis();
		Long deadLine = 0l;
		Integer taskId = json.getInteger("taskId");
		try {
			System.out.println("taskid:"+taskId);
			Date expiry = taskService.findTaskByTaskId(taskId).getTaskExpiry();
			deadLine = expiry.getTime();
		} catch (Exception e) {
			flag = -1;
			e.printStackTrace();
		}
		System.out.println("curTime:"+curTime);
		System.out.println("deadLine:"+deadLine);
		if(curTime > deadLine){
			flag = 1;
		}else{
			flag = 0;
		}
		JSONObject reJson = new JSONObject();
		reJson.put("flag", flag);
		try {
			response.getWriter().print(reJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
