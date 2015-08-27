package com.shenj.gservice.pojo;

import java.util.List;

public class SJMethod {
	
	private String note;	// 注释	
	private String requestUrl;		// rest api
	private String requestMethod;	// 请求方法
	private String methodName;
	private Boolean hasReturn;		// 是否有返回值
	private String returnType;		// 返回类型
	private List<SJMethodParam> params;	// 参数

	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Boolean getHasReturn() {
		
		if (this.returnType != null && !this.returnType.equals("void"))
			return true;
		else
			return false;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public List<SJMethodParam> getParams() {
		return params;
	}

	public void setParams(List<SJMethodParam> params) {
		this.params = params;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public void setHasReturn(Boolean hasReturn) {
		this.hasReturn = hasReturn;
	}
	
	
}

