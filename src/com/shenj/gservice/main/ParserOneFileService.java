package com.shenj.gservice.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.shenj.geservice.output.HttpServiceFileOutput;
import com.shenj.geservice.output.IFileOutput;
import com.shenj.geservice.output.ServiceFileOutput;
import com.shenj.geservice.output.ServiceImplFileOutput;
import com.shenj.gservice.pojo.SJMethod;
import com.shenj.gservice.reader.SJFileReader;
import com.shenj.gservice.reader.JavaFileReader;
import com.shenj.gservice.util.ContentSeparateUtil;
import com.shenj.gservice.util.MethodSeparateUtil;
import com.shenj.gservice.writer.SJFileWriter;
import com.shenj.gservice.writer.JavaFileWriter;

public class ParserOneFileService {
	
	private String filePath;
	private String outputPath;
	private String pojoName;
	
	private List<SJMethod> methods;

	public ParserOneFileService(String filePath, String outputPath,
			String pojoName) {
		super();
		this.filePath = filePath;
		this.outputPath = outputPath;
		this.pojoName = pojoName;
		
		methods = getMethods();
	}
	
	/**
	 * 导出成文件
	 * @author 王文路
	 * @date 2015-8-27
	 * @return
	 */
	public boolean outputOne(){
		
		outputPath = outputPath.replaceAll("\\\\", "/");
		outputPath += "/" + pojoName.toLowerCase();
		
		File file = new File(outputPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		
		String httpService = outputPath + "/"  + pojoName + "HttpService.java";
		String servie = outputPath + "/"  +  pojoName  + "Service.java";
		String serviceImpl = outputPath + "/"  +  pojoName + "ServiceImpl.java";
		
		SJFileWriter writer  = new JavaFileWriter(httpService  ,  getHttpServiceContent());
		boolean b1 = writer.writeToFile();
		
		writer  = new JavaFileWriter(servie  ,  getServiceContent());
		boolean b2 =writer.writeToFile();
		
		writer  = new JavaFileWriter(serviceImpl  ,  getServiceImplContent());
		boolean b3 =writer.writeToFile();
		
		return b1 && b2 && b3;
	}
	
	/**
	 * 解析出HttpService内容
	 * @author 王文路
	 * @date 2015-8-27
	 * @return
	 */
	private String getHttpServiceContent(){
		
		IFileOutput output = new HttpServiceFileOutput();
		
		return output.getFileContent(methods, pojoName);
		
	}
	
	/**
	 * 解析出Service内容
	 * @author 王文路
	 * @date 2015-8-27
	 * @return
	 */
	private String getServiceContent(){
		IFileOutput output = new ServiceFileOutput();
		
		return output.getFileContent(methods, pojoName);
	}
	
	/**
	 * 解析出ServiceImpl内容
	 * @author 王文路
	 * @date 2015-8-27
	 * @return
	 */
	private String getServiceImplContent(){
		
		IFileOutput output = new ServiceImplFileOutput();
		
		return output.getFileContent(methods, pojoName);
	}
	
	private List<SJMethod> getMethods (){
		
		List<SJMethod> rets = new ArrayList<SJMethod>();
		
		SJFileReader reader =  new JavaFileReader(filePath);
		
		String totalContent = reader.readString();
		
		ContentSeparateUtil contentUtil = new ContentSeparateUtil(totalContent);
		List<String> methodContents = contentUtil.getMethodContents();
		
		for (String content : methodContents) {
			
			MethodSeparateUtil methodUtil = new MethodSeparateUtil(content);
			
			rets.add(methodUtil.getMethod());
		}
		
		return rets;
		
	}
	
	public static void main(String[] args) {
		ParserOneFileService oneService = new ParserOneFileService("D:\\DocumentAPIController.java", "D:\\", "Document");
		
//		System.out.println(oneService.getServiceContent());
		System.out.println(oneService.outputOne()); 
	}
	

}
