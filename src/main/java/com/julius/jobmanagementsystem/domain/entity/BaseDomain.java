package com.julius.jobmanagementsystem.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author julius
 * @date 2019/3/26
 * @time 13:58
 * 通用实体类
 **/
@Setter
@Getter
public class BaseDomain {
    //主键id
    private Long id;
    //创建日期
    private Date createdDate;
    //更新日期
    private Date updatedDate;
    //删除状态位
    private Character deleteFlag;
}
