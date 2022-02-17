package br.com.sijoga.exception;

public class IntimacaoException extends Exception {

    public IntimacaoException(String message) {
        super(message);
    }
    
    public IntimacaoException(String message, Throwable cause){
        super(message, cause);
    }
            
}
