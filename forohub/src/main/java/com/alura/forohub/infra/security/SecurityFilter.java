package com.alura.forohub.infra.security;

import com.alura.forohub.infra.exception.InvalidToken;
import com.alura.forohub.repository.UserRepository;
import com.alura.forohub.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter  extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        var authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            var token = authHeader.replace("Bearer ", "");
            String email;
            try{
                email = tokenService.getSubject(token);
                if (email != null){
                    var user = userRepository.UserByEmail(email);
                    if (!user.isPresent()){
                        throw new IllegalStateException("Usuario no encontrado");
                    }
                    var authentication = new UsernamePasswordAuthenticationToken(user.get(), null, user.get().getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }
            } catch (InvalidToken e) {
                throw new IllegalStateException();
            }
        }
        filterChain.doFilter(request, response);
    }

}
