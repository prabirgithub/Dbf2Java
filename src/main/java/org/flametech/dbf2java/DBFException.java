package org.flametech.dbf2java;

import java.io.IOException;

public class DBFException extends IOException{
	private static final long serialVersionUID = 1L;
	
	public DBFException() {
		super();
    }

    public DBFException(String msg) {
    	super(msg);
    }
    
    public DBFException(Throwable throable) {
    	super(throable);
    }    
}
