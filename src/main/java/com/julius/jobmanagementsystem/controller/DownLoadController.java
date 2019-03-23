package com.julius.jobmanagementsystem.controller;

import com.julius.jobmanagementsystem.service.DownLoadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author julius
 * @create 2019-03-23 10:31
 * 处理文件下载控制层
 */
@Controller
@RequestMapping("/download")
public class DownLoadController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private DownLoadService downLoadService;

    public DownLoadController() {
    }

    @Autowired
    public DownLoadController(DownLoadService downLoadService) {
        this.downLoadService = downLoadService;
    }

    /**
     * 学生下载老师布置的作业文件（打包成.zip文件）
     * 文件名形如：实验三贝叶斯分类实验.zip
     *
     * @param taskId   作业ID
     * @param request  请求对象
     * @param response 响应对象
     */
    @RequestMapping(value = "/task/{taskId}", method = RequestMethod.GET)
    public void downloadTask(final @PathVariable("taskId") int taskId,
                             final HttpServletRequest request,
                             final HttpServletResponse response) {
        downLoadService.downloadTask(taskId, request, response);
    }

    /**
     * 老师下载学生上交的作业文件（打包成.zip文件）
     * 文件名形如：Task1_M201672888.zip
     *
     * @param taskId   作业ID
     * @param stuId    学生ID
     * @param request  请求对象
     * @param response 响应对象
     */
    @RequestMapping(value = "/result/{taskId}/{stuId}", method = RequestMethod.GET)
    public void downloadResult(final @PathVariable("taskId") int taskId,
                               final @PathVariable("stuId") String stuId,
                               final HttpServletRequest request,
                               final HttpServletResponse response) {

        downLoadService.downloadResult(taskId, stuId, request, response);
    }

    /**
     * 导出学生成绩成excel表格
     *
     * @param taskIds
     * @param request
     * @param response
     */
    @RequestMapping(value = "/results/{taskIds}", method = RequestMethod.GET)
    public void downloadAllResults(@PathVariable("taskIds") String taskIds,
                                   HttpServletRequest request, HttpServletResponse response) {
        System.out.println("taskId: " + taskIds);
        //将传来的id拼接成的字符串拆分为List<Integer>
        List<Integer> ids = new ArrayList<Integer>();
        String[] idStrs = taskIds.split(",");
        for (String s : idStrs) {
            ids.add(Integer.valueOf(s));
        }
        downLoadService.downloadAllResults(ids, request, response);
    }
}