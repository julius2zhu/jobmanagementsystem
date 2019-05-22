package com.julius.jobmanagementsystem.controller;

import com.alibaba.fastjson.JSONObject;
import com.julius.jobmanagementsystem.domain.entity.Student;
import com.julius.jobmanagementsystem.domain.entity.Teacher;
import com.julius.jobmanagementsystem.service.StudentService;
import com.julius.jobmanagementsystem.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoginController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    /**
     * 根据学生id和学生密码检查学生登录
     *
     * @param stu     学生信息实体
     * @param request 响应对象
     * @return 学生对应的物理视图
     */
    @RequestMapping(value = "/student", method = RequestMethod.POST)
    public String studentLogin(Student stu, HttpServletRequest request) {
        Student student = new Student();
        request.getAttribute("id");
        try {
            student = studentService.findStudentInfoByStuId(stu.getStuId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getSession().setAttribute("studentId", student.getStuId());
        request.getSession().setAttribute("stuName", student.getStuName());
        request.getSession().removeAttribute("teaName");
        return "/student";
    }

    @RequestMapping(value = "/student", method = RequestMethod.GET)
    public String studentLoginGet(HttpServletRequest request) {
        String name = (String) request.getSession().getAttribute("stuName");
        if (name != null)
            return "/student";
        else
            return "redirect:/";
    }

    @RequestMapping(value = "/teacher", method = RequestMethod.POST)
    public String teacherLogin(Student stu, HttpServletRequest request) {
        Teacher teacher = new Teacher();
        try {
            teacher = teacherService.findTeacherByTeaId(stu.getStuId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getSession().setAttribute("id", teacher.getTeaId());
        request.getSession().setAttribute("teaName", teacher.getTeaName());
        request.getSession().removeAttribute("stuName");
        return "/teacher";
    }

    @RequestMapping(value = "/teacher", method = RequestMethod.GET)
    public String teacherLoginGet(HttpServletRequest request) {
        String name = (String) request.getSession().getAttribute("teaName");
        if (name != null)
            return "/teacher";
        else
            return "redirect:/";
    }

    @RequestMapping(value = "/checkpwd", method = RequestMethod.POST, params = "type=student")
    public void checkStuPwd(@RequestBody JSONObject json,
                            HttpServletRequest request, HttpServletResponse response) {
        JSONObject reJson = new JSONObject();
        Integer id = Integer.valueOf(json.getString("id"));
        String pwd = json.getString("pwd");
        int flag = studentService.login(id, pwd);
        try {
            reJson.put("flag", flag);
            response.getWriter().print(reJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/checkpwd", method = RequestMethod.POST, params = "type=teacher")
    public void checkTeaPwd(@RequestBody JSONObject json, HttpServletResponse response) {
        JSONObject reJson = new JSONObject();
        Integer id = Integer.valueOf(json.getString("id"));
        String pwd = json.getString("pwd");
        int flag = teacherService.login(id, pwd);
        System.out.println("flag" + flag);
        try {
            reJson.put("flag", flag);
            response.getWriter().print(reJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("name");
        //若去除id login界面就帐号栏就没有当前用户帐号
        //	request.getSession().removeAttribute("id");
        return "redirect:/";
    }
}
