package com.alura.forohub.infra.exception;

public class InvalidToken extends Throwable{
    public InvalidToken(){
        super();
    }
    public InvalidToken(String mensaje){
        super(mensaje);
    }
    public InvalidToken(String mensaje, Throwable causa){
        super(mensaje, causa);
    }
}
