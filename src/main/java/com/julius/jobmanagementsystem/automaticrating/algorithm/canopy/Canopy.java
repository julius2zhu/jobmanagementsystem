package com.julius.jobmanagementsystem.automaticrating.algorithm.canopy;

import com.julius.jobmanagementsystem.automaticrating.distance.CosDistance;
import com.julius.jobmanagementsystem.automaticrating.utils.ClusterUtil;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

/**
 * Canopy聚类算法
 * @author
 *
 */
@Setter
@Getter
public class Canopy {	
	//原始文本对应向量
	private List<double[]> vectors;
	
	//聚类结果对应的下标
	private List<List<Integer>> resultIndex;

	//Canopy初始阈值
	private double T = 0f;
	
	//聚类结果类别数量
	private int canopy = 0;
	
	//用于相似度计算的类
	private CosDistance cosDistance;
	
	//canopy算法 
	public void cluster(){		
		//初始化cosDistance类
		cosDistance = new CosDistance(vectors);
		if(T == 0 ){
			setT(cosDistance.getThreshold());	
		}
		//初始化聚类结果集
		resultIndex = new ArrayList<List<Integer>>();
		List<Integer> tmpIndex = null;		
		//遍历向量集合
		for(int i = 0 ; i < vectors.size() ; i++){
			//i = 0 一个类都没有时，直接添加进resultIndex。
			if(i == 0){
				//把第1个向量的索引添加到tmpIndex，作为第一个类
				tmpIndex = new ArrayList<Integer>();
				tmpIndex.add(i);
				//把第一个类加入到resultIndex
				resultIndex.add(tmpIndex);
				continue;
			}			
			//找到符合相似度要求的类的标志
			boolean isFind = false;
			
			for(int j = 0 ; j < resultIndex.size() ; j++){
				//计算向量与已分的类的向量平均值是否大于阈值，大于则添加到该类
				if(cosDistance.getDistance(i, resultIndex.get(j)) > T){
					//获取待比较的那个类的所有元素的索引， 存放在tmpIndex
					tmpIndex = resultIndex.get(j);
					
					//把i(对应向量的索引值)加入到tmpIndex
					tmpIndex.add(i);
					
					//从resultIndex里删除旧的待比较的那个类
					resultIndex.remove(j);
					
					//添加加入了i的新类到resultIndex
					resultIndex.add(tmpIndex);
					//已加入相似类，退出当前循环
					isFind = true;
					break;
				}
			}			
			//与前面的类相似度都不符合要求则新建一个类
			if(!isFind){
				tmpIndex = new ArrayList<Integer>();
				tmpIndex.add(i);
				resultIndex.add(tmpIndex);
			}
		}
		resultIndex = ClusterUtil.delNullCluster(resultIndex);
		//获取聚类的数量
		canopy = resultIndex.size();
		
	}
}
