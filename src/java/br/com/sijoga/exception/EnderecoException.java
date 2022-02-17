package br.com.sijoga.exception;

public class EnderecoException extends Exception {

    public EnderecoException(String message) {
        super(message);
    }
    
    public EnderecoException(String message, Throwable cause){
        super(message, cause);
    }
            
}
