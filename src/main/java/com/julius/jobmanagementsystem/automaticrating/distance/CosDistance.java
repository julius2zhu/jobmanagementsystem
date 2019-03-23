package com.julius.jobmanagementsystem.automaticrating.distance;

import com.julius.jobmanagementsystem.automaticrating.utils.VectorUtils;
import java.util.List;

public class CosDistance {
	//向量集合（存放分词后所用来相似度计算的向量）
	private List<double[]> vectors;
	//相似度矩形（以下标为坐标存储两向量间的相似度如：matrix[i][j]就是i向量与j向量的相似度，其中i，j为向量集合vectors中向量存放的顺序）
	private double[][] matrix;	
	//初始化
	public CosDistance(List<double[]> list) {
		this.vectors = list;
		init();
	}
	
	//初始化操作，用来初始化相似度矩形，对角线上的设为1，其余的为计算后向量间的相似度值，<i,j>与<j,i>相似度值一样所以该矩阵是一个对称矩阵
	private void init() {		
		matrix = new double[vectors.size()][vectors.size()];
		for (int i = 0; i < vectors.size(); i++) {
			for (int j = i; j < vectors.size(); j++) {
				if(i == j){
					matrix[i][j] = 1;
					continue;
				}
				//计算i向量与j向量的相似度
				matrix[i][j] = caculate(vectors.get(i),vectors.get(j));
				
				//输出相似度矩形
//				DecimalFormat df = new DecimalFormat("#0.000000");
//				System.out.print(df.format(matrix[i][j]) +"  ");
				matrix[j][i] = matrix[i][j];
			}
//			System.out.println();
		}
	}
//获取指定向量之间的相似度（i,j是向量在list集合中的位置）	
	public double getDistance(int i, int j){		
		return matrix[i][j];
	}

//计算两个给定向量的相似度
	public double caculate(double[] vec1, double[] vec2) {
		return VectorUtils.multiply(vec1, vec2)/(VectorUtils.module(vec1)*VectorUtils.module(vec2));
	}
//计算一个向量与一个向量集合的相似度，及计算该向量与集合中向量相似度的平均值（向量来源是通过下标得到的）
	public double getDistance(int index, List<Integer> list) {
		double sum = 0;
		for (int i = 0; i < list.size(); i++) {
			sum += getDistance(index, list.get(i));
		}
		return sum/list.size();
	}
//计算一个向量与一个向量集合的相似度，及计算该向量与集合中向量相似度的平均值
	public double getDistance(double[] vector, List<double[]> list) {
		double sum = 0;
		for (int i = 0; i < list.size(); i++) {
			sum += caculate(vector,list.get(i));
		}
		return sum/list.size();
	}
	
	/**
	 * 获取Canopy阈值
	 * 即所有向量相似度的平均值
	 * @return
	 */
	public double getThreshold(){
		double sum = 0f;
		int n = matrix.length;
		for(int i = 0 ; i < n ; i++){
			
			for(int j = 0 ; j < i ; j++){
				sum += matrix[i][j];
			}
		}
		
		return sum / ( n * (n-1)/2);
	}
}
