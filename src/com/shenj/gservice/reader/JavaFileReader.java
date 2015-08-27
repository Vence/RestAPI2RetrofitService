package com.shenj.gservice.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class JavaFileReader extends SJFileReader{

	public JavaFileReader(String filePath) {
		super(filePath);
		
	}

	@Override
	public String readString() {
		
		String str = "";

	    File file=new File(filePath);

	    try {

	        FileInputStream in=new FileInputStream(file);

	        int size=in.available();

	        byte[] buffer=new byte[size];

	        in.read(buffer);

	        in.close();

	        str=new String(buffer,"utf-8");

	    } catch (IOException e) {

	    	e.printStackTrace();
	        return null;
	    }

	    return str;
	}
	
	public static void main(String[] args){
		SJFileReader reader = new JavaFileReader("D:\\DomainAPIController.java");

		String content = reader.readString();
		
		content = content.replace("\r\n", "");
		System.out.println(content);
	}

}
