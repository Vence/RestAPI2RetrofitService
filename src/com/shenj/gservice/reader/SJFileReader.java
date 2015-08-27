package com.shenj.gservice.reader;

public  abstract class SJFileReader {
	
	protected String filePath;
	
	public SJFileReader(String filePath) {
		super();
		this.filePath = filePath;
	}
	
	public abstract String readString();
	
}
