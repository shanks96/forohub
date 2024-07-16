package com.alura.forohub.models.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name="user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Perfil perfil;
    private boolean activo;

    public User(UserInfo userInfo){
        this.nombre = userInfo.nombre();
        this.email = userInfo.email();
        this.perfil = userInfo.perfil();
        this.password = userInfo.password();
        this.activo = true;
    }

    public void userUpdate(UserUpdateInfo userUpdateInfo){
        if(userUpdateInfo.nombre() != null){
            this.nombre = userUpdateInfo.nombre();
        }
        if (userUpdateInfo.password() != null){
            this.password = userUpdateInfo.password();
        }
    }

    public void deactivate(){
        this.activo = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return switch (this.perfil) {
            case ADMINISTRADOR -> List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
            case MODERADOR -> List.of(new SimpleGrantedAuthority("ROLE_MODERATOR"));
            case ESTUDIANTE -> List.of(new SimpleGrantedAuthority("ROLE_STUDENT"));
            case INSTRUCTOR -> List.of(new SimpleGrantedAuthority("ROLE_INSTRUCTOR"));
            default -> throw new IllegalArgumentException("Perfil desconocido: " + this.perfil);
        };
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.nombre;
    }
}
