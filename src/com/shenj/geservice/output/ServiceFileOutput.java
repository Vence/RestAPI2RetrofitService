package com.shenj.geservice.output;

import java.util.List;

import com.shenj.gservice.pojo.SJMethod;
import com.shenj.gservice.pojo.SJMethodParam;
import com.shenj.gservice.util.MainUtil;

/**
 * Service接口文件的内容
 * 
 * @author 王文路
 * @date 2015-8-26
 *
 */
public class ServiceFileOutput implements IFileOutput{

	@Override
	public String getFileContent(List<SJMethod> methods, String pojoName) {
		
		String fileName = pojoName + "Service";
		
		StringBuffer buf = new StringBuffer();
		
		// 包名
		buf.append("package " + pojoName.toLowerCase()).append(";\r\n\r\n");
		
		// 引入包
		buf.append("import java.util.List;").append("\r\n")
		.append("import retrofit.Callback;").append("\r\n")
		.append("\r\n");
		
		// 定义接口
		buf.append("public interface ").append(fileName).append("{");
		
		// 接口方法
		for (SJMethod method : methods) {
			buf.append("\r\n");
			buf.append(getMethodContent(method));
			buf.append("\r\n");
		}
		
		// 闭合
		buf.append("}");
		
		return buf.toString();
	}

	public String getMethodContent(SJMethod method) {
		
		StringBuffer buf = new StringBuffer();
		
		// 注释
		if (method.getNote() != null)
			buf.append(method.getNote()).append("\r\n");
		
		// 方法定义
		buf.append(MainUtil.INDENTATION).append("public void ")
		.append(method.getMethodName()).append("(");
		
		// 方法的参数
		for (SJMethodParam param : method.getParams()) {
			buf.append(param.getParamType()).append(" ").append(param.getValueName())
			.append(",");
		}
		
		// 方法参数 - 回调(固定)
		if(method.getHasReturn())
			buf.append("Callback<").append(method.getReturnType()).append("> cb);");
		else 
			buf.append("Callback").append(" cb);");
		
		return buf.toString();
	}

}
