package com.shenj.geservice.output;

import java.util.List;

import com.shenj.gservice.pojo.SJMethod;

public interface IFileOutput {
	
	public String getFileContent(List<SJMethod> methods , String pojoName);
	
}
