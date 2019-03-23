package com.julius.jobmanagementsystem.utils;

import com.julius.jobmanagementsystem.domain.entity.Result;
import com.julius.jobmanagementsystem.domain.entity.Task;
import com.julius.jobmanagementsystem.service.ResultService;
import com.julius.jobmanagementsystem.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author julius
 * @create 2019-03-22 22:27
 * 通用类
 */
@Component
public class Common {
    private static final Logger logger = LoggerFactory.getLogger(Common.class);
    private static TaskService taskService;
    private static ResultService resultService;

    @Autowired
    public Common(final TaskService taskService, final ResultService resultService) {
        Common.taskService = taskService;
        Common.resultService = resultService;
    }

    /**
     * 根据作业名称返回作业对象
     *
     * @param taskName    作业名称
     * @param taskService 作业service层
     * @return 作业对象
     */
    public static Task getTaskByName(final String taskName, final TaskService taskService) {
        int count = 0;
        Task task = null;
        while (task == null) {
            try {
                task = taskService.findTaskByTaskName(taskName);
                Thread.sleep(2000);
                count++;
                if (count > 20) {
                    break;
                }
            } catch (Exception e) {
                task = null;
                e.printStackTrace();
            }
        }
        return task;
    }

    /**
     * 通用请求错误页面显示
     *
     * @param ex       异常对象
     * @param response 响应对象
     * @param message  输出的信息
     */
    public static void commonShow(final Exception ex,
                                  final HttpServletResponse response,
                                  final String message) {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(message);
        } catch (IOException e) {
            logger.error("error:{}", e.getMessage());
            if (ex != null) {
                logger.error("error:{}", ex.getMessage());
            }
        }
    }

    /**
     * 检查提交的作业是否超过时间限制
     *
     * @param taskId 作业id
     * @return -1,出错,1超过,0未超过
     */
    public static Integer checkSubmitDate(Integer taskId) {
        Integer flag = -1;
        //获取服务器当前系统时间戳
        Long curTime = System.currentTimeMillis();
        Long deadLine = 0L;
        try {
            Date expiry = Common.taskService.findTaskByTaskId(taskId).getTaskExpiry();
            deadLine = expiry.getTime();
        } catch (Exception e) {
            flag = -1;
            e.printStackTrace();
        }
        if (curTime > deadLine) {
            flag = 1;
        } else {
            flag = 0;
        }
        return flag;
    }

    /**
     * @param multipartFiles 保存文件流对象
     * @param task           作业对象
     * @param studentId      学生id
     * @return
     */
    public static Boolean saveUpLoadFiles(
            final MultipartFile[] multipartFiles,
            final Task task,
            final String studentId) {
        final String path = Config.task + task.getTaskId() + "/" + studentId;

        UploadUtils up = new UploadUtils();
        if (up.uploadUtils(multipartFiles, path)) {
            //根据作业ID，和用户ID。修改result表中的submit状态
            Result result = new Result();
            result.setStuId(studentId);
            result.setTaskId(task.getTaskId());
            result.setSubmit(true);
            try {
                //更新数据库操作
                Common.resultService.updateResult(result);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("error:{}", e.getMessage());
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }
}
