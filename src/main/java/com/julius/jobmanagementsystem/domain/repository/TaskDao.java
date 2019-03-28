package com.julius.jobmanagementsystem.domain.repository;

import com.julius.jobmanagementsystem.domain.entity.Task;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("taskDao")
public interface TaskDao {

    /**
     * 插入一条作业记录
     *
     * @param task  作业实体对象
     * @return 受影响的行数
     */
    Integer insert(Task task);

    /**
     * 插入一条作业记录
     * 插入时判断属性值是否为空
     *
     * @param task
     * @return
     */
    int insertSelective(Task task);

    /**
     * 通过taskId删除作业记录
     *
     * @param id 被删除的主键
     * @return 受影响行数
     */
    Integer deleteTaskById(Long id);

    /**
     * 通过taskId查询作业
     *
     * @param taskId
     * @return
     */
    Task selectByTaskId(Integer taskId);

    /**
     * 通过taskName查询作业
     *
     * @param taskName
     * @return
     */
    Task selectByTaskName(String taskName);

    /**
     * 查询所有作业
     *
     * @return
     */
    List<Task> findAllTasks();

    /**
     * 更新作业
     * 更新时检查属性值非空
     *
     * @param task
     * @return
     */
    int updateByTaskIdSelective(Task task);

    /**
     * 更新作业
     *
     * @param task
     * @return
     */
    int updateByTaskId(Task task);

    /**
     * 根据学生学号查询课程信息
     *
     * @param studentId 学生学号
     * @return 课程信息对象集合
     */
    List<Task> finTaskByStudentId(@Param("studentId") Integer studentId);

    /**
     * 查询作业表记录集合
     * @return  taskId倒序集合
     */
    List<Task> findTaskMaxTaskId();
}