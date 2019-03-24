package com.julius.jobmanagementsystem.service.impl;

import com.julius.jobmanagementsystem.domain.entity.Student;
import com.julius.jobmanagementsystem.domain.repository.StudentDao;
import com.julius.jobmanagementsystem.service.StudentService;
import com.julius.jobmanagementsystem.utils.StudentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("studentService")
@Transactional
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentDao studentDao;

    public int addStuList(List<Student> list) {
        if (list.get(0).getStuPwd() == null)
            list = StudentUtils.initialStudentPassword(list);
        return studentDao.insertBatch(list);
    }

    public int addStu(Student stu) {
        if (stu.getStuPwd() == null)
            stu = StudentUtils.resetStudentPassword(stu);
        return studentDao.insert(stu);
    }

    public int deleteStuByStuId(String stuId) {
        return studentDao.deleteByStuId(stuId);
    }

    public int deleteAll(List<String> stuIdList) {
        return studentDao.deleteBatch(stuIdList);
    }

    public int updatePasswordByStuId(String stuId, String pwd) {
        Student stu = new Student();
        stu.setStuId(stuId);
        stu.setStuPwd(pwd);
        return studentDao.updateByStuIdSelective(stu);
    }

    public int updateStuNameByStuId(String stuId, String name) {
        // TODO Auto-generated method stub
        Student stu = new Student();
        stu.setStuId(stuId);
        stu.setStuName(name);
        return studentDao.updateByStuIdSelective(stu);
    }

    public int updateStudentInfo(Student stu) {
        // TODO Auto-generated method stub
        return studentDao.updateByStuId(stu);
    }

    public String findStudentPwdByStuId(String stuId) throws Exception {
        return studentDao.selectByStuId(stuId).getStuPwd();
    }

    public Student findStudentInfoByStuId(String stuId) {
        // TODO Auto-generated method stub
        return studentDao.selectByStuId(stuId);
    }

    public List<Student> findAllStudent() {
        // TODO Auto-generated method stub
        return studentDao.selectAllStu();
    }

    public Integer login(String stuId, String pwd) {
        try {
            if (pwd.equals(findStudentPwdByStuId(stuId)))
                return 1;
            else
                return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public Integer register(final Student student) {
        Integer result = -1;
        //账号是否存在校验
        Student record = studentDao.selectByStuId(student.getStuId());
        if (record != null) {
            result = 0;
        } else {
            Integer registerResult = studentDao.register(student);
            if (registerResult > 0) {
                result = 1;
            }
        }
        return result;
    }
}
