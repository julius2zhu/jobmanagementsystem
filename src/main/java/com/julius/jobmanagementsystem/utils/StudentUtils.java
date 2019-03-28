package com.julius.jobmanagementsystem.utils;

import com.julius.jobmanagementsystem.domain.entity.Student;

import java.util.List;

public class StudentUtils {
    public static Student resetStudentPassword(Student stu) {
        stu.setStuPwd(String.valueOf(stu.getStuId()));
        return stu;
    }

    public static List<Student> initialStudentPassword(List<Student> list) {
        for (Student stu : list) {
            stu.setStuPwd(String.valueOf(stu.getStuId()));
        }
        return list;
    }
}
