package com.julius.jobmanagementsystem.controller;

import com.julius.jobmanagementsystem.domain.entity.Student;
import com.julius.jobmanagementsystem.domain.entity.Teacher;
import com.julius.jobmanagementsystem.service.StudentService;
import com.julius.jobmanagementsystem.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;

/**
 * 修改密码Controller。包括修改学生密码、老师密码。
 * @author Chan
 *
 */
@Controller
public class PasswordController {

	@Autowired
	StudentService studentService;
	@Autowired
	TeacherService teacherService;
	
	@RequestMapping(value = "/modifyStuPassword", method = RequestMethod.POST)
	public String modifyStuPassword(HttpServletRequest request){
		String currentID=(String) request.getSession().getAttribute("id");
		try {
			Student stu = studentService.findStudentInfoByStuId(currentID);
			if(!stu.getStuPwd().equals(request.getParameter("oldPwd"))){
				return "/modifyPwdFailed";
			}
			studentService.updatePasswordByStuId(currentID, request.getParameter("newPwd"));
			
		} catch (Exception e) {
			e.printStackTrace();
			return "/modifyPwdFailed";
		}
		return "/modifyPwdSuccess";
	}
	
	@RequestMapping(value = "/modifyTeaPassword", method = RequestMethod.POST)
	public String modifyTeaPassword(HttpServletRequest request){
		String currentID=(String) request.getSession().getAttribute("id");
		try {
			Teacher tea = teacherService.findTeacherByTeaId(currentID);
			if(!tea.getTeaPwd().equals(request.getParameter("oldPwd"))){
				return "/modifyPwdFailed";
			}
			teacherService.updatePasswordByTeaId(currentID, request.getParameter("newPwd"));
			
		} catch (Exception e) {
			e.printStackTrace();
			return "/modifyPwdFailed";
		}
		return "/modifyPwdSuccess";
	}
}
