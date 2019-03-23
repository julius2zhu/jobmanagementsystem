package com.julius.jobmanagementsystem.utils;

import com.julius.jobmanagementsystem.domain.entity.Task;
import com.julius.jobmanagementsystem.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author julius
 * @create 2019-03-22 22:27
 * 通用类
 */
public class Common {
    private static final Logger logger = LoggerFactory.getLogger(Common.class);

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
     * @param ex        异常对象
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
}
