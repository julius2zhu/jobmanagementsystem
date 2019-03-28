package com.julius.jobmanagementsystem.automaticrating;

import com.julius.jobmanagementsystem.domain.entity.Result;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GetResultOfAllStudent {

	private List<Result> results;

	public GetResultOfAllStudent() {
		results = new ArrayList<Result>();
	}

	// 获得filepath这个文件的所有子文件夹
	private List<String> getALLFile(String filePath) {
		List<String> list = new ArrayList<>();
		File f = new File(filePath);
		if (f.isDirectory()) // 如果filePath是路径
		{
			File[] fList = f.listFiles();
			for (File file : fList) {
				list.add(file.getPath());
			}

		}
		return list;

	}


	public List<Result> getResults() {
		return results;
	}

}
