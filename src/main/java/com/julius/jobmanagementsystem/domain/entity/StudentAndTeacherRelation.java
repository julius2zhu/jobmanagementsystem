package com.julius.jobmanagementsystem.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author julius
 * @create 2019-03-28 19:24
 * 学生和教师关系维护实体
 */
@Setter
@Getter
public final class StudentAndTeacherRelation extends BaseDomain implements Serializable {
    //学生id
    private Integer studentId;
    //教师id
    private Integer teacherId;

}
