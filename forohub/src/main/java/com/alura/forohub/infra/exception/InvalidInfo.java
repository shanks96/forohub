package com.alura.forohub.infra.exception;

public class InvalidInfo extends Throwable{
    public InvalidInfo(){
        super();
    }
    public InvalidInfo(String mensaje){
        super(mensaje);
    }
    public InvalidInfo(String mensaje, Throwable motivo){
        super(mensaje, motivo);
    }
}
