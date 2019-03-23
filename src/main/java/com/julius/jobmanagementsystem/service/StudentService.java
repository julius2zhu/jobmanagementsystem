package com.julius.jobmanagementsystem.service;

import com.julius.jobmanagementsystem.domain.entity.Student;

import java.util.List;


public interface StudentService {
    /**
     * 批量插入学生信息（最好是完整的学生信息）
     *
     * @param list
     * @return
     */
    int addStuList(List<Student> list) throws Exception;

    /**
     * 插入单个学生信息
     *
     * @param stu
     * @return
     */
    int addStu(Student stu) throws Exception;

    /**
     * 根据学生id删除学生信息
     *
     * @param stuId
     * @return
     */
    public int deleteStuByStuId(String stuId) throws Exception;

    /**
     * 根据id删除list中所有学生信息
     *
     * @return
     */
    int deleteAll(List<String> list) throws Exception;

    /**
     * 根据学生学号修改密码
     *
     * @param stuId
     * @param password
     * @return
     */
    int updatePasswordByStuId(String stuId, String password) throws Exception;

    /**
     * 根据学生学号修改名字
     *
     * @param stuId
     * @param name
     * @return
     */
    int updateStuNameByStuId(String stuId, String name) throws Exception;

    /**
     * 根据学生对象修改学生信息
     *
     * @param stu 必须包含学生学号
     * @return
     */
    int updateStudentInfo(Student stu) throws Exception;

    /**
     * 根据学号获取学生密码
     *
     * @param stuId
     * @return
     * @throws Exception
     */
    String findStudentPwdByStuId(String stuId) throws Exception;

    /**
     * 根据学号获取学生信息
     *
     * @param stuId
     * @return
     */
    Student findStudentInfoByStuId(String stuId) throws Exception;

    /**
     * 获取所有学生信息
     *
     * @return
     */
    List<Student> findAllStudent() throws Exception;

    /**
     * 判断登录是否成功，成功返回1，否则返回0,id不存在返回-1
     *
     * @param stuId
     * @param pwd
     * @return
     */
    int login(String stuId, String pwd);
}
