package com.julius.jobmanagementsystem.service.impl;

import com.julius.jobmanagementsystem.domain.entity.Teacher;
import com.julius.jobmanagementsystem.domain.repository.TeacherDao;
import com.julius.jobmanagementsystem.service.TeacherService;
import com.julius.jobmanagementsystem.utils.TeacherUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("teacherService")
@Transactional
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherDao teacherDao;

    public int addTeacher(Teacher teacher) {
        if (teacher.getTeaPwd() == null)
            teacher = TeacherUtils.resetTeacherPassword(teacher);
        return teacherDao.insert(teacher);
    }

    public int deleteTeacherByTeaId(String teaId) {

        return teacherDao.deleteByTeaId(teaId);
    }

    // 未实现
    public int deleteAll() {

        return 0;
    }

    public int updateTeacherNameByTeaId(String teaId, String name) {

        Teacher teacher = new Teacher();
        teacher.setTeaId(teaId);
        teacher.setTeaName(name);
        return teacherDao.updateByTeaIdSelective(teacher);
    }

    public int updatePasswordByTeaId(String teaId, String pwd) {

        Teacher teacher = new Teacher();
        teacher.setTeaId(teaId);
        teacher.setTeaPwd(pwd);
        return teacherDao.updateByTeaIdSelective(teacher);
    }

    public Teacher findTeacherByTeaId(String teaId) throws Exception {

        return teacherDao.selectByTeaId(teaId);
    }

    public List<Teacher> findAll() {

        return teacherDao.selectAllTeacher();
    }

    public int login(String teaId, String pwd) {

        try {
            if (pwd.equals(findPwdByTeaId(teaId)))
                return 1;
            else
                return 0;
        } catch (Exception e) {

            return -1;
        }
    }

    public String findPwdByTeaId(String teaId) throws Exception {

        return teacherDao.selectByTeaId(teaId).getTeaPwd();
    }

}
