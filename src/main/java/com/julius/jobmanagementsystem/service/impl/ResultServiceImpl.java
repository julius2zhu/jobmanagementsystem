package com.julius.jobmanagementsystem.service.impl;

import com.julius.jobmanagementsystem.domain.entity.Result;
import com.julius.jobmanagementsystem.domain.repository.ResultDao;
import com.julius.jobmanagementsystem.service.ResultService;
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

    public List<Result> findResultByTaskId(Integer taskId, Integer... pageInfo) {
        List<Result> list = new ArrayList<Result>();
        if (pageInfo.length >= 2) {
            int begin = pageInfo[0];
            int size = pageInfo[1];
            if (begin != 0 || size != 0) {
                list = resultDao.selectTaskByPage(taskId, begin, size);
            }
        } else
            list = resultDao.selectByTaskId(taskId);
        return list;
    }

    public Result findResult(String stuId, Integer taskId) {

        Result result = new Result();
        result.setStuId(stuId);
        result.setTaskId(taskId);
        return resultDao.selectByPrimaryKey(result);
    }

    @Override
    public Integer findTaskIsSubmit(final Integer studentId, final Integer taskId) {
        return  resultDao.findTaskIsSubmit(studentId,taskId);
    }
}
