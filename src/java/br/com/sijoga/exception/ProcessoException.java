package br.com.sijoga.exception;

public class ProcessoException extends Exception {

    public ProcessoException(String message) {
        super(message);
    }
    
    public ProcessoException(String message, Throwable cause){
        super(message, cause);
    }
            
}
