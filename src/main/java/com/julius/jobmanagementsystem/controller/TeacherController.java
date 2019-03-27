package com.julius.jobmanagementsystem.controller;

import com.alibaba.fastjson.JSONObject;
import com.julius.jobmanagementsystem.domain.entity.Result;
import com.julius.jobmanagementsystem.domain.entity.Student;
import com.julius.jobmanagementsystem.domain.entity.Task;
import com.julius.jobmanagementsystem.service.ResultService;
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
import java.text.ParseException;
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
    private StudentService studentService;
    @Autowired
    private TaskService taskService;

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

        List<Result> results = resultService.findResultByTaskId(taskId, currentPage);
        List<Task> tasks = taskService.findAllTasks();
        model.addAttribute("taskList", tasks);
        model.addAttribute("resultList", results);

        return "/queryResult";
    }

    @RequestMapping(value = "/updateResult", method = RequestMethod.POST)
    public void updateResult(@RequestBody JSONObject json, HttpServletResponse response) {
        String stuId = json.getString("stuId");
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
     * @param request    请求信息
     * @param taskId     作业编号
     * @param taskName   作业名称
     * @param datetime   更新日期
     * @param uploadfile 上传文件的流对象
     * @return
     */
    @RequestMapping("/updatetask")
    public String updateTask(
            final HttpServletRequest request,
            final @RequestParam(value = "taskid") Integer taskId,
            final @RequestParam(value = "taskname") String taskName,
            final @RequestParam(value = "datetime") String datetime,
            final @RequestParam(value = "uploadfile", required = false)
                    MultipartFile[] uploadfile) {
        Task task = new Task();
        String oldUrl = "";
        String newUrl = "";
        String oldName = "";
        String newName = "";
        boolean flag = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            task = taskService.findTaskByTaskId(taskId);
            oldName = task.getTaskDownloadName();
            newName = uploadfile[0].getOriginalFilename();
            oldUrl = Config.title + taskId;
            newUrl = Config.title + taskId;
            task.setTaskName(taskName);
            task.setTaskExpiry(sdf.parse(datetime));
            task.setTaskDownloadName(newName);
            taskService.updateTask(task);
        } catch (Exception e) {
            e.printStackTrace();
        }
        File oldDir = new File(oldUrl);
        if (uploadfile[0].getSize() == 0) {//未选择上传文件,只修改文件夹名字
            flag = oldDir.renameTo(new File(newUrl, newName));
            if (flag) {
                System.out.println("文件名修改成功");
            }
        } else {
            //选择上传文件，删除原来的文件，新建文件并写入上传的文件
            FileUtils fu = new FileUtils();
            File file = new File(oldDir, oldName);
            fu.deleteFile(file);
            UploadUtils up = new UploadUtils();
            up.uploadUtils(uploadfile, newUrl);
        }
        //获取修改成功后的作业信息
//        Task t = Common.getTaskByName(taskName, taskService);
        //获取评分类对象
//        Rating rate = new Rating(t.getTaskName(), t.getTaskId());
        //获取当前活跃的线程信息，找到要修改的作业之前开启的线程并关闭
        //获取当前活跃的线程组
//        ThreadGroup group = Thread.currentThread().getThreadGroup();
//        Thread thread = null;
//        String threadName = oldName;
        //找到指定名字的线程，即获取要停止运行的线程
//        while (group != null) {
//            Thread[] threads = new Thread[(int) (group.activeCount() * 1.2)];
//            int count = group.enumerate(threads, true);
//            for (int i = 0; i < count; i++) {
//                System.out.println("线程名字：" + threads[i].getName());
//                if (threadName.equals(threads[i].getName())) {
//                    thread = threads[i];
//                    break;
//                }
//            }
//            group = group.getParent();
//        }
//        //如果要停止运行的线程存在，即仍在运行，则打断线程
//        if (thread != null) {
//            thread.interrupt();
//            System.out.println(oldName + "线程已被打断");
//        } else {
//            System.out.println("找不到线程！！！！");
//        }
//        //以新的作业信息开启新线程
//        new Thread(new AutoCheckThread(rate, t.getTaskExpiry(), resultService), t.getTaskName()).start();
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
                         @RequestParam(value = "datetime") String datetime,
                         @RequestParam(value = "uploadfile", required = false)
                                 MultipartFile[] uploadFiles) {
        //查询数据表已经存在的作业记录,在其基础上自增
        List<Task> tasks = taskService.findAllTasks();
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date date = sdf.parse(datetime);
                task.setTaskExpiry(date);
                // 把作业记录添加到数据库中
                taskService.addTask(task);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //更新result表,推送给所有学生
            Integer result = resultService.pushAllStudent(taskId);
        }
        return "redirect:/managejob";
    }

    @RequestMapping("/taskIsExist")
    public void taskIsExist(String taskname, HttpServletResponse response) {
        String flag = "true";
        List<Task> tasks = new ArrayList<Task>();
        // 存放所有的作业
        try {
            tasks = taskService.findAllTasks();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Task task : tasks) {
            if (task.getTaskName().equals(taskname)) {
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
