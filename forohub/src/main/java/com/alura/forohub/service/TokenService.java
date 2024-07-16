package com.alura.forohub.service;

import com.alura.forohub.dto.UserDTO;
import com.alura.forohub.infra.exception.DontExist;
import com.alura.forohub.models.user.User;
import com.alura.forohub.models.user.UserInfo;
import com.alura.forohub.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.secret}")
    private String apiSecret;

    @Autowired
    private UserRepository userRepository;

    public String generarToken(User user) throws DontExist, TokenIssues{
        try{
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("forohub")
                    .withSubject(user.getEmail())
                    .withClaim("id", user.getId())
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);
        }catch (JWTCreationException e){
            throw new TokenIssues("No existe el usuario");
        }
    }

    @SuppressWarnings("null")
    public String getSubject(String token) throws InvalidToken{
        if (token == null){
            throw new InvalidToken("El token es nulo");
        }
        DecodedJWT verifier = null;
        try{
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            verifier = JWT.require(algorithm)
                    .withIssuer("forohub")
                    .build()
                    .verify(token);
            verifier.getSubject();
        }catch (JWTCreationException e){
            System.out.println(e.toString());
        }
        if(verifier.getSubject() == null){
            throw new InvalidToken("Verifier invalido");
        }
        return verifier.getSubject();
    }

    private Instant expirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-06:00"));
    }

    public UserDTO registrarUsuario(@Valid UserInfo userInfo){
        var user = new User(userInfo);
        userRepository.save(user);
        return new UserDTO(user);
    }
}
