package br.com.sijoga.exception;

public class JuizException extends Exception {

    public JuizException(String message) {
        super(message);
    }
    
    public JuizException(String message, Throwable cause){
        super(message, cause);
    }
            
}
