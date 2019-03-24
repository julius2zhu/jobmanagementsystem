package com.julius.jobmanagementsystem.domain.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Student {
    private Long id;
    //学生ID
    private String stuId;
    //学生姓名
    private String stuName;
    //学生密码 
    private String stuPwd;

    @Override
    public String toString() {
        return "Student{" +
                "stuId='" + stuId + '\'' +
                ", stuName='" + stuName + '\'' +
                ", stuPwd='" + stuPwd + '\'' +
                '}';
    }
}