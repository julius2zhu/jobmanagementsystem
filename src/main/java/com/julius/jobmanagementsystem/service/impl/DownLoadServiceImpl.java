package com.julius.jobmanagementsystem.service.impl;

import com.julius.jobmanagementsystem.domain.entity.Result;
import com.julius.jobmanagementsystem.domain.entity.Student;
import com.julius.jobmanagementsystem.domain.entity.Task;
import com.julius.jobmanagementsystem.domain.repository.ResultDao;
import com.julius.jobmanagementsystem.domain.repository.StudentDao;
import com.julius.jobmanagementsystem.domain.repository.TaskDao;
import com.julius.jobmanagementsystem.service.DownLoadService;
import com.julius.jobmanagementsystem.utils.Common;
import com.julius.jobmanagementsystem.utils.Config;
import com.julius.jobmanagementsystem.utils.DownloadUtils;
import com.julius.jobmanagementsystem.utils.ExcelUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;
import java.util.Map.Entry;

@Service("downLoadService")
@Transactional
public class DownLoadServiceImpl implements DownLoadService {
    private final Logger logger = LogManager.getLogger(getClass());
    @Autowired
    private ResultDao resultDao;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private StudentDao studentDao;

    @Override
    public void downloadTask(final int taskId,
                             final HttpServletRequest request,
                             final HttpServletResponse response) {

        //根据作业id在数据库中获取作业实体对象
        Task task = taskDao.selectByTaskId(taskId);
        //获取作业绝对路径
        String taskPath = Config.title;
        //将作业进行压缩再进行下载
        String zipPath = Config.IMPORT_INFO;

        //文件夹不存在创建
        File zipPathExists = new File(zipPath);
        if (!zipPathExists.exists()) {
            zipPathExists.mkdirs();
        }
        String zipFileName = task.getTaskName() + ".zip";
        File file = new File(zipPath + zipFileName);
        //判断临时目录有没有该作业，有的话删除
        if (file.exists()) {
            System.out.println("文件存在？" + file.exists());
            file.delete();
        }
        DownloadUtils.fileToZip(taskPath + taskId, zipPath, zipFileName);
        try {
            DownloadUtils.download(zipPath, zipFileName, request, response);
        } catch (Exception e) {
            logger.error("error:{}", e);
        }

    }

    @Override
    public void downloadResult(final int taskId, String stuId,
                               final HttpServletRequest request,
                               final HttpServletResponse response) {
        //作业的文件存放路径
        String basePath = Config.task;

        //所有作业的压缩文件的临时存放路径
        String zipPath = Config.IMPORT_INFO;

        //文件夹不存在则创建
        File pathExists = new File(basePath);
        if (!pathExists.exists()) {
            pathExists.mkdirs();
        }
        File zipPathExists = new File(zipPath);
        if (!zipPathExists.exists()) {
            zipPathExists.mkdirs();
        }

        File resultDir = new File(basePath + taskId + "/" + stuId);
        logger.debug(resultDir);
        if (!resultDir.exists()) {
            logger.debug("作业不存在");
            String message = "<html><body><h2>Sorry!不存在该作业文件!</h2></body></html>";
            Common.commonShow(null, response, message);
            return;
        }

        Task task = taskDao.selectByTaskId(taskId);

        String zipFileName = task.getTaskName() + "_" + stuId + ".zip";
        File file = new File(zipPath + zipFileName);
        //判断临时目录有没有该作业，有的话删除
        if (file.exists()) {
            System.out.println("文件存在？" + file.exists());
            file.delete();
        }
        DownloadUtils.fileToZip(basePath + taskId + "/" + stuId, zipPath, zipFileName);
        try {
            DownloadUtils.download(zipPath, zipFileName, request, response);
        } catch (Exception e) {
            logger.error("error:{}", e);
        }
    }

    @Override
    public void downloadAllResults(List<Integer> taskIdList,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        //所有学生的集合
        List<Student> stuList = new ArrayList<>();

        //存放作业名的列表
        List<String> tasknameList = new ArrayList<String>();
        //学生-作业（多个）分数 Map
        Map<Student, List<Integer>> stuScoreMap = new HashMap<>();

        //取第一个ID查询作业结果为了获取学生ID
        stuList = studentDao.selectAllStu();
        //遍历学生
        for (Student s : stuList) {
            Integer stuId = s.getStuId();
            //分数列表
            List<Integer> scoreList = new ArrayList<>();
            for (int taskId : taskIdList) {
                Result r = new Result();
                r.setStuId(stuId);
                r.setTaskId(taskId);
                if (stuList.indexOf(s) == 0) {
                    tasknameList.add(taskDao.selectByTaskId(taskId).getTaskName());
                }
                Integer score = 0;
                if (resultDao.selectByPrimaryKey(r) != null) {
                    score = resultDao.selectByPrimaryKey(r).getScore();
                    if (score == null) {
                        score = 0;
                    }
                }
                scoreList.add(Integer.valueOf(score));
            }
            stuScoreMap.put(s, scoreList);
        }

        String excelPath = Config.IMPORT_INFO;
        //判断文件夹不存在创建
        File file = new File(excelPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String excelName = "学生成绩.xls";
        //创建成绩excel表
        toExcel(tasknameList, stuScoreMap, excelPath + excelName);
        try {
            //下载excel文件
            DownloadUtils.download(excelPath, excelName, request, response);
        } catch (Exception ex) {
            String message = "<html><body><h2>Sorry!出错啦!</h2></body></html>";
            Common.commonShow(ex, response, message);
        }
    }

    public void toExcel(List<String> tasknameList, Map<Student, List<Integer>> stuScoreMap, String path) {

        int colSize = tasknameList.size();
        System.out.println("colSize:" + colSize);

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("学生成绩");

        //存放表格数据的二维数组
        Object[][] value = new Object[stuScoreMap.size() + 1][2 + colSize];

        //表头数据存入value数组---表格第一行
        value[0][0] = "学号";
        value[0][1] = "姓名";
        for (int m = 2; m < colSize + 2; m++) {
            value[0][m] = tasknameList.get(m - 2);
            System.out.println("表头：" + value[0][m]);
        }

        //从表格第二行开始，遍历map，将学生及作业分数保存到value数组
        int i = 1;//行数i
        Iterator iter = stuScoreMap.entrySet().iterator();
        while (iter.hasNext()) {
            Entry entry = (Entry) iter.next();
            Student s = (Student) entry.getKey();
            List<Integer> scoreList = (List) entry.getValue();
            System.out.println(scoreList.size());
            value[i][0] = s.getStuId();
            value[i][1] = s.getStuName();

            for (int j = 0; j < scoreList.size(); j++) {
                value[i][j + 2] = scoreList.get(j);

            }
            //行数i自增
            i++;
        }
        //把数据写入到Excel
        ExcelUtil.writeArrayToExcel(wb, sheet, stuScoreMap.size() + 1, colSize + 2, value);

        ExcelUtil.writeWorkbook(wb, path);
//	     ExcelUtil.writeToExcel(path, stuScoreMap.size() + 1, colSize+2, value);
    }
}
