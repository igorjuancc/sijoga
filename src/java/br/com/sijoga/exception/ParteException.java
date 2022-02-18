package br.com.sijoga.exception;

public class ParteException extends Exception {

    public ParteException(String message) {
        super(message);
    }
    
    public ParteException(String message, Throwable cause){
        super(message, cause);
    }
            
}
