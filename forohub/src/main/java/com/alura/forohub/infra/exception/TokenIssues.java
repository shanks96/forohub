package com.alura.forohub.infra.exception;

public class TokenIssues extends Throwable{
    public TokenIssues(){
        super();
    }
    public TokenIssues(String mensaje){
        super(mensaje);
    }
    public TokenIssues(String mensaje, Throwable motivo){
        super(mensaje, motivo);
    }
}
