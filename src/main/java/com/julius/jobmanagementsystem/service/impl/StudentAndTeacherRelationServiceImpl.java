package com.julius.jobmanagementsystem.service.impl;

import com.julius.jobmanagementsystem.domain.entity.StudentAndTeacherRelation;
import com.julius.jobmanagementsystem.domain.repository.StudentAndTeacherRelationServiceDao;
import com.julius.jobmanagementsystem.service.StudentAndTeacherRelationService;
import com.julius.jobmanagementsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.ServiceMode;
import java.util.List;

/**
 * @author julius
 * @create 2019-03-28 19:34
 * 教师和学生关系维护业务层接口实现类
 */
@Service
@Transactional
public class StudentAndTeacherRelationServiceImpl implements StudentAndTeacherRelationService {
    @Autowired
    private StudentAndTeacherRelationServiceDao dao;
    @Autowired
    private StudentService studentService;
    @Override
    public List<StudentAndTeacherRelation> selectAll() {
        return dao.selectAll();
    }

    @Override
    public List<StudentAndTeacherRelation> selectByTeacherId(Integer teacherId) {
        return dao.selectByTeacherId(teacherId);
    }

    @Override
    public List<StudentAndTeacherRelation> selectByStudentId(Integer studentId) {
        return selectByStudentId(studentId);
    }

    @Override
    public Integer deleteTeacherById(Integer teacherId) {
        return deleteTeacherById(teacherId);
    }

    @Override
    public Integer deleteStudentById(Integer studentId) {
        return deleteStudentById(studentId);
    }
}

