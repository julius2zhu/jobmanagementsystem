package com.julius.jobmanagementsystem.domain.repository;

import com.julius.jobmanagementsystem.domain.entity.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("resultDao")
public interface ResultDao {
	
	/**
	 * 向成绩表插入数据
	 * @param result
	 * @return
	 */
    int insert(Result result);

    /**
     * 有选择插入成绩表
     * @param result
     * @return
     */
    int insertSelective(Result result);
   
    /**
     * 通过主键（stuId,taskId）删除成绩
     * @param result
     * @return
     */
    int deleteByPrimaryKey(Result result);
	
    /**
     * 通过stuId删除成绩
     * @param stuId
     * @return
     */
	int deleteByStuId(String stuId);
    
	/**
	 * 通过taskId删除成绩
	 * @param taskId
	 * @return
	 */
    int deleteByTaskId(Integer taskId);
    
    /**
     * 通过主键（stuId，taskId）查找成绩
     * @param result
     * @return
     */
    Result selectByPrimaryKey(Result result);

    /**
     * 通过stuId查找成绩
     * @param stuId
     * @return
     */
    List<Result> selectByStuId(String stuId);
   
    /**
     * 通过主键taskId查找成绩
     * @param taskId
     * @return
     */
    List<Result> selectByTaskId(Integer taskId);
    /**
     * 分页查询作业成绩
     * @param taskId 作业号 
     * @param begin 开始
     * @param size 每页个数
     * @return
     */
    List<Result> selectTaskByPage(@Param("taskId") Integer taskId, @Param("begin") Integer begin, @Param("size") Integer size);
    
    /**
     * 查询所有人的所有成绩 
     * @return
     */
    List<Result> selectAll();
    
    /**
     * 通过主键（stuId，taskId）更新成绩
     * 属性为空则不更新
     * @param result
     * @return
     */
    int updateByPrimaryKeySelective(Result result);

    /**
     * 通过主键（stuId，taskId）更新成绩
     * @param result
     * @return
     */
    int updateByPrimaryKey(Result result);
}