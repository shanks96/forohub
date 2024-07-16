package com.alura.forohub.infra.exception;

public class DontExist extends Throwable{
    public DontExist(){
        super();
    }

    public DontExist(String mensaje){
        super(mensaje);
    }

    public DontExist(String mensaje, Throwable motivo){
        super(mensaje, motivo);
    }
}
