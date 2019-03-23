package com.julius.jobmanagementsystem.controller;

import com.julius.jobmanagementsystem.domain.entity.Student;
import com.julius.jobmanagementsystem.service.StudentService;
import com.julius.jobmanagementsystem.utils.Config;
import com.julius.jobmanagementsystem.utils.UploadUtils;
import com.julius.jobmanagementsystem.utils.readExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class importStudent {

	@Autowired
	private StudentService studentservice;
	private StudentService taskservice;
	
	@RequestMapping(value="/importStudent",method = RequestMethod.POST)
	public String importStudent(@RequestParam(value = "uploadfile", required = false) MultipartFile[] uploadfile)
	{
		System.out.println("***********");
		String road= Config.title;
		UploadUtils up = new UploadUtils();
		readExcel rExcel=new readExcel();
		List<String> filename = new ArrayList<String>();
		filename = up.upload(uploadfile, road);
		for (String string : filename) { //遍历filename中所有的文件，内容存到result中
			List<Map<String, String>> result = new ArrayList<Map<String,String>>();//对应excel文件
			try {
				String s = road+"/"+string;
			
				result = rExcel.readStudent(road+string);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for (Map<String, String> map : result) 
			{
				
				Student student = new Student();
				student.setStuId(map.get("学号"));
				student.setStuPwd(map.get("学号"));
				student.setStuName(map.get("姓名"));
				try {
					studentservice.addStu(student);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			}
			
		}
		return "redirect:/managejob";
		
	}
	

}
