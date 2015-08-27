package com.shenj.gservice.writer;


public abstract class SJFileWriter {
	
	protected String outputPath;
	
	public SJFileWriter(String outputPath) {
		super();
		this.outputPath = outputPath;
	}

	public abstract boolean writeToFile() ;

}
