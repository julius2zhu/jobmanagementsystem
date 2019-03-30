package com.julius.jobmanagementsystem.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.julius.jobmanagementsystem.domain.entity.Task;
import com.julius.jobmanagementsystem.domain.repository.ResultDao;
import com.julius.jobmanagementsystem.domain.repository.TaskDao;
import com.julius.jobmanagementsystem.service.TaskService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("taskService")
@Transactional
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private ResultDao resultDao;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public List<Task> findAllTasks(final Integer currentPage, Integer pageSize) {
        Page<Task> taskPage = PageHelper.startPage(currentPage, pageSize);
        List<Task> tasks = taskDao.findAllTasks();
        //不会返回null会返回空集合
        if (tasks.size() > 0) {
            tasks.get(0).setCurrentPage(currentPage);
            tasks.get(0).setTotalPage(taskPage.getPages());
            tasks.get(0).setPageSize(taskPage.getPageSize());
        }
        return tasks;
    }

    @Override
    public List<Task> findAllTasks() {
        return taskDao.findAllTasks();
    }

    public Integer addTask(final  Task task) {
        return taskDao.insert(task);
    }

    public Integer deleteTaskById(Long id) {
        //删除task表
        Integer record1 = taskDao.deleteTaskById(id);
        //删除result表
        Integer record2 = resultDao.deleteTaskById(id);
        if (record1 > 0 && record2 > 0) {
            return record1;
        }
        return -1;
    }

    // 暂未实现
    public int deleteAll() {
        return 0;
    }

    public int updateTaskRuleByTaskId(Integer taskId, String taskRule) {
        Task task = new Task();
        task.setTaskId(taskId);
        task.setTaskRule(taskRule);
        return taskDao.updateByTaskIdSelective(task);
    }

    public int updatetaskExpiryByTaskId(Integer taskId, Date taskExpiry) {
        Task task = new Task();
        task.setTaskId(taskId);
        task.setTaskExpiry(taskExpiry);
        return taskDao.updateByTaskIdSelective(task);
    }

    public int updateMinsizeByTaskId(Integer taskId, Integer minsize) {
        Task task = new Task();
        task.setTaskId(taskId);
        task.setTaskMinsize(minsize);
        return taskDao.updateByTaskIdSelective(task);
    }

    @Override
    public List<Task> findTaskMaxTaskId() {

        return taskDao.findTaskMaxTaskId();
    }

    public Task findTaskByTaskId(Integer taskId) {
        return taskDao.selectByTaskId(taskId);
    }

    @Override
    public List<Task> finTaskByStudentId(Integer studentId) {
        return taskDao.finTaskByStudentId(studentId);
    }

    @Override
    public Task findTaskByTaskName(String taskName) throws Exception {
        return taskDao.selectByTaskName(taskName);
    }

    @Override
    public int updateTask(Task task) throws Exception {
        return taskDao.updateByTaskIdSelective(task);
    }

}
