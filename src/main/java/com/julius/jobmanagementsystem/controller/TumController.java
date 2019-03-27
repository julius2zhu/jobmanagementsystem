package com.julius.jobmanagementsystem.controller;

import com.julius.jobmanagementsystem.domain.entity.Result;
import com.julius.jobmanagementsystem.domain.entity.Task;
import com.julius.jobmanagementsystem.service.ResultService;
import com.julius.jobmanagementsystem.service.TaskService;
import org.slf4j.Logger;
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
        if (stuName == null) {
            return "/joblist";
        } else {
            stuId = (String) request.getSession().getAttribute("id");
        }
        List<Task> taskList = taskService.findAllTasks();
        List<Result> resultList = getResultList(taskList, stuId);
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
        //封装成一个个集合对象
        List<Task> taskList = taskService.findAllTasks();
        List<Result> resultList = getResultList(taskList, stuId);
        if (taskList != null && taskList.size() > 0) {
            //添加到session进行返回
            model.addAttribute("taskList", taskList);
            model.addAttribute("resultList", resultList);
        }
        //页面跳转
        return "/personResult";
    }

    /**
     * 查询作业信息
     *
     * @param model 模型共享数据
     * @return 跳转页面
     */
    @RequestMapping("/queryResult")
    public String tumQueryResult(Model model) {
        List<Task> list = new ArrayList<>();
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

    /**
     * 遍历tasks集合返回result实体对象集合
     *
     * @param tasks     被遍历的task集合对象
     * @param studentId 学生id
     * @return result实体对象集合
     */
    private List<Result> getResultList(List<Task> tasks, String studentId) {
        List<Result> results = new ArrayList<>();
        for (Task task : tasks) {
            Result result = new Result();
            result = resultService.findResult(studentId, task.getTaskId());
            results.add(result);
        }
        return results;
    }
}
