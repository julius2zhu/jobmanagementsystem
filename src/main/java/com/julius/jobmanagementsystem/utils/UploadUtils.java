package com.julius.jobmanagementsystem.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UploadUtils {
	
	//上传
		public boolean uploadUtils(MultipartFile[] uploadfile,String road)
		{
			if(uploadfile!=null && uploadfile.length > 0) 
			{
				for(MultipartFile file:uploadfile)
				{
					
					//设置上传文件位置
	        		String uploadpath = file.getOriginalFilename();  //获取文件名
	        		System.out.println(uploadpath);
	                //创建文件夹
	        	    File uploadtargetFile = new File(road,uploadpath);
					//判断文件是否存在
	        	    isExists(uploadtargetFile);
					//保存文件
					if (saveFile(file, uploadtargetFile)) {	
						
					}
				    
				}
				return true;
			}
			
			return false;
		}
		
		//判断该路径下文件是否存在
		private boolean isExists(File uploadtargetFile)
		{
			if (!uploadtargetFile.exists())
		    {
		    	uploadtargetFile.mkdirs(); 
		    	return true;
			}
		    else{
		    	System.out.println("文件已存在");
		    	return false;
		    }
		}
		
		//保存文件
		private boolean saveFile(MultipartFile file, File uploadtargetFile)
		{  
	        // 判断文件是否为空  
	        if (!file.isEmpty()) {  
	            try {  
	        	    file.transferTo(uploadtargetFile);  //写入文件
	                return true;  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        return false;  
	    }

		//以数组的形式返回上传文件的文件名，包括后缀
		public List<String> upload(MultipartFile[] uploadfile,String road)
		{
			List<String> result=new ArrayList<String>();
			if(uploadfile!=null && uploadfile.length > 0) 
			{
				for(MultipartFile file:uploadfile)
				{
					
					//设置上传文件位置
	        		String uploadpath = file.getOriginalFilename();  //获取文件名
	        		System.out.println("hahahah"+uploadpath);  
	        		System.out.println("&&&&"+uploadpath);  
	                //创建文件夹
	        	    File uploadtargetFile = new File(road,uploadpath);
					//判断文件是否存在
	        	    isExists(uploadtargetFile);
					//保存文件
					if (saveFile(file, uploadtargetFile)) {	
						
						result.add(uploadpath);
					}
				    
				}
				
			}
			
			return result;
		}
}
