package com.shenj.gservice.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.shenj.gservice.pojo.SJMethod;
import com.shenj.gservice.pojo.SJMethodParam;
import com.shenj.gservice.reader.SJFileReader;
import com.shenj.gservice.reader.JavaFileReader;

/**
 * 方法体分离
 * 
 * @author 王文路
 * @date 2015-8-26
 *
 */
public class MethodSeparateUtil {
	
	private String methodContent;

	public MethodSeparateUtil(String methodContent) {
		super();
		this.methodContent = methodContent;
	}
	
	 Pattern bracketPattern = Pattern.compile("(?<=\\()[^\\)]+");  
	 Pattern quotationPattern =  Pattern.compile("(?<=\")[^\"]+");  //Pattern.compile("\"[\\S]*\"");  
	
	public SJMethod getMethod(){
		
		SJMethod method = new SJMethod();
		method.setRequestMethod(getRequestMethod());
		method.setNote(getNote());
		method.setParams(getParams());
		method.setReturnType(getReturnType());
		method.setRequestUrl(getRequestUrl());
		method.setMethodName(getMethodName());
		
		return method;
		
	}
	
	/**
	 * 获取注释
	 * @author 王文路
	 * @date 2015-8-26
	 * @return
	 */
	private String getNote(){
		int endIndex = methodContent.indexOf("@RequestMapping");
		String cont = methodContent.substring(0 , endIndex);
		
		int start = cont.indexOf("/**");
		int end = cont.indexOf("*/");
		
		if (start == -1 || end == -1)
			return null;
		
		return MainUtil.INDENTATION + cont.substring(start , end + 2);
	}
	
	/**
	 * 获取url
	 * @author 王文路
	 * @date 2015-8-26
	 * @return
	 */
	private String getRequestUrl(){
		
		String cont = methodContent.substring(0 , methodContent.indexOf("public"));
		
		Matcher matcher = bracketPattern.matcher(cont);
		
		String path = "";
		while(matcher.find()) {
			path = matcher.group();
		}
		
		Matcher quotationMatcher = quotationPattern.matcher(path);
		String url = "";
		while (quotationMatcher.find()){
			url = quotationMatcher.group();
			return url;
		}
		
		return null;
	}
	
	/**
	 * 获取方法
	 * @author 王文路
	 * @date 2015-8-26
	 * @return
	 */
	private String getRequestMethod(){
		
		String cont = methodContent.substring(0 , methodContent.indexOf("public"));
		
		Matcher matcher = bracketPattern.matcher(cont);
		
		String path = "";
		while(matcher.find()) {
			path = matcher.group();
		}
		
		int methodIndex = path.lastIndexOf("RequestMethod.");
		if (methodIndex == -1)
			return "GET";
		
		String met = path.substring(methodIndex + "RequestMethod.".length());
		
		if ("POST".equals(met))
			return "POST";
		else if ("GET".equals(met)) 
			return "GET";
		else if ("PUT".equals(met)) 
			return "PUT";
		else if ("DELETE".equals(met)) 
			return "DELETE";
		else return null;
		
	}
	
	/**
	 * 获取返回类型
	 * @author 王文路
	 * @date 2015-8-26
	 * @return
	 */
	private String getReturnType (){
		int resultIndex = methodContent.indexOf("ResultInfo");
		
		if (resultIndex == -1)
			return "void";
		
		int endIndex = MainUtil.getLastMappingSignIndex(methodContent, resultIndex, '<', '>');
		
		return methodContent.substring(resultIndex + "ResultInfo".length() + 1  , endIndex );
	}

	/**
	 * 获取方法名
	 * 
	 * @author 王文路
	 * @date 2015-8-26
	 * @return
	 */
	private String getMethodName(){
		
		int resultIndex = methodContent.indexOf("ResultInfo");
		int endIndex = -1;
		int methodNameIndex = -1 ;
		String ret = "";
		
		if (resultIndex == -1){
			endIndex =  methodContent.indexOf("void");
			methodNameIndex = methodContent.indexOf("(", endIndex);
			ret =  methodContent.substring(endIndex + "void".length() , methodNameIndex); 
		}
		else  {
			endIndex = MainUtil.getLastMappingSignIndex(methodContent, resultIndex, '<', '>');
			methodNameIndex = methodContent.indexOf("(", endIndex);
			ret =  methodContent.substring(endIndex +1 , methodNameIndex); 
		}
		
		return ret.trim();
	}
	
	/**
	 * 获取参数集合
	 * 
	 * @author 王文路
	 * @date 2015-8-26
	 * @return
	 */
	private List<SJMethodParam> getParams(){
		
		List<SJMethodParam> rets = new ArrayList<SJMethodParam>();
		
		
		int startIndex = methodContent.indexOf("public");
		
		if (startIndex == -1)
			return rets;
		
		int endIndex = methodContent.indexOf("{" , startIndex);
		
		String cont = methodContent.substring(startIndex , endIndex);
	
		cont = cont.substring(cont.indexOf("(") + 1, cont.lastIndexOf(")"));

		String[] paramStrs = cont.split(",");
		
		for (int i = paramStrs.length - 1 ; i >= 0 ; i--) {
			
			String temp = "";
			
			String queryType ;
			String keyName;
			String valueName;
			String paramType;
			Boolean required = true;
			if (paramStrs[i].indexOf("@PathVariable") != -1) {
				queryType = "Path";
			}else if (paramStrs[i].indexOf("@RequestParam") != -1) {
				queryType = "Query";
			} else {
				if (paramStrs[i].indexOf("required") != -1){
					paramStrs[i - 1] += paramStrs[i]; 
				}
				continue;
			}

			Matcher matcher = quotationPattern.matcher(paramStrs[i]);
			String params = "";
			while(matcher.find()) {
				params = matcher.group();
				break;
			}
			
			if (paramStrs[i].indexOf("required") != -1)
				required = false;
				
			keyName = params;
			String[] que = paramStrs[i].split("\\s+|\\)");
			paramType = que[que.length - 2];
			valueName = que[que.length - 1];
				
			SJMethodParam p = new SJMethodParam();
			p.setKeyName(keyName);
			p.setParamType(paramType);
			p.setQueryType(queryType);
			p.setRequired(required);
			p.setValueName(valueName);
			
			rets.add(p);
				
		}
		
		return rets;
		
	}
	
	public static void main(String[] args) {
		SJFileReader reader = new JavaFileReader("D:\\DomainAPIController.java");
		String content = reader.readString();
		
		ContentSeparateUtil util = new ContentSeparateUtil(content);
		List<String> rets = util.getMethodContents();
		
		String cont = rets.get(0);
		
		MethodSeparateUtil methodUtil = new MethodSeparateUtil(cont);
		
		
		SJMethod method = methodUtil.getMethod();
		
		System.out.println(cont);
		System.out.println(method.getMethodName());
		System.out.println(method.getNote());
		System.out.println(method.getRequestMethod());
		System.out.println(method.getRequestUrl());
		System.out.println(method.getReturnType());
		
		List<SJMethodParam> params = method.getParams();
		
		for (SJMethodParam p : params) {
			System.out.print(p.getQueryType() + " ");
			System.out.print(p.getKeyName() + " ");
			System.out.print(p.getRequired() + " ");
			System.out.print(p.getParamType() + " ");
			System.out.print(p.getValueName() + " ");
			
			System.out.println();
		}
		
	}
}
