package com.julius.jobmanagementsystem.domain.repository;

import com.julius.jobmanagementsystem.domain.entity.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("studentDao")
public interface StudentDao {
    /**
     * 向学生表插入一条记录
     *
     * @param student
     * @return
     */
    int insert(Student student);

    /**
     * 向学生表插入学生记录
     * 属性非空才插入该属性
     *
     * @param student
     * @return
     */
    int insertSelective(Student student);

    /**
     * 批量插入学生记录
     *
     * @param stuList
     * @return
     */
    int insertBatch(List<Student> stuList);

    /**
     * 通过stuId删除学生记录
     *
     * @param stuId
     * @return
     */
    int deleteByStuId(String stuId);

    /**
     * 通过stuId批量删除学生记录
     *
     * @param stuIdList
     * @return
     */
    int deleteBatch(List<String> stuIdList);

    /**
     * 通过stuId查询学生记录
     *
     * @param stuId
     * @return
     */
    Student selectByStuId(String stuId);

    /**
     * 查询所有学生信息
     *
     * @return
     */
    List<Student> selectAllStu();

    /**
     * 修改学生信息
     * 检查属性非空
     *
     * @param student
     * @return
     */
    int updateByStuIdSelective(Student student);

    /**
     * 修改学生信息
     *
     * @param student
     * @return
     */
    int updateByStuId(Student student);

    /**
     * app端学生注册
     *
     * @param student 学生信息实体
     * @return 受影响的行数
     */
    Integer register(Student student);

    /**
     * 查询所有学生信息
     *
     * @return 学生信息对象集合
     */
    List<Student> findAllStudent();
}