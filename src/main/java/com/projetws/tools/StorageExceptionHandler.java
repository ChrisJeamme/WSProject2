package com.projetws.tools;

public class StorageExceptionHandler extends RuntimeException{


    public StorageExceptionHandler(String message) {
        super(message);
    }

    public StorageExceptionHandler(String message, Throwable cause) {
        super(message, cause);
    }
}
