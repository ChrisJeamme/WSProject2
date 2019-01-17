package com.projetws.tools;

public class StorageExceptionHandler extends RuntimeException{


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StorageExceptionHandler(String message) {
        super(message);
    }

    public StorageExceptionHandler(String message, Throwable cause) {
        super(message, cause);
    }
}
