package com.julius.jobmanagementsystem.controller;

import com.julius.jobmanagementsystem.domain.entity.Result;
import com.julius.jobmanagementsystem.domain.entity.Task;
import com.julius.jobmanagementsystem.service.ResultService;
import com.julius.jobmanagementsystem.service.TaskService;import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TumController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private TaskService taskService;
    @Autowired
    private ResultService resultService;

    @RequestMapping("/addjob")
    public String tumAddjob() {
        return "/addjob";
    }

    @RequestMapping("/addStudent")
    public String tumAddStudent() {
        return "/addStudent";
    }

    @RequestMapping("/exportResult")
    public String tumExportResult(Model model) {
        List<Task> taskList = new ArrayList<Task>();
        try {
            taskList = taskService.findAllTasks();
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("taskList", taskList);
        return "/exportResult";
    }

    @RequestMapping("/joblist")
    public String tumJoblist(HttpServletRequest request, Model model) {
        String stuName = (String) request.getSession().getAttribute("stuName");
        String stuId = "";
        //验证当前用户是否还在线
        if (stuName == null)
            return "/joblist";
        else
            stuId = (String) request.getSession().getAttribute("id");
        List<Task> taskList = new ArrayList<Task>();
        List<Result> resultList = new ArrayList<Result>();
        try {
            taskList = taskService.findAllTasks();
            for (Task task : taskList) {
                Result result = new Result();
                result = resultService.findResult(stuId, task.getTaskId());
                resultList.add(result);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        model.addAttribute("taskList", taskList);
        model.addAttribute("resultList", resultList);
        return "/joblist";
    }

    @RequestMapping("/managejob")
    public String tumManagejob(Model model) {
        List<Task> taskList = new ArrayList<Task>();
        try {
            taskList = taskService.findAllTasks();
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("taskList", taskList);
        return "/managejob";
    }

    @RequestMapping(value = "/pass")
    public String pass() {
        return "/pass";
    }

    @RequestMapping("/personResult")
    public String tumpersonResult(HttpServletRequest request, Model model) {
        String stuName = (String) request.getSession().getAttribute("stuName");
        String stuId = "";
        //验证当前用户是否还在线
        if (stuName == null) {
            return "/personResult";
        } else {
            stuId = (String) request.getSession().getAttribute("id");
        }
        List<Task> taskList = new ArrayList<Task>();
        List<Result> resultList = new ArrayList<Result>();
        //封装成一个个集合对象
        try {
            taskList = taskService.findAllTasks();
            for (Task task : taskList) {
                Result result = new Result();
                result = resultService.findResult(stuId, task.getTaskId());
                resultList.add(result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //添加到session进行返回
        model.addAttribute("taskList", taskList);
        model.addAttribute("resultList", resultList);
        logger.debug("list:{}", taskList.get(0).getTaskName());
        //页面跳转
        return "/personResult";
    }

    @RequestMapping("/queryResult")
    public String tumQueryResult(Model model) {
        List<Task> list = new ArrayList<Task>();
        try {
            list = taskService.findAllTasks();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //session返回
        model.addAttribute("taskList", list);
        return "/queryResult";
    }

    @RequestMapping("/updatejob")
    public String tumUpdateTask(HttpServletRequest request, Model model) {
        String taskId = (String) request.getParameter("taskId");
        String taskName = (String) request.getParameter("taskName");
        String taskExpiry = (String) request.getParameter("taskExpiry");
        model.addAttribute("taskId", taskId);
        model.addAttribute("taskName", taskName);
        model.addAttribute("taskExpiry", taskExpiry);
        return "/updatejob";
    }
}
