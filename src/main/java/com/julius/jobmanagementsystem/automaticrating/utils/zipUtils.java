package com.julius.jobmanagementsystem.automaticrating.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class zipUtils {

	/**
	 * 解压.zip文件
	 * @param resultZip 压缩文件
	 * @param unzipPath 解压后的路径
	 * @throws Exception
	 */
	public static void unzip(File resultZip, String unzipPath) throws Exception {
		ZipFile zip = new ZipFile(resultZip,Charset.forName("GBK"));//解决中文文件夹乱码  
          
        File pathFile = new File(unzipPath);  
        if (!pathFile.exists()) {  
            pathFile.mkdirs();  
        }  
          
        for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements();) {  
            ZipEntry entry = (ZipEntry) entries.nextElement();  
            String zipEntryName = entry.getName();
            InputStream in = zip.getInputStream(entry);
            //文件解压后的输出路径
            String outPath = (unzipPath +File.separator+ zipEntryName);//.replaceAll("\\*", "/");  
              
            // 判断路径是否存在,不存在则创建文件路径  
            File file = new File(outPath.substring(0, outPath.lastIndexOf(File.separator)));  
            if (!file.exists()) {  
                file.mkdirs();  
            }  
            // 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压  
            if (new File(outPath).isDirectory()) {  
                continue;  
            }  
            // 输出文件路径信息  
//          System.out.println(outPath);  
  
            FileOutputStream out = new FileOutputStream(outPath);  
            byte[] buf1 = new byte[1024];  
            int len;  
            while ((len = in.read(buf1)) > 0) {  
                out.write(buf1, 0, len);  
            }  
            in.close();  
            out.close();  
        }  
        System.out.println("******************解压完毕********************");  
        return;  
	}
}
