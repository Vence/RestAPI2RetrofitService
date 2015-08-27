package com.shenj.gservice.util;

import java.util.Stack;

public class MainUtil {
	
	public static final String INDENTATION =  "	 ";

	/**
	 * 获取文件名
	 * @author 王文路
	 * @date 2015-8-25
	 * @param path
	 * @return
	 */
	public static String getFileName(String path){
		
		path = path.replaceAll("\\\\", "/");
		
		return path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
	}
	
	/**
	 * 获取文件后缀名 ，不包含点(.)
	 * @author 王文路
	 * @date 2015-8-25
	 * @param path
	 * @return
	 */
	public static String getFilenameExtension(String path){
		
		return path.substring(path.lastIndexOf(".") + 1);
	}
	
	/**
	 * 根据文件名获取POJO(指定命名规则下)
	 * 
	 * @author 王文路
	 * @date 2015-8-25
	 * @param path	文件路径
	 * @return
	 */
	public static String getPojoName(String path){
		
		String fileName = getFileName(path);
		
		if (fileName.toLowerCase().indexOf("test")!= -1 
				|| fileName.indexOf("ViewController")!= -1)
			return null;
		
		int endIndex = - 1;
		
		endIndex = fileName.lastIndexOf("API");
		
		if (endIndex == -1) {
			endIndex = fileName.lastIndexOf("Controller");
			
			if (endIndex == -1)
				return null;
		}
		
		return  fileName.substring(0 , endIndex);
	}
	
	/**
	 * 获取匹配的最后一个 符号
	 * 使用堆栈
	 * 
	 * @author 王文路
	 * @date 2015-8-26
	 * @param start
	 * @return
	 */
	public static int getLastMappingSignIndex(String content ,int start, char leftSign , char rightSign){
		
		int startIndex = content.indexOf(leftSign , start);
		
		char[] chars = content.toCharArray();
		
		int endIndex = -1;
		
		Stack<TokenType> symbols = new Stack<TokenType>();
		Stack<Integer> indexes = new Stack<Integer>();
	
		symbols.push(TokenType.leftBracket);
		indexes.push(startIndex);
		
		for (int i = startIndex + 1 ; i < chars.length ; ++i) {
			
			if (symbols.isEmpty()) {
				break;
			}
			
			if (chars[i] == leftSign) {
				symbols.push(TokenType.leftBracket);
				indexes.push(i);
			} else if (chars[i] == rightSign) {
				TokenType token = symbols.peek();
				
				if (token == TokenType.leftBracket) {
					
					symbols.pop();
					
					if (symbols.isEmpty())  {
						endIndex = i;
					} 
					
					indexes.pop();
					
				} else {
					symbols.push(TokenType.rightBracket);
					indexes.push(i);
				}
			}
		
		}
	
		return endIndex;
	}
	
}

enum TokenType {
	leftBracket,
	rightBracket
}
