package com.julius.jobmanagementsystem.controller;

import com.alibaba.fastjson.JSONObject;
import com.julius.jobmanagementsystem.domain.entity.Task;
import com.julius.jobmanagementsystem.service.ResultService;
import com.julius.jobmanagementsystem.service.StudentService;
import com.julius.jobmanagementsystem.service.TaskService;
import com.julius.jobmanagementsystem.utils.Common;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;


/**
 * @author julius
 * @create 2019-03-23 10:35
 * 处理学生请求控制层
 */
@Controller
public class StudentController {
    private ResultService resultService;
    private TaskService taskService;
    private final Logger LOGGER = LogManager.getLogger(getClass());
    private StudentService studentService;

    public StudentController() {
    }

    @Autowired
    public StudentController(final ResultService resultService, final TaskService taskService,
                             final StudentService studentService) {
        this.resultService = resultService;
        this.taskService = taskService;
        this.studentService = studentService;
    }

    /**
     * 学生上交作业
     *
     * @param request    请求对象
     * @param response   响应对象
     * @param taskId     作业id
     * @param uploadFile 文件流对象
     * @return 重定向到之前页面
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(final HttpServletRequest request,
                         final HttpServletResponse response,
                         final @RequestParam(value = "taskid") int taskId,
                         final @RequestParam(value = "uploadfile", required = false)
                                 MultipartFile[] uploadFile) {
        String currentID = (String) request.getSession().getAttribute("id");
        Task task = new Task();
        task.setTaskId(taskId);
        Boolean result = Common.saveUpLoadFiles(uploadFile, task, currentID);
        if (!result) {
            String message = "<html><body><h2>Sorry!出错啦!</h2></body></html>";
            Common.commonShow(null, response, message);
            return "";
        }
        return "redirect:/joblist";  //返回作业列表
    }

    /**
     * 检查是否超过上交时间
     *
     * @param json     taskId
     * @param response 响应对象
     */
    @RequestMapping(value = "/checkTime", method = RequestMethod.POST)
    public void checkTime(final @RequestBody JSONObject json,
                          final HttpServletResponse response) {
        Integer taskId = json.getInteger("taskId");
        Integer flag = Common.checkSubmitDate(taskId);
        JSONObject reJson = new JSONObject();
        reJson.put("flag", flag);
        try {
            response.getWriter().print(reJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/appLogin")
    @ResponseBody
    public String appLogin(final @RequestParam(value = "studentId", required = true) String studentId,
                           final @RequestParam(value = "password", required = true) String password) {
        String loginResult = "成功";
        int result = studentService.login(studentId, password);
        //成功返回1，否则返回0,id不存在返回-1
        if (result == 0) {
            loginResult = "登录失败,请检查账号或者密码";
        } else if (result == -1) {
            loginResult = "账号不存在";
        }
        return loginResult;
    }

    /**
     * 处理APP端作业上传
     *
     * @param taskId     作业id
     * @param studentId  学生id
     * @param uploadFile 上传文件对象
     * @return 上传结果
     */
    @ResponseBody
    @RequestMapping(value = "/appUpload", method = RequestMethod.POST)
    public String appUploadTask(final @RequestParam("taskId") Integer taskId,
                                final @RequestParam("studentId") String studentId,
                                final @RequestParam(value = "uploadFile", required = false)
                                        MultipartFile[] uploadFile) {
        LOGGER.debug("submit  task");
        String result = "提交成功";
        //检查是否已经超过提交时间限制
        Integer flag = Common.checkSubmitDate(taskId);
        if (flag == 1) {
            result = "已经超过提交时间,请联系负责老师!";
        } else if (flag == -1) {
            result = "服务器出错";
        } else {
            //保存文件的提交
            Task task = new Task();
            task.setTaskId(taskId);
            Boolean really = Common.saveUpLoadFiles(uploadFile, task, studentId);
            if (!really) {
                return "提交失败,请稍后再试!";
            }
        }
        return result;
    }

    /**
     * 检查作业是否已经提交
     *
     * @param taskId    作业id
     * @param studentId 学生id
     * @return 提交/""
     */
    @RequestMapping(value = "/taskIsSubmit",
            method = RequestMethod.POST)
    @ResponseBody
    public String appTaskIsSubmit(Integer taskId, Integer studentId) {
        String result = "";
        if (resultService.findTaskIsSubmit(studentId, taskId) > 0) {
            result = "作业已经提交,本次提交将会覆盖之前作业,确认是否提交?";
        }
        return result;
    }

    /**
     * 根据学生id获取学生的课程信息
     *
     * @param studentId 学生id
     * @return 课程信息对象集合
     */
    @RequestMapping(value = "/getTasks")
    @ResponseBody
    public List<Task> appGetTasks(Integer studentId) {
        List<Task> tasks = taskService.finTaskByStudentId(studentId);
        return tasks.size() > 0 ? tasks : Collections.emptyList();
    }
}
