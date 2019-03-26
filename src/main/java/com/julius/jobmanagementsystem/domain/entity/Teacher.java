package com.julius.jobmanagementsystem.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public  final  class Teacher extends BaseDomain implements Serializable {
    private Long id;
    //	教师ID
    private String teaId;
    //教师姓名
    private String teaName;
    //教师登录密码
    private String teaPwd;

    @Override
    public String toString() {
        return "Teacher{" +
                "teaId='" + teaId + '\'' +
                ", teaName='" + teaName + '\'' +
                ", teaPwd='" + teaPwd + '\'' +
                '}';
    }
}