package com.julius.jobmanagementsystem.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
public  final class Task  extends  BaseDomain implements Serializable {
    //作业ID
    private Integer taskId;
    //作业名称 
    private String taskName;
    //作业下载的名称
    private String taskDownloadName;
    //作业评分规则存放路径
    private String taskRule;
    //作业截止日期
    private Date taskExpiry;
    //作业文件大小最小值
    private Integer taskMinsize;
}