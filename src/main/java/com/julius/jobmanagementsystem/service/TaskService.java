package com.julius.jobmanagementsystem.service;

import com.julius.jobmanagementsystem.domain.entity.Task;

import java.util.Date;
import java.util.List;


public interface TaskService {
    /**
     * 添加作业
     *
     * @param task
     * @return
     */
    int addTask(Task task);

    /**
     * 根据作业id删除作业
     *
     * @param id 作业主键
     * @return 受影响行数
     */
    Integer deleteTaskById(Long id);

    /**
     * 删除所有作业
     *
     * @return
     */
    public int deleteAll() throws Exception;

    /**
     * 根据作业id更新作业规则路径信息
     *
     * @param taskId
     * @param taskRule
     * @return
     */
    public int updateTaskRuleByTaskId(Integer taskId, String taskRule) throws Exception;

    /**
     * 根据所给对象选择更新task
     *
     * @param task
     * @return
     * @throws Exception
     */
    int updateTask(Task task) throws Exception;

    /**
     * 根据作业id更新作业截止时间
     *
     * @param taskId
     * @param taskExpiry
     * @return
     */
    int updatetaskExpiryByTaskId(Integer taskId, Date taskExpiry) throws Exception;

    /**
     * 根据作业id更新作业最小大小
     *
     * @param taskId
     * @param minsize
     * @return
     */
    int updateMinsizeByTaskId(Integer taskId, Integer minsize) throws Exception;

    /**
     * 根据作业id查找作业
     *
     * @param taskId
     * @return
     */
    Task findTaskByTaskId(Integer taskId) throws Exception;

    /**
     * 根据作业name查找作业
     *
     * @param taskName
     * @return
     */
    Task findTaskByTaskName(String taskName) throws Exception;

    /**
     * 查找所有作业
     *
     * @return
     */
    List<Task> findAllTasks();

    /**
     * 根据学生学号查询课程信息
     *
     * @param studentId 学生学号
     * @return 课程信息对象集合
     */
    List<Task> finTaskByStudentId(Integer studentId);
}
