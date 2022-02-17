package br.com.sijoga.exception;

public class AdvogadoException extends Exception {

    public AdvogadoException(String message) {
        super(message);
    }
    
    public AdvogadoException(String message, Throwable cause){
        super(message, cause);
    }
            
}
