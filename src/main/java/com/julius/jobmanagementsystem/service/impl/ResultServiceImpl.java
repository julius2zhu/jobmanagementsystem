package com.julius.jobmanagementsystem.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.julius.jobmanagementsystem.domain.entity.Result;
import com.julius.jobmanagementsystem.domain.entity.Student;
import com.julius.jobmanagementsystem.domain.repository.BatchInsert;
import com.julius.jobmanagementsystem.domain.repository.ResultDao;
import com.julius.jobmanagementsystem.service.ResultService;
import com.julius.jobmanagementsystem.service.StudentService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
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
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

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
        List<Result> list = new ArrayList<>();
        list = resultDao.selectAll();
        return list;
    }

    public List<Result> findResultByStuId(Integer stuId) {

         return resultDao.selectByStuId(stuId);

    }

    public List<Result> findResultByTaskId(Integer taskId, Integer currentPage) {
        //默认每次展示十条数据
        Integer pageSize = 10;
        //分页操作,当前页和每页显示的数据
        Page<Result> page = PageHelper.startPage(currentPage, pageSize);
        List<Result> results = resultDao.findResultByTaskId(taskId);
        //不会返回null会返回空集合
        if (results.size() > 0) {
            results.get(0).setCurrentPage(currentPage);
            results.get(0).setTotalPage(page.getPages());
            results.get(0).setPageSize(page.getPageSize());
        }
        return results;
    }

    public Result findResult(Integer stuId, Integer taskId) {
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
        //需要被插入的数据
        List<Result> insertData = new ArrayList<>();
        //查询所有学生信息
        List<Student> studentList = studentService.findAllStudent();
        //仅仅学生表有数据
        if (studentList.size() > 0) {
            for (Student student : studentList) {
                Integer stuId = student.getStuId();
                Result insertResult = new Result();
                insertResult.setStuId(stuId);
                insertResult.setTaskId(taskId);
                insertData.add(insertResult);
            }
        } else {
            //student和result表都没数据,什么都不操作
            return -1;
        }
        //获取一个支持批量插入的SqlSession并且自动提交事务
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, true);
        BatchInsert batchInsert = sqlSession.getMapper(BatchInsert.class);
        //批量插入
        try {
            batchInsert.batchInsert(insertData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //提交事务,此处设置了自动提交事务还是没有起作用
        sqlSession.commit();
        if (sqlSession != null) {
            sqlSession.close();
        }
        return null;
    }
}
