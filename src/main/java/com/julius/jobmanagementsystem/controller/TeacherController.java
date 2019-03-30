package com.julius.jobmanagementsystem.controller;

import com.alibaba.fastjson.JSONObject;
import com.julius.jobmanagementsystem.domain.entity.Result;
import com.julius.jobmanagementsystem.domain.entity.Student;
import com.julius.jobmanagementsystem.domain.entity.Task;
import com.julius.jobmanagementsystem.service.ResultService;
import com.julius.jobmanagementsystem.service.StudentAndTeacherRelationService;
import com.julius.jobmanagementsystem.service.StudentService;
import com.julius.jobmanagementsystem.service.TaskService;
import com.julius.jobmanagementsystem.utils.Config;
import com.julius.jobmanagementsystem.utils.FileUtils;
import com.julius.jobmanagementsystem.utils.UploadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class TeacherController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Autowired
    private ResultService resultService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private StudentAndTeacherRelationService studentAndTeacherRelationService;
    @Autowired
    private StudentService studentService;

    /**
     * 教师管理学生信息界面
     *
     * @param request 请求对象
     * @param model   模型共享对象
     * @return 页面
     */
    @RequestMapping("/studentInfoManage")
    public String studentInfoManage(HttpServletRequest request, Model model) {
        Integer teacherId = 0;
        try {
            teacherId = Integer.valueOf(request.getSession().getAttribute("id").toString());
        } catch (Exception e) {
            LOGGER.debug("用户已经离线");
        }
        //获取该教师所管辖的学生
        List<Student> students = studentService.selectStudentInfoByTeacherId(teacherId);
        model.addAttribute("teacherId", teacherId);
        model.addAttribute("students", students);
        return "studentInfoManage";
    }

    /**
     * 根据作业号查询学生作业提交情况
     *
     * @param taskId      作业号
     * @param currentPage 当前页
     * @param model       共享数据模型对象
     * @return 跳转页面
     */
    @RequestMapping("/query")
    public String queryResult(Integer taskId, Integer currentPage, Model model) {
        Integer totalPage = 1;
        Integer pageSize = 10;
        //当前页不能为负数
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        //查询所有作业信息
        List<Task> tasks = taskService.findAllTasks();
        //根据作业id查询作业的分页信息
        List<Result> results = resultService.findResultByTaskId(taskId, currentPage);
        //传递的当前页可以查询到数据
        if (results.size() > 0) {
            currentPage = results.get(0).getCurrentPage();
            totalPage = results.get(0).getTotalPage();
            pageSize = results.get(0).getPageSize();
        } else {
            //当前页没有数据,已经到末页,直接返回上一页去查询
            if (currentPage > 1) {
                currentPage--;
            }
            results = resultService.findResultByTaskId(taskId, currentPage);
            if (results.size() > 0) {
                currentPage = results.get(0).getCurrentPage();
                totalPage = results.get(0).getTotalPage();
                pageSize = results.get(0).getPageSize();
            }
        }
        //返回分页信息
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("pageSize", pageSize);
        //返回作业信息
        model.addAttribute("taskList", tasks);
        //返回提交情况
        model.addAttribute("resultList", results);
        //返回所有作业id
        model.addAttribute("taskId", taskId);
        return "/queryResult";
    }

    /**
     * 教师修改分数
     *
     * @param json     json对象
     * @param response 响应对象
     */
    @RequestMapping(value = "/updateResult", method = RequestMethod.POST)
    public void updateResult(@RequestBody JSONObject json, HttpServletResponse response) {
        Integer stuId = Integer.valueOf(json.getString("stuId"));
        Integer taskId = json.getInteger("taskId");
        Integer score = json.getInteger("score");
        Result result = new Result();
        result.setStuId(stuId);
        result.setTaskId(taskId);
        result.setScore(score);
        int flag = 0;
        try {
            flag = resultService.updateResult(result);
        } catch (Exception e) {
            flag = 0;
            e.printStackTrace();
        }
        try {
            response.getWriter().print(flag);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 教师更新作业
     *
     * @param updateTask 被更新的task对象
     * @param uploadfile 上传文件的流对象
     * @return
     */
    @RequestMapping("/updatetask")
    public String updateTask(
            final Task updateTask,
            final @RequestParam(value = "uploadfile", required = false)
                    MultipartFile[] uploadfile) {
        System.out.println(updateTask.getTaskExpiry());
        String oldUrl = "";
        String newUrl = "";
        String oldName = "";
        String newName = "";
        boolean flag = false;
        try {
            Task task = taskService.findTaskByTaskId(updateTask.getTaskId());
            oldName = task.getTaskDownloadName();
            newName = uploadfile[0].getOriginalFilename();
            oldUrl = Config.title + updateTask.getTaskId();
            newUrl = Config.title + updateTask.getTaskId();
            task.setTaskName(updateTask.getTaskName());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-mm-dd HH:mm:ss");

            task.setTaskExpiry(updateTask.getTaskExpiry());
            if (newName.equals("") || newName == null) {
                task.setTaskDownloadName(null);
            } else {
                task.setTaskDownloadName(newName);
            }
            taskService.updateTask(task);
        } catch (Exception e) {
            e.printStackTrace();
        }
        File oldDir = new File(oldUrl);
        if (uploadfile[0].getSize() == 0) {//未选择上传文件,只修改文件夹名字
            flag = oldDir.renameTo(new File(newUrl, newName));
            if (flag) {
                LOGGER.debug("message:{}", "文件修改成功");
            }
        } else {
            //选择上传文件，删除原来的文件，新建文件并写入上传的文件
            FileUtils fu = new FileUtils();
            File file = new File(oldDir, oldName);
            fu.deleteFile(file);
            UploadUtils up = new UploadUtils();
            up.uploadUtils(uploadfile, newUrl);
        }
        return "redirect:/managejob";
    }

    /**
     * 教师新增作业
     *
     * @param taskName    作业名称
     * @param datetime    作业最迟提交日期
     * @param uploadFiles 上传文件流对象
     * @return
     */
    @RequestMapping(value = "/uploadtask", method = RequestMethod.POST)
    public String upload(@RequestParam(value = "taskname") String taskName,
                         @RequestParam(value = "datetime") Date datetime,
                         @RequestParam(value = "uploadfile", required = false)
                                 MultipartFile[] uploadFiles) {
        System.out.println(datetime);
        //查询数据表已经存在的作业最大id,在其基础上自增
        List<Task> tasks = taskService.findTaskMaxTaskId();
        Task task = new Task();
        Integer taskId = 0;
        if (tasks.size() > 0) {
            taskId = tasks.get(0).getTaskId();
            taskId++;
            task.setTaskId(taskId);
        } else {
            //第一次操作,数据库没有数据
            taskId++;
            task.setTaskId(taskId);
        }
        //文件存放路径,绝对路径+作业id
        String road = Config.title + task.getTaskId();

        UploadUtils up = new UploadUtils();
        if (up.uploadUtils(uploadFiles, road)) {
            //设置作业名称
            task.setTaskName(taskName);
            //文件下载名称
            task.setTaskDownloadName(uploadFiles[0].getOriginalFilename());
            // 日期转换
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            try {
                task.setTaskExpiry(datetime);
                // 把作业记录添加到数据库中
                taskService.addTask(task);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //更新result表,推送给所有学生
            resultService.pushAllStudent(taskId);
        }
        return "redirect:/managejob";
    }

    /**
     * 检作业是否存在
     *
     * @param taskName 作业名
     * @param response 响应对象
     */
    @RequestMapping("/taskIsExist")
    public void taskIsExist(String taskName, HttpServletResponse response) {
        String flag = "true";
        List<Task> tasks = new ArrayList<Task>();
        // 存放所有的作业
        try {
            tasks = taskService.findAllTasks();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Task task : tasks) {
            if (task.getTaskName().equals(taskName)) {
                flag = "false";
            }
        }
        JSONObject json = new JSONObject();
        json.put("getdata", flag);
        try {
            response.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 教师删除作业,单项删除
     *
     * @param id 被删除的作业id主键
     * @return
     */
    @RequestMapping(value = "/deleteTask")
    public String deleteTaskById(Long id) {
        LOGGER.debug("delete:{}", id);
        Integer result = taskService.deleteTaskById(id);
        //先不判断,都返回页面
        //重定向到作业管理页面
        return "redirect:/managejob";
    }

    /**
     * 从excel中导入学生名单
     * 功能异常
     *
     * @param uploadfile 需要被导入的文件
     * @return
     */
    @RequestMapping(value = "/importStudent", method = RequestMethod.POST)
    public String importStudent(@RequestParam(value = "uploadfile", required = false) final MultipartFile[] uploadfile) {
        LOGGER.debug("import  running");
        String road = Config.IMPORT_INFO;
        File file = new File(road);
        if (!file.exists()) {
            file.mkdirs();
        }
        return "redirect:/managejob";
    }
}
