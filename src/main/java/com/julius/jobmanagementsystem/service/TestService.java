package com.julius.jobmanagementsystem.service;

import com.julius.jobmanagementsystem.domain.entity.Result;
import java.util.List;


public interface TestService {

	List<Result> findAllByTaskId(Integer taskId);

}