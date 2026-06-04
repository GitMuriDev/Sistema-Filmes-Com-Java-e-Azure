package com.Senai.Filmes.Service;

import com.Senai.Filmes.Repository.IUsuarioRepository;
import com.Senai.Filmes.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;


    //Cadastrar(novo usuário)

    //Login

}
