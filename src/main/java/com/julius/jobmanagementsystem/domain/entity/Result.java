package com.julius.jobmanagementsystem.domain.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Result {
    //学生ID
    private String stuId;
    //作业ID
    private Integer taskId;
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