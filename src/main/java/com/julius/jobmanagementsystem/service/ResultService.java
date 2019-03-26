package com.julius.jobmanagementsystem.service;

import com.julius.jobmanagementsystem.domain.entity.Result;

import java.util.List;

public interface ResultService {
    /**
     * 插入成绩
     *
     * @param result
     * @return
     */
    int addResult(Result result) throws Exception;

    /**
     * 根据学生id删除该学生所有成绩
     *
     * @param stuId
     * @return
     */
    int deleteResultByStuId(String stuId) throws Exception;

    /**
     * 根据作业id删除该作业所有人的成绩
     *
     * @param taskId
     * @return
     */
    int deleteResultByTaskId(Integer taskId) throws Exception;

    /**
     * 根据Result对象删除该作业成绩记录
     *
     * @param result 必须包含stuId,taskId
     * @return
     */
    int deleteResultByResult(Result result) throws Exception;

    /**
     * 根据Result修改该作业成绩
     *
     * @param result 必须包含stuId，taskId，以及修改后的其他信息
     * @return
     */
    int updateResult(Result result) throws Exception;

    /**
     * 查询所有人的所有作业成绩
     *
     * @return
     */
    List<Result> findAllResult() throws Exception;

    /**
     * 根据学生学号查询该学生的所有作业成绩
     *
     * @param stuId
     * @return
     */
    List<Result> findResultByStuId(String stuId) throws Exception;

    /**
     * 根据作业号查询该作业所有学生的成绩
     *
     * @param taskId
     * @return
     */
    List<Result> findResultByTaskId(Integer taskId, Integer... pageInfo) throws Exception;

    /**
     * 根据学生学号和作业号查询该学生该作业的成绩
     *
     * @param stuId
     * @param taskId
     * @return
     */
    Result findResult(String stuId, Integer taskId);


    /**
     * 查询作业是否已经提交
     *
     * @param studentId 学生id
     * @param taskId    作业id
     * @return 记录数
     */
    Integer findTaskIsSubmit(Integer studentId, Integer taskId);
}
