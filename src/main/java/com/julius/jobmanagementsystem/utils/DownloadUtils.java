package com.julius.jobmanagementsystem.utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 下载工具类
 * 包括下载、文件夹打包等功能
 * @author tankai
 *
 */
public class DownloadUtils {

	/**
	 * 将文件夹所有文件打包成.zip文件
	 * @param sourceFilePath 源文件路径
	 * @param zipFilePath	压缩文件路径
	 * @param fileName	压缩文件文件名
	 * @return
	 */
	public static boolean fileToZip(String sourceFilePath,String zipFilePath,String fileName){  
        boolean flag = false;  
        File sourceFile = new File(sourceFilePath);  
        FileInputStream fis = null;
        BufferedInputStream bis = null;  
        FileOutputStream fos = null;  
        ZipOutputStream zos = null;  
          
        if(sourceFile.exists() == false){  
            System.out.println("待压缩的文件目录："+sourceFilePath+"不存在.");  
        }else{  
            try {  
            	//判断存放压缩文件的临时文件夹是否存在，不存在就创建
            	File tmpdir = new File(zipFilePath);
            	if(!tmpdir.exists()){
            		tmpdir.mkdir();
            	}
            	
                File zipFile = new File(zipFilePath + "/" + fileName);  
                if(zipFile.exists()){  
                    System.out.println(zipFilePath + "目录下存在名字为:" + fileName +".zip" +"打包文件.");  
                }else{  
                    File[] sourceFiles = sourceFile.listFiles();  
                    if(null == sourceFiles || sourceFiles.length<1){  
                        System.out.println("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");  
                    }else{  
                        fos = new FileOutputStream(zipFile);  
                        zos = new ZipOutputStream(new BufferedOutputStream(fos));  
                        byte[] bufs = new byte[1024*10];
                        for(int i=0;i<sourceFiles.length;i++){  
                        	if(sourceFiles[i].isDirectory()){
                        		continue;
                        	}
                            //创建ZIP实体，并添加进压缩包  
                            ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());  
                            zos.putNextEntry(zipEntry);  
                            //读取待压缩的文件并写进压缩包里  
                            fis = new FileInputStream(sourceFiles[i]);  
                            bis = new BufferedInputStream(fis, 1024*10);  
                            int read = 0;
                            while((read=bis.read(bufs, 0, 1024*10)) != -1){  
                                zos.write(bufs,0,read);  
                            }  
                        }  
                        flag = true;  
                    }  
                }  
            } catch (FileNotFoundException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            } catch (IOException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            } finally{  
                //关闭流  
                try {  
                    if(null != bis) bis.close();  
                    if(null != zos) zos.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                    throw new RuntimeException(e);  
                }  
            }  
        }  
        
        return flag;  
    }  
	
	public static void download(String filePath,String fileName, HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException{
		//得到该文件
        File file = new File(filePath+fileName);
        if(!file.exists()){
            System.out.println("不存在该文件!");            
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("<html><body><h2>Sorry!不存在该文件!</h2></body></html>");
            return;//文件不存在就退出方法
        }
        System.out.println(fileName);
        
        FileInputStream fileInputStream = new FileInputStream(file);
        
        //文件名转码
        fileName =  URLEncoder.encode(fileName, "UTF-8");
        
        response.setContentType("application/octet-stream;charset=utf-8");
        
        //获取发送请求的客户端的头信息
        String agent = (String)request.getHeader("USER-AGENT"); 
        
        //判断客户端的类型
        if(agent != null && agent.toLowerCase().indexOf("firefox") > -1)
        {
        	//设置Http响应头告诉浏览器下载这个附件
            response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" +fileName);
            System.out.println("firefox"+fileName);
        }
        else
        {
        	response.setHeader("Content-Disposition", "attachment;filename=" +fileName);
        }        
      
        //创建输出流，向客户端输出数据  
        OutputStream outputStream = response.getOutputStream();
        byte[] bytes = new byte[2048];
        int len = 0;
        while ((len = fileInputStream.read(bytes))>0){
            outputStream.write(bytes,0,len);
        }
        fileInputStream.close();
        outputStream.close();
	}
}
