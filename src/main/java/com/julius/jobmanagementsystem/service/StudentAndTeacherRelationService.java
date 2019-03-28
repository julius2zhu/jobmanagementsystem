package com.julius.jobmanagementsystem.service;

import com.julius.jobmanagementsystem.domain.entity.StudentAndTeacherRelation;

import java.util.List;

/**
 * @author julius
 * @create 2019-03-28 19:27
 * 学生和教师关系维护业务层接口
 */
public interface StudentAndTeacherRelationService {
    /**
     * 查询所有记录
     *
     * @return 实体记录
     */
    List<StudentAndTeacherRelation> selectAll();

    /**
     * 根据教师id去查询该教师下面的学生
     *
     * @param teacherId 教师id
     * @return 学生id集合
     */
    List<StudentAndTeacherRelation> selectByTeacherId(Integer teacherId);

    /**
     * 根据学生去查询该学生的全部老师
     *
     * @param studentId 学生id
     * @return 教师id集合
     */
    List<StudentAndTeacherRelation> selectByStudentId(Integer studentId);

    /**
     * 根据教师id删除该教师信息
     *
     * @param teacherId 教师id
     * @return 受影响的行数
     */
    Integer deleteTeacherById(Integer teacherId);

    /**
     * 根据学生id删除该学生信息
     *
     * @param studentId 学生id
     * @return 受影响的行数
     */
    Integer deleteStudentById(Integer studentId);

}
