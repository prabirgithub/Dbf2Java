package org.flametech.dbf2java.core;

import java.nio.charset.Charset;

public final class DBFile {
	private String fileName;
	Charset stringCharset = null;
	
	public DBFile(String fullFilePath)
	{
		this.fileName = fullFilePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setCharset(String charset) {
		stringCharset = Charset.forName(charset);
	}
	
	public Charset getCharset() {
		return stringCharset;
	}
	
	
}
