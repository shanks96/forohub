package com.alura.forohub.dto;

import com.alura.forohub.models.user.User;

public record UserDTO(String nombre, String email) {

    public UserDTO(User autor){
        this(autor.getNombre(), autor.getEmail());
    }
}
