package com.julius.jobmanagementsystem.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public final class Result extends BaseDomain implements Serializable {
    //学生ID
    private Integer stuId;
    //学生姓名
    private String studentName;
    //作业ID
    private Integer taskId;
    //作业下载名称
    private String taskDownLoadName;
    //作业提交状态
    private Boolean submit;
    //作业分数
    private Integer score;

    @Override
    public String toString() {
        return "Result{" +
                "stuId='" + stuId + '\'' +
                ", taskId=" + taskId +
                ", submit=" + submit +
                ", score=" + score +
                '}';
    }
}