package com.julius.jobmanagementsystem.utils;

//控制文件上传的路径
public class Config {

    //存放老师上传的题目Linux
    public static final String title = "/opt/task/teacher/upload/topic/";
    //存放学生提交的作业存放路径
    public static final String task = "/opt/task/student/homework/upload/";
    //评分文件生成目录
    public static final String IMPORT_INFO = "/opt/task/student/importInfo/";
    //临时存放打包压缩文件位置
    public static final String TEMP_COMPRESS_PATH = "/opt/task/temp/download/";

//
//    //存放老师上传的题目,本地测试windows
//    public static final String title = "C:/task/teacher/upload/topic/";
//    //存放学生提交的作业存放路径
//    public static final String task = "C:/task/student/homework/upload/";
//    //评分文件生成目录
//    public static final String IMPORT_INFO = "C:/task/student/importInfo/";
//    //临时存放打包压缩文件位置
//    public static final String TEMP_COMPRESS_PATH = "C:/task/temp/download/";


    //项目根路径
//	public static String bacePath = System.getProperty("user.dir");
    //停用词文件路径
    public static final String stopWordPath = Config.class.getResource("/defaultDic/stopwords.txt").getPath();
    //用户词表的存放路径
    public static final String userWordsPath = Config.class.getResource("/defaultDic/userwords.txt").getPath();
    //Canopy聚类结果文件路径
    public static final String CANOPY_RESULT_PATH = "result/cluster/canopy/";
    //KMeans聚类结果文件路径
    public static final String KMEANS_RESULT_PATH = "result/cluster/kmeans/";
}
