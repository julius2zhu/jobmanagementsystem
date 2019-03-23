package com.julius.jobmanagementsystem.utils;

import com.julius.jobmanagementsystem.domain.entity.Teacher;
import java.util.List;

public class TeacherUtils {
	public static Teacher resetTeacherPassword(Teacher teacher){
		teacher.setTeaPwd(teacher.getTeaId());
		return teacher;
	}
	
	public static List<Teacher> initialTeacherPassword(List<Teacher> list){
		for (Teacher teacher : list) {
			teacher.setTeaPwd(teacher.getTeaId());
		}
		return list;
	}
}
