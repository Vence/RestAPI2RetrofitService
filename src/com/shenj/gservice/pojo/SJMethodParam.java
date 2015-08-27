package com.shenj.gservice.pojo;

public class SJMethodParam {
	
	private String queryType ;		//查询类型， Path/ Query
	private String paramType;	// 参数类型
	private String keyName;		// 参数名
	private String valueName;	// 参数
	private Boolean required;	// 是否必須項
	
	
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getParamType() {
		return paramType;
	}
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public String getValueName() {
		return valueName;
	}
	public void setValueName(String valueName) {
		this.valueName = valueName;
	}
	public Boolean getRequired() {
		return required;
	}
	public void setRequired(Boolean required) {
		this.required = required;
	}
	

}
