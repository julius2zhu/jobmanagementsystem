package com.julius.jobmanagementsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.julius.jobmanagementsystem.domain.entity.Result;
import com.julius.jobmanagementsystem.domain.entity.Student;
import com.julius.jobmanagementsystem.domain.repository.ResultDao;
import com.julius.jobmanagementsystem.service.ResultService;
import com.julius.jobmanagementsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service("resultService")
@Transactional
public class ResultServiceImpl implements ResultService {
    @Autowired
    private ResultDao resultDao;
    @Autowired
    private StudentService studentService;

    public int addResult(Result result) {
        return resultDao.insertSelective(result);
    }

    public int deleteResultByStuId(String stuId) {
        return resultDao.deleteByStuId(stuId);
    }

    public int deleteResultByTaskId(Integer taskId) {

        return resultDao.deleteByTaskId(taskId);
    }

    public int deleteResultByResult(Result result) {
        if (result.getStuId() == null || result.getTaskId() == null)
            return 0;
        return resultDao.deleteByPrimaryKey(result);
    }

    public int updateResult(Result result) {
        if (result.getStuId() == null || result.getTaskId() == null)
            return 0;
        return resultDao.updateByPrimaryKeySelective(result);
    }

    public List<Result> findAllResult() {
        List<Result> list = new ArrayList<Result>();
        list = resultDao.selectAll();
        return list;
    }

    public List<Result> findResultByStuId(String stuId) {
        List<Result> list = new ArrayList<Result>();
        list = resultDao.selectByStuId(stuId);
        return list;
    }

    public List<Result> findResultByTaskId(Integer taskId, Integer currentPage) {
        //分页操作,当前页和每页显示的数据
        if (currentPage == null) {
            currentPage = 1;
        } else {
            currentPage++;
        }
        PageHelper.startPage(currentPage, 10);
        PageInfo pageInfo = new PageInfo(resultDao.findResultByTaskId(taskId));
        List<Result> results = pageInfo.getList();
        System.out.println(results.size());
        return results;
    }

    public Result findResult(String stuId, Integer taskId) {

        Result result = new Result();
        result.setStuId(stuId);
        result.setTaskId(taskId);
        return resultDao.selectByPrimaryKey(result);
    }

    @Override
    public Integer findTaskIsSubmit(final Integer studentId, final Integer taskId) {
        return resultDao.findTaskIsSubmit(studentId, taskId);
    }

    @Override
    public Integer pushAllStudent(final Integer taskId) {
        //查询所有学生信息
        List<Student> studentList = studentService.findAllStudent();
        //遍历所有的学生id添加到result表中
        for (Student student : studentList) {
            String studentId = student.getStuId();
            //判断若是不存在则添加
            Integer count = resultDao.findResultStudentById(studentId, taskId);
            if (!(count > 0)) {
                //插入数据
                Result result = new Result();
                result.setStuId(studentId);
                result.setTaskId(taskId);
                resultDao.insert(result);
            }
        }
        return null;
    }
}
