package com.julius.jobmanagementsystem.service;

import com.julius.jobmanagementsystem.domain.entity.Teacher;

import java.util.List;


public interface TeacherService {
	/**
	 * 添加教师
	 * 
	 * @param teacher
	 * @return
	 */
	 int addTeacher(Teacher teacher) throws Exception;

	/**
	 * 根据id删除教师
	 * 
	 * @param teaId
	 * @return
	 */
	 int deleteTeacherByTeaId(String teaId) throws Exception;

	/**
	 * 删除所有教师
	 * 
	 * @return
	 */
	 int deleteAll() throws Exception;

	/**
	 * 根据id更新name
	 * 
	 * @param teaId
	 * @param name
	 * @return
	 */
	 int updateTeacherNameByTeaId(String teaId, String name) throws Exception;

	/**
	 * 根据id更新密码
	 * 
	 * @param teaId
	 * @param pwd
	 * @return
	 * @throws Exception 
	 */
	 int updatePasswordByTeaId(String teaId, String pwd) throws Exception;

	/**
	 * 根据id找教师
	 * 
	 * @param teaId
	 * @return
	 * @throws Exception 
	 */
	 Teacher findTeacherByTeaId(String teaId) throws Exception;

	/**
	 * 查询所有教师
	 * 
	 * @return
	 */
	 List<Teacher> findAll() throws Exception;

	/**
	 * 根据id查密码 
	 * 
	 * @param teaId
	 * @return
	 * @throws Exception 
	 */
	 String findPwdByTeaId(String teaId) throws Exception;

	/**
	 * 教师登录，成功返回1，否则返回0
	 * 
	 * @param teaId
	 * @param pwd
	 * @return
	 */
	 int login(String teaId, String pwd);
}
