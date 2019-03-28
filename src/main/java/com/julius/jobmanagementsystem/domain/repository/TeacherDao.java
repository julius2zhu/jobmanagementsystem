package com.julius.jobmanagementsystem.domain.repository;

import com.julius.jobmanagementsystem.domain.entity.Teacher;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("teacherDao")
public interface TeacherDao {

	/**
	 * 插入一条教师记录
	 * @param teacher
	 * @return
	 */
    int insert(Teacher teacher);

    /**
     * 插入一条教师记录
     * 插入时判断是否属性值为空
     * @param teacher
     * @return
     */
    int insertSelective(Teacher teacher);

	/**
	 * 通过teaId删除教师
	 * @param teaId
	 * @return
	 */
    int deleteByTeaId(String teaId);

    /**
     * 通过teaId查询教师
     * @param teaId
     * @return
     */
    Teacher selectByTeaId(Integer teaId);

    /**
     * 查询所有教师记录 
     * @return
     */
    List<Teacher> selectAllTeacher();
    
    /**
     * 更新教师信息
     * 判断非空
     * @param teacher
     * @return
     */
    int updateByTeaIdSelective(Teacher teacher);

    /**
     * 更新教师信息 
     * @param teacher
     * @return
     */
    int updateByTeaId(Teacher teacher);
}