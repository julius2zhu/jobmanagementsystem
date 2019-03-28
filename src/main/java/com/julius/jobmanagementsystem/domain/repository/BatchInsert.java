package com.julius.jobmanagementsystem.domain.repository;

import com.julius.jobmanagementsystem.domain.entity.Result;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author julius
 * @date 2019/3/28
 * @time 11:30
 * 批量插入数据接口
 **/
public interface BatchInsert {

    /**
     * 批量插入数据
     * @param results
     * @return
     */
    Integer batchInsert(@Param("results") List<Result> results);
}
