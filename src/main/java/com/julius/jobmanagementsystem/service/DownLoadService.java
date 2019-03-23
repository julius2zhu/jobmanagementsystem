package com.julius.jobmanagementsystem.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 下载Service
 * 包括学生下载作业（题目），老师下载学生作业
 * 老师导出成绩等功能
 *
 * @author tankai
 */
public interface DownLoadService {

    /**
     * 老师下载学生上交的作业（可能包含多个文件，打包下载）
     *
     * @param taskId   作业ID
     * @param stuId    学生ID
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    void downloadResult(int taskId, String stuId, HttpServletRequest request, HttpServletResponse response);

    /**
     * 老师选择作业导出所有学生作业成绩
     * 导出格式：成绩.xlsx
     *
     * @param taskIdList 作业ID
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    void downloadAllResults(List<Integer> taskIdList,
                            HttpServletRequest request,
                            HttpServletResponse response);

    /**
     * 学生下载老师布置的作业（可能包含多个文件，打包下载）
     *
     * @param taskId   作业ID
     * @param request  请求对象
     * @param response 响应对象
     */
    void downloadTask(int taskId, HttpServletRequest request, HttpServletResponse response);

}