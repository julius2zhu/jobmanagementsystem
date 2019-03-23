package com.julius.jobmanagementsystem.service.impl;

import com.julius.jobmanagementsystem.domain.entity.Result;
import com.julius.jobmanagementsystem.domain.repository.ResultDao;
import com.julius.jobmanagementsystem.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("testService")
@Transactional
public class TestServiceImpl implements TestService {

	@Autowired
	private ResultDao resultDao;
	
	
	public List<Result> findAllByTaskId(Integer taskId){
		
		return resultDao.selectByTaskId(taskId);
	}
}
