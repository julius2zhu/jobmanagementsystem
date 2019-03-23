package com.julius.jobmanagementsystem.utils;

import com.julius.jobmanagementsystem.automaticrating.Rating;
import com.julius.jobmanagementsystem.domain.entity.Result;
import com.julius.jobmanagementsystem.service.ResultService;

import java.util.Date;
import java.util.List;

public class AutoCheckThread implements Runnable{
	
	private Date deadline;	//作业截至日期
	private Rating rate;	//自动评分类对象
	private ResultService resultService ; //更新数据库里面result表的serveice对象（从controller层获取避免为空）
	//初始化对象
	public AutoCheckThread(Rating rate,Date deadline,ResultService resultService) {
		// TODO Auto-generated constructor stub
		this.rate = rate;
		this.deadline = deadline;
		this.resultService = resultService;		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("线程名："+Thread.currentThread().getName()+"  线程ID:"+Thread.currentThread().getId()+" 已启动！");
//		Date deadline = rate.getDeadline();
		Long curTime = System.currentTimeMillis();
		while(curTime < deadline.getTime()){
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			curTime = System.currentTimeMillis();
		}
		//开始评分
		rate.startRating();
		//获取结果开始评分
		List<Result> results = rate.getResults();
		for (Result result : results) {
			try {
				resultService.updateResult(result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
