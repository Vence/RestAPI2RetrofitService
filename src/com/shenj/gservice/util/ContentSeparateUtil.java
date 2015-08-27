package com.shenj.gservice.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import com.shenj.gservice.reader.SJFileReader;
import com.shenj.gservice.reader.JavaFileReader;

/**
 * 将文件内容分离出独立的方法体
 * 
 * @author 王文路
 * @date 2015-8-26
 *
 */
public class ContentSeparateUtil {
	
	/**
	 * 注释的阀值， 如果注释位置距离方法体在 NOTE_THRESHOLD 内，就认为是该方法的注释
	 */
	private static final int NOTE_THRESHOLD = 150 ; 
	
	private String content;
	private List<String> methodContents;
	
	public ContentSeparateUtil(String content) {
		super();
		this.content = content;
		methodContents = new ArrayList<String>();
		this.setMethodContent();
	}
	
	public List<String> getMethodContents() {
		return methodContents;
	}

	private  void setMethodContent() {
		
		int startIndex = content.indexOf("@RequestMapping");
		if (startIndex == -1)
			return;
		
		String cont = getOneMethodContent(startIndex);
		
		if (content != null)
			methodContents.add(cont);
		
		content = content.replace(cont, "");
		
		setMethodContent();
	}

	/**
	 * 获取每个方法主题的内容
	 * 
	 * @author 王文路
	 * @date 2015-8-26
	 * @param startIndex
	 * @return
	 */
	private String getOneMethodContent(int startIndex){
		
		String preContent = content.substring(0 , startIndex);
		
		int noteIndex =  preContent.lastIndexOf("	/**");
		
		if (noteIndex != -1 && startIndex - noteIndex < NOTE_THRESHOLD )
			startIndex = noteIndex;
		
		int publicIndex = content.indexOf("public" ,startIndex +1 ) ;
		
		int endIndex = MainUtil.getLastMappingSignIndex(content, publicIndex, '{', '}');
		
		if (endIndex == -1)
			return null;
		
		return content.substring(startIndex , endIndex  + 1);
	}
	
	public static void main(String[] args){
		
		SJFileReader reader = new JavaFileReader("D:\\DomainAPIController.java");
		String content = reader.readString();
		
		ContentSeparateUtil util = new ContentSeparateUtil(content);
		List<String> rets = util.getMethodContents();
		
		for (String ret : rets) {
			System.out.println(ret);
			
			System.out.println("/******************************/");
		}
	}
	
}

