package com.julius.jobmanagementsystem.automaticrating.algorithm.kmeans;

import com.julius.jobmanagementsystem.automaticrating.distance.CosDistance;
import com.julius.jobmanagementsystem.automaticrating.utils.ClusterUtil;
import com.julius.jobmanagementsystem.automaticrating.utils.VectorUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * KMeans聚类算法
 *
 * @author tankai
 */
@Setter
@Getter
public class KMeans {
    //原始文本对应向量
    private List<double[]> vectors;

    //聚类结果对应的下标
    private List<List<Integer>> resultIndex;

    //K个聚类中心点
    private List<double[]> centers;

    private List<double[]> newcenters;

    //KMeans初始K值
    private int K = 0;

    //迭代次数
    private int iterTimes = 10;

    //用于相似度计算的类
    private CosDistance cosDistance;

    //构造函数，进行初始化
    public KMeans(int k, List<double[]> vectors, int times) {
        //初始化K
        setK(k);
        setVectors(vectors);
        setIterTimes(times);

        centers = new ArrayList<>();
        resultIndex = new ArrayList<>();

        cosDistance = new CosDistance(vectors);
    }

    //初始化K个初始聚类中心点--随机选择K个向量
    public void initClusters() {
        //得到随机数
        Vector<Integer> vecIndex = random(K, vectors.size());
        for (int i = 0; i < K; i++) {
            //生成的第i个随机数对应的向量作为第i个中心
            centers.add(vectors.get(vecIndex.get(i)));
            //存放下标
            List<Integer> cluster = new ArrayList<>();
            resultIndex.add(cluster);
        }
    }

    /**
     * KMeans聚类
     */
    public void cluster() {
        //初始化聚类中心点、结果集
        initClusters();

        //记录最大的相似度
        double maxSim = 0f;
        int tmpIndex = 0;
        //新中心点
        newcenters = centers;
        //开始迭代
        while (iterTimes > 0) {
            iterTimes--;
            //遍历所有向量，
            for (int i = 0; i < vectors.size(); i++) {
                double[] vector = vectors.get(i);
                //相似度置0
                maxSim = 0;
                //依次计算文本向量集中的向量与k个类簇中心的距离
                for (int j = 0; j < K; j++) {
                    if (cosDistance.caculate(vector, newcenters.get(j)) > maxSim) {
                        maxSim = cosDistance.caculate(vector, newcenters.get(j));
                        tmpIndex = j;
                    }
                }
                //把文本向量集中的每一个向量对应的索引加入最近的类簇中心集合中
                resultIndex.get(tmpIndex).add(i);

            }
            //重新计算每个类簇的类簇中心
            for (int i = 0; i < resultIndex.size(); i++) {
                List<Integer> cluster = resultIndex.get(i);
                double[] sum = new double[vectors.get(0).length];
                for (int j : cluster) {
                    sum = VectorUtil.add(sum, vectors.get(j));
                }
                newcenters.set(i, VectorUtil.center(sum, cluster.size()));
            }
            //判断新簇中心相对于旧中心移动的距离是否在条件内
            if (!centerMove(centers, newcenters)) {
                centers = newcenters;
                break;
            }
            //形成新的类簇中心
            centers = newcenters;
            //清除掉结果集里的数据，继续迭代
            resultIndex.clear();
        }

        resultIndex = ClusterUtil.delNullCluster(resultIndex);
    }

    /**
     * 判断聚类中心点是否移动
     * 新中心和旧中心相似度小于T=95%，return true
     * 新中心和旧中心相似度大于等于T=95%，return false
     *
     * @param centers
     * @param newcenters
     * @return true--移动
     */
    private boolean centerMove(List<double[]> centers, List<double[]> newcenters) {
        // TODO Auto-generated method stub
        double T = 0.95f;

        double sum = 0f;
        for (int i = 0; i < K; i++) {
            sum += cosDistance.caculate(centers.get(i), newcenters.get(i));
        }
        if (sum / centers.size() < T) {
            System.out.println(sum / centers.size());
            return true;
        }
        return false;
    }

    /**
     * 产生一组随机数作为K个聚类中心的下标
     *
     * @param K
     * @param size 向量总数
     * @return
     */
    private Vector<Integer> random(int K, int size) {
        //创建一个产生随机数的对象
        Random r = new Random();
        //创建一个存储随机数的集合
        Vector<Integer> v = new Vector<Integer>();
        //定义一个统计变量
        int count = 0;
        while (count < K) {
            int number = r.nextInt(size);
            //判断number是否在集合中存在
            if (!v.contains(number)) {
                //不在集合中，就添加
                v.add(number);
                count++;
            }
        }
        return v;
    }
}
