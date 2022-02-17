package br.com.sijoga.exception;

public class FaseException extends Exception {

    public FaseException(String message) {
        super(message);
    }
    
    public FaseException(String message, Throwable cause){
        super(message, cause);
    }
            
}
