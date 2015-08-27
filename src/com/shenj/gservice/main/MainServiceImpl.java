package com.shenj.gservice.main;

import java.io.File;

import com.shenj.gservice.util.MainUtil;

/**
 * 主服务 实现 
 * 
 * @author 王文路
 * @date 2015-8-27
 *
 */
public class MainServiceImpl implements MainService{
	
	private String inputPage;
	private String outputPath;
	
	public MainServiceImpl( String inputPage , String outputPath) {
		super();
		this.inputPage = inputPage;
		this.outputPath = outputPath;
	}


	@Override
	public boolean saveFiles(){
		
		File file = new File(inputPage);
		
		this.optionFiles(file);
		
		return true;
		
	}
	
	/**
	 * 递归处理文件
	 * 
	 * @author 王文路
	 * @date 2015-8-27
	 * @param inputFile
	 * @return
	 */
	private boolean optionFiles(File inputFile) {
		
		if(inputFile.isDirectory()) {
			File[] files = inputFile.listFiles();
			
			for (File file : files){
				optionFiles(file);
			}
		} else {
			return this.saveOneFile(inputFile.getPath());
		}
		
		return true;
	}
	
	
	/**
	 * 保存文件
	 * @author 王文路
	 * @date 2015-8-27
	 * @param filePath
	 * @return
	 */
	private boolean saveOneFile(String filePath){
		
		String fileExtension = MainUtil.getFilenameExtension(filePath);
		
		if (!"java".equals(fileExtension))
			return false;
		
		String pojoName =  MainUtil.getPojoName(filePath);
		
		if (pojoName == null)
			return false;
		
		ParserOneFileService oneService = new ParserOneFileService(filePath , outputPath ,  pojoName);
		return oneService.outputOne();
		
	}
	
	public static void main(String[] args) {
		
		MainService mainService = new MainServiceImpl("D:\\input", "D:\\com\\shenj\\teworksandroid\\http");
		mainService.saveFiles();
	}
	
}
