package com.shenj.gservice.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JavaFileWriter  extends SJFileWriter{
	
	private String content;
	
	public JavaFileWriter(String outputPath, String content) {
		super(outputPath);
		this.content = content;
	}


	@Override
	public boolean writeToFile() {
		
		System.out.println("保存文件：" + outputPath);
		
		File file = new File(this.outputPath);
		
		if (file.isDirectory())
			return false;
		
		try {
			
			FileWriter writer = new FileWriter(this.outputPath);
			
		    writer.write(content);
            writer.flush();
            writer.close();
			 
		} catch (IOException e) {
			
			e.printStackTrace();
			return false;
		}
		
		 return true;
		
	}
	
	

}
