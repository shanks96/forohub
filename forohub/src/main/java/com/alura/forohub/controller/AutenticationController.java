package com.alura.forohub.controller;

import com.alura.forohub.dto.UserDTO;
import com.alura.forohub.infra.exception.TokenIssues;
import com.alura.forohub.infra.exception.DontExist;
import com.alura.forohub.models.jwt.TokenInfo;
import com.alura.forohub.models.user.Login;
import com.alura.forohub.models.user.User;
import com.alura.forohub.models.user.UserInfo;
import com.alura.forohub.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "\login")
public class AutenticationController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<TokenInfo> UserAutentication(@RequestBody @Valid Login usuario) throws DontExist, TokenIssues {
        Authentication authToken = new UsernamePasswordAuthenticationToken(usuario.email(), usuario.password());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken((User) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new TokenInfo(JWTtoken));
    }

    @PostMapping("/registrar")
    public ResponseEntity<UserDTO> registrarUsuario(@RequestBody @Valid UserInfo userInfo) throws DontExist,TokenIssues{
        var nuevoUsuario = tokenService.registrarUsuario(userInfo);
        return ResponseEntity.ok(nuevoUsuario);
    }
}
