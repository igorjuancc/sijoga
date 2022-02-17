package br.com.sijoga.exception;

public class DocumentoException extends Exception {

    public DocumentoException(String message) {
        super(message);
    }
    
    public DocumentoException(String message, Throwable cause){
        super(message, cause);
    }
            
}
